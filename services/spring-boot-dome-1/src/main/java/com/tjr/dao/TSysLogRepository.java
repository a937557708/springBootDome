package com.tjr.dao;

import com.tjr.Entity.TSysLog;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TSysLogRepository  extends BaseRepository<TSysLog, Long> , QuerydslPredicateExecutor<TSysLog> {
}


