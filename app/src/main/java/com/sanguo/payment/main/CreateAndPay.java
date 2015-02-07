package com.sanguo.payment.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.sanguo.payment.alipay.util.Submit;
import com.sanguo.payment.zxing.activity.CaptureActivity;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Map;

/**
 * Created by Administrator on 2015/1/19.
 */
public class CreateAndPay extends AsyncTask<Map<String, String>, Integer, String> {

    DataFinishListener dataFinishListener;

    public void setFinishListener(DataFinishListener dataFinishListener) {
        this.dataFinishListener = dataFinishListener;
    }

    public interface DataFinishListener {
        void dataFinishSuccessfully(String data);
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {
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

            return "支付成功";
        }catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(String val) {
        dataFinishListener.dataFinishSuccessfully(val);
    }
}
