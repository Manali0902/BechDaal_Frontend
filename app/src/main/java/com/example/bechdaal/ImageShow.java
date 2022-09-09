package com.example.bechdaal;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ImageShow extends AppCompatActivity {

    ImageView showImage;
    Button show;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);

        showImage = findViewById(R.id.showImage);
        show = findViewById(R.id.show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/showimage",
                        response -> {
                            Toast.makeText(ImageShow.this, response, Toast.LENGTH_SHORT).show();
                            uri = Uri.parse(response);
                            showImage.setImageURI(uri);
                        },error -> {
                    Toast.makeText(ImageShow.this, "Something Went wrong in displaying Image", Toast.LENGTH_SHORT).show();
                    Log.d("ggf","Display Image: "+error.getMessage());
                }){};
                RequestQueue requestQueue = Volley.newRequestQueue(ImageShow.this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);
            }
        });


    }
}