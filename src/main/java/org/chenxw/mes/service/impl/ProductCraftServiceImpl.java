package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.mes.mapper.ProductCraftMapper;
import org.chenxw.mes.entity.ProductCraft;
import org.chenxw.mes.service.ProductCraftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ProductCraftServiceImpl extends ServiceImpl<ProductCraftMapper, ProductCraft> implements ProductCraftService {

}
