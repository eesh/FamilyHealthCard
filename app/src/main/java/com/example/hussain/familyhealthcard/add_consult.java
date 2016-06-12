package com.example.hussain.familyhealthcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;


public class add_consult extends AppCompatActivity {


    @Bind(R.id.editText5) EditText findingsET;
    @Bind(R.id.editText2) EditText diagnosisET;
    @Bind(R.id.editText3) EditText treatmentET;
    @Bind(R.id.editText1) EditText labfindingsET;
    @Bind(R.id.editText4) EditText referralET;
    @Bind(R.id.submit) Button submitButton;


    String findings, diagnosis, treatment, referral,labfindings;

    DataSend dataSend = null;

    int patientID;

    BroadcastReceiver receiver = null;
    IntentFilter filter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consult);
        ButterKnife.bind(this);
        dataSend = DataSend.getInstance(getApplicationContext());

        Intent intent = getIntent();
        patientID = intent.getIntExtra("patientID", 0);
        if(patientID == 0) finish();

        setReceiver();
        buttons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_consult, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    public void buttons() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = checkFields();
                if (!check)
                    return;

                else {
                    findings = findingsET.getText().toString().replaceAll("'", "\'").replaceAll("\"", "\"\"");
                    labfindings = labfindingsET.getText().toString().replaceAll("'", "\'").replaceAll("\"", "\"\"");
                    diagnosis = diagnosisET.getText().toString();
                    treatment = treatmentET.getText().toString();
                    referral = referralET.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("findings", findings);
                    bundle.putString("labfindings",labfindings);
                    bundle.putString("diagnosis", diagnosis);
                    bundle.putString("treatment", treatment);
                    bundle.putString("referral", referral);

                    dataSend.addConsultation(patientID, bundle);
                    registerReceiver(receiver, filter);
                }
            }
        });
    }


    public void setReceiver() {

        filter = new IntentFilter();

        filter.addAction("datasend.addConsultSuccess");
        filter.addAction("datasend.updateConsultSuccess");
        filter.addAction("datasend.deleteConsultSuccess");
        filter.addAction("datasend.consultationsFound");
        filter.addAction("datasend.addConsultFailed");
        filter.addAction("datasend.updateConsultFailed");
        filter.addAction("datasend.deleteConsultFailed");
        filter.addAction("datasend.consultationsNotFound");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intentHandler(context, intent);
            }
        };

    }


    public void intentHandler(Context context, Intent intent) {

        String action = intent.getAction();
        if(action.equalsIgnoreCase("datasend.addConsultSuccess")) {
            Toast.makeText(add_consult.this, "Consultation added successfully", Toast.LENGTH_SHORT).show();
            unregisterReceiver(receiver);
            finish();
        } else if (action.equalsIgnoreCase("datasend.addConsultFailed")) {
            unregisterReceiver(receiver);
            Toast.makeText(add_consult.this, "Failed to add consultation", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkFields() {

        if(findingsET.length() == 0 || diagnosisET.length() == 0 || referralET.length() == 0 ||
                treatmentET.length() == 0 ) {
            Toast.makeText(add_consult.this, "Check text fields", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}
