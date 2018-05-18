package com.jewel;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/05/16
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
