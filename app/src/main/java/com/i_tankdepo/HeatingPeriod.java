package com.i_tankdepo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.HeatingBean;
import com.i_tankdepo.Constants.ConstantValues;
import com.i_tankdepo.Constants.GlobalConstants;
import com.i_tankdepo.helper.ServiceHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Metaplore on 10/18/2016.
 */

public class HeatingPeriod extends CommonActivity  {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView menu,im_startDate,im_endDate,im_startTime,im_endTime,iv_back,add_new_heating,im_okay,im_cancel;
    private ListView heating_list_view;

    private TextView tv_totalCost,text1,text2,text3,text4,tv_toolbarTitle,tv_startdate,tv_startTime,tv_endDate,tv_temp,tv_endTime;
    LinearLayout LL_Add_New,LL_heat_submit;
    private ProgressDialog progressDialog;
    private ArrayList<HeatingBean> heating_arraylist=new ArrayList<>();
    private HeatingBean heating_bean;
    /*private ViewHolder holder;
    private UserListAdapter adapter;*/
    private ListView listview,heat_list;
    String curTime,systemDate,get_HourlyRate,get_MinRate ,Equip_NO,Cust_Name,Equip_Type,In_date,Cargo,Total_Peroid,Min_Rate,Hourly_Rate,Min_Htng_Prd,Total_Cost,get_startDate,get_startTime,get_endDate,get_htngPeriod,get_endTime,get_temp;
    private Intent mServiceIntent;
    private EditText ed_startDate,ed_endTime,ed_startTime,ed_endDate,ed_temp,ed_totalPrd,ed_minRate,ed_hourlyRate;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    private boolean startDate = false, endDate = false;
    TimePickerDialog timePickerDialog;
    int mHour,mMinute;
    private Calendar mcurrentTime;
    private int hour,minute;
    private TimePickerDialog mTimePicker;
    private String total_htng_period,total_htng_rate;
    private Button heat_refresh,heat_home,heat_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heating_period);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        menu=(ImageView)findViewById(R.id.iv_menu) ;
        iv_back = (ImageView)findViewById(R.id.iv_back);
        add_new_heating = (ImageView)findViewById(R.id.im_add);
        menu.setVisibility(View.GONE);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Heating Periods");

        LL_Add_New=(LinearLayout)findViewById(R.id.LL_add_new_heating);

        LL_Add_New.setVisibility(View.GONE);
        heat_home = (Button)findViewById(R.id.heat_home);
        heat_refresh = (Button)findViewById(R.id.heat_refresh);
        heat_submit = (Button)findViewById(R.id.heat_submit);
        LL_heat_submit = (LinearLayout)findViewById(R.id.LL_heat_submit);
        LL_heat_submit.setAlpha(0.5f);
        LL_heat_submit.setClickable(false);
        ed_totalPrd = (EditText)findViewById(R.id.ed_totalPrd);
        ed_minRate = (EditText)findViewById(R.id.ed_minRate);
        ed_hourlyRate = (EditText)findViewById(R.id.ed_hourlyRate);
        tv_totalCost = (TextView) findViewById(R.id.tv_totalCost);
        ed_startDate = (EditText)findViewById(R.id.ed_startDate);
        ed_endTime = (EditText)findViewById(R.id.ed_endTime);
        ed_startTime = (EditText)findViewById(R.id.ed_startTime);
        ed_endDate = (EditText)findViewById(R.id.ed_endDate);
        ed_temp = (EditText)findViewById(R.id.ed_temp);
        im_startDate = (ImageView)findViewById(R.id.im_startDate);
        im_endDate = (ImageView)findViewById(R.id.im_endDate);
        im_startTime = (ImageView)findViewById(R.id.im_startTime);
        im_endTime = (ImageView)findViewById(R.id.im_endTime);
        im_okay = (ImageView)findViewById(R.id.im_okay);
        im_cancel = (ImageView)findViewById(R.id.im_cancel);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        text3 = (TextView)findViewById(R.id.text3);
        text4 = (TextView)findViewById(R.id.text4);
        tv_startdate = (TextView)findViewById(R.id.tv_startDate);
        tv_startTime = (TextView)findViewById(R.id.tv_startTime);
        tv_endDate = (TextView)findViewById(R.id.tv_endDate);
        tv_endTime = (TextView)findViewById(R.id.tv_endTime);
        tv_temp = (TextView)findViewById(R.id.tv_temp);

        Cust_Name = GlobalConstants.customer_name;
        Equip_NO = GlobalConstants.equipment_no;
        Equip_Type = GlobalConstants.type;
        In_date = GlobalConstants.date;
        Cargo = GlobalConstants.previous_cargo;
        Total_Peroid = GlobalConstants.ttl_htngPrd;
        Min_Rate = GlobalConstants.min_htng_rate;
        Hourly_Rate = GlobalConstants.hourly_charge;
        Total_Cost = GlobalConstants.ttl_RT_NC;
        Min_Htng_Prd = GlobalConstants.min_htngPrd;

        if((Min_Rate==null || Min_Rate.equals("") ||Min_Rate=="null") ||
                (Hourly_Rate==null || Hourly_Rate.equals("") ||Hourly_Rate=="null") ||
                (Min_Htng_Prd==null || Min_Htng_Prd.equals("") ||Min_Htng_Prd=="null")
                )
        {
            Min_Rate="0";
            Hourly_Rate="0";
            Min_Htng_Prd="0";
        }

        add_new_heating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed_temp.requestFocus();
                LL_Add_New.setVisibility(View.VISIBLE);
                ed_startDate.setText(systemDate);
                ed_startTime.setText(curTime);
                ed_endDate.setText(systemDate);
                ed_endTime.setText(curTime);
                get_HourlyRate = ed_hourlyRate.getText().toString();
                get_MinRate = ed_minRate.getText().toString();
            }
        });
        text1.setText(Cust_Name);
        text2.setText(Equip_NO +" , "+Equip_Type);
        text3.setText(In_date);
        text4.setText(Cargo);
        ed_totalPrd.setText(Total_Peroid);
        ed_minRate.setText(Min_Rate);
        ed_hourlyRate.setText(Hourly_Rate);

        if(Total_Cost == null || Total_Cost.equals("")|| Total_Cost=="null"){
            tv_totalCost.setText("0");
        }else{
            tv_totalCost.setText(Total_Cost);
        }
        iv_back.setOnClickListener(this);
        im_cancel.setOnClickListener(this);
        im_okay.setOnClickListener(this);
        ed_startDate.setOnClickListener(this);
        ed_startTime.setOnClickListener(this);
        ed_endDate.setOnClickListener(this);
        ed_endTime.setOnClickListener(this);
        im_startDate.setOnClickListener(this);
        im_startTime.setOnClickListener(this);
        im_endDate.setOnClickListener(this);
        im_endTime.setOnClickListener(this);
        heat_home.setOnClickListener(this);
        heat_refresh.setOnClickListener(this);

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



        String startDate = getColoredSpanned("Start Date","#bbbbbb");
        String startTime = getColoredSpanned("Start Time","#bbbbbb");
        String endDate = getColoredSpanned("End Date","#bbbbbb");
        String endTime = getColoredSpanned("End Time","#bbbbbb");
        String temp = getColoredSpanned("Temp","#bbbbbb");
        String degree = getColoredSpanned("(°C)","#bbbbbb");

        String surName = getColoredSpanned("*","#cb0da5");

        tv_startdate.setText(Html.fromHtml(startDate+" "+surName));
        tv_startTime.setText(Html.fromHtml(startTime+" "+surName));
        tv_endDate.setText(Html.fromHtml(endDate+" "+surName));
        tv_endTime.setText(Html.fromHtml(endTime+" "+surName));
        tv_temp.setText(Html.fromHtml(temp+ degree +" "+surName));




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

       menu.setOnClickListener(this);
        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(Gravity.START))
                    drawer.closeDrawer(Gravity.END);
                else
                    drawer.openDrawer(Gravity.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);*/
    }
    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
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
            case R.id.ed_startDate:
                startDate = true;
                endDate = false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.ed_endDate:
                startDate = false;
                endDate = true;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_startDate:
                startDate = true;
                endDate = false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_endDate:
                startDate = false;
                endDate = true;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.ed_startTime:

                 mcurrentTime = Calendar.getInstance();
                 hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                 minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(HeatingPeriod.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed_startTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute,true
                );
                mTimePicker.show();
                break;
            case R.id.ed_endTime:
                 mcurrentTime = Calendar.getInstance();
                 hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                 minute = mcurrentTime.get(Calendar.MINUTE);
                mTimePicker = new TimePickerDialog(HeatingPeriod.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed_endTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute,true
                );

                mTimePicker.show();
                break;

            case R.id.im_startTime:
                 mcurrentTime = Calendar.getInstance();
                 hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                 minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(HeatingPeriod.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed_startTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute,true
                );

                mTimePicker.show();
                break;
            case R.id.im_endTime:
                 mcurrentTime = Calendar.getInstance();
                 hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                 minute = mcurrentTime.get(Calendar.MINUTE);

                mTimePicker = new TimePickerDialog(HeatingPeriod.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ed_endTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute,true
                );
                mTimePicker.show();
                break;
            case R.id.im_cancel:
                LL_Add_New.setVisibility(View.GONE);
                ed_startDate.setText("");
                ed_startTime.setText("");
                ed_endDate.setText("");
                ed_endTime.setText("");
                break;
            case R.id.im_okay:
                get_startDate = ed_startDate.getText().toString();
                get_startTime = ed_startTime.getText().toString();
                get_endDate = ed_endDate.getText().toString();
                get_endTime = ed_endTime.getText().toString();
                get_temp = ed_temp.getText().toString();

                if((get_startDate.equals("") || get_startDate == null) ||
                        (get_startTime.equals("") || get_startTime == null ) ||
                        (get_endDate.equals("") || get_endDate == null) ||
                        (get_endTime.equals("")|| get_endTime == null) ||
                        (get_temp.equals("") || get_temp == null)){
                   shortToast(getApplicationContext(),"Please select the Mandatory Fields.");
                }else{
                    if (cd.isConnectingToInternet()){
                        new Post_Calc_Heating_period().execute();
                        ed_startDate.setText("");
                        ed_startTime.setText("");
                        ed_endDate.setText("");
                        ed_endTime.setText("");
                        LL_Add_New.setVisibility(View.GONE);
                    }else{
                        shortToast(getApplicationContext(),"Please Check Your Internet Connection");
                    }
                }
//                LL_Add_New.setVisibility(View.GONE);
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

            view.setMinDate(System.currentTimeMillis() - 1000);

            if(startDate==true)
            {

                ed_startDate.setText(formatDate(year, month, day));
            }else {

                ed_endDate.setText(formatDate(year, month, day));
            }

        }
    };






    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    public class Post_Calc_Heating_period extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        JSONObject jsonobject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HeatingPeriod.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            //  progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCalcHeatingPeriod);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("str_HeatingStartDate", get_startDate);
                jsonObject.put("str_HeatingStartTime",get_startTime);
                jsonObject.put("str_HeatingEndDate", get_endDate);
                jsonObject.put("str_HeatingEndTime", get_endTime);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("responce", resp);
                Log.d("req", jsonObject.toString());
                JSONObject jsonResp = new JSONObject(resp);

                jsonobject = jsonResp.getJSONObject("d");

                if (jsonobject != null) {

                    total_htng_period=jsonobject.getString("TotalHeatingPeriod");

                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(jsonobject!=null)
            {
                if(total_htng_period == null || total_htng_period.equals("")|| total_htng_period=="null") {
                    ed_totalPrd.setText("0");
                    get_htngPeriod = ed_totalPrd.getText().toString();
                    new Post_Calc_Total_Rate().execute();
                    ed_temp.setText("");
                }else{
                    ed_totalPrd.setText(total_htng_period);
                    get_htngPeriod = ed_totalPrd.getText().toString();
                    new Post_Calc_Total_Rate().execute();
                    ed_temp.setText("");
                }
            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");
            }

            progressDialog.dismiss();

        }
    }

    public class Post_Calc_Total_Rate extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        JSONObject jsonobject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HeatingPeriod.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCalcTotalRate);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("str_MinRate",get_MinRate);
                jsonObject.put("str_TotaPeriod",get_htngPeriod);
                jsonObject.put("str_MinHeatingPerod", Min_Htng_Prd);
                jsonObject.put("str_HourlyRate", get_HourlyRate);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("total amount responce", resp);
                Log.d("req", jsonObject.toString());
                JSONObject jsonResp = new JSONObject(resp);

                jsonobject = jsonResp.getJSONObject("d");

                if (jsonobject != null) {

                    total_htng_rate=jsonobject.getString("TotalHeatingRate");

                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(jsonobject!=null)
            {
                tv_totalCost.setText(total_htng_rate);
            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");
            }

            progressDialog.dismiss();

        }
    }



}
