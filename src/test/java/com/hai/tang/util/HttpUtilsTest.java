package com.hai.tang.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DisplayName("发送请求测试类")
public class HttpUtilsTest {

    @DisplayName("get请求测试")
    @Test
    public void httpGet() {

      /*Map<String, Object> map = new HashMap<>();
        map.put("positions", 541);
        String callInterfaceResult = HttpUtil.doGet("https://kunpeng.csdn.net/ad/json/list", map);
        System.out.println(callInterfaceResult);*/

        Map<String, Object> map = new HashMap<>();
        map.put("isChange", 0);
        map.put("t", 1692352032459L);
        map.put("pn", 1);
        String callInterfaceResult = HttpUtil.doGet("https://zhidao.baidu.com/question/api/recommend", map);
        System.out.println(callInterfaceResult);
    }

    @DisplayName("post请求测试")
    @Test
    public void httpPost() {
        String  str="{\"invoke_info\":{\"pos_1\":[{}],\"pos_2\":[{}],\"pos_3\":[{}]}}";
        Map<String, Object> map = JSONObject.parseObject(str, new TypeReference<Map<String, Object>>() {});
        String callInterfaceResult = HttpUtil.doPost("https://ug.baidu.com/mcp/pc/pcsearch", map);
        System.out.println(callInterfaceResult);
    }

    @DisplayName("下载文件")
    @Test
    public void downloadFile() {
        String callInterfaceResult = HttpUtil.downloadFile("https://profile-avatar.csdnimg.cn/1e4cb9f8e4e24552947a1774f42656c3_qq_33697094.jpg","D:\\video\\");
        System.out.println(callInterfaceResult);
    }
    @DisplayName("上传多文件并携带多参数")
    @Test
    public void upLoadFiles(List<MultipartFile> files) {
        Map<String, String> params = new HashMap<>();
        params.put("name", "张三");
        params.put("age", "18");
        //调用三方receiveFiles接口，并把多个文件和参数传过去，这里的 receiveFiles 接口是使用springboot开发的
        // receiveFiles 接口中的 @RequestParam 里相关的值，要和 HttpUtil 工具类中postUploadFile 方法里添加到的 addBinaryBody 中的一致
        String result = HttpUtil.postUploadFile("http://localhost:8080/apiList/download/receiveFiles", files, params);
        System.out.println(result);
    }

}
