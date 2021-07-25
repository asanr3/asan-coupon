package com.asan.coupon.permission;

import com.asan.coupon.vo.CheckPermissionRequest;
import com.asan.coupon.vo.CommonResponse;
import com.asan.coupon.vo.CreatePathRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * <h1>路径创建与权限校验功能 Feign 接口实现</h1>
 * @author Asan
 */
@FeignClient(value = "eureka-client-coupon-permission")
public interface PermissionClient {

    /**
     * 接口路径创建（注册）服务
     * @param request {@link CreatePathRequest}
     * @return Path 数据记录的主键
     */
    @RequestMapping(value = "/coupon-permission/create/path",
            method = RequestMethod.POST)
    CommonResponse<List<Integer>> createPath(
            @RequestBody CreatePathRequest request
    );

    /**
     * 接口权限校验服务
     * @param request {@link CheckPermissionRequest}
     * @return true/false
     */
    @RequestMapping(value = "/coupon-permission/check/permission",
    method = RequestMethod.POST)
    Boolean checkPermission(@RequestBody CheckPermissionRequest request);
}
