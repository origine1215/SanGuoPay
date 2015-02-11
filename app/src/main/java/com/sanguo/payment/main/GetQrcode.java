package com.sanguo.payment.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.sanguo.payment.alipay.config.Config;
import com.sanguo.payment.alipay.util.Submit;
import com.sanguo.payment.dbutil.HttpQuery;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/16.
 */
public class GetQrcode extends AsyncTask<Map<String, String>, Integer, Bitmap> {

    ImageView img = null;

    GetQrcode(ImageView img){
        super();
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(Map<String, String>... params) {
        String xml = null;
        try {
            xml = Submit.buildRequest("", "", params[0]);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (xml == null){
            return null;
        }

        String url = xmlAnalyze(xml, params[0]);
        if (url == null) {
            return null;
        }

        Bitmap bitmap = getBmp(url);
        return bitmap;
    }

    //解析返回xml字符串
    private String xmlAnalyze(String xml, Map<String, String> paramMap){
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
            if (result_code.equals("FAIL")) {
                return null;
            }

            //更新数据库
            updateRecord(paramMap);

            ret = alipayElement.elementText("pic_url");
        }catch(Exception e){
            return null;
        }

        return ret;
    }

    //将交易记录插入数据库
    private int updateRecord(Map<String, String> paramMap){
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", Config.partner);
        map.put("key", Config.key);
        map.put("out_trade_no", paramMap.get("out_trade_no"));
        map.put("subject", paramMap.get("subject"));
        map.put("product_code", paramMap.get("product_code"));
        map.put("total_fee", paramMap.get("total_fee"));
        map.put("dynamic_id_type", "");
        map.put("dynamic_id", "");

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

    private Bitmap getBmp(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return  null;
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        if (bmp == null){
        }else{
            img.setImageBitmap(bmp);
        }
    }
}
