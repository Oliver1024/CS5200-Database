package seattlerHub.model;

import java.util.Date;

public class Reviews {
  protected Integer reviewId;
  protected Date created;
  protected String content;
  protected Double rating;
  protected Users users;
  protected Housing housing;

  public Reviews(Integer reviewId, Date created, String content, Double rating, Users users, Housing housing) {
    this.reviewId = reviewId;
    this.created = created;
    this.content = content;
    this.rating = rating;
    this.users = users;
    this.housing = housing;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public void setReviewId(Integer reviewId) {
    this.reviewId = reviewId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
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

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  public Housing getHousing() {
    return housing;
  }

  public void setHousing(Housing housing) {
    this.housing = housing;
  }
}
