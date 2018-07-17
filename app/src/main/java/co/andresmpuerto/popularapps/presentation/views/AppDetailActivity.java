package co.andresmpuerto.popularapps.presentation.views;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.andresmpuerto.popularapps.R;
import co.andresmpuerto.popularapps.models.App;

public class AppDetailActivity extends AppCompatActivity implements Target {
  
  Gson gson;
  
  @BindView(R.id.image_app)
  ImageView image_app;
  
  @BindView(R.id.summary_app)
  TextView summary_app;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_app_detail);
    ButterKnife.bind(this);
    Bundle extras = getIntent().getExtras();
    assert extras != null;
    String b = extras.getString("app");
    gson = new Gson();
    App app = gson.fromJson(b, App.class);
    
    setTitle(app.getTitle());
    if (app.getSummary() != null)
      summary_app.setText(app.getSummary());
    else{
      summary_app.setText(app.getTitle());
    }
    Picasso.get().load(app.getUrl_image()).into(this);
  }
  
  @Override
  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
    image_app.setImageBitmap(bitmap);
  }
  
  @Override
  public void onBitmapFailed(Exception e, Drawable errorDrawable) {
  
  }
  
  @Override
  public void onPrepareLoad(Drawable placeHolderDrawable) {
  
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: //hago un case por si en un futuro agrego mas opciones ;
        finish();
        return true;
  
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
