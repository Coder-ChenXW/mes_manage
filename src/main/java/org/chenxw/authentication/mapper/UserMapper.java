package org.chenxw.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.chenxw.authentication.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findByUsername(@Param("username") String username);
    void updateUsernameGender(User user);
    void updateStatus(@Param("id") Long id, @Param("status") int status);

    void updatePassword(@Param("id") Long id, @Param("pwd") String pwd);
}
