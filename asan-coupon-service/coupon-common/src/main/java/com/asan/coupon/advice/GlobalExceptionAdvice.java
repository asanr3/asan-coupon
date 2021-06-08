package com.asan.coupon.advice;

import com.asan.coupon.exception.CouponException;
import com.asan.coupon.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Asan
 * @date 2020/1/9 15:40
 * 全局异常处理
 * @RestControllerAdvice ：组合注解，controllerAdvice + ResponseBody,是对RestController的功能增强
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /** @ExceptionHandler 异常处理器，指定对CouponException 进行统一处理*/
    @ExceptionHandler(value = CouponException.class)
    public CommonResponse<String> handlerCouponException(HttpServletRequest req, CouponException ex){
        //统一异常接口的响应
        // 优化：定义不同类型的异常枚举（异常码和异常信息）
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
