package com.sanguo.payment.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sanguo.payment.R;
import com.sanguo.payment.zxing.activity.CaptureActivity;

/**
 * Created by Administrator on 2015/2/4.
 */
public class JSQActivity  extends Activity {

    EditText edit;
    String code = "";
    String firstNum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsq);

        edit = (EditText)findViewById(R.id.edit);
        edit.setInputType(InputType.TYPE_NULL);

        ImageView fanhui =  (ImageView)findViewById(R.id.jsq1);
        fanhui.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        JSQActivity.this.finish();
                    }
                }
        );

        //1
        ImageView one =  (ImageView)findViewById(R.id.jsq4);
        one.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"1");
                    }
                }
        );

        //2
        ImageView two =  (ImageView)findViewById(R.id.jsq5);
        two.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"2");
                    }
                }
        );

        //3
        ImageView three =  (ImageView)findViewById(R.id.jsq6);
        three.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"3");
                    }
                }
        );

        //4
        ImageView four =  (ImageView)findViewById(R.id.jsq10);
        four.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"4");
                    }
                }
        );

        //5
        ImageView five =  (ImageView)findViewById(R.id.jsq11);
        five.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"5");
                    }
                }
        );

        //6
        ImageView six =  (ImageView)findViewById(R.id.jsq8);
        six.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"6");
                    }
                }
        );

        //7
        ImageView seven =  (ImageView)findViewById(R.id.jsq12);
        seven.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"7");
                    }
                }
        );

        //8
        ImageView eight =  (ImageView)findViewById(R.id.jsq13);
        eight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"8");
                    }
                }
        );

        //9
        ImageView nine =  (ImageView)findViewById(R.id.jsq14);
        nine.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"9");
                    }
                }
        );

        //0
        ImageView zero =  (ImageView)findViewById(R.id.jsq9);
        zero.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,"0");
                    }
                }
        );

        //.
        ImageView dot =  (ImageView)findViewById(R.id.jsq15);
        dot.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        editable.insert(start,".");
                    }
                }
        );

        //c
        ImageView clear =  (ImageView)findViewById(R.id.jsq16);
        clear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        edit.setText("");
                    }
                }
        );

        //del
        ImageView delete =  (ImageView)findViewById(R.id.jsq7);
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        int start = edit.getSelectionStart();
                        if (editable != null && editable.length() > 0) {
                            editable.delete(start - 1, start);
                        }
                    }
                }
        );

        //add
        ImageView add =  (ImageView)findViewById(R.id.jsq18);
        add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        if (editable.equals("")){
                            return;
                        }

                        int start = edit.getSelectionStart();
                        editable.insert(start,"+");
                        firstNum = editable.toString();
                        code = "add";
                    }
                }
        );

        //subtraction
        ImageView subtraction =  (ImageView)findViewById(R.id.jsq17);
        subtraction.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        if (editable.equals("")){
                            return;
                        }

                        int start = edit.getSelectionStart();
                        editable.insert(start,"-");
                        firstNum = editable.toString();
                        code = "subtraction";
                    }
                }
        );

        //equal
        ImageView equal =  (ImageView)findViewById(R.id.jsq19);
        equal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Editable editable = edit.getText();
                        String textVal = editable.toString();
                        String retVal = "";

                        if (textVal.equals("")){
                            return;
                        }

                        if (textVal.indexOf("+") > 0){
                            String[] vals = textVal.split("\\+");
                            if (vals[0].matches("[0-9]+") && vals[1].matches("[0-9]+")){
                                retVal = String.valueOf(Integer.valueOf(vals[0]) + Integer.valueOf(vals[1]));
                            }else{
                                retVal = String.valueOf(Float.valueOf(vals[0]) + Float.valueOf(vals[1]));
                            }
                        }else if (textVal.indexOf("-") > 0){
                            String[] vals = textVal.split("-");
                            if (vals[0].matches("[0-9]+") && vals[1].matches("[0-9]+")){
                                retVal = String.valueOf(Integer.valueOf(vals[0]) - Integer.valueOf(vals[1]));
                            }else{
                                retVal = String.valueOf(Float.valueOf(vals[0]) - Float.valueOf(vals[1]));
                            }
                        }
                        edit.setText(String.valueOf(retVal));
                        edit.setSelection(edit.getText().length());
                    }
                }
        );

        //扫描二维码
        ImageView scan =  (ImageView)findViewById(R.id.jsq21);
        scan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.putExtra("total_fee", edit.getText().toString());
                        intent.setClass(JSQActivity.this, CaptureActivity.class);
                        startActivity(intent);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                }
        );

        //生成二维码
        ImageView qrcode =  (ImageView)findViewById(R.id.jsq20);
        qrcode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.putExtra("total_fee", edit.getText().toString());
                        intent.setClass(JSQActivity.this, FKMActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    void getSum(){

    }
}
