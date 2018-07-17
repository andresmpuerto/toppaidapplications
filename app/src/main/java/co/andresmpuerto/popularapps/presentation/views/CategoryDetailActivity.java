package co.andresmpuerto.popularapps.presentation.views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.andresmpuerto.popularapps.R;
import co.andresmpuerto.popularapps.database.Preferences;
import co.andresmpuerto.popularapps.models.App;
import co.andresmpuerto.popularapps.presentation.adapters.AppsAdapter;

public class CategoryDetailActivity extends AppCompatActivity
    implements  SearchView.OnQueryTextListener{
  
  private SearchManager searchManager;
  private SearchView searchView;
  private AppsAdapter appsAdapter;
  private Gson gson = new Gson();
  List<App> listado;
  
  @BindView(R.id.list_apps)
  RecyclerView apps;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_detail);
    ButterKnife.bind(this);
    Bundle extras = getIntent().getExtras();
    
//    getActionBar().setDisplayHomeAsUpEnabled(true);
    
    setTitle("Categoria: " + extras.getString("category_name"));
    int id_categoria = extras.getInt("category_id");
    apps.setLayoutManager(new LinearLayoutManager(this));
    apps.setItemAnimator(new DefaultItemAnimator());
    
    
  
    String apps = Preferences.getApps(this);
    listado = new ArrayList<>();
    try {
      JSONArray data = new JSONArray(apps);
      for(int i =0; i< data.length();i++){
        App a = gson.fromJson(data.getJSONObject(i).toString(), App.class);
        if (a.getId_category() == id_categoria) {
          listado.add(a);
        }
      }
      setApps(listado);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
  
  public void setApps(List<App> apps){
    appsAdapter = new AppsAdapter(apps, new AppsAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(App item) {
        Intent intent = new Intent(CategoryDetailActivity.this, AppDetailActivity.class);
        Log.d("7777", item.getCategory());
        intent.putExtra("app", gson.toJson(item));
        startActivity(intent);
      }
    });
    
    this.apps.setAdapter(appsAdapter);
  }
  
  @Override
  public void onBackPressed() {
    if (!searchView.isIconified()) {
      searchView.setIconified(true);
      return;
    }
    super.onBackPressed();
  }
  
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.home, menu);
  
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView = (SearchView) menu.findItem(R.id.action_search)
        .getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setMaxWidth(Integer.MAX_VALUE);
    
    searchView.setQueryHint("Buscar App");
    searchView.setOnQueryTextListener(this);
    
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        finish();
        return true;
      
      case R.id.action_order_alfa:
        if (listado.size() > 0) {
          Collections.sort(listado, new Comparator<App>() {
            @Override
            public int compare(final App object1, final App object2) {
              return object1.getTitle().compareTo(object2.getTitle());
            }
          });
          appsAdapter.notifyDataSetChanged();
        }
        return true;
  
      case R.id.action_order_date:
  
        if (listado.size() > 0) {
          Collections.sort(listado, new Comparator<App>() {
            @Override
            public int compare(final App object1, final App object2) {
              return object1.getDate_release().compareTo(object2.getDate_release());
            }
          });
          appsAdapter.notifyDataSetChanged();
        }
        return true;
      
      case R.id.action_search:
        return true;
    }
    
    return super.onOptionsItemSelected(item);
  }
  
  
  //Interfaces para búsqueda en la toolbar
  
  @Override
  public boolean onQueryTextSubmit(String query) {
    searchView.setQuery("", false);
    searchView.setIconified(true);
    appsAdapter.getFilter().filter(query);
    appsAdapter.notifyDataSetChanged();
    return false;
  }
  
  @Override
  public boolean onQueryTextChange(String newText) {
    appsAdapter.getFilter().filter(newText);
    appsAdapter.notifyDataSetChanged();
    return false;
  }
}
