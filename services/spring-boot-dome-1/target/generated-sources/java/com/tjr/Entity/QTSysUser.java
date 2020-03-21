package com.tjr.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTSysUser is a Querydsl query type for TSysUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTSysUser extends EntityPathBase<TSysUser> {

    private static final long serialVersionUID = 799316400L;

    public static final QTSysUser tSysUser = new QTSysUser("tSysUser");

    public final StringPath avatar = createString("avatar");

    public final StringPath email = createString("email");

    public final StringPath flag = createString("flag");

    public final DateTimePath<java.sql.Timestamp> gmtCreate = createDateTime("gmtCreate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> gmtModified = createDateTime("gmtModified", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath pwd = createString("pwd");

    public final StringPath qqOppenId = createString("qqOppenId");

    public final StringPath salt = createString("salt");

    public final StringPath sex = createString("sex");

    public final StringPath token = createString("token");

    public final StringPath username = createString("username");

    public QTSysUser(String variable) {
        super(TSysUser.class, forVariable(variable));
    }

    public QTSysUser(Path<? extends TSysUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTSysUser(PathMetadata metadata) {
        super(TSysUser.class, metadata);
    }

}

