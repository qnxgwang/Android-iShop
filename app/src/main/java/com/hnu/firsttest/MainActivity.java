package com.hnu.firsttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity implements com.baidu.speech.EventListener {
    protected TextView addText;
    protected EditText searchText;
    private EventManager asr;
    private ImageViewImpl2 dynamicview;
    private ImageView buttonView;
    private Context mContext;


    private void start(){
        Toast.makeText(mContext, "请开始说话", Toast.LENGTH_SHORT).show();
        Map<String,Object> params = new LinkedHashMap<>();//传递Map<String,Object>的参数，会将Map自动序列化为json
        String event = null;
        event = SpeechConstant.ASR_START;
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME,false);//回调当前音量
        String json = null;
        json = new JSONObject(params).toString();//demo用json数据来做数据交换的方式
        asr.send(event, json, null, 0, 0);// 初始化EventManager对象,这个实例只能创建一次，就是我们上方创建的asr，此处开始传入
    }
    private void stop(){
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);//此处停止
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initView();
        initPermission();
        asr = EventManagerFactory.create(this,"asr");//注册自己的输出事件类
        asr.registerListener(this);//// 调用 EventListener 中 onEvent方法

    }

    @Override
    protected void onPause() {
        super.onPause();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        asr.unregisterListener(this);//退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
    }
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String resultTxt = null;
//        System.out.println("*****************************");
//        System.out.println("name:"+name);
//        System.out.println("param:"+params);
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)){//识别结果参数
            if (params.contains("\"final_result\"")){//语义结果值
                try {
                    JSONObject json = new JSONObject(params);
//                    System.out.println("*****************************");
//                    System.out.println(json);
                    String result = json.getString("best_result");//取得key的识别结果
                    resultTxt = result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (resultTxt != null){
            searchText.setText(resultTxt);
        }
    }
    private void initView() {
        getSupportActionBar().hide();//隐藏标题栏
        addText = findViewById(R.id.addtext);
        searchText = findViewById(R.id.searchText);
        buttonView = findViewById(R.id.imageButton);
        buttonView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(searchText.getText().toString() != ""){

                        new Thread(){
                            public void run(){
                                String hs="";
                                try {
                                    hs= httpUtil(searchText.getText().toString());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Intent searchIntent = new Intent(mContext,ListActivity.class);
                                searchIntent.putExtra("goods",hs);
                                startActivity(searchIntent);

                            }
                        }.start();
                }
                else{
                    Toast.makeText(mContext, "请手动输入文字或者语音输入", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        dynamicview = findViewById(R.id.dynamicview);
        dynamicview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Paint paint = getPaint();
                int action = event.getAction();
                switch(action){
                    case MotionEvent.ACTION_DOWN:{
                        dynamicview.autoImage(v.getWidth()/2,v.getHeight()/2,paint);
                        start();
                    }break;
                    case MotionEvent.ACTION_UP:{
                        dynamicview.clearImage(v.getWidth()/2,v.getHeight()/2,paint);
                        stop();
                    }break;
                    default:break;
                }

                return true;
            }
        });
    }
    public Paint getPaint(){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        return paint;
    }

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
    /**
     * 动态加载权限信息
     */
    private void initPermission() {
            String permissions[] = {Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm :permissions){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()){
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。

    }

}
