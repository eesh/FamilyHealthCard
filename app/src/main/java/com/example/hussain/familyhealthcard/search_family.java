package com.example.hussain.familyhealthcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class search_family extends AppCompatActivity {

    @Bind(R.id.button) Button searchButton;
    @Bind(R.id.button1) Button addButton;
    @Bind(R.id.button3) Button updateButton;


    @Bind(R.id.editText) EditText placeET;
    @Bind(R.id.editText13) EditText areaET;
    @Bind(R.id.editText14) EditText streetET;
    @Bind(R.id.editText15) EditText houseET;

    @Bind(R.id.spinner3) Spinner patientsSpinner;

    BroadcastReceiver receiver = null;
    IntentFilter filter = null;
    DataSend dataSend = null;
    AdapterView.OnItemSelectedListener onItemSelectedListener = null;
    ArrayAdapter<Patient> patientAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_family);
        ButterKnife.bind(this);
        enableButtons(false);
        dataSend = DataSend.getInstance(getApplicationContext());


        filter = new IntentFilter();
        filter.addAction("datasend.familyNotFound");
        filter.addAction("datasend.familyFound");

        onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            int count=0;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(count >= 1 && i != 0) {
                    Patient selectedPatient = (Patient) adapterView.getItemAtPosition(i);
                    Intent intent = new Intent(search_family.this, create_family.class);
                    intent.putExtra("update", true);
                    intent.putExtra("pName", selectedPatient.getName());
                    startActivity(intent);
                }
                count ++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equalsIgnoreCase("datasend.familyNotFound")) {
                    enableButtons(false);
                    patientsSpinner.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Family with specified details not found", Toast.LENGTH_SHORT)
                            .show();
                } else if (intent.getAction().equalsIgnoreCase("datasend.familyFound")) {
                    DataModels model = DataModels.getInstance();
                    ArrayList<Patient> patients = new ArrayList<>(model.getPatientsList());
                    if(patients.size()>0) {
                        patients.add(0, new Patient(true));
                    }
                    patientAdapter = new ArrayAdapter<>(search_family.this, android.R.layout.simple_spinner_item, patients);
                    patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    patientsSpinner.setAdapter(patientAdapter);
                    patientsSpinner.setVisibility(View.VISIBLE);
                    patientsSpinner.setPrompt("Select patient");
                    enableButtons(true);
                }
                unregisterReceiver(this);
            }
        };


        if (patientsSpinner != null) {
            patientsSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }

        button();
    }

    @Override
    protected void onResume() {
        if(patientAdapter != null) {
            DataModels model = DataModels.getInstance();
            ArrayList<Patient> patients = new ArrayList<>(model.getPatientsList());
            if (patients.size() > 0) {
                patients.add(0, new Patient(true));
            }
            patientAdapter = new ArrayAdapter<>(search_family.this, android.R.layout.simple_spinner_item, patients);
            patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            patientsSpinner.setAdapter(patientAdapter);
            patientsSpinner.setVisibility(View.VISIBLE);
            patientsSpinner.setPrompt("Select patient");
            enableButtons(true);
        }
        super.onResume();
        Intent i = getIntent();
        if(i.getBooleanExtra("fromCreateFamily",false)) {
            int place = i.getIntExtra("place", 0);
            int area = i.getIntExtra("area", 0);
            int street = i.getIntExtra("street", 0);
            int house = i.getIntExtra("hno", 0);

            if(place != 0) {

                placeET.setText(""+place);
                areaET.setText(""+area);
                streetET.setText(""+street);
                houseET.setText(""+house);

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_family, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    public void button()
    {

        if(searchButton != null) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(patientAdapter != null) {
                        patientAdapter.clear();
                        patientAdapter.notifyDataSetChanged();
                    }
                    DataModels.getInstance().clear();
                    Log.e("Length", "" + placeET.length());
                    if(placeET.length() == 0 || areaET.length() == 0 || streetET.length() == 0 || houseET.length() == 0){
                        Toast.makeText(search_family.this, "Check fields", Toast.LENGTH_SHORT).show();
                    } else {
                        int place = Integer.parseInt(placeET.getText().toString());
                        int area = Integer.parseInt(areaET.getText().toString());
                        int street = Integer.parseInt(streetET.getText().toString());
                        int house = Integer.parseInt(houseET.getText().toString());
                        registerReceiver(receiver, filter);
                        dataSend.getFamilyDetails(place, area, street, house);
                    }
                }
            });
        }

        if (addButton != null) {
            addButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(search_family.this, create_family.class);
                            intent.putExtra("update", false);
                            startActivity(intent);
                            finish();
                        }
                    });
        }

        if (updateButton != null) {
            updateButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(search_family.this, create_family_2.class);
                            intent.putExtra("update", true);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
    }

    public void enableButtons(boolean enable) {
        if(enable) {
            addButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.VISIBLE);
            patientsSpinner.setVisibility(View.VISIBLE);

        } else {
            addButton.setVisibility(View.INVISIBLE);
            updateButton.setVisibility(View.INVISIBLE);
            patientsSpinner.setVisibility(View.INVISIBLE);
        }
    }
}
