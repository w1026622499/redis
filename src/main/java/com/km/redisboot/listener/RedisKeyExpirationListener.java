package com.km.redisboot.listener;


import com.km.redisboot.domain.OrderEntity;
import com.km.redisboot.mapper.OrderMapper;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private OrderMapper orderMapper;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 待支付
     */
    private static final Integer ORDER_STAYPAY = 0;
    /**
     * 失效
     */
    private static final Integer ORDER_INVALID = 2;

    /**
     * 使用该方法监听 当我们都key失效的时候执行该方法
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiraKey = message.toString();
        System.out.println("该key ：expiraKey:" + expiraKey + "失效啦~");
        //前缀判断 orderToken 、库
        OrderEntity orderNumber = orderMapper.getOrderNumber(expiraKey);
        if (orderNumber == null) {
            return;
        }
        // 获取订单状态
        Integer orderStatus = orderNumber.getOrderStatus();
        // 如果该订单状态为待支付的情况下，直接将该订单修改为已经超时s
        if (orderStatus.equals(ORDER_STAYPAY)) {
            orderMapper.updateOrderStatus(expiraKey, ORDER_INVALID);
            // 库存在加上1
        }

    }
}
