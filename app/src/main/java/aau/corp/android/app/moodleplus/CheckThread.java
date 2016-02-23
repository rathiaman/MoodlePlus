package aau.corp.android.app.moodleplus;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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


public class CheckThread extends AppCompatActivity {

    String course_code;
    String[] description_notify_array;
    String[] time_notify_array;
    Integer[] is_seen_array;

    //String adder = "10.192.13.44";
    String adder = "192.168.43.226";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_thread);
        sendNotification();


    }

    private void sendNotification() {

        String url = "http://" + adder + ":8000//default/notifications.json";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("hello1", response.toString());

                        Toast.makeText(CheckThread.this, response.toString(), Toast.LENGTH_SHORT).show();
                        dowithNoification(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CheckThread.this, "You have an error in request", Toast.LENGTH_SHORT).show();

                    }
                });
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(request);

    }

    //creates array form the response
    void dowithNoification(String response) {
        JSONObject mainObject;

        try {
            mainObject = new JSONObject(response);
            JSONArray json_notification = mainObject.getJSONArray("notifications");

            description_notify_array = new String[json_notification.length()];
            time_notify_array = new String[json_notification.length()];
            is_seen_array = new Integer[json_notification.length()];

            for (int i = 0; i < json_notification.length(); i++) {
                JSONObject childJSONObject = json_notification.getJSONObject(i);
                time_notify_array[i]= childJSONObject.getString("created_at");
                description_notify_array[i] = childJSONObject.getString("description");
                is_seen_array[i] = childJSONObject.getInt("is_seen");
            }
            if(json_notification.length()!=0) {
                create_notification_table();
            }
            else{
                //////////////////enter what to if we have no notification thread
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //creates the table for displaying hte notification
    public void create_notification_table() {

        Toast.makeText(CheckThread.this, "table", Toast.LENGTH_SHORT).show();

        TableLayout course_assig_table = (TableLayout) findViewById(R.id.table_notification);
        course_assig_table.setColumnShrinkable(1, true);
        course_assig_table.setStretchAllColumns(true);

        for (int i = 0; i < time_notify_array.length; i++) {
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
            sno.setWidth(3);
            TextView description = new TextView(this);
            //setting the values of textview
            sno.setText(String.valueOf(i + 1) + ". ");
            description.setText(Html.fromHtml(description_notify_array[i]));

            //for giving span to the name
            TableRow.LayoutParams trParam = new TableRow.LayoutParams();
            trParam.column = 1;
            trParam.span = 2;
            //layout parametrrs for the name
            description.setLayoutParams(trParam);
            description.setTextSize(15);
            description.setTypeface(null, Typeface.BOLD);
            sno.setTextSize(15);
            sno.setTypeface(null, Typeface.BOLD);
            //add textview to the row

            //finished with setting layout

            row1.addView(sno);
            row1.addView(description);
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

            blank.setText(" ");
            blank.setWidth(3);
            start_text.setText("created at: ");
            start.setText(time_notify_array[i]);
            start.setGravity(Gravity.LEFT);
            start_text.setTextSize(15);
            start.setTextSize(15);
            row2.addView(blank);
            row2.addView(start_text);
            row2.addView(start);
            row2.setLayoutParams(lp2);
            row2.setGravity(Gravity.LEFT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                row2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
            course_assig_table.addView(row2);
////////////////////////////////////////////////////////////////
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
            row4.setLayoutParams(lp);
            course_assig_table.addView(row4);
        }

    }


}
