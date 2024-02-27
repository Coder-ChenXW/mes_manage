package org.chenxw.authentication.domain.dto;

import lombok.Data;
import org.chenxw.authentication.entity.User;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;


/**
 * @description:
 * @author: ChenXW
 * @date: 2024/2/25 20:59
 */
@Data
public class UserRegisterDto implements Serializable {

    private String username;
    private String password;
    private String nickname;
    private Integer age;
    private String tel;
    private Integer gender;
    private String email;
    private List<Long> roleIds;


    public User transfer(){
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
