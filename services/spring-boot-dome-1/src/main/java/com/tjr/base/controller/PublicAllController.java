package com.tjr.base.controller;

import com.querydsl.core.types.Predicate;
import com.tjr.base.Entity.QTSysMenu;
import com.tjr.base.Entity.TSysMenu;
import com.tjr.base.dao.TSysMenuRepository;
import com.tjr.base.services.AsyncService;
import com.tjr.common.SystemConfig;
import com.tjr.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PublicAllController {
    @Autowired
    private TSysMenuRepository iTSysMenuReository;

    @Autowired
    private AsyncService IAsyncService;
    private static final Logger logger=  LoggerFactory.getLogger(PublicAllController.class);
    @GetMapping("/jpa")
    public ResponseUtils testJpa() {
        QTSysMenu qc = QTSysMenu.tSysMenu;
        Predicate pre = qc.url.like("%system%");

        List<TSysMenu> list = (List<TSysMenu>) iTSysMenuReository.findAll(pre);
        System.out.println(list.size());
        return ResponseUtils.ok(list);
    }


    @GetMapping("/jpa1")
    public ResponseUtils testJpa1() {
        QTSysMenu qc = QTSysMenu.tSysMenu;
        Predicate pre = qc.url.like("%system%");

        List<TSysMenu> list=new ArrayList<>();
        try{
            list = SystemConfig.queryFactory.selectFrom(qc).where(pre).fetch();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseUtils.ok(list);
    }


    @GetMapping("/syncTset")
    public ResponseUtils syncTset() {
        IAsyncService.test();
        logger.info("syncTset");
        return ResponseUtils.ok("");
    }
}
