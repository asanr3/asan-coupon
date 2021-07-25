package com.asan.coupon.dao;

import com.asan.coupon.entity.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <h1>UserRoleMapping Dao</h1>
 * @author Asan
 */
public interface UserRoleMappingRepository
        extends JpaRepository<UserRoleMapping, Long> {

    /**
     * <h2>通过 userId 寻找数据记录</h2>
     * */
    UserRoleMapping findByUserId(Long userId);
}
