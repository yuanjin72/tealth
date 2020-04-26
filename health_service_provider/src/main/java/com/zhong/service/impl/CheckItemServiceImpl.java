package com.zhong.service.impl;/**
 * @Author yuanjin
 * @Date 2020/4/2221:50
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.dao.CheckItemDao;
import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.pojo.CheckItem;
import com.zhong.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-22 21:50
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }
    //分页查询
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        //完成分页查询
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCodition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }
    //根据Id删除检查项
    @Override
    public void deleteById(Integer id) {
        //判断是否关联检查组
     long count = checkItemDao.findCountByCheckItemId(id);
       if(count > 0){
           //如果有关联不容许删除
           throw new RuntimeException();
       }
       checkItemDao.deleteById(id);
    }
    //回显数据
    @Override
    public CheckItem findById(Integer id) {

        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    //编辑检查项
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);

    }
}
