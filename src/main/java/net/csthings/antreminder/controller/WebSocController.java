package net.csthings.antreminder.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.csthings.antreminder.config.WebSocSettings;
import net.csthings.antreminder.utils.Attributes;
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
    Logger LOG = LoggerFactory.getLogger(WebSocController.class);
    public final static String PAGE = "schedule";
    @Autowired
    WebSocService webSocService;
    @Autowired
    private WebSocSettings websocSettings;
    RestClientService restService;
    StaticDataRepository repository = new OfflineStaticDataRepositoryImpl();

    @PostConstruct
    public void init() {
        restService = new RestClientService(websocSettings.getBaseUrl());
    }

    @RequestMapping(value = "${websoc.formUrl}", method = RequestMethod.GET)
    public String websocGet(Model model) {
        model.asMap().clear();
        model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.FORM);
        model.addAttribute(Attributes.PAGE, webSocService.generateInnerFormHtml());
        return PAGE;
    }

    @RequestMapping(value = "${websoc.formUrl}", method = RequestMethod.POST,
        // headers = { "content-type=application/x-www-form-urlencoded" },
        produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public String websocPost(HttpServletRequest servletRequest, @RequestBody MultiValueMap body, Model model) {
        model.asMap().clear();
        try {
            String response = restService.getHtml(WebSocParser.toMultivalueMap(body), "");
            model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.SEARCH);
            model.addAttribute(Attributes.PAGE, response);
        }
        catch (Exception e) {
            LOG.error("WebSoc POST error.", e);
            model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.ERROR);
        }

        if (!model.containsAttribute(Attributes.FRAGMENT))
            model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.FORM);
        return PAGE;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@ModelAttribute("class") ClassDto clazz, Model model) {
        model.addAttribute("classList", repository.getAllClasses());
        model.addAttribute("class", new ClassDto());
        return "test";
    }
}
