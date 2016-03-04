package com.example.hussain.familyhealthcard;


import org.json.JSONObject;

import java.util.ArrayList;

public class DataModels {

    public static DataModels instance = null;

    private ArrayList<Patient> patientsList = null;

    public static DataModels getInstance() {
        if(instance == null) {
            instance = new DataModels();
            instance.patientsList = new ArrayList<>();
        }
        return instance;
     }

    public void add(JSONObject patientDetails) {
        patientsList.add(new Patient(patientDetails));
    }

    public void clear() {
        patientsList.clear();
    }

    public ArrayList<Patient> getPatientsList() {
        return patientsList;
    }
}
