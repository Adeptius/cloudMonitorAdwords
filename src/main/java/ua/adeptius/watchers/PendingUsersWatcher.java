package ua.adeptius.watchers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.adeptius.dao.PendingUserRepository;
import ua.adeptius.main.Starter;
import ua.adeptius.model.PendingUser;
import ua.adeptius.model.UserContainer;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PendingUsersWatcher extends Thread {

    private static Logger LOGGER = LoggerFactory.getLogger(PendingUsersWatcher.class.getSimpleName());
    private PendingUserRepository pendingUserRepository;

    public PendingUsersWatcher(PendingUserRepository pendingUserRepository) {
        this.pendingUserRepository = pendingUserRepository;
        setDaemon(true);
        start();
    }


    @Override
    public void run() {
        while (!interrupted()){
            try {
                Thread.sleep(60000);
                clean();
            }catch (Exception e){
                LOGGER.error("Error in watcher", e);
            }
        }
    }

    private void clean(){
        List<PendingUser> pendingUsers = pendingUserRepository.findAll();
        Iterator<PendingUser> it = pendingUsers.iterator();
        while (it.hasNext()){
            PendingUser pendingUser = it.next();
            try{
                long timeCreated = pendingUser.getDate().getTime();
                long timeNow = new Date().getTime();
                long pastTime = timeNow - timeCreated;
                int pastMinutes = (int)(pastTime / 1000 / 60);
                if (pastMinutes>30){
                    LOGGER.info("Pending user {} was removed. Past {} minutes", pendingUser.getLogin(), pastMinutes);
                    UserContainer.pendingUsers.remove(pendingUser);
                    pendingUserRepository.remove(pendingUser);
                    it.remove();
                }
            }catch (Exception e){
                LOGGER.error("Pending user because error",e);
                UserContainer.pendingUsers.remove(pendingUser);
                pendingUserRepository.remove(pendingUser);
                it.remove();
            }
        }
    }
}
