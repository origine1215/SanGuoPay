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
import com.sanguo.payment.alipay.config.Config;
import com.sanguo.payment.dbutil.setAlicount;
/**
 * Created by Administrator on 2015/2/8.
 */
public class ZHSZActivity extends Activity {

    EditText companyEdit;
    EditText alicountEdit;
    EditText pidEdit;
    EditText keyEdit;
    TextView hintText;
    ImageView tijiao;
    String company;
    String alicount;
    String pid;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhsz);

        companyEdit = (EditText)findViewById(R.id.zhsz3);
        alicountEdit = (EditText)findViewById(R.id.zhsz4);
        pidEdit = (EditText)findViewById(R.id.zhsz5);
        keyEdit = (EditText)findViewById(R.id.zhsz6);
        hintText = (TextView)findViewById(R.id.zhsz7);
        tijiao =  (ImageView)findViewById(R.id.zhsz8);

        if (Config.seller_email != null && Config.seller_email.length() > 0){
            companyEdit.setText(Config.company);
            alicountEdit.setText(Config.seller_email);
            pidEdit.setText(Config.partner);
            keyEdit.setText(Config.key);

            companyEdit.setKeyListener(null);
            alicountEdit.setKeyListener(null);
            pidEdit.setKeyListener(null);
            keyEdit.setKeyListener(null);

            tijiao.setEnabled(false);
        }

        tijiao.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        company = companyEdit.getText().toString();
                        alicount = alicountEdit.getText().toString();
                        pid = pidEdit.getText().toString();
                        key = keyEdit.getText().toString();

                        if (company == null || company.length() <= 0){
                            hintText.setText("请输入公司简称");
                            return;
                        }

                        if (alicount == null || alicount.length() <= 0){
                            hintText.setText("请输入支付宝账号");
                            return;
                        }

                        if (pid == null || pid.length() <= 0){
                            hintText.setText("Pid");
                            return;
                        }

                        if (key == null || key.length() <= 0){
                            hintText.setText("请输入Key");
                            return;
                        }

                        setAlicount setcount = new setAlicount(myHandler, company, alicount, pid, key);
                        setcount.start();
                    }
                }
        );

        ImageView fanhui =  (ImageView)findViewById(R.id.zhsz1);
        fanhui.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        ZHSZActivity.this.finish();
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
                hintText.setText("注册失败，请重新输入");
            }else if (retVal == 0){
                Config.company = company;
                Config.seller_email = alicount;
                Config.partner = pid;
                Config.key = key;
                hintText.setTextColor(Color.GREEN);
                hintText.setText("设置成功");
            }
        }
    };

}
