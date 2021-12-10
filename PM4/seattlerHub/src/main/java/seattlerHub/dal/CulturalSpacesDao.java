package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import seattlerHub.model.*;

public class CulturalSpacesDao {
	protected ConnectionManager connectionManager;

	private static CulturalSpacesDao instance = null;
	protected CulturalSpacesDao() {
		connectionManager = new ConnectionManager();
	}

	public static CulturalSpacesDao getInstance() {
		if(instance == null) {
			instance = new CulturalSpacesDao();
		}
		return instance;
	}
	
	public List<CulturalSpaces> getCulturalSpacesByName(String name) throws SQLException {
		List<CulturalSpaces> culturalSpaces = new ArrayList<>();
		String selectCulturalSpaces = "SELECT CulturalSpaceId,CulturalSpaceName,DominantDiscipline,"
				+ "Zipcode,Address,Longitude,Latitude FROM CulturalSpaces WHERE CulturalSpaceName=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCulturalSpaces);
			selectStmt.setString(1, name);
			results = selectStmt.executeQuery();
			NeighborhoodsDao neighborhoodsDao = NeighborhoodsDao.getInstance();
			
			while(results.next()) {
				int culturalSpaceId = results.getInt("CulturalSpaceId");
				String culturalSpaceName = results.getString("CulturalSpaceName");
				String dominantDiscipline = results.getNString("DominantDiscipline");
				Neighborhoods neighborhoods = neighborhoodsDao.getNeighborhoodByZipcode(results.getInt("Zipcode"));
				Double longitude = results.getDouble("Longitude");
				Double latitude = results.getDouble("Latitude");
				String address = results.getNString("Address");
				
				CulturalSpaces culturalSpace = new CulturalSpaces(culturalSpaceId,culturalSpaceName,dominantDiscipline,neighborhoods,
						address,longitude,latitude);
				culturalSpaces.add(culturalSpace);	
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
		return culturalSpaces;
	}
	
	public int getCulturalSpacesWithinCertainMilesOfHousing(Housing housing, int radius) throws SQLException {
		String selectCulturalSpaces = "SELECT COUNT(*) AS NumOfCulturalSpaces FROM CulturalSpaces "
				+ "WHERE Longitude != 0 AND Latitude != 0 AND (SQRT(POWER(69.1 * (Latitude - ?), 2) "
				+ "+ POWER(69.1 * (? - Longitude ) * COS(Latitude / 57.3), 2))) <= ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCulturalSpaces);
			selectStmt.setDouble(1, housing.getLatitude());
			selectStmt.setDouble(2, housing.getLongitude());
			selectStmt.setInt(3, radius);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfCulturalSpaces = results.getInt("NumOfCulturalSpaces");
				return numOfCulturalSpaces;
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
	
	public int getCulturalSpacesByNeighborhood(Neighborhoods neighborhood) throws SQLException {
		String selectCulturalSpaces = "SELECT COUNT(*) AS NumOfCulturalSpaces FROM CulturalSpaces WHERE Zipcode = ?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCulturalSpaces);
			selectStmt.setInt(1, neighborhood.getZipcode());
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int numOfCulturalSpaces = results.getInt("NumOfCulturalSpaces");
				return numOfCulturalSpaces;
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
