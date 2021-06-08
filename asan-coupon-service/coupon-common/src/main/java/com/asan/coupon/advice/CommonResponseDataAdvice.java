package com.asan.coupon.advice;

import com.asan.coupon.annotation.IgnoreResponseAdvice;
import com.asan.coupon.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Asan
 * @date 2021/6/8
 * 统一响应
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /** 判断是否需要对响应进行处理
     * @return false:不需要处理，true 需要处理
     * */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果当前方法所在的类标识了@IgnoreResponseAdvice 注解，则不需要处理
        if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }
        // 如果当前方法标识了 @IgnoreResponseAdvice 注解, 不需要处理
        // if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
        //     return false;
        // }
        //
        // // 对响应进行处理, 执行 beforeBodyWrite 方法
        // return true;

       return !methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class);
    }

    /** 响应之前返回的处理 */
    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        // 定义最终的返回对象
        CommonResponse<Object> response = new CommonResponse<>(0, "");

        // 如果 o是null ，response不需要设置data
        if (null == o) {
            return response;
            // 如果o已经是CommonResponse，不需要再次处理
        }else if (o instanceof CommonResponse){
            response = (CommonResponse<Object>) o;
            //否则把响应对象作为 CommonResponse的data部分
        }else{
            response.setData(o);
        }
        return response;
    }
}
