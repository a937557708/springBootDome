package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysUserRole is a Querydsl query type for SysUserRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysUserRole extends EntityPathBase<SysUserRole> {

    private static final long serialVersionUID = -148862111L;

    public static final QSysUserRole sysUserRole = new QSysUserRole("sysUserRole");

    public final QAbstractDO _super = new QAbstractDO(this);

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSysUserRole(String variable) {
        super(SysUserRole.class, forVariable(variable));
    }

    public QSysUserRole(Path<? extends SysUserRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysUserRole(PathMetadata metadata) {
        super(SysUserRole.class, metadata);
    }

}

