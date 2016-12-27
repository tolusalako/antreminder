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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.rossillo.spring.web.mvc.CacheControl;
import net.rossillo.spring.web.mvc.CachePolicy;

/**
 * -Created on: Jul 28, 2016
 * @author Toluwanimi Salako
 *-Last edited: Jul 28, 2016
 * @purpose - Controller for websoc
 */

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    Logger LOG = LoggerFactory.getLogger(ScheduleController.class);
    public final static String PAGE_NAME = "schedule";
    @Autowired
    WebSocService webSocService;
    @Autowired
    private WebSocSettings websocSettings;
    RestClientService restService;

    @PostConstruct
    public void init() {
        restService = new RestClientService(websocSettings.getBaseUrl());
    }

    @CacheControl(maxAge = 1800, policy = CachePolicy.NO_CACHE)
    @RequestMapping(method = RequestMethod.GET)
    public String scheduleGet(Model model, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        model.asMap().clear();
        model.addAttribute(Attributes.FRAGMENT, Attributes.Fragments.FORM);
        model.addAttribute(Attributes.PAGE, webSocService.generateInnerFormHtml());
        return PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_XHTML_XML_VALUE)
    public String schedulePost(Model model, @RequestBody MultiValueMap body, HttpSession session,
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
        model.addAttribute(Attributes.REMINDER_ADD_URL, "/reminders/add");
        return PAGE_NAME;
    }
}
