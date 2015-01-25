package com.sanguo.payment.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.sanguo.payment.R;
import com.sanguo.payment.alipay.config.Config;
import com.sanguo.payment.zxing.activity.CaptureActivity;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    EditText edit;
    KeyboardView keyboardView;
    AlertDialog dialog = null;
    ImageView img = null;
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit);
        edit.setInputType(InputType.TYPE_NULL);

        keyboardView = (KeyboardView)findViewById(R.id.keyboard_view);
        keyboardView.setKeyboard(new Keyboard(this, R.layout.keyboard));
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);

        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Editable editable = edit.getText();
                int start = edit.getSelectionStart();
                if (primaryCode == Keyboard.KEYCODE_CANCEL) {
                    //hideKeyboard();
                } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
                    if (editable != null && editable.length() > 0) {
                        editable.delete(start - 1, start);
                    }
                } else if (primaryCode == 12) {
                    edit.setText("");
                } else if (primaryCode == 13) {
                    getXml(edit.getText().toString());
                } else if (primaryCode == 14) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CaptureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                } else {
                    editable.insert(start, Character.toString((char) primaryCode));
                }
            }

            @Override
            public void onPress(int primaryCode) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onRelease(int primaryCode) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onText(CharSequence text) {
                // TODO Auto-generated method stub

            }

            @Override
            public void swipeDown() {
                // TODO Auto-generated method stub

            }

            @Override
            public void swipeLeft() {
                // TODO Auto-generated method stub

            }

            @Override
            public void swipeRight() {
                // TODO Auto-generated method stub

            }

            @Override
            public void swipeUp() {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();

                    Map<String, String> sParaTemp = new HashMap<String, String>();
                    sParaTemp.put("service", "alipay.acquire.createandpay");
                    sParaTemp.put("partner", Config.partner);
                    sParaTemp.put("seller_email", Config.seller_email);
                    sParaTemp.put("out_trade_no", String.valueOf(System.currentTimeMillis()));
                    sParaTemp.put("subject", "三国淘园线下支付");
                    sParaTemp.put("total_fee", edit.getText().toString());
                    sParaTemp.put("product_code", "BARCODE_PAY_OFFLINE");
                    sParaTemp.put("dynamic_id_type", "qrcode");
                    sParaTemp.put("dynamic_id", bundle.getString("result"));
                    sParaTemp.put("_input_charset", Config.input_charset);

                    dialog = new AlertDialog.Builder(MainActivity.this)
                            .setPositiveButton("确定", null)
                            .create();

                    CreateAndPay pay = new CreateAndPay(dialog);
                    pay.execute(sParaTemp);
                }
                break;
        }

    }

    //获取二维码
    public void getXml(String total_fee) {
        String out_trade_no = String.valueOf(System.currentTimeMillis());

        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "alipay.acquire.precreate");
        sParaTemp.put("partner", Config.partner);
        sParaTemp.put("_input_charset", Config.input_charset);
        sParaTemp.put("seller_email", Config.seller_email);
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", "三国淘园线下支付");
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("product_code", "QR_CODE_OFFLINE");
        //sParaTemp.put("dynamic_id_type", dynamic_id_type);
        //sParaTemp.put("dynamic_id", dynamic_id);

        dialog = new AlertDialog.Builder(MainActivity.this)
                .setPositiveButton("确定", null)
                .create();
        img = new ImageView(MainActivity.this);
        GetQrcode getCode = new GetQrcode(dialog, img);
        getCode.execute(sParaTemp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
