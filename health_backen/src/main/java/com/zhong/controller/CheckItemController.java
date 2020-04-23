package com.zhong.controller;/**

 * @Author yuanjin
 * @Date 2020/4/2218:06
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.constant.MessageConstant;
import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.entity.Result;
import com.zhong.pojo.CheckItem;
import com.zhong.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-22 18:06
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference //查找服务
    private CheckItemService checkItemService;
    //新增基础项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return  new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //检查项分页查询
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);

        return  pageResult;
    }
    //删除检查项
    @RequestMapping("/delete")
    public Result Delete(@RequestBody Integer id ) {
       try {
           checkItemService.deleteById(id);
       }catch (Exception e){
           e.printStackTrace();
           return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
       }

        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
