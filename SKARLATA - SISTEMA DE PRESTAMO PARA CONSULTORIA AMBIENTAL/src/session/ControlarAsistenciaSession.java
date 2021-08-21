package session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sun.rowset.CachedRowSetImpl;

@SuppressWarnings("restriction")
public class ControlarAsistenciaSession {

	public static ResultSet obtenerasistencias(int CodigoFuncionario, int Mes, int Año) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT asi_dia, asi_dianombre, asi_fecha, CONCAT(CONCAT(fun_nombre,' '), fun_apellido) as nomapefuncionario,asi_horaentrada, asi_horasalida, asi_horaextra, asi_horadescontada, "
					+ "CONCAT(CONCAT(usu_nombre,' '), usu_apellido) as nomapeusuario, obs_descri, asi_totalhoras, asi_codigo FROM asistencia, funcionario, usuario, observaciones WHERE asi_func = fun_codigo " + "AND asi_usu = usu_codigo AND "
					+ "asi_obs = obs_codigo " + "AND asi_func = " + CodigoFuncionario + " AND EXTRACT(MONTH FROM asi_fecha) =  " + Mes + " AND EXTRACT(YEAR FROM asi_fecha) = " + Año + " ORDER BY asi_fecha");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;

		} catch (Exception e) {
			System.out.println(e);
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

		return obtenerasistencias(CodigoFuncionario, Mes, Año);
	}

	public static ResultSet ObtenerAsistenciasPorFiltro(String FiltroSql) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT asi_dia AS dia, asi_dianombre AS dianombre, to_char(asi_fecha, 'dd/mm/yyyy') AS fecha, CONCAT(CONCAT(fun_nombre,' '), fun_apellido) AS nomapefuncionario, "
					+ "asi_horaentrada AS horaentrada, asi_horasalida AS horasalida, asi_horaextra AS horaextra, asi_horadescontada AS horadescuento, "
					+ "CONCAT(CONCAT(usu_nombre,' '), usu_apellido) as nomapeusuario, obs_descri, asi_codigo AS observacion,  SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(asi_horaentrada, asi_horasalida)))) AS TotalHoras "
					+ "FROM asistencia, funcionario, usuario, observaciones " + "WHERE asi_func = fun_codigo " + "AND asi_usu = usu_codigo AND " + "asi_obs = obs_codigo " + FiltroSql + " ORDER BY asi_fecha");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;

		} catch (Exception e) {
			System.out.println(e);
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

		return ObtenerAsistenciasPorFiltro(FiltroSql);
	}

	public static void InsertarAsistencia(int Codigo, int Dia, String DiaNombre, Date Fecha, int CodigoFuncionario, Time HoraEntrada, Time HoraSalida, Time HoraExtra, String HoraDescontada, int CodigoUsuario, int CodigoObservacion, String TotalHoras) {
		Connection conexion = null;
		PreparedStatement DatosACargar = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			
			String sql = "INSERT INTO asistencia(asi_codigo, asi_dia, asi_dianombre, asi_fecha, asi_func, asi_horaentrada, asi_horasalida, asi_horaextra, asi_horadescontada, asi_usu, asi_obs, asi_totalhoras) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			DatosACargar = conexion.prepareStatement(sql);
			DatosACargar.setInt(1, Codigo);
			DatosACargar.setInt(2, Dia);
			DatosACargar.setString(3, DiaNombre);
			DatosACargar.setDate(4, new java.sql.Date(Fecha.getTime()));
			DatosACargar.setInt(5, CodigoFuncionario);
			DatosACargar.setTime(6, HoraEntrada);
			DatosACargar.setTime(7, HoraSalida);
			DatosACargar.setTime(8, HoraExtra);
			DatosACargar.setString(9, HoraDescontada);
			DatosACargar.setInt(10, CodigoUsuario);
			DatosACargar.setInt(11, CodigoObservacion);
			DatosACargar.setString(12, TotalHoras);
			DatosACargar.executeUpdate();
			DatosACargar.close();
			conexion.close();
		} catch (Exception e) {
			System.out.println("Error sql InsertarAsistencia " + e);
		}
	}

	public static void ActualizarAsistencia(int Codigo, int Dia, String DiaNombre, Date Fecha, int CodigoFuncionario, Time HoraEntrada, Time HoraSalida, Time HoraExtra, String HoraDescontada, int CodigoUsuario, int CodigoObservacion, String TotalHoras,
			int CodigoAsistencia) {
		Connection conexion = null;
		PreparedStatement DatosACargar = null;

		try {
			String sql = "UPDATE asistencia SET asi_codigo=?, asi_dia=?, asi_dianombre=?, asi_fecha=?, asi_func=?, asi_horaentrada=?, asi_horasalida=?, asi_horaextra=?, asi_horadescontada=?, asi_usu=?, asi_obs=?, asi_totalhoras=? WHERE asi_codigo = "
					+ CodigoAsistencia;
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			DatosACargar = conexion.prepareStatement(sql);
			DatosACargar.setInt(1, Codigo);
			DatosACargar.setInt(2, Dia);
			DatosACargar.setString(3, DiaNombre);
			DatosACargar.setDate(4, new java.sql.Date(Fecha.getTime()));
			DatosACargar.setInt(5, CodigoFuncionario);
			DatosACargar.setTime(6, HoraEntrada);
			DatosACargar.setTime(7, HoraSalida);
			DatosACargar.setTime(8, HoraExtra);
			DatosACargar.setString(9, HoraDescontada);
			DatosACargar.setInt(10, CodigoUsuario);
			DatosACargar.setInt(11, CodigoObservacion);
			DatosACargar.setString(12, TotalHoras);
			DatosACargar.executeUpdate();
			DatosACargar.close();
			conexion.close();
		} catch (Exception e) {
			System.out.println("Error sql ActualizarAsistencia " + e);
		}
	}

	public static CachedRowSetImpl ObtenerCodigoUltimoRegistro() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT asi_codigo FROM asistencia WHERE asi_codigo <= @asi_codigo ORDER BY asi_codigo DESC limit 1");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			// TODO: handle exception
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

		return ObtenerCodigoUltimoRegistro();
	}

	public static ResultSet ObtenerUsuarioPorLogin(String loginusuario) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT usu_codigo, CONCAT (CONCAT(usu_nombre,' '), usu_apellido) as nomapelli FROM usuario WHERE usu_login = '" + loginusuario + "'");
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

		return ObtenerUsuarioPorLogin(loginusuario);
	}

	public static ResultSet ObtenerUsuarioPorCodigo(int CodigoUsuario) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT usu_codigo, CONCAT (CONCAT(usu_nombre,' '), usu_apellido) as nomapelli FROM usuario WHERE usu_codigo = " + CodigoUsuario);
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

		return ObtenerUsuarioPorCodigo(CodigoUsuario);
	}

	public static ResultSet ObtenerFuncionarios() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT fun_codigo, fun_nombre, fun_apellido, car_descri FROM funcionario, cargo WHERE fun_cargo = car_codigo ORDER BY fun_codigo");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			System.out.println(e);
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

		return ObtenerFuncionarios();
	}

	public static ResultSet ObtenerFuncionarioPorCodigo(int CodigoFuncionario) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT fun_codigo, fun_nombre, fun_apellido, car_descri, car_salario, frec_entrada, frec_salida, fun_cedula FROM funcionario, cargo, frecuencia WHERE fun_codigo = " + CodigoFuncionario
					+ " AND fun_cargo = car_codigo AND fun_frec = frec_codigo");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			System.out.println(e);
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

		return ObtenerFuncionarioPorCodigo(CodigoFuncionario);
	}

	public static ResultSet ObtenerAsistenciaPorCodigo(int CodigoAsistencia) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT * FROM asistencia WHERE asi_codigo = " + CodigoAsistencia + " ORDER BY asi_codigo");
			crs = new CachedRowSetImpl();
			crs.populate(rs);

			return crs;
		} catch (Exception e) {
			System.out.println(e);
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

		return ObtenerAsistenciaPorCodigo(CodigoAsistencia);
	}

	public static void eliminarregistro(int Codigo) {
		EntityManagerFactory emf = null;
		emf = Persistence.createEntityManagerFactory("UnitBiblioteca");
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			String sql = "DELETE FROM asistencia WHERE asi_codigo =" + Codigo;
			em.createNativeQuery(sql).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			em.close();
		}
	}
}
