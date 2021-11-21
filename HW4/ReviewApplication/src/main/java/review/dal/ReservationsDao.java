package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import review.model.Reservations;
import review.model.Restaurants;
import review.model.SitDownRestaurant;
import review.model.Users;

/**
 * 
 * @author Kejian Tong
 *
 */
public class ReservationsDao {
  protected  ConnectionManager connectionManager;

  private static ReservationsDao instance = null;

  protected ReservationsDao() {
    connectionManager = new ConnectionManager();
  }

  public static ReservationsDao getInstance() {
    if(instance == null) {
      instance = new ReservationsDao();
    }
    return instance;
  }

  public Reservations create(Reservations reservation) throws SQLException {
    String insertReservation = "INSERT INTO Reservations(Start, End, Size, UserName, RestaurantId) VALUES (?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;

    try{
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setTimestamp(1, new Timestamp(reservation.getStart().getTime()));
      insertStmt.setTimestamp(2, new Timestamp(reservation.getEnd().getTime()));
      insertStmt.setInt(3, reservation.getSize());
      insertStmt.setString(4, reservation.getUser().getUserName());
      insertStmt.setInt(5,reservation.getRestaurant().getRestaurantId());
      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int reservationId = -1;
      
      if(resultKey.next()) {
        reservationId = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto generated key.");
      }
      reservation.setReservationId(reservationId);
      return reservation;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(insertStmt != null) {
        insertStmt.close();
      }
      if(resultKey != null) {
        resultKey.close();
      }
    }
  }

  public Reservations getReservationById(int reservationId) throws SQLException{
    String selectReservation = "SELECT ReservationId, Start, End, Size, UserName, RestaurantId "
        + "FROM Reservations WHERE ReservationId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReservation);
      selectStmt.setInt(1, reservationId);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
      
      if(results.next()) {
        Integer resultReservationId = results.getInt("ReservationId");
        Timestamp start = results.getTimestamp("Start");
        Timestamp end = results.getTimestamp("End");
        Integer size = results.getInt("Size");
        String userName = results.getString("UserName");
        Integer restaurantId = results.getInt("RestaurantId");
        Users user = usersDao.getUserByUserName(userName);
        Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
        Reservations reservation = new Reservations(resultReservationId, start, end, size, user, restaurant);
        return reservation;
        
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Reservations> getReservationsByUserName(String userName) throws SQLException{
    List<Reservations> reservations = new ArrayList<>();
    String selectReservations = "SELECT ReservationId, Start, End, Size, UserName, RestaurantId"
        + "FROM Reservations WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReservations);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();

      while (results.next()) {
        Integer reservationId = results.getInt("ReservationId");
        Timestamp start = results.getTimestamp("Start");
        Timestamp end = results.getTimestamp("End");
        Integer size = results.getInt("Size");
        Integer restaurantId = results.getInt("RestaurantId");
        Users user = usersDao.getUserByUserName(userName);
        Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
        Reservations reservation = new Reservations(reservationId, start, end, size, user, restaurant);
        reservations.add(reservation);
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return reservations;
  }

  public List<Reservations> getReservationsBySitDownRestaurantId(int sitDownRestaurantId)
    throws SQLException {
    List<Reservations> reservations = new ArrayList<>();
    String selectReservations = "SELECT Reservations.ReservationId, Reservations.Start, Reservations.End, "
        + "Reservations.Size, Reservations.UserName, Reservations.RestaurantId"
        + "FROM Reservations INNER JOIN SitdownRestaurant "
        + "ON Reservations.RestaurantId = SitdownResaurant.RestaurantId "
        + "WHERE Reservations.RestaurantId=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReservations);
      selectStmt.setInt(1, sitDownRestaurantId);
      results = selectStmt.executeQuery();
      UsersDao usersDao = UsersDao.getInstance();
      SitDownRestaurantDao sitDownRestaurantsDao = SitDownRestaurantDao.getInstance();

      while (results.next()) {
        Integer reservationId = results.getInt("Reservations.ReservationId");
        Timestamp start = results.getTimestamp("Reservations.Start");
        Timestamp end = results.getTimestamp("Reservations.End");
        Integer size = results.getInt("Reservations.Size");
        String userName = results.getString("Reservations.UserName");
        Users user = usersDao.getUserByUserName(userName);
        SitDownRestaurant sitDownRestaurant = sitDownRestaurantsDao.getSitDownRestaurantById(sitDownRestaurantId);
        Reservations reservation = new Reservations(reservationId, start, end, size, user, sitDownRestaurant);
        reservations.add(reservation);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return reservations;
  }

  public Reservations delete(Reservations reservation) throws SQLException {
    String deleteReservation = "DELETE FROM Reservations WHERE ReservationId=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteReservation);
      deleteStmt.setInt(1, reservation.getReservationId());
      deleteStmt.executeUpdate();
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }

}
