package com.tjr.dao;

import com.tjr.Entity.SysUserRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface SysUserRoleRepository   extends BaseRepository<SysUserRole, Long>, QuerydslPredicateExecutor<SysUserRole> {

}
