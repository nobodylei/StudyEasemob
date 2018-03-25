package com.lei.studyeasemob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by yanle on 2018/3/25.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText etName, etPW;
    private Button btnLogin;

    private Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行登陆功能
                EMClient.getInstance().login(etName.getText() + "",
                        etPW.getText() + "",
                        new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                //登录成功
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onError(int i, String s) {
                                //登录失败
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                //进度
                            }
                        });
            }
        });
    }

    private void initView() {
        etName = findViewById(R.id.et_name_login);
        etPW = findViewById(R.id.et_pw_login);
        btnLogin = findViewById(R.id.btn_login);
    }
}
