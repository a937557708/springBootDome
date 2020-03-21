package com.tjr.dao;

import com.tjr.Entity.TSysRole;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysRoleRepository  extends BaseRepository<TSysRole, Long> , QuerydslPredicateExecutor<TSysRole> {
}
