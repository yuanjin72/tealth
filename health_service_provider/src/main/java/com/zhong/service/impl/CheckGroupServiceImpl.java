package com.zhong.service.impl;/**
 * @Author yuanjin
 * @Date 2020/4/2513:19
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.dao.CheckGroupDao;
import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.pojo.CheckGroup;
import com.zhong.pojo.CheckItem;
import com.zhong.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-25 13:19
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    //新增检查组，提示需要关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
    //新增检查组
        checkGroupDao.add(checkGroup);
    //设置检查组与检查项的关联关系
        Integer checkGroupId = checkGroup.getId();
        //批量注入
        List<Map> list = new ArrayList<>();
       this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();    //查询条件
        Integer currentPage = queryPageBean.getCurrentPage();   //当前页码
        Integer pageSize = queryPageBean.getPageSize();         //每页显示数
        //调用分页助手法方
        PageHelper.startPage(currentPage,pageSize);

        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
    //根据id查询检查组
    @Override
    public CheckGroup findById(Integer id) {

        return checkGroupDao.findById(id);
    }

    //根据检查组I查询检查项Id
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {

        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }
    //根据检查组Id重新编辑
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组基本信息，
            checkGroupDao.edit(checkGroup);
        //清理当前检查组关联的检查项，
        checkGroupDao.deleteAssoication(checkGroup.getId());
        //重新建立检查组和检查项关联关系
        Integer checkGroupId = checkGroup.getId();

        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }
    //建立检查组和检查项多对多关系
    public  void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if (checkitemIds != null && checkitemIds.length >0){
            for (Integer checkitemId : checkitemIds) {
                //创建一个map集合存放关联关系
                HashMap<String, Integer> map = new HashMap<>();
                map.put("checkGroupId",checkGroupId);
                map.put("checkItemId",checkitemId);
                checkGroupDao.setCheckGroupIdAndCheckItem(map);
            }

        }
    }
}
