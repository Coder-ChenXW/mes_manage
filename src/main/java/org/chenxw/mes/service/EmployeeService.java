package org.chenxw.mes.service;

import org.chenxw.authentication.entity.Role;
import org.chenxw.authentication.entity.User;
import org.chenxw.mes.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
public interface EmployeeService extends IService<Employee> {

    int EMPLOYEE_STATUS_ON_THE_JOB = 1;
    int EMPLOYEE_STATUS_LEAVE_THE_JOB = 0;


}