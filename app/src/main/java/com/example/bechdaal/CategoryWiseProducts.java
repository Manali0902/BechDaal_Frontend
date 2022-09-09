package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CategoryWiseProducts extends AppCompatActivity {

    private static final int IMG_WIDTH = 180;
    private static final int IMG_HEIGHT = 180;

    JSONObject jsonObject;
    List<Product> productList;
    RecyclerView recyclerViewforProducts;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_products);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        productList = new ArrayList<>();

        recyclerViewforProducts = findViewById(R.id.recycleView);
        recyclerViewforProducts.setHasFixedSize(true);
        recyclerViewforProducts.setLayoutManager(new GridLayoutManager(CategoryWiseProducts.this,2));

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/viewproduct",
                response -> {
                    try {
                        Log.d("jjj","fgff: "+response);
                        jsonObject = new JSONObject(response);
                        Iterator<String> listKEY = jsonObject.keys();
                        do {
                            String newKEY = listKEY.next();
                            JSONObject object= jsonObject.getJSONObject(newKEY);
                            String prodName = object.getString("model");
                            String prodPrice = object.getString("price");
                            String prodImage = object.getString("image");
                            String prodId = object.getString("pId");
                            String fav = object.getString("fav");
                            productList.add(new Product(convertStringToBitmap(prodImage),prodName,prodPrice,prodId,fav));
                        }while (listKEY.hasNext());
                        productAdapter = new ProductAdapter(CategoryWiseProducts.this,productList);
                        recyclerViewforProducts.setAdapter(productAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },error -> {}){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                SessionManagement sessionManagement = new SessionManagement(CategoryWiseProducts.this);
                String email = sessionManagement.getSession();
                param.put("email",email);
                param.put("text",category);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryWiseProducts.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

    private static String resizeBase64Image(String base64image) {
        byte[] encodeByte = android.util.Base64.decode(base64image.getBytes(), android.util.Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);

        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, IMG_WIDTH, IMG_HEIGHT, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return android.util.Base64.encodeToString(b, android.util.Base64.NO_WRAP);
    }

    private static Bitmap convertString64ToImage(String base64String) {
        byte[] decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static Bitmap convertStringToBitmap(String base64String) {
        return convertString64ToImage(resizeBase64Image(base64String));
    }

}