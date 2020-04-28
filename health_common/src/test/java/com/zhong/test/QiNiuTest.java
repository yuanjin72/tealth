package com.zhong.test;/**
 * @Author yuanjin
 * @Date 2020/4/2616:45
 */

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;



/**
 *@Author yuanjin72@163.com
 *@Date 2020-04-26 16:45
 */
public class QiNiuTest {
    //测试使用七牛提供的SDK上传文件
    @Test
    public void test1(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        String accessKey = "9Tq96a_FpTyG3vDv3aX9k8ip6h8ErI61a_mS7eAP";
        String secretKey = "eCy0c0Z2uBhoneMtEXpokBZN6OV1bKMaO1qZl2i-";
        String bucket = "zhong-image";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "E:\\itheim33\\微信课件\\传智健康2.0\\day04\\资源\\图片资源\\03a36073-a140-4942-9b9b-712cecb144901.jpg";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
    @Test
    public void Test2() {
        //构造一个带指定 Region 对象的配置类
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
//...其他参数参考类注释
        String accessKey = "9Tq96a_FpTyG3vDv3aX9k8ip6h8ErI61a_mS7eAP";
        String secretKey = "eCy0c0Z2uBhoneMtEXpokBZN6OV1bKMaO1qZl2i-";
        String bucket = "image";
        String key = "FuM1Sa5TtL_ekLsdkYWcf5pyjKGu";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
