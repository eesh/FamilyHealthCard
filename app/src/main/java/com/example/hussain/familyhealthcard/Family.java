package com.example.hussain.familyhealthcard;


import org.json.JSONException;
import org.json.JSONObject;

public class Family {
    
    String familyID, waterSource, familyType,socialEconomicStatus,eligibleCouples,familyPlanning, planningReasons;
    String house, typeOfHouse, vehicle, latrineFacility, solidWasteDisposal, sullageDisposal, spaceAroundHouse;
    String kitchen, pets, domesticAnimals, media, electricSupply;
    
    int grossIncome, perCapitaIncome, noOfPeople, livingSpace, distanceFromHouse;

    //test
    
    public Family(JSONObject object) {
        try {
            familyID = object.getString("familyID");
            familyType = object.getString("familyType");
            grossIncome = object.getInt("grossIncome");
            perCapitaIncome = object.getInt("perCapitaIncome");
            socialEconomicStatus = object.getString("socialEconomicStatus");
            eligibleCouples = object.getString("eligibleCouples");
            familyPlanning = object.getString("familyPlanning");
            planningReasons = object.getString("planningReasons");
            house = object.getString("house");
            typeOfHouse = object.getString("typeOfHouse");
            livingSpace = object.getInt("livingSpace");
            noOfPeople = object.getInt("noOfPeople");
            waterSource = object.getString("waterSource");
            distanceFromHouse = object.getInt("distanceFromHouse");
            latrineFacility = object.getString("latrineFacility");
            solidWasteDisposal = object.getString("solidWasteDisposal");
            sullageDisposal = object.getString("sullageDisposal");
            spaceAroundHouse = object.getString("spaceAroundHouse");
            kitchen = object.getString("kitchen");
            pets = object.getString("pets");
            domesticAnimals = object.getString("domesticAnimals");
            media = object.getString("media");
            vehicle = object.getString("vehicle");
            electricSupply = object.getString("electricSupply");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
