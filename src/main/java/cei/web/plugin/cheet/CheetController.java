package cei.web.plugin.cheet;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cheet")
public class CheetController {
	private static final String MENU_SESSION_NAME = "cheet.menu";

	@RequestMapping("/index")
	public String main(Model model, HttpSession session) {
		model.addAttribute("cheet.menu", session.getAttribute(MENU_SESSION_NAME));
		return "__cheet__/index";
	}

	@RequestMapping("/home")
	public String home(Model model) {
		return "__cheet__/home";
	}
}
