package com.example.zz.ebuy;


public class Shop {
private String name;
private String imageId;
public Shop(String name, String imageId){
    this.name = name;
    this.imageId = imageId;
}
public String getName(){
    return name;
}
public String getImageId(){
    return imageId;
}
}
