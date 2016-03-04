package com.example.hussain.familyhealthcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataSend {

    private static String addPatientURL = "http://srmmedicalproject.net23.net/apis/addPatient.php";
    private static String addFamilyURL = "http://srmmedicalproject.net23.net/apis/addFamily.php";
    private static String familyDetailsURL = "http://srmmedicalproject.net23.net/apis/getFamilyDetails.php";

    private static String updatePatientURL = "http://srmmedicalproject.net23.net/apis/updatePatient.php";
    private static String authURL = "http://srmmedicalproject.net23.net/apis/auth.php";
    private Context appContext;
    private static DataSend instance = null;
    private RequestQueue queue = null;

    public DataSend(Context context) {
        appContext = context;
        queue = Singleton.getInstance(appContext).getRequestQueue();
    }

    public static DataSend getInstance(Context context) {
        if(instance == null) {
            instance = new DataSend(context);
        }
        return instance;
    }

    public void addPatient(Bundle bundle) {
        JSONObject object = new JSONObject();
        try {
            object.put("name",bundle.get("name"));
            object.put("age",bundle.get("age"));
            object.put("memberType",bundle.get("memberType"));
            object.put("martialStatus",bundle.get("martialStatus"));
            object.put("education",bundle.get("education"));
            object.put("occupation",bundle.get("occupation"));
            object.put("income",bundle.get("income"));
            object.put("religion",bundle.get("religion"));
            object.put("houseID",bundle.get("houseID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, addPatientURL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization",getAuthDetails());
                return map;
            }
        };
        queue.add(request);
    }

    public void createFamily(Bundle bundle)  {
        final JSONObject object = new JSONObject();
        try {
            object.put("familyID",bundle.get("familyID"));
            object.put("familyType",bundle.get("familyType"));
            object.put("grossIncome",bundle.get("grossIncome"));
            object.put("perCapitaIncome",bundle.get("perCapitaIncome"));
            object.put("socialEconomicStatus",bundle.get("socialEconomicStatus"));
            object.put("eligibleCouples",bundle.get("eligibleCouples"));
            object.put("familyPlanning",bundle.get("familyPlanning"));
            object.put("planningReasons",bundle.get("planningReasons"));
            object.put("house",bundle.get("house"));
            object.put("typeOfHouse",bundle.get("typeOfHouse"));
            object.put("livingSpace",bundle.get("livingSpace"));
            object.put("noOfPeople",bundle.get("noOfPeople"));
            object.put("waterSource",bundle.get("waterSource"));
            object.put("distanceFromHouse",bundle.get("distanceFromHouse"));
            object.put("latrineFacility",bundle.get("latrineFacility"));
            object.put("solidWasteDisposal",bundle.get("solidWasteDisposal"));
            object.put("sullageDisposal",bundle.get("sullageDisposal"));
            object.put("spaceAroundHouse",bundle.get("spaceAroundHouse"));
            object.put("kitchen",bundle.get("kitchen"));
            object.put("pets",bundle.get("pets"));
            object.put("domesticAnimals",bundle.get("domesticAnimals"));
            object.put("media",bundle.get("media"));
            object.put("vehicle",bundle.get("vehicle"));
            object.put("electricSupply",bundle.get("electricSupply"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, addFamilyURL, object,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Authorization", "Basic " + getAuthDetails());
                return map;
            }
        };


        Log.e("REQUEST",request.toString());
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void updatePatient(Bundle bundle, List<String> fields) {
        JSONObject object = new JSONObject();
        try {
            for (String field:fields) {
                object.put(field, bundle.get(field));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest request = new JsonObjectRequest(Request.Method.POST, updatePatientURL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    public void authenticate(String username, String password) {

        final String auth = username + ":" + password;
        StringRequest request = new StringRequest(Request.Method.POST, authURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    Log.e("JSON", object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(object != null) {
                    try {
                        if(object.getBoolean("logged")) {
                            appContext.sendBroadcast(new Intent("datasender.authSuccess"));
                        } else {
                            appContext.sendBroadcast(new Intent("datasender.authFailed"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Basic "+Base64.encodeToString(auth.getBytes(),Base64.NO_WRAP));
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }



    public void getFamilyDetails() {
        final DataModels model = DataModels.getInstance();
        StringRequest request = new StringRequest(Request.Method.GET, familyDetailsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object, familyDetails, member;
                JSONArray members;
                try {
                    object = new JSONObject(response);
                    familyDetails = object.getJSONObject("familyDetails");
                    members = object.getJSONArray("members");
                    for(int i=0; i < members.length(); i++) {
                        member = members.getJSONObject(i);
                        model.add(member);
                    }
                    appContext.sendBroadcast(new Intent("datasender.patientsReceived"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("Authorization",getAuthDetails());
                return map;
            }
        };
    }

    private String getAuthDetails() {
        String credentials = "";
        SharedPreferences pref = appContext.getSharedPreferences(loginActivity.PREFS_NAME, loginActivity.MODE_PRIVATE);
        String auth = pref.getString("auth", null);
        return auth;
    }
}
