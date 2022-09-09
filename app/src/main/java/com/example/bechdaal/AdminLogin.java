package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminLogin extends AppCompatActivity {
    ImageView imageView;
    EditText adminId,pass;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        imageView = findViewById(R.id.imageView);
        adminId = findViewById(R.id.adminId);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(AdminLogin.this,Login.class);
                startActivity(i);
                finish();
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/adminlogin",response -> {
                    if(response.equals("Invalid Admin ID or password") || response.equals("Admin does not exist")){
                        Toast.makeText(AdminLogin.this, response, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("message").equals("Login Successfull")){

                                Toast.makeText(AdminLogin.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                Log.d("abc","response is: "+jsonObject.getString("adminid")+jsonObject.getString("email")+jsonObject.getString("name")+jsonObject.getString("phone")+ jsonObject.getString("password"));
                                Admin admin = new Admin(jsonObject.getString("adminid"),jsonObject.getString("email"),jsonObject.getString("name"),jsonObject.getString("phone"),jsonObject.getString("password"));

                                SessionManagement sessionManagement = new SessionManagement(AdminLogin.this);
                                sessionManagement.saveAdminSession(admin);

                                Intent i = new Intent(AdminLogin.this,AdminHomeScreen.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                Toast.makeText(AdminLogin.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },error -> {})
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("admin_id", adminId.getText().toString());
                        param.put("secret_code", pass.getText().toString());
                        return param;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AdminLogin.this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);
            }
        });

    }
}