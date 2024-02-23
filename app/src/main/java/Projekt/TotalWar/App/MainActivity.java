package Projekt.TotalWar.App;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ApiCallHelper apiCallHelper;
    private EditText factionIdEditText;
    private Button getFactionButton, getUpdateDataButton, getPostDataButton, getDeleteDataButton;
    private EditText getEditTextView;
    private ListView getFactionListView;
    // Declare the adapter globally
    private FactionListAdapter adapter;

    private ArrayList<FactionModel> parseJsonResponse(String jsonResponse) {
        ArrayList<FactionModel> factionList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Long factionId = jsonObject.getLong("factionId");
                String factionName = jsonObject.getString("factionName");
                // Parse other attributes as needed

                // Create a FactionModel object and add it to the list
                FactionModel factionModel = new FactionModel();
                factionModel.setFactionId(factionId);
                factionModel.setFactionName(factionName);
                // Set other attributes as needed
                factionList.add(factionModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return factionList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiCallHelper = new ApiCallHelper(this);
        factionIdEditText = findViewById(R.id.myEditText);
        getFactionButton = findViewById(R.id.myButton);
        getEditTextView = findViewById(R.id.et_data);
        getUpdateDataButton = findViewById(R.id.btn_updateRequest);
        getPostDataButton = findViewById(R.id.btn_postRequest);
        getDeleteDataButton = findViewById(R.id.btn_deleteRequest);
        getFactionListView = findViewById(R.id.lv_factions);

        // Initialize the adapter
        adapter = new FactionListAdapter(getApplicationContext(), new ArrayList<>());
        getFactionListView.setAdapter(adapter);

        // Example usage for a GET request
        // https://stackoverflow.com/a/54810907
        String apiUrlGet = "http://10.0.2.2:8080/factions";
        apiCallHelper.makeGetRequest(apiUrlGet, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                // Parse the JSON response to extract FactionModel data
                ArrayList<FactionModel> factionList = parseJsonResponse(result);

                // Update the adapter data and notify the adapter
                adapter.clear();
                // Add each item from the parsed list to the adapter
                for (FactionModel factionModel : factionList) {
                    adapter.addItem(factionModel);
                }
                adapter.notifyDataSetChanged();

                Log.i(TAG, "GET ALL Request Response: " + result);

            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "GET ALL Request Error: " + error);
            }
        });

/*        // Example usage for a POST request
        // https://stackoverflow.com/a/54810907
        String apiUrlPost = "http://10.0.2.2:8080/factions/";
        JSONObject postRequestBody = new JSONObject();
        try {
            postRequestBody.put("factionName", "Empire"); // Replace "Empire" with the actual value of factionName
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiCallHelper.makePostRequest(apiUrlPost, postRequestBody, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.i(TAG, "POST Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "POST Request Error: " + error);
            }
        });

        // Example usage for a PUT request
        // https://stackoverflow.com/a/54810907
        String apiUrlPut = "http://10.0.2.2:8080/factions/{factionId}";
        JSONObject putRequestBody = new JSONObject();
        try {
            putRequestBody.put("factionName", "UpdatedEmpire"); // Replace "UpdatedEmpire" with the new value of factionName
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiCallHelper.makePutRequest(apiUrlPut, putRequestBody, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.i(TAG, "PUT Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "PUT Request Error: " + error);
            }
        });

        // Example usage for a DELETE request
        // https://stackoverflow.com/a/54810907
        String apiUrlDelete = "http://10.0.2.2:8080/factions/{factionId}";

        apiCallHelper.makeDeleteRequest(apiUrlDelete, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.i(TAG, "DELETE Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "DELETE Request Error: " + error);
            }
        });*/

        getFactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String factionId = factionIdEditText.getText().toString().trim();
                if (!factionId.isEmpty()) {
                    // https://stackoverflow.com/a/54810907
                    String apiUrlGet = "http://10.0.2.2:8080/factions/" + factionId;
                    apiCallHelper.makeGetRequest(apiUrlGet, new ApiCallHelper.ApiCallback() {
                        @Override
                        public void onApiCompleted(String result) {
                            // Handle the API response here
                            // Convert the response string to a JSONObject
                            Log.i(TAG, "GET Request Response: " + result);
                            Toast.makeText(MainActivity.this, "GET RESPONSE: "+
                                            result,
                                    Toast.LENGTH_LONG).show();
                            try {
                                // Convert the response string to a JSONObject
                                JSONObject responseObject = new JSONObject(result);

                                // Get the value of the specific field from the JSONObject
                                String fieldValue = responseObject.optString("factionName");

                                // Display the extracted field value in a TextView or Toast
                                // For example, if you have a TextView with id 'textView'
                                getEditTextView.setText(fieldValue);
                            } catch (JSONException e) {
                                // Handle JSON parsing error
                                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                Toast.makeText(MainActivity.this, "ERROR: "+
                                                e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onApiError(String error) {
                            // Handle API error
                            Log.e(TAG, "GET Request Error: " + error);
                            Toast.makeText(MainActivity.this, "GET Request Error:" + error,
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    // Handle empty input error
                    Log.e(TAG, "Faction ID is empty");
                }
            }
        });
        getUpdateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new data from the EditText
                String newData = getEditTextView.getText().toString();

                // Check if newData is not empty and is in valid JSON format
                if (!newData.isEmpty()) {
                    // Convert the newData string to a JSONObject
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("factionName", newData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // Get the faction ID from the factionIdEditText
                    String factionId = factionIdEditText.getText().toString().trim();

                    // Construct the API URL for the PUT request
                    String apiUrlPut = "http://10.0.2.2:8080/factions/" + factionId;

                    // Make a PUT request to update the data
                    apiCallHelper.makePutRequest(apiUrlPut, jsonObject, new ApiCallHelper.ApiCallback() {
                        @Override
                        public void onApiCompleted(String result) {
                            // Handle the API response here
                            Log.i(TAG, "PUT Request Response: " + result);
                            Toast.makeText(MainActivity.this, "UPDATED ID: "+
                                    factionIdEditText.getText().toString() + " to: " + jsonObject.optString("factionName"),
                                    Toast.LENGTH_LONG).show();
                            try {
                                // Parse the JSON response to extract the updated faction data
                                JSONObject updatedFactionObject = new JSONObject(result);
                                Long factionId = updatedFactionObject.getLong("factionId");
                                String factionName = updatedFactionObject.getString("factionName");

                                // Find the corresponding FactionModel in the adapter's data list and update it
                                for (FactionModel factionModel : adapter.getFactionList()) {
                                    if (factionModel.getFactionId().equals(factionId)) {
                                        // Update the faction name
                                        factionModel.setFactionName(factionName);
                                        break; // Break the loop once the item is found and updated
                                    }
                                }

                                // Notify the adapter that the data set has changed
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                // Handle JSON parsing error
                                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                // Show error message to the user
                                Toast.makeText(MainActivity.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onApiError(String error) {
                            // Handle API error
                            Log.e(TAG, "PUT Request Error: " + error);
                            Toast.makeText(MainActivity.this,"PUT Request Error: " + error,Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    // Inform the user about invalid input format
                    Toast.makeText(MainActivity.this, "Invalid JSON format", Toast.LENGTH_LONG).show();
                }
            }
        });
        getPostDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new data from the EditText
                String newData = getEditTextView.getText().toString();

                // Check if newData is not empty and is in valid JSON format
                if (!newData.isEmpty()) {
                    // Convert the newData string to a JSONObject
                    JSONObject postRequestBody = new JSONObject();
                    try {
                        postRequestBody.put("factionName", newData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    Log.i(TAG, postRequestBody.toString());

                    // Construct the API URL for the PUT request
                    String apiUrlPost = "http://10.0.2.2:8080/factions";

                    // Make a PUT request to update the data
                    apiCallHelper.makePostRequest(apiUrlPost, postRequestBody, new ApiCallHelper.ApiCallback() {
                        @Override
                        public void onApiCompleted(String result) {
                            // Handle the API response here
                            Log.i(TAG, "POST Request Response: " + result);
                            Toast.makeText(MainActivity.this, "POSTED new data" + result,
                                    Toast.LENGTH_LONG).show();
                            try {
                                // Parse the JSON response to extract the newly added faction data
                                JSONObject newFactionObject = new JSONObject(result);
                                Long factionId = newFactionObject.getLong("factionId");
                                String factionName = newFactionObject.getString("factionName");

                                // Create a new FactionModel object for the newly added faction
                                FactionModel newFaction = new FactionModel();
                                newFaction.setFactionId(factionId);
                                newFaction.setFactionName(factionName);

                                // Add the new faction to the adapter
                                adapter.addItem(newFaction);

                                // Notify the adapter that the data set has changed
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                // Handle JSON parsing error
                                Log.e(TAG, "JSON parsing error: " + e.getMessage());
                                // Show error message to the user
                                Toast.makeText(MainActivity.this, "Error parsing JSON: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onApiError(String error) {
                            // Handle API error
                            Log.e(TAG, "DELETE Request Error: " + error);
                            Toast.makeText(MainActivity.this,"DELETE Request Error: " + error,Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    // Inform the user about invalid input format
                    Toast.makeText(MainActivity.this, "Invalid JSON format",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        getDeleteDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new data from the EditText
                String factionId = factionIdEditText.getText().toString().trim();

                // Construct the API URL for the DELETE request
                String apiUrlDelete = "http://10.0.2.2:8080/factions/" + factionId;

                // Make a DELETE request to delete the data
                apiCallHelper.makeDeleteRequest(apiUrlDelete, new ApiCallHelper.ApiCallback() {
                    @Override
                    public void onApiCompleted(String result) {
                        // Handle the API response here
                        Log.i(TAG, "DELETE Request Response: " + result);

                        // Remove the deleted item from the adapter's data source
                        for (int i = 0; i < adapter.getCount(); i++) {
                            FactionModel faction = (FactionModel) adapter.getItem(i);
                            if (faction.getFactionId().toString().equals(factionId)) {
                                adapter.removeAt(i);
                                break; // Exit loop once item is removed
                            }
                        }

                        // Notify the adapter that the data set has changed
                        adapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "DELETED ID: " + factionId, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onApiError(String error) {
                        // Handle API error
                        Log.e(TAG, "DELETE Request Error: " + error);
                        Toast.makeText(MainActivity.this, "DELETE Request Error:" + error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    // Validate JSON format
}