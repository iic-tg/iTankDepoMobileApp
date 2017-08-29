package com.i_tankdepo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.CustomerDropdownBean;
import com.i_tankdepo.Beanclass.Equipment_Info_TypeDropdownBean;
import com.i_tankdepo.Beanclass.Image_Bean;
import com.i_tankdepo.Beanclass.LineItem_Bean;
import com.i_tankdepo.Beanclass.RepairBean;
import com.i_tankdepo.Constants.ConstantValues;
import com.i_tankdepo.Constants.GlobalConstants;
import com.i_tankdepo.adapter.SpinnerCustomAdapter;
import com.i_tankdepo.customcomponents.CustomSpinner;
import com.i_tankdepo.helper.ServiceHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Metaplore on 10/18/2016.
 */

public class AddRepair_Approvel_Details_MySubmit extends CommonActivity implements View.OnClickListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView iv_changeOfStatus,im_original_est_endDate,im_remark,menu, im_down, im_up, im_heat_close, im_heat_ok, im_skip,iv_back;


    private TextView tv_toolbarTitle, tv_search_options,no_data;
    LinearLayout LL_hole, LL_heat_submit,LL_search_Value,LL_heat;

    private ProgressDialog progressDialog;

    private Intent mServiceIntent;

    private Button heat_refresh, heat_home, heat_submit,heating,cleaning,inspection;
    private FragmentActivity mycontaxt;
    private TextView next_test_type_code,customer_name,tv_Approval_Date,tv_Estimation_date,tv_Approval_RefNo,equipment_no,eqip_type;
    private Button LL_Line_iteam,LL_attachments,LL_summary;
    private Spinner sp_tarif_group,sp_repair_type,sp_tarif_code;
    private CustomSpinner sp_invoicing_party;
    private ArrayList<Equipment_Info_TypeDropdownBean> dropdown_MoreInfo_arraylist;
    private Equipment_Info_TypeDropdownBean moreInfo_DropdownBean;
    private String infoId,infoCode;
    ArrayList<String> dropdown_MoreInfo_list = new ArrayList<>();
    List<String> Cargo_ID = new ArrayList<>();
    List<String> Cargo_Code = new ArrayList<>();
    List<String> Cargo_Description = new ArrayList<>();
    private Spinner sp_last_test_type;
    private EditText edit_estimate_date,ed_estimate_date,ed_next_type;
    private ArrayList<Object> dropdown_customer_list;
    private ArrayList<String> worldlist;
    private ArrayList<String> Invoice_worldlist;
    private ArrayList<CustomerDropdownBean> CustomerDropdownArrayList;
    private ArrayList<CustomerDropdownBean> CustomerDropdown_Invoice_ArrayList;
    private CustomerDropdownBean customer_DropdownBean;
    private String RepairType_Name,RepairType_Code;
    List<String> Cust_name = new ArrayList<>();
    List<String> Cust_code = new ArrayList<>();
    private String get_repair_type,invoice_name,get_sp_last_test_type,get_sp_last_test_type_id,get_sp_repairtype_code,repairType_name,get_sp_invoice_code="0";
    private EditText edit_original_est_date,ed_original_est_date,ed_status,ed_party_approval_refNo,edit_last_testDate,ed_approval_refNo,edit_repair_type,ed_repair_type,edit_invoicing_party,ed_invoicing_party;
    private ImageView im_next_testDate,im_last_testDate,im_approvel_date,im_endDate;
    private Switch switch_heating;
    private EditText ed_approval_date,edit_next_test_date,ed_next_test_date,ed_remarks,ed_labor_rate,ed_validate_period_for_test,ed_last_survey,ed_last_testDate;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    private String curTime,systemDate;
    private boolean im_estimate=false,ed_estimate=false,
            im_original_date=false,ed_original_date=false,
            im_appr_date=false,ed_appr_date=false,
            im_next_test_date=false,ed_nxt_test_date=false,
            last_test_date=false,im_last_test_date=false;
    private String get_cleanline_bets,get_est_date,get_status,get_next_test_type_id,get_original_est_date,get_next_test_type,get_last_survey,get_validate_period_for_test,get_labor_rate,
            get_ed_remarks,get_next_test_date,get_ed_last_testDate,get_ed_party_approval_refNo,get_ed_approval_refNo;
    private LinearLayout LL_Line;
    private Button LL_add_details;
    private LinearLayout LL_add_details_back;
    private ArrayList<RepairBean> repair_arraylist;
    private LineItem_Bean lineitem_bean;
    private String get_cust,get_eqp_no,get_indate,get_previous_cargo,get_repair_type_cd,
            get_repair_type_id,get_laststatus_date,get_validate_period_test,get_lbr_rate,get_nexttest_date,
            get_lst_survey,get_last_test_type,get_nexttest_type,get_last_test_date,get_rematk,get_invoice_prty_cd
            ,get_invoice_prty_id,get_revision_no,get_cust_app_ref_no,get_approval_date,get_invoice_prty_name,get_gi_trans_no,
            get_repair_est_id,get_party_app_ref_no,get_survey_name,get_survey_completion_date,get_rep_status,get_repair_est_no;
    private int totel_amount=0;
     String add_detail_jsonobject;
    private TextView tv_amount;
    private String customer_Id;
    private ArrayList<Image_Bean> encodeArray;
    private String Line_item_Json;
    private TextView repair_estimate_text;
    private Button repair_completion,repair_estimate,repair_approval,survey_completion;
    private RelativeLayout RL_heating,RL_Repair;
    private ImageView line_item,more_info;
    private ImageView iv_send_mail;
    private Button notification_text;
    private TextView tv_invoice_party;
    private int Invoice_party;
    private String In_date;
    private Date date1;
    private String ActivityDate;
    private Date Activity_date1;
    private int invoice_count=0;
    private String est_id;
    private String date;
    private SharedPreferences.Editor editor;
    private String status;
    private String totale_amount;
    private int invoi_count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_repair_details_approvel);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        In_date= GlobalConstants.indate;

        customer_Id= GlobalConstants.customer_Id;
        add_detail_jsonobject= GlobalConstants.add_detail_jsonobject;
        encodeArray= GlobalConstants.multiple_encodeArray;
        Line_item_Json=GlobalConstants.Line_item_Json;
        repair_estimate_text = (TextView)findViewById(R.id.tv_heating);
        repair_estimate_text.setText("Repair Approval");
        sp_repair_type=(Spinner)findViewById(R.id.sp_repair_type);
        sp_invoicing_party=(CustomSpinner)findViewById(R.id.sp_invoicing_party);

        menu = (ImageView) findViewById(R.id.iv_menu);
        im_remark = (ImageView) findViewById(R.id.im_remark);
        im_remark.setOnClickListener(this);
        im_original_est_endDate = (ImageView) findViewById(R.id.im_original_est_endDate);
        iv_changeOfStatus = (ImageView) findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);
        iv_changeOfStatus.setVisibility(View.GONE);
        more_info = (ImageView) findViewById(R.id.more_info);
        line_item = (ImageView) findViewById(R.id.line_item);
        line_item.setOnClickListener(this);
        iv_send_mail = (ImageView) findViewById(R.id.iv_send_mail);
        iv_send_mail.setOnClickListener(this);
        more_info.setOnClickListener(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        im_skip = (ImageView) findViewById(R.id.im_skip);
        im_skip.setOnClickListener(this);
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        menu.setVisibility(View.GONE);
        switch_heating=(Switch)findViewById(R.id.switch_heating);
        sp_last_test_type=(Spinner)findViewById(R.id.sp_last_testtype);
        ed_next_type=(EditText)findViewById(R.id.ed_next_type);
        ed_estimate_date=(EditText)findViewById(R.id.ed_estimate_date);
        ed_estimate_date.setText(In_date);
        edit_estimate_date=(EditText)findViewById(R.id.edit_estimate_date);
        ed_last_testDate=(EditText)findViewById(R.id.ed_last_testDate);
        ed_last_survey=(EditText)findViewById(R.id.ed_last_survey);
        ed_validate_period_for_test=(EditText)findViewById(R.id.ed_validate_period_for_test);
        ed_labor_rate=(EditText)findViewById(R.id.ed_labor_rate);
        ed_remarks=(EditText)findViewById(R.id.ed_remarks);
        ed_validate_period_for_test=(EditText)findViewById(R.id.ed_validate_period_for_test);
        ed_next_test_date=(EditText)findViewById(R.id.ed_next_test_date);
        edit_next_test_date=(EditText)findViewById(R.id.edit_next_test_date);
        im_next_testDate=(ImageView) findViewById(R.id.im_next_testDate);
        ed_approval_date=(EditText) findViewById(R.id.ed_approval_date);
        ed_approval_date.setOnClickListener(this);
        notification_text = (Button) findViewById(R.id.notification_text);
        ed_repair_type = (EditText) findViewById(R.id.ed_repair_type);
        edit_repair_type = (EditText) findViewById(R.id.edit_repair_type);
        ed_approval_refNo = (EditText) findViewById(R.id.ed_approval_refNo);
        ed_approval_refNo.setText(GlobalConstants.Cust_AppRef);
        edit_last_testDate = (EditText) findViewById(R.id.edit_last_testDate);
        ed_party_approval_refNo = (EditText) findViewById(R.id.ed_party_approval_refNo);
        ed_status = (EditText) findViewById(R.id.ed_status);
        ed_original_est_date = (EditText) findViewById(R.id.ed_original_est_date);
        edit_original_est_date = (EditText) findViewById(R.id.edit_original_est_date);
        im_endDate = (ImageView) findViewById(R.id.im_endDate);
        im_approvel_date = (ImageView) findViewById(R.id.im_approvel_date);
        im_approvel_date.setOnClickListener(this);
        im_last_testDate = (ImageView) findViewById(R.id.im_last_testDate);
        ed_invoicing_party = (EditText) findViewById(R.id.ed_invoicing_party);
        edit_invoicing_party = (EditText) findViewById(R.id.edit_invoicing_party);
        ed_repair_type.setOnClickListener(this);
        LL_attachments=(Button)findViewById(R.id.attachments);
        RL_heating =(RelativeLayout)findViewById(R.id.RL_heating);
        RL_Repair =(RelativeLayout)findViewById(R.id.RL_Repair);
        RL_Repair.setOnClickListener(this);
        RL_heating.setVisibility(View.GONE);
        if(GlobalConstants.from.equalsIgnoreCase("MySubmitRepair")|| GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                ||GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                ||GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                ||GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

        }else
        {
            ed_estimate_date.setOnClickListener(this);

        }

        im_original_est_endDate.setOnClickListener(this);

        ed_last_testDate.setOnClickListener(this);
        im_last_testDate.setOnClickListener(this);

//        ed_next_test_date.setOnClickListener(this);
//        im_next_testDate.setOnClickListener(this);

        ed_original_est_date.setOnClickListener(this);
        im_original_est_endDate.setOnClickListener(this);
        LL_Line_iteam = (Button) findViewById(R.id.Line_iteam);
        LL_Line = (LinearLayout) findViewById(R.id.LL_Line_iteam);
        LL_Line_iteam.setOnClickListener(this);
        LL_add_details = (Button) findViewById(R.id.add_details);


        LL_add_details_back = (LinearLayout) findViewById(R.id.LL_add_details);
        LL_add_details_back.setBackgroundColor(getResources().getColor(R.color.submit));

        LL_add_details.setOnClickListener(this);
        LL_attachments = (Button) findViewById(R.id.attachments);
        LL_attachments.setOnClickListener(this);

        LL_summary=(Button)findViewById(R.id.summary);
        LL_summary.setOnClickListener(this);
        LL_summary.setVisibility(View.GONE);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_amount.setOnClickListener(this);
        tv_amount.setText(GlobalConstants.totale_amount);

        heat_home = (Button) findViewById(R.id.heat_home);
        heat_refresh = (Button) findViewById(R.id.heat_refresh);
        heat_submit = (Button) findViewById(R.id.heat_submit);
        LL_heat_submit = (LinearLayout) findViewById(R.id.LL_heat_submit);

        LL_heat_submit.setClickable(false);
        LL_heat = (LinearLayout)findViewById(R.id.LL_heat);
        LL_heat.setOnClickListener(this);
        LL_heat_submit.setAlpha(0.5f);
        LL_heat_submit.setClickable(false);

//        tabLayout.setupWithViewPager(viewPager);

        try {
            if(GlobalConstants.attach_count!=null)
            {
                notification_text.setText((GlobalConstants.attach_count));

            }else
            {
//                notification_text.setText((GlobalConstants.attach_count));
                notification_text.setText(String.valueOf(encodeArray.size()));

            }

        } catch (Exception e) {
            notification_text.setText((GlobalConstants.attach_count));
        }
        // tabLayout.clearOnTabSelectedListeners();
        tv_invoice_party = (TextView)findViewById(R.id.tv_invoice_party);

        Invoice_party=GlobalConstants.Invoice_party;
        String surName = getColoredSpanned("*","#cb0da5");

        if(Invoice_party>0)
        {
            String lable_invoice_party = getColoredSpanned("Invoice Party","#bbbbbb");
            tv_invoice_party.setText(Html.fromHtml(lable_invoice_party+" "+surName));
            ed_invoicing_party.setOnClickListener(this);

        }else
        {
            sp_invoicing_party.setEnabled(false);
        }
        if(cd.isConnectingToInternet())
        {
            new Create_GateIn_moreInfo_list_details().execute();
            new Repait_Type_details().execute();
            new InvoiceParty_details().execute();
        }else
        {
            shortToast(getApplicationContext(), "Please check your Internet Connection.");
        }
        heating = (Button)findViewById(R.id.heating);
        cleaning = (Button)findViewById(R.id.cleaning);
        inspection = (Button)findViewById(R.id.inspection);
        inspection.setVisibility(View.GONE);
        cleaning.setVisibility(View.GONE);

        heat_refresh.setOnClickListener(this);
        heat_home.setOnClickListener(this);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);

        Log.i("customer_name",GlobalConstants.customer_name);
        customer_name = (TextView) findViewById(R.id.text1);
        next_test_type_code = (TextView) findViewById(R.id.code);
        customer_name.setText(GlobalConstants.customer_name + " , " +", "+GlobalConstants.type+", "+ GlobalConstants.equipment_no+","+ GlobalConstants.repairEstimateNo);
        equipment_no = (TextView) findViewById(R.id.text2);
        tv_Approval_RefNo = (TextView) findViewById(R.id.tv_Approval_RefNo);
        tv_Estimation_date = (TextView) findViewById(R.id.tv_Estimation_date);
        tv_Approval_Date = (TextView) findViewById(R.id.tv_Approval_Date);

        String lable_Approval_RefNo = getColoredSpanned("Cust App Ref No","#bbbbbb");
        String lable_Estimation_date = getColoredSpanned("Estimation_date","#bbbbbb");
        String lable_Approval_Date = getColoredSpanned("Approval_Date","#bbbbbb");
        tv_Approval_RefNo.setText(Html.fromHtml(lable_Approval_RefNo+" "+surName));
        tv_Estimation_date.setText(Html.fromHtml(lable_Estimation_date+" "+surName));
        tv_Approval_Date.setText(Html.fromHtml(lable_Approval_Date+" "+surName));
//        equipment_no.setText(GlobalConstants.equipment_no);
        eqip_type = (TextView) findViewById(R.id.text3);
//        eqip_type.setText(GlobalConstants.previous_cargo);
        tv_toolbarTitle.setText("Add Details");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        second = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        //12 hour format
        int hour = c.get(Calendar.HOUR);
        //24 hour format
        int hourofday = c.get(Calendar.HOUR_OF_DAY);
        SimpleDateFormat time = new SimpleDateFormat("hh:mm");
        curTime = time.format(new Date());
        systemDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());



        //  ed_time.setText(curTime);
//        ed_next_test_date.setText(systemDate);
        repair_estimate = (Button)findViewById(R.id.repair_estimate);
        repair_estimate.setOnClickListener(this);
        repair_approval = (Button)findViewById(R.id.repair_approval);
        repair_approval.setVisibility(View.GONE);
        repair_completion = (Button)findViewById(R.id.repair_completion);
        repair_completion.setVisibility(View.GONE);
        survey_completion = (Button)findViewById(R.id.survey_completion);
        survey_completion.setVisibility(View.GONE);
        if(GlobalConstants.from.equalsIgnoreCase("MySubmitRepair")|| GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                ||GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                ||GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                ||GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
            iv_send_mail.setVisibility(View.VISIBLE);
            im_remark.setVisibility(View.VISIBLE);
            ActivityDate=GlobalConstants.ActivityDate;
            try {
                Activity_date1 = new SimpleDateFormat("dd-MM-yyyy").parse(ActivityDate);
            }catch (Exception e)
            {

            }

            new Get_Repair_MySubmit_details().execute();

        }else
        {

            if(!add_detail_jsonobject.equalsIgnoreCase("detail_json") || !add_detail_jsonobject.equals(null))
            {
                try {
                    JSONObject jsonrootObject = new JSONObject(add_detail_jsonobject);
                    ed_estimate_date.setText( jsonrootObject.getString("get_est_date"));
                    ed_repair_type.setText(jsonrootObject.getString("InvoiceParty_name"));
                    jsonrootObject.getString("get_sp_repairtype_code");
                    ed_invoicing_party.setText(jsonrootObject.getString("invoice_name"));
                    jsonrootObject.getString("get_sp_invoice_code");
                    ed_status.setText(jsonrootObject.getString("get_status"));
                    ed_original_est_date.setText(jsonrootObject.getString("get_original_est_date"));;
                    jsonrootObject.getString("get_sp_last_test_type");
                    jsonrootObject.getString("get_sp_last_test_type_id");
                    jsonrootObject.getString("get_next_test_type");
                    jsonrootObject.getString("get_next_test_type_id");
                    ed_last_survey.setText(jsonrootObject.getString("get_last_survey"));
                    ed_next_test_date.setText(jsonrootObject.getString("get_next_test_date"));
                    ed_last_testDate.setText(jsonrootObject.getString("get_ed_last_testDate"));
                    ed_validate_period_for_test.setText(jsonrootObject.getString("get_validate_period_for_test"));
                    ed_approval_refNo.setText(jsonrootObject.getString("get_ed_approval_refNo"));
                    ed_party_approval_refNo.setText(jsonrootObject.getString("get_ed_party_approval_refNo"));
                    jsonrootObject.getString("get_labor_rate");
                    ed_remarks.setText(jsonrootObject.getString("get_ed_remarks"));

                }catch (Exception e)
                {

                }

            }
        }



        ed_original_est_date.setText(systemDate);
        ed_last_testDate.setText(GlobalConstants.last_testDate);
        ed_approval_date.setText(systemDate);


        get_cleanline_bets="False";
        switch_heating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    get_cleanline_bets="True";
                }else
                {
                    get_cleanline_bets="False";

                }
            }
        });

        sp_last_test_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                get_sp_last_test_type = sp_last_test_type.getSelectedItem().toString();
                get_sp_last_test_type_id=dropdown_MoreInfo_arraylist.get(i).getId();
                if(i == 0){
                    ed_next_type.setText(dropdown_MoreInfo_arraylist.get(1).getCode());
                    next_test_type_code.setText(dropdown_MoreInfo_arraylist.get(1).getId());
                }else if(i==1)
                {
                    ed_next_type.setText(dropdown_MoreInfo_arraylist.get(0).getCode());
                    next_test_type_code.setText(dropdown_MoreInfo_arraylist.get(0).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_repair_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                repairType_name = sp_repair_type.getSelectedItem().toString();
                get_sp_repairtype_code = CustomerDropdownArrayList.get(i).getCode();
                ed_repair_type.setText(repairType_name);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_invoicing_party.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Invoice_party > 0) {
                    get_sp_invoice_code = CustomerDropdown_Invoice_ArrayList.get(i).getCode();
                    invoice_name = CustomerDropdown_Invoice_ArrayList.get(i).getName();
                    ed_invoicing_party.setText(invoice_name);
                    GlobalConstants.invoice_PartyCD=invoice_name;
                    GlobalConstants.invoice_PartyID=get_sp_invoice_code;
                } else {
                    if (invoi_count != 0) {
                        get_sp_invoice_code = CustomerDropdown_Invoice_ArrayList.get(i).getCode();
                        invoice_name = CustomerDropdown_Invoice_ArrayList.get(i).getName();
                        ed_invoicing_party.setText(invoice_name);
                        GlobalConstants.invoice_PartyCD=invoice_name;
                        GlobalConstants.invoice_PartyID=get_sp_invoice_code;
                    }
                    invoi_count++;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(
                        Gravity.START))
                    drawer.closeDrawer(Gravity.END);
                else
                    drawer.openDrawer(Gravity.START);
            }
        });

        try {
            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(In_date);
        }catch (Exception e)
        {

        }

    }





    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heat_home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.heat_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.RL_Repair:
                encodeArray=new ArrayList();
                GlobalConstants.multiple_encodeArray=encodeArray;
                finish();
                startActivity(new Intent(getApplicationContext(), Repair_MainActivity.class));
                break;
            case R.id.line_item:
                LL_Line_iteam.performClick();
            break;
            case R.id.more_info:

                popUp_equipment_info(GlobalConstants.equipment_no,GlobalConstants.status,GlobalConstants.status_id,GlobalConstants.previous_cargo,"","","","");


                break;
            case R.id.im_remark:
                get_ed_remarks=ed_remarks.getText().toString();
                popUp_remarks(get_ed_remarks);
                break;
            case R.id.repair_estimate:
                finish();
                startActivity(new Intent(getApplicationContext(),Repair_MainActivity.class));
                break;
            case R.id.Line_iteam:

                get_est_date=ed_estimate_date.getText().toString();
                //  InvoiceParty_name,get_sp_repairtype_code
                //  invoice_name,get_sp_invoice_code
                get_status=ed_status.getText().toString();
                get_original_est_date=ed_original_est_date.getText().toString();
//                get_sp_last_test_type
                get_next_test_type=ed_next_type.getText().toString();
                get_approval_date=ed_approval_date.getText().toString();
                get_next_test_type_id=next_test_type_code.getText().toString();
                get_last_survey=ed_last_survey.getText().toString();
                get_validate_period_for_test=ed_validate_period_for_test.getText().toString();
                get_labor_rate=ed_labor_rate.getText().toString();
                get_ed_remarks=ed_remarks.getText().toString();
                get_next_test_date=ed_next_test_date.getText().toString();
                get_ed_last_testDate=ed_last_testDate.getText().toString();
                get_ed_approval_refNo=ed_approval_refNo.getText().toString();
                get_ed_party_approval_refNo=ed_party_approval_refNo.getText().toString();
                invoice_name=ed_invoicing_party.getText().toString();
                invoice_name=ed_invoicing_party.getText().toString();
                get_repair_type=ed_repair_type.getText().toString();


//                get_cleanline_bets
                JSONObject obiect=new JSONObject();
                if(Invoice_party>0) {
                    if (ed_invoicing_party.getText().toString().equals("")) {
                      /*  if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {
*/
                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                             date = GlobalConstants.indate;
                            est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            GlobalConstants.Invoice_party=Invoice_party;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            GlobalConstants.indate = date;
                            finish();

                            startActivity(new Intent(getApplicationContext(), Repair_Estimation_Approvel_wizard_MySubmit.class));
//                        }
                    }else
                    {
                        if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {

                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                            date = GlobalConstants.indate;
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            GlobalConstants.Invoice_party=Invoice_party;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.indate = date;
                            finish();

                            startActivity(new Intent(getApplicationContext(), Repair_Estimation_Approvel_wizard_MySubmit.class));
                        }
                    }
                }else
                {
                    if (get_est_date.equals("") || get_status.equals("")) {
                        shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                    } else {

                        try {
                            obiect.put("get_est_date", get_est_date);
                            obiect.put("InvoiceParty_name", invoice_name);
                            obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                            obiect.put("invoice_name", invoice_name);
                            obiect.put("get_repair_type", get_repair_type);
                            if (invoice_name.equals("")) {
                                obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                            } else {
                                obiect.put("get_sp_invoice_code", "0");

                            }
                            obiect.put("get_status", get_status);
                            obiect.put("get_original_est_date", get_original_est_date);
                            obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                            obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                            obiect.put("get_next_test_type", get_next_test_type);
                            obiect.put("get_next_test_type_id", get_next_test_type_id);
                            obiect.put("get_last_survey", get_last_survey);
                            obiect.put("get_next_test_date", get_next_test_date);
                            obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                            obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                            obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                            obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                            obiect.put("get_labor_rate", get_labor_rate);
                            obiect.put("get_ed_remarks", get_ed_remarks);
                            obiect.put("get_approval_date", get_approval_date);

                            obiect.put("get_cleanline_bets", get_cleanline_bets);
                        } catch (Exception e) {

                        }
                        Log.i("obiect", String.valueOf(obiect));
                        if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                            GlobalConstants.from = "MysubmitAddDetails";
                        } else {
                            GlobalConstants.from = "AddDetails";
                        }
                        GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                        GlobalConstants.multiple_encodeArray = encodeArray;
                        GlobalConstants.Line_item_Json = Line_item_Json;
                        date = GlobalConstants.indate;
                        String est_id=GlobalConstants.repair_EstimateID;
                        String reff_no=GlobalConstants.Cust_AppRef;
                        GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                        String est_no=GlobalConstants.repairEstimateNo;
                        String gi_transaction_no= GlobalConstants.gi_trans_no;
                        GlobalConstants.repair_EstimateID=est_id;
                        GlobalConstants.repair_EstimateNo=est_no;
                        GlobalConstants.Invoice_party=Invoice_party;
                        GlobalConstants.gi_trans_no=gi_transaction_no;
                        GlobalConstants.repair_EstimateID = est_id;
                        GlobalConstants.indate = date;
                        finish();

                        startActivity(new Intent(getApplicationContext(), Repair_Estimation_Approvel_wizard_MySubmit.class));
                    }
                }
                break;
            case  R.id.tv_amount:
                LL_summary.performClick();
                break;
            case R.id.im_skip:

                get_est_date=ed_estimate_date.getText().toString();
                //  InvoiceParty_name,get_sp_repairtype_code
                //  invoice_name,get_sp_invoice_code
                get_status=ed_status.getText().toString();
                get_original_est_date=ed_original_est_date.getText().toString();
//                get_sp_last_test_type
                get_next_test_type=ed_next_type.getText().toString();
                get_approval_date=ed_approval_date.getText().toString();
                get_next_test_type_id=next_test_type_code.getText().toString();
                get_last_survey=ed_last_survey.getText().toString();
                get_validate_period_for_test=ed_validate_period_for_test.getText().toString();
                get_labor_rate=ed_labor_rate.getText().toString();
                get_ed_remarks=ed_remarks.getText().toString();
                get_next_test_date=ed_next_test_date.getText().toString();
                get_ed_last_testDate=ed_last_testDate.getText().toString();
                get_ed_approval_refNo=ed_approval_refNo.getText().toString();
                get_ed_party_approval_refNo=ed_party_approval_refNo.getText().toString();
                invoice_name=ed_invoicing_party.getText().toString();
                get_repair_type=ed_repair_type.getText().toString();
//                get_cleanline_bets
                 obiect=new JSONObject();

                if(Invoice_party>0) {
                   if (ed_invoicing_party.getText().toString().equals("")) {
                      /*  if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {*/

                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            String status = GlobalConstants.status;
                            String totale_amount = GlobalConstants.totale_amount;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.status = status;
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            GlobalConstants.repair_EstimateID = est_id;
                            date = GlobalConstants.indate;
                            GlobalConstants.indate = date;
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                            editor.commit();
                            finish();
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                             reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.customer_Id = customer_Id;
                            GlobalConstants.totale_amount = totale_amount;

                            startActivity(new Intent(getApplicationContext(), Attach_Repair_Approvel_MySubmit.class));
//                        }
                    }else
                    {
                        if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {

                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            String status = GlobalConstants.status;
                            String totale_amount = GlobalConstants.totale_amount;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.status = status;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            GlobalConstants.repair_EstimateID = est_id;
                            date = GlobalConstants.indate;
                            GlobalConstants.indate = date;
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                            editor.commit();
                            finish();
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.Line_item_Json = Line_item_Json;

                            GlobalConstants.customer_Id = customer_Id;
                            GlobalConstants.totale_amount = totale_amount;

                            startActivity(new Intent(getApplicationContext(), Attach_Repair_Approvel_MySubmit.class));
                        }
                    }
                }else
                {
                    if (get_est_date.equals("") || get_status.equals("")) {
                        shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                    } else {

                        try {
                            obiect.put("get_est_date", get_est_date);
                            obiect.put("InvoiceParty_name", invoice_name);
                            obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                            obiect.put("invoice_name", invoice_name);
                            obiect.put("get_repair_type", get_repair_type);
                            if (invoice_name.equals("")) {
                                obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                            } else {
                                obiect.put("get_sp_invoice_code", "0");

                            }
                            obiect.put("get_status", get_status);
                            obiect.put("get_original_est_date", get_original_est_date);
                            obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                            obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                            obiect.put("get_next_test_type", get_next_test_type);
                            obiect.put("get_next_test_type_id", get_next_test_type_id);
                            obiect.put("get_last_survey", get_last_survey);
                            obiect.put("get_next_test_date", get_next_test_date);
                            obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                            obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                            obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                            obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                            obiect.put("get_labor_rate", get_labor_rate);
                            obiect.put("get_ed_remarks", get_ed_remarks);
                            obiect.put("get_approval_date", get_approval_date);

                            obiect.put("get_cleanline_bets", get_cleanline_bets);
                        } catch (Exception e) {

                        }
                        Log.i("obiect", String.valueOf(obiect));
                        GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                        String status = GlobalConstants.status;
                        String totale_amount = GlobalConstants.totale_amount;
                        GlobalConstants.totale_amount = totale_amount;
                        GlobalConstants.status = status;
                        String est_id=GlobalConstants.repair_EstimateID;
                        String reff_no=GlobalConstants.Cust_AppRef;
                        GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                        String est_no=GlobalConstants.repairEstimateNo;
                        String gi_transaction_no= GlobalConstants.gi_trans_no;
                        GlobalConstants.repair_EstimateID=est_id;
                        GlobalConstants.repair_EstimateNo=est_no;
                        GlobalConstants.gi_trans_no=gi_transaction_no;
                        GlobalConstants.repair_EstimateID = est_id;
                        date = GlobalConstants.indate;
                        GlobalConstants.indate = date;
                        if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                            GlobalConstants.from = "MysubmitAddDetails";
                        } else {
                            GlobalConstants.from = "AddDetails";
                        }

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                        editor.commit();
                        finish();
                        GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                        GlobalConstants.multiple_encodeArray = encodeArray;
                        GlobalConstants.Line_item_Json = Line_item_Json;

                        GlobalConstants.customer_Id = customer_Id;
                        GlobalConstants.totale_amount = totale_amount;

                        startActivity(new Intent(getApplicationContext(), Attach_Repair_Approvel_MySubmit.class));
                    }
                }


                break;
            case R.id.ed_invoicing_party:
                sp_invoicing_party.performClick();

                break;
            case R.id.ed_repair_type:
                sp_repair_type.performClick();

                break;
            case R.id.attachments:


                get_est_date=ed_estimate_date.getText().toString();
                //  InvoiceParty_name,get_sp_repairtype_code
                //  invoice_name,get_sp_invoice_code
                get_status=ed_status.getText().toString();
                get_original_est_date=ed_original_est_date.getText().toString();
//                get_sp_last_test_type
                get_next_test_type=ed_next_type.getText().toString();
                get_next_test_type_id=next_test_type_code.getText().toString();
                get_last_survey=ed_last_survey.getText().toString();
                get_validate_period_for_test=ed_validate_period_for_test.getText().toString();
                get_labor_rate=ed_labor_rate.getText().toString();
                get_ed_remarks=ed_remarks.getText().toString();
                get_approval_date=ed_approval_date.getText().toString();
                get_next_test_date=ed_next_test_date.getText().toString();
                get_ed_last_testDate=ed_last_testDate.getText().toString();
                get_ed_approval_refNo=ed_approval_refNo.getText().toString();
                get_ed_party_approval_refNo=ed_party_approval_refNo.getText().toString();
                invoice_name=ed_invoicing_party.getText().toString();
                get_repair_type=ed_repair_type.getText().toString();
//                get_cleanline_bets
                 obiect=new JSONObject();

                if(Invoice_party>0) {
                   if (ed_invoicing_party.getText().toString().equals("")) {
                       /* if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {*/
                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            status = GlobalConstants.status;
                            totale_amount = GlobalConstants.totale_amount;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.status = status;
                            date = GlobalConstants.indate;
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.repair_EstimateID = est_id;
                            GlobalConstants.indate = date;
                            editor = sp.edit();
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }

                            editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                            editor.commit();
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.customer_Id = customer_Id;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                            finish();

                            startActivity(new Intent(getApplicationContext(), Attach_Repair_Approvel_MySubmit.class));

//                        }
                    }else
                    {
                        if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {
                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            status = GlobalConstants.status;
                            totale_amount = GlobalConstants.totale_amount;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.status = status;
                            date = GlobalConstants.indate;
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.repair_EstimateID = est_id;
                            GlobalConstants.indate = date;
                            editor = sp.edit();
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }

                            editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                            editor.commit();
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.customer_Id = customer_Id;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                            finish();

                            startActivity(new Intent(getApplicationContext(), Attach_Repair_Approvel_MySubmit.class));

                        }
                    }
                }else{
                    if (get_est_date.equals("") || get_status.equals("")) {
                        shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                    } else {
                        try {
                            obiect.put("get_est_date", get_est_date);
                            obiect.put("InvoiceParty_name", invoice_name);
                            obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                            obiect.put("invoice_name", invoice_name);
                            obiect.put("get_repair_type", get_repair_type);
                            if (invoice_name.equals("")) {
                                obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                            } else {
                                obiect.put("get_sp_invoice_code", "0");

                            }
                            obiect.put("get_status", get_status);
                            obiect.put("get_original_est_date", get_original_est_date);
                            obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                            obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                            obiect.put("get_next_test_type", get_next_test_type);
                            obiect.put("get_next_test_type_id", get_next_test_type_id);
                            obiect.put("get_last_survey", get_last_survey);
                            obiect.put("get_next_test_date", get_next_test_date);
                            obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                            obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                            obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                            obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                            obiect.put("get_labor_rate", get_labor_rate);
                            obiect.put("get_ed_remarks", get_ed_remarks);
                            obiect.put("get_approval_date", get_approval_date);

                            obiect.put("get_cleanline_bets", get_cleanline_bets);
                        } catch (Exception e) {

                        }
                        Log.i("obiect", String.valueOf(obiect));
                        GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                        status = GlobalConstants.status;
                        totale_amount = GlobalConstants.totale_amount;
                        GlobalConstants.totale_amount = totale_amount;
                        GlobalConstants.status = status;
                        date = GlobalConstants.indate;
                        String est_id=GlobalConstants.repair_EstimateID;
                        String est_no=GlobalConstants.repairEstimateNo;
                        String gi_transaction_no= GlobalConstants.gi_trans_no;
                        GlobalConstants.repair_EstimateID=est_id;
                        GlobalConstants.repair_EstimateNo=est_no;
                        GlobalConstants.gi_trans_no=gi_transaction_no;
                        GlobalConstants.repair_EstimateID = est_id;
                        GlobalConstants.indate = date;
                        editor = sp.edit();
                        if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

                            GlobalConstants.from = "MysubmitAddDetails";
                        } else {
                            GlobalConstants.from = "AddDetails";
                        }

                        editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                        editor.commit();
                        GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                        GlobalConstants.multiple_encodeArray = encodeArray;
                        GlobalConstants.customer_Id = customer_Id;
                        GlobalConstants.totale_amount = totale_amount;
                        String reff_no=GlobalConstants.Cust_AppRef;
                        GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                        GlobalConstants.Line_item_Json = Line_item_Json;
                        finish();

                        startActivity(new Intent(getApplicationContext(), Attach_Repair_Approvel_MySubmit.class));

                    }
                }
                break;
            case R.id.summary:
                status=GlobalConstants.status;
                GlobalConstants.status=status;

                get_est_date=ed_estimate_date.getText().toString();
                //  InvoiceParty_name,get_sp_repairtype_code
                //  invoice_name,get_sp_invoice_code
                get_status=ed_status.getText().toString();
                get_original_est_date=ed_original_est_date.getText().toString();
//                get_sp_last_test_type
                get_next_test_type=ed_next_type.getText().toString();
                get_next_test_type_id=next_test_type_code.getText().toString();
                get_last_survey=ed_last_survey.getText().toString();
                get_validate_period_for_test=ed_validate_period_for_test.getText().toString();
                get_labor_rate=ed_labor_rate.getText().toString();
                get_ed_remarks=ed_remarks.getText().toString();
                get_approval_date=ed_approval_date.getText().toString();
                get_next_test_date=ed_next_test_date.getText().toString();
                get_ed_last_testDate=ed_last_testDate.getText().toString();
                get_ed_approval_refNo=ed_approval_refNo.getText().toString();
                get_ed_party_approval_refNo=ed_party_approval_refNo.getText().toString();
                invoice_name=ed_invoicing_party.getText().toString();
                get_repair_type=ed_repair_type.getText().toString();
//                get_cleanline_bets
                obiect=new JSONObject();
                if(Invoice_party>0) {

                   if (ed_invoicing_party.getText().toString().equals("")) {
                       /* if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {*/

                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            status = GlobalConstants.status;
                            GlobalConstants.status = status;
                            totale_amount = GlobalConstants.totale_amount;

                            GlobalConstants.totale_amount = totale_amount;
                            editor = sp.edit();
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }

                            editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                            editor.commit();
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.customer_Id = customer_Id;
                            date = GlobalConstants.indate;
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            GlobalConstants.repair_EstimateID = est_id;
                            GlobalConstants.indate = date;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                            finish();


                            startActivity(new Intent(getApplicationContext(), Repair_Approvel_Summary_MySubmit.class));

//                        }
                    }else
                    {
                        if (get_est_date.equals("") || get_status.equals("")) {
                            shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                        } else {

                            try {
                                obiect.put("get_est_date", get_est_date);
                                obiect.put("InvoiceParty_name", invoice_name);
                                obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                                obiect.put("invoice_name", invoice_name);
                                obiect.put("get_repair_type", get_repair_type);
                                if (invoice_name.equals("")) {
                                    obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                                } else {
                                    obiect.put("get_sp_invoice_code", "0");

                                }
                                obiect.put("get_status", get_status);
                                obiect.put("get_original_est_date", get_original_est_date);
                                obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                                obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                                obiect.put("get_next_test_type", get_next_test_type);
                                obiect.put("get_next_test_type_id", get_next_test_type_id);
                                obiect.put("get_last_survey", get_last_survey);
                                obiect.put("get_next_test_date", get_next_test_date);
                                obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                                obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                                obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                                obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                                obiect.put("get_labor_rate", get_labor_rate);
                                obiect.put("get_ed_remarks", get_ed_remarks);
                                obiect.put("get_approval_date", get_approval_date);

                                obiect.put("get_cleanline_bets", get_cleanline_bets);
                            } catch (Exception e) {

                            }
                            Log.i("obiect", String.valueOf(obiect));
                            status = GlobalConstants.status;
                            GlobalConstants.status = status;
                            totale_amount = GlobalConstants.totale_amount;

                            GlobalConstants.totale_amount = totale_amount;
                            editor = sp.edit();
                            if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                    || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

                                GlobalConstants.from = "MysubmitAddDetails";
                            } else {
                                GlobalConstants.from = "AddDetails";
                            }

                            editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                            editor.commit();
                            GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                            GlobalConstants.multiple_encodeArray = encodeArray;
                            GlobalConstants.customer_Id = customer_Id;
                            date = GlobalConstants.indate;
                            String est_id=GlobalConstants.repair_EstimateID;
                            String est_no=GlobalConstants.repairEstimateNo;
                            String gi_transaction_no= GlobalConstants.gi_trans_no;
                            GlobalConstants.repair_EstimateID=est_id;
                            GlobalConstants.repair_EstimateNo=est_no;
                            GlobalConstants.gi_trans_no=gi_transaction_no;
                            String reff_no=GlobalConstants.Cust_AppRef;
                            GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                            GlobalConstants.repair_EstimateID = est_id;
                            GlobalConstants.indate = date;
                            GlobalConstants.totale_amount = totale_amount;
                            GlobalConstants.Line_item_Json = Line_item_Json;
                            finish();


                            startActivity(new Intent(getApplicationContext(), Repair_Approvel_Summary_MySubmit.class));

                        }
                    }
                }else
                {
                    if (get_est_date.equals("") || get_status.equals("")) {
                        shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                    } else {

                        try {
                            obiect.put("get_est_date", get_est_date);
                            obiect.put("InvoiceParty_name", invoice_name);
                            obiect.put("get_sp_repairtype_code", get_sp_repairtype_code);
                            obiect.put("invoice_name", invoice_name);
                            obiect.put("get_repair_type", get_repair_type);
                            if (invoice_name.equals("")) {
                                obiect.put("get_sp_invoice_code", get_sp_invoice_code);

                            } else {
                                obiect.put("get_sp_invoice_code", "0");

                            }
                            obiect.put("get_status", get_status);
                            obiect.put("get_original_est_date", get_original_est_date);
                            obiect.put("get_sp_last_test_type", get_sp_last_test_type);
                            obiect.put("get_sp_last_test_type_id", get_sp_last_test_type_id);
                            obiect.put("get_next_test_type", get_next_test_type);
                            obiect.put("get_next_test_type_id", get_next_test_type_id);
                            obiect.put("get_last_survey", get_last_survey);
                            obiect.put("get_next_test_date", get_next_test_date);
                            obiect.put("get_ed_last_testDate", get_ed_last_testDate);
                            obiect.put("get_validate_period_for_test", get_validate_period_for_test);
                            obiect.put("get_ed_approval_refNo", get_ed_approval_refNo);
                            obiect.put("get_ed_party_approval_refNo", get_ed_party_approval_refNo);
                            obiect.put("get_labor_rate", get_labor_rate);
                            obiect.put("get_ed_remarks", get_ed_remarks);
                            obiect.put("get_approval_date", get_approval_date);

                            obiect.put("get_cleanline_bets", get_cleanline_bets);
                        } catch (Exception e) {

                        }
                        Log.i("obiect", String.valueOf(obiect));
                        status = GlobalConstants.status;
                        GlobalConstants.status = status;
                        totale_amount = GlobalConstants.totale_amount;

                        GlobalConstants.totale_amount = totale_amount;
                        editor = sp.edit();
                        if (GlobalConstants.from.equalsIgnoreCase("MySubmitRepair") || GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                                || GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {

                            GlobalConstants.from = "MysubmitAddDetails";
                        } else {
                            GlobalConstants.from = "AddDetails";
                        }

                        editor.putString(SP_Add_datails_Json, String.valueOf(obiect));
                        editor.commit();
                        GlobalConstants.add_detail_jsonobject = String.valueOf(obiect);
                        GlobalConstants.multiple_encodeArray = encodeArray;
                        GlobalConstants.customer_Id = customer_Id;
                        date = GlobalConstants.indate;
                        String est_id=GlobalConstants.repair_EstimateID;
                        String est_no=GlobalConstants.repairEstimateNo;
                        String gi_transaction_no= GlobalConstants.gi_trans_no;
                        GlobalConstants.repair_EstimateID=est_id;
                        GlobalConstants.repair_EstimateNo=est_no;
                        GlobalConstants.gi_trans_no=gi_transaction_no;
                        GlobalConstants.repair_EstimateID = est_id;
                        String reff_no=GlobalConstants.Cust_AppRef;
                        GlobalConstants.Cust_AppRef=ed_approval_refNo.getText().toString();
                        GlobalConstants.indate = date;
                        GlobalConstants.totale_amount = totale_amount;
                        GlobalConstants.Line_item_Json = Line_item_Json;
                        finish();


                        startActivity(new Intent(getApplicationContext(), Repair_Approvel_Summary_MySubmit.class));

                    }
                }
                break;
            case R.id.iv_changeOfStatus:
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));

                break;
            case R.id.iv_send_mail:

             /*   GlobalConstants.print_string= String.valueOf(print_object);
                startActivity(new Intent(getApplicationContext(),SendMailActivity.class));*/
                try {
                    String targetPdf = "/sdcard/test.pdf";

                    String sendemail = "";
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{ sendemail});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Repair Estimate");
                    email.setType("message/rfc822");
                    if (targetPdf != null) {
                        email.putExtra(Intent.EXTRA_STREAM, targetPdf);
                    }
                    startActivity(Intent.createChooser(email, "Select Email Client"));



                } catch (Throwable t) {
                    Toast.makeText(this,
                            "Request failed try again: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.ed_estimate_date:
                ed_estimate=true;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_original_date=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.ed_approval_date:
                ed_estimate=false;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_appr_date=true;
                ed_original_date=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_approvel_date:
                im_appr_date=true;
                ed_estimate=false;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_original_date=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_repair_estimation:
                ed_estimate=false;
                im_estimate=true;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_original_date=false;

                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.ed_original_est_date:
                ed_estimate=false;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_original_date=true;


                showDialog(DATE_DIALOG_ID);

                break;
            case R.id.im_original_est_endDate:
                ed_estimate=false;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_original_date=false;
                im_original_date=true;

                showDialog(DATE_DIALOG_ID);

                break;
            case R.id.ed_last_testDate:
                ed_estimate=false;
                last_test_date=true;
                ed_nxt_test_date=false;
                ed_original_date=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_last_testDate:
                ed_estimate=false;
                im_last_test_date=true;
                last_test_date=false;
                ed_nxt_test_date=false;
                ed_original_date=false;

                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.ed_next_test_date:

                ed_estimate=false;
                last_test_date=false;
                ed_nxt_test_date=true;
                ed_original_date=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_next_testDate:
                ed_estimate=false;
                last_test_date=false;
                ed_nxt_test_date=false;
                im_next_test_date=true;
                ed_original_date=false;
                showDialog(DATE_DIALOG_ID);
                break;

        }

    }












    @Override
    public void onPause() {
        super.onPause();

        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    public class Create_GateIn_moreInfo_list_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddRepair_Approvel_Details_MySubmit.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLEquipmentInfoDropdownType);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));

               /* JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("Credentials",jsonObject);*/

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("arrayOfDropdowns");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found.");
                            }
                        });
                    }else {

                        dropdown_MoreInfo_arraylist = new ArrayList<Equipment_Info_TypeDropdownBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            moreInfo_DropdownBean = new Equipment_Info_TypeDropdownBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            moreInfo_DropdownBean.setId(jsonObject.getString("ENM_ID"));
                            moreInfo_DropdownBean.setCode(jsonObject.getString("ENM_CD"));
                            infoId = jsonObject.getString("ENM_ID");
                            infoCode = jsonObject.getString("ENM_CD");
                            String[] set1 = new String[2];
                            set1[0] = infoId;
                            set1[1] = infoCode;
                            dropdown_MoreInfo_list.add(infoCode);
                            Cargo_ID.add(set1[0]);
                            Cargo_Code.add(set1[1]);
                            dropdown_MoreInfo_arraylist.add(moreInfo_DropdownBean);
                        }
                    }
                }else if(jsonarray.length()<1){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(),"No Records Found.");
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute (Void aVoid){



            if(jsonarray!=null)
            {
//                UserListAdapter adapter = new UserListAdapter(Create_GateIn.this, R.layout.list_item_row, pending_arraylist);
//                listview.setAdapter(adapter);
                ArrayAdapter<String> CargoAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,dropdown_MoreInfo_list);
                CargoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_last_test_type.setAdapter(CargoAdapter);

            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }

            progressDialog.dismiss();

        }

    }
    public class Repait_Type_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddRepair_Approvel_Details_MySubmit.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLRepair_Type);
            httpPost.setHeader("Content-Type", "application/json");
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID, "user_Id"));
                jsonObject.put("StatusName","");


                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep_repair_type", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("arrayOfDropdowns");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list" + jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found.");
                            }
                        });
                    } else {

                        dropdown_customer_list = new ArrayList<>();


                        worldlist = new ArrayList<String>();
                        CustomerDropdownArrayList = new ArrayList<CustomerDropdownBean>();
                        for (int i = 0; i < jsonarray.length(); i++) {

                            customer_DropdownBean = new CustomerDropdownBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            customer_DropdownBean.setName(jsonObject.getString("ENM_CD"));
                            customer_DropdownBean.setCode(jsonObject.getString("ENM_ID"));
                            RepairType_Name = jsonObject.getString("ENM_CD");
                            RepairType_Code = jsonObject.getString("ENM_ID");

                            String[] set1 = new String[2];
                            set1[0] = RepairType_Name;
                            set1[1] = RepairType_Code;
                            dropdown_customer_list.add(set1);
                            Cust_name.add(set1[0]);
                            Cust_code.add(set1[1]);
                            CustomerDropdownArrayList.add(customer_DropdownBean);
                            worldlist.add(RepairType_Name);


                        }
                    }
                } else if (jsonarray.length() < 1) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(), "No Records Found.");


                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            if (dropdown_customer_list != null) {
                ArrayAdapter<String> CustomerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, worldlist);
                CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_repair_type.setAdapter(CustomerAdapter);

            } else {
                shortToast(getApplicationContext(), "Data Not Found");

            }

            progressDialog.dismiss();

        }

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        int cyear = c.get(Calendar.YEAR);
        int cmonth = c.get(Calendar.MONTH);
        int cday = c.get(Calendar.DAY_OF_MONTH);
        switch (id) {
            case DATE_DIALOG_ID:
                //start changes...
                DatePickerDialog dialog = new DatePickerDialog(this, pickerListener, cyear, cmonth, cday);
                dialog.getDatePicker().setMaxDate(new Date().getTime());

                    if(GlobalConstants.from.equalsIgnoreCase("MySubmitRepair")|| GlobalConstants.from.equalsIgnoreCase("MySubmitRepair_Summary")
                            ||GlobalConstants.from.equalsIgnoreCase("MysubmitAttach")
                            ||GlobalConstants.from.equalsIgnoreCase("MysubmitAddDetails")
                            ||GlobalConstants.from.equalsIgnoreCase("MysubmitlineItem")) {
                        if(ed_appr_date)
                        {
                            dialog.getDatePicker().setMinDate(Activity_date1.getTime());

                        }

                    }else
                    {
                        if(ed_estimate|| ed_appr_date) {

                            dialog.getDatePicker().setMinDate(date1.getTime());
                    }

                }

                return dialog;
            //end changes...
        }
        return null;
    }
    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date).toString();
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;


            if(ed_estimate==true || im_estimate==true)
            {
                ed_estimate_date.setText(formatDate(year, month, day));


            }else if(last_test_date==true|| im_last_test_date==true) {

                ed_last_testDate.setText(formatDate(year, month, day));
                ed_next_test_date.setText(formatDate(year, month, day));


            }else if(ed_original_date==true|| im_original_date==true)
            {
                ed_original_est_date.setText(formatDate(year, month, day));
            }else if(ed_nxt_test_date==true|| im_next_test_date==true)
            {
                ed_next_test_date.setText(formatDate(year, month, day));
            }else if(im_appr_date==true || ed_appr_date==true)
            {
                ed_approval_date.setText(formatDate(year, month, day));
            }

            //    System.out.println("am a new from date====>>" + str_From);

            //  date.setText(formatDate(year, month, day));

        }
    };


    public class InvoiceParty_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddRepair_Approvel_Details_MySubmit.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLInvoice_Party);
            httpPost.setHeader("Content-Type", "application/json");
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID, "user_Id"));
                jsonObject.put("StatusName","");


                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("arrayOfDropdowns");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list" + jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found.");
                            }
                        });
                    } else {

                        dropdown_customer_list = new ArrayList<>();


                        Invoice_worldlist = new ArrayList<String>();
                        CustomerDropdown_Invoice_ArrayList = new ArrayList<CustomerDropdownBean>();
                        for (int i = 0; i < jsonarray.length(); i++) {

                            customer_DropdownBean = new CustomerDropdownBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            customer_DropdownBean.setName(jsonObject.getString("INVCNG_PRTY_CD"));
                            customer_DropdownBean.setCode(jsonObject.getString("INVCNG_PRTY_ID"));
                            customer_DropdownBean.setTRFF_CD_DESCRPTN_VC(jsonObject.getString("INVCNG_PRTY_CD"));
                            RepairType_Name = jsonObject.getString("INVCNG_PRTY_CD");
                            RepairType_Code = jsonObject.getString("INVCNG_PRTY_ID");

                            String[] set1 = new String[2];
                            set1[0] = RepairType_Name;
                            set1[1] = RepairType_Code;
                            dropdown_customer_list.add(set1);
                            Cust_name.add(set1[0]);
                            Cust_code.add(set1[1]);
                            CustomerDropdown_Invoice_ArrayList.add(customer_DropdownBean);
                            Invoice_worldlist.add(RepairType_Name);


                        }
                    }
                } else if (jsonarray.length() < 1) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(), "No Records Found.");


                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            if (dropdown_customer_list != null) {

                if(Invoice_party <=0)
                {
                    SpinnerCustomAdapter CustomerAdapter = new SpinnerCustomAdapter(AddRepair_Approvel_Details_MySubmit.this, R.layout.spinner_text, CustomerDropdown_Invoice_ArrayList);
                    CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_invoicing_party.setAdapter(CustomerAdapter);
                }else
                {
                    SpinnerCustomAdapter CustomerAdapter = new SpinnerCustomAdapter(AddRepair_Approvel_Details_MySubmit.this, R.layout.spinner_text, CustomerDropdown_Invoice_ArrayList);
                    CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_invoicing_party.setAdapter(CustomerAdapter);
                    for(int i=0;i<Invoice_worldlist.size();i++)
                    {
                        try {
                            if (GlobalConstants.invoice_PartyCD.equals(Invoice_worldlist.get(i))) {


                                sp_invoicing_party.setSelection(i);



                            }
                        }catch (Exception e)
                        {

                        }
                    }

                }


               /* try {
                    if (GlobalConstants.Invoice_party > 0) {
                        sp_invoicing_party.setSelection(Integer.parseInt(GlobalConstants.position));
                    }
                }catch (Exception e)
                {

                }
*/
            } else {
                shortToast(getApplicationContext(), "Data Not Found");

            }

            progressDialog.dismiss();

        }

    }
    public class Get_Repair_MySubmit_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;
        private JSONArray LineItems_Json;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddRepair_Approvel_Details_MySubmit.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLRepairEstimateList);
            httpPost.setHeader("Content-Type", "application/json");

            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID, "user_Id"));
                jsonObject.put("Mode", "edit");
                jsonObject.put("PageName", "Repair Approval");


                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
//                JSONObject jsonrootObject = new JSONObject(Line_item_Json);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("Response");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list" + jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found");
                            }
                        });
                    } else {


                        for (int j = 0; j < jsonarray.length(); j++) {

                            jsonObject = jsonarray.getJSONObject(j);

                            if (GlobalConstants.equipment_no.equalsIgnoreCase(jsonObject.getString("EquipmentNo"))) {
                                get_cust = jsonObject.getString("Customer");
                                get_eqp_no = jsonObject.getString("EquipmentNo");
                                get_indate = jsonObject.getString("InDate");
                                get_previous_cargo = jsonObject.getString("PreviousCargo");
                                get_laststatus_date = jsonObject.getString("LastStatusDate");
                                get_lbr_rate = jsonObject.getString("LaborRate");
                                get_last_test_type = jsonObject.getString("LastTestType");
                                get_nexttest_type = jsonObject.getString("NextTestType");
                                get_last_test_date = jsonObject.getString("LastTestDate");
                                get_nexttest_date = jsonObject.getString("NextTestDate");
                                get_lst_survey = jsonObject.getString("LastSurveyor");
                                get_validate_period_test = jsonObject.getString("ValidityPeriodforTest");
                                get_repair_type_id = jsonObject.getString("RepairTypeID");
                                get_repair_type_cd = jsonObject.getString("RepairTypeCD");
                                get_rematk = jsonObject.getString("Remarks");
                                get_invoice_prty_cd = jsonObject.getString("InvoicingPartyCD");
                                get_invoice_prty_id = jsonObject.getString("InvoicingPartyID");
                                get_invoice_prty_name = jsonObject.getString("InvoicingPartyName");
                                get_gi_trans_no = jsonObject.getString("GiTransactionNo");
                                get_repair_est_id = jsonObject.getString("RepairEstimateID");
                                get_revision_no = jsonObject.getString("RevisionNo");
                                get_cust_app_ref_no = jsonObject.getString("CustomerAppRef");
                                get_approval_date = jsonObject.getString("ApprovalDate");
                                String[] app_date = get_approval_date.split(" ");
                                get_approval_date = app_date[0];
                                get_party_app_ref_no = jsonObject.getString("PartyAppRef");
                                get_survey_name = jsonObject.getString("SurveyorName");
                                get_survey_completion_date = jsonObject.getString("SurveyCompletionDate");
                                get_repair_est_no = jsonObject.getString("RepairEstimateNo");
                                get_rep_status = jsonObject.getString("EquipmentStatusCd");


                            }


                        }
                    }
                } else if (jsonarray.length() < 1) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(), "No Records Found");


                        }
                    });

                }

            }catch (JSONException e)
            {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
        @Override
        protected void onPostExecute (Void aVoid){

            if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();

//            if ((progressDialog != null) && progressDialog.isShowing()) {
//            }
            if(jsonarray!=null)
            {
                ed_labor_rate.setText(get_lbr_rate);

                try {
                    if(get_invoice_prty_cd!=null)
                    {
                        ed_invoicing_party.setText(get_invoice_prty_cd);
                        ed_invoicing_party.setVisibility(View.VISIBLE);
                        sp_invoicing_party.setEnabled(false);
                        sp_invoicing_party.setClickable(false);
                    }else
                    {
                        ed_invoicing_party.setText(get_invoice_prty_cd);

                    }
                    if(get_repair_type_cd!=null)
                    {
//                        ed_repair_type.setText(get_repair_type_cd);
//                        ed_repair_type.setVisibility(View.GONE);
//                        sp_repair_type.setEnabled(false);
                        sp_repair_type.setClickable(false);
                    }else
                    {
//                        ed_repair_type.setText(get_repair_type_cd);

                    }
                }catch (Exception e)
                {

                }
                switch_heating.setEnabled(false);
                Log.i("edit_repair_estimate",get_last_test_type+"-"+get_nexttest_type+"-"+get_lbr_rate+"-"+get_invoice_prty_cd);
                ed_estimate_date.setText(In_date);
                ed_last_survey.setText(get_lst_survey);
                ed_next_type.setText(get_nexttest_type);
                ed_next_type.setClickable(false);
                ed_last_testDate.setText(get_last_test_date);
                ed_last_testDate.setVisibility(View.VISIBLE);
                ed_next_test_date.setText(get_next_test_date);
                ed_next_test_date.setVisibility(View.VISIBLE);
                ed_approval_date.setText(get_approval_date);
                ed_validate_period_for_test.setText(get_validate_period_test);
                ed_remarks.setText(get_rematk);
                ed_last_survey.setText(get_lst_survey);



            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }

        }

    }

}
