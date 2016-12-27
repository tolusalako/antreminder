/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.services.email.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.csthings.antreminder.config.MailSettings;
import net.csthings.antreminder.services.email.EmailService;
import net.csthings.antreminder.services.email.exception.EmailException;
import net.csthings.common.utils.CommonUtils;

public class MailGunEmailServiceImpl implements EmailService {
    Logger LOG = LoggerFactory.getLogger(MailGunEmailServiceImpl.class);
    public static final String MAIL_URL = "https://api.mailgun.net/v3/csthings.net/messages";
    public static final String EVENTS_URL = "https://api.mailgun.net/v3/csthings.net/events";

    @Autowired
    private MailSettings mailSettings;

    public String name;
    public String email;
    private String key;
    private Client client;
    private WebResource mailResource;
    private WebResource eventsResource;
    JsonParser parser;

    @PostConstruct
    private void init() {

        this.name = mailSettings.getName();
        this.email = mailSettings.getEmail();
        this.key = mailSettings.getKey();
        client = Client.create();
        client.addFilter(getAuthFilter());
        parser = new JsonParser();
        mailResource = client.resource(MAIL_URL);
        eventsResource = client.resource(EVENTS_URL);
    }

    @Override
    public String sendEmail(String subject, String text, String[] to, String[] cc, String[] bcc) throws EmailException {
        if (CommonUtils.allNull(to, cc, bcc))
            throw new EmailException("to, cc, and bcc cannot all be null.");

        MultivaluedMap<String, String> form = new MultivaluedMapImpl();
        form.add("from", StringUtils.join(name, " ", "<", email, ">"));
        if (!CommonUtils.isNull(to))
            for (String email : to)
                form.add("to", email);
        if (!CommonUtils.isNull(cc))
            for (String email : cc)
                form.add("cc", email);
        if (!CommonUtils.isNull(bcc))
            for (String email : bcc)
                form.add("bcc", email);

        form.add("subject", subject);
        form.add("text", text);
        LOG.info("Sending email {} to {}", subject.toString(), to.toString());
        return processEmail(mailResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, form));
    }

    @Override
    public String sendHtmlEmail(String subject, String text, String[] to, String[] cc, String[] bcc)
            throws EmailException {
        if (CommonUtils.allNull(to, cc, bcc))
            throw new EmailException("to, cc, and bcc cannot all be null.");

        MultivaluedMap<String, String> form = new MultivaluedMapImpl();
        form.add("from", StringUtils.join(name, " ", "<", email, ">"));
        if (!CommonUtils.isNull(to))
            for (String email : to)
                form.add("to", email);
        if (!CommonUtils.isNull(cc))
            for (String email : cc)
                form.add("cc", email);
        if (!CommonUtils.isNull(bcc))
            for (String email : bcc)
                form.add("bcc", email);

        form.add("subject", subject);
        form.add("html", text);
        LOG.info("Sending email {} to {}", subject.toString(), to.toString());
        return processEmail(mailResource.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class, form));
    }

    @Override
    public Map<String, Boolean> getEmailReport(String messageId, String[] emails) throws EmailException {
        Map<String, Boolean> result = new HashMap<>();
        for (String email : emails)
            // Assume all emails were delivered successfully
            result.put(email, true);

        MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
        queryParams.add("message-id", messageId);
        queryParams.add("event", "(failed OR rejected)");
        JsonArray rez;
        try {
            rez = processEvent(
                    eventsResource.queryParam("message-id", messageId).queryParam("event", "(failed OR rejected)")
                            .accept(MediaType.APPLICATION_JSON).get(ClientResponse.class));
        }
        catch (MailgunEmailServiceException e) {
            throw new EmailException(e.getMessage(), e);
        }

        for (int i = 0; i < rez.size(); ++i) {
            JsonObject json = rez.get(i).getAsJsonObject();
            String reason = json.get("reason").getAsString();
            String email = json.get("recipient").getAsString();
            String id = json.get("id").getAsString();
            LOG.error("[{}] - Could not deliver email to {}. Reason: {}", id, email, reason);
            result.put(email, false);
        }
        return result;
    }

    private String processEmail(ClientResponse response) throws EmailException {
        JsonElement message = parser.parse(response.getEntity(String.class));
        if (response.getStatus() != Status.OK.getStatusCode())
            throw new EmailException("Could not send email. Reason: " + message);

        String messageId = message.getAsJsonObject().get("id").getAsString();
        return messageId.substring(1, messageId.length() - 1);
    }

    private JsonArray processEvent(ClientResponse response) throws MailgunEmailServiceException {
        JsonObject message = parser.parse(response.getEntity(String.class)).getAsJsonObject();
        if (response.getStatus() != Status.OK.getStatusCode())
            throw new MailgunEmailServiceException(
                    "Could not get status. Reason: " + response.getStatusInfo().toString());

        return message.get("items").getAsJsonArray();
    }

    public HTTPBasicAuthFilter getAuthFilter() {
        return new HTTPBasicAuthFilter("api", key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public class MailgunEmailServiceException extends Exception {
        public MailgunEmailServiceException(String message) {
            super(message);
        }
    }

}
