package com.skyhorsemanpower.BE_AuctionPost.quartz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@RequiredArgsConstructor
public class EndAuctionPost implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
