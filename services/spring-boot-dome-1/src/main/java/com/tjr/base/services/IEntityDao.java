package com.tjr.base.services;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

public interface IEntityDao<T> {
    <S extends T> S update(S entity);

    <S extends T> List<S> batchUpdate(Iterable<S> entities);


    void insert(T entity);

    void batchInsert(Iterable<T> entities);
}
