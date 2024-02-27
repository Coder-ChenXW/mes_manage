package org.chenxw.authentication.controller.params;


import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-25
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
