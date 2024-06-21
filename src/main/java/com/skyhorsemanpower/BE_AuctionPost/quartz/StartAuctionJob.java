package com.skyhorsemanpower.BE_AuctionPost.quartz;

import com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command.CommandAuctionPost;
import com.skyhorsemanpower.BE_AuctionPost.repository.cqrs.command.CommandAuctionPostRepository;
import com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@RequiredArgsConstructor
public class StartAuctionJob implements Job {

    private final CommandAuctionPostRepository commandAuctionPostRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(">>>>> startAuctionJob");

        String auctionUuid = context.getJobDetail().getJobDataMap().getString("auctionUuid");

        Optional<CommandAuctionPost> commandAuctionPostOpt = commandAuctionPostRepository.findByAuctionUuid(auctionUuid);
        if (commandAuctionPostOpt.isEmpty()) {
            context.getJobDetail().getJobDataMap().put("auctionPost", null);
            throw new JobExecutionException("AuctionPost does not exist: " + auctionUuid);
        }

        context.getJobDetail().getJobDataMap().put("auctionPost", commandAuctionPostOpt.get());
        changeAuctionState(commandAuctionPostOpt.get());
    }

    private void changeAuctionState(CommandAuctionPost commandAuctionPost) {
        commandAuctionPostRepository.save(CommandAuctionPost.builder()
            .auctionPostId(commandAuctionPost.getAuctionPostId())
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
