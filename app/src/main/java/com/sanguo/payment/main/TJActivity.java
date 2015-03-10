package com.sanguo.payment.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sanguo.payment.R;
import com.sanguo.payment.dbutil.GetStatic;
import com.sanguo.payment.dbutil.GetTradeList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/2/10.
 */
public class TJActivity extends Activity {

    ListView listView;
    protected BarChart mChart;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tj);

        //下拉列表
        spinner = (Spinner)findViewById(R.id.tj_spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_string,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                    {
                        String selectedStr = adapter.getItem(position).toString();
                        if (selectedStr.equals("今日")){
                            listView.setVisibility(View.VISIBLE);
                            mChart.setVisibility(View.GONE);
                        }else if (selectedStr.equals("最近7天")){
                            listView.setVisibility(View.GONE);
                            mChart.setVisibility(View.VISIBLE);
                            GetStatic getStatic = new GetStatic(statistics_week_handler, "week");
                            getStatic.start();
                        }else if (selectedStr.equals("最近半年")){
                            listView.setVisibility(View.GONE);
                            mChart.setVisibility(View.VISIBLE);
                            GetStatic getStatic = new GetStatic(statistics_month_handler, "month");
                            getStatic.start();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                }
        );

        //交易列表
        listView = (ListView) this.findViewById(R.id.tj2);
        GetTradeList getTradeList = new GetTradeList(myHandler);
        getTradeList.start();

        mChart = (BarChart) findViewById(R.id.tj_chart);
        initChart();
    }

    //初始化图表
    private void initChart(){
        mChart.setDrawBarShadow(true);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        //mChart.setMaxVisibleValueCount(60);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
        mChart.setDrawBarShadow(false);
        //mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);

        //ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8);
        //leftAxis.setValueFormatter(custom);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8);

        //rightAxis.setValueFormatter(custom);
    }

    private Handler statistics_month_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取bundle对象的值
            Bundle b = msg.getData();
            String data = b.getString("data");
            //System.out.println("data="+ data);

            ArrayList<String> dateList = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            for (int i = -5; i <= 0; i++){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, i);
                Date date = c.getTime();
                String dateString = sdf.format(date);
                dateList.add(dateString);
            }

            ArrayList<BarEntry> valList = new ArrayList<BarEntry>();
            if (data != null && data.length() > 0) {
                try {
                    JSONArray jsonarray = new JSONArray(data);
                    for (int i = 0; i < dateList.size(); i++) {
                        int val = 0;
                        for (int j = 0; j < jsonarray.length(); j++) {//遍历JSONArray
                            JSONObject oj = jsonarray.getJSONObject(j);
                            if (oj.get("time").toString().equals(dateList.get(i))) {
                                float floatVal = Float.valueOf(oj.get("price").toString());
                                val = (int)floatVal;
                                break;
                            }
                        }
                        valList.add(new BarEntry(val, i));
                    }
                }catch (Exception e){
                    String mm = e.getMessage();
                    e.printStackTrace();
                }

                BarDataSet set1 = new BarDataSet(valList, "总金额");
                set1.setBarSpacePercent(35f);

                ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                dataSets.add(set1);

                for (int i = 0; i < dateList.size(); i++){
                    dateList.set(i, dateList.get(i).substring(5));
                }

                BarData barData = new BarData(dateList, dataSets);
                //        data.setValueFormatter(new MyValueFormatter());
                barData.setValueTextSize(10f);
                //data.setValueTypeface(mTf);

                mChart.setData(barData);
                mChart.invalidate();
            }
        }
    };

    private Handler statistics_week_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取bundle对象的值
            Bundle b = msg.getData();
            String data = b.getString("data");
            //System.out.println("data="+ data);

            ArrayList<String> dateList = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = -6; i <= 0; i++){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, i);
                Date date = c.getTime();
                String dateString = sdf.format(date);
                dateList.add(dateString);
            }

            ArrayList<BarEntry> valList = new ArrayList<BarEntry>();
            if (data != null && data.length() > 0) {
                try {
                    JSONArray jsonarray = new JSONArray(data);
                    for (int i = 0; i < dateList.size(); i++) {
                        int val = 0;
                        for (int j = 0; j < jsonarray.length(); j++) {//遍历JSONArray
                            JSONObject oj = jsonarray.getJSONObject(j);
                            if (oj.get("time").toString().equals(dateList.get(i))) {
                                float floatVal = Float.valueOf(oj.get("price").toString());
                                val = (int)floatVal;
                                break;
                            }
                        }
                        valList.add(new BarEntry(val, i));
                    }
                }catch (Exception e){
                    String mm = e.getMessage();
                    e.printStackTrace();
                }

                BarDataSet set1 = new BarDataSet(valList, "总金额");
                set1.setBarSpacePercent(35f);

                ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                dataSets.add(set1);

                for (int i = 0; i < dateList.size(); i++){
                    dateList.set(i, dateList.get(i).substring(5));
                }

                BarData barData = new BarData(dateList, dataSets);
       //        data.setValueFormatter(new MyValueFormatter());
                barData.setValueTextSize(10f);
                //data.setValueTypeface(mTf);

                mChart.setData(barData);
                mChart.invalidate();
            }
        }
    };

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
