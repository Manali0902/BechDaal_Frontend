package com.example.bechdaal;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_cardview, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = productList.get(position);

        //binding the data with the viewholder views
        holder.productName.setText(product.getProduct_name());
        holder.productPrice.setText(product.getProduct_price());
        holder.productImage.setImageBitmap(product.getProduct_image());
        holder.productId.setText(product.getProduct_id());
        if (product.getFav().equals("Yes"))
            holder.fav.setBackgroundResource(R.drawable.favorite_red);
        else
            holder.fav.setBackgroundResource(R.drawable.favorite);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productPrice, productId;
        ImageView productImage,fav;
        CardView cardViewX;

        public ProductViewHolder(View itemView) {
            super(itemView);

            cardViewX = itemView.findViewById(R.id.cardViewX);
            productId = itemView.findViewById(R.id.productId);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            fav = itemView.findViewById(R.id.fav);

            cardViewX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),productId.getText().toString() , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(v.getContext(),FullProductDetails.class);
                    i.putExtra("pid",productId.getText().toString());
                    v.getContext().startActivity(i);
                }
            });

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/addfavorites",
                            response -> {
                        if (response.equals("Already Favorite")){
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST,"http://192.168.29.17:8000/removefavorite",
                                    response1 -> {
                                if (response1.equals("Removed from Favorite")){
                                    fav.setBackgroundResource(R.drawable.favorite);
                                }
                                    }, error -> {
                                Toast.makeText(v.getContext(), "Cannot remove from Favorites", Toast.LENGTH_SHORT).show();
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> param = new HashMap<>();
                                    SessionManagement sessionManagement = new SessionManagement(v.getContext());
                                    String email = sessionManagement.getSession();
                                    param.put("email",email);
                                    param.put("pid",productId.getText().toString());
                                    return param;
                                }
                            };
                            RequestQueue requestQueue1 = Volley.newRequestQueue(v.getContext());
                            requestQueue1.getCache().clear();
                            requestQueue1.add(stringRequest1);
                        }
                        else if (response.equals("Added to Favorite")){
                            fav.setBackgroundResource(R.drawable.favorite_red);
                        }
                            },error -> {
                        Toast.makeText(v.getContext(), "Cannot add to Favorites", Toast.LENGTH_SHORT).show();
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            SessionManagement sessionManagement = new SessionManagement(v.getContext());
                            String email = sessionManagement.getSession();
                            param.put("email",email);
                            param.put("pid",productId.getText().toString());
                            return param;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                    requestQueue.getCache().clear();
                    requestQueue.add(stringRequest);


                }
            });

        }
    }
}
