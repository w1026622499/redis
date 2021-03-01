package com.km.redisboot.mapper;

import com.km.redisboot.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Wh
 * @title
 * @description
 * @createTime 2021年03月01日 09:59:00
 * @modifier：Wh
 * @modification_time：2021-03-01 09:59
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> findMemberAll();

}
