package aau.corp.android.app.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Particular_thread extends AppCompatActivity {

    private static EditText comment;

     String course_code;
    Integer index;
     Button post_comment;
    Integer[] user_id_array;
    String[] description_array;
    String[] time_array;


    String adder = "10.192.18.219";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_thread);
        comment = (EditText)findViewById(R.id.add_comment);
        post_comment =(Button)findViewById(R.id.post_comment);

        //get thread id and course_code
        Bundle extras = getIntent().getExtras();


        course_code = extras.getString("EXTRA_COURSE_CODE");
        index = extras.getInt("EXTRA_THREAD_ID");
        Toast.makeText(Particular_thread.this, Integer.toString(index), Toast.LENGTH_SHORT ).show();




        //API call to get the details of page
        sendParticularThread();

        post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitNewComment();


            }

        });

    }

    public void submitNewComment(){


        final String post_comment = comment.getText().toString();

        String url = "http://" + adder + ":8000//threads/post_comment.json?thread_id=" + String.valueOf(index) + "&description=" + post_comment;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response.toString());
                        Toast.makeText(Particular_thread.this, response.toString(), Toast.LENGTH_SHORT).show();

                        //dialog box

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Particular_thread.this, "You have an error in request", Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);




    }



    private void sendParticularThread()
    {

        String url = "http://" + adder + ":8000/threads/thread.json/"+Integer.toString(index);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response.toString());
                        Toast.makeText(Particular_thread.this, response.toString(), Toast.LENGTH_SHORT).show();

                        dowithParticularThread(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Particular_thread.this, "You have an error in request", Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    void dowithParticularThread(String response)
    {
        JSONObject mainObject ;

        try{
            mainObject = new JSONObject(response);
            JSONObject json_thread = mainObject.getJSONObject("thread");

            String name1,thready_title1,updated1,created1,description1;
            // String[] title_array,updated_array;

            name1 = String.valueOf(json_thread.getInt("user_id"));
            thready_title1=json_thread.getString("title");
            updated1 = json_thread.getString("updated_at");
            created1=json_thread.getString("created_at");
            description1 = json_thread.getString("description");

            // giving ids to text fields
            TextView name = (TextView)findViewById(R.id.name);
            TextView thready_title = (TextView)findViewById(R.id.thready_title);
            TextView updated = (TextView)findViewById(R.id.update);
            TextView created = (TextView)findViewById(R.id.created);
            TextView description =(TextView)findViewById(R.id.description);

              // String actual_name = getUserNamefromId(name1);

               name.setText(name1);
               thready_title.setText(thready_title1);
               updated.setText(updated1);
               created.setText(created1);
               description.setText(description1);


            JSONArray json_array_comments =   mainObject.getJSONArray("comments");
            JSONArray json_array_time =   mainObject.getJSONArray("times_readable");


            //declaring the size of the array

           user_id_array = new Integer[json_array_comments.length()];
            description_array = new String[json_array_comments.length()];
            time_array = new String[json_array_time.length()];


            for (int i = 0; i < json_array_comments.length(); i++) {
                JSONObject childJSONObject = json_array_comments.getJSONObject(i);
                user_id_array[i] =    childJSONObject.getInt("user_id");
                description_array[i] =     childJSONObject.getString("description");
                 }

            for (int i = 0; i < json_array_time.length(); i++) {

                time_array[i] =  json_array_time.getString(i);
            }


            /////////////
        //user_id_array, description_array , time_array
        // create_comment_table();

        }catch (JSONException e){
            e.printStackTrace();}

    }


    public String getUserNamefromId(String id){

        final String[]  nameOfId = new String[0];
        String url = "http://" + adder + ":8000/users/user.json/"+ id;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject mainObject ;

                        try {
                            mainObject = new JSONObject(response);
                            JSONObject json_username = mainObject.getJSONObject("user");

                            nameOfId[0] = json_username.getString("first_name");

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    return nameOfId[0];

    }





}
