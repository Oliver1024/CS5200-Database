package review.model;

/**
 * 
 * @author Kejian Tong
 *
 */
public class TakeOutRestaurants extends Restaurants{

  protected Integer maxWaitingTime;


  public TakeOutRestaurants(Integer restaurantId, String name, String description,
      String menu, String hours, Boolean active, Cuisine cuisine, String street1,
      String street2, String city, String state, Integer zip, Companies company,
      Integer maxWaitingTime) {
    super(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city,
        state, zip, company);
    this.maxWaitingTime = maxWaitingTime;
  }

  public TakeOutRestaurants(Integer restaurantId) {
    super(restaurantId);
  }

  public Integer getMaxWaitingTime() {
    return maxWaitingTime;
  }

  public void setMaxWaitingTime(Integer maxWaitingTime) {
    this.maxWaitingTime = maxWaitingTime;
  }
}
