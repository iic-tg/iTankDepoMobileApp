package com.i_tankdepo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

public class SplashScreenActivity extends CommonActivity {

    private static int SPLASH_TIME_OUT = 3000;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mHandler.postDelayed(run, SPLASH_TIME_OUT);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            boolean loggedIn = sp.getBoolean(SP_LOGGED_IN, false);

            if (loggedIn) {
               /* CommonFragment cf=new CommonFragment() {
                    @Override
                    public void init(View view) {

                    }

                    @Override
                    public void onClick(View v) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                };
                if(cf.location_status.equals("success")){
                    if(cf.routes_status.equals("success")){*/
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                   /* }
                    else {
                        startActivity(new Intent(SplashScreen.this, SignUpActivity.class));
                        cf.replaceFragment(new SixthPage());
                    }

                }
                else {
                    startActivity(new Intent(SplashScreen.this, SignUpActivity.class));
                    cf.replaceFragment(new FifthPageRider());

                }*/

            }
            else {

                startActivity(new Intent(SplashScreenActivity.this, Login_Activity.class));

            }
            finish();
        }
    };

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
