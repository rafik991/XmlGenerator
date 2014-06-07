package pl.asseco.echoweb.web.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.asseco.echoweb.model.dto.UserDTO;
import pl.asseco.echoweb.service.IUserService;
import pl.asseco.echoweb.util.SHA1;
import pl.asseco.echoweb.util.TokenGenerator;
import pl.asseco.echoweb.web.model.UserModel;
import pl.asseco.echoweb.web.model.validator.UserValidator;

/**
 * Kontroler indeksu.
 * 
 * @author rafal.machnik
 * 
 */
@Controller
public class IndexController {

	@Autowired
	@Qualifier("UserService")
	IUserService userService;

	UserValidator userValidator = new UserValidator();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showIndex() {
		return "index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public String showHelp() {
		return "help";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logIn(Model model) {
		model.addAttribute("user", new UserModel());
		return "login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Model model, HttpSession session) {
		UserModel um = new UserModel();
		String token = TokenGenerator.RandomAlphaNumericString();
		um.setToken(token);
		session.setAttribute(token, token + '1');
		model.addAttribute("User", um);

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String handleRegister(@ModelAttribute("User") UserModel model,
			BindingResult result, HttpSession session) {
		userValidator.validate(model, result);
		String token = (String) session.getAttribute(model.getToken());
		session.setAttribute(model.getToken(), model.getToken() + '1');
		if (result.hasErrors() && token.charAt(token.length() - 1) == '1')

			return "register";
		else {
			UserDTO user = new UserDTO();
			user.setActive(true);
			user.setName(model.getName());
			user.setLogin(model.getLogin());
			user.setLastName(model.getLastName());
			try {
				user.setPassword(SHA1.sha1(model.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Threre is no such algorithm exception!");
				e.printStackTrace();
			}
			user.setRole("ROLE_USER");
			userService.createUser(user);

			return "rediredt:/";
		}

	}

}
