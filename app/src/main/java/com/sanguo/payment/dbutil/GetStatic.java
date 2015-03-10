package com.sanguo.payment.dbutil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.sanguo.payment.alipay.config.Config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/8.
 */
public class GetStatic extends Thread {

    Handler handler;
    String time;
    private static String queryUrl = "http://app.51sanguo.cn/index.php/HttpService/DbOperator/getStatic";

    public GetStatic(Handler handler, String time){
        this.handler = handler;
        this.time = time;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("partner", Config.partner);
        paramMap.put("time",this.time);
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
