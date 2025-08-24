package com.xiliu.XTClock.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("极简悬浮时钟，启动后以悬浮窗显顶层，黑底黄字清晰展示时分秒。可拖动调位，不挡操作，助抢购时精准读秒，把控提交时机。"
        +"/n"
        +"注：启动悬浮，需要先获得悬浮窗授权");
    }

    public LiveData<String> getText() {
        return mText;
    }
}