package com.asan.coupon.controller;

import com.asan.coupon.annotation.IgnoreResponseAdvice;
import com.asan.coupon.service.PathService;
import com.asan.coupon.service.PermissionService;
import com.asan.coupon.vo.CheckPermissionRequest;
import com.asan.coupon.vo.CreatePathRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>路径创建与权限校验对外服务接口实现</h1>
 * @author Asan
 */
@Slf4j
@RestController
public class PermissionController {

    private final PathService pathService;
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PathService pathService,
                                PermissionService permissionService) {
        this.pathService = pathService;
        this.permissionService = permissionService;
    }

    /**
     * <h2>路径创建接口</h2>
     * */
    @PostMapping("/create/path")
    public List<Integer> createPath(@RequestBody CreatePathRequest request) {

        log.info("createPath: {}", request.getPathInfos().size());
        return pathService.createPath(request);
    }

    /**
     * <h2>权限校验接口</h2>
     * */
    @IgnoreResponseAdvice
    @PostMapping("/check/permission")
    public Boolean checkPermission(@RequestBody CheckPermissionRequest request) {

        log.info("checkPermission for args: {}, {}, {}",
                request.getUserId(), request.getUri(), request.getHttpMethod());
        return permissionService.checkPermission(
                request.getUserId(), request.getUri(), request.getHttpMethod()
        );
    }
}
