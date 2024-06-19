package com.skyhorsemanpower.BE_AuctionPost.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseCreateAndEndTimeEntity is a Querydsl query type for BaseCreateAndEndTimeEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseCreateAndEndTimeEntity extends EntityPathBase<BaseCreateAndEndTimeEntity> {

    private static final long serialVersionUID = 1201468631L;

    public static final QBaseCreateAndEndTimeEntity baseCreateAndEndTimeEntity = new QBaseCreateAndEndTimeEntity("baseCreateAndEndTimeEntity");

    public final NumberPath<Long> createdAt = createNumber("createdAt", Long.class);

    public final NumberPath<Long> updatedAt = createNumber("updatedAt", Long.class);

    public QBaseCreateAndEndTimeEntity(String variable) {
        super(BaseCreateAndEndTimeEntity.class, forVariable(variable));
    }

    public QBaseCreateAndEndTimeEntity(Path<? extends BaseCreateAndEndTimeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseCreateAndEndTimeEntity(PathMetadata metadata) {
        super(BaseCreateAndEndTimeEntity.class, metadata);
    }

}

