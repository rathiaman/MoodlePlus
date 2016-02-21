package aau.corp.android.app.moodleplus;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import static android.widget.RadioGroup.*;

public class GradeScreen extends AppCompatActivity {

    //declaring arrays for storung the data to be displayed
    String[] code_array ;
    String[] ltp_array ;
    Integer[] marks_array ;
    Integer[] credits_array ;
    String[] item_array ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        send_data_request();
    }

    public void send_data_request(){
        //url for grades
        String url="http://10.192.7.98:8000//default/grades.json";

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
                            Toast.makeText(GradeScreen.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("u2" , error.toString());
                        //Toast.makeText(LoginScreen.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        Toast.makeText(GradeScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
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

            JSONArray json_array_course_request =   mainObject.getJSONArray("courses");
            JSONArray json_array_grade_request =    mainObject.getJSONArray("grades");
            //declaring the size of the array
            code_array = new String[json_array_course_request.length()];
            credits_array = new Integer[json_array_course_request.length()];
            ltp_array = new String[json_array_course_request.length()];
            item_array = new String[json_array_course_request.length()];
            marks_array =  new Integer[json_array_course_request.length()];

            for (int i = 0; i < json_array_course_request.length(); i++) {
                JSONObject childJSONObject = json_array_course_request.getJSONObject(i);
                code_array[i] = 	childJSONObject.getString("code");
                credits_array[i] = 	childJSONObject.getInt("credits");
                ltp_array[i] = 		childJSONObject.getString("l_t_p");
            }
            for (int i = 0; i < json_array_grade_request.length(); i++) {
                JSONObject childJSONObject = json_array_grade_request.getJSONObject(i);
                item_array[i] =     childJSONObject.getString("name");
                marks_array[i] =
                        childJSONObject.getInt("weightage")*childJSONObject.getInt("score")/
                                childJSONObject.getInt("weightage");

            }
            create_grade_table();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void create_grade_table(){
        /* Find Tablelayout defined in main.xml */
        TableLayout all_grade_table = (TableLayout) findViewById(R.id.all_grade_table);

        //running loops for creating rows
        /*TableRow.LayoutParams  params1=new TableRow.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
*/
        for(int i =0 ; i< code_array.length ; i++) {
            //Creating new tablerows and textviews
            TableRow row    =   new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView code   =   new TextView(this);
            TextView credits=   new TextView(this);
            TextView item   =   new TextView(this);
            TextView marks  =   new TextView(this);
            //setting the text
            code.setText(code_array[i]);
            credits.setText(String.valueOf(credits_array[i]));
            item.setText(item_array[i]);
            marks.setText(String.valueOf(marks_array[i]));
/*
            code.setLayoutParams(params1);
            credits.setLayoutParams(params1);
            item.setLayoutParams(params1);
            marks.setLayoutParams(params1);
  */          //the textviews have to be added to the row created
            row.addView(code);
            row.addView(credits);
            row.addView(item);
            row.addView(marks);
            //row.setLayoutParams(params2);
            all_grade_table.addView(row);}
    }
}
