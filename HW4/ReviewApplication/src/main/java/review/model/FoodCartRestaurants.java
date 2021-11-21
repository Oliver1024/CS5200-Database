package review.model;

/**
 * 
 * @author Kejian Tong
 *
 */
public class FoodCartRestaurants extends Restaurants{
  protected Boolean licensed;

  public FoodCartRestaurants(Integer restaurantId, Boolean licensed) {
    super(restaurantId);
  }

  public FoodCartRestaurants(Integer restaurantId, String name, String description,
      String menu, String hours, Boolean active, Cuisine cuisine, String street1,
      String street2, String city, String state, Integer zip, Companies company,
      Boolean licensed) {
    super(restaurantId, name, description, menu, hours, active, cuisine, street1, street2, city,
        state, zip, company);
    this.licensed = licensed;
  }


  public Boolean getLicensed() {
    return licensed;
  }

  public void setLicensed(Boolean licensed) {
    this.licensed = licensed;
  }
}
