package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysUser is a Querydsl query type for SysUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysUser extends EntityPathBase<SysUser> {

    private static final long serialVersionUID = 55751755L;

    public static final QSysUser sysUser = new QSysUser("sysUser");

    public final QAbstractDO _super = new QAbstractDO(this);

    public final StringPath avatar = createString("avatar");

    public final DateTimePath<java.util.Date> birthday = createDateTime("birthday", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath lastLoginIp = createString("lastLoginIp");

    public final DateTimePath<java.util.Date> lastLoginTime = createDateTime("lastLoginTime", java.util.Date.class);

    public final NumberPath<Integer> loginCount = createNumber("loginCount", Integer.class);

    public final StringPath mobile = createString("mobile");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath qq = createString("qq");

    public final StringPath regIp = createString("regIp");

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final StringPath username = createString("username");

    public final StringPath userType = createString("userType");

    public QSysUser(String variable) {
        super(SysUser.class, forVariable(variable));
    }

    public QSysUser(Path<? extends SysUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysUser(PathMetadata metadata) {
        super(SysUser.class, metadata);
    }

}

