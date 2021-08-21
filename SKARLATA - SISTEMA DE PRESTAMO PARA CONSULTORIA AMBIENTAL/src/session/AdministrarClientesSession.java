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

public class AdministrarClientesSession {

	@SuppressWarnings("restriction")
	public static ResultSet obtenerclientes() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT cli_codigo, cli_nombre, cli_apelli, cli_cedula, cli_direcc, cli_telefo FROM cliente ORDER BY cli_codigo");
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

		return obtenerclientes();
	}

	private static EntityManagerFactory emf = null;

	public static EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("UnitBiblioteca");
		return emf.createEntityManager();
	}

	public static void InsertarClientes(int Codigo, String Nombre, String Apellido, int Cedula, String Direccion, String Telefono) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "INSERT INTO cliente (cli_codigo, cli_nombre, cli_apelli, cli_cedula, cli_direcc, cli_telefo) "
					+ "VALUES (" + Codigo + ", '" + Nombre + "', '" + Apellido + "', '" + Cedula + "', '" + Direccion + "', " + Telefono + ")";
			em.createNativeQuery(sql).executeUpdate();

		} catch (Exception e) {
			System.out.println("Error sql InsertarClientes " + e);
		}
		em.getTransaction().commit();
		em.close();
	}

	public static void ActualizarClientes(int Codigo, String Nombre, String Apellido, int Cedula, String Direccion, String Telefono) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "UPDATE cliente SET "
					+ "cli_codigo = " + Codigo + ", "
						+ "cli_nombre = '" + Nombre + "', "
							+ "cli_apelli = '" + Apellido + "',  "
									+ "cli_cedula = '" + Cedula + "', "
											+ "cli_direcc = '" + Direccion + "', "
													+ "cli_telefo = " + Telefono
														+ " WHERE cli_codigo =  " + Codigo + "";
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
			rs = s.executeQuery("SELECT cli_codigo FROM cliente WHERE cli_codigo <= @cli_codigo ORDER BY cli_codigo DESC limit 1");
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
			rs = s.executeQuery("SELECT * FROM cliente WHERE cli_codigo = " + codigo);
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
			String sql = "DELETE FROM cliente WHERE cli_codigo =" + codigo;
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			em.close();
		}
	}

}
