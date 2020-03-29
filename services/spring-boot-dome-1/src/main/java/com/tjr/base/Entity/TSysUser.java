package com.tjr.base.Entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="t_sys_user")
public class TSysUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String nickName;
    private String sex;
    private String phone;
    private String email;
    private String avatar;
    private String flag;
    private String salt;
    private String token;
    private String qqOppenId;
    private String pwd;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;

}
