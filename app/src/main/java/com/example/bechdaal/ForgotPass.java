package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgotPass extends AppCompatActivity {

    EditText email;
    Button sendLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email = findViewById(R.id.email);
        sendLink = findViewById(R.id.sendLink);

        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/passwordresetlink",response -> {
                    Toast.makeText(ForgotPass.this, response, Toast.LENGTH_SHORT).show();
                },error -> {
                    Toast.makeText(ForgotPass.this, "Check Your Internet", Toast.LENGTH_SHORT).show();
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("user_email", email.getText().toString());
                        return param;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(ForgotPass.this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);


            }
        });

    }
}