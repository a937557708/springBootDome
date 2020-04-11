package com.tjr.base.dao;

import com.tjr.base.services.IEntityDao;
import org.springframework.stereotype.Repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tjr.base.Entity.SysResources;

@Repository
public interface SysResourcesRepository extends BaseRepository<SysResources, Long>
        , QuerydslPredicateExecutor<SysResources> {

}
