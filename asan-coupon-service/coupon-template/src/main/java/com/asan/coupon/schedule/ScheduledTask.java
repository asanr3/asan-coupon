package com.asan.coupon.schedule;

import com.asan.coupon.dao.CouponTemplateDao;
import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Asan
 * @date 2021/6/14
 * 定时清理已过期的优惠券模板
 */
@Slf4j
@Component
public class ScheduledTask {

    /** CouponTemplate Dao*/
    private final CouponTemplateDao templateDao;

    public ScheduledTask(CouponTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    /**
     * 下线已过期的优惠券模板
     * @Scheduled(fixedRate = 60 * 60 * 1000) 一小时执行一次
     * */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void offlineCouponTemplate(){
        log.info("Start To Expire CouponTemplate");

        // 查询数据库中所有未过期的优惠券模板
        List<CouponTemplate> templates = templateDao.findAllByExpired(false);
        if(CollectionUtils.isEmpty(templates)){
            log.info("Done To Expire CouponTemplate.");
            return;
        }

        // 由于是定期处理过期的优惠券模板，可能存在查询的时候出现脏数据（已过期但是没有及时处理），所有再次判断
        Date cur = new Date();
        // 保存过期的优惠券模板
        List<CouponTemplate> expiredTemplates = new ArrayList<>(templates.size());

        templates.forEach(t -> {
            // 根据优惠券模板规则中的“过期规则”校验模板是否过期
            TemplateRule rule = t.getRule();
            // 优惠券过期时间<当前时间，则代表过期
            if(rule.getExpiration().getDeadline() < cur.getTime()){
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });

        if(CollectionUtils.isNotEmpty(expiredTemplates)){
            log.info("Expired CouponTemplate Num: {} ", templateDao.saveAll(expiredTemplates));
        }
        log.info("Done To Expire CouponTemplate.");
    }
}
