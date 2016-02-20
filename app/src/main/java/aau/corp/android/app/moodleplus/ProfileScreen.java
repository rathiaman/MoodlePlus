package aau.corp.android.app.moodleplus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

public class ProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting the values
        String first_name=  getIntent().getExtras().getString("EXTRA_FIRST").toString();
        String last_name=   getIntent().getExtras().getString("EXTRA_LAST").toString();
        String entry_number=getIntent().getExtras().getString("EXTRA_ENTRY").toString();
        String email=       getIntent().getExtras().getString("EXTRA_EMAIL").toString();
        String int_type=    getIntent().getExtras().getString("EXTRA_TYPE").toString();

        Toast.makeText(ProfileScreen.this, "hehe", Toast.LENGTH_SHORT).show();
        show_profile_detail(first_name,last_name,entry_number,email,int_type);
    }

    public void show_profile_detail(String first_name, String last_name, String entry_number, String email, String int_type){

    }

}
