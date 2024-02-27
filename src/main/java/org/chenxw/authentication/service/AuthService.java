package org.chenxw.authentication.service;

import org.chenxw.authentication.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AuthService {

    User register(User user, List<Long> roleIds);
    User getCurrentUser();
    void unregister(String username);
    String login(String username, String password);
    void inactive(String username);
    void active(String username);
    void logout(HttpServletRequest request, HttpServletResponse response);
    void updatePassword(HttpServletRequest request, HttpServletResponse response, String oldPassword, String newPassword);

}