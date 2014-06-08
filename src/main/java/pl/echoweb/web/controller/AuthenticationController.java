package pl.echoweb.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.echoweb.model.dto.UserDTO;
import pl.echoweb.service.IUserService;
import pl.echoweb.util.TokenGenerator;
import pl.echoweb.web.model.UserModel;
import pl.echoweb.web.model.validator.UserValidator;

import javax.servlet.http.HttpSession;

/**
 * Rafal on 6/8/2014.
 */
@Controller
@RequestMapping("/auth/")
public class AuthenticationController {
    @Autowired
    @Qualifier("UserService")
    IUserService userService;
    UserValidator userValidator = new UserValidator();

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLoginPage(@RequestParam(value = "error", required = false) boolean error,
                               ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Model model, HttpSession session) {
        UserModel um = new UserModel();
        String token = TokenGenerator.RandomAlphaNumericString();
        um.setToken(token);
        session.setAttribute(token, token + '1');
        model.addAttribute("User", um);

        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String handleRegister(@ModelAttribute("User") UserModel model,
                                 BindingResult result, HttpSession session) {
        userValidator.validate(model, result);
        UserDTO user = new UserDTO();
        user.setActive(true);
        user.setName(model.getName());
        user.setLogin(model.getLogin());
        user.setLastName(model.getLastName());
        user.setPassword(model.getPassword());
        user.setEmail(model.getEmail());
        user.setRole("ROLE_USER");
        userService.createUser(user);

        return "rediredt:/mvc/app/";


    }


}
