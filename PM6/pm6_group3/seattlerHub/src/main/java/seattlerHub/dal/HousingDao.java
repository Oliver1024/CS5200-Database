package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import seattlerHub.model.*;

public class HousingDao {
	
	protected ConnectionManager connectionManager;
	private static HousingDao instance = null;
	protected HousingDao() {
		connectionManager = new ConnectionManager();
	}

	public static HousingDao getInstance() {
		if(instance == null) {
			instance = new HousingDao();
		}
		return instance;
	}
	
	public Housing create(Housing housing) throws SQLException {
		String insertHousing = "INSERT INTO Housing(Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link) VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertHousing,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, housing.getNeighborhoods().getZipcode());
			insertStmt.setString(2, housing.getName());
			insertStmt.setString(3, housing.getAddress());
			insertStmt.setDouble(4, housing.getLongitude());
			insertStmt.setDouble(5, housing.getLatitude());
			insertStmt.setInt(6, housing.getRentalPrice());
			insertStmt.setString(7, housing.getImgLink());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int housingId = -1;
			if(resultKey.next()) {
				housingId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			housing.setHousingId(housingId);
			return housing;
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
	
	public Housing delete(Housing housing) throws SQLException {
		String deleteHousing = "DELETE FROM Housing WHERE HousingId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteHousing);
			deleteStmt.setInt(1, housing.getHousingId());
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
	
	
	public List<Housing> getAllHousing() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String selectHousing =
				"SELECT HousingId,Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link "
				+ "FROM Housing;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectHousing);
				results = selectStmt.executeQuery();
				NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
				while(results.next()) {
					int housingId = results.getInt("HousingId");
					Neighborhoods neighborhood = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
					String name = results.getString("Name");
					String address = results.getString("Address");
					double longitude = results.getDouble("Longitude");
					double latitude = results.getDouble("Latitude");
					int rentalPrice = results.getInt("RentalPrice");
					String imgLink = results.getString("Link");

					Housing housing = new Housing(housingId, neighborhood, name, address, longitude, latitude, rentalPrice, imgLink);
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
	
	public List<Housing> getTopTenRatedHousingHighToLow() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String selectHousing =
				"SELECT Housing.HousingId, Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link, AVG(Reviews.Rating) AS AVG_RATING "
				+ "FROM Reviews "
				+ "INNER JOIN Housing "
				+ "ON Reviews.HousingId = Housing.HousingId "
			    + "GROUP BY Reviews.HousingId "
				+ "ORDER BY AVG_RATING DESC "
				+ "LIMIT 10; ";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectHousing);
				results = selectStmt.executeQuery();
				NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
				while(results.next()) {
					int housingId = results.getInt("HousingId");
					Neighborhoods neighborhood = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
					String name = results.getString("Name");
					String address = results.getString("Address");
					double longitude = results.getDouble("Longitude");
					double latitude = results.getDouble("Latitude");
					int rentalPrice = results.getInt("RentalPrice");
					String imgLink = results.getString("Link");

					Housing housing = new Housing(housingId, neighborhood, name, address, longitude, latitude, rentalPrice, imgLink);
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
	
	
	public List<Housing> getTopTenRatedHousingLowToHigh() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String selectHousing =
				"SELECT Housing.HousingId, Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link, AVG(Reviews.Rating) AS AVG_RATING "
				+ "FROM Reviews "
				+ "INNER JOIN Housing "
				+ "ON Reviews.HousingId = Housing.HousingId "
			    + "GROUP BY Reviews.HousingId "
				+ "ORDER BY AVG_RATING ASC "
				+ "LIMIT 10; ";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectHousing);
				results = selectStmt.executeQuery();
				NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
				while(results.next()) {
					int housingId = results.getInt("HousingId");
					Neighborhoods neighborhood = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
					String name = results.getString("Name");
					String address = results.getString("Address");
					double longitude = results.getDouble("Longitude");
					double latitude = results.getDouble("Latitude");
					int rentalPrice = results.getInt("RentalPrice");
					String imgLink = results.getString("Link");

					Housing housing = new Housing(housingId, neighborhood, name, address, longitude, latitude, rentalPrice, imgLink);
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
	
	public List<Housing> getTopTenHousingPriceHighToLow() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String selectHousing =
				"SELECT HousingId, Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link "
				+ "FROM Housing "
				+ "ORDER BY RentalPrice DESC "
				+ "LIMIT 10; ";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectHousing);
				results = selectStmt.executeQuery();
				NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
				while(results.next()) {
					int housingId = results.getInt("HousingId");
					Neighborhoods neighborhood = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
					String name = results.getString("Name");
					String address = results.getString("Address");
					double longitude = results.getDouble("Longitude");
					double latitude = results.getDouble("Latitude");
					int rentalPrice = results.getInt("RentalPrice");
					String imgLink = results.getString("Link");

					Housing housing = new Housing(housingId, neighborhood, name, address, longitude, latitude, rentalPrice, imgLink);
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
	
	
	public List<Housing> getTopTenHousingPriceLowToHigh() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String selectHousing =
				"SELECT HousingId, Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link "
				+ "FROM Housing "
				+ "ORDER BY RentalPrice ASC "
				+ "LIMIT 10; ";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectHousing);
				results = selectStmt.executeQuery();
				NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
				while(results.next()) {
					int housingId = results.getInt("HousingId");
					Neighborhoods neighborhood = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
					String name = results.getString("Name");
					String address = results.getString("Address");
					double longitude = results.getDouble("Longitude");
					double latitude = results.getDouble("Latitude");
					int rentalPrice = results.getInt("RentalPrice");
					String imgLink = results.getString("Link");

					Housing housing = new Housing(housingId, neighborhood, name, address, longitude, latitude, rentalPrice, imgLink);
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
	
	public Housing getHousingByHousingId(int housingId) throws SQLException {
		String selectHousing = "SELECT HousingId,Zipcode,Name,Address,Longitude,Latitude,RentalPrice,Link FROM Housing WHERE HousingId=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectHousing);
			selectStmt.setInt(1, housingId);
			results = selectStmt.executeQuery();
				
			if(results.next()) {
				int resultHousingId = results.getInt("HousingId");
				String name = results.getString("Name");
				String address = results.getString("Address");
				Neighborhoods neighborhood = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
				int rentalPrice = results.getInt("RentalPrice");
				Double longitude = results.getDouble("Longitude");
				Double latitude = results.getDouble("Latitude");
				String imgLink = results.getString("Link");
					
				Housing housing = new Housing(resultHousingId, neighborhood, name, address, longitude, latitude, rentalPrice, imgLink);
				
				return housing;
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
	
	public Housing updateHousing(Housing housing, String name, int rentalPrice) throws SQLException {
		String updateHousing = "UPDATE Housing SET Name=?, RentalPrice=? WHERE HousingId=?;";
		
		Connection connection = null;
		PreparedStatement updateStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateHousing);
			updateStmt.setString(1, name);
			updateStmt.setInt(2, rentalPrice);
			updateStmt.setInt(3, housing.getHousingId());
			updateStmt.executeUpdate();
			
			housing.setName(name);
			housing.setRentalPrice(rentalPrice);
			
			return housing;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
	}
	
}
