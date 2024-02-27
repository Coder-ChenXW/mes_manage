package org.chenxw.mes.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.chenxw.mes.domain.ProductInfo;
import org.chenxw.mes.entity.Product;
import org.chenxw.mes.entity.ProductCraft;
import org.chenxw.mes.entity.ProductLabel;

import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
public interface ProductService extends IService<Product> {

    int PRODUCT_STATUS_ACTIVATION = 1;
    int PRODUCT_STATUS_NO_ACTIVATION = 0;

    IPage<ProductInfo> queryPageData(IPage<Product> pageRequest, QueryWrapper<Product> wrapper);

    ProductInfo getInfoById(Long id);

    Product update(Product product, List<ProductLabel> labels, List<ProductCraft> crafts);


    Product create(Product product, List<ProductLabel> labels, List<ProductCraft> crafts);

    void inactive(Long id);

    void active(Long id);

}
