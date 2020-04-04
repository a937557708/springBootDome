package com.tjr.es.conroller;

import com.tjr.es.dao.GoodsRepository;
import com.tjr.es.model.Goods;
import com.tjr.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsRepository iGoodsRepository;
    @PostMapping("/insert")
    public ResponseUtils createDocumentList(@RequestBody Goods goods, HttpServletRequest request) {
        iGoodsRepository.save(goods);
        return ResponseUtils.ok();
    }

    @GetMapping("/view/all")
    public ResponseUtils findAll( HttpServletRequest request) {
        Iterable<Goods> list = iGoodsRepository.findAll();
        return ResponseUtils.ok(list);
    }



}
