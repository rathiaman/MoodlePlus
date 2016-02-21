package aau.corp.android.app.moodleplus;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreadActivity extends AppCompatActivity {


    private static EditText thread_title, description;
   // String[] title_array,updated_array;

    String[] title_array,updated_array;
    Integer[] id_array,thread_no;
    String course_code;
    Button Submit;


    String adder = "10.192.18.219";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Submit = (Button)findViewById(R.id.submit);
        thread_title = (EditText) findViewById(R.id.thread_title);
        description = (EditText) findViewById(R.id.description);


        course_code = getIntent().getExtras().getString("course_code");

        String title = "Threads: " + course_code.toUpperCase();
        setTitle(title);


        sendThread();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitNewThread();


            }

        });


    }



    private void sendThread()
    {


        String url = "http://" + adder + ":8000/courses/course.json/"+course_code +"/threads";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response.toString());
                        Toast.makeText(ThreadActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                       dowithThread(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThreadActivity.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(LoginScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    void dowithThread(String response)
    {
        JSONObject mainObject ;

        try{
            mainObject = new JSONObject(response);
            //Toast.makeText(GradeScreen.this, mainObject.toString(), Toast.LENGTH_SHORT).show();

            JSONArray json_array_thread_request =   mainObject.getJSONArray("course_threads");

            //declaring the size of the array

            title_array = new String[json_array_thread_request.length()];
            updated_array = new String[json_array_thread_request.length()];
            id_array = new Integer[json_array_thread_request.length()];



            for (int i = 0; i < json_array_thread_request.length(); i++) {
                JSONObject childJSONObject = json_array_thread_request.getJSONObject(i);
                title_array[i] =    childJSONObject.getString("title");
                updated_array[i] =     childJSONObject.getString("updated_at");
                id_array[i] =        childJSONObject.getInt("id");
            }

            /////////////

            Toast.makeText(ThreadActivity.this,"size" + title_array.length, Toast.LENGTH_SHORT ).show();



            create_thread_table();
        }catch (JSONException e){
            e.printStackTrace();}


    }
   // int idOfThread;

    public void create_thread_table(){
        /* Find Tablelayout defined in main.xml */
        TableLayout all_thread_table = (TableLayout) findViewById(R.id.all_thread_table);

        //running loops for creating rows
        /*TableRow.LayoutParams  params1=new TableRow.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(RadioGroup.LayoutParams.FILL_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
*/




        for(int i =0 ; i< title_array.length ; i++) {
            //Creating new tablerows and textviews
            TableRow row    =   new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView title   =   new TextView(this);
            TextView id=   new TextView(this);
            TextView updated_at   =   new TextView(this);
            //setting the text
            title.setText(title_array[i]);
            id.setText(String.valueOf(i+1));
            updated_at.setText(updated_array[i]);
            //setting the allignment
            title.setGravity(Gravity.CENTER);
            id.setGravity(Gravity.CENTER);
            updated_at.setGravity(Gravity.CENTER);
            title.setTextAppearance(this,
                    android.R.style.TextAppearance_Medium);
            title.setTypeface(Typeface.DEFAULT);
            title.setTextColor(Color.BLUE);
            title.setPaintFlags(title.getPaintFlags()
                    | Paint.UNDERLINE_TEXT_FLAG);

            //declaration as final so that i can be used in onclicklistener
            final int idOfThread = id_array[i];

            title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ThreadActivity.this,"Clicked",Toast.LENGTH_SHORT).show();


                    Intent in = new Intent(ThreadActivity.this,Particular_thread.class);

                    Bundle extras = new Bundle();

                    extras.putInt("EXTRA_THREAD_ID",idOfThread);
                    extras.putString("EXTRA_COURSE_CODE", course_code);
                    in.putExtras(extras);

                    startActivity(in);


                }
            });


          //the textviews have to be added to the row created
            row.addView(id);
            row.addView(title);
            row.addView(updated_at);

            all_thread_table.addView(row);}
    }

  public void submitNewThread(){


      final String thread_title1 = thread_title.getText().toString();
      final String description1 = description.getText().toString();

      String url = "http://" + adder + ":8000/threads/new.json?title=" + thread_title1 +"&description=" + description1 + "&course_code=" + course_code;
      StringRequest request = new StringRequest(Request.Method.GET, url,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      Log.e("hello1", response.toString());
                      Toast.makeText(ThreadActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                      //dialog box

                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                      Toast.makeText(ThreadActivity.this, "You have an error in request", Toast.LENGTH_SHORT).show();
                      //Toast.makeText(LoginScreen.this, error.toString(), Toast.LENGTH_SHORT).show();
                  }
              });

      RequestQueue requestQueue = Volley.newRequestQueue(this);
      requestQueue.add(request);




  }





}
