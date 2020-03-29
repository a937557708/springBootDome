package com.tjr.base.dao;

import com.tjr.base.Entity.SysUserRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface SysUserRoleRepository   extends BaseRepository<SysUserRole, Long>, QuerydslPredicateExecutor<SysUserRole> {

}
