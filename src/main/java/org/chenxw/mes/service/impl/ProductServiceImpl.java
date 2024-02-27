package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.authentication.service.AuthService;
import org.chenxw.mes.mapper.ProductMapper;
import org.chenxw.mes.domain.ProductInfo;
import org.chenxw.mes.entity.*;
import org.chenxw.mes.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Service
@Transactional
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private
    EmployeeService employeeService;

    @Autowired
    private ProductLabelService labelService;

    @Autowired
    private ProductCraftService craftService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthService authService;

    // 分页数据查询
    @Override
    public IPage<ProductInfo> queryPageData(IPage<Product> pageRequest, QueryWrapper<Product> wrapper) {
        IPage<Product> page = getBaseMapper().selectPage(pageRequest, wrapper);
        IPage<ProductInfo> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        List<ProductInfo> records = page.getRecords().stream().map(this::transToInfo).collect(Collectors.toList());
        result.setRecords(records);
        return result;
    }

    @Override
    public ProductInfo getInfoById(Long id) {
        Product entity = getById(id);
        return this.transToInfo(entity);
    }

    @Override
    public Product update(Product product, List<ProductLabel> labels, List<ProductCraft> crafts) {
        checkExists(product);
        List<ProductLabel> existsLabels = labelService.getByProductId(product.getId());
        List<ProductCraft> existsCrafts = craftService.getByProductId(product.getId());

        List<String> simpleLabels0 = labels.stream().map(ProductLabel::getCode).collect(Collectors.toList());
        List<String> simpleCrafts0 = crafts.stream().map(ProductCraft::getName).collect(Collectors.toList());

        if (!simpleCrafts0.isEmpty() || !simpleLabels0.isEmpty()){
            List<Order> orders = orderService.getByProductId(product.getId());
            List<String> relatedOrders = orders.stream().filter(o -> o.getStatus() != OrderService.ORDER_STATUS_FINISHED).map(Order::getOrderNo).collect(Collectors.toList());
            if (relatedOrders.size()!=0){
                String str = StringUtils.join(relatedOrders, ",");
                throw new RuntimeException("有关联此产品的订单正在生产，请先完成未完成的订单: [" + str + "]");
            }
        }

        existsLabels.stream().filter(el->!simpleLabels0.contains(el.getCode())).forEach(el->labelService.removeById(el.getId()));
        existsCrafts.stream().filter(el->!simpleCrafts0.contains(el.getName())).forEach(el->craftService.removeById(el.getId()));
        // save need saved
        labels.stream().peek(l->{
            l.setProductId(product.getId());
            Optional<ProductLabel> optional = existsLabels.stream().filter(el -> el.getCode().equals(l.getCode())).findFirst();
            optional.ifPresent(opel->l.setId(opel.getId()));
            l.setStatus(ProductLabelService.LABEL_STATUS_NORMAL);
        }).forEach(l->{
            if (l.getId()!=null){
                labelService.updateById(l);
            }else{
                labelService.save(l);
            }
        });
        crafts.stream().peek(c->{
            c.setProductId(product.getId());
            Optional<ProductCraft> optional = existsCrafts.stream().filter(ec -> ec.getName().equals(c.getName())).findFirst();
            optional.ifPresent(opec->c.setId(opec.getId()));
        }).forEach(c->{
            if (c.getId()!=null){
                craftService.updateById(c);
            }else{
                craftService.save(c);
            }
        });
        getBaseMapper().updateById(product);
        return product;
    }

    @Override
    public Product create(Product product, List<ProductLabel> labels, List<ProductCraft> crafts) {

        checkExists(product);

        Employee employee = employeeService.getByUserId(authService.getCurrentUser().getId());

        if (employee==null){
            throw new RuntimeException("system error");
        }

        product.setStatus(PRODUCT_STATUS_ACTIVATION);
        product.setCreateBy(employee.getId());
        product.setUpdateBy(employee.getId());
        getBaseMapper().insert(product);
        labels.stream().peek(l->l.setStatus(ProductLabelService.LABEL_STATUS_NORMAL)).peek(l->l.setProductId(product.getId())).forEach(labelService::save);
        crafts.stream().peek(c->c.setProductId(product.getId())).forEach(craftService::save);
        return product;


    }

    @Override
    public void inactive(Long id) {
        Product product = getBaseMapper().selectById(id);
        if (product!=null){
            product.setStatus(PRODUCT_STATUS_NO_ACTIVATION);
            getBaseMapper().updateById(product);
        }
    }

    @Override
    public void active(Long id) {
        Product product = getBaseMapper().selectById(id);
        if (product!=null){
            product.setStatus(PRODUCT_STATUS_ACTIVATION);
            getBaseMapper().updateById(product);
        }
    }

    private void checkExists(Product product){
        QueryWrapper<Product> wrapper = Wrappers.query();
        wrapper.eq("product_no", product.getProductNo());
        if (product.getId()!=null){
            wrapper.ne("id", product.getId());
        }
        List<Product> products = getBaseMapper().selectList(wrapper);
        if (products.size()!=0){
            throw new RuntimeException("产品编号重复");
        }
    }

    private ProductInfo transToInfo(Product product) {
        ProductInfo info = new ProductInfo();
        BeanUtils.copyProperties(product, info);
        List<ProductLabel> labels = labelService.getByProductId(product.getId());
        List<ProductCraft> crafts = craftService.getByProductId(product.getId());
        info.setLabels(labels);
        info.setCrafts(crafts);

        Long createBy = product.getCreateBy();
        Long updateBy = product.getUpdateBy();
        Employee creator = employeeService.getById(createBy);
        Employee updater = employeeService.getById(updateBy);

        info.setCreateByName(creator.getEmployeeName());
        info.setUpdateByName(updater.getEmployeeName());
        return info;
    }

}
