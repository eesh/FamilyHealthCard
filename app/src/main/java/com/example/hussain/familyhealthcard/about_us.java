package com.example.hussain.familyhealthcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class about_us extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        int min = 55;
        int max = 59;

        Random r = new Random();


        int rand = r.nextInt(max - min + 1) + min;

        if(rand==55) {
            ImageView imageView = (ImageView) findViewById(R.id.numberDays55);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    //your stuff
                    Intent intent = new Intent(about_us.this, easter_egg.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

        if(rand==56) {
            ImageView imageView = (ImageView) findViewById(R.id.numberDays56);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    //your stuff
                    Intent intent = new Intent(about_us.this, easter_egg.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

        if(rand==57) {
            ImageView imageView = (ImageView) findViewById(R.id.numberDays57);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    //your stuff
                    Intent intent = new Intent(about_us.this, easter_egg.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

        if(rand==58) {
            ImageView imageView = (ImageView) findViewById(R.id.numberDays58);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    //your stuff
                    Intent intent = new Intent(about_us.this, easter_egg.class);
                    startActivity(intent);
                    return true;
                }
            });
        }

        if(rand==59) {
            ImageView imageView = (ImageView) findViewById(R.id.numberDays59);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    //your stuff
                    Intent intent = new Intent(about_us.this, easter_egg.class);
                    startActivity(intent);
                    return true;
                }
            });
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }
}
