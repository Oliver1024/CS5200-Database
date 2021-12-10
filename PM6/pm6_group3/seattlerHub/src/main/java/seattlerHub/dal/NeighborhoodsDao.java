package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import seattlerHub.model.*;

public class NeighborhoodsDao {
	protected ConnectionManager connectionManager;

	private static NeighborhoodsDao instance = null;
	protected NeighborhoodsDao() {
		connectionManager = new ConnectionManager();
	}

	public static NeighborhoodsDao getInstance() {
		if(instance == null) {
			instance = new NeighborhoodsDao();
		}
		return instance;
	}
	
	public List<Neighborhoods> getAllNeighborhoods() throws SQLException {
		List<Neighborhoods> neighborhoods = new ArrayList<>();
		String selectNeighborhoods = "SELECT Zipcode, AvgRentalPriceOneBedroom FROM Neighborhoods;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNeighborhoods);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int zipcode = results.getInt("Zipcode");
				int avgRentalPriceOneBedroom = results.getInt("AvgRentalPriceOneBedroom");
				
				Neighborhoods neighborhood = new Neighborhoods(zipcode, avgRentalPriceOneBedroom);
				neighborhoods.add(neighborhood);	
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
		return neighborhoods;
	}
	
	public Neighborhoods getNeighborhoodByZipcode(int zipcode) throws SQLException {
		String selectNeighborhood = "SELECT Zipcode, AvgRentalPriceOneBedroom FROM Neighborhoods WHERE Zipcode=?;";
			
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
			
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNeighborhood);
			selectStmt.setInt(1, zipcode);
			results = selectStmt.executeQuery();
				
			if(results.next()) {
				int resultZipcode = results.getInt("Zipcode");
				int avgRentalPriceOneBedroom = results.getInt("AvgRentalPriceOneBedroom");
					
				Neighborhoods neighborhood = new Neighborhoods(resultZipcode, avgRentalPriceOneBedroom);
				return neighborhood;
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
	
	public Neighborhoods updateNeighborhoodAvgRentalPrice(Neighborhoods neighborhood, int newPrice) throws SQLException {
		String updateNeighborhood = "UPDATE Neighborhoods SET AvgRentalPriceOneBedroom=? WHERE Zipcode=?;";
		
		Connection connection = null;
		PreparedStatement updateStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateNeighborhood);
			updateStmt.setInt(1, newPrice);
			updateStmt.setInt(2, neighborhood.getZipcode());
			results = updateStmt.executeQuery();
		
			neighborhood.setAvgRentalPriceOneBedroom(newPrice);
			return neighborhood;
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
