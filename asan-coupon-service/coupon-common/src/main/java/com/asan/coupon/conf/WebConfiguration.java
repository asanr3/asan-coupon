package com.asan.coupon.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Asan
 * @date 2021/6/8
 * 定制HTTP消息转换器
 * 即java实体对象向http输出请求的转换和http输入请求向java实体对象的转换
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    /** 配置消息转换器 */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        // 指定消息转换器（不需要springboot自动挑选消息转换器）以json的格式将消息返回
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
