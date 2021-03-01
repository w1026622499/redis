package com.km.redisboot.controller;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.km.redisboot.domain.OrderEntity;
import com.km.redisboot.mapper.OrderMapper;
import com.km.redisboot.uitl.RedisTemplateUtils;
import com.km.redisboot.uitl.RedisUitl;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蚂蚁课堂创始人-余胜军QQ644064779
 * @title: OrderController
 * @description: 每特教育独创第五期互联网架构课程
 * @date 2019/11/1921:27
 */
@RestController
public class OrderController {

    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private RedisUitl redisUtils;

    @Resource
    private RedisTemplateUtils redisTemplateUtils;

    BloomFilter<Integer> integerBloomFilter;

    @RequestMapping("/addOrder")
    public String addOrder() {
        // 1.提前生成订单token 临时且唯一
        String orderToken = UUID.randomUUID().toString();
        Long orderId = System.currentTimeMillis();
        // 2.将我们的token存放到rdis中
        redisUtils.setString(orderToken, orderId + "", 10L);
        OrderEntity orderEntity = new OrderEntity(null, "蚂蚁课堂永久会员",orderId + "", orderToken);
        return orderMapper.insertOrder(orderEntity) > 0 ? "success" : "fail";
    }

    @RequestMapping("/getOrder")
    public OrderEntity getOrder(Integer orderId) {

        // 0.判断我们的布隆过滤器
        if (!integerBloomFilter.mightContain(orderId)) {
            System.out.println("从布隆过滤器中查询不存在");
            return null;
        }

        // 1.先查询Redis中数据是否存在
        OrderEntity orderRedisEntity = (OrderEntity) redisTemplateUtils.getObjet(orderId + "");
        if (orderRedisEntity != null) {
            System.out.println("直接从Redis中返回数据");
            return orderRedisEntity;
        }
        // 2. 查询数据库的内容
        System.out.println("从DB查询数据");
        OrderEntity orderDBEntity = orderMapper.getOrderById(orderId);
        if (orderDBEntity != null) {
            System.out.println("将Db数据放入到Redis中");
            redisTemplateUtils.setObject(orderId + "", orderDBEntity);
        }
        return orderDBEntity;
    }

    @RequestMapping("/dbToBulong")
    public String dbToBulong() {
        // 1.从数据库预热id到布隆过滤器中
        List<Integer> orderIds = orderMapper.getOrderIds();
        integerBloomFilter = BloomFilter.create(Funnels.integerFunnel(), orderIds.size(), 0.01);
        for (int i = 0; i < orderIds.size(); i++) {
            // 添加到我们的布隆过滤器中
            integerBloomFilter.put(orderIds.get(i));
        }

        return "success";
    }
}
