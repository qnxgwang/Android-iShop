package com.hnu.firsttest;

import android.widget.ImageView;

public class Shop {
    private String name;
    private String price;
    private String shop;
    private int imageId;
    private int Id;
    private String add;

    public Shop(String name, int imageId,String shop,String price,int id,String add){
        this.name = name;
        this.imageId = imageId;
        this.price=price;
        this.shop=shop;
        this.Id=id;
        this.add=add;
    }
    public int getId() {
        return Id;
    }
    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getShop(){
        return shop;
    }

    public int getImageId() {
        return imageId;
    }
    public String getAdd(){
        return add;
    }

}
