package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import seattlerHub.model.*;

public class CrimeCasesDao {
	protected ConnectionManager connectionManager;

	private static CrimeCasesDao instance = null;
	protected CrimeCasesDao() {
		connectionManager = new ConnectionManager();
	}

	public static CrimeCasesDao getInstance() {
		if(instance == null) {
			instance = new CrimeCasesDao();
		}
		return instance;
	}
	
	public int getNumOfCrimeCasesWithinCertainMilesOfHousing(Housing housing, int radius) throws SQLException {
		String selectCrimeCases = "SELECT COUNT(*) AS NumOfCrimeCases FROM CrimeCases "
				+ "WHERE Longitude != 0 AND Latitude != 0 AND (SQRT(POWER(69.1 * (Latitude - ?), 2) "
				+ "+ POWER(69.1 * (? - Longitude ) * COS(Latitude / 57.3), 2))) <= ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCrimeCases);
			selectStmt.setDouble(1, housing.getLatitude());
			selectStmt.setDouble(2, housing.getLongitude());
			selectStmt.setInt(3, radius);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfCrimeCases = results.getInt("NumOfCrimeCases");
				return numOfCrimeCases;
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
	
	
	public int getNumOfCrimeCasesWithinCertainMilesOfHousingFromCertainDay(Housing housing, int radius, Date date) throws SQLException {
		String selectCrimeCases = "SELECT COUNT(*) AS NumOfCrimeCases FROM CrimeCases "
				+ "WHERE Longitude != 0 AND Latitude != 0 AND (SQRT(POWER(69.1 * (Latitude - ?), 2) "
				+ "+ POWER(69.1 * (? - Longitude ) * COS(Latitude / 57.3), 2))) <= ? "
				+ "AND ReportDate >= ?";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCrimeCases);
			selectStmt.setDouble(1, housing.getLatitude());
			selectStmt.setDouble(2, housing.getLongitude());
			selectStmt.setInt(3, radius);
			selectStmt.setDate(4, (java.sql.Date) date);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfCrimeCases = results.getInt("NumOfCrimeCases");
				return numOfCrimeCases;
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
	
	public int getNumOfCrimeCasesByNeighborhood(Neighborhoods neighborhood) throws SQLException {
		String selectCrimeCases = "SELECT COUNT(*) AS NumOfCrimeCases FROM CrimeCases WHERE Zipcode = ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCrimeCases);
			selectStmt.setInt(1, neighborhood.getZipcode());
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfCrimeCases = results.getInt("NumOfCrimeCases");
				return numOfCrimeCases;
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
	
	
	public int getNumOfCrimeCasesByNeighborhoodFromCertainDate(Neighborhoods neighborhood, Date date) throws SQLException {
		String selectCrimeCases = "SELECT COUNT(*) AS NumOfCrimeCases FROM CrimeCases WHERE Zipcode = ? AND ReportDate >= ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCrimeCases);
			selectStmt.setInt(1, neighborhood.getZipcode());
			selectStmt.setDate(2, (java.sql.Date) date);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfCrimeCases = results.getInt("NumOfCrimeCases");
				return numOfCrimeCases;
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
}
