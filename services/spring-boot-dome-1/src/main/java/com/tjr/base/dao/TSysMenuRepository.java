package com.tjr.base.dao;


import com.tjr.base.Entity.TSysMenu;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysMenuRepository extends BaseRepository<TSysMenu, Long> , QuerydslPredicateExecutor<TSysMenu> {
}
