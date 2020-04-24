package com.zhong.dao;

import com.github.pagehelper.Page;
import com.zhong.pojo.CheckItem;

import java.util.List;

/**
 * @Author yuanjin
 * @Date 2020/4/2219:40
 */
public interface CheckItemDao {
    public void add(CheckItem checkItem);
    public Page<CheckItem> selectByCodition(String queryString);
    public long findCountByCheckItemId(Integer id);
    public void deleteById(Integer id);


    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
}
