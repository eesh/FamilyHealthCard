package com.example.hussain.familyhealthcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class view_consult extends AppCompatActivity {

    @Bind(R.id.textView9) TextView dateTV;
    @Bind(R.id.textView1) TextView findingTV;
    @Bind(R.id.textView11) TextView labfindingTV;
    @Bind(R.id.textView13) TextView diagnosisTV;
    @Bind(R.id.textView15) TextView treatmentTV;
    @Bind(R.id.textView17) TextView referralTV;
    @Bind(R.id.gotomain) Button backButton;

    DataModels models;
    int patientID;
    ArrayList<Consultation> consultations;
    Consultation consultation;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_consult);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        patientID = intent.getIntExtra("patientID", 0);

        models = DataModels.getInstance();

        if(patientID != 0) {

            patient = models.getPatientFromID(patientID);
            consultations = new ArrayList<>(patient.getConsultations());
            consultation = consultations.get(consultations.size()-1);
            setupFields();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid patient ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        buttons();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_consult, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    public void setupFields() {

        String time = consultation.time;
        dateTV.setText(time);
        findingTV.setText(consultation.findings);
        diagnosisTV.setText(consultation.diagnosis);
        labfindingTV.setText(consultation.labfindings);
        treatmentTV.setText(consultation.treatment);
        referralTV.setText(consultation.referral);
    }


    public void buttons() {

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view_consult.this.finish();
            }
        });
    }
}