package com.asan.coupon.constant;

/**
 * @author Asan
 * @date 2021/6/11
 * 通用常量定义
 */
public class Constant {

    /** Kafka 消息的 Topic
     * asan_user_coupon_op:asan=前缀， user_coupon:用户的优惠券， op：operation操作
     * */
    public static final String TOPIC = "asan_user_coupon_op";

    /**
     * Redis key 的前缀定义
     * */
    public static class RedisPrefix{

        /** 优惠券码 key 前缀*/
        public static final String COUPON_TEMPLATE = "asan_coupon_template_code_";

        /** 用户当前所有可用的优惠券 key 前缀*/
        public static final String USER_COUPON_USABLE = "asan_user_coupon_usable_";

        /** 用户当前所有已使用的优惠券 key 前缀 */
        public static final String USER_COUPON_USED = "asan_user_coupon_USED_";

        /** 用户当前所有已过期的优惠券 key 前缀 */
        public static final String USER_COUPON_EXPIRED = "asan_user_coupon_expired_";
    }
}
