package com.tjr.base.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="t_sys_log")
@EqualsAndHashCode(callSuper = false)
public class TSysLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String url;
    private String ip;
    private Integer userId;

    private Integer status;
    private String executeTime;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
