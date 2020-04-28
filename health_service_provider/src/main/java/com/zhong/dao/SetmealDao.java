package com.zhong.dao;

import com.github.pagehelper.Page;
import com.zhong.pojo.CheckGroup;
import com.zhong.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @Author yuanjin
 * @Date 2020/4/2513:19
 */
public interface SetmealDao {

    void add(Setmeal setmeal);

    void setSetmealAndCheckgroup(Map map);

    Page<Setmeal> findByCondition(String queryString);
}
