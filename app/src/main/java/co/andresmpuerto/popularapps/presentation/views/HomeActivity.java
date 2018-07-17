package co.andresmpuerto.popularapps.presentation.views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.andresmpuerto.popularapps.R;

import co.andresmpuerto.popularapps.database.Preferences;
import co.andresmpuerto.popularapps.models.Category;
import co.andresmpuerto.popularapps.presentation.adapters.CategoryAdapter;
import co.andresmpuerto.popularapps.presentation.presenters.HomePresenter;

public class HomeActivity extends AppCompatActivity {
  
  //@BindView(R.id.toolbar)
  //Toolbar toolbar;
  
  //@BindView(R.id.list_categories)
  RecyclerView list_categories;
  
  private CategoryAdapter categoryAdapter;
  private HomePresenter homePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.app_bar_home);
    //ButterKnife.bind(HomeActivity.this);
    
    Toolbar toolbar =  findViewById(R.id.toolbar);
    list_categories  = findViewById(R.id.list_categories);
    setSupportActionBar(toolbar);
    //settingToolbar(1);
    
    setTitle("Categorias");
    list_categories.setLayoutManager(new LinearLayoutManager(this));
    list_categories.setItemAnimator(new DefaultItemAnimator());
    
    homePresenter = new HomePresenter(this);
    Preferences.clear(this);
    homePresenter.getInfo();
  }
  
  public void setList(List<Category> categories){
    categoryAdapter = new CategoryAdapter(categories, new CategoryAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(Category item) {
        Intent intent = new Intent(HomeActivity.this, CategoryDetailActivity.class);
        intent.putExtra("category_name", item.getTitle());
        intent.putExtra("category_id", item.getId());
        startActivity(intent);
      }
    });
    
    list_categories.setAdapter(categoryAdapter);
  }
  
  public void adviceToView(int type){
    switch (type) {
      case 0:
        Toast.makeText(this, "No tiene conexión a Internet", Toast.LENGTH_SHORT).show();
        break;
      case 404:
        break;
      case 500:
        break;
    }
  }
 
}