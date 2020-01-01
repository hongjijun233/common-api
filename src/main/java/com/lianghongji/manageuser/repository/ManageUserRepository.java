package com.lianghongji.manageuser.repository;

import com.lianghongji.manageuser.entity.ManageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户管理Repository
 *
 * @author liang.hongji
 */
@Repository
public interface ManageUserRepository extends JpaRepository<ManageUser, Long> {

    ManageUser findByUserNameAndPassword(String userName, String password);
    ManageUser findByUserName(String userName);

}
