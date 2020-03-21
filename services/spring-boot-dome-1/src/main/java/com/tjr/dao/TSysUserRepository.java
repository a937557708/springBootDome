package com.tjr.dao;

import com.tjr.Entity.TSysUser;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysUserRepository  extends BaseRepository<TSysUser, Long> , QuerydslPredicateExecutor<TSysUser> {
}
