package net.csthings.antreminder.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.google.gson.JsonObject;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import net.csthings.antreminder.security.SecurityUtils;
import net.csthings.antreminder.services.ServiceException;
import net.csthings.antreminder.services.reminder.ReminderDto;
import net.csthings.antreminder.services.reminder.ResultDto;
import net.csthings.antreminder.services.rest.RestClientService;
import net.csthings.antreminder.utils.FormUtils;
import net.csthings.antreminder.utils.Status;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    Logger LOG = LoggerFactory.getLogger(ReminderController.class);

    public static final String PAGE_NAME = "reminders";

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

    @PostConstruct
    public void init() {
        restService = new RestClientService(API_REMINDER_URL);
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView reminderGetAll(Model model, @QueryParam("status") String status) {
        // Get Reminders
        MultivaluedMap<String, String> queries = new MultivaluedMapImpl();
        queries.add("accountid", SecurityUtils.getAccountId().toString());
        String response = restService.get(API_REMINDER_GETALL, queries);
        ResultDto<List<ReminderDto>> rez = null;
        try {
            rez = mapper.readValue(response, new TypeReference<ResultDto<List<ReminderDto>>>() {
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
        return new ModelAndView(PAGE_NAME, "Model", model);
    }

    // Add reminder
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String reminderAdd(Model model, @RequestBody MultiValueMap<String, String> body,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

        String response = "";
        JsonObject jsonResponse = new JsonObject();
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {

            if (!SecurityUtils.isAuthenticated()) {
                httpResponse.sendError(javax.ws.rs.core.Response.Status.FORBIDDEN.getStatusCode());
                jsonResponse.addProperty("msg", "User is Unauthorized");
                jsonResponse.addProperty("status", Status.FAILED);
            }
            else {
                Map<String, Object> map = FormUtils.toSingleValuedMap(body);
                map.put("accountId", SecurityUtils.getAccountId());
                map.remove("_csrf");
                String json = mapper.writeValueAsString(map);
                response = restService.post(API_REMINDER_ADD, json);
                return response;
            }
        }
        catch (JsonProcessingException | ServiceException e) {
            LOG.error("Could not convert data to String", e);
            jsonResponse.addProperty("msg", "Could not add Reminder.");
            jsonResponse.addProperty("status", Status.FAILED);
        }
        return jsonResponse.toString();
    }

    private void getReminders() {

    }
}
