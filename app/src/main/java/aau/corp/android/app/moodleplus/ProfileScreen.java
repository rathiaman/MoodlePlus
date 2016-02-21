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

public class ProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        //getting the value for the username
        Integer user_id=  getIntent().getExtras().getInt("user");

        send_data_request(user_id);

    }

    public void send_data_request( int user_id){
        //url for grades
        String url="http://10.192.18.219:8000//users/user.json/"+String.valueOf(user_id);

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void show_user_data(String response){

        try {
            JSONObject user = new JSONObject(response);
            JSONObject user_detail = user.getJSONObject("user");

            TextView first_name_answer, last_name_answer, entry_no_answer, email_address_answer, account_type_answer;

            first_name_answer = (TextView) findViewById(R.id.first_name_answer);
            last_name_answer = (TextView) findViewById(R.id.last_name_answer);
            entry_no_answer = (TextView) findViewById(R.id.entry_no_answer);
            email_address_answer = (TextView) findViewById(R.id.email_address_answer);
            account_type_answer = (TextView) findViewById(R.id.account_type_answer);

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