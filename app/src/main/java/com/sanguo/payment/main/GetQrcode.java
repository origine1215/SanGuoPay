package com.sanguo.payment.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.sanguo.payment.dbutil.HttpQuery;

import org.json.JSONObject;

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

    ImageView img = null;

    GetQrcode(ImageView img){
        super();
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(Map<String, String>... params) {

        String queryUrl = "http://app.51sanguo.cn/index.php/HttpService/Offline/precreate";
        Bitmap bitmap = null;
        try {
            String retVal = HttpQuery.buildRequest(params[0], queryUrl);
            JSONObject jsonObject = new JSONObject(retVal);
            Map map = HttpQuery.toMap(jsonObject.toString());
            if (Integer.valueOf(map.get("error").toString()) == 0) {
                bitmap = getBmp(map.get("pic_url").toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
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
