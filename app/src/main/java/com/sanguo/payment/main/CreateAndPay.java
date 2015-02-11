package com.sanguo.payment.main;

import android.os.AsyncTask;

import com.sanguo.payment.alipay.config.Config;
import com.sanguo.payment.alipay.util.Submit;
import com.sanguo.payment.dbutil.HttpQuery;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/19.
 */
public class CreateAndPay extends AsyncTask<Map<String, String>, Integer, String> {

    DataFinishListener dataFinishListener;
    Map<String, String> paramMap;

    public void setFinishListener(DataFinishListener dataFinishListener) {
        this.dataFinishListener = dataFinishListener;
    }

    public interface DataFinishListener {
        void dataFinishSuccessfully(String data);
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {
        this.paramMap = params[0];
        String xml = null;
        try {
            xml = Submit.buildRequest("", "", params[0]);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (xml == null){
            return null;
        }

        String ret = xmlAnalyze(xml);
        return  ret;
    }

    private String xmlAnalyze(String xml){
        Document doc = null;
        String ret;

        try {
            doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            String is_success = root.elementText("is_success");
            if (is_success.equals("F")) {
                return null;
            }

            Element alipayElement = root.element("response").element("alipay");
            String result_code = alipayElement.elementText("result_code");
            if (result_code.equals("ORDER_FAIL")) {
                ret = alipayElement.elementText("detail_error_des");
                return ret;
            }

            //更新记录
            updateRecord();

            return "支付成功";
        }catch(Exception e){
            return null;
        }
    }

    //将交易记录插入数据库
    private int updateRecord(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", Config.partner);
        map.put("key", Config.key);
        map.put("out_trade_no", paramMap.get("out_trade_no"));
        map.put("subject", paramMap.get("subject"));
        map.put("product_code", paramMap.get("product_code"));
        map.put("total_fee", paramMap.get("total_fee"));
        map.put("dynamic_id_type",paramMap.get("dynamic_id_type"));
        map.put("dynamic_id", paramMap.get("dynamic_id"));

        String json = new JSONObject(map).toString();
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("data", json);
        String url = "http://weixin.51sanguo.cn/index.php/Wap/Offline/insert";
        try {
            String retVal = HttpQuery.buildRequest(dataMap, url);
            JSONObject jsonObject = new JSONObject(retVal);
            Map retMap = HttpQuery.toMap(jsonObject.toString());
            int ret = Integer.valueOf(retMap.get("error").toString());
            return ret;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 2;
    }
    @Override
    protected void onPostExecute(String val) {
        dataFinishListener.dataFinishSuccessfully(val);
    }
}
