package aau.corp.android.app.moodleplus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseAssignmentScreen extends AppCompatActivity {

    //declaring arrays for storung the data to be displayed
    String[] name_array ;
    String[] start_array ;
    String[] end_array ;
    String course_code ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_assignment_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        course_code = getIntent().getExtras().getString("course_code");
        send_data_request();
    }


    public void send_data_request(){
        //url for grades
        String url="http://10.192.18.219:8000//courses/course.json/"+course_code+"/assignments";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response);
                        try {
                            Log.e("qwerty", response.toString());
                            //mainObject = new JSONObject(response);
                            create_all_data_array(response);
                            //Toast.makeText(GradeScreen.this, response, Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e){
                            Log.e("u1" , e.toString());
                            e.printStackTrace();
                            Toast.makeText(CourseAssignmentScreen.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("u2" , error.toString());
                        //Toast.makeText(LoginScreen.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CourseAssignmentScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void create_all_data_array(String response){

        JSONObject mainObject ;

        try{
            mainObject = new JSONObject(response);
            //Toast.makeText(GradeScreen.this, mainObject.toString(), Toast.LENGTH_SHORT).show();

            JSONArray json_array_course_request =   mainObject.getJSONArray("assignments");
            //declaring the size of the array
            name_array = new String[json_array_course_request.length()];
            start_array = new String[json_array_course_request.length()];
            end_array = new String[json_array_course_request.length()];

            for (int i = 0; i < json_array_course_request.length(); i++) {
                JSONObject childJSONObject = json_array_course_request.getJSONObject(i);
                name_array[i] = childJSONObject.getString("name");
                start_array[i] = childJSONObject.getString("created_at");
                end_array[i] = childJSONObject.getString("deadline");
            }
            create_grade_table();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void create_grade_table(){
        /* Find Tablelayout defined in main.xml */
        TableLayout course_assig_table = (TableLayout) findViewById(R.id.course_assig_table);
        course_assig_table.setColumnShrinkable(1,true);

        //running loops for creating rows
        /*TableRow.LayoutParams  params1=new TableRow.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
*/
        for(int i =0 ; i< name_array.length ; i++) {
            //Creating new tablerows and textviews
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            int leftMargin=0;
            int topMargin=2;
            int rightMargin=0;
            int bottomMargin=2;

            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

            TextView sno = new TextView(this);
            TextView name = new TextView(this);

            sno.setText(String.valueOf(i + 1) + ".) ");
            name.setText(name_array[i] + " ");

            //for span
            TableRow.LayoutParams trParam = new TableRow.LayoutParams();
            trParam.column= 1;
            trParam.span = 2;

            name.setLayoutParams(trParam);

            row1.addView(sno);
            row1.addView(name);

            row1.setLayoutParams(lp);
            course_assig_table.addView(row1);

            TableRow row2 = new TableRow(this);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);

            int leftMargin2=0;
            int topMargin2=2;
            int rightMargin2=0;
            int bottomMargin2=8;

            lp2.setMargins(leftMargin2, topMargin2, rightMargin2, bottomMargin2);

            TextView blank = new TextView(this);
            //TextView blank1 = new TextView(this);
            TextView start = new TextView(this);
            TextView end = new TextView(this);

            blank.setText("  ");
            //blank1.setText("  ");
            start.setText(start_array[i]);
            end.setText(" "+end_array[i]);

            //row2.addView(blank1);
            row2.addView(blank);
            row2.addView(start);
            row2.addView(end);

            row2.setLayoutParams(lp2);
            course_assig_table.addView(row2);
        }

/*
            code.setLayoutParams(params1);
            credits.setLayoutParams(params1);
            item.setLayoutParams(params1);
            marks.setLayoutParams(params1);
  */          //the textviews have to be added to the row created
        //row.setLayoutParams(params2);

    }



}