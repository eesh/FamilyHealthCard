package com.example.hussain.familyhealthcard;


import org.json.JSONException;
import org.json.JSONObject;

public class Family {
    
    String waterSource, familyType,socialEconomicStatus,eligibleCouples,familyPlanning, planningReasons;
    String house, typeOfHouse, vehicle, latrineFacility, solidWasteDisposal, sullageDisposal, spaceAroundHouse;
    String kitchen, pets, domesticAnimals, media, electricSupply;
    
    int familyID, grossIncome, perCapitaIncome, noOfPeople, livingSpace, distanceFromHouse, area, place, street, houseno;
    
    
    public Family(JSONObject object) {
        try {

            familyID = Integer.parseInt(object.getString("familyID"));
            area = Integer.parseInt(object.getString("place"));
            place = Integer.parseInt(object.getString("area"));
            street = Integer.parseInt(object.getString("street"));
            houseno = Integer.parseInt(object.getString("hno"));
            familyType = object.getString("familyType");
            grossIncome = Integer.parseInt(object.getString("grossIncome"));
            perCapitaIncome = Integer.parseInt(object.getString("perCapitaIncome"));
            socialEconomicStatus = object.getString("socialEconomicStatus");
            eligibleCouples = object.getString("eligibleCouples");
            familyPlanning = object.getString("familyPlanning");
            planningReasons = object.getString("planningReasons");
            house = object.getString("house");
            typeOfHouse = object.getString("typeOfHouse");
            livingSpace = Integer.parseInt(object.getString("livingSpace"));
            noOfPeople = Integer.parseInt(object.getString("noOfPeople"));
            waterSource = object.getString("waterSource");
            distanceFromHouse = Integer.parseInt(object.getString("distanceFromHouse"));
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
