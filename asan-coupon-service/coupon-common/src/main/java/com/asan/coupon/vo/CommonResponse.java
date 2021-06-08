package com.asan.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Asan
 * @date 2021/6/8
 * 通用响应对象定义
 * @Data 自动注入get set请求
 * @NoArgsConstructor 无参构造函数
 * @AllArgsConstructor 全参构造函数
 * Serializable 序列化接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public CommonResponse(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
