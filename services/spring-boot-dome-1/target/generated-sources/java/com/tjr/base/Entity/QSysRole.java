package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRole is a Querydsl query type for SysRole
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRole extends EntityPathBase<SysRole> {

    private static final long serialVersionUID = 55658742L;

    public static final QSysRole sysRole = new QSysRole("sysRole");

    public final QAbstractDO _super = new QAbstractDO(this);

    public final BooleanPath available = createBoolean("available");

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public QSysRole(String variable) {
        super(SysRole.class, forVariable(variable));
    }

    public QSysRole(Path<? extends SysRole> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRole(PathMetadata metadata) {
        super(SysRole.class, metadata);
    }

}

