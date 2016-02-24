package aau.corp.android.app.moodleplus;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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

///////////////////////////////////
// This activity is for a particular thread
// On clicking any particular thread, this activity will open up
////////////////////////////////////

public class Particular_thread extends AppCompatActivity {

    private static EditText comment;

    String course_code;
    Integer index;
    Button post_comment;
    String[] user_name_array;
    String[] description_array;
    String[] time_array;
    Integer[] user_type_array;

    ///////////////////////////////////
    // IP address
    ///////////////////////////////////
    String adder = IPAddress.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_thread);

        ///////////////////////////////////
        // Defining ids
        ///////////////////////////////////
        comment = (EditText)findViewById(R.id.add_comment);
        post_comment =(Button)findViewById(R.id.post_comment);

        ///////////////////////////////////
        //get thread id and course_code
        ///////////////////////////////////
        Bundle extras = getIntent().getExtras();

        ///////////////////////////////////
        // Extracting from intent bundle
        ///////////////////////////////////
        course_code = extras.getString("EXTRA_COURSE_CODE");
        getSupportActionBar().setTitle("Threads: " + course_code.toUpperCase());
        index = extras.getInt("EXTRA_THREAD_ID");
    //    Toast.makeText(Particular_thread.this, Integer.toString(index), Toast.LENGTH_SHORT ).show();

        ///////////////////////////////////
        //API call to get the details of page
        ///////////////////////////////////
        sendParticularThread();

        ///////////////////////////////////
        // On click listener for the post comment button
        ///////////////////////////////////
        post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitNewComment();

                comment.setText("");
            }
        });

    }

    ///////////////////////////////////
    // Function for submitting new comments
    ///////////////////////////////////
    public void submitNewComment(){

        final String post_comment = comment.getText().toString();

        final ProgressDialog messageDialog = new ProgressDialog(this);
        messageDialog.setMessage("sending the information");
        messageDialog.show();

        //url for grades
        String adder1 = IPAddress.getName();
        String url="http://" + adder1 + "//threads/post_comment.json?thread_id=" + String.valueOf(index) + "&description=" + post_comment;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        messageDialog.hide();
                  //      Toast.makeText(Particular_thread.this, response.toString(), Toast.LENGTH_SHORT).show();
                        //dialog box
                        JSONObject mainObject ;

                        try {
                            mainObject = new JSONObject(response);
                            String json_success = mainObject.getString("success");
                            if (json_success == "true")
                            {Toast.makeText(Particular_thread.this, "Comment Posted. Reload ", Toast.LENGTH_SHORT).show();}
                            else
                            {Toast.makeText(Particular_thread.this, "Unable to Post Comment ", Toast.LENGTH_SHORT).show();}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Particular_thread.this, "Network Error", Toast.LENGTH_SHORT).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(request);

    }
    ///////////////////////////////////
    // Function requesting the server for a particular thread
    ///////////////////////////////////
    private void sendParticularThread(){

        final ProgressDialog messageDialog = new ProgressDialog(this);
        messageDialog.setMessage("sending the information");
        messageDialog.show();
        //url for grades
        String adder1 = IPAddress.getName();
        String url="http://" + adder1 + "/threads/thread.json/"+Integer.toString(index);
        //String url = "http://" + adder + ":8000/threads/thread.json/"+Integer.toString(index);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response.toString());
                        messageDialog.hide();
                        dowithParticularThread(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Particular_thread.this, "Network Error", Toast.LENGTH_SHORT).show();

                    }
                });

        // Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }
    ///////////////////////////////////
    // JSON Object extraction
    ///////////////////////////////////
    void dowithParticularThread(String response)
    {
        JSONObject mainObject ;

        try{
            mainObject = new JSONObject(response);
            JSONObject json_thread = mainObject.getJSONObject("thread");

            ///////////////////////////////////
            // Declaring variables
            ///////////////////////////////////
            String name1,thready_title1,updated1,created1,description1;
            // String[] title_array,updated_array;

            ///////////////////////////////////
            // Extracting data from the JSON Object
            ///////////////////////////////////

            name1 = String.valueOf(json_thread.getInt("user_id"));
            thready_title1=json_thread.getString("title");
            updated1 = json_thread.getString("updated_at");
            created1=json_thread.getString("created_at");
            description1 = json_thread.getString("description");

            ///////////////////////////////////
            // giving ids to text fields
            ///////////////////////////////////

            TextView thready_title = (TextView)findViewById(R.id.thready_title);
            TextView updated = (TextView)findViewById(R.id.update);
            TextView created = (TextView)findViewById(R.id.created);
            TextView description =(TextView)findViewById(R.id.description);

            getUserNamefromId(name1);

            thready_title.setText(thready_title1);
            updated.setText(updated1);
            created.setText(created1);
            description.setText(description1);

            JSONArray json_array_comments = mainObject.getJSONArray("comments");
            JSONArray json_array_time = mainObject.getJSONArray("times_readable");
            JSONArray json_array_comment_name = mainObject.getJSONArray("comment_users");

//declaring the size of the array

            user_name_array = new String[json_array_comment_name.length()];
            description_array = new String[json_array_comments.length()];
            time_array = new String[json_array_time.length()];
            user_type_array = new Integer[json_array_comment_name.length()];


            for (int i = 0; i < json_array_comments.length(); i++) {
                JSONObject childJSONObject = json_array_comments.getJSONObject(i);
                description_array[i] = childJSONObject.getString("description");
            }

            for (int i = 0; i < json_array_time.length(); i++) {
                time_array[i] = json_array_time.getString(i);
            }

            for (int i = 0; i < json_array_comment_name.length(); i++) {
                JSONObject childJSONObject = json_array_comment_name.getJSONObject(i);
                user_name_array[i] = childJSONObject.getString("first_name");
                user_type_array[i]= childJSONObject.getInt("type_");
            }


            create_thread_table();


            ///////////////////////////////////
            //user_id_array, description_array , time_array
            // create_comment_table();

        }catch (JSONException e){
            e.printStackTrace();}

    }
    ///////////////////////////////////
    //
    ///////////////////////////////////
    public void getUserNamefromId(String id){

        String url = "http://" + adder + "/users/user.json/"+ id;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject mainObject ;

                        try {
                            mainObject = new JSONObject(response);
                            JSONObject json_username = mainObject.getJSONObject("user");
                            TextView theady_user_name = (TextView)findViewById(R.id.theady_user_name);
                            theady_user_name.setText(json_username.getString("first_name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Particular_thread.this, "You have an error in request", Toast.LENGTH_SHORT).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    public void create_thread_table() {
        //Find Tablelayout defined in main.xml

        TableLayout course_assig_table = (TableLayout) findViewById(R.id.course_assig_table);
        course_assig_table.setColumnShrinkable(2, true);
        course_assig_table.setStretchAllColumns(true);


        for (int i = 0; i < user_name_array.length; i++) {
            //Creating new tablerows and textviews
            TableRow row1 = new TableRow(this);
            //layout parameters
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin = 0;
            int topMargin = 0;
            int rightMargin = 0;
            int bottomMargin = 0;
            lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
            //making textview
            TextView sno = new TextView(this);
            TextView name = new TextView(this);
            //setting the values of textview
            sno.setText(String.valueOf(i + 1) + ". ");
            name.setText(user_name_array[i]);
            //for giving span to the name
            TableRow.LayoutParams trParam = new TableRow.LayoutParams();
            trParam.column = 1;
            //layout parametrrs for the name
            name.setLayoutParams(trParam);
            name.setTextSize(15);
            name.setTypeface(null, Typeface.BOLD);
            sno.setTextSize(15);
            sno.setTypeface(null, Typeface.BOLD);
            //add textview to the row

            //finished with setting layout

            row1.addView(sno);
            row1.addView(name);
            row1.setGravity(Gravity.CENTER);
            //set the layoout parameters for the row
            course_assig_table.addView(row1);
///////////////////////////////////////////////////////
            TableRow row2 = new TableRow(this);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin2 = 0;
            int topMargin2 = 0;
            int rightMargin2 = 0;
            int bottomMargin2 = 0;
            lp2.setMargins(leftMargin2, topMargin2, rightMargin2, bottomMargin2);
            //making textview
            TextView blank = new TextView(this);
            TextView start_text = new TextView(this);
            TextView start = new TextView(this);
            // TextView end = new TextView(this);

            blank.setText("  ");
            start_text.setText("  ");
            start.setText(description_array[i]);
            start_text.setTextSize(15);
            start.setTextSize(15);
            row2.addView(blank);
            row2.addView(start_text);
            row2.addView(start);
            row2.setLayoutParams(lp2);
            //row2.setGravity(Gravity.CENTER);
            course_assig_table.addView(row2);
////////////////////////////////////////////////////////////////
            TableRow row3 = new TableRow(this);
            TableRow.LayoutParams lp3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            int leftMargin3 = 0;
            int topMargin3 = 0;
            int rightMargin3 = 0;
            int bottomMargin3 = 0;
            lp3.setMargins(leftMargin3, topMargin3, rightMargin3, bottomMargin3);
            //making textview
            TextView blank3 = new TextView(this);
            TextView end_text = new TextView(this);
            TextView end = new TextView(this);

            blank3.setText("  ");
            if (user_type_array[i]==0)
            {end_text.setText("Student");
            }
            else if (user_type_array[i]==1)
            {end_text.setText("Teacher");
            }
            else
            {end_text.setText("posted");}
            end.setText(time_array[i]);
            end_text.setTextSize(15);
            end.setTextSize(15);
            row3.addView(blank3);
            row3.addView(end_text);
            row3.addView(end);
            row3.setLayoutParams(lp3);
            row3.setGravity(Gravity.CENTER);
            course_assig_table.addView(row3);
/////////////////////////////////////
            //entering blank row

            TableRow row4 = new TableRow(this);
            TextView blank_1 = new TextView(this);
            TextView blank_2 = new TextView(this);
            TextView blank_3 = new TextView(this);
            blank_1.setText("  ");
            blank_2.setText("  ");
            blank_3.setText("  ");
            row4.addView(blank_1);
            row4.addView(blank_2);
            row4.addView(blank_3);
            row3.setLayoutParams(lp3);
            course_assig_table.addView(row4);
        }

    }

}
