package com.example.hussain.familyhealthcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


public class add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Button();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        return super.onOptionsItemSelected(item);
    }

    public void Button()
    {
        Button createfamily = (Button)findViewById(R.id.create_family);
        Button updatedetails = (Button)findViewById(R.id.update_details);
        Button logout = (Button)findViewById(R.id.logout);
        Button aboutUs=(Button)findViewById(R.id.about);

        if (createfamily != null) {
            createfamily.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(add.this, create_family_2.class);
                            startActivity(intent);
                        }
                    });
        }
        if (updatedetails != null) {
            updatedetails.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DataModels.getInstance().clear();
                            Intent intent = new Intent(add.this, search_family.class);
                            startActivity(intent);

                        }
                    });
        }
        if (logout != null) {
            logout.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences sharedPrefs =getSharedPreferences(loginActivity.PREFS_NAME,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPrefs.edit();
                            editor.clear();
                            editor.commit();
                            //show login form
                            Intent intent=new Intent(add.this, loginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            if (aboutUs!= null) {
                aboutUs.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(add.this, about_us.class);
                                startActivity(intent);
                            }
                        });
            }

        }

    }


}
