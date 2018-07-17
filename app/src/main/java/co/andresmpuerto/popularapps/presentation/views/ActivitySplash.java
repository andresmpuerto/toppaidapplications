package co.andresmpuerto.popularapps.presentation.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import co.andresmpuerto.popularapps.R;
import co.andresmpuerto.popularapps.utils.Constants;

public class ActivitySplash extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        startActivity(new Intent(ActivitySplash.this, HomeActivity.class));
        finish();
      }

    };
    
    Timer timer = new Timer();
    timer.schedule(task, Constants.SPLASH_SCREEN_DELAY);
  }
}
