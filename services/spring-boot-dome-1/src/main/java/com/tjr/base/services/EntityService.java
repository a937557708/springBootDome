package com.tjr.base.services;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EntityService<T> implements IEntityDao<T> {

    private EntityManager em;

    public void EntityService(EntityManager em) {
        this.em = em;
    }

    private static int BATCH_SIZE = 500;

    @Override
    public <S extends T> S update(S entity) {
        S s = em.merge(entity);
        em.clear();
        em.flush();
        return s;
    }

    @Override
    public <S extends T> List<S> batchUpdate(Iterable<S> entities) {
        List<S> result = new ArrayList<S>();
        if (entities == null) {
            return result;
        }
        int num = 1;
        for (S entity : entities) {
            S s = em.merge(entity);
            result.add(s);
            if (num++ == BATCH_SIZE) {
                em.clear();
                em.flush();
            }
        }
        if (num != BATCH_SIZE) {
            em.clear();
            em.flush();
        }
        return result;
    }

    @Override
    public void insert(T entity) {
        em.persist(entity);
        em.clear();
        em.flush();
    }

    @Override
    public void batchInsert(Iterable<T> entities) {
        int num = 1;
        for (T entity : entities) {
            em.persist(entity);
            if (num++ == BATCH_SIZE) {
                em.clear();
                em.flush();
            }
        }
        if (num != BATCH_SIZE) {
            em.clear();
            em.flush();
        }
    }
}
