package aau.corp.android.app.moodleplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Aman Rathi on 18-02-2016.
 */

import static aau.corp.android.app.moodleplus.R.anim.abc_fade_out;


public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final ImageView splashscreen = (ImageView)findViewById(R.id.imageView);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.duration);
        final Animation animation2 = AnimationUtils.loadAnimation(getBaseContext(), abc_fade_out);


        splashscreen.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                splashscreen.startAnimation(animation2);
                finish();
                //Intent i = new Intent("aau.corp.android.app.moodleplus.LoginScreen");
              Intent i = new Intent(Splash.this, LoginScreen.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
