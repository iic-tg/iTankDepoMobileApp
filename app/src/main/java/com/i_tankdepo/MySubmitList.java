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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.CustomerDropdownBean;
import com.i_tankdepo.Beanclass.Multi_Photo_Bean;
import com.i_tankdepo.Beanclass.PendingAccordionBean;
import com.i_tankdepo.Beanclass.PendingBean;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Metaplore on 10/18/2016.
 */

public class MySubmitList extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ListView listview, searchlist;
    private RelativeLayout RL_musubmit, RL_pending;
    private ImageView menu, im_up, im_down, im_ok, im_close,iv_back;
    LinearLayout LL_hole, LL_Submit;
    Button bt_pending, bt_add, bt_mysubmit, bt_home, bt_refresh;
    String equip_no, equipment_no,Cust_Name,previous_crg,attachFilename,attachmentstatus,gateIn_Id,code,location,Gate_In,cust_code,type_id,code_id,pre_code,pre_id,
            vechicle,transport,Eir_no,heating_bt,rental_bt,remark,type,status,date,time,pre_adv_id,gatin_transaction_no;
    private String[] Fields = {"Customer", "Equipment No", "Type", "Previous Cargo"};
    private String[] Operators = {"Contains", "Does Not Contain", "Equals", "Not Similar", "Similar"};
    ArrayList<String> selectedlist = new ArrayList<>();
    private TextView tv_toolbarTitle, tv_add, tv_search_options,no_data,list_noData,tv_cust_name,tv_cargo,tv_equip_no,tv_type;
    private LinearLayout footer_add_btn,LL_footer_delete,LL_search_Value,LL_username;

    private Intent mServiceIntent;
    private ArrayList<PendingBean> pending_arraylist = new ArrayList<>();
    private PendingBean pending_bean;
    private ViewHolder holder;
    private Button im_add, im_print;
    private ArrayList<PendingAccordionBean> pending_accordion_arraylist = new ArrayList<>();
    private PendingAccordionBean pending_accordion_bean;
    private Spinner fieldSpinner, operatorSpinner;
    private String fieldItems, opratorItems;
    ArrayList<Product> products ;
    private ListAdapter boxAdapter;
    private ArrayList<Product> box;
    List<String> selected_name = new ArrayList<String>();
    private UserListAdapter adapter;
    private EditText searchView2,searchView1,ed_text;
    private ProgressDialog progressDialog;
    private String filename,attachID;
    private String Lock_return_Message;
    private String getEditText;
    private ImageView iv_changeOfStatus;
    private JSONObject filenamejson;
    private ArrayList<Multi_Photo_Bean> pending_attach_arraylist;
    private Multi_Photo_Bean attach_pending_bean;
    private ArrayList<CustomerDropdownBean> CustomerDropdownArrayList;
    private CustomerDropdownBean customer_DropdownBean;
    private String CustomerName,CustomerCode;
    private ArrayList<String[]> dropdown_customer_list = new ArrayList<>();
    private ArrayList<String> worldlist;
    List<String> Cust_name = new ArrayList<>();
    List<String> Cust_code = new ArrayList<>();
    private String Date_in;
    private ArrayList<Multi_Photo_Bean> new_pending_attach_arraylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gate_in);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu=(ImageView)findViewById(R.id.iv_menu) ;
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        im_down=(ImageView)findViewById(R.id.im_down) ;
        im_up=(ImageView)findViewById(R.id.im_up) ;
        listview=(ListView)findViewById(R.id.list_view);
        searchlist=(ListView)findViewById(R.id.search_list);
        searchView2=(EditText)findViewById(R.id.searchView2);
        ed_text=(EditText)findViewById(R.id.editText2);
        searchView1 = (EditText) findViewById(R.id.searchView1);
        no_data = (TextView)findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);
        list_noData = (TextView)findViewById(R.id.list_noData);
        list_noData.setVisibility(View.GONE);

        bt_pending=(Button)findViewById(R.id.bt_pending);
        RL_musubmit = (RelativeLayout) findViewById(R.id.RL_mysubmit);
        LL_hole = (LinearLayout) findViewById(R.id.LL_hole);
        LL_Submit = (LinearLayout) findViewById(R.id.LL_Submit);
        footer_add_btn = (LinearLayout) findViewById(R.id.footer_add_btn);
        LL_footer_delete = (LinearLayout) findViewById(R.id.LL_footer_delete);
        LL_username = (LinearLayout) findViewById(R.id.LL_username);

        im_ok = (ImageView)findViewById(R.id.im_ok);
        im_close = (ImageView)findViewById(R.id.im_close);
        im_ok.setOnClickListener(this);
        im_close.setOnClickListener(this);
        bt_pending.setOnClickListener(this);

        LL_Submit.setAlpha(0.5f);
        LL_footer_delete.setAlpha(0.5f);
        footer_add_btn.setAlpha(0.5f);
        LL_Submit.setClickable(false);
        footer_add_btn.setClickable(false);
        LL_footer_delete.setClickable(false);
        RL_pending = (RelativeLayout) findViewById(R.id.RL_pending);
        bt_mysubmit=(Button)findViewById(R.id.bt_mysubmit);
        bt_add=(Button)findViewById(R.id.add);
        bt_home = (Button)findViewById(R.id.home);
        bt_home.setOnClickListener(this);
        bt_refresh = (Button)findViewById(R.id.refresh);
        bt_refresh.setOnClickListener(this);
        fieldSpinner=(Spinner) findViewById(R.id.sp_fields);
        operatorSpinner=(Spinner) findViewById(R.id.sp_operators);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_search_options = (TextView) findViewById(R.id.tv_search_options);
        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);
        LL_search_Value = (LinearLayout)findViewById(R.id.LL_search_Value);
        LL_search_Value.setVisibility(View.GONE);
//        tv_search_options.setVisibility(View.GONE);
        tv_cust_name = (TextView)findViewById(R.id.tv_cust_name);
        tv_cargo = (TextView)findViewById(R.id.tv_cargo);
        tv_equip_no = (TextView)findViewById(R.id.tv_equip_no);
        tv_type = (TextView)findViewById(R.id.tv_type);
        tv_cargo.setVisibility(View.GONE);
        tv_type.setVisibility(View.GONE);
        tv_equip_no.setVisibility(View.GONE);

        tv_add.setOnClickListener(this);

        tv_toolbarTitle.setText("Gate In");
        im_add = (Button)findViewById(R.id.add);
        im_print = (Button)findViewById(R.id.print);
        im_print.setVisibility(View.GONE);
        RL_pending.setBackgroundColor(Color.parseColor("#ffffff"));

        searchView2.requestFocus();

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

        ed_text.addTextChangedListener(editTextWatcher);


        if(cd.isConnectingToInternet())
        {
            new Get_GateIn_MySubmit_details().execute();
        }
        else{
            shortToast(getApplicationContext(),"Please check your Internet Connection.");
        }


        im_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL_hole.setVisibility(View.VISIBLE);
                im_down.setVisibility(View.GONE);
                im_up.setVisibility(View.VISIBLE);

                if(cd.isConnectingToInternet()) {
                    getEditText = "";
                    if (fieldItems.equalsIgnoreCase("Customer") ||fieldItems.equalsIgnoreCase("CSTMR_CD")  ) {
                        new Create_GateIn_Customer_details().execute();
                    }else {
                        new Get_GateIn_Dropdown_details().execute();
                        new Get_GateIn_MySubmit_details().execute();

                    }
                }else
                {
                    shortToast(getApplicationContext(),"Please check Your Internet Connection");
                }
                new Get_GateIn_MySubmit_details().execute();


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

        ArrayAdapter<String> FieldsAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,Fields);
        FieldsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fieldSpinner.setAdapter(FieldsAdapter);



        ArrayAdapter<String> OperatorAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.spinner_text,Operators);
        OperatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(OperatorAdapter);



        fieldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldItems = fieldSpinner.getSelectedItem().toString();
                Log.i("Selected item : ", fieldItems);
                if(fieldItems.equalsIgnoreCase("Customer"))
                {
                    fieldItems="CSTMR_CD";
                    tv_cust_name.setVisibility(View.VISIBLE);
                    tv_cargo.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.GONE);
                    if(cd.isConnectingToInternet()) {
                        new Create_GateIn_Customer_details().execute();
                        LL_hole.setVisibility(View.GONE);
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                    }

                }else if(fieldItems.equalsIgnoreCase("Equipment No"))
                {
                    fieldItems="EQPMNT_NO";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.VISIBLE);
                    tv_cargo.setVisibility(View.GONE);
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                    }
                }else if(fieldItems.equalsIgnoreCase("Type"))
                {
                    fieldItems="EQPMNT_TYP_CD";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.VISIBLE);
                    tv_equip_no.setVisibility(View.GONE);
                    tv_cargo.setVisibility(View.GONE);
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                    }
                }else if(fieldItems.equalsIgnoreCase("Previous Cargo"))
                {
                    fieldItems="PRDCT_DSCRPTN_VC";
                    tv_cust_name.setVisibility(View.GONE);
                    tv_type.setVisibility(View.GONE);
                    tv_equip_no.setVisibility(View.GONE);
                    tv_cargo.setVisibility(View.VISIBLE);
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
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

                /*Similar,
                Contains
                Equals*/
                if(opratorItems.equalsIgnoreCase("Does Not Contain"))
                {
                    opratorItems="";
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                    }
                }else if(opratorItems.equalsIgnoreCase("Not Similar"))
                {
                    opratorItems="";
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
                    }
                }else{
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_Dropdown_details().execute();
                    }else{
                        shortToast(getApplicationContext(),"Please check your Internet Connection..!");
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
                if(drawer.isDrawerOpen(Gravity.START))
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
            if(cd.isConnectingToInternet()) {
                new Get_GateIn_Dropdown_details().execute();
            }else
            {
                shortToast(getApplicationContext(),"Please check Your Internet Connection");
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.iv_changeOfStatus:
                GlobalConstants.equipment_no="";
                GlobalConstants.status="IND";
                GlobalConstants.status_id="1";
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.add:
                startActivity(new Intent(getApplicationContext(),CaptureActivity.class));
                GlobalConstants.pendingcount= Integer.parseInt(String.valueOf(pending_arraylist.size()));
                break;
            case R.id.footer_add_btn:
                startActivity(new Intent(getApplicationContext(),CaptureActivity.class));
                GlobalConstants.pendingcount= Integer.parseInt(String.valueOf(pending_arraylist.size()));

                break;
            case R.id.tv_add:
                startActivity(new Intent(getApplicationContext(),CaptureActivity.class));
                GlobalConstants.pendingcount= Integer.parseInt(String.valueOf(pending_arraylist.size()));

                break;

            case R.id.bt_pending:

                finish();
                startActivity(new Intent(getApplicationContext(),GateIn.class));


                break;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.refresh:
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


                           /* for(int i=0;i<selected_name.size();i++) {
                                tv_search_options.append(selected_name.get(i)+", ");
                            }

                            LL_search_Value.setVisibility(View.VISIBLE);*/

                                //shortToast(getApplicationContext(),p.name);


                            } else {
                                shortToast(getApplicationContext(), "Please Select CustomerName");
                            }
                        }
                    }
                    if (cd.isConnectingToInternet()) {
                        new Get_GateIn_SearchList_details().execute();
                    } else {
                        shortToast(getApplicationContext(), "Please check Your Internet Connection");
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

    public class Get_GateIn_MySubmit_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MySubmitList.this);
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

            HttpPost httpPost = new HttpPost(ConstantValues.baseURLGateMySubmit);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("Mode", "edit");

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


                jsonarray = getJsonObject.getJSONArray("ListGateInss");
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

                        pending_arraylist = new ArrayList<>();
                        pending_attach_arraylist = new ArrayList<Multi_Photo_Bean>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            pending_bean = new PendingBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            JSONArray attachmentjson=jsonObject.getJSONArray("attchement");
                            for(int j=0;j<attachmentjson.length();j++)
                            {
                                attach_pending_bean = new Multi_Photo_Bean();
                                filenamejson=attachmentjson.getJSONObject(j);
//                                filename=filenamejson.getString("fileName");
                                attach_pending_bean.setName(filenamejson.getString("fileName"));
                                attach_pending_bean.setAttachment_Id(filenamejson.getString("attchId"));
                                attach_pending_bean.setPathUrl(filenamejson.getString("imageUrl"));
                                attach_pending_bean.setAttchPath(filenamejson.getString("attchPath"));
                                attach_pending_bean.setBase64(filenamejson.getString("imageUrl"));
                                attach_pending_bean.setEquipmentNo(jsonObject.getString("EQPMNT_NO"));
                                pending_attach_arraylist.add(attach_pending_bean);

                            }
                            pending_bean.setCustomerName(jsonObject.getString("CSTMR_CD"));
                            pending_bean.setEquipmentNo(jsonObject.getString("EQPMNT_NO"));
                            pending_bean.setType(jsonObject.getString("EQPMNT_TYP_CD"));
                            pending_bean.setCode(jsonObject.getString("EQPMNT_CD_CD"));
                            pending_bean.setLocation(jsonObject.getString("YRD_LCTN"));
                            pending_bean.setVechicle(jsonObject.getString("VHCL_NO"));
                            pending_bean.setTransport(jsonObject.getString("TRNSPRTR_CD"));
                            pending_bean.setEir_no(jsonObject.getString("EIR_NO"));
                            pending_bean.setHeating_bt(jsonObject.getString("HTNG_BT"));
                            pending_bean.setStatus(jsonObject.getString("EQPMNT_STTS_CD"));
                            pending_bean.setRental_bt(jsonObject.getString("RNTL_BT"));
                            pending_bean.setRemark(jsonObject.getString("RMRKS_VC"));
                            SimpleDateFormat fromUser = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
                            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                            Date_in=jsonObject.getString("GTN_DT");
                            String[] In_date=Date_in.split(" ");
                            Date_in=In_date[0];
                            try {
                                if (Date_in.equals(null) || Date_in.length() < 0) {

                                    Date_in = "";
                                } else {

                                    Date_in = myFormat.format(fromUser.parse(Date_in));




                                }

                            }catch (Exception e)
                            {

                            }

                            pending_bean.setDate(Date_in);
                            pending_bean.setGateIn_Id(jsonObject.getString("GTN_ID"));
                            pending_bean.setTime(jsonObject.getString("GTN_TM"));
                            pending_bean.setCust_code(jsonObject.getString("CSTMR_ID"));
                            pending_bean.setType_code(jsonObject.getString("EQPMNT_TYP_ID"));
                            pending_bean.setCode_Id(jsonObject.getString("EQPMNT_CD_ID"));
                            pending_bean.setPrev_Id(jsonObject.getString("PRDCT_ID"));
                            pending_bean.setPrev_code(jsonObject.getString("PRDCT_CD"));
                            pending_bean.setPR_ADVC_CD(jsonObject.getString("PR_ADVC_CD"));
                            pending_bean.setPreviousCargo(jsonObject.getString("PRDCT_DSCRPTN_VC"));
                            pending_bean.setGI_TRNSCTN_NO(jsonObject.getString("GI_TRNSCTN_NO"));
                          /*  String EIMNFCTR_DT;
                            String     EITR_WGHT_NC;
                            String      EIGRSS_WGHT_NC;
                            String      EICPCTY_NC;
                            String      EILST_SRVYR_NM;
                            String      EILST_TST_DT;
                            String      EILST_TST_TYP_ID;
                            String      EINXT_TST_DT;
                            String     EINXT_TST_TYP_ID;
                            String      EIRMRKS_VC;
*/

                            if((attachmentjson.length()==0)|| (attachmentjson.equals("")))
                            {
                                pending_bean.setAttachmentStatus("False");
                            }else
                            {
                                pending_bean.setAttachmentStatus("True");
                            }

                            pending_arraylist.add(pending_bean);



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
        protected void onPostExecute(Void aVoid) {


            if (pending_arraylist != null) {
                 adapter = new UserListAdapter(MySubmitList.this, R.layout.list_item_row, pending_arraylist);
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


            } else if (pending_arraylist.size() < 1) {
                shortToast(getApplicationContext(), "Data Not Found");


            }

            progressDialog.dismiss();

        }

    }


    public class UserListAdapter extends BaseAdapter {
        private final ArrayList<PendingBean> arraylist;
        Context context;
        ArrayList<PendingBean> list = new ArrayList<>();
        int resource;
        private PendingBean userListBean;
        int lastPosition=-1;

        public UserListAdapter(Context context, int resource, ArrayList<PendingBean> list) {
            this.context = context;
            this.list = list;
            this.resource = resource;
            this.arraylist = new ArrayList<PendingBean>();
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
                holder.attachmentstatus = (TextView) convertView.findViewById(R.id.text5);
                holder.type = (TextView) convertView.findViewById(R.id.tv_type);
                holder.gateIn_Id = (TextView) convertView.findViewById(R.id.text6);
                holder.code = (TextView) convertView.findViewById(R.id.tv_code);
                holder.location = (TextView) convertView.findViewById(R.id.tv_location);
                holder.transport = (TextView) convertView.findViewById(R.id.tv_transport);
                holder.vechicle = (TextView) convertView.findViewById(R.id.tv_vechicle);
                holder.Eir_no = (TextView) convertView.findViewById(R.id.text7);
                holder.remark = (TextView) convertView.findViewById(R.id.text8);
                holder.pre_adv_id = (TextView) convertView.findViewById(R.id.tv_pre_adv_id);
                holder.rental_bt = (TextView) convertView.findViewById(R.id.text10);
                holder.status = (TextView) convertView.findViewById(R.id.tv_status);
                holder.heating_bt = (TextView) convertView.findViewById(R.id.text9);
                holder.pre_code = (TextView) convertView.findViewById(R.id.tv_pre_code);
                holder.pre_id = (TextView) convertView.findViewById(R.id.tv_pre_id);
                holder.cust_code = (TextView) convertView.findViewById(R.id.tv_cust_code);
                holder.type_id = (TextView) convertView.findViewById(R.id.tv_type_code);
                holder.code_id = (TextView) convertView.findViewById(R.id.tv_code_id);
                holder.gatin_trans_no = (TextView) convertView.findViewById(R.id.text12);
                holder.filename = (TextView) convertView.findViewById(R.id.tv_text13);
                holder.attachID = (TextView) convertView.findViewById(R.id.tv_text14);
                holder.username = (TextView) convertView.findViewById(R.id.tv_username);



                // R.id.tv_customerName,R.id.tv_Inv_no,R.id.tv_date,R.id.tv_val,R.id.tv_due
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.size() < 1){
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            }else {
                userListBean = list.get(position);
               /* String[] parts = userListBean.getDate().split(" ");
                String part1_date = parts[0];
                String part1_time = parts[1];
                System.out.println("from date after split" + part1_date);


                SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                String createddate = null;
                try {
                    createddate = myFormat.format(fromUser.parse(part1_date));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
*/

                holder.equip_no.setText(userListBean.getEquipmentNo() + "," + userListBean.getType());
                holder.Cust_Name.setText(userListBean.getCustomerName());
                holder.time.setText(userListBean.getDate() + " & " + userListBean.getTime());
                holder.previous_crg.setText(userListBean.getPreviousCargo());
                holder.attachmentstatus.setText(userListBean.getAttachmentStatus());
                holder.gateIn_Id.setText(userListBean.getGateIn_Id());
                holder.location.setText(userListBean.getLocation());
                holder.vechicle.setText("");
                holder.gatin_trans_no.setText(userListBean.getGI_TRNSCTN_NO());
                holder.username.setText("Submited by: "+sp.getString(SP_USER_ID, "user_Id"));

/*
                if(userListBean.getVechicle().equals("")||userListBean.getVechicle()==null)
                {
                    holder.vechicle.setText("");
                }
               else{
                    holder.vechicle.setText(userListBean.getVechicle());
                }*/

                holder.transport.setText(userListBean.getTransport());
                holder.Eir_no.setText(userListBean.getEir_no());
                holder.heating_bt.setText(userListBean.getHeating_bt());
                holder.rental_bt.setText(userListBean.getRental_bt());
                holder.remark.setText(userListBean.getRemark());
                holder.type.setText(userListBean.getType());
                holder.code.setText(userListBean.getCode());
                holder.status.setText(userListBean.getStatus());
                holder.pre_id.setText(userListBean.getPrev_Id());
                holder.pre_code.setText(userListBean.getPrev_code());
                holder.cust_code.setText(userListBean.getCust_code());
                holder.type_id.setText(userListBean.getType_code());
                holder.code_id.setText(userListBean.getCode_Id());
                holder.pre_adv_id.setText(userListBean.getPR_ADVC_CD());
                holder.filename.setText(userListBean.getFilename());
                holder.attachID.setText(userListBean.getAttach_ID());




                holder.whole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equipment_no=list.get(position).getEquipmentNo();


                        Cust_Name= list.get(position).getCustomerName();
                        Gate_In= list.get(position).getGateIn_Id();
//                        equip_no= list.get(position).getEquipmentNo();
                        type= list.get(position).getType();
                        gatin_transaction_no= list.get(position).getGI_TRNSCTN_NO();

                        code= list.get(position).getCode();
                        status= list.get(position).getStatus();
                        location= list.get(position).getLocation();
                        date= list.get(position).getDate();
                        time= list.get(position).getTime();
                        previous_crg= list.get(position).getPreviousCargo();
                        Eir_no= list.get(position).getEir_no();
                        vechicle= list.get(position).getVechicle();
                        transport= list.get(position).getTransport();
                        heating_bt= list.get(position).getHeating_bt();
                        rental_bt= list.get(position).getRental_bt();
                        remark= list.get(position).getRemark();
                        cust_code= list.get(position).getCust_code();
                        type_id= list.get(position).getType_code();
                        code_id= list.get(position).getCode_Id();
                        pre_code= list.get(position).getPrev_code();
                        attachmentstatus= list.get(position).getAttachmentStatus();
                        pre_id= list.get(position).getPrev_Id();
                        pre_adv_id= list.get(position).getPR_ADVC_CD();
                        filename = list.get(position).getFilename();
                        attachID = list.get(position).getAttach_ID();


                        GlobalConstants.pending_attach_arraylist=pending_attach_arraylist;
                        new Get_GateIn_Lock_Check().execute();
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
                for (PendingBean wp : arraylist) {
                    if (wp.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getEquipmentNo().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getPreviousCargo().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getDate().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getType().toLowerCase(Locale.getDefault()).contains(charText)||
                            wp.getTime().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        list.add(wp);
/*
                        listview.setVisibility(View.VISIBLE);
*/
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
        TextView equip_no,time, Cust_Name,previous_crg,attachmentstatus,gateIn_Id,code,location,pre_id,pre_code,username,filename,gatin_trans_no,cust_code,type_id,code_id,
                vechicle,transport,Eir_no,heating_bt,rental_bt,remark,status,pre_adv_id,type,attachID;
        CheckBox checkBox;

        LinearLayout whole;
    }
    public class Get_GateIn_Lock_Check extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;
        private JSONObject jsonrootObject;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MySubmitList.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLVerify_EquipmentNo_Lock);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("bv_userName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("bv_strEquipmentNo", equipment_no);
                jsonObject.put("strMode", "edit");



                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d(" Lock rep", resp);
                Log.d("Lock req", jsonObject.toString());
                jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                //   jsonarray = getJsonObject.getJSONArray("ListGateInss");
                if (jsonrootObject != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonrootObject.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");
                                shortToast(getApplicationContext(), "No Records Found");

                            }
                        });
                    }else {

                        Lock_return_Message= getJsonObject.getString("lockDataValidation");

                    }
                }else if(jsonarray.length()<1){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(),"No Records Found.");
                            // LL_hole.setVisibility(View.GONE);

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



            if(jsonrootObject!=null)
            {
                if(Lock_return_Message.equalsIgnoreCase("Success"))
                {
                    Intent intent=new Intent(getApplicationContext(),Update_GateIn.class);

                    GlobalConstants.GateInId=Gate_In;
                    GlobalConstants.equipment_no= equipment_no;
                    GlobalConstants.location=location;
                    GlobalConstants.customer_name=Cust_Name;
                    GlobalConstants.code=code;
                    GlobalConstants.from="MySubmit";
                    GlobalConstants.type=type;
                    GlobalConstants.gateIn_Trans_no=gatin_transaction_no;
                    GlobalConstants.status=status;
                    GlobalConstants.date=date;
                    GlobalConstants.time=time;
                    GlobalConstants.previous_cargo=previous_crg;
                    GlobalConstants.eir_no=Eir_no;
                    GlobalConstants.vechicle_no=vechicle;
                    GlobalConstants.Transport_No=transport;
                    GlobalConstants.heating_bt=heating_bt;
                    GlobalConstants.rental_bt=rental_bt;
                    GlobalConstants.remark=remark;
                    GlobalConstants.cust_code=cust_code;
                    GlobalConstants.type_id=type_id;
                    GlobalConstants.code_id=code_id;
                    GlobalConstants.pre_code=pre_code;
                    GlobalConstants.pre_id=pre_id;
                    GlobalConstants.pre_adv_id=pre_adv_id;
                    GlobalConstants.attachmentStatus=attachmentstatus;
                    GlobalConstants.attach_filename = filename;
                    GlobalConstants.attach_ID = attachID;
                    pending_attach_arraylist= GlobalConstants.pending_attach_arraylist;
                    new_pending_attach_arraylist =new ArrayList<Multi_Photo_Bean>();
                    for (int i = 0; i < pending_attach_arraylist.size(); i++) {
                        if (equipment_no.equals(pending_attach_arraylist.get(i).getEquipmentNo())) {

                            attach_pending_bean = new Multi_Photo_Bean();
                            attach_pending_bean.setName(pending_attach_arraylist.get(i).getName());
                            attach_pending_bean.setAttachment_Id(pending_attach_arraylist.get(i).getAttachment_Id());
                            attach_pending_bean.setPathUrl(pending_attach_arraylist.get(i).getPathUrl());
                            attach_pending_bean.setAttchPath(pending_attach_arraylist.get(i).getAttchPath());
                            attach_pending_bean.setBase64(pending_attach_arraylist.get(i).getBase64());
                            new_pending_attach_arraylist.add(attach_pending_bean);

                        } else {
                        }
                    }

                    GlobalConstants.pending_attach_arraylist=new_pending_attach_arraylist;

                    startActivity(intent);

                }else {
                    shortToast(getApplicationContext(),Lock_return_Message);
                }

            }
            else
            {

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
            progressDialog = new ProgressDialog(MySubmitList.this);
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
                boxAdapter = new ListAdapter(MySubmitList.this, products);
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


    public class Get_GateIn_Dropdown_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MySubmitList.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLGatePendingFIlter);


            httpPost.setHeader("Content-Type", "application/json");


            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("filterCondition", opratorItems);

                jsonObject.put("filterValue", getEditText);

                jsonObject.put("Mode", "edit");

               /* JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("Credentials",jsonObject);*/

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
                                listview.setVisibility(View.GONE);


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

                boxAdapter = new ListAdapter(MySubmitList.this, products);
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



          /*  for(int i=0;i<selected_member_arraylist.size();i++)
            {
                if(p.memberId .equalsIgnoreCase(String.valueOf(selected_member_arraylist.get(i).getId())))
                {
                    cbBuy.setChecked(true);
                }
            }
*/
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
    public class Get_GateIn_SearchList_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;
        private JSONArray preadvicejsonlist;
        private JSONObject preadvicejsonObject;
        private JSONObject SearchValuesObject;
        private String preadviceObject;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MySubmitList.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLGateInSearchList);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try{
                JSONObject jsonObject = new JSONObject();

                preadvicejsonlist = new JSONArray();
                SearchValuesObject=new JSONObject();



                for (int i = 0; i < selected_name.size(); i++) {
                    preadvicejsonObject=new JSONObject();
                    preadvicejsonObject.put("values", selected_name.get(i));
                    preadvicejsonlist.put(preadvicejsonObject);
                }


                SearchValuesObject.put("SearchValues",preadvicejsonlist);




                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("Mode", "edit");
                jsonObject.put("SearchValues", SearchValuesObject);

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
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("ListGateInss");
                if (jsonarray != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");

//                                shortToast(getApplicationContext(), "No Records Found");
//                                products.clear();
                                shortToast(getApplicationContext(), "Data Not Found");
                                listview.setVisibility(View.GONE);

                            }
                        });
                    }else {

                        pending_arraylist = new ArrayList<>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            pending_bean = new PendingBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            JSONArray attachmentjson=jsonObject.getJSONArray("attchement");

                            for(int j=0;j<attachmentjson.length();j++)
                            {
                                filenamejson=attachmentjson.getJSONObject(j);
//                                filename=filenamejson.getString("fileName");
                                pending_bean.setFilename(filenamejson.getString("fileName"));
                                pending_bean.setAttach_ID(filenamejson.getString("attchId"));
                                //   Log.d("attachment",filename);

                            }

                            pending_bean.setCustomerName(jsonObject.getString("CSTMR_CD"));
                            pending_bean.setEquipmentNo(jsonObject.getString("EQPMNT_NO"));
                            pending_bean.setType(jsonObject.getString("EQPMNT_TYP_CD"));
                            pending_bean.setCode(jsonObject.getString("EQPMNT_CD_CD"));
                            pending_bean.setLocation(jsonObject.getString("YRD_LCTN"));
                            pending_bean.setVechicle(jsonObject.getString("VHCL_NO"));
                            pending_bean.setTransport(jsonObject.getString("TRNSPRTR_CD"));
                            pending_bean.setEir_no(jsonObject.getString("EIR_NO"));
                            pending_bean.setHeating_bt(jsonObject.getString("HTNG_BT"));
                            pending_bean.setStatus(jsonObject.getString("EQPMNT_STTS_CD"));
                            pending_bean.setRental_bt(jsonObject.getString("RNTL_BT"));
                            pending_bean.setRemark(jsonObject.getString("RMRKS_VC"));
                            pending_bean.setDate(jsonObject.getString("GTN_DT"));
                            pending_bean.setGateIn_Id(jsonObject.getString("GTN_ID"));
                            pending_bean.setTime(jsonObject.getString("GTN_TM"));
                            pending_bean.setCust_code(jsonObject.getString("CSTMR_ID"));
                            pending_bean.setType_code(jsonObject.getString("EQPMNT_TYP_ID"));
                            pending_bean.setCode_Id(jsonObject.getString("EQPMNT_CD_ID"));
                            pending_bean.setPrev_Id(jsonObject.getString("PRDCT_ID"));
                            pending_bean.setPrev_code(jsonObject.getString("PRDCT_CD"));
                            pending_bean.setPR_ADVC_CD(jsonObject.getString("PR_ADVC_CD"));
                            pending_bean.setPreviousCargo(jsonObject.getString("PRDCT_DSCRPTN_VC"));
                            pending_bean.setGI_TRNSCTN_NO(jsonObject.getString("GI_TRNSCTN_NO"));
                            pending_arraylist.add(pending_bean);
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

//                            shortToast(getApplicationContext(),"No Records Found");
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
                if (pending_arraylist != null) {

                    adapter = new UserListAdapter(MySubmitList.this, R.layout.list_item_row, pending_arraylist);
                    listview.setAdapter(adapter);

                } else {
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
