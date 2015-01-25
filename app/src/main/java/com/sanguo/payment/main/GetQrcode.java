package com.sanguo.payment.main;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.sanguo.payment.alipay.util.Submit;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/16.
 */
public class GetQrcode extends AsyncTask<Map<String, String>, Integer, Bitmap> {

    AlertDialog dialog = null;
    ImageView img = null;

    GetQrcode(AlertDialog dialog, ImageView img){
        super();
        this.dialog = dialog;
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

        String url = xmlAnalyze(xml);
        if (url == null) {
            return null;
        }

        Bitmap bitmap = getBmp(url);
        return bitmap;
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
            if (result_code.equals("FAIL")) {
                return null;
            }

            ret = alipayElement.elementText("pic_url");
        }catch(Exception e){
            return null;
        }

        return ret;
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
            dialog.setTitle("错误");
            dialog.setMessage("请求失败");
            dialog.show();
        }else{
            img.setImageBitmap(bmp);
            dialog.setTitle("二维码");
            dialog.setView(img);
            dialog.show();
        }
    }
}
