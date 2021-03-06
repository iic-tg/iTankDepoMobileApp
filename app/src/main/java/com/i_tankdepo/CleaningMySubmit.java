package com.i_tankdepo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.ScrollView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.CustomerDropdownBean;
import com.i_tankdepo.Beanclass.PendingAccordionBean;
import com.i_tankdepo.Beanclass.CleaningBean;
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

import java.util.List;
import java.util.Locale;

/**
 * Created by Metaplore on 10/18/2016.
 */

public class CleaningMySubmit extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private ListView listview, searchlist;
    private RelativeLayout RL_musubmit, RL_pending,RL_heating,RL_Repair;
    private ImageView menu, im_up, im_down, im_ok, im_close;
    String equip_no, Cust_Name, previous_crg, attachmentstatus, gateIn_Id, code, location, Gate_In, cust_code, type_id, code_id, pre_code, pre_id,
            vechicle, transport, Eir_no, heating_bt, rental_bt, remark, type, status, date, time, pre_adv_id;
    LinearLayout LL_hole, LL_Submit,LL_search_Value,LL_heat;
    Button bt_pending, bt_add, bt_mysubmit, bt_home, bt_refresh, im_add, im_print,heating,cleaning,inspection,Leaktest,bt_gateout;
    private String[] Fields = {"Customer", "Equipment No", "Type", "Previous Cargo"};
    private String[] Operators = {"Contains", "Does Not Contain", "Equals", "Not Similar", "Similar"};
    ArrayList<String> selectedlist = new ArrayList<>();
    private TextView tv_toolbarTitle, tv_add, tv_search_options,no_data,cleaning_text,list_noData,tv_type,tv_equip_no,tv_cargo,tv_cust_name;
    private Intent mServiceIntent;
    private ArrayList<CleaningBean> cleaning_arraylist = new ArrayList<>();
    private CleaningBean cleaning_bean;
    private ArrayList<PendingAccordionBean> pending_accordion_arraylist = new ArrayList<>();
    private PendingAccordionBean pending_accordion_bean;
    private ViewHolder holder;

    private Spinner fieldSpinner, operatorSpinner;
    private String fieldItems, opratorItems;
    private EditText searchView2, searchView1, ed_text;

    private UserListAdapter adapter;
    ArrayList<Product> products ;
    private ListAdapter boxAdapter;
    private ArrayList<Product> box;
    List<String> selected_name = new ArrayList<String>();
    private JSONObject filenamejson;
    private String filename;
    private ProgressDialog progressDialog;
    private String equipment_no;
    private String Lock_return_Message;
    private ImageView iv_back;
    private String getEditText;
    private ScrollView scrollbar;
    private ImageView iv_changeOfStatus;
    List<String> Cust_name = new ArrayList<>();
    List<String> Cust_code = new ArrayList<>();
    private ArrayList<String[]> dropdown_customer_list = new ArrayList<>();
    private ArrayList<String> worldlist;
    private ArrayList<CustomerDropdownBean> CustomerDropdownArrayList;
    private CustomerDropdownBean customer_DropdownBean;
    private String CustomerName,CustomerCode;
    private String LastStatusDate,OriginalCleaningDate,Date_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleaning);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        im_down = (ImageView) findViewById(R.id.im_down);
        im_up = (ImageView) findViewById(R.id.im_up);
        listview = (ListView) findViewById(R.id.list_view);
        searchlist = (ListView) findViewById(R.id.search_list);
        searchView2 = (EditText) findViewById(R.id.searchView2);
        searchView1 = (EditText) findViewById(R.id.searchView1);
        ed_text = (EditText) findViewById(R.id.editText2);
        no_data = (TextView)findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);
        bt_pending = (Button) findViewById(R.id.bt_pending);
        RL_musubmit = (RelativeLayout) findViewById(R.id.RL_mysubmit);
        LL_hole = (LinearLayout) findViewById(R.id.LL_hole);
        LL_Submit = (LinearLayout) findViewById(R.id.LL_heat_submit);
        im_ok = (ImageView) findViewById(R.id.im_ok);
        im_close = (ImageView) findViewById(R.id.im_close);
        LL_heat = (LinearLayout) findViewById(R.id.LL_heat);
        LL_heat.setAlpha(0.5f);
        LL_heat.setClickable(false);
        im_ok.setOnClickListener(this);
        im_close.setOnClickListener(this);
        bt_pending.setOnClickListener(this);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Cleaning");
        bt_gateout = (Button)findViewById(R.id.bt_gateout);
        bt_gateout.setVisibility(View.GONE);

        LL_Submit.setAlpha(0.5f);
        LL_Submit.setClickable(false);

        RL_pending = (RelativeLayout) findViewById(R.id.RL_pending);

        bt_mysubmit = (Button) findViewById(R.id.bt_mysubmit);
        RL_heating =(RelativeLayout)findViewById(R.id.RL_heating);
        RL_Repair =(RelativeLayout)findViewById(R.id.RL_Repair);
        RL_Repair.setVisibility(View.GONE);

        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);

        bt_mysubmit.setOnClickListener(this);
        bt_home = (Button) findViewById(R.id.heat_home);
        bt_home.setOnClickListener(this);
        bt_refresh = (Button) findViewById(R.id.heat_refresh);
        bt_refresh.setOnClickListener(this);
        fieldSpinner = (Spinner) findViewById(R.id.sp_fields);
        operatorSpinner = (Spinner) findViewById(R.id.sp_operators);


        tv_search_options = (TextView) findViewById(R.id.tv_search_options);
        LL_search_Value = (LinearLayout)findViewById(R.id.LL_search_Value);
        scrollbar = (ScrollView)findViewById(R.id.scrollbar);
        LL_search_Value.setVisibility(View.GONE);
        heating = (Button)findViewById(R.id.heating);
        inspection = (Button)findViewById(R.id.inspection);
        Leaktest = (Button)findViewById(R.id.leakTest);
        inspection.setVisibility(View.GONE);
        Leaktest.setVisibility(View.GONE);
        heating.setVisibility(View.GONE);
        cleaning_text = (TextView)findViewById(R.id.tv_heating);
        cleaning = (Button)findViewById(R.id.cleaning);
        cleaning_text.setText("Cleaning");
        cleaning_text.setGravity(Gravity.CENTER_HORIZONTAL);
        list_noData = (TextView)findViewById(R.id.list_noData);
        list_noData.setVisibility(View.GONE);

        tv_cust_name = (TextView)findViewById(R.id.tv_cust_name);
        tv_cargo = (TextView)findViewById(R.id.tv_cargo);
        tv_equip_no = (TextView)findViewById(R.id.tv_equip_no);
        tv_type = (TextView)findViewById(R.id.tv_type);
        tv_cargo.setVisibility(View.GONE);
        tv_type.setVisibility(View.GONE);
        tv_equip_no.setVisibility(View.GONE);

        RL_pending.setBackgroundColor(Color.parseColor("#ffffff"));

        searchView2.requestFocus();

        listview.setTextFilterEnabled(true);

        searchlist.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        im_up.setVisibility(View.GONE);
        LL_hole.setVisibility(View.GONE);
//        im_down.setVisibility(View.GONE);

        ed_text.addTextChangedListener(editTextWatcher);
//        searchView2.addTextChangedListener(mTextEditorWatcher);

        if (cd.isConnectingToInternet()) {
            new Get_Cleaning_Mysubmit_details().execute();
        } else {
            shortToast(getApplicationContext(), "Please check your Internet Connection.");
        }
        im_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL_hole.setVisibility(View.VISIBLE);
                im_down.setVisibility(View.GONE);
                im_up.setVisibility(View.VISIBLE);

                if (cd.isConnectingToInternet()) {
                    getEditText = "";
                    if (fieldItems.equalsIgnoreCase("Customer") ||fieldItems.equalsIgnoreCase("CSTMR_CD")  ) {
                        new Create_GateIn_Customer_details().execute();
                    }else {
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();
                        new Get_Cleaning_Mysubmit_details().execute();
                    }
                } else {
                    shortToast(getApplicationContext(), "Please check Your Internet Connection");
                }
                new Get_Cleaning_Mysubmit_details().execute();
            }
        });
        im_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL_hole.setVisibility(View.GONE);
                im_down.setVisibility(View.VISIBLE);
                im_up.setVisibility(View.GONE);
            }
        });




        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ArrayAdapter<String> FieldsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text, Fields);


        FieldsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldSpinner.setAdapter(FieldsAdapter);



        ArrayAdapter<String> OperatorAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text, Operators);
        OperatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(OperatorAdapter);


        fieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldItems = fieldSpinner.getSelectedItem().toString();
                Log.i("Selected item : ", fieldItems);
                if (fieldItems.equalsIgnoreCase("Customer")) {
                    fieldItems = "CSTMR_CD";
                    tv_cust_name.setVisibility(View.VISIBLE);
                    tv_cargo.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.GONE);
                    if(cd.isConnectingToInternet()){
                        new Create_GateIn_Customer_details().execute();
                        LL_hole.setVisibility(View.GONE);
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }


                } else if (fieldItems.equalsIgnoreCase("Equipment No")) {
                    fieldItems = "EQPMNT_NO";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.VISIBLE);
                    tv_cargo.setVisibility(View.GONE);
                    if(cd.isConnectingToInternet()){
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }
                } else if (fieldItems.equalsIgnoreCase("Type")) {
                    fieldItems = "EQPMNT_TYP_CD";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.VISIBLE);
                    tv_equip_no.setVisibility(View.GONE);
                    tv_cargo.setVisibility(View.GONE);
                    if(cd.isConnectingToInternet()){
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }
                } else if (fieldItems.equalsIgnoreCase("Previous Cargo")) {
                    fieldItems = "PRDCT_DSCRPTN_VC";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.GONE);
                    tv_cargo.setVisibility(View.VISIBLE);
                    if(cd.isConnectingToInternet()){
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        operatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                opratorItems = operatorSpinner.getSelectedItem().toString();

                if (opratorItems.equalsIgnoreCase("Does Not Contain")) {
                    opratorItems = "";
                    if(cd.isConnectingToInternet()){
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }
                } else if (opratorItems.equalsIgnoreCase("Not Similar")) {
                    opratorItems = "";
                    if(cd.isConnectingToInternet()){
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }
                }else{
                    if(cd.isConnectingToInternet()){
                        new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                if (drawer.isDrawerOpen(Gravity.START))

                    drawer.closeDrawer(Gravity.END);
                else
                    drawer.openDrawer(Gravity.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }




    private final TextWatcher editTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {

            getEditText = ed_text.getText().toString();
            no_data.setVisibility(View.GONE);
            LL_hole.setVisibility(View.VISIBLE);
            if (cd.isConnectingToInternet()) {
                if(cd.isConnectingToInternet()){
                    new Get_Cleaning_Mysubmit_Dropdown_details().execute();

                }else{
                    shortToast(getApplicationContext(),"Please check your Internet Connection.!");
                }
            }else if(getEditText.length()==0){

            }
            else {
                shortToast(getApplicationContext(), "Please check Your Internet Connection");
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_changeOfStatus:
                GlobalConstants.equipment_no="";
                GlobalConstants.status="ACN";
                GlobalConstants.status_id="3";
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.bt_pending:
                finish();
                startActivity(new Intent(getApplicationContext(),Cleaning.class));
                break;
            case R.id.heat_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.heat_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.im_close:
                LL_hole.setVisibility(View.GONE);
                im_down.setVisibility(View.VISIBLE);
                im_up.setVisibility(View.GONE);
                try {
                    GlobalConstants.selected_Stock_Cust_Id.removeAll( GlobalConstants.selected_Stock_Cust_Id);
                }catch (Exception e)
                {

                }
                finish();
                startActivity(getIntent());
                break;
            case R.id.im_ok:
                if(boxAdapter.getBox().size()==0) {
                    shortToast(getApplicationContext(), "Please Select atleast One Value..!");
                }else {
                    selected_name.clear();
                    for (Product p : boxAdapter.getBox()) {
                        if (p.box) {
                            if (p.box == true) {
                                String[] set = new String[2];
                                set[0] = p.name;

                                selected_name.add(set[0]);
                                GlobalConstants.selected_Stock_Cust_Id=selected_name;
                                LL_hole.setVisibility(View.GONE);
                                im_down.setVisibility(View.VISIBLE);
                                im_up.setVisibility(View.GONE);

                            /*for(int i=0;i<selected_name.size();i++) {
                                tv_search_options.append(selected_name.get(i)+", ");
                            }
                                LL_search_Value.setVisibility(View.VISIBLE);*/

                                //shortToast(getApplicationContext(),p.name);

                                if (cd.isConnectingToInternet()) {
                                    new Get_Cleaning_Mysubmit_SearchList_details().execute();
                                } else {
                                    shortToast(getApplicationContext(), "Please check Your Internet Connection");
                                }
                            } else {
                                shortToast(getApplicationContext(), "Please Select CustomerName");
                            }
                        }
                    }
                }
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        //
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            // Handle the camera action
        }else if (id == R.id.nav_changePwd) {
            startActivity(new Intent(getApplicationContext(),Change_Password.class));
        } else if (id == R.id.nav_Logout) {
            if(mServiceIntent!=null)
                getApplicationContext().stopService(mServiceIntent);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(SP_LOGGED_IN, false);
            editor.commit();
            finish();
            Intent in = new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class Get_Cleaning_Mysubmit_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CleaningMySubmit.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCleaningMySubmit);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));


                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("ArrayOfCleaning");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found");
                            }
                        });
                    }else {

                        cleaning_arraylist = new ArrayList<CleaningBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            cleaning_bean = new CleaningBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            /*JSONArray attachmentjson=jsonObject.getJSONArray("attchement");


                            for(int j=0;j<attachmentjson.length();j++)
                            {
                                filenamejson=attachmentjson.getJSONObject(j);
                                filename=filenamejson.getString("fileName");
                            }*/

                            cleaning_bean.setCustomerName(jsonObject.getString("Customer"));
                            cleaning_bean.setCustomerId(jsonObject.getString("CustomerId"));
                            cleaning_bean.setEquipno(jsonObject.getString("EquipmentNo"));
                            cleaning_bean.setEquipStatus(jsonObject.getString("EquipmentStatus"));
                            cleaning_bean.setEquipStatusType(jsonObject.getString("EquipmentStatusType"));



                            SimpleDateFormat fromUser = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                            Date_in=jsonObject.getString("InDate");
                            OriginalCleaningDate=jsonObject.getString("OriginalCleaningDate");
                            LastStatusDate=jsonObject.getString("LastStatusDate");
                            String[] In_date=Date_in.split(" ");
                            String[] Original_CleaningDate=OriginalCleaningDate.split(" ");
                            String[] LastStatus_Date=LastStatusDate.split(" ");
                            Date_in=In_date[0];
                            OriginalCleaningDate=Original_CleaningDate[0];
                            LastStatusDate=LastStatus_Date[0];

                            try {
                                if (Date_in.equals(null) || Date_in.length() < 0
                                        ||Date_in.equals("") ) {

                                    Date_in = "";
                                } else {

                                    Date_in = myFormat.format(fromUser.parse(Date_in));




                                } if (OriginalCleaningDate.equals(null) || OriginalCleaningDate.length() < 0
                                        ||OriginalCleaningDate.equals("")||OriginalCleaningDate.equals("null") ) {

                                    OriginalCleaningDate = "";
                                } else {

                                    OriginalCleaningDate = myFormat.format(fromUser.parse(OriginalCleaningDate));




                                }if (LastStatusDate.equals(null) || LastStatusDate.length() < 0
                                        ||LastStatusDate.equals("") ) {

                                    LastStatusDate = "";
                                } else {

                                    LastStatusDate = myFormat.format(fromUser.parse(LastStatusDate));




                                }

                            }catch (Exception e)
                            {

                            }


                            cleaning_bean.setInDate(Date_in);
                            cleaning_bean.setOriginalCleaningDate(OriginalCleaningDate);
                            cleaning_bean.setLastStatusDate(LastStatusDate);


//                            cleaning_bean.setInDate(jsonObject.getString("InDate"));
                            cleaning_bean.setPrevoiusCargo(jsonObject.getString("PrevoiusCargo"));
//                            cleaning_bean.setLastStatusDate(jsonObject.getString("LastStatusDate"));
                            cleaning_bean.setAdditionalCleaningBit(jsonObject.getString("AdditionalCleaningBit"));
                            cleaning_bean.setCleaningId(jsonObject.getString("CleaningId"));
                            cleaning_bean.setCleaningReferenceNo(jsonObject.getString("CleaningReferenceNo"));
                            cleaning_bean.setRemarks(jsonObject.getString("Remarks"));
//                            cleaning_bean.setOriginalCleaningDate(jsonObject.getString("OriginalCleaningDate"));
                            cleaning_bean.setCleaningRate(jsonObject.getString("CleaningRate"));
                            cleaning_bean.setSlabRate(jsonObject.getString("SlabRate"));
                            cleaning_bean.setGiTransactionNo(jsonObject.getString("GiTransactionNo"));
                            cleaning_bean.setCleaningmethod(jsonObject.getString("CleaningMethod"));
                            cleaning_bean.setChemicalName(jsonObject.getString("ChemicalName"));

                           /* if((attachmentjson.length()==0)|| (attachmentjson.equals("")))
                            {
                                cleaning_bean.setAttachmentStatus("False");
                            }else
                            {
                                cleaning_bean.setAttachmentStatus("True");
                            }*/

                            cleaning_arraylist.add(cleaning_bean);



                        }
                    }
                }else if(jsonarray.length()<1){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(),"No Records Found");


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


            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if(cleaning_arraylist!=null)
            {
                adapter = new UserListAdapter(CleaningMySubmit.this, R.layout.cleaning_list_item_row, cleaning_arraylist);
                listview.setAdapter(adapter);

                searchView2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = searchView2.getText().toString().toLowerCase(Locale.getDefault());
                        adapter.filter(text);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                        // TODO Auto-generated method stub
                    }
                });
            }
            else if(cleaning_arraylist.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }
        }

    }

    public class UserListAdapter extends BaseAdapter {
        private final ArrayList<CleaningBean> arraylist;
        private ArrayList<CleaningBean> list;
        Context context;

        int resource;
        private CleaningBean userListBean;
        int lastPosition = -1;
        public UserListAdapter(Context context, int resource, ArrayList<CleaningBean> list) {
            this.context = context;
            this.list = list;
            this.resource = resource;
            this.arraylist = new ArrayList<CleaningBean>();
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

               /* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
                convertView.startAnimation(animation);
                lastPosition = position;*/
                holder.whole = (LinearLayout) convertView.findViewById(R.id.LL_whole);
                holder.Cust_Name = (TextView) convertView.findViewById(R.id.text1);
                holder.equip_no = (TextView) convertView.findViewById(R.id.text2);
                holder.time = (TextView) convertView.findViewById(R.id.text3);
                holder.previous_crg = (TextView) convertView.findViewById(R.id.text4);
                holder.equipStatus = (TextView) convertView.findViewById(R.id.text5);
                holder.orgCleaningDate = (TextView) convertView.findViewById(R.id.tv_type);
                holder.customer_Id = (TextView) convertView.findViewById(R.id.text6);
                holder.cleaningRate = (TextView) convertView.findViewById(R.id.tv_code);
                holder.lastStatusDate = (TextView) convertView.findViewById(R.id.tv_location);
                holder.add_cleaning_bit = (TextView) convertView.findViewById(R.id.tv_transport);
                holder.vechicle = (TextView) convertView.findViewById(R.id.tv_vechicle);
                holder.cleaningId = (TextView) convertView.findViewById(R.id.text7);
                holder.remark = (TextView) convertView.findViewById(R.id.text8);
                holder.pre_adv_id = (TextView) convertView.findViewById(R.id.tv_pre_adv_id);
                holder.rental_bt = (TextView) convertView.findViewById(R.id.text10);
                holder.slabRate = (TextView) convertView.findViewById(R.id.tv_status);
                holder.cleaningRefNo = (TextView) convertView.findViewById(R.id.text9);
                holder.pre_code = (TextView) convertView.findViewById(R.id.tv_pre_code);
                holder.gi_transNo = (TextView) convertView.findViewById(R.id.tv_pre_id);
                holder.cust_code = (TextView) convertView.findViewById(R.id.tv_cust_code);
                holder.type_id = (TextView) convertView.findViewById(R.id.tv_type_code);
                holder.code_id = (TextView) convertView.findViewById(R.id.tv_code_id);
                holder.cleaningMethod = (TextView) convertView.findViewById(R.id.tv_cleaning_method);
                holder.add_Cleaning = (TextView) convertView.findViewById(R.id.tv_additional_cleraning);
                holder.username = (TextView) convertView.findViewById(R.id.tv_username);
                holder.add_Cleaning = (TextView) convertView.findViewById(R.id.tv_additional_cleraning);


                // R.id.tv_customerName,R.id.tv_Inv_no,R.id.tv_date,R.id.tv_val,R.id.tv_due
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.size() < 1){
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            }else {

                userListBean = list.get(position);
               /* String[] parts = userListBean.getInDate().split(" ");
                String part1_date = parts[0];
                String part1_time = parts[1];
                System.out.println("from date after split" + part1_date);
*/
                holder.equip_no.setText(userListBean.getEquipno() );
                holder.Cust_Name.setText(userListBean.getCustomerName());
                holder.time.setText(userListBean.getInDate());
                holder.previous_crg.setText(userListBean.getPrevoiusCargo());
//                holder.username.setText(sp.getString(SP_USER_ID,"user_Id"));

                String additional_cleaning=list.get(position).getAdditionalCleaningBit();
                if(additional_cleaning.equals("False"))
                {
                    holder.add_Cleaning .setText("No");
                }else {
                    holder.add_Cleaning .setText("Yes");


                }


/*
                if(userListBean.getVechicle().equals("")||userListBean.getVechicle()==null)
                {
                    holder.vechicle.setText("");
                }
               else{
                    holder.vechicle.setText(userListBean.getVechicle());
                }*/
                holder.equipStatus.setText(userListBean.getEquipStatus());
                holder.customer_Id.setText(userListBean.getCustomerId());
                holder.lastStatusDate.setText(userListBean.getLastStatusDate());
                holder.add_cleaning_bit.setText(userListBean.getAdditionalCleaningBit());
                holder.cleaningId.setText(userListBean.getCleaningId());
                holder.cleaningRefNo.setText(userListBean.getCleaningReferenceNo());
                holder.remark.setText(userListBean.getRemarks());
                holder.orgCleaningDate.setText(userListBean.getOriginalCleaningDate());
                holder.cleaningRate.setText(userListBean.getCleaningRate());
                holder.slabRate.setText(userListBean.getSlabRate());
                holder.gi_transNo.setText(userListBean.getGiTransactionNo());




                holder.whole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getApplicationContext(), CleaningInstruction_MySubmit.class);
                        GlobalConstants.equipment_no = list.get(position).getEquipno();
                        GlobalConstants.customer_name = list.get(position).getCustomerName();
                        GlobalConstants.customer_Id = list.get(position).getCustomerId();
                        GlobalConstants.equip_status = list.get(position).getEquipStatus();
                        GlobalConstants.equip_status_type = list.get(position).getEquipStatusType();
                        GlobalConstants.indate = list.get(position).getInDate();
                        GlobalConstants.previous_cargo = list.get(position).getPrevoiusCargo();
                        GlobalConstants.lastStatusDate = list.get(position).getLastStatusDate();
                        GlobalConstants.add_cleaning_bit = list.get(position).getAdditionalCleaningBit();
                        GlobalConstants.cleaning_id = list.get(position).getCleaningId();
                        GlobalConstants.cleaning_RefNo = list.get(position).getCleaningReferenceNo();
                        GlobalConstants.remark = list.get(position).getRemarks();
                        GlobalConstants.org_cleaningDate = list.get(position).getOriginalCleaningDate();
                        GlobalConstants.clean_rate = list.get(position).getCleaningRate();
                        GlobalConstants.cleaning_method = list.get(position).getCleaningmethod();
                        GlobalConstants.slab_rate = list.get(position).getSlabRate();
                        GlobalConstants.gi_trans_no = list.get(position).getGiTransactionNo();
                        GlobalConstants.status = "CLN";
                        GlobalConstants.equip_status = "CLN";
                        GlobalConstants.equip_status_id = "5";
                        GlobalConstants.ChemicalName =  list.get(position).getChemicalName();
                        startActivity(i);


                    }
                });

            }
            return convertView;
        }



        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            list.clear();
            if (charText.length() == 0) {
                list.addAll(arraylist);
                /*listview.setVisibility(View.VISIBLE);
                list_noData.setVisibility(View.GONE);*/
            } else {
                for (CleaningBean wp : arraylist) {
                    if (wp.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getEquipno().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getPrevoiusCargo().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getEquipStatusType().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getInDate().toLowerCase(Locale.getDefault()).contains(charText)
                            ) {
                        list.add(wp);
                       /* listview.setVisibility(View.VISIBLE);*/
                    }/*else{
                        list_noData.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                    }*/
                }
            }
            notifyDataSetChanged();
        }

    }
    static class ViewHolder {
        TextView equip_no,time, date,Cust_Name,previous_crg, equipStatus, customer_Id, cleaningRate, add_Cleaning,lastStatusDate, cleaningMethod,gi_transNo,pre_code,cust_code,type_id,code_id,
                vechicle, add_cleaning_bit, cleaningId, cleaningRefNo,username,rental_bt,remark, slabRate,pre_adv_id, orgCleaningDate;
        CheckBox checkBox;

        LinearLayout whole;
    }

    public class Create_GateIn_Customer_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CleaningMySubmit.this);
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
                        products = new ArrayList<Product>();
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
                            products.add(new Product(jsonObject.getString("Name"),false));

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
        protected void onPostExecute (Void aVoid) {


            if (jsonarray != null)
            {

                if (dropdown_customer_list != null) {
                    boxAdapter = new ListAdapter(CleaningMySubmit.this, products);
                    searchlist.setAdapter(boxAdapter);

             /*   UserListAdapterDropdown adapter = new UserListAdapterDropdown(GateIn.this, R.layout.list_item_row_accordion, pending_accordion_arraylist);
                searchlist.setAdapter(adapter);*/

                    searchView1.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub
                            String text = searchView1.getText().toString().toLowerCase(Locale.getDefault());
                            boxAdapter.filter(text);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1,
                                                      int arg2, int arg3) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                            // TODO Auto-generated method stub
                        }
                    });


                } else {
                    shortToast(getApplicationContext(), "Data Not Found");

                }
        }else {
//                shortToast(getApplicationContext(), "Data Not Found");

            }

            progressDialog.dismiss();

        }

    }


    public class Get_Cleaning_Mysubmit_Dropdown_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CleaningMySubmit.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCleaningFilter);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("filterCondition", opratorItems);
                jsonObject.put("filterValue", getEditText);
                jsonObject.put("Mode", "edit");



                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d(" rep", resp);
                Log.d(" req", jsonObject.toString());
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("ListGateInss");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
//                                shortToast(getApplicationContext(), "No Records Found");
                                products.clear();
                                no_data.setVisibility(View.VISIBLE);

                            }
                        });
                    }else {

                        pending_accordion_arraylist = new ArrayList<>();

                        products = new ArrayList<Product>();
                        for (int i = 0; i < jsonarray.length(); i++) {

                            pending_accordion_bean = new PendingAccordionBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            pending_accordion_bean.setValues(jsonObject.getString("Values"));
                            products.add(new Product(jsonObject.getString("Values"),false));

                            pending_accordion_arraylist.add(pending_accordion_bean);

                        }
                    }
                }else if(jsonarray.length()<1){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
//                            shortToast(getApplicationContext(),"No Records Found.");
                            products.clear();
                            no_data.setVisibility(View.VISIBLE);

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



            if(pending_accordion_arraylist!=null)
            {
                boxAdapter = new ListAdapter(CleaningMySubmit.this, products);
                searchlist.setAdapter(boxAdapter);

             /*   UserListAdapterDropdown adapter = new UserListAdapterDropdown(GateIn.this, R.layout.list_item_row_accordion, pending_accordion_arraylist);
                searchlist.setAdapter(adapter);*/

                searchView1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = searchView1.getText().toString().toLowerCase(Locale.getDefault());
                        boxAdapter.filter(text);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                        // TODO Auto-generated method stub
                    }
                });




            }
            else if(pending_accordion_arraylist.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");
                LL_hole.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                searchlist.setVisibility(View.GONE);


            }

            progressDialog.dismiss();

        }

    }
    public class ListAdapter extends BaseAdapter {
        private final ArrayList<Product> arraylist;
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Product> objects;
        Product p;

        ListAdapter(Context context, ArrayList<Product> products) {
            ctx = context;
            objects = products;
            this.arraylist = new ArrayList<Product>();
            this.arraylist.addAll(objects);
            lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Object getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {

                view = lInflater.inflate(R.layout.list_item_row_accordion, parent, false);
            }

            p = getProduct(position);

            ((TextView) view.findViewById(R.id.tv_cust_name)).setText(p.name);


            CheckBox cbBuy = (CheckBox) view.findViewById(R.id.checkbox);
            cbBuy.setOnCheckedChangeListener(myCheckChangList);
            cbBuy.setTag(position);
            cbBuy.setChecked(p.box);




            if(GlobalConstants.selected_Stock_Cust_Id!=null) {
                for (int i = 0; i < GlobalConstants.selected_Stock_Cust_Id.size(); i++) {
                    if (p.name.equalsIgnoreCase(String.valueOf(GlobalConstants.selected_Stock_Cust_Id.get(i)))) {
                        cbBuy.setChecked(true);
                    }
                }
            }
            return view;
        }

        Product getProduct(int position) {
            return ((Product) getItem(position));
        }

        ArrayList<Product> getBox() {


            box = new ArrayList<Product>();
            box.clear();
            for (Product p : objects) {
                if (p.box)
                    box.add(p);
            }
            return box;
        }

        CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                getProduct((Integer) buttonView.getTag()).box = isChecked;
            }
        };

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            objects.clear();
            if (charText.length() == 0) {
                objects.addAll(arraylist);
            } else {
                for (Product wp : arraylist) {
                    if (wp.name.toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        objects.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }


    }

    /*
        public class UserListAdapterDropdown extends BaseAdapter {

            Context context;
            ArrayList<PendingAccordionBean> list = new ArrayList<>();
            int resource;
            private PendingAccordionBean userListBean;
            int lastPosition=-1;

            public UserListAdapterDropdown(Context context, int resource, ArrayList<PendingAccordionBean> list) {
                this.context = context;
                this.list = list;
                this.resource = resource;
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

                   */
/* Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
                convertView.startAnimation(animation);
                lastPosition = position;*//*

                holder.whole = (LinearLayout) convertView.findViewById(R.id.LL_whole);
                holder.Cust_Name = (TextView) convertView.findViewById(R.id.tv_cust_name);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);


                // R.id.tv_customerName,R.id.tv_Inv_no,R.id.tv_date,R.id.tv_val,R.id.tv_due
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.size() < 1){
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            }else {
                userListBean = list.get(position);

                holder.Cust_Name.setText(userListBean.getValues());




                holder.whole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      */
/*  Intent i=new Intent(getApplicationContext(),View_Invoice.class);
                        i.putExtra("name", userListBean.getCustomer());
                        i.putExtra("value",userListBean.getInvoice_amount());
                        i.putExtra("due",userListBean.getInvoice_due());
                        i.putExtra("date",userListBean.getInvoice_date());
                        i.putExtra("no",userListBean.getInvoice_no());
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);*//*

                    }
                });

            }
            return convertView;
        }
    }
*/
    public class Get_Cleaning_Mysubmit_SearchList_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;
        private JSONArray preadvicejsonlist;
        private JSONObject preadvicejsonObject;
        private JSONObject SearchValuesObject;
        private String preadviceObject;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CleaningMySubmit.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
//            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandler sh = new ServiceHandler();
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLCleaninggSearchList);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try{
                JSONObject jsonObject = new JSONObject();

                preadvicejsonlist = new JSONArray();
                SearchValuesObject=new JSONObject();

                for (int i = 0; i <selected_name.size(); i++) {
                    preadvicejsonObject=new JSONObject();
                    preadvicejsonObject.put("values", selected_name.get(i));
                    preadvicejsonlist.put(preadvicejsonObject);
                }

                SearchValuesObject.put("SearchValues",preadvicejsonlist);



                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("Mode", "edit");
                jsonObject.put("SearchValues", SearchValuesObject);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                Log.d("Search_request", jsonObject.toString());

                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("ArrayOfCleaning");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found");
                                listview.setVisibility(View.GONE);
                            }
                        });
                    }else {

                        cleaning_arraylist = new ArrayList<CleaningBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            cleaning_bean = new CleaningBean();
                            jsonObject = jsonarray.getJSONObject(i);




                            cleaning_bean.setCustomerName(jsonObject.getString("Customer"));
                            cleaning_bean.setCustomerId(jsonObject.getString("CustomerId"));
                            cleaning_bean.setEquipno(jsonObject.getString("EquipmentNo"));
                            cleaning_bean.setEquipStatus(jsonObject.getString("EquipmentStatus"));
                            cleaning_bean.setEquipStatusType(jsonObject.getString("EquipmentStatusType"));
                            cleaning_bean.setInDate(jsonObject.getString("InDate"));
                            cleaning_bean.setPrevoiusCargo(jsonObject.getString("PrevoiusCargo"));
                            cleaning_bean.setLastStatusDate(jsonObject.getString("LastStatusDate"));
                            cleaning_bean.setAdditionalCleaningBit(jsonObject.getString("AdditionalCleaningBit"));
                            cleaning_bean.setCleaningId(jsonObject.getString("CleaningId"));
                            cleaning_bean.setCleaningReferenceNo(jsonObject.getString("CleaningReferenceNo"));
                            cleaning_bean.setRemarks(jsonObject.getString("Remarks"));
                            cleaning_bean.setOriginalCleaningDate(jsonObject.getString("OriginalCleaningDate"));
                            cleaning_bean.setCleaningRate(jsonObject.getString("CleaningRate"));
                            cleaning_bean.setSlabRate(jsonObject.getString("SlabRate"));
                            cleaning_bean.setGiTransactionNo(jsonObject.getString("GiTransactionNo"));
                            cleaning_bean.setCleaningmethod(jsonObject.getString("CleaningMethod"));
                            cleaning_bean.setChemicalName(jsonObject.getString("ChemicalName"));
                            cleaning_arraylist.add(cleaning_bean);

                            runOnUiThread(new Runnable(){

                                @Override
                                public void run(){
                                    //update ui here
                                    // display toast here
                                    listview.setVisibility(View.VISIBLE);

                                }
                            });


                        }
                    }
                }else {
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(),"No Records Found");
                            listview.setVisibility(View.GONE);

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


            if (jsonarray != null) {
                if (cleaning_arraylist != null) {
                    adapter = new UserListAdapter(CleaningMySubmit.this, R.layout.cleaning_list_item_row, cleaning_arraylist);
                    listview.setAdapter(adapter);

                } else {
                    shortToast(getApplicationContext(),"No Records Found");
                    listview.setVisibility(View.GONE);


                }
            }else
            {
                shortToast(getApplicationContext(),"No Records Found");
                listview.setVisibility(View.GONE);
            }
            progressDialog.dismiss();

        }

    }
    @Override
    public void onPause() {
        super.onPause();

        if ((progressDialog != null) && progressDialog.isShowing())
            progressDialog.dismiss();
        progressDialog = null;
    }

}
