package co.andresmpuerto.popularapps.models;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class Category {
  
  private int id;
  private String title;
  
  public Category() {
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
}