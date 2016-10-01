package net.csthings.antreminder.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.csthings.antreminder.config.WebSocSettings;
import net.csthings.antreminder.services.rest.RestClientService;
import net.csthings.antreminder.utils.Attributes;
import net.csthings.antreminder.utils.FormUtils;
import net.csthings.antreminder.websoc.service.WebSocService;

/**
 * -Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 *-Last edited: Jul 28, 2016
 * @purpose - Controller for websoc
 */

@Controller
@RequestMapping("${schedule.url}")
public class ScheduleController {
    Logger LOG = LoggerFactory.getLogger(ScheduleController.class);
    public final static String PAGE = "schedule";
    @Autowired
    WebSocService webSocService;
    @Autowired
    private WebSocSettings websocSettings;
    RestClientService restService;

    @Value("${reminders.add}")
    private String REMINDER_ADD;

    @PostConstruct
    public void init() {
        restService = new RestClientService(websocSettings.getBaseUrl());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String reminderGet(Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        model.asMap().clear();
        model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.FORM);
        model.addAttribute(Attributes.PAGE, webSocService.generateInnerFormHtml());
        return PAGE;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public String reminderPost(Model model, @RequestBody MultiValueMap body, HttpSession session,
            HttpServletRequest request, HttpServletResponse httpResponse) {
        model.asMap().clear();
        try {
            String response = restService.getHtml(FormUtils.toMultiValuedMap(body), "");
            model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.SEARCH);
            model.addAttribute(Attributes.PAGE, response);
        }
        catch (Exception e) {
            LOG.error("WebSoc POST error.", e);
            model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.ERROR);
        }

        if (!model.containsAttribute(Attributes.FRAGMENT))
            model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.FORM);
        model.addAttribute(Attributes.REMINDER_ADD_URL, REMINDER_ADD);
        return PAGE;
    }
}
