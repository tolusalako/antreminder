package net.csthings.antreminder.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.csthings.antreminder.entity.User;
import net.csthings.antreminder.security.AuthenticationImpl;
import net.csthings.antreminder.security.SecurityUtils;
import net.csthings.antreminder.service.ServiceException;
import net.csthings.antreminder.service.reminder.ResultDto;
import net.csthings.antreminder.service.rest.RestClientService;
import net.csthings.antreminder.utils.Status;
import net.csthings.antreminder.websoc.utils.WebSocParser;

@RestController
public class AccountController {
    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    /**
     * Api links
     */
    @Value("${api.account.url}")
    private String API_ACCOUNT_URL;
    @Value("${api.account.url.login}")
    private String API_ACCOUNT_LOGIN;
    @Value("${api.account.url.create}")
    private String API_ACCOUNT_REGISTER;
    @Value("${api.account.url.validate}")
    private String API_ACCOUNT_VALIDATE;

    public final static String PAGE_NAME = "login";

    RestClientService restService;
    ObjectMapper mapper;

    @PostConstruct
    public void init() {
        restService = new RestClientService(API_ACCOUNT_URL);
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ModelAndView login(Model model, @RequestBody MultiValueMap body, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        List<String> pages = (List<String>) body.get("page");
        String path = body.keySet().contains(PAGE_NAME) ? API_ACCOUNT_LOGIN : API_ACCOUNT_REGISTER;
        String responsePage = pages.get(0);

        ResultDto<Object> apiResponse = null;
        try {
            String result = restService.post(path, WebSocParser.toMultivaluedMap(body));
            apiResponse = mapper.readValue(result, new TypeReference<ResultDto<Object>>() {
            });
        }
        catch (IOException | ServiceException e) {
            LOG.error("Error posting to server", e);
            // TODO: fail here
        }
        if (null == apiResponse || apiResponse.getStatus().equals(Status.FAILED)) {
            model.addAttribute("apiResponse", apiResponse);
            return new ModelAndView(AccountController.PAGE_NAME, "Model", model);
        }

        // Redirect logins and home to Reminders
        if (responsePage.equals(AccountController.PAGE_NAME) || responsePage.equals("/"))
            responsePage = ReminderController.PAGE_NAME;
        else
            responsePage = responsePage.substring(1, responsePage.length());

        responsePage = "redirect:/" + responsePage;

        Gson gson = new Gson();
        List<String> emails = (List<String>) body.get("email");
        String jsonString = gson.toJson(apiResponse.getItem());
        JsonObject credentials = new JsonParser().parse(jsonString).getAsJsonObject();

        String token = credentials.get("sessionId").getAsString();
        UUID accountId = UUID.fromString(credentials.get("accountId").getAsString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 24);
        Cookie cookie = new Cookie("session", token);
        cookie.setPath(responsePage);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        User user = new User(accountId, emails.get(0), true, token);
        Authentication authentication = new AuthenticationImpl(user, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ModelAndView(responsePage);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ModelAndView loginGet(Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        return new ModelAndView(
                SecurityUtils.isAuthenticated() ? ReminderController.PAGE_NAME : AccountController.PAGE_NAME, "Model",
                model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public ModelAndView logout(Model model, @RequestBody MultiValueMap body, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {

        return new ModelAndView("redirect:/index");
    }

}
