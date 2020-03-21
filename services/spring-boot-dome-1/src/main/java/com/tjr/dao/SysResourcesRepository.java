package com.tjr.dao;

import org.springframework.stereotype.Repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.tjr.Entity.SysResources;
@Repository
public interface SysResourcesRepository  extends BaseRepository<SysResources, Long>,QuerydslPredicateExecutor<SysResources> {

}
