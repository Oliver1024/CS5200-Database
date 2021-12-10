package seattlerHub.model;

public class Restaurants {
  protected Integer restaurantId;
  protected String restaurantName;
  protected String description;
  protected Neighborhoods neighborhoods;
  protected Double longitude;
  protected Double latitude;
  protected String address;

  public Restaurants(Integer restaurantId, String restaurantName, String description,
      Neighborhoods neighborhoods, Double longitude, Double latitude, String address) {
    this.restaurantId = restaurantId;
    this.restaurantName = restaurantName;
    this.description = description;
    this.neighborhoods = neighborhoods;
    this.longitude = longitude;
    this.latitude = latitude;
    this.address = address;
  }

  public Integer getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(Integer restaurantId) {
    this.restaurantId = restaurantId;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Neighborhoods getNeighborhoods() {
    return neighborhoods;
  }

  public void setNeighborhoods(Neighborhoods neighborhoods) {
    this.neighborhoods = neighborhoods;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
