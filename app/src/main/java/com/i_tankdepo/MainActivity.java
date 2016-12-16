package com.i_tankdepo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.i_tankdepo.Constants.ConstantValues;
import com.i_tankdepo.Constants.GlobalConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CommonActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    private ImageView menu,up,down,iv_back;
    private DrawerLayout drawer;
    private LinearLayout LL_Gatein,LL_Heating;
    LinearLayout LL_hole;
    private TextView tv_toolbarTitle;
    private Intent mServiceIntent;
    private JSONArray jsonArray;
    private ArrayList<String> list;
    private String activityName,RoleID;
    private ImageView im_gatein,im_gateout,im_heating,im_cleaning,im_repair,im_leaktest,im_equipement,im_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        im_gatein=(ImageView)findViewById(R.id.im_gatein);
        im_gateout=(ImageView)findViewById(R.id.im_gateout);
        im_heating=(ImageView)findViewById(R.id.im_heating);
        im_cleaning=(ImageView)findViewById(R.id.im_cleaning);
        im_repair=(ImageView)findViewById(R.id.im_repair);
        im_leaktest=(ImageView)findViewById(R.id.im_leaktest);
        im_equipement=(ImageView)findViewById(R.id.im_history);
        im_stock=(ImageView)findViewById(R.id.im_report);
        menu=(ImageView)findViewById(R.id.iv_menu);
        iv_back = (ImageView)findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);
        RoleID = GlobalConstants.roleID;

        if (cd.isConnectingToInternet()){
            new Post_Role_Based().execute();
        } else{
            shortToast(getApplicationContext(), "Please Check Your Internet Connection");
        }


/*
            try {
                jsonArray = new JSONArray(getIntent().getStringExtra("jsonArray"));

                if (jsonArray != null) {
                    list = new ArrayList<String>();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String view = jsonObject.getString("View");

                        if (view.equalsIgnoreCase("False")) {
                            // AtivityName
                            activityName = jsonObject.getString("AtivityName");

                            list.add(activityName);
                        }

                    }

                    for (int i = 0; i < list.size(); i++) {
                        String role = list.get(i);
                        if (role.equalsIgnoreCase("Heating")) {
                            im_heating.setVisibility(View.GONE);

                        } else if (role.equalsIgnoreCase("Gate Out")) {
                            im_gateout.setVisibility(View.GONE);
                        } else if (role.equalsIgnoreCase("Equipment History")) {
                            im_equipement.setVisibility(View.GONE);
                        } else if (role.equalsIgnoreCase("Gate In Creation")) {
                            im_gatein.setVisibility(View.GONE);

                        }
                        if (role.equalsIgnoreCase("Cleaning")) {

                            im_cleaning.setVisibility(View.GONE);

                        } else if (role.equalsIgnoreCase("Leak Test")) {
                            im_leaktest.setVisibility(View.GONE);

                        } else if (role.equalsIgnoreCase("Repair Estimate")) {
                            im_repair.setVisibility(View.GONE);
                        }

                        Log.i("role", role);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                shortToast(getApplicationContext(), "Data Not Found");
            }
*/


        LL_Gatein = (LinearLayout) findViewById(R.id.LL_GateIn);
        LL_Heating = (LinearLayout) findViewById(R.id.LL_Heating);

        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_toolbarTitle.setText("Home");



        LL_Gatein.setOnClickListener(this);
        LL_Heating.setOnClickListener(this);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //  startActivity(new Intent(getApplicationContext(),MainActivity.class));
        int id = item.getItemId();

        if (id == R.id.nav_home) {


        }else if (id == R.id.nav_changePwd) {

            startActivity(new Intent(getApplicationContext(),Change_Password.class));

        }else if (id == R.id.nav_Logout) {

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

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.LL_GateIn:
                    startActivity(new Intent(getApplicationContext(),GateIn.class));
                break;
            case R.id.LL_Heating:
                startActivity(new Intent(getApplicationContext(),Heating.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public class Post_Role_Based extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        private JSONObject jsonResp;
        private JSONArray jsonArrayforRole;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
//            if ((progressDialog != null) && progressDialog.isShowing()) {
            progressDialog.show();
//            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse response = null;
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(ConstantValues.baseURLRoleBasedLogin);
            // httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            //     httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
//            httpPost.setHeader("SecurityToken", sp.getString(SP_TOKEN,"token"));
            try {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("RL_ID", RoleID);


                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                String resp = EntityUtils.toString(httpEntity);

                Log.d("responce", resp);
                JSONObject jsonrootObject = new JSONObject(resp);
                JSONObject getJsonObject = jsonrootObject.getJSONObject("d");


                jsonArray = getJsonObject.getJSONArray("RoleDetails");
                try {

                    if (jsonArray != null) {
                        list = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            String view = jsonObject.getString("View");

                            if (view.equalsIgnoreCase("False")) {
                                // AtivityName
                                activityName = jsonObject.getString("AtivityName");

                                list.add(activityName);
                            }

                        }

                        for (int i = 0; i < list.size(); i++) {
                            String role = list.get(i);
                            if (role.equalsIgnoreCase("Heating")) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_heating.setVisibility(View.GONE);


                                    }
                                });
                            } else if (role.equalsIgnoreCase("Gate Out")) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_gateout.setVisibility(View.GONE);


                                    }
                                });
                            } else if (role.equalsIgnoreCase("Equipment History")) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_equipement.setVisibility(View.GONE);


                                    }
                                });
                            } else if (role.equalsIgnoreCase("Gate In Creation")) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_gatein.setVisibility(View.GONE);


                                    }
                                });

                            }
                            if (role.equalsIgnoreCase("Cleaning")) {


                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_cleaning.setVisibility(View.GONE);


                                    }
                                });

                            } else if (role.equalsIgnoreCase("Leak Test")) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_leaktest.setVisibility(View.GONE);


                                    }
                                });

                            } else if (role.equalsIgnoreCase("Repair Estimate")) {

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        //update ui here
                                        // display toast here
                                        im_repair.setVisibility(View.GONE);


                                    }
                                });
                            }

                            Log.i("role", role);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    shortToast(getApplicationContext(), "Data Not Found");
                }
            } catch (UnsupportedEncodingException e) {
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


            progressDialog.dismiss();

        }
    }



}