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

public class ProfileScreen extends AppCompatActivity {

    TextView first_name_answer, last_name_answer, entry_no_answer, email_address_answer, account_type_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        first_name_answer = (TextView) findViewById(R.id.first_name_answer);
        last_name_answer = (TextView) findViewById(R.id.last_name_answer);
        entry_no_answer = (TextView) findViewById(R.id.entry_no_answer);
        email_address_answer = (TextView) findViewById(R.id.email_address_answer);
        account_type_answer = (TextView) findViewById(R.id.account_type_answer);




        //getting the values
        String first_name=  getIntent().getExtras().getString("EXTRA_FIRST").toString();
        String last_name=   getIntent().getExtras().getString("EXTRA_LAST").toString();
        String entry_number=getIntent().getExtras().getString("EXTRA_ENTRY").toString();
        String email=       getIntent().getExtras().getString("EXTRA_EMAIL").toString();
        String int_type=    getIntent().getExtras().getString("EXTRA_TYPE").toString();

        first_name_answer.setText(first_name);
        last_name_answer.setText(last_name);
        entry_no_answer.setText(entry_number);
        email_address_answer.setText(email);

        if(Integer.parseInt(int_type) == 0){
            account_type_answer.setText("Student");
        }
        if (Integer.parseInt(int_type) == 1){
            account_type_answer.setText("Faculty");
        }

        Toast.makeText(ProfileScreen.this, "hehe", Toast.LENGTH_SHORT).show();
       // show_profile_detail(first_name,last_name,entry_number,email,int_type);
    }


}
