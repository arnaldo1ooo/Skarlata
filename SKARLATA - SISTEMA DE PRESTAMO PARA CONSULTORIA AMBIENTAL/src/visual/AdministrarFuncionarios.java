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
import session.AdministrarFuncionariosSession;
import utilidades.Utilidades;

public class AdministrarFuncionarios extends JDialog {

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
	private JComboBox cbCargo;
	private JLabel lblDireccion;
	private JTextField tDireccion;
	private JTable tableFuncionarios;
	private static JTable table;
	private JPanel pnDatosFuncionario;
	private JComboBox cbFrecuencia;
	private JScrollPane scrollPane;
	private JTextField tCodigo;
	private Utilidades utilidades = new Utilidades();
	private JComboBox cbOrden;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministrarFuncionarios frame = new AdministrarFuncionarios();
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
	public AdministrarFuncionarios() {

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
		label.setBounds(691, 305, 110, 33);
		label.setFont(new Font("Rockwell", Font.BOLD, 17));

		comboFiltro = new JComboBox();
		comboFiltro.setBounds(806, 305, 238, 33);
		comboFiltro.setModel(new DefaultComboBoxModel(new String[] { "Codigo", "Nombre", "Apellido", "Cargo", "Hora entrada", "Hora salida", "Todos" }));
		comboFiltro.setSelectedIndex(6);
		comboFiltro.setFont(new Font("Rockwell", Font.BOLD, 17));

		tBusqueda = new JTextField();
		tBusqueda.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent arg0) {
				String cadena = (tBusqueda.getText().toUpperCase());
				tBusqueda.setText(cadena);
				repaint();

				trsfiltro = new TableRowSorter(tableFuncionarios.getModel());
				filtro();
				tableFuncionarios.setRowSorter(trsfiltro);
			}
		});
		tBusqueda.setBounds(1044, 305, 455, 33);
		tBusqueda.setFont(new Font("Rockwell", Font.PLAIN, 17));
		tBusqueda.setColumns(10);

		panel.setLayout(null);
		panel.add(label);
		panel.add(comboFiltro);
		panel.add(scrollPane);

		tableFuncionarios = new JTable();
		MostrarResultSetEnJtableFuncionarios(AdministrarFuncionariosSession.obtenerfuncionarios());
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
					ResultSet rs = AdministrarFuncionariosSession.obtenercodigoultimoregistro();
					rs.next();
					int UltimoCodigo = rs.getInt("fun_codigo");
					gettCodigo().setText((UltimoCodigo + 1) + "");

				} catch (Exception e2) {
					gettCodigo().setText("1");
					System.out.println("Error en rs" + e2);
				}

				ComboTablaFrecuencia();
				ComboTablaCargo();

			}
		});
		btnNuevo.setBounds(21, 38, 190, 68);
		pnBotones.add(btnNuevo);
		btnNuevo.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/new-file-33984.png")));
		btnNuevo.setIconTextGap(5);
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ComboTablaCargo();
				ComboTablaFrecuencia();
				HabilitarModoEdicion(true);
				// Cargar los textfields con el lector seleccionado en el jtable
				int row = tableFuncionarios.getSelectedRow();
				int CodigoSeleccionado = Integer.parseInt(tableFuncionarios.getValueAt(row, 0).toString());
				ResultSet RegistroSeleccionado = AdministrarFuncionariosSession.obtenerregistro(CodigoSeleccionado);

				try {
					RegistroSeleccionado.next();
					gettCodigo().setText(RegistroSeleccionado.getString("fun_codigo"));
					gettNombre().setText(RegistroSeleccionado.getString("fun_nombre"));
					gettApellido().setText(RegistroSeleccionado.getString("fun_apellido"));
					gettDireccion().setText(RegistroSeleccionado.getString("fun_direcc"));
					getCbCargo().setSelectedIndex(RegistroSeleccionado.getInt("fun_cargo") - 1);
					getCbFrecuencia().setSelectedIndex(RegistroSeleccionado.getInt("fun_frec") - 1);
				} catch (SQLException e) {
					// TODO Bloque catch generado automáticamente
					e.printStackTrace();
				}
			}
		});
		btnModificar.setBounds(21, 144, 190, 68);
		pnBotones.add(btnModificar);
		btnModificar.setEnabled(false);
		btnModificar.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/modificar-el-icono-del-documento-14646 copia.png")));
		btnModificar.setIconTextGap(5);
		btnModificar.setHorizontalAlignment(SwingConstants.LEADING);
		btnModificar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = tableFuncionarios.getSelectedRow();
				int CodigoSeleccionado = Integer.parseInt(tableFuncionarios.getValueAt(row, 0).toString());
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar a " + tableFuncionarios.getValueAt(row, 1).toString() + " " + tableFuncionarios.getValueAt(row, 2).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {

						AdministrarFuncionariosSession.eliminarregistro(CodigoSeleccionado);
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
		btnEliminar.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/document_delete_128 copia.png")));
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
		btnInforme.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/distributor-report-icon copia.png")));
		btnInforme.setIconTextGap(5);
		btnInforme.setHorizontalAlignment(SwingConstants.LEADING);
		btnInforme.setFont(new Font("Rockwell", Font.BOLD, 20));

		pnDatosFuncionario = new JPanel();
		pnDatosFuncionario.setBackground(SystemColor.activeCaption);
		pnDatosFuncionario.setLayout(null);
		pnDatosFuncionario.setBorder(new TitledBorder(null, "Datos del Funcionario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatosFuncionario.setBounds(417, 357, 1082, 350);
		panel.add(pnDatosFuncionario);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(791, 268, 139, 68);
		pnDatosFuncionario.add(btnSalir);
		btnSalir.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/Salir copia.png")));
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
		pnDatosFuncionario.add(btnCancelar);
		btnCancelar.setEnabled(false);
		btnCancelar.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(150, 268, 168, 68);
		pnDatosFuncionario.add(btnGuardar);
		btnGuardar.setEnabled(false);
		btnGuardar.setIconTextGap(5);
		btnGuardar.setHorizontalAlignment(SwingConstants.LEADING);
		btnGuardar.setIcon(new ImageIcon(AdministrarFuncionarios.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AdministrarFuncionariosSession.InsertarFuncionarios(Integer.parseInt(gettCodigo().getText()), gettNombre().getText(), gettApellido().getText(), gettDireccion().getText(), (getCbCargo().getSelectedIndex() + 1),
							(getCbFrecuencia().getSelectedIndex() + 1));
					JOptionPane.showMessageDialog(null, "Funcionario insertado con éxito");
				} catch (Exception e2) {
					try {
						AdministrarFuncionariosSession.ActualizarFuncionarios(Integer.parseInt(gettCodigo().getText()), gettNombre().getText(), gettApellido().getText(), gettDireccion().getText(), (getCbCargo().getSelectedIndex() + 1),
								(getCbFrecuencia().getSelectedIndex() + 1));
						JOptionPane.showMessageDialog(null, "Funcionario actualizado con éxito");
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
		pnDatosFuncionario.add(label_5);
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
		pnDatosFuncionario.add(tNombre);
		tNombre.setEnabled(false);
		tNombre.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tNombre.setColumns(10);

		lblApellido = new JLabel("Apellidos");
		lblApellido.setBounds(111, 103, 92, 33);
		pnDatosFuncionario.add(lblApellido);
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
		pnDatosFuncionario.add(tApellido);
		tApellido.setEnabled(false);
		tApellido.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tApellido.setColumns(10);

		cbCargo = new JComboBox();
		cbCargo.setBounds(213, 183, 256, 33);
		pnDatosFuncionario.add(cbCargo);
		cbCargo.setEnabled(false);
		cbCargo.setFont(new Font("SansSerif", Font.PLAIN, 15));

		lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(111, 143, 92, 33);
		pnDatosFuncionario.add(lblDireccion);
		lblDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 17));

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
		tDireccion.setBounds(213, 143, 463, 33);
		pnDatosFuncionario.add(tDireccion);
		tDireccion.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tDireccion.setEnabled(false);
		tDireccion.setColumns(10);

		JLabel lblPrivilegio = new JLabel("Cargo");
		lblPrivilegio.setBounds(121, 183, 82, 33);
		pnDatosFuncionario.add(lblPrivilegio);
		lblPrivilegio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrivilegio.setFont(new Font("Tahoma", Font.BOLD, 17));

		JLabel lblFrecuencia = new JLabel("Frecuencia");
		lblFrecuencia.setBounds(102, 223, 101, 33);
		pnDatosFuncionario.add(lblFrecuencia);
		lblFrecuencia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFrecuencia.setFont(new Font("Tahoma", Font.BOLD, 17));

		cbFrecuencia = new JComboBox();
		cbFrecuencia.setBounds(213, 223, 256, 33);
		pnDatosFuncionario.add(cbFrecuencia);
		cbFrecuencia.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbFrecuencia.setEnabled(false);

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodigo.setBounds(130, 23, 73, 33);
		pnDatosFuncionario.add(lblCodigo);

		tCodigo = new JTextField();
		tCodigo.setFont(new Font("SansSerif", Font.PLAIN, 16));
		tCodigo.setEnabled(false);
		tCodigo.setColumns(10);
		tCodigo.setBounds(215, 23, 73, 33);
		pnDatosFuncionario.add(tCodigo);

		//Privilegios
		try {
			Utilidades utilidades = new Utilidades();
			ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
			UsuarioPrivilegios.next();
			btnNuevo.setEnabled(UsuarioPrivilegios.getBoolean("pri_funcionarionuevo"));
			btnModificar.setEnabled(UsuarioPrivilegios.getBoolean("pri_funcionariomodificar"));
			btnEliminar.setEnabled(UsuarioPrivilegios.getBoolean("pri_funcionarioeliminar"));
			btnInforme.setEnabled(UsuarioPrivilegios.getBoolean("pri_funcionarioinforme"));

			cbOrden = new JComboBox();
			cbOrden.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ActualizarTabla();
				}
			});
			cbOrden.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre", "Apellido", "Direccion", "Cargo", "Hora Entrada", "Hora Salida"}));
			cbOrden.setSelectedIndex(0);
			cbOrden.setFont(new Font("Rockwell", Font.BOLD, 17));
			cbOrden.setBounds(532, 305, 158, 33);
			panel.add(cbOrden);

			JLabel label_1 = new JLabel("Ordenar por");
			label_1.setHorizontalAlignment(SwingConstants.RIGHT);
			label_1.setFont(new Font("Rockwell", Font.BOLD, 17));
			label_1.setBounds(417, 305, 110, 33);
			panel.add(label_1);
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

	public JComboBox getCbCargo() {
		return cbCargo;
	}

	public void setCbCargo(JComboBox cbCargo) {
		this.cbCargo = cbCargo;
	}

	public JTextField gettDireccion() {
		return tDireccion;
	}

	public void settDireccion(JTextField tDireccion) {
		this.tDireccion = tDireccion;
	}

	public JComboBox getCbFrecuencia() {
		return cbFrecuencia;
	}

	public void setCbFrecuencia(JComboBox cbFrecuencia) {
		this.cbFrecuencia = cbFrecuencia;
	}

	public JTextField gettCodigo() {
		return tCodigo;
	}

	public void settCodigo(JTextField tCodigo) {
		this.tCodigo = tCodigo;
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

	private void MostrarResultSetEnJtableFuncionarios(ResultSet rs) {

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
					if (tableFuncionarios.isEnabled() == true) {
						if (tableFuncionarios.getSelectedRow() != -1) {
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
			modelo.addColumn("Direccion");
			modelo.addColumn("Cargo");
			modelo.addColumn("Hora entrada");
			modelo.addColumn("Hora salida");

			// TAMANO DE CADA COLUMNA
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(65);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);
			table.getColumnModel().getColumn(3).setPreferredWidth(185);
			table.getColumnModel().getColumn(4).setPreferredWidth(234);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);

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

	@SuppressWarnings("unchecked")
	private void ComboTablaCargo() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbCargo.removeAllItems();

		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT car_codigo, car_descri FROM cargo ORDER BY car_codigo");

			class Item {
				private String codigo;
				private String descripcion;

				public Item(String codigo, String descripcion) {
					this.codigo = codigo;
					this.descripcion = descripcion;
				}

				@Override
				public String toString() {
					return descripcion; //o que vai aparecer na comboBox  
				}
			}

			//agora para adicionar na combo, crie items 
			while (rs.next()) { // Faz o loop para preencher o CombServicos             
				String codigo = rs.getString("car_codigo");
				String descripcion = rs.getString("car_descri");
				cbCargo.addItem(new Item(codigo, descripcion));
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos, o un error de sintaxis en el codigo sql");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}

	}

	@SuppressWarnings("unchecked")
	private void ComboTablaFrecuencia() {
		Connection conexion = null;
		Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbFrecuencia.removeAllItems();

		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT frec_entrada, frec_salida FROM frecuencia ORDER BY frec_codigo");

			class Item {
				private String entrada;
				private String salida;

				public Item(String entrada, String salida) {
					this.entrada = entrada;
					this.salida = salida;
				}

				@Override
				public String toString() {
					return entrada + "    -    " + salida; //o que vai aparecer na comboBox  
				}
			}

			//agora para adicionar na combo, crie items 
			while (rs.next()) { // Faz o loop para preencher o CombServicos             
				String entrada = rs.getString("frec_entrada");
				String salida = rs.getString("frec_salida");
				cbFrecuencia.addItem(new Item(entrada, salida));
			}

			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos, o un error de sintaxis en el codigo sql");

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos");

		}

	}

	private void HabilitarModoEdicion(boolean habilitar) {
		tNombre.setEnabled(habilitar);
		tApellido.setEnabled(habilitar);
		tDireccion.setEnabled(habilitar);
		cbCargo.setEnabled(habilitar);
		cbFrecuencia.setEnabled(habilitar);
		btnGuardar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnNuevo.setEnabled(!habilitar);
		btnModificar.setEnabled(!habilitar);
		btnEliminar.setEnabled(!habilitar);
		gettCodigo().setText("");
		gettNombre().setText("");
		gettApellido().setText("");
		gettDireccion().setText("");
		getCbCargo().setSelectedIndex(-1);
		getCbFrecuencia().setSelectedIndex(-1);

		table.setEnabled(!habilitar);
	}

	private void ActualizarTabla() {
		String Orden = "";
		if (cbOrden.getSelectedItem() == "Codigo") {
			Orden = "fun_codigo";
		}
		if (cbOrden.getSelectedItem() == "Nombre") {
			Orden = "fun_nombre";
		}
		if (cbOrden.getSelectedItem() == "Apellido") {
			Orden = "fun_apellido";
		}
		if (cbOrden.getSelectedItem() == "Direccion") {
			Orden = "fun_direcc";
		}
		if (cbOrden.getSelectedItem() == "Cargo") {
			Orden = "fun_cargo";
		}
		if (cbOrden.getSelectedItem() == "Hora Entrada") {
			Orden = "frec_entrada";
		}
		if (cbOrden.getSelectedItem() == "Hora Salida") {
			Orden = "frec_salida";
		}
		ResultSet Funcionarios = utilidades.ejecutarSQLSelect("SELECT fun_codigo, fun_nombre, fun_apellido, fun_direcc, car_descri, frec_entrada, frec_salida FROM funcionario, frecuencia, cargo WHERE fun_frec = frec_codigo AND fun_cargo = car_codigo ORDER BY " + Orden);
		MostrarResultSetEnJtableFuncionarios(Funcionarios);
		scrollPane.setViewportView(table);
	}

	private static Object GenerarReporte() {
		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(table.getModel());
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = AdministrarUsuarios.class.getResourceAsStream("/reportes/reportFuncionario.jasper");
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
