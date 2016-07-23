package net.csthings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebSocWebScrap {
	

	@RequestMapping(value="/websoc", method = RequestMethod.GET)
	public String websoc(Model model){
		return "websoc/search";
	}
}
