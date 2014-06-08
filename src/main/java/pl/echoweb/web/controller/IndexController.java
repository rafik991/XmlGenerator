package pl.echoweb.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
