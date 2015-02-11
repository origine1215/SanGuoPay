package com.sanguo.payment.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.sanguo.payment.R;
import com.sanguo.payment.alipay.config.Config;

/**
 * Created by Administrator on 2015/2/3.
 */
public class SYActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy);

        ImageView shoukuan =  (ImageView)findViewById(R.id.sy6);
        shoukuan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (Config.seller_email == null || Config.seller_email.length() <= 0 || Config.seller_email.equals("null")){
                            AlertDialog dialog = new AlertDialog.Builder(SYActivity.this)
                                   .setPositiveButton("确定", null)
                                    .setMessage("请先设置收款账号")
                                    .create();
                            dialog.show();
                        }else {
                            Intent intent = new Intent();
                            intent.setClass(SYActivity.this, JSQActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );

        ImageView shezhi =  (ImageView)findViewById(R.id.sy7);
        shezhi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                            Intent intent = new Intent();
                            intent.setClass(SYActivity.this, ZHSZActivity.class);
                            startActivity(intent);
                    }
                }
        );

        ImageView tongji =  (ImageView)findViewById(R.id.sy5);
        tongji.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(SYActivity.this, TJActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    //屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_HOME:return true;
            case KeyEvent.KEYCODE_BACK:return true;
            case KeyEvent.KEYCODE_CALL:return true;
            case KeyEvent.KEYCODE_SYM: return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN: return true;
            case KeyEvent.KEYCODE_VOLUME_UP: return true;
            case KeyEvent.KEYCODE_STAR: return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
