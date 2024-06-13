package com.skyhorsemanpower.BE_AuctionPost.domain.cqrs.command;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInfluencer is a Querydsl query type for Influencer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInfluencer extends EntityPathBase<Influencer> {

    private static final long serialVersionUID = 2028712898L;

    public static final QInfluencer influencer = new QInfluencer("influencer");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phoneNum = createString("phoneNum");

    public final StringPath profileImage = createString("profileImage");

    public final StringPath uuid = createString("uuid");

    public QInfluencer(String variable) {
        super(Influencer.class, forVariable(variable));
    }

    public QInfluencer(Path<? extends Influencer> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInfluencer(PathMetadata metadata) {
        super(Influencer.class, metadata);
    }

}

