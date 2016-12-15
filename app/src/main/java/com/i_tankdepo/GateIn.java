package com.i_tankdepo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.i_tankdepo.Beanclass.PendingAccordionBean;
import com.i_tankdepo.Beanclass.PendingBean;
import com.i_tankdepo.Constants.ConstantValues;
import com.i_tankdepo.Constants.GlobalConstants;
import com.i_tankdepo.helper.ServiceHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static com.i_tankdepo.R.id.im_add;
import static com.i_tankdepo.R.id.sp_fields;
import static com.i_tankdepo.R.id.sp_operators;
import static com.i_tankdepo.R.layout.list_item_row_accordion;

/**
 * Created by Metaplore on 10/18/2016.
 */

public class GateIn extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ListView listview, searchlist;
    private RelativeLayout RL_musubmit, RL_pending;
    private ImageView menu, im_up, im_down, im_ok, im_close;
    String equip_no, Cust_Name, previous_crg, attachmentstatus, gateIn_Id, code, location, Gate_In, cust_code, type_id, code_id, pre_code, pre_id,
            vechicle, transport, Eir_no, heating_bt, rental_bt, remark, type, status, date, time, pre_adv_id;
    LinearLayout LL_hole, LL_Submit, LL_footer_delete;
    Button bt_pending, bt_add, bt_mysubmit, bt_home, bt_refresh, im_add, im_print;
    private String[] Fields = {"Customer", "Equipment No", "Type", "Previous Cargo"};
    private String[] Operators = {"Contains", "Does Not Contain", "Equals", "Not Similar", "Similar"};
    ArrayList<String> selectedlist = new ArrayList<>();
    private TextView tv_toolbarTitle, tv_add, tv_search_options;
    private Intent mServiceIntent;
    private ArrayList<PendingBean> pending_arraylist = new ArrayList<>();
    private PendingBean pending_bean;
    private ArrayList<PendingAccordionBean> pending_accordion_arraylist = new ArrayList<>();
    private PendingAccordionBean pending_accordion_bean;
    private ViewHolder holder;

    private Spinner fieldSpinner, operatorSpinner;
    private String fieldItems, opratorItems;
    private EditText searchView2, searchView1, ed_text;
    private UserListAdapter adapter;
    ArrayList<Product> products = new ArrayList<Product>();
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
    private UserListAdapter.ContactsFilter mContactsFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gate_in);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        im_down = (ImageView) findViewById(R.id.im_down);
        im_up = (ImageView) findViewById(R.id.im_up);
        listview = (ListView) findViewById(R.id.list_view);
        searchlist = (ListView) findViewById(R.id.search_list);
        searchView2 = (EditText) findViewById(R.id.searchView2);
        ed_text = (EditText) findViewById(R.id.editText2);

        bt_pending = (Button) findViewById(R.id.bt_pending);
        RL_musubmit = (RelativeLayout) findViewById(R.id.RL_mysubmit);
        LL_hole = (LinearLayout) findViewById(R.id.LL_hole);
        LL_Submit = (LinearLayout) findViewById(R.id.LL_Submit);
        LL_footer_delete = (LinearLayout) findViewById(R.id.LL_footer_delete);
        im_ok = (ImageView) findViewById(R.id.im_ok);
        im_close = (ImageView) findViewById(R.id.im_close);
        im_ok.setOnClickListener(this);
        im_close.setOnClickListener(this);

        LL_Submit.setAlpha(0.5f);
        LL_footer_delete.setAlpha(0.5f);
        LL_footer_delete.setClickable(false);
        LL_Submit.setClickable(false);

        RL_pending = (RelativeLayout) findViewById(R.id.RL_pending);
        bt_mysubmit = (Button) findViewById(R.id.bt_mysubmit);
        bt_add = (Button) findViewById(R.id.add);
        bt_add.setOnClickListener(this);
        bt_mysubmit.setOnClickListener(this);
        bt_home = (Button) findViewById(R.id.home);
        bt_home.setOnClickListener(this);
        bt_refresh = (Button) findViewById(R.id.refresh);
        bt_refresh.setOnClickListener(this);
        fieldSpinner = (Spinner) findViewById(R.id.sp_fields);
        operatorSpinner = (Spinner) findViewById(R.id.sp_operators);
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_search_options = (TextView) findViewById(R.id.tv_search_options);
        tv_search_options.setVisibility(View.GONE);
        tv_add.setOnClickListener(this);
        tv_toolbarTitle.setText("Gate In");
        im_add = (Button) findViewById(R.id.add);
        im_print = (Button) findViewById(R.id.print);
        im_print.setVisibility(View.GONE);
        RL_musubmit.setBackgroundColor(Color.parseColor("#ffffff"));

        searchView2.requestFocus();

        im_up.setVisibility(View.GONE);
        LL_hole.setVisibility(View.GONE);
//        im_down.setVisibility(View.GONE);

        ed_text.addTextChangedListener(editTextWatcher);
//        searchView2.addTextChangedListener(mTextEditorWatcher);

        if (cd.isConnectingToInternet()) {
            new Get_GateIn_details().execute();
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
                    new Get_GateIn_Dropdown_details().execute();
                } else {
                    shortToast(getApplicationContext(), "Please check Your Internet Connection");
                }
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
                } else if (fieldItems.equalsIgnoreCase("Equipment No")) {
                    fieldItems = "EQPMNT_NO";
                } else if (fieldItems.equalsIgnoreCase("Type")) {
                    fieldItems = "EQPMNT_TYP_CD";
                } else if (fieldItems.equalsIgnoreCase("Previous Cargo")) {
                    fieldItems = "PRDCT_DSCRPTN_VC4";
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
                } else if (opratorItems.equalsIgnoreCase("Not Similar")) {
                    opratorItems = "";
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

            if (cd.isConnectingToInternet()) {
                new Get_GateIn_Dropdown_details().execute();
            } else {
                shortToast(getApplicationContext(), "Please check Your Internet Connection");
            }

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
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

            case R.id.bt_mysubmit:
                finish();
                startActivity(new Intent(getApplicationContext(),MySubmitList.class));
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
                break;
            case R.id.im_ok:
                for (Product p : boxAdapter.getBox()) {
                    if (p.box){
                        if(p.box==true) {
                            String[] set = new String[2];
                            set[0] = p.name;

                            selected_name.add(set[0]);
                            LL_hole.setVisibility(View.GONE);
                            im_down.setVisibility(View.VISIBLE);
                            im_up.setVisibility(View.GONE);
                            //shortToast(getApplicationContext(),p.name);

                            if(cd.isConnectingToInternet()){
                                new  Get_GateIn_SearchList_details().execute();
                            }else{
                                shortToast(getApplicationContext(),"Please check Your Internet Connection");
                            }
                        }else
                        {
                            shortToast(getApplicationContext(),"Please Select CustomerName");
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

    public class Get_GateIn_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(GateIn.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLGatePending);
            httpPost.setHeader("Content-Type", "application/json");
            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("Mode", "new");
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


                        for (int i = 0; i < jsonarray.length(); i++) {

                            pending_bean = new PendingBean();
                             jsonObject = jsonarray.getJSONObject(i);

                            JSONArray attachmentjson=jsonObject.getJSONArray("attchement");


                            for(int j=0;j<attachmentjson.length();j++)
                            {
                                filenamejson=attachmentjson.getJSONObject(j);
                                filename=filenamejson.getString("fileName");
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
        protected void onPostExecute (Void aVoid){


            if ((progressDialog != null) && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if(pending_arraylist!=null)
            {
                 adapter = new UserListAdapter(GateIn.this, R.layout.list_item_row, pending_arraylist);
                listview.setAdapter(adapter);

                searchView2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if(s.length()==0) {
//
//                            new Get_GateIn_details().execute();
//
//                        }else
//                        {
//                            adapter.getFilter().filter(s);
//
//                        }
                        adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                     adapter.getFilter().filter(s);
                    }
                });



            }
            else if(pending_arraylist.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");
            }
        }

    }


    public class UserListAdapter extends BaseAdapter {

        Context context;
        ArrayList<PendingBean> list = new ArrayList<>();
        int resource;
        private PendingBean userListBean;
        int lastPosition=-1;

        public UserListAdapter(Context context, int resource, ArrayList<PendingBean> list) {
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


                // R.id.tv_customerName,R.id.tv_Inv_no,R.id.tv_date,R.id.tv_val,R.id.tv_due
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (list.size() < 1){
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            }else {
                userListBean = list.get(position);
                String[] parts = userListBean.getDate().split(" ");
                String part1_date = parts[0];
                String part1_time = parts[1];
                System.out.println("from date after split" + part1_date);
                holder.equip_no.setText(userListBean.getEquipmentNo()+","+ userListBean.getType());
                holder.Cust_Name.setText(userListBean.getCustomerName());
                holder.time.setText(part1_date+ " & " +part1_time);
                holder.previous_crg.setText(userListBean.getPreviousCargo());
                holder.attachmentstatus.setText(userListBean.getAttachmentStatus());
                holder.gateIn_Id.setText(userListBean.getGateIn_Id());
                holder.location.setText(userListBean.getLocation());
                holder.vechicle.setText("");
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



                holder.whole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        equipment_no=list.get(position).getEquipmentNo();


                        Cust_Name= list.get(position).getCustomerName();
                        Gate_In= list.get(position).getGateIn_Id();
                        equip_no= list.get(position).getEquipmentNo();
                        type= list.get(position).getType();
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
                        new Get_GateIn_Lock_Check().execute();
                    }
                });

            }
            return convertView;
        }



        public Filter getFilter() {
            if (mContactsFilter == null)
                mContactsFilter = new ContactsFilter();

            return mContactsFilter;
        }

        // Filter

        private class ContactsFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();


                if (constraint == null || constraint.length() == 0) {
                    results.values = list;
                    results.count = list.size();
                }
                else {

                    ArrayList<PendingBean> filteredContacts = new ArrayList<PendingBean>();


                    for (PendingBean c : list) {
                        if (c.getCustomerName().toUpperCase().contains(constraint.toString().toUpperCase())) {

                            filteredContacts.add(c);
                        }
                    }


                    results.values = filteredContacts;
                    results.count = filteredContacts.size();
                }


                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<PendingBean>) results.values;
                notifyDataSetChanged();
            }
        }

    }
    static class ViewHolder {
        TextView equip_no,time, Cust_Name,previous_crg,attachmentstatus,gateIn_Id,code,location,pre_id,pre_code,cust_code,type_id,code_id,
                vechicle,transport,Eir_no,heating_bt,rental_bt,remark,status,pre_adv_id,type;
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
            progressDialog = new ProgressDialog(GateIn.this);
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
            //     httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("bv_userName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("bv_strEquipmentNo", equipment_no);
                jsonObject.put("strMode", "new");



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
                    Intent i=new Intent(getApplicationContext(),Update_GateIn.class);
                    GlobalConstants.GateInId=Gate_In;
                    GlobalConstants.equipment_no= equipment_no;
                    GlobalConstants.location=location;
                    GlobalConstants.customer_name=Cust_Name;
                    GlobalConstants.code=code;
                    GlobalConstants.type=type;
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

                    startActivity(i);
                                                                                                                                                    startActivity(i);
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



    public class Get_GateIn_Dropdown_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(GateIn.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLGatePendingFIlter);

            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("filterCondition", opratorItems);
                jsonObject.put("filterValue", getEditText);
                jsonObject.put("Mode", "new");


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
                                shortToast(getApplicationContext(), "No Records Found");
                                pending_accordion_arraylist.clear();
                                im_up.setVisibility(View.GONE);
                                LL_hole.setVisibility(View.GONE);

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

                           // pending_accordion_arraylist.add(pending_accordion_bean);

                        }
                    }
                }else if(jsonarray.length()<1){
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run(){
                            //update ui here
                            // display toast here
                            shortToast(getApplicationContext(),"No Records Found.");
                            pending_accordion_arraylist.clear();
                            im_up.setVisibility(View.GONE);
                            LL_hole.setVisibility(View.GONE);

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

                boxAdapter = new ListAdapter(GateIn.this, products);
                searchlist.setAdapter(boxAdapter);
             /*   UserListAdapterDropdown adapter = new UserListAdapterDropdown(GateIn.this, R.layout.list_item_row_accordion, pending_accordion_arraylist);
                searchlist.setAdapter(adapter);*/
                LL_hole.setVisibility(View.VISIBLE);
            }
            else if(pending_accordion_arraylist.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");
                LL_hole.setVisibility(View.GONE);


            }

            progressDialog.dismiss();

        }

    }
    public class ListAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Product> objects;
        Product p;

        ListAdapter(Context context, ArrayList<Product> products) {
            ctx = context;
            objects = products;
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
                view = lInflater.inflate(list_item_row_accordion, parent, false);
            }

            p = getProduct(position);

            ((TextView) view.findViewById(R.id.tv_cust_name)).setText(p.name);


            CheckBox cbBuy = (CheckBox) view.findViewById(R.id.checkbox);
            cbBuy.setOnCheckedChangeListener(myCheckChangList);
            cbBuy.setTag(position);
            cbBuy.setChecked(p.box);




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
        progressDialog = new ProgressDialog(GateIn.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.show();
        }
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
        //     httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
        try{
            JSONObject jsonObject = new JSONObject();

            preadvicejsonlist = new JSONArray();
            SearchValuesObject=new JSONObject();
            preadvicejsonObject=new JSONObject();

                for (int i = 0; i < selected_name.size(); i++) {

                    preadvicejsonObject.put("values", selected_name.get(i));

                }
            preadvicejsonlist.put(preadvicejsonObject);
            SearchValuesObject.put("SearchValues",preadvicejsonlist);




            jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
            jsonObject.put("filterType", fieldItems);
            jsonObject.put("Mode", "new");
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
                            shortToast(getApplicationContext(), "No Records Found");
                        }
                    });
                }else {

                    pending_arraylist = new ArrayList<>();


                    for (int i = 0; i < jsonarray.length(); i++) {

                        pending_bean = new PendingBean();
                        jsonObject = jsonarray.getJSONObject(i);




                        pending_bean.setCustomerName(jsonObject.getString("CSTMR_CD"));
                        pending_bean.setEquipmentNo(jsonObject.getString("EQPMNT_NO"));
                        pending_bean.setType(jsonObject.getString("EQPMNT_TYP_CD"));
                        pending_bean.setDate(jsonObject.getString("GTN_DT"));
                        pending_bean.setTime(jsonObject.getString("GTN_TM"));
                        pending_bean.setPreviousCargo(jsonObject.getString("PRDCT_DSCRPTN_VC"));
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
    protected void onPostExecute (Void aVoid){



        if(pending_arraylist!=null)
        {
            adapter = new UserListAdapter(GateIn.this, R.layout.list_item_row, pending_arraylist);
            listview.setAdapter(adapter);

        }
        else if(pending_arraylist.size()<1)
        {
            shortToast(getApplicationContext(),"Data Not Found");


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
