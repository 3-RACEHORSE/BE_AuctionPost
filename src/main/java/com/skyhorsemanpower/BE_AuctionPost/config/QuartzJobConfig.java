package com.skyhorsemanpower.BE_AuctionPost.config;

import com.skyhorsemanpower.BE_AuctionPost.quartz.StartAuctionJob;
import com.skyhorsemanpower.BE_AuctionPost.quartz.StartAuctionJobListener;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QuartzJobConfig {

    private final Scheduler scheduler;
    private final CommandAuctionPostRepository commandAuctionPostRepository;

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        return scheduler;
    }

    public void schedulerStartAuctionJob(String auctionUuid, long auctionStartTime)
        throws SchedulerException {

        JobDetail job = JobBuilder
            .newJob(StartAuctionJob.class)
            .withIdentity("StartAuctionJob_" + auctionUuid, "StartAuctionGroup")
            .usingJobData("auctionUuid", auctionUuid)
            .withDescription(
                "Change the auction state when auction start at an auction start time.")
            .build();

        Trigger trigger = TriggerBuilder
            .newTrigger()
            .withIdentity("StartAuctionTrigger_" + auctionUuid, "StartAuctionGroup")
            .withDescription("Trigger when auction start time.")
            .startAt(Date.from(Instant.ofEpochMilli(auctionStartTime)))
            .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withMisfireHandlingInstructionNextWithExistingCount().withRepeatCount(0))
            .build();

        StartAuctionJobListener startAuctionJobListener = new StartAuctionJobListener(commandAuctionPostRepository);
        scheduler.getListenerManager()
            .addJobListener(startAuctionJobListener);
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }
}
