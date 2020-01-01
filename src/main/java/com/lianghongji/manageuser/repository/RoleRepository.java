package com.lianghongji.manageuser.repository;

import com.lianghongji.manageuser.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 角色管理Repository
 *
 * @author liang.hongji
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * 根据姓名模糊查找
     * @param name
     * @param pageable
     * @return
     */
    List<Role> findByNameStartingWith(String name, Pageable pageable);

    /**
     * 统计总数
     * @param name
     * @return
     */
    Long countByNameStartingWith(String name);

    /**
     * 批量删除角色
     *
     * @param ids
     */
    void deleteByIdIn(Collection<Long> ids);
}
