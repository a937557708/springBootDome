package com.tjr.es.dao;

import com.tjr.es.model.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}