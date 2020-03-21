package com.tjr.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTSysUserRole is a Querydsl query type for TSysUserRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTSysUserRole extends EntityPathBase<TSysUserRole> {

    private static final long serialVersionUID = 864499270L;

    public static final QTSysUserRole tSysUserRole = new QTSysUserRole("tSysUserRole");

    public final DateTimePath<java.sql.Timestamp> gmtCreate = createDateTime("gmtCreate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> gmtModified = createDateTime("gmtModified", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QTSysUserRole(String variable) {
        super(TSysUserRole.class, forVariable(variable));
    }

    public QTSysUserRole(Path<? extends TSysUserRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTSysUserRole(PathMetadata metadata) {
        super(TSysUserRole.class, metadata);
    }

}

