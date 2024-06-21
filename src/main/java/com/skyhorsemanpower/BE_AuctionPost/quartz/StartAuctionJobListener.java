package com.skyhorsemanpower.BE_AuctionPost.quartz;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
@RequiredArgsConstructor
public class StartAuctionJobListener implements JobListener {

    private final CommandAuctionPostRepository commandAuctionPostRepository;

    @Override
    public String getName() {
        return "StartAuctionJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        // Job 실행 전
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        // 다른 job에 의해 jpb 실행 실패
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobExecutionException) {
        // job 실행 후
    }
}
