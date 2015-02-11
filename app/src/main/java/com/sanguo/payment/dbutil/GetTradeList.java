package com.sanguo.payment.dbutil;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.sanguo.payment.alipay.config.Config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/10.
 */
public class GetTradeList extends Thread {

    Handler handler;
    private static String queryUrl = "http://app.51sanguo.cn/?m=Home&c=DbOperator&a=getTradeList";

    public GetTradeList(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run() {

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("partner", Config.partner);
        Message msg = new Message();

        try {
            String retVal = HttpQuery.buildRequest(paramMap, queryUrl);
            JSONObject jsonObject = new JSONObject(retVal);
            Map map = HttpQuery.toMap(jsonObject.toString());

            int code = Integer.valueOf(map.get("retval").toString());
            if (code == 0){
                Bundle b = new Bundle();
                b.putString("data", map.get("data").toString());
                msg.setData(b);
                //msg.sendToTarget();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        handler.sendMessage(msg);
    }
}
