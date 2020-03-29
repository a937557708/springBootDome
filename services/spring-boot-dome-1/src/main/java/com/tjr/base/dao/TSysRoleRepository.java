package com.tjr.base.dao;

import com.tjr.base.Entity.TSysRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysRoleRepository  extends BaseRepository<TSysRole, Long> , QuerydslPredicateExecutor<TSysRole> {
}
