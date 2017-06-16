package ua.adeptius.watchers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.adeptius.HttpWorker;
import ua.adeptius.model.HttpReport;
import ua.adeptius.model.Site;
import ua.adeptius.model.User;
import ua.adeptius.model.UserContainer;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class SiteWatcher extends Thread {

    //    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(2, 15, 5L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
//    public static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    public static final ExecutorService EXECUTOR = new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
    private static Logger LOGGER = LoggerFactory.getLogger(SiteWatcher.class.getSimpleName());


    public SiteWatcher() {
        setDaemon(true);
        start();
    }

    @Override
    public void run() {
        LOGGER.info("SiteWatcher was started");
        while (true) {
            try {
                Thread.sleep(5000);
                checkAllSites();
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void checkAllSites() {
        LOGGER.debug("SiteWatcher checking all sites");
        List<Site> sites = UserContainer.getUsers().stream().flatMap(user -> user.getSites().stream()).collect(Collectors.toList());
        sites.forEach(site -> EXECUTOR.submit(() -> checkSite(site)));
    }

    private void checkSite(@Nonnull Site site) {
        HttpReport report = HttpWorker.getResultViaJsoupConnect(site.getUrl(), site.getLookingWord());
        LOGGER.info("Site {} is checked", site.getUrl());
        System.out.println(report);
    }
}
