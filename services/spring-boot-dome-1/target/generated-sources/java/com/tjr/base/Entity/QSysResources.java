package com.tjr.base.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSysResources is a Querydsl query type for SysResources
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSysResources extends EntityPathBase<SysResources> {

    private static final long serialVersionUID = 1664763301L;

    public static final QSysResources sysResources = new QSysResources("sysResources");

    public final QAbstractDO _super = new QAbstractDO(this);

    public final BooleanPath available = createBoolean("available");

    //inherited
    public final DateTimePath<java.util.Date> createTime = _super.createTime;

    public final BooleanPath external = createBoolean("external");

    public final StringPath icon = createString("icon");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath permission = createString("permission");

    public final NumberPath<Integer> sort = createNumber("sort", Integer.class);

    public final StringPath type = createString("type");

    //inherited
    public final DateTimePath<java.util.Date> updateTime = _super.updateTime;

    public final StringPath url = createString("url");

    public QSysResources(String variable) {
        super(SysResources.class, forVariable(variable));
    }

    public QSysResources(Path<? extends SysResources> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSysResources(PathMetadata metadata) {
        super(SysResources.class, metadata);
    }

}

