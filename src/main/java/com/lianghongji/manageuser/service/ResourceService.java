package com.lianghongji.manageuser.service;


import com.lianghongji.controller.PageCommResult;
import com.lianghongji.manageuser.dto.req.ResourceSearch;
import com.lianghongji.manageuser.dto.resp.ResourceSearchResult;
import com.lianghongji.manageuser.entity.ManageResources;
import com.lianghongji.manageuser.repository.ResourceRepository;
import com.lianghongji.service.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源业务类
 *
 * @author chen.jiale
 * @date 2019/7/23 8:53
 */
@Service
public class ResourceService extends AbstractService<ResourceRepository, ManageResources, Long> {

    @Transactional
    public void batchDelete(List<Long> resourceIds) {
        this.repository.deleteByIdIn(resourceIds);
    }

    @Transactional(readOnly = true)
    public PageCommResult<ResourceSearchResult> search(ResourceSearch resourceSearch) {
        int start = resourceSearch.getPage().getStart();
        int count = resourceSearch.getPage().getCount();
        Pageable page = new PageRequest(start, count, Sort.Direction.DESC, "createTime");
        List<ManageResources> manageResources = this.repository.findByMenuNameStartingWith(resourceSearch.getMenuName(), page);
        List<ResourceSearchResult> searchResults = new ArrayList<>();
        manageResources.forEach(resources -> {
            ResourceSearchResult result = new ResourceSearchResult();
            BeanUtils.copyProperties(resources,result);
            searchResults.add(result);
        });
        Long total = this.repository.countByMenuNameStartingWith(resourceSearch.getMenuName());
        return PageCommResult.successPageResult(searchResults, start, count, total);
    }
}
