package com.example.hussain.familyhealthcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class create_family extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.spinner) Spinner spinner;
    @Bind(R.id.spinner2) Spinner spinner2;

    @Bind(R.id.next) Button deleteButton;
    @Bind(R.id.add_family) Button add_family;
    @Bind(R.id.button2) Button addConsultButton;
    @Bind(R.id.button3) Button viewConsultButton;


    @Bind(R.id.editText3) EditText uniqueET;
    @Bind(R.id.editText4) EditText nameET;
    @Bind(R.id.editText9) EditText ageET;
    @Bind(R.id.myRadioGroup) RadioGroup genderRG;
    @Bind(R.id.radioButton) RadioButton maleRB;
    @Bind(R.id.radioButton2) RadioButton femaleRB;
    @Bind(R.id.myRadioGroup2) RadioGroup martialRG;
    @Bind(R.id.radioButton3) RadioButton marriedRB;
    @Bind(R.id.radioButton4) RadioButton singleRB;
    @Bind(R.id.editText10) EditText occuationET;
    @Bind(R.id.editText11) EditText religionET;
    @Bind(R.id.editText12) EditText incomeET;

    Patient patient;
    boolean updateMode = false;
    DataSend dataSend;
    DataModels dataModels;
    Family fam;

    String name, religion, occupation, sex, memberType, martialStatus, education;
    int age, income, familyID, patientID, uniqueID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        spinners();
        buttons();

        dataModels = DataModels.getInstance();
        fam = dataModels.getFamily();
        familyID = fam.familyID;
        Intent intent = getIntent();
        if(intent.getBooleanExtra("update", false)) {
            updateMode = true;
            setupFields();
        }

        dataSend = DataSend.getInstance(getApplicationContext());
        dataSend.getConsultations(patientID);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(updateMode) {
            dataSend.getConsultations(patientID);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?>parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if(parent == spinner) {
            memberType = parent.getItemAtPosition(position).toString();
        } else {
            education = parent.getItemAtPosition(position).toString();
        }

    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_family, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

   /*int id=item.getItemId();
        if(id==android.R.id.home)
            finish();*/
        return super.onOptionsItemSelected(item);
    }


    public void spinners() {
        // Spinner click listener
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        if (spinner2 != null) {
            spinner2.setOnItemSelectedListener(this);
        }

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select");
        categories.add("Self");
        categories.add("Wife");
        categories.add("Son");
        categories.add("Daughter");
        categories.add("Brother");
        categories.add("Sister");
        categories.add("Mother");
        categories.add("Father");

        List<String> categories2 = new ArrayList<String>();
        categories2.add("Select");
        categories2.add("Illiterate");
        categories2.add("Primary(Upto 5th Standard)");
        categories2.add("Middle(Upto 8th Standard)");
        categories2.add("Higher Secondary(Upto 12th Standard)");
        categories2.add("Graduate");
        categories2.add("Postgraduate");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);
    }


    public void buttons() {



        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(updateMode) {
                            dataSend.deletePatient(patientID);
                            Toast.makeText(getApplicationContext(), "Patient Deleted",
                                    Toast.LENGTH_SHORT).show();
                            dataModels.getPatientsList().remove(patient);
                            Intent intent = new Intent(create_family.this, search_family.class);
                            startActivity(intent);
                            finish();
                        } else finish();
                    }
                });
        add_family.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean check = checkFields();
                        if (!check)
                            return;

                        else {
                            getFieldValues();
                            Bundle bundle = new Bundle();
                            bundle.putInt("patientID", patientID);
                            bundle.putInt("houseID", uniqueID);
                            bundle.putInt("familyID", familyID);
                            bundle.putString("name", name);
                            bundle.putString("religion", religion);
                            bundle.putInt("age", age);
                            bundle.putInt("income", income);
                            bundle.putString("occupation", occupation);
                            bundle.putString("martialStatus", martialStatus);
                            bundle.putString("relation", memberType);
                            bundle.putString("sex", sex);
                            bundle.putString("education", education);
                            add_family.setEnabled(false);
                            if (updateMode) {
                                bundle.putBoolean("update", true);
                                dataSend.updatePatient(bundle);
                                Toast.makeText(getApplicationContext(), "Patient Updated",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(create_family.this, search_family.class);
                                startActivity(intent);
                                finish();
                            } else {
                                bundle.putBoolean("update", false);
                                dataSend.addPatient(bundle);
                                Toast.makeText(getApplicationContext(), "Patient Added",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(create_family.this, search_family.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });

        addConsultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(create_family.this, add_consult.class);
                intent.putExtra("patientID", patientID);
                startActivity(intent);
            }
        });

        viewConsultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataModels.getPatientFromID(patientID).consultations == null) {
                    Toast.makeText(create_family.this, "Data unavailable", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(create_family.this, view_consult.class);
                intent.putExtra("patientID", patientID);
                startActivity(intent);
            }
        });
    }


    private void getFieldValues() {

        RadioButton sexRB = (RadioButton) findViewById(genderRG.getCheckedRadioButtonId());
        RadioButton martialRB = (RadioButton) findViewById(martialRG.getCheckedRadioButtonId());
        uniqueID = Integer.parseInt(uniqueET.getText().toString());
        name = nameET.getText().toString();
        religion = religionET.getText().toString();
        age = Integer.parseInt(ageET.getText().toString());
        income = Integer.parseInt(incomeET.getText().toString());
        occupation = occuationET.getText().toString();
        if(sexRB != null) {
            if(maleRB != sexRB)
                sex = "Female";
            else sex = "Male";
        }
        if(martialRB != null) {
            if (martialRB != marriedRB)
                martialStatus = "Single";
            else martialStatus = "Married";
        }

    }


    public void setupFields() {

        deleteButton.setText("Delete Member");
        Intent intent = getIntent();
        if(intent.getBooleanExtra("update", false)) {

            String name = intent.getStringExtra("pName");
            patient = getPatientFromDataModel(name);
            if(patient != null) {

                patientID = patient.patientID;
                String age = ""+patient.age;
                String income = ""+patient.income;
                String uniqueIDstr = "" + patient.uniqueID;
                uniqueET.setText(uniqueIDstr);
                uniqueET.setEnabled(false);
                nameET.setText(patient.name);
                ageET.setText(age);
                religionET.setText(patient.religion);
                occuationET.setText(patient.occupation);
                incomeET.setText(income);
                setMarriedRB(patient.isMarried());
                setGenderRB(patient.sex);
                setSpinner(spinner, patient.memberType);
                memberType = patient.memberType;
                setSpinner(spinner2, patient.education);
                education = patient.education;
            }
        }
    }


    private Patient getPatientFromDataModel(String name) {

        if(name != null) {


            ArrayList<Patient> patients;
            patients = dataModels.getPatientsList();
            for(Patient p:patients) {

                if(name.equalsIgnoreCase(p.getName())) {

                    return p;
                }
            }
        }
        return null;
    }


    public void setSpinner(Spinner sp, String text) {
        boolean found = false;
        for(int i=0; i < sp.getAdapter().getCount(); i++) {
            String item = (String)sp.getItemAtPosition(i);
            if(text.equalsIgnoreCase(item)) {
                sp.setSelection(i);
                found = true;
                break;
            }
        }
        if(!found) {
            ArrayAdapter<String> adapter = (ArrayAdapter) sp.getAdapter();
            adapter.add(text);
            adapter.notifyDataSetChanged();
            sp.setSelection(adapter.getCount());
        }
    }


    public void setGenderRB(String gender) {
        if(gender.equalsIgnoreCase("Male")) {
            genderRG.check(R.id.radioButton);
        } else {
            genderRG.check(R.id.radioButton2);
        }
    }


    public void setMarriedRB(boolean married) {
        if(married) {
            martialRG.check(R.id.radioButton3);
        } else {
            martialRG.check(R.id.radioButton4);
        }
    }
    public boolean checkFields() {


        if(martialRG.getCheckedRadioButtonId() == -1 ||
                genderRG.getCheckedRadioButtonId()== -1 ) {
            Toast.makeText(create_family.this, "Check choice boxes", Toast.LENGTH_SHORT).show();
            return false;
        }




        if(nameET.length() == 0 || religionET.length() == 0 || occuationET.length() == 0 ||
                ageET.length() == 0 ||incomeET.length() == 0 || uniqueET.length() == 0) {
            Toast.makeText(create_family.this, "Check text or number fields", Toast.LENGTH_SHORT).show();
            return false;
        }



        if(spinner2.getSelectedItemPosition() == 0 || spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(create_family.this, "Check dropdown selections", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
