package aau.corp.android.app.moodleplus;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CourseGradeScreen extends AppCompatActivity {

    String course_code;

    String[] name_array;
    String[] weightage_array;
    String[] outof_array;
    String[] marks_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_grade_screen);

        course_code = getIntent().getExtras().getString("course_code");
        send_data_request(course_code);
    }

    private void send_data_request(String course_code) {
        //url for grades
/*

        EditText ip_address_text = (EditText) findViewById(R.id.edit_text_IPAddress);
        final String adder1 = ip_address_text.getText().toString();
*/
        String url="http://10.192.18.219:8000//courses/course.json/"+course_code+"/grades";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response);
                        try
                        catch(Exception e){
                            Log.e("u1" , e.toString());
                            e.printStackTrace();
                            Toast.makeText(CourseGradeScreen.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }{
                            Log.e("qwerty", response.toString());
                            //mainObject = new JSONObject(response);
                            create_all_data_array(response);
                            //Toast.makeText(GradeScreen.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("u2" , error.toString());
                        //Toast.makeText(LoginScreen.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CourseGradeScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);

    }

    private void create_all_data_array(String response) {

        try {
            JSONObject json_response = new JSONObject(response);
            //Toast.makeText(CourseGradeScreen.this, response, Toast.LENGTH_SHORT).show();
            JSONArray course_grade = json_response.getJSONArray("grades");

            name_array        = new String[course_grade.length()];
            weightage_array   = new String[course_grade.length()];
            outof_array       = new String[course_grade.length()];
            marks_array       = new String[course_grade.length()];

            for(int i = 0 ; i<course_grade.length(); i++){

                JSONObject childJSONObject  = course_grade.getJSONObject(i);
                name_array[i]               = childJSONObject.getString("name");
                weightage_array[i]          = childJSONObject.getString("weightage");
                outof_array[i]              = childJSONObject.getString("out_of");
                marks_array[i]              = childJSONObject.getString("score");
            }

            if(course_grade.length()!=0){
                show_course_grade();
            }
            else{
                TextView course_grade_textveiw_1 = (TextView) findViewById(R.id.course_grade_textveiw_1);
                course_grade_textveiw_1.setText("You have not been assigned any grades for this course");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void show_course_grade(){
        TableLayout course_assig_table = (TableLayout) findViewById(R.id.course_grade_table);
        course_assig_table.setColumnShrinkable(1, true);
        course_assig_table.setStretchAllColumns(true);

        TableRow row_text = new TableRow(this);

        TextView sno_text       = new TextView(this);
        TextView name_text      = new TextView(this);
        TextView weightage_text = new TextView(this);
        TextView outof_text     = new TextView(this);
        TextView marks_text     = new TextView(this);

        sno_text.setText("S.No.");
        name_text.setText("Item Name");
        weightage_text.setText("Weightage");
        outof_text.setText("Max. Marks");
        marks_text.setText("Obtained");

        sno_text.setTypeface(null, Typeface.BOLD);
        name_text.setTypeface(null, Typeface.BOLD);
        weightage_text.setTypeface(null, Typeface.BOLD);
        outof_text.setTypeface(null, Typeface.BOLD);
        marks_text.setTypeface(null, Typeface.BOLD);
        //add textview to the row
        row_text.addView(sno_text);
        row_text.addView(name_text);
        row_text.addView(weightage_text);
        row_text.addView(outof_text);
        row_text.addView(marks_text);

        row_text.setGravity(Gravity.CENTER);

        //set the layoout parameters for the row
        course_assig_table.addView(row_text);

        for(int i =0 ; i< name_array.length ; i++) {


            //layout parameters
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin=0;
            int topMargin=0;
            int rightMargin=0;
            int bottomMargin = 0;
            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                     //entering blank row
            TableRow row4 = new TableRow(this);
            TextView blank_1       = new TextView(this);
            TextView blank_2     = new TextView(this);
            TextView blank_3       = new TextView(this);
            TextView blank_4     = new TextView(this);
            TextView blank_5       = new TextView(this);
            blank_1.setText("  ");
            blank_2.setText("  ");
            blank_3.setText("  ");
            blank_4.setText("  ");
            blank_5.setText("  ");
            row4.addView(blank_1);
            row4.addView(blank_2);
            row4.addView(blank_3);
            row4.addView(blank_4);
            row4.addView(blank_5);
            row4.setLayoutParams(lp);
            row4.setGravity(Gravity.CENTER);
            course_assig_table.addView(row4);
            ///////////////////////
            //Creating new tablerows and textviews
            TableRow row1 = new TableRow(this);

            //making textview
            TextView smo            = new TextView(this);
            TextView name           = new TextView(this);
            TextView weightage      = new TextView(this);
            TextView outof          = new TextView(this);
            TextView marks          = new TextView(this);

            //setting the values of textview
            smo.setText(String.valueOf(i + 1) + ". ");
            name.setText(name_array[i]);
            weightage.setText(weightage_array[i]);
            outof.setText(outof_array[i]);
            marks.setText(marks_array[i]);

            //add textview to the row
            row1.addView(smo);
            row1.addView(name);
            row1.addView(weightage);
            row1.addView(outof);
            row1.addView(marks);

            row1.setGravity(Gravity.CENTER);

            //set the layoout parameters for the row
            course_assig_table.addView(row1);


        }
    }
}
