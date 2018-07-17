package co.andresmpuerto.popularapps.presentation.adapters;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.andresmpuerto.popularapps.R;
import co.andresmpuerto.popularapps.models.Category;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
  
  private List<Category> categories;
  private OnItemClickListener listener;
  
  public CategoryAdapter(List<Category> categories, OnItemClickListener listener) {
    this.categories = categories;
    this.listener = listener;
  }
  
  @NonNull
  @Override
  public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_categories, null);
    RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    v.setLayoutParams(lp);
    ViewHolder vh = new ViewHolder(v);
    vh.setContext(parent.getContext());
    return vh;
  }
  
  @Override
  public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
    holder.setItems(categories.get(position), this.listener);
  }
  
  @Override
  public int getItemCount() {
    return categories.size();
  }
  
  class ViewHolder extends RecyclerView.ViewHolder {
    
    private Context context;
    @BindView(R.id.title_category)
    TextView title_category;
    
    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
    
    public void setContext(Context context) {
      this.context = context;
    }
    
    public void setItems(final Category category, final OnItemClickListener listener) {
        title_category.setText(category.getTitle());
        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            listener.onItemClick(category);
          }
        });
    }
  }
  
  public interface OnItemClickListener {
    void onItemClick(Category item);
  }
  
}
