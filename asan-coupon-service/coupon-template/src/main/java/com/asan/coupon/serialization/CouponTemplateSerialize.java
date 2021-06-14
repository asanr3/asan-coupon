package com.asan.coupon.serialization;

import com.alibaba.fastjson.JSON;
import com.asan.coupon.entity.CouponTemplate;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author Asan
 * @date 2021/6/11
 * 优惠券模板实体类自定义序列化器
 */

public class CouponTemplateSerialize extends JsonSerializer<CouponTemplate> {

    /**
     * 参数1：想要序列化的对象，2：生成json的生成器， 3作为序列化工具提供
     * */
    @Override
    public void serialize(CouponTemplate template,
                          JsonGenerator generator,
                          SerializerProvider serializerProvider)
            throws IOException {

        //开始序列化对象
        generator.writeStartObject();
        generator.writeStringField("id",template.getId().toString());
        generator.writeStringField("name",template.getName());
        generator.writeStringField("logo",template.getLogo());
        generator.writeStringField("desc",template.getDesc());
        // 不直接返回 code 而是返回描述信息
        generator.writeStringField("category",template.getCategory().getDescription());
        generator.writeStringField("productLine",template.getProductLine().getDescription());
        generator.writeStringField("count",template.getCount().toString());
        generator.writeStringField("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(template.getCreateTime()));
        generator.writeStringField("userId",template.getUserId().toString());
        // 给优惠券模板唯一编码拼接上4位的id
        generator.writeStringField("key",template.getKey() + String.format("%04d",template.getId()));
        generator.writeStringField("target", template.getTarget().getDescription());
        generator.writeStringField("rule", JSON.toJSONString(template.getRule()));
        // 结束序列化对象
        generator.writeEndObject();
    }
}
