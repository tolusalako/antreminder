package net.csthings.antreminder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @RequestMapping(value = "/loaderio-27a92395c44b94af53ef36a24e81b285", method = RequestMethod.GET)
    public String loader(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "loaderio-27a92395c44b94af53ef36a24e81b285";
    }
}
