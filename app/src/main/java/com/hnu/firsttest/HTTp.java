package com.hnu.firsttest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 测试接口类
 */

public class HTTp {

    public static String httpUtil(String msg) throws IOException {
        String version = "";
        version= URLEncoder.encode(msg,"UTF-8");
        String uri = "http://192.168.43.158:8089/shopAndGoods/detail?originalText="+version;
        URL url = new URL(uri);
        //2. HttpURLConnection
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        //3. set(GET)
        conn.setRequestMethod("GET");

        //4. getInputStream

        InputStream is = conn.getInputStream();
        //5. 解析is，获取responseText，这里用缓冲字符流
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while((line=reader.readLine()) != null){
            sb.append(line);
        }
        //获取响应文本
        String responseText = sb.toString();
        return responseText;
    }
    public static void main(String [] args) throws IOException {
        System.out.println(httpUtil("我想要两件橘红的衬衫"));
    }
}
