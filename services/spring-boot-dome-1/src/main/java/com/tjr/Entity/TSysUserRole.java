package com.tjr.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="t_sys_user_role")
public class TSysUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer userId;
    private Integer roleId;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
