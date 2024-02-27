package org.chenxw.authentication.service.impl;

import org.chenxw.authentication.mapper.RoleMapper;
import org.chenxw.authentication.mapper.UserMapper;
import org.chenxw.authentication.mapper.UserRoleMapper;
import org.chenxw.authentication.entity.Role;
import org.chenxw.authentication.entity.User;
import org.chenxw.authentication.entity.UserRole;
import org.chenxw.authentication.service.AuthService;
import org.chenxw.authentication.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    public static final int ACTIVE = 1;
    public static final int IN_ACTIVE = 0;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public String login(String username, String password) {


        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(upToken);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (((User)userDetails).getStatus()==IN_ACTIVE){
                throw new RuntimeException("此账号已停止使用,请联系管理员");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return JwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new RuntimeException("用户名或密码错误");
        }
    }

    @Override
    public void inactive(String username) {
        User user = userMapper.findByUsername(username);
        userMapper.updateStatus(user.getId(), IN_ACTIVE);
    }

    @Override
    public void active(String username) {
        User user = userMapper.findByUsername(username);
        userMapper.updateStatus(user.getId(), ACTIVE);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    @Override
    public void updatePassword(HttpServletRequest request, HttpServletResponse response, String oldPassword, String newPassword) {
        User user = getCurrentUser();
        if (user != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String rawPassword = user.getPassword();
            if (!encoder.matches(oldPassword, rawPassword)) {
                throw new RuntimeException("原密码不匹配");
            }
            userMapper.updatePassword(user.getId(), encoder.encode(newPassword));
            logout(request, response);
        }
    }


    @Override
    public User register(User user, List<Long> roleIds) {
        final String username = user.getUsername();
        User existsUser = userMapper.findByUsername(username);
        if (existsUser != null) {
            return existsUser;
        }
        List<Role> roles = roleMapper.selectBatchIds(roleIds);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setAvatar("/avatar.png");
        user.setStatus(ACTIVE);
        userMapper.insert(user);

        roles.stream().map(r -> new UserRole(user.getId(), r.getId())).forEach(userRoleMapper::insert);
        user.setRoles(roles);
        return user;
    }

    @Override
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public void unregister(String username) {
        User existsUser = userMapper.findByUsername(username);
        if (existsUser!=null){
            Map<String, Object> params = new HashMap<>();
            params.put("user_id", existsUser.getId());
            List<UserRole> userRoles = userRoleMapper.selectByMap(params);
            userRoles.forEach(ur->userRoleMapper.deleteById(ur.getId()));
            userMapper.deleteById(existsUser.getId());
        }
    }
}