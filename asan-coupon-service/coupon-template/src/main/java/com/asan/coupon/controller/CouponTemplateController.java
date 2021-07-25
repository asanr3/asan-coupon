package com.asan.coupon.controller;

import com.alibaba.fastjson.JSON;
import com.asan.coupon.annotation.AsanCouponPermission;
import com.asan.coupon.annotation.IgnorePermission;
import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.exception.CouponException;
import com.asan.coupon.service.IBuildTemplateService;
import com.asan.coupon.service.ITemplateBaseService;
import com.asan.coupon.vo.CouponTemplateSDK;
import com.asan.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <h1>优惠券模板相关的功能控制器</>
 * @author Asan
 * @date 2021/6/14
 */
@Slf4j
@RestController
public class CouponTemplateController {

    /** 构建优惠券模板服务*/
    private final IBuildTemplateService buildTemplateService;

    /** 优惠券模板基础服务*/
    private final ITemplateBaseService templateBaseService;

    public CouponTemplateController(IBuildTemplateService buildTemplateService, ITemplateBaseService templateBaseService) {
        this.buildTemplateService = buildTemplateService;
        this.templateBaseService = templateBaseService;
    }

    /**
     * <h2>构建优惠券模板</h2>
     * 通过应用访问：127.0.0.1:7001/coupon-template/template/build
     * 通过网关访问：127.0.0.1:9000/asan/coupon-template/template/build
     * */
    @PostMapping("/template/build")
    @AsanCouponPermission(description = "buildTemplate", readOnly = false)
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request) throws CouponException {
        log.info("Build Template: {}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * <h2>构造优惠券模板详情</>
     * 127.0.0.1:7001/coupon-template/template/info?id=1
     * 通过网关访问：127.0.0.1:9000/asan/coupon-template/template/info?id=1
     * */
    @GetMapping("/template/info")
    @AsanCouponPermission(description = "buildTemplateInfo")
    public CouponTemplate buildTemplateInfo(@RequestParam("id") Integer id) throws CouponException {
        log.info("Build Template Info For: {}", id);
        return templateBaseService.buildTemplateInfo(id);
    }

    /**
     * <h2>查找所有可用的优惠券模板</>
     * 127.0.0.1:7001/coupon-template/template/sdk/all
     * 通过网关访问：127.0.0.1:9000/asan/coupon-template/template/sdk/all
     * */
    @GetMapping("/template/sdk/all")
    @IgnorePermission
    public List<CouponTemplateSDK> findAllUsableTemplate(){
        log.info("Find All Usable Template.");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * <h2>获取模板 ids 到CouponTemplateSDK的映射</>
     * 127.0.0.1:7001/coupon-template/template/sdk/infos
     * 通过网关访问：127.0.0.1:9000/asan/coupon-template/template/sdk/infos?ids=1,2
     * */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(@RequestParam("ids")Collection<Integer> ids){
        log.info("FindIds2TemplateSDK:{}", JSON.toJSONString(ids));
        return templateBaseService.findIds2TemplateSDK(ids);
    }
}
