package com.tjr.utils;

import lombok.Data;

public enum CacheConst {
    PROJECT("springBootDome");
    private String name;
    private CacheConst(String name) {
        this.name = name();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
