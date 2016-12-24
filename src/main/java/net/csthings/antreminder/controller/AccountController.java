package net.csthings.antreminder.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.csthings.antreminder.entity.dto.ReminderDto;
import net.csthings.antreminder.security.SecurityUtils;
import net.csthings.antreminder.services.account.AccountService;
import net.csthings.antreminder.services.account.LoginService;
import net.csthings.antreminder.services.account.impl.LoginServiceImpl;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.antreminder.utils.FormUtils;
import net.csthings.antreminder.utils.Status;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;

@RestController
public class AccountController {
    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    public static final String PAGE_NAME = "login";

    @Autowired
    AccountService accountService;

    @Autowired
    LoginService loginService;

    @Autowired
    ReminderService reminderService;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ModelAndView login(Model model, @RequestBody MultiValueMap body, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> singleValuedBody = FormUtils.toSingleValuedMap(body);
        ResultDto<Cookie> loginResponse = null;
        if (body.keySet().contains(PAGE_NAME)) {
            // LOGIN
            loginResponse = loginService.login(singleValuedBody.get("email").toString(),
                    singleValuedBody.get("password").toString(), body.containsKey("rememberme"));
            if (loginResponse.getStatus().equals(Status.FAILED)) {
                model.addAttribute("apiResponse", loginResponse.getMsg());
                return new ModelAndView(AccountController.PAGE_NAME, "Model", model);
            }
        }
        else {
            // REGISTER
            EmptyResultDto rez = accountService.createAccount(singleValuedBody.get("email").toString(),
                    singleValuedBody.get("password").toString());
            if (rez.getStatus().equals(Status.FAILED)) {
                model.addAttribute("apiResponse", rez.getMsg());
                return new ModelAndView(AccountController.PAGE_NAME, "Model", model);
            }
            else {
                model.addAttribute("apiResponse", "confirmation");
                return new ModelAndView(AccountController.PAGE_NAME, "Model", model);
            }
            // return confirmation here
        }

        // After successful login
        response.addCookie(loginResponse.getItem());
        List<String> pages = (List<String>) body.get("page");
        String responsePage = pages.get(0);
        responsePage = responsePage.startsWith("/") ? responsePage.substring(1, responsePage.length()) : responsePage;
        // Redirect logins and home to Reminders
        if (responsePage.equals(AccountController.PAGE_NAME) || responsePage.equals(" "))
            responsePage = ReminderController.PAGE_NAME;
        else if (responsePage.equals(ScheduleController.PAGE_NAME))
            responsePage = ScheduleController.PAGE_NAME;
        else {
            responsePage = responsePage.substring(1, responsePage.length());
            ResultDto<Collection<ReminderDto>> rez = reminderService.get(SecurityUtils.getAccountId(), "");
            Collection<ReminderDto> reminders = rez.getItem();
            model.addAttribute("reminders", reminders);
        }

        responsePage = "redirect:/" + responsePage;

        return new ModelAndView(responsePage, "Model", model);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ModelAndView loginGet(Model model, HttpServletRequest request) {

        if (SecurityUtils.isAuthenticated())
            return new ModelAndView(ReminderController.PAGE_NAME, "Model", model);
        if (request != null && request.getCookies() != null)
            for (Cookie cookie : request.getCookies())
                if (cookie.getName().equals(LoginServiceImpl.SESSION_NAME)
                        && loginService.validateSession(cookie.getValue()).getItem())
                    return new ModelAndView(ReminderController.PAGE_NAME, "Model", model);

        return new ModelAndView(
                SecurityUtils.isAuthenticated() ? ReminderController.PAGE_NAME : AccountController.PAGE_NAME, "Model",
                model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public ModelAndView validatePost(Model model, @RequestParam(value = "token", defaultValue = "") String token,
            @RequestParam(value = "email", defaultValue = "") String email) {
        if (token.isEmpty() && !email.isEmpty()) {
            EmptyResultDto res = accountService.sendValidation(email);
            model.addAttribute("apiResponse", res.getMsg());
        }
        else if (!token.isEmpty() && email.isEmpty()) {
            ResultDto<Boolean> res = accountService.validateAccount(token);
            model.addAttribute("apiResponse", res.getMsg());
        }
        return loginGet(model, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    public ModelAndView logout(Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        loginService.logout(SecurityUtils.getAccountId());
        for (Cookie cookie : request.getCookies())
            if (cookie.getName().equals(LoginServiceImpl.SESSION_NAME)) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        return new ModelAndView("redirect:/schedule");
    }

}
