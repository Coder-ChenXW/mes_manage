package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.mes.mapper.ProductLabelMapper;
import org.chenxw.mes.entity.ProductLabel;
import org.chenxw.mes.service.ProductLabelService;
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
public class ProductLabelServiceImpl extends ServiceImpl<ProductLabelMapper, ProductLabel> implements ProductLabelService {

    @Override
    public List<ProductLabel> getByProductId(Long productId) {
        return getBaseMapper().selectByMap(Collections.singletonMap("product_id", productId));
    }

}
