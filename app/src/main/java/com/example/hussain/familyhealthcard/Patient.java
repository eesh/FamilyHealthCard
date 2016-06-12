package com.example.hussain.familyhealthcard;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Patient {


    public int age, income, uniqueID, patientID;
    public String name, sex,religion, occupation, education, martialStatus, memberType;
    ArrayList<Consultation> consultations = null;



    public ArrayList<Consultation> getConsultations() {
        return consultations;
    }

    Patient(boolean spinner) {
        if(spinner) name = "Select Patient";
        else name = "No Patients";
    }

    Patient(JSONObject object) {
        try {

            patientID = Integer.parseInt(object.getString("patientID"));
            age = Integer.parseInt(object.getString("age"));
            income = Integer.parseInt(object.getString("income"));
            uniqueID = Integer.parseInt(object.getString("houseID"));
            name = object.getString("name");
            sex = object.getString("sex");
            religion = object.getString("religion");
            education = object.getString("education");
            occupation = object.getString("occupation");
            martialStatus = object.getString("martialStatus");
            memberType = object.getString("memberType");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isMarried() {
        return martialStatus.equalsIgnoreCase("Married");
    }

    @Override
    public String toString() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int houseID) {
        this.uniqueID = houseID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String martialStatus) {
        this.martialStatus = martialStatus;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public void addConsultation(Consultation consultation) {

        if(consultations == null) {
            consultations = new ArrayList<Consultation>();
        }
        consultations.add(consultation);
    }
}
