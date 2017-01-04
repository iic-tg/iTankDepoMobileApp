package com.i_tankdepo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.i_tankdepo.Constants.GlobalConstants;

/**
 * Created by Admin on 1/4/2017.
 */

public class StockReport extends CommonActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ImageView menu, im_down, im_up, im_heat_close, im_heat_ok, iv_back,iv_changeOfStatus;
    private Intent mServiceIntent;
    private ListView mandate_fields,optional_fields;
    private TextView tv_toolbarTitle,tv_heat_refresh,tv_mandate_fields,tv_optional_fields;
    private LinearLayout LL_status_submit,LL_run;
    private Button status_home,status_refresh,run_report;
    private String[] Mandate_Fields = {"Customer","Equipment Type","Previous Cargo","Current Status","Next Test Type","Depot"};
    private String[] Optional_Fields = {"Cleaning Date Form","Cleaning Date To","Inspection Date From","Inspection Date To","Current Status Date From"
                                        ,"Current Status Date To","Next Test Date From","Next Test Date To","Equipment Number","EIR Number","Out Date From"};
    private RadioButton radio_button;
    private RadioGroup radio_group,radio_group1;
    private RadioButton radioButton;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_report);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        menu = (ImageView) findViewById(R.id.iv_menu);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.GONE);


        tv_toolbarTitle = (TextView) findViewById(R.id.tv_Title);
        tv_mandate_fields = (TextView) findViewById(R.id.tv_mandate_fields);
        tv_optional_fields = (TextView) findViewById(R.id.tv_optional_fields);
        tv_mandate_fields.setOnClickListener(this);
        tv_optional_fields.setOnClickListener(this);
        tv_toolbarTitle.setText("Stock Report");
        tv_heat_refresh  = (TextView)findViewById(R.id.tv_heat_refresh);
        tv_heat_refresh.setText("Reset");

        LL_status_submit = (LinearLayout)findViewById(R.id.LL_status_submit);
        LL_run = (LinearLayout)findViewById(R.id.LL_run);
        LL_status_submit.setVisibility(View.GONE);

        radio_button = (RadioButton)findViewById(R.id.radio_button);
        radio_group = (RadioGroup)findViewById(R.id.Radio_group);
        radio_group1 = (RadioGroup)findViewById(R.id.Radio_group1);
/*
        radio_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(radio_button != null)
                {
                    radio_button.setChecked(false);
                }

                radio_button.setChecked(true);
                //selected = rbFolder;
            }
        });
*/

        status_home = (Button)findViewById(R.id.status_home);
        status_refresh = (Button)findViewById(R.id.status_refresh);
        run_report = (Button)findViewById(R.id.run_report);
        iv_changeOfStatus = (ImageView)findViewById(R.id.iv_changeOfStatus);
        iv_changeOfStatus.setOnClickListener(this);

        status_home.setOnClickListener(this);
        status_refresh.setOnClickListener(this);
        run_report.setOnClickListener(this);

/*
        radio_group
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Log.d("chk", "id" + checkedId);
                        int id= radio_group.getCheckedRadioButtonId();
                        View radioButton = radio_group.findViewById(id);
                        int radioId = radio_group.indexOfChild(radioButton);
                        RadioButton btn = (RadioButton) radio_group.getChildAt(radioId);
                        if (checkedId == R.id.radio_bt_customer) {
                            //some code
                        } else if (checkedId == R.id.radio_button1) {

                            //some code
                        } else if (checkedId == R.id.radio_button3) {

                            //some code
                        } else if (checkedId == R.id.radio_button4) {

                            //some code
                        } else if (checkedId == R.id.radio_button5) {

                            //some code
                        } else if (checkedId == R.id.radio_button6) {

                            //some code
                        }

                    }

                });
*/


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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



    @Override
    public void onClick(View view) {
        switch (view.getId() ){
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
            case R.id.run_report:

                break;
            case R.id.tv_mandate_fields:
                int selectedId = radio_group.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                 selected=radioButton.getText().toString();
              //  shortToast(getApplicationContext(), selected);

                Intent i=new Intent(getApplicationContext(),SelectOptions.class);
                GlobalConstants.from="mandate";
                GlobalConstants.selectedName=selected;

                startActivity(i);

                break;
            case R.id.tv_optional_fields:
                 selectedId = radio_group1.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);
                try {
                    selected = radioButton.getText().toString();

                }catch (Exception e)
                {

                }
             //   shortToast(getApplicationContext(), selected);

                Intent i1=new Intent(getApplicationContext(),SelectOptions.class);
                GlobalConstants.from="Optional";
                GlobalConstants.selectedName=selected;

                startActivity(i1);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
