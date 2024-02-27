package org.chenxw.authentication.controller.params;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
public class InfoResponse {

    private String name;
    private String avatar;
    private List<String> roles;
    private List<String> buttons;
}
