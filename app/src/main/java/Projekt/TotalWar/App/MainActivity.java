package Projekt.TotalWar.App;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ApiCallHelper apiCallHelper;
    private EditText factionIdEditText;
    private Button getFactionButton, getUpdateDataButton, getPostDataButton, getDeleteDataButton;
    private EditText getEditTextView;

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

        // Example usage for a GET request
        // https://stackoverflow.com/a/54810907
        String apiUrlGet = "http://10.0.2.2:8080/factions";
        apiCallHelper.makeGetRequest(apiUrlGet, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.i(TAG, "GET Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "GET Request Error: " + error);
            }
        });

        // Example usage for a POST request
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
        });
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
                        }

                        @Override
                        public void onApiError(String error) {
                            // Handle API error
                            Log.e(TAG, "PUT Request Error: " + error);
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
                            Log.i(TAG, "DELETE Request Response: " + result);
                            Toast.makeText(MainActivity.this, "POSTED new data" + result,
                                    Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onApiError(String error) {
                            // Handle API error
                            Log.e(TAG, "DELETE Request Error: " + error);
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

                // Construct the API URL for the PUT request
                String apiUrlDelete = "http://10.0.2.2:8080/factions/" + factionId;

                // Make a PUT request to update the data
                apiCallHelper.makeDeleteRequest(apiUrlDelete, new ApiCallHelper.ApiCallback() {
                    @Override
                    public void onApiCompleted(String result) {
                        // Handle the API response here
                        Log.i(TAG, "POST Request Response: " + result);
                        Toast.makeText(MainActivity.this, "DELTED ID: "+
                                factionIdEditText, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onApiError(String error) {
                        // Handle API error
                        Log.e(TAG, "POST Request Error: " + error);
                    }
                });
            }
        });
    }
    // Validate JSON format
}