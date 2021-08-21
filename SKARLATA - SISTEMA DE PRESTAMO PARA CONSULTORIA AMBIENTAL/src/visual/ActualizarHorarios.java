package visual;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import utilidades.Utilidades;

public class ActualizarHorarios extends JDialog {

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

	private ActualizarHorarios administrarusuarios;
	private JPanel panel;
	private JButton btnNuevo;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JLabel lblEntrada;
	private JLabel lblApellido;
	private JTable table;
	private JPanel pnDatosCliente;
	private JScrollPane scrollPane;
	private JTextField tCodigo;
	private JSpinner spHoraEntrada;
	private JSpinner spHoraSalida;
	private Utilidades utilidades = new Utilidades();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActualizarHorarios frame = new ActualizarHorarios();
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
	public ActualizarHorarios() {

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
		setTitle("Horarios de trabajo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setSize(990, 441); // Tamano de ventana
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 0, 972, 394);
		contentPane.add(panel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(261, 23, 325, 246);

		panel.setLayout(null);
		panel.add(scrollPane);

		table = new JTable();
		ActualizarTabla();
		scrollPane.setViewportView(table);

		JPanel pnBotones = new JPanel();
		pnBotones.setBackground(SystemColor.activeCaption);
		pnBotones.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBotones.setBounds(17, 23, 232, 246);
		panel.add(pnBotones);
		pnBotones.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HabilitarModoEdicion(true);
				try {
					ResultSet ultimocodigo = utilidades.ejecutarSQLSelect("SELECT frec_codigo FROM frecuencia WHERE frec_codigo <= @frec_codigo ORDER BY frec_codigo DESC limit 1");
					ultimocodigo.next();
					tCodigo.setText(((ultimocodigo.getInt(1) + 1) + ""));
					ultimocodigo.close();
				} catch (SQLException e1) {
					System.out.println("Error al buscar ultimo codigo " + e1);
				}
			}
		});
		btnNuevo.setBounds(21, 10, 190, 68);
		pnBotones.add(btnNuevo);
		btnNuevo.setIcon(new ImageIcon(ActualizarHorarios.class.getResource("/icono/new-file-33984.png")));
		btnNuevo.setIconTextGap(5);
		btnNuevo.setHorizontalAlignment(SwingConstants.LEFT);
		btnNuevo.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HabilitarModoEdicion(true);
				MostrarDatosEnTextField();
			}
		});
		btnModificar.setBounds(21, 88, 190, 68);
		pnBotones.add(btnModificar);
		btnModificar.setEnabled(false);
		btnModificar.setIcon(new ImageIcon(ActualizarHorarios.class.getResource("/icono/modificar-el-icono-del-documento-14646 copia.png")));
		btnModificar.setIconTextGap(5);
		btnModificar.setHorizontalAlignment(SwingConstants.LEADING);
		btnModificar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar el horario seleccionado?", "Confirmacion", JOptionPane.YES_NO_OPTION);

				if (opt == 0) {
					int CodigoRegistroSeleccionado = (int) table.getValueAt(row, 0);
					utilidades.ejecutarSQL("DELETE FROM frecuencia WHERE frec_codigo = " + CodigoRegistroSeleccionado);
					JOptionPane.showMessageDialog(null, "Horario eliminado con exito");
				}
				ActualizarTabla();

			}

		});
		btnEliminar.setBounds(21, 166, 190, 68);
		pnBotones.add(btnEliminar);
		btnEliminar.setEnabled(false);
		btnEliminar.setIcon(new ImageIcon(ActualizarHorarios.class.getResource("/icono/document_delete_128 copia.png")));
		btnEliminar.setIconTextGap(5);
		btnEliminar.setHorizontalAlignment(SwingConstants.LEADING);
		btnEliminar.setFont(new Font("Rockwell", Font.BOLD, 20));

		pnDatosCliente = new JPanel();
		pnDatosCliente.setBackground(SystemColor.activeCaption);
		pnDatosCliente.setLayout(null);
		pnDatosCliente.setBorder(new TitledBorder(null, "Actualizar Horario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatosCliente.setBounds(598, 27, 355, 246);
		panel.add(pnDatosCliente);

		lblEntrada = new JLabel("Entrada");
		lblEntrada.setBounds(25, 89, 73, 33);
		pnDatosCliente.add(lblEntrada);
		lblEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEntrada.setFont(new Font("Tahoma", Font.BOLD, 17));

		lblApellido = new JLabel("Salida");
		lblApellido.setBounds(6, 150, 92, 33);
		pnDatosCliente.add(lblApellido);
		lblApellido.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellido.setFont(new Font("Tahoma", Font.BOLD, 17));

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCodigo.setBounds(25, 28, 73, 33);
		pnDatosCliente.add(lblCodigo);

		tCodigo = new JTextField();
		tCodigo.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tCodigo.setEnabled(false);
		tCodigo.setColumns(10);
		tCodigo.setBounds(110, 28, 53, 33);
		pnDatosCliente.add(tCodigo);

		spHoraSalida = new JSpinner((SpinnerModel) new SpinnerDateModel(new Date(1467172800267L), null, null, Calendar.DAY_OF_MONTH));

		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spHoraSalida, "HH:mm:ss");
		spHoraSalida.setEditor(timeEditor);
		spHoraSalida.setFont(new Font("SansSerif", Font.PLAIN, 17));
		spHoraSalida.setEnabled(false);
		spHoraSalida.setBounds(110, 150, 103, 33);
		pnDatosCliente.add(spHoraSalida);

		spHoraEntrada = new JSpinner((SpinnerModel) new SpinnerDateModel(new Date(1467172800267L), null, null, Calendar.DAY_OF_MONTH));
		JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(spHoraEntrada, "HH:mm:ss");
		spHoraEntrada.setEditor(timeEditor2);
		spHoraEntrada.setFont(new Font("SansSerif", Font.PLAIN, 17));
		spHoraEntrada.setEnabled(false);
		spHoraEntrada.setBounds(110, 89, 103, 33);
		pnDatosCliente.add(spHoraEntrada);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(710, 311, 139, 68);
		panel.add(btnSalir);
		btnSalir.setIcon(new ImageIcon(ActualizarHorarios.class.getResource("/icono/Salir copia.png")));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(414, 311, 173, 68);
		panel.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				HabilitarModoEdicion(false);
			}
		});
		btnCancelar.setEnabled(false);
		btnCancelar.setIcon(new ImageIcon(ActualizarHorarios.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Rockwell", Font.BOLD, 20));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(123, 311, 168, 68);
		panel.add(btnGuardar);
		btnGuardar.setEnabled(false);
		btnGuardar.setIconTextGap(5);
		btnGuardar.setHorizontalAlignment(SwingConstants.LEADING);
		btnGuardar.setIcon(new ImageIcon(ActualizarHorarios.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int CodigoRegistroSeleccionado = Integer.parseInt(tCodigo.getText());
					SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
					ResultSet SiExisteRegistro = utilidades.ejecutarSQLSelect("SELECT * FROM frecuencia WHERE frec_codigo = " + CodigoRegistroSeleccionado);
					if (SiExisteRegistro.next() == true) {
						int opt = JOptionPane.showConfirmDialog(null, "Desea actualizar el horario seleccionado?", "Confirmacion", JOptionPane.YES_NO_OPTION);

						if (opt == 0) {
							utilidades.ejecutarSQL("UPDATE public.frecuencia SET frec_codigo=" + tCodigo.getText() + ", frec_entrada='" + Time.valueOf(formatohora.format(spHoraEntrada.getValue())) + "', frec_salida='"
									+ Time.valueOf(formatohora.format(spHoraSalida.getValue())) + "' WHERE frec_codigo = " + tCodigo.getText());
							JOptionPane.showMessageDialog(null, "Horario actualizado con exito");
						}

					} else {
						utilidades.ejecutarSQL("INSERT INTO public.frecuencia(frec_codigo, frec_entrada, frec_salida) VALUES (" + tCodigo.getText() + ",'" + Time.valueOf(formatohora.format(spHoraEntrada.getValue())) + "','"
								+ Time.valueOf(formatohora.format(spHoraSalida.getValue())) + "')");
						JOptionPane.showMessageDialog(null, "Horario insertado con exito");
						;
					}
					SiExisteRegistro.close();
				} catch (SQLException e1) {
					System.out.println("Error al actualizar o insertar registro " + e1);
				}
				HabilitarModoEdicion(false);

				ActualizarTabla();
			}
		});
		btnGuardar.setFont(new Font("Rockwell", Font.BOLD, 20));
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
					}

				}
			});
			table.setFont(new Font("SansSerif", Font.PLAIN, 15));

			table.getTableHeader().setReorderingAllowed(false); // Para que no se pueda mover columnas
			table.getTableHeader().setResizingAllowed(false); // Para que no se puede cambiar tamaño de columnas
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Para que solo se pueda seleccionar un registro a la vez

			// Creamos las columnas.
			modelo.addColumn("Codigo");
			modelo.addColumn("Entrada");
			modelo.addColumn("Salida");

			// TAMANO DE CADA COLUMNA
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(65);
			table.getColumnModel().getColumn(1).setPreferredWidth(126);
			table.getColumnModel().getColumn(2).setPreferredWidth(126);

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
		btnGuardar.setEnabled(habilitar);
		btnCancelar.setEnabled(habilitar);
		btnNuevo.setEnabled(!habilitar);
		btnModificar.setEnabled(!habilitar);
		btnEliminar.setEnabled(!habilitar);
		tCodigo.setText("");
		spHoraEntrada.setValue(new Date(1467172800820L));
		spHoraEntrada.setEnabled(habilitar);
		spHoraSalida.setEnabled(habilitar);
		spHoraSalida.setValue(new Date(1467172800820L));

		if (table.getSelectedRow() == -1) {
			btnModificar.setEnabled(false);
			btnEliminar.setEnabled(false);
		}

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
		ResultSet horarios = utilidades.ejecutarSQLSelect("SELECT * FROM frecuencia ORDER BY frec_codigo");
		try {
			MostrarResultSetEnJtable(horarios);
			scrollPane.setViewportView(table);
			horarios.close();
		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	private void MostrarDatosEnTextField() {
		int row = table.getSelectedRow();
		tCodigo.setText(table.getValueAt(row, 0) + "");
		spHoraEntrada.setValue(table.getValueAt(row, 1));
		spHoraSalida.setValue(table.getValueAt(row, 2));
	}
}
