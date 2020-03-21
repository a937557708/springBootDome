package com.tjr.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.tjr.Entity.SysRoleResources;
@Repository
public interface SysRoleResourcesRepository extends BaseRepository<SysRoleResources, Long>, QuerydslPredicateExecutor<SysRoleResources> {

}
