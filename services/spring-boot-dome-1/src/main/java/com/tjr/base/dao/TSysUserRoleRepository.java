package com.tjr.base.dao;

import com.tjr.base.Entity.TSysUserRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysUserRoleRepository  extends BaseRepository<TSysUserRole, Long> , QuerydslPredicateExecutor<TSysUserRole> {
}
