package com.capa1Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

	private static Connection databaseConnection;

	private static String CONNECTION_STRING = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String USUARIO = "PDTAMBO";
	private static String CLAVE = "PDTAMBO1";

	static {
		databaseConnection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Se encontro el Driver para Oracle DB - La libreria necesaria esta referenciada");

			try {
				databaseConnection = DriverManager.getConnection(CONNECTION_STRING, USUARIO, CLAVE);
				System.out.println("Conexion creada con exito, es posible acceder a la base de datos!");

			} catch (SQLException e) {
				System.out.println("No logramos instanciar una conexion!");
				e.printStackTrace();

			} // try { intentamos instanciar el objeto connection

		} catch (ClassNotFoundException e) {
			System.out.println("No tienes el driver en tu build path?");
			e.printStackTrace();
		}
	} // try tenemos vinculada la case en nuestro proyecto?

	public static Connection getConnection() {
		return databaseConnection;
	}

}