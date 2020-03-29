package com.tjr.base.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="t_sys_role")
@EqualsAndHashCode(callSuper = false)
public class TSysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String remarks;
    private String code;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
