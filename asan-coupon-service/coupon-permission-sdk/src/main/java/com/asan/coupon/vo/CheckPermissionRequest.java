package com.asan.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>权限校验请求对象定义</h1>
 * @author Asan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPermissionRequest {
    /** 用户id **/
   private Long userId;

    /** 接口路径 **/
   private String uri;

    /** 接口访问类型：get、post...**/
   private String httpMethod;
}
