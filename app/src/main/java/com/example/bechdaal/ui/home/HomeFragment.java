package com.example.bechdaal.ui.home;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bechdaal.Model.SliderItem;
import com.example.bechdaal.Product;
import com.example.bechdaal.ProductAdapter;
import com.example.bechdaal.R;
import com.example.bechdaal.RecyclerViewDataAdapter;
import com.example.bechdaal.SectionDataModel;
import com.example.bechdaal.SessionManagement;
import com.example.bechdaal.SingleItemModel;
import com.example.bechdaal.SliderAdapterExample;
import com.example.bechdaal.ui.categories.CategoriesFragment;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private static final int IMG_WIDTH = 180;
    private static final int IMG_HEIGHT = 180;

    SliderView sliderView;
    private SliderAdapterExample adapter1;
    JSONObject jsonObject;

    ArrayList<SectionDataModel> allSampleData;
    List<Product> productList;
    RecyclerView recyclerViewforProducts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sliderView = root.findViewById(R.id.imageSlider);
        recyclerViewforProducts = root.findViewById(R.id.recycler_view_for_products);
        recyclerViewforProducts.setHasFixedSize(true);
        recyclerViewforProducts.setLayoutManager(new GridLayoutManager(getContext(),2));

        allSampleData = new ArrayList<SectionDataModel>();
        productList = new ArrayList<>();

        createDummyData();
        generateRandomProducts();

        RecyclerView my_recycler_view = root.findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getContext(), allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);


        adapter1 = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter1);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data


        SliderItem sliderItem1 = new SliderItem();
        sliderItem1.setImageUrl(R.drawable.is1);
        sliderItemList.add(sliderItem1);

        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setImageUrl(R.drawable.is2);
        sliderItemList.add(sliderItem2);

        SliderItem sliderItem3 = new SliderItem();
        sliderItem3.setImageUrl(R.drawable.is3);
        sliderItemList.add(sliderItem3);

        SliderItem sliderItem4 = new SliderItem();
        sliderItem4.setImageUrl(R.drawable.is4);
        sliderItemList.add(sliderItem4);

        adapter1.renewItems(sliderItemList);


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });


        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateRandomProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/randomproducts",
                response -> {
                    try {
                        Log.d("jjj","fgff: "+response);
                        jsonObject = new JSONObject(response);
                        Iterator<String> listKEY = jsonObject.keys();
                        do {
                            String newKEY = listKEY.next();
                            JSONObject object= jsonObject.getJSONObject(newKEY);
                            String prodName = object.getString("Product Name");
                            String prodPrice = object.getString("Product Price");
                            String prodImage = object.getString("Image");
                            String prodId = object.getString("Product Id");
                            String fav = object.getString("Favorite");
                            productList.add(new Product(convertStringToBitmap(prodImage),prodName,prodPrice,prodId,fav));
                        }while (listKEY.hasNext());
                        ProductAdapter productAdapter = new ProductAdapter(getContext(),productList);
                        recyclerViewforProducts.setAdapter(productAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                SessionManagement sessionManagement = new SessionManagement(getContext());
                String email = sessionManagement.getSession();
                param.put("email",email);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);


//        productList.add(new Product(R.drawable.logo,"Product 1","Rs. 10,00,000"));
//        productList.add(new Product(R.drawable.cat3,"Product 2","Rs. 1,00,000"));
//        productList.add(new Product(R.drawable.cat2,"Product 3","Rs. 9,000"));
//        productList.add(new Product(R.drawable.cat1,"Product 4","Rs. 50,000"));

    }

    public void createDummyData() {

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();

                singleItem.add(new SingleItemModel("Car", R.drawable.cat1));
                singleItem.add(new SingleItemModel("Property",R.drawable.cat5));
                singleItem.add(new SingleItemModel("Laptop",R.drawable.cat4));
                singleItem.add(new SingleItemModel("Bike",R.drawable.cat2));
                singleItem.add(new SingleItemModel("Mobile",R.drawable.cat3));
                singleItem.add(new SingleItemModel("Furniture",R.drawable.cat6));


            SectionDataModel dm = new SectionDataModel("Categories:" ,singleItem);

            dm.setHeaderTitle("Categories:" );

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);
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


