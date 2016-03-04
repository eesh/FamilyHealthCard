package com.example.hussain.familyhealthcard;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class create_family_2 extends ActionBarActivity implements AdapterView.OnItemSelectedListener{


    @InjectView(R.id.spinner) Spinner spinner;
    @InjectView(R.id.spinner2) Spinner spinner2;

    @InjectView(R.id.myRadioGroup) RadioGroup familyType;
    @InjectView(R.id.myRadioGroup2) RadioGroup house;
    @InjectView(R.id.myRadioGroup3) RadioGroup houseType;
    @InjectView(R.id.myRadioGroup4) RadioGroup eSupply;
    @InjectView(R.id.myRadioGroup5) RadioGroup latrine;
    @InjectView(R.id.myRadioGroup6) RadioGroup solidWaste;
    @InjectView(R.id.myRadioGroup7) RadioGroup sullage;
    @InjectView(R.id.myRadioGroup8) RadioGroup space;
    @InjectView(R.id.myRadioGroup9) RadioGroup garden;
    @InjectView(R.id.myRadioGroup10) RadioGroup pets;
    @InjectView(R.id.myRadioGroup11) RadioGroup domestic;
    @InjectView(R.id.myRadioGroup12) RadioGroup media;

    @InjectView(R.id.editText1) EditText grossIncome;
    @InjectView(R.id.editText2) EditText perCapita;
    @InjectView(R.id.editText4) EditText ecoStatus;
    @InjectView(R.id.editText5) EditText livingSpace;
    @InjectView(R.id.editText6) EditText people;
    @InjectView(R.id.editText10) EditText distFromHouse;
    @InjectView(R.id.editText11) EditText place;
    @InjectView(R.id.editText12) EditText area;
    @InjectView(R.id.editText7) EditText street;
    @InjectView(R.id.editText8) EditText hno;


    DataSend dataSend = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family_2);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.inject(this);

        dataSend = DataSend.getInstance(getApplicationContext());

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        List<String> categories2= new ArrayList<String>();
        categories.add("Select");
        categories.add("Protected");
        categories.add("Unprotected");
        categories.add("Pipe");
        categories.add("Street Tap");
        categories.add("Tube Well");
        categories.add("Open Well");
        categories.add("Surface Water");

        categories2.add("Select");
        categories2.add("Cycle");
        categories2.add("Motorcycle");
        categories2.add("Car");
        categories2.add("Bullock Cart");
        categories2.add("Other");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);

        button();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_family_2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void button()
    {
        final Bundle bundle = new Bundle();
        Button submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String familyID = place.getText().toString() + area.getText().toString() + street.getText().toString()
                                + hno.getText().toString();


                        RadioButton familyTypeButton = (RadioButton) findViewById(familyType.getCheckedRadioButtonId());
                        RadioButton houseButton = (RadioButton) findViewById(house.getCheckedRadioButtonId());
                        RadioButton houseTypeButton = (RadioButton) findViewById(houseType.getCheckedRadioButtonId());
                        RadioButton eSupplyButton = (RadioButton) findViewById(eSupply.getCheckedRadioButtonId());
                        RadioButton latrineButton = (RadioButton) findViewById(latrine.getCheckedRadioButtonId());
                        RadioButton solidWasteButton  = (RadioButton) findViewById(solidWaste.getCheckedRadioButtonId());
                        RadioButton sullageButton = (RadioButton) findViewById(sullage.getCheckedRadioButtonId());
                        RadioButton spaceButton = (RadioButton) findViewById(space.getCheckedRadioButtonId());
                        RadioButton gardenButton = (RadioButton) findViewById(garden.getCheckedRadioButtonId());
                        RadioButton petsButton = (RadioButton) findViewById(pets.getCheckedRadioButtonId());
                        RadioButton domesticButton = (RadioButton) findViewById(domestic.getCheckedRadioButtonId());
                        RadioButton mediaButton = (RadioButton) findViewById(media.getCheckedRadioButtonId());

                        bundle.putString("vehicle", spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString());
                        bundle.putString("waterSource", spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
                        bundle.putString("familyType", familyTypeButton.getText().toString());
                        bundle.putString("familyID", familyID);
                        bundle.putInt("grossIncome",Integer.parseInt(grossIncome.getText().toString()));
                        bundle.putInt("perCapitaIncome",Integer.parseInt(perCapita.getText().toString()));
                        bundle.putString("socialEconomicStatus",ecoStatus.getText().toString());
                        bundle.putString("eligibleCouples","");
                        bundle.putString("familyPlanning","");
                        bundle.putString("planningReasons","");
                        bundle.putString("house",houseButton.getText().toString());
                        bundle.putString("typeOfHouse",houseTypeButton.getText().toString());
                        bundle.putInt("livingSpace",Integer.parseInt(livingSpace.getText().toString()));
                        bundle.putInt("noOfPeople",Integer.parseInt(people.getText().toString()));
                        bundle.putInt("distanceFromHouse",Integer.parseInt(distFromHouse.getText().toString()));
                        bundle.putString("latrineFacility", latrineButton.getText().toString());
                        bundle.putString("solidWasteDisposal", solidWasteButton.getText().toString());
                        bundle.putString("sullageDisposal", sullageButton.getText().toString());
                        bundle.putString("spaceAroundHouse", spaceButton.getText().toString());
                        bundle.putString("kitchen", gardenButton.getText().toString());
                        bundle.putString("pets", petsButton.getText().toString());
                        bundle.putString("domesticAnimals", domesticButton.getText().toString());
                        bundle.putString("media", mediaButton.getText().toString());
                        bundle.putString("electricSupply", eSupplyButton.getText().toString());


                        dataSend.createFamily(bundle);


                        /*
                        Intent intent = new Intent(create_family_2.this, add.class);
                        intent.putExtra("familyDetails", bundle);
                        startActivity(intent);
                        finish(); */
                    }
                });

    }
}
