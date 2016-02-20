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
import java.util.Arrays;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;


// This is the home screen when you are logged in correctly
// This is screen displays your notifications, assignments, grades in various subjects as well as your courses

// to show your courses, a dropdown list has been enabled which on click opens and show your registered courses
// Notifications, grades and assignments takes you to the new activity of the respective options

public class HomeScreen extends AppCompatActivity
     //   implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener
{

    public Button to_grades;

    String first_name;
    String last_name;
    String entry_number;
    String email;
    String int_type;
    public String [] course_list_with_name ;
    public String [] course_list_codes ;



    // This creates a list for the courses and list opens up if you tap on it
    HashMap<String, List<String>> my_courses = new HashMap<String, List<String>>();
    List<String> courses_list;
    ExpandableListView my_course_list;
    courseAdapter adapter_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sendRequest();

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
      //  my_courses = Courses_data.getInfo();

        ArrayList<String> dummy1 = new ArrayList<>();
        my_courses.put("MY COURSES", dummy1);

        Toast.makeText(HomeScreen.this, dummy1.get(1), Toast.LENGTH_SHORT).show();

        courses_list = new ArrayList<String>(my_courses.keySet());
        adapter_list = new courseAdapter(this, my_courses, courses_list);
        my_course_list.setAdapter(adapter_list);
        onButtonClickListener_grades();
      //  onButtonClickListener();


        my_course_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

               // int ls = my_courses.get((courses_list).get(groupPosition)).get(childPosition);

                int ls = childPosition;

                Intent transition = new Intent(getApplicationContext(), Courses.class);
                transition.putExtra("Item_Number", ls);
                startActivity(transition);
                return false;
            }
        });

        ////////////////////////////////////////////////////////////
        //getting the values for the previous activity to be transfered to the next activity
        first_name = getIntent().getExtras().getString("EXTRA_FIRST");
        last_name = getIntent().getExtras().getString("EXTRA_LAST");
        entry_number = getIntent().getExtras().getString("EXTRA_ENTRY");
        email = getIntent().getExtras().getString("EXTRA_EMAIL");
        int_type = getIntent().getExtras().getString("EXTRA_TYPE");

        ////////////////////////////////////////////////


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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            Intent profile_page = new Intent(getApplicationContext(), ProfileScreen.class);
            profile_page.putExtra("EXTRA_FIRST", first_name);
            profile_page.putExtra("EXTRA_LAST", last_name);
            profile_page.putExtra("EXTRA_ENTRY", entry_number);
            profile_page.putExtra("EXTRA_EMAIL", email);
            profile_page.putExtra("EXTRA_TYPE", int_type);
            startActivity(profile_page);
        }
        return false;
    }


    private void sendRequest() {

        String url = "http://10.192.18.219:8000/courses/list.json";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(LoginScreen.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(HomeScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void PJson(String response){

        try {
            JSONObject course_details = new JSONObject(response);
            JSONArray json_array_course_request =   course_details.getJSONArray("courses");
            course_list_with_name = new String[json_array_course_request.length()+1];
            course_list_codes = new String[json_array_course_request.length()];
            course_list_with_name[0] = "Home";
            for (int i = 0; i < json_array_course_request.length(); i++) {
                JSONObject childJSONObject = json_array_course_request.getJSONObject(i);
                course_list_codes[i] = 	childJSONObject.getString("code");
                course_list_with_name[i+1] = 	childJSONObject.getString("code") + " : "+
                        childJSONObject.getString("name");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}

