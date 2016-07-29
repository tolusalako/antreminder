package net.csthings.antreminder.websoc.controllers;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.csthings.antreminder.websoc.ClassDto;
import net.csthings.antreminder.websoc.impl.OfflineStaticDataRepositoryImpl;
import net.csthings.antreminder.websoc.service.StaticDataRepository;

/**
 * -Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 *-Last edited: Jul 28, 2016
 * @purpose - Controller for websoc
 */

@Controller
public class WebSocController {

    StaticDataRepository repository = new OfflineStaticDataRepositoryImpl();

    @RequestMapping(value = "${websoc.formUrl}", method = RequestMethod.GET)
    public String websocGet(Model model) {
        return "websoc/form";
    }

    @RequestMapping(value = "${websoc.searchUrl}", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
    public String websocPost(Map<String, Object> model) {
        System.out.println("Ap2 post!!!");
        return "websoc/form";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@ModelAttribute("class") ClassDto clazz, Model model) {
        model.addAttribute("classList", repository.getAllClasses());
        model.addAttribute("class", new ClassDto());
        return "test";
    }
}
