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

public class MobileSell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mobileType,brand,model,year;
    EditText features,details,price,name, phone;
    Button image,map,next;
    String mobileTypeItem,brandItem="",yearItem="",modelItem,featuresItem,detailsItem,imageItem,priceItem,mapItem="address",
            nameItem,phoneItem,emailItem,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_sell);
        Intent intent = getIntent();
        address = intent.getStringExtra("ADDRESS");

        if (address == null){
            //do nothing
        }
        else {
            mapItem = address;
        }

        mobileType = findViewById(R.id.mobileType);
        brand = findViewById(R.id.brand);
        model = findViewById(R.id.model);
        year = findViewById(R.id.year);
        features = findViewById(R.id.features);
        details = findViewById(R.id.details);
        image = findViewById(R.id.image);
        price = findViewById(R.id.price);
        map = findViewById(R.id.map);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        next = findViewById(R.id.next);

        brand.setEnabled(false);
        model.setEnabled(false);
        year.setEnabled(false);
        features.setEnabled(false);
        details.setEnabled(false);
        image.setEnabled(false);
        price.setEnabled(false);
        map.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        next.setEnabled(false);

        mobileType.setOnItemSelectedListener(MobileSell.this);
        brand.setOnItemSelectedListener(this);
        model.setOnItemSelectedListener(this);

        List<String> MobileType = new ArrayList<>();
        MobileType.add("Mobile");
        MobileType.add("Tablet");
        MobileType.add("Accessories");
        MobileType.add("Select Mobile Type");

        ArrayAdapter<String> dataAdapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MobileType){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mobileType.setAdapter(dataAdapter0);
        mobileType.setSelection(dataAdapter0.getCount());

        List<String> Brand = new ArrayList<String>();
        Brand.add("Brand 1");
        Brand.add("Brand 2");
        Brand.add("Brand 3");
        Brand.add("Brand 4");
        Brand.add("Brand 5");
        Brand.add("Brand 6");
        Brand.add("Select Brand");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Brand){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand.setAdapter(dataAdapter);
        brand.setSelection(dataAdapter.getCount());

        List<String> Model = new ArrayList<>();
        Model.add("M1");
        Model.add("M2");
        Model.add("M3");
        Model.add("M4");
        Model.add("Select Model");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Model){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        model.setAdapter(dataAdapter2);
        model.setSelection(dataAdapter2.getCount());


        List<String> Year = new ArrayList<String>();
        Year.add("1");
        Year.add("2");
        Year.add("3");
        Year.add("4");
        Year.add("5");
        Year.add("6");
        Year.add("Select Year");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Year){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(dataAdapter1);
        year.setSelection(dataAdapter1.getCount());

        mobileType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mobileTypeItem = parent.getItemAtPosition(position).toString();
                if (!mobileTypeItem.equals("Select Mobile Type")){
                    brand.setEnabled(true);
                    List<String> Brand = new ArrayList<String>();
                    switch (mobileTypeItem){
                        case "Mobile":
                            Brand.add("M Brand 1");
                            Brand.add("M Brand 2");
                            Brand.add("M Brand 3");
                            Brand.add("M Brand 4");
                            Brand.add("M Brand 5");
                            Brand.add("M Brand 6");
                            Brand.add("Select Brand");
                            break;
                        case "Tablet":
                            Brand.add("T Brand 1");
                            Brand.add("T Brand 2");
                            Brand.add("T Brand 3");
                            Brand.add("T Brand 4");
                            Brand.add("T Brand 5");
                            Brand.add("T Brand 6");
                            Brand.add("Select Brand");
                            break;
                        default:
                            Brand.add("Brand 1");
                            Brand.add("Brand 2");
                            Brand.add("Brand 3");
                            Brand.add("Brand 4");
                            Brand.add("Brand 5");
                            Brand.add("Brand 6");
                            Brand.add("Select Brand");
                            break;
                    }
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MobileSell.this, android.R.layout.simple_spinner_item, Brand){
                        @Override
                        public int getCount() {
                            return super.getCount()-1;
                        }
                    };
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    brand.setAdapter(dataAdapter2);
                    brand.setSelection(dataAdapter2.getCount());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandItem = parent.getItemAtPosition(position).toString();
                if (!brandItem.equals("Select Brand")){
                    model.setEnabled(true);
                    List<String> Model = new ArrayList<>();
                    switch (brandItem) {
                        case "T Brand 1":
                            Model.add("B1M1");
                            Model.add("B1M2");
                            Model.add("B1M3");
                            Model.add("B1M4");
                            Model.add("Select Model");
                            break;
                        case "T Brand 2":
                            Model.add("B2M1");
                            Model.add("B2M2");
                            Model.add("B2M3");
                            Model.add("B2M4");
                            Model.add("Select Model");
                            break;
                        case "T Brand 3":
                            Model.add("B3M1");
                            Model.add("B3M2");
                            Model.add("B3M3");
                            Model.add("B3M4");
                            Model.add("Select Model");
                            break;
                        case "T Brand 4":
                            Model.add("B4M1");
                            Model.add("B4M2");
                            Model.add("B4M3");
                            Model.add("B4M4");
                            Model.add("Select Model");
                            break;
                        case "T Brand 5":
                            Model.add("B5M1");
                            Model.add("B5M2");
                            Model.add("B5M3");
                            Model.add("B5M4");
                            Model.add("Select Model");
                            break;
                        case "T Brand 6":
                            Model.add("B6M1");
                            Model.add("B6M2");
                            Model.add("B6M3");
                            Model.add("B6M4");
                            Model.add("Select Model");
                            break;
                        case "M Brand 1":
                            Model.add("B1M1");
                            Model.add("B1M2");
                            Model.add("B1M3");
                            Model.add("B1M4");
                            Model.add("Select Model");
                            break;
                        case "M Brand 2":
                            Model.add("B2M1");
                            Model.add("B2M2");
                            Model.add("B2M3");
                            Model.add("B2M4");
                            Model.add("Select Model");
                            break;
                        case "M Brand 3":
                            Model.add("B3M1");
                            Model.add("B3M2");
                            Model.add("B3M3");
                            Model.add("B3M4");
                            Model.add("Select Model");
                            break;
                        case "M Brand 4":
                            Model.add("B4M1");
                            Model.add("B4M2");
                            Model.add("B4M3");
                            Model.add("B4M4");
                            Model.add("Select Model");
                            break;
                        case "M Brand 5":
                            Model.add("B5M1");
                            Model.add("B5M2");
                            Model.add("B5M3");
                            Model.add("B5M4");
                            Model.add("Select Model");
                            break;
                        case "M Brand 6":
                            Model.add("B6M1");
                            Model.add("B6M2");
                            Model.add("B6M3");
                            Model.add("B6M4");
                            Model.add("Select Model");
                            break;
                        default:
                            Model.add("M1");
                            Model.add("M2");
                            Model.add("M3");
                            Model.add("M4");
                            Model.add("Select Model");
                            break;
                    }
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(MobileSell.this, android.R.layout.simple_spinner_item, Model){
                        @Override
                        public int getCount() {
                            return super.getCount()-1;
                        }
                    };
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(dataAdapter2);
                    model.setSelection(dataAdapter2.getCount());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modelItem = parent.getItemAtPosition(position).toString();
                if (!modelItem.equals("Select Model")){
                    year.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearItem = parent.getItemAtPosition(position).toString();
                if (!yearItem.equals("Select Year")){
                    features.setEnabled(true);
                    details.setEnabled(true);
                    image.setEnabled(true);
                    price.setEnabled(true);
                    map.setEnabled(true);
                    name.setEnabled(true);
                    phone.setEnabled(true);
                    next.setEnabled(true);
                    SessionManagement sessionManagement = new SessionManagement(MobileSell.this);
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
                Intent i = new Intent(MobileSell.this,MapActivity.class);
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
                    Toast.makeText(MobileSell.this, "Please Add Features", Toast.LENGTH_SHORT).show();
                }
                else
                if (detailsItem.equals("")){
                    Toast.makeText(MobileSell.this, "Please describe your car", Toast.LENGTH_SHORT).show();
                }
                else
                if (priceItem.equals("")){
                    Toast.makeText(MobileSell.this, "Write the estimate price", Toast.LENGTH_SHORT).show();
                }
                else
                if (nameItem.equals("")){
                    Toast.makeText(MobileSell.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else
                if (phoneItem.equals("")){
                    Toast.makeText(MobileSell.this, "Add your contact number", Toast.LENGTH_SHORT).show();
                }
                else {
                    addItemToMyProducts();
                }
            }
        });
    }

    private void addItemToMyProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/uploadmobile",
                response -> {if (response.equals("Saved")){
                    Toast.makeText(this, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }},error -> Log.d("qq","Error:"+error.getMessage())){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("mobile_type", mobileTypeItem);
                param.put("brand",brandItem);
                param.put("model",modelItem);
                param.put("year",yearItem);
                param.put("features",featuresItem);
                param.put("details",detailsItem);
                param.put("image",imageItem);
                param.put("price",priceItem);
                param.put("location",mapItem);
                param.put("name",nameItem);
                param.put("phone",phoneItem);
                SessionManagement sessionManagement = new SessionManagement(MobileSell.this);
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
                        bitmap = MediaStore.Images.Media.getBitmap(MobileSell.this.getContentResolver(), selectedImage);
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