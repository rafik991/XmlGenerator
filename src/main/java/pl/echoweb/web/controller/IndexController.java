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
import pl.echoweb.model.dto.UserDTO;
import pl.echoweb.service.IUserService;
import pl.echoweb.util.SHA1;
import pl.echoweb.util.TokenGenerator;
import pl.echoweb.web.model.UserModel;
import pl.echoweb.web.model.validator.UserValidator;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

/**
 * Kontroler indeksu.
 *
 * @author rafal.machnik
 */
@Controller
@RequestMapping("/app/")
public class IndexController {




    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showIndex() {
        return "index";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "help", method = RequestMethod.GET)
    public String showHelp() {
        return "help";
    }

  /*  @RequestMapping(value = "login", method = RequestMethod.GET)
    public String logIn(Model model) {
        model.addAttribute("user", new UserModel());
        return "login";

    }*/

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {

        return "login";

    }


}
