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
package net.csthings.antreminder.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.csthings.antreminder.services.account.AccountService;
import net.csthings.antreminder.services.account.LoginService;
import net.csthings.antreminder.services.reminder.ReminderService;
import net.csthings.antreminder.utils.FormUtils;
import net.csthings.antreminder.utils.Status;
import net.csthings.common.dto.EmptyResultDto;
import net.csthings.common.dto.ResultDto;

@RestController
public class AccountController {
    Logger LOG = LoggerFactory.getLogger(AccountController.class);

    public static final String LOGIN_NAME = "login";
    public static final String REG_NAME = "register";

    @Autowired
    AccountService accountService;

    @Autowired
    LoginService loginService;

    @Autowired
    ReminderService reminderService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpSession session, @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            Exception exception = (Exception) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            model.addObject("apiResponse",
                    exception == null ? "Invalid username or password!" : exception.getMessage());
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ModelAndView register(Model model, @RequestBody MultiValueMap body, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> singleValuedBody = FormUtils.toSingleValuedMap(body);

        // REGISTER
        EmptyResultDto rez = accountService.createAccount(singleValuedBody.get("username").toString(),
                singleValuedBody.get("password").toString());
        if (rez.getStatus().equals(Status.FAILED)) {
            model.addAttribute("apiResponse", rez.getMsg());
            return new ModelAndView(AccountController.REG_NAME, "Model", model);
        }
        else {
            model.addAttribute("apiResponse", "confirmation");
            return new ModelAndView(AccountController.LOGIN_NAME, "Model", model);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ModelAndView registerGet(Model model, HttpServletRequest request) {
        return new ModelAndView("register", "Model", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public ModelAndView validatePost(Model model, @RequestParam(value = "token", defaultValue = "") String token,
            @RequestParam(value = "email", defaultValue = "") String email) {
        if (token.isEmpty() && !email.isEmpty()) {
            EmptyResultDto res = accountService.sendValidation(email);
            if (res.getStatus().equals(Status.FAILED))
                model.addAttribute("apiResponse", res.getMsg());
            else
                model.addAttribute("apiResponse", "confirmation");
        }
        else if (!token.isEmpty() && email.isEmpty()) {
            ResultDto<Boolean> res = accountService.validateAccount(token);
            if (res.getItem())
                model.addAttribute("apiResponse", "activated");
            else
                model.addAttribute("apiResponse", res.getMsg());
        }
        return new ModelAndView(AccountController.LOGIN_NAME, "Model", model);
    }

}
