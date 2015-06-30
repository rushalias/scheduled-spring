package com.opentable.coresvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@Component
public class ScheduledTasks implements ExitCodeGenerator {

    static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    static final AtomicInteger invocationCounter = new AtomicInteger(0);

    @Scheduled(fixedDelay = 5000)
    public void reportCurrentTime() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        int sleepTime = new Random().nextInt(5000) + 1000;

        LOG.info("Starting process {}: now = {}, duration = {}", invocationCounter, dateTime, sleepTime);
        LOG.debug("Sleeping for " + sleepTime);
        try {
            sleep((long) sleepTime);
            dateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LOG.debug("Done sleeping + " + dateTime);
        } catch (InterruptedException ignored) {
        }

        int ic = invocationCounter.incrementAndGet();
        if (ic > 20) {
            LOG.error("Exiting....");
            System.exit(-1);
            Application.cac.close();
            //SpringApplication.exit(Application.cac,this);
        }

        if (ic  > 5 && ic < 8)
            throw new RuntimeException("Testing mesos with out a health check");
    }

    @Override
    public int getExitCode() {
        return -1;
    }
}
