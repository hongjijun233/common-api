package com.lianghongji.manageuser.repository;

import com.lianghongji.manageuser.entity.ManageResources;
import com.lianghongji.manageuser.entity.Role;
import com.lianghongji.manageuser.entity.RoleResourceRela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author chen.jiale
 * @date 2019/7/23 14:53
 */
public interface RoleResourceRelaRepository extends JpaRepository<RoleResourceRela,Long> {
    /**
     * 根据roleId查询
     *
     * @param role
     * @return
     */
    List<RoleResourceRela> findByRole(Role role);

    /**
     *
     * 根据roleid和resourcesid查询
     * @param role
     * @param manageResources
     * @return
     */
    RoleResourceRela findByRoleAndManageResources(Role role, ManageResources manageResources);

}
