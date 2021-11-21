package review.dal;

import review.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Kejian Tong
 *
 */
public class SitDownRestaurantDao extends RestaurantsDao{
	protected ConnectionManager connectionManager;

	private static SitDownRestaurantDao instance = null;
	
	protected SitDownRestaurantDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static SitDownRestaurantDao getInstance() {
		if(instance == null) {
			instance = new SitDownRestaurantDao();
		}
		return instance;
	}
	
	public SitDownRestaurant create(SitDownRestaurant sitDownRestaurant) throws SQLException {
		create(new Restaurants(sitDownRestaurant.getName(), sitDownRestaurant.getDescription(), sitDownRestaurant.getMenu(),
				sitDownRestaurant.getHours(), sitDownRestaurant.getActive(), sitDownRestaurant.getCuisine(), sitDownRestaurant.getStreet1(),
				sitDownRestaurant.getStreet2(), sitDownRestaurant.getCity(), sitDownRestaurant.getState(), sitDownRestaurant.getZip(),
				sitDownRestaurant.getCompany()));

		String insertSitDownRestaurant = "INSERT INTO SitDownRestaurant(RestaurantId, capacity) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSitDownRestaurant);
			insertStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			insertStmt.setInt(2, sitDownRestaurant.getCapacity());
			insertStmt.executeUpdate();
			return sitDownRestaurant;
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
	
	public SitDownRestaurant getSitDownRestaurantById(int sitDownRestaurantId) throws SQLException {
		
		String selectSitDownRestaurant =
			"SELECT SitDownRestaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName, Capacity " +
			"FROM SitDownRestaurants INNER JOIN Restaurants " +
			"  ON SitDownRestaurants.RestaurantId = Restaurants.RestaurantId " +
			"WHERE SitDownRestaurants.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSitDownRestaurant);
			selectStmt.setInt(1, sitDownRestaurantId);
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int resultSitDownRestaurantId = results.getInt("RestaurantId");
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
				int zipcode = results.getInt("Zip");
				Companies company = companiesDao.getCompanyByCompanyName(results.getString("CompanyName"));
				int capacity = results.getInt("Capacity");
				
				SitDownRestaurant restaurant  = new SitDownRestaurant(resultSitDownRestaurantId, name, description, menu, hours, active, cuisine, street1, street2, city, state, zipcode, company, capacity);
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

	public List<SitDownRestaurant> getSitDownRestaurantsByCompanyName(String companyName) throws SQLException {
		List<SitDownRestaurant> result = new ArrayList<>();
		String selectSitDownRestaurant = 
				"SELECT SitDownRestaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName, Capacity " +
				"FROM SitDownRestaurants INNER JOIN Restaurants " +
				"  ON SitDownRestaurants.RestaurantId = Restaurants.RestaurantId " +
				"WHERE SitDownRestaurants.CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSitDownRestaurant);
			selectStmt.setString(1, companyName);
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			results = selectStmt.executeQuery();

			while(results.next()) {
				int resultSitDownRestaurantId = results.getInt("RestaurantId");
				String name = results.getString("Name");
				String description = results.getString("Description");
				String menu = results.getString("Menu");
				String hours = results.getString("Hours");
				boolean active = results.getBoolean("Active");
				Restaurants.Cuisine cuisineType = Restaurants.Cuisine.valueOf(results.getString("CuisineType"));
				String street1 = results.getString("Street1");
				String street2 = results.getString("Street2");
				String city = results.getString("City");
				String state = results.getString("State");
				int zip = results.getInt("Zip");
				Companies company = companiesDao.getCompanyByCompanyName(results.getString("CompanyName"));
				int capacity = results.getInt("Capacity");
				
				SitDownRestaurant restaurant  = new SitDownRestaurant(resultSitDownRestaurantId, name, description, menu, hours, active, 
														  cuisineType, street1, street2, city, state, zip, company, capacity);
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
	
	public SitDownRestaurant delete(SitDownRestaurant sitDownRestaurant) throws SQLException {
		String deleteSitDownRestaurant = "DELETE FROM SitDownRestaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSitDownRestaurant);
			deleteStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			deleteStmt.executeUpdate();


			super.delete(sitDownRestaurant);

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
