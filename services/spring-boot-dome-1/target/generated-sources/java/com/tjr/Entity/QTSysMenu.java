package com.tjr.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTSysMenu is a Querydsl query type for TSysMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTSysMenu extends EntityPathBase<TSysMenu> {

    private static final long serialVersionUID = 799064900L;

    public static final QTSysMenu tSysMenu = new QTSysMenu("tSysMenu");

    public final DateTimePath<java.sql.Timestamp> gmtCreate = createDateTime("gmtCreate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> gmtModified = createDateTime("gmtModified", java.sql.Timestamp.class);

    public final StringPath icon = createString("icon");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath parentId = createString("parentId");

    public final StringPath remarks = createString("remarks");

    public final StringPath resources = createString("resources");

    public final NumberPath<Integer> sortNo = createNumber("sortNo", Integer.class);

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    public final StringPath url = createString("url");

    public QTSysMenu(String variable) {
        super(TSysMenu.class, forVariable(variable));
    }

    public QTSysMenu(Path<? extends TSysMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTSysMenu(PathMetadata metadata) {
        super(TSysMenu.class, metadata);
    }

}

