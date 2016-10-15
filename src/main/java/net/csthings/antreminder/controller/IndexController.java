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

    @RequestMapping(value = "/loaderio-0593fa28aef604d618aaa261ae25ddab", method = RequestMethod.GET)
    public String loader(Model model, HttpServletRequest request, HttpServletResponse response) {
        return "loaderio-0593fa28aef604d618aaa261ae25ddab";
    }
}
