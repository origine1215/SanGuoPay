package com.sanguo.payment.main;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
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

        String out_trade_no = String.valueOf(System.currentTimeMillis());
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "alipay.acquire.precreate");
        sParaTemp.put("partner", Config.partner);
        sParaTemp.put("_input_charset", Config.input_charset);
        sParaTemp.put("seller_email", Config.seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", "三国淘园线下支付");
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("product_code", "QR_CODE_OFFLINE");

        GetQrcode getCode = new GetQrcode(img);
        getCode.execute(sParaTemp);
    }
}
