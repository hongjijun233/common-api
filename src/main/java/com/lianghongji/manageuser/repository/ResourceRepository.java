package com.lianghongji.manageuser.repository;

import com.lianghongji.manageuser.entity.ManageResources;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author chen.jiale
 * @date 2019/7/23 8:55
 */
public interface ResourceRepository extends JpaRepository<ManageResources,Long> {
    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteByIdIn(Collection<Long> ids);

    /**
     * 分页查询
     *
     * @param menuName
     * @param page
     * @return
     */
    List<ManageResources> findByMenuNameStartingWith(String menuName,Pageable page);

    /**
     * 总资源数
     *
     * @param menuName
     * @return
     */
    Long countByMenuNameStartingWith(String menuName);
}
