package com.sanguo.payment.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanguo.payment.R;
import com.sanguo.payment.alipay.config.Config;
import com.sanguo.payment.dbutil.UserCheck;

/**
 * Created by Administrator on 2015/2/6.
 */
public class DLActivity  extends Activity {

    EditText nameEdit;
    EditText pwdEdit;
    TextView hintText;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl);

        nameEdit = (EditText)findViewById(R.id.dl2);
        pwdEdit = (EditText)findViewById(R.id.dl3);
        hintText = (TextView)findViewById(R.id.dl4);

        ImageView denglu =  (ImageView)findViewById(R.id.dl5);
        denglu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String name = nameEdit.getText().toString();
                        String password = pwdEdit.getText().toString();

                        if(name == null || name.length() <= 0){
                            hintText.setText("请输入用户名");
                            return;
                        }

                        if (password == null || password.length() <= 0){
                            hintText.setText("请输入登录密码");
                            return;
                        }

                        DLActivity.this.name = name;
                        UserCheck check = new UserCheck(myHandler, name, password);
                        check.start();
                    }
                }
        );

        ImageView zhuce =  (ImageView)findViewById(R.id.dl7);
        zhuce.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(DLActivity.this, ZCActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int retVal = msg.arg1;
            if (retVal == -1) {
                hintText.setText("服务器连接失败");
            }else if (retVal == -2){
                hintText.setText("用户名或密码错误");
            }else if (retVal == 0){
                Config.name = name;
                Intent intent = new Intent();
                intent.setClass(DLActivity.this, SYActivity.class);
                startActivity(intent);
            }
        }

    };
}
