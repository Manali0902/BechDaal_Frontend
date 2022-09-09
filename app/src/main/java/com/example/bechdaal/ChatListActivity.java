package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatListActivity extends AppCompatActivity {

    RecyclerView chatListRecyclerView;
    List<ChatList> chatNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = findViewById(R.id.chatListRecyclerView);
        chatListRecyclerView.setHasFixedSize(true);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(ChatListActivity.this));

        chatNameList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/chatnamelist",
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Iterator<String> listKEY = jsonObject.keys();
                        do{
                            String number = listKEY.next();
                            String name = jsonObject.getString(number);
                            chatNameList.add(new ChatList(name,number));
                        }while (listKEY.hasNext());
                        ChatListAdapter chatListAdapter = new ChatListAdapter(ChatListActivity.this,chatNameList);
                        chatListRecyclerView.setAdapter(chatListAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(this, "Err : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                SessionManagement sessionManagement = new SessionManagement(ChatListActivity.this);
                String phone = sessionManagement.getSessionPhone();
                param.put("myusername",phone);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ChatListActivity.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

    }
}