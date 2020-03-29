package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractDO is a Querydsl query type for AbstractDO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAbstractDO extends EntityPathBase<AbstractDO> {

    private static final long serialVersionUID = -1677600966L;

    public static final QAbstractDO abstractDO = new QAbstractDO("abstractDO");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QAbstractDO(String variable) {
        super(AbstractDO.class, forVariable(variable));
    }

    public QAbstractDO(Path<? extends AbstractDO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractDO(PathMetadata metadata) {
        super(AbstractDO.class, metadata);
    }

}

