package com.hangzhou.hz890.module_b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zyf.fwms.commonlibrary.base.BaseActivity;
import com.zyf.fwms.commonlibrary.base.BaseApplication;
import com.zyf.fwms.commonlibrary.utils.Constants;

@Route(path = Constants.BA)
public class BActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }
}
