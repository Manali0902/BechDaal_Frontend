package com.example.bechdaal.ui.changepass;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePassViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChangePassViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Change your password here");
    }

    public LiveData<String> getText() {
        return mText;
    }
}