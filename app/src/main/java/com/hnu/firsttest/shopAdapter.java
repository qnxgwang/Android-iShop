package com.hnu.firsttest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

class ShopAdapter extends ArrayAdapter<Shop> {
    private int newResourceId;
    public ShopAdapter(Context context, int resourceId, List<Shop> shopList){
        super(context, resourceId, shopList);
        newResourceId = resourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Shop shop = getItem(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        convertView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Toast.makeText (getContext (), getItem(position).getName (), Toast.LENGTH_SHORT).show ();

                new Thread(){
                    public void run(){
                        Intent goodsIntent = new Intent(getContext(),GoodsActivity.class);
                        String str = "";
                        try {
                            String param=""+getItem(position).getId();
                            str = httpUtil(param);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        goodsIntent.putExtra("shop",getItem(position).getShop());
                        goodsIntent.putExtra("shopadd",getItem(position).getAdd());
                        goodsIntent.putExtra("detail",str);
                        goodsIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(goodsIntent);
                    }
                }.start();
            }
        });

        ImageView shopImage = convertView.findViewById(R.id.shop_icon);
        TextView clothesName = convertView.findViewById(R.id.goods_name);
        TextView price = convertView.findViewById(R.id.price);
        TextView shopName = convertView.findViewById(R.id.shop_Name);
        clothesName.setText(shop.getName());
        shopImage.setImageResource(shop.getImageId());
        price.setText(shop.getPrice());
        shopName.setText(shop.getShop());
        return convertView;
    }

    /**
     * 后端接口类
     * @param msg
     * @return
     * @throws IOException
     */
    public String httpUtil(String msg) throws IOException {
        String uri = "http://192.168.43.158:8089/goodsDetail?id="+msg;
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
}
