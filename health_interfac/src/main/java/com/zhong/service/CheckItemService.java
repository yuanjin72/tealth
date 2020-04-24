package com.zhong.service;

import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.pojo.CheckItem;

/**
 * @Author yuanjin
 * @Date 2020/4/2219:13
 */
//
public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult pageQuery(QueryPageBean queryPageBean);
    public void deleteById(Integer id);

   public void edit(CheckItem checkItem);
    public CheckItem findById(Integer id);
}
