package com.tjr.base.dao;

import com.tjr.base.Entity.TSysUser;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysUserRepository  extends BaseRepository<TSysUser, Long> , QuerydslPredicateExecutor<TSysUser> {
}
