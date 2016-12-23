package com.i_tankdepo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.i_tankdepo.Beanclass.CustomerDropdownBean;
import com.i_tankdepo.Beanclass.LeakTestBean;
import com.i_tankdepo.Constants.ConstantValues;
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
import java.util.List;

/**
 * Created by Admin on 12/23/2016.
 */

public class LeakTestCreate extends CommonActivity {
    private ImageView menu,iv_back,add_new_heating,im_testDate;
    private TextView tv_toolbarTitle,leakTest_text,tv_heat_refresh,text1,text2;
    private Button heat_home,heat_refresh,bt_heating,cleaning,inspection,leakTest,heat_submit,bt_revisionNo;
    private LinearLayout LL_heat_submit,LL_heat;
    private RelativeLayout RL_heating,RL_Repair;
    private EditText ed_customer;
    private String curTime,systemDate,Cust_Name,Equip_NO,Type,NoofTimesGenerated,TestDate,RevisionNo,relief_value1,relief_value2,pressureGauge1,pressureGauge2,
            shellTest,steamTubeTest,remark,current_status,Gi_trans_no,Checked;
    private Switch switch_shellTest,switch_steam;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    private LeakTestUpdate.ViewHolder holder;
    private ProgressDialog progressDialog;
    private String getTestDate,getreliefvalue1,getreliefvalue2,getpress_guage1,getpress_guage2,getReamrk,get_switch_shellTest,get_switch_steam;
    private ArrayList<LeakTestBean> leakTest_arraylist = new ArrayList<>();
    private LeakTestBean leakTest_bean;
    private Spinner sp_equipment_no;
    ArrayList<String[]> dropdown_equipment_no_list = new ArrayList<>();
    private ArrayList<String> worldlist;
    private CustomerDropdownBean customer_DropdownBean;
    ArrayList<CustomerDropdownBean> CustomerDropdownArrayList = new ArrayList<>();
    List<String> Cust_name = new ArrayList<>();
    List<String> Cust_code = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaktest_create);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        add_new_heating = (ImageView) findViewById(R.id.im_add);
        menu.setVisibility(View.GONE);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Leak Test Create");

        heat_home = (Button) findViewById(R.id.heat_home);
        heat_refresh = (Button) findViewById(R.id.heat_refresh);


        bt_heating = (Button) findViewById(R.id.heating);
        bt_heating.setVisibility(View.GONE);
        cleaning = (Button) findViewById(R.id.cleaning);
        inspection = (Button) findViewById(R.id.inspection);
        LL_heat = (LinearLayout) findViewById(R.id.LL_heat);
        inspection.setVisibility(View.GONE);
        cleaning.setVisibility(View.GONE);
        LL_heat.setAlpha(0.5f);
        LL_heat.setClickable(false);
        leakTest_text = (TextView) findViewById(R.id.tv_heating);
        leakTest_text.setText("Add New");
        LL_heat_submit = (LinearLayout) findViewById(R.id.LL_heat_submit);
        heat_submit = (Button) findViewById(R.id.heat_submit);

        tv_heat_refresh = (TextView) findViewById(R.id.tv_heat_refresh);
        tv_heat_refresh.setText("Reset");

        RL_heating = (RelativeLayout) findViewById(R.id.RL_heating);
        RL_Repair = (RelativeLayout) findViewById(R.id.RL_Repair);
        RL_Repair.setVisibility(View.GONE);

        sp_equipment_no = (Spinner)findViewById(R.id.sp_equipment_no);
        ed_customer = (EditText)findViewById(R.id.ed_customer);

        heat_refresh.setOnClickListener(this);
        heat_home.setOnClickListener(this);
        iv_back.setOnClickListener(this);

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
        switch (view.getId()) {
            case R.id.heat_home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

    public class Get_Equipment_Number_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LeakTestCreate.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCreateGateInCustomer);
//            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
//            httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
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

                        dropdown_equipment_no_list = new ArrayList<>();


                       /* businessAccessDetailsBeanArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            businessAccessDetailsBean = new BusinessAccessDetailsBean();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            businessAccessDetailsBean.setBusinessCode(jsonObject.getString("BUSINESS CODE"));
                            businessAccessDetailsBean.setBusinessDescription(jsonObject.getString("BUSINESS DESC"));
                            businessAccessDetailsBeanArrayList.add(businessAccessDetailsBean);
                        }*/
                        worldlist = new ArrayList<String>();
                        CustomerDropdownArrayList=new ArrayList<CustomerDropdownBean>();
                        for (int i = 0; i < jsonarray.length(); i++) {

                            customer_DropdownBean = new CustomerDropdownBean();
                            jsonObject = jsonarray.getJSONObject(i);


                            customer_DropdownBean.setName(jsonObject.getString("Name"));
                            customer_DropdownBean.setCode(jsonObject.getString("Code"));
                            CustomerName = jsonObject.getString("Name");
                            CustomerCode = jsonObject.getString("Code");
                            String[] set1 = new String[2];
                            set1[0] = CustomerName;
                            set1[1] = CustomerCode;
                            dropdown_customer_list.add(set1);
                            Cust_name.add(set1[0]);
                            Cust_code.add(set1[1]);
                            CustomerDropdownArrayList.add(customer_DropdownBean);
                            worldlist.add(CustomerName);


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



            if(dropdown_equipment_no_list!=null)
            {
                ArrayAdapter<String> CustomerAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,worldlist);
                CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_customer.setAdapter(CustomerAdapter);

            }
            else if(dropdown_equipment_no_list.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");

            }

            progressDialog.dismiss();

        }

    }

}
