package com.tjr.base.dao;

import com.tjr.base.Entity.SysResources;
import com.tjr.base.services.IEntityDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, S extends Serializable> extends JpaRepository<T, S>, QuerydslPredicateExecutor<T> {


    int count(Specification<T> specification);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

    List<T> findAll(Specification<T> specification);


}
