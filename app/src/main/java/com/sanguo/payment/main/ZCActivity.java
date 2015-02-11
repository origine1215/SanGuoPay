package com.sanguo.payment.main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanguo.payment.R;
import com.sanguo.payment.dbutil.register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/2/9.
 */
public class ZCActivity extends Activity {

    EditText nameEdit;
    EditText pwdEdit;
    TextView hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zc);

        nameEdit = (EditText)findViewById(R.id.zc2);
        pwdEdit = (EditText)findViewById(R.id.zc3);
        hintText = (TextView)findViewById(R.id.zc4);
        ImageView zhuce =  (ImageView)findViewById(R.id.zc5);
        zhuce.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        String name = nameEdit.getText().toString();
                        String password = pwdEdit.getText().toString();

                        if (name == null || name.length() <= 0){
                            hintText.setText("请输入手机号或邮箱");
                            return;
                        }

                        if (!isPhoneNumber(name) && !isEmail(name)){
                            hintText.setText("请输入正确的手机号或邮箱");
                            return;
                        }

                        if (password == null || password.length() <= 0){
                            hintText.setText("请输入密码");
                            return;
                        }

                        if (password.length() < 6){
                            hintText.setText("请至少输入6位密码");
                            return;
                        }

                        register reg = new register(myHandler, name, password);
                        reg.start();
                    }
                }
        );
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int retVal = msg.arg1;
            if (retVal == -1){
                hintText.setTextColor(android.graphics.Color.RED);
                hintText.setText("服务器连接失败");
            }else if (retVal == -2){
                hintText.setTextColor(android.graphics.Color.RED);
                hintText.setText("注册失败，请重新输入用户名");
            }else if (retVal == 0){
                hintText.setTextColor(Color.GREEN);
                hintText.setText("注册成功，请重新登录");
            }
        }
    };

    private boolean isPhoneNumber(String phoneNumber) {
        boolean isValid = false;

        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    private boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }
}
