package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import seattlerHub.model.*;

public class ProfessionalsDao extends UsersDao{
	
	protected ConnectionManager connectionManager;
	private static ProfessionalsDao instance = null;
	protected ProfessionalsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ProfessionalsDao getInstance() {
		if(instance == null) {
			instance = new ProfessionalsDao();
		}
		return instance;
	}
	
	public Professionals create(Professionals professional) throws SQLException {
		create(new Users(professional.getUserName(), professional.getPassword(), professional.getFirstName(), professional.getLastName(),
      professional.getEmail(), professional.getPhone(), professional.getHousing(), professional.getIfBioVisible(), professional.getAuthenticatedResident()));
		
		String insertProfessional = "INSERT INTO Professionals(UserName, Company) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertProfessional);
			insertStmt.setString(1, professional.getUserName());
			insertStmt.setString(2, professional.getCompany());
			insertStmt.executeUpdate();
			return professional;
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
	
	
	public Professionals getProfessionalByUserName(String userName) throws SQLException {
		String selectProfessional = "SELECT Professionals.UserName AS UserName, Password, FirstName, LastName, Email, Phone, HousingId, IfBioVisible, IsAuthenticatedResident, Company " +
				"FROM Professionals INNER JOIN Users " +
				"  ON Professionals.UserName = Users.UserName " +
				"WHERE Professionals.UserName=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		HousingDao housingDao = HousingDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectProfessional);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				int phone = results.getInt("Phone");
				Housing housing = housingDao.getHousingByHousingId(results.getInt("HosuingID"));
				boolean ifBioVisible = results.getBoolean("IfBioVisble");
				boolean isAuthenticatedResident = results.getBoolean("IsAuthenticatedResident");
				String company = results.getString("Company");
					
				Professionals professional = new Professionals (resultUserName, password,firstName,lastName, email, phone, housing, ifBioVisible, isAuthenticatedResident, company);
				return professional;
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
	
	public Students updateStudentSchool(Students student, String school) throws SQLException {
		String updateStudent = "UPDATE Students SET School=? WHERE UserName=?;";
		
		Connection connection = null;
		PreparedStatement updateStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateStudent);
			updateStmt.setString(1, school);
			updateStmt.setString(2, student.getUserName());
			results = updateStmt.executeQuery();
		
			student.setSchool(school);
			return student;
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
	
	
	
	public Professionals delete(Professionals professional) throws SQLException {
		
		String deleteProfessional = "DELETE FROM Professionals WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteProfessional);
			deleteStmt.setString(1, professional.getUserName());
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
