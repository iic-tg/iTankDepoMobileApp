package com.i_tankdepo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.i_tankdepo.connection.ConnectionDetector;

import java.util.List;


/**
 * Created by Edwin on 24-11-2015.
 */
public abstract class CommonActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener {
//public abstract class CommonActivity extends AppCompatActivity {
    public Typeface Regular_font, Bold_font;
//    public abstract void init();

    // Shared Preferences
    public static SharedPreferences sp;
    public SharedPreferences sp2;
    public static final String PREF_NAME2 = "Redington perm";
    public static final String SP_USER_REMEMBER = "remember";
    public static final String SP_USER_ROLL = "ASM";
    public static final String SP_USER_IMAGE = "profile";
    public static final String SP2_CAMERA_PERM_DENIED = "camPermDenied";
    public static final String SP2_STORAGE_PERM_DENIED = "storagePermDenied";

    public ConnectionDetector cd;
    public static final String PREF_NAME = "HappiHive";
    public static final String SP_AUTHR_USER = "redington";
    public static final String SP_AUTHR_PASSWORD = "password";
    public static final String SP_TOKEN = "token";
    public static final String SP_LOGGED_IN = "loggedIn";
    public static final String SP_USER_ID = "user_Id";
    public static final String SP_ID= "Id";
    public static final String SP_HS_ID = "hsId";

    public static final String SP_TYPE = "user_type";
    public Dialog dialog;
    public ListView mListView;
    ArrayAdapter<String> aa;
    private String TAG="CommonActivity";
     Dialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Regular_font = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat-Regular.ttf");
        Bold_font = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat-Bold.ttf");

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp2 = getSharedPreferences(PREF_NAME2, Context.MODE_PRIVATE);
        cd=new ConnectionDetector(this);
//        init();
    }


    //for Applying Regular_font
    public void setRegularFont(int[] ids) {
        for (int i=0; i<ids.length; i++) {
            if (findViewById(ids[i]) instanceof Button) {
                Button button = (Button) findViewById(ids[i]);
                button.setTypeface(Regular_font);
            }
            else if (findViewById(ids[i]) instanceof TextView){
                TextView textView = (TextView) findViewById(ids[i]);
                textView.setTypeface(Regular_font);
            }
            else if (findViewById(ids[i]) instanceof EditText){
                EditText editText = (EditText) findViewById(ids[i]);
                editText.setTypeface(Regular_font);
            }
        }
    }

    //for Applying Bold_font
    public void setBoldFont(int[] ids) {
        for (int i=0; i<ids.length; i++) {
            if (findViewById(ids[i]) instanceof Button) {
                Button button = (Button) findViewById(ids[i]);
                button.setTypeface(Bold_font);
            }
            else if (findViewById(ids[i]) instanceof TextView){
                TextView textView = (TextView) findViewById(ids[i]);
                textView.setTypeface(Bold_font);
            }
            else if (findViewById(ids[i]) instanceof EditText){
                EditText editText = (EditText) findViewById(ids[i]);
                editText.setTypeface(Bold_font);
            }
        }
    }

    //for Dialog
    public void setRegularFont(Dialog dialog, int[] ids) {
        for (int i=0; i<ids.length; i++) {
            if (dialog.findViewById(ids[i]) instanceof Button) {
                Button button = (Button) dialog.findViewById(ids[i]);
                button.setTypeface(Regular_font);
            } else if (dialog.findViewById(ids[i]) instanceof EditText) {
                EditText editText = (EditText) dialog.findViewById(ids[i]);
                editText.setTypeface(Regular_font);
            } else if (dialog.findViewById(ids[i]) instanceof TextView) {
                TextView textView = (TextView) dialog.findViewById(ids[i]);
                textView.setTypeface(Regular_font);
            }
        }
    }

    //for Dialog
    public void setBoldFont(Dialog dialog, int[] ids) {
        for (int i=0; i<ids.length; i++) {
            if (dialog.findViewById(ids[i]) instanceof Button) {
                Button button = (Button) dialog.findViewById(ids[i]);
                button.setTypeface(Bold_font);
            } else if (dialog.findViewById(ids[i]) instanceof EditText) {
                EditText editText = (EditText) dialog.findViewById(ids[i]);
                editText.setTypeface(Bold_font);
            } else if (dialog.findViewById(ids[i]) instanceof TextView) {
                TextView textView = (TextView) dialog.findViewById(ids[i]);
                textView.setTypeface(Bold_font);
            }
        }
    }

    public static boolean isEmpty(String input) {
        if (input == null || input.trim().length() == 0
                || "".equals(input.toString().trim())
                || " ".equals(input.toString().trim())) {
            return true;
        }
        return false;
    }

    public void shortToast(Context con, String msg) {
        Toast.makeText(con, msg, Toast.LENGTH_SHORT).show();
    }

    public void longToast(Context con, String msg) {
        Toast.makeText(con, msg, Toast.LENGTH_LONG).show();
    }
    public boolean isReqFieldsFilled(Context con, String[] allFields) {
        for (int i=0; i<allFields.length; i++) {
            if(isEmpty(allFields[i])) {
                longToast(getApplicationContext(), "Please fill the required fields");
                return false;
            }
        }
        return true;
    }

    public void showDialogWithLV(Context con, final List<String> list) {
        mDialog = new Dialog(con);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.list_view);
        mDialog.setCanceledOnTouchOutside(true);


        mListView = (ListView) mDialog.findViewById(R.id.ad_listView);
        mListView.setOnItemClickListener(this);

        aa = new ArrayAdapter<String>(getApplicationContext(), R.layout.mytextview, list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                aa.notifyDataSetChanged();
                TextView text = (TextView) view.findViewById(android.R.id.text1);


                //text.setTypeface(containerFont);


                return view;
            }

        };
        mListView.setAdapter(aa);

        // aa.notifyDataSetChanged();

        mDialog.show();
    }


}
