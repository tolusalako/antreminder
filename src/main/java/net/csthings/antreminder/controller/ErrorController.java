package net.csthings.antreminder.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = { Exception.class })
    public String accessDenied(Model model) {
        return "redirect:/login";
    }
}
