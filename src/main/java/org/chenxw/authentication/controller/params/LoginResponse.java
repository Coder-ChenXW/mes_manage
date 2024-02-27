package org.chenxw.authentication.controller.params;


import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
