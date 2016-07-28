package net.csthings;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.csthings.antreminder.websoc.ClassDto;
import net.csthings.antreminder.websoc.impl.OfflineStaticDataRepositoryImpl;
import net.csthings.antreminder.websoc.service.StaticDataRepository;

@Controller
public class WebSocWebScrap {
    
    StaticDataRepository repository = new OfflineStaticDataRepositoryImpl();

    @RequestMapping(value = "/websoc", method = RequestMethod.GET)
    public String websoc(Model model) {
        return "websoc/search";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@ModelAttribute("class") ClassDto clazz,  Model model) {
       model.addAttribute("classList", repository.getAllClasses());
       model.addAttribute("class", new ClassDto());
       return "test";
    }
}
