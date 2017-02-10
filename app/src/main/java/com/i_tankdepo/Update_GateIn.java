package com.i_tankdepo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.CustomerDropdownBean;
import com.i_tankdepo.Beanclass.EquipmentDropdownBean;
import com.i_tankdepo.Beanclass.Equipment_Info_TypeDropdownBean;
import com.i_tankdepo.Beanclass.Multi_Photo_Bean;
import com.i_tankdepo.Beanclass.Previous_CargoDropdownBean;
import com.i_tankdepo.Constants.ConstantValues;
import com.i_tankdepo.Constants.GlobalConstants;
import com.i_tankdepo.Util.Utility;
import com.i_tankdepo.helper.ServiceHandler;

import org.apache.commons.io.FilenameUtils;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Metaplore on 10/18/2016.
 */

public class Update_GateIn extends CommonActivity {

    ImageView up,more_up,equip_up,down,more_down,equip_down;
    LinearLayout LL_general_info;
    private TextView tv_toolbarTitle,tv_manuf_date,tv_next_text_id,tv_add,tv_name,tv_equip_no,tv_type,tv_code,tv_status,tv_date,tv_time,tv_cargo;

    private ImageView menu,im_date,im_time,im_Attachment,iv_back,im_manuf_date,im_last_testDate;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private Intent mServiceIntent;
    private String userChoosenTask;
    private Button fotter_add,im_add,im_print,bt_home,bt_refresh,fotter_submit;
    private EditText ed_time,ed_attach,ed_date,ed_equipement,ed_code,ed_status,
            ed_location,ed_eir_no,ed_vechicle,ed_transport,ed_remark,ed_type,ed_customer;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    private String systemDate,get_equipment,get_Type_Id,getType,get_date,get_time,get_code,get_status,get_location,get_vechicle,get_transport,get_eir_no,get_remark;
    private String curTime,get_swt_heating,get_swt_rental;
    int mHour,mMinute;
    boolean new_attachment=false;

    private boolean manuf_date=false,last_test_date=false;

    TimePickerDialog timePickerDialog;

    ArrayList<String[]> dropdown_customer_list = new ArrayList<>();
    private String CustomerName,CustomerCode,EquipmentType,EquipmentCode,PreviousCargoID,PreviousCargoCode,PreviousCargoDescription;
    List<String> Cust_name = new ArrayList<>();
    List<String> Cust_code = new ArrayList<>();
    private EditText ed_manuf_date,ed_tare_weight,ed_Gross_weight,ed_capacity,ed_last_survey,ed_last_test_date,
            ed_next_date,ed_info_remark,switch_active,switch_remtal,ed_next_type;
    ArrayList<String> dropdown_equipment_list = new ArrayList<>();
    List<String> Equip_Type = new ArrayList<>();
    List<String> Equip_Code = new ArrayList<>();
    String get_manu_date,get_tare_weight,get_gross, get_capacity,get_last_survy,get_last_test_date,get_last_test_type,get_next_date,get_next_type,
            get_info_remark;
    String EIEQPMNT_TYP_CD,EIMNFCTR_DT,EITR_WGHT_NC,EIGRSS_WGHT_NC,EICPCTY_NC,
            EILST_SRVYR_NM,EILST_TST_DT,EILST_TST_TYP_ID,EINXT_TST_DT,EINXT_TST_TYP_ID,EIRMRKS_VC,EIACTV_BT,EIRNTL_BT;
    ArrayList<String> dropdown_PreviousCargo_list = new ArrayList<>();
    ArrayList<Previous_CargoDropdownBean> dropdown_PreviousCargo_arraylist = new ArrayList<>();
    List<String> Cargo_ID = new ArrayList<>();
    List<String> Cargo_Code = new ArrayList<>();
    List<String> Cargo_Description = new ArrayList<>();
    LinearLayout LL_Equipment_Info,LL_Submit,LL_footer_delete;
    private int pendingsize;
    String equip_no,from,type_name, Cust_Name,previous_crg,cust_name,attachmentstatus,gateIn_Id,code,location,cust_code,type_id,code_id,pre_code,pre_id,
            trans_no, vechicle,transport,Eir_no,heating_bt,rental_bt,info_heating_bt,info_rental_bt,remark,type,status,date,time,pre_adv_id,get_swt_info_rental,get_swt_info_active;

    Switch heating,rental,info_active,info_rental;
    private String get_sp_customer_id,get_sp_customer,get_sp_equipe,get_sp_previous,get_sp_previous_id,get_sp_Type_Id,get_sp_Type;
    private CustomerDropdownBean customer_DropdownBean;
    ArrayList<CustomerDropdownBean> CustomerDropdownArrayList = new ArrayList<>();
    private ArrayList<String> worldlist;
    private String get_sp_customer_code,get_sp_equipe_code,get_sp_previous_code;
    private EquipmentDropdownBean equipment_DropdownBean;
    private ArrayList<EquipmentDropdownBean> dropdown_equipment_arraylist;
    private Previous_CargoDropdownBean cargo_DropdownBean;
    private String equipement_code,equipement_id;
    public static boolean isCamPermission;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String TAG="Create_GateIn";
    private String encodedImage,selectedImagePath,filename,IfAttchment;
    private File file;
    private String validationStatus;
    private JSONObject reqObj;
    private Spinner sp_previous_cargo,sp_customer,sp_type;
    private String changes;
    private ArrayList<Equipment_Info_TypeDropdownBean> dropdown_MoreInfo_arraylist;
    private Equipment_Info_TypeDropdownBean moreInfo_DropdownBean;
    private String infoId,infoCode;
    ArrayList<String> dropdown_MoreInfo_list = new ArrayList<>();
    private Spinner sp_last_test_type;
    private ImageView iv_changeOfStatus;
    private String Filename,filePath;
    private String imageName;
    private String deleteAttachment;
    private ListView attach_listView;
    private static final int CustomGallerySelectId = 1;//Set Intent Id
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Value
    private ArrayList<Multi_Photo_Bean> encodeArray;
    private Bitmap selectedImageBitmap;
    private Multi_Photo_Bean multi_photo_bean;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_gate_in);
        pendingsize= GlobalConstants.pendingcount;
        Cust_Name= GlobalConstants.customer_name;
        attachmentstatus= GlobalConstants.attachmentStatus;


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        equip_no= GlobalConstants.equipment_no;
        gateIn_Id= GlobalConstants.GateInId;
        pre_adv_id= GlobalConstants.pre_adv_id;
        from= GlobalConstants.from;
        type= GlobalConstants.type;
        code= GlobalConstants.code;
        status= GlobalConstants.status;
        location= GlobalConstants.location;


        if(cd.isConnectingToInternet()) {
            new Equipment_More_Info().execute();
        }else{
            shortToast(getApplicationContext(),"Please Check your Internet Connection..!");
        }
        trans_no= GlobalConstants.gateIn_Trans_no;
        date= GlobalConstants.date;
        time= GlobalConstants.time;
        get_sp_previous= GlobalConstants.previous_cargo;
        Eir_no= GlobalConstants.eir_no;
        vechicle= GlobalConstants.vechicle_no;
        transport= GlobalConstants.Transport_No;
        heating_bt= GlobalConstants.heating_bt;
        rental_bt= GlobalConstants.rental_bt;


        cust_code= GlobalConstants.cust_code;
        type_id= GlobalConstants.type_id;
        code_id= GlobalConstants.code_id;
        pre_code= GlobalConstants.pre_code;
        pre_id= GlobalConstants.pre_id;
        remark = GlobalConstants.remark;
        filename =GlobalConstants.attach_filename;

        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_equip_no = (TextView)findViewById(R.id.tv_equip_no);
        tv_type = (TextView)findViewById(R.id.tv_type);
        tv_code = (TextView)findViewById(R.id.tv_code);
        tv_status = (TextView)findViewById(R.id.tv_status);
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_cargo = (TextView)findViewById(R.id.tv_cargo);
        ed_date = (EditText)findViewById(R.id.ed_date);
        ed_time = (EditText)findViewById(R.id.ed_time);
//        sp_customer = (Spinner)findViewById(R.id.sp_customer);
//        sp_type = (Spinner)findViewById(R.id.sp_type);
        LL_footer_delete = (LinearLayout) findViewById(R.id.LL_footer_delete);
        LL_footer_delete.setAlpha(0.5f);
        LL_footer_delete.setClickable(false);

        im_last_testDate = (ImageView)findViewById(R.id.im_last_Testdate);
        ed_last_test_date=(EditText)findViewById(R.id.ed_test_date);
        ed_last_test_date.setOnClickListener(this);
        im_last_testDate.setOnClickListener(this);
        ed_customer = (EditText)findViewById(R.id.ed_customer);
        ed_type = (EditText)findViewById(R.id.ed_type);
        im_manuf_date = (ImageView)findViewById(R.id.im_manuf_date);
        ed_manuf_date=(EditText)findViewById(R.id.ed_manfu);
        ed_manuf_date.setOnClickListener(this);
        im_manuf_date.setOnClickListener(this);

        ed_tare_weight=(EditText)findViewById(R.id.ed_tare_weight);
        ed_Gross_weight=(EditText)findViewById(R.id.ed_gross_weight);
        ed_capacity=(EditText)findViewById(R.id.ed_capacity);
        ed_last_survey=(EditText)findViewById(R.id.ed_Last_survay);
        ed_last_test_date=(EditText)findViewById(R.id.ed_test_date);
        sp_last_test_type=(Spinner)findViewById(R.id.sp_last_testtype);
        ed_next_date=(EditText)findViewById(R.id.ed_next_date);
        ed_next_type=(EditText)findViewById(R.id.ed_next_testtype);
        ed_info_remark=(EditText)findViewById(R.id.ed_info_remark);

        tv_manuf_date = (TextView)findViewById(R.id.tv_manuf_date);
        im_manuf_date = (ImageView)findViewById(R.id.im_manuf_date);
        im_last_testDate = (ImageView)findViewById(R.id.im_last_Testdate);



      //  ed_previous = (EditText)findViewById(R.id.ed_previous);
        sp_previous_cargo = (Spinner)findViewById(R.id.sp_previ_cargo);
        ed_attach = (EditText)findViewById(R.id.ed_attach);
        heating=(Switch)findViewById(R.id.switch_heating);
        rental=(Switch)findViewById(R.id.switch_rental);
        info_active=(Switch)findViewById(R.id.swt_moreinfo_Active);
        info_rental=(Switch)findViewById(R.id.swt_moreinfo_remtal);
        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);

        ed_equipement = (EditText)findViewById(R.id.ed_equip_no);
        ed_equipement.setText(equip_no);
        ed_equipement.addTextChangedListener(mTextEditorWatcher);
        ed_code = (EditText)findViewById(R.id.ed_code);

        ed_eir_no = (EditText)findViewById(R.id.ed_eir_no);
        ed_location = (EditText)findViewById(R.id.ed_location);
        ed_status = (EditText)findViewById(R.id.ed_status);
        ed_vechicle = (EditText)findViewById(R.id.ed_vechicle);
        ed_transport = (EditText)findViewById(R.id.ed_transport);
        ed_remark = (EditText)findViewById(R.id.ed_remarks);
        im_date =(ImageView)findViewById(R.id.im_date);
        im_Attachment =(ImageView)findViewById(R.id.im_Attachment);
        im_time =(ImageView)findViewById(R.id.im_time);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_next_text_id = (TextView) findViewById(R.id.tv_next_text_id);
        LL_Equipment_Info = (LinearLayout) findViewById(R.id.LL_Equipment_Info);
        LL_Submit = (LinearLayout) findViewById(R.id.LL_Submit);

        ed_customer.setFocusable(false);
        ed_equipement.setFocusable(false);
        ed_type.setFocusable(false);
        ed_status.setText(status);
        ed_date.setText(date);
        ed_time.setText(time);
        ed_location.setText(location);
        ed_eir_no.setText(Eir_no);
        ed_attach.setText(filename);
        ed_code.setText(code);


//        sp_previous_cargo.setSelection(get_sp_previous);

        if(vechicle.equals("")||vechicle.equalsIgnoreCase("null")||vechicle=="null")
        {
            ed_vechicle.setText("");
        }else
        {
            ed_vechicle.setText(vechicle);
        }

        ed_customer.setText(Cust_Name);
        ed_type.setText(type);
        ed_transport.setText(transport);
        ed_remark.setText(remark);

        LL_Equipment_Info.setVisibility(View.GONE);

        if(heating_bt.equalsIgnoreCase("True"))
        {
            heating.setChecked(true);
        }else
        {
            heating.setChecked(false);
        }
        if(rental_bt.equalsIgnoreCase("True"))
        {
            rental.setChecked(true);
        }else
        {
            rental.setChecked(false);
        }

       /* if(info_rental_bt.equalsIgnoreCase("True")){
            info_rental.setChecked(true);
        }else{
            info_rental.setChecked(false);
        }
        if(info_heating_bt.equalsIgnoreCase("True")){
            info_active.setChecked(true);
        }else{
            info_active.setChecked(false);
        }*/

        tv_toolbarTitle.setText("Update GateIn");
        menu=(ImageView)findViewById(R.id.iv_menu) ;
        iv_back = (ImageView)findViewById(R.id.iv_back);
        menu.setVisibility(View.GONE);
        equip_up=(ImageView)findViewById(R.id.equip_up) ;
        equip_down=(ImageView)findViewById(R.id.equip_down) ;
        fotter_add=(Button)findViewById(R.id.add);
        fotter_submit=(Button)findViewById(R.id.submit);
        fotter_submit.setOnClickListener(this);
        tv_add=(TextView)findViewById(R.id.tv_add);
        tv_add.setText("Print");
        im_add = (Button)findViewById(R.id.add);
        im_print = (Button)findViewById(R.id.print);
        im_add.setVisibility(View.GONE);
        equip_up.setVisibility(View.GONE);
        LL_Submit.setOnClickListener(this);
        im_Attachment.setOnClickListener(this);

        equip_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LL_Equipment_Info.setVisibility(View.VISIBLE);

                equip_up.setVisibility(View.VISIBLE);
                equip_down.setVisibility(View.GONE);

/*
                if((get_equipment.length()<=0) || get_equipment==null ){

                    shortToast(getApplicationContext(),"Please Enter the Equipment Number");
                    ed_equipement.requestFocus();
                    ed_manuf_date.setText(systemDate);

                }else
                {

                }*/
                //editText.requestFocus();

            }
        });

        if(cd.isConnectingToInternet())
        {
           /* new Create_GateIn_Customer_details().execute();
            new Create_GateIn_EquipmentType_details().execute();*/
            new Create_GateIn_PreviousCargo_details().execute();
            new Create_GateIn_moreInfo_list_details().execute();
        }
        else{
            shortToast(getApplicationContext(),"Please check your Internet Connection.");
        }

//        get_swt_heating="False";
        heating.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    get_swt_heating="True";
                }else
                {
                    get_swt_heating="False";

                }
            }
        });
//        get_swt_rental="False";
        rental.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {

                    new Post_Verify_Rental_Entry().execute();

                }else
                {
                    get_swt_rental="False";
                }
            }
        });
        get_swt_info_rental="False";
        info_rental.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    get_swt_info_rental="True";
                }else
                {
                    get_swt_info_rental="False";

                }
            }
        });
        get_swt_info_active="False";
        info_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    get_swt_info_active="True";
                }else
                {
                    get_swt_info_active="False";
                }
            }
        });
        equip_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL_Equipment_Info.setVisibility(View.GONE);
                equip_up.setVisibility(View.GONE);
                equip_down.setVisibility(View.VISIBLE);
            }
        });

/*
        sp_customer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                get_sp_customer=CustomerDropdownArrayList.get(position).getName();
                get_sp_customer_id=CustomerDropdownArrayList.get(position).getCode();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
/*
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                get_sp_Type=dropdown_equipment_arraylist.get(position).getName();
                get_sp_Type_Id=dropdown_equipment_arraylist.get(position).getCode();

                if(cd.isConnectingToInternet()) {
                    new PostCustomer_Code().execute();
                }else{
                    shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        sp_previous_cargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                get_sp_previous_code=dropdown_PreviousCargo_arraylist.get(i).getCode();
                get_sp_previous_id=dropdown_PreviousCargo_arraylist.get(i).getId();
                get_sp_previous=dropdown_PreviousCargo_arraylist.get(i).getDesc();
                //  shortToast(getApplicationContext(),"get_sp_previous==>"+get_sp_previous);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




       
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ed_manuf_date.setOnClickListener(this);
        im_manuf_date.setOnClickListener(this);
        ed_last_test_date.setOnClickListener(this);
        im_last_testDate.setOnClickListener(this);

        iv_back.setOnClickListener(this);
        ed_time.setOnClickListener(this);
        ed_date.setOnClickListener(this);
        im_date.setOnClickListener(this);
        im_time.setOnClickListener(this);
        bt_home = (Button)findViewById(R.id.home);
        bt_home.setOnClickListener(this);
        bt_refresh = (Button)findViewById(R.id.refresh);
        bt_refresh.setOnClickListener(this);
        bt_refresh.setText("Reset");
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

        ed_date.setText(systemDate);
        ed_time.setText(curTime);
        ed_manuf_date.setText(systemDate);
        ed_last_test_date.setText(systemDate);

        sp_last_test_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    ed_next_type.setText(dropdown_MoreInfo_arraylist.get(1).getCode());
                    tv_next_text_id.setText(dropdown_MoreInfo_arraylist.get(1).getId());
                    get_last_test_type=dropdown_MoreInfo_arraylist.get(i).getId();
                }else if(i==1)
                {
                    ed_next_type.setText(dropdown_MoreInfo_arraylist.get(0).getCode());
                    tv_next_text_id.setText(dropdown_MoreInfo_arraylist.get(0).getId());
                    get_last_test_type=dropdown_MoreInfo_arraylist.get(i).getId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        String customer = getColoredSpanned("Customer","#bbbbbb");
        String equipNo = getColoredSpanned("Equipment Number","#bbbbbb");
        String type = getColoredSpanned("Type","#bbbbbb");
        String code = getColoredSpanned("Code","#bbbbbb");
        String status = getColoredSpanned("Status","#bbbbbb");
        String date = getColoredSpanned("In Date","#bbbbbb");
        String t_time = getColoredSpanned("Time","#bbbbbb");
        String cargo = getColoredSpanned("Previous Cargo","#bbbbbb");
//        String manuf_date = getColoredSpanned("Manuf. Date","#bbbbbb");
        String surName = getColoredSpanned("*","#cb0da5");

        tv_name.setText(Html.fromHtml(customer+" "+surName));
        tv_equip_no.setText(Html.fromHtml(equipNo+" "+surName));
        tv_type.setText(Html.fromHtml(type+" "+surName));
        tv_code.setText(Html.fromHtml(code+" "+surName));
        tv_status.setText(Html.fromHtml(status+" "+surName));
        tv_date.setText(Html.fromHtml(date+" "+surName));
        tv_time.setText(Html.fromHtml(t_time+" "+surName));
        tv_cargo.setText(Html.fromHtml(cargo+" "+surName));
//        tv_manuf_date.setText(Html.fromHtml(manuf_date+" "+surName));



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(Gravity.START))
                    drawer.closeDrawer(Gravity.END);
                else
                    drawer.openDrawer(Gravity.START);
            }
        });

    }
    // EditTextWacther  Implementation

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            // When No Password Entered
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

        public void afterTextChanged(Editable s)
        {
            if(s.length()<=11) {
               // textViewPasswordStrengthIndiactor.setText("Password Max Length Reached");
               // shortToast(getApplicationContext(),"Please Enter 11 Digit");
            }else if(s.length()==11 )
            {

            }
        }
    };

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.iv_changeOfStatus:
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.LL_GateIn:
                startActivity(new Intent(getApplicationContext(),GateIn.class));
                break;
            case R.id.ed_date:
                manuf_date=false;

                last_test_date=false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_date:
                manuf_date=false;

                last_test_date=false;

                showDialog(DATE_DIALOG_ID);

                break;
            case R.id.ed_manfu:
                manuf_date=true;

                last_test_date = false;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_manuf_date:
                last_test_date = false;

                manuf_date=true;
                showDialog(DATE_DIALOG_ID);
                break;

            case R.id.ed_test_date:

                manuf_date=false;
                last_test_date = true;
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_last_Testdate:
                manuf_date=false;
                last_test_date = true;

                manuf_date=false;
                showDialog(DATE_DIALOG_ID);
                break;


            case R.id.iv_back:
                finish();
                onBackPressed();
                break;
            case R.id.submit:

//                if((attachmentstatus.equalsIgnoreCase("true") && attachmentstatus.length()>0 || new_attachment==true)) {
              /*  if( new_attachment==true) {

                    IfAttchment = "True";

                }else if(attachmentstatus.equalsIgnoreCase("False") ) {
                    IfAttchment = "False";

                }*/

                    try {
                        if (encodedImage.length() < 0) {

                        } else {
                            IfAttchment = "True";
                        }
                    } catch (Exception e) {
                         IfAttchment = "False";
                   }


                get_sp_previous = sp_previous_cargo.getSelectedItem().toString();
                get_sp_customer=ed_customer.getText().toString();
                get_sp_equipe=ed_equipement.getText().toString();
                get_equipment=ed_equipement.getText().toString();
                getType=ed_type.getText().toString();
                get_status=ed_status.getText().toString();
                get_code=ed_code.getText().toString();
                get_date=ed_date.getText().toString();
                get_time=ed_time.getText().toString();
                get_location=ed_location.getText().toString();
                get_eir_no=ed_eir_no.getText().toString();
                get_vechicle=ed_vechicle.getText().toString();
                get_transport=ed_transport.getText().toString();
                get_remark=ed_remark.getText().toString();

                get_manu_date = ed_manuf_date.getText().toString();
                get_tare_weight = ed_tare_weight.getText().toString();
                get_gross = ed_Gross_weight.getText().toString();
                get_last_survy = ed_last_survey.getText().toString();
                get_capacity = ed_capacity.getText().toString();
                get_last_test_date = ed_last_test_date.getText().toString();
                get_next_date = ed_next_date.getText().toString();
                get_next_type = tv_next_text_id.getText().toString();
              //  get_last_test_type=sp_last_test_type.getSelectedItem().toString();

                get_info_remark = ed_info_remark.getText().toString();

                if((get_equipment.trim().equals("") || get_equipment==null) ||
                        (get_sp_customer.trim().equals("") || get_sp_customer==null) ||
                        (get_sp_equipe.trim().equals("") || get_sp_equipe==null) ||
                        (get_code.trim().equals("") || get_code==null) ||
                        (get_sp_previous.trim().equals("") || get_sp_previous==null) ||
                        (get_time.trim().equals("") || get_time==null)||
                        (get_date.trim().equals("") || get_date==null))
                {
                    shortToast(getApplicationContext(), "Please key-in Mandate Fields");
                }else if(systemDate.compareTo(get_date)<0){

                    shortToast(getApplicationContext(),"In Date cannot be greater than Current Date..!");

                }else if( systemDate.compareTo(get_manu_date)<0)
                {
                    shortToast(getApplicationContext(),"Maunf. Date cannot be greater than Current Date..!");

                }
                else
                {

                    if (get_manu_date.equals(EIMNFCTR_DT) && get_tare_weight.equals(EITR_WGHT_NC) && get_gross.equals(EIGRSS_WGHT_NC)
                            && get_capacity.equals(EICPCTY_NC)
                            && get_last_survy.equals(EILST_SRVYR_NM) && get_last_test_date.equals(EILST_TST_DT)
                            && get_last_test_type.equals(EILST_TST_TYP_ID) && get_next_date.equals(EINXT_TST_DT)
                            && get_next_type.equals(EINXT_TST_TYP_ID) && get_info_remark.equals(EIRMRKS_VC) && get_swt_info_rental.equals(EIRNTL_BT)
                            && get_swt_info_active.equals(EIACTV_BT)) {

                        changes = "False";
                        if (cd.isConnectingToInternet()) {
                            new PostInfo().execute();
                        } else {
                            shortToast(getApplicationContext(), "Please Check Your Internet Connection");
                        }


                    } else {

                        changes = "True";

                        if (cd.isConnectingToInternet()) {
                            new PostInfo().execute();
                        } else {
                            shortToast(getApplicationContext(), "Please Check Your Internet Connection");
                        }

                    }

                }
                break;

            case R.id.ed_time:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.im_time:
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                boolean isPM = (hourOfDay >= 12);
                                ed_time.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12,minute, isPM ? "PM" : "AM"));
                                //   from_time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.home:
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.im_Attachment:

                selectImage();

                break;

        }
    }

    private void selectImage() {
        final CharSequence[] items = {  "Choose from Library"};

        isCamPermission = sp2.getBoolean(SP2_CAMERA_PERM_DENIED, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(Update_GateIn.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(Update_GateIn.this, isCamPermission);

               /* if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else*/ if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        startActivityForResult(new Intent(Update_GateIn.this, CustomGallery_Activity.class), CustomGallerySelectId);

                }
            }
        });
        builder.show();
    }


    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                String imagesArray = data.getStringExtra(CustomGalleryIntentKey);//get Intent data
                //Convert string array into List by splitting by ',' and substring after '[' and before ']'
                List<String> selectedImages = Arrays.asList(imagesArray.substring(1, imagesArray.length() - 1).split(", "));
                loadGridView(new ArrayList<String>(selectedImages), data);//call load gridview method by passing converted list into arrayList
            } /*else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            else
            {
                Uri uri = data.getData();
                Log.d(TAG, "File Uri: " + uri.toString());
                // Get the path
                String path = null;
                path = getPath(this,uri);
                filePath = data.getData().getPath();
                file = new File(filePath);
                Filename= file.getAbsolutePath();
                imageName=filePath.substring(Filename.lastIndexOf("/")+1);
                String basename = FilenameUtils.getBaseName(Filename);
                String fileType = FilenameUtils.getExtension(Filename);
                ed_attach.setText(basename+fileType);
                new_attachment=true;
                filename=Filename.substring(Filename.lastIndexOf("/")+1);
                Log.d(TAG, "File Path: " + path);
            }*/
        }
    }

    private void loadGridView(ArrayList<String> imagesArray,Intent imagereturnintent) {
        GridView_Adapter adapter = new GridView_Adapter(Update_GateIn.this, imagesArray, false);

        encodeArray=new ArrayList<Multi_Photo_Bean>();

        for (int i=0;i<imagesArray.size();i++){
//            imagesPathList.add(imagesArray(i));
            selectedImageBitmap = BitmapFactory.decodeFile(imagesArray.get(i));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            Log.i("encodedImage ", encodedImage);
           /* filePath = imagereturnintent.getData().getPath();
            multi_photo_bean= new Multi_Photo_Bean();
            file = new File(filePath);
            Filename= file.getAbsolutePath();
            filename=Filename.substring(Filename.lastIndexOf("/")+1);*/
            multi_photo_bean=new Multi_Photo_Bean();
            Filename="imageName";
            multi_photo_bean.setName(Filename+imagesArray.get(i));
            multi_photo_bean.setBase64(encodedImage);
            multi_photo_bean.setLength(String.valueOf(encodedImage.length()));
            encodeArray.add(multi_photo_bean);


        }
//        ed_attach.setText(multi_photo_bean.getName());
        ed_attach.setText("imageName");
        Filename=ed_attach.getText().toString();

    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        filename= destination.getAbsolutePath();
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  profile.setImageBitmap(thumbnail);
        byte[] byteArrayImage = bytes.toByteArray();
        encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        filename=filename.substring(filename.lastIndexOf("/")+1);
        ed_attach.setText(filename);
        new_attachment=true;

    }
    public String getPath(Context context, Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        Log.i("intent ", String.valueOf(data));
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (data != null) {

            Uri selectedImageUri = data.getData();
            String filePath = null;
            // OI FILE Manager
            String filemanagerstring = selectedImageUri.getPath();
            // MEDIA GALLERY
            selectedImagePath = getPath(this,selectedImageUri);
            filePath = data.getData().getPath();
            /*filePath = getPath(this, selectedImageUri);
            filename=selectedImagePath.substring(selectedImagePath.lastIndexOf("/")+1);*/
//            ed_attach.setText(filename);

            file = new File(filePath);
            Filename= file.getAbsolutePath();
            imageName=filePath.substring(Filename.lastIndexOf("/")+1);
            String basename = FilenameUtils.getBaseName(Filename);
            String fileType = FilenameUtils.getExtension(Filename);
            ed_attach.setText(basename+fileType);
           // typedFile = new TypedFile("image*//*", file);
            Bitmap selectedImageBitmap = null;
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //  profile.setImageBitmap(selectedImageBitmap);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArrayImage = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
            filename=Filename.substring(Filename.lastIndexOf("/")+1);

            new_attachment=true;


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



            if(manuf_date==true)
            {
                ed_manuf_date.setText(formatDate(year, month, day));


            }else if(last_test_date==true) {

                ed_last_test_date.setText(formatDate(year, month, day));


            }else
            {
                ed_date.setText(formatDate(year, month, day));
            }

            //    System.out.println("am a new from date====>>" + str_From);

            //  date.setText(formatDate(year, month, day));

        }
    };


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


    public class PostInfo extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        String responseString;
        private JSONArray invite_jsonlist;
        private JSONObject invitejsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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
            //  HttpPost httppost = new HttpPost("http://49.207.183.45/HH/api/accounts/RegisterUser");
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCreate_GateIn);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();

                invite_jsonlist = new JSONArray();
                 reqObj = new JSONObject();
                try {

                    for(int i=0;i<encodeArray.size();i++) {
                        invitejsonObject = new JSONObject();
                        invitejsonObject.put("FileName", encodeArray.get(i).getName());
                        invitejsonObject.put("ContentLength",encodeArray.get(i).getLength());
                        invitejsonObject.put("base64imageString", encodeArray.get(i).getBase64());
                        invite_jsonlist.put(invitejsonObject);
                    }
                    reqObj.put("ArrayOfFileParams",invite_jsonlist);




                }catch (Exception e)
                {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //update ui here
                            // display toast here
                            //    pluse.setVisibility(View.INVISIBLE);
                            // no_eventtoday.setVisibility(View.INVISIBLE);
                            //   shortToast(getApplicationContext(),"please select Members");


                        }
                    });

                }
                String numberAsString = Integer.toString( pendingsize+1);
                jsonObject.put("GTN_ID", gateIn_Id);
                jsonObject.put("CSTMR_ID", cust_code);
                jsonObject.put("CSTMR_CD", get_sp_customer);
                jsonObject.put("EQPMNT_NO", get_equipment);
                jsonObject.put("EQPMNT_TYP_ID", type_id);
                jsonObject.put("EQPMNT_TYP_CD", getType);
                jsonObject.put("EQPMNT_CD_ID",code_id );
                jsonObject.put("EQPMNT_CD_CD", get_code);
                jsonObject.put("YRD_LCTN", get_location);
                jsonObject.put("GTN_DT", get_date );
                jsonObject.put("GTN_TM", get_time);
                jsonObject.put("PRDCT_ID", get_sp_previous_id);
                jsonObject.put("PRDCT_CD", get_sp_previous_code);
                jsonObject.put("EIR_NO", get_eir_no);
                jsonObject.put("VHCL_NO", get_vechicle);
                jsonObject.put("TRNSPRTR_CD",get_transport);
                jsonObject.put("HTNG_BT", heating_bt);
                jsonObject.put("RMRKS_VC", get_remark);
                jsonObject.put("CHECKED", "True");
                jsonObject.put("PRDCT_DSCRPTN_VC", get_sp_previous);
                jsonObject.put("RNTL_BT", rental_bt);

                if(from.equalsIgnoreCase("GateIn")) {


                    if (attachmentstatus.equalsIgnoreCase("True")) {
                        jsonObject.put("RepairEstimateId", pre_adv_id);
                    }else {
                        jsonObject.put("RepairEstimateId", gateIn_Id);

                    }
                }else{
                    jsonObject.put("RepairEstimateId", gateIn_Id);

                }
                jsonObject.put("IfAttchment",IfAttchment );
                jsonObject.put("UserName",sp.getString(SP_USER_ID,"user_Id"));

                if(from.equalsIgnoreCase("GateIn")) {
                    jsonObject.put("Mode", "new");
                }else
                {
                    jsonObject.put("Mode", "edit");
                }

                jsonObject.put("EIMNFCTR_DT", get_manu_date);
                jsonObject.put("EITR_WGHT_NC", get_tare_weight);
                jsonObject.put("EIGRSS_WGHT_NC", get_gross);
                jsonObject.put("EICPCTY_NC",get_capacity);
                jsonObject.put("EILST_SRVYR_NM", get_last_survy);
                jsonObject.put("EILST_TST_DT", get_last_test_date);
                jsonObject.put("EILST_TST_TYP_ID", get_last_test_type);
                jsonObject.put("EINXT_TST_DT", get_next_date);
                jsonObject.put("EINXT_TST_TYP_ID", get_next_type);
                jsonObject.put("EIRMRKS_VC", get_info_remark);
                jsonObject.put("EIACTV_BT", get_swt_info_active);
                jsonObject.put("EIRNTL_BT", get_swt_info_rental);
                jsonObject.put("EIAttachment", "False");
                jsonObject.put("EIHasChanges", changes);
                jsonObject.put("PageName", "GateIn");
                jsonObject.put("GateinTransactionNo",trans_no);
                jsonObject.put("hfc", reqObj);



                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("Update request", jsonObject.toString());
                Log.d("Update responce", resp);
                JSONObject jsonResp = new JSONObject(resp);


                JSONObject returnMessage = jsonResp.getJSONObject("d");
                String message = returnMessage.getString("equipmentUpdate");
                responseString=message;
                Log.d("responseString", returnMessage.toString());
              /*  for(int i=0;i<returnMessage.length();i++)
                {
                    String message = returnMessage.getString(i);
                    responseString=message;
                    Log.i("....responseString...",message);
                    // loop and add it to array or arraylist
                }*/


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
            if(responseString!=null) {
                if (responseString.equalsIgnoreCase("Success") || responseString.equalsIgnoreCase("This operation requires IIS integrated pipeline mode.")) {
                    Toast.makeText(getApplicationContext(), "GateIn Updated Successfully.", Toast.LENGTH_SHORT).show();

                    finish();
                    if(GlobalConstants.from.equalsIgnoreCase("GateIn")) {
                        Intent i = new Intent(getApplicationContext(), GateIn.class);
                        startActivity(i);
                    }else{
                        Intent i = new Intent(getApplicationContext(), MySubmitList.class);
                        startActivity(i);
                    }
                } else if(responseString.equalsIgnoreCase("GateIn Not Updated")) {

                    Toast.makeText(getApplicationContext(), "Update GateIn Failed", Toast.LENGTH_SHORT).show();

                }else if(responseString.equalsIgnoreCase("EINotUpdated"))
                {

                    Toast.makeText(getApplicationContext(), "Update GateIn Failed", Toast.LENGTH_SHORT).show();


                }
            }else
            {
                Toast.makeText(getApplicationContext(), "Unable to connect the Server..!", Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();

        }
    }
    public class Create_GateIn_Customer_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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

                        dropdown_customer_list = new ArrayList<>();


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



            if(dropdown_customer_list!=null)
            {
                for(int i=0;i<dropdown_customer_list.size();i++)
                {
                    if(Cust_Name.equals(worldlist.get(i)))
                    {

                        int index = worldlist.indexOf(Cust_Name);
                        worldlist.remove(index);
                        System.out.println("before appartmentArray"+worldlist.size());

                        worldlist.add(0, Cust_Name);
                        System.out.println("after appartmentArray"+worldlist.size());

                        CustomerDropdownBean cust_dropdown=CustomerDropdownArrayList.get(index);
                        CustomerDropdownArrayList.remove(index);
                        CustomerDropdownArrayList.add(0, cust_dropdown);


                    }
                }
                ArrayAdapter<String> CustomerAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,worldlist);
                CustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sp_customer.setAdapter(CustomerAdapter);

            }
            else if(dropdown_customer_list.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");

            }

            progressDialog.dismiss();

        }

    }

    public class Create_GateIn_EquipmentType_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            //  progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCreateGateInEquipMentType);
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

                        dropdown_equipment_list = new ArrayList<>();
                        dropdown_equipment_arraylist = new ArrayList<EquipmentDropdownBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            equipment_DropdownBean = new EquipmentDropdownBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            equipment_DropdownBean.setName(jsonObject.getString("Type"));
                            equipment_DropdownBean.setCode(jsonObject.getString("Code"));
                            EquipmentType = jsonObject.getString("Type");
                            EquipmentCode = jsonObject.getString("Code");
                            String[] set1 = new String[2];
                            set1[0] = EquipmentType;
                            set1[1] = EquipmentCode;
                            dropdown_equipment_list.add(EquipmentType);
                            Equip_Type.add(set1[0]);
                            Equip_Code.add(set1[1]);
                            dropdown_equipment_arraylist.add(equipment_DropdownBean);
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



            if(dropdown_equipment_list!=null)
            {
                for(int i=0;i<dropdown_equipment_list.size();i++)
                {
                    if(type.equals(dropdown_equipment_list.get(i)))
                    {

                        int index = dropdown_equipment_list.indexOf(type);
                        dropdown_equipment_list.remove(index);
                        System.out.println("before appartmentArray"+dropdown_equipment_list.size());

                        dropdown_equipment_list.add(0, type);
                        System.out.println("after appartmentArray"+dropdown_equipment_list.size());

                        EquipmentDropdownBean type_dropdown=dropdown_equipment_arraylist.get(index);
                        dropdown_equipment_arraylist.remove(index);
                        dropdown_equipment_arraylist.add(0, type_dropdown);


                    }
                }

//                UserListAdapter adapter = new UserListAdapter(Create_GateIn.this, R.layout.list_item_row, pending_arraylist);
//                listview.setAdapter(adapter);
                ArrayAdapter<String> EquipmentAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,dropdown_equipment_list);
                EquipmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sp_type.setAdapter(EquipmentAdapter);
            }
            else if(dropdown_equipment_list.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }

            progressDialog.dismiss();

        }

    }

    public class Create_GateIn_PreviousCargo_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCreateGateInPreviousCargo);
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

                        dropdown_PreviousCargo_arraylist = new ArrayList<Previous_CargoDropdownBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            cargo_DropdownBean = new Previous_CargoDropdownBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            cargo_DropdownBean.setId(jsonObject.getString("ID"));
                            cargo_DropdownBean.setCode(jsonObject.getString("Code"));
                            cargo_DropdownBean.setDesc(jsonObject.getString("Description"));
                            PreviousCargoID = jsonObject.getString("ID");
                            PreviousCargoCode = jsonObject.getString("Code");
                            PreviousCargoDescription = jsonObject.getString("Description");
                            String[] set1 = new String[3];
                            set1[0] = PreviousCargoID;
                            set1[1] = PreviousCargoCode;
                            set1[2] = PreviousCargoDescription;
                            dropdown_PreviousCargo_list.add(PreviousCargoDescription);
                            Cargo_ID.add(set1[0]);
                            Cargo_Code.add(set1[1]);
                            Cargo_Description.add(set1[2]);
                            dropdown_PreviousCargo_arraylist.add(cargo_DropdownBean);
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



            if(dropdown_PreviousCargo_list!=null)
            {
                for(int i=0;i<dropdown_PreviousCargo_list.size();i++)
                {
                    if(get_sp_previous.equals(dropdown_PreviousCargo_list.get(i)))
                    {

                        int index = dropdown_PreviousCargo_list.indexOf(get_sp_previous);
                        dropdown_PreviousCargo_list.remove(index);
                        System.out.println("before appartmentArray"+dropdown_PreviousCargo_list.size());

                        dropdown_PreviousCargo_list.add(0, get_sp_previous);
                        System.out.println("after appartmentArray"+dropdown_PreviousCargo_list.size());

                    }
                }

                ArrayAdapter<String> CargoAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,dropdown_PreviousCargo_list);
                CargoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_previous_cargo.setAdapter(CargoAdapter);

                /*if (!get_sp_previous.equals(null)) {
                    int spinnerPosition = CargoAdapter.getPosition(get_sp_previous);
                    sp_previous_cargo.setSelection(spinnerPosition);
                }*/

            }
            else if(dropdown_PreviousCargo_list.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }

            progressDialog.dismiss();

        }

    }


    public class Equipment_More_Info extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;
        private JSONObject getJsonObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLVerify_Equipment_InFo);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                            /*"EquipmentNo":"TEST3333333",
             "PageName":"GateIn",
             "GateinTransactionNo":"",
             "Attachment":"False",*/

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("EquipmentNo", equip_no);
                jsonObject.put("PageName","GateIn");
                jsonObject.put("Attachment","False");
                jsonObject.put("GateinTransactionNo","");

               /* JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("Credentials",jsonObject);*/

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                Log.d("request", jsonObject.toString());
                JSONObject jsonrootObject = new JSONObject(resp);
                getJsonObject = jsonrootObject.getJSONObject("d");


                if (getJsonObject != null) {


                    if (getJsonObject.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found.");
                            }
                        });
                    }else {


                        EIEQPMNT_TYP_CD=(getJsonObject.getString("EIEQPMNT_TYP_CD"));
                        EICPCTY_NC=(getJsonObject.getString("EICPCTY_NC"));
                        EIGRSS_WGHT_NC=(getJsonObject.getString("EIGRSS_WGHT_NC"));
                        EILST_SRVYR_NM=(getJsonObject.getString("EILST_SRVYR_NM"));
                        EILST_TST_DT=(getJsonObject.getString("EILST_TST_DT"));
                        EILST_TST_TYP_ID=(getJsonObject.getString("EILST_TST_TYP_ID"));
                        EITR_WGHT_NC=(getJsonObject.getString("EITR_WGHT_NC"));
                        EIRMRKS_VC=(getJsonObject.getString("EIRMRKS_VC"));
                        EIACTV_BT=(getJsonObject.getString("EIACTV_BT"));
                        EIMNFCTR_DT=(getJsonObject.getString("EIMNFCTR_DT"));
                        EINXT_TST_TYP_ID=(getJsonObject.getString("EINXT_TST_TYP_ID"));
                        EIRNTL_BT=(getJsonObject.getString("EIRNTL_BT"));


                    }
                }else if(getJsonObject.length()<1){
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



            if(getJsonObject!=null)
            {

                LL_Equipment_Info.setVisibility(View.VISIBLE);
                equip_up.setVisibility(View.VISIBLE);
                equip_down.setVisibility(View.GONE);
                String[] Part1 = EIMNFCTR_DT.split(" ");
                String Manuf_date = Part1[0];
                ed_manuf_date.setText(Manuf_date);
                ed_tare_weight.setText(EITR_WGHT_NC);
                ed_Gross_weight.setText(EIGRSS_WGHT_NC);
                ed_capacity.setText(EICPCTY_NC);
                ed_last_survey.setText(EILST_SRVYR_NM);
//                ed_last_test_date.setText(EILST_TST_DT);
                //   ed_last_test_type.setText(EILST_TST_TYP_ID);
                ed_next_date.setText(EINXT_TST_DT);
                tv_next_text_id.setText(EINXT_TST_TYP_ID);
                ed_info_remark.setText(EIRMRKS_VC);


                if(EIACTV_BT.equalsIgnoreCase("true"))
                {
                    info_active.setChecked(true);
                }else
                {
                    info_active.setChecked(false);
                }

                if(EIRNTL_BT.equalsIgnoreCase("true"))
                {
                    info_rental.setChecked(true);
                }else{
                    info_rental.setChecked(false);
                }


            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }

            progressDialog.dismiss();

        }

    }

    public class Create_GateIn_moreInfo_list_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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
                ArrayAdapter<String> CargoAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_text,dropdown_MoreInfo_list);
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

    public class Post_Verify_Rental_Entry extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        JSONObject jsonobject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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

            HttpPost httpPost = new HttpPost(ConstantValues.baseURLVerify_Rental_Entry);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("EquipmentNo", equip_no);

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

                    validationStatus=jsonobject.getString("statusText");

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
                if(validationStatus.equalsIgnoreCase("Success"))
                {
                    get_swt_rental="True";

                }else if(validationStatus.contains("cannot be marked for Rental Gate In"))
                {
                    longToast(getApplicationContext(),"Equipment" +equip_no+ "cannot be marked for Rental Gate In, as there is no Rental Entry / Rental Gate Out created");
                    get_swt_rental="False";
                }
            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");
            }

            progressDialog.dismiss();

        }
    }

    public class PostCustomer_Code extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        String responseString;
        JSONObject jsonobject;
        JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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
            //  HttpPost httppost = new HttpPost("http://49.207.183.45/HH/api/accounts/RegisterUser");
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLGet_Type_Code);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bv_strType", get_sp_Type);
                jsonObject.put("bv_userName", sp.getString(SP_USER_ID,"user_Id"));

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("responce", resp);
                Log.d("req", jsonObject.toString());
                JSONObject jsonResp = new JSONObject(resp);

                jsonobject = jsonResp.getJSONObject("d");
                // jsonarray = jsonobject.getJSONArray("subcategorylist");
                if (jsonobject != null) {

                    equipement_code=jsonobject.getString("code");
                    equipement_id=jsonobject.getString("id");

/*
                    System.out.println("Am HashMap list"+jsonarray);


                    for (int i = 0; i < jsonarray.length(); i++) {

                        jsonobject = jsonarray.getJSONObject(i);

                        String Subcat_id = jsonobject.getString("SubCategoryId");
                        String Subcat_name = jsonobject.getString("SubCategoryName");


                    }
*/
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
                ed_code.setText(equipement_code);


            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");
            }

            progressDialog.dismiss();

        }
    }

    public class Post_delete_attachment extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        JSONObject jsonobject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update_GateIn.this);
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

            HttpPost httpPost = new HttpPost(ConstantValues.baseURLVerify_Rental_Entry);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("EquipmentNo", equip_no);
                jsonObject.put("bv_strAttachmentId", equip_no);
                jsonObject.put("bv_strRepairEstimateId", equip_no);
                if(from.equalsIgnoreCase("GateIn")) {
                    jsonObject.put("Mode", "new");
                }else
                {
                    jsonObject.put("Mode", "edit");
                }

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

                    deleteAttachment = jsonobject.getString("statusText");

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
                if(deleteAttachment.equalsIgnoreCase("Deleted"))
                {


                }else
                {

                }
            }
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");
            }

            progressDialog.dismiss();

        }
    }


}
