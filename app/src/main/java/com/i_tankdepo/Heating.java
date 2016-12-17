package com.i_tankdepo;

import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.i_tankdepo.Beanclass.HeatingAccordionBean;
import com.i_tankdepo.Beanclass.HeatingBean;
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
import java.util.ArrayList;
import java.util.List;

import static com.i_tankdepo.Constants.GlobalConstants.equipment_no;

/**
 * Created by Metaplore on 10/18/2016.
 */

public class Heating extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView menu, im_down, im_up, im_heat_close, im_heat_ok, iv_back;
    private ListView heating_list_view;

    private String[] Fields = {"Customer", "Equipment No", "Type", "Previous Cargo"};
    private String[] Operators = {"Contains", "Does Not Contain", "Equals", "Not Similar", "Similar"};


    private TextView tv_toolbarTitle, tv_search_options,no_data;
    LinearLayout LL_hole, LL_heat_submit,LL_search_Value,LL_heat;

    private Spinner sp_fields, sp_operator;
    private String fieldItems, opratorItems;
    private ProgressDialog progressDialog;
    private ArrayList<HeatingBean> heating_arraylist = new ArrayList<>();
    private HeatingBean heating_bean;
    private ViewHolder holder;
    private ArrayList<HeatingAccordionBean> heating_accordion_arraylist = new ArrayList<>();
    private HeatingAccordionBean heating_accordion_bean;
    ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Product> box;
    private Heating.ListAdapter boxAdapter;
    private UserListAdapter adapter;
    private ListView listview, search_heat_list;
    List<String> selected_name = new ArrayList<String>();

    private Intent mServiceIntent;

    private EditText ed_text1, searchView1, searchView2;
    private Button heat_refresh, heat_home, heat_submit;
    private String getEditText;
    private ListAdapter.ContactsFilterBox mContactsFilterBox;
    private UserListAdapter.ContactsFilter mContactsFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heating);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        im_down = (ImageView) findViewById(R.id.im_down);
        im_up = (ImageView) findViewById(R.id.im_up);
        listview = (ListView) findViewById(R.id.heating_list_view);
        LL_hole = (LinearLayout) findViewById(R.id.LL_hole);
        heat_home = (Button) findViewById(R.id.heat_home);
        heat_refresh = (Button) findViewById(R.id.heat_refresh);
        heat_submit = (Button) findViewById(R.id.heat_submit);
        LL_heat_submit = (LinearLayout) findViewById(R.id.LL_heat_submit);
        LL_heat_submit.setAlpha(0.5f);
        LL_heat_submit.setClickable(false);
        LL_heat = (LinearLayout)findViewById(R.id.LL_heat);
        LL_heat.setAlpha(0.5f);
        LL_heat.setClickable(false);
        search_heat_list = (ListView) findViewById(R.id.search_heat_list);
        ed_text1 = (EditText) findViewById(R.id.ed_text1);

        searchView1 = (EditText) findViewById(R.id.searchView1);
        searchView2 = (EditText) findViewById(R.id.searchView2);
        im_heat_close = (ImageView) findViewById(R.id.im_heat_close);
        im_heat_ok = (ImageView) findViewById(R.id.im_heat_ok);
        no_data = (TextView)findViewById(R.id.no_data);
        no_data.setVisibility(View.GONE);

        im_heat_close.setOnClickListener(this);
        im_heat_ok.setOnClickListener(this);
        heat_refresh.setOnClickListener(this);
        heat_home.setOnClickListener(this);
        tv_search_options = (TextView) findViewById(R.id.tv_search_options);

        LL_search_Value = (LinearLayout) findViewById(R.id.LL_search_Value);
//        tv_search_options.setVisibility(View.GONE);
        LL_search_Value.setVisibility(View.GONE);

        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Heating");
        sp_fields = (Spinner) findViewById(R.id.sp_heat_customer);
        sp_operator = (Spinner) findViewById(R.id.sp_heat_operator);

        searchView2.requestFocus();
        search_heat_list.setOnTouchListener(new View.OnTouchListener() {
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
        ed_text1.addTextChangedListener(editTextWatcher);



//        heating_list_view.setOnItemClickListener(this);

        im_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LL_hole.setVisibility(View.VISIBLE);
                im_down.setVisibility(View.GONE);
                im_up.setVisibility(View.VISIBLE);

                if(cd.isConnectingToInternet()) {
                    getEditText = "";
                    new Get_Heating_filter().execute();
                }else
                {
                    shortToast(getApplicationContext(),"Please check Your Internet Connection");
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

        if (cd.isConnectingToInternet()) {
            new Get_Heating_details().execute();
        } else {
            shortToast(getApplicationContext(), "Please Check your Internet Connection.");
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ArrayAdapter<String> FieldsAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text,Fields);
        FieldsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_fields.setAdapter(FieldsAdapter);


        ArrayAdapter<String> OperatorAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_text,Operators);
        OperatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_operator.setAdapter(OperatorAdapter);

        sp_fields.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fieldItems = sp_fields.getSelectedItem().toString();
                Log.i("Selected item : ", fieldItems);
                if (fieldItems.equalsIgnoreCase("Customer")) {
                    fieldItems = "CSTMR_CD";

                    new Get_Heating_filter().execute();
                    LL_hole.setVisibility(View.GONE);
                } else if (fieldItems.equalsIgnoreCase("Equipment No")) {
                    fieldItems = "EQPMNT_NO";
                    new Get_Heating_filter().execute();
                } else if (fieldItems.equalsIgnoreCase("Type")) {
                    fieldItems = "EQPMNT_TYP_CD";
                    new Get_Heating_filter().execute();
                } else if (fieldItems.equalsIgnoreCase("Previous Cargo")) {
                    fieldItems = "PRDCT_DSCRPTN_VC";
                    new Get_Heating_filter().execute();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_operator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opratorItems = sp_operator.getSelectedItem().toString();


                if (opratorItems.equalsIgnoreCase("Does Not Contain")) {
                    opratorItems = "";
                    new Get_Heating_filter().execute();
                } else if (opratorItems.equalsIgnoreCase("Not Similar")) {
                    opratorItems = "";
                    new Get_Heating_filter().execute();
                }else{
                    new Get_Heating_filter().execute();
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

            getEditText = ed_text1.getText().toString();
            no_data.setVisibility(View.GONE);
            LL_hole.setVisibility(View.VISIBLE);
            if (cd.isConnectingToInternet()) {
                new Get_Heating_filter().execute();
            } else {
                shortToast(getApplicationContext(), "Please check Your Internet Connection");
            }
        }
    };




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
            case R.id.im_heat_close:
                LL_hole.setVisibility(View.GONE);
                im_down.setVisibility(View.VISIBLE);
                im_up.setVisibility(View.GONE);
                break;
            case R.id.im_heat_ok:
                for (Product p : boxAdapter.getBox()) {
                    if (p.box){
                        if(p.box==true) {
                            String[] set = new String[2];
                            set[0] = p.name;

                            selected_name.add(set[0]);
                            LL_hole.setVisibility(View.GONE);
                            im_down.setVisibility(View.VISIBLE);
                            im_up.setVisibility(View.GONE);

                            /*for(int i=0;i<selected_name.size();i++) {
                                tv_search_options.append(selected_name.get(i)+", ");
                            }
                                LL_search_Value.setVisibility(View.VISIBLE);*/


                            //shortToast(getApplicationContext(),p.name);

                            if(cd.isConnectingToInternet()){
                                new Get_Heating_SearchList_details().execute();
                            }else {
                                shortToast(getApplicationContext(),"Please check Your Internet Connection");
                            }
                        }else
                        {
                            shortToast(getApplicationContext(),"Please Select Customer Name");
                        }
                    }
                }

                break;
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        //
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_changePwd) {
            startActivity(new Intent(getApplicationContext(), Change_Password.class));
        } else if (id == R.id.nav_Logout) {
            {

                if (mServiceIntent != null)
                    getApplicationContext().stopService(mServiceIntent);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(SP_LOGGED_IN, false);
                editor.commit();
                finish();
                Intent in = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(in);

            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public class Get_Heating_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Heating.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLHeatingList);

            httpPost.setHeader("Content-Type", "application/json");


            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID, "user_Id"));


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


                jsonarray = getJsonObject.getJSONArray("HeatingArray");
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

                        heating_arraylist = new ArrayList<>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            heating_bean = new HeatingBean();
                            jsonObject = jsonarray.getJSONObject(i);


                            heating_bean.setEQPMNT_NO(jsonObject.getString("EQPMNT_NO"));
                            heating_bean.setCSTMR_CD(jsonObject.getString("CSTMR_CD"));
                            heating_bean.setEQPMNT_TYP_CD(jsonObject.getString("EQPMNT_TYP_CD"));
                            heating_bean.setPRDCT_DSCRPTN_VC(jsonObject.getString("PRDCT_DSCRPTN_VC"));
                            heating_bean.setGTN_DT(jsonObject.getString("GTN_DT"));
                            heating_bean.setYRD_LCTN(jsonObject.getString("YRD_LCTN"));
                            heating_bean.setGI_TRNSCTN_NO(jsonObject.getString("GI_TRNSCTN_NO"));
                            heating_bean.setHTNG_STRT_DT(jsonObject.getString("HTNG_STRT_DT"));
                            heating_bean.setHTNG_STRT_TM(jsonObject.getString("HTNG_STRT_TM"));
                            heating_bean.setHTNG_END_DT(jsonObject.getString("HTNG_END_DT"));
                            heating_bean.setHTNG_END_TM(jsonObject.getString("HTNG_END_TM"));
                            heating_bean.setHTNG_TMPRTR(jsonObject.getString("HTNG_TMPRTR"));
                            heating_bean.setTTL_HTN_PRD(jsonObject.getString("TTL_HTN_PRD"));
                            heating_bean.setMIN_HTNG_RT_NC(jsonObject.getString("MIN_HTNG_RT_NC"));
                            heating_bean.setHRLY_CHRG_NC(jsonObject.getString("HRLY_CHRG_NC"));
                            heating_bean.setCSTMR_CRRNCY_CD(jsonObject.getString("CSTMR_CRRNCY_CD"));
                            heating_bean.setMIN_HTNG_PRD_NC(jsonObject.getString("MIN_HTNG_PRD_NC"));
                            heating_bean.setTTL_RT_NC(jsonObject.getString("TTL_RT_NC"));

                            heating_arraylist.add(heating_bean);


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
            if (heating_arraylist != null) {
                adapter = new UserListAdapter(Heating.this, R.layout.list_item_row, heating_arraylist);
                listview.setAdapter(adapter);


                searchView2.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        adapter.getFilter().filter(s);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() == 0){
                            new Get_Heating_details().execute();
                        }else
                        {
                            adapter.getFilter().filter(s);
                        }

                    }
                });


            } else if (heating_arraylist.size() < 1) {
                shortToast(getApplicationContext(), "Data Not Found");
            }

        }

    }

    public class UserListAdapter extends BaseAdapter {

        Context context;
        ArrayList<HeatingBean> list = new ArrayList<>();
        int resource;
        private HeatingBean userListBean;
        int lastPosition = -1;

        public UserListAdapter(Context context, int resource, ArrayList<HeatingBean> list) {
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
                holder = new Heating.ViewHolder();



                holder.whole = (LinearLayout) convertView.findViewById(R.id.LL_whole);
                holder.Cust_Name = (TextView) convertView.findViewById(R.id.text1);
                holder.equip_no = (TextView) convertView.findViewById(R.id.text2);
                holder.date = (TextView) convertView.findViewById(R.id.text3);
                holder.previous_crg = (TextView) convertView.findViewById(R.id.text4);
                holder.transactionNO = (TextView) convertView.findViewById(R.id.text5);
                holder.type = (TextView) convertView.findViewById(R.id.tv_type);
                holder.location = (TextView) convertView.findViewById(R.id.text6);
                holder.startDate = (TextView) convertView.findViewById(R.id.tv_code);
                holder.startTime = (TextView) convertView.findViewById(R.id.tv_location);
                holder.endDate = (TextView) convertView.findViewById(R.id.tv_transport);
                holder.endTime = (TextView) convertView.findViewById(R.id.tv_vechicle);
                holder.temp = (TextView) convertView.findViewById(R.id.text7);
                holder.ttl_htngPrd = (TextView) convertView.findViewById(R.id.text8);
                holder.min_htngRate_NC = (TextView) convertView.findViewById(R.id.tv_pre_adv_id);
                holder.hourly_charge = (TextView) convertView.findViewById(R.id.text10);
                holder.cust_currency = (TextView) convertView.findViewById(R.id.tv_status);
                holder.min_htngPRD_NC = (TextView) convertView.findViewById(R.id.text9);
                holder.TTL_RT_NC = (TextView) convertView.findViewById(R.id.tv_pre_code);

                convertView.setTag(holder);
            } else {
                holder = (Heating.ViewHolder) convertView.getTag();
            }
            if (list.size() < 1) {
                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_LONG).show();
            } else {
                userListBean = list.get(position);
//                String[] parts = userListBean.getGTN_DT().split(" ");
//                String part1_date = parts[0];
//                String part1_time = parts[1];
//                System.out.println("from date after split" + part1_date);
                holder.equip_no.setText(userListBean.getEQPMNT_NO() + "," + userListBean.getEQPMNT_TYP_CD());
                holder.Cust_Name.setText(userListBean.getCSTMR_CD());
                holder.date.setText(userListBean.getGTN_DT());
                holder.previous_crg.setText(userListBean.getPRDCT_DSCRPTN_VC());
                holder.transactionNO.setText(userListBean.getGI_TRNSCTN_NO());
                holder.temp.setText(userListBean.getHTNG_TMPRTR());
                holder.location.setText(userListBean.getYRD_LCTN());
                holder.startDate.setText(userListBean.getHTNG_STRT_DT());
                holder.startTime.setText(userListBean.getHTNG_STRT_TM());
                holder.endDate.setText(userListBean.getHTNG_END_DT());
                holder.endTime.setText(userListBean.getHTNG_END_TM());
                holder.ttl_htngPrd.setText(userListBean.getTTL_HTN_PRD());
                holder.min_htngRate_NC.setText(userListBean.getMIN_HTNG_RT_NC());
                holder.hourly_charge.setText(userListBean.getHRLY_CHRG_NC());
                holder.cust_currency.setText(userListBean.getCSTMR_CRRNCY_CD());
                holder.min_htngPRD_NC.setText(userListBean.getMIN_HTNG_PRD_NC());
                holder.TTL_RT_NC.setText(userListBean.getTTL_RT_NC());
/*
                if(userListBean.getVechicle().equals("")||userListBean.getVechicle()==null)
                {
                    holder.endTime.setText("");
                }
               else{
                    holder.endTime.setText(userListBean.getVechicle());
                }*/


                holder.whole.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getApplicationContext(), HeatingPeriod.class);

                        GlobalConstants.equipment_no = list.get(position).getEQPMNT_NO();
                        GlobalConstants.customer_name = list.get(position).getCSTMR_CD();
                        GlobalConstants.date = list.get(position).getGTN_DT();
                        GlobalConstants.type = list.get(position).getEQPMNT_TYP_CD();
                        GlobalConstants.previous_cargo = list.get(position).getPRDCT_DSCRPTN_VC();
                        GlobalConstants.location = list.get(position).getYRD_LCTN();
                        GlobalConstants.gt_transaction_no = list.get(position).getGI_TRNSCTN_NO();
                        GlobalConstants.htng_startDate = list.get(position).getHTNG_STRT_DT();
                        GlobalConstants.htng_startTime = list.get(position).getHTNG_STRT_TM();
                        GlobalConstants.htng_EndDate = list.get(position).getHTNG_END_DT();
                        GlobalConstants.htng_EndTime = list.get(position).getHTNG_END_TM();
                        GlobalConstants.htng_temp = list.get(position).getHTNG_TMPRTR();
                        GlobalConstants.ttl_htngPrd = list.get(position).getTTL_HTN_PRD();
                        GlobalConstants.min_htng_rate = list.get(position).getMIN_HTNG_RT_NC();
                        GlobalConstants.hourly_charge = list.get(position).getHRLY_CHRG_NC();
                        GlobalConstants.cust_currency = list.get(position).getCSTMR_CRRNCY_CD();
                        GlobalConstants.min_htngPrd = list.get(position).getMIN_HTNG_PRD_NC();
                        GlobalConstants.ttl_RT_NC = list.get(position).getMIN_HTNG_RT_NC();

                        startActivity(i);
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

                    ArrayList<HeatingBean> filteredContacts = new ArrayList<HeatingBean>();


                    for (HeatingBean c : list) {
                        if ((c.getCSTMR_CD().toUpperCase().contains(constraint.toString().toUpperCase()))
                                || (c.getEQPMNT_NO().toUpperCase().contains(constraint.toString().toUpperCase()))
                                || (c.getEQPMNT_TYP_CD().toUpperCase().contains(constraint.toString().toUpperCase()))
                                || (c.getPRDCT_DSCRPTN_VC().toUpperCase().contains(constraint.toString().toUpperCase()))
                                || (c.getGTN_DT().toUpperCase().contains(constraint.toString().toUpperCase()))) {
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
                list = (ArrayList<HeatingBean>) results.values;
                notifyDataSetChanged();
            }
        }



    }

    static class ViewHolder {
        TextView equip_no, date, Cust_Name, previous_crg, transactionNO, location, startDate, startTime, pre_id, TTL_RT_NC, cust_code, type_id, code_id,
                endTime, endDate, temp, min_htngPRD_NC, hourly_charge, ttl_htngPrd, cust_currency, min_htngRate_NC, type;
        CheckBox checkBox;

        LinearLayout whole;
    }


    public class Get_Heating_filter extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Heating.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLHeatingFilter);
            httpPost.setHeader("Content-Type", "application/json");

            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID, "user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("filterCondition", opratorItems);

                jsonObject.put("filterValue", getEditText);

                jsonObject.put("Mode", "");

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

                    System.out.println("Am HashMap list" + jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {
//                        longToast("This takes longer than usual time. Connection Timeout !");

//                                shortToast(getApplicationContext(), "No Records Found");
                                products.clear();
                                no_data.setVisibility(View.VISIBLE);

                            }
                        });
                    } else {

                        heating_accordion_arraylist = new ArrayList<>();

                        products = new ArrayList<Product>();
                        for (int i = 0; i < jsonarray.length(); i++) {

                            heating_accordion_bean = new HeatingAccordionBean();
                            jsonObject = jsonarray.getJSONObject(i);


                            heating_accordion_bean.setValues(jsonObject.getString("Values"));
                            products.add(new Product(jsonObject.getString("Values"), false));

                            // pending_accordion_arraylist.add(pending_accordion_bean);


                        }
                    }
                } else if (jsonarray.length() < 1) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //update ui here
                            // display toast here

//                            shortToast(getApplicationContext(), "No Records Found.");
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
        protected void onPostExecute(Void aVoid) {


            if (heating_accordion_arraylist != null) {

                boxAdapter = new Heating.ListAdapter(Heating.this, products);
                search_heat_list.setAdapter(boxAdapter);
             /*   UserListAdapterDropdown adapter = new UserListAdapterDropdown(GateIn.this, R.layout.list_item_row_accordion, pending_accordion_arraylist);
                searchlist.setAdapter(adapter);*/


                searchView1.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length()== 0){
                            new Get_Heating_filter().execute();
                        }
                        boxAdapter.getFilter().filter(s);
                    }
                });


            } else if (heating_accordion_arraylist.size() < 1) {
                shortToast(getApplicationContext(), "Data Not Found");
                LL_hole.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                search_heat_list.setVisibility(View.GONE);


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
                view = lInflater.inflate(R.layout.list_item_row_accordion, parent, false);
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


        public Filter getFilter() {
            if (mContactsFilterBox == null)
                mContactsFilterBox = new ContactsFilterBox();

            return mContactsFilterBox;
        }

        // Filter

        private class ContactsFilterBox extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();


                if (constraint == null || constraint.length() == 0) {
                    results.values = objects;
                    results.count = objects.size();
                }
                else {

                    ArrayList<Product> filteredContacts = new ArrayList<Product>();


                    for (Product c : objects) {
                        if ((c.name.toUpperCase().contains(constraint.toString().toUpperCase()))) {
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
                objects = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        }



    }

    public class Get_Heating_SearchList_details extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;
        private JSONArray heatingjsonlist;
        private JSONObject heatingjsonObject;
        private JSONObject SearchValuesObject;
        private String preadviceObject;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Heating.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLHeatingSearchList);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                heatingjsonlist = new JSONArray();
                SearchValuesObject=new JSONObject();



                for (int i = 0; i < selected_name.size(); i++) {
                    heatingjsonObject=new JSONObject();
                    heatingjsonObject.put("values", selected_name.get(i));
                    heatingjsonlist.put(heatingjsonObject);
                }

                SearchValuesObject.put("SearchValues",heatingjsonlist);




                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObject.put("filterType", fieldItems);
                jsonObject.put("Mode", "new");
                jsonObject.put("SearchValues", SearchValuesObject);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                Log.d("request", jsonObject.toString());
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonarray = getJsonObject.getJSONArray("HeatingArray");
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

                        heating_arraylist = new ArrayList<>();


                        for (int i = 0; i < jsonarray.length(); i++) {

                            heating_bean = new HeatingBean();
                            jsonObject = jsonarray.getJSONObject(i);




                            heating_bean.setCSTMR_CD(jsonObject.getString("CSTMR_CD"));
                            heating_bean.setEQPMNT_NO(jsonObject.getString("EQPMNT_NO"));
                            heating_bean.setEQPMNT_TYP_CD(jsonObject.getString("EQPMNT_TYP_CD"));
                            heating_bean.setGTN_DT(jsonObject.getString("GTN_DT"));
                            heating_bean.setPRDCT_DSCRPTN_VC(jsonObject.getString("PRDCT_DSCRPTN_VC"));
                            heating_arraylist.add(heating_bean);



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



            if(heating_arraylist!=null)
            {
                adapter = new Heating.UserListAdapter(Heating.this, R.layout.list_item_row, heating_arraylist);
                listview.setAdapter(adapter);

            }
            else if(heating_arraylist.size()<1)
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
