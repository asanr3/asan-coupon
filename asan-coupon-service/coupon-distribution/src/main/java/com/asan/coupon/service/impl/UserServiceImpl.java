package com.asan.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.asan.coupon.constant.Constant;
import com.asan.coupon.constant.CouponStatus;
import com.asan.coupon.dao.CouponDao;
import com.asan.coupon.entity.Coupon;
import com.asan.coupon.exception.CouponException;
import com.asan.coupon.feign.SettlementClient;
import com.asan.coupon.feign.TemplateClient;
import com.asan.coupon.service.IRedisService;
import com.asan.coupon.service.IUserService;
import com.asan.coupon.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h1>用户服务相关的接口实现</h1>
 * 所有的操作过程, 状态都保存在 Redis 中, 并通过 Kafka 把消息传递到 MySQL 中
 * 什么使用 Kafka, 而不是直接使用 SpringBoot 中的异步处理 ?
 * 出于安全性考虑使用kafka，因为异步任务可能会失败，但是即使传递到kafka中的消息失败了还是可以重新从kafka中获取消息保证一致性
 *
 * @author mingkai yun
 * @date 2021/6/21
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    /**
     * Coupon Dao
     */
    @Autowired
    private CouponDao couponDao;

    /**
     * Redis 服务
     */
    @Autowired
    private IRedisService redisService;

    /**
     * 模板微服务客户端
     */
    @Autowired
    private TemplateClient templateClient;

    /**
     * 结算微服务客户端
     */
    @Autowired
    private SettlementClient settlementClient;

    /**
     * Kafka 客户端
     */
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * <h2>根据用户 id 和状态查询优惠券记录</h2>
     *
     * @param userId 用户 id
     * @param status 优惠券状态
     * @return {@link Coupon}s
     */
    @Override
    public List<Coupon> findCouponsByStatus(Long userId, Integer status) throws CouponException {
        // 从redis中获取对应的优惠券数据，如果为null，会自动加入一张id为-1的无效优惠券
        List<Coupon> curCached = redisService.getCachedCoupons(userId, status);
        // 保存出参数据
        List<Coupon> preTarget;

        // 判断从缓存中获取的当前优惠券是否为空
        if (CollectionUtils.isNotEmpty(curCached)) {
            log.debug("coupon cache is not empty: {}, {}", userId, status);
            preTarget = curCached;
        } else {
            log.debug("coupon cache is empty, get coupon from db: {}, {}",
                    userId, status);
            // 如果为空则查询数据库
            List<Coupon> dbCoupons = couponDao.findAllByUserIdAndStatus(
                    userId, CouponStatus.of(status)
            );

            // 如果数据库中没有记录, 直接返回就可以, Cache 中已经加入了一张无效的优惠券
            if (CollectionUtils.isEmpty(dbCoupons)) {
                log.debug("current user do not have coupon: {}, {}", userId, status);
                return dbCoupons;
            }

            // 填充 dbCoupons的 templateSDK 字段
            Map<Integer, CouponTemplateSDK> id2TemplateSDK =
                    templateClient.findIds2TemplateSDK(
                            dbCoupons.stream()
                                    .map(Coupon::getTemplateId)
                                    .collect(Collectors.toList())
                    ).getData();
            dbCoupons.forEach(
                    dc -> dc.setTemplateSDK(
                            id2TemplateSDK.get(dc.getTemplateId())
                    )
            );
            // 数据库中存在记录
            preTarget = dbCoupons;
            // 将记录写入 Cache
            redisService.addCouponToCache(userId, preTarget, status);
        }

        // 将无效优惠券剔除
        preTarget = preTarget.stream()
                .filter(c -> c.getId() != -1)
                .collect(Collectors.toList());
        // 如果当前获取的是可用优惠券, 还需要做对已过期优惠券的延迟处理
        if (CouponStatus.of(status) == CouponStatus.USABLE) {
            CouponClassify classify = CouponClassify.classify(preTarget);
            // 如果已过期状态不为空, 需要做延迟处理
            if (CollectionUtils.isNotEmpty(classify.getExpired())) {
                log.info("Add Expired Coupons To Cache From FindCouponsByStatus: " +
                        "{}, {}", userId, status);
                redisService.addCouponToCache(
                        userId, classify.getExpired(),
                        CouponStatus.EXPIRED.getCode()
                );
                // 发送到 kafka 中做异步处理
                kafkaTemplate.send(
                        Constant.TOPIC,
                        JSON.toJSONString(new CouponKafkaMessage(
                                CouponStatus.EXPIRED.getCode(),
                                classify.getExpired().stream()
                                        .map(Coupon::getId)
                                        .collect(Collectors.toList())
                        ))
                );
            }

            return classify.getUsable();
        }
        return preTarget;
    }

    /**
     * <h2>根据用户 id 查找当前可以领取的优惠券模板</h2>
     *
     * @param userId 用户 id
     * @return {@link CouponTemplateSDK}s
     */
    @Override
    public List<CouponTemplateSDK> findAvailableTemplate(Long userId) throws CouponException {
        return null;
    }

    /**
     * <h2>用户领取优惠券</h2>
     *
     * @param request {@link AcquireTemplateRequest}
     * @return {@link Coupon}
     */
    @Override
    public Coupon acquireTemplate(AcquireTemplateRequest request) throws CouponException {
        return null;
    }

    /**
     * <h2>结算(核销)优惠券</h2>
     * SettlementInfo与结算微服务是通用的，所以需要定义在共用的地方，即coupon-common中
     *
     * @param info {@link SettlementInfo}
     * @return {@link SettlementInfo}
     */
    @Override
    public SettlementInfo settlement(SettlementInfo info) throws CouponException {
        return null;
    }
}
