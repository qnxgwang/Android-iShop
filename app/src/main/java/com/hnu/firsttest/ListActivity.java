package com.hnu.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private ListView myCityListView;
    private ImageView returnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();//隐藏标题栏
        myCityListView = findViewById (R.id.list_content);
        returnView = findViewById(R.id.returnButton);
        returnView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initData() throws JSONException {
        Intent it = getIntent();
        String result = it.getStringExtra("goods");
        System.out.println();
        JSONArray array = new JSONArray(result);
        int length = array.length();
        List<Shop> shop= new ArrayList<>();
        for(int i=0;i<length;i++){
            JSONObject goods = array.getJSONObject(i);
            String goods_name = goods.getString("goodsName");
            String shop_name = goods.getString("shopName");
            String price = goods.getString("price");
            String add = goods.getString("address");
            int id = goods.getInt("goodsId");
            shop.add(new Shop(goods_name,R.drawable.ishop_search,shop_name,price,id,add));
        }
        ShopAdapter shopAdapter = new ShopAdapter(ListActivity.this,R.layout.activity_list,shop);
        myCityListView.setAdapter(shopAdapter);
    }

}
