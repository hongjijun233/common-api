package com.lianghongji.manageuser.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 资源和角色的对应关系，资源角色多对多
 *
 * @author liang.hongji
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class RoleResourceRela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "role_id")
    Role role;

    @OneToOne
    @JoinColumn(name = "resource_id")
    ManageResources manageResources;

    private Date createTime;
}
