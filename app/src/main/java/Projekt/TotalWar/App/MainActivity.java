package Projekt.TotalWar.App;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ApiCallHelper apiCallHelper;
    private EditText factionIdEditText;
    private Button getFactionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiCallHelper = new ApiCallHelper(this);
        factionIdEditText = findViewById(R.id.myEditText);
        getFactionButton = findViewById(R.id.myButton);

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
                            Log.i(TAG, "GET Request Response: " + result);
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
    }
}