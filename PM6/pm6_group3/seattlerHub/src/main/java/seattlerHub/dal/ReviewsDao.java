package seattlerHub.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seattlerHub.model.*;

public class ReviewsDao {
	
	protected ConnectionManager connectionManager;
	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}

	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}
	
	public Reviews create(Reviews review) throws SQLException {
		String insertReview = "INSERT INTO Reviews(Content, Created, Rating,UserName,HousingId) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
					Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, review.getContent());
			insertStmt.setTimestamp(2, new Timestamp(review.getCreated().getTime()));
			insertStmt.setDouble(3, review.getRating());
			insertStmt.setString(4, review.getUsers().getUserName());
			insertStmt.setInt(5, review.getHousing().getHousingId());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			return review;
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
	
	public Reviews getReviewByReviewId(int reviewId) throws SQLException {
		String selectReview = "SELECT Created,Content,Rating,UserName,HousingId FROM Reviews WHERE ReviewId=?;";
			
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		UsersDao usersDao = UsersDao.getInstance();
		HousingDao housingDao = HousingDao.getInstance();
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
				
			if(results.next()) {
				Timestamp created = results.getTimestamp("Created");
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				Users user = usersDao.getUserByUserName(results.getString("UserName"));
				Housing housing = housingDao.getHousingByHousingId(results.getInt("HousingId"));
					
				Reviews review = new Reviews(reviewId, created, content, rating, user, housing);
				return review;
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
	
	public Reviews updateReviewContent(Reviews review, String content) throws SQLException {
		String updateReview = "UPDATE Reviews SET Content=? WHERE ReviewId=?;";
		
		Connection connection = null;
		PreparedStatement updateStmt = null;
		ResultSet results = null;
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setString(1, content);
			updateStmt.setInt(2, review.getReviewId());
			results = updateStmt.executeQuery();
		
			review.setContent(content);
			return review;
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
	
	public List<Reviews> getReviewsByHousing(Housing housing) throws SQLException {
		  List<Reviews> reviews = new ArrayList<>();
		  String selectReviews =
				  "SELECT ReviewId,Created,Content,Rating,UserName,HousingId FROM Reviews " +
			      "WHERE HousingId=?;";
		   Connection connection = null;
		   PreparedStatement selectStmt = null;
		   ResultSet results = null;
		   UsersDao usersDao = UsersDao.getInstance();
		   HousingDao housingDao = HousingDao.getInstance();

		   try {
		    connection = connectionManager.getConnection();
		    selectStmt = connection.prepareStatement(selectReviews);
		    selectStmt.setInt(1, housing.getHousingId());
		    results = selectStmt.executeQuery();
		    while(results.next()) {
		    	int reviewId = results.getInt("ReviewId");
		    	Date created = new Date(results.getTimestamp("Created").getTime());		    	
		    	String content = results.getString("Content");
		    	double rating = results.getDouble("Rating");
		    	Users user = usersDao.getUserByUserName(results.getString("UserName"));
		    	Housing resultHousing = housingDao.getHousingByHousingId(housing.getHousingId());
		    	Reviews review = new Reviews(reviewId, created, content, rating, user, resultHousing);
		    	reviews.add(review);
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
		   return reviews;
		  }
	
	
	
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
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
