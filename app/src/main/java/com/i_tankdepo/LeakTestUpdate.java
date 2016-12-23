package com.i_tankdepo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.LeakTestBean;
import com.i_tankdepo.Constants.ConstantValues;
import com.i_tankdepo.Constants.GlobalConstants;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 12/22/2016.
 */

public class LeakTestUpdate extends CommonActivity{
    private ImageView menu,iv_back,add_new_heating,im_testDate;
    private TextView tv_toolbarTitle,leakTest_text,tv_heat_refresh,text1,text2;
    private Button heat_home,heat_refresh,bt_heating,cleaning,inspection,leakTest,heat_submit,bt_revisionNo;
    private LinearLayout LL_heat_submit,LL_heat;
    private RelativeLayout RL_heating,RL_Repair;
    private EditText ed_remarks,ed_current_status,ed_relief_value1,ed_relief_value2,ed_press_guage1,ed_press_guage2,ref_no,ed_testDate;
    private String curTime,systemDate,Cust_Name,Equip_NO,Type,NoofTimesGenerated,TestDate,RevisionNo,relief_value1,relief_value2,pressureGauge1,pressureGauge2,
            shellTest,steamTubeTest,remark,current_status,Gi_trans_no,Checked;
    private  Switch switch_shellTest,switch_steam;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    private ViewHolder holder;
    private ProgressDialog progressDialog;
    private String getTestDate,getreliefvalue1,getreliefvalue2,getpress_guage1,getpress_guage2,getReamrk,get_switch_shellTest,get_switch_steam;
    private ArrayList<LeakTestBean> leakTest_arraylist = new ArrayList<>();
    private LeakTestBean leakTest_bean;
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
        LL_heat = (LinearLayout)findViewById(R.id.LL_heat);
        inspection.setVisibility(View.GONE);
        cleaning.setVisibility(View.GONE);
        LL_heat.setAlpha(0.5f);
        LL_heat.setClickable(false);
        leakTest_text = (TextView)findViewById(R.id.tv_heating);
        leakTest_text.setText("Add New");
        LL_heat_submit = (LinearLayout)findViewById(R.id.LL_heat_submit);
        heat_submit = (Button) findViewById(R.id.heat_submit);

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
        ed_current_status = (EditText)findViewById(R.id.ed_current_status);
        switch_shellTest = (Switch)findViewById(R.id.switch_shellTest);
        switch_steam = (Switch)findViewById(R.id.switch_steam);
        im_testDate = (ImageView)findViewById(R.id.im_testDate);
        bt_revisionNo = (Button) findViewById(R.id.bt_revisionNo);

        heat_refresh.setOnClickListener(this);
        heat_home.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        ed_testDate.setOnClickListener(this);
        im_testDate.setOnClickListener(this);
        heat_submit.setOnClickListener(this);
        bt_revisionNo.setOnClickListener(this);

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
        current_status = GlobalConstants.EQPMNT_STTS_CD;
        Gi_trans_no = GlobalConstants.gt_transaction_no;




        String[] parts = TestDate.split(" ");
        String part1_date = parts[0];


        text1.setText(Cust_Name+" , "+Equip_NO+" , "+Type);
        text2.setText(NoofTimesGenerated);
        ed_testDate.setText(part1_date);
        ed_relief_value1.setText(relief_value1);
        ed_relief_value2.setText(relief_value2);
        ed_press_guage1.setText(pressureGauge1);
        ed_press_guage2.setText(pressureGauge2);
        ed_remarks.setText(remark);
        ed_current_status.setText(current_status);
        bt_revisionNo.setText(RevisionNo);

        if(shellTest.equalsIgnoreCase("True")){
            switch_shellTest.setChecked(true);
        }else{
            switch_shellTest.setChecked(false);
        }

        if(steamTubeTest.equalsIgnoreCase("True")){
            switch_steam.setChecked(true);
        }else{
            switch_steam.setChecked(false);
        }

        switch_shellTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    get_switch_shellTest="True";
                }else
                {
                    get_switch_shellTest="False";

                }
            }
        });

        switch_steam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    get_switch_steam="True";
                }else
                {
                    get_switch_steam="False";

                }
            }
        });

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
        systemDate = new SimpleDateFormat("yyyy-MMM-dd").format(new Date());

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
            case R.id.ed_test_date:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_testDate:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.bt_revisionNo:
                    if(cd.isConnectingToInternet()){
                        new Get_Revision_Number_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                    }
                break;
            case R.id.heat_submit:
                    getTestDate = ed_testDate.getText().toString();
                    getreliefvalue1 = ed_relief_value1.getText().toString();
                    getreliefvalue2 = ed_relief_value2.getText().toString();
                    getpress_guage1 = ed_press_guage1.getText().toString();
                    getpress_guage2 = ed_press_guage2.getText().toString();
                    getReamrk = ed_remarks.getText().toString();
                    Checked = "True";
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);



        }
        return null;
    }
    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");

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

            view.setMinDate(System.currentTimeMillis() - 1000);


                ed_testDate.setText(formatDate(year, month, day));


        }
    };



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class Get_Revision_Number_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LeakTestUpdate.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
//            if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.show();
//            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLLeakTestRevisionNo);

            httpPost.setHeader("Content-Type", "application/json");


            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID, "user_Id"));
                jsonObject.put("equipmentNo",Equip_NO);
                jsonObject.put("GITransactionNo",Gi_trans_no);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("arrayOfLTM");
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

                        leakTest_arraylist = new ArrayList<LeakTestBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            leakTest_bean = new LeakTestBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            leakTest_bean.setLK_TST_ID(jsonObject.getString("LK_TST_ID"));
                            leakTest_bean.setEQPMNT_NO(jsonObject.getString("EQPMNT_NO"));
                            leakTest_bean.setGI_TRNSCTN_NO(jsonObject.getString("GI_TRNSCTN_NO"));
                            leakTest_bean.setTST_DT(jsonObject.getString("TST_DT"));
                            leakTest_bean.setRLF_VLV_SRL_1(jsonObject.getString("RLF_VLV_SRL_1"));
                            leakTest_bean.setRLF_VLV_SRL_2(jsonObject.getString("RLF_VLV_SRL_2"));
                            leakTest_bean.setPG_1(jsonObject.getString("PG_1"));
                            leakTest_bean.setPG_2(jsonObject.getString("PG_2"));
                            leakTest_bean.setRVSN_NO(jsonObject.getString("RVSN_NO"));
                            leakTest_bean.setLST_GNRTD_BY(jsonObject.getString("LST_GNRTD_BY"));
                            leakTest_bean.setLST_GNRTD_DT(jsonObject.getString("LST_GNRTD_DT"));
                            leakTest_bean.setLTST_RPRT_NO(jsonObject.getString("LTST_RPRT_NO"));
                            leakTest_bean.setSHLL_TST_BT(jsonObject.getString("SHLL_TST_BT"));
                            leakTest_bean.setSTM_TB_TST_BT(jsonObject.getString("STM_TB_TST_BT"));
                            leakTest_bean.setRMRKS_VC(jsonObject.getString("RMRKS_VC"));


                            leakTest_arraylist.add(leakTest_bean);


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


//            if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.dismiss();
//            }
            if (leakTest_arraylist != null) {
                /*adapter = new UserListAdapter(LeakTestUpdate.this, R.layout.list_item_row, leakTest_arraylist);
                listview.setAdapter(adapter);*/




            } else if (leakTest_arraylist.size() < 1) {
                shortToast(getApplicationContext(), "Data Not Found");
            }

        }

    }

    public class UserListAdapter extends BaseAdapter {
        private final ArrayList<LeakTestBean> arraylist;
        Context context;
        ArrayList<LeakTestBean> list = new ArrayList<>();
        int resource;
        private LeakTestBean userListBean;
        int lastPosition = -1;

        public UserListAdapter(Context context, int resource, ArrayList<LeakTestBean> list) {
            this.context = context;
            this.list = list;
            this.resource = resource;
            this.arraylist = new ArrayList<LeakTestBean>();
            this.arraylist.addAll(list);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();



                holder.whole = (LinearLayout) convertView.findViewById(R.id.LL_whole);
                holder.Cust_Name = (TextView) convertView.findViewById(R.id.text1);
                holder.equip_no = (TextView) convertView.findViewById(R.id.text2);
                holder.date = (TextView) convertView.findViewById(R.id.text3);
                holder.previous_cargo = (TextView) convertView.findViewById(R.id.text4);
                holder.previous_cargo.setVisibility(View.GONE);
                holder.leaktestID = (TextView) convertView.findViewById(R.id.tv_text30);
                holder.transactionNO = (TextView) convertView.findViewById(R.id.text5);
                holder.type = (TextView) convertView.findViewById(R.id.tv_type);
                holder.testDate = (TextView) convertView.findViewById(R.id.text6);
                holder.RLFVLV1 = (TextView) convertView.findViewById(R.id.tv_code);
                holder.RLFVLV2 = (TextView) convertView.findViewById(R.id.tv_location);
                holder.PG1 = (TextView) convertView.findViewById(R.id.tv_transport);
                holder.PG2 = (TextView) convertView.findViewById(R.id.tv_vechicle);
                holder.estimateNo = (TextView) convertView.findViewById(R.id.text7);
                holder.revisionNO = (TextView) convertView.findViewById(R.id.text8);
                holder.listGrantedBy = (TextView) convertView.findViewById(R.id.tv_pre_adv_id);
                holder.listGrantedDate = (TextView) convertView.findViewById(R.id.text10);
                holder.listReportNo = (TextView) convertView.findViewById(R.id.tv_status);
                holder.noofTimesGranted = (TextView) convertView.findViewById(R.id.text9);
                holder.testBit = (TextView) convertView.findViewById(R.id.tv_text11);
                holder.STM_TB_TST_BT = (TextView) convertView.findViewById(R.id.text12);
                holder.RMRKS_VC = (TextView) convertView.findViewById(R.id.tv_text13);
                holder.ACTV_BT = (TextView) convertView.findViewById(R.id.tv_text14);
                holder.CRTD_BY = (TextView) convertView.findViewById(R.id.tv_text15);
                holder.CRTD_DT = (TextView) convertView.findViewById(R.id.tv_text16);
                holder.MDFD_BY = (TextView) convertView.findViewById(R.id.tv_text17);
                holder.MDFD_DT = (TextView) convertView.findViewById(R.id.tv_text18);
                holder.EQPMNT_TYP_ID = (TextView) convertView.findViewById(R.id.tv_text19);
                holder.EQPMNT_CD_ID = (TextView) convertView.findViewById(R.id.tv_text20);
                holder.EQPMNT_CD_CD = (TextView) convertView.findViewById(R.id.tv_text29);
                holder.EQPMNT_STTS_ID = (TextView) convertView.findViewById(R.id.tv_text21);
                holder.EQPMNT_STTS_CD = (TextView) convertView.findViewById(R.id.tv_text22);
                holder.CHECKED = (TextView) convertView.findViewById(R.id.tv_text23);
                holder.CSTMR_ID = (TextView) convertView.findViewById(R.id.tv_text24);
                holder.DPT_ID = (TextView) convertView.findViewById(R.id.tv_text25);
                holder.SHL_TST = (TextView) convertView.findViewById(R.id.tv_text26);
                holder.STM_TB_TST = (TextView) convertView.findViewById(R.id.tv_text27);
                holder.YRD_LCTN = (TextView) convertView.findViewById(R.id.tv_text28);

                holder.LL_username = (LinearLayout)convertView.findViewById(R.id.LL_username);
                holder.LL_username.setVisibility(View.GONE);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.size() < 1) {
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            } else {
                userListBean = list.get(position);
//                String[] parts = userListBean.getGTN_DT().split(" ");
//                String part1_date = parts[0];
//                String part1_time = parts[1];
//                System.out.println("from date after split" + part1_date);
                holder.equip_no.setText(userListBean.getEQPMNT_NO());

                holder.leaktestID.setText(userListBean.getLK_TST_ID());
                holder.transactionNO.setText(userListBean.getGI_TRNSCTN_NO());
                holder.testDate.setText(userListBean.getTST_DT());
                holder.RLFVLV1.setText(userListBean.getRLF_VLV_SRL_1());
                holder.RLFVLV2.setText(userListBean.getRLF_VLV_SRL_2());
                holder.PG1.setText(userListBean.getPG_1());
                holder.PG2.setText(userListBean.getPG_2());
                holder.revisionNO.setText(userListBean.getRVSN_NO());
                holder.listGrantedBy.setText(userListBean.getLST_GNRTD_BY());
                holder.listGrantedDate.setText(userListBean.getLST_GNRTD_DT());
                holder.listReportNo.setText(userListBean.getLTST_RPRT_NO());
                holder.testBit.setText(userListBean.getSHLL_TST_BT());
                holder.STM_TB_TST_BT.setText(userListBean.getSTM_TB_TST_BT());
                holder.RMRKS_VC.setText(userListBean.getRMRKS_VC());
/*
                if(userListBean.getVechicle().equals("")||userListBean.getVechicle()==null)
                {
                    holder.PG2.setText("");
                }
               else{
                    holder.PG2.setText(userListBean.getVechicle());
                }*/

            }
            return convertView;
        }


    }

    static class ViewHolder {
        TextView equip_no, previous_cargo,date, Cust_Name, leaktestID, transactionNO, testDate, RLFVLV1, RLFVLV2, pre_id, testBit, cust_code, type_id, code_id,
                PG2, PG1, estimateNo, noofTimesGranted, listGrantedDate, revisionNO, listReportNo, listGrantedBy, type,CRTD_DT,
                CRTD_BY,ACTV_BT,RMRKS_VC,STM_TB_TST_BT,MDFD_BY,MDFD_DT,EQPMNT_TYP_ID,EQPMNT_CD_CD,EQPMNT_CD_ID,EQPMNT_STTS_ID,EQPMNT_STTS_CD,CHECKED
                ,CSTMR_ID,DPT_ID,SHL_TST,STM_TB_TST,YRD_LCTN;
        CheckBox checkBox;

        LinearLayout whole,LL_username;
    }



}
