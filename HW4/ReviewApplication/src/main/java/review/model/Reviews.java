package review.model;

import java.sql.Timestamp;

/**
 * 
 * @author Kejian Tong
 *
 */
public class Reviews {
  protected Integer reviewId;
  protected Timestamp created;
  protected String content;
  protected Double rating;
  protected Users user;
  protected Restaurants restaurant;

  public Reviews(Integer reviewId, Timestamp created, String content, Double rating,
      Users user, Restaurants restaurant) {
    this.reviewId = reviewId;
    this.created = created;
    this.content = content;
    this.rating = rating;
    this.user = user;
    this.restaurant = restaurant;
  }

  public Reviews(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public Reviews(Timestamp created, String content, Double rating, Users user,
      Restaurants restaurant) {
    this.created = created;
    this.content = content;
    this.rating = rating;
    this.user = user;
    this.restaurant = restaurant;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public Timestamp getCreated() {
    return created;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
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
