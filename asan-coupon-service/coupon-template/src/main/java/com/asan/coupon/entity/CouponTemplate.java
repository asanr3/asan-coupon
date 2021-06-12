package com.asan.coupon.entity;

import com.asan.coupon.constant.CouponCategory;
import com.asan.coupon.constant.DistributeTarget;
import com.asan.coupon.constant.ProductLine;
import com.asan.coupon.converter.CouponCategoryConverter;
import com.asan.coupon.converter.DistributeTargetConverter;
import com.asan.coupon.converter.ProductLineConverter;
import com.asan.coupon.converter.RuleConverter;
import com.asan.coupon.vo.TemplateRule;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Convert;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Asan
 * @date 2021/6/12
 * 优惠券模板实体类定义：基础属性 + 规则属性
 * @EntityListeners 实体监听器，自动添加创建时间等
 * @Convert 属性转换器 用于数据库字段与实体属性之间的转换
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("coupon_template")
public class CouponTemplate implements Serializable {

    /** 自增主键 */
    @TableId(type= IdType.AUTO)
    private Integer id;

    /** 是否是可用状态 */
    @TableField("available")
    @NonNull
    private Boolean available;

    /** 是否过期 */
    @TableField("expired")
    @NonNull
    private Boolean expired;

    /** 优惠券名称 */
    @TableField("name")
    @NonNull
    private String name;

    /** 优惠券 logo */
    @TableField("logo")
    @NonNull
    private String logo;

    /** 优惠券描述 */
    @TableField("intro")
    @NonNull
    private String desc;

    /** 优惠券分类 */
    @TableField("category")
    @NonNull
    @Convert(converter = CouponCategoryConverter.class)
    private CouponCategory category;

    /** 产品线 */
    @TableField("product_line")
    @NonNull
    @Convert(converter = ProductLineConverter.class)
    private ProductLine productLine;

    /** 总数 */
    @TableField("coupon_count")
    @NonNull
    private Integer count;

    /** 创建时间 */
    @TableField("create_time")
    @NonNull
    private Date createTime;

    /** 创建用户 */
    @TableField("user_id")
    @NonNull
    private Long userId;

    /** 优惠券模板的编码 */
    @TableField("template_key")
    @NonNull
    private String key;

    /** 目标用户 */
    @TableField("target")
    @NonNull
    @Convert(converter = DistributeTargetConverter.class)
    private DistributeTarget target;

    /** 优惠券规则 */
    @TableField("rule")
    @NonNull
    @Convert(converter = RuleConverter.class)
    private TemplateRule rule;

    /**
     * <h2>自定义构造函数</h2>
     * */
    public CouponTemplate(String name, String logo, String desc, String category,
                          Integer productLine, Integer count, Long userId,
                          Integer target, TemplateRule rule) {

        this.available = false;
        this.expired = false;
        this.name = name;
        this.logo = logo;
        this.desc = desc;
        this.category = CouponCategory.of(category);
        this.productLine = ProductLine.of(productLine);
        this.count = count;
        this.userId = userId;
        // 优惠券模板唯一编码 = 4(产品线和类型) + 8(日期: 20190101) + id(扩充为4位)
        this.key = productLine.toString() + category +
                new SimpleDateFormat("yyyyMMdd").format(new Date());
        this.target = DistributeTarget.of(target);
        this.rule = rule;
    }

}
