package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.authentication.entity.Role;
import org.chenxw.authentication.entity.User;
import org.chenxw.authentication.entity.UserRole;
import org.chenxw.authentication.mapper.RoleMapper;
import org.chenxw.authentication.mapper.UserMapper;
import org.chenxw.authentication.mapper.UserRoleMapper;
import org.chenxw.authentication.service.AuthService;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.mapper.EmployeeMapper;
import org.chenxw.mes.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *    服务实现类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Service
@Transactional
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthService authService;

    @Override
    public List<Employee> getActivatedEmployees() {
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.eq("status", EMPLOYEE_STATUS_ON_THE_JOB);
        wrapper.gt("id", 0);
        return getBaseMapper().selectList(wrapper);
    }

    @Override
    public void update(Employee employee) {
        checkExists(employee);
        Employee entity = getBaseMapper().selectById(employee.getId());
        User user = userMapper.findByUsername(entity.getTel());
        user.setUsername(employee.getTel());
        user.setGender(employee.getGender());
        userMapper.updateUsernameGender(user);

        Role role = roleMapper.selectById(employee.getRoleId());
        if (!employee.getRoleId().equals(entity.getRoleId())){
            List<UserRole> roles = userRoleMapper.selectByMap(Collections.singletonMap("user_id", employee.getUserId()));
            roles.forEach(userRoleMapper::deleteById);
            UserRole newRoles = new UserRole();
            newRoles.setRoleId(role.getId());
            newRoles.setUserId(entity.getUserId());
            userRoleMapper.insert(newRoles);
        }
        baseMapper.updateById(employee);
    }

    @Override
    public void onTheJob(Long id) {
        Employee employee = baseMapper.selectById(id);
        if (employee!=null){
            employee.setStatus(EMPLOYEE_STATUS_ON_THE_JOB);
            authService.active(employee.getTel());
            baseMapper.updateById(employee);
        }
    }

    @Override
    public void leaveTheJob(Long id) {
        Employee employee = baseMapper.selectById(id);
        if (employee!=null){
            String username = employee.getTel();
            employee.setStatus(EMPLOYEE_STATUS_LEAVE_THE_JOB);
            authService.inactive(username);
            baseMapper.updateById(employee);
        }
    }

    // 检查员工是否存在
    private void checkExists(Employee employee){
        QueryWrapper<Employee> wrapper = Wrappers.query();
        wrapper.eq("tel", employee.getTel());
        wrapper.eq("employee_no", employee.getEmployeeNo());
        if (employee.getId()!=null){
            wrapper.ne("id", employee.getId());
        }
        List<Employee> employees = getBaseMapper().selectList(wrapper);
        if (employees.size()!=0){
            throw new RuntimeException("员工号或电话号码重复");
        }
    }



}
