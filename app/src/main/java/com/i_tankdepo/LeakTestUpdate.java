package com.i_tankdepo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.i_tankdepo.Constants.GlobalConstants;

/**
 * Created by Admin on 12/22/2016.
 */

public class LeakTestUpdate extends CommonActivity{
    private ImageView menu,iv_back,add_new_heating;
    private TextView tv_toolbarTitle,leakTest_text,tv_heat_refresh,text1,text2;
    private Button heat_home,heat_refresh,bt_heating,cleaning,inspection,leakTest;
    private LinearLayout LL_heat_submit;
    private RelativeLayout RL_heating,RL_Repair;
    private EditText ed_remarks,ed_relief_value1,ed_relief_value2,ed_press_guage1,ed_press_guage2,ref_no,ed_testDate;
    private String Cust_Name,Equip_NO,Type,NoofTimesGenerated,TestDate,RevisionNo,relief_value1,relief_value2,pressureGauge1,pressureGauge2,
            shellTest,steamTubeTest,remark;
    private  Switch switch_shellTest,switch_steam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaktest_update);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu=(ImageView)findViewById(R.id.iv_menu) ;
        iv_back = (ImageView)findViewById(R.id.iv_back);
        add_new_heating = (ImageView)findViewById(R.id.im_add);
        menu.setVisibility(View.GONE);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Leak Test Update");

        heat_home = (Button)findViewById(R.id.heat_home);
        heat_refresh = (Button)findViewById(R.id.heat_refresh);

        bt_heating = (Button)findViewById(R.id.heating);
        bt_heating.setVisibility(View.GONE);
        cleaning = (Button)findViewById(R.id.cleaning);
        inspection = (Button)findViewById(R.id.inspection);
        leakTest = (Button)findViewById(R.id.leakTest);
        leakTest.setVisibility(View.GONE);
        inspection.setVisibility(View.GONE);
        cleaning.setVisibility(View.GONE);
        leakTest_text = (TextView)findViewById(R.id.tv_heating);
        leakTest_text.setText("Add New");
        LL_heat_submit = (LinearLayout)findViewById(R.id.LL_heat_submit);

        tv_heat_refresh = (TextView)findViewById(R.id.tv_heat_refresh);
        tv_heat_refresh.setText("Reset");

        RL_heating =(RelativeLayout)findViewById(R.id.RL_heating);
        RL_Repair =(RelativeLayout)findViewById(R.id.RL_Repair);
        RL_Repair.setVisibility(View.GONE);

        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        ed_testDate = (EditText)findViewById(R.id.ed_testDate);
        ref_no = (EditText)findViewById(R.id.ref_no);
        ed_relief_value1 = (EditText)findViewById(R.id.ed_relief_value1);
        ed_relief_value2 = (EditText)findViewById(R.id.ed_relief_value2);
        ed_press_guage1 = (EditText)findViewById(R.id.ed_press_guage1);
        ed_press_guage2 = (EditText)findViewById(R.id.ed_press_guage2);
        ed_remarks = (EditText)findViewById(R.id.ed_remarks);
        switch_shellTest = (Switch)findViewById(R.id.switch_shellTest);
        switch_steam = (Switch)findViewById(R.id.switch_steam);

        heat_refresh.setOnClickListener(this);
        heat_home.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        Cust_Name = GlobalConstants.customer_name;
        Equip_NO = GlobalConstants.equipment_no;
        Type = GlobalConstants.type;
        NoofTimesGenerated = GlobalConstants.no_of_tms_granted;
        TestDate = GlobalConstants.TestDate;
        RevisionNo = GlobalConstants.revisionNo;
        relief_value1 = GlobalConstants.RLFVLV1;
        relief_value2 = GlobalConstants.RLFVLV2;
        pressureGauge1 = GlobalConstants.PG1;
        pressureGauge2 = GlobalConstants.PG2;
        remark = GlobalConstants.remark;
        shellTest = GlobalConstants.SHLL_TST_BT;
        steamTubeTest = GlobalConstants.STM_TB_TST_BT;

        text1.setText(Cust_Name+" , "+Equip_NO+" , "+Type);
        text2.setText(NoofTimesGenerated);
        ed_testDate.setText(TestDate);
        ed_relief_value1.setText(relief_value1);
        ed_relief_value2.setText(relief_value2);
        ed_press_guage1.setText(pressureGauge1);
        ed_press_guage2.setText(pressureGauge2);
        ed_remarks.setText(remark);


    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.heat_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.heat_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
