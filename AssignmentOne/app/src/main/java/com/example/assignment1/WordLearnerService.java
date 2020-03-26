package com.example.assignment1;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment1.Database.WordObjectApplication;
import com.example.assignment1.Database.WordObjectDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordLearnerService extends Service
{
    private static final String LOG = "WordLearnerService";
    private static final String LOGParse = "ParseJSONToWordObejct";
    private RequestQueue requestQueue;
    private WordObjectDatabase wordObjectDatabase;
    private final IBinder binder = new WordLearnerServiceBinder();

    @Override
    public void onCreate()
    {
        super.onCreate();
        wordObjectDatabase = ((WordObjectApplication)getApplicationContext()).getDb();
    }

    public class WordLearnerServiceBinder extends Binder
    {
         public WordLearnerService getService()
         {
             return WordLearnerService.this;
         }
    }

    private void sendRequest(final String word)
    {
        //send request using Volley
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(this);
        }
        String url = "https://owlbot.info/api/v4/dictionary/" + word;

       JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        WordObject newWord = parseJSONToWordObject(response);
                        wordObjectDatabase.wordObjectDAO().add(newWord);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG, "Failed to fetch words from API request");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token fef073a9f1aafd3a756e36c1e7f68f80a147cfe8");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private WordObject parseJSONToWordObject(JSONObject jsonString)
    {
        Gson gson = new GsonBuilder().create();
        WordObject wordObject = gson.fromJson(jsonString.toString(), WordObject.class);
        Log.d(LOGParse, "Parsed Successfully");
        return wordObject;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public List<WordObject> getAllWords()
    {
        return ((WordObjectApplication)getApplicationContext()).getDb().wordObjectDAO().getAll();
    }

    public void addWord(String newWord)
    {
        sendRequest(newWord);
        //wordObjectDatabase.wordObjectDAO().add(wordObject);
    }

    public void getWord(String word)
    {
        ((WordObjectApplication)getApplicationContext()).getDb().wordObjectDAO().getWord(word);
    }

    public void deleteWord(WordObject deleteWordObject)
    {
        ((WordObjectApplication)getApplicationContext()).getDb().wordObjectDAO().delete(deleteWordObject);
    }

    public void updateWord(WordObject updateWordObject)
    {
        ((WordObjectApplication)getApplicationContext()).getDb().wordObjectDAO().update(updateWordObject);
    }

}
