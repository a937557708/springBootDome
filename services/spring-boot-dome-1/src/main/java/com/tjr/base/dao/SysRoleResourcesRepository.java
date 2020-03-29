package com.tjr.base.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.tjr.base.Entity.SysRoleResources;
@Repository
public interface SysRoleResourcesRepository extends BaseRepository<SysRoleResources, Long>, QuerydslPredicateExecutor<SysRoleResources> {

}
