package com.lianghongji.manageuser.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 后台用户实体类
 *
 * @author liang.hongji
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class ManageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String name;
    private String mobile;
    private String email;
    private String remark;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Date createTime;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    private String salt;

    public ManageUser(String userName, String name, String password, Role role, String salt){
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.role = role;
        this.salt = salt;
    }

    public ManageUser(){

    }
}
