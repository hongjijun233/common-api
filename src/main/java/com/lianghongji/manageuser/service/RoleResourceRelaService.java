package com.lianghongji.manageuser.service;


import com.lianghongji.controller.PageCommResult;
import com.lianghongji.manageuser.dto.ResourceDto;
import com.lianghongji.manageuser.entity.ManageResources;
import com.lianghongji.manageuser.entity.Role;
import com.lianghongji.manageuser.entity.RoleResourceRela;
import com.lianghongji.manageuser.repository.RoleResourceRelaRepository;
import com.lianghongji.service.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色资源Service
 *
 * @author chen.jiale
 * @date 2019/7/23 14:57
 */
@Service
public class RoleResourceRelaService extends AbstractService<RoleResourceRelaRepository, RoleResourceRela, Long> {

    /**
     * 根据roleId查询所有资源
     *
     * @param roleId
     * @return
     */
    @Transactional(readOnly = true)
    public PageCommResult<ResourceDto> search(long roleId) {
        Role role = new Role();
        role.setId(roleId);
        List<RoleResourceRela> roleResourceRelas = this.repository.findByRole(role);
        List<ResourceDto> resourceDtos = new ArrayList<>();
        roleResourceRelas.forEach(roleResourceRela -> {
            ResourceDto resourceDto = new ResourceDto();
            BeanUtils.copyProperties(roleResourceRela.getManageResources(), resourceDto);
            resourceDtos.add(resourceDto);
        });
        return PageCommResult.successPageResult(resourceDtos, 0, 0, resourceDtos.size());
    }

    /**
     * 给角色设置资源
     *
     * @param roleId
     * @param resourceIds
     */
    @Transactional
    public void batchInsert(long roleId, List<Long> resourceIds) {
        List<RoleResourceRela> resourceRelas;
        Role role = new Role();
        role.setId(roleId);
        resourceRelas = this.repository.findByRole(role);
        for (RoleResourceRela r: resourceRelas){
            delete(r.getId());
        }
        resourceRelas = new ArrayList<>();
        if (resourceIds == null)
            return;
        for (Long resourceId : resourceIds) {
            ManageResources resources = new ManageResources();
            resources.setId(resourceId);
            RoleResourceRela resourceRela = new RoleResourceRela();
            resourceRela.setRole(role);
            resourceRela.setManageResources(resources);
            resourceRelas.add(resourceRela);
        }
        this.repository.saveAll(resourceRelas);
    }
}
