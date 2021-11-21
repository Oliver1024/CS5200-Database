package review.model;

/**
 * 
 * @author Kejian Tong
 *
 */
public class SitDownRestaurant extends Restaurants{
  protected Integer capacity;

  public SitDownRestaurant(Integer restaurantId) {
    super(restaurantId);
  }

  public SitDownRestaurant(Integer restaurantId, String name, String description,
      String menu, String hours, Boolean active, Cuisine cuisine, String street1,
      String street2, String city, String state, Integer zip, Companies company,
      Integer capacity) {
    super(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city,
        state, zip, company);
    this.capacity = capacity;
  }


  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }
}
