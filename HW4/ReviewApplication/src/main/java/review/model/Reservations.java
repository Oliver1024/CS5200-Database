package review.model;

import java.sql.Timestamp;

/**
 * 
 * @author Kejian Tong
 *
 */
public class Reservations {
  protected Integer reservationId;
  protected Timestamp start;
  protected Timestamp end;
  protected Integer size;
  protected Users user;
  protected Restaurants restaurant;

  public Reservations(Integer reservationId, Timestamp start, Timestamp end, Integer size,
      Users user, Restaurants restaurant) {
    this.reservationId = reservationId;
    this.start = start;
    this.end = end;
    this.size = size;
    this.user = user;
    this.restaurant = restaurant;
  }

  public Reservations(Integer reservationId) {
    this.reservationId = reservationId;
  }

  public Reservations(Timestamp start, Timestamp end, Integer size, Users user,
      Restaurants restaurant) {
    this.start = start;
    this.end = end;
    this.size = size;
    this.user = user;
    this.restaurant = restaurant;
  }

  public Integer getReservationId() {
    return reservationId;
  }

  public void setReservationId(Integer reservationId) {
    this.reservationId = reservationId;
  }

  public Timestamp getStart() {
    return start;
  }

  public void setStart(Timestamp start) {
    this.start = start;
  }

  public Timestamp getEnd() {
    return end;
  }

  public void setEnd(Timestamp end) {
    this.end = end;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
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
