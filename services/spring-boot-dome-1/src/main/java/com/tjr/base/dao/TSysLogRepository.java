package com.tjr.base.dao;

import com.tjr.base.Entity.TSysLog;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysLogRepository  extends BaseRepository<TSysLog, Long> , QuerydslPredicateExecutor<TSysLog> {
}


