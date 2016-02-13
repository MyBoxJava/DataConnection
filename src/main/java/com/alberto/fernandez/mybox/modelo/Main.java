package com.alberto.fernandez.mybox.modelo;

import java.sql.SQLException;

import com.alberto.fernandez.mybox.dao.implementation.UserDAO;

public class Main {

public static void main(String[] args) {
	/*DbConnection db;
	try {
		db = new DbConnection();
		db.getConnection();
		db.desconectar();
		System.out.println("Conexion conseguida");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
	UserDAO dao = new UserDAO();
	System.out.println(dao.checkEmail("a@a.com"));
	System.out.println(dao.checkEmail("dd"));
	
}

}
