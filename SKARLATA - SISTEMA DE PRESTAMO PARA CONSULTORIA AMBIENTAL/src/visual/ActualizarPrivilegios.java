package visual;

import java.awt.Component;
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
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import session.AdministrarFuncionariosSession;
import utilidades.JPanelBackground;
import utilidades.Utilidades;

import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JCheckBox;

public class ActualizarPrivilegios extends JDialog {

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

	private ActualizarPrivilegios administrarusuarios;
	private JPanel panel;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnGestionarFuncionario;
	private JCheckBox chbGestionarFuncionario;
	private JCheckBox chbNuevoFuncionario;
	private JCheckBox chbModificarFuncionario;
	private JCheckBox chbEliminarFuncionario;
	private JCheckBox chbInformeDeFuncionarios;
	private JPanel pnGestionarAsistencia;
	private JCheckBox chbGestionarAsistencia;
	private JCheckBox chbNuevoAsistencia;
	private JCheckBox chbModificarAsistencia;
	private JCheckBox chbEliminarAsistencia;
	private JCheckBox chbInformeDeAsistencias;
	private JPanel pnGestionarCliente;
	private JCheckBox chbGestionarCliente;
	private JCheckBox chbNuevoCliente;
	private JCheckBox chbModificarCliente;
	private JCheckBox chbEliminarCliente;
	private JCheckBox chbInformeDeClientes;
	private JPanel GestionarUsuario;
	private JCheckBox chbGestionarUsuario;
	private JCheckBox chbNuevoUsuario;
	private JCheckBox chbModificarUsuario;
	private JCheckBox chbEliminarUsuario;
	private JCheckBox chbInformeDeUsuarios;
	private JCheckBox chbGestionarProyecto;
	private JCheckBox chbNuevoProyecto;
	private JCheckBox chbModificarProyecto;
	private JCheckBox chbEliminarProyecto;
	private JCheckBox chbInformeDeProyectos;
	private Utilidades utilidades = new Utilidades();
	private JTextField tDescripcion;
	private JTextField tCodigo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActualizarPrivilegios frame = new ActualizarPrivilegios();
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
	public ActualizarPrivilegios() {
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
		setTitle("Privilegios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setSize(1324, 663); // Tamano de ventana
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 1306, 616);
		contentPane.add(panel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(255, 17, 374, 483);

		panel.setLayout(null);
		panel.add(scrollPane);

		table = new JTable();
		MostrarResultSetEnJtable(AdministrarFuncionariosSession.obtenerfuncionarios());
		scrollPane.setViewportView(table);

		JPanel pnBotones = new JPanel();
		pnBotones.setBackground(SystemColor.activeCaption);
		pnBotones.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBotones.setBounds(11, 17, 232, 352);
		panel.add(pnBotones);
		pnBotones.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCancelar.setEnabled(true);
				btnCancelar.doClick();
				HabilitarModoEdicion(true);
				SeleccionarCheckBoxs();

				try {
					ResultSet UltimoCodigo = utilidades.ejecutarSQLSelect("SELECT pri_codigo FROM privilegio WHERE pri_codigo <= @pri_codigo ORDER BY pri_codigo DESC limit 1");
					UltimoCodigo.next();
					tCodigo.setText((UltimoCodigo.getInt(1) + 1) + "");
				} catch (SQLException e1) {
					System.out.println("Error al buscar ultimo codigo" + e1);
					tCodigo.setText("1");
				}

			}
		});
		btnNuevo.setBounds(21, 37, 190, 68);
		pnBotones.add(btnNuevo);
		btnNuevo.setIcon(new ImageIcon(ActualizarPrivilegios.class.getResource("/icono/new-file-33984.png")));
		btnNuevo.setIconTextGap(5);
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HabilitarModoEdicion(true);
				chbGestionarProyecto.setEnabled(true);
				chbGestionarFuncionario.setEnabled(true);
				chbGestionarAsistencia.setEnabled(true);
				chbGestionarCliente.setEnabled(true);
				chbGestionarUsuario.setEnabled(true);

			}
		});
		btnModificar.setBounds(21, 142, 190, 68);
		pnBotones.add(btnModificar);
		btnModificar.setEnabled(false);
		btnModificar.setIcon(new ImageIcon(ActualizarPrivilegios.class.getResource("/icono/modificar-el-icono-del-documento-14646 copia.png")));
		btnModificar.setIconTextGap(5);
		btnModificar.setHorizontalAlignment(SwingConstants.LEADING);
		btnModificar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar el privilegio " + table.getValueAt(row, 1).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);

				if (opt == 0) {
					int CodigoRegistroSeleccionado = (int) table.getValueAt(row, 0);
					utilidades.ejecutarSQL("DELETE FROM privilegio WHERE pri_codigo = " + CodigoRegistroSeleccionado);
					JOptionPane.showMessageDialog(null, "Privilegio eliminado con exito");
				}
				ActualizarTabla();

			}
		});
		btnEliminar.setBounds(21, 247, 190, 68);
		pnBotones.add(btnEliminar);
		btnEliminar.setEnabled(false);
		btnEliminar.setIcon(new ImageIcon(ActualizarPrivilegios.class.getResource("/icono/document_delete_128 copia.png")));
		btnEliminar.setIconTextGap(5);
		btnEliminar.setHorizontalAlignment(SwingConstants.LEADING);
		btnEliminar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(206, 542, 168, 68);
		panel.add(btnGuardar);
		btnGuardar.setEnabled(false);
		btnGuardar.setIconTextGap(5);
		btnGuardar.setHorizontalAlignment(SwingConstants.LEADING);
		btnGuardar.setIcon(new ImageIcon(ActualizarPrivilegios.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = -1;
				int CodigoRegistroSeleccionado = 0;
				try {
					row = table.getSelectedRow();
					CodigoRegistroSeleccionado = (int) table.getValueAt(row, 0);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {

					ResultSet SiExisteRegistro = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio WHERE pri_codigo = " + CodigoRegistroSeleccionado);
					if (SiExisteRegistro.next() == true) {
						int opt = JOptionPane.showConfirmDialog(null, "Desea actualizar el privilegio " + table.getValueAt(row, 1).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);

						if (opt == 0) {
							utilidades.ejecutarSQL("UPDATE public.privilegio SET pri_codigo=" + Integer.parseInt(tCodigo.getText()) + ", pri_descri='" + tDescripcion.getText() + "', pri_proyecto=" + chbGestionarProyecto.isSelected()
									+ ", pri_proyectonuevo=" + chbNuevoProyecto.isSelected() + ", " + "pri_proyectomodificar=" + chbModificarProyecto.isSelected() + ", pri_proyectoeliminar=" + chbEliminarProyecto.isSelected()
									+ ", pri_proyectoinforme=" + chbInformeDeProyectos.isSelected() + ", pri_funcionario=" + chbGestionarFuncionario.isSelected() + ", pri_funcionarionuevo=" + chbNuevoFuncionario.isSelected() + ", "
									+ "pri_funcionariomodificar=" + chbModificarFuncionario.isSelected() + ", pri_funcionarioeliminar=" + chbEliminarFuncionario.isSelected() + ", pri_funcionarioinforme=" + chbInformeDeFuncionarios.isSelected()
									+ ", pri_asistencia=" + chbGestionarAsistencia.isSelected() + ", pri_asistencianuevo=" + chbNuevoAsistencia.isSelected() + ", " + "pri_asistenciamodificar=" + chbModificarAsistencia.isSelected()
									+ ", pri_asistenciaeliminar=" + chbEliminarAsistencia.isSelected() + ", pri_asistenciainforme=" + chbInformeDeAsistencias.isSelected() + ", pri_cliente=" + chbGestionarCliente.isSelected() + ", pri_clientenuevo="
									+ chbNuevoCliente.isSelected() + ", " + "pri_clientemodificar=" + chbModificarCliente.isSelected() + ", pri_clienteeliminar=" + chbEliminarCliente.isSelected() + ", pri_clienteinforme="
									+ chbInformeDeClientes.isSelected() + ", pri_usuario=" + chbGestionarUsuario.isSelected() + ", pri_usuarionuevo=" + chbNuevoUsuario.isSelected() + ", " + "pri_usuariomodificar=" + chbModificarUsuario.isSelected()
									+ ", pri_usuarioeliminar=" + chbEliminarUsuario.isSelected() + ", pri_usuarioinforme=" + chbInformeDeUsuarios.isSelected() + " WHERE pri_codigo = " + tCodigo.getText());
							JOptionPane.showMessageDialog(null, "Privilegio actualizado con exito");
						}
					} else {
						utilidades.ejecutarSQL("INSERT INTO public.privilegio(pri_codigo, pri_descri, pri_proyecto, pri_proyectonuevo, pri_proyectomodificar, "
								+ "pri_proyectoeliminar, pri_proyectoinforme, pri_funcionario, pri_funcionarionuevo, pri_funcionariomodificar, "
								+ "pri_funcionarioeliminar, pri_funcionarioinforme, pri_asistencia, pri_asistencianuevo, pri_asistenciamodificar, "
								+ "pri_asistenciaeliminar, pri_asistenciainforme, pri_cliente, pri_clientenuevo, pri_clientemodificar, "
								+ "pri_clienteeliminar, pri_clienteinforme, pri_usuario, pri_usuarionuevo, pri_usuariomodificar, pri_usuarioeliminar, " + "pri_usuarioinforme) " + "VALUES(" + Integer.parseInt(tCodigo.getText()) + ",'"
								+ tDescripcion.getText() + "'," + chbGestionarProyecto.isSelected() + "," + chbNuevoProyecto.isSelected() + "," + chbModificarProyecto.isSelected() + "," + chbEliminarProyecto.isSelected() + ","
								+ chbInformeDeProyectos.isSelected() + "," + chbGestionarFuncionario.isSelected() + "," + chbNuevoFuncionario.isSelected() + "," + chbModificarFuncionario.isSelected() + "," + chbEliminarFuncionario.isSelected() + ","
								+ chbInformeDeFuncionarios.isSelected() + "," + chbGestionarAsistencia.isSelected() + "," + chbNuevoAsistencia.isSelected() + "," + chbModificarAsistencia.isSelected() + "," + chbEliminarAsistencia.isSelected() + ","
								+ chbInformeDeAsistencias.isSelected() + "," + chbGestionarCliente.isSelected() + "," + chbNuevoCliente.isSelected() + "," + chbModificarCliente.isSelected() + "," + chbEliminarCliente.isSelected() + ","
								+ chbInformeDeClientes.isSelected() + "," + chbGestionarUsuario.isSelected() + "," + chbNuevoUsuario.isSelected() + "," + chbModificarUsuario.isSelected() + "," + chbEliminarUsuario.isSelected() + ","
								+ chbInformeDeUsuarios.isSelected() + ")");
						JOptionPane.showMessageDialog(null, "Privilegio insertado con exito");
					}
					SiExisteRegistro.close();
					ActualizarTabla();
					btnCancelar.doClick();
				} catch (SQLException e1) {
					System.out.println("Error al actualizar o insertar registro " + e1);
				}
			}
		});
		btnGuardar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(580, 542, 173, 68);
		panel.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tCodigo.setText("");
				tDescripcion.setText("");
				chbGestionarProyecto.setSelected(false);
				chbGestionarAsistencia.setSelected(false);
				chbGestionarCliente.setSelected(false);
				chbGestionarFuncionario.setSelected(false);
				chbGestionarUsuario.setSelected(false);
				HabilitarModoEdicion(false);
				SeleccionarCheckBoxs();
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
		btnCancelar.setEnabled(false);
		btnCancelar.setIcon(new ImageIcon(ActualizarPrivilegios.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(959, 542, 139, 68);
		panel.add(btnSalir);
		btnSalir.setIcon(new ImageIcon(ActualizarPrivilegios.class.getResource("/icono/Salir copia.png")));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Rockwell", Font.BOLD, 20));

		JPanel pnGestionarProyecto = new JPanel();
		pnGestionarProyecto.setLayout(null);
		pnGestionarProyecto.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnGestionarProyecto.setBackground(SystemColor.activeCaption);
		pnGestionarProyecto.setBounds(666, 88, 202, 200);
		panel.add(pnGestionarProyecto);

		chbGestionarProyecto = new JCheckBox("Gestionar Proyecto");
		chbGestionarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarCheckBoxs();
				SeleccionarCheckBoxs();
			}
		});
		chbGestionarProyecto.setEnabled(false);
		chbGestionarProyecto.setFont(new Font("SansSerif", Font.BOLD, 15));
		chbGestionarProyecto.setBounds(6, 6, 159, 18);
		pnGestionarProyecto.add(chbGestionarProyecto);

		chbNuevoProyecto = new JCheckBox("Nuevo Proyecto");
		chbNuevoProyecto.setEnabled(false);
		chbNuevoProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbNuevoProyecto.setBounds(6, 52, 174, 18);
		pnGestionarProyecto.add(chbNuevoProyecto);

		chbModificarProyecto = new JCheckBox("Modificar Proyecto");
		chbModificarProyecto.setEnabled(false);
		chbModificarProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbModificarProyecto.setBounds(6, 84, 174, 18);
		pnGestionarProyecto.add(chbModificarProyecto);

		chbEliminarProyecto = new JCheckBox("Eliminar Proyecto");
		chbEliminarProyecto.setEnabled(false);
		chbEliminarProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbEliminarProyecto.setBounds(6, 116, 174, 18);
		pnGestionarProyecto.add(chbEliminarProyecto);

		chbInformeDeProyectos = new JCheckBox("Informe de Proyectos");
		chbInformeDeProyectos.setEnabled(false);
		chbInformeDeProyectos.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbInformeDeProyectos.setBounds(6, 148, 174, 18);
		pnGestionarProyecto.add(chbInformeDeProyectos);

		pnGestionarFuncionario = new JPanel();
		pnGestionarFuncionario.setLayout(null);
		pnGestionarFuncionario.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnGestionarFuncionario.setBackground(SystemColor.activeCaption);
		pnGestionarFuncionario.setBounds(880, 88, 202, 200);
		panel.add(pnGestionarFuncionario);

		chbGestionarFuncionario = new JCheckBox("Gestionar Funcionario");
		chbGestionarFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarCheckBoxs();
				SeleccionarCheckBoxs();
			}
		});
		chbGestionarFuncionario.setEnabled(false);
		chbGestionarFuncionario.setFont(new Font("SansSerif", Font.BOLD, 15));
		chbGestionarFuncionario.setBounds(6, 6, 190, 18);
		pnGestionarFuncionario.add(chbGestionarFuncionario);

		chbNuevoFuncionario = new JCheckBox("Nuevo Funcionario");
		chbNuevoFuncionario.setEnabled(false);
		chbNuevoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbNuevoFuncionario.setBounds(6, 52, 174, 18);
		pnGestionarFuncionario.add(chbNuevoFuncionario);

		chbModificarFuncionario = new JCheckBox("Modificar Funcionario");
		chbModificarFuncionario.setEnabled(false);
		chbModificarFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbModificarFuncionario.setBounds(6, 84, 174, 18);
		pnGestionarFuncionario.add(chbModificarFuncionario);

		chbEliminarFuncionario = new JCheckBox("Eliminar Funcionario");
		chbEliminarFuncionario.setEnabled(false);
		chbEliminarFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbEliminarFuncionario.setBounds(6, 116, 174, 18);
		pnGestionarFuncionario.add(chbEliminarFuncionario);

		chbInformeDeFuncionarios = new JCheckBox("Informe de Funcionarios");
		chbInformeDeFuncionarios.setEnabled(false);
		chbInformeDeFuncionarios.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbInformeDeFuncionarios.setBounds(6, 148, 190, 18);
		pnGestionarFuncionario.add(chbInformeDeFuncionarios);

		pnGestionarAsistencia = new JPanel();
		pnGestionarAsistencia.setLayout(null);
		pnGestionarAsistencia.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnGestionarAsistencia.setBackground(SystemColor.activeCaption);
		pnGestionarAsistencia.setBounds(1094, 88, 202, 200);
		panel.add(pnGestionarAsistencia);

		chbGestionarAsistencia = new JCheckBox("Gestionar Asistencia");
		chbGestionarAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarCheckBoxs();
				SeleccionarCheckBoxs();
			}
		});
		chbGestionarAsistencia.setEnabled(false);
		chbGestionarAsistencia.setFont(new Font("SansSerif", Font.BOLD, 15));
		chbGestionarAsistencia.setBounds(6, 6, 190, 18);
		pnGestionarAsistencia.add(chbGestionarAsistencia);

		chbNuevoAsistencia = new JCheckBox("Nuevo Asistencia");
		chbNuevoAsistencia.setEnabled(false);
		chbNuevoAsistencia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbNuevoAsistencia.setBounds(6, 52, 174, 18);
		pnGestionarAsistencia.add(chbNuevoAsistencia);

		chbModificarAsistencia = new JCheckBox("Modificar Asistencia");
		chbModificarAsistencia.setEnabled(false);
		chbModificarAsistencia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbModificarAsistencia.setBounds(6, 84, 174, 18);
		pnGestionarAsistencia.add(chbModificarAsistencia);

		chbEliminarAsistencia = new JCheckBox("Eliminar Asistencia");
		chbEliminarAsistencia.setEnabled(false);
		chbEliminarAsistencia.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbEliminarAsistencia.setBounds(6, 116, 174, 18);
		pnGestionarAsistencia.add(chbEliminarAsistencia);

		chbInformeDeAsistencias = new JCheckBox("Informe de Asistencias");
		chbInformeDeAsistencias.setEnabled(false);
		chbInformeDeAsistencias.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbInformeDeAsistencias.setBounds(6, 148, 190, 18);
		pnGestionarAsistencia.add(chbInformeDeAsistencias);

		pnGestionarCliente = new JPanel();
		pnGestionarCliente.setLayout(null);
		pnGestionarCliente.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnGestionarCliente.setBackground(SystemColor.activeCaption);
		pnGestionarCliente.setBounds(666, 300, 202, 200);
		panel.add(pnGestionarCliente);

		chbGestionarCliente = new JCheckBox("Gestionar Cliente");
		chbGestionarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarCheckBoxs();
				SeleccionarCheckBoxs();
			}
		});
		chbGestionarCliente.setEnabled(false);
		chbGestionarCliente.setFont(new Font("SansSerif", Font.BOLD, 15));
		chbGestionarCliente.setBounds(6, 6, 190, 18);
		pnGestionarCliente.add(chbGestionarCliente);

		chbNuevoCliente = new JCheckBox("Nuevo Cliente");
		chbNuevoCliente.setEnabled(false);
		chbNuevoCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbNuevoCliente.setBounds(6, 52, 174, 18);
		pnGestionarCliente.add(chbNuevoCliente);

		chbModificarCliente = new JCheckBox("Modificar Cliente");
		chbModificarCliente.setEnabled(false);
		chbModificarCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbModificarCliente.setBounds(6, 84, 174, 18);
		pnGestionarCliente.add(chbModificarCliente);

		chbEliminarCliente = new JCheckBox("Eliminar Cliente");
		chbEliminarCliente.setEnabled(false);
		chbEliminarCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbEliminarCliente.setBounds(6, 116, 174, 18);
		pnGestionarCliente.add(chbEliminarCliente);

		chbInformeDeClientes = new JCheckBox("Informe de Clientes");
		chbInformeDeClientes.setEnabled(false);
		chbInformeDeClientes.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbInformeDeClientes.setBounds(6, 148, 190, 18);
		pnGestionarCliente.add(chbInformeDeClientes);

		GestionarUsuario = new JPanel();
		GestionarUsuario.setLayout(null);
		GestionarUsuario.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GestionarUsuario.setBackground(SystemColor.activeCaption);
		GestionarUsuario.setBounds(880, 300, 202, 200);
		panel.add(GestionarUsuario);

		chbGestionarUsuario = new JCheckBox("Gestionar Usuario");
		chbGestionarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VerificarCheckBoxs();
				SeleccionarCheckBoxs();
			}
		});
		chbGestionarUsuario.setEnabled(false);
		chbGestionarUsuario.setFont(new Font("SansSerif", Font.BOLD, 15));
		chbGestionarUsuario.setBounds(6, 6, 190, 18);
		GestionarUsuario.add(chbGestionarUsuario);

		chbNuevoUsuario = new JCheckBox("Nuevo Usuario");
		chbNuevoUsuario.setEnabled(false);
		chbNuevoUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbNuevoUsuario.setBounds(6, 52, 174, 18);
		GestionarUsuario.add(chbNuevoUsuario);

		chbModificarUsuario = new JCheckBox("Modificar Usuario");
		chbModificarUsuario.setEnabled(false);
		chbModificarUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbModificarUsuario.setBounds(6, 84, 174, 18);
		GestionarUsuario.add(chbModificarUsuario);

		chbEliminarUsuario = new JCheckBox("Eliminar Usuario");
		chbEliminarUsuario.setEnabled(false);
		chbEliminarUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbEliminarUsuario.setBounds(6, 116, 174, 18);
		GestionarUsuario.add(chbEliminarUsuario);

		chbInformeDeUsuarios = new JCheckBox("Informe de Usuarios");
		chbInformeDeUsuarios.setEnabled(false);
		chbInformeDeUsuarios.setFont(new Font("SansSerif", Font.PLAIN, 14));
		chbInformeDeUsuarios.setBounds(6, 148, 190, 18);
		GestionarUsuario.add(chbInformeDeUsuarios);

		tDescripcion = new JTextField();
		tDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {

				Character s = arg0.getKeyChar();
				if (Character.isLetter(s)) {
					tDescripcion.setText(tDescripcion.getText().toUpperCase());

				}
			}
		});
		tDescripcion.setEnabled(false);
		tDescripcion.setBounds(974, 43, 322, 33);
		panel.add(tDescripcion);
		tDescripcion.setColumns(10);

		tCodigo = new JTextField();
		tCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		tCodigo.setEnabled(false);
		tCodigo.setColumns(10);
		tCodigo.setBounds(736, 43, 79, 33);
		panel.add(tCodigo);

		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel.setBounds(654, 43, 77, 33);
		panel.add(lblNewLabel);

		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescripcion.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblDescripcion.setBounds(865, 43, 97, 33);
		panel.add(lblDescripcion);

		ActualizarTabla();

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
					if (table.isEnabled() == true) {
						if (table.getSelectedRow() != -1) {
							btnModificar.setEnabled(true);
							btnEliminar.setEnabled(true);
						} else {
							btnModificar.setEnabled(false);
							btnEliminar.setEnabled(false);
						}
						MostrarEnCheckBox();
					}

				}
			});
			table.setFont(new Font("SansSerif", Font.PLAIN, 15));

			table.getTableHeader().setReorderingAllowed(false); // Para que no se pueda mover columnas
			table.getTableHeader().setResizingAllowed(false); // Para que no se puede cambiar tamaño de columnas
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Para que solo se pueda seleccionar un registro a la vez

			// Creamos las columnas.
			modelo.addColumn("Codigo");
			modelo.addColumn("Descripcion");

			// TAMANO DE CADA COLUMNA
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(65);
			table.getColumnModel().getColumn(1).setPreferredWidth(300);

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
		tDescripcion.setEnabled(habilitar);
		chbGestionarProyecto.setEnabled(habilitar);
		chbGestionarAsistencia.setEnabled(habilitar);
		chbGestionarCliente.setEnabled(habilitar);
		chbGestionarFuncionario.setEnabled(habilitar);
		chbGestionarUsuario.setEnabled(habilitar);

		btnGuardar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnNuevo.setEnabled(!habilitar);
		btnModificar.setEnabled(!habilitar);
		btnEliminar.setEnabled(!habilitar);
		VerificarCheckBoxs();
		table.setEnabled(!habilitar);
		if (habilitar == true) {
			table.setBackground(Color.LIGHT_GRAY);
			table.setForeground(Color.DARK_GRAY);
		} else {
			table.setBackground(Color.WHITE);
			table.setForeground(Color.BLACK);
		}
	}

	private void ActualizarTabla() {
		MostrarResultSetEnJtable(utilidades.ejecutarSQLSelect("SELECT pri_codigo, pri_descri FROM privilegio"));
		scrollPane.setViewportView(table);
	}

	private void MostrarEnCheckBox() {

		int row = table.getSelectedRow();
		int CodigoRegistroSeleccionado = (int) table.getValueAt(row, 0);
		ResultSet RegistroSeleccionado = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio WHERE pri_codigo = " + CodigoRegistroSeleccionado);
		try {
			RegistroSeleccionado.next();
			tCodigo.setText(RegistroSeleccionado.getString(1));
			tDescripcion.setText(RegistroSeleccionado.getString(2));
			chbGestionarProyecto.setSelected(RegistroSeleccionado.getBoolean(3));
			chbNuevoProyecto.setSelected(RegistroSeleccionado.getBoolean(4));
			chbModificarProyecto.setSelected(RegistroSeleccionado.getBoolean(5));
			chbEliminarProyecto.setSelected(RegistroSeleccionado.getBoolean(6));
			chbInformeDeProyectos.setSelected(RegistroSeleccionado.getBoolean(7));
			chbGestionarFuncionario.setSelected(RegistroSeleccionado.getBoolean(8));
			chbNuevoFuncionario.setSelected(RegistroSeleccionado.getBoolean(9));
			chbModificarFuncionario.setSelected(RegistroSeleccionado.getBoolean(10));
			chbEliminarFuncionario.setSelected(RegistroSeleccionado.getBoolean(11));
			chbInformeDeFuncionarios.setSelected(RegistroSeleccionado.getBoolean(12));
			chbGestionarAsistencia.setSelected(RegistroSeleccionado.getBoolean(13));
			chbNuevoAsistencia.setSelected(RegistroSeleccionado.getBoolean(14));
			chbModificarAsistencia.setSelected(RegistroSeleccionado.getBoolean(15));
			chbEliminarAsistencia.setSelected(RegistroSeleccionado.getBoolean(16));
			chbInformeDeAsistencias.setSelected(RegistroSeleccionado.getBoolean(17));
			chbGestionarCliente.setSelected(RegistroSeleccionado.getBoolean(18));
			chbNuevoCliente.setSelected(RegistroSeleccionado.getBoolean(19));
			chbModificarCliente.setSelected(RegistroSeleccionado.getBoolean(20));
			chbEliminarCliente.setSelected(RegistroSeleccionado.getBoolean(21));
			chbInformeDeClientes.setSelected(RegistroSeleccionado.getBoolean(22));
			chbGestionarUsuario.setSelected(RegistroSeleccionado.getBoolean(23));
			chbNuevoUsuario.setSelected(RegistroSeleccionado.getBoolean(24));
			chbModificarUsuario.setSelected(RegistroSeleccionado.getBoolean(25));
			chbEliminarUsuario.setSelected(RegistroSeleccionado.getBoolean(26));
			chbInformeDeUsuarios.setSelected(RegistroSeleccionado.getBoolean(27));
			RegistroSeleccionado.close();
		} catch (SQLException e1) {
			// TODO Bloque catch generado automáticamente
			System.out.println("Error al cargar booleanos" + e1);
		}
	}

	private void VerificarCheckBoxs() {
		if (chbGestionarProyecto.isSelected()) {
			chbNuevoProyecto.setEnabled(true);
			chbModificarProyecto.setEnabled(true);
			chbEliminarProyecto.setEnabled(true);
			chbInformeDeProyectos.setEnabled(true);
		} else {
			chbNuevoProyecto.setEnabled(false);
			chbModificarProyecto.setEnabled(false);
			chbEliminarProyecto.setEnabled(false);
			chbInformeDeProyectos.setEnabled(false);
		}

		if (chbGestionarFuncionario.isSelected()) {
			chbNuevoFuncionario.setEnabled(true);
			chbModificarFuncionario.setEnabled(true);
			chbEliminarFuncionario.setEnabled(true);
			chbInformeDeFuncionarios.setEnabled(true);
		} else {
			chbNuevoFuncionario.setEnabled(false);
			chbModificarFuncionario.setEnabled(false);
			chbEliminarFuncionario.setEnabled(false);
			chbInformeDeFuncionarios.setEnabled(false);
		}

		if (chbGestionarAsistencia.isSelected()) {
			chbNuevoAsistencia.setEnabled(true);
			chbModificarAsistencia.setEnabled(true);
			chbEliminarAsistencia.setEnabled(true);
			chbInformeDeAsistencias.setEnabled(true);

		} else {
			chbNuevoAsistencia.setEnabled(false);
			chbModificarAsistencia.setEnabled(false);
			chbEliminarAsistencia.setEnabled(false);
			chbInformeDeAsistencias.setEnabled(false);
		}

		if (chbGestionarCliente.isSelected()) {
			chbNuevoCliente.setEnabled(true);
			chbModificarCliente.setEnabled(true);
			chbEliminarCliente.setEnabled(true);
			chbInformeDeClientes.setEnabled(true);
		} else {
			chbNuevoCliente.setEnabled(false);
			chbModificarCliente.setEnabled(false);
			chbEliminarCliente.setEnabled(false);
			chbInformeDeClientes.setEnabled(false);
		}

		if (chbGestionarUsuario.isSelected()) {
			chbNuevoUsuario.setEnabled(true);
			chbModificarUsuario.setEnabled(true);
			chbEliminarUsuario.setEnabled(true);
			chbInformeDeUsuarios.setEnabled(true);
		} else {
			chbNuevoUsuario.setEnabled(false);
			chbModificarUsuario.setEnabled(false);
			chbEliminarUsuario.setEnabled(false);
			chbInformeDeUsuarios.setEnabled(false);
		}

	}

	private void SeleccionarCheckBoxs() {
		if (chbGestionarProyecto.isSelected()) {
			chbNuevoProyecto.setSelected(true);
			chbModificarProyecto.setSelected(true);
			chbEliminarProyecto.setSelected(true);
			chbInformeDeProyectos.setSelected(true);
		} else {
			chbNuevoProyecto.setSelected(false);
			chbModificarProyecto.setSelected(false);
			chbEliminarProyecto.setSelected(false);
			chbInformeDeProyectos.setSelected(false);
		}

		if (chbGestionarFuncionario.isSelected()) {
			chbNuevoFuncionario.setSelected(true);
			chbModificarFuncionario.setSelected(true);
			chbEliminarFuncionario.setSelected(true);
			chbInformeDeFuncionarios.setSelected(true);
		} else {
			chbNuevoFuncionario.setSelected(false);
			chbModificarFuncionario.setSelected(false);
			chbEliminarFuncionario.setSelected(false);
			chbInformeDeFuncionarios.setSelected(false);
		}

		if (chbGestionarAsistencia.isSelected()) {
			chbNuevoAsistencia.setSelected(true);
			chbModificarAsistencia.setSelected(true);
			chbEliminarAsistencia.setSelected(true);
			chbInformeDeAsistencias.setSelected(true);

		} else {
			chbNuevoAsistencia.setSelected(false);
			chbModificarAsistencia.setSelected(false);
			chbEliminarAsistencia.setSelected(false);
			chbInformeDeAsistencias.setSelected(false);
		}

		if (chbGestionarCliente.isSelected()) {
			chbNuevoCliente.setSelected(true);
			chbModificarCliente.setSelected(true);
			chbEliminarCliente.setSelected(true);
			chbInformeDeClientes.setSelected(true);
		} else {
			chbNuevoCliente.setSelected(false);
			chbModificarCliente.setSelected(false);
			chbEliminarCliente.setSelected(false);
			chbInformeDeClientes.setSelected(false);
		}

		if (chbGestionarUsuario.isSelected()) {
			chbNuevoUsuario.setSelected(true);
			chbModificarUsuario.setSelected(true);
			chbEliminarUsuario.setSelected(true);
			chbInformeDeUsuarios.setSelected(true);
		} else {
			chbNuevoUsuario.setSelected(false);
			chbModificarUsuario.setSelected(false);
			chbEliminarUsuario.setSelected(false);
			chbInformeDeUsuarios.setSelected(false);
		}

	}
}
