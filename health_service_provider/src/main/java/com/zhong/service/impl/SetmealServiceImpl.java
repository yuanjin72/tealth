package com.zhong.service.impl;/**
 * @Author yuanjin
 * @Date 2020/4/2513:19
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhong.constant.RedisConstant;
import com.zhong.dao.CheckGroupDao;
import com.zhong.dao.SetmealDao;
import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.pojo.CheckGroup;
import com.zhong.pojo.Setmeal;
import com.zhong.service.CheckGroupService;
import com.zhong.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-25 13:19
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    //新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId,checkgroupIds);
        //将图片存放在Redisz中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    //分页查询
    public PageResult queryPage(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        //调用分页助手
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //设置套餐和检查组的关系
    public void setSetmealAndCheckgroup(Integer setmealId,Integer[] checkgroupIds){
      if(checkgroupIds != null && checkgroupIds.length > 0){
          Map<String,Integer> map = null;
          for (Integer checkgroupId : checkgroupIds) {
              map = new HashMap<>();
              map.put("setmealId",setmealId);
              map.put("checkgroupId",checkgroupId);
              setmealDao.setSetmealAndCheckgroup(map);
          }
      }
    }
}
