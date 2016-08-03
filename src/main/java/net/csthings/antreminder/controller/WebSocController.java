package net.csthings.antreminder.controller;

import java.io.File;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.csthings.antreminder.websoc.ClassDto;
import net.csthings.antreminder.websoc.impl.OfflineStaticDataRepositoryImpl;
import net.csthings.antreminder.websoc.service.RestClientService;
import net.csthings.antreminder.websoc.service.StaticDataRepository;
import net.csthings.antreminder.websoc.service.WebSocService;
import net.csthings.antreminder.websoc.utils.WebSocParser;

/**
 * -Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 *-Last edited: Jul 28, 2016
 * @purpose - Controller for websoc
 */

@Controller
public class WebSocController {

    @Autowired
    RestClientService restService;

    @Autowired
    WebSocService webSocService;

    StaticDataRepository repository = new OfflineStaticDataRepositoryImpl();

    @RequestMapping(value = "${websoc.formUrl}", method = RequestMethod.GET)
    public String websocGet(Model model) {
        return "form";
    }

    @RequestMapping(value = "${websoc.searchUrl}", method = RequestMethod.POST,
        headers = { "content-type=application/x-www-form-urlencoded" }, produces = MediaType.ALL_VALUE)
    public String websocPost(HttpServletRequest servletRequest, @RequestBody MultiValueMap body) {
        String response = "form";
        try {
            response = restService.getHtml(WebSocParser.toMultivalueMap(body), "");
            File file = new File("temp.html");
            System.out.println(file.getAbsolutePath());
            FileUtils.write(file, response, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response = "error";
        }
        return response;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@ModelAttribute("class") ClassDto clazz, Model model) {
        model.addAttribute("classList", repository.getAllClasses());
        model.addAttribute("class", new ClassDto());
        return "test";
    }
}
