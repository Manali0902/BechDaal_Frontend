package com.example.bechdaal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.bechdaal.CategoryWiseProducts.convertStringToBitmap;

public class FullProductDetails extends AppCompatActivity implements PaymentResultListener {

    private static final int IMG_WIDTH = 400;
    private static final int IMG_HEIGHT = 300;

    ImageView image_for_product;
    TextView p_id,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8,textView9,textView10,
    textView11,textView12;
    RelativeLayout relativeLayout;
    JSONObject jsonObject,object;
    int count=0,amount;
    Button chat,buy;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_product_details);

        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");

        relativeLayout = findViewById(R.id.relative_layout);
        p_id = findViewById(R.id.p_id_gone);
        image_for_product = findViewById(R.id.image_for_product);
        textView1 = findViewById(R.id.textview1);
        textView2 = findViewById(R.id.textview2);
        textView3 = findViewById(R.id.textview3);
        textView4 = findViewById(R.id.textview4);
        textView5 = findViewById(R.id.textview5);
        textView6 = findViewById(R.id.textview6);
        textView7 = findViewById(R.id.textview7);
        textView8 = findViewById(R.id.textview8);
        textView9 = findViewById(R.id.textview9);
        textView10 = findViewById(R.id.textview10);
        textView11 = findViewById(R.id.textview11);
        textView12 = findViewById(R.id.textview12);
        chat = findViewById(R.id.chat);
        buy = findViewById(R.id.buy);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/viewspecificproduct",
                response -> {
                    Log.d("rrrr","tttt: "+response);

                    try {
                        Log.d("jjj","fgff: "+response);
                        jsonObject = new JSONObject(response);
                        object= jsonObject.getJSONObject(pid);
                        ArrayList keyList = new ArrayList();
                        Iterator<String> listKEY = object.keys();
                        do {
                            keyList.add(listKEY.next());
                        }while (listKEY.hasNext());
                        Log.d("dsd","fff"+keyList.toString());
                        ArrayList keyValue = new ArrayList();
                        for (int i = 1;i<keyList.size()-1;i++){
                            String key = keyList.get(i).toString();
                            String val = object.getString(key);
                            keyValue.add(key+": "+val);
                        }
                        Bitmap img = convertStringToBitmap(object.getString("Image"));
                        image_for_product.setImageBitmap(RotateBitmap(img,90));
                            textView1.setText(keyValue.get(0).toString());
                            textView2.setText(keyValue.get(1).toString());
                            textView3.setText(keyValue.get(2).toString());
                            textView4.setText(keyValue.get(3).toString());
                            textView5.setText(keyValue.get(4).toString());
                            textView6.setText(keyValue.get(5).toString());
                            textView7.setText(keyValue.get(6).toString());
//                            textView8.setText(keyValue.get(7).toString());
//                            textView9.setText(keyValue.get(8).toString());
//                            textView10.setText(keyValue.get(9).toString());




//                        Iterator<String> listKEY1 = object.keys();
//                        do {
//                            String newKEY = listKEY1.next();
//                            String value = object.getString(newKEY);
//                            productList.add(new Product(convertStringToBitmap(prodImage),prodName,prodPrice,prodId,fav));
//                        }while (listKEY1.hasNext());
//
//                        productAdapter = new ProductAdapter(CategoryWiseProducts.this,productList);
//                        recyclerViewforProducts.setAdapter(productAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
            Log.d("ff","wrong: "+error.getMessage());
            })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("pid",pid);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(FullProductDetails.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement = new SessionManagement(FullProductDetails.this);
                Intent i = new Intent(FullProductDetails.this,ChatActivity.class);
                i.putExtra("myusername",sessionManagement.getSessionPhone());
                try {
                    i.putExtra("otherusername",object.getString("Phone"));
                    i.putExtra("otherusernamename",object.getString("Seller Name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                v.getContext().startActivity(i);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    amount = Math.round(Float.parseFloat(object.getString("Price")) * 100 );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_AAcZ4fMAoFptC9");
                checkout.setImage(R.drawable.logo);
                SessionManagement sessionManagement = new SessionManagement(FullProductDetails.this);
                String phone = sessionManagement.getSessionPhone();
                String email = sessionManagement.getSession();
                JSONObject jobj = new JSONObject();
                try {
                    jobj.put("name","Test Coding");
                    jobj.put("description","Test Payment");
                    jobj.put("theme.color","#0093DD");
                    jobj.put("currency","INR");
                    jobj.put("amount",amount);
                    jobj.put("prefill.contact",phone);
                    jobj.put("prefill.email",email);
                    checkout.open(FullProductDetails.this,jobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static String resizeBase64Image(String base64image) {
        byte[] encodeByte = android.util.Base64.decode(base64image.getBytes(), android.util.Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length, options);

        if (image.getHeight() <= 400 && image.getWidth() <= 400) {
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, IMG_WIDTH, IMG_HEIGHT, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] b = baos.toByteArray();
        System.gc();
        return android.util.Base64.encodeToString(b, android.util.Base64.NO_WRAP);
    }

    private static Bitmap convertString64ToImage(String base64String) {
        byte[] decodedString = android.util.Base64.decode(base64String, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static Bitmap convertStringToBitmap(String base64String) {
        return convertString64ToImage(resizeBase64Image(base64String));
    }


    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment Id");
        builder.setMessage(s);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}