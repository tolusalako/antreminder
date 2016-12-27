package net.csthings.antreminder.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.security.SecurityUtils;
import net.csthings.antreminder.services.ServiceException;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.antreminder.utils.FormUtils;
import net.csthings.antreminder.utils.Status;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    Logger LOG = LoggerFactory.getLogger(ReminderController.class);

    public static final String PAGE_NAME = "reminders";

    @Autowired
    ReminderService reminderService;
    private static final ObjectMapper objMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView reminderGet(Model model, @QueryParam("status") String status, Authentication auth) {
        // Get Reminders
        if (null != auth && auth.isAuthenticated()) {
            ResultDto<Collection<ReminderDto>> rez = reminderService.get(SecurityUtils.getAccountId(), status);
            Collection<ReminderDto> reminders = rez.getItem();
            model.addAttribute("reminders", reminders);
            return new ModelAndView(PAGE_NAME, "Model", model);
        }
        else
            return new ModelAndView("redirect:/" + AccountController.LOGIN_NAME, "Model", model);
    }

    // Add reminder
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String reminderAdd(Model model, @RequestBody MultiValueMap<String, String> body,
            HttpServletResponse httpResponse) throws IOException {

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
                map.remove("_csrf");
                ReminderDto reminder = objMapper.convertValue(map, ReminderDto.class);
                map.put("accountId", SecurityUtils.getAccountId());
                EmptyResultDto result = reminderService.add(SecurityUtils.getAccountId(), SecurityUtils.getEmail(),
                        reminder);
                if (!result.getStatus().equals(Status.SUCCESS))
                    throw new ServiceException(result.getMsg());
                jsonResponse.addProperty("msg", "Reminder added.");
                jsonResponse.addProperty("status", Status.SUCCESS);
                return jsonResponse.toString();
            }
        }
        catch (JsonProcessingException e) {
            LOG.error("Could not convert data to String", e);
            jsonResponse.addProperty("msg", "Could not add Reminder.");
            jsonResponse.addProperty("status", Status.FAILED);
        }
        catch (ServiceException e) {
            LOG.error("Error adding reminder", e);
            jsonResponse.addProperty("msg", e.getMessage());
            jsonResponse.addProperty("status", Status.FAILED);
        }
        return jsonResponse.toString();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String reminderRemove(Model model, @RequestBody MultiValueMap<String, String> body,
            HttpServletResponse httpResponse) throws IOException {

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
                map.remove("_csrf");
                ReminderDto reminder = objMapper.convertValue(map, ReminderDto.class);
                map.put("accountId", SecurityUtils.getAccountId());
                EmptyResultDto result = reminderService.delete(SecurityUtils.getAccountId(), SecurityUtils.getEmail(),
                        reminder);
                if (!result.getStatus().equals(Status.SUCCESS))
                    throw new ServiceException(result.getMsg());
                jsonResponse.addProperty("msg", "Reminder remooved.");
                jsonResponse.addProperty("status", Status.SUCCESS);
                return jsonResponse.toString();
            }
        }
        catch (JsonProcessingException e) {
            LOG.error("Could not convert data to String", e);
            jsonResponse.addProperty("msg", "Could not add Reminder.");
            jsonResponse.addProperty("status", Status.FAILED);
        }
        catch (ServiceException e) {
            LOG.error("Error adding reminder", e);
            jsonResponse.addProperty("msg", e.getMessage());
            jsonResponse.addProperty("status", Status.FAILED);
        }
        return jsonResponse.toString();
    }
}
