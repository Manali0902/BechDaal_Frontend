package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

public class SignUp extends AppCompatActivity {

    EditText email,name,phone,pass,cPass;
    Button signup;
    TextView login;
    ImageView imageView;
    Animation frombottom;
//
//    private TextWatcher mTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            // check Fields For Empty Values
//            checkFieldsForEmptyValues();
//        }
//    };
//
//    void checkFieldsForEmptyValues(){
//        if (phone.getText().toString().length() == 10) {
//            sendOTP.setEnabled(true);
//            otp.setEnabled(true);
//            pass.setEnabled(true);
//            cPass.setEnabled(true);
//            signup.setEnabled(true);
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
//        sendOTP = findViewById(R.id.sendOTP);
//        otp = findViewById(R.id.otp);
        pass = findViewById(R.id.pass);
        cPass = findViewById(R.id.cPass);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        imageView = findViewById(R.id.imageView);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        imageView.setAnimation(frombottom);

//        sendOTP.setEnabled(false);
//        otp.setEnabled(false);
//        pass.setEnabled(false);
//        cPass.setEnabled(false);
//        signup.setEnabled(false);

//        phone.addTextChangedListener(mTextWatcher);
//
//        checkFieldsForEmptyValues();
//
//        sendOTP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String number = phone.getEditableText().toString();
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.29.17:8000/sendotp",
//                        response -> {},
//                        error -> {})
//                {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String, String> param = new HashMap<>();
//                        param.put("phone number",number);
//                        return param;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
//                requestQueue.getCache().clear();
//                requestQueue.add(stringRequest);
//            }
//        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getEditableText().toString().isEmpty()){
                    email.setError("Enter mail id");
                }
                else if(!(email.getEditableText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"))) {
                    email.setError("Invalid Email Address");
                }
                else if(name.getEditableText().toString().isEmpty()){
                    name.setError("Enter Name");
                }
                else if(phone.getEditableText().toString().isEmpty()){
                    phone.setError("Enter Phone Number");
                }
                else if(!(phone.getEditableText().toString().length()==10)){
                    phone.setError("Enter Valid Phone Number");
                }
                else if(pass.getEditableText().toString().isEmpty()){
                    pass.setError("Enter Password");
                }
                else if(cPass.getEditableText().toString().isEmpty()){
                    cPass.setError("Confirm your Password");
                }
                else if(!(cPass.getEditableText().toString()).equals(pass.getEditableText().toString())){
                    cPass.setError("Confirm Password does not match your Password");
                }
                else
                    insert();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent su = new Intent(SignUp.this,Login.class);
                startActivity(su);
                finish();
            }
        });

    }

    public void insert(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.29.17:8000/signup", response -> {
             Log.d("asggggg", "insert:  "+ response.toString());
             if (response.equals("Saved")){

                 StringRequest sendMailRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/sendhimail",response1 -> {
                     Log.d("ml","Mailing Link: "+response1);
                 },error1->{})
                 {
                     @Override
                     protected Map<String, String> getParams() throws AuthFailureError {
                         HashMap<String, String> param1 = new HashMap<>();
                         param1.put("user_email", email.getText().toString());
                         param1.put("user_name", name.getText().toString());
                         return param1;
                     }
                 };
                 RequestQueue requestQueue = Volley.newRequestQueue(this);
                 requestQueue.getCache().clear();
                 requestQueue.add(sendMailRequest);

                 Intent i = new Intent(SignUp.this,Login.class);
                 Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                 startActivity(i);
                 finish();
             }
             else if(response.equals("User Already Exists")){
                 Toast.makeText(SignUp.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                 email.setText(""); 
                 name.setText(""); 
                 phone.setText(""); 
                 pass.setText(""); 
                 cPass.setText("");
             }
             else
             {
                 Toast.makeText(SignUp.this, "Could not Register", Toast.LENGTH_SHORT).show();
             }


        }, error -> Log.d("asggggg", "insert:  "+ error.getMessage())) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("user_email", email.getText().toString());
                param.put("user_name", name.getText().toString());
                param.put("user_phone", phone.getEditableText().toString());
                param.put("user_password", cPass.getText().toString());
                Log.d("ff", String.valueOf(name));
                return param;
            }
        };

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.getCache().clear();
        requestQueue1.add(stringRequest);
    }

}

