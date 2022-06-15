package com.wzp109761.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wzp109761.annotation.BindPath;

@BindPath("member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ;
    }
}