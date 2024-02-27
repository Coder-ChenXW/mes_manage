package org.chenxw.authentication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.authentication.common.Constants;
import org.chenxw.authentication.controller.params.InfoResponse;
import org.chenxw.authentication.controller.params.LoginRequest;
import org.chenxw.authentication.controller.params.LoginResponse;
import org.chenxw.authentication.controller.params.UpdatePwdRequest;
import org.chenxw.authentication.domain.dto.UserRegisterDto;
import org.chenxw.authentication.entity.Role;
import org.chenxw.authentication.entity.User;
import org.chenxw.authentication.service.AuthService;
import org.chenxw.authentication.service.CustomizeUserDetailService;
import org.chenxw.authentication.utils.JwtTokenUtil;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户业务
 * @author: ChenXW
 * @date: 2024/2/25 19:37
 */
@RestController
@RequestMapping(value ="/ac/authentication")
@Api(value = "用户验证相关")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private CustomizeUserDetailService customizeUserDetailService;

    /**
     * @description: 登录
     * @author: ChenXW
     * @date: 2024/2/25 19:41
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<LoginResponse> createToken(@RequestBody LoginRequest request) throws AuthenticationException {
        String token = authService.login(request.getUsername(), request.getPassword());
        return Result.generateSuccess(new LoginResponse(token));
    }

    /**
     * @description: 注册
     * @author: ChenXW
     * @date: 2024/2/25 20:09
     */
    @ApiOperation("注册")
    @PostMapping("/register")
    public Result<User> register(@RequestBody UserRegisterDto params) throws AuthenticationException {
        User user = authService.register(params.transfer(), params.getRoleIds());
        return Result.generateSuccess(user);
    }

    /**
     * @description: 登出
     * @author: ChenXW
     * @date: 2024/2/25 20:21
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        authService.logout(request, response);
        return Result.generateSuccess(null);
    }

    /**
     * @description: tokenInfo
     * @author: ChenXW
     * @date: 2024/2/25 20:30
     */
    @ApiOperation("token令牌信息")
    @GetMapping("/info")
    public Result<InfoResponse> info(@RequestParam("token") String token) throws AuthenticationException {
        String username = JwtTokenUtil.getUsernameFromToken(token);
        User user = (User) customizeUserDetailService.loadUserByUsername(username);
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        List<String> buttons = roles.stream().map(Constants.roleButtons::get).flatMap(List::stream).collect(Collectors.toList());
        return Result.generateSuccess(new InfoResponse(user.getNickname(), user.getAvatar(), roles, buttons));
    }

    /**
     * @description: 修改密码
     * @author: ChenXW
     * @date: 2024/2/26 22:18
     */
    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public Result<Void> updatePassword(HttpServletRequest req, HttpServletResponse resp, @RequestBody UpdatePwdRequest request) throws AuthenticationException {
        if (StringUtils.isNotBlank(request.getOldPassword()) && StringUtils.isNotBlank(request.getNewPassword())){
            authService.updatePassword(req, resp, request.getOldPassword(), request.getNewPassword());
        }
        return Result.generateSuccess(null);
    }

}