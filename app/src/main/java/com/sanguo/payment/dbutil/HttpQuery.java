package com.sanguo.payment.dbutil;

import com.sanguo.payment.alipay.config.Config;
import com.sanguo.payment.alipay.util.httpClient.HttpProtocolHandler;
import com.sanguo.payment.alipay.util.httpClient.HttpRequest;
import com.sanguo.payment.alipay.util.httpClient.HttpResponse;
import com.sanguo.payment.alipay.util.httpClient.HttpResultType;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/7.
 */
public class HttpQuery {

    public static String buildRequest(Map<String, String> sParaTemp, String queryUrl) throws Exception {
        //待请求参数数组
        Map<String, String> sPara = sParaTemp;

        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        //设置编码集
        request.setCharset(Config.input_charset);

        request.setParameters(generatNameValuePair(sPara));
        //request.setUrl(ALIPAY_GATEWAY_NEW+"_input_charset="+ Config.input_charset);
        request.setUrl(queryUrl);

        HttpResponse response = httpProtocolHandler.execute(request, "", "");
        if (response == null) {
            return null;
        }

        String strResult = response.getStringResult();

        return strResult;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
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
