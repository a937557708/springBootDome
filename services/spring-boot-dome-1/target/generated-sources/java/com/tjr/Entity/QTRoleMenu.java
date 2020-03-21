package com.tjr.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTRoleMenu is a Querydsl query type for TRoleMenu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTRoleMenu extends EntityPathBase<TRoleMenu> {

    private static final long serialVersionUID = 1089299581L;

    public static final QTRoleMenu tRoleMenu = new QTRoleMenu("tRoleMenu");

    public final DateTimePath<java.sql.Timestamp> gmtCreate = createDateTime("gmtCreate", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> gmtModified = createDateTime("gmtModified", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> menuId = createNumber("menuId", Integer.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QTRoleMenu(String variable) {
        super(TRoleMenu.class, forVariable(variable));
    }

    public QTRoleMenu(Path<? extends TRoleMenu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTRoleMenu(PathMetadata metadata) {
        super(TRoleMenu.class, metadata);
    }

}

