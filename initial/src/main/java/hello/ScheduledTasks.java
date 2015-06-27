package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static java.lang.Thread.sleep;

@Component
public class ScheduledTasks {

    static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        int sleepTime = new Random().nextInt(10000) + 5000;
        LOG.info("The time is now " + dateTime);
        LOG.info("Sleeping for " + sleepTime);
        try {
            sleep((long) sleepTime);
            dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LOG.info("Done sleeping + " + dateTime);
        } catch (Exception e) {
        }
    }
}
