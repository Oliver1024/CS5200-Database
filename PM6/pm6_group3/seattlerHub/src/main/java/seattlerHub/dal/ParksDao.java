package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import seattlerHub.model.*;

public class ParksDao {
	protected ConnectionManager connectionManager;

	private static ParksDao instance = null;
	protected ParksDao() {
		connectionManager = new ConnectionManager();
	}

	public static ParksDao getInstance() {
		if(instance == null) {
			instance = new ParksDao();
		}
		return instance;
	}
	
	
	public List<Parks> getParksByName(String name) throws SQLException {
		List<Parks> parks = new ArrayList<>();
		String selectParks = "SELECT ParkId,ParkName,Address,Zipcode,"
				+ "Longitude,Latitude FROM Restaurants WHERE ParkName=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectParks);
			selectStmt.setString(1, name);
			results = selectStmt.executeQuery();
			NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
			
			while(results.next()) {
				int parkId = results.getInt("ParkId");
				String parkName = results.getString("ParkName");
				Neighborhoods neighborhoods = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
				Double longitude = results.getDouble("Longitude");
				Double latitude = results.getDouble("Latitude");
				String address = results.getNString("Address");
				
				Parks park = new Parks(parkId,parkName,address,neighborhoods,longitude,latitude);
				parks.add(park);	
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
		return parks;
	}
	
	
	public int getNumOfParksWithinCertainMilesOfHousing(Housing housing, int radius) throws SQLException {
		String selectParks = "SELECT COUNT(*) AS NumOfParks FROM Parks "
				+ "WHERE Longitude != 0 AND Latitude != 0 AND (SQRT(POWER(69.1 * (Latitude - ?), 2) "
				+ "+ POWER(69.1 * (? - Longitude ) * COS(Latitude / 57.3), 2))) <= ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectParks);
			selectStmt.setDouble(1, housing.getLatitude());
			selectStmt.setDouble(2, housing.getLongitude());
			selectStmt.setInt(3, radius);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfParks = results.getInt("NumOfParks");
				return numOfParks;
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
		
	public int getNumOfParksByNeighborhood(Neighborhoods neighborhood) throws SQLException {
		String selectParks = "SELECT COUNT(*) AS NumOfParks FROM Parks WHERE Zipcode = ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectParks);
			selectStmt.setInt(1, neighborhood.getZipcode());
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfParks = results.getInt("NumOfParks");
				return numOfParks;
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
	
	public List<Housing> getTop20HousingsWithMostParks() throws SQLException {
		List<Housing> housings = new ArrayList<>();
		String parkDistance = "(SQRT(POWER(69.1 * (Parks.Latitude - Housing.Latitude), 2) "
				+ "+ POWER(69.1 * ( Housing.Longitude - Parks.Longitude ) * COS(Parks.Latitude / 57.3), 2))) <= 1 ";
		String selectHousings = "SELECT HousingId, COUNT(*) AS NumNearbyParks FROM Parks CROSS JOIN Housing "
				+ "WHERE Parks.Longitude != 0 AND Parks.Latitude != 0 AND " 
				+ parkDistance
				+ "GROUP BY HousingId "
				+ "ORDER BY NumNearbyParks DESC LIMIT 20";
	
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
