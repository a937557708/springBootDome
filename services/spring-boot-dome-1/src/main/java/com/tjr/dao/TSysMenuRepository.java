package com.tjr.dao;


import com.tjr.Entity.TSysMenu;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysMenuRepository extends BaseRepository<TSysMenu, Long> , QuerydslPredicateExecutor<TSysMenu> {
}
