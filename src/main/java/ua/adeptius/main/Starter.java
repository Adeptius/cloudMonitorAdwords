package ua.adeptius.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.adeptius.dao.PendingUserRepository;
import ua.adeptius.dao.SiteRepository;
import ua.adeptius.dao.UserController;
import ua.adeptius.dao.UserRepository;
import ua.adeptius.model.PendingUser;
import ua.adeptius.model.Site;
import ua.adeptius.model.User;
import ua.adeptius.model.UserContainer;
import ua.adeptius.watchers.PendingUsersWatcher;
import ua.adeptius.watchers.SiteWatcher;
import ua.adeptius.webcontrollers.RegistrationWebController;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class Starter {


    private static Logger LOGGER = LoggerFactory.getLogger(Starter.class.getSimpleName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PendingUserRepository pendingUserRepository;

    @Autowired
    SiteRepository siteRepository;

    public static String htmlMailBody = "";

    @PostConstruct
    public void init() {
        LOGGER.info("Starting initializating");

        try {
            LOGGER.info("Reading html message from resources");
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("myMail.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder builder = new StringBuilder(4300);
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
            htmlMailBody = builder.toString();
            LOGGER.info("HTML message readed succesfully: {} lines", builder.length());
        } catch (Exception e) {
            LOGGER.error("Could not read HTML from resources", e);
        }


        LOGGER.info("Loading users");
        try {
            UserContainer.setUsers(userRepository.findAll());
            List<User> userList = UserContainer.getUsers();
            LOGGER.info("Loaded {} users", userList.size());
            userList.forEach(System.out::println);
        } catch (Exception e) {
            LOGGER.error("Could not load users", e);
        }


        UserContainer.pendingUserRepository = pendingUserRepository;
        UserContainer.updatePendingUsers();
        LOGGER.info("Loaded {} pending users..", UserContainer.pendingUsers.size());
        UserContainer.pendingUsers.forEach(System.out::println);

        LOGGER.info("Starting pending users watcher...");
        new PendingUsersWatcher(pendingUserRepository);

        LOGGER.info("Starting site watcher...");
        new SiteWatcher();

    }
}
