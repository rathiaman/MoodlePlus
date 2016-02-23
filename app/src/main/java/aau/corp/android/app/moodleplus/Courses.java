package aau.corp.android.app.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

///////////////////////////////////
// This class is for the courses of a particular person who is logged in
// This class opens up when you click on a particular course at the Home Screen
// On opening, the page will contains course code, course name, course description, course credits, course ltp and three buttos for assignments, thread and notifications
///////////////////////////////////

public class Courses extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /////////////////////////////////////
    // Defining variables
    ///////////////////////////////////
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listview1;
    List<String> dummy;

    //ujjawal
    public Button assignment_button, thread_button, grade_button;
    int course_index;
    String course_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        ///////////////////////////////////
        // Giving Variables an id
        ///////////////////////////////////
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listview1 = (ListView) findViewById(R.id.drawerList);

        dummy = getIntent().getStringArrayListExtra("Course_Code_List");
        ArrayList<String> navArray = (ArrayList<String>) dummy;
        listview1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        /////////////////////////////////////
        // Action Bar Toggle Button Function for Open & Close
        ///////////////////////////////////
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, navArray);
        listview1.setAdapter(adapter);
        listview1.setOnItemClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.opendrawer, R.string.closedrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ///////////////////////////////////
        // Selection of item from the navigation drawer
        // Selection depends upon the method course clicked on the Home Screen
        // The selection has been brought here via Intent
        ///////////////////////////////////
        int ls = getIntent().getExtras().getInt("Item_Number");
        loadSelection(ls + 1);
        selectItem(ls + 1);

        ///////////////////////////////////
        // Button Click Listeners for Assignment and Threads Button
        ///////////////////////////////////
        onButtonClickListener_course_assignment();
        onButtonClickListener_course_thread();
        onButtonClickListener_course_grade();
    }

    /////////////////////////////////////
    // This is a function for the selection of any item from the navigation drawer
    ///////////////////////////////////
    public void loadSelection(int i) {
        listview1.setItemChecked(i, true);

        // Total number of item case are defined
        int total_cases = dummy.size() + 1;

        ///////////////////////////////////
        // On tapping Home Screen, it is taking back to home screen
        ///////////////////////////////////
        if (i == 0) {
            Intent take_to_home = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(take_to_home);
        }
        ///////////////////////////////////
        // Else its calling for the details of the respective course
        ///////////////////////////////////
        course_index = i - 1;
        String response = getIntent().getStringExtra("response");
        show_course_detail(course_index, response);
    }

    ///////////////////////////////////
    // Function for Button Click Listener ----- Course Assignment Button
    ///////////////////////////////////
    private void onButtonClickListener_course_assignment() {
        assignment_button = (Button) findViewById(R.id.assignment_button);
        assignment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////////////
                // This new Intent takes us to a new activity where Course Assignments are displayed
                ///////////////////////////////////
                Intent course_assignment = new Intent(getApplicationContext(), CourseAssignmentScreen.class);
                course_assignment.putExtra("course_code", course_code);
                startActivity(course_assignment);
            }
        });
    }

    ///////////////////////////////////
    // Function for Button Click Listener ----- Course Thread Button
    ///////////////////////////////////
    private void onButtonClickListener_course_thread() {
        thread_button = (Button) findViewById(R.id.thread_button);
        thread_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////////////
                // This new Intent takes us to a new activity where Threads of Particular course are displayed
                ///////////////////////////////////
                Intent course_assignment = new Intent(getApplicationContext(), ThreadActivity.class);
                course_assignment.putExtra("course_code", course_code);
                startActivity(course_assignment);
            }
        });
    }

    ///////////////////////////////////
    // Function for Button Click Listener ----- Course grade Button
    ///////////////////////////////////
    private void onButtonClickListener_course_grade(){
        grade_button = (Button) findViewById(R.id.grade_button);
        grade_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////////////
                // This new Intent takes us to a new activity where Course Grades are displayed
                ///////////////////////////////////
                Intent course_assignment = new Intent(getApplicationContext(), CourseGradeScreen.class);
                course_assignment.putExtra("course_code", course_code);
                startActivity(course_assignment);
            }
        });
    }
    ///////////////////////////////////

    ///////////////////////////////////
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    ///////////////////////////////////
    // This function is a listener for the Top Left 3 lines from the Navigation Drawer
    ///////////////////////////////////
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            ///////////////////////////////////
            // Closes the drawer layout if open
            ///////////////////////////////////
            if (drawerLayout.isDrawerOpen(listview1)) {
                drawerLayout.closeDrawer(listview1);
            } else {
                ///////////////////////////////////
                // Opens the Drawer Layout
                ///////////////////////////////////
                drawerLayout.openDrawer(listview1);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    ///////////////////////////////////
    // Item Click Listener for the item selected from the navigation drawer
    ///////////////////////////////////
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ///////////////////////////////////
        // Calls Load Selection and then closes the drawer
        ///////////////////////////////////
        loadSelection(position);
        selectItem(position);
        drawerLayout.closeDrawer(listview1);
    }

    ///////////////////////////////////
    // This function makes the selected item as checked and changes the title accordingly
    ///////////////////////////////////
    public void selectItem(int position) {
        listview1.setItemChecked(position, true);
        if (position != 0) {
            setTitle(listview1.getItemAtPosition(position).toString());
        }
    }

    ///////////////////////////////////
    // Function for title change
    ///////////////////////////////////
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    ///////////////////////////////////
    //get the course details and show it on  the screen
    //also sets the value of course code
    ///////////////////////////////////
    public void show_course_detail(int course_index, String response) {

        try {
            JSONObject json_response = new JSONObject(response);
            JSONArray jsonarray_response = json_response.getJSONArray("courses");
            JSONObject course_json = jsonarray_response.getJSONObject(course_index);

            //assigns the value of the course code
            course_code = course_json.getString("code").toLowerCase();

            //String code          =   course_json.getString("code");
            String name = course_json.getString("name");
            String description = course_json.getString("description");
            String credits = String.valueOf(course_json.getInt("credits"));
            String ltp = course_json.getString("l_t_p");
            //TextView code_tv        =   (TextView) findViewById(R.id.);
            TextView name_tv = (TextView) findViewById(R.id.course_name);
            TextView description_tv = (TextView) findViewById(R.id.course_decription);
            TextView credits_tv = (TextView) findViewById(R.id.course_credits);
            TextView ltp_tv = (TextView) findViewById(R.id.course_ltp);
            //code_tv.setText(code);
            name_tv.setText(name);
            description_tv.setText(description);
            credits_tv.setText(credits);
            ltp_tv.setText(ltp);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}