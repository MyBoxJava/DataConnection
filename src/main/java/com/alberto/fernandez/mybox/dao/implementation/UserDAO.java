package com.alberto.fernandez.mybox.dao.implementation;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.alberto.fernandez.mybox.dao.intefaces.Persistable;
import com.alberto.fernandez.mybox.modelo.DbConnection;
import com.alberto.fernandez.mybox.pojo.User;
import com.alberto.fernandez.mybox.service.intefaces.UserServiceInterface;

public class UserDAO implements UserServiceInterface, Persistable<User> {

	private DbConnection db;

	public UserDAO() {
		try {
			this.db = new DbConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkEmail(String username) {
		Boolean result = false;
		try {
			String sql = "{call check_user_email(?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setString(1, username);
			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				result = rs.getBoolean("RESULT");
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public boolean singInUser(String username, String password, String email) {
		Boolean result = false;
		try {
			String sql = "{call sing_in_user(? , ?, ?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setString(1, username);
			call.setString(2, email);
			call.setString(3, password);
			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				result = rs.getBoolean("RESULT");
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public User logInUser(String email, String password) {
		User result = null;
		try {
			String sql = "{call login_user(? , ?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setString(1, email);
			call.setString(2, password);
			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				try {
					result = mapping(rs);
				} catch (SQLException e) {
					// TODO Log4J
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public boolean changeUserPassword(String username, String password,
			String newPassword) {
		Boolean result = false;
		try {
			String sql = "{call user_change_password(?, ?, ?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setString(1, username);
			call.setString(2, password);
			call.setString(3, newPassword);

			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				result = rs.getBoolean("RESULT");
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public User getById(int id) {
		User result = null;
		try {
			String sql = "{call user_by_id(?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setInt(1, id);

			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				try {
					result = mapping(rs);

				} catch (SQLException e) {
					// TODO Log4J
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public List<User> getAll() {
		List<User> result = new LinkedList<User>();
		try {
			String sql = "{call user_all()}";
			CallableStatement call = db.getConnection().prepareCall(sql);

			ResultSet rs = call.executeQuery();

			User aux = null;

			while (rs.next()) {
				try {
					aux = mapping(rs);
					result.add(aux);
				} catch (SQLException e) {
					// TODO Log4J
					e.printStackTrace();
				}
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public boolean delete(int id) {
		Boolean result = false;
		try {
			String sql = "{call delete_user(?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setInt(1, id);

			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				result = rs.getBoolean("RESULT");
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public boolean update(User persistable) {
		Boolean result = false;
		try {
			String sql = "{call user_update(?, ?, ?)}";
			CallableStatement call = db.getConnection().prepareCall(sql);
			call.setString(1, persistable.getEmail());
			call.setString(2, persistable.getPassword());
			call.setString(3, persistable.getName());

			ResultSet rs = call.executeQuery();

			if (rs.next()) {
				result = rs.getBoolean("RESULT");
			}

		} catch (SQLException e) {
			// TODO Log4J
			e.printStackTrace();
		}
		return result;
	}

	public int insert(User persistable) {
		int insertId = -1;
		
		this.singInUser(persistable.getName(), persistable.getPassword(), persistable.getEmail());
		
		try{
			insertId = this.logInUser(persistable.getEmail(), persistable.getPassword()).getId();
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		return insertId;
	}

	public User mapping(ResultSet rs) throws SQLException {
		User aux = new User();

		aux.setId(rs.getInt("id"));
		aux.setEmail(rs.getString("email"));
		aux.setName(rs.getString("username"));
		aux.setPassword(rs.getString("password"));
		aux.setSalt(rs.getString("salt"));

		return aux;
	}

}
