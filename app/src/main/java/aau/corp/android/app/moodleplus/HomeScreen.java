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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;


// This is the home screen when you are logged in correctly
// This is screen displays your notifications, assignments, grades in various subjects as well as your courses

// to show your courses, a dropdown list has been enabled which on click opens and show your registered courses
// Notifications, grades and assignments takes you to the new activity of the respective options

public class HomeScreen extends AppCompatActivity
     //   implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener
{

    public Button to_grades;


    // This creates a list for the courses and list opens up if you tap on it
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
        onButtonClickListener_grades();
      //  onButtonClickListener();


        my_course_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent transition = new Intent(HomeScreen.this, Courses.class);
                startActivity(transition);
                return false;
            }
        });

    }


    private void onButtonClickListener_grades() {
        to_grades = (Button) findViewById(R.id.to_grades);
        to_grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent grade_page = new Intent(HomeScreen.this, GradeScreen.class);
                startActivity(grade_page);
            }
        });
    }


    // This function is for the three dots button where a small menu open up which displays several items like your name, entry number, profile, signout optons

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




    // This functions inflate the 3 dot menu button with items present in the menu file
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }
}

