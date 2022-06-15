package com.wzp109761.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wzp109761.annotation.BindPath;
import com.wzp109761.router.ARouter;

@BindPath("login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jumpActivity(View view) {
        ARouter.getInstance().jumpActivity("member/member",null);
    }
}