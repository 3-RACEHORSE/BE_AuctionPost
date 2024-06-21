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
        log.info(">>>>> jobListener");
        //job 실행이 실패했고 auctionPost가 존재할때 강제로 state 갱신
        if (jobExecutionException != null && context.getJobDetail().getJobDataMap().get("auctionPost") != null) {
            CommandAuctionPost commandAuctionPost = (CommandAuctionPost) context.getJobDetail().getJobDataMap().get("auctionPost");
            auctionPostStateChange(commandAuctionPost);
        }
    }

    private void auctionPostStateChange(CommandAuctionPost commandAuctionPost) {
        // 현재 시간 이후라면 auctionUuid에 해당하는 경매글의 state가 BEFORE_AUCTION이면 AUCTION_IS_IN_PROGRESS로 업데이트하기
        commandAuctionPostRepository.save(CommandAuctionPost.builder()
            .auctionPostId(commandAuctionPost.getAuctionPostId())
            .auctionUuid(commandAuctionPost.getAuctionUuid())
            .auctionUuid(commandAuctionPost.getAuctionUuid())
            .adminUuid(commandAuctionPost.getAdminUuid())
            .influencerUuid(commandAuctionPost.getInfluencerUuid())
            .influencerName(commandAuctionPost.getInfluencerName())
            .title(commandAuctionPost.getTitle())
            .content(commandAuctionPost.getContent())
            .numberOfEventParticipants(commandAuctionPost.getNumberOfEventParticipants())
            .localName(commandAuctionPost.getLocalName())
            .eventPlace(commandAuctionPost.getEventPlace())
            .eventStartTime(commandAuctionPost.getEventStartTime())
            .eventCloseTime(commandAuctionPost.getEventCloseTime())
            .auctionStartTime(commandAuctionPost.getAuctionStartTime())
            .auctionEndTime(commandAuctionPost.getAuctionEndTime())
            .startPrice(commandAuctionPost.getStartPrice())
            .incrementUnit(commandAuctionPost.getIncrementUnit())
            .totalDonation(commandAuctionPost.getTotalDonation())
            .state(AuctionStateEnum.AUCTION_IS_IN_PROGRESS)
            .build());
    }

}
