/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sun.rowset.CachedRowSetImpl;

/**
 *
 * @author Trucos Jen <Jen at jenjen172009@gmail.com>
 */
public class Utilidades {
	private Connection conexion = null;

	/**
	* Metodo utilizado para recuperar el valor del atributo conexion
	* @return conexion contiene el estado de la conexión
	*
	*/
	public Connection getConexion() {
		return conexion;
	}

	/**
	* Metodo utilizado para establecer la conexion con la base de datos
	* @return estado regresa el estado de la conexion, true si se establecio la conexion,
	* falso en caso contrario
	*/
	public boolean crearConexion() {
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			if (conexion != null) {
				return true;
			}
		} catch (SQLException ex) {
			System.out.println("error en conexion: " + ex);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
		return false;
	}

	/**
	*
	*Metodo utilizado para realizar las instrucciones: INSERT, DELETE y UPDATE
	*@param sql Cadena que contiene la instrucción SQL a ejecutar
	*@return estado regresa el estado de la ejecucion, true(exito) o false(error)
	*
	*/
	public boolean ejecutarSQL(String sql) {
		EntityManagerFactory emf = null;
		emf = Persistence.createEntityManagerFactory("UnitBiblioteca");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
			System.out.println("Sentencia SQL ejecutada con exito: " + sql);
			return true;
		} catch (Exception e) {
			System.out.println("Error al ejecutar Sentencia SQL: " + sql + " " + e);
			return false;
		} finally {
			em.close();
		}
	}

	/**
	*
	*Metodo utilizado para realizar la instruccion SELECT
	*@param sql Cadena que contiene la instruccion SQL a ejecutar
	*@return resultado regresa los registros generados por la consulta
	*
	*/
	public ResultSet ejecutarSQLSelect(String sql) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery(sql);
			crs = new CachedRowSetImpl();
			crs.populate(rs);
			System.out.println("Sentencia SQL SELECT ejecutada con exito: " + sql);
			return crs;

		} catch (Exception e) {
			System.out.println("Error al ejecutar Sentencia SQL SELECT: " + sql + " " + e);
		} finally {
			if (conexion != null) {
				try {
					conexion.commit();
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
		return ejecutarSQLSelect(sql);
	}
	
	
	/**
	*
	*Metodo utilizado para realizar la instruccion SELECT
	*@param sql Cadena que contiene la instruccion SQL a ejecutar
	*@return resultado regresa los registros generados por la consulta
	*
	*/
	public ResultSet ejecutarSQLSelect2(String sql) {
		ResultSet resultado;
		try {
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			resultado = sentencia.executeQuery();
			return resultado;
		} catch (SQLException ex) {
			System.err.println("Error " + ex);
			return null;
		}
	}
}
