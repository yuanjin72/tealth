package com.zhong.service;

import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.pojo.CheckGroup;
import com.zhong.pojo.Setmeal;

import java.util.List;

/**
 * @Author yuanjin
 * @Date 2020/4/2512:59
 */
public interface SetmealService {


    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult queryPage(QueryPageBean queryPageBean);
}
