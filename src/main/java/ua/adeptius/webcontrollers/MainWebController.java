package ua.adeptius.webcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class MainWebController {

    @RequestMapping(value = "/")
    public String register() {
        return "main";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "authorization";
    }


}
