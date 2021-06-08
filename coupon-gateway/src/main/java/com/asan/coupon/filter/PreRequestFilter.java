package com.asan.coupon.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <h1>在过滤器中存储客户端发起请求的时间戳</h1>
 * @author Asan
 */
@Slf4j
@Component
public class PreRequestFilter extends AbstractPreZuulFilter {

    @Override
    protected Object cRun() {

        context.set("startTime", System.currentTimeMillis());

        return success();
    }

    /**
     * filterOrder() must also be defined for a filter. Filters may have the same  filterOrder if precedence is not
     * important for a filter. filterOrders do not need to be sequential.
     * 同种类的过滤器的执行顺序。
     * 按照返回值的自然升序执行。
     * @return the int order of a filter
     */
    @Override
    public int filterOrder() {
        return 0;
    }
}
