package com.wzp109761.basic;

import android.app.Application;

import com.wzp109761.router.ARouter;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().init(this);
    }
}
