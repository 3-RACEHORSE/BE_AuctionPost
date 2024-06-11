package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommandAuctionPost is a Querydsl query type for CommandAuctionPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommandAuctionPost extends EntityPathBase<CommandAuctionPost> {

    private static final long serialVersionUID = -1810076707L;

    public static final QCommandAuctionPost commandAuctionPost = new QCommandAuctionPost("commandAuctionPost");

    public final com.skyhorsemanpower.BE_AuctionPost.common.QBaseCreateAndEndTimeEntity _super = new com.skyhorsemanpower.BE_AuctionPost.common.QBaseCreateAndEndTimeEntity(this);

    public final StringPath adminUuid = createString("adminUuid");

    public final NumberPath<Long> auctionPostId = createNumber("auctionPostId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> auctionStartTime = createDateTime("auctionStartTime", java.time.LocalDateTime.class);

    public final StringPath auctionUuid = createString("auctionUuid");

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> eventCloseTime = createDateTime("eventCloseTime", java.time.LocalDateTime.class);

    public final StringPath eventPlace = createString("eventPlace");

    public final DateTimePath<java.time.LocalDateTime> eventStartTime = createDateTime("eventStartTime", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigDecimal> incrementUnit = createNumber("incrementUnit", java.math.BigDecimal.class);

    public final StringPath influencerName = createString("influencerName");

    public final StringPath influencerUuid = createString("influencerUuid");

    public final StringPath localName = createString("localName");

    public final NumberPath<Integer> numberOfEventParticipants = createNumber("numberOfEventParticipants", Integer.class);

    public final NumberPath<java.math.BigDecimal> startPrice = createNumber("startPrice", java.math.BigDecimal.class);

    public final EnumPath<com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum> state = createEnum("state", com.skyhorsemanpower.BE_AuctionPost.status.AuctionStateEnum.class);

    public final StringPath title = createString("title");

    public final NumberPath<java.math.BigDecimal> totalDonation = createNumber("totalDonation", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCommandAuctionPost(String variable) {
        super(CommandAuctionPost.class, forVariable(variable));
    }

    public QCommandAuctionPost(Path<? extends CommandAuctionPost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommandAuctionPost(PathMetadata metadata) {
        super(CommandAuctionPost.class, metadata);
    }

}

