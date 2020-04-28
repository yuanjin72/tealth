package com.zhong.dao;

import com.github.pagehelper.Page;
import com.zhong.pojo.CheckGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yuanjin
 * @Date 2020/4/2513:19
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupIdAndCheckItem(Map map);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssoication(Integer id);

    List<CheckGroup> findAll();
}
