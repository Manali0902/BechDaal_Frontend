package com.example.bechdaal.ui.myprofile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.load.ImageHeaderParser;
import com.example.bechdaal.R;
import com.example.bechdaal.SessionManagement;

public class MyProfileFragment extends Fragment {

    private MyProfileViewModel myProfileViewModel;
    TextView name,email,phone;
    ImageView imageView;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myProfileViewModel =
                new ViewModelProvider(this).get(MyProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_my_profile);
        name = root.findViewById(R.id.name);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phone);

        SessionManagement sessionManagement = new SessionManagement(getContext());
        name.setText("Name: "+sessionManagement.getSessionName());
        email.setText("Email:\n"+sessionManagement.getSession());
        phone.setText("Mobile No.: "+sessionManagement.getSessionPhone());

        myProfileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}