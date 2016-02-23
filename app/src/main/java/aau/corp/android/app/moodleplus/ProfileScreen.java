package aau.corp.android.app.moodleplus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONStringer;


///////////////////////////////////
// This class is for the user profile screen
// This class will open when user taps on the profile option at the 3-dot menu item in Home screen
// Contains data of the user like first name, last name, entry number, email address, account type
///////////////////////////////////

public class ProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        ///////////////////////////////////
        //getting the value for the username
        ///////////////////////////////////
        Integer user_id=  getIntent().getExtras().getInt("user");

        ///////////////////////////////////
        // Calling the defined function send data request
        ///////////////////////////////////
        send_data_request(user_id);

    }

    ///////////////////////////////////
    // This function sends the to the server
    ///////////////////////////////////
    public void send_data_request( int user_id){
        //url for grades
        String adder1 = IPAddress.getName();
        String url="http://" + adder1 + "//users/user.json/"+String.valueOf(user_id);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response);
                        try {
                            Log.e("qwerty", response.toString());
                            //mainObject = new JSONObject(response);
                            show_user_data(response);
                            //Toast.makeText(GradeScreen.this, response, Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            Log.e("u1" , e.toString());
                            e.printStackTrace();
                            Toast.makeText(ProfileScreen.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("u2" , error.toString());
                        //Toast.makeText(LoginScreen.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(ProfileScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        ///////////////////////////////////
        // Get a RequestQueue
        ///////////////////////////////////
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    ///////////////////////////////////
    // Extracting user data via JSON parsing
    ///////////////////////////////////
    public void show_user_data(String response){

        try {
            JSONObject user = new JSONObject(response);
            JSONObject user_detail = user.getJSONObject("user");

            TextView first_name_answer, last_name_answer, entry_no_answer, email_address_answer, account_type_answer;

            ///////////////////////////////////
            // Defining variables
            ///////////////////////////////////
            first_name_answer = (TextView) findViewById(R.id.first_name_answer);
            last_name_answer = (TextView) findViewById(R.id.last_name_answer);
            entry_no_answer = (TextView) findViewById(R.id.entry_no_answer);
            email_address_answer = (TextView) findViewById(R.id.email_address_answer);
            account_type_answer = (TextView) findViewById(R.id.account_type_answer);

            ///////////////////////////////////
            // Setting values of variables
            ///////////////////////////////////
            first_name_answer.setText(user_detail.getString("first_name"));
            last_name_answer.setText(user_detail.getString("last_name"));
            entry_no_answer.setText(user_detail.getString("entry_no"));
            email_address_answer.setText(user_detail.getString("email"));
            int user_type = user_detail.getInt("type_");

            if(user_type == 0){
                account_type_answer.setText("Student");
            }
            if (user_type == 1){
                account_type_answer.setText("Faculty");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}