package com.example.hussain.familyhealthcard;


import org.json.JSONObject;

import java.util.ArrayList;

public class DataModels {

    public static DataModels instance = null;

    private ArrayList<Patient> patientsList = null;

    private Family family = null;




    public Family getFamily() {
        return family;
    }

    public void setFamily(JSONObject object) {
        this.family = new Family(object);
    }

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
        if(patientsList != null) {
            patientsList.clear();
        }
        family = null;
    }

    public ArrayList<Patient> getPatientsList() {
        return patientsList;
    }

    public Patient getPatientFromID(int patientid) {

        for(Patient p:patientsList) {
            if(p.patientID == patientid) {
                return p;
            }
        }
        return null;
    }
}
