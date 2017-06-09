package ua.adeptius.webcontrollers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.adeptius.dao.PendingUserRepository;
import ua.adeptius.dao.UserRepository;
import ua.adeptius.model.PendingUser;
import ua.adeptius.model.User;
import ua.adeptius.model.UserContainer;
import ua.adeptius.sender.EmailSender;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Controller
@RequestMapping("/authorization")
public class AuthorizationWebController {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthorizationWebController.class.getSimpleName());

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/checkAuthorization", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String checkLogin(String login, String password) {
        User user = UserContainer.getUserByName(login);
        if (user == null){
            return "false";
        }
        if (!user.getPassword().equals(password)){
            return "false";
        }else {
            return "token="+UserContainer.getHashOfUser(user);
        }
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String checkLogin(String token) {
        User user = UserContainer.getUserByHash(token);
        if (user == null){
            return "false";
        }else {
            return "true";
        }
    }
}
