package visual;

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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import session.AdministrarClientesSession;
import utilidades.Utilidades;

public class AdministrarClientes extends JDialog {

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

	private AdministrarClientes administrarusuarios;
	private JPanel panel;
	private JTextField tBusqueda;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnInforme;
	private JComboBox comboFiltro;
	private JLabel label_5;
	private JTextField tNombre;
	private JLabel lblApellido;
	private JTextField tApellido;
	private JLabel lblDireccion;
	private JTextField tCedula;
	private static JTable table;
	private JPanel pnDatosCliente;
	private JScrollPane scrollPane;
	private JTextField tCodigo;
	private JTextField tDireccion;
	private JTextField tTelefono;
	private Utilidades utilidades = new Utilidades();
	private JComboBox cbOrden;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministrarClientes frame = new AdministrarClientes();
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
	public AdministrarClientes() {

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
		label.setBounds(782, 305, 110, 33);
		label.setFont(new Font("Rockwell", Font.BOLD, 17));

		comboFiltro = new JComboBox();
		comboFiltro.setBounds(897, 305, 238, 33);
		comboFiltro.setModel(new DefaultComboBoxModel(new String[] { "Codigo", "Nombre", "Apellido", "Cargo", "Hora entrada", "Hora salida", "Todos" }));
		comboFiltro.setSelectedIndex(6);
		comboFiltro.setFont(new Font("Rockwell", Font.BOLD, 17));

		tBusqueda = new JTextField();
		tBusqueda.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				String cadena = (tBusqueda.getText().toUpperCase());
				tBusqueda.setText(cadena);
				repaint();

				trsfiltro = new TableRowSorter(table.getModel());
				filtro();
				table.setRowSorter(trsfiltro);
			}
		});
		tBusqueda.setBounds(1134, 305, 365, 33);
		tBusqueda.setFont(new Font("Rockwell", Font.PLAIN, 17));
		tBusqueda.setColumns(10);

		panel.setLayout(null);
		panel.add(label);
		panel.add(comboFiltro);
		panel.add(scrollPane);

		table = new JTable();
		MostrarResultSetEnJtableClientes(AdministrarClientesSession.obtenerclientes());
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

				try {
					ResultSet rs = AdministrarClientesSession.obtenercodigoultimoregistro();
					rs.next();
					int UltimoCodigo = rs.getInt("cli_codigo");
					gettCodigo().setText((UltimoCodigo + 1) + "");

				} catch (Exception e2) {
					gettCodigo().setText("1");
					System.out.println("Error en rs" + e2);
				}
			}
		});
		btnNuevo.setBounds(21, 38, 190, 68);
		pnBotones.add(btnNuevo);
		btnNuevo.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/new-file-33984.png")));
		btnNuevo.setIconTextGap(5);
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HabilitarModoEdicion(true);
				// Cargar los textfields con el lector seleccionado en el jtable
				int row = table.getSelectedRow();
				int CodigoSeleccionado = Integer.parseInt(table.getValueAt(row, 0).toString());
				ResultSet RegistroSeleccionado = AdministrarClientesSession.obtenerregistro(CodigoSeleccionado);

				try {
					RegistroSeleccionado.next();
					gettCodigo().setText(RegistroSeleccionado.getString("cli_codigo"));
					gettNombre().setText(RegistroSeleccionado.getString("cli_nombre"));
					gettApellido().setText(RegistroSeleccionado.getString("cli_apelli"));
					gettCedula().setText(RegistroSeleccionado.getString("cli_cedula"));
					gettDireccion().setText(RegistroSeleccionado.getString("cli_direcc"));
					gettTelefono().setText(RegistroSeleccionado.getString("cli_telefo"));
				} catch (SQLException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
			}
		});
		btnModificar.setBounds(21, 144, 190, 68);
		pnBotones.add(btnModificar);
		btnModificar.setEnabled(false);
		btnModificar.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/modificar-el-icono-del-documento-14646 copia.png")));
		btnModificar.setIconTextGap(5);
		btnModificar.setHorizontalAlignment(SwingConstants.LEADING);
		btnModificar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = table.getSelectedRow();
				int CodigoSeleccionado = Integer.parseInt(table.getValueAt(row, 0).toString());
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar a " + table.getValueAt(row, 1).toString() + " " + table.getValueAt(row, 2).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {

						AdministrarClientesSession.eliminarregistro(CodigoSeleccionado);
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
		btnEliminar.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/document_delete_128 copia.png")));
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
		btnInforme.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/distributor-report-icon copia.png")));
		btnInforme.setIconTextGap(5);
		btnInforme.setHorizontalAlignment(SwingConstants.LEADING);
		btnInforme.setFont(new Font("Rockwell", Font.BOLD, 20));

		pnDatosCliente = new JPanel();
		pnDatosCliente.setBackground(SystemColor.activeCaption);
		pnDatosCliente.setLayout(null);
		pnDatosCliente.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatosCliente.setBounds(417, 357, 1082, 350);
		panel.add(pnDatosCliente);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(791, 268, 139, 68);
		pnDatosCliente.add(btnSalir);
		btnSalir.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/Salir copia.png")));
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
			}
		});
		btnCancelar.setBounds(468, 268, 173, 68);
		pnDatosCliente.add(btnCancelar);
		btnCancelar.setEnabled(false);
		btnCancelar.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(150, 268, 168, 68);
		pnDatosCliente.add(btnGuardar);
		btnGuardar.setEnabled(false);
		btnGuardar.setIconTextGap(5);
		btnGuardar.setHorizontalAlignment(SwingConstants.LEADING);
		btnGuardar.setIcon(new ImageIcon(AdministrarClientes.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AdministrarClientesSession.InsertarClientes(Integer.parseInt(gettCodigo().getText()), gettNombre().getText(), gettApellido().getText(), Integer.parseInt(gettCedula().getText()), gettDireccion().getText(),
							gettTelefono().getText());
					JOptionPane.showMessageDialog(null, "Cliente insertado con éxito");
				} catch (Exception e2) {
					try {
						AdministrarClientesSession.ActualizarClientes(Integer.parseInt(gettCodigo().getText()), gettNombre().getText(), gettApellido().getText(), Integer.parseInt(gettCedula().getText()), gettDireccion().getText(),
								gettTelefono().getText());
						JOptionPane.showMessageDialog(null, "Cliente actualizado con éxito");
					} catch (Exception e3) {
						System.out.println(e3);
					}
				}
				HabilitarModoEdicion(false);
				ActualizarTabla();
			}
		});
		btnGuardar.setFont(new Font("Rockwell", Font.BOLD, 20));

		label_5 = new JLabel("Nombre");
		label_5.setBounds(130, 63, 73, 33);
		pnDatosCliente.add(label_5);
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 17));

		tNombre = new JTextField();
		tNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tNombre.setText(tNombre.getText().toUpperCase());
				}
			}
		});
		tNombre.setBounds(213, 63, 463, 33);
		pnDatosCliente.add(tNombre);
		tNombre.setEnabled(false);
		tNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tNombre.setColumns(10);

		lblApellido = new JLabel("Apellidos");
		lblApellido.setBounds(111, 103, 92, 33);
		pnDatosCliente.add(lblApellido);
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 17));

		tApellido = new JTextField();
		tApellido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tApellido.setText(tApellido.getText().toUpperCase());
				}
			}
		});
		tApellido.setBounds(213, 103, 463, 33);
		pnDatosCliente.add(tApellido);
		tApellido.setEnabled(false);
		tApellido.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tApellido.setColumns(10);

		lblDireccion = new JLabel("Cedula");
		lblDireccion.setBounds(111, 143, 92, 33);
		pnDatosCliente.add(lblDireccion);
		lblDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 17));

		tCedula = new JTextField();
		tCedula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tCedula.setBounds(213, 143, 463, 33);
		pnDatosCliente.add(tCedula);
		tCedula.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tCedula.setEnabled(false);
		tCedula.setColumns(10);

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodigo.setBounds(130, 23, 73, 33);
		pnDatosCliente.add(lblCodigo);

		tCodigo = new JTextField();
		tCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tCodigo.setEnabled(false);
		tCodigo.setColumns(10);
		tCodigo.setBounds(215, 23, 73, 33);
		pnDatosCliente.add(tCodigo);

		JLabel label_1 = new JLabel("Direccion");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 17));
		label_1.setBounds(111, 183, 92, 33);
		pnDatosCliente.add(label_1);

		tDireccion = new JTextField();
		tDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tDireccion.setText(tDireccion.getText().toUpperCase());
				}
			}
		});
		tDireccion.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tDireccion.setEnabled(false);
		tDireccion.setColumns(10);
		tDireccion.setBounds(213, 183, 463, 33);
		pnDatosCliente.add(tDireccion);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefono.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblTelefono.setBounds(111, 223, 92, 33);
		pnDatosCliente.add(lblTelefono);

		tTelefono = new JTextField();
		tTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// Verificar si la tecla pulsada no es un digito
				char caracter = e.getKeyChar();
				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /* corresponde a BACK_SPACE */)) {
					e.consume(); // ignorar el evento de teclado
				}
			}
		});
		tTelefono.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tTelefono.setEnabled(false);
		tTelefono.setColumns(10);
		tTelefono.setBounds(213, 223, 463, 33);
		pnDatosCliente.add(tTelefono);

		//Privilegios
		try {
		
			ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
			UsuarioPrivilegios.next();
			btnNuevo.setEnabled(UsuarioPrivilegios.getBoolean("pri_clientenuevo"));
			btnModificar.setEnabled(UsuarioPrivilegios.getBoolean("pri_clientemodificar"));
			btnEliminar.setEnabled(UsuarioPrivilegios.getBoolean("pri_clienteeliminar"));
			btnInforme.setEnabled(UsuarioPrivilegios.getBoolean("pri_clienteinforme"));
			
			JLabel lblOrdenarPor = new JLabel("Ordenar por");
			lblOrdenarPor.setHorizontalAlignment(SwingConstants.RIGHT);
			lblOrdenarPor.setFont(new Font("Rockwell", Font.BOLD, 17));
			lblOrdenarPor.setBounds(427, 305, 110, 33);
			panel.add(lblOrdenarPor);
			
			cbOrden = new JComboBox();
			cbOrden.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ActualizarTabla();
				}
			});
			cbOrden.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre", "Apellido", "Cedula", "Direccion", "Telefono"}));
			cbOrden.setSelectedIndex(0);
			cbOrden.setFont(new Font("Rockwell", Font.BOLD, 17));
			cbOrden.setBounds(542, 305, 158, 33);
			panel.add(cbOrden);
			UsuarioPrivilegios.close();
		} catch (SQLException e1) {
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

	public JTextField gettCodigo() {
		return tCodigo;
	}

	public void settCodigo(JTextField tCodigo) {
		this.tCodigo = tCodigo;
	}

	public JTextField gettDireccion() {
		return tDireccion;
	}

	public void settDireccion(JTextField tDireccion) {
		this.tDireccion = tDireccion;
	}

	public JTextField gettTelefono() {
		return tTelefono;
	}

	public void settTelefono(JTextField tTelefono) {
		this.tTelefono = tTelefono;
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
		if (comboFiltro.getSelectedItem() == "Cargo") {
			columnaABuscar = 3;
		}
		if (comboFiltro.getSelectedItem() == "Hora entrada") {
			columnaABuscar = 4;
		}

		if (comboFiltro.getSelectedItem() == "Hora salida") {
			columnaABuscar = 5;
		}

		if (comboFiltro.getSelectedItem() == "Todos") {
			trsfiltro.setRowFilter(RowFilter.regexFilter(tBusqueda.getText(), 0, 1, 2, 3, 4, 5));
			return;
		}
		trsfiltro.setRowFilter(RowFilter.regexFilter(tBusqueda.getText(), columnaABuscar));
	}

	private void MostrarResultSetEnJtableClientes(ResultSet rs) {

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
					if (table.isEnabled() == true) {
						if (table.getSelectedRow() != -1) {
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
			modelo.addColumn("Direccion");
			modelo.addColumn("Telefono");

			// TAMANO DE CADA COLUMNA
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(65);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.getColumnModel().getColumn(3).setPreferredWidth(185);
			table.getColumnModel().getColumn(4).setPreferredWidth(234);
			table.getColumnModel().getColumn(5).setPreferredWidth(190);

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
		tDireccion.setEnabled(habilitar);
		tTelefono.setEnabled(habilitar);
		btnGuardar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnNuevo.setEnabled(!habilitar);
		btnModificar.setEnabled(habilitar);
		btnEliminar.setEnabled(habilitar);
		btnInforme.setEnabled(!habilitar);
		gettCodigo().setText("");
		gettNombre().setText("");
		gettApellido().setText("");
		gettCedula().setText("");
		gettDireccion().setText("");
		gettTelefono().setText("");

		table.setEnabled(!habilitar);
	}

	private void ActualizarTabla() {
		String Orden = "";
		if (cbOrden.getSelectedItem() == "Codigo") {
			Orden = "cli_codigo";
		}
		if (cbOrden.getSelectedItem() == "Nombre") {
			Orden = "cli_nombre";
		}
		if (cbOrden.getSelectedItem() == "Apellido") {
			Orden = "cli_apelli";
		}
		if (cbOrden.getSelectedItem() == "Cedula") {
			Orden = "cli_cedula";
		}
		if (cbOrden.getSelectedItem() == "Direccion") {
			Orden = "cli_direcc";
		}
		if (cbOrden.getSelectedItem() == "Telefono") {
			Orden = "cli_telefo";
		}
		
		ResultSet RsClientes = utilidades.ejecutarSQLSelect("SELECT * FROM cliente ORDER BY " + Orden);
		MostrarResultSetEnJtableClientes(RsClientes);
		scrollPane.setViewportView(table);
	}

	@SuppressWarnings("unchecked")
	private static Object GenerarReporte() {
		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(table.getModel());
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = AdministrarClientes.class.getResourceAsStream("/reportes/reportCliente.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			Principal principal = new Principal();
			parametros.put("NombreOrganizacion", "Karanday");

			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, tablemodel);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setTitle("Informe de Clientes");
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
