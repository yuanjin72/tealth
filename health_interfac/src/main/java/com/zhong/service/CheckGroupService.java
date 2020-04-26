package com.zhong.service;

import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.pojo.CheckGroup;
import com.zhong.pojo.CheckItem;

import java.util.List;

/**
 * @Author yuanjin
 * @Date 2020/4/2512:59
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup,Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);
}
