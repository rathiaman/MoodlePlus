package aau.corp.android.app.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import java.lang.reflect.Field;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;


public class HomeScreen extends AppCompatActivity {
    public Button courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getOverflowMenu();
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }

        onButtonClickListener();
    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onButtonClickListener() {
        courses = (Button) findViewById(R.id.courses);
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent i = new Intent("aau.corp.android.app.moodleplus.Courses");
                Intent j = new Intent(HomeScreen.this, Courses.class);
                startActivity(j);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
}
