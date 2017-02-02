package com.i_tankdepo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 12/29/2016.
 */

public class CleaningInstruction extends CommonActivity {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView menu,im_startDate,im_endDate,im_startTime,im_endTime,im_info_cleaning,
            iv_back,add_new_heating,im_okay,im_cancel,iv_changeOfStatus,im_cleaning_startTime,im_org_cleanDate,im_lat_cleanDate,im_cleaning_endTime;
    private TextView tv_toolbarTitle,cleaning_text,text1,AddClean,tv_lat_cleanDate,tv_org_cleanDate;
    Button bt_pending, bt_add, bt_mysubmit, bt_home, bt_refresh, im_add, im_print,cleaning,heating,inspection,Leaktest,bt_gateout,bt_cleaningInst;
    private LinearLayout LL_heat_submit,LL_heat;
    private RelativeLayout RL_Repair,RL_heating;
    private EditText ed_cleanBy,ed_cleanRefNo,ed_org_cleanDate,ed_lat_cleanDate,ed_startTime,ed_endTime,ed_cleaning_start_time,ed_cleaning_endTime,ed_remark;
    private Spinner sp_previ_cargo2,sp_previ_cargo3;
    private String curTime,systemDate;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    TimePickerDialog timePickerDialog;
    private boolean latest_cleanDate=false;
    int mHour,mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleaning_instruction);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        menu.setVisibility(View.GONE);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Cleaning Instruction");


        cleaning_text = (TextView)findViewById(R.id.tv_heating);
        heating = (Button)findViewById(R.id.heating);
        cleaning = (Button)findViewById(R.id.cleaning);
        inspection = (Button)findViewById(R.id.inspection);
        Leaktest = (Button)findViewById(R.id.leakTest);
        inspection.setVisibility(View.GONE);
        heating.setVisibility(View.GONE);
        Leaktest.setVisibility(View.GONE);
        cleaning_text.setText("Cleaning");
        bt_gateout = (Button)findViewById(R.id.bt_gateout);
        bt_gateout.setVisibility(View.GONE);

        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);

        LL_heat_submit = (LinearLayout) findViewById(R.id.LL_heat_submit);
        LL_heat = (LinearLayout) findViewById(R.id.LL_heat);

        RL_heating =(RelativeLayout)findViewById(R.id.RL_heating);
        RL_Repair =(RelativeLayout)findViewById(R.id.RL_Repair);
        RL_Repair.setVisibility(View.GONE);

        bt_home = (Button) findViewById(R.id.heat_home);
        bt_home.setOnClickListener(this);
        bt_refresh = (Button) findViewById(R.id.heat_refresh);
        bt_refresh.setOnClickListener(this);

        text1 = (TextView)findViewById(R.id.text1);
        im_info_cleaning = (ImageView)findViewById(R.id.im_info_cleaning);

        AddClean = (TextView)findViewById(R.id.AddClean);
        ed_cleanBy = (EditText)findViewById(R.id.ed_cleanBy);
        ed_cleanRefNo = (EditText)findViewById(R.id.ed_cleanRefNo);
        ed_org_cleanDate = (EditText)findViewById(R.id.ed_org_cleanDate);
        ed_lat_cleanDate = (EditText)findViewById(R.id.ed_lat_cleanDate);
        ed_startTime = (EditText)findViewById(R.id.ed_startTime);
        ed_endTime = (EditText)findViewById(R.id.ed_endTime);
        ed_cleaning_start_time = (EditText)findViewById(R.id.ed_cleaning_start_time);
        ed_cleaning_endTime = (EditText)findViewById(R.id.ed_cleaning_endTime);
        ed_remark = (EditText)findViewById(R.id.ed_remark);

        bt_cleaningInst = (Button)findViewById(R.id.bt_cleaningInst);
        bt_cleaningInst.setOnClickListener(this);

        sp_previ_cargo2 = (Spinner)findViewById(R.id.sp_previ_cargo2);
        sp_previ_cargo3 = (Spinner)findViewById(R.id.sp_previ_cargo3);

        im_org_cleanDate = (ImageView)findViewById(R.id.im_org_cleanDate);
        im_lat_cleanDate = (ImageView)findViewById(R.id.im_lat_cleanDate);
        im_startTime = (ImageView)findViewById(R.id.im_startTime);
        im_endTime = (ImageView)findViewById(R.id.im_endTime);
        im_cleaning_startTime = (ImageView)findViewById(R.id.im_cleaning_startTime);
        im_cleaning_endTime = (ImageView)findViewById(R.id.im_cleaning_endTime);
        tv_org_cleanDate = (TextView)findViewById(R.id.tv_org_cleanDate);
        tv_lat_cleanDate = (TextView)findViewById(R.id.tv_lat_cleanDate);

        ed_org_cleanDate.setOnClickListener(this);
        ed_lat_cleanDate.setOnClickListener(this);
        ed_startTime.setOnClickListener(this);
        ed_endTime.setOnClickListener(this);
        ed_cleaning_start_time.setOnClickListener(this);
        ed_cleaning_endTime.setOnClickListener(this);
        im_org_cleanDate.setOnClickListener(this);
        im_lat_cleanDate.setOnClickListener(this);
        im_startTime.setOnClickListener(this);
        im_endTime.setOnClickListener(this);
        im_cleaning_startTime.setOnClickListener(this);
        im_cleaning_endTime.setOnClickListener(this);

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

        ed_org_cleanDate.setText(systemDate);
        ed_lat_cleanDate.setText(systemDate);


        String original = getColoredSpanned("Original Cleaning Date","#bbbbbb");
        String latest = getColoredSpanned("Latest Cleaning Date","#bbbbbb");

        String surName = getColoredSpanned("*","#cb0da5");

        tv_org_cleanDate.setText(Html.fromHtml(original+" "+surName));
        tv_lat_cleanDate.setText(Html.fromHtml(latest+" "+surName));

    }


    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_changeOfStatus:
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.heat_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.heat_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.iv_back:
                finish();
                onBackPressed();
                break;
            case R.id.ed_org_cleanDate:
                latest_cleanDate=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.ed_lat_cleanDate:
                latest_cleanDate=true;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_org_cleanDate:
                latest_cleanDate=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_lat_cleanDate:
                latest_cleanDate=true;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.ed_startTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_startTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.ed_endTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_endTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.ed_cleaning_start_time:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_cleaning_start_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.ed_cleaning_endTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_cleaning_endTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.im_startTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_startTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.im_endTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_endTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.im_cleaning_startTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_cleaning_start_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.im_cleaning_endTime:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_cleaning_endTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
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

            if(latest_cleanDate==true) {
                ed_lat_cleanDate.setText(formatDate(year, month, day));

            }
            else
            {
                ed_org_cleanDate.setText(formatDate(year, month, day));
            }

            //    System.out.println("am a new from date====>>" + str_From);

            //  date.setText(formatDate(year, month, day));

        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
