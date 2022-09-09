package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Dictionary;
import java.util.Hashtable;

public class AdminHomeScreen extends AppCompatActivity {
    Button seeUsers,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);
        seeUsers = findViewById(R.id.seeUsers);
        logout = findViewById(R.id.logout);

        seeUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/showallusers",response -> {
                    Log.d("data","user data response: "+ response);
                    Dictionary dictionary = new Hashtable();
                    dictionary.put("response",response);
                    Intent i = new Intent(AdminHomeScreen.this,SeeAllUsers.class);
                    i.putExtra("data",dictionary.toString());
                    startActivity(i);
                },error -> {}){};
                RequestQueue requestQueue = Volley.newRequestQueue(AdminHomeScreen.this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(AdminHomeScreen.this);
                sessionManagement.removeAdminSession();
                Intent i = new Intent(AdminHomeScreen.this, AdminLogin.class);
                startActivity(i);
                finish();
            }
        });

    }
}