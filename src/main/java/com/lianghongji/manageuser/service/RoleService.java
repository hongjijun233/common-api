package com.lianghongji.manageuser.service;

import com.lianghongji.controller.PageCommResult;
import com.lianghongji.manageuser.dto.resp.RoleWithIdDto;
import com.lianghongji.manageuser.entity.Role;
import com.lianghongji.manageuser.repository.RoleRepository;
import com.lianghongji.service.AbstractService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色管理Service
 *
 * @author liang.hongji
 */
@Service
public class RoleService extends AbstractService<RoleRepository, Role, Long> {

    @Transactional(readOnly = true)
    public PageCommResult<RoleWithIdDto> search(String roleName, int start, int count) {
        Pageable page = new PageRequest(start, count, Sort.Direction.DESC, "createTime");
        List<Role> roles = this.repository.findByNameStartingWith(roleName, page);
        List<RoleWithIdDto> roleDtos = new ArrayList<RoleWithIdDto>();
        roles.forEach(role -> roleDtos.add(new RoleWithIdDto(role.getName(), role.getRemark(), role.getId(), role.getCreateTime().toString())));
        Long total = this.repository.countByNameStartingWith(roleName);
        return PageCommResult.successPageResult(roleDtos, start, count, total);
    }

    /**
     * 批量删除角色
     *
     * @param roleIds
     */
    @Transactional
    public void batchDelete(List<Long> roleIds) {
        //TODO 需要删除绑定的资源关系
        this.repository.deleteByIdIn(roleIds);
    }
}
