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

public class AdministrarFuncionariosSession {

	@SuppressWarnings("restriction")
	public static ResultSet obtenerfuncionarios() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT fun_codigo, fun_nombre, fun_apellido, fun_direcc, car_descri, frec_entrada, frec_salida FROM funcionario, frecuencia, cargo WHERE fun_frec = frec_codigo AND fun_cargo = car_codigo ORDER BY fun_codigo");
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

		return obtenerfuncionarios();
	}

	private static EntityManagerFactory emf = null;

	public static EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("UnitBiblioteca");
		return emf.createEntityManager();
	}

	public static void InsertarFuncionarios(int Codigo, String Nombre, String Apellido, String Direccion, int Cargo, int Frecuencia) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "INSERT INTO funcionario (fun_codigo, fun_nombre, fun_apellido, fun_direcc, fun_cargo, fun_frec) VALUES (" + Codigo + ", '" + Nombre + "', '" + Apellido + "', '" + Direccion + "', " + Cargo + ", " + Frecuencia + ")";
			em.createNativeQuery(sql).executeUpdate();

		} catch (Exception e) {
			System.out.println("Ya existe el este numero de registro " + e);
		}
		em.getTransaction().commit();
		em.close();
	}

	public static void ActualizarFuncionarios(int Codigo, String Nombre, String Apellido, String Direccion, int Cargo, int Frecuencia) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "UPDATE funcionario SET fun_codigo = " + Codigo + ", fun_nombre = '" + Nombre + "', fun_apellido = '" + Apellido + "', fun_direcc = '" + Direccion + "', fun_cargo = " + Cargo + ", fun_frec = " + Frecuencia
					+ " WHERE fun_codigo =  " + Codigo + "";
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
			rs = s.executeQuery("SELECT fun_codigo FROM funcionario WHERE fun_codigo <= @fun_codigo ORDER BY fun_codigo DESC limit 1");
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
			rs = s.executeQuery("SELECT * FROM funcionario WHERE fun_codigo = " + codigo);
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
			String sql = "DELETE FROM funcionario WHERE fun_codigo =" + codigo;
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			em.close();
		}
	}

}
