package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import seattlerHub.model.*;

public class StudentsDao extends UsersDao{
	
	protected ConnectionManager connectionManager;
	private static StudentsDao instance = null;
	protected StudentsDao() {
		connectionManager = new ConnectionManager();
	}

	public static StudentsDao getInstance() {
		if(instance == null) {
			instance = new StudentsDao();
		}
		return instance;
	}
	
	public Students create(Students student) throws SQLException {
		create(new Users(student.getUserName(), student.getPassword(), student.getFirstName(), student.getLastName(),
      student.getEmail(), student.getPhone(), student.getHousing(), student.getIfBioVisible(), student.getAuthenticatedResident()));
		
		String insertStudent = "INSERT INTO Students(UserName, School) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertStudent);
			insertStmt.setString(1, student.getUserName());
			insertStmt.setString(2, student.getSchool());
			insertStmt.executeUpdate();
			return student;
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
	
	public Students getStudentByUserName(String userName) throws SQLException {
		String selectStudent = "SELECT Students.UserName AS UserName, Password,FirstName,LastName,Email,Phone,HousingId,IfBioVisible, IsAuthenticatedResident, School " +
				"FROM Students INNER JOIN Users " +
				"  ON Students.UserName = Users.UserName " +
				"WHERE Students.UserName=?;";
		
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		HousingDao housingDao = HousingDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStudent);
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
				String school = results.getString("School");
					
				Students student = new Students (resultUserName, password,firstName,lastName, email, phone, housing, ifBioVisible, isAuthenticatedResident, school);
				return student;
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
	
	public Students delete(Students student) throws SQLException {
		
		String deleteStudent = "DELETE FROM Students WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteStudent);
			deleteStmt.setString(1, student.getUserName());
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
