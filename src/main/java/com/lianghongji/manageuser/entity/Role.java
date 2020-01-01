package com.lianghongji.manageuser.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 角色实体类
 *
 * @author liang.hongji
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String remark;
    private Date createTime;

    public boolean isAdmin(){
        if (name.equals("admin"))
            return true;
        return false;
    }
}
