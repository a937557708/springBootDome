package com.tjr.base.dao;

import com.tjr.base.Entity.TRoleMenu;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TRoleMenuRepository extends BaseRepository<TRoleMenu, Long> , QuerydslPredicateExecutor<TRoleMenu> {
}
