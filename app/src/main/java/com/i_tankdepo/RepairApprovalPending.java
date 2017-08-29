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
import com.i_tankdepo.Beanclass.Image_Bean;
import com.i_tankdepo.Beanclass.PendingAccordionBean;
import com.i_tankdepo.Beanclass.PendingBean;
import com.i_tankdepo.Beanclass.RepairBean;
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

import static com.i_tankdepo.R.layout.heating;




public class RepairApprovalPending extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;

    private ListView listview, searchlist;
    private RelativeLayout RL_musubmit, RL_pending,RL_heating,RL_Repair;
    private ImageView menu, im_up, im_down, im_ok, im_close;
    String equip_no, Cust_Name, previous_crg, attachmentstatus, gateIn_Id, code, location, Gate_In, cust_code, type_id, code_id, pre_code, pre_id,
            vechicle, transport, Eir_no, heating_bt, rental_bt, remark, type, status, date, time, pre_adv_id;
    LinearLayout LL_hole, LL_Submit, LL_footer_delete,LL_search_Value,LL_heat_submit,LL_heat,LL_username;
    Button repair_approval,repair_estimate,repair_completion,survey_completion,bt_pending, bt_add, bt_mysubmit, bt_home, bt_refresh, im_add, im_print,cleaning,heating,inspection;
    private String[] Fields = {"Customer", "Equipment No", "Type", "Previous Cargo"};
    private String[] Operators = {"Contains", "Does Not Contain", "Equals", "Not Similar", "Similar"};
    ArrayList<String> selectedlist = new ArrayList<>();
    private TextView tv_type,tv_equip_no,tv_cargo,tv_cust_name,tv_toolbarTitle, tv_add, tv_search_options,no_data,repair_estimate_text;
    private Intent mServiceIntent;
    private ArrayList<RepairBean> repair_arraylist = new ArrayList<>();
    private RepairBean repair_bean;
    private ArrayList<PendingAccordionBean> pending_accordion_arraylist = new ArrayList<>();
    private PendingAccordionBean pending_accordion_bean;
    private ViewHolder holder;
    List<String> Cust_name = new ArrayList<>();
    List<String> Cust_code = new ArrayList<>();
    private ArrayList<String[]> dropdown_customer_list = new ArrayList<>();
    private ArrayList<String> worldlist;
    private ArrayList<CustomerDropdownBean> CustomerDropdownArrayList;
    private CustomerDropdownBean customer_DropdownBean;
    private String CustomerName,CustomerCode;


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
    private String Date_in;
    private String LastStatusDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_estimate);
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
        no_data = (TextView)findViewById(R.id.list_noData);
        repair_estimate_text = (TextView)findViewById(R.id.tv_heating);
        repair_estimate_text.setText("Repair Approval");
        no_data.setVisibility(View.GONE);

        bt_pending = (Button) findViewById(R.id.bt_pending);
        RL_musubmit = (RelativeLayout) findViewById(R.id.RL_mysubmit);

        RL_heating =(RelativeLayout)findViewById(R.id.RL_heating);
        RL_Repair =(RelativeLayout)findViewById(R.id.RL_Repair);
        RL_Repair.setOnClickListener(this);
        RL_heating.setVisibility(View.GONE);

        repair_estimate = (Button)findViewById(R.id.repair_estimate);
        repair_estimate.setOnClickListener(this);
        repair_approval = (Button)findViewById(R.id.repair_approval);
        repair_approval.setVisibility(View.GONE);

        repair_completion = (Button)findViewById(R.id.repair_completion);
        repair_completion.setVisibility(View.GONE);
        survey_completion = (Button)findViewById(R.id.survey_completion);
        survey_completion.setVisibility(View.GONE);

        LL_hole = (LinearLayout) findViewById(R.id.LL_hole);
        LL_heat_submit = (LinearLayout) findViewById(R.id.LL_heat_submit);
        LL_heat = (LinearLayout) findViewById(R.id.LL_heat);


        im_ok = (ImageView) findViewById(R.id.im_ok);
        im_close = (ImageView) findViewById(R.id.im_close);
        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);
        iv_changeOfStatus.setVisibility(View.GONE);

        im_ok.setOnClickListener(this);
        im_close.setOnClickListener(this);

        LL_heat_submit.setAlpha(0.5f);
        LL_heat_submit.setClickable(false);

        LL_heat.setOnClickListener(this);

        RL_pending = (RelativeLayout) findViewById(R.id.RL_pending);

        bt_mysubmit = (Button) findViewById(R.id.bt_mysubmit);


        bt_mysubmit.setOnClickListener(this);
        bt_home = (Button) findViewById(R.id.heat_home);
        bt_home.setOnClickListener(this);
        bt_refresh = (Button) findViewById(R.id.heat_refresh);
        bt_refresh.setOnClickListener(this);
        fieldSpinner = (Spinner) findViewById(R.id.sp_fields);
        operatorSpinner = (Spinner) findViewById(R.id.sp_operators);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_search_options = (TextView) findViewById(R.id.tv_search_options);
        LL_search_Value = (LinearLayout)findViewById(R.id.LL_search_Value);
        scrollbar = (ScrollView)findViewById(R.id.scrollbar);
        LL_search_Value.setVisibility(View.GONE);


        tv_toolbarTitle.setText("Repair Approval");

        tv_cust_name = (TextView)findViewById(R.id.tv_cust_name);
        tv_cargo = (TextView)findViewById(R.id.tv_cargo);
        tv_equip_no = (TextView)findViewById(R.id.tv_equip_no);
        tv_type = (TextView)findViewById(R.id.tv_type);
        tv_cargo.setVisibility(View.GONE);
        tv_type.setVisibility(View.GONE);
        tv_equip_no.setVisibility(View.GONE);

        RL_musubmit.setBackgroundColor(Color.parseColor("#ffffff"));

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
            new Get_Repair_Approval_details().execute();
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
                        new Get_Repair_Approval_Dropdown_details().execute();
                        new Get_Repair_Approval_details().execute();
                    }
                } else {
                    shortToast(getApplicationContext(), "Please check Your Internet Connection");
                }
                new Get_Repair_Approval_details().execute();
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
                    if(cd.isConnectingToInternet()) {
                        new Create_GateIn_Customer_details().execute();
                        LL_hole.setVisibility(View.GONE);
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
                    }
                } else if (fieldItems.equalsIgnoreCase("Equipment No")) {
                    fieldItems = "EQPMNT_NO";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.VISIBLE);
                    tv_cargo.setVisibility(View.GONE);
                    if (cd.isConnectingToInternet()) {
                        new Get_Repair_Approval_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
                    }
                } else if (fieldItems.equalsIgnoreCase("Type")) {
                    fieldItems = "EQPMNT_TYP_CD";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.VISIBLE);
                    tv_equip_no.setVisibility(View.GONE);
                    tv_cargo.setVisibility(View.GONE);
                    if (cd.isConnectingToInternet()) {
                        new Get_Repair_Approval_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
                    }
                } else if (fieldItems.equalsIgnoreCase("Previous Cargo")) {
                    fieldItems = "PRDCT_DSCRPTN_VC";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.GONE);
                    tv_cargo.setVisibility(View.VISIBLE);
                    if (cd.isConnectingToInternet()) {
                        new Get_Repair_Approval_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
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
                    if (cd.isConnectingToInternet()) {
                        new Get_Repair_Approval_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
                    }
                } else if (opratorItems.equalsIgnoreCase("Not Similar")) {
                    opratorItems = "";
                    if (cd.isConnectingToInternet()) {
                        new Get_Repair_Approval_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
                    }
                }else{
                    if (cd.isConnectingToInternet()) {
                        new Get_Repair_Approval_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check Your Internet Connection");
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
                new Get_Repair_Approval_Dropdown_details().execute();
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
                GlobalConstants.status="AAR";
                GlobalConstants.status_id="9";
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.repair_estimate:
                startActivity(new Intent(getApplicationContext(),Repair_MainActivity.class));
                break;
            case R.id.bt_mysubmit:
                finish();
                startActivity(new Intent(getApplicationContext(),RepairApprovalMySubmit.class));
                break;
            case R.id.heat_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.repair_approval:
                startActivity(new Intent(getApplicationContext(),Repair_MainActivity.class));
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

                           /* for(int i=0;i<selected_name.size();i++) {
                                tv_search_options.append(selected_name.get(i)+", ");
                            }
                                LL_search_Value.setVisibility(View.VISIBLE);*/


                                //shortToast(getApplicationContext(),p.name);

                                if (cd.isConnectingToInternet()) {
                                    new Get_Repair_Approval_SearchList_details().execute();
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


    public class Get_Repair_Approval_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RepairApprovalPending.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLRepairApprovalList);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("Mode","new");
                jsonObject.put("PageName","Repair Approval");



                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("Response");
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

                        repair_arraylist = new ArrayList<RepairBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            repair_bean = new RepairBean();
                            jsonObject = jsonarray.getJSONObject(i);



                            repair_bean.setCustomer(jsonObject.getString("Customer"));
                            repair_bean.setEquip_no(jsonObject.getString("EquipmentNo"));

                            SimpleDateFormat fromUser = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);
                            Date_in=jsonObject.getString("InDate");
                            LastStatusDate=jsonObject.getString("LastStatusDate");
                            String[] In_date=Date_in.split(" ");
                            String[] split_LastStatusDate=LastStatusDate.split(" ");
                            Date_in=In_date[0];
                            LastStatusDate=split_LastStatusDate[0];
                            try {
                                if (Date_in.equals(null) || Date_in.length() < 0) {

                                    Date_in = "";
                                } else {

                                    Date_in = myFormat.format(fromUser.parse(Date_in));




                                } if (LastStatusDate.equals(null) || LastStatusDate.length() < 0) {

                                    LastStatusDate = "";
                                } else {

                                    LastStatusDate = myFormat.format(fromUser.parse(LastStatusDate));




                                }

                            }catch (Exception e)
                            {

                            }

                            repair_bean.setInDate(Date_in);
                            repair_bean.setLastStatusDate(LastStatusDate);
                            repair_bean.setCustomer_Id(jsonObject.getString("CSTMR_ID"));
                            repair_bean.setPrevious_cargo(jsonObject.getString("PreviousCargo"));
                            repair_bean.setLoborRate(jsonObject.getString("LaborRate"));
                            repair_bean.setLastTestType(jsonObject.getString("LastTestType"));
                            repair_bean.setType(jsonObject.getString("EquipmentType_Cd"));
                            repair_bean.setNextTestType(jsonObject.getString("NextTestType"));
                            repair_bean.setLastTestDate(jsonObject.getString("LastTestDate"));
                            repair_bean.setNextTestDate(jsonObject.getString("NextTestDate"));
                            repair_bean.setLastSurveyor(jsonObject.getString("LastSurveyor"));
                            repair_bean.setValidityPeriodForTest(jsonObject.getString("ValidityPeriodforTest"));
                            repair_bean.setRepairTypeId(jsonObject.getString("RepairTypeID"));
                            repair_bean.setRepairTypeCd(jsonObject.getString("RepairTypeCD"));
                            repair_bean.setRemarks(jsonObject.getString("Remarks"));
                            repair_bean.setInvoicingPartyCD(jsonObject.getString("InvoicingPartyCD"));
                            repair_bean.setInvoicingPartyID(jsonObject.getString("InvoicingPartyID"));
                            repair_bean.setInvoicingPartyName(jsonObject.getString("InvoicingPartyName"));
                            repair_bean.setGi_trans_no(jsonObject.getString("GiTransactionNo"));
                            repair_bean.setRepairEstimateId(jsonObject.getString("RepairEstimateID"));
                            repair_bean.setRevision_no(jsonObject.getString("RevisionNo"));
                            repair_bean.setCust_appRef(jsonObject.getString("CustomerAppRef"));
                            repair_bean.setApprovalDate(jsonObject.getString("ApprovalDate"));
                            repair_bean.setParty_appRef(jsonObject.getString("PartyAppRef"));
                            repair_bean.setSurveyor_name(jsonObject.getString("SurveyorName"));
                            repair_bean.setSurvey_completionDate(jsonObject.getString("SurveyCompletionDate"));
                            repair_bean.setLineItems(jsonObject.getString("LineItems"));
                            repair_bean.setAttachment(jsonObject.getString("attchement"));
                            repair_bean.setRepairEstimateNo(jsonObject.getString("RepairEstimateNo"));
                            repair_bean.setCurencyCD(jsonObject.getString("CurencyCD"));

                            repair_arraylist.add(repair_bean);




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
            if(repair_arraylist!=null)
            {
                adapter = new UserListAdapter(RepairApprovalPending.this, R.layout.list_item_row, repair_arraylist);
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
            else if(repair_arraylist.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }
        }

    }

    public class UserListAdapter extends BaseAdapter {
        private final ArrayList<RepairBean> arraylist;
        private ArrayList<RepairBean> list;
        Context context;

        int resource;
        private RepairBean userListBean;
        int lastPosition = -1;
        public UserListAdapter(Context context, int resource, ArrayList<RepairBean> list) {
            this.context = context;
            this.list = list;
            this.resource = resource;
            this.arraylist = new ArrayList<RepairBean>();
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

                lastPosition = position;


                holder.whole = (LinearLayout) convertView.findViewById(R.id.LL_whole);
                holder.Cust_Name = (TextView) convertView.findViewById(R.id.text1);
                holder.equip_no = (TextView) convertView.findViewById(R.id.text2);
                holder.time = (TextView) convertView.findViewById(R.id.text3);
                holder.previous_crg = (TextView) convertView.findViewById(R.id.text4);
                holder.loborRate = (TextView) convertView.findViewById(R.id.text5);
                holder.lastTestType = (TextView) convertView.findViewById(R.id.tv_type);
                holder.nextTestType = (TextView) convertView.findViewById(R.id.text6);
                holder.lastTestDate = (TextView) convertView.findViewById(R.id.tv_code);
                holder.nextTestDate = (TextView) convertView.findViewById(R.id.tv_location);
                holder.lastSurveyor = (TextView) convertView.findViewById(R.id.tv_transport);
                holder.val_PrdForTest = (TextView) convertView.findViewById(R.id.tv_vechicle);
                holder.repairTypeId = (TextView) convertView.findViewById(R.id.text7);
                holder.remark = (TextView) convertView.findViewById(R.id.text8);
                holder.repairTypeCd = (TextView) convertView.findViewById(R.id.tv_pre_adv_id);
                holder.invoicePartyID = (TextView) convertView.findViewById(R.id.text10);
                holder.invoicePartyCD = (TextView) convertView.findViewById(R.id.tv_status);
                holder.invoicePartyName = (TextView) convertView.findViewById(R.id.text9);
                holder.repairEstimateID = (TextView) convertView.findViewById(R.id.tv_pre_code);
                holder.gi_transNo = (TextView) convertView.findViewById(R.id.tv_pre_id);
                holder.revisionNo = (TextView) convertView.findViewById(R.id.tv_cust_code);
                holder.cust_appRef = (TextView) convertView.findViewById(R.id.tv_type_code);
                holder.approvalDate = (TextView) convertView.findViewById(R.id.tv_code_id);
                holder.survey_completion_date = (TextView) convertView.findViewById(R.id.tv_text11);
                holder.lineItems = (TextView) convertView.findViewById(R.id.tv_text12);
                holder.attachment = (TextView) convertView.findViewById(R.id.tv_text13);
                holder.repairEstimateNo = (TextView) convertView.findViewById(R.id.tv_text14);
                holder.lastStatusDate = (TextView) convertView.findViewById(R.id.tv_text15);
                holder.party_appRef = (TextView) convertView.findViewById(R.id.tv_text16);
                holder.surveyor_name = (TextView) convertView.findViewById(R.id.tv_text17);
                holder.type = (TextView) convertView.findViewById(R.id.tv_text20);
                holder.LL_username = (LinearLayout)convertView.findViewById(R.id.LL_username);
                holder.tv_customer_id = (TextView) convertView.findViewById(R.id.tv_customer_id);
                holder.currency = (TextView) convertView.findViewById(R.id.text_currency);

                holder.LL_username.setVisibility(View.GONE);


                // R.id.tv_customerName,R.id.tv_Inv_no,R.id.tv_date,R.id.tv_val,R.id.tv_due
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.size() < 1){
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            }else {

                userListBean = list.get(position);


                holder.equip_no.setText(userListBean.getEquip_no()+", "+userListBean.getType());
//                holder.equip_no.setText(userListBean.getEquip_no() + "," + userListBean.getEquip_statusType());
                holder.Cust_Name.setText(userListBean.getCustomer()+","+ userListBean.getPrevious_cargo());
                String [] date=userListBean.getSurvey_completionDate().split(" ");
                String sp_date=date[0];
                holder.type.setText(userListBean.getType());
                holder.time.setText(sp_date);
                holder.previous_crg.setText(userListBean.getRepairEstimateNo());

                holder.tv_customer_id.setText(userListBean.getCustomer_Id());

                holder.loborRate.setText(userListBean.getLoborRate());
                holder.lastStatusDate.setText(userListBean.getLastStatusDate());
                holder.lastTestType.setText(userListBean.getLastTestType());
                holder.nextTestType.setText(userListBean.getNextTestType());
                holder.lastTestDate.setText(userListBean.getLastTestDate());
                holder.nextTestDate.setText(userListBean.getNextTestDate());

                holder.lastSurveyor.setText(userListBean.getLastSurveyor());
                holder.val_PrdForTest.setText(userListBean.getValidityPeriodForTest());
                holder.repairTypeId.setText(userListBean.getRepairTypeId());
                holder.repairTypeCd.setText(userListBean.getRepairTypeCd());
                holder.remark.setText(userListBean.getRemarks());
                holder.invoicePartyCD.setText(userListBean.getInvoicingPartyCD());
                holder.invoicePartyID.setText(userListBean.getInvoicingPartyID());
                holder.invoicePartyName.setText(userListBean.getInvoicingPartyName());
                holder.gi_transNo.setText(userListBean.getGi_trans_no());
                holder.repairEstimateID.setText(userListBean.getRepairEstimateId());
                holder.revisionNo.setText(userListBean.getRevision_no());
                holder.cust_appRef.setText(userListBean.getCust_appRef());
                holder.approvalDate.setText(userListBean.getApprovalDate());
                holder.party_appRef.setText(userListBean.getParty_appRef());
                holder.surveyor_name.setText(userListBean.getSurveyor_name());
                holder.survey_completion_date.setText(userListBean.getSurvey_completionDate());
                holder.lineItems.setText(userListBean.getLineItems());
                holder.attachment.setText(userListBean.getAttachment());
                holder.repairEstimateNo.setText(userListBean.getRepairEstimateNo());
                holder.currency.setText(userListBean.getCurencyCD());





                holder.whole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GlobalConstants.equipment_no = list.get(position).getEquip_no();
                        GlobalConstants.customer_name = list.get(position).getCustomer();
                        GlobalConstants.indate = list.get(position).getInDate();
                        GlobalConstants.previous_cargo = list.get(position).getPrevious_cargo();
                        GlobalConstants.lastStatusDate = list.get(position).getLastStatusDate();
                        GlobalConstants.lobor_rate = list.get(position).getLoborRate();
                        GlobalConstants.last_testType = list.get(position).getLastTestType();
                        GlobalConstants.next_testType = list.get(position).getNextTestType();
                        GlobalConstants.last_testDate = list.get(position).getLastTestDate();
                        GlobalConstants.next_testDate = list.get(position).getNextTestDate();
                        GlobalConstants.lastSurveyor = list.get(position).getLastSurveyor();
                        GlobalConstants.ValidityPeriodforTest = list.get(position).getValidityPeriodForTest();
                        GlobalConstants.remark = list.get(position).getRemarks();
                        GlobalConstants.repair_TypeID = list.get(position).getRepairTypeId();
                        GlobalConstants.repair_TypeCD = list.get(position).getRepairTypeCd();
                        GlobalConstants.invoice_PartyCD = list.get(position).getInvoicingPartyCD();
                        GlobalConstants.invoice_PartyID = list.get(position).getInvoicingPartyID();
                        GlobalConstants.invoice_PartyName = list.get(position).getInvoicingPartyName();
                        GlobalConstants.gi_trans_no = list.get(position).getGi_trans_no();
                        GlobalConstants.repair_EstimateID = list.get(position).getRepairEstimateId();
                        GlobalConstants.Cust_AppRef = list.get(position).getCust_appRef();
                        GlobalConstants.approvalDate = list.get(position).getApprovalDate();
                        GlobalConstants.Party_AppRef = list.get(position).getParty_appRef();
                        GlobalConstants.revisionNo = list.get(position).getRevision_no();
                        GlobalConstants.Surveyor_name = list.get(position).getSurveyor_name();
                        GlobalConstants.Survey_CompletionDate= list.get(position).getSurvey_completionDate();
                        GlobalConstants.lineItems= list.get(position).getLineItems();
                        GlobalConstants.attchement= list.get(position).getAttachment();
                        GlobalConstants.type= list.get(position).getType();
                        GlobalConstants.cust_Id= list.get(position).getCustomer_Id();
                        GlobalConstants.repairEstimateNo= list.get(position).getRepairEstimateNo();
                        GlobalConstants.repair_arraylist=repair_arraylist;
                        GlobalConstants.currency=list.get(position).getCurencyCD();
                        GlobalConstants.from="RepairApprovelPending";
                        ArrayList<Image_Bean> encodeArray=new ArrayList();
                        GlobalConstants.multiple_encodeArray=encodeArray;
                        GlobalConstants.Line_item_Json="";
                        GlobalConstants.InvoiceParty_name = "";
                        GlobalConstants.InvoiceParty_Id = "";
                        GlobalConstants.position = "";
                        GlobalConstants.attach_count = "";
                        GlobalConstants.Invoice_party=0;
                        GlobalConstants.add_detail_jsonobject="";
                        startActivity(new Intent(getApplicationContext(),Repair_Estimation_Approvel_wizard.class));


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
            } else {
                for (RepairBean wp : arraylist) {
                    if (wp.getCustomer().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getEquip_no().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getPrevious_cargo().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getInDate().toLowerCase(Locale.getDefault()).contains(charText)
                            ) {
                        list.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }


    }
    static class ViewHolder {
        TextView equip_no,time,date,Cust_Name,previous_crg,loborRate,lastStatusDate,lastTestType,lastTestDate,nextTestType,nextTestDate,lastSurveyor,val_PrdForTest,
                repairTypeId,repairTypeCd,remark,invoicePartyCD,invoicePartyID,invoicePartyName,gi_transNo,repairEstimateID,revisionNo,
                cust_appRef,approvalDate,party_appRef,surveyor_name,survey_completion_date,lineItems,attachment,repairEstimateNo;
        CheckBox checkBox;

        LinearLayout whole;
        TextView type,currency,tv_customer_id;
        LinearLayout LL_username;
    }

    public class Create_GateIn_Customer_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RepairApprovalPending.this);
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
        protected void onPostExecute (Void aVoid){



            if(dropdown_customer_list!=null)
            {
                boxAdapter = new ListAdapter(RepairApprovalPending.this, products);
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
            else if(dropdown_customer_list.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");

            }

            progressDialog.dismiss();

        }

    }


    public class Get_Repair_Approval_Dropdown_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RepairApprovalPending.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLRepairApprovalFilter);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("filterCondition", opratorItems);
                jsonObject.put("filterValue", getEditText);
                jsonObject.put("Mode", "new");
                jsonObject.put("PageName", "Repair Approval");



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
                boxAdapter = new ListAdapter(RepairApprovalPending.this, products);
                searchlist.setAdapter(boxAdapter);



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



    public class Get_Repair_Approval_SearchList_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;
        private JSONArray preadvicejsonlist;
        private JSONObject preadvicejsonObject;
        private JSONObject SearchValuesObject;
        private String preadviceObject;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RepairApprovalPending.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLRepairApprovalSearchList);
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
                jsonObject.put("Mode", "new");
                jsonObject.put("SearchValues", SearchValuesObject);
                jsonObject.put("PageName", "Repair Approval");




                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                Log.d("Search_request", jsonObject.toString());

                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("Response");
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

                        repair_arraylist = new ArrayList<RepairBean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            repair_bean = new RepairBean();
                            jsonObject = jsonarray.getJSONObject(i);




                            repair_bean.setCustomer(jsonObject.getString("Customer"));
                            repair_bean.setEquip_no(jsonObject.getString("EquipmentNo"));
                            repair_bean.setType(jsonObject.getString("EquipmentType_Cd"));
                            repair_bean.setInDate(jsonObject.getString("InDate"));
                            repair_bean.setPrevious_cargo(jsonObject.getString("PreviousCargo"));
                            repair_bean.setLastStatusDate(jsonObject.getString("LastStatusDate"));
                            repair_bean.setLoborRate(jsonObject.getString("LaborRate"));
                            repair_bean.setLastTestType(jsonObject.getString("LastTestType"));
                            repair_bean.setNextTestType(jsonObject.getString("NextTestType"));
                            repair_bean.setLastTestDate(jsonObject.getString("LastTestDate"));
                            repair_bean.setNextTestDate(jsonObject.getString("NextTestDate"));
                            repair_bean.setLastSurveyor(jsonObject.getString("LastSurveyor"));
                            repair_bean.setValidityPeriodForTest(jsonObject.getString("ValidityPeriodforTest"));
                            repair_bean.setRepairTypeId(jsonObject.getString("RepairTypeID"));
                            repair_bean.setRepairTypeCd(jsonObject.getString("RepairTypeCD"));
                            repair_bean.setRemarks(jsonObject.getString("Remarks"));
                            repair_bean.setInvoicingPartyCD(jsonObject.getString("InvoicingPartyCD"));
                            repair_bean.setInvoicingPartyID(jsonObject.getString("InvoicingPartyID"));
                            repair_bean.setInvoicingPartyName(jsonObject.getString("InvoicingPartyName"));
                            repair_bean.setGi_trans_no(jsonObject.getString("GiTransactionNo"));
                            repair_bean.setRepairEstimateId(jsonObject.getString("RepairEstimateID"));
                            repair_bean.setRevision_no(jsonObject.getString("RevisionNo"));
                            repair_bean.setCust_appRef(jsonObject.getString("CustomerAppRef"));
                            repair_bean.setApprovalDate(jsonObject.getString("ApprovalDate"));
                            repair_bean.setParty_appRef(jsonObject.getString("PartyAppRef"));
                            repair_bean.setSurveyor_name(jsonObject.getString("SurveyorName"));
                            repair_bean.setSurvey_completionDate(jsonObject.getString("SurveyCompletionDate"));
                            repair_bean.setLineItems(jsonObject.getString("LineItems"));
                            repair_bean.setAttachment(jsonObject.getString("attchement"));
                            repair_bean.setRepairEstimateNo(jsonObject.getString("RepairEstimateNo"));
                            repair_arraylist.add(repair_bean);

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
                            shortToast(getApplicationContext(), "Data Not Found");
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

                if (repair_arraylist != null) {
                    adapter = new UserListAdapter(RepairApprovalPending.this, R.layout.list_item_row, repair_arraylist);
                    listview.setAdapter(adapter);

                } else  {
                    shortToast(getApplicationContext(), "Data Not Found");
                    listview.setVisibility(View.GONE);

                }
            }else
            {
                shortToast(getApplicationContext(), "Data Not Found");
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
