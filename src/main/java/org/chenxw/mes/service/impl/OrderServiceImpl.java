package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.authentication.entity.User;
import org.chenxw.authentication.service.AuthService;
import org.chenxw.mes.mapper.OrderMapper;
import org.chenxw.mes.domain.OrderInfo;
import org.chenxw.mes.domain.OrderItemInfo;
import org.chenxw.mes.entity.*;
import org.chenxw.mes.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


}
