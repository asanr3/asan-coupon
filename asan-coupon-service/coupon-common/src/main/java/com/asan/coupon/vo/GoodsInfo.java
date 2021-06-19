package com.asan.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>fake 商品信息</h1>
 * @author Asan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {

    /** 商品类型*/
    private Integer type;

    /** 商品价格 */
    private Double price;

    /** 商品数量 */
    private Integer count;
}
