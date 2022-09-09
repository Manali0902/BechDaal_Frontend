package com.example.bechdaal.ui.categories;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bechdaal.Artist;
import com.example.bechdaal.CategoryWiseProducts;
import com.example.bechdaal.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

import java.util.HashMap;
import java.util.Map;

public class SingleCheckArtistViewHolder extends CheckableChildViewHolder {

  private CheckedTextView childCheckedTextView;

  public SingleCheckArtistViewHolder(View itemView) {
    super(itemView);
    childCheckedTextView =
        (CheckedTextView) itemView.findViewById(R.id.list_item_singlecheck_artist_name);
    childCheckedTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(itemView.getContext(), childCheckedTextView.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(itemView.getContext(), CategoryWiseProducts.class);
        i.putExtra("category",childCheckedTextView.getText().toString());
        itemView.getContext().startActivity(i);
      }
    });
  }

  @Override
  public Checkable getCheckable() {
    return childCheckedTextView;
  }

  public void setArtistName(String artistName) {
    childCheckedTextView.setText(artistName);
  }
}
