package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    EditText editText;
    ImageButton send;
    RecyclerView chatRecyclerView;
    List<Chat> chatlist;
    int z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        editText = findViewById(R.id.editText);
        send = findViewById(R.id.send);

        Intent intent = getIntent();
        String user = intent.getStringExtra("myusername");
        String otherUser = intent.getStringExtra("otherusername");
        String otherUserName = intent.getStringExtra("otherusernamename");

        chatlist = new ArrayList<>();
        loadChat(user,otherUser);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(editText.getText().toString()).isEmpty()){
                    String chatText = editText.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/savechat",
                            response -> {
                        if (response.equals("Saved")){
                            editText.setText("");
                            chatlist.add(new Chat("Sender "+chatText));
//                            ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this,chatlist);
//                            chatRecyclerView.setAdapter(chatAdapter);
                        }
                            },
                            error -> {
                                Toast.makeText(ChatActivity.this, "ErrorXYZ:"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("sender",user);
                            param.put("reciever", otherUser);
                            param.put("message",chatText);
                            param.put("name",otherUserName);
                            return param;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this);
                    requestQueue.getCache().clear();
                    requestQueue.add(stringRequest);
                }
            }
        });

    }

    public void loadChat(String user, String otherUser){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/loadchat",
                response ->{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("Chat");
                        Log.d("tttt","rrr:"+jsonArray);
                        for (z = 0; z <jsonArray.length(); z += 1){
                            Log.d("tttt","rrr:"+jsonArray.getString(z));
                            chatlist.add(new Chat(jsonArray.getString(z)));
                            Log.d("tttt","rrr:"+chatlist.get(z).getText());
                        }
                        ChatAdapter chatAdapter = new ChatAdapter(this,chatlist);
                        chatRecyclerView.setAdapter(chatAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(this, "Made"+ chatlist.toString(), Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "ErrorABC: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tttt","Errorrrr:"+error.getMessage());
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("sender",user);
                param.put("reciever",otherUser);

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}