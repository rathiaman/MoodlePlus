package aau.corp.android.app.moodleplus;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;


public class LoginScreen extends AppCompatActivity {

  //  public EditText editText_Username, editText_Password;
        public Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        onButtonClickListener();
       /* editText_Username = (EditText) findViewById(R.id.editText_Username);
        editText_Password = (EditText) findViewById(R.id.editText_Password);
*/
    }

    // function for back button
    // On back press, a toast message is displayed to press back button again
    // If you press again the back button within time, you end the application
    private boolean PressTwice = false;
    @Override
    public void onBackPressed(){
        if(PressTwice){

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);

        }

        PressTwice = true;
        Toast.makeText(this, "Press BACK Button again to Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PressTwice = false;
            }
        }, 2000);
    }


    // This is the Button Click Listener for the login button
    // On clicking the login button, a dialogue box is created which asks for confirmaton
    // If you press No, you are taken back to login screen with respeective details entered
    // If you press yes, server request in sent and if correct credentials are entered, ypu are correctly logged in to your  home screen
    public void onButtonClickListener() {
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // calls the alert dialogue box
                AlertDialog.Builder submit_alert = new AlertDialog.Builder(LoginScreen.this);
                submit_alert.setMessage("Confirm your details !!!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // calls the function which send the request to the server
                        sendRequest();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {    // If no is pressed, you are taken back to the login screen
                        dialog.cancel();
                    }
                });

                AlertDialog alert = submit_alert.create();
                alert.setTitle("Alert !!!");
                alert.show();


            }
        });
 }



    //function defined for sending the string request
    private void sendRequest() {

        //creates aDialog box
        final ProgressDialog messageDialog = new ProgressDialog(this);
        messageDialog.setMessage("sending the information");
        messageDialog.show();

        //obtain the string value for username and password field
        final String username = findViewById(R.id.edit_text_username).toString().trim();
        final String password = findViewById(R.id.edit_text_password).toString().trim();
        //flag for sending to the home page
        int flag = 0;

        String url="http://10.192.18.219:8000/default/login.json?userid=cs1110200&password=john";
       // String url="http://tapi.cse.iitd.ernet.in:1805/default/login.json?userid=" + username + "&password=" + password;
        //   String url = "http://10.192.7.98:8000/default/login.json?userid=" + username + "&password=" + password;
        //String url = "http://headers.jsontest.com/";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response.toString());
                        Toast.makeText(LoginScreen.this, "request sent", Toast.LENGTH_SHORT).show();
                        PJson(response);
                        /*
                        int flag = PJson(response);
                        if (flag == 1) {
                            Intent i = new Intent(LoginScreen.this, HomeScreen.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginScreen.this, "incorrect fields", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginScreen.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        //for handling cookies
        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
    }

    private void PJson(String res) {
        int result = 0;
        try {
            JSONObject Object = new JSONObject(res);
            Boolean success_value = Object.getBoolean("success");
            if (!success_value)
                result = 0;

            else {
                result =1 ;
            }
            if (result == 1) {
                // Toast.makeText(MainActivity.this, myinteger, Toast.LENGTH_SHORT).show();
                JSONObject user = Object.getJSONObject("user");
                String first_name = user.getString("first_name");
                String last_name = user.getString("last_name");
                String entry_no = user.getString("entry_no");
                String email = user.getString("email");
                String type_ = user.getString("type_");

                Intent in = new Intent(getApplicationContext(),
                        HomeScreen.class);
                //in.putExtra(EXTRA_MESSAGE,)
                Bundle extras = new Bundle();
                extras.putString("EXTRA_FIRST", first_name);
                extras.putString("EXTRA_LAST", last_name);
                extras.putString("EXTRA_ENTRY", entry_no);
                extras.putString("EXTRA_EMAIL", email);
                extras.putString("EXTRA_TYPE", type_);
                in.putExtras(extras);
                startActivity(in);

            }
            else{
                Toast.makeText(LoginScreen.this, "invalid login", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            result = 0;
        }
    }

    }
