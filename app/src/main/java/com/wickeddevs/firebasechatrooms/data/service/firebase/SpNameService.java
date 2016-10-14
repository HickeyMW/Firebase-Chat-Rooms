package com.wickeddevs.firebasechatrooms.data.service.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wickeddevs.firebasechatrooms.MyApplication;
import com.wickeddevs.firebasechatrooms.data.service.contract.NameService;

public class SpNameService implements NameService {

    private final String SPK_NAME = "SP_KEY_NAME";

    @Override
    public String getUsername() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.myApplication);
        return sp.getString(SPK_NAME, null);
    }

    @Override
    public void setUserName(String name) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.myApplication);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SPK_NAME, name);
        editor.apply();
    }
}
