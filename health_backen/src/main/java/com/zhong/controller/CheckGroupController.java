package com.zhong.controller;/**
 * @Author yuanjin
 * @Date 2020/4/2512:48
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.constant.MessageConstant;
import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.entity.Result;
import com.zhong.pojo.CheckGroup;
import com.zhong.pojo.CheckItem;
import com.zhong.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-25 12:48
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
        public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
            try {
                checkGroupService.add(checkGroup,checkitemIds);
            }catch (Exception e){
                e.printStackTrace();
                return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
            }
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //检查组分页查询
    @RequestMapping("/findpage")
    public PageResult findpage(@RequestBody QueryPageBean queryPageBean){

        return  checkGroupService.findPage(queryPageBean);
    }
    //根据Id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);//查询失败
        }
    }
    //根据检查组Id查询检查组包含的检查项的多个Id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id) {
      try {
          List<Integer> checkItems =  checkGroupService.findCheckItemIdsByCheckGroupId(id);
          return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);//查询成功
      }catch (Exception e){
          e.printStackTrace();
          return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
      }
    }
    //根据检查组Id重新编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        try {
            checkGroupService.edit(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return  new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
