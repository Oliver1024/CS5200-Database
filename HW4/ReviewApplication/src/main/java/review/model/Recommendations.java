package review.model;

/**
 * 
 * @author Kejian Tong
 *
 */
public class Recommendations {
  protected Integer recommendationId;
  protected Users user;
  protected Restaurants restaurant;

  public Recommendations(Integer recommendationId, Users user, Restaurants restaurant) {
    this.recommendationId = recommendationId;
    this.user = user;
    this.restaurant = restaurant;
  }

  public Recommendations(Integer recommendationId) {
    this.recommendationId = recommendationId;
  }

  public Recommendations(Users user, Restaurants restaurant) {
    this.user = user;
    this.restaurant = restaurant;
  }

  public Integer getRecommendationId() {
    return recommendationId;
  }

  public void setRecommendationId(Integer recommendationId) {
    this.recommendationId = recommendationId;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }

  public Restaurants getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurants restaurant) {
    this.restaurant = restaurant;
  }
}
