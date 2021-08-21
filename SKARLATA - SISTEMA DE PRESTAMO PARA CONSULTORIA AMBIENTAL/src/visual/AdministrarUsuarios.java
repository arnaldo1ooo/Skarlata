package visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import session.AdministrarUsuariosSession;
import utilidades.Utilidades;

public class AdministrarUsuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1936636351999089124L;
	private JPanel contentPane;
	DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
	private TableRowSorter trsfiltro;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnSalir;

	private AdministrarUsuarios administrarusuarios;
	private JPanel panel;
	private JTextField tBusqueda;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnInforme;
	private JComboBox comboFiltro;
	private JTable tableUsuarios;
	private static JTable table;
	private JPanel pnDatosUsuario;
	private JScrollPane scrollPane;
	private JTextField tNombre;
	private JTextField tApellido;
	private JTextField tCedula;
	private JTextField tNomUsuario;
	private JComboBox cbEstado;
	private JComboBox cbPrivilegio;
	private JTextField tCodigo;
	private JPasswordField tpPass;
	private JPasswordField tpRepePass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministrarUsuarios frame = new AdministrarUsuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AdministrarUsuarios() {

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
		setType(Type.UTILITY);
		setTitle("Administrar Usuarios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height; // Tamano total vertical
		int width = pantalla.width;// Tamano total horizontal
		setLocation(0, 155); //Localizacion de pantalla x,y
		setSize(width, 720); // Tamano de ventana

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 1920, 720);
		contentPane.add(panel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(417, 23, 1082, 286);

		JLabel label = new JLabel("Buscar por");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(496, 305, 110, 33);
		label.setFont(new Font("Rockwell", Font.BOLD, 17));

		comboFiltro = new JComboBox();
		comboFiltro.setBounds(611, 305, 238, 33);
		comboFiltro.setModel(new DefaultComboBoxModel(new String[] { "Codigo", "Nombre", "Apellido", "Cedula", "Login", "Estado", "Privilegio", "Todos" }));
		comboFiltro.setSelectedIndex(7);
		comboFiltro.setFont(new Font("Rockwell", Font.BOLD, 17));

		tBusqueda = new JTextField();
		tBusqueda.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				String cadena = (tBusqueda.getText().toUpperCase());
				tBusqueda.setText(cadena);
				repaint();

				trsfiltro = new TableRowSorter(tableUsuarios.getModel());
				filtro();
				tableUsuarios.setRowSorter(trsfiltro);
			}
		});
		tBusqueda.setBounds(854, 305, 645, 33);
		tBusqueda.setFont(new Font("Rockwell", Font.PLAIN, 17));
		tBusqueda.setColumns(10);

		panel.setLayout(null);
		panel.add(label);
		panel.add(comboFiltro);
		panel.add(scrollPane);

		tableUsuarios = new JTable();
		MostrarResultSetEnJtable(AdministrarUsuariosSession.obtenertabla());
		scrollPane.setViewportView(table);
		panel.add(tBusqueda);

		JPanel pnBotones = new JPanel();
		pnBotones.setBackground(SystemColor.activeCaption);
		pnBotones.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBotones.setBounds(124, 23, 232, 462);
		panel.add(pnBotones);
		pnBotones.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HabilitarModoEdicion(true);

				ComboConsulta();

				try {
					ResultSet rs = AdministrarUsuariosSession.obtenercodigoultimoregistro();
					rs.next();
					int UltimoCodigo = rs.getInt("usu_codigo");
					gettCodigo().setText((UltimoCodigo + 1) + "");

				} catch (Exception e2) {
					gettCodigo().setText("1");
					System.out.println("Error en rs" + e2);
				}
			}
		});
		btnNuevo.setBounds(21, 38, 190, 68);
		pnBotones.add(btnNuevo);
		btnNuevo.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/new-file-33984.png")));
		btnNuevo.setIconTextGap(5);
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HabilitarModoEdicion(true);
				// Cargar los textfields con el lector seleccionado en el jtable
				int row = tableUsuarios.getSelectedRow();
				int CodigoSeleccionado = Integer.parseInt(tableUsuarios.getValueAt(row, 0).toString());
				ResultSet RegistroSeleccionado = AdministrarUsuariosSession.obtenerregistro(CodigoSeleccionado);

				ComboConsulta();
				try {
					RegistroSeleccionado.next();
					gettCodigo().setText(RegistroSeleccionado.getString("usu_codigo"));
					gettNombre().setText(RegistroSeleccionado.getString("usu_nombre"));
					gettApellido().setText(RegistroSeleccionado.getString("usu_apellido"));
					gettCedula().setText(RegistroSeleccionado.getString("usu_cedula"));
					gettNomUsuario().setText(RegistroSeleccionado.getString("usu_login"));
					getCbEstado().setSelectedIndex(Integer.parseInt(RegistroSeleccionado.getString("usu_estado")));
					getCbPrivilegio().setSelectedIndex(Integer.parseInt(RegistroSeleccionado.getString("usu_privilegio")) - 2);
					getTpPass().setText(RegistroSeleccionado.getString("usu_pass"));

				} catch (SQLException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
			}
		});
		btnModificar.setBounds(21, 144, 190, 68);
		pnBotones.add(btnModificar);
		btnModificar.setEnabled(false);
		btnModificar.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/modificar-el-icono-del-documento-14646 copia.png")));
		btnModificar.setIconTextGap(5);
		btnModificar.setHorizontalAlignment(SwingConstants.LEADING);
		btnModificar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = tableUsuarios.getSelectedRow();
				int CodigoSeleccionado = Integer.parseInt(tableUsuarios.getValueAt(row, 0).toString());
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar a " + tableUsuarios.getValueAt(row, 1).toString() + " " + tableUsuarios.getValueAt(row, 2).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {

						AdministrarUsuariosSession.eliminarregistro(CodigoSeleccionado);
						ActualizarTabla();
						JOptionPane.showMessageDialog(null, "Registro eliminado con exito");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e);
					}
				}
			}
		});
		btnEliminar.setBounds(21, 250, 190, 68);
		pnBotones.add(btnEliminar);
		btnEliminar.setEnabled(false);
		btnEliminar.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/document_delete_128 copia.png")));
		btnEliminar.setIconTextGap(5);
		btnEliminar.setHorizontalAlignment(SwingConstants.LEADING);
		btnEliminar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnInforme = new JButton("Informe");
		btnInforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerarReporte();
			}
		});
		btnInforme.setBounds(21, 356, 190, 68);
		pnBotones.add(btnInforme);
		btnInforme.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/distributor-report-icon copia.png")));
		btnInforme.setIconTextGap(5);
		btnInforme.setHorizontalAlignment(SwingConstants.LEADING);
		btnInforme.setFont(new Font("Rockwell", Font.BOLD, 20));

		pnDatosUsuario = new JPanel();
		pnDatosUsuario.setBackground(SystemColor.activeCaption);
		pnDatosUsuario.setLayout(null);
		pnDatosUsuario.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos del Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatosUsuario.setBounds(417, 357, 1082, 350);
		panel.add(pnDatosUsuario);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(791, 268, 139, 68);
		pnDatosUsuario.add(btnSalir);
		btnSalir.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/Salir copia.png")));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HabilitarModoEdicion(false);
				tCodigo.setText("");
				tNombre.setText("");
				tApellido.setText("");
				tCedula.setText("");
				cbEstado.setSelectedIndex(-1);
				cbPrivilegio.setSelectedIndex(-1);
				tNomUsuario.setText("");
				tpPass.setText("");
				tpRepePass.setText("");
			}
		});
		btnCancelar.setBounds(468, 268, 173, 68);
		pnDatosUsuario.add(btnCancelar);
		btnCancelar.setEnabled(false);
		btnCancelar.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(150, 268, 168, 68);
		pnDatosUsuario.add(btnGuardar);
		btnGuardar.setEnabled(false);
		btnGuardar.setIconTextGap(5);
		btnGuardar.setHorizontalAlignment(SwingConstants.LEADING);
		btnGuardar.setIcon(new ImageIcon(AdministrarUsuarios.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obtener el password
				char passArray[] = getTpPass().getPassword();
				// Revisar que sean letras y numeros
				for (int i = 0; i < passArray.length; i++) {
					char c = passArray[i];
					// Si no es letra o numero entonces no es valido
					if (!Character.isLetterOrDigit(c))
						JOptionPane.showMessageDialog(null, "La contraseña deben ser numeros y/o letras");
				}
				// Convertir el password a String
				String pass = new String(passArray);

				// Obtener el password
				char repepassArray[] = getTpRepePass().getPassword();
				// Revisar que sean letras y numeros
				for (int i = 0; i < repepassArray.length; i++) {
					char c = repepassArray[i];
					// Si no es letra o numero entonces no es valido
					if (!Character.isLetterOrDigit(c))
						JOptionPane.showMessageDialog(null, "La contraseña deben ser numeros y/o letras");
				}
				// Convertir el password a String
				String repepass = new String(passArray);

				if (pass.equals(repepass)) {

				} else {
					return;
				}

				try {
					AdministrarUsuariosSession.InsertarUsuarios(Integer.parseInt(gettCodigo().getText()), gettNombre().getText(), gettApellido().getText(), Integer.parseInt(gettCedula().getText()), gettNomUsuario().getText(), pass,
							cbEstado.getSelectedIndex(), cbPrivilegio.getSelectedIndex() + 2);
					JOptionPane.showMessageDialog(null, "Usuario insertado con éxito");
				} catch (Exception e2) {
					try {
						AdministrarUsuariosSession.ActualizarUsuarios(Integer.parseInt(gettCodigo().getText()), gettNombre().getText(), gettApellido().getText(), Integer.parseInt(gettCedula().getText()), gettNomUsuario().getText(), pass,
								cbEstado.getSelectedIndex(), cbPrivilegio.getSelectedIndex() + 2);
						JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito");
					} catch (Exception e3) {
						System.out.println(e3);
					}
				}
				getBtnCancelar().doClick();
				HabilitarModoEdicion(false);
				ActualizarTabla();

			}
		});
		btnGuardar.setFont(new Font("Rockwell", Font.BOLD, 20));

		JLabel label_1 = new JLabel("Nombre");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_1.setBounds(36, 63, 73, 33);
		pnDatosUsuario.add(label_1);

		tNombre = new JTextField();
		tNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tNombre.setEnabled(false);
		tNombre.setColumns(10);
		tNombre.setBounds(119, 63, 463, 33);
		pnDatosUsuario.add(tNombre);

		JLabel label_2 = new JLabel("Apellidos");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_2.setBounds(17, 103, 92, 33);
		pnDatosUsuario.add(label_2);

		tApellido = new JTextField();
		tApellido.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tApellido.setEnabled(false);
		tApellido.setColumns(10);
		tApellido.setBounds(119, 103, 463, 33);
		pnDatosUsuario.add(tApellido);

		cbEstado = new JComboBox();
		cbEstado.setModel(new DefaultComboBoxModel(new String[] { "INACTIVO", "ACTIVO" }));
		cbEstado.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbEstado.setEnabled(false);
		cbEstado.setBounds(119, 183, 175, 33);
		pnDatosUsuario.add(cbEstado);

		JLabel label_3 = new JLabel("Cedula");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setForeground(Color.BLACK);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_3.setBounds(37, 143, 72, 33);
		pnDatosUsuario.add(label_3);

		tCedula = new JTextField();
		tCedula.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tCedula.setEnabled(false);
		tCedula.setColumns(10);
		tCedula.setBounds(119, 143, 238, 33);
		pnDatosUsuario.add(tCedula);

		JLabel label_4 = new JLabel("Estado");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setForeground(Color.BLACK);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_4.setBounds(27, 183, 82, 33);
		pnDatosUsuario.add(label_4);

		JLabel label_5 = new JLabel("Privilegio");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setForeground(Color.BLACK);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_5.setBounds(27, 223, 82, 33);
		pnDatosUsuario.add(label_5);

		cbPrivilegio = new JComboBox();
		cbPrivilegio.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbPrivilegio.setEnabled(false);
		cbPrivilegio.setBounds(119, 223, 175, 33);
		pnDatosUsuario.add(cbPrivilegio);

		JLabel label_6 = new JLabel("Nombre de usuario");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_6.setBounds(594, 24, 189, 33);
		pnDatosUsuario.add(label_6);

		JLabel label_7 = new JLabel("Contrase\u00F1a");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_7.setBounds(643, 64, 140, 33);
		pnDatosUsuario.add(label_7);

		JLabel label_8 = new JLabel("Repetir Contrase\u00F1a");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_8.setBounds(594, 104, 189, 33);
		pnDatosUsuario.add(label_8);

		tNomUsuario = new JTextField();
		tNomUsuario.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tNomUsuario.setEnabled(false);
		tNomUsuario.setColumns(10);
		tNomUsuario.setBounds(795, 24, 251, 33);
		pnDatosUsuario.add(tNomUsuario);

		tCodigo = new JTextField();
		tCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tCodigo.setEnabled(false);
		tCodigo.setColumns(10);
		tCodigo.setBounds(119, 24, 58, 33);
		pnDatosUsuario.add(tCodigo);

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setForeground(Color.BLACK);
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodigo.setBounds(36, 24, 73, 33);
		pnDatosUsuario.add(lblCodigo);

		tpPass = new JPasswordField();
		tpPass.setEnabled(false);
		tpPass.setBounds(795, 64, 251, 33);
		pnDatosUsuario.add(tpPass);

		tpRepePass = new JPasswordField();
		tpRepePass.setEnabled(false);
		tpRepePass.setBounds(795, 104, 251, 33);
		pnDatosUsuario.add(tpRepePass);

		//Privilegios
		try {
			Utilidades utilidades = new Utilidades();
			ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
			UsuarioPrivilegios.next();
			btnNuevo.setEnabled(UsuarioPrivilegios.getBoolean("pri_usuarionuevo"));
			btnModificar.setEnabled(UsuarioPrivilegios.getBoolean("pri_usuariomodificar"));
			btnEliminar.setEnabled(UsuarioPrivilegios.getBoolean("pri_usuarioeliminar"));
			btnInforme.setEnabled(UsuarioPrivilegios.getBoolean("pri_usuarioinforme"));
			UsuarioPrivilegios.close();
		} catch (SQLException e1) {
			// TODO Bloque catch generado automáticamente
			System.out.println("Error al ejecutar privilegios de usuario " + e1);
		}

	}

	public TableRowSorter getTrsfiltro() {
		return trsfiltro;
	}

	public void setTrsfiltro(TableRowSorter trsfiltro) {
		this.trsfiltro = trsfiltro;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public void setBtnNuevo(JButton btnNuevo) {
		this.btnNuevo = btnNuevo;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(JButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public JButton getBtnInforme() {
		return btnInforme;
	}

	public void setBtnInforme(JButton btnInforme) {
		this.btnInforme = btnInforme;
	}

	public JTextField gettCodigo() {
		return tCodigo;
	}

	public void settCodigo(JTextField tCodigo) {
		this.tCodigo = tCodigo;
	}

	public JTextField gettBusqueda() {
		return tBusqueda;
	}

	public void settBusqueda(JTextField tBusqueda) {
		this.tBusqueda = tBusqueda;
	}

	public JTable getTableClientes() {
		return table;
	}

	public void setTableClientes(JTable tableClientes) {
		this.table = tableClientes;
	}

	public JTextField gettNombre() {
		return tNombre;
	}

	public void settNombre(JTextField tNombre) {
		this.tNombre = tNombre;
	}

	public JTextField gettApellido() {
		return tApellido;
	}

	public void settApellido(JTextField tApellido) {
		this.tApellido = tApellido;
	}

	public JTextField gettCedula() {
		return tCedula;
	}

	public void settCedula(JTextField tCedula) {
		this.tCedula = tCedula;
	}

	public JTextField gettNomUsuario() {
		return tNomUsuario;
	}

	public void settNomUsuario(JTextField tNomUsuario) {
		this.tNomUsuario = tNomUsuario;
	}

	public JPasswordField getTpPass() {
		return tpPass;
	}

	public void setTpPass(JPasswordField tpPass) {
		this.tpPass = tpPass;
	}

	public JPasswordField getTpRepePass() {
		return tpRepePass;
	}

	public void setTpRepePass(JPasswordField tpRepePass) {
		this.tpRepePass = tpRepePass;
	}

	public JComboBox getCbEstado() {
		return cbEstado;
	}

	public void setCbEstado(JComboBox cbEstado) {
		this.cbEstado = cbEstado;
	}

	public JComboBox getCbPrivilegio() {
		return cbPrivilegio;
	}

	public void setCbPrivilegio(JComboBox cbPrivilegio) {
		this.cbPrivilegio = cbPrivilegio;
	}

	@SuppressWarnings("unchecked")
	public void filtro() {
		int columnaABuscar = 0;
		if (comboFiltro.getSelectedItem() == "Codigo") {
			columnaABuscar = 0;
		}
		if (comboFiltro.getSelectedItem().toString() == "Nombre") {
			columnaABuscar = 1;
		}
		if (comboFiltro.getSelectedItem() == "Apellido") {
			columnaABuscar = 2;
		}
		if (comboFiltro.getSelectedItem() == "Cedula") {
			columnaABuscar = 3;
		}
		if (comboFiltro.getSelectedItem() == "Login") {
			columnaABuscar = 4;
		}

		if (comboFiltro.getSelectedItem() == "Estado") {
			columnaABuscar = 6;
		}
		if (comboFiltro.getSelectedItem() == "Privilegio") {
			columnaABuscar = 7;
		}

		if (comboFiltro.getSelectedItem() == "Todos") {
			trsfiltro.setRowFilter(RowFilter.regexFilter(tBusqueda.getText(), 0, 1, 2, 3, 4, 6, 7));
			return;
		}
		trsfiltro.setRowFilter(RowFilter.regexFilter(tBusqueda.getText(), columnaABuscar));
	}

	private void MostrarResultSetEnJtable(ResultSet rs) {

		try {
			DefaultTableModel modelo = new DefaultTableModel();
			// Para que registros no sean editables
			table = new JTable(modelo) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (tableUsuarios.isEnabled() == true) {
						if (tableUsuarios.getSelectedRow() != -1) {
							btnModificar.setEnabled(true);
							btnEliminar.setEnabled(true);
						} else {
							btnModificar.setEnabled(false);
							btnEliminar.setEnabled(false);
						}
					}

				}
			});
			table.setFont(new Font("SansSerif", Font.PLAIN, 15));

			table.getTableHeader().setReorderingAllowed(false); // Para que no se pueda mover columnas
			table.getTableHeader().setResizingAllowed(false); // Para que no se puede cambiar tamaño de columnas
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Para que solo se pueda seleccionar un registro a la vez

			// Creamos las columnas.
			modelo.addColumn("Codigo");
			modelo.addColumn("Nombre");
			modelo.addColumn("Apellido");
			modelo.addColumn("Cedula");
			modelo.addColumn("Login");
			modelo.addColumn("Pass");
			modelo.addColumn("Estado");
			modelo.addColumn("Privilegio");

			// TAMANO DE CADA COLUMNA
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(65);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.getColumnModel().getColumn(3).setPreferredWidth(185);
			table.getColumnModel().getColumn(4).setPreferredWidth(234);
			table.getColumnModel().getColumn(5).setPreferredWidth(190);
			table.getColumnModel().getColumn(6).setPreferredWidth(234);
			table.getColumnModel().getColumn(7).setPreferredWidth(190);

			ResultSetMetaData metaDatos = rs.getMetaData();
			// Se obtiene el número de columnas.
			int numeroColumnas = metaDatos.getColumnCount();
			// Bucle para cada resultado en la consulta
			while (rs.next()) {
				// Se crea un array que será una de las filas de la tabla.
				Object[] fila = new Object[numeroColumnas]; // se pone el numero de columnas en la tabla	
				for (int i = 0; i < numeroColumnas; i++) // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
					fila[i] = rs.getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

				modelo.addRow(fila);// Se añade al modelo la fila completa.
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void HabilitarModoEdicion(boolean habilitar) {
		tNombre.setEnabled(habilitar);
		tApellido.setEnabled(habilitar);
		tCedula.setEnabled(habilitar);
		cbEstado.setEnabled(habilitar);
		cbPrivilegio.setEnabled(habilitar);
		tNomUsuario.setEnabled(habilitar);
		tpPass.setEnabled(habilitar);
		tpRepePass.setEnabled(habilitar);

		btnGuardar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnNuevo.setEnabled(!habilitar);
		btnModificar.setEnabled(habilitar);
		btnEliminar.setEnabled(habilitar);

		table.setEnabled(!habilitar);
	}

	private void ActualizarTabla() {
		MostrarResultSetEnJtable(AdministrarUsuariosSession.obtenertabla());
		scrollPane.setViewportView(table);
	}

	//Cargar combo con consulta
	private void ComboConsulta() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbPrivilegio.removeAllItems();
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT pri_descri FROM privilegio WHERE pri_codigo != 1 ORDER BY pri_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbPrivilegio.addItem(rs.getObject("pri_descri"));

			}
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}
	}
	
	@SuppressWarnings("unchecked")
	private static Object GenerarReporte() {
		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(table.getModel());
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = AdministrarUsuarios.class.getResourceAsStream("/reportes/reportUsuario.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			Principal principal = new Principal();
			parametros.put("NombreOrganizacion", "Karanday");

			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, tablemodel);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setTitle("Informe de Usuarios");
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
