package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysRoleResources is a Querydsl query type for SysRoleResources
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysRoleResources extends EntityPathBase<SysRoleResources> {

    private static final long serialVersionUID = -1888400945L;

    public static final QSysRoleResources sysRoleResources = new QSysRoleResources("sysRoleResources");

    public final QAbstractDO _super = new QAbstractDO(this);

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> resourcesId = createNumber("resourcesId", Long.class);

    public final NumberPath<Long> roleId = createNumber("roleId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public QSysRoleResources(String variable) {
        super(SysRoleResources.class, forVariable(variable));
    }

    public QSysRoleResources(Path<? extends SysRoleResources> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysRoleResources(PathMetadata metadata) {
        super(SysRoleResources.class, metadata);
    }

}

