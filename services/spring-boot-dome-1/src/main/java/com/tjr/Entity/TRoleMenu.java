package com.tjr.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="t_sys_log")
@EqualsAndHashCode(callSuper = false)
public class TRoleMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer userId;
    private Integer menuId;

    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
