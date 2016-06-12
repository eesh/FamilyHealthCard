package com.example.hussain.familyhealthcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    private static EditText username;
    private static EditText password;
    private DataSend dataSend = null;
    private static Button login_button;



    public static String  PREFS_NAME="mypre";
    public static String PREF_USERNAME="username";
    public static String PREF_PASSWORD = "password";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // get the listview

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);



        return true;

    }

    public void onStart(){
        super.onStart();
        //read username and password from SharedPreferences
        getUser();
    }

    public void doLogin(View view){
        final EditText txtuser=(EditText)findViewById(R.id.txt_user);
        EditText txtpwd=(EditText)findViewById(R.id.txt_pwd);

        if(txtuser.length() == 0 || txtpwd.length() == 0) {
            Toast.makeText(loginActivity.this, "Check fields", Toast.LENGTH_SHORT).show();
        }
        final String username=txtuser.getText().toString();
        final String password=txtpwd.getText().toString();
        dataSend = DataSend.getInstance(getApplicationContext());
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equalsIgnoreCase("datasender.authSuccess")) {
                    rememberMe(username, password);
                    showLogout(username);
                    unregisterReceiver(this);
                }else if (intent.getAction().equalsIgnoreCase("datasender.noConnection")){
                    Toast.makeText(loginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Authentication Failed. Check details again", Toast.LENGTH_SHORT).show();
                    unregisterReceiver(this);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("datasender.authSuccess");
        filter.addAction("datasender.authFailed");
        filter.addAction("datasender.noConnection");
        registerReceiver(receiver,filter);
        dataSend.authenticate(txtuser.getText().toString(), txtpwd.getText().toString());
    }

    public void getUser(){
        SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username != null || password != null) {
            //directly show logout form

            showLogout(username);
        }
    }

    public void rememberMe(String user, String password){
        String base = Base64.encodeToString((user+":"+password).getBytes(), Base64.NO_WRAP);
        //save username and password in SharedPreferences
        getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putString(PREF_USERNAME,user)
                .putString(PREF_PASSWORD,password)
                .putString("auth", base)
                .commit();
    }

    public void showLogout(String username){
        //display log out activity
        Intent intent=new Intent(this, add.class);
        intent.putExtra("user",username);
        startActivity(intent);
        finish();
    }



}