package com.tjr.dao;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.tjr.Entity.SysRole;
@Repository
public interface SysRoleRepository extends BaseRepository<SysRole, Long>, QuerydslPredicateExecutor<SysRole> {

}
