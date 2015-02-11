package com.sanguo.payment.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sanguo.payment.R;
import com.sanguo.payment.dbutil.GetTradeList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/2/10.
 */
public class TJActivity extends Activity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tj);

        listView = (ListView) this.findViewById(R.id.tj2);
        GetTradeList getTradeList = new GetTradeList(myHandler);
        getTradeList.start();
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取bundle对象的值
            Bundle b = msg.getData();
            String data = b.getString("data");

            if (data != null && data.length() > 0) {
                List<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
                try {
                    JSONArray jsonarray = new JSONArray(data);
                    for(int i = 0; i < jsonarray.length(); i++){//遍历JSONArray

                        JSONObject oj = jsonarray.getJSONObject(i);
                        HashMap<String, Object> item = new HashMap<String, Object>();
                        if (oj.get("buyer_email").toString().equals("null")){
                            item.put("name", "无");
                        }else {
                            item.put("name", oj.get("buyer_email"));
                        }
                        item.put("price", oj.get("total_fee"));
                        item.put("time", oj.get("time"));

                        if (oj.get("trade_status").equals("TRADE_SUCCESS")){
                            item.put("success", "已支付");
                        }else if (oj.get("trade_status").equals("WAIT_BUYER_PAY")){
                            item.put("success", "未支付");
                        }
                        dataList.add(item);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(TJActivity.this, dataList, R.layout.listview_tj,
                            new String[]{"name", "price","time","success"}, new int[]{R.id.list_tj1, R.id.list_tj2,R.id.list_tj3,R.id.list_tj4});
                    listView.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }
    };
}
