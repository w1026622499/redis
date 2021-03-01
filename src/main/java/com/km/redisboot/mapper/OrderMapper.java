package com.km.redisboot.mapper;

import com.km.redisboot.domain.OrderEntity;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrderMapper {

    @Insert("insert into order_number values (null,#{orderName},0,#{orderToken},#{orderId})")
    int insertOrder(OrderEntity OrderEntity);


    @Select("SELECT ID AS ID ,orderName AS orderName ,orderStatus AS orderstatus,orderToken as ordertoken,orderId as  orderid FROM order_number\n" +
            "where orderToken=#{orderToken};")
    OrderEntity getOrderNumber(String orderToken);

    @Update("\n" +
            "\n" +
            "update order_number set orderStatus=#{orderStatus} where orderToken=#{orderToken};")
    int updateOrderStatus(String orderToken, Integer orderStatus);


    @Select("SELECT id AS ID ,orderName AS orderName ,orderStatus AS orderstatus,ordertoken as ordertoken,order_id as  orderId FROM order_number \n" +
            "where id=#{orderId};")
    OrderEntity getOrderById(Integer orderId);

    @Select("SELECT id AS ID  FROM order_number\n")
    List<Integer> getOrderIds();

}
