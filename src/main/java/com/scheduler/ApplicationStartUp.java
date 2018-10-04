package com.scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * class responsible for deleting from routes list all the record where arrival
 * date is less than yesterday.
 */
public class ApplicationStartUp implements ServletContextListener {

    /**
     *
     * */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();

            Scheduler scheduler = schedulerFactory.getScheduler();

            scheduler.start();

            long currTime = System.currentTimeMillis();

            JobDetail job = newJob(ClearRoutesJob.class)
                    .withIdentity("myJob", "group1")
                    .build();

            Trigger trigger = newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(30)
                            .repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
