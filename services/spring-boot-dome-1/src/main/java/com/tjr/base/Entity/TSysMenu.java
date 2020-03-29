package com.tjr.base.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="t_sys_menu")
@EqualsAndHashCode(callSuper = false)
public class TSysMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parentId;
    private String url;
    private String resources;
    private String title;

    private Integer level;
    private Integer sortNo;
    private String icon;

    private String type;
    private String remarks;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
