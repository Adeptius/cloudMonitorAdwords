package ua.adeptius.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.adeptius.dao.PendingUserRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.MessageDigest;
import java.util.*;

public class UserContainer {

    private static Logger LOGGER =  LoggerFactory.getLogger(UserContainer.class.getSimpleName());

    @Autowired
    public static PendingUserRepository pendingUserRepository;


    private static List<User> users = new ArrayList<>();
    private static HashMap<String, User> hashes = new HashMap<>();
    public  static List<PendingUser> pendingUsers = new ArrayList<>();

    private static void recalculateHashesForAllUsers(){
        LOGGER.debug("Пересчет хэша для всех пользователей");
        hashes.clear();
        for (User user : users) {
            hashes.put(createMd5(user), user);
        }
    }

    public static User getUserByHash(String hash){
        return hashes.get(hash);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void removeUser(User user) {
        LOGGER.debug("Удаление пользователя {}",user);
        getUsers().remove(user);
        hashes.remove(getHashOfUser(user));
    }

    public static HashMap<String, User> getHashes() {
        return hashes;
    }

    public static void setUsers(@Nonnull List<User> users) {
        UserContainer.users = users;
        hashes.clear();
        for (User user : users) {
            hashes.put(createMd5(user), user);
        }
    }

    public static String putUser(@Nonnull User user){
        LOGGER.debug("Добавление пользователя {}", user);
        users.add(user);
        String md5 = createMd5(user);
        hashes.put(md5, user);
        return md5;
    }

    @Nullable
    public static User getUserByName(String name){
        try {
            return users.stream().filter(user -> user.getLogin().equals(name)).findFirst().get();
        }catch (NoSuchElementException e){
            return null;
        }
    }


    public static String getHashOfUser(User user){
        for (Map.Entry<String, User> entry : hashes.entrySet()) {
            if (entry.getValue().equals(user)){
                return entry.getKey();
            }
        }
        return null;
    }

    public static String createMd5(User user){
        return createMd5(user.getLogin()+user.getPassword());
    }

    public static String createMd5(String st) {
        try{MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(st.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        }catch (Exception e){}
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public static void updatePendingUsers() {
        try{
            pendingUsers = pendingUserRepository.findAll();
        }catch (Exception e){
            LOGGER.error("Error load pending users", e);
        }
    }
}
