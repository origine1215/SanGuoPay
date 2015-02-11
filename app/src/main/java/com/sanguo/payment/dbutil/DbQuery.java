package com.sanguo.payment.dbutil;

import com.sanguo.payment.alipay.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by Administrator on 2015/2/7.
 */
public class DbQuery  {
    private static String queryUrl = "http://app.51sanguo.cn/?m=Home&c=DbOperator&a=";
    public static String name = null;
    public static String password = null;

    public int userCheck(){
        String url = queryUrl + "userCheck";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", name);
        paramMap.put("password", password);

        try {
            String retVal = HttpQuery.buildRequest(paramMap, url);
            JSONObject jsonObject = new JSONObject(retVal);
            Map map = toMap(jsonObject.toString());

            int code = Integer.valueOf(map.get("retval").toString());
            if (code == 0){
                Config.partner = map.get("pid").toString();
                Config.company = map.get("company").toString();
                Config.key = map.get("key").toString();
                Config.seller_email = map.get("alicount").toString();
            }
            return code;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int register(String name, String password){
        String url = queryUrl + "register";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", name);
        paramMap.put("password", password);

        try {
            String retVal = HttpQuery.buildRequest(paramMap, url);
            JSONObject jsonObject = new JSONObject(retVal);
            Map map = toMap(jsonObject.toString());

            int code = Integer.valueOf(map.get("retval").toString());
            return code;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int setAlicount(String name, String company, String alicount, String pid, String key){
        String url = queryUrl + "setAlicount";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", name);
        paramMap.put("company", company);
        paramMap.put("alicount", alicount);
        paramMap.put("pid", pid);
        paramMap.put("key", key);

        try {
            String retVal = HttpQuery.buildRequest(paramMap, url);
            JSONObject jsonObject = new JSONObject(retVal);
            Map map = toMap(jsonObject.toString());

            int code = Integer.valueOf(map.get("retval").toString());
            return code;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public static Map toMap(String jsonString) throws JSONException {
        if (jsonString != null && jsonString.startsWith("\ufeff")) {
            jsonString = jsonString.substring(1);
        }

        JSONObject jsonObject = new JSONObject(jsonString);

        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);

        }
        return result;

    }
}
