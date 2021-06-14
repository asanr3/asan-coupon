package com.asan.coupon.service.impl;

import com.asan.coupon.constant.Constant;
import com.asan.coupon.dao.CouponTemplateDao;
import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.service.IAsyncService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Asan
 * @date 2020/2/17 16:34
 * 异步服务接口实现
 */
@Slf4j
@Service
public class AsyncServiceImpl implements IAsyncService {

    /** CouponTemplateDao */
    @Autowired
    private CouponTemplateDao templateDao;

    /** 注入Redis模板类*/
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据模板异步的创建优惠券码
     * @param template {@link {@link CouponTemplate } 优惠券模板实体
     */
    @Async("getAsyncExecutor")
    @Override
    public void asyncConstructCouponByTemplate(CouponTemplate template) {

        // 计时器
        Stopwatch watch = Stopwatch.createStarted();

        Set<String> couponCodes = buildCouponCode(template);

        // asan_coupon_template_code_1的形式
        String rediskey = String .format("%s%s", Constant.RedisPrefix.COUPON_TEMPLATE, template.getId().toString());

        // 将优惠券码存到redis
        log.info("Push CouponCode To Redis: {}", redisTemplate.opsForList().rightPushAll(rediskey, couponCodes));

        // 当优惠券模板和优惠券码生成之后，设置它的可用状态
        template.setAvailable(true);
        templateDao.save(template);

        watch.stop();
        log.info("Construct CouponCode By Template Cost: {}ms", watch.elapsed(TimeUnit.MILLISECONDS));

        // TODO 通知优惠券模板已经可以使用
        log.info("CouponTemplate({}) Is Available！", template.getId());
    }

    /**
     * 构造优惠券码
     * 优惠券码对应每一张优惠券，18位
     * 前四位：产品线 + 类型
     * 中间六位：日期随机
     * 后八位： 0-9随机数构成
     * @param template {@link CouponTemplate} 实体类
     * @return Set<String> 与template.count 相同个数的优惠券码
     * */
    private Set<String> buildCouponCode(CouponTemplate template){
        // 定义一个计时器
        Stopwatch watch = Stopwatch.createStarted();

        Set<String> result = new HashSet<>(template.getCount());

        // 前四位
        String prefix4 = template.getProductLine().getCode().toString() + template.getCategory().getCode();
        String date = new SimpleDateFormat("yyMMdd").format(template.getCreateTime());

        /**
         * 循环生成优惠券码，由于后八位是随机生成的可能会重复，所以用set保证不重复
         * 但是这样会出现由于重复而被set抛弃，从而导致生成的优惠券码小于模板数量
         * 所以使用while循环再次添加
         * 之所以要用for(先) + while(后)是因为for的循环条件是整数要比while先求result总数再循环要快
         * */
        for(int i = 0; i != template.getCount(); ++i){
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }
        while (result.size() < template.getCount()) {
            result.add(prefix4 + buildCouponCodeSuffix14(date));
        }

        assert result.size() == template.getCount();
        watch.stop();
        // 打印时长：毫秒
        log.info("Build Coupon Code Cost: {}ms",watch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }

    /**
     * 构造优惠券码的后14位
     * @param date 创建优惠券的日期
     * @return 14位优惠券码
     * */
    private String buildCouponCodeSuffix14(String date){
        char[] bases = new char[]{'1','2','3','4','5','6','7','8','9'};
        //中间6位
        List<Character> chars = date.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
        // 使用shuffle洗牌算法，将chars随机排序
        Collections.shuffle(chars);
        // 先将chars转为string然后在通过Collectors.joining()组成一个string字符串
        String mid6 = chars.stream().map(Objects::toString).collect(Collectors.joining());

        // 后八位: 第一个设置为不是0，后面7位随机0-9的数字
        String suffix8 = RandomStringUtils.random(1,bases) + RandomStringUtils.randomNumeric(7);

        return mid6 + suffix8;
    }
}
