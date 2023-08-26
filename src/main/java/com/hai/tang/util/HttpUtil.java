package com.hai.tang.util;


import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 用于发送http请求
 */
public class HttpUtil {

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
    private static String chaset = "UTF-8";    // 默认编码

    /**
     * 发送 GET 请求（HTTP），无参数
     *
     * @param url
     * @return
     */
    public static String doGet(String url) {
        if (StringUtils.isBlank(url)) {
            return "url不能为空";
        }
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        // 设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
        String result = "";

        try (
                CloseableHttpClient httpclient = HttpClients.createDefault();// 创建Httpclient对象
                CloseableHttpResponse response = httpclient.execute(httpGet);// 执行请求
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), chaset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送 GET 请求（HTTP），有参数
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        if (params != null) {
            StringBuffer extraUrl = new StringBuffer();
            int temp = 0;
            for (String key : params.keySet()) {
                if (temp == 0) {
                    extraUrl.append("?");
                } else {
                    extraUrl.append("&");
                }
                extraUrl.append(key).append("=").append(params.get(key));
                temp++;
            }
            url += extraUrl;
        }
        return doGet(url);
    }


    /**
     * 发送 GET 请求（HTTP），获取跳转的最终url链接返回
     *
     * @param url
     * @return
     */
    public static String doGetJumpLink(String url) {
        if (StringUtils.isBlank(url)) {
            return "url不能为空";
        }
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        HttpContext context = new BasicHttpContext();
        String currentUrl = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault();// 创建Httpclient对象
             CloseableHttpResponse response = httpclient.execute(httpGet, context);// 执行请求
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return currentUrl = "HttpGet方式请求失败！状态码:" + statusCode;

            }
            HttpUriRequest currentReq = (HttpUriRequest) context.getAttribute(
                    HttpCoreContext.HTTP_REQUEST);
            HttpHost currentHost = (HttpHost) context.getAttribute(
                    HttpCoreContext.HTTP_TARGET_HOST);
            currentUrl = (currentReq.getURI().isAbsolute()) ? currentReq.getURI().toString() : (currentHost.toURI() + currentReq.getURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUrl;
    }


    /**
     * 发送 POST 请求（HTTP），无参数
     *
     * @param url
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 发送 POST 请求（HTTP），有参数
     *
     * @param url
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json");
        if (params != null) {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.create("application/json", "utf-8")));
        }
        String result = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault();// 创建Httpclient对象
             CloseableHttpResponse response = httpclient.execute(httpPost);// 执行请求
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), chaset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String doPost1(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(5000)// 设置连接请求超时时间
                .setSocketTimeout(5000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }
            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 发送 POST 请求（HTTP），有参数和cookie
     *
     * @param url
     * @param params 参数
     * @param cookie 例如：LBSESSION=NGEzODUyNjUtYjZkYy00ZmNkLWJhZTAtMDBmYWNjYzQzZjBi
     * @return
     */
    public static String doPost(String url, Map<String, Object> params, String cookie) {
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json");
        if (params != null) {
            httpPost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.create("application/json", "utf-8")));
        }
        if (cookie != null) {
            httpPost.addHeader("Cookie", cookie);
        }
        String result = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault();// 创建Httpclient对象
             CloseableHttpResponse response = httpclient.execute(httpPost);// 执行请求
        ) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), chaset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post调用三方请求上传单个文件
     *
     * @param url
     * @param file
     * @return
     */
    public static String postUploadFile(String url, MultipartFile file) {
        HttpEntity httpEntity = null;
        String fileName = file.getOriginalFilename();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
        try {
            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
        } catch (IOException e) {
            e.printStackTrace();
        }

        httpEntity = builder.build();
        httpPost.setEntity(httpEntity);
        String result = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault();// 创建Httpclient对象
             CloseableHttpResponse response = httpclient.execute(httpPost);// 执行请求
        ) {
            System.out.println("httpPost:" + httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), chaset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post调用三方请求上传多个文件
     *
     * @param url
     * @param files 多个文件
     * @return params 多个参数
     */
    public static String postUploadFile(String url, List<MultipartFile> files, Map<String, String> params) {
        HttpEntity httpEntity = null;
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
        try {
            if (null != files) {
                for (MultipartFile file : files) {
                    String fileName = file.getOriginalFilename();
                    builder.addBinaryBody("files", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != params) {
            for (Map.Entry<String, String> m : params.entrySet()) {
                builder.addPart(m.getKey(), new StringBody(m.getValue(), ContentType.create("text/plain", StandardCharsets.UTF_8)));
            }
        }

        httpEntity = builder.build();
        httpPost.setEntity(httpEntity);
        String result = "";
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpPost);
        ) {
            System.out.println("httpPost:" + httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), chaset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 通过url下载文件
     *
     * @param url      如：https://profile-avatar.csdnimg.cn/1e4cb9f8e4e24552947a1774f42656c3_qq_33697094.jpg
     * @param filepath 下载到本地的路径，如：D:\video\
     * @return
     */
    public static String downloadFile(String url, String filepath) {
        String filename = "";
        if (!url.contains(".")) {
            return "url中不含有文件后缀！";
        }
        String[] split = url.split("\\.");
        try {
            //获取文件流
            HttpClient client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();

            //filepath不是斜杠结束的在末尾添加斜杠
            if (filepath.length() - 1 != filepath.lastIndexOf("\\")) {
                filepath += "\\";
            }

            //如果没有文件夹，则创建
            File file = new File(filepath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            //获取文件夹下的文件数量，文件数量加一作为要下载的文件名
            int fileCount = 0;
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                if (list[i].isFile()) {
                    fileCount++;
                }
            }
            fileCount += 1;

            //如果文件不存在则创建文件
            filename = filepath + fileCount + "." + split[split.length - 1];
            File file_name = new File(filename);
            if (!file_name.exists()) {
                //创建文件
                file_name.createNewFile();
            }

            //视频流写入文件
            FileOutputStream fileout = new FileOutputStream(file_name);
            BufferedInputStream buf = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int ch = 0;
            while ((ch = buf.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "下载成功，文件名：" + filename;
    }
}
