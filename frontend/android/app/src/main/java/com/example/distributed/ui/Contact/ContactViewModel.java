package com.example.distributed.ui.Contact;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    public ContactViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the contact fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}