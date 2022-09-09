package com.example.bechdaal;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;


public final class SeeAllUsers extends AppCompatActivity {
    private ExpandingList mExpandingList;
    private HashMap _$_findViewCache;
    JSONObject jsonObject;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_see_all_users);
        this.mExpandingList = (ExpandingList)this.findViewById(R.id.expanding_list_main);
        this.createItems();
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        try {
            jsonObject = new JSONObject(data);
            JSONObject response = jsonObject.getJSONObject("response");
            Iterator<String> listKEY = response.keys();
            do {
                String newKEY = listKEY.next();
                JSONObject object= response.getJSONObject(newKEY);
                String name = object.getString("Name");
                String email = object.getString("Email");
                String phone = object.getString("Phone");
                this.addItem(name,new String[]{email,phone},R.color.teal_200,R.drawable.ic_menu_my_profile);
            }while (listKEY.hasNext());

//            Map<String, JSONObject> map = (Map<String,JSONObject>)response.getMap();
//            ArrayList<String> list = new ArrayList<String>(map.keySet());
//
//            System.out.println(list);
            Log.d("data","data: "+ jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private final void createItems() {
//        this.addItem("John", new String[]{"House", "Boat", "Candy", "Collection", "Sport", "Ball", "Head"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Mary", new String[]{"Dog", "Horse", "Boat"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Ana", new String[]{"Cat"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Peter", new String[]{"Parrot", "Elephant", "Coffee"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Joseph", new String[0], R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Paul", new String[]{"Golf", "Football"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Larry", new String[]{"Ferrari", "Mazda", "Honda", "Toyota", "Fiat"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Moe", new String[]{"Beans", "Rice", "Meat"}, R.color.white, R.drawable.ic_menu_my_profile);
//        this.addItem("Bart", new String[]{"Hamburger", "Ice cream", "Candy"}, R.color.white, R.drawable.ic_menu_my_profile);
    }

    private final void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        ExpandingList var10000 = this.mExpandingList;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        final ExpandingItem item = var10000.createNewItem(R.layout.expanding_layout);
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            View var9 = item.findViewById(R.id.title);
            if (var9 == null) {
                throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
            }

            ((TextView)var9).setText((CharSequence)title);
            item.createSubItems(subItems.length);
            int i = 0;

            for(int var7 = item.getSubItemsCount(); i < var7; ++i) {
                View view = item.getSubItemView(i);
                Intrinsics.checkExpressionValueIsNotNull(view, "view");
                this.configureSubItem(item, view, subItems[i]);
            }

//            ((ImageView)item.findViewById(R.id.add_more_sub_items)).setOnClickListener((OnClickListener)(new OnClickListener() {
//                public final void onClick(View it) {
//                    SeeAllUsers.this.showInsertDialog((SeeAllUsers.OnItemCreated)(new SeeAllUsers.OnItemCreated() {
//                        public void itemCreated(@NotNull String title) {
//                            Intrinsics.checkParameterIsNotNull(title, "title");
//                            View newSubItem = item.createSubItem();
//                            SeeAllUsers var10000 = SeeAllUsers.this;
//                            ExpandingItem var10001 = item;
//                            if (newSubItem == null) {
//                                Intrinsics.throwNpe();
//                            }
//
//                            Intrinsics.checkExpressionValueIsNotNull(newSubItem, "newSubItem!!");
//                            var10000.configureSubItem(var10001, newSubItem, title);
//                        }
//                    }));
//                }
//            }));
//            ((ImageView)item.findViewById(R.id.remove_item)).setOnClickListener((OnClickListener)(new OnClickListener() {
//                public final void onClick(View it) {
//                    ExpandingList var10000 = SeeAllUsers.this.mExpandingList;
//                    if (var10000 == null) {
//                        Intrinsics.throwNpe();
//                    }
//
//                    var10000.removeItem(item);
//                }
//            }));
        }

    }

    private final void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        View var10000 = view.findViewById(R.id.sub_title);
        if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
        } else {
            ((TextView)var10000).setText((CharSequence)subTitle);
//            ((ImageView)view.findViewById(R.id.remove_sub_item)).setOnClickListener((OnClickListener)(new OnClickListener() {
//                public final void onClick(View it) {
//                    ExpandingItem var10000 = item;
//                    if (var10000 == null) {
//                        Intrinsics.throwNpe();
//                    }
//
//                    var10000.removeSubItem(view);
//                }
//            }));
        }
    }

    private final void showInsertDialog(final SeeAllUsers.OnItemCreated positive) {
        final EditText text = new EditText((Context)this);
        Builder builder = new Builder((Context)this);
        builder.setView((View)text);
        builder.setTitle("Enter Title");
        builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener)(new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        }));
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener)null);
        builder.show();
    }

    // $FF: synthetic method
    public static final void access$setMExpandingList$p(SeeAllUsers $this, ExpandingList var1) {
        $this.mExpandingList = var1;
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }


    public interface OnItemCreated {
        void itemCreated(@NotNull String var1);
    }
}
