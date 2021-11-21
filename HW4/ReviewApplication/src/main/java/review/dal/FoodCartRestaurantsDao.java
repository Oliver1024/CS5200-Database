package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import review.model.Companies;
import review.model.FoodCartRestaurants;
import review.model.Restaurants;

/**
 * 
 * @author Kejian Tong
 *
 */
public class FoodCartRestaurantsDao extends RestaurantsDao{
	protected ConnectionManager connectionManager;

	private static FoodCartRestaurantsDao instance = null;
	
	protected FoodCartRestaurantsDao() {
		connectionManager = new ConnectionManager();
	}
	
	public static FoodCartRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new FoodCartRestaurantsDao();
		}
		return instance;
	}
	
	public FoodCartRestaurants create(FoodCartRestaurants foodCartRestaurant) throws SQLException {
		
		create(new Restaurants(foodCartRestaurant.getName(), foodCartRestaurant.getDescription(), foodCartRestaurant.getMenu(),
				foodCartRestaurant.getHours(), foodCartRestaurant.getActive(), foodCartRestaurant.getCuisine(), foodCartRestaurant.getStreet1(),
				foodCartRestaurant.getStreet2(), foodCartRestaurant.getCity(), foodCartRestaurant.getState(), foodCartRestaurant.getZip(),
				foodCartRestaurant.getCompany()));

		String insertFoodCartRestaurant = "INSERT INTO FoodCartRestaurant(RestaurantId, Licensed) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFoodCartRestaurant);
			insertStmt.setInt(1, foodCartRestaurant.getRestaurantId());
			insertStmt.setBoolean(2, foodCartRestaurant.getLicensed());
			insertStmt.executeUpdate();
			return foodCartRestaurant;
			
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
	
	public FoodCartRestaurants getFoodCartRestaurantById(int foodCartRestaurantId) throws SQLException {
		
		String selectFoodCartRestaurant =
			"SELECT FoodCartRestaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName,Licensed " +
			"FROM FoodCartRestaurants INNER JOIN Restaurants " +
			"  ON FoodCartRestaurants.RestaurantId = Restaurants.RestaurantId " +
			"WHERE FoodCartRestaurants.RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFoodCartRestaurant);
			selectStmt.setInt(1, foodCartRestaurantId);
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int resultFoodCartRestaurantId = results.getInt("RestaurantId");
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
				boolean licensed = results.getBoolean("Licensed");
				
				FoodCartRestaurants restaurant  = new FoodCartRestaurants(resultFoodCartRestaurantId, name, description, menu, hours, active, 
														  cuisine, street1, street2, city, state, zip, company, licensed);
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
	
	
	public List<FoodCartRestaurants> getFoodCartRestaurantsByCompanyName(String companyName) throws SQLException {
		List<FoodCartRestaurants> result = new ArrayList<>();
		String selectFoodCartRestaurant = 
				"SELECT FoodCartRestaurants.RestaurantId AS RestaurantId, Name,Description,Menu,Hours,Active,CuisineType,Street1,Street2,City,State,Zip,CompanyName,Licensed" +
				"FROM FoodCartRestaurants INNER JOIN Restaurants " +
				"  ON FoodCartRestaurants.RestaurantId = Restaurants.RestaurantId " +
				"WHERE FoodCartRestaurants.CompanyName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectFoodCartRestaurant);
			selectStmt.setString(1, companyName);
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			results = selectStmt.executeQuery();

			while(results.next()) {
				int resultFoodCartRestaurantId = results.getInt("RestaurantId");
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
				boolean licensed = results.getBoolean("Licensed");
				FoodCartRestaurants restaurant  = new FoodCartRestaurants(resultFoodCartRestaurantId, name, description, menu, hours, active, 
														  cuisine, street1, street2, city, state, zip, company, licensed);
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
	
	public FoodCartRestaurants delete(FoodCartRestaurants foodCartRestaurant) throws SQLException {
		String deleteFoodCartRestaurant = "DELETE FROM FoodCartRestaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFoodCartRestaurant);
			deleteStmt.setInt(1, foodCartRestaurant.getRestaurantId());
			deleteStmt.executeUpdate();

			super.delete(foodCartRestaurant);

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
