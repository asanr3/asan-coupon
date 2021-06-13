package com.asan.coupon.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Asan
 * @date 2020/6/13
 * 自定义异步任务线程池
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {

    /**
     * 自定义线程池
     * @return
     */
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(10);
        // 最大线程池数量
        executor.setMaxPoolSize(20);
        // 队列容量
        executor.setQueueCapacity(20);
        // 线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(60);
        // 线程名称前缀
        executor.setThreadNamePrefix("AsanAsync_");

        // 服务关闭 线程池是否退出
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 服务关闭 线程池最长等待时间
        executor.setAwaitTerminationSeconds(60);

        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 初始化线程池
        executor.initialize();

        return executor;
    }

    /**
     * 捕捉IllegalArgumentException异常
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        /**
         * @param throwable:异步任务抛出的异常
         * @param method :异步任务对应的方法
         * @param objects:异步任务参数数组
         * */
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {

            throwable.printStackTrace();
            log.error("AsyncError: {}, Method: {}, Param:{}",
                    throwable.getMessage(),
                    method.getName(),
                    JSON.toJSONString(objects));

            // TODO 在真实的企业开发中，可以在发生异常时主动的发送短信或者邮件给相关工作人员，做进一步处理
        }
    }
}
