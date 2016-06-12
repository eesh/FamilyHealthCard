package com.example.hussain.familyhealthcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class create_family_2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;

    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.spinner2)
    Spinner spinner2;

    @Bind(R.id.myRadioGroup)
    RadioGroup familyType;
    @Bind(R.id.myRadioGroup2)
    RadioGroup houseRG;
    @Bind(R.id.myRadioGroup3)
    RadioGroup houseType;
    @Bind(R.id.myRadioGroup4)
    RadioGroup eSupply;
    @Bind(R.id.myRadioGroup5)
    RadioGroup latrine;
    @Bind(R.id.myRadioGroup6)
    RadioGroup solidWaste;
    @Bind(R.id.myRadioGroup7)
    RadioGroup sullage;
    @Bind(R.id.myRadioGroup8)
    RadioGroup space;
    @Bind(R.id.myRadioGroup9)
    RadioGroup garden;
    @Bind(R.id.myRadioGroup10)
    RadioGroup pets;
    @Bind(R.id.myRadioGroup11)
    RadioGroup domestic;
    @Bind(R.id.myRadioGroup12)
    RadioGroup media;
    @Bind(R.id.radioButton)
    RadioButton nuclear;
    @Bind(R.id.radioButton2)
    RadioButton joint;
    @Bind(R.id.radioButton3)
    RadioButton extended;
    @Bind(R.id.radioButton4)
    RadioButton rent;
    @Bind(R.id.radioButton5)
    RadioButton own;
    @Bind(R.id.radioButton6)
    RadioButton kuchha;
    @Bind(R.id.radioButton7)
    RadioButton semipucca;
    @Bind(R.id.radioButton8)
    RadioButton pucca;
    @Bind(R.id.radioButton9)
    RadioButton present;
    @Bind(R.id.radioButton10)
    RadioButton absent;
    @Bind(R.id.radioButton11)
    RadioButton noFacility;
    @Bind(R.id.radioButton12)
    RadioButton conservancy;
    @Bind(R.id.radioButton13)
    RadioButton sanitary;
    @Bind(R.id.radioButton14)
    RadioButton indiscriminate;
    @Bind(R.id.radioButton15)
    RadioButton municipal;
    @Bind(R.id.radioButton16)
    RadioButton openS;
    @Bind(R.id.radioButton17)
    RadioButton socket;
    @Bind(R.id.radioButton18)
    RadioButton sewageD;
    @Bind(R.id.radioButton19)
    RadioButton pre;
    @Bind(R.id.radioButton20)
    RadioButton ab;
    @Bind(R.id.radioButton21)
    RadioButton pre1;
    @Bind(R.id.radioButton22)
    RadioButton ab1;
    @Bind(R.id.radioButton23)
    RadioButton yes;
    @Bind(R.id.radioButton24)
    RadioButton no;
    @Bind(R.id.radioButton26)
    RadioButton y1;
    @Bind(R.id.radioButton27)
    RadioButton n1;
    @Bind(R.id.radioButton28)
    RadioButton radio;
    @Bind(R.id.radioButton29)
    RadioButton tv;
    @Bind(R.id.radioButton30)
    RadioButton newspaper;

    @Bind(R.id.editText1)
    EditText grossIncome;
    @Bind(R.id.editText2)
    EditText perCapita;
    @Bind(R.id.editText4)
    EditText ecoStatus;
    @Bind(R.id.editText5)
    EditText livingSpace;
    @Bind(R.id.editText6)
    EditText people;
    @Bind(R.id.editText10)
    EditText distFromHouse;
    @Bind(R.id.editText11)
    EditText placeET;
    @Bind(R.id.editText12)
    EditText areaET;
    @Bind(R.id.editText7)
    EditText streetET;
    @Bind(R.id.editText8)
    EditText hnoET;


    int grossI, perCap, livingS, dist, People, place, area, street, house;
    String ecoS, familyT, houseT, houseTy, eS, lat, solid, sul, Space, Garden, Pets, Dom, Media;
    int age, income, familyID;

    Family family;

    DataModels models;
    DataSend dataSend = null;
    boolean updateMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family_2);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ButterKnife.bind(this);

        dataSend = DataSend.getInstance(getApplicationContext());

        spinners();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("update", false)) {
            updateMode = true;
            models = DataModels.getInstance();
            family = models.getFamily();
            familyID = family.familyID;
            setupFields();
        }
        button();
    }


    public void setupFields() {

        setFamilyTypeRB(family.familyType);
        setHouseRB(family.house);
        setHouseTypeRB(family.typeOfHouse);
        seteSupplyRB(family.electricSupply);
        setLatrineRB(family.latrineFacility);
        setSolidWasteRB(family.solidWasteDisposal);
        setSullageRB(family.sullageDisposal);
        setSpaceRB(family.spaceAroundHouse);
        setGardenRB(family.kitchen);
        setPetsRB(family.pets);
        setDomesticRB(family.domesticAnimals);
        setMediaRB(family.media);
        setSpinner(spinner, family.waterSource);
        setSpinner(spinner2, family.vehicle);
        String grossIncomestr = ""+family.grossIncome;
        String perCstr = ""+family.perCapitaIncome;
        String livingSpaceStr = ""+family.livingSpace;
        String noOfpplstr = ""+family.noOfPeople;
        String distStr = ""+family.distanceFromHouse;
        grossIncome.setText(grossIncomestr);
        perCapita.setText(perCstr);
        livingSpace.setText(livingSpaceStr);
        people.setText(noOfpplstr);
        distFromHouse.setText(distStr);
        ecoStatus.setText(family.socialEconomicStatus);
        String placestr = ""+family.place;
        String areastr = ""+family.area;
        String streetstr = "" + family.street;
        String housestr = "" + family.houseno;
        placeET.setText(placestr);
        areaET.setText(areastr);
        streetET.setText(streetstr);
        hnoET.setText(housestr);
        placeET.setEnabled(false);
        areaET.setEnabled(false);
        streetET.setEnabled(false);
        hnoET.setEnabled(false);
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


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onBackPressed() {
        if (updateMode) {
            Intent i = new Intent(create_family_2.this, search_family.class);
            startActivity(i);
        } else {
            Intent i = new Intent(create_family_2.this, add.class);
            startActivity(i);
            finish();
        }
        finish();
    }


    public void intentHandler(Context context, Intent intent, Bundle bundle) {

        if (intent.getAction().equalsIgnoreCase("datasend.createFSuccess")) {

            Intent i = new Intent(create_family_2.this, search_family.class);
            i.putExtra("familyDetails", bundle);
            i.putExtra("place",place);
            i.putExtra("area", area);
            i.putExtra("street", street);
            i.putExtra("hno", house);
            i.putExtra("fromCreateFamily",true);
            startActivity(i);
            finish();
        } else if (intent.getAction().equalsIgnoreCase("datasend.createFFailed")) {
            Toast.makeText(getApplicationContext(), "Couldn't create family. Try again",
                    Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equalsIgnoreCase("datasend.updateFamilyFailed")) {
            Toast.makeText(getApplicationContext(), "Failed to update details",
                    Toast.LENGTH_SHORT).show();
                    /*
                    Intent i = new Intent(create_family_2.this, search_family.class);
                    startActivity(i);
                    finish();*/
        } else if (intent.getAction().equalsIgnoreCase("datasend.updateFamilySuccess")) {
            Toast.makeText(getApplicationContext(), "Details Updated",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(create_family_2.this, search_family.class);
            startActivity(i);
            finish();
        }
    }


    public void button() {

        final Bundle bundle = new Bundle();
        Button submit = (Button) findViewById(R.id.submit);

        final IntentFilter filter = new IntentFilter();
        filter.addAction("datasend.createFSuccess");
        filter.addAction("datasend.createFFailed");
        filter.addAction("datasend.updateFamilySuccess");
        filter.addAction("datasend.updateFamilyFailed");

        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                intentHandler(context, intent, bundle);
                unregisterReceiver(this);
            }
        };

        if (submit != null) {
            submit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            boolean check = checkFields();
                            if(!check) {
                                return;
                            }

                            place = Integer.parseInt(placeET.getText().toString());
                            area = Integer.parseInt(areaET.getText().toString());
                            street = Integer.parseInt(streetET.getText().toString());
                            house = Integer.parseInt(hnoET.getText().toString());



                            RadioButton familyTypeButton = (RadioButton) findViewById(familyType.getCheckedRadioButtonId());
                            RadioButton houseButton = (RadioButton) findViewById(houseRG.getCheckedRadioButtonId());
                            RadioButton houseTypeButton = (RadioButton) findViewById(houseType.getCheckedRadioButtonId());
                            RadioButton eSupplyButton = (RadioButton) findViewById(eSupply.getCheckedRadioButtonId());
                            RadioButton latrineButton = (RadioButton) findViewById(latrine.getCheckedRadioButtonId());
                            RadioButton solidWasteButton = (RadioButton) findViewById(solidWaste.getCheckedRadioButtonId());
                            RadioButton sullageButton = (RadioButton) findViewById(sullage.getCheckedRadioButtonId());
                            RadioButton spaceButton = (RadioButton) findViewById(space.getCheckedRadioButtonId());
                            RadioButton gardenButton = (RadioButton) findViewById(garden.getCheckedRadioButtonId());
                            RadioButton petsButton = (RadioButton) findViewById(pets.getCheckedRadioButtonId());
                            RadioButton domesticButton = (RadioButton) findViewById(domestic.getCheckedRadioButtonId());
                            RadioButton mediaButton = (RadioButton) findViewById(media.getCheckedRadioButtonId());

                            bundle.putInt("place", place);
                            bundle.putInt("area", area);
                            bundle.putInt("street", street);
                            bundle.putInt("hno", house);
                            bundle.putString("vehicle", spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString());
                            bundle.putString("waterSource", spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
                            bundle.putString("familyType", familyTypeButton.getText().toString());
                            bundle.putInt("familyID", familyID);
                            bundle.putInt("grossIncome", Integer.parseInt(grossIncome.getText().toString()));
                            bundle.putInt("perCapitaIncome", Integer.parseInt(perCapita.getText().toString()));
                            bundle.putString("socialEconomicStatus", ecoStatus.getText().toString());
                            bundle.putString("eligibleCouples", "");
                            bundle.putString("familyPlanning", "");
                            bundle.putString("planningReasons", "");
                            bundle.putString("house", houseButton.getText().toString());
                            bundle.putString("typeOfHouse", houseTypeButton.getText().toString());
                            bundle.putInt("livingSpace", Integer.parseInt(livingSpace.getText().toString()));
                            bundle.putInt("noOfPeople", Integer.parseInt(people.getText().toString()));
                            bundle.putInt("distanceFromHouse", Integer.parseInt(distFromHouse.getText().toString()));
                            bundle.putString("latrineFacility", latrineButton.getText().toString());
                            bundle.putString("solidWasteDisposal", solidWasteButton.getText().toString());
                            bundle.putString("sullageDisposal", sullageButton.getText().toString());
                            bundle.putString("spaceAroundHouse", spaceButton.getText().toString());
                            bundle.putString("kitchen", gardenButton.getText().toString());
                            bundle.putString("pets", petsButton.getText().toString());
                            bundle.putString("domesticAnimals", domesticButton.getText().toString());
                            bundle.putString("media", mediaButton.getText().toString());
                            bundle.putString("electricSupply", eSupplyButton.getText().toString());


                            registerReceiver(receiver, filter);

                            if (updateMode) {
                                dataSend.updateFamily(bundle);
                            } else dataSend.createFamily(bundle);
                        }
                    });
        }


    }


    public void spinners() {

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        List<String> categories2 = new ArrayList<String>();
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
    }

    private void getFieldValues() {

        RadioButton familyTypeRB = (RadioButton) findViewById(familyType.getCheckedRadioButtonId());
        RadioButton houseRB = (RadioButton) findViewById(houseRG.getCheckedRadioButtonId());
        RadioButton houseTypeRB = (RadioButton) findViewById(houseType.getCheckedRadioButtonId());
        RadioButton eSupplyRB = (RadioButton) findViewById(eSupply.getCheckedRadioButtonId());
        RadioButton latrineRB = (RadioButton) findViewById(latrine.getCheckedRadioButtonId());
        RadioButton solidWasteRB = (RadioButton) findViewById(solidWaste.getCheckedRadioButtonId());
        RadioButton sullageRB = (RadioButton) findViewById(sullage.getCheckedRadioButtonId());
        RadioButton spaceRB = (RadioButton) findViewById(space.getCheckedRadioButtonId());
        RadioButton gardenRB = (RadioButton) findViewById(garden.getCheckedRadioButtonId());
        RadioButton petsRB = (RadioButton) findViewById(pets.getCheckedRadioButtonId());
        RadioButton domesticRB = (RadioButton) findViewById(domestic.getCheckedRadioButtonId());
        RadioButton mediaRB = (RadioButton) findViewById(media.getCheckedRadioButtonId());
        grossI = Integer.parseInt(grossIncome.getText().toString());
        perCap = Integer.parseInt(perCapita.getText().toString());
        livingS = Integer.parseInt(livingSpace.getText().toString());
        People = Integer.parseInt(people.getText().toString());
        ecoS = ecoStatus.getText().toString();
        dist = Integer.parseInt(distFromHouse.getText().toString());
        place = Integer.parseInt(placeET.getText().toString());
        area = Integer.parseInt(areaET.getText().toString());
        street = Integer.parseInt(streetET.getText().toString());
        house = Integer.parseInt(hnoET.getText().toString());
        if (familyTypeRB != null) {
            if (familyTypeRB != nuclear && familyTypeRB != joint)
                familyT = "Extended";
            else if (familyTypeRB != nuclear && familyTypeRB != extended)
                familyT = "Joint";
            else familyT = "Nuclear";
        }
        if (houseRB != null) {
            if (houseRB != rent)
                houseT = "Own";

            else houseT = "Rent";
        }
        if (houseTypeRB != null) {
            if (houseRB != pucca && houseRB != semipucca)
                houseTy = "Kuccha";
            else if (houseRB != pucca && houseRB != kuchha)
                houseTy = "Semipucca";
            else familyT = "pucca";
        }
        if (eSupplyRB != null) {
            if (eSupplyRB != present)
                eS = "Absent";

            else eS = "Present";
        }
        if (latrineRB != null) {
            if (latrineRB != conservancy && latrineRB != sanitary)
                lat = "No Facility";
            else if (latrineRB != noFacility && latrineRB != sanitary)
                houseTy = "Conservancy";
            else familyT = "Sanitary";
        }
        if (solidWasteRB != null) {
            if (solidWasteRB != municipal)
                solid = "Indiscriminate";

            else solid = "Sanitary";

        }
        if (sullageRB != null) {
            if (sullageRB != sewageD && latrineRB != socket)
                sul = "Open Stagnation";
            else if (sullageRB != openS && latrineRB != socket)
                sul = "Sewage Disposal";
            else
                sul = "Sockpit";
        }

        if (spaceRB != null) {
            if (spaceRB != pre)
                Space = "Absent";

            else
                Space = "Present";
        }
        if (gardenRB != null) {
            if (gardenRB != pre1)
                Garden = "Absent";

            else
                Garden = "Present";
        }
        if (petsRB != null) {
            if (petsRB != yes)
                Pets = "No";

            else
                Pets = "Yes";
        }
        if (domesticRB != null) {
            if (domesticRB != y1)
                Dom = "No";

            else
                Dom = "Yes";
        }
        if (mediaRB != null) {
            if (mediaRB != radio && mediaRB != tv)
                Media = "News Paper";
            else if (mediaRB != radio && mediaRB != newspaper)
                Media = "Television";
            else Media = "Radio";
        }
    }

    public void setSpinner(Spinner sp, String text) {
        boolean found = false;
        for (int i = 0; i < sp.getAdapter().getCount(); i++) {
            String item = (String) sp.getItemAtPosition(i);
            if (text.equalsIgnoreCase(item)) {
                sp.setSelection(i);
                found = true;
                break;
            }
        }
        if (!found) {
            ArrayAdapter<String> adapter = (ArrayAdapter) sp.getAdapter();
            adapter.add(text);
            adapter.notifyDataSetChanged();
            sp.setSelection(adapter.getCount());
        }
    }

    public void setFamilyTypeRB(String type) {
        familyType = (RadioGroup) findViewById(R.id.myRadioGroup);
        if (type.equalsIgnoreCase("Nuclear")) {
            familyType.check(R.id.radioButton);
        } else if (type.equalsIgnoreCase("Joint")) {
            familyType.check(R.id.radioButton2);
        } else {
            familyType.check(R.id.radioButton3);
        }
    }

    public void setHouseRB(String house) {
        if (house.equalsIgnoreCase("Rent"))
            houseRG.check(R.id.radioButton4);
        else

            houseRG.check(R.id.radioButton5);

    }

    public void setHouseTypeRB(String houset) {
        if (houset.equalsIgnoreCase("Kucha"))
            houseType.check(R.id.radioButton6);
        else if (houset.equalsIgnoreCase("Semipucca"))
            houseType.check(R.id.radioButton7);
        else

            houseType.check(R.id.radioButton8);

    }

    public void seteSupplyRB(String esupply) {
        if (esupply.equalsIgnoreCase("Present"))
            eSupply.check(R.id.radioButton9);
        else


            eSupply.check(R.id.radioButton10);

    }

    public void setLatrineRB(String laterine) {
        if (laterine.equalsIgnoreCase("No Facility"))
            latrine.check(R.id.radioButton11);
        else if (laterine.equalsIgnoreCase("Conservancy"))
            latrine.check(R.id.radioButton12);
        else

            latrine.check(R.id.radioButton13);

    }

    public void setSolidWasteRB(String solidW) {
        if (solidW.equalsIgnoreCase("Indiscriminate"))
            solidWaste.check(R.id.radioButton14);
        else
            solidWaste.check(R.id.radioButton15);

    }

    public void setSullageRB(String sul) {
        if (sul.equalsIgnoreCase("Open Stagnation"))
            sullage.check(R.id.radioButton16);
        else if (sul.equalsIgnoreCase("Sockpit"))
            sullage.check(R.id.radioButton17);
        else
            sullage.check(R.id.radioButton18);

    }

    public void setSpaceRB(String sp) {
        if (sp.equalsIgnoreCase("Present"))
            space.check(R.id.radioButton19);

        else

            space.check(R.id.radioButton20);

    }

    public void setGardenRB(String g) {
        if (g.equalsIgnoreCase("Present"))
         garden.check(R.id.radioButton21);

        else

            garden.check(R.id.radioButton22);

    }

    public void setPetsRB(String p) {
        if (p.equalsIgnoreCase("Yes"))
            pets.check(R.id.radioButton23);

        else

            pets.check(R.id.radioButton24);

    }

    public void setDomesticRB(String d) {
        if (d.equalsIgnoreCase("Yes"))
           domestic.check(R.id.radioButton26);

        else

            domestic.check(R.id.radioButton27);

    }

    public void setMediaRB(String m) {
        if (m.equalsIgnoreCase("Radio"))
            media.check(R.id.radioButton28);

        else
        if (m.equalsIgnoreCase("Television"))
            media.check(R.id.radioButton29);

        else

            media.check(R.id.radioButton30);

    }
    
    public boolean checkFields() {


        if(familyType.getCheckedRadioButtonId() == -1 ||
                houseRG.getCheckedRadioButtonId()== -1 ||
                houseType.getCheckedRadioButtonId() == -1 ||
                eSupply.getCheckedRadioButtonId() == -1 ||
                latrine.getCheckedRadioButtonId() == -1 ||
                solidWaste.getCheckedRadioButtonId() == -1 ||
                sullage.getCheckedRadioButtonId() == -1 ||
                space.getCheckedRadioButtonId() == -1 ||
                garden.getCheckedRadioButtonId() == -1 ||
                pets.getCheckedRadioButtonId() == -1 ||
                domestic.getCheckedRadioButtonId() == -1 ||
                media.getCheckedRadioButtonId() == -1) {
            Toast.makeText(create_family_2.this, "Check choice boxes", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(grossIncome.length() == 0 || perCapita.length() == 0 || livingSpace.length() == 0 ||
                people.length() == 0 || ecoStatus.length() == 0 || distFromHouse.length() == 0 ||
                placeET.length()== 0 || areaET.length() == 0 || streetET.length() == 0 ||
                hnoET.length() == 0) {
            Toast.makeText(create_family_2.this, "Check text or number fields", Toast.LENGTH_SHORT).show();
            return false;
        }



        if(spinner2.getSelectedItemPosition() == 0 || spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(create_family_2.this, "Check dropdown selections", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}



