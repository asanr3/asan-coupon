package com.asan.coupon.service.impl;

import com.asan.coupon.dao.CouponTemplateDao;
import com.asan.coupon.entity.CouponTemplate;
import com.asan.coupon.exception.CouponException;
import com.asan.coupon.service.ITemplateBaseService;
import com.asan.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Asan
 * @date 2021/6/14
 * 优惠券模板基础服务接口实现
 */
@Slf4j
@Service
public class TemplateBaseServiceImpl implements ITemplateBaseService {

    /** CouponTemplate Dao */
    private final CouponTemplateDao templateDao;

    public TemplateBaseServiceImpl(CouponTemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    /**
     * 根据优惠券模板 id 获取优惠券模板信息
     * @param id 模板id
     * @return {@link CouponTemplate} 优惠券模板实体
     */
    @Override
    public CouponTemplate buildTemplateInfo(Integer id) throws CouponException {

        // Optional 可以判断参数是否为null
        Optional<CouponTemplate> template = templateDao.findById(id);

        // id不存在则抛出异常
        if(!template.isPresent()){
            throw new CouponException("Template Is Not Exist: " + id);
        }
        return template.get();
    }

    /**
     * 查找所有可用的优惠券模板
     * @return {@link CouponTemplateSDK}S
     */
    @Override
    public List<CouponTemplateSDK> findAllUsableTemplate() {

        List<CouponTemplate> templates = templateDao.findAllByAvailableAndExpired(true,false);
        return templates.stream().map(this::template2TemplateSDK).collect(Collectors.toList());
    }


    /**
     * 获取模板 ids 到CouponTemplateSDK的映射
     *
     * @param ids 模板 ids
     * @return Map<key:模板id ， value: CouponTemplateSDK>
     */
    @Override
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids) {

        List<CouponTemplate> templates = templateDao.findAllById(ids);
        return templates.stream().map(this::template2TemplateSDK)
                .collect(Collectors.toMap(CouponTemplateSDK::getId, Function.identity()));
    }

    /** 将CouponTemplate 转换为 CouponTemplateSDK*/
    private CouponTemplateSDK template2TemplateSDK(CouponTemplate template){
        return new CouponTemplateSDK(
                template.getId(),
                template.getName(),
                template.getLogo(),
                template.getDesc(),
                template.getCategory().getCode(),
                template.getProductLine().getCode(),
                // 并不是拼装好的 Template Key 由产品线+类型+日期
                template.getKey(),
                template.getTarget().getCode(),
                template.getRule()
        );
    }
}
