package com.sanguo.payment.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sanguo.payment.R;
import com.sanguo.payment.alipay.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/6.
 */
public class FKMActivity  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fkm);


        Bundle extras = getIntent().getExtras();
        String total_fee = extras.getString("total_fee");

        ImageView fanhui = (ImageView)findViewById(R.id.fkm1);
        fanhui.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        FKMActivity.this.finish();
                    }
                }
        );

        ImageView img = (ImageView)findViewById(R.id.fkm2);

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("partner", Config.partner);
        sParaTemp.put("key", Config.key);
        sParaTemp.put("seller_email", Config.seller_email);
        sParaTemp.put("subject", Config.company + "线下支付");
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("app_user", Config.name);

        GetQrcode getCode = new GetQrcode(img);
        getCode.execute(sParaTemp);
    }
}
