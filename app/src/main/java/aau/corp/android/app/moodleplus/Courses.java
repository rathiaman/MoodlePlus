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
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Courses extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    private ListView listview1;

    public HomeScreen homescreenobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courses);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listview1 = (ListView) findViewById(R.id.drawerList);

//        ArrayList<String> navArray = new ArrayList<String>();
     //   ArrayList<String> navArray = new ArrayList<String>();
     //   navArray = Courses_data.getcourselist();
        List<String> dummy = Arrays.asList(homescreenobj.course_list_codes);
        ArrayList<String> navArray = new ArrayList<String>(dummy);

        /*navArray.add("Home");
        navArray.add("COP290");
        navArray.add("MCL136");
        navArray.add("HUL281");
        navArray.add("MCL311");
        navArray.add("MCL331");
        navArray.add("MCL361");
        */listview1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,navArray);
        listview1.setAdapter(adapter);
        listview1.setOnItemClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.opendrawer,R.string.closedrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        int ls = getIntent().getExtras().getInt("Item_Number");
        loadSelection(ls+1);
        selectItem(ls+1);
       /* int i = homescreen_content.selectedItem();
        loadSelection(i);
*/
    }


    public void loadSelection(int i){
        listview1.setItemChecked(i, true);

        switch (i){
            case 0:
                Intent take_to_home = new Intent(Courses.this, HomeScreen.class);
                startActivity(take_to_home);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }

    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }




    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            if(drawerLayout.isDrawerOpen(listview1)){
                drawerLayout.closeDrawer(listview1);
            }
            else {
                drawerLayout.openDrawer(listview1);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        loadSelection(position);
        selectItem(position);
        drawerLayout.closeDrawer(listview1);
    }

    public void selectItem(int position){
        listview1.setItemChecked(position,true);
        setTitle(listview1.getItemAtPosition(position).toString());
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
