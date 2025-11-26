package com.x1a02.pension.mapper;

import com.x1a02.pension.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserMapper {

    int insert(User user);

    int update(User user);

    int deleteById(Long id);

    User selectById(Long id);

    User selectByUsername(@Param("username") String username);

    List<User> selectAll();

    List<User> selectByUserType(@Param("userType") Integer userType);
}
