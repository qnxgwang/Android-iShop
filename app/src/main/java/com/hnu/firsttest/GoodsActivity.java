package com.hnu.firsttest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GoodsActivity extends AppCompatActivity {

    private ImageView Max_Image;
    private ImageView[] simple_Image = new ImageView[10];
    protected int []image = {R.drawable.welcomeeight,R.drawable.welcomeeighteen,R.drawable.welcomeeleven};
    private int num = 1;

    private TextView price ;
    private TextView goods_name ;
    private TextView shop_name ;
    private TextView shop_address ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        getSupportActionBar().hide();//隐藏标题栏
        ImageView returnImage = findViewById(R.id.returnButton);
        initText();
        initImage();
        returnImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                finish();
                return true;
            }
        });


    }

    public void turn_Image(){
        Max_Image.setBackgroundDrawable(getResources().getDrawable(image[num++]));
        Max_Image.invalidate();
        if(num>=3){
            num = 1;
        }
    }

    public void initImage(){
        simple_Image[1] = findViewById(R.id.color1);
        simple_Image[2] = findViewById(R.id.color2);
        for(int i=1;i<=2;i++){
            simple_Image[i].setImageDrawable(getResources().getDrawable(image[i]));
        }
        Max_Image = findViewById(R.id.Max_View);
        Max_Image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_UP:turn_Image();break;
                    default:break;
                }
                return true;
            }
        });
    }
    public void initText(){
        price = findViewById(R.id.goods_Price);
        goods_name = findViewById(R.id.detailMsg);
        shop_name = findViewById(R.id.shop_Name);
        shop_address = findViewById(R.id.shop_Adress);
        Intent intent = getIntent();
        String goods = intent.getStringExtra("detail");
        try {
            JSONObject object = new JSONObject(goods);
            price.setText(object.getString("price"));
            goods_name.setText(object.getString("goods_Name"));
            shop_name.setText(intent.getStringExtra("shop"));
            shop_address.setText(intent.getStringExtra("shopadd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
