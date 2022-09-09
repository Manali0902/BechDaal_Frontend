package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnitureSell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner furnitureType;
    EditText features,details,price,name, phone;
    Button image,map,next;
    String furnitureTypeItem,featuresItem,detailsItem,imageItem,priceItem,mapItem="address",
            nameItem,phoneItem,emailItem,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_sell);

        Intent intent = getIntent();
        address = intent.getStringExtra("ADDRESS");

        if (address == null){
            //do nothing
        }
        else {
            mapItem = address;
        }

        furnitureType = findViewById(R.id.furnitureType);
        features = findViewById(R.id.features);
        details = findViewById(R.id.details);
        image = findViewById(R.id.image);
        price = findViewById(R.id.price);
        map = findViewById(R.id.map);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        next = findViewById(R.id.next);

        features.setEnabled(false);
        details.setEnabled(false);
        image.setEnabled(false);
        price.setEnabled(false);
        map.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        next.setEnabled(false);

        furnitureType.setOnItemSelectedListener(FurnitureSell.this);

        List<String> FurnitureType = new ArrayList<>();
        FurnitureType.add("Sofa & Dinning");
        FurnitureType.add("Beds & Wardrobes");
        FurnitureType.add("Home Decor & Garden");
        FurnitureType.add("Kids Furniture");
        FurnitureType.add("Select Furniture Type");

        ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, FurnitureType){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        furnitureType.setAdapter(dataAdapter0);
        furnitureType.setSelection(dataAdapter0.getCount());

        furnitureType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                furnitureTypeItem = parent.getItemAtPosition(position).toString();
                if (!furnitureTypeItem.equals("Select Furniture Type")){
                    features.setEnabled(true);
                    details.setEnabled(true);
                    image.setEnabled(true);
                    price.setEnabled(true);
                    map.setEnabled(true);
                    name.setEnabled(true);
                    phone.setEnabled(true);
                    next.setEnabled(true);
                    SessionManagement sessionManagement = new SessionManagement(FurnitureSell.this);
                    name.setText(sessionManagement.getSessionName());
                    phone.setText(sessionManagement.getSessionPhone());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            });

        map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(FurnitureSell.this,MapActivity.class);
                    i.putExtra("activity","CarSell");
                    startActivityForResult(i,423);
                }
            });


        next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    featuresItem = features.getText().toString();
                    detailsItem = details.getText().toString();
                    //image
                    priceItem = price.getText().toString();
                    //map location
                    nameItem = name.getText().toString();
                    phoneItem = phone.getText().toString();
                    if (featuresItem.equals("")){
                        Toast.makeText(FurnitureSell.this, "Please Add Features", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (detailsItem.equals("")){
                        Toast.makeText(FurnitureSell.this, "Please describe your car", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (priceItem.equals("")){
                        Toast.makeText(FurnitureSell.this, "Write the estimate price", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (nameItem.equals("")){
                        Toast.makeText(FurnitureSell.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if (phoneItem.equals("")){
                        Toast.makeText(FurnitureSell.this, "Add your contact number", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addItemToMyProducts();
                    }
                }
            });
        }

            private void addItemToMyProducts() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/uploadfurniture",
                        response -> {if (response.equals("Saved")){
                            Toast.makeText(this, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }},error -> Log.d("qq","Error:"+error.getMessage())){
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("furniture_type", furnitureTypeItem);
                        param.put("features",featuresItem);
                        param.put("details",detailsItem);
                        param.put("image",imageItem);
                        param.put("price",priceItem);
                        param.put("location",mapItem);
                        param.put("name",nameItem);
                        param.put("phone",phoneItem);
                        SessionManagement sessionManagement = new SessionManagement(FurnitureSell.this);
                        emailItem = sessionManagement.getSession();
                        param.put("email",emailItem);
                        return param;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == 1 || requestCode == 2) {

                    if (resultCode == Activity.RESULT_OK) {
                        Bitmap bitmap = null;
                        if (requestCode == 1) {
                            bitmap = (Bitmap) data.getExtras().get("data");
                        } else if (requestCode == 2) {
                            Uri selectedImage = data.getData();

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(FurnitureSell.this.getContentResolver(), selectedImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        bitmap = getResizedBitmap(bitmap, 800);

                        imageItem = BitMapToString(bitmap);

                    }
                }
                else if (requestCode == 423){
                    if (resultCode == Activity.RESULT_OK){
                        mapItem = data.getStringExtra(MapActivity.ADDRESS);
                        Toast.makeText(this, mapItem, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
                int width = image.getWidth();
                int height = image.getHeight();

                float bitmapRatio = (float)width / (float) height;
                if (bitmapRatio > 1) {
                    width = maxSize;
                    height = (int) (width / bitmapRatio);
                } else {
                    height = maxSize;
                    width = (int) (height * bitmapRatio);
                }
                return Bitmap.createScaledBitmap(image, width, height, true);
            }

            private String BitMapToString(Bitmap userImage1) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
                byte[] b = baos.toByteArray();
                String Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
                return Document_img1;
            }





}