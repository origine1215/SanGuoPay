package com.sanguo.payment.main;

import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.widget.TextView;

import com.sanguo.payment.R;

/**
 * Created by Administrator on 2015/2/5.
 */
public class FKCGActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fkcg);

        TextView text = (TextView)findViewById(R.id.fkcg1);
        Bundle extras = getIntent().getExtras();
        text.setText(extras.getString("total_fee"));
    }
}
