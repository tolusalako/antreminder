package net.csthings.antreminder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
    @RequestMapping(method = RequestMethod.GET, value = "/error")
    public String accessDenied(Model model) {
        return "redirect:/schedule";
    }
}
