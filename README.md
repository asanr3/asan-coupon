
## 1.简介
    基于springcloud微服务开发优惠券后台系统

## 2.代码结构

```														
├─asan-coupon											整体工程
│  ├─asan-coupon-service								功能微服务						
│  │  ├─coupon-common                                   公共模块
│  │  ├─coupon-distribution                             优惠券分发微服务
│  │  ├─coupon-template                                 优惠券模板微服务
│  │  ├─coupon-settlement                               优惠券结算微服务                              
│  │  ├─coupon-permission-sdk                           权限系统sdk
│  │  ├─coupon-permission-service                       权限系统功能服务
│  │  └─coupon-permission-detector                      权限系统探测器
│  ├─coupon-eureka										注册中心			
│  ├─coupon-gateway	                                    网关
```
