package net.csthings.antreminder.websoc.service;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import net.csthings.config.WebSocSettings;
import net.csthings.service.rest.ClientUtils;

@Singleton
@Service("websocRestService")
public class RestClientService {
    Logger LOG = LoggerFactory.getLogger(RestClientService.class);
    Client client;
    WebResource resource;
    ClientResponse response;
    ObjectMapper mapper;

    @Autowired
    WebSocSettings websocSettings;

    @PostConstruct
    public void init() {
        client = Client.create(ClientUtils.configureClient());
        resource = client.resource(websocSettings.getBaseUrl());
        mapper = new ObjectMapper();
    }

    public String getHtml(MultivaluedMap params, String path)
            throws UniformInterfaceException, ClientHandlerException, JsonProcessingException {
        response = resource.accept(MediaType.TEXT_HTML).accept(MediaType.APPLICATION_XHTML_XML)
                .accept(MediaType.APPLICATION_XML).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class, params);

        if (response.getStatus() != Status.OK.getStatusCode())
            return null;
        else
            return response.getEntity(String.class);
    }
}
