package aau.corp.android.app.moodleplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

  //  public EditText editText_Username, editText_Password;
        public Button login_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        onButtonClickListener();
       /* editText_Username = (EditText) findViewById(R.id.editText_Username);
        editText_Password = (EditText) findViewById(R.id.editText_Password);


*/
    }


    public void onButtonClickListener() {
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(i);

            }
        });
 }

    }
