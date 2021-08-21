package session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.rowset.CachedRowSetImpl;

@SuppressWarnings("restriction")
public class LoginSession {

	public static CachedRowSetImpl BuscarUsuario(String login, String pass) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT * FROM usuario WHERE usu_login = '" + login + "' AND usu_pass = '" + pass + "'");
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			return crs;
		} catch (Exception e) {
			System.out.println("Error al ejecutar el SQL " + e);
		} finally {
			if (conexion != null) {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (s != null) {
				try {
					s.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return BuscarUsuario(login, pass);
	}

}
