package co.andresmpuerto.popularapps.presentation.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.andresmpuerto.popularapps.R;
import co.andresmpuerto.popularapps.models.App;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder> implements Filterable {
  
  private List<App> apps;
  private List<App> appsFiltered;
  private OnItemClickListener listener;
  
  public AppsAdapter(List<App> apps, OnItemClickListener listener) {
    this.apps = apps;
    this.appsFiltered = apps;
    this.listener = listener;
  }
  
  @NonNull
  @Override
  public AppsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list, null);
    RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    v.setLayoutParams(lp);
    ViewHolder vh = new ViewHolder(v);
    vh.setContext(parent.getContext());
    return vh;
  }
  
  @Override
  public void onBindViewHolder(@NonNull AppsAdapter.ViewHolder holder, int position) {
    holder.setItems(appsFiltered.get(position), this.listener);
  }
  
  @Override
  public int getItemCount() {
    return appsFiltered.size();
  }
  
  @Override
  public Filter getFilter() {
    return new Filter() {
      @Override
      protected FilterResults performFiltering(CharSequence charSequence) {
        String charString = charSequence.toString();
        if (charString.isEmpty()) {
          appsFiltered = apps;
        } else {
          List<App> filteredList = new ArrayList<>();
          for (App row : apps) {
            if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
              filteredList.add(row);
            }
          }
          appsFiltered = filteredList;
        }
        FilterResults filterResults = new FilterResults();
        filterResults.values = appsFiltered;
        return filterResults;
      }
      
      @Override
      protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        appsFiltered = (ArrayList<App>) filterResults.values;
        notifyDataSetChanged();
      }
    };
  }
  
  class ViewHolder extends RecyclerView.ViewHolder implements Target {
    
    private Context context;
    
    @BindView(R.id.title_app)
    TextView title_app;
    
    @BindView(R.id.img_app)
    ImageView img_app;
    
    @BindView(R.id.category_app)
    TextView category_app;
    
    @BindView(R.id.author_app)
    TextView author_app;
    
    @BindView(R.id.price_app)
    TextView price_app;
  
    @BindView(R.id.release)
    TextView release;
    
    
    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
    
    public void setContext(Context context) {
      this.context = context;
    }
    
    
    public void setItems(final App app, final OnItemClickListener listener) {
      title_app.setText(app.getTitle());
      category_app.setText(app.getCategory());
      author_app.setText(app.getAuthor());
      price_app.setText(app.getPrice());
      release.setText(app.getDate_release().toString());
      Picasso.get().load(app.getUrl_image()).into(this);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          listener.onItemClick(app);
        }
      });
    }
    
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
      img_app.setImageBitmap(bitmap);
    }
    
    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
      Toast.makeText(context, "imagen no se pudo cargar", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    
    }
  }
  
  public interface OnItemClickListener {
    void onItemClick(App item);
  }
  
}
