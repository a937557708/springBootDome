package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTSysLog is a Querydsl query type for TSysLog
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTSysLog extends EntityPathBase<TSysLog> {

    private static final long serialVersionUID = -138254626L;

    public static final QTSysLog tSysLog = new QTSysLog("tSysLog");

    public final StringPath executeTime = createString("executeTime");

    public final DateTimePath<java.sql.Timestamp> gmtCreate = createDateTime("gmtCreate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> gmtModified = createDateTime("gmtModified", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath url = createString("url");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QTSysLog(String variable) {
        super(TSysLog.class, forVariable(variable));
    }

    public QTSysLog(Path<? extends TSysLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTSysLog(PathMetadata metadata) {
        super(TSysLog.class, metadata);
    }

}

