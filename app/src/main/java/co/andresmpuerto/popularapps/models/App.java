package co.andresmpuerto.popularapps.models;

import android.support.annotation.NonNull;

import java.util.Comparator;
import java.util.Date;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class App implements Comparator{

  public int id;
  
  public String title;
  
  public String summary;
  
  public String price;
  
  public int id_category;
  
  public String category;
  
  public String author;
  
  private String url_image;
  
  private Date date_release;
  
  public App() {
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getSummary() {
    return summary;
  }
  
  public void setSummary(String summary) {
    this.summary = summary;
  }
  
  public String getPrice() {
    return price;
  }
  
  public void setPrice(String price) {
    this.price = price;
  }
  
  public int getId_category() {
    return id_category;
  }
  
  public void setId_category(int id_category) {
    this.id_category = id_category;
  }
  
  public String getCategory() {
    return category;
  }
  
  public void setCategory(String category) {
    this.category = category;
  }
  
  public String getAuthor() {
    return author;
  }
  
  public void setAuthor(String author) {
    this.author = author;
  }
  
  
  public String getUrl_image() {
    return url_image;
  }
  
  public void setUrl_image(String url_image) {
    this.url_image = url_image;
  }
  
  public Date getDate_release() {
    return date_release;
  }
  
  public void setDate_release(Date date_release) {
    this.date_release = date_release;
  }
  
  @Override
  public int compare(Object o, Object t1) {
    return 0;
  }
}
