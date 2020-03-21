package com.tjr.dao;

import com.tjr.Entity.TSysUserRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysUserRoleRepository  extends BaseRepository<TSysUserRole, Long> , QuerydslPredicateExecutor<TSysUserRole> {
}
