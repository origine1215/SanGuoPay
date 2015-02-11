package com.sanguo.payment.dbutil;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/9.
 */
public class register extends Thread {

    Handler handler;
    String name;
    String password;
    private static String queryUrl = "http://app.51sanguo.cn/?m=Home&c=DbOperator&a=register";

    public register(Handler handler, String name, String password){
        this.handler = handler;
        this.name = name;
        this.password = password;
    }

    @Override
    public void run() {

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", name);
        paramMap.put("password", password);
        Message msg = new Message();
        msg.arg1 = -1;
        try {
            String retVal = HttpQuery.buildRequest(paramMap, queryUrl);
            JSONObject jsonObject = new JSONObject(retVal);
            Map map = HttpQuery.toMap(jsonObject.toString());

            int code = Integer.valueOf(map.get("retval").toString());
            msg.arg1 = code;
        }catch (Exception e){
            e.printStackTrace();
        }
        handler.sendMessage(msg);
    }
}
