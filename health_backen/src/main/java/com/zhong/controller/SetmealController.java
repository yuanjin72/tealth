package com.zhong.controller;/**
 * @Author yuanjin
 * @Date 2020/4/2711:44
 */

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhong.constant.MessageConstant;
import com.zhong.constant.RedisConstant;
import com.zhong.entity.PageResult;
import com.zhong.entity.QueryPageBean;
import com.zhong.entity.Result;
import com.zhong.pojo.Setmeal;
import com.zhong.service.SetmealService;
import com.zhong.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-27 11:44
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    //使用jedispool
    @Autowired
    private JedisPool jedisPool;

    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
        String originalFilename = imgFile.getOriginalFilename(); //原始文件名
        int index = originalFilename.lastIndexOf(".");
        String extention = originalFilename.substring(index - 1); //.jpg
        String fileName = UUID.randomUUID().toString() + extention;
        try {
            //将文件上传到服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }
    @Reference
    private SetmealService setmealService;
    //新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
       return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.queryPage(queryPageBean);
    }
}
