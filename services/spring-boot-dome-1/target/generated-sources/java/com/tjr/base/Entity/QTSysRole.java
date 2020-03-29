package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTSysRole is a Querydsl query type for TSysRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTSysRole extends EntityPathBase<TSysRole> {

    private static final long serialVersionUID = 9252892L;

    public static final QTSysRole tSysRole = new QTSysRole("tSysRole");

    public final StringPath code = createString("code");

    public final DateTimePath<java.sql.Timestamp> gmtCreate = createDateTime("gmtCreate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> gmtModified = createDateTime("gmtModified", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath remarks = createString("remarks");

    public QTSysRole(String variable) {
        super(TSysRole.class, forVariable(variable));
    }

    public QTSysRole(Path<? extends TSysRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTSysRole(PathMetadata metadata) {
        super(TSysRole.class, metadata);
    }

}

