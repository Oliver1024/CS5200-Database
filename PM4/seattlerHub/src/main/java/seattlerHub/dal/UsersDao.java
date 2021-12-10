package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import seattlerHub.model.*;


public class UsersDao {
	
	protected ConnectionManager connectionManager;
	private static UsersDao instance = null;
	protected UsersDao() {
		connectionManager = new ConnectionManager();
	}

	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}
	
	
	public Users create(Users user) throws SQLException {
		String insertUser = "INSERT INTO Users(UserName,Password,FirstName,LastName,Email,Phone,HousingId,IfBioVisible, IsAuthenticatedResident) VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, user.getUserName());
			insertStmt.setString(2, user.getPassword());
			insertStmt.setString(3, user.getFirstName());
			insertStmt.setString(4, user.getLastName());
			insertStmt.setString(5, user.getEmail());
			insertStmt.setInt(6, user.getPhone());
			insertStmt.setNull(7, Types.NULL);
//			insertStmt.setInt(7, user.getHousing().getHousingId());
			insertStmt.setBoolean(8, user.getIfBioVisible());
			insertStmt.setBoolean(9, user.getAuthenticatedResident());
			insertStmt.executeUpdate();
			return user;
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
	
	public Users getUserByEmail(String email) throws SQLException {
		String selectUser = "SELECT UserName, Password,FirstName,LastName,Email,Phone,HousingId,IfBioVisible, IsAuthenticatedResident " +
				"FROM Users " +
				"WHERE Email=?; ";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		HousingDao housingDao = HousingDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, email);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String userName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				//String email = results.getString("Email");
				int phone = results.getInt("Phone");
				Housing housing = housingDao.getHousingByHousingId(results.getInt("HousingID"));
				boolean ifBioVisible = results.getBoolean("IfBioVisible");
				boolean isAuthenticatedResident = results.getBoolean("IsAuthenticatedResident");
					
				Users user = new Users(userName, password,firstName,lastName, email, phone, housing, ifBioVisible, isAuthenticatedResident);
				return user;
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
	
	public Users getUserByUserName(String userName) throws SQLException {
		String selectUser = "SELECT UserName, Password,FirstName,LastName,Email,Phone,HousingId,IfBioVisible, IsAuthenticatedResident " +
				"FROM Users " +
				"WHERE UserName=?; ";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		HousingDao housingDao = HousingDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				int phone = results.getInt("Phone");
					
				Users user = new Users (resultUserName, password,firstName,lastName, email, phone, null, false, false);
				return user;
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
	
	public Users updateUsersEmail(Users user, String newEmail) throws SQLException {
		String updateUser = "UPDATE Users SET Email=? WHERE UserName=?;";
		
		Connection connection = null;
		PreparedStatement updateStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newEmail);
			updateStmt.setString(2, user.getUserName());
			results = updateStmt.executeQuery();
		
			user.setEmail(newEmail);
			return user;
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
	
	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
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
