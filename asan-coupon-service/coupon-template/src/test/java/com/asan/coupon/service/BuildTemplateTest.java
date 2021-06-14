package com.asan.coupon.service;

import com.alibaba.fastjson.JSON;
import com.asan.coupon.constant.CouponCategory;
import com.asan.coupon.constant.DistributeTarget;
import com.asan.coupon.constant.PeriodType;
import com.asan.coupon.constant.ProductLine;
import com.asan.coupon.service.IBuildTemplateService;
import com.asan.coupon.vo.TemplateRequest;
import com.asan.coupon.vo.TemplateRule;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * <h1>构造优惠券模板服务测试</h1>
 * @author Asan
 * @date 2021/6/14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuildTemplateTest {
    @Autowired
    private IBuildTemplateService buildTemplateService;

    @Test
    public void testBuildTemplate() throws Exception {

        System.out.println(JSON.toJSONString(
                buildTemplateService.buildTemplate(fakeTemplateRequest())
        ));
        // 在测试时给定一个休眠时间让异步服务生成优惠券，正式环境下由于服务一直开着，所以不需要
        Thread.sleep(5000);
    }

    /**
     * <h2>fake TemplateRequest</h2>
     * */
    private TemplateRequest fakeTemplateRequest() {

        TemplateRequest request = new TemplateRequest();
        // 基础信息
        // 使用时间戳作为优惠券模板后缀，防止测试时生成的优惠券模板名称重复
        request.setName("优惠券模板-" + new Date().getTime());
        request.setLogo("http://www.baidu.com");
        request.setDesc("这是一张优惠券模板");
        request.setCategory(CouponCategory.MANJIAN.getCode());
        request.setProductLine(ProductLine.DAMAO.getCode());
        request.setCount(10000);
        request.setUserId(10001L);  // long 类型 fake user id
        request.setTarget(DistributeTarget.SINGLE.getCode());

        // 规则信息
        TemplateRule rule = new TemplateRule();
        rule.setExpiration(new TemplateRule.Expiration(
                PeriodType.SHIFT.getCode(),
                1, DateUtils.addDays(new Date(), 60).getTime()
        ));
        rule.setDiscount(new TemplateRule.Discount(5, 1));
        rule.setLimitation(1);
        rule.setUsage(new TemplateRule.Usage(
                "海南省", "文昌市",
                JSON.toJSONString(Arrays.asList("文娱", "家居"))
        ));
        rule.setWeight(JSON.toJSONString(Collections.EMPTY_LIST));

        request.setRule(rule);

        return request;
    }
}
