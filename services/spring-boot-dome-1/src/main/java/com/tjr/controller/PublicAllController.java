package com.tjr.controller;

import com.querydsl.core.types.Predicate;
import com.tjr.Entity.QTSysMenu;
import com.tjr.Entity.TSysMenu;
import com.tjr.dao.TSysMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicAllController {
    @Autowired
    private TSysMenuRepository iTSysMenuReository;

    @GetMapping("/jpa")
    public void testJpa(){
        QTSysMenu qc=QTSysMenu.tSysMenu;
        Predicate pre=qc.url.like("%system%");
        List<TSysMenu> list=  (List<TSysMenu>) iTSysMenuReository.findAll(pre);
        System.out.println(list.size());

    }
}
