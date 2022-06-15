package com.wzp109761.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzp109761.annotation.BindPath;
import com.wzp109761.login.LoginActivity;
import com.wzp109761.router.ARouter;


@BindPath("main/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void JumpActivity(View view) {
        ARouter.getInstance().jumpActivity("login/login",null);
    }
}