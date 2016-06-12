package com.example.hussain.familyhealthcard;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eesh on 14/04/16.
 */
public class Consultation {

    int id;


    String referral, treatment, findings, diagnosis,labfindings;
    String time;
    Date date;


    Consultation(JSONObject object) {
        try {
            id = Integer.parseInt(object.getString("cID"));
            referral = object.getString("referral");
            treatment = object.getString("treatment");
            findings = object.getString("findings");
            labfindings=object.getString("labfindings");
            diagnosis = object.getString("diagnosis");
            time = object.getString("time");
            DateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            try {
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
