package com.i_tankdepo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Admin on 1/4/2017.
 */

public class GeneralReport extends CommonActivity {


    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView menu, im_down, im_up, im_heat_close, im_heat_ok, iv_back,iv_changeOfStatus;
    private TextView tv_date,tv_createdBy,tv_toolbarTitle,tv_heat_refresh;
    private Button bt_general,bt_customer,bt_type,bt_all,status_home,status_refresh,run_report;
    private LinearLayout LL_status_submit,LL_run;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        menu.setVisibility(View.GONE);

        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Reports");
        tv_heat_refresh  = (TextView)findViewById(R.id.tv_heat_refresh);
        tv_heat_refresh.setText("Reset");
        tv_date  = (TextView)findViewById(R.id.tv_date);
        tv_createdBy  = (TextView)findViewById(R.id.tv_createdBy);

        bt_general = (Button)findViewById(R.id.bt_general);
        bt_customer = (Button)findViewById(R.id.bt_customer);
        bt_type = (Button)findViewById(R.id.bt_type);
        bt_all = (Button)findViewById(R.id.bt_all);
        status_home = (Button)findViewById(R.id.status_home);
        status_refresh = (Button)findViewById(R.id.status_refresh);
        run_report = (Button)findViewById(R.id.run_report);
        LL_status_submit = (LinearLayout)findViewById(R.id.LL_status_submit);
        LL_run = (LinearLayout)findViewById(R.id.LL_run);
        LL_run.setAlpha(0.5f);
        LL_run.setClickable(false);
        LL_status_submit.setVisibility(View.GONE);

        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);



        iv_changeOfStatus.setOnClickListener(this);
        bt_general.setOnClickListener(this);
        bt_customer.setOnClickListener(this);
        bt_type.setOnClickListener(this);
        bt_all.setOnClickListener(this);
        status_home.setOnClickListener(this);
        status_refresh.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_changeOfStatus:
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.status_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.status_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.bt_type:
                finish();
                startActivity(new Intent(getApplicationContext(),TypeReport.class));
                break;
            case R.id.bt_customer:
                finish();
                startActivity(new Intent(getApplicationContext(),CustomerReport.class));
                break;
            case R.id.bt_all:
                finish();
                startActivity(new Intent(getApplicationContext(),AllReport.class));
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
