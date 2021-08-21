package session;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import com.sun.rowset.CachedRowSetImpl;

public class TableConsultoresSession {

	@SuppressWarnings("restriction")
	public static ResultSet obtenertabla() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT fun_codigo, fun_nombre, fun_apellido, fun_direcc, car_descri, frec_entrada, frec_salida FROM funcionario, frecuencia, cargo WHERE fun_cargo = 1 AND fun_frec = frec_codigo AND fun_cargo = car_codigo ORDER BY fun_codigo");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			// TODO: handle exception
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

		return obtenertabla();
	}

	private static EntityManagerFactory emf = null;

	public static EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("UnitBiblioteca");
		return emf.createEntityManager();
	}

	public static void InsertarUsuarios(int Codigo, String Nombre, String Apellido, int Cedula, String Login, String Pass, int Estado, int Privilegio) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "INSERT INTO usuario (usu_codigo, usu_nombre, usu_apellido, usu_cedula, usu_login, usu_pass, usu_estado, usu_privilegio) "
					+ "VALUES (" + Codigo + ", '" + Nombre + "', '" + Apellido + "', '" + Cedula + "', '" + Login + "', '" + Pass + "', " + Estado + ", " + Privilegio + ")";
			em.createNativeQuery(sql).executeUpdate();

		} catch (Exception e) {
			System.out.println("Error sql InsertarClientes " + e);
		}
		em.getTransaction().commit();
		em.close();
	}

	public static void ActualizarUsuarios(int Codigo, String Nombre, String Apellido, int Cedula, String Login, String Pass, int Estado, int Privilegio) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "UPDATE usuario SET "
					+ "usu_codigo = " + Codigo + ", "
						+ "usu_nombre = '" + Nombre + "', "
							+ "usu_apellido = '" + Apellido + "',  "
									+ "usu_cedula = '" + Cedula + "', "
											+ "usu_login = '" + Login + "', "
													+ "usu_pass = " + Pass + ", "
														+ "usu_estado = " + Estado + ", "
															+ "usu_privilegio = " + Privilegio
															+ " WHERE usu_codigo =  " + Codigo + "";
			em.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			System.out.println("Error al actualizar registro " + e);
		}
		em.getTransaction().commit();
		em.close();
	}

	public static CachedRowSetImpl obtenercodigoultimoregistro() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT usu_codigo FROM usuario WHERE usu_codigo <= @usu_codigo ORDER BY usu_codigo DESC limit 1");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			// TODO: handle exception
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
		return obtenercodigoultimoregistro();
	}

	public static CachedRowSetImpl obtenerregistro(int codigo) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT * FROM usuario WHERE usu_codigo = " + codigo);
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			// TODO: handle exception
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
		return obtenerregistro(codigo);
	}

	public static void eliminarregistro(int codigo) {

		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			String sql = "DELETE FROM usuario WHERE usu_codigo =" + codigo;
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			em.close();
		}
	}

}
