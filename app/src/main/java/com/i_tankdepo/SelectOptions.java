package com.i_tankdepo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
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
import android.widget.ListView;
import android.widget.TextView;

import com.i_tankdepo.Beanclass.GeneralReportBean;
import com.i_tankdepo.Beanclass.PendingAccordionBean;
import com.i_tankdepo.Beanclass.TypeReportBean;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 1/4/2017.
 */

public class SelectOptions extends CommonActivity {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView menu, im_down, im_up, im_heat_close, im_heat_ok, iv_back,iv_changeOfStatus,im_date;
    private Button status_home,status_refresh,run_report;
    private EditText searchView;
    private ListView select_list_view;
    private TextView tv_toolbarTitle,tv_heat_refresh,tv_selected;
    private LinearLayout LL_status_submit,LL_run,LL_date,LL_mandate_fields;
    String from,selected_name;
    private PendingAccordionBean pending_accordion_bean;
    private ArrayList<PendingAccordionBean> pending_accordion_arraylist;
    private ArrayList<Product_Stock> products;
    private ArrayList<Product_Stock> box;
    private ListAdapter boxAdapter;
    List<String> selected_id_list = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private GeneralReportBean generalReportBean;
    private ArrayList<GeneralReportBean> generalReportBeanArrayList;
    private TypeReportBean typeReportBean;
    private ArrayList<TypeReportBean> typeReportBeanArrayList;
    private EditText ed_date;
    static final int DATE_DIALOG_ID = 1999;
    private Calendar c;
    private int year,month,day,second;
    private String systemDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_options);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        menu.setVisibility(View.GONE);


        from= GlobalConstants.from;
        selected_name= GlobalConstants.selectedName;
        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_selected = (TextView) findViewById(R.id.tv_selected);
        tv_toolbarTitle.setText("Select Options");
        tv_heat_refresh  = (TextView)findViewById(R.id.tv_heat_refresh);
        select_list_view  = (ListView)findViewById(R.id.select_list_view);
        tv_heat_refresh.setText("Reset");

        ed_date = (EditText)findViewById(R.id.ed_date);
        ed_date.setOnClickListener(this);

        im_date = (ImageView)findViewById(R.id.im_date);
        im_date.setOnClickListener(this);
        if(cd.isConnectingToInternet()){
             new Get_ScockReport_list_details().execute();
        }else{
            shortToast(getApplicationContext(),"Please check your Internet Connection..!");
        }
        status_home = (Button)findViewById(R.id.status_home);
        status_refresh = (Button)findViewById(R.id.status_refresh);
        run_report = (Button)findViewById(R.id.run_report);
        searchView = (EditText)findViewById(R.id.searchView);
        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);
        LL_status_submit = (LinearLayout)findViewById(R.id.LL_status_submit);
        LL_run = (LinearLayout)findViewById(R.id.LL_run);
        LL_mandate_fields = (LinearLayout)findViewById(R.id.LL_mandate_fields);
        LL_date = (LinearLayout)findViewById(R.id.LL_date);
        LL_status_submit.setVisibility(View.GONE);
        tv_selected.setOnClickListener(this);
        if(from.equalsIgnoreCase("optional"))
        {
            LL_mandate_fields.setVisibility(View.GONE);
            LL_date.setVisibility(View.VISIBLE);
        }else
        {
            LL_mandate_fields.setVisibility(View.VISIBLE);
            LL_date.setVisibility(View.GONE);
        }

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
        
        systemDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        status_home.setOnClickListener(this);
        status_refresh.setOnClickListener(this);
        run_report.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId() ){
            case R.id.iv_changeOfStatus:
                startActivity(new Intent(getApplicationContext(),ChangeOfStatus.class));
                break;
            case R.id.status_home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.status_refresh:
                finish();
                startActivity(getIntent());
                break;
            case R.id.ed_date:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.im_date:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_selected:
                if(boxAdapter.getBox().size()==0) {
                    shortToast(getApplicationContext(), "Please Select atleast One Value..!");
                }else {
                    for (Product_Stock p : boxAdapter.getBox()) {
                        if (p.box) {
                            if (p.box == true) {
                                String[] set = new String[2];
                                set[0] = p.Id;

                                selected_id_list.add(set[0]);

                            } else {
                                shortToast(getApplicationContext(), "Please Select CustomerName");
                            }
                        }
                    }
                }
                break;

            case R.id.run_report:
                if(boxAdapter.getBox().size()==0) {
                    shortToast(getApplicationContext(), "Please Select atleast One Value..!");
                }else {
                    for (Product_Stock p : boxAdapter.getBox()) {
                        if (p.box) {
                            if (p.box == true) {
                                String[] set = new String[2];
                                set[0] = p.Id;

                                selected_id_list.add(set[0]);

                            } else {
                                shortToast(getApplicationContext(), "Please Select CustomerName");
                            }
                        }
                    }

                    if(cd.isConnectingToInternet())
                    {
                        new Post_Stock_report().execute();
                    }else
                    {
                        shortToast(getApplicationContext(),"Please Check Your Intenet Connection");
                    }                }

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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



            //    System.out.println("am a new from date====>>" + str_From);

            ed_date.setText(formatDate(year, month, day));

        }
    };

    public class Post_Stock_report extends AsyncTask<Void, Void, Void> {
        private JSONArray jsonarray;
        private JSONArray preadvicejsonlist;
        private JSONObject preadvicejsonObject;
        private JSONObject SearchValuesObject;
        private String preadviceObject;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SelectOptions.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLStock_Run_Report);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-orgCleaningDate", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try{
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObjectStockReportModel = new JSONObject();
                JSONObject finalObject = new JSONObject();

                preadvicejsonlist = new JSONArray();

                if(selected_name.equalsIgnoreCase("Customer"))
                {
                    for (int i = 0; i <selected_id_list.size(); i++) {
                        preadvicejsonObject=new JSONObject();
                        preadvicejsonObject.put("Type", selected_id_list.get(i));
                        preadvicejsonlist.put(preadvicejsonObject);
                    }

                }else if(selected_name.equalsIgnoreCase("Equipment Type"))
                {
                    for (int i = 0; i <selected_id_list.size(); i++) {
                        preadvicejsonObject=new JSONObject();
                        preadvicejsonObject.put("Type", selected_id_list.get(i));
                        preadvicejsonlist.put(preadvicejsonObject);
                    }
                }else if(selected_name.equalsIgnoreCase("Previous Cargo"))
                {
                    for (int i = 0; i <selected_id_list.size(); i++) {
                        preadvicejsonObject=new JSONObject();
                        preadvicejsonObject.put("Type", selected_id_list.get(i));
                        preadvicejsonlist.put(preadvicejsonObject);
                    }

                }else if(selected_name.equalsIgnoreCase("Current Status"))
                {
                    for (int i = 0; i <selected_id_list.size(); i++) {
                        preadvicejsonObject=new JSONObject();
                        preadvicejsonObject.put("Type", selected_id_list.get(i));
                        preadvicejsonlist.put(preadvicejsonObject);
                    }
                }else if(selected_name.equalsIgnoreCase("Next Test Type"))
                {
                    for (int i = 0; i <selected_id_list.size(); i++) {
                        preadvicejsonObject=new JSONObject();
                        preadvicejsonObject.put("Type", selected_id_list.get(i));
                        preadvicejsonlist.put(preadvicejsonObject);
                    }
                }else
                {
                    for (int i = 0; i <selected_id_list.size(); i++) {
                        preadvicejsonObject=new JSONObject();
                        preadvicejsonObject.put("Type", selected_id_list.get(i));
                        preadvicejsonlist.put(preadvicejsonObject);
                    }
                }
                jsonObjectStockReportModel.put("Customer",preadvicejsonlist);
                jsonObjectStockReportModel.put("Depot",preadvicejsonlist);
                jsonObjectStockReportModel.put("Next_Test_Type",preadvicejsonlist);
                jsonObjectStockReportModel.put("Current_Status",preadvicejsonlist);
                jsonObjectStockReportModel.put("Previous_Cargo",preadvicejsonlist);
                jsonObjectStockReportModel.put("Equipment_Type",preadvicejsonlist);



                jsonObjectStockReportModel.put("UserName", sp.getString(SP_USER_ID,"user_Id"));
                jsonObjectStockReportModel.put("Next_Test_Date_From", "");
                jsonObjectStockReportModel.put("Next_Test_Date_To", "");
                jsonObjectStockReportModel.put("Equipment_No", "");
                jsonObjectStockReportModel.put("EIR_No", "");
                jsonObjectStockReportModel.put("Out_Date_From", "");
                jsonObjectStockReportModel.put("Cleaning_Date_From", "");
                jsonObjectStockReportModel.put("Cleaning_Date_To", "");
                jsonObjectStockReportModel.put("Inspection_Date_From", "");
                jsonObjectStockReportModel.put("Inspection_Date_To", "");
                jsonObjectStockReportModel.put("Current_Status_Date_From", "");
                jsonObjectStockReportModel.put("In_Date_From", "");
                jsonObjectStockReportModel.put("In_Date_To", "");
                jsonObjectStockReportModel.put("Current_Status_Date_To", "");
                jsonObjectStockReportModel.put("Out_Date_To", "");
                jsonObject.put("StockReportModel",jsonObjectStockReportModel);
               /* JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("Credentials",jsonObject);*/

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("rep", resp);
                Log.d("Search_request", jsonObject.toString());

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

                     /*   pending_arraylist = new ArrayList<>();


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



                        }*/
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



        /*    if(pending_arraylist!=null)
            {
                adapter = new UserListAdapter(GateIn.this, R.layout.list_item_row, pending_arraylist);
                listview.setAdapter(adapter);

            }
            else if(pending_arraylist.size()<1)
            {
                shortToast(getApplicationContext(),"Data Not Found");


            }
*/
            progressDialog.dismiss();

        }

    }

    public class Get_ScockReport_list_details extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONArray jsonarray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SelectOptions.this);
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
            HttpPost httpPost = new HttpPost(ConstantValues.baseURLStock_DropDown);
            httpPost.setHeader("Content-Type", "application/json");

            try{
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("UserName", sp.getString(SP_USER_ID,"user_Id"));



                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d(" rep", resp);
                Log.d(" req", jsonObject.toString());
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");

                if(selected_name.equalsIgnoreCase("Customer"))
                {
                    jsonarray = getJsonObject.getJSONArray("Customer");
                }else if(selected_name.equalsIgnoreCase("Equipment Type"))
                {
                    jsonarray = getJsonObject.getJSONArray("EquipmentType");
                }else if(selected_name.equalsIgnoreCase("Previous Cargo"))
                {
                    jsonarray = getJsonObject.getJSONArray("PrevoiusCargo");

                }else if(selected_name.equalsIgnoreCase("Current Status")) {
                    jsonarray = getJsonObject.getJSONArray("CurrentStatus");

                }else if(selected_name.equalsIgnoreCase("Next Test Type"))
                {
                    jsonarray = getJsonObject.getJSONArray("NextTestType");

                }else {
                    jsonarray = getJsonObject.getJSONArray("Depot");

                }

                if (jsonarray != null) {

                    System.out.println("Am HashMap list"+jsonarray);
                    if (jsonarray.length() < 1) {
                        runOnUiThread(new Runnable() {
                            public void run() {

                                products.clear();

                            }
                        });
                    }else {

                        pending_accordion_arraylist = new ArrayList<PendingAccordionBean>();

                        products = new ArrayList<Product_Stock>();
                        for (int i = 0; i < jsonarray.length(); i++) {

                            pending_accordion_bean = new PendingAccordionBean();
                            jsonObject = jsonarray.getJSONObject(i);

                            pending_accordion_bean.setValues(jsonObject.getString("Description"));
                            products.add(new Product_Stock(jsonObject.getString("Description"),jsonObject.getString("Id"),false));

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
                          //  no_data.setVisibility(View.VISIBLE);

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
                boxAdapter = new ListAdapter(SelectOptions.this, products);
                select_list_view.setAdapter(boxAdapter);

             /*   UserListAdapterDropdown adapter = new UserListAdapterDropdown(GateIn.this, R.layout.list_item_row_accordion, pending_accordion_arraylist);
                searchlist.setAdapter(adapter);*/

                searchView.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
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
            else
            {
                shortToast(getApplicationContext(),"Data Not Found");

            }

            progressDialog.dismiss();

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public class ListAdapter extends BaseAdapter {
        private final ArrayList<Product_Stock> arraylist;
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Product_Stock> objects;
        Product_Stock p;

        ListAdapter(Context context, ArrayList<Product_Stock> products) {
            ctx = context;
            objects = products;
            this.arraylist = new ArrayList<Product_Stock>();
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

            ((TextView) view.findViewById(R.id.tv_cust_name)).setText(p.Id+" , "+p.name);


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

        Product_Stock getProduct(int position) {
            return ((Product_Stock) getItem(position));
        }

        ArrayList<Product_Stock> getBox() {


            box = new ArrayList<Product_Stock>();
            box.clear();
            for (Product_Stock p : objects) {
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
                for (Product_Stock wp : arraylist) {
                    if (wp.name.toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        objects.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

}
