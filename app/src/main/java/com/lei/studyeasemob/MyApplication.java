package com.lei.studyeasemob;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by yanle on 2018/3/25.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //用来设置SDK属性
        EMOptions emp = new EMOptions();
        emp.setAutoLogin(false);
        //初始化SDK
        EMClient.getInstance().init(this, emp);
    }
}
