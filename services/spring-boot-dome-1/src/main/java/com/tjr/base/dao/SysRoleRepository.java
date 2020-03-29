package com.tjr.base.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.tjr.base.Entity.SysRole;
@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, Long>, QuerydslPredicateExecutor<SysRole> {

}
