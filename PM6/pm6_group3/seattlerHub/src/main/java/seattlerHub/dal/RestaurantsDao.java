package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import seattlerHub.model.*;

public class RestaurantsDao {
	protected ConnectionManager connectionManager;

	private static RestaurantsDao instance = null;
	protected RestaurantsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RestaurantsDao getInstance() {
		if(instance == null) {
			instance = new RestaurantsDao();
		}
		return instance;
	}

	
	public List<Restaurants> getRestaurantsByName(String name) throws SQLException {
		List<Restaurants> restaurants = new ArrayList<>();
		String selectRestaurants = "SELECT RestaurantId,RestaurantName,Description,Zipcode,"
				+ "Longitude,Latitude,Address FROM Restaurants WHERE RestaurantName=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, name);
			results = selectStmt.executeQuery();
			NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
			
			while(results.next()) {
				int restaurantId = results.getInt("RestaurantId");
				String restaurantName = results.getString("RestaurantName");
				String description = results.getNString("Description");
				Neighborhoods neighborhoods = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
				Double longitude = results.getDouble("Longitude");
				Double latitude = results.getDouble("Latitude");
				String address = results.getNString("Address");
				
				Restaurants restaurant = new Restaurants(restaurantId,restaurantName,description,neighborhoods,
						longitude,latitude,address);
				restaurants.add(restaurant);	
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
		return restaurants;
	}
	
	
	public int getNumOfRestaurantsWithinCertainMilesOfHousing(Housing housing, int radius) throws SQLException {
		String selectRestaurants = "SELECT COUNT(*) AS NumOfRestaurants FROM Restaurants "
				+ "WHERE Longitude != 0 AND Latitude != 0 AND (SQRT(POWER(69.1 * (Latitude - ?), 2) "
				+ "+ POWER(69.1 * (? - Longitude ) * COS(Latitude / 57.3), 2))) <= ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setDouble(1, housing.getLatitude());
			selectStmt.setDouble(2, housing.getLongitude());
			selectStmt.setInt(3, radius);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfRestaurants = results.getInt("NumOfRestaurants");
				return numOfRestaurants;
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
		return -1;	
	}
	
	
	public int getNumOfRestaurantsByNeighborhood(Neighborhoods neighborhood) throws SQLException {
		String selectRestaurants = "SELECT COUNT(*) AS NumOfRestaurants FROM Schools WHERE Zipcode = ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setInt(1, neighborhood.getZipcode());
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfRestaurants = results.getInt("NumOfRestaurants");
				return numOfRestaurants;
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
		return -1;
	}
	
	public List<Housing> getTop20HousingsWithMostRestaurants() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String restaurantDistance = "(SQRT(POWER(69.1 * (Restaurants.Latitude - Housing.Latitude), 2) "
				+ "+ POWER(69.1 * ( Housing.Longitude - Restaurants.Longitude ) * COS(Restaurants.Latitude / 57.3), 2))) <= 1 ";
		String selectHousings = "SELECT HousingId, COUNT(*) AS NumNearbyRestaurants FROM Housing CROSS JOIN Restaurants "
				+ "WHERE Restaurants.Longitude != 0 AND Restaurants.Latitude != 0 AND " 
				+ restaurantDistance
				+ "GROUP BY HousingId "
				+ "ORDER BY NumNearbyRestaurants DESC LIMIT 20";
	
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		HousingDao housingDao = HousingDao.getInstance();
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectHousings);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				int housingId = results.getInt("HousingId");
				Housing housing = housingDao.getHousingByHousingId(housingId);
				housings.add(housing);
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
		return housings;
	}
	
	
}
