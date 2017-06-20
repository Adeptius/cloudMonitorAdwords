package ua.adeptius.webcontrollers;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.adeptius.dao.UserRepository;
import ua.adeptius.json.Message;
import ua.adeptius.model.User;
import ua.adeptius.model.UserContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserWebController {

    private static Logger LOGGER = LoggerFactory.getLogger(UserWebController.class.getSimpleName());

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/getUser", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) {
        User user = UserContainer.getUserByHash(request.getHeader("Authorization"));
        if (user == null){
            return new Message(Message.Status.Error, "Wrong token").toString();
        }

        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Message(Message.Status.Error, "Internal error").toString();
        }
    }




}
