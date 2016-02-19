package aau.corp.android.app.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Courses extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private android.support.v4.app.FragmentManager fragmentManager;
    private ListView listview1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listview1 = (ListView) findViewById(R.id.drawerList);

        ArrayList<String> navArray = new ArrayList<String>();
        navArray.add("Home");
        navArray.add("Fragment 1");
        navArray.add("Fragment 2");
        navArray.add("Fragment 3");
        navArray.add("Fragment 4");
        navArray.add("Fragment 5");
        listview1.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,navArray);
        listview1.setAdapter(adapter);
        listview1.setOnItemClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.opendrawer,R.string.closedrawer);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        loadSelection(0);

    }


    private void loadSelection(int i){
        listview1.setItemChecked(i, true);

        switch (i){
            case 0:
                HomeFragment homeFragment = new HomeFragment();

                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragmentholder, homeFragment);
                fragmentTransaction.commit();

                break;
            case 1:
                Fragment1 myFragment1 = new Fragment1();

                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragmentholder, myFragment1);
                fragmentTransaction.commit();
                break;
            case 2:
                Fragment2 myFragment2 = new Fragment2();

                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragmentholder, myFragment2);
                fragmentTransaction.commit();

                break;
            case 3:
                Fragment3 myFragment3 = new Fragment3();

                fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragmentholder, myFragment3);
                fragmentTransaction.commit();

                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
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
        drawerLayout.closeDrawer(listview1);
    }


}
