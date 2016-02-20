package aau.corp.android.app.moodleplus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;


public class HomeScreen extends AppCompatActivity {
    public Button courses;

    HashMap<String, List<String>> my_courses;
    List<String> courses_list;
    ExpandableListView my_course_list;
    courseAdapter adapter_list;


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

        my_course_list = (ExpandableListView) findViewById(R.id.my_course_expan_list);
        my_courses = Courses_data.getInfo();
        courses_list = new ArrayList<String>(my_courses.keySet());
        adapter_list = new courseAdapter(this, my_courses, courses_list);
        my_course_list.setAdapter(adapter_list);
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

