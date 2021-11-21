package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import review.model.Companies;
import review.model.Restaurants;
import review.model.TakeOutRestaurants;

/**
 * 
 * @author Kejian Tong
 *
 */
public class TakeOutRestaurantsDao extends RestaurantsDao{
	protected ConnectionManager connectionManager;

	private static TakeOutRestaurantsDao instance = null;
	
	protected TakeOutRestaurantsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static TakeOutRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new TakeOutRestaurantsDao();
		}
		return instance;
	}
	
	public TakeOutRestaurants create(TakeOutRestaurants takeOutRestaurant) throws SQLException {

		create(new Restaurants(takeOutRestaurant.getName(), takeOutRestaurant.getDescription(), takeOutRestaurant.getMenu(),
				takeOutRestaurant.getHours(), takeOutRestaurant.getActive(), takeOutRestaurant.getCuisine(), takeOutRestaurant.getStreet1(),
				takeOutRestaurant.getStreet2(), takeOutRestaurant.getCity(), takeOutRestaurant.getState(), takeOutRestaurant.getZip(),
				takeOutRestaurant.getCompany()));

		String insertSitDownRestaurant = "INSERT INTO TakeOutRestaurant(RestaurantId, MaxWaitTime) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSitDownRestaurant);
			insertStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			insertStmt.setInt(2, takeOutRestaurant.getMaxWaitingTime());
			insertStmt.executeUpdate();
			return takeOutRestaurant;
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
		}
	}
	
	public TakeOutRestaurants getTakeOutRestaurantById(int takeOutRestaurantId) throws SQLException {

		String selectTakeOutRestaurant =
			"SELECT TakeOutRestaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,Cuisine,Street1,Street2,City,State,Zip,CompanyName, MaxWaitingTime " +
			"FROM TakeOutRestaurants INNER JOIN Restaurants " +
			"  ON TakeOutRestaurants.RestaurantId = Restaurants.RestaurantId " +
			"WHERE TakeOutRestaurants.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTakeOutRestaurant);
			selectStmt.setInt(1, takeOutRestaurantId);
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int resultTakeOutRestaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.Cuisine cuisine = Restaurants.Cuisine.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				Companies company = companiesDao.getCompanyByCompanyName(results.getString("CompanyName"));
				int maxWaitingTime = results.getInt("MaxWaitTime");
				
				TakeOutRestaurants restaurant  = new TakeOutRestaurants(resultTakeOutRestaurantId, name, description, menu, hours, active, 
														  cuisine, street1, street2, city, state, zip, company, maxWaitingTime);
				return restaurant;
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
	
	public List<TakeOutRestaurants> getTakeOutRestaurantsByCompanyName(String companyName) throws SQLException {
		List<TakeOutRestaurants> result = new ArrayList<>();
		String selectTakeOutRestaurants = 
				"SELECT TakeOutRestaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName, Capacity " +
				"FROM TakeOutRestaurants INNER JOIN Restaurants " +
				"  ON TakeOutRestaurants.RestaurantId = Restaurants.RestaurantId " +
				"WHERE TakeOutRestaurants.CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTakeOutRestaurants);
			selectStmt.setString(1, companyName);
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			results = selectStmt.executeQuery();

			while(results.next()) {
				int resultTakeOutRestaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.Cuisine cuisine = Restaurants.Cuisine.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				Companies company = companiesDao.getCompanyByCompanyName(results.getString("CompanyName"));
				int maxWaitingTime = results.getInt("MaxWaitingTime");
				
				TakeOutRestaurants restaurant  = new TakeOutRestaurants(resultTakeOutRestaurantId, name, description, menu, hours, active, 
														  cuisine, street1, street2, city, state, zip, company, maxWaitingTime);
				result.add(restaurant);
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
		return result;
	}
	
	public TakeOutRestaurants delete(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		String deleteTakeOutRestaurant = "DELETE FROM TakeOutRestaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTakeOutRestaurant);
			deleteStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			deleteStmt.executeUpdate();


			super.delete(takeOutRestaurant);

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
