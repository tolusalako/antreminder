package net.csthings.antreminder.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.csthings.antreminder.service.reminder.ReminderDto;
import net.csthings.antreminder.service.rest.RestClientService;
import net.csthings.services.common.dto.ResultDto;

@RestController
@RequestMapping(value = "${reminders.url}")
public class ReminderController {
    Logger LOG = LoggerFactory.getLogger(ReminderController.class);

    public final static String PAGE = "reminders";

    /**
     * Sub Pages
     */
    @Value("${reminders.url}")
    private String REMINDER;

    /**
     * Api links
     */
    @Value("${api.reminders.url}")
    private String API_REMINDER_URL;
    @Value("${api.reminders.url.get}")
    private String API_REMINDER_GET;
    @Value("${api.reminders.url.getall}")
    private String API_REMINDER_GETALL;
    @Value("${api.reminders.url.add}")
    private String API_REMINDER_ADD;

    RestClientService restService;
    ObjectMapper mapper;

    private String test_account_id = "0013d030-6418-11e6-880a-a51705403ed5";

    @PostConstruct
    public void init() {
        restService = new RestClientService(API_REMINDER_URL);
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView reminderGetAll(Model model, @QueryParam("status") String status) {
        // Get Reminders
        MultivaluedMap<String, String> queries = new MultivaluedMapImpl();
        queries.add("accountid", test_account_id);
        String result = restService.get(API_REMINDER_GETALL, queries);
        ResultDto<List<ReminderDto>> rez = null;
        try {
            rez = mapper.readValue(result, new TypeReference<ResultDto<List<ReminderDto>>>() {
            });
        }
        catch (IOException e) {
            LOG.error("Error mapping reminders", e);
            // TODO: fail here
        }
        // if (!rez.getStatus().equals(Status.SUCCESS))
        // TODO: fail here
        List<ReminderDto> reminders = rez.getItem();
        model.addAttribute("reminders", reminders);
        return new ModelAndView(PAGE, "Model", model);
    }

    // Add reminder
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String reminderAdd(HttpServletRequest servletRequest, Model model, @RequestBody MultiValueMap body) {
        String response;
        try {
            response = mapper.writeValueAsString(body);
        }
        catch (JsonProcessingException e) {
            LOG.error("Could not convert data to String", e);
            response = "Error";
        }
        return response;
    }

    // @RequestMapping(method = RequestMethod.POST,
    // // headers = { "content-type=application/x-www-form-urlencoded" },
    // produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    // public String reminderPost(HttpServletRequest servletRequest,
    // @RequestBody MultiValueMap body, Model model) {
    // model.asMap().clear();
    // try {
    // String response = restService.getHtml(WebSocParser.toMultivalueMap(body),
    // "");
    // model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.SEARCH);
    // model.addAttribute(Attributes.PAGE, response);
    // }
    // catch (Exception e) {
    // LOG.error("WebSoc POST error.", e);
    // model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.ERROR);
    // }
    //
    // if (!model.containsAttribute(Attributes.FRAGMENT))
    // model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.FORM);
    // return PAGE;
    // }

    private void getReminders() {

    }
}
