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
package net.csthings.antreminder.services.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;

import net.csthings.antreminder.services.ServiceException;

public class RestClientService {
    Logger LOG = LoggerFactory.getLogger(RestClientService.class);
    Client client;
    WebResource resource;
    ObjectMapper mapper;

    public RestClientService(String url) {
        ClientConfig config = ClientUtils.configureClient();
        client = Client.create(config);
        resource = client.resource(url);
        mapper = new ObjectMapper();
    }

    public String post(String path, MultivaluedMap form) throws ServiceException {
        ClientResponse response = resource.path(path).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,
                form);
        return handleResponse(response);
    }

    public String post(String path, String json) throws ServiceException {
        ClientResponse response = resource.path(path).accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        return handleResponse(response);
    }

    public String get(String path, MultivaluedMap queryParams) {
        return resource.path(path).queryParams(queryParams).accept(MediaType.APPLICATION_JSON).get(String.class);
    }

    public String getHtml(MultivaluedMap params, String path)
            throws UniformInterfaceException, ClientHandlerException, JsonProcessingException, ServiceException {

        ClientResponse response = resource.accept(MediaType.TEXT_HTML).accept(MediaType.APPLICATION_XHTML_XML)
                .accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class, params);
        return handleResponse(response);
    }

    public String handleResponse(ClientResponse response) throws ServiceException {
        if (response.getStatus() != Status.OK.getStatusCode())
            throw new ServiceException("Got unexpected rest response");
        else
            return response.getEntity(String.class);
    }
}
