package com.xiliu.XTClock.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("极简悬浮时钟，悬浮显时间，助抢购精准读秒。");
    }

    public LiveData<String> getText() {
        return mText;
    }
}