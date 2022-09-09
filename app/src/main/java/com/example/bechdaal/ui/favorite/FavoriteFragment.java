package com.example.bechdaal.ui.favorite;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bechdaal.Product;
import com.example.bechdaal.ProductAdapter;
import com.example.bechdaal.R;
import com.example.bechdaal.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FavoriteFragment extends Fragment {

    private static final int IMG_WIDTH = 180;
    private static final int IMG_HEIGHT = 180;

    JSONObject jsonObject;
    List<Product> productList;
    RecyclerView recyclerViewforProducts;

    private FavoriteViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);

        productList = new ArrayList<>();

        recyclerViewforProducts = root.findViewById(R.id.recycleViewFav);
        recyclerViewforProducts.setHasFixedSize(true);
        recyclerViewforProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.29.17:8000/favproduct",
                response -> {
                    try {
                        Log.d("advg", "fgff: " + response);
                        jsonObject = new JSONObject(response);
                        Iterator<String> listKEY = jsonObject.keys();
                        do {
                            String newKEY = listKEY.next();
                            JSONObject object = jsonObject.getJSONObject(newKEY);
                            String prodName = object.getString("Product Name");
                            String prodPrice = object.getString("Product Price");
                            String prodImage = object.getString("Image");
                            String prodId = object.getString("Product Id");
                            String fav = object.getString("Favorite");
                            productList.add(new Product(convertStringToBitmap(prodImage),prodName,prodPrice,prodId,fav));
                        } while (listKEY.hasNext());
                         ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
                        recyclerViewforProducts.setAdapter(productAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                SessionManagement sessionManagement = new SessionManagement(getContext());
                String email = sessionManagement.getSession();
                param.put("email", email);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);

        return root;

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


}