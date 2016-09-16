package net.csthings.antreminder.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.csthings.antreminder.entity.User;
import net.csthings.antreminder.security.AuthenticationImpl;
import net.csthings.antreminder.service.ServiceException;
import net.csthings.antreminder.service.reminder.ResultDto;
import net.csthings.antreminder.service.rest.RestClientService;
import net.csthings.antreminder.utils.Status;
import net.csthings.antreminder.websoc.utils.WebSocParser;

@RestController
@RequestMapping(value = "/account")
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
    String responsePage = "/reminders";

    RestClientService restService;
    ObjectMapper mapper;

    @PostConstruct
    public void init() {
        restService = new RestClientService(API_ACCOUNT_URL);
        mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ModelAndView login(@RequestBody MultiValueMap body, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        List<String> pages = (List<String>) body.get("page");
        String responsePage = pages.get(0);
        responsePage = responsePage.equals("/account/login") ? ReminderController.PAGE_NAME : responsePage;

        responsePage = "redirect:/" + responsePage;

        ResultDto<String> apiResponse = null;
        try {
            String result = restService.post(API_ACCOUNT_LOGIN, WebSocParser.toMultivaluedMap(body));
            apiResponse = mapper.readValue(result, new TypeReference<ResultDto<String>>() {
            });
        }
        catch (IOException | ServiceException e) {
            LOG.error("Error posting to server", e);
            // TODO: fail here
        }
        if (null == apiResponse || apiResponse.getStatus().equals(Status.FAILED)) {
            // model.addAttribute("apiResponse", apiResponse);
            return new ModelAndView(responsePage);
        }
        List<String> emails = (List<String>) body.get("email");
        String token = apiResponse.getItem();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 24);
        Cookie cookie = new Cookie("session", token);
        cookie.setPath(responsePage);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        User user = new User(emails.get(0), true, token);
        SecurityContextHolder.getContext().setAuthentication(new AuthenticationImpl(user, token));
        // redirectAttrs.addAttribute("authenticated", user.getAuthenticated());
        // redirectAttrs.addAttribute("email", user.getEmail());
        // try {
        // response.sendRedirect(
        // request.getContextPath() + (responsePage.equals("redirect:index") ?
        // "redirect:/" : responsePage));
        // }
        // catch (IOException e) {
        // LOG.error("Could not chage response's redirect", e);
        // }
        return new ModelAndView(responsePage);

    }

}
