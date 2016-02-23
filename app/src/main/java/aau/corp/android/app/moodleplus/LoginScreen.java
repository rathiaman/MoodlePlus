package aau.corp.android.app.moodleplus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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

///////////////////////////////////
// This class is for the login screen
// You need to put in your username and password and on correct credentials, you will be correctly logged in
// This screen also contains extra features like remember password and show password to make it asthetically better and user friendly
///////////////////////////////////


public class LoginScreen extends AppCompatActivity {

    ///////////////////////////////////
    // Declaring variables
    ///////////////////////////////////

    ///////////////////////////////////
    // Variables for different palletes of login screen
    ///////////////////////////////////
    public EditText editText_Username;
    public Button login_button;
    public EditText password_text;
    public EditText ip_address_text;
    public CheckBox show_password, remember_password;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        onButtonClickListener();

        ///////////////////////////////////
        // Declaring ids for the variables made
        ///////////////////////////////////
        show_password = (CheckBox) findViewById(R.id.show_password);
        password_text = (EditText) findViewById(R.id.edit_text_password);

        ///////////////////////////////////
        // Show password option
        // On clicking the checkbox, the password will be immediately converted to the entered text
        // On checked change listenen
        ///////////////////////////////////
        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        remember_password = (CheckBox) findViewById(R.id.checkBox_save_password);

    }


    ///////////////////////////////////
    // This function is for back pressed button
    // If you press back, an alert dialogue box will appear and show Yes and No options
    // On Clicking Yes, you will exit the application
    // On Clicking No, nothing will happen and you will be back at Login Screen
    ///////////////////////////////////
    @Override
    public void onBackPressed() {

        AlertDialog.Builder submit_alert = new AlertDialog.Builder(LoginScreen.this);
        submit_alert.setMessage("Are you sure you want to exit !!!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // calls the function which send the request
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
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

    ///////////////////////////////////
    // This is the Button Click Listener for the login button
    // On clicking the login button, a dialogue box is created which asks for confirmaton
    // If you press No, you are taken back to login screen with respeective details entered
    // If you press yes, server request in sent and if correct credentials are entered, ypu are correctly logged in to your  home screen
    ///////////////////////////////////
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

    ///////////////////////////////////
    //function defined for sending the string request
    ///////////////////////////////////
    private void sendRequest() {

        //creates aDialog box
        final ProgressDialog messageDialog = new ProgressDialog(this);
        messageDialog.setMessage("Logging in");
        messageDialog.show();

        //obtain the string value for username and password field
        /*final String username = ( (EditText) findViewById(R.id.edit_text_username).getText()).toString().trim();
        final String password = findViewById(R.id.edit_text_password).toString().trim();
       */ //flag for sending to the home page
        int flag = 0;

        ip_address_text = (EditText) findViewById(R.id.edit_text_IPAddress);
        final String adder1 = ip_address_text.getText().toString();
      //  Toast.makeText(LoginScreen.this, adder1, Toast.LENGTH_SHORT).show();

        String url="http://" + adder1 + ":8000/default/login.json?userid=cs1110200&password=john";
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

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);

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
                Integer user_id = user.getInt("id");
                Intent in = new Intent(getApplicationContext(),
                        HomeScreen.class);
                in.putExtra("user", user_id);
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
