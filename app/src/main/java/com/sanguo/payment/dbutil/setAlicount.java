package com.sanguo.payment.dbutil;

import android.os.Handler;
import android.os.Message;

import com.sanguo.payment.alipay.config.Config;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/10.
 */
public class setAlicount extends Thread {

    Handler handler;
    String company;
    String alicount;
    String pid;
    String key;
    private static String queryUrl = "http://app.51sanguo.cn/?m=Home&c=DbOperator&a=setAlicount";

    public setAlicount(Handler handler, String company, String alicount, String pid, String key){
        this.handler = handler;
        this.company = company;
        this.alicount = alicount;
        this.pid = pid;
        this.key = key;
    }

    @Override
    public void run() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", Config.name);
        paramMap.put("company", company);
        paramMap.put("alicount", alicount);
        paramMap.put("pid", pid);
        paramMap.put("key", key);
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
