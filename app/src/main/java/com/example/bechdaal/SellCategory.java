package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SellCategory extends AppCompatActivity {

    LinearLayout car,bike,mobile,laptop,property,furniture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_category);

        car = findViewById(R.id.car);
        bike = findViewById(R.id.bike);
        mobile = findViewById(R.id.mobile);
        laptop = findViewById(R.id.laptop);
        property = findViewById(R.id.property);
        furniture = findViewById(R.id.furniture);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellCategory.this,CarSell.class);
                startActivity(i);
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellCategory.this,BikeSell.class);
                startActivity(i);
            }
        });

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellCategory.this,MobileSell.class);
                startActivity(i);
            }
        });

        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellCategory.this,LaptopSell.class);
                startActivity(i);
            }
        });

        property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SellCategory.this, "Property Cliked", Toast.LENGTH_SHORT).show();
            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SellCategory.this,FurnitureSell.class);
                startActivity(i);
            }
        });

    }

}