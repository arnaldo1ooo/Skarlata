package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOpenAerialTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.MapQuestOsmTileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import session.AdministrarClientesSession;
import session.AdministrarFuncionariosSession;
import session.AdministrarProyectosSession;
import session.AdministrarUsuariosSession;
import utilidades.CargarImagen;
import utilidades.FormatearJtable;
import utilidades.Utilidades;

public class AdministrarProyectos extends JDialog {

	private final JPanel pnBotones = new JPanel();
	private JTextField tNroProyecto;
	private JTextField tDenominacion;
	private JTextField tCantFinca;
	private JTextField tCantBloque;
	public static JTextField tPropietario;
	private JLabel lblImagen;
	private JTextField tEntregar;
	private JTextField tObs;
	private JTextField tOrdenPu;
	private JTextField tDistancia;
	private JTextField tPrecioCombustible;
	private JTextField tTotalGastoCombustible;
	private JTextField tRuc;
	private JTextField tHonorarios;
	private JTextField tTotalCostos;
	private JTextField tPorcentaje;
	private JTextField tValorTotalProyecto;
	private JTextField tRetiLicencia;
	private static JComboBox cbProfesional;
	private static JTextField tCodigoInicialProyecto;
	private static JTextField tCodigoFinalProyecto;
	private static JTextField tInicialCliente;
	private static JTextField tFinalCliente;
	private Utilidades utilidades = new Utilidades();
	private JTextField tRegistrarInmueble;
	private JTextField tRealizarPagoTasa;
	private JTextField tCostoCuentaCatastral;
	private JTextField tPrepararCarpetaSeam;
	private JTextField tPermisoAmbiental;
	private JTextField tAdicionalObs;
	public static JTextField tNomApeCliente;
	public static JTextField tCodigoCliente;
	private JTextField tCodigoUsuario;
	private JTextField tNomApeUsuario;
	private JTextField tGastoLitroCombustible;
	private JComboBox cbTipoVehiculo;
	private JTextField tNroPadron;
	private JTextField tLocalidad;
	private JComboBox cbDepartamento;
	private static JComboBox cbDistrito;
	public static JTextField tCodigoConsultor;
	public static JTextField tNomApeConsultor;
	private JComboBox cbTipoEmprendimiento;
	private JDateChooser dcFechaContrato;
	private JDateChooser dcFechaEntrega;
	private JButton buttonClientes;
	private JButton buttonConsultor;
	private JTextField tX;
	private JTextField tY;
	private JCheckBox chbObs;
	private JTextField tCodigoInmueble;
	private JCheckBox chbTituloPropiedad;
	private JCheckBox chbImpuestoIn;
	private JCheckBox chbPlanoFincas;
	private JButton btnCargarImagen;
	private JCheckBox chbSubContrato;
	private JTextField tSubNomApe;
	private int SumaCostos = 0;
	private JTextField tTotalTasaSeam;
	private int SumaTasaSeam = 0;
	private JTextField tCantPersonas;
	private JTextField tCostoPorPersona;
	private JComboBox cbTipoAlojamiento;
	private JCheckBox chbRegistraInmueble;
	private JCheckBox chbRealizarPagoTasa;
	private JCheckBox chbCostoCuentaCatastral;
	private JCheckBox chbPrepararCarpetaSeam;
	private JCheckBox chbPermisoAmbiental;
	private JCheckBox chbObsProcesos;
	private JTextField tCostoTotalViatico;
	private int TotalViatico;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JTabbedPane tpCostos;
	private int CalPorcentaje;
	private JComboBox cbTipoMapa;
	private JMapViewer mapViewer;
	private File fichero;
	private JComboBox cbTipoCoordenada;
	private static JTable table;
	private JLabel CoordenadaA;
	private JLabel CoordenadaB;
	private byte[] bytes;
	private JScrollPane scrollPane;
	private static JDateChooser dcFechaInicialContrato;
	private static JDateChooser dcFechaFinalContrato;
	private static JDateChooser dcFechaInicialEntrega;
	private static JDateChooser dcFechaFinalEntrega;
	private ResultSet ProyectoSeleccionado = null;
	private ResultSet inmueble = null;
	private FileInputStream imagenfis;
	private int longimagen;
	private String NombreImagen;
	private JButton btnInforme;
	private JTextField textField;
	private JButton btnInformeIndividual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AdministrarProyectos dialog = new AdministrarProyectos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AdministrarProyectos() {
		getContentPane().setBackground(SystemColor.activeCaption);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		// Aplicar tema
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		setTitle("Nuevo Proyecto");
		setBounds(350, 0, 1509, 924);
		getContentPane().setLayout(null);
		pnBotones.setBounds(553, 634, 933, 65);
		pnBotones.setBackground(SystemColor.activeCaption);
		pnBotones.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnBotones);
		pnBotones.setLayout(null);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height; // Tamano total vertical
		int width = pantalla.width;// Tamano total horizontal
		setLocation(0, 155); //Localizacion de pantalla x,y
		setSize(width, 705);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setEnabled(false);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				habilitarModoEdicion(false);
				table.clearSelection();
				if (table.getSelectedRow() != -1) {
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
				} else {
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
				}
			}
		});
		btnCancelar.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(394, 2, 144, 60);
		pnBotones.add(btnCancelar);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/Salir copia.png")));
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSalir.setBounds(663, 2, 144, 60);
		pnBotones.add(btnSalir);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Utilidades utilidades = new Utilidades();
				utilidades.crearConexion();
				ResultSet ObtenerProyecto = utilidades.ejecutarSQLSelect("SELECT * FROM proyecto WHERE pro_codigo = " + tNroProyecto.getText());
				try {
					if (ObtenerProyecto.next() == true) {//Si registro ya existe
						ActualizarRegistro();
					} else {//Si no existe
						InsertarRegistro();
					}
					ObtenerProyecto.close();
				} catch (Exception e) {
					System.out.println("Error al ObtenerProyecto para comprobar si ya existe" + e);
				}
				ActualizarTabla();
				habilitarModoEdicion(false);
			}

		});
		btnGuardar.setEnabled(false);
		btnGuardar.setBounds(125, 2, 144, 60);
		pnBotones.add(btnGuardar);
		btnGuardar.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.setBounds(553, 6, 933, 610);
		getContentPane().add(tabbedPane);
		tabbedPane.setBackground(SystemColor.activeCaption);
		tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 18));

		JPanel pnDatosProyecto = new JPanel();
		pnDatosProyecto.setLocation(10, 64);
		tabbedPane.addTab("Datos del Proyecto", null, pnDatosProyecto, null);
		pnDatosProyecto.setBackground(SystemColor.activeCaption);
		pnDatosProyecto.setLayout(null);

		JLabel lblDenominacion = new JLabel("Denominacion");
		lblDenominacion.setBounds(137, 67, 114, 33);
		pnDatosProyecto.add(lblDenominacion);
		lblDenominacion.setForeground(SystemColor.desktop);
		lblDenominacion.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblDenominacion.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel lblCodigo = new JLabel("Nro de Proyecto");
		lblCodigo.setBounds(126, 17, 125, 33);
		pnDatosProyecto.add(lblCodigo);
		lblCodigo.setForeground(SystemColor.desktop);
		lblCodigo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);

		tNroProyecto = new JTextField();
		tNroProyecto.setEnabled(false);
		tNroProyecto.setBounds(257, 17, 63, 33);
		pnDatosProyecto.add(tNroProyecto);
		tNroProyecto.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tNroProyecto.setColumns(10);

		tDenominacion = new JTextField();
		tDenominacion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				Character s = arg0.getKeyChar();
				if (Character.isLetter(s)) {
					tDenominacion.setText(tDenominacion.getText().toUpperCase());
				}
			}
		});
		tDenominacion.setEnabled(false);
		tDenominacion.setBounds(257, 67, 453, 33);
		pnDatosProyecto.add(tDenominacion);
		tDenominacion.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tDenominacion.setColumns(10);

		JLabel lblFechaInicio = new JLabel("Fecha de Contrato");
		lblFechaInicio.setBounds(112, 170, 139, 33);
		pnDatosProyecto.add(lblFechaInicio);
		lblFechaInicio.setForeground(SystemColor.desktop);
		lblFechaInicio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaInicio.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblTipoDeProyecto = new JLabel("Tipo de Emprendimiento");
		lblTipoDeProyecto.setBounds(68, 117, 183, 33);
		pnDatosProyecto.add(lblTipoDeProyecto);
		lblTipoDeProyecto.setForeground(SystemColor.desktop);
		lblTipoDeProyecto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoDeProyecto.setFont(new Font("SansSerif", Font.BOLD, 15));

		cbTipoEmprendimiento = new JComboBox();
		cbTipoEmprendimiento.setEnabled(false);
		cbTipoEmprendimiento.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbTipoEmprendimiento.setMaximumRowCount(20);
		cbTipoEmprendimiento.setBounds(257, 117, 338, 33);
		pnDatosProyecto.add(cbTipoEmprendimiento);

		dcFechaContrato = new JDateChooser();
		dcFechaContrato.setEnabled(false);
		dcFechaContrato.setFont(new Font("SansSerif", Font.PLAIN, 16));
		dcFechaContrato.getCalendarButton().setFont(new Font("SansSerif", Font.PLAIN, 14));
		dcFechaContrato.setBounds(257, 170, 150, 33);
		pnDatosProyecto.add(dcFechaContrato);

		JLabel lblFechaFin = new JLabel("Fecha de Entrega del Documento");
		lblFechaFin.setBounds(6, 220, 245, 33);
		pnDatosProyecto.add(lblFechaFin);
		lblFechaFin.setForeground(SystemColor.desktop);
		lblFechaFin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaFin.setFont(new Font("SansSerif", Font.BOLD, 15));

		dcFechaEntrega = new JDateChooser();
		dcFechaEntrega.setEnabled(false);
		dcFechaEntrega.setFont(new Font("SansSerif", Font.PLAIN, 16));
		dcFechaEntrega.getCalendarButton().setFont(new Font("SansSerif", Font.PLAIN, 14));
		dcFechaEntrega.setBounds(257, 220, 150, 33);
		pnDatosProyecto.add(dcFechaEntrega);

		tNomApeCliente = new JTextField();
		tNomApeCliente.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tNomApeCliente.setEnabled(false);
		tNomApeCliente.setColumns(10);
		tNomApeCliente.setBounds(321, 270, 453, 33);
		pnDatosProyecto.add(tNomApeCliente);

		JLabel lblCliente_2 = new JLabel("Cliente");
		lblCliente_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCliente_2.setForeground(Color.BLACK);
		lblCliente_2.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCliente_2.setBounds(165, 270, 86, 33);
		pnDatosProyecto.add(lblCliente_2);

		tCodigoCliente = new JTextField();
		tCodigoCliente.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCodigoCliente.setEnabled(false);
		tCodigoCliente.setColumns(10);
		tCodigoCliente.setBounds(257, 270, 56, 33);
		pnDatosProyecto.add(tCodigoCliente);

		buttonClientes = new JButton("...");
		buttonClientes.setEnabled(false);
		buttonClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClientes tableclientes = new TableClientes();
				tableclientes.setVisible(true);
			}
		});
		buttonClientes.setBounds(786, 270, 43, 33);
		pnDatosProyecto.add(buttonClientes);

		tCodigoUsuario = new JTextField();
		tCodigoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCodigoUsuario.setEnabled(false);
		tCodigoUsuario.setColumns(10);
		tCodigoUsuario.setBounds(258, 372, 56, 33);
		pnDatosProyecto.add(tCodigoUsuario);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setForeground(Color.BLACK);
		lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblUsuario.setBounds(166, 372, 86, 33);
		pnDatosProyecto.add(lblUsuario);

		tNomApeUsuario = new JTextField();
		tNomApeUsuario.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tNomApeUsuario.setEnabled(false);
		tNomApeUsuario.setColumns(10);
		tNomApeUsuario.setBounds(322, 372, 453, 33);
		pnDatosProyecto.add(tNomApeUsuario);

		JLabel lblConsultor = new JLabel("Consultor Encargado");
		lblConsultor.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConsultor.setForeground(Color.BLACK);
		lblConsultor.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblConsultor.setBounds(84, 322, 166, 33);
		pnDatosProyecto.add(lblConsultor);

		tCodigoConsultor = new JTextField();
		tCodigoConsultor.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCodigoConsultor.setEnabled(false);
		tCodigoConsultor.setColumns(10);
		tCodigoConsultor.setBounds(258, 322, 56, 33);
		pnDatosProyecto.add(tCodigoConsultor);

		tNomApeConsultor = new JTextField();
		tNomApeConsultor.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tNomApeConsultor.setEnabled(false);
		tNomApeConsultor.setColumns(10);
		tNomApeConsultor.setBounds(322, 322, 453, 33);
		pnDatosProyecto.add(tNomApeConsultor);

		buttonConsultor = new JButton("...");
		buttonConsultor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableConsultores tableconsultores = new TableConsultores();
				tableconsultores.setVisible(true);
			}
		});
		buttonConsultor.setEnabled(false);
		buttonConsultor.setBounds(787, 322, 43, 33);
		pnDatosProyecto.add(buttonConsultor);

		JPanel pn_Inmueble = new JPanel();
		pn_Inmueble.setBackground(SystemColor.activeCaption);
		tabbedPane.addTab("Inmueble", null, pn_Inmueble, null);
		tabbedPane.setEnabledAt(1, true);
		pn_Inmueble.setLayout(null);

		JTabbedPane tpInmueble = new JTabbedPane(JTabbedPane.TOP);
		tpInmueble.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tpInmueble.setBackground(SystemColor.activeCaption);
		tpInmueble.setBounds(0, 0, 913, 552);
		pn_Inmueble.add(tpInmueble);

		JPanel pnDatosInmueble = new JPanel();
		pnDatosInmueble.setBackground(SystemColor.activeCaption);
		tpInmueble.addTab("Datos Inmueble", null, pnDatosInmueble, null);
		pnDatosInmueble.setLayout(null);

		JLabel lblNroInmueble = new JLabel("Cantidad de F\u00EDncas");
		lblNroInmueble.setForeground(SystemColor.desktop);
		lblNroInmueble.setBounds(21, 51, 146, 33);
		pnDatosInmueble.add(lblNroInmueble);
		lblNroInmueble.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNroInmueble.setFont(new Font("SansSerif", Font.BOLD, 15));

		tCantFinca = new JTextField();
		tCantFinca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = arg0.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					arg0.consume(); // ignorar el evento de teclado
				}
			}
		});
		tCantFinca.setHorizontalAlignment(SwingConstants.RIGHT);
		tCantFinca.setEnabled(false);
		tCantFinca.setBounds(179, 51, 132, 33);
		pnDatosInmueble.add(tCantFinca);
		tCantFinca.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCantFinca.setColumns(10);

		JLabel lblBloque = new JLabel("Cantidad de Bloques");
		lblBloque.setBounds(6, 96, 162, 33);
		pnDatosInmueble.add(lblBloque);
		lblBloque.setForeground(SystemColor.desktop);
		lblBloque.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBloque.setFont(new Font("SansSerif", Font.BOLD, 15));

		tCantBloque = new JTextField();
		tCantBloque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = arg0.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					arg0.consume(); // ignorar el evento de teclado
				}
			}
		});
		tCantBloque.setBounds(180, 96, 132, 33);
		pnDatosInmueble.add(tCantBloque);
		tCantBloque.setHorizontalAlignment(SwingConstants.RIGHT);
		tCantBloque.setEnabled(false);
		tCantBloque.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCantBloque.setColumns(10);

		JLabel lblTitulo = new JLabel("Propietario");
		lblTitulo.setBounds(75, 142, 93, 33);
		pnDatosInmueble.add(lblTitulo);
		lblTitulo.setForeground(SystemColor.desktop);
		lblTitulo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));

		tPropietario = new JTextField();
		tPropietario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				Character s = arg0.getKeyChar();
				if (Character.isLetter(s)) {
					tPropietario.setText(tPropietario.getText().toUpperCase());
				}
			}
		});

		tPropietario.setBounds(180, 142, 293, 33);
		pnDatosInmueble.add(tPropietario);
		tPropietario.setHorizontalAlignment(SwingConstants.LEFT);
		tPropietario.setEnabled(false);
		tPropietario.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tPropietario.setColumns(10);

		JLabel lblNumeroDePadrn = new JLabel("N\u00FAmero de Padr\u00F3n");
		lblNumeroDePadrn.setBounds(21, 188, 147, 33);
		pnDatosInmueble.add(lblNumeroDePadrn);
		lblNumeroDePadrn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNumeroDePadrn.setForeground(Color.BLACK);
		lblNumeroDePadrn.setFont(new Font("SansSerif", Font.BOLD, 15));

		tNroPadron = new JTextField();
		tNroPadron.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tNroPadron.setBounds(180, 188, 132, 33);
		pnDatosInmueble.add(tNroPadron);
		tNroPadron.setHorizontalAlignment(SwingConstants.RIGHT);
		tNroPadron.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tNroPadron.setEnabled(false);
		tNroPadron.setColumns(10);

		JLabel lblLocalidad = new JLabel("Departamento");
		lblLocalidad.setBounds(64, 234, 104, 33);
		pnDatosInmueble.add(lblLocalidad);
		lblLocalidad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLocalidad.setForeground(Color.BLACK);
		lblLocalidad.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblDistrito = new JLabel("Distrito");
		lblDistrito.setBounds(82, 280, 86, 33);
		pnDatosInmueble.add(lblDistrito);
		lblDistrito.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDistrito.setForeground(Color.BLACK);
		lblDistrito.setFont(new Font("SansSerif", Font.BOLD, 15));

		cbDistrito = new JComboBox();
		cbDistrito.setEnabled(false);
		cbDistrito.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbDistrito.setBounds(180, 280, 293, 33);
		pnDatosInmueble.add(cbDistrito);
		cbDistrito.setMaximumRowCount(20);

		JLabel label_2 = new JLabel("Localidad");
		label_2.setBounds(82, 326, 86, 33);
		pnDatosInmueble.add(label_2);
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("SansSerif", Font.BOLD, 15));

		tLocalidad = new JTextField();
		tLocalidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tLocalidad.setText(tLocalidad.getText().toUpperCase());
				}
			}
		});
		tLocalidad.setBounds(180, 326, 213, 33);
		pnDatosInmueble.add(tLocalidad);
		tLocalidad.setHorizontalAlignment(SwingConstants.LEFT);
		tLocalidad.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tLocalidad.setEnabled(false);
		tLocalidad.setColumns(10);

		cbDepartamento = new JComboBox();
		cbDepartamento.setEnabled(false);
		cbDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int CodDepartamento = cbDepartamento.getSelectedIndex() + 1;
				ComboConsultaDistritos(CodDepartamento);
			}
		});
		cbDepartamento.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbDepartamento.setBounds(180, 234, 185, 33);
		pnDatosInmueble.add(cbDepartamento);
		cbDepartamento.setMaximumRowCount(20);

		JLabel lblCodigo_1 = new JLabel("Codigo");
		lblCodigo_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo_1.setForeground(Color.BLACK);
		lblCodigo_1.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCodigo_1.setBounds(35, 6, 132, 33);
		pnDatosInmueble.add(lblCodigo_1);

		tCodigoInmueble = new JTextField();
		tCodigoInmueble.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCodigoInmueble.setEnabled(false);
		tCodigoInmueble.setColumns(10);
		tCodigoInmueble.setBounds(179, 6, 63, 33);
		pnDatosInmueble.add(tCodigoInmueble);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		tpInmueble.addTab("Geolocalizaci\u00F3n", null, panel_1, null);
		panel_1.setLayout(null);

		CoordenadaA = new JLabel("Latitud=");
		CoordenadaA.setHorizontalAlignment(SwingConstants.RIGHT);
		CoordenadaA.setForeground(Color.BLACK);
		CoordenadaA.setFont(new Font("SansSerif", Font.BOLD, 15));
		CoordenadaA.setBounds(40, 6, 150, 33);
		panel_1.add(CoordenadaA);

		tX = new JTextField();

		tX.setHorizontalAlignment(SwingConstants.RIGHT);
		tX.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tX.setEnabled(false);
		tX.setColumns(10);
		tX.setBounds(195, 6, 366, 33);
		panel_1.add(tX);

		CoordenadaB = new JLabel("Longitud=");
		CoordenadaB.setHorizontalAlignment(SwingConstants.RIGHT);
		CoordenadaB.setForeground(Color.BLACK);
		CoordenadaB.setFont(new Font("SansSerif", Font.BOLD, 15));
		CoordenadaB.setBounds(32, 40, 157, 33);
		panel_1.add(CoordenadaB);

		tY = new JTextField();

		tY.setHorizontalAlignment(SwingConstants.RIGHT);
		tY.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tY.setEnabled(false);
		tY.setColumns(10);
		tY.setBounds(195, 40, 366, 33);
		panel_1.add(tY);

		JButton btnNewButton = new JButton("Ubicar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coordinate coordenadas = new Coordinate(Double.parseDouble(tX.getText()), Double.parseDouble(tY.getText()));
				mapViewer.setDisplayPosition(coordenadas, 19);

			}
		});
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 15));
		btnNewButton.setBounds(573, 40, 90, 33);
		panel_1.add(btnNewButton);

		mapViewer = new JMapViewer();
		mapViewer.setTileSource(new BingAerialTileSource());
		mapViewer.setScrollWrapEnabled(true);
		mapViewer.setBounds(4, 85, 904, 386);
		panel_1.add(mapViewer);

		cbTipoMapa = new JComboBox();
		cbTipoMapa.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbTipoMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cbTipoMapa.getSelectedIndex() == 0) {
					mapViewer.setTileSource(new BingAerialTileSource());
				}
				if (cbTipoMapa.getSelectedIndex() == 1) {
					mapViewer.setTileSource(new MapQuestOpenAerialTileSource());
				}
				if (cbTipoMapa.getSelectedIndex() == 2) {
					mapViewer.setTileSource(new MapQuestOsmTileSource());
				}
				if (cbTipoMapa.getSelectedIndex() == 3) {
					mapViewer.setTileSource(new OsmTileSource.Mapnik());
				}
				if (cbTipoMapa.getSelectedIndex() == 4) {
					mapViewer.setTileSource(new OsmTileSource.CycleMap());
				}
			}
		});
		cbTipoMapa.setModel(new DefaultComboBoxModel(new String[] { "BingAerialTileSource", "MapQuestOpenAerial", "MapQuestOsm", "Mapnik", "CycleMap" }));
		cbTipoMapa.setBounds(6, 478, 190, 33);
		panel_1.add(cbTipoMapa);

		cbTipoCoordenada = new JComboBox();
		cbTipoCoordenada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbTipoCoordenada.getSelectedIndex() == 0) {
					CoordenadaA.setText("Latitud=");
					CoordenadaB.setText("Longitud=");
				} else {
					CoordenadaA.setText("Coordenada UTM X=");
					CoordenadaB.setText("Coordenada UTM Y=");
				}
			}
		});
		cbTipoCoordenada.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbTipoCoordenada.setModel(new DefaultComboBoxModel(new String[] { "Coordenadas Geogr\u00E1ficas", "Coordenadas UTM" }));
		cbTipoCoordenada.setSelectedIndex(0);
		cbTipoCoordenada.setBounds(573, 6, 204, 33);
		panel_1.add(cbTipoCoordenada);

		JPanel pnDocumentosListos = new JPanel();
		pnDocumentosListos.setBackground(SystemColor.activeCaption);
		tpInmueble.addTab("Documentos listos", null, pnDocumentosListos, null);
		pnDocumentosListos.setLayout(null);

		chbTituloPropiedad = new JCheckBox("Titulo de Propiedad");
		chbTituloPropiedad.setEnabled(false);
		chbTituloPropiedad.setForeground(SystemColor.desktop);
		chbTituloPropiedad.setFont(new Font("SansSerif", Font.PLAIN, 19));
		chbTituloPropiedad.setBounds(60, 23, 251, 31);
		pnDocumentosListos.add(chbTituloPropiedad);

		chbImpuestoIn = new JCheckBox("Impuesto Inmobiliario");
		chbImpuestoIn.setEnabled(false);
		chbImpuestoIn.setForeground(SystemColor.desktop);
		chbImpuestoIn.setFont(new Font("SansSerif", Font.PLAIN, 19));
		chbImpuestoIn.setBounds(60, 77, 209, 31);
		pnDocumentosListos.add(chbImpuestoIn);

		chbPlanoFincas = new JCheckBox("Planos de Fincas");
		chbPlanoFincas.setEnabled(false);
		chbPlanoFincas.setForeground(SystemColor.desktop);
		chbPlanoFincas.setFont(new Font("SansSerif", Font.PLAIN, 19));
		chbPlanoFincas.setBounds(60, 131, 209, 31);
		pnDocumentosListos.add(chbPlanoFincas);

		JPanel pnUbicacionCroquis = new JPanel();
		pnUbicacionCroquis.setBackground(SystemColor.activeCaption);
		tpInmueble.addTab("Ubicacion Croquis", null, pnUbicacionCroquis, null);
		pnUbicacionCroquis.setLayout(null);

		btnCargarImagen = new JButton("Cargar Imagen");
		btnCargarImagen.setEnabled(false);
		btnCargarImagen.setBounds(381, 468, 150, 43);
		pnUbicacionCroquis.add(btnCargarImagen);
		btnCargarImagen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				CargarImagen();
			}
		});
		btnCargarImagen.setFont(new Font("SansSerif", Font.PLAIN, 14));

		JScrollPane sp_ImagenCroquis = new JScrollPane();
		sp_ImagenCroquis.setBackground(SystemColor.activeCaption);
		sp_ImagenCroquis.setBounds(11, 6, 891, 456);
		pnUbicacionCroquis.add(sp_ImagenCroquis);

		lblImagen = new JLabel("IMAGEN");
		lblImagen.setBackground(SystemColor.activeCaption);
		sp_ImagenCroquis.setViewportView(lblImagen);
		lblImagen.setForeground(SystemColor.activeCaption);
		lblImagen.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		lblImagen.setFont(new Font("SansSerif", Font.BOLD, 20));
		lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
		lblImagen.setHorizontalTextPosition(SwingConstants.CENTER);

		JPanel pnCostos = new JPanel();
		pnCostos.setBackground(SystemColor.activeCaption);
		pnCostos.setLayout(null);
		tabbedPane.addTab("Costos", null, pnCostos, null);

		tpCostos = new JTabbedPane(JTabbedPane.TOP);
		tpCostos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SumarCostoTotal();
				CalculoPorcentaje();
			}
		});

		tpCostos.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tpCostos.setBackground(SystemColor.activeCaption);
		tpCostos.setBounds(0, 0, 913, 546);
		pnCostos.add(tpCostos);

		JPanel pnTazasSEAM = new JPanel();
		pnTazasSEAM.setBackground(SystemColor.activeCaption);
		tpCostos.addTab("Tasa SEAM", null, pnTazasSEAM, null);
		pnTazasSEAM.setLayout(null);

		JLabel label_7 = new JLabel("Entrega de carpeta");
		label_7.setForeground(SystemColor.desktop);
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_7.setBounds(89, 19, 135, 33);
		pnTazasSEAM.add(label_7);

		tEntregar = new JTextField();
		tEntregar.setText("0");

		tEntregar.setEnabled(false);
		tEntregar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}

			}

			//Poner puntos cada 3 numeros
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tEntregar.setText(decimalmiles.format(Double.parseDouble(tEntregar.getText().replace(".", ""))));
				SumarTasaSEAM();
			}

		});
		tEntregar.setHorizontalAlignment(SwingConstants.RIGHT);
		tEntregar.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tEntregar.setColumns(10);
		tEntregar.setBounds(233, 19, 179, 33);
		pnTazasSEAM.add(tEntregar);

		JLabel label_8 = new JLabel("GS.");
		label_8.setForeground(SystemColor.desktop);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_8.setBounds(409, 19, 40, 33);
		pnTazasSEAM.add(label_8);

		tObs = new JTextField();
		tObs.setText("0");

		tObs.setEnabled(false);
		tObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}

			}

			//Poner puntos cada 3 numeros
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tObs.setText(decimalmiles.format(Double.parseDouble(tObs.getText().replace(".", ""))));

				SumarTasaSEAM();
			}
		});
		tObs.setHorizontalAlignment(SwingConstants.RIGHT);
		tObs.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tObs.setColumns(10);
		tObs.setBounds(233, 71, 179, 33);
		pnTazasSEAM.add(tObs);

		JLabel label_9 = new JLabel("GS.");
		label_9.setForeground(SystemColor.desktop);
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_9.setBounds(409, 71, 40, 33);
		pnTazasSEAM.add(label_9);

		JLabel label_10 = new JLabel("Retiro de la Licencia");
		label_10.setForeground(SystemColor.desktop);
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_10.setBounds(70, 176, 154, 33);
		pnTazasSEAM.add(label_10);

		tOrdenPu = new JTextField();
		tOrdenPu.setText("0");

		tOrdenPu.setEnabled(false);
		tOrdenPu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}

			}

			//Poner puntos cada 3 numeros
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tOrdenPu.setText(decimalmiles.format(Double.parseDouble(tOrdenPu.getText().replace(".", ""))));

				SumarTasaSEAM();
			}
		});
		tOrdenPu.setHorizontalAlignment(SwingConstants.RIGHT);
		tOrdenPu.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tOrdenPu.setColumns(10);
		tOrdenPu.setBounds(233, 123, 179, 33);
		pnTazasSEAM.add(tOrdenPu);

		JLabel label_12 = new JLabel("GS.");
		label_12.setForeground(SystemColor.desktop);
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_12.setBounds(409, 123, 40, 33);
		pnTazasSEAM.add(label_12);

		chbObs = new JCheckBox("Observacion");
		chbObs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbObs.isSelected()) {
					tObs.setEnabled(true);
				} else {
					tObs.setEnabled(false);
				}
			}
		});
		chbObs.setEnabled(false);
		chbObs.setForeground(SystemColor.desktop);
		chbObs.setHorizontalAlignment(SwingConstants.RIGHT);
		chbObs.setFont(new Font("SansSerif", Font.BOLD, 15));
		chbObs.setBounds(89, 71, 135, 33);
		pnTazasSEAM.add(chbObs);

		JLabel label_31 = new JLabel("Orden de publicacion");
		label_31.setForeground(SystemColor.desktop);
		label_31.setHorizontalAlignment(SwingConstants.RIGHT);
		label_31.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_31.setBounds(70, 124, 154, 33);
		pnTazasSEAM.add(label_31);

		tRetiLicencia = new JTextField();
		tRetiLicencia.setText("0");

		tRetiLicencia.setEnabled(false);
		tRetiLicencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}

			}

			//Poner puntos cada 3 numeros
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tRetiLicencia.setText(decimalmiles.format(Double.parseDouble(tRetiLicencia.getText().replace(".", ""))));

				SumarTasaSEAM();
			}
		});
		tRetiLicencia.setHorizontalAlignment(SwingConstants.RIGHT);
		tRetiLicencia.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tRetiLicencia.setColumns(10);
		tRetiLicencia.setBounds(233, 175, 179, 33);
		pnTazasSEAM.add(tRetiLicencia);

		JLabel label_32 = new JLabel("GS.");
		label_32.setForeground(SystemColor.desktop);
		label_32.setHorizontalAlignment(SwingConstants.CENTER);
		label_32.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_32.setBounds(409, 175, 40, 33);
		pnTazasSEAM.add(label_32);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setForeground(Color.BLACK);
		lblTotal.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTotal.setBounds(70, 245, 154, 33);
		pnTazasSEAM.add(lblTotal);

		tTotalTasaSeam = new JTextField();

		tTotalTasaSeam.setText("0");
		tTotalTasaSeam.setHorizontalAlignment(SwingConstants.RIGHT);
		tTotalTasaSeam.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tTotalTasaSeam.setEnabled(false);
		tTotalTasaSeam.setColumns(10);
		tTotalTasaSeam.setBounds(233, 244, 179, 33);
		pnTazasSEAM.add(tTotalTasaSeam);

		JLabel label_29 = new JLabel("GS.");
		label_29.setHorizontalAlignment(SwingConstants.CENTER);
		label_29.setForeground(Color.BLACK);
		label_29.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_29.setBounds(409, 244, 40, 33);
		pnTazasSEAM.add(label_29);

		JPanel pnGastoCombustible = new JPanel();
		pnGastoCombustible.setBackground(SystemColor.activeCaption);
		tpCostos.addTab("Gasto de Combustible y Viatico", null, pnGastoCombustible, null);
		pnGastoCombustible.setLayout(null);

		JPanel pnViatico = new JPanel();
		pnViatico.setLayout(null);
		pnViatico.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Viatico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnViatico.setBackground(SystemColor.activeCaption);
		pnViatico.setBounds(48, 244, 817, 158);
		pnGastoCombustible.add(pnViatico);

		tCantPersonas = new JTextField();
		tCantPersonas.setText("0");
		tCantPersonas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				CalculoViatico();
			}
		});
		tCantPersonas.setBounds(224, 23, 58, 33);
		pnViatico.add(tCantPersonas);
		tCantPersonas.setHorizontalAlignment(SwingConstants.RIGHT);
		tCantPersonas.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCantPersonas.setEnabled(false);
		tCantPersonas.setColumns(10);

		JLabel lblCostoPorPersona = new JLabel("Costo por Persona");
		lblCostoPorPersona.setBounds(415, 23, 154, 33);
		pnViatico.add(lblCostoPorPersona);
		lblCostoPorPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCostoPorPersona.setForeground(Color.BLACK);
		lblCostoPorPersona.setFont(new Font("SansSerif", Font.BOLD, 15));

		tCostoPorPersona = new JTextField();
		tCostoPorPersona.setText("0");

		tCostoPorPersona.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tCostoPorPersona.setText(decimalmiles.format(Double.parseDouble(tCostoPorPersona.getText().replace(".", ""))));

				CalculoViatico();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tCostoPorPersona.setBounds(578, 23, 179, 33);
		pnViatico.add(tCostoPorPersona);
		tCostoPorPersona.setHorizontalAlignment(SwingConstants.RIGHT);
		tCostoPorPersona.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCostoPorPersona.setEnabled(false);
		tCostoPorPersona.setColumns(10);

		JLabel label_26 = new JLabel("GS.");
		label_26.setBounds(754, 23, 40, 33);
		pnViatico.add(label_26);
		label_26.setHorizontalAlignment(SwingConstants.CENTER);
		label_26.setForeground(Color.BLACK);
		label_26.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel label_27 = new JLabel("Tipo de Alojamiento");
		label_27.setBounds(61, 67, 154, 33);
		pnViatico.add(label_27);
		label_27.setHorizontalAlignment(SwingConstants.RIGHT);
		label_27.setForeground(Color.BLACK);
		label_27.setFont(new Font("SansSerif", Font.BOLD, 15));

		cbTipoAlojamiento = new JComboBox();
		cbTipoAlojamiento.setBounds(224, 67, 179, 33);
		pnViatico.add(cbTipoAlojamiento);
		cbTipoAlojamiento.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbTipoAlojamiento.setEnabled(false);

		JLabel label_24 = new JLabel("Cantidad de Personas");
		label_24.setBounds(36, 23, 179, 33);
		pnViatico.add(label_24);
		label_24.setHorizontalAlignment(SwingConstants.RIGHT);
		label_24.setForeground(Color.BLACK);
		label_24.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblCostoTotalViatico = new JLabel("Costo Total Viatico");
		lblCostoTotalViatico.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCostoTotalViatico.setForeground(Color.BLACK);
		lblCostoTotalViatico.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCostoTotalViatico.setBounds(415, 67, 154, 33);
		pnViatico.add(lblCostoTotalViatico);

		tCostoTotalViatico = new JTextField();
		tCostoTotalViatico.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tCostoTotalViatico.setText("0");
		tCostoTotalViatico.setHorizontalAlignment(SwingConstants.RIGHT);
		tCostoTotalViatico.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCostoTotalViatico.setEnabled(false);
		tCostoTotalViatico.setColumns(10);
		tCostoTotalViatico.setBounds(578, 67, 179, 33);
		pnViatico.add(tCostoTotalViatico);

		JLabel label_19 = new JLabel("GS.");
		label_19.setHorizontalAlignment(SwingConstants.CENTER);
		label_19.setForeground(Color.BLACK);
		label_19.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_19.setBounds(754, 67, 40, 33);
		pnViatico.add(label_19);

		JPanel pnCombustible = new JPanel();
		pnCombustible.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "C\u00E1lculo Combustible", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnCombustible.setBackground(SystemColor.activeCaption);
		pnCombustible.setBounds(48, 23, 817, 196);
		pnGastoCombustible.add(pnCombustible);
		pnCombustible.setLayout(null);

		JLabel label_3 = new JLabel("Distancia");
		label_3.setBounds(43, 32, 154, 33);
		pnCombustible.add(label_3);
		label_3.setForeground(SystemColor.desktop);
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("SansSerif", Font.BOLD, 15));

		tDistancia = new JTextField();
		tDistancia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				CalculoCombustible();
			}
		});
		tDistancia.setText("0");

		tDistancia.setBounds(206, 32, 142, 33);
		pnCombustible.add(tDistancia);
		tDistancia.setEnabled(false);

		tDistancia.setHorizontalAlignment(SwingConstants.RIGHT);
		tDistancia.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tDistancia.setColumns(10);

		JLabel label_6 = new JLabel("Km");
		label_6.setBounds(347, 32, 40, 33);
		pnCombustible.add(label_6);
		label_6.setForeground(SystemColor.desktop);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel label_14 = new JLabel("Tipo de Vehiculo");
		label_14.setBounds(43, 136, 154, 33);
		pnCombustible.add(label_14);
		label_14.setForeground(SystemColor.desktop);
		label_14.setHorizontalAlignment(SwingConstants.RIGHT);
		label_14.setFont(new Font("SansSerif", Font.BOLD, 15));

		cbTipoVehiculo = new JComboBox();
		cbTipoVehiculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalculoCombustible();
			}
		});
		cbTipoVehiculo.setBounds(206, 136, 142, 33);
		pnCombustible.add(cbTipoVehiculo);
		cbTipoVehiculo.setEnabled(false);
		cbTipoVehiculo.setFont(new Font("SansSerif", Font.PLAIN, 15));

		JLabel lblPrecioLitroDe = new JLabel("Precio litro de combustible");
		lblPrecioLitroDe.setBounds(-21, 84, 221, 33);
		pnCombustible.add(lblPrecioLitroDe);
		lblPrecioLitroDe.setForeground(SystemColor.desktop);
		lblPrecioLitroDe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecioLitroDe.setFont(new Font("SansSerif", Font.BOLD, 15));

		tPrecioCombustible = new JTextField();
		tPrecioCombustible.setText("0");

		tPrecioCombustible.setBounds(206, 84, 142, 33);
		pnCombustible.add(tPrecioCombustible);
		tPrecioCombustible.setEnabled(false);
		tPrecioCombustible.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}

			}

			//Poner puntos cada 3 numeros
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tPrecioCombustible.setText(decimalmiles.format(Double.parseDouble(tPrecioCombustible.getText().replace(".", ""))));

				CalculoCombustible();
			}
		});
		tPrecioCombustible.setHorizontalAlignment(SwingConstants.RIGHT);
		tPrecioCombustible.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tPrecioCombustible.setColumns(10);

		JLabel label = new JLabel("Gs.");
		label.setBounds(347, 84, 40, 33);
		pnCombustible.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("SansSerif", Font.BOLD, 15));

		tGastoLitroCombustible = new JTextField();
		tGastoLitroCombustible.setText("0,0");
		tGastoLitroCombustible.setBounds(633, 32, 86, 33);
		pnCombustible.add(tGastoLitroCombustible);
		tGastoLitroCombustible.setEnabled(false);
		tGastoLitroCombustible.setHorizontalAlignment(SwingConstants.RIGHT);
		tGastoLitroCombustible.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tGastoLitroCombustible.setColumns(10);

		JLabel lblLitros = new JLabel("Lts.");
		lblLitros.setBounds(717, 32, 40, 33);
		pnCombustible.add(lblLitros);
		lblLitros.setHorizontalAlignment(SwingConstants.CENTER);
		lblLitros.setForeground(Color.BLACK);
		lblLitros.setFont(new Font("SansSerif", Font.BOLD, 15));

		tTotalGastoCombustible = new JTextField();
		tTotalGastoCombustible.setText("0");
		tTotalGastoCombustible.setBounds(633, 84, 142, 33);
		pnCombustible.add(tTotalGastoCombustible);
		tTotalGastoCombustible.setEnabled(false);
		tTotalGastoCombustible.setHorizontalAlignment(SwingConstants.RIGHT);
		tTotalGastoCombustible.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tTotalGastoCombustible.setColumns(10);

		JLabel label_17 = new JLabel("Gs.");
		label_17.setBounds(771, 84, 40, 33);
		pnCombustible.add(label_17);
		label_17.setForeground(SystemColor.desktop);
		label_17.setHorizontalAlignment(SwingConstants.CENTER);
		label_17.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblGastoEstimadoDe = new JLabel("Gasto Estimado de Combustible");
		lblGastoEstimadoDe.setBounds(391, 32, 236, 33);
		pnCombustible.add(lblGastoEstimadoDe);
		lblGastoEstimadoDe.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGastoEstimadoDe.setForeground(Color.BLACK);
		lblGastoEstimadoDe.setFont(new Font("SansSerif", Font.BOLD, 15));

		JLabel lblTotalGastoEstimado = new JLabel("Total Gasto Estimado");
		lblTotalGastoEstimado.setBounds(406, 84, 221, 33);
		pnCombustible.add(lblTotalGastoEstimado);
		lblTotalGastoEstimado.setForeground(SystemColor.desktop);
		lblTotalGastoEstimado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalGastoEstimado.setFont(new Font("SansSerif", Font.BOLD, 15));

		DecimalFormat decimalFormat = new DecimalFormat("0.0E0");

		JPanel pnSubContrato = new JPanel();
		pnSubContrato.setBackground(SystemColor.activeCaption);
		tpCostos.addTab("SubContrato", null, pnSubContrato, null);
		pnSubContrato.setLayout(null);

		chbSubContrato = new JCheckBox("Subcontrato");
		chbSubContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbSubContrato.isSelected()) {
					cbProfesional.setEnabled(true);
					tSubNomApe.setEnabled(true);
					tRuc.setEnabled(true);
					tHonorarios.setEnabled(true);
				} else {
					cbProfesional.setEnabled(false);
					tSubNomApe.setEnabled(false);
					tRuc.setEnabled(false);
					tHonorarios.setEnabled(false);
				}
			}
		});
		chbSubContrato.setEnabled(false);
		chbSubContrato.setForeground(SystemColor.desktop);
		chbSubContrato.setHorizontalAlignment(SwingConstants.RIGHT);
		chbSubContrato.setFont(new Font("SansSerif", Font.BOLD, 17));
		chbSubContrato.setBounds(108, 11, 135, 33);
		pnSubContrato.add(chbSubContrato);

		JLabel label_20 = new JLabel("Profesional");
		label_20.setForeground(SystemColor.desktop);
		label_20.setHorizontalAlignment(SwingConstants.RIGHT);
		label_20.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_20.setBounds(145, 61, 107, 33);
		pnSubContrato.add(label_20);

		tRuc = new JTextField();
		tRuc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if ((caracter == '-') || (caracter >= '0' && caracter <= '9')) {

				} else {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tRuc.setHorizontalAlignment(SwingConstants.RIGHT);
		tRuc.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tRuc.setEnabled(false);
		tRuc.setColumns(10);
		tRuc.setBounds(261, 153, 179, 33);
		pnSubContrato.add(tRuc);

		JLabel label_21 = new JLabel("Honorarios");
		label_21.setForeground(SystemColor.desktop);
		label_21.setHorizontalAlignment(SwingConstants.RIGHT);
		label_21.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_21.setBounds(156, 198, 98, 33);
		pnSubContrato.add(label_21);

		tHonorarios = new JTextField();
		tHonorarios.setText("0");

		tHonorarios.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = arg0.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					arg0.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tHonorarios.setText(decimalmiles.format(Double.parseDouble(tHonorarios.getText().replace(".", ""))));
			}
		});
		tHonorarios.setHorizontalAlignment(SwingConstants.RIGHT);
		tHonorarios.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tHonorarios.setEnabled(false);
		tHonorarios.setColumns(10);
		tHonorarios.setBounds(261, 198, 179, 33);
		pnSubContrato.add(tHonorarios);

		JLabel label_22 = new JLabel("RUC");
		label_22.setForeground(SystemColor.desktop);
		label_22.setHorizontalAlignment(SwingConstants.RIGHT);
		label_22.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_22.setBounds(190, 153, 64, 33);
		pnSubContrato.add(label_22);

		JLabel label_18 = new JLabel("GS.");
		label_18.setHorizontalAlignment(SwingConstants.CENTER);
		label_18.setForeground(Color.BLACK);
		label_18.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_18.setBounds(436, 198, 40, 33);
		pnSubContrato.add(label_18);

		JLabel lblNombreYApellido = new JLabel("Nombre y Apellido");
		lblNombreYApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombreYApellido.setForeground(Color.BLACK);
		lblNombreYApellido.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNombreYApellido.setBounds(117, 108, 135, 33);
		pnSubContrato.add(lblNombreYApellido);

		tSubNomApe = new JTextField();
		tSubNomApe.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tSubNomApe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tSubNomApe.setText(tSubNomApe.getText().toUpperCase());
				}
			}
		});
		tSubNomApe.setEnabled(false);
		tSubNomApe.setBounds(261, 108, 265, 33);
		pnSubContrato.add(tSubNomApe);
		tSubNomApe.setColumns(10);
		
		cbProfesional = new JComboBox();
		cbProfesional.setEnabled(false);
		cbProfesional.setFont(new Font("SansSerif", Font.PLAIN, 17));
		cbProfesional.setBounds(264, 61, 399, 33);
		pnSubContrato.add(cbProfesional);

		JPanel pnProcesos = new JPanel();
		tpCostos.addTab("Procesos", null, pnProcesos, null);
		pnProcesos.setBackground(SystemColor.activeCaption);
		pnProcesos.setLayout(null);

		chbRegistraInmueble = new JCheckBox("Registrar Inmueble");
		chbRegistraInmueble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbRegistraInmueble.isSelected()) {
					tRegistrarInmueble.setEnabled(true);
				} else {
					tRegistrarInmueble.setEnabled(false);
				}
			}
		});
		chbRegistraInmueble.setEnabled(false);
		chbRegistraInmueble.setBorderPaintedFlat(true);
		chbRegistraInmueble.setBorderPainted(true);
		chbRegistraInmueble.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		chbRegistraInmueble.setFont(new Font("SansSerif", Font.BOLD, 14));
		chbRegistraInmueble.setBounds(3, 19, 167, 85);
		pnProcesos.add(chbRegistraInmueble);

		JPanel pcCostosProcesos = new JPanel();
		pcCostosProcesos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Costos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pcCostosProcesos.setBackground(SystemColor.activeCaption);
		pcCostosProcesos.setBounds(3, 114, 906, 144);
		pnProcesos.add(pcCostosProcesos);
		pcCostosProcesos.setLayout(null);

		tRegistrarInmueble = new JTextField();
		tRegistrarInmueble.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tRegistrarInmueble.setText("0");
		tRegistrarInmueble.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tRegistrarInmueble.setText(decimalmiles.format(Double.parseDouble(tRegistrarInmueble.getText().replace(".", ""))));
			}
		});
		tRegistrarInmueble.setHorizontalAlignment(SwingConstants.RIGHT);
		tRegistrarInmueble.setEnabled(false);
		tRegistrarInmueble.setColumns(10);
		tRegistrarInmueble.setBounds(31, 20, 103, 33);
		pcCostosProcesos.add(tRegistrarInmueble);

		tRealizarPagoTasa = new JTextField();
		tRealizarPagoTasa.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tRealizarPagoTasa.setText("0");
		tRealizarPagoTasa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tRealizarPagoTasa.setText(decimalmiles.format(Double.parseDouble(tRealizarPagoTasa.getText().replace(".", ""))));
			}
		});
		tRealizarPagoTasa.setHorizontalAlignment(SwingConstants.RIGHT);
		tRealizarPagoTasa.setEnabled(false);
		tRealizarPagoTasa.setColumns(10);
		tRealizarPagoTasa.setBounds(208, 20, 103, 33);
		pcCostosProcesos.add(tRealizarPagoTasa);

		tCostoCuentaCatastral = new JTextField();
		tCostoCuentaCatastral.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCostoCuentaCatastral.setText("0");
		tCostoCuentaCatastral.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tCostoCuentaCatastral.setText(decimalmiles.format(Double.parseDouble(tCostoCuentaCatastral.getText().replace(".", ""))));
			}
		});
		tCostoCuentaCatastral.setHorizontalAlignment(SwingConstants.RIGHT);
		tCostoCuentaCatastral.setEnabled(false);
		tCostoCuentaCatastral.setColumns(10);
		tCostoCuentaCatastral.setBounds(406, 20, 103, 33);
		pcCostosProcesos.add(tCostoCuentaCatastral);

		tPrepararCarpetaSeam = new JTextField();
		tPrepararCarpetaSeam.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tPrepararCarpetaSeam.setText("0");
		tPrepararCarpetaSeam.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tPrepararCarpetaSeam.setText(decimalmiles.format(Double.parseDouble(tPrepararCarpetaSeam.getText().replace(".", ""))));
			}
		});
		tPrepararCarpetaSeam.setHorizontalAlignment(SwingConstants.RIGHT);
		tPrepararCarpetaSeam.setEnabled(false);
		tPrepararCarpetaSeam.setColumns(10);
		tPrepararCarpetaSeam.setBounds(599, 20, 103, 33);
		pcCostosProcesos.add(tPrepararCarpetaSeam);

		tPermisoAmbiental = new JTextField();
		tPermisoAmbiental.setBounds(780, 20, 103, 33);
		pcCostosProcesos.add(tPermisoAmbiental);
		tPermisoAmbiental.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tPermisoAmbiental.setText("0");
		tPermisoAmbiental.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tPermisoAmbiental.setText(decimalmiles.format(Double.parseDouble(tPermisoAmbiental.getText().replace(".", ""))));
			}
		});
		tPermisoAmbiental.setHorizontalAlignment(SwingConstants.RIGHT);
		tPermisoAmbiental.setEnabled(false);
		tPermisoAmbiental.setColumns(10);

		chbObsProcesos = new JCheckBox("Adicional Obs");
		chbObsProcesos.setBounds(780, 67, 104, 18);
		pcCostosProcesos.add(chbObsProcesos);
		chbObsProcesos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbObsProcesos.isSelected()) {
					tAdicionalObs.setEnabled(true);
				} else {
					tAdicionalObs.setEnabled(false);
				}
			}
		});
		chbObsProcesos.setEnabled(false);

		tAdicionalObs = new JTextField();
		tAdicionalObs.setBounds(780, 87, 103, 33);
		pcCostosProcesos.add(tAdicionalObs);
		tAdicionalObs.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tAdicionalObs.setText("0");
		tAdicionalObs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
				tAdicionalObs.setText(decimalmiles.format(Double.parseDouble(tAdicionalObs.getText().replace(".", ""))));
			}
		});
		tAdicionalObs.setHorizontalAlignment(SwingConstants.RIGHT);
		tAdicionalObs.setEnabled(false);
		tAdicionalObs.setColumns(10);

		chbRealizarPagoTasa = new JCheckBox("Realizar Pago de Tasa");
		chbRealizarPagoTasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbRealizarPagoTasa.isSelected()) {
					tRealizarPagoTasa.setEnabled(true);
				} else {
					tRealizarPagoTasa.setEnabled(false);
				}
			}
		});
		chbRealizarPagoTasa.setEnabled(false);
		chbRealizarPagoTasa.setFont(new Font("SansSerif", Font.BOLD, 14));
		chbRealizarPagoTasa.setBorderPaintedFlat(true);
		chbRealizarPagoTasa.setBorderPainted(true);
		chbRealizarPagoTasa.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		chbRealizarPagoTasa.setBounds(173, 19, 179, 85);
		pnProcesos.add(chbRealizarPagoTasa);

		chbCostoCuentaCatastral = new JCheckBox("Costo Cuenta Catastral");
		chbCostoCuentaCatastral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbCostoCuentaCatastral.isSelected()) {
					tCostoCuentaCatastral.setEnabled(true);
				} else {
					tCostoCuentaCatastral.setEnabled(false);
				}
			}
		});
		chbCostoCuentaCatastral.setEnabled(false);
		chbCostoCuentaCatastral.setFont(new Font("SansSerif", Font.BOLD, 14));
		chbCostoCuentaCatastral.setBorderPaintedFlat(true);
		chbCostoCuentaCatastral.setBorderPainted(true);
		chbCostoCuentaCatastral.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		chbCostoCuentaCatastral.setBounds(354, 19, 194, 85);
		pnProcesos.add(chbCostoCuentaCatastral);

		chbPrepararCarpetaSeam = new JCheckBox("Preparar Carpeta SEAM");
		chbPrepararCarpetaSeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbPrepararCarpetaSeam.isSelected()) {
					tPrepararCarpetaSeam.setEnabled(true);
				} else {
					tPrepararCarpetaSeam.setEnabled(false);
				}
			}
		});
		chbPrepararCarpetaSeam.setEnabled(false);
		chbPrepararCarpetaSeam.setFont(new Font("SansSerif", Font.BOLD, 14));
		chbPrepararCarpetaSeam.setBorderPaintedFlat(true);
		chbPrepararCarpetaSeam.setBorderPainted(true);
		chbPrepararCarpetaSeam.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		chbPrepararCarpetaSeam.setBounds(549, 19, 194, 85);
		pnProcesos.add(chbPrepararCarpetaSeam);

		chbPermisoAmbiental = new JCheckBox("Permiso Ambiental");
		chbPermisoAmbiental.setBounds(745, 19, 164, 85);
		pnProcesos.add(chbPermisoAmbiental);
		chbPermisoAmbiental.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chbPermisoAmbiental.isSelected()) {
					tPermisoAmbiental.setEnabled(true);
				} else {
					tPermisoAmbiental.setEnabled(false);
				}
			}
		});
		chbPermisoAmbiental.setEnabled(false);
		chbPermisoAmbiental.setFont(new Font("SansSerif", Font.BOLD, 14));
		chbPermisoAmbiental.setBorderPaintedFlat(true);
		chbPermisoAmbiental.setBorderPainted(true);
		chbPermisoAmbiental.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		JPanel pnTotal = new JPanel();

		pnTotal.setBackground(SystemColor.activeCaption);
		pnTotal.setLayout(null);
		tpCostos.addTab("Total", null, pnTotal, null);

		JLabel lblTotalDeCostos = new JLabel("Total de Costos");
		lblTotalDeCostos.setForeground(SystemColor.desktop);
		lblTotalDeCostos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalDeCostos.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTotalDeCostos.setBounds(75, 68, 135, 33);
		pnTotal.add(lblTotalDeCostos);

		tTotalCostos = new JTextField();

		tTotalCostos.setText("0");

		tTotalCostos.setEnabled(false);
		tTotalCostos.setHorizontalAlignment(SwingConstants.RIGHT);
		tTotalCostos.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tTotalCostos.setColumns(10);
		tTotalCostos.setBounds(219, 68, 179, 33);
		pnTotal.add(tTotalCostos);

		tPorcentaje = new JTextField();
		tPorcentaje.setText("25");
		tPorcentaje.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = arg0.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					arg0.consume(); // ignorar el evento de teclado
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				CalculoPorcentaje();
			}
		});
		tPorcentaje.setEnabled(false);
		tPorcentaje.setHorizontalAlignment(SwingConstants.RIGHT);
		tPorcentaje.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tPorcentaje.setColumns(10);
		tPorcentaje.setBounds(219, 107, 49, 33);
		pnTotal.add(tPorcentaje);

		JLabel lblValorDelProyecto = new JLabel("Valor del Proyecto");
		lblValorDelProyecto.setForeground(SystemColor.desktop);
		lblValorDelProyecto.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValorDelProyecto.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblValorDelProyecto.setBounds(31, 26, 179, 33);
		pnTotal.add(lblValorDelProyecto);

		tValorTotalProyecto = new JTextField();
		tValorTotalProyecto.setText("0");
		tValorTotalProyecto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tValorTotalProyecto.setEnabled(false);
		tValorTotalProyecto.setHorizontalAlignment(SwingConstants.RIGHT);
		tValorTotalProyecto.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tValorTotalProyecto.setColumns(10);
		tValorTotalProyecto.setBounds(219, 26, 179, 33);
		pnTotal.add(tValorTotalProyecto);

		JLabel lblMargenDeGanancia = new JLabel("Margen de Ganancia");
		lblMargenDeGanancia.setForeground(SystemColor.desktop);
		lblMargenDeGanancia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMargenDeGanancia.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblMargenDeGanancia.setBounds(56, 107, 154, 33);
		pnTotal.add(lblMargenDeGanancia);

		JLabel label_30 = new JLabel("%");
		label_30.setForeground(SystemColor.desktop);
		label_30.setHorizontalAlignment(SwingConstants.LEFT);
		label_30.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_30.setBounds(268, 108, 40, 33);
		pnTotal.add(label_30);

		JLabel label_11 = new JLabel("Gs.");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setForeground(Color.BLACK);
		label_11.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_11.setBounds(397, 26, 40, 33);
		pnTotal.add(label_11);

		JLabel label_23 = new JLabel("Gs.");
		label_23.setHorizontalAlignment(SwingConstants.CENTER);
		label_23.setForeground(Color.BLACK);
		label_23.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_23.setBounds(397, 68, 40, 33);
		pnTotal.add(label_23);

		JPanel pnTableProyectos = new JPanel();
		pnTableProyectos.setBorder(new TitledBorder(null, "Proyectos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnTableProyectos.setBackground(SystemColor.activeCaption);
		pnTableProyectos.setBounds(6, 6, 537, 370);
		getContentPane().add(pnTableProyectos);
		pnTableProyectos.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 21, 504, 289);
		pnTableProyectos.add(scrollPane);

		table = new JTable();
		MostrarResultSetEnJtable("", "");
		scrollPane.setViewportView(table);

		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(15);
		comboBox.setModel(new DefaultComboBoxModel(
				new String[] { "Codigo", "Denominacion", "Tipo Emprendimiento", "Fecha Contrato", "Fecha Entrega", "Cliente", "Funcionario", "Usuario", "Valor del Proyecto", "Total de Costos", "Porcentaje de Ganancias", "Todos" }));
		comboBox.setSelectedIndex(11);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox.setBounds(99, 305, 174, 33);
		pnTableProyectos.add(comboBox);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(272, 305, 248, 33);
		pnTableProyectos.add(textField);

		JLabel label_5 = new JLabel("Buscar por");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_5.setBounds(16, 305, 92, 33);
		pnTableProyectos.add(label_5);

		JPanel pnFiltradoPor = new JPanel();
		pnFiltradoPor.setLayout(null);
		pnFiltradoPor.setBorder(new TitledBorder(null, "Filtrado por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnFiltradoPor.setBackground(SystemColor.activeCaption);
		pnFiltradoPor.setBounds(6, 374, 535, 242);
		getContentPane().add(pnFiltradoPor);

		JLabel lblNro = new JLabel("Nro");
		lblNro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNro.setForeground(Color.BLACK);
		lblNro.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblNro.setBounds(68, 13, 63, 33);
		pnFiltradoPor.add(lblNro);

		tCodigoInicialProyecto = new JTextField();
		tCodigoInicialProyecto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});

		tCodigoInicialProyecto.setText("0");
		tCodigoInicialProyecto.setHorizontalAlignment(SwingConstants.RIGHT);
		tCodigoInicialProyecto.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tCodigoInicialProyecto.setColumns(10);
		tCodigoInicialProyecto.setBounds(137, 13, 125, 33);
		pnFiltradoPor.add(tCodigoInicialProyecto);

		JLabel lblA = new JLabel("a");
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		lblA.setForeground(Color.BLACK);
		lblA.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblA.setBounds(262, 13, 41, 33);
		pnFiltradoPor.add(lblA);

		tCodigoFinalProyecto = new JTextField();
		tCodigoFinalProyecto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});

		tCodigoFinalProyecto.setText("9999");
		tCodigoFinalProyecto.setHorizontalAlignment(SwingConstants.RIGHT);
		tCodigoFinalProyecto.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tCodigoFinalProyecto.setColumns(10);
		tCodigoFinalProyecto.setBounds(305, 13, 132, 33);
		pnFiltradoPor.add(tCodigoFinalProyecto);

		JLabel lblCliente_1 = new JLabel("Cliente");
		lblCliente_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCliente_1.setForeground(Color.BLACK);
		lblCliente_1.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblCliente_1.setBounds(68, 59, 63, 33);
		pnFiltradoPor.add(lblCliente_1);

		tInicialCliente = new JTextField();
		tInicialCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tInicialCliente.setText(tInicialCliente.getText().toUpperCase());
				}
			}
		});
		tInicialCliente.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tInicialCliente.setColumns(10);
		tInicialCliente.setBounds(137, 59, 125, 33);
		pnFiltradoPor.add(tInicialCliente);

		JLabel label_1 = new JLabel("a");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_1.setBounds(262, 59, 41, 33);
		pnFiltradoPor.add(label_1);

		tFinalCliente = new JTextField();
		tFinalCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tFinalCliente.setText(tFinalCliente.getText().toUpperCase());
				}
			}
		});
		tFinalCliente.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tFinalCliente.setColumns(10);
		tFinalCliente.setBounds(305, 59, 132, 33);
		pnFiltradoPor.add(tFinalCliente);

		JLabel lblFecha = new JLabel("Fecha Contrato");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setForeground(Color.BLACK);
		lblFecha.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblFecha.setBounds(16, 105, 115, 33);
		pnFiltradoPor.add(lblFecha);

		JLabel label_4 = new JLabel("a");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_4.setBounds(262, 105, 41, 33);
		pnFiltradoPor.add(label_4);

		dcFechaInicialContrato = new JDateChooser();
		dcFechaInicialContrato.setBounds(137, 105, 125, 33);
		pnFiltradoPor.add(dcFechaInicialContrato);

		dcFechaFinalContrato = new JDateChooser();
		dcFechaFinalContrato.setBounds(305, 105, 132, 33);
		pnFiltradoPor.add(dcFechaFinalContrato);

		JLabel lblFechaEntrega = new JLabel("Fecha Entrega");
		lblFechaEntrega.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaEntrega.setForeground(Color.BLACK);
		lblFechaEntrega.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblFechaEntrega.setBounds(16, 151, 115, 33);
		pnFiltradoPor.add(lblFechaEntrega);

		dcFechaInicialEntrega = new JDateChooser();
		dcFechaInicialEntrega.setBounds(137, 151, 125, 33);
		pnFiltradoPor.add(dcFechaInicialEntrega);

		JLabel label_13 = new JLabel("a");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setForeground(Color.BLACK);
		label_13.setFont(new Font("SansSerif", Font.BOLD, 15));
		label_13.setBounds(262, 151, 41, 33);
		pnFiltradoPor.add(label_13);

		dcFechaFinalEntrega = new JDateChooser();
		dcFechaFinalEntrega.setBounds(305, 151, 132, 33);
		pnFiltradoPor.add(dcFechaFinalEntrega);

		JButton btnFiltro = new JButton("Filtro");
		btnFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ordensql = " ORDER BY pro_codigo";
				String filtrosql = "";
				int contador = 0;
				if (tCodigoInicialProyecto.getText().isEmpty() == false || tCodigoFinalProyecto.getText().isEmpty() == false) {
					filtrosql = filtrosql + " AND pro_codigo BETWEEN '" + tCodigoInicialProyecto.getText() + "' AND " + "'" + tCodigoFinalProyecto.getText() + "'";
					ordensql = " ORDER BY pro_codigo";
					contador = contador + 1;
				}

				if (tInicialCliente.getText().isEmpty() == false || tFinalCliente.getText().isEmpty() == false) {
					filtrosql = filtrosql + " AND cli_nombre BETWEEN '" + tInicialCliente.getText() + "' AND " + "'" + tFinalCliente.getText() + "zz'";
					ordensql = " ORDER BY lib_descri";
					contador = contador + 1;
				}

				if (dcFechaInicialContrato.getDate() != null || dcFechaFinalContrato.getDate() != null) {
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					filtrosql = filtrosql + " AND pro_fechacontrato BETWEEN '" + formato.format(dcFechaInicialContrato.getDate()) + "' AND " + "'" + formato.format(dcFechaFinalContrato.getDate()) + "'";
					ordensql = " ORDER BY pro_fechacontrato";
					contador = contador + 1;
				}

				if (dcFechaInicialEntrega.getDate() != null || dcFechaFinalEntrega.getDate() != null) {
					SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
					filtrosql = filtrosql + " AND pro_fechaentrega BETWEEN '" + formato.format(dcFechaInicialEntrega.getDate()) + "' AND " + "'" + formato.format(dcFechaFinalEntrega.getDate()) + "'";
					ordensql = " ORDER BY pro_fechaentrega";
					contador = contador + 1;
				}

				if (contador != 1) {
					ordensql = " ORDER BY pro_codigo";
				}

				MostrarResultSetEnJtable(filtrosql, ordensql);
				scrollPane.setViewportView(table);
			}
		});
		btnFiltro.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnFiltro.setBounds(328, 196, 90, 33);
		pnFiltradoPor.add(btnFiltro);

		JPanel pnButtons = new JPanel();
		pnButtons.setBounds(1498, 41, 154, 396);
		getContentPane().add(pnButtons);
		pnButtons.setLayout(null);
		pnButtons.setBackground(SystemColor.activeCaption);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				habilitarModoEdicion(true);
				MostrarEnTextFieldProyectoSeleccionado();
				SiTrueORFalseCheckbox();
			}
		});
		btnModificar.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/modificar-el-icono-del-documento-14646 copia.png")));
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModificar.setEnabled(false);
		btnModificar.setBounds(4, 166, 145, 62);
		pnButtons.add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar el Proyecto " + table.getValueAt(row, 1).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						Utilidades utilidades = new Utilidades();
						utilidades.ejecutarSQL("DELETE FROM proyecto WHERE pro_codigo = " + Integer.parseInt(table.getValueAt(row, 0).toString()));
						utilidades.ejecutarSQL("DELETE FROM inmueble WHERE in_codigo = " + Integer.parseInt(table.getValueAt(row, 0).toString()));

						File fichero = new File("src/imagencroquis/" + "imagen" + tCodigoInmueble.getText() + ".jpg");

						if (fichero.delete()) {
							System.out.println("El fichero ha sido borrado satisfactoriamente");
						} else {
							System.out.println("El fichero no puede ser borrado");
						}

						ActualizarTabla();
						JOptionPane.showMessageDialog(null, "Proyecto eliminado con exito");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e);
					}
				}

			}
		});
		btnEliminar.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/document_delete_128 copia.png")));
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(4, 280, 145, 62);
		pnButtons.add(btnEliminar);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				habilitarModoEdicion(true);

				try {
					ResultSet rs = AdministrarProyectosSession.obtenercodigoultimoregistro();
					rs.next();
					int UltimoCodigo = rs.getInt("pro_codigo");
					gettNroProyecto().setText((UltimoCodigo + 1) + "");
					gettCodigoInmueble().setText((UltimoCodigo + 1) + "");

				} catch (Exception e2) {
					gettNroProyecto().setText("1");
					gettCodigoInmueble().setText("1");
					System.out.println("Error en rs" + e2);
				}

				//ObtenerUsuario
				try {
					String login = Principal.tLogin.getText();
					ResultSet usuario = AdministrarProyectosSession.obtenerUsuario(login);
					usuario.next();
					tCodigoUsuario.setText(usuario.getString("usu_codigo"));
					tNomApeUsuario.setText(usuario.getString("nomape"));
				} catch (SQLException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}

			}

		});
		btnNuevo.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/new-file-33984.png")));
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNuevo.setBounds(4, 52, 145, 62);
		pnButtons.add(btnNuevo);
		
		ComboConsultaVehiculos();
		ComboConsultaDepartamentos();
		ComboConsultaTipoEmprendimiento();
		ComboConsultaProfesional();
		ComboConsultaAlojamiento();

		//Privilegios
		try {
			Utilidades utilidades = new Utilidades();
			ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
			UsuarioPrivilegios.next();
			btnNuevo.setEnabled(UsuarioPrivilegios.getBoolean("pri_proyectonuevo"));
			btnModificar.setEnabled(UsuarioPrivilegios.getBoolean("pri_proyectomodificar"));
			btnEliminar.setEnabled(UsuarioPrivilegios.getBoolean("pri_proyectoeliminar"));

			btnInforme = new JButton("Informe");
			btnInforme.setBounds(76, 636, 154, 60);
			getContentPane().add(btnInforme);
			btnInforme.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/distributor-report-icon copia.png")));
			btnInforme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerarReporte();
				}
			});
			btnInforme.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnInforme.setEnabled(UsuarioPrivilegios.getBoolean("pri_proyectoinforme"));

			btnInformeIndividual = new JButton("Informe Individual");
			btnInformeIndividual.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerarReporteProyectoIndividual();
				}
			});
			btnInformeIndividual.setIcon(new ImageIcon(AdministrarProyectos.class.getResource("/icono/icono.reporte.rt_-200x200.png")));
			btnInformeIndividual.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnInformeIndividual.setEnabled(false);
			btnInformeIndividual.setBounds(260, 636, 221, 60);
			getContentPane().add(btnInformeIndividual);

			UsuarioPrivilegios.close();
		} catch (SQLException e1) {
			// TODO Bloque catch generado automáticamente
			System.out.println("Error al ejecutar privilegios de usuario " + e1);
		}
	}

	//Cargar combo con consulta
	private void ComboConsultaVehiculos() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbTipoVehiculo.removeAllItems();
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT ve_descri, ve_km FROM vehiculo ORDER BY ve_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbTipoVehiculo.addItem(rs.getObject("ve_descri"));

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}

	//Cargar combo con consulta
	private void ComboConsultaDepartamentos() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbDepartamento.removeAllItems();
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT de_descri FROM departamento ORDER BY de_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbDepartamento.addItem(rs.getObject("de_descri"));

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}

	//Cargar combo con consulta
	private void ComboConsultaProfesional() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT profe_descri FROM profesional ORDER BY profe_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbProfesional.addItem(rs.getObject("profe_descri"));

			}
			
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}

	//Cargar combo con consulta
	private void ComboConsultaDistritos(int coddepartamento) {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbDistrito.removeAllItems();
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT di_descri FROM distrito WHERE di_departamento = " + coddepartamento + " ORDER BY di_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbDistrito.addItem(rs.getObject("di_descri"));

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}

	//Cargar combo con consulta
	private void ComboConsultaTipoEmprendimiento() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbTipoEmprendimiento.removeAllItems();
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT em_descri FROM emprendimiento ORDER BY em_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbTipoEmprendimiento.addItem(rs.getObject("em_descri"));

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}

	private void ComboConsultaAlojamiento() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT alo_descri FROM alojamiento ORDER BY alo_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbTipoAlojamiento.addItem(rs.getObject("alo_descri"));

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}

	public JTextField gettNroProyecto() {
		return tNroProyecto;
	}

	public void settNroProyecto(JTextField tNroProyecto) {
		this.tNroProyecto = tNroProyecto;
	}

	public JTextField gettCodigoInmueble() {
		return tCodigoInmueble;
	}

	public void settCodigoInmueble(JTextField tCodigoInmueble) {
		this.tCodigoInmueble = tCodigoInmueble;
	}

	private void habilitarModoEdicion(boolean habilitar) {
		btnModificar.setEnabled(!habilitar);
		btnEliminar.setEnabled(!habilitar);
		tDenominacion.setEnabled(habilitar);
		cbTipoEmprendimiento.setEnabled(habilitar);
		dcFechaContrato.setEnabled(habilitar);
		dcFechaEntrega.setEnabled(habilitar);
		buttonClientes.setEnabled(habilitar);

		buttonConsultor.setEnabled(habilitar);

		tCantFinca.setEnabled(habilitar);
		tCantBloque.setEnabled(habilitar);
		tPropietario.setEnabled(habilitar);
		tNroPadron.setEnabled(habilitar);
		cbDepartamento.setEnabled(habilitar);
		cbDistrito.setEnabled(habilitar);
		tLocalidad.setEnabled(habilitar);

		tX.setEnabled(habilitar);
		tY.setEnabled(habilitar);

		chbImpuestoIn.setEnabled(habilitar);
		chbPlanoFincas.setEnabled(habilitar);
		chbTituloPropiedad.setEnabled(habilitar);
		lblImagen.setText("IMAGEN");
		btnCargarImagen.setEnabled(habilitar);

		tEntregar.setEnabled(habilitar);
		chbObs.setEnabled(habilitar);
		tOrdenPu.setEnabled(habilitar);
		tRetiLicencia.setEnabled(habilitar);

		tDistancia.setEnabled(habilitar);
		tPrecioCombustible.setEnabled(habilitar);
		cbTipoVehiculo.setEnabled(habilitar);

		chbSubContrato.setEnabled(habilitar);

		tCantPersonas.setEnabled(habilitar);
		cbTipoAlojamiento.setEnabled(habilitar);
		tCostoPorPersona.setEnabled(habilitar);

		chbRegistraInmueble.setEnabled(habilitar);
		chbRealizarPagoTasa.setEnabled(habilitar);
		chbCostoCuentaCatastral.setEnabled(habilitar);
		chbPrepararCarpetaSeam.setEnabled(habilitar);
		chbImpuestoIn.setEnabled(habilitar);
		chbPermisoAmbiental.setEnabled(habilitar);
		chbObsProcesos.setEnabled(habilitar);

		tPorcentaje.setEnabled(habilitar);

		btnNuevo.setEnabled(!habilitar);
		btnGuardar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);

		tNroProyecto.setText("");
		tDenominacion.setText("");
		cbTipoEmprendimiento.setSelectedIndex(0);
		dcFechaContrato.setDate(null);
		dcFechaEntrega.setDate(null);
		tCodigoCliente.setText("");
		tNomApeCliente.setText("");
		tCodigoConsultor.setText("");
		tNomApeConsultor.setText("");
		tCodigoUsuario.setText("");
		tNomApeUsuario.setText("");

		tCodigoInmueble.setText("");
		tCantFinca.setText("");
		tCantBloque.setText("");
		tPropietario.setText("");
		tNroPadron.setText("");
		cbDepartamento.setSelectedIndex(0);
		cbDistrito.setSelectedIndex(0);
		tLocalidad.setText("");
		tX.setText("");
		tY.setText("");
		chbTituloPropiedad.setSelected(false);
		chbImpuestoIn.setSelected(false);
		chbPlanoFincas.setSelected(false);
		lblImagen.setIcon(null);
		chbObs.setSelected(false);

		tEntregar.setText("0");
		tObs.setText("0");
		tAdicionalObs.setText("0");
		tOrdenPu.setText("0");
		tRetiLicencia.setText("0");
		tTotalTasaSeam.setText("0");
		tDistancia.setText("0");
		tPrecioCombustible.setText("0");
		cbTipoVehiculo.setSelectedIndex(0);
		tGastoLitroCombustible.setText("0,0");
		tTotalGastoCombustible.setText("0");
		tCantPersonas.setText("0");
		cbTipoAlojamiento.setSelectedIndex(0);
		tCostoPorPersona.setText("0");
		tCostoTotalViatico.setText("0");
		chbSubContrato.setSelected(false);
		cbProfesional.setSelectedIndex(0);
		tSubNomApe.setText("");
		tRuc.setText("");
		tHonorarios.setText("0");
		chbRegistraInmueble.setSelected(false);
		chbRealizarPagoTasa.setSelected(false);
		chbCostoCuentaCatastral.setSelected(false);
		chbPrepararCarpetaSeam.setSelected(false);
		chbPermisoAmbiental.setSelected(false);
		tRegistrarInmueble.setText("0");
		tRealizarPagoTasa.setText("0");
		tCostoCuentaCatastral.setText("0");
		tPrepararCarpetaSeam.setText("0");
		tPermisoAmbiental.setText("0");

		tValorTotalProyecto.setText("0");
		tTotalCostos.setText("0");
		tPorcentaje.setText("25");

		SiTrueORFalseCheckbox();

		if (habilitar == true) {
			table.setEnabled(false);
			table.setBackground(Color.LIGHT_GRAY);
			table.setForeground(Color.DARK_GRAY);
		} else {
			table.setEnabled(true);
			table.setBackground(Color.WHITE);
			table.setForeground(Color.BLACK);
		}

	}

	private void SumarTasaSEAM() {
		DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
		SumaTasaSeam = Integer.parseInt(tEntregar.getText().replace(".", "")) + Integer.parseInt(tObs.getText().replace(".", "")) + Integer.parseInt(tOrdenPu.getText().replace(".", "")) + Integer.parseInt(tRetiLicencia.getText().replace(".", ""));
		tTotalTasaSeam.setText(decimalmiles.format(SumaTasaSeam) + "");

	}

	private void SumarCostoTotal() {
		SumaCostos = Integer.parseInt(tTotalTasaSeam.getText().replace(".", "")) + Integer.parseInt(tTotalGastoCombustible.getText().replace(".", "")) + Integer.parseInt(tCostoTotalViatico.getText().replace(".", ""))
				+ Integer.parseInt(tHonorarios.getText().replace(".", "")) + Integer.parseInt(tRegistrarInmueble.getText().replace(".", "")) + Integer.parseInt(tRealizarPagoTasa.getText().replace(".", ""))
				+ Integer.parseInt(tCostoCuentaCatastral.getText().replace(".", "")) + Integer.parseInt(tPrepararCarpetaSeam.getText().replace(".", "")) + Integer.parseInt(tPermisoAmbiental.getText().replace(".", ""))
				+ Integer.parseInt(tAdicionalObs.getText().replace(".", ""));

		tTotalCostos.setText(SumaCostos + "");
		DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
		tTotalCostos.setText(decimalmiles.format(Double.parseDouble(tTotalCostos.getText().replace(".", ""))));
	}

	private void CalculoPorcentaje() {
		CalPorcentaje = ((Integer.parseInt(tPorcentaje.getText().replace(".", "")) * Integer.parseInt(tTotalCostos.getText().replace(".", ""))) / 100) + Integer.parseInt(tTotalCostos.getText().replace(".", ""));
		tValorTotalProyecto.setText(CalPorcentaje + "");

		DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
		tValorTotalProyecto.setText(decimalmiles.format(Double.parseDouble(tValorTotalProyecto.getText().replace(".", ""))));
	}

	private void CalculoCombustible() {
		try {
			ResultSet km = AdministrarProyectosSession.obtenerkm(cbTipoVehiculo.getSelectedIndex() + 1);
			km.next();
			DecimalFormat decimales = new DecimalFormat("#.#"); //el . se imprime como ,
			String Litro = decimales.format(Double.parseDouble(tDistancia.getText()) / Double.parseDouble(km.getString("ve_km")));
			tGastoLitroCombustible.setText(Litro + "");

			DecimalFormat decimallmiles = new DecimalFormat("###,###"); //la , se imprime como .
			String Multi = decimallmiles.format(Double.parseDouble(Litro.replace(',', '.')) * Integer.parseInt(tPrecioCombustible.getText().replace(".", "")));
			tTotalGastoCombustible.setText(Multi + "");
		} catch (NumberFormatException | SQLException ee) {
			// TODO Bloque catch generado automáticamente
			ee.printStackTrace();
		}
	}

	private void CalculoViatico() {
		TotalViatico = Integer.parseInt(tCantPersonas.getText().replace(".", "")) * Integer.parseInt(tCostoPorPersona.getText().replace(".", ""));
		tCostoTotalViatico.setText(TotalViatico + "");

		DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
		tCostoTotalViatico.setText(decimalmiles.format(Double.parseDouble(tCostoTotalViatico.getText().replace(".", ""))));
	}

	private void ActualizarTabla() {
		MostrarResultSetEnJtable("", "");
		scrollPane.setViewportView(table);
	}

	private void SiTrueORFalseCheckbox() {
		if (chbObs.isSelected()) {
			tObs.setEnabled(true);
		} else {
			tObs.setEnabled(false);
		}

		if (chbSubContrato.isSelected()) {
			cbProfesional.setEnabled(true);
			tSubNomApe.setEnabled(true);
			tRuc.setEnabled(true);
			tHonorarios.setEnabled(true);
		} else {
			cbProfesional.setEnabled(false);
			tSubNomApe.setEnabled(false);
			tRuc.setEnabled(false);
			tHonorarios.setEnabled(false);
		}

		if (chbRegistraInmueble.isSelected()) {
			tRegistrarInmueble.setEnabled(true);
		} else {
			tRegistrarInmueble.setEnabled(false);
		}

		if (chbRealizarPagoTasa.isSelected()) {
			tRealizarPagoTasa.setEnabled(true);
		} else {
			tRealizarPagoTasa.setEnabled(false);
		}

		if (chbCostoCuentaCatastral.isSelected()) {
			tCostoCuentaCatastral.setEnabled(true);
		} else {
			tCostoCuentaCatastral.setEnabled(false);
		}

		if (chbPrepararCarpetaSeam.isSelected()) {
			tPrepararCarpetaSeam.setEnabled(true);
		} else {
			tPrepararCarpetaSeam.setEnabled(false);
		}

		if (chbPrepararCarpetaSeam.isSelected()) {
			tPrepararCarpetaSeam.setEnabled(true);
		} else {
			tPrepararCarpetaSeam.setEnabled(false);
		}

		if (chbObsProcesos.isSelected()) {
			tAdicionalObs.setEnabled(true);
		} else {
			tAdicionalObs.setEnabled(false);
		}
	}

	//Se le pasa el sql en resultset rs
	@SuppressWarnings("serial")
	private void MostrarResultSetEnJtable(String filtrosql, String ordensql) {
		ResultSet rs = null;
		try {
			rs = utilidades.ejecutarSQLSelect("SELECT pro_codigo, pro_denominacion, em_descri, pro_fechacontrato, pro_fechaentrega,  CONCAT (CONCAT(cli_nombre,' '), "
					+ "cli_apelli) AS nomapecliente,  CONCAT (CONCAT(fun_nombre,' '), fun_apellido) AS nomapeconsultor, CONCAT (CONCAT(usu_nombre,' '), "
					+ "usu_apellido) AS nomapeusuario, pro_valorproyecto, pro_totalcostos, pro_porcentajeganancia FROM proyecto, cliente, funcionario, usuario, emprendimiento WHERE pro_codcliente = cli_codigo AND "
					+ "pro_codconsultor = fun_codigo AND pro_codusuario = usu_codigo AND pro_tipoempre = em_codigo" + filtrosql + ordensql + " ORDER BY pro_codigo");

			DefaultTableModel modelo = new DefaultTableModel();
			//Para que registros no sean editables
			table = new JTable(modelo) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					if (table.isEnabled() == true) {

						MostrarEnTextFieldProyectoSeleccionado();
						if (table.isEnabled() == true) {
							if (table.getSelectedRow() != -1) {
								try {
									btnModificar.setEnabled(true);
									btnEliminar.setEnabled(true);
									ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
									UsuarioPrivilegios.next();
									btnInformeIndividual.setEnabled(UsuarioPrivilegios.getBoolean("pri_proyectoinforme"));
									UsuarioPrivilegios.close();
								} catch (SQLException e) {
									// TODO Bloque catch generado automáticamente
									e.printStackTrace();
								}

							} else {
								btnModificar.setEnabled(false);
								btnEliminar.setEnabled(false);
							}
						}
					}
				}
			});
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //Para que solo se pueda seleccionar un registro a la vez
			table.getTableHeader().setResizingAllowed(false); //Deshabilitar cambios en el tamaño de las columnas de un JTable
			table.getTableHeader().setReorderingAllowed(false); //No Mover Columnas de un JTable

			// Creamos las columnas.
			modelo.addColumn("Codigo");
			modelo.addColumn("Denominacion");
			modelo.addColumn("Tipo Emprendimiento");
			modelo.addColumn("Fecha Contrato");
			modelo.addColumn("Fecha Entrega");
			modelo.addColumn("Cliente");
			modelo.addColumn("Consultor");
			modelo.addColumn("Usuario");
			modelo.addColumn("Valor del Proyecto");
			modelo.addColumn("Total de Costos");
			modelo.addColumn("Porcentaje de Ganacia");

			// TAMANO DE CADA COLUMNA
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.getColumnModel().getColumn(3).setPreferredWidth(100);
			table.getColumnModel().getColumn(4).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(234);
			table.getColumnModel().getColumn(6).setPreferredWidth(234);
			table.getColumnModel().getColumn(7).setPreferredWidth(234);
			table.getColumnModel().getColumn(8).setPreferredWidth(150);
			table.getColumnModel().getColumn(9).setPreferredWidth(150);
			table.getColumnModel().getColumn(10).setPreferredWidth(150);

			// FORMATEARCOLUMNAS DATE
			table.getColumnModel().getColumn(3).setCellRenderer(FormatearJtable.getDateRenderer());
			table.getColumnModel().getColumn(4).setCellRenderer(FormatearJtable.getDateRenderer());

			ResultSetMetaData metaDatos = rs.getMetaData();
			// Se obtiene el número de columnas.
			int numeroColumnas = metaDatos.getColumnCount();
			// Bucle para cada resultado en la consulta
			while (rs.next()) {
				// Se crea un array que será una de las filas de la tabla. 
				Object[] fila = new Object[numeroColumnas]; // se pone el numero de columnas en la tabla
				// Se rellena cada posición del array con una de las columnas de la en base de datos.
				for (int i = 0; i < numeroColumnas; i++)
					fila[i] = rs.getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.
				// Se añade al modelo la fila completa.
				modelo.addRow(fila);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void MostrarEnTextFieldProyectoSeleccionado() {

		// Cargar los textfields  seleccionado en el jtable
		int row = table.getSelectedRow();
		int codigoseleccionado = (int) table.getValueAt(row, 0);
		try {
			ProyectoSeleccionado = AdministrarProyectosSession.ObtenerRegistro(codigoseleccionado);
			ProyectoSeleccionado.next();
			tNroProyecto.setText(ProyectoSeleccionado.getString("pro_codigo"));
			tDenominacion.setText(ProyectoSeleccionado.getString("pro_denominacion"));
			cbTipoEmprendimiento.setSelectedIndex(ProyectoSeleccionado.getInt("pro_tipoempre") - 1);
			dcFechaContrato.setDate(ProyectoSeleccionado.getDate("pro_fechacontrato"));
			dcFechaEntrega.setDate(ProyectoSeleccionado.getDate("pro_fechaentrega"));
			tCodigoCliente.setText(ProyectoSeleccionado.getString("pro_codcliente"));
			ResultSet cliente = AdministrarClientesSession.obtenerregistro(Integer.parseInt(tCodigoCliente.getText()));
			cliente.next();
			tNomApeCliente.setText(cliente.getString("cli_nombre") + ' ' + cliente.getString("cli_apelli"));
			tCodigoConsultor.setText(ProyectoSeleccionado.getString("pro_codconsultor"));
			ResultSet consultor = AdministrarFuncionariosSession.obtenerregistro(Integer.parseInt(tCodigoConsultor.getText()));
			consultor.next();
			tNomApeConsultor.setText(consultor.getString("fun_nombre") + ' ' + consultor.getString("fun_apellido"));
			tCodigoUsuario.setText(ProyectoSeleccionado.getString("pro_codusuario"));
			ResultSet usuario = AdministrarUsuariosSession.obtenerregistro(Integer.parseInt(tCodigoUsuario.getText()));
			usuario.next();
			tNomApeUsuario.setText(usuario.getString("usu_nombre") + ' ' + usuario.getString("usu_apellido"));

			tCodigoInmueble.setText(ProyectoSeleccionado.getString("pro_codinmueble"));
			inmueble = AdministrarProyectosSession.ObtenerInmueble(Integer.parseInt(tCodigoInmueble.getText()));
			inmueble.next();
			tCantFinca.setText(inmueble.getString("in_cantfinca"));
			tCantBloque.setText(inmueble.getString("in_cantbloque"));
			tPropietario.setText(inmueble.getString("in_propietario"));
			tNroPadron.setText(inmueble.getString("in_numpadron"));
			cbDepartamento.setSelectedIndex(inmueble.getInt("in_departamento") - 1);
			cbDistrito.setSelectedIndex(inmueble.getInt("in_distrito") - 1);
			tLocalidad.setText(inmueble.getString("in_localidad"));
			tX.setText(inmueble.getString("in_x"));
			tY.setText(inmueble.getString("in_y"));
			cbTipoCoordenada.setSelectedIndex(inmueble.getInt("in_tipocoordenada"));
			chbTituloPropiedad.setSelected(inmueble.getBoolean("in_titulopropiedad"));
			chbImpuestoIn.setSelected(inmueble.getBoolean("in_impuesto"));
			chbPlanoFincas.setSelected(inmueble.getBoolean("in_planofinca"));

			ImageIcon icon = new ImageIcon("src/imagencroquis/" + "imagen" + tCodigoInmueble.getText() + ".jpg");
			lblImagen.setIcon(icon);
			lblImagen.setText("");

			DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .

			tEntregar.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_entregarcarpeta")));
			chbObs.setSelected(ProyectoSeleccionado.getBoolean("pro_observacion"));
			tObs.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_observacionnum")));
			tOrdenPu.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_ordenpublicacion")));
			tRetiLicencia.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_retirolicencia")));
			tTotalTasaSeam.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_totaltasaseam")));
			tDistancia.setText(ProyectoSeleccionado.getString("pro_distancia"));
			tPrecioCombustible.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_preciocombustible")));
			cbTipoVehiculo.setSelectedIndex(ProyectoSeleccionado.getInt("pro_tipovehiculo") - 1);
			tGastoLitroCombustible.setText(ProyectoSeleccionado.getString("pro_gastolitrocombustible"));
			tTotalGastoCombustible.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_totalgastocombustible")));
			tCantPersonas.setText(ProyectoSeleccionado.getString("pro_cantpersonas"));
			cbTipoAlojamiento.setSelectedIndex(ProyectoSeleccionado.getInt("pro_codtipoaloja"));
			tCostoPorPersona.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_costoporpersona")));
			tCostoTotalViatico.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_costototalviatico")));
			chbSubContrato.setSelected(ProyectoSeleccionado.getBoolean("pro_subcontrato"));
			cbProfesional.setSelectedIndex(ProyectoSeleccionado.getInt("pro_codprofesional") - 1);
			tSubNomApe.setText(ProyectoSeleccionado.getString("pro_nomapeprofesional"));
			tRuc.setText(ProyectoSeleccionado.getString("pro_rucprofesional"));
			tHonorarios.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_honorarios")));
			chbRegistraInmueble.setSelected(ProyectoSeleccionado.getBoolean("pro_registrarinmueble"));
			chbRealizarPagoTasa.setSelected(ProyectoSeleccionado.getBoolean("pro_realizarpagotasa"));
			chbCostoCuentaCatastral.setSelected(ProyectoSeleccionado.getBoolean("pro_costocuentacatastral"));
			chbPrepararCarpetaSeam.setSelected(ProyectoSeleccionado.getBoolean("pro_prepararcarpetaseam"));
			chbPermisoAmbiental.setSelected(ProyectoSeleccionado.getBoolean("pro_permisoambiental"));
			chbObsProcesos.setSelected(ProyectoSeleccionado.getBoolean("pro_adicionalobs"));
			tRegistrarInmueble.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_registrarinmueblenum")));
			tRealizarPagoTasa.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_realizarpagotasanum")));
			tCostoCuentaCatastral.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_costocuentacatastralnum")));
			tPrepararCarpetaSeam.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_prepararcarpetaseamnum")));
			tPermisoAmbiental.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_permisoambientalnum")));
			tAdicionalObs.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_adicionalobsnum")));
			tValorTotalProyecto.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_valorproyecto")));
			tTotalCostos.setText(decimalmiles.format(ProyectoSeleccionado.getInt("pro_totalcostos")));
			tPorcentaje.setText(ProyectoSeleccionado.getString("pro_porcentajeganancia"));

			ProyectoSeleccionado.close();
			cliente.close();
			consultor.close();
			usuario.close();
			inmueble.close();
		} catch (Exception e1) {
			System.out.println(e1);
		}
	}

	private void CargarImagen() {
		int resultado;

		CargarImagen cargarimagen = new CargarImagen();

		FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");

		cargarimagen.fcCargarImagen.setFileFilter(filtro);

		resultado = cargarimagen.fcCargarImagen.showOpenDialog(null);

		if (JFileChooser.APPROVE_OPTION == resultado) {
			try {
				imagenfis = new FileInputStream(cargarimagen.fcCargarImagen.getSelectedFile());
				//necesitamos saber la cantidad de bytes
				longimagen = (int) cargarimagen.fcCargarImagen.getSelectedFile().length();

			} catch (Exception e) {
				System.out.println(e);
			}
			fichero = cargarimagen.fcCargarImagen.getSelectedFile();

			try {

				ImageIcon icon = new ImageIcon(fichero.toString());

				Icon icono = new ImageIcon(icon.getImage().getScaledInstance(lblImagen.getWidth(), -1, Image.SCALE_DEFAULT));
				lblImagen.setText(null);

				lblImagen.setIcon((Icon) icono);

			} catch (Exception ex) {

				JOptionPane.showMessageDialog(null, "Error abriendo la imagen " + ex);

			}

		}

	}

	private void GuardarImagen(JLabel label, String direcciondondeguardar) {
		try {
			ImageIcon icon = (ImageIcon) label.getIcon();
			BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = image.createGraphics();
			g2.drawImage(icon.getImage(), 0, 0, icon.getImageObserver());
			g2.dispose();
			ImageIO.write(image, "jpg", new File(direcciondondeguardar));
		} catch (Exception e) {
			System.out.println("Error al guardar imagen :" + e);
		}
	}

	private void ActualizarRegistro() {
		try {
			int row = table.getSelectedRow();
			int opt = JOptionPane.showConfirmDialog(null, "Desea Actualizar el Proyecto " + table.getValueAt(row, 1).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
			if (opt == 0) {
				GuardarImagen(lblImagen, "src/imagencroquis/" + "imagen" + tCodigoInmueble.getText() + ".jpg");

				AdministrarProyectosSession.ActualizarInmueble(Integer.parseInt(tCodigoInmueble.getText()), Integer.parseInt(tCantFinca.getText()), Integer.parseInt(tCantBloque.getText()), tPropietario.getText(),
						Integer.parseInt(tNroPadron.getText()), cbDepartamento.getSelectedIndex() + 1, cbDistrito.getSelectedIndex() + 1, tLocalidad.getText(), Double.parseDouble(tX.getText()), Double.parseDouble(tY.getText()),
						cbTipoCoordenada.getSelectedIndex(), chbTituloPropiedad.isSelected(), chbImpuestoIn.isSelected(), chbPlanoFincas.isSelected());

				AdministrarProyectosSession.ActualizarProyecto(Integer.parseInt(gettNroProyecto().getText()), tDenominacion.getText(), (cbTipoEmprendimiento.getSelectedIndex() + 1), dcFechaContrato.getDate(), dcFechaEntrega.getDate(),
						Integer.parseInt(tCodigoCliente.getText()), Integer.parseInt(tCodigoConsultor.getText()), Integer.parseInt(tCodigoUsuario.getText()), Integer.parseInt(tCodigoInmueble.getText()),
						Double.parseDouble(tEntregar.getText().replace(".", "")), chbObs.isSelected(), Double.parseDouble(tObs.getText().replace(".", "")), Double.parseDouble(tOrdenPu.getText().replace(".", "")),
						Double.parseDouble(tRetiLicencia.getText().replace(".", "")), Double.parseDouble(tTotalTasaSeam.getText().replace(".", "")), Integer.parseInt(tDistancia.getText().replace(".", "")),
						Double.parseDouble(tPrecioCombustible.getText().replace(".", "")), cbTipoVehiculo.getSelectedIndex() + 1, Double.parseDouble(tGastoLitroCombustible.getText().replace(",", ".")),
						Double.parseDouble(tTotalGastoCombustible.getText().replace(".", "")), Integer.parseInt(tCantPersonas.getText()), cbTipoAlojamiento.getSelectedIndex(), Double.parseDouble(tCostoPorPersona.getText().replace(".", "")),
						Double.parseDouble(tCostoTotalViatico.getText().replace(".", "")), chbSubContrato.isSelected(), cbProfesional.getSelectedIndex() + 1, tSubNomApe.getText(), tRuc.getText(),
						Double.parseDouble(tHonorarios.getText().replace(".", "")), chbRegistraInmueble.isSelected(), chbRealizarPagoTasa.isSelected(), chbCostoCuentaCatastral.isSelected(), chbPrepararCarpetaSeam.isSelected(),
						chbPermisoAmbiental.isSelected(), chbObsProcesos.isSelected(), Double.parseDouble(tRegistrarInmueble.getText().replace(".", "")), Double.parseDouble(tRealizarPagoTasa.getText().replace(".", "")),
						Double.parseDouble(tCostoCuentaCatastral.getText().replace(".", "")), Double.parseDouble(tPrepararCarpetaSeam.getText().replace(".", "")), Double.parseDouble(tPermisoAmbiental.getText().replace(".", "")),
						Double.parseDouble(tAdicionalObs.getText().replace(".", "")), Double.parseDouble(tValorTotalProyecto.getText().replace(".", "")), Double.parseDouble(tTotalCostos.getText().replace(".", "")),
						Integer.parseInt(tPorcentaje.getText()));

				JOptionPane.showMessageDialog(null, "Proyecto actualizado con éxito");
			}
		} catch (Exception e3) {
			System.out.println("Error al actualizar registro : " + e3);
		}

	}

	private void InsertarRegistro() {
		try {
			GuardarImagen(lblImagen, "src/imagencroquis/" + "imagen" + tCodigoInmueble.getText() + ".jpg");

			AdministrarProyectosSession.InsertarInmueble(Integer.parseInt(tCodigoInmueble.getText()), Integer.parseInt(tCantFinca.getText()), Integer.parseInt(tCantBloque.getText()), tPropietario.getText(), Integer.parseInt(tNroPadron.getText()),
					cbDepartamento.getSelectedIndex() + 1, cbDistrito.getSelectedIndex() + 1, tLocalidad.getText(), Double.parseDouble(tX.getText()), Double.parseDouble(tY.getText()), cbTipoCoordenada.getSelectedIndex(),
					chbTituloPropiedad.isSelected(), chbImpuestoIn.isSelected(), chbPlanoFincas.isSelected(), (tCodigoInmueble.getText() + "Imagen"), imagenfis, longimagen);

			AdministrarProyectosSession.InsertarProyecto(Integer.parseInt(gettNroProyecto().getText()), tDenominacion.getText(), cbTipoEmprendimiento.getSelectedIndex() + 1, dcFechaContrato.getDate(), dcFechaEntrega.getDate(),
					Integer.parseInt(tCodigoCliente.getText()), Integer.parseInt(tCodigoConsultor.getText()), Integer.parseInt(tCodigoUsuario.getText()), Integer.parseInt(tCodigoInmueble.getText()),
					Double.parseDouble(tEntregar.getText().replace(".", "")), chbObs.isSelected(), Double.parseDouble(tObs.getText().replace(".", "")), Double.parseDouble(tOrdenPu.getText().replace(".", "")),
					Double.parseDouble(tRetiLicencia.getText().replace(".", "")), Double.parseDouble(tTotalTasaSeam.getText().replace(".", "")), Integer.parseInt(tDistancia.getText().replace(".", "")),
					Double.parseDouble(tPrecioCombustible.getText().replace(".", "")), cbTipoVehiculo.getSelectedIndex() + 1, Double.parseDouble(tGastoLitroCombustible.getText().replace(",", ".")),
					Double.parseDouble(tTotalGastoCombustible.getText().replace(".", "")), Integer.parseInt(tCantPersonas.getText()), cbTipoAlojamiento.getSelectedIndex(), Double.parseDouble(tCostoPorPersona.getText().replace(".", "")),
					Double.parseDouble(tCostoTotalViatico.getText().replace(".", "")), chbSubContrato.isSelected(), cbProfesional.getSelectedIndex() + 1, tSubNomApe.getText(), tRuc.getText(),
					Double.parseDouble(tHonorarios.getText().replace(".", "")), chbRegistraInmueble.isSelected(), chbRealizarPagoTasa.isSelected(), chbCostoCuentaCatastral.isSelected(), chbPrepararCarpetaSeam.isSelected(),
					chbPermisoAmbiental.isSelected(), chbObsProcesos.isSelected(), Double.parseDouble(tRegistrarInmueble.getText().replace(".", "")), Double.parseDouble(tRealizarPagoTasa.getText().replace(".", "")),
					Double.parseDouble(tCostoCuentaCatastral.getText().replace(".", "")), Double.parseDouble(tPrepararCarpetaSeam.getText().replace(".", "")), Double.parseDouble(tPermisoAmbiental.getText().replace(".", "")),
					Double.parseDouble(tAdicionalObs.getText().replace(".", "")), Double.parseDouble(tValorTotalProyecto.getText().replace(".", "")), Double.parseDouble(tTotalCostos.getText().replace(".", "")),
					Integer.parseInt(tPorcentaje.getText()));

			JOptionPane.showMessageDialog(null, "Proyecto insertado con éxito");
		} catch (Exception e2) {
			System.out.println("Error Insertar registro : " + e2);

		}
	}

	@SuppressWarnings("unchecked")
	private static Object GenerarReporte() {

		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(table.getModel());
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = AdministrarProyectos.class.getResourceAsStream("/reportes/reportProyecto.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			Principal principal = new Principal();
			parametros.put("NombreOrganizacion", "Karanday");

			parametros.put("INICIO_CODIGO", tCodigoInicialProyecto.getText());
			parametros.put("FINAL_CODIGO", tCodigoFinalProyecto.getText());
			parametros.put("INICIAL_CLIENTE", tInicialCliente.getText());
			parametros.put("FINAL_CLIENTE", tFinalCliente.getText());
			parametros.put("INICIAL_FECHACONTRATO", dcFechaInicialContrato.getDate());
			parametros.put("FINAL_FECHACONTRATO", dcFechaFinalContrato.getDate());
			parametros.put("INICIAL_FECHAENTREGA", dcFechaInicialEntrega.getDate());
			parametros.put("FINAL_FECHAENTREGA", dcFechaFinalEntrega.getDate());

			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, tablemodel);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setTitle("Informe de Proyectos");
			jasperViewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
			jasperViewer.setZoomRatio((float) 1);
			jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
			jasperViewer.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			jasperViewer.requestFocus();
			jasperViewer.setVisible(true);
			//Para guardar directamente a pdf JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Eclipse/workspace/BIBLIOTECA/Reportpdf.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static Object GenerarReporteProyectoIndividual() {

		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(table.getModel());
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = AdministrarProyectos.class.getResourceAsStream("/reportes/reportProyectoIndividual.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			Principal principal = new Principal();

			Utilidades utilidades = new Utilidades();
			int row = table.getSelectedRow();
			ResultSet ProyectoSeleccionado = utilidades.ejecutarSQLSelect(
					"SELECT pro_denominacion, em_descri, pro_fechacontrato, pro_fechaentrega, CONCAT (CONCAT(cli_nombre,' '), cli_apelli) AS nomapecliente, CONCAT (CONCAT(fun_nombre,' '), fun_apellido) AS nomapeconsultor, CONCAT (CONCAT(usu_nombre,' '), usu_apellido) AS nomapeusuario, in_cantfinca, in_cantbloque, in_propietario, in_numpadron, de_descri, di_descri, in_localidad, in_x, in_y, pro_ordenpublicacion, pro_retirolicencia, pro_totaltasaseam, pro_distancia, pro_preciocombustible, ve_descri, pro_gastolitrocombustible, pro_totalgastocombustible, pro_cantpersonas, alo_descri, pro_costoporpersona, pro_costototalviatico, pro_codprofesional, pro_nomapeprofesional, pro_rucprofesional, pro_honorarios, pro_registrarinmueblenum, pro_realizarpagotasanum, pro_costocuentacatastralnum, pro_prepararcarpetaseamnum, pro_permisoambientalnum, pro_adicionalobsnum, pro_valorproyecto, pro_totalcostos, pro_porcentajeganancia, pro_entregarcarpeta, pro_observacionnum FROM proyecto, emprendimiento, cliente, funcionario, usuario, inmueble, departamento, distrito, vehiculo, alojamiento WHERE pro_tipoempre = em_codigo AND pro_codcliente = cli_codigo AND pro_codconsultor = fun_codigo AND pro_codusuario = usu_codigo AND pro_codinmueble = in_codigo AND in_departamento = de_codigo AND in_distrito = di_codigo AND pro_tipovehiculo = ve_codigo AND pro_codtipoaloja = alo_codigo AND pro_codigo = "
							+ table.getValueAt(row, 0));
			ProyectoSeleccionado.next();
			DecimalFormat decimalmiles = new DecimalFormat("###,###"); //la , se imprime como .
			parametros.put("NombreOrganizacion", "Karanday");
			parametros.put("DENOMINACIONPROYECTO", ProyectoSeleccionado.getString(1));
			parametros.put("TIPOEMPRENDIMIENTO", ProyectoSeleccionado.getString(2));
			parametros.put("FECHACONTRATO", ProyectoSeleccionado.getDate(3));
			parametros.put("FECHAENTREGADOCUMENTO", ProyectoSeleccionado.getDate(4));
			parametros.put("CLIENTE", ProyectoSeleccionado.getString(5));
			parametros.put("CONSULTORENCARGADO", ProyectoSeleccionado.getString(6));
			parametros.put("USUARIO", ProyectoSeleccionado.getString(7));
			parametros.put("CANTIDADFINCAS", ProyectoSeleccionado.getString(8));
			parametros.put("CANTIDADBLOQUES", ProyectoSeleccionado.getString(9));
			parametros.put("PROPIETARIO", ProyectoSeleccionado.getString(10));
			parametros.put("NUMEROPADRON", ProyectoSeleccionado.getString(11));
			parametros.put("DEPARTAMENTO", ProyectoSeleccionado.getString(12));
			parametros.put("DISTRITO", cbDistrito.getSelectedItem());
			parametros.put("LOCALIDAD", ProyectoSeleccionado.getString(14));
			parametros.put("X", ProyectoSeleccionado.getString(15));
			parametros.put("Y", ProyectoSeleccionado.getString("in_y"));
			parametros.put("ENTREGARCARPETA", decimalmiles.format(ProyectoSeleccionado.getDouble("pro_entregarcarpeta")));
			parametros.put("OBSERVACION", decimalmiles.format(ProyectoSeleccionado.getDouble("pro_observacionnum")));
			parametros.put("ORDENPUBLICACION", decimalmiles.format(ProyectoSeleccionado.getDouble(17)));
			parametros.put("RETIROLICENCIA", decimalmiles.format(ProyectoSeleccionado.getDouble(18)));
			parametros.put("TOTALTASASEAM", decimalmiles.format(ProyectoSeleccionado.getDouble(19)));
			parametros.put("DISTANCIA", ProyectoSeleccionado.getString(20));
			parametros.put("PRECIOLITROCOMBUSTIBLE", decimalmiles.format(ProyectoSeleccionado.getDouble(21)));
			parametros.put("TIPOVEHICULO", ProyectoSeleccionado.getString(22));
			parametros.put("GASTOESTIMADOCOMBUSTIBLE", ProyectoSeleccionado.getString(23));
			parametros.put("TOTALGASTOESTIMADO", decimalmiles.format(ProyectoSeleccionado.getDouble(24)));
			parametros.put("CANTIDADPERSONAS", ProyectoSeleccionado.getString(25));
			parametros.put("TIPOALOJAMIENTO", ProyectoSeleccionado.getString(26));
			parametros.put("COSTOPORPERSONA", decimalmiles.format(ProyectoSeleccionado.getDouble(27)));
			parametros.put("COSTOTOTALVIATICO", decimalmiles.format(ProyectoSeleccionado.getDouble(28)));
			parametros.put("NOMBREAPELLIDO", ProyectoSeleccionado.getString("pro_nomapeprofesional"));
			parametros.put("PROFESIONAL", cbProfesional.getSelectedItem());
			parametros.put("RUC", ProyectoSeleccionado.getString("pro_rucprofesional"));
			parametros.put("HONORARIOS", decimalmiles.format(ProyectoSeleccionado.getDouble("pro_honorarios")));
			parametros.put("REGISTRARINMUEBLE", decimalmiles.format(ProyectoSeleccionado.getDouble(32)));
			parametros.put("REALIZARPAGOTASAS", decimalmiles.format(ProyectoSeleccionado.getDouble(33)));
			parametros.put("COSTOCUENTACATASTRAL", decimalmiles.format(ProyectoSeleccionado.getDouble(34)));
			parametros.put("PREPARARCARPETASEAM", decimalmiles.format(ProyectoSeleccionado.getDouble(35)));
			parametros.put("PERMISOAMBIENTAL", decimalmiles.format(ProyectoSeleccionado.getDouble(36)));
			parametros.put("OBSERVACIONPROCESO", decimalmiles.format(ProyectoSeleccionado.getDouble("pro_adicionalobsnum")));
			parametros.put("VALORPROYECTO", decimalmiles.format(ProyectoSeleccionado.getDouble("pro_valorproyecto")));
			parametros.put("TOTALCOSTOS", decimalmiles.format(ProyectoSeleccionado.getDouble("pro_totalcostos")));
			parametros.put("MARGENGANANCIA", ProyectoSeleccionado.getString("pro_porcentajeganancia") + " %");
			

			ProyectoSeleccionado.close();
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, tablemodel);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setTitle("Informe de Proyectos");
			jasperViewer.setDefaultCloseOperation(JasperViewer.DISPOSE_ON_CLOSE);
			jasperViewer.setZoomRatio((float) 1);
			jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
			jasperViewer.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			jasperViewer.requestFocus();
			jasperViewer.setVisible(true);
			//Para guardar directamente a pdf JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Eclipse/workspace/BIBLIOTECA/Reportpdf.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
