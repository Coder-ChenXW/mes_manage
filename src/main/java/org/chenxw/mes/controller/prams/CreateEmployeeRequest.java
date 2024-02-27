package org.chenxw.mes.controller.prams;

import lombok.Data;
import org.chenxw.authentication.entity.Role;
import org.chenxw.authentication.entity.User;
import org.chenxw.mes.entity.Employee;

@Data
public class CreateEmployeeRequest {
    private Employee employee;
    private User user;
    private Role role;
}
