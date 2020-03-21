package com.tjr.dao;

import com.tjr.Entity.TRoleMenu;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TRoleMenuRepository extends BaseRepository<TRoleMenu, Long> , QuerydslPredicateExecutor<TRoleMenu> {
}
