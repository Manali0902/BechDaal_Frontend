package com.example.bechdaal.ui.changepass;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bechdaal.R;
import com.example.bechdaal.SessionManagement;

import java.util.HashMap;
import java.util.Map;

public class ChangePassFragment extends Fragment {

    TextView crntPass,newPass,cNewPass;
    Button change;
    String currentPass,email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_change_pass, container, false);

        change = root.findViewById(R.id.changePass);
        crntPass = root.findViewById(R.id.currentPass);
        newPass = root.findViewById(R.id.newPass);
        cNewPass = root.findViewById(R.id.cNewPass);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(getContext());
                currentPass = sessionManagement.getSessionPwd();
                email = sessionManagement.getSession();
                if(crntPass.getText().toString().equals(currentPass)){
                    if (newPass.getText().toString().equals(cNewPass.getText().toString())){
                        //changes made in the database
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/changepassword",response -> {
                            if (response.equals("Password Changed")){
                                Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                                crntPass.setText("");
                                newPass.setText("");
                                cNewPass.setText("");
                                //changes made to the session
                                sessionManagement.setSessionPwd(newPass.getText().toString());

                            }
                        },error -> Log.d("ch","Change Password Error: "+ error.getMessage())){
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("user_email", email);
                                param.put("user_password", newPass.getText().toString());
                                return param;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.getCache().clear();
                        requestQueue.add(stringRequest);

                    }
                    else{
                        Toast.makeText(getContext(), "New password and confirm new password does not match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Wrong Current Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}