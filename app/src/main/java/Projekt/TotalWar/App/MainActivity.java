package Projekt.TotalWar.App;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ApiCallHelper apiCallHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiCallHelper = new ApiCallHelper(this);

        // Set up buttons for each API call
        Button getButton = findViewById(R.id.getButton);
        Button postButton = findViewById(R.id.postButton);
        Button putButton = findViewById(R.id.putButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        // Set up EditText fields for input
        EditText factionNameEditText = findViewById(R.id.factionNameEditText);
        EditText updatedFactionNameEditText = findViewById(R.id.updatedFactionNameEditText);

        // Set up click listeners for each button
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Execute GET request when the GET button is clicked
                String apiUrlGet = "http://10.131.213.178:8080/factions";
                apiCallHelper.makeGetRequest(apiUrlGet, new ApiCallHelper.ApiCallback() {
                    @Override
                    public void onApiCompleted(String result) {
                        // Handle the GET API response here
                        Log.d(TAG, "GET Request Response: " + result);

                        // Parse JSON using Gson
                        Gson gson = new Gson();
                        YourDataClass data = gson.fromJson(result, YourDataClass.class);

                        // Now you can use 'data' object as needed
                        Log.d(TAG, "Parsed Data: " + data.toString());
                    }

                    @Override
                    public void onApiError(String error) {
                        // Handle GET API error
                        Log.e(TAG, "GET Request Error: " + error);
                    }
                });
            }
        });


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Execute POST request when the POST button is clicked
                String apiUrlPost = "http://10.131.213.178:8080/factions/";
                JSONObject postRequestBody = new JSONObject();
                try {
                    String factionName = factionNameEditText.getText().toString();
                    postRequestBody.put("factionName", factionName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                apiCallHelper.makePostRequest(apiUrlPost, postRequestBody, new ApiCallHelper.ApiCallback() {
                    @Override
                    public void onApiCompleted(String result) {
                        // Handle the POST API response here
                        Log.d(TAG, "POST Request Response: " + result);
                    }

                    @Override
                    public void onApiError(String error) {
                        // Handle POST API error
                        Log.e(TAG, "POST Request Error: " + error);
                    }
                });
            }
        });

        putButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Execute PUT request when the PUT button is clicked
                String apiUrlPut = "http://10.131.213.178:8080/factions/{factionId}";
                JSONObject putRequestBody = new JSONObject();
                try {
                    String updatedFactionName = updatedFactionNameEditText.getText().toString();
                    putRequestBody.put("factionName", updatedFactionName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                apiCallHelper.makePutRequest(apiUrlPut, putRequestBody, new ApiCallHelper.ApiCallback() {
                    @Override
                    public void onApiCompleted(String result) {
                        // Handle the PUT API response here
                        Log.d(TAG, "PUT Request Response: " + result);
                    }

                    @Override
                    public void onApiError(String error) {
                        // Handle PUT API error
                        Log.e(TAG, "PUT Request Error: " + error);
                    }
                });
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Execute DELETE request when the DELETE button is clicked
                String apiUrlDelete = "http://10.131.213.178:8080/factions/{factionId}";

                apiCallHelper.makeDeleteRequest(apiUrlDelete, new ApiCallHelper.ApiCallback() {
                    @Override
                    public void onApiCompleted(String result) {
                        // Handle the DELETE API response here
                        Log.d(TAG, "DELETE Request Response: " + result);
                    }

                    @Override
                    public void onApiError(String error) {
                        // Handle DELETE API error
                        Log.e(TAG, "DELETE Request Error: " + error);
                    }
                });
            }
        });
    }
}