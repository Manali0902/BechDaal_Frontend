package com.example.bechdaal;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Product {
    Bitmap product_image;
    String product_name,product_price,product_id,fav;

    public Product(Bitmap product_image, String product_name, String product_price, String product_id,String fav) {
        this.product_image = RotateBitmap(product_image,90);
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_id = product_id;
        this.fav = fav;
    }

    public Bitmap getProduct_image() {
        return product_image;
    }

    public void setProduct_image(Bitmap product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
