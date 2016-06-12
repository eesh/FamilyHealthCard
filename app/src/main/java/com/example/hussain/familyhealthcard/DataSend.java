package com.example.hussain.familyhealthcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
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
import java.util.Map;
import java.util.Set;


public class DataSend {

    private static String addPatientURL = "http://srmmedicalproject.net23.net/apis/addPatient.php";
    private static String updatePatientURL = "http://srmmedicalproject.net23.net/apis/updatePatient.php";
    private static String deletePatientURL = "http://srmmedicalproject.net23.net/apis/deletePatient.php";


    private static String addFamilyURL = "http://srmmedicalproject.net23.net/apis/addFamily.php";
    private static String updateFamilyURL = "http://srmmedicalproject.net23.net/apis/updateFamily.php";


    private static String addConsultURL = "http://srmmedicalproject.net23.net/apis/addConsult.php";
    private static String viewConsultURL = "http://srmmedicalproject.net23.net/apis/viewConsult.php";
    private static String updateConsultURL = "http://srmmedicalproject.net23.net/apis/updateConsul.php";
    private static String deleteConsultURL = "http://srmmedicalproject.net23.net/apis/deleteConsult.php";

    private static String familyDetailsURL = "http://srmmedicalproject.net23.net/apis/getFamilyDetails.php";


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
            object.put("familyID",bundle.get("familyID"));
            object.put("name",bundle.get("name"));
            object.put("age",bundle.get("age"));
            object.put("relation",bundle.get("relation"));
            object.put("martialStatus",bundle.get("martialStatus"));
            object.put("education",bundle.get("education"));
            object.put("occupation",bundle.get("occupation"));
            object.put("income",bundle.get("income"));
            object.put("religion",bundle.get("religion"));
            object.put("houseID",bundle.get("houseID"));
            object.put("sex", bundle.get("sex"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, addPatientURL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
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


    public void updatePatient(Bundle bundle) {
        final JSONObject object = new JSONObject();
        Set<String> fields = bundle.keySet();
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
                Log.e("response", response.toString());
                try {
                    boolean failed = object.getBoolean("error");
                    if(!failed) {
                        appContext.sendBroadcast(new Intent("datasend.patientUpdateSuccess"));
                    } else appContext.sendBroadcast(new Intent("datasend.patientUpdateFailed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    appContext.sendBroadcast(new Intent("datasend.patientUpdateFailed"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                appContext.sendBroadcast(new Intent("datasend.patientUpdateFailed"));
            }
        });
        Log.e("request",request.toString());
        queue.add(request);
    }


    public void deletePatient(int patientID) {
        JSONObject object = new JSONObject();
        try {
            object.put("patientID", patientID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, deletePatientURL, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", response.toString());
                        try {
                            boolean failed = response.getBoolean("error");
                            if(!failed) {
                                appContext.sendBroadcast(new Intent("datasend.deletePatientSuccess"));
                            } else appContext.sendBroadcast(new Intent("datasend.deletePatientFailed"));
                        } catch(JSONException e) {
                            e.printStackTrace();
                            appContext.sendBroadcast(new Intent("datasend.deletePatientFailed"));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                        appContext.sendBroadcast(new Intent("datasend.deletePatientFailed"));
                    }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Authorization", "Basic " + getAuthDetails());
                return map;
            }
        };

        queue.add(request);
    }


    public void createFamily(Bundle bundle)  {
        final JSONObject object = new JSONObject();
        try {
            object.put("place",bundle.get("place"));
            object.put("area",bundle.get("area"));
            object.put("street",bundle.get("street"));
            object.put("hno",bundle.get("hno"));
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
                if(response != null) {
                    try {
                        if(response.getBoolean("success")) {
                            appContext.sendBroadcast(new Intent("datasend.createFSuccess"));
                        } else {
                            appContext.sendBroadcast(new Intent("datasend.createFFailed"));
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


    public void updateFamily(Bundle bundle) {
        JSONObject object = new JSONObject();
        Set<String> fields = bundle.keySet();
        try {
            for (String field:fields) {
                object.put(field, bundle.get(field));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest request = new JsonObjectRequest(Request.Method.POST, updateFamilyURL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", response.toString());
                try {
                    boolean failed = response.getBoolean("error");
                    if(!failed) {
                        appContext.sendBroadcast(new Intent("datasend.updateFamilySuccess"));
                    } else appContext.sendBroadcast(new Intent("datasend.updateFamilyFailed"));
                } catch(JSONException e) {
                    e.printStackTrace();
                    appContext.sendBroadcast(new Intent("datasend.updateFamilyFailed"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("Authorization","Basic " + getAuthDetails());
                return map;
            }
        };
        Log.e("request",request.toString());
        queue.add(request);
    }


    public void getFamilyDetails(int place, int area, int street, int house) {
        String url = familyDetailsURL + "?place="+place+"&area="+area+"&street="+street+"&house="+house;
        Log.e("url",url);
        final DataModels model = DataModels.getInstance();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                JSONObject object, familyDetails, member;
                JSONArray members;
                try {
                    object = new JSONObject(response);
                    if(object.getBoolean("found")) {
                        familyDetails = object.getJSONObject("familyDetails");
                        model.setFamily(familyDetails);
                        members = object.getJSONArray("members");
                        for (int i = 0; i < members.length(); i++) {
                            member = members.getJSONObject(i);
                            model.add(member);
                        }
                        appContext.sendBroadcast(new Intent("datasend.familyFound"));
                    } else appContext.sendBroadcast(new Intent("datasend.familyNotFound"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                appContext.sendBroadcast(new Intent("datasend.familyNotFound"));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("Authorization","Basic " + getAuthDetails());
                return map;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }


    public void addConsultation(int patientID, Bundle bundle) {
        JSONObject object = new JSONObject();
        Set<String> fields = bundle.keySet();
        try {
            for (String field:fields) {
                object.put(field, bundle.get(field));
            }
            object.put("patientID", patientID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON", object.toString());
        JsonRequest request = new JsonObjectRequest(Request.Method.POST, addConsultURL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", response.toString());
                try {
                    boolean failed = response.getBoolean("error");
                    if(!failed) {
                        appContext.sendBroadcast(new Intent("datasend.addConsultSuccess"));
                    } else appContext.sendBroadcast(new Intent("datasend.addConsultFailed"));
                } catch(JSONException e) {
                    e.printStackTrace();
                    appContext.sendBroadcast(new Intent("datasend.addConsultFailed"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("Authorization","Basic " + getAuthDetails());
                return map;
            }
        };
        Log.e("request",request.toString());
        queue.add(request);
    }


    public void getConsultations(final int patientID) {
        String url = viewConsultURL + "?patientID="+patientID;
        final DataModels model = DataModels.getInstance();
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object, consultationJSON;
                JSONArray consultationsArray;
                Patient patient = model.getPatientFromID(patientID);
                try {
                    object = new JSONObject(response);
                    if(object.getBoolean("found")) {

                        consultationsArray = object.getJSONArray("consultations");
                        for (int i = 0; i < consultationsArray.length(); i++) {
                            consultationJSON = consultationsArray.getJSONObject(i);
                            patient.addConsultation(new Consultation(consultationJSON));
                        }
                        appContext.sendBroadcast(new Intent("datasend.consultationsFound"));
                    } else appContext.sendBroadcast(new Intent("datasend.consultationsNotFound"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                appContext.sendBroadcast(new Intent("datasend.consultationsNotFound"));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("Authorization","Basic " + getAuthDetails());
                return map;
            }
        };

        queue.add(request);
    }


    public void updateConsultation(int consulationID, Bundle bundle) {
        JSONObject object = new JSONObject();
        Set<String> fields = bundle.keySet();
        try {
            for (String field:fields) {
                object.put(field, bundle.get(field));
            }
            object.put("cID", consulationID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest request = new JsonObjectRequest(Request.Method.POST, updateConsultURL, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", response.toString());
                try {
                    boolean failed = response.getBoolean("error");
                    if(!failed) {
                        appContext.sendBroadcast(new Intent("datasend.updateConsultSuccess"));
                    } else appContext.sendBroadcast(new Intent("datasend.updateConsultFailed"));
                } catch(JSONException e) {
                    e.printStackTrace();
                    appContext.sendBroadcast(new Intent("datasend.updateConsultFailed"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("Authorization","Basic " + getAuthDetails());
                return map;
            }
        };
        Log.e("request",request.toString());
        queue.add(request);
    }


    public void deleteConsultation(int consultationID) {
        StringRequest request = new StringRequest(Request.Method.GET, deleteConsultURL+"?cID="+consultationID, new Response.Listener<String>() {
            @Override
            public void onResponse(String stringResponse) {

                Log.e("response", stringResponse.toString());
                try {
                    JSONObject response = new JSONObject(stringResponse);
                    boolean failed = response.getBoolean("error");
                    if(!failed) {
                        appContext.sendBroadcast(new Intent("datasend.deleteConsultSuccess"));
                    } else appContext.sendBroadcast(new Intent("datasend.deleteConsultFailed"));
                } catch(JSONException e) {
                    e.printStackTrace();
                    appContext.sendBroadcast(new Intent("datasend.deleteConsultFailed"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String > map = new HashMap<>();
                map.put("Authorization","Basic " + getAuthDetails());
                return map;
            }
        };
        Log.e("request",request.toString());
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
                if(error instanceof NoConnectionError) {
                    appContext.sendBroadcast(new Intent("datasender.noConnection"));
                }
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


    private String getAuthDetails() {
        String credentials = "";
        SharedPreferences pref = appContext.getSharedPreferences(loginActivity.PREFS_NAME, loginActivity.MODE_PRIVATE);
        return pref.getString("auth", null);
    }
}
