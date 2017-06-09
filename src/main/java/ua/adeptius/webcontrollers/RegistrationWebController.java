package ua.adeptius.webcontrollers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.adeptius.dao.PendingUserRepository;
import ua.adeptius.dao.UserRepository;
import ua.adeptius.model.PendingUser;
import ua.adeptius.model.User;
import ua.adeptius.model.UserContainer;
import ua.adeptius.sender.EmailSender;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Controller
@RequestMapping("/registration")
public class RegistrationWebController {

    private static Logger LOGGER = LoggerFactory.getLogger(RegistrationWebController.class.getSimpleName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    PendingUserRepository pendingUserRepository;

    @RequestMapping(value = "/checkNewLogin", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String checkLogin(String login) {
        return validateLogin(login);
    }


    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String checkEmail(String email) {
        return validateEmail(email);
    }


    @RequestMapping(value = "/checkPassword", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String checkPassword(String password) {
        return validatePassword(password);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String register(String login, String email, String password) {
        LOGGER.info("Register request for {}, email: {}, password length: {}", login, email, password.length());
        boolean loginOk = validateLogin(login).equals("good");
        boolean emailOk = validateEmail(email).equals("good");
        boolean passwordOk = validatePassword(password).equals("good");

        try {
            if (loginOk && emailOk && passwordOk) {
                PendingUser pendingUser = new PendingUser(login, password, email, checkDelay);
                pendingUserRepository.save(pendingUser);
                new EmailSender(email, pendingUser.getKey());
                UserContainer.updatePendingUsers();
                LOGGER.info("Key sended to {}", email);
                return "key sended";
            } else {
                return "Wrong data";
            }
        } catch (Exception e) {
            LOGGER.error("FAILED register request", e);
            return "Internal error";
        }
    }

    @RequestMapping(value = "/key", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String registerConfirm(String key) {
       PendingUser pendingUser = pendingUserRepository.findByKey(key);
       if (pendingUser==null){
           LOGGER.info("Key {} is wrong or expired", key);
           return "WrongKey";
       }else {
           User newUser = new User(pendingUser.getLogin(), pendingUser.getPassword(), pendingUser.getEmail());
           userRepository.save(newUser);
           String md5 = UserContainer.putUser(newUser);
           pendingUserRepository.remove(pendingUser);
           UserContainer.updatePendingUsers();
           LOGGER.info("User {} successfully registered", pendingUser.getLogin());
           return "token="+md5;
       }
    }

    private String validateLogin(String login) {
        if (login.length() < 4) {
            return "error";
        }

        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < login.length(); i++) {
            String s = login.substring(i, i + 1);
            if (!str.contains(s)) {
                return "error";
            }
        }

        User user = UserContainer.getUserByName(login);
        if (user != null) {
            return "busy";
        }

        if (UserContainer.pendingUsers.stream().anyMatch(us -> us.getLogin().equals(login))){
            return "awaiting";
        }

        return "good";
    }

    private String validateEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            return "error";
        }
        if (email.lastIndexOf(".") < email.lastIndexOf("@")) {
            return "error";
        }

        User user = UserContainer.getUserByEmail(email);
        if (user != null) {
            return "busy";
        }
        if (UserContainer.pendingUsers.stream().anyMatch(pu -> pu.getEmail().equals(email))){
            return "awaiting";
        }

        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return "good";
        } catch (AddressException ex) {
            return "error";
        }
    }

    private String validatePassword(String password) {
        if (password.length() < 6) {
            return "error";
        }

        String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < password.length(); i++) {
            String s = password.substring(i, i + 1);
            if (!str.contains(s)) {
                return "error";
            }
        }
        return "good";
    }
}
