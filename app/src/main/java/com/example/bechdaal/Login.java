package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    EditText email,pass;
    String mailid,password;
    Button login;
    TextView signup, forgotPass;
    ImageView imageView;
    Animation frombottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        forgotPass = findViewById(R.id.forgotPass);
        imageView = findViewById(R.id.imageView);

        mailid = email.getEditableText().toString();
        password = pass.getEditableText().toString();

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        imageView.setAnimation(frombottom);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(Login.this,AdminLogin.class);
                startActivity(i);
                finish();
                return false;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fPass =  new Intent(Login.this,ForgotPass.class);
                startActivity(fPass);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent su = new Intent(Login.this,SignUp.class);
                startActivity(su);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionManagement sessionManagement = new SessionManagement(Login.this);
        String userEmail = sessionManagement.getSession();
        String adminId = sessionManagement.getAdminSession();

        if(!userEmail.equals("none")){
            Intent i = new Intent(Login.this,HomeScreen.class);
            startActivity(i);
            finish();
        }
        else if (!adminId.equals("none")){
            Intent i = new Intent(Login.this,AdminHomeScreen.class);
            startActivity(i);
            finish();
        }

    }

    void login(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/login",response -> {
            Log.d("asggggg", "Connection Made: "+ response);

            if (response.equals("Incorrect username or password")||response.equals("User does not exist")){
                Toast.makeText(Login.this,response, Toast.LENGTH_SHORT).show();
            }
            else{
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("Login Successfull")){

                        Toast.makeText(Login.this,jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        Log.d("abc","response is: "+jsonObject.getString("email")+jsonObject.getString("name")+jsonObject.getString("phone")+ jsonObject.getString("password"));
                        User user = new User(jsonObject.getString("email"),jsonObject.getString("name"),jsonObject.getString("phone"),jsonObject.getString("password"));

                        SessionManagement sessionManagement = new SessionManagement(Login.this);
                        sessionManagement.saveSession(user);

                        Intent i = new Intent(Login.this,HomeScreen.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(Login.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },error -> Log.d("asg", "Login Error : "+ error.getMessage())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("user_email", email.getText().toString());
                param.put("user_password", pass.getText().toString());
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }

}