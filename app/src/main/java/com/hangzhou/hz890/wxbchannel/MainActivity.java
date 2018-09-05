package com.hangzhou.hz890.wxbchannel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zyf.fwms.commonlibrary.base.BaseActivity;
import com.zyf.fwms.commonlibrary.utils.UIHelper;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.enterA();
            }
        });
        findViewById(R.id.bt_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.enterB();
            }
        });

        setNeedTransition(false);
    }
}
