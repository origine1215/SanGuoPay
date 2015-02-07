package com.sanguo.payment.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.sanguo.payment.R;
import com.sanguo.payment.zxing.activity.CaptureActivity;

import android.view.View;
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
                        Intent intent = new Intent();
                        intent.setClass(SYActivity.this, JSQActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
