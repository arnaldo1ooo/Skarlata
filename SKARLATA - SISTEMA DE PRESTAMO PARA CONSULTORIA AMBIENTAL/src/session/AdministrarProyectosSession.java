package session;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.sun.rowset.CachedRowSetImpl;

public class AdministrarProyectosSession {

	private static EntityManagerFactory emf = null;

	public static EntityManager getEntityManager() {
		emf = Persistence.createEntityManagerFactory("UnitBiblioteca");
		return emf.createEntityManager();
	}

	public static ResultSet obtenerproyectos() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT pro_codigo, pro_denominacion, em_descri, pro_fechacontrato, pro_fechaentrega,  CONCAT (CONCAT(cli_nombre,' '), "
					+ "cli_apelli) AS nomapecliente,  CONCAT (CONCAT(fun_nombre,' '), fun_apellido) AS nomapeconsultor, CONCAT (CONCAT(usu_nombre,' '), "
					+ "usu_apellido) AS nomapeusuario FROM proyecto, cliente, funcionario, usuario, emprendimiento WHERE pro_codcliente = cli_codigo AND "
					+ "pro_codconsultor = fun_codigo AND pro_codusuario = usu_codigo AND pro_tipoempre = em_codigo ORDER BY pro_codigo");
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

		return obtenerproyectos();
	}

	public static void InsertarProyecto(int CodigoNroProyecto, String Denominacion, int TipoEmpre, Date FechaContrato, Date FechaEntrega, int CodCliente, int CodConsultor, int CodUsuario, int CodInmueble, double EntregarCarpeta,
			boolean Observacion, double ObservacionNum, double OrdenPublicacion, double RetiroLicencia, double TotalSeam, int Distancia, double PrecioCombustible, int TipoVehiculo, double GastoLitroCombustible, double TotalGasto, int CantPersona,
			int CodTipoAloja, double CostoPorPersona, double CostoTotalViatico, boolean SubContrato, int CodProfesional, String NomApeProfesional, String RucProfesional, double Honorarios, boolean RegistrarInmueble, boolean RealizarPagoTasa,
			boolean CostoCuentaCatastral, boolean PrepararCarpetaSeam, boolean PermisoAmbiental, boolean AdicionalObs, double RegistrarInmuebleNum, double RealizarPagoTasaNum, double CostoCuentaCatastralNum, double PrepararCarpetaSeamNum,
			double PermisoAmbientalNum, double AdicionalObsNum, double ValorProyecto, double TotalCostos, int PorcentajeGanancia) {
		Connection conexion = null;
		PreparedStatement DatosACargar = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");

			String sql = "INSERT INTO proyecto(pro_codigo, pro_denominacion, pro_tipoempre, pro_fechacontrato, pro_fechaentrega, pro_codcliente, pro_codconsultor, pro_codusuario, pro_codinmueble, pro_entregarcarpeta, pro_observacion, pro_observacionnum, pro_ordenpublicacion, pro_retirolicencia, pro_totaltasaseam, pro_distancia, pro_preciocombustible, pro_tipovehiculo, pro_gastolitrocombustible, pro_totalgastocombustible, pro_cantpersonas, pro_codtipoaloja, pro_costoporpersona, pro_costototalviatico, pro_subcontrato, pro_codprofesional, pro_nomapeprofesional, pro_rucprofesional, pro_honorarios, pro_registrarinmueble, pro_realizarpagotasa, pro_costocuentacatastral, pro_prepararcarpetaseam, pro_permisoambiental, pro_adicionalobs, pro_registrarinmueblenum, pro_realizarpagotasanum, pro_costocuentacatastralnum, pro_prepararcarpetaseamnum, pro_permisoambientalnum, pro_adicionalobsnum, pro_valorproyecto, pro_totalcostos, pro_porcentajeganancia) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			DatosACargar = conexion.prepareStatement(sql);
			DatosACargar.setInt(1, CodigoNroProyecto);
			DatosACargar.setString(2, Denominacion);
			DatosACargar.setInt(3, TipoEmpre);
			DatosACargar.setDate(4, new java.sql.Date(FechaContrato.getTime()));
			DatosACargar.setDate(5, new java.sql.Date(FechaEntrega.getTime()));
			DatosACargar.setInt(6, CodCliente);
			DatosACargar.setInt(7, CodConsultor);
			DatosACargar.setInt(8, CodUsuario);
			DatosACargar.setInt(9, CodInmueble);
			DatosACargar.setDouble(10, EntregarCarpeta);
			DatosACargar.setBoolean(11, Observacion);
			DatosACargar.setDouble(12, ObservacionNum);
			DatosACargar.setDouble(13, OrdenPublicacion);
			DatosACargar.setDouble(14, RetiroLicencia);
			DatosACargar.setDouble(15, TotalSeam);
			DatosACargar.setInt(16, Distancia);
			DatosACargar.setDouble(17, PrecioCombustible);
			DatosACargar.setInt(18, TipoVehiculo);
			DatosACargar.setDouble(19, GastoLitroCombustible);
			DatosACargar.setDouble(20, TotalGasto);
			DatosACargar.setInt(21, CantPersona);
			DatosACargar.setInt(22, CodTipoAloja);
			DatosACargar.setDouble(23, CostoPorPersona);
			DatosACargar.setDouble(24, CostoTotalViatico);
			DatosACargar.setBoolean(25, SubContrato);
			DatosACargar.setInt(26, CodProfesional);
			DatosACargar.setString(27, NomApeProfesional);
			DatosACargar.setString(28, RucProfesional);
			DatosACargar.setDouble(29, Honorarios);
			DatosACargar.setBoolean(30, RegistrarInmueble);
			DatosACargar.setBoolean(31, RealizarPagoTasa);
			DatosACargar.setBoolean(32, CostoCuentaCatastral);
			DatosACargar.setBoolean(33, PrepararCarpetaSeam);
			DatosACargar.setBoolean(34, PermisoAmbiental);
			DatosACargar.setBoolean(35, AdicionalObs);
			DatosACargar.setDouble(36, RegistrarInmuebleNum);
			DatosACargar.setDouble(37, RealizarPagoTasaNum);
			DatosACargar.setDouble(38, CostoCuentaCatastralNum);
			DatosACargar.setDouble(39, PrepararCarpetaSeamNum);
			DatosACargar.setDouble(40, PermisoAmbientalNum);
			DatosACargar.setDouble(41, AdicionalObsNum);
			DatosACargar.setDouble(42, ValorProyecto);
			DatosACargar.setDouble(43, TotalCostos);
			DatosACargar.setInt(44, PorcentajeGanancia);
			DatosACargar.executeUpdate();
			DatosACargar.close();
			conexion.close();
		} catch (Exception e) {
			System.out.println("Error al insertar proyecto " + e);
		}
	}

	public static void InsertarInmueble(int CodInmueble, int CantFinca, int CantBloque, String Propietario, int NumPadron, int Departamento, int Distrito, String Localidad, double x, double y, int tipocoordenada, boolean TituloPropiedad,
			boolean Impuesto, boolean PlanoFinca, String NombreImagen, FileInputStream ImagenCroquis, int LongBytes) {
		Connection conexion = null;
		PreparedStatement DatosACargar = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");

			String sql = "INSERT INTO inmueble(in_codigo, in_cantfinca, in_cantbloque, in_propietario, in_numpadron, in_departamento, in_distrito, in_localidad, in_x, in_y, in_tipocoordenada, in_titulopropiedad, in_impuesto, in_planofinca) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			DatosACargar = conexion.prepareStatement(sql);
			DatosACargar.setInt(1, CodInmueble);
			DatosACargar.setInt(2, CantFinca);
			DatosACargar.setInt(3, CantBloque);
			DatosACargar.setString(4, Propietario);
			DatosACargar.setInt(5, NumPadron);
			DatosACargar.setInt(6, Departamento);
			DatosACargar.setInt(7, Distrito);
			DatosACargar.setString(8, Localidad);
			DatosACargar.setDouble(9, x);
			DatosACargar.setDouble(10, y);
			DatosACargar.setInt(11, tipocoordenada);
			DatosACargar.setBoolean(12, TituloPropiedad);
			DatosACargar.setBoolean(13, Impuesto);
			DatosACargar.setBoolean(14, PlanoFinca);
			DatosACargar.executeUpdate();
			DatosACargar.close();
			conexion.close();
		} catch (Exception e) {
			System.out.println("Error al insertar inmueble " + e);
		}
	}

	public static void ActualizarProyecto(int CodigoNroProyecto, String Denominacion, int TipoEmpre, Date FechaContrato, Date FechaEntrega, int CodCliente, int CodConsultor, int CodUsuario, int CodInmueble, double EntregarCarpeta,
			boolean Observacion, double ObservacionNum, double OrdenPublicacion, double RetiroLicencia, double TotalSeam, int Distancia, double PrecioCombustible, int TipoVehiculo, double GastoLitroCombustible, double TotalGasto, int CantPersona,
			int CodTipoAloja, double CostoPorPersona, double CostoTotalViatico, boolean SubContrato, int CodProfesional, String NomApeProfesional, String RucProfesional, double Honorarios, boolean RegistrarInmueble, boolean RealizarPagoTasa,
			boolean CostoCuentaCatastral, boolean PrepararCarpetaSeam, boolean PermisoAmbiental, boolean AdicionalObs, double RegistrarInmuebleNum, double RealizarPagoTasaNum, double CostoCuentaCatastralNum, double PrepararCarpetaSeamNum,
			double PermisoAmbientalNum, double AdicionalObsNum, double ValorProyecto, double TotalCostos, int PorcentajeGanancia) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "UPDATE public.proyecto SET " + "pro_codigo= " + CodigoNroProyecto + ", pro_denominacion= '" + Denominacion + "', pro_tipoempre= " + TipoEmpre + ", pro_fechacontrato= '" + FechaContrato + "', pro_fechaentrega= '"
					+ FechaEntrega + "', pro_codcliente= " + CodCliente + ", pro_codconsultor= " + CodConsultor + ", pro_codusuario= " + CodUsuario + ", pro_codinmueble= " + CodInmueble + ", pro_entregarcarpeta= "
					+ EntregarCarpeta + ", pro_observacion= " + Observacion + ", pro_observacionnum= " + ObservacionNum + ", pro_ordenpublicacion= " + OrdenPublicacion + ", pro_retirolicencia= " + RetiroLicencia + ", pro_totaltasaseam= " + TotalSeam
					+ ", pro_distancia= " + Distancia + ", pro_preciocombustible= " + PrecioCombustible + ", pro_tipovehiculo= " + TipoVehiculo + ", pro_gastolitrocombustible= " + GastoLitroCombustible + ", pro_totalgastocombustible= " + TotalGasto
					+ ", pro_cantpersonas= " + CantPersona + ", pro_codtipoaloja= " + CodTipoAloja + ", pro_costoporpersona= " + CostoPorPersona + ", pro_costototalviatico= " + CostoTotalViatico + ", pro_subcontrato= " + SubContrato
					+ ", pro_codprofesional= " + CodProfesional + ", pro_nomapeprofesional= '" + NomApeProfesional + "', pro_rucprofesional= '" + RucProfesional + "', pro_honorarios= " + Honorarios + ", pro_registrarinmueble= " + RegistrarInmueble
					+ ", pro_realizarpagotasa= " + RealizarPagoTasa + ", pro_costocuentacatastral= " + CostoCuentaCatastral + ", pro_prepararcarpetaseam= " + PrepararCarpetaSeam + ", pro_permisoambiental= " + PermisoAmbiental + ", pro_adicionalobs= "
					+ AdicionalObs + ", pro_registrarinmueblenum= " + RegistrarInmuebleNum + ", pro_realizarpagotasanum= " + RealizarPagoTasaNum + ", pro_costocuentacatastralnum= " + CostoCuentaCatastralNum + ", pro_prepararcarpetaseamnum= "
					+ PrepararCarpetaSeamNum + ", pro_permisoambientalnum= " + PermisoAmbientalNum + ", pro_adicionalobsnum= " + AdicionalObsNum + ", pro_valorproyecto= " + ValorProyecto + ", pro_totalcostos= " + TotalCostos
					+ ", pro_porcentajeganancia= " + PorcentajeGanancia + " WHERE pro_codigo = " + CodigoNroProyecto;
			em.createNativeQuery(sql).executeUpdate();

		} catch (Exception e) {
			System.out.println("Error sql ActualizarProyecto " + e);
		}
		em.getTransaction().commit();
		em.close();
	}

	public static void ActualizarInmueble(int CodInmueble, int CantFinca, int CantBloque, String Propietario, int NumPadron, int Departamento, int Distrito, String Localidad, double x, double y, int tipocoordenada, boolean TituloPropiedad,
			boolean Impuesto, boolean PlanoFinca) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		try {
			String sql = "UPDATE inmueble SET " + "in_codigo= " + CodInmueble + ", in_cantfinca= " + CantFinca + ", in_cantbloque= " + CantBloque + ", in_propietario= '" + Propietario + "', in_numpadron= " + NumPadron + ", in_departamento= "
					+ Departamento + ", in_distrito= " + Distrito + ", in_localidad= '" + Localidad + "', in_x= " + x + ", in_y= " + y + ", in_tipocoordenada= " + tipocoordenada + ", in_titulopropiedad= " + TituloPropiedad + ", in_impuesto= "
					+ Impuesto + ", in_planofinca= " + PlanoFinca + " WHERE in_codigo = " + CodInmueble;
			em.createNativeQuery(sql).executeUpdate();

		} catch (Exception e) {
			System.out.println("Error sql ActualizarInmueble " + e);
		}
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("restriction")
	public static ResultSet obtenerkm(int Codigo) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT ve_km FROM vehiculo WHERE ve_codigo = " + Codigo + " ORDER BY ve_codigo");
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

		return obtenerkm(Codigo);
	}

	public static ResultSet obtenerUsuario(String login) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT usu_codigo,  CONCAT (CONCAT(usu_nombre,' '), usu_apellido) AS nomape FROM usuario WHERE usu_login = '" + login + "'");
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

		return obtenerUsuario(login);
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
			rs = s.executeQuery("SELECT pro_codigo FROM proyecto WHERE pro_codigo <= @pro_codigo ORDER BY pro_codigo DESC limit 1");
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

	public static ResultSet ObtenerRegistro(int CodigoProyecto) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT * FROM proyecto WHERE pro_codigo = " + CodigoProyecto);
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

		return ObtenerRegistro(CodigoProyecto);
	}

	public static ResultSet ObtenerInmueble(int CodigoInmueble) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		CachedRowSetImpl crs = null;

		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/skarlata", "postgres", "admin");
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT * FROM inmueble WHERE in_codigo = " + CodigoInmueble);
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

		return ObtenerRegistro(CodigoInmueble);
	}
}