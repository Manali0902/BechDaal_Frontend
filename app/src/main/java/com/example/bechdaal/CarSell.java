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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class CarSell extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner brand,yor,sor,model,owners,km,variant,fuel;
    EditText features,details,price,name, phone;
    Button image,map,next;
    RadioGroup transmission;
    RadioButton trans;
    String brandItem="",yorItem="",modelItem,variantItem,sorItem,ownersItem,kmItem,fuelItem,transmissionItem,featuresItem,detailsItem,
            imageItem,priceItem,mapItem="address",nameItem,phoneItem,emailItem,address;
    private static final int GalleryRequest = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_sell);

        Intent intent = getIntent();
        address = intent.getStringExtra("ADDRESS");

        if (address == null){
            //do nothing
        }
        else {
            mapItem = address;
        }

        brand = findViewById(R.id.brand);
        yor = findViewById(R.id.yor);
        model = findViewById(R.id.model);
        variant = findViewById(R.id.variant);
        sor = findViewById(R.id.sor);
        owners = findViewById(R.id.owners);
        km = findViewById(R.id.km);
        fuel = findViewById(R.id.fuel);
        transmission = findViewById(R.id.transmission);
        features = findViewById(R.id.features);
        details = findViewById(R.id.details);
        image = findViewById(R.id.image);
        price = findViewById(R.id.price);
        map = findViewById(R.id.map);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        next = findViewById(R.id.next);

        yor.setEnabled(false);
        model.setEnabled(false);
        variant.setEnabled(false);
        sor.setEnabled(false);
        owners.setEnabled(false);
        km.setEnabled(false);
        fuel.setEnabled(false);
        features.setEnabled(false);
        details.setEnabled(false);
        image.setEnabled(false);
        price.setEnabled(false);
        map.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        next.setEnabled(false);

        brand.setOnItemSelectedListener(this);
        yor.setOnItemSelectedListener(this);
        model.setOnItemSelectedListener(this);
        variant.setOnItemSelectedListener(this);
        sor.setOnItemSelectedListener(this);
        owners.setOnItemSelectedListener(this);
        km.setOnItemSelectedListener(this);
        fuel.setOnItemSelectedListener(this);

        List<String> Brand = new ArrayList<String>();
        Brand.add("Brand 1");
        Brand.add("Brand 2");
        Brand.add("Brand 3");
        Brand.add("Brand 4");
        Brand.add("Brand 5");
        Brand.add("Brand 6");
        Brand.add("");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Brand){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand.setAdapter(dataAdapter);
        brand.setSelection(dataAdapter.getCount());

        List<String> Yor = new ArrayList<String>();
        Yor.add("2021");
        Yor.add("2020");
        Yor.add("2019");
        Yor.add("2018");
        Yor.add("2017");
        Yor.add("2016");
        Yor.add("2015");
        Yor.add("2014");
        Yor.add("2013");
        Yor.add("2012");
        Yor.add("2011");
        Yor.add("2010");
        Yor.add("2009");
        Yor.add("2008");
        Yor.add("2007");
        Yor.add("2006");
        Yor.add("2005");
        Yor.add("Select Year");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Yor){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yor.setAdapter(dataAdapter1);
        yor.setSelection(dataAdapter1.getCount());

        List<String> Model = new ArrayList<>();
        Model.add("M1");
        Model.add("M2");
        Model.add("M3");
        Model.add("M4");
        Model.add("Select Model");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Model){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        model.setAdapter(dataAdapter2);
        model.setSelection(dataAdapter2.getCount());

        List<String> Variant = new ArrayList<>();
        Variant.add("V1");
        Variant.add("V2");
        Variant.add("V3");
        Variant.add("V4");
        Variant.add("Select Variant");
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Variant){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        variant.setAdapter(dataAdapter3);
        variant.setSelection(dataAdapter3.getCount());

        List<String> Sor = new ArrayList<>();
        Sor.add("State 1");
        Sor.add("State 2");
        Sor.add("State 3");
        Sor.add("State 4");
        Sor.add("State 5");
        Sor.add("Select State of Registration");
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Sor){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sor.setAdapter(dataAdapter4);
        sor.setSelection(dataAdapter4.getCount());

        List<String> Owners = new ArrayList<>();
        Owners.add("First");
        Owners.add("Second");
        Owners.add("Third");
        Owners.add("Fourth");
        Owners.add("More than four");
        Owners.add("Select No. of Owners");
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Owners){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        owners.setAdapter(dataAdapter5);
        owners.setSelection(dataAdapter5.getCount());

        List<String> Km = new ArrayList<>();
        Km.add("0-10,000 KM");
        Km.add("10,001-20,000 KM");
        Km.add("20,001-30,000 KM");
        Km.add("30,001-40,000 KM");
        Km.add("40,001-50,000 KM");
        Km.add("50,001-60,000 KM");
        Km.add("60,001-70,000 KM");
        Km.add("70,001-80,000 KM");
        Km.add("Select Kilometers");
        ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Km){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        km.setAdapter(dataAdapter6);
        km.setSelection(dataAdapter6.getCount());

        List<String> Fuel = new ArrayList<>();
        Fuel.add("CNG & Hybrid");
        Fuel.add("Diesel");
        Fuel.add("Electric");
        Fuel.add("LPG");
        Fuel.add("Petrol");
        Fuel.add("Select Fuel Type");
        ArrayAdapter<String> dataAdapter7 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Fuel){
            @Override
            public int getCount() {
                return super.getCount()-1;
            }
        };
        dataAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel.setAdapter(dataAdapter7);
        fuel.setSelection(dataAdapter7.getCount());



        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brandItem = parent.getItemAtPosition(position).toString();
                if (!brandItem.equals("")){
                    yor.setEnabled(true);
                    List<String> Model = new ArrayList<>();
                    switch (brandItem) {
                        case "Brand 1":
                            Model.add("B1M1");
                            Model.add("B1M2");
                            Model.add("B1M3");
                            Model.add("B1M4");
                            Model.add("Select Model");
                            break;
                        case "Brand 2":
                            Model.add("B2M1");
                            Model.add("B2M2");
                            Model.add("B2M3");
                            Model.add("B2M4");
                            Model.add("Select Model");
                            break;
                        case "Brand 3":
                            Model.add("B3M1");
                            Model.add("B3M2");
                            Model.add("B3M3");
                            Model.add("B3M4");
                            Model.add("Select Model");
                            break;
                        case "Brand 4":
                            Model.add("B4M1");
                            Model.add("B4M2");
                            Model.add("B4M3");
                            Model.add("B4M4");
                            Model.add("Select Model");
                            break;
                        case "Brand 5":
                            Model.add("B5M1");
                            Model.add("B5M2");
                            Model.add("B5M3");
                            Model.add("B5M4");
                            Model.add("Select Model");
                            break;
                        case "Brand 6":
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
                    ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Model){
                        @Override
                        public int getCount() {
                            return super.getCount()-1;
                        }
                    };
                    dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model.setAdapter(dataAdapter2);
                    model.setSelection(dataAdapter2.getCount());
                }
                Log.d("ssss","BrandItem: "+brandItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        yor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yorItem = parent.getItemAtPosition(position).toString();
                if (!yorItem.equals("Select Year")){
                    model.setEnabled(true);
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
                    variant.setEnabled(true);
                    List<String> Variant = new ArrayList<>();
                    switch (modelItem) {
                        case "B1M1":
                            Variant.add("B1M1V1");
                            Variant.add("B1M1V2");
                            Variant.add("B1M1V3");
                            Variant.add("B1M1V4");
                            Variant.add("Select Variant");
                            break;
                        case "B1M2":
                            Variant.add("B1M2V1");
                            Variant.add("B1M2V2");
                            Variant.add("B1M2V3");
                            Variant.add("B1M2V4");
                            Variant.add("Select Variant");
                            break;
                        case "B1M3":
                            Variant.add("B1M3V1");
                            Variant.add("B1M3V2");
                            Variant.add("B1M3V3");
                            Variant.add("B1M3V4");
                            Variant.add("Select Variant");
                            break;
                        case "B1M4":
                            Variant.add("B1M4V1");
                            Variant.add("B1M4V2");
                            Variant.add("B1M4V3");
                            Variant.add("B1M4V4");
                            Variant.add("Select Variant");
                            break;
                        case "B2M1":
                            Variant.add("B2M1V1");
                            Variant.add("B2M1V2");
                            Variant.add("B2M1V3");
                            Variant.add("B2M1V4");
                            Variant.add("Select Variant");
                            break;
                        case "B2M2":
                            Variant.add("B2M2V1");
                            Variant.add("B2M2V2");
                            Variant.add("B2M2V3");
                            Variant.add("B2M2V4");
                            Variant.add("Select Variant");
                            break;
                        case "B2M3":
                            Variant.add("B2M3V1");
                            Variant.add("B2M3V2");
                            Variant.add("B2M3V3");
                            Variant.add("B2M3V4");
                            Variant.add("Select Variant");
                            break;
                        case "B2M4":
                            Variant.add("B2M4V1");
                            Variant.add("B2M4V2");
                            Variant.add("B2M4V3");
                            Variant.add("B2M4V4");
                            Variant.add("Select Variant");
                            break;
                        case "B3M1":
                            Variant.add("B3M1V1");
                            Variant.add("B3M1V1");
                            Variant.add("B3M1V1");
                            Variant.add("B3M1V1");
                            Variant.add("Select Variant");
                            break;
                        case "B3M2":
                            Variant.add("B3M2V1");
                            Variant.add("B3M2V1");
                            Variant.add("B3M2V1");
                            Variant.add("B3M2V1");
                            Variant.add("Select Variant");
                            break;
                        case "B3M3":
                            Variant.add("B3M3V1");
                            Variant.add("B3M3V1");
                            Variant.add("B3M3V1");
                            Variant.add("B3M3V1");
                            Variant.add("Select Variant");
                            break;
                        case "B3M4":
                            Variant.add("B3M4V1");
                            Variant.add("B3M4V1");
                            Variant.add("B3M4V1");
                            Variant.add("B3M4V1");
                            Variant.add("Select Variant");
                            break;
                        case "B4M1":
                            Variant.add("B4M1V1");
                            Variant.add("B4M1V2");
                            Variant.add("B4M1V3");
                            Variant.add("B4M1V4");
                            Variant.add("Select Variant");
                            break;
                        case "B4M2":
                            Variant.add("B4M2V1");
                            Variant.add("B4M2V2");
                            Variant.add("B4M2V3");
                            Variant.add("B4M2V4");
                            Variant.add("Select Variant");
                            break;
                        case "B4M3":
                            Variant.add("B4M3V1");
                            Variant.add("B4M3V2");
                            Variant.add("B4M3V3");
                            Variant.add("B4M3V4");
                            Variant.add("Select Variant");
                            break;
                        case "B4M4":
                            Variant.add("B4M4V1");
                            Variant.add("B4M4V2");
                            Variant.add("B4M4V3");
                            Variant.add("B4M4V4");
                            Variant.add("Select Variant");
                            break;
                        case "B5M1":
                            Variant.add("B5M1V1");
                            Variant.add("B5M1V2");
                            Variant.add("B5M1V3");
                            Variant.add("B5M1V4");
                            Variant.add("Select Variant");
                            break;
                        case "B5M2":
                            Variant.add("B5M2V1");
                            Variant.add("B5M2V2");
                            Variant.add("B5M2V3");
                            Variant.add("B5M2V4");
                            Variant.add("Select Variant");
                            break;
                        case "B5M3":
                            Variant.add("B5M3V1");
                            Variant.add("B5M3V2");
                            Variant.add("B5M3V3");
                            Variant.add("B5M3V4");
                            Variant.add("Select Variant");
                            break;
                        case "B5M4":
                            Variant.add("B5M4V1");
                            Variant.add("B5M4V2");
                            Variant.add("B5M4V3");
                            Variant.add("B5M4V4");
                            Variant.add("Select Variant");
                            break;
                        case "B6M1":
                            Variant.add("B6M1V1");
                            Variant.add("B6M1V2");
                            Variant.add("B6M1V3");
                            Variant.add("B6M1V4");
                            Variant.add("Select Variant");
                            break;
                        case "B6M2":
                            Variant.add("B6M2V1");
                            Variant.add("B6M2V2");
                            Variant.add("B6M2V3");
                            Variant.add("B6M2V4");
                            Variant.add("Select Variant");
                            break;
                        case "B6M3":
                            Variant.add("B6M3V1");
                            Variant.add("B6M3V2");
                            Variant.add("B6M3V3");
                            Variant.add("B6M3V4");
                            Variant.add("Select Variant");
                            break;
                        case "B6M4":
                            Variant.add("B6M4V1");
                            Variant.add("B6M4V2");
                            Variant.add("B6M4V3");
                            Variant.add("B6M4V4");
                            Variant.add("Select Variant");
                            break;
                        default:
                            Variant.add("V1");
                            Variant.add("V2");
                            Variant.add("V3");
                            Variant.add("V4");
                            Variant.add("Select Variant");
                            break;
                    }
                    ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(CarSell.this, android.R.layout.simple_spinner_item, Variant){
                        @Override
                        public int getCount() {
                            return super.getCount()-1;
                        }
                    };
                    dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    variant.setAdapter(dataAdapter3);
                    variant.setSelection(dataAdapter3.getCount());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        variant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                variantItem = parent.getItemAtPosition(position).toString();
                if (!variantItem.equals("Select Variant")){
                    sor.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sorItem = parent.getItemAtPosition(position).toString();
                if (!sorItem.equals("Select State of Registration")){
                    owners.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        owners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ownersItem = parent.getItemAtPosition(position).toString();
                if (!ownersItem.equals("Select No. of Owners")){
                    km.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        km.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kmItem = parent.getItemAtPosition(position).toString();
                if (!kmItem.equals("Select Kilometers")){
                    fuel.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fuelItem = parent.getItemAtPosition(position).toString();
                if (!fuelItem.equals("Select Fuel Type")){
                    features.setEnabled(true);
                    details.setEnabled(true);
                    image.setEnabled(true);
                    price.setEnabled(true);
                    map.setEnabled(true);
                    name.setEnabled(true);
                    phone.setEnabled(true);
                    next.setEnabled(true);
                    SessionManagement sessionManagement = new SessionManagement(CarSell.this);
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
                Intent i = new Intent(CarSell.this,MapActivity.class);
                i.putExtra("activity","CarSell");
                startActivityForResult(i,423);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = transmission.getCheckedRadioButtonId();
                trans = findViewById(selectedId);
                if(selectedId==-1){
                    Toast.makeText(CarSell.this,"Please select Transmission Type", Toast.LENGTH_SHORT).show();
                }
                else{
                    transmissionItem = trans.getText().toString();
                }
                featuresItem = features.getText().toString();
                detailsItem = details.getText().toString();
                //image
                priceItem = price.getText().toString();
                //map location
                nameItem = name.getText().toString();
                phoneItem = phone.getText().toString();
                if (featuresItem.equals("")){
                    Toast.makeText(CarSell.this, "Please Add Features", Toast.LENGTH_SHORT).show();
                }
                else
                    if (detailsItem.equals("")){
                        Toast.makeText(CarSell.this, "Please describe your car", Toast.LENGTH_SHORT).show();
                    }
                    else
                        if (priceItem.equals("")){
                            Toast.makeText(CarSell.this, "Write the estimate price", Toast.LENGTH_SHORT).show();
                        }
                        else
                            if (nameItem.equals("")){
                                Toast.makeText(CarSell.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                            }
                            else
                                if (phoneItem.equals("")){
                                    Toast.makeText(CarSell.this, "Add your contact number", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    addItemToMyProducts();
                                }
            }
        });



    }

    private void addItemToMyProducts() {
        System.out.println(brandItem);
        System.out.println(yorItem);
        System.out.println(modelItem);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/uploadcar",
                response -> {if (response.equals("Saved")){
                    Toast.makeText(this, "Item Saved Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }},error -> Log.d("qq","Error:"+error.getMessage())){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("brand",brandItem);
                param.put("yor",yorItem);
                param.put("model",modelItem);
                param.put("variant",variantItem);
                param.put("sor",sorItem);
                param.put("owners",ownersItem);
                param.put("km",kmItem);
                param.put("fuel",fuelItem);
                param.put("transmission",transmissionItem);
                param.put("features",featuresItem);
                param.put("details",detailsItem);
                param.put("image",imageItem);
                param.put("price",priceItem);
                param.put("location",mapItem);
                param.put("name",nameItem);
                param.put("phone",phoneItem);
                SessionManagement sessionManagement = new SessionManagement(CarSell.this);
                emailItem = sessionManagement.getSession();
                //System.out.println(emailItem);
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
        //below lines for image collection
        if (requestCode == 1 || requestCode == 2) {

            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = null;
                if (requestCode == 1) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else if (requestCode == 2) {
                    Uri selectedImage = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(CarSell.this.getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                bitmap = getResizedBitmap(bitmap, 800);

                System.out.println(bitmap);

                imageItem = BitMapToString(bitmap);

            }
        }
        //below lines for map location
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