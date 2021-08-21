package visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import utilidades.Utilidades;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	private JPanel contentPane;
	public Principal principal;
	private final JPanel pnMenu = new JPanel();
	public static JTextField tLogin;
	private JMenuItem mntmActualizarUsuariosDel;
	private JMenuItem mntmControlarAsistencia;
	private JMenuItem mntmInicializarSistema;
	private JLabel lblKaranday;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					boolean opc = true;
					if (opc == true) {
						Principal frame = new Principal();
						frame.setVisible(true);
						opc = false;
					} else {
						System.out.println("JFrame Principal ya abierta");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setTitle("Sistema de Gestion para Consultorial Ambiental");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				int opt = JOptionPane.showConfirmDialog(null, "Desea salir del sistema?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						System.exit(0);
					} catch (Exception e2) {
						return;
					}
				}
			}
		});

		//Aplicar tema
		try {

			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 100, 100);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// Hacer pantalla completa y deshabiltiar maximizar
		setVisible(true);
		setResizable(false);
		Dimension d = getSize();
		setMinimumSize(d);

		Insets in = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		Dimension d1 = Toolkit.getDefaultToolkit().getScreenSize();
		int width = d1.width - (in.left + in.top);
		int height = d1.height - (in.top + in.bottom);
		this.setLocation(0, 0);// Localizacion de pantalla x,y
		this.setSize(width, height); // Tamano de ventana
		setResizable(false);

		this.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				setEnabled(false);
				setEnabled(true);
			}
		});
		contentPane.setLayout(null);
		ImageIcon imagen = new ImageIcon(Principal.class.getResource("/icono/Logo Skarlata.jpg"));
		this.repaint();
		//Para resimensionar imagen en jlabel
		ImageIcon imagen1 = new ImageIcon(Principal.class.getResource("/icono/Logo Karanday.jpg"));
		this.repaint();
		ImageIcon imagen1111 = new ImageIcon(Principal.class.getResource("/icono/Sin t\u00EDtulo-1.jpg"));
		ImageIcon imagen11 = new ImageIcon(Principal.class.getResource("/icono/fondo2.jpg"));

		JPanel pnEncabezado = new JPanel();
		pnEncabezado.setBounds(0, 0, 1924, 122);
		contentPane.add(pnEncabezado);
		pnEncabezado.setLayout(null);

		JLabel lblNewLabel = new JLabel("SKARLATA");
		lblNewLabel.setBounds(6, 5, 134, 33);
		pnEncabezado.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Minion Pro SmBd", Font.BOLD, 24));

		JLabel lblSistemaDeGestin = new JLabel("Sistema de gestion para Consultoria Ambiental");
		lblSistemaDeGestin.setBounds(6, 43, 389, 33);
		pnEncabezado.add(lblSistemaDeGestin);
		lblSistemaDeGestin.setForeground(new Color(255, 255, 255));
		lblSistemaDeGestin.setFont(new Font("Tahoma", Font.PLAIN, 19));

		JLabel lblTelef = new JLabel("Telef. 0984673075");
		lblTelef.setBounds(6, 81, 161, 33);
		pnEncabezado.add(lblTelef);
		lblTelef.setForeground(Color.WHITE);
		lblTelef.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel label_1 = new JLabel("");
		label_1.setBounds(416, 3, 132, 116);
		pnEncabezado.add(label_1);

		label_1.setIcon(new ImageIcon(Principal.class.getResource("/icono/Logo Skarlata.jpg")));

		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), Image.SCALE_DEFAULT));
		label_1.setIcon(icono);

		lblKaranday = new JLabel("KARANDAY");
		lblKaranday.setBounds(670, 5, 155, 33);
		pnEncabezado.add(lblKaranday);
		lblKaranday.setForeground(new Color(255, 255, 255));
		lblKaranday.setBackground(new Color(255, 255, 255));
		lblKaranday.setFont(new Font("Palatino Linotype", Font.BOLD, 24));

		JLabel lblConsultoriaAmbiental = new JLabel("Consultoria Ambiental");
		lblConsultoriaAmbiental.setBounds(670, 43, 191, 33);
		pnEncabezado.add(lblConsultoriaAmbiental);
		lblConsultoriaAmbiental.setForeground(new Color(255, 255, 255));
		lblConsultoriaAmbiental.setFont(new Font("Tahoma", Font.PLAIN, 19));

		JLabel label = new JLabel("");
		label.setBounds(869, 3, 389, 116);
		pnEncabezado.add(label);
		label.setIcon(new ImageIcon(Principal.class.getResource("/icono/Logo Karanday.jpg")));
		Icon icono1 = new ImageIcon(imagen1.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icono1);

		JLabel label_3 = new JLabel("Telef. 0984673075");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_3.setBounds(670, 81, 161, 33);
		pnEncabezado.add(label_3);

		JLabel label_2 = new JLabel("");
		label_2.setBounds(0, 0, 1924, 125);
		pnEncabezado.add(label_2);
		label_2.setIcon(new ImageIcon(Principal.class.getResource("/icono/Sin t\u00EDtulo-1.jpg")));
		Icon icono1111 = new ImageIcon(imagen1111.getImage().getScaledInstance(label_2.getWidth(), label_2.getHeight(), Image.SCALE_DEFAULT));
		label_2.setIcon(icono1111);
		pnMenu.setBounds(0, 122, 1924, 60);
		contentPane.add(pnMenu);
		pnMenu.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 2000, 60);
		pnMenu.add(menuBar);
		menuBar.setBackground(SystemColor.inactiveCaption);
		JMenu mnGestionarLicenciaAmbiental = new JMenu("Gestionar Licencia Ambiental");
		mnGestionarLicenciaAmbiental.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuBar.add(mnGestionarLicenciaAmbiental);
		mnGestionarLicenciaAmbiental.setBackground(new Color(255, 255, 255));
		mnGestionarLicenciaAmbiental.setForeground(new Color(0, 128, 128));
		mnGestionarLicenciaAmbiental.setIcon(new ImageIcon(Principal.class.getResource("/icono/Documento.png")));
		mnGestionarLicenciaAmbiental.setFont(new Font("Arial", Font.BOLD, 17));

		JMenuItem mntmNuevoProyecto = new JMenuItem("Gestionar proyectos");
		mntmNuevoProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarProyectos proyecto = new AdministrarProyectos();
				proyecto.setVisible(true);
			}
		});
		mntmNuevoProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnGestionarLicenciaAmbiental.add(mntmNuevoProyecto);

		JMenuItem mntmRegistrarCliente = new JMenuItem("Registrar Cliente");
		mntmRegistrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarClientes registrardatoscliente = new AdministrarClientes();
				registrardatoscliente.setLocationRelativeTo(principal);
				registrardatoscliente.setVisible(true);
			}
		});
		mntmRegistrarCliente.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mntmRegistrarCliente.setIcon(null);
		mnGestionarLicenciaAmbiental.add(mntmRegistrarCliente);

		JMenu mnGestionarUsuarios = new JMenu("Gestionar Usuario");
		menuBar.add(mnGestionarUsuarios);
		mnGestionarUsuarios.setBackground(new Color(248, 248, 255));
		mnGestionarUsuarios.setForeground(new Color(0, 128, 128));
		mnGestionarUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/icono/Usuario.png")));
		mnGestionarUsuarios.setFont(new Font("Arial", Font.BOLD, 17));

		mntmActualizarUsuariosDel = new JMenuItem("Administrar usuario");
		mntmActualizarUsuariosDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarUsuarios administrarusuarios = new AdministrarUsuarios();
				administrarusuarios.setVisible(true);
			}
		});
		mntmActualizarUsuariosDel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnGestionarUsuarios.add(mntmActualizarUsuariosDel);

		JMenuItem mntmActualizarPrivilegiosDe = new JMenuItem("Actualizar Privilegios de Usuario");
		mntmActualizarPrivilegiosDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActualizarPrivilegios privilegios = new ActualizarPrivilegios();
				privilegios.setVisible(true);
			}
		});
		mnGestionarUsuarios.add(mntmActualizarPrivilegiosDe);
		mntmActualizarPrivilegiosDe.setFont(new Font("Segoe UI", Font.PLAIN, 18));

		JMenu mnGestionarRecursosHumanos = new JMenu("Gestionar Recursos Humanos ");
		menuBar.add(mnGestionarRecursosHumanos);
		mnGestionarRecursosHumanos.setBackground(new Color(248, 248, 255));
		mnGestionarRecursosHumanos.setForeground(new Color(0, 128, 128));
		mnGestionarRecursosHumanos.setIcon(new ImageIcon(Principal.class.getResource("/icono/RRHH.png")));
		mnGestionarRecursosHumanos.setFont(new Font("Arial", Font.BOLD, 17));

		mntmControlarAsistencia = new JMenuItem("Controlar Asistencia");
		mntmControlarAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControlarAsistencia controlarasistencia = new ControlarAsistencia();
				controlarasistencia.setVisible(true);
			}
		});
		mntmControlarAsistencia.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnGestionarRecursosHumanos.add(mntmControlarAsistencia);

		JMenuItem mntmActualizarHorarios = new JMenuItem("Actualizar Horarios");
		mntmActualizarHorarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActualizarHorarios actualizarhorarios = new ActualizarHorarios();
				actualizarhorarios.setVisible(true);
			}
		});
		mntmActualizarHorarios.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnGestionarRecursosHumanos.add(mntmActualizarHorarios);

		JMenu mnConfiguracion = new JMenu("Configuracion");
		mnConfiguracion.setIcon(new ImageIcon(Principal.class.getResource("/icono/Config.png")));
		mnConfiguracion.setForeground(new Color(0, 128, 128));
		mnConfiguracion.setFont(new Font("Arial", Font.BOLD, 17));
		mnConfiguracion.setBackground(new Color(248, 248, 255));
		menuBar.add(mnConfiguracion);

		mntmInicializarSistema = new JMenuItem("Inicializar sistema");
		mntmInicializarSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InicializarSistema inicializar = new InicializarSistema();
				inicializar.setVisible(true);
			}
		});
		mntmInicializarSistema.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnConfiguracion.add(mntmInicializarSistema);

		JMenuItem mntmAcercaDeSkarlata = new JMenuItem("Acerca de Skarlata");
		mntmAcercaDeSkarlata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AcercaDe acercade = new AcercaDe();
				acercade.setVisible(true);
			}
		});
		mntmAcercaDeSkarlata.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		mnConfiguracion.add(mntmAcercaDeSkarlata);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 189, 1708, 686);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnGestionarProyectos = new JButton("Proyectos");
		btnGestionarProyectos.setHorizontalAlignment(SwingConstants.LEFT);
		btnGestionarProyectos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdministrarProyectos proyecto = new AdministrarProyectos();
				proyecto.setVisible(true);
			}
		});
		btnGestionarProyectos.setIcon(new ImageIcon(Principal.class.getResource("/icono/Proyecto.png")));
		btnGestionarProyectos.setFont(new Font("Rockwell", Font.BOLD, 15));
		btnGestionarProyectos.setBounds(27, 36, 189, 68);
		panel.add(btnGestionarProyectos);

		JButton btnGestionarClientes = new JButton("Clientes");
		btnGestionarClientes.setHorizontalAlignment(SwingConstants.LEFT);
		btnGestionarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarClientes administrarclientes = new AdministrarClientes();
				administrarclientes.setVisible(true);
			}
		});
		btnGestionarClientes.setIcon(new ImageIcon(Principal.class.getResource("/icono/Cliente.png")));
		btnGestionarClientes.setFont(new Font("Rockwell", Font.BOLD, 15));
		btnGestionarClientes.setBounds(27, 140, 189, 68);
		panel.add(btnGestionarClientes);

		JButton btnGestionarFuncionarios = new JButton("Funcionarios");
		btnGestionarFuncionarios.setHorizontalAlignment(SwingConstants.LEFT);
		btnGestionarFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarFuncionarios administrarfuncionarios = new AdministrarFuncionarios();
				administrarfuncionarios.setVisible(true);
			}
		});
		btnGestionarFuncionarios.setIcon(new ImageIcon(Principal.class.getResource("/icono/Funcionarios.png")));
		btnGestionarFuncionarios.setFont(new Font("Rockwell", Font.BOLD, 15));
		btnGestionarFuncionarios.setBounds(27, 244, 189, 68);
		panel.add(btnGestionarFuncionarios);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				int opt = JOptionPane.showConfirmDialog(null, "Desea salir del sistema?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						System.exit(0);
					} catch (Exception e2) {
						return;
					}
				}
			}
		});
		btnSalir.setIcon(new ImageIcon(Principal.class.getResource("/icono/Salir copia.png")));
		btnSalir.setFont(new Font("Rockwell", Font.BOLD, 15));
		btnSalir.setBounds(27, 348, 189, 68);
		panel.add(btnSalir);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(27, 644, 65, 35);
		panel.add(lblUsuario);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setFont(new Font("Segoe UI Emoji", Font.BOLD, 17));

		JLabel lblNewLabel_1 = new JLabel("Copyright \u00A9 2016. Todos los derechos reservados. SKARLATA");
		lblNewLabel_1.setBounds(1285, 644, 402, 35);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1.setForeground(Color.WHITE);

		tLogin = new JTextField();
		tLogin.setBorder(null);
		tLogin.setBackground(SystemColor.activeCaption);
		tLogin.setText((String) null);
		tLogin.setFont(new Font("Segoe UI Emoji", Font.BOLD, 17));
		tLogin.setEditable(false);

		tLogin.setColumns(10);
		tLogin.setBounds(101, 644, 152, 35);
		panel.add(tLogin);

		//Que aparezca el login del usuario
		tLogin.setText(Login.tUsuario.getText());

		//Privilegios
		try {
			Utilidades utilidades = new Utilidades();
			ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
			UsuarioPrivilegios.next();
			mntmNuevoProyecto.setEnabled(UsuarioPrivilegios.getBoolean("pri_proyecto"));
			mntmRegistrarCliente.setEnabled(UsuarioPrivilegios.getBoolean("pri_cliente"));

			JMenuItem mntmConstanciaDeEntrega = new JMenuItem("Generar Constancia de entrega de documentos");
			mntmConstanciaDeEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			mnGestionarLicenciaAmbiental.add(mntmConstanciaDeEntrega);

			JMenuItem mntmGenerarInformeDe = new JMenuItem("Generar Informe de Proyectos");
			mntmGenerarInformeDe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerarReporte();
				}
			});
			mntmGenerarInformeDe.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			mnGestionarLicenciaAmbiental.add(mntmGenerarInformeDe);
			mntmControlarAsistencia.setEnabled(UsuarioPrivilegios.getBoolean("pri_asistencia"));

			JMenuItem mntmGestionarFuncionarios = new JMenuItem("Gestionar Funcionarios");
			mntmGestionarFuncionarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AdministrarFuncionarios administrarfuncionarios = new AdministrarFuncionarios();
					administrarfuncionarios.setVisible(true);
				}
			});

			JMenuItem mntmGenerarLiquidacinSalarial = new JMenuItem("Generar Liquidaci\u00F3n Salarial");
			mntmGenerarLiquidacinSalarial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					LiquidacionSalarial liquidacion = new LiquidacionSalarial();
					liquidacion.setVisible(true);
				}
			});
			mntmGenerarLiquidacinSalarial.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			mnGestionarRecursosHumanos.add(mntmGenerarLiquidacinSalarial);
			mntmGestionarFuncionarios.setFont(new Font("Segoe UI", Font.PLAIN, 18));
			mnGestionarRecursosHumanos.add(mntmGestionarFuncionarios);

			if (Principal.tLogin.getText().equals("admin")) {
				mntmInicializarSistema.setEnabled(true);
				System.out.println("El usuario es admin");
			} else {
				mntmInicializarSistema.setEnabled(false);
				System.out.println("Es usuario no es admin");
			}
			UsuarioPrivilegios.close();
		} catch (SQLException e1) {
			// TODO Bloque catch generado automáticamente
			System.out.println("Error al ejecutar privilegios de usuario " + e1);
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public JTextField gettLogin() {
		return tLogin;
	}

	public void settLogin(JTextField tLogin) {
		this.tLogin = tLogin;
	}

	@SuppressWarnings("unchecked")
	private static Object GenerarReporte() {
		try {
			Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skarlata", "postgres", "admin");

			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = AdministrarProyectos.class.getResourceAsStream("/reportes/reportProyectoPrincipal.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			Principal principal = new Principal();
			parametros.put("NombreOrganizacion", "Karanday");

			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, conexion);
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
			System.out.println("Error " + e);
		}
		return null;
	}
}
