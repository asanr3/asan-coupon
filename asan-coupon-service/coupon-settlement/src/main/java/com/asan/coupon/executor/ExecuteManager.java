package com.asan.coupon.executor;

import com.asan.coupon.constant.CouponCategory;
import com.asan.coupon.constant.RuleFlag;
import com.asan.coupon.exception.CouponException;
import com.asan.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>优惠券结算规则执行管理器</h1>
 * 即根据用户的请求(SettlementInfo)找到对应的 Executor, 去做结算
 * BeanPostProcessor: Bean 后置处理器 是在系统中所有的Bean都创建出来并且放进spring容器中 两个接口方法才会被执行到
 * @author Asan
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class ExecuteManager implements BeanPostProcessor {

    /** 规则执行器映射 根据RuleFlag找到对应的RuleExecutor*/
    private static Map<RuleFlag, RuleExecutor> executorIndex =
            new HashMap<>(RuleFlag.values().length);

    /**
     * <h2>优惠券结算规则计算入口</h2>
     * 根据前端传进来的settlement去选择对应的计算规则
     * 注意: 一定要保证传递进来的优惠券个数 >= 1
     * */
    public SettlementInfo computeRule(SettlementInfo settlement)
            throws CouponException {

        SettlementInfo result = null;

        // 单类优惠券
        if (settlement.getCouponAndTemplateInfos().size() == 1) {

            // 获取优惠券的类别
            CouponCategory category = CouponCategory.of(
                    settlement.getCouponAndTemplateInfos().get(0)
                            .getTemplate().getCategory()
            );

            // 根据优惠券类型选择对应的计算规则
            switch (category) {
                case MANJIAN:
                    result = executorIndex.get(RuleFlag.MANJIAN)
                            .computeRule(settlement);
                    break;
                case ZHEKOU:
                    result = executorIndex.get(RuleFlag.ZHEKOU)
                            .computeRule(settlement);
                    break;
                case LIJIAN:
                    result = executorIndex.get(RuleFlag.LIJIAN)
                            .computeRule(settlement);
                    break;
            }
        } else {
            // 多类优惠券
            List<CouponCategory> categories = new ArrayList<>(
                    settlement.getCouponAndTemplateInfos().size()
            );
            settlement.getCouponAndTemplateInfos().forEach(ct ->
                    categories.add(CouponCategory.of(
                            ct.getTemplate().getCategory()
                    )));
            // 如果优惠券类型不等于2个，则抛出异常，因为我们只设计了满减和折扣两种优惠券的组合使用
            if (categories.size() != 2) {
                throw new CouponException("Not Support For More " +
                        "Template Category");
            } else {
                // 如果等于2个还要判断一下是否是满减和折扣，否则也抛出异常
                if (categories.contains(CouponCategory.MANJIAN)
                        && categories.contains(CouponCategory.ZHEKOU)) {
                    result = executorIndex.get(RuleFlag.MANJIAN_ZHEKOU)
                            .computeRule(settlement);
                } else {
                    throw new CouponException("Not Support For Other " +
                            "Template Category");
                }
            }
        }

        return result;
    }

    /**
     * <h2>在 bean 初始化之前去执行(before)</h2>
     * */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // 判断当前的bean是否属于RuleExecutor类的实例
        if (!(bean instanceof RuleExecutor)) {
            return bean;
        }
        // 属于就将该bean进行强转，并获取到RuleExecutor对应的RuleFlag
        RuleExecutor executor = (RuleExecutor) bean;
        RuleFlag ruleFlag = executor.ruleConfig();

        // 如果executorIndex中包含了当前的ruleFlag就代表这个executor被注册过来，抛出重复注册异常
        if (executorIndex.containsKey(ruleFlag)) {
            throw new IllegalStateException("There is already an executor" +
                    "for rule flag: " + ruleFlag);
        }

        log.info("Load executor {} for rule flag {}.",
                executor.getClass(), ruleFlag);
        executorIndex.put(ruleFlag, executor);

        return null;
    }

    /**
     * <h2>在 bean 初始化之后去执行(after)</h2>
     * */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
