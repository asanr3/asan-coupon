package com.asan.coupon.vo;

import com.asan.coupon.constant.CouponCategory;
import com.asan.coupon.constant.DistributeTarget;
import com.asan.coupon.constant.ProductLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Asan
 * @date 2020/6/13
 * 优惠券模板创建请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {

    /** 优惠券名称 */
    private String name;

    /** 优惠券logo */
    private String logo;

    /** 优惠券描述 */
    private String desc;

    /** 优惠券分类 */
    private String category;

    /** 产品线 */
    private Integer productLine;

    /** 总数 */
    private Integer count;

    /** 创建用户 */
    private Long userId;

    /** 目标用户 */
    private Integer target;

    /** 优惠券规则 */
    private TemplateRule rule;

    /** 检验对象的合法性 */
    public boolean validate(){
        /**字符型的判断*/
        boolean stringValid = StringUtils.isNotEmpty(name)
                && StringUtils.isNotEmpty(logo)
                && StringUtils.isNotEmpty(desc);
        /**枚举型的判断*/
        boolean enumValib = null != CouponCategory.of(category)
                && null != ProductLine.of(productLine)
                && null != DistributeTarget.of(target);
        /**数字型的判断*/
        boolean numValib = count > 0 && userId > 0;

        return stringValid && enumValib && numValib && rule.validate();

    }
}
