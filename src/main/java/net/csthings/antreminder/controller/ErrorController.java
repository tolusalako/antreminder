package net.csthings.antreminder.controller;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

    @ExceptionHandler({ FileNotFoundException.class })
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        return "redirect:/login";
    }
}
