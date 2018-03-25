package com.lei.studyeasemob;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvContent;
    private EditText etContent, etUser;
    private Button btnSend;
    private MyMessageListener ml;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        initView();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送消息
                //创建消息对象
                String msg = etContent.getText() + " ";
                EMMessage message = EMMessage.createTxtSendMessage(msg, etUser.getText() + "");
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                tvContent.setText(tvContent.getText() + "自己:" + msg + "\n");
                etContent.setText("");
            }
        });
    }

    private void initView() {
        tvContent = findViewById(R.id.tv_content);
        etContent = findViewById(R.id.et_content_chat);
        etUser = findViewById(R.id.et_receiver_chat);
        btnSend = findViewById(R.id.btn_send_chat);
        ml = new MyMessageListener();
        EMClient.getInstance().chatManager().addMessageListener(ml);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(ml);
    }

    class MyMessageListener implements EMMessageListener {

        @Override
        public void onMessageReceived(List<EMMessage> list) {
            //获取接受到的消息

            //解析消息
            for(EMMessage msg : list) {
                final String from = msg.getFrom();
                EMMessage.Type type = msg.getType();
                switch (type) {
                    case TXT:
                        EMTextMessageBody body = (EMTextMessageBody) msg.getBody();
                        final String content = body.getMessage();
                        Log.e("tag", content);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvContent.setText(tvContent.getText() + from + ":" + content + "\n");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {

        }

        @Override
        public void onMessageRead(List<EMMessage> list) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {

        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {

        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {

        }
    }

    private void initPermission() {
        String permissions[] = {Manifest.permission.VIBRATE,
                Manifest.permission.INTERNET,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.GET_TASKS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }
    }
}
