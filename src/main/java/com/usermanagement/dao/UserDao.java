package com.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usermanagement.model.User;

public class UserDao {
	private String jdbcURL = "jdbc:mysql://localhost:3306/management?allowPublicKeyRetrieval=true&useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country, phone) VALUES "
			+ " (?, ?, ?, ?);";

	private static final String SELECT_USER_BY_ID = "select * from users where id =?";
	private static final String SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set name = ?,email = ?, country = ?, phone = ? where id = ?;";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	// create operation 
	public int insertUser(User user) throws SQLException{
		int result = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setString(4, user.getPhone());
			
			result = preparedStatement.executeUpdate();
			
			connection.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// read user by id
	public User selectUser(int id) {
		User user = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			
			preparedStatement.setInt(1, id);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				String phone = rs.getString("phone");
				user = new User(id, name, email, country,phone);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// read all users
	public List<User> selectAllUsers() {

		List<User> users = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				String phone = rs.getString("phone");
				users.add(new User(id, name, email, country,phone));
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	// update user
	public int updateUser(User user) throws SQLException {
		int isUpdated = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);

			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setInt(5, user.getId());

			isUpdated = preparedStatement.executeUpdate();
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	// delete user
	public int deleteUser(int id) throws SQLException {
		int IsRowDeleted = 0;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);
			preparedStatement.setInt(1, id);
			
			IsRowDeleted = preparedStatement.executeUpdate();
			
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return IsRowDeleted;
	}
		
}
