package aau.corp.android.app.moodleplus;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
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
        course_assig_table.setStretchAllColumns(true);


      /*  FrameLayout.LayoutParams lp_t = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        course_assig_table.setLayoutParams(lp_t);
        course_assig_table.setStretchAllColumns(true);
*/

        for(int i =0 ; i< name_array.length ; i++) {
            //Creating new tablerows and textviews
            TableRow row1 = new TableRow(this);
            //layout parameters
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin=0;
            int topMargin=0;
            int rightMargin=0;
            int bottomMargin = 0;
            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            //making textview
            TextView sno     = new TextView(this);
            TextView name    = new TextView(this);
            //setting the values of textview
            sno.setText(String.valueOf(i + 1) + ". ");
            name.setText(name_array[i]);
            //for giving span to the name
            TableRow.LayoutParams trParam = new TableRow.LayoutParams();
            trParam.column= 1;
            trParam.span = 2;
            //layout parametrrs for the name
            name.setLayoutParams(trParam);
            name.setTextSize(15);
            name.setTypeface(null, Typeface.BOLD);
            sno.setTextSize(15);
            sno.setTypeface(null, Typeface.BOLD);
            //add textview to the row
            row1.addView(sno);
            row1.addView(name);
            //set the layoout parameters for the row
            course_assig_table.addView(row1);
///////////////////////////////////////////////////////
            TableRow row2 = new TableRow(this);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin2=0;
            int topMargin2=0;
            int rightMargin2=0;
            int bottomMargin2=0;
            lp2.setMargins(leftMargin2, topMargin2, rightMargin2, bottomMargin2);
            //making textview
            TextView blank       = new TextView(this);
            TextView start_text  = new TextView(this);
            TextView start = new TextView(this);
           // TextView end = new TextView(this);

            blank.setText("  ");
            start_text.setText("Released on :  ");
            start.setText(start_array[i]);
            start_text.setTextSize(15);
            start.setTextSize(15);
            row2.addView(blank);
            row2.addView(start_text);
            row2.addView(start);
            row2.setLayoutParams(lp2);
            course_assig_table.addView(row2);
////////////////////////////////////////////////////////////////
            TableRow row3 = new TableRow(this);
            TableRow.LayoutParams lp3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin3=0;
            int topMargin3=0;
            int rightMargin3=0;
            int bottomMargin3=0;
            lp3.setMargins(leftMargin3, topMargin3, rightMargin3, bottomMargin3);
            //making textview
            TextView blank3       = new TextView(this);
            TextView end_text  = new TextView(this);
            TextView end = new TextView(this);

            blank3.setText("  ");
            end_text.setText("Deadline :  ");
            end.setText(end_array[i]);
            end_text.setTextSize(15);
            end.setTextSize(15);
            row3.addView(blank3);
            row3.addView(end_text);
            row3.addView(end);
            row3.setLayoutParams(lp3);
            course_assig_table.addView(row3);
/////////////////////////////////////
            //entering blank row
            TableRow row4 = new TableRow(this);
            TextView blank_1       = new TextView(this);
            TextView blank_2     = new TextView(this);
            TextView blank_3       = new TextView(this);
            blank_1.setText("  ");
            blank_2.setText("  ");
            blank_3.setText("  ");
            row4.addView(blank_1);
            row4.addView(blank_2);
            row4.addView(blank_3);
            row3.setLayoutParams(lp3);
            course_assig_table.addView(row4);
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