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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import session.ControlarAsistenciaSession;
import utilidades.FormatearJtable;
import utilidades.Utilidades;
import javax.swing.border.MatteBorder;

public class ControlarAsistencia extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1936636351999089124L;
	private JPanel contentPane;
	private JButton btnEntrada;
	private JButton btnModificar;
	private JButton btnEliminar;
	private JButton btnSalir;
	private JSpinner spAño;
	private JButton btnCancelar;
	private JButton btnGuardar;
	private static JComboBox cbMes;
	private static Utilidades utilidades = new Utilidades();
	private JTextField tBusquedaAsistencia;
	private JLabel lblBusqueda;
	private JComboBox comboFiltroAsistencia;
	private TableRowSorter trsfiltroAsistencia;
	private TableRowSorter trsfiltroFuncionario;
	public static ControlarAsistencia abmlibros;
	private JButton btnInforme;
	public static JTable tableAsistencias;
	private JTextField tCodigo;
	private JTextField tCargoFuncionario;
	private JTextField tNombreUsuario;
	private JTextField tCodigoUsuario;
	private JSpinner spHoraEntrada;
	private JSpinner spHoraSalida;
	private JSpinner spHorasExtras;
	private JPanel pnMovimientos;
	private JTable tableFuncionarios;
	private JScrollPane scrollPaneAsistencias;
	private JTextField tNombreFuncionario;
	private JTextField tApellidoFuncionario;
	private JTextField tHoraEntradaFuncionario;
	private JTextField tHoraSalidaFuncionario;
	private JLabel lblObservaciones;
	private JLabel lblAo;
	private JTextField tBusquedaFuncionario;
	private JComboBox comboFiltroFuncionario;
	private JLabel label;
	private JComboBox cbObservaciones;
	ResultSet funcionario = null;
	private JDateChooser dcFecha;
	private JSpinner spDia;
	public static JTextField tCodigoFuncionario;

	private JButton btnSalida;
	private JSpinner spHorasDesc;
	private static DefaultTableModel ModeloAsistencia;
	private JButton btnLiquidacionSalarial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlarAsistencia frame = new ControlarAsistencia();
					frame.setVisible(true);
					abmlibros = frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ControlarAsistencia() {
		setUndecorated(true);
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
		setTitle("Controlar Asistencia");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int height = pantalla.height; // Tamano total vertical
		int width = pantalla.width;// Tamano total horizontal
		setLocation(0, 155);
		setSize(width, 688);

		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pnActualizarAsistencia = new JPanel();
		pnActualizarAsistencia.setToolTipText("");
		pnActualizarAsistencia.setBackground(SystemColor.activeCaption);
		pnActualizarAsistencia.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Asistencia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnActualizarAsistencia.setBounds(798, 16, 857, 256);
		contentPane.add(pnActualizarAsistencia);
		pnActualizarAsistencia.setLayout(null);

		tCodigo = new JTextField();
		tCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
		tCodigo.setBounds(148, 11, 56, 30);
		pnActualizarAsistencia.add(tCodigo);
		tCodigo.setEnabled(false);
		tCodigo.setColumns(10);

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setForeground(SystemColor.desktop);
		lblCodigo.setBounds(25, 11, 134, 30);
		pnActualizarAsistencia.add(lblCodigo);
		lblCodigo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblCodigo.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setForeground(SystemColor.desktop);
		lblUsuario.setBounds(319, 11, 64, 30);
		pnActualizarAsistencia.add(lblUsuario);
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

		tNombreUsuario = new JTextField();
		tNombreUsuario.setBounds(515, 11, 338, 30);
		pnActualizarAsistencia.add(tNombreUsuario);
		tNombreUsuario.setEnabled(false);
		tNombreUsuario.setEditable(false);
		tNombreUsuario.setColumns(10);

		tCodigoUsuario = new JTextField();
		tCodigoUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		tCodigoUsuario.setBounds(458, 11, 56, 30);
		pnActualizarAsistencia.add(tCodigoUsuario);
		tCodigoUsuario.setEnabled(false);
		tCodigoUsuario.setColumns(10);

		JLabel lblHorasExtras = new JLabel("Horas Extras");
		lblHorasExtras.setForeground(SystemColor.desktop);
		lblHorasExtras.setBounds(319, 52, 92, 30);
		pnActualizarAsistencia.add(lblHorasExtras);
		lblHorasExtras.setHorizontalAlignment(SwingConstants.LEFT);
		lblHorasExtras.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

		spHorasExtras = new JSpinner(new SpinnerDateModel(new Date(1467172800267L), null, null, Calendar.DAY_OF_MONTH));

		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spHorasExtras, "HH:mm:ss");
		spHorasExtras.setEditor(timeEditor);
		spHorasExtras.setBounds(458, 52, 88, 30);
		pnActualizarAsistencia.add(spHorasExtras);
		spHorasExtras.setEnabled(false);
		spHorasExtras.setFont(new Font("SansSerif", Font.PLAIN, 15));

		JLabel lblDa = new JLabel("D\u00EDa");
		lblDa.setHorizontalAlignment(SwingConstants.LEFT);
		lblDa.setForeground(Color.BLACK);
		lblDa.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblDa.setBounds(25, 52, 92, 30);
		pnActualizarAsistencia.add(lblDa);

		spDia = new JSpinner();
		spDia.setFont(new Font("SansSerif", Font.PLAIN, 15));
		spDia.setEnabled(false);
		spDia.setBounds(148, 52, 56, 30);
		pnActualizarAsistencia.add(spDia);

		JLabel lblHoraDeEntrada = new JLabel("Hora de Entrada");
		lblHoraDeEntrada.setHorizontalAlignment(SwingConstants.LEFT);
		lblHoraDeEntrada.setForeground(Color.BLACK);
		lblHoraDeEntrada.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblHoraDeEntrada.setBounds(25, 134, 115, 30);
		pnActualizarAsistencia.add(lblHoraDeEntrada);

		JLabel lblHoraDeSalida = new JLabel("Hora de Salida");
		lblHoraDeSalida.setHorizontalAlignment(SwingConstants.LEFT);
		lblHoraDeSalida.setForeground(Color.BLACK);
		lblHoraDeSalida.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblHoraDeSalida.setBounds(25, 175, 116, 30);
		pnActualizarAsistencia.add(lblHoraDeSalida);

		JLabel lblHorasDescontadas = new JLabel("Horas Descontadas");
		lblHorasDescontadas.setHorizontalTextPosition(SwingConstants.CENTER);
		lblHorasDescontadas.setHorizontalAlignment(SwingConstants.LEFT);
		lblHorasDescontadas.setForeground(Color.BLACK);
		lblHorasDescontadas.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblHorasDescontadas.setBounds(319, 93, 144, 30);
		pnActualizarAsistencia.add(lblHorasDescontadas);

		lblObservaciones = new JLabel("Observaciones");
		lblObservaciones.setHorizontalAlignment(SwingConstants.LEFT);
		lblObservaciones.setForeground(Color.BLACK);
		lblObservaciones.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblObservaciones.setBounds(319, 134, 115, 30);
		pnActualizarAsistencia.add(lblObservaciones);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(540, 190, 144, 60);
		pnActualizarAsistencia.add(btnGuardar);
		btnGuardar.setEnabled(false);
		btnGuardar.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/diskette_save_saveas_1514_opt (2).png")));
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 14));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(709, 190, 144, 60);
		pnActualizarAsistencia.add(btnCancelar);
		btnCancelar.setEnabled(false);
		btnCancelar.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/Cancelar.png")));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));

		cbObservaciones = new JComboBox();
		cbObservaciones.setMaximumRowCount(20);
		cbObservaciones.setEnabled(false);
		cbObservaciones.setBounds(458, 131, 229, 33);
		pnActualizarAsistencia.add(cbObservaciones);

		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setHorizontalAlignment(SwingConstants.LEFT);
		lblFecha.setForeground(Color.BLACK);
		lblFecha.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblFecha.setBounds(25, 93, 92, 30);
		pnActualizarAsistencia.add(lblFecha);

		dcFecha = new JDateChooser();
		dcFecha.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				try {
					SimpleDateFormat formatodia = new SimpleDateFormat("dd");
					spDia.setValue(Integer.parseInt(formatodia.format(dcFecha.getDate())));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});

		dcFecha.setEnabled(false);
		dcFecha.setBounds(148, 93, 125, 30);
		pnActualizarAsistencia.add(dcFecha);

		spHorasDesc = new JSpinner(new SpinnerDateModel(new Date(1467172800820L), null, null, Calendar.DAY_OF_MONTH));

		JSpinner.DateEditor timeEditor1 = new JSpinner.DateEditor(spHorasDesc, "HH:mm:ss");
		spHorasDesc.setEditor(timeEditor1);
		spHorasDesc.setFont(new Font("SansSerif", Font.PLAIN, 15));
		spHorasDesc.setEnabled(false);
		spHorasDesc.setBounds(458, 93, 88, 30);
		pnActualizarAsistencia.add(spHorasDesc);

		spHoraEntrada = new JSpinner(new SpinnerDateModel(new Date(1467172800820L), null, null, Calendar.DAY_OF_MONTH));

		JSpinner.DateEditor timeEditor11 = new JSpinner.DateEditor(spHoraEntrada, "HH:mm:ss");
		spHoraEntrada.setEditor(timeEditor11);
		spHoraEntrada.setFont(new Font("SansSerif", Font.PLAIN, 15));
		spHoraEntrada.setEnabled(false);
		spHoraEntrada.setBounds(148, 134, 88, 30);
		pnActualizarAsistencia.add(spHoraEntrada);

		spHoraSalida = new JSpinner(new SpinnerDateModel(new Date(1467172800820L), null, null, Calendar.DAY_OF_MONTH));

		JSpinner.DateEditor timeEditor111 = new JSpinner.DateEditor(spHoraSalida, "HH:mm:ss");
		spHoraSalida.setEditor(timeEditor111);
		spHoraSalida.setFont(new Font("SansSerif", Font.PLAIN, 15));
		spHoraSalida.setEnabled(false);
		spHoraSalida.setBounds(148, 175, 88, 30);
		pnActualizarAsistencia.add(spHoraSalida);

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				habilitarModoEdicion(false);
				tCodigo.setText("");
				spDia.setValue(0);
				dcFecha.setDate(null);
				spHoraEntrada.setValue(new Date(1467172800820L));
				spHoraSalida.setValue(new Date(1467172800820L));
				tCodigoUsuario.setText("");
				tNombreUsuario.setText("");
				spHorasExtras.setValue(new Date(1467172800820L));
				spHorasDesc.setValue(new Date(1467172800820L));
				cbObservaciones.setSelectedIndex(0);

				int row = tableAsistencias.getSelectedRow();
				if (tableAsistencias.getValueAt(row, 3) != null) {
					btnEntrada.setEnabled(false);
				}
				tableAsistencias.clearSelection();
				if (tableAsistencias.getSelectedRow() != -1) {
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
				} else {
					btnModificar.setEnabled(false);
					btnEliminar.setEnabled(false);
				}
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
					ResultSet ObtenerAsistencia = ControlarAsistenciaSession.ObtenerAsistenciaPorCodigo(Integer.parseInt(tCodigo.getText()));
					if (ObtenerAsistencia.next() == true) { //Si ya existe registro

						//CalculoHorasTrabajadas
						Calendar CalHoraEntrada = Calendar.getInstance();
						CalHoraEntrada.setTime(formatohora.parse(formatohora.format(spHoraEntrada.getValue())));

						Calendar CalHoraSalida = Calendar.getInstance();
						CalHoraSalida.setTime(formatohora.parse(formatohora.format(spHoraSalida.getValue())));

						long milisegundos = CalHoraSalida.getTimeInMillis() - CalHoraEntrada.getTimeInMillis();
						Time DiferenciaHora = MiliAhhmmss(milisegundos);

						ControlarAsistenciaSession.ActualizarAsistencia(Integer.parseInt(tCodigo.getText()), (Integer) spDia.getValue(), DiaDeLaSemana(dcFecha.getDate()), dcFecha.getDate(), Integer.parseInt(tCodigoFuncionario.getText()),
								Time.valueOf(formatohora.format(spHoraEntrada.getValue())), Time.valueOf(formatohora.format(spHoraSalida.getValue())), Time.valueOf(formatohora.format(spHorasExtras.getValue())),
								formatohora.format(spHorasDesc.getValue()), Integer.parseInt(tCodigoUsuario.getText()), cbObservaciones.getSelectedIndex() + 1, DiferenciaHora + "", Integer.parseInt(tCodigo.getText()));
						btnCancelar.doClick();
						ObtenerAsistencia.close();
						JOptionPane.showMessageDialog(null, "Actualizado con exito");
					} else { //Si no existe registro
						try {
							ControlarAsistenciaSession.InsertarAsistencia(Integer.parseInt(tCodigo.getText()), (Integer) spDia.getValue(), DiaDeLaSemana(dcFecha.getDate()), dcFecha.getDate(), Integer.parseInt(tCodigoFuncionario.getText()),
									Time.valueOf(formatohora.format(spHoraEntrada.getValue())), null, Time.valueOf(formatohora.format(spHorasExtras.getValue())), formatohora.format(spHorasDesc.getValue()), Integer.parseInt(tCodigoUsuario.getText()),
									cbObservaciones.getSelectedIndex() + 1, "-08:00:00");
						} catch (NumberFormatException e1) {
							System.out.println(e1);
						}
						btnCancelar.doClick();
						JOptionPane.showMessageDialog(null, "Guardado con exito");
					}
					ObtenerAsistencia.close();
				} catch (SQLException | ParseException e1) {
					System.out.println(e1);

				}

				LlenarTablaAsistenciaConFiltro();
			}
		});

		tableFuncionarios = new JTable();

		MostrarResultSetEnJtableFuncionarios(ControlarAsistenciaSession.ObtenerFuncionarios());
		tableFuncionarios.setRowSelectionInterval(0, 0); //Selecciona el primer registro

		pnMovimientos = new JPanel();
		pnMovimientos.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Movimientos registrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnMovimientos.setBackground(SystemColor.activeCaption);
		pnMovimientos.setBounds(441, 285, 1214, 299);
		contentPane.add(pnMovimientos);
		pnMovimientos.setLayout(null);

		scrollPaneAsistencias = new JScrollPane();
		scrollPaneAsistencias.setBounds(50, 50, 1113, 217);
		pnMovimientos.add(scrollPaneAsistencias);

		tBusquedaAsistencia = new JTextField();
		tBusquedaAsistencia.setBounds(737, 262, 426, 33);
		pnMovimientos.add(tBusquedaAsistencia);
		tBusquedaAsistencia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tBusquedaAsistencia.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {

				String cadena = (tBusquedaAsistencia.getText().toUpperCase());
				tBusquedaAsistencia.setText(cadena);
				repaint();

				trsfiltroAsistencia = new TableRowSorter(tableAsistencias.getModel());
				filtroAsistencia();
				tableAsistencias.setRowSorter(trsfiltroAsistencia);
			}
		});

		tBusquedaAsistencia.addMouseListener(new MouseAdapter() {

		});
		tBusquedaAsistencia.setColumns(10);

		lblBusqueda = new JLabel("Buscar por");
		lblBusqueda.setForeground(Color.BLACK);
		lblBusqueda.setBounds(447, 262, 92, 33);
		pnMovimientos.add(lblBusqueda);
		lblBusqueda.setFont(new Font("Tahoma", Font.BOLD, 15));

		comboFiltroAsistencia = new JComboBox();
		comboFiltroAsistencia.setBounds(537, 262, 201, 33);
		pnMovimientos.add(comboFiltroAsistencia);
		comboFiltroAsistencia.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboFiltroAsistencia.setModel(new DefaultComboBoxModel(new String[] { "Dia", "Dia Semana", "Fecha", "Hora Entrada", "Hora Salida", "Horas Extras", "Hora Desc.", "Usuario", "Observacion", "Hora Total", "Codigo", "Todos" }));
		comboFiltroAsistencia.setSelectedIndex(11);

		JLabel lblMes = new JLabel("Mes");
		lblMes.setBounds(50, 19, 56, 30);
		pnMovimientos.add(lblMes);
		lblMes.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMes.setForeground(Color.BLACK);
		lblMes.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

		cbMes = new JComboBox();
		cbMes.setBounds(107, 19, 134, 30);
		pnMovimientos.add(cbMes);
		Date FechaActual = new Date();
		cbMes.setModel(new DefaultComboBoxModel(new String[] { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" }));
		cbMes.setSelectedIndex(FechaActual.getMonth());
		cbMes.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbMes.setEditable(false);

		lblAo = new JLabel("A\u00F1o");
		lblAo.setBounds(239, 19, 47, 30);
		pnMovimientos.add(lblAo);
		lblAo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAo.setForeground(Color.BLACK);
		lblAo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

		spAño = new JSpinner();
		spAño.setBounds(290, 19, 81, 30);
		pnMovimientos.add(spAño);
		spAño.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				LlenarTablaAsistenciaConFiltro();
			}
		});
		spAño.setModel(new SpinnerNumberModel(2016, 1950, 2100, 1));

		spAño.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LlenarTablaAsistenciaConFiltro();
			}
		});

		JPanel pnDatosFuncionario = new JPanel();
		pnDatosFuncionario.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Datos del Funcionario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDatosFuncionario.setBackground(SystemColor.activeCaption);
		pnDatosFuncionario.setBounds(441, 16, 350, 257);
		contentPane.add(pnDatosFuncionario);
		pnDatosFuncionario.setLayout(null);

		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(18, 160, 64, 28);
		pnDatosFuncionario.add(lblCargo);
		lblCargo.setForeground(SystemColor.desktop);
		lblCargo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCargo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

		tCargoFuncionario = new JTextField();
		tCargoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tCargoFuncionario.setBounds(94, 158, 244, 30);
		pnDatosFuncionario.add(tCargoFuncionario);
		tCargoFuncionario.setEnabled(false);
		tCargoFuncionario.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombre.setForeground(Color.BLACK);
		lblNombre.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblNombre.setBounds(18, 66, 64, 28);
		pnDatosFuncionario.add(lblNombre);

		tNombreFuncionario = new JTextField();
		tNombreFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tNombreFuncionario.setEnabled(false);
		tNombreFuncionario.setColumns(10);
		tNombreFuncionario.setBounds(94, 64, 244, 30);
		pnDatosFuncionario.add(tNombreFuncionario);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setHorizontalAlignment(SwingConstants.LEFT);
		lblApellido.setForeground(Color.BLACK);
		lblApellido.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblApellido.setBounds(18, 113, 64, 28);
		pnDatosFuncionario.add(lblApellido);

		tApellidoFuncionario = new JTextField();
		tApellidoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tApellidoFuncionario.setEnabled(false);
		tApellidoFuncionario.setColumns(10);
		tApellidoFuncionario.setBounds(94, 111, 244, 30);
		pnDatosFuncionario.add(tApellidoFuncionario);

		JLabel lblFrecuencia = new JLabel("Frecuencia");
		lblFrecuencia.setHorizontalAlignment(SwingConstants.LEFT);
		lblFrecuencia.setForeground(Color.BLACK);
		lblFrecuencia.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblFrecuencia.setBounds(18, 207, 79, 28);
		pnDatosFuncionario.add(lblFrecuencia);

		tHoraEntradaFuncionario = new JTextField();
		tHoraEntradaFuncionario.setHorizontalAlignment(SwingConstants.CENTER);
		tHoraEntradaFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tHoraEntradaFuncionario.setEnabled(false);
		tHoraEntradaFuncionario.setColumns(10);
		tHoraEntradaFuncionario.setBounds(97, 205, 75, 30);
		pnDatosFuncionario.add(tHoraEntradaFuncionario);

		JLabel lblHasta = new JLabel("hasta");
		lblHasta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHasta.setForeground(Color.BLACK);
		lblHasta.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblHasta.setBounds(200, 207, 54, 28);
		pnDatosFuncionario.add(lblHasta);

		tHoraSalidaFuncionario = new JTextField();
		tHoraSalidaFuncionario.setHorizontalAlignment(SwingConstants.RIGHT);
		tHoraSalidaFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tHoraSalidaFuncionario.setEnabled(false);
		tHoraSalidaFuncionario.setColumns(10);
		tHoraSalidaFuncionario.setBounds(263, 205, 75, 30);
		pnDatosFuncionario.add(tHoraSalidaFuncionario);

		JLabel lblCodigo_1 = new JLabel("Codigo");
		lblCodigo_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblCodigo_1.setForeground(Color.BLACK);
		lblCodigo_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		lblCodigo_1.setBounds(18, 19, 64, 28);
		pnDatosFuncionario.add(lblCodigo_1);

		tCodigoFuncionario = new JTextField();
		tCodigoFuncionario.setText((String) null);
		tCodigoFuncionario.setFont(new Font("SansSerif", Font.PLAIN, 14));
		tCodigoFuncionario.setEnabled(false);
		tCodigoFuncionario.setColumns(10);
		tCodigoFuncionario.setBounds(94, 17, 46, 30);
		pnDatosFuncionario.add(tCodigoFuncionario);

		JPanel pnFuncionarios = new JPanel();
		pnFuncionarios.setBounds(6, 16, 433, 568);
		contentPane.add(pnFuncionarios);
		pnFuncionarios.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Funcionarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnFuncionarios.setBackground(SystemColor.activeCaption);
		pnFuncionarios.setLayout(null);

		JScrollPane scrollPaneFuncionarios = new JScrollPane();
		scrollPaneFuncionarios.setBounds(13, 17, 406, 517);
		pnFuncionarios.add(scrollPaneFuncionarios);
		scrollPaneFuncionarios.setViewportView(tableFuncionarios);
		scrollPaneFuncionarios.setViewportView(tableFuncionarios);

		tBusquedaFuncionario = new JTextField();
		tBusquedaFuncionario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String cadena = (tBusquedaFuncionario.getText().toUpperCase());
				tBusquedaFuncionario.setText(cadena);
				repaint();

				trsfiltroFuncionario = new TableRowSorter(tableFuncionarios.getModel());
				filtroFuncionario();
				tableFuncionarios.setRowSorter(trsfiltroFuncionario);
			}
		});
		tBusquedaFuncionario.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tBusquedaFuncionario.setColumns(10);
		tBusquedaFuncionario.setBounds(187, 529, 232, 33);
		pnFuncionarios.add(tBusquedaFuncionario);

		comboFiltroFuncionario = new JComboBox();
		comboFiltroFuncionario.setModel(new DefaultComboBoxModel(new String[] { "Codigo", "Nombre", "Apellido", "Cargo", "Todos" }));
		comboFiltroFuncionario.setSelectedIndex(4);
		comboFiltroFuncionario.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboFiltroFuncionario.setBounds(89, 529, 100, 33);
		pnFuncionarios.add(comboFiltroFuncionario);

		label = new JLabel("Buscar por");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(6, 529, 92, 33);
		pnFuncionarios.add(label);

		JPanel pnBotones = new JPanel();
		pnBotones.setBackground(SystemColor.activeCaption);
		pnBotones.setBounds(441, 596, 847, 85);
		contentPane.add(pnBotones);
		pnBotones.setLayout(null);
		btnEntrada = new JButton("Registrar Entrada");
		btnEntrada.setSize(214, 62);
		btnEntrada.setLocation(10, 17);
		pnBotones.add(btnEntrada);
		btnEntrada.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/Entrada.png")));
		btnEntrada.setEnabled(false);
		btnEntrada.setFont(new Font("Tahoma", Font.BOLD, 14));

		if (tableFuncionarios.getSelectedRow() != -1) {
			btnEntrada.setEnabled(true);
		}

		btnModificar = new JButton("Modificar");
		btnModificar.setSize(134, 62);
		btnModificar.setLocation(433, 17);
		pnBotones.add(btnModificar);
		btnModificar.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/documento-de-modificar-el-archivo-de-papel-icono-4468-96_opt.png")));
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnModificar.setEnabled(false);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setSize(136, 62);
		btnEliminar.setLocation(577, 17);
		pnBotones.add(btnEliminar);
		btnEliminar.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/document_delete_128 copia.png")));
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEliminar.setEnabled(false);

		btnSalir = new JButton("Salir");
		btnSalir.setSize(111, 62);
		btnSalir.setLocation(723, 17);
		pnBotones.add(btnSalir);
		btnSalir.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/Salir copia.png")));
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 14));

		btnSalida = new JButton("Registrar Salida");
		btnSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CargarAsistenciaATextField();

				habilitarModoEdicion(true);
				spHoraEntrada.setEnabled(false);
				spHoraSalida.setEnabled(true);
				//HoraSalida
				SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
				Date FechaActual = new Date();
				spHoraSalida.setValue(Time.valueOf(formatoHora.format(FechaActual)));

				//Observacion
				cbObservaciones.setSelectedIndex(0);

			}
		});
		btnSalida.setBounds(234, 17, 189, 62);
		pnBotones.add(btnSalida);
		btnSalida.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/Salida.png")));
		btnSalida.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSalida.setEnabled(false);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int row = tableAsistencias.getSelectedRow();
				int opt = JOptionPane.showConfirmDialog(null, "Desea eliminar la asistencia de " + tableAsistencias.getValueAt(row, 3).toString() + "?", "Confirmacion", JOptionPane.YES_NO_OPTION);

				if (opt == 0) {
					try {

						ControlarAsistenciaSession.eliminarregistro((Integer) tableAsistencias.getValueAt(row, 11));
						LlenarTablaAsistenciaConFiltro();
						JOptionPane.showMessageDialog(null, "Asistencia eliminada con exito");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error al eliminar registro " + e);
					}
				}

			}
		});
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				habilitarModoEdicion(true);
				spHorasExtras.setEnabled(true);
				spHorasDesc.setEnabled(true);
				CargarAsistenciaATextField();

				spHoraSalida.setEnabled(true);
				dcFecha.setEnabled(false);
			}
		});
		btnEntrada.addActionListener(new ActionListener() {
			@SuppressWarnings("restriction")
			public void actionPerformed(ActionEvent e) {
				if (tableFuncionarios.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Seleccione un Funcionario");
					return;
				}

				habilitarModoEdicion(true);

				//Codigo
				try {
					ResultSet ultimocodigo = (ResultSet) ControlarAsistenciaSession.ObtenerCodigoUltimoRegistro();
					ultimocodigo.next();
					tCodigo.setText(ultimocodigo.getInt("asi_codigo") + 1 + "");
					ultimocodigo.close();
				} catch (Exception e2) {
					tCodigo.setText("1");
					System.out.println("Error en rs de ultimocodigo" + e2);
				}

				try {
					//Fecha sino se selecciono asistencia
					if (tableAsistencias.getSelectedRow() != -1) {
						//Dia
						int row = tableAsistencias.getSelectedRow();
						spDia.setValue(tableAsistencias.getValueAt(row, 0));
						//Fecha
						int dia = (Integer) tableAsistencias.getValueAt(row, 0);
						int mes = cbMes.getSelectedIndex() + 1;
						int año = (int) spAño.getValue();
						String Fecha = dia + "/" + mes + "/" + año;
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
						Date FechaDate;
						FechaDate = formato.parse(Fecha);
						dcFecha.setDate(FechaDate);
					} else {
						SimpleDateFormat formatodia = new SimpleDateFormat("dd");
						Date FechaActual = new Date();
						//Dia
						spDia.setValue(Integer.parseInt(formatodia.format(FechaActual)));
						dcFecha.setDate(FechaActual);
					}

					//HoraEntrada
					SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
					Date FechaActual = new Date();
					spHoraEntrada.setValue(Time.valueOf(formatoHora.format(FechaActual)));

				} catch (ParseException e1) {
					System.out.println("Fecha sino se selecciono asistencia : " + e1);
				}

				//Usuario
				try {
					ResultSet usuario = ControlarAsistenciaSession.ObtenerUsuarioPorLogin(Principal.tLogin.getText());
					usuario.next();
					tCodigoUsuario.setText(usuario.getString("usu_codigo"));
					tNombreUsuario.setText(usuario.getString("nomapelli"));
					usuario.close();
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente
					System.out.println(e1);
				}

			}
		});

		tableAsistencias = new JTable();

		LlenarTablaAsistenciaConFiltro();

		MostrarFuncionario();
		//Privilegios
		try {
			Utilidades utilidades = new Utilidades();
			ResultSet UsuarioPrivilegios = utilidades.ejecutarSQLSelect("SELECT * FROM privilegio, usuario WHERE usu_privilegio = pri_codigo AND usu_login = '" + Principal.tLogin.getText() + "'");
			UsuarioPrivilegios.next();
			btnEntrada.setEnabled(UsuarioPrivilegios.getBoolean("pri_asistencianuevo"));
			btnModificar.setEnabled(UsuarioPrivilegios.getBoolean("pri_asistenciamodificar"));
			btnEliminar.setEnabled(UsuarioPrivilegios.getBoolean("pri_asistenciaeliminar"));

			JPanel panel = new JPanel();
			panel.setBounds(1292, 596, 363, 85);
			contentPane.add(panel);
			panel.setBackground(SystemColor.activeCaption);
			panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Opciones de Informe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setLayout(null);

			btnLiquidacionSalarial = new JButton("Liquidacion Salarial");
			btnLiquidacionSalarial.setBounds(151, 17, 198, 62);
			panel.add(btnLiquidacionSalarial);
			btnLiquidacionSalarial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerarLiquidacionSalarial();
				}
			});
			btnLiquidacionSalarial.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/LiquidacionSalarial.png")));
			btnLiquidacionSalarial.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnLiquidacionSalarial.setEnabled(false);
			btnLiquidacionSalarial.setEnabled(UsuarioPrivilegios.getBoolean("pri_asistenciainforme"));

			btnInforme = new JButton("Informe");
			btnInforme.setBounds(12, 17, 127, 62);
			panel.add(btnInforme);
			btnInforme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GenerarReporteAsistencia();
				}
			});
			btnInforme.setIcon(new ImageIcon(ControlarAsistencia.class.getResource("/icono/distributor-report-icon copia.png")));
			btnInforme.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnInforme.setEnabled(UsuarioPrivilegios.getBoolean("pri_asistenciainforme"));

			UsuarioPrivilegios.close();
		} catch (SQLException e1) {
			// TODO Bloque catch generado automáticamente
			System.out.println("Error al ejecutar privilegios de usuario " + e1);
		}

	}

	public JButton getBtnNuevo() {
		return btnEntrada;
	}

	public void setBtnNuevo(JButton btnNuevo) {
		this.btnEntrada = btnNuevo;
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

	public JButton getBtnSalir() {
		return btnSalir;
	}

	public void setBtnSalir(JButton btnSalir) {
		this.btnSalir = btnSalir;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JButton getBtnInformeAsistencia() {
		return btnInforme;
	}

	public void setBtnInformeAsistencia(JButton btnInformeAsistencia) {
		this.btnInforme = btnInformeAsistencia;
	}

	public void habilitarModoEdicion(boolean habilitar) {

		ConsultaTablaObservaciones();
		cbObservaciones.setSelectedIndex(10);
		getBtnNuevo().setEnabled(!habilitar);
		getBtnModificar().setEnabled(!habilitar);
		getBtnEliminar().setEnabled(!habilitar);
		getBtnGuardar().setEnabled(habilitar);
		getBtnCancelar().setEnabled(habilitar);
		spHorasExtras.setEnabled(false);
		spHorasDesc.setEnabled(false);
		spHoraEntrada.setEnabled(habilitar);
		spHoraSalida.setEnabled(false);
		cbObservaciones.setEnabled(habilitar);
		btnSalida.setEnabled(false);
		tableFuncionarios.setEnabled(!habilitar);
		tableAsistencias.setEnabled(!habilitar);
		cbMes.setEnabled(!habilitar);
		spAño.setEnabled(!habilitar);
		if (habilitar == true) {
			tableFuncionarios.setBackground(Color.LIGHT_GRAY);
			tableFuncionarios.setForeground(Color.DARK_GRAY);
			tableAsistencias.setBackground(Color.LIGHT_GRAY);
			tableAsistencias.setForeground(Color.DARK_GRAY);
		} else {
			tableFuncionarios.setBackground(Color.WHITE);
			tableFuncionarios.setForeground(Color.BLACK);
			tableAsistencias.setBackground(Color.WHITE);
			tableAsistencias.setForeground(Color.BLACK);
		}

	}

	public void filtroFuncionario() {
		int columnaABuscar = 0;
		if (comboFiltroFuncionario.getSelectedItem() == "Codigo") {
			columnaABuscar = 0;
		}
		if (comboFiltroFuncionario.getSelectedItem() == "Nombre") {
			columnaABuscar = 1;
		}
		if (comboFiltroFuncionario.getSelectedItem() == "Apellido") {
			columnaABuscar = 2;
		}
		if (comboFiltroFuncionario.getSelectedItem() == "Cargo") {
			columnaABuscar = 3;
		}

		if (comboFiltroFuncionario.getSelectedItem() == "Todos") {
			trsfiltroFuncionario.setRowFilter(RowFilter.regexFilter(tBusquedaFuncionario.getText(), 0, 1, 2, 3));
			return;
		}
		trsfiltroFuncionario.setRowFilter(RowFilter.regexFilter(tBusquedaFuncionario.getText(), columnaABuscar));
	}

	public void filtroAsistencia() {
		int columnaABuscar = 0;
		if (comboFiltroAsistencia.getSelectedItem() == "Dia") {
			columnaABuscar = 0;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Dia Semana") {
			columnaABuscar = 1;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Fecha") {
			columnaABuscar = 2;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Hora de Entrada") {
			columnaABuscar = 4;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Hora de Salida") {
			columnaABuscar = 5;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Horas Extras") {
			columnaABuscar = 6;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Horas Desc.") {
			columnaABuscar = 7;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Usuario") {
			columnaABuscar = 8;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Observacion") {
			columnaABuscar = 9;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Hora Total") {
			columnaABuscar = 10;
		}

		if (comboFiltroAsistencia.getSelectedItem() == "Codigo") {
			columnaABuscar = 11;
		}
		if (comboFiltroAsistencia.getSelectedItem() == "Todos") {
			trsfiltroAsistencia.setRowFilter(RowFilter.regexFilter(tBusquedaAsistencia.getText(), 0, 1, 3, 4, 5, 6, 7, 8, 9, 10));
			return;
		}
		trsfiltroAsistencia.setRowFilter(RowFilter.regexFilter(tBusquedaAsistencia.getText(), columnaABuscar));
	}

	private void MostrarResultSetEnJtableFuncionarios(ResultSet rs) {

		try {
			DefaultTableModel ModeloFuncionario = new DefaultTableModel();
			tableFuncionarios = new JTable(ModeloFuncionario) {
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableFuncionarios.setBackground(Color.WHITE);

			tableFuncionarios.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					if (tableFuncionarios.isEnabled() == true) {
						MostrarFuncionario();
						LlenarTablaAsistenciaConFiltro();
						if (tableFuncionarios.getSelectedRow() != -1) {
							btnEntrada.setEnabled(true);
						}
					}
				}
			});

			// Creamos las columnas.
			ModeloFuncionario.addColumn("Codigo");
			ModeloFuncionario.addColumn("Nombre");
			ModeloFuncionario.addColumn("Apellido");
			ModeloFuncionario.addColumn("Cargo");

			tableFuncionarios.getTableHeader().setReorderingAllowed(false); // Para que no se pueda mover columnas
			tableFuncionarios.getTableHeader().setResizingAllowed(false); // Para que no se puede cambiar tamaño de columnas
			tableFuncionarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Para que solo se pueda seleccionar un registro a la vez

			// TAMANO DE CADA COLUMNA
			tableFuncionarios.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tableFuncionarios.getColumnModel().getColumn(0).setPreferredWidth(50);
			tableFuncionarios.getColumnModel().getColumn(1).setPreferredWidth(120);
			tableFuncionarios.getColumnModel().getColumn(2).setPreferredWidth(120);
			tableFuncionarios.getColumnModel().getColumn(3).setPreferredWidth(170);

			ResultSetMetaData metaDatos = rs.getMetaData();
			// Se obtiene el número de columnas.
			int numeroColumnas = metaDatos.getColumnCount();
			// Bucle para cada resultado en la consulta
			while (rs.next()) {
				// Se crea un array que será una de las filas de la tabla.
				Object[] fila = new Object[numeroColumnas]; // se pone el numero de columnas en la tabla	
				for (int i = 0; i < numeroColumnas; i++) // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
					fila[i] = rs.getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

				ModeloFuncionario.addRow(fila);// Se añade al modelo la fila completa.
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void MostrarResultSetEnJtableAsistencia(ResultSet rs) {

		try {
			ModeloAsistencia = new DefaultTableModel() {
				//Para que se pueda ordenar por Dias
				@Override
				public Class getColumnClass(int columna) {
					if (columna == 0) {
						return Integer.class; //Le dice al modelo que la primera columna es de tipo integer
					}
					return String.class; //Si no, es String
				}
			};
			// Para que registros no sean editables
			tableAsistencias = new JTable(ModeloAsistencia) {

				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			tableAsistencias.setBackground(Color.WHITE);

			tableAsistencias.setShowHorizontalLines(true);
			tableAsistencias.setShowVerticalLines(true);

			tableAsistencias.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (tableAsistencias.isEnabled() == true) {
						int row = tableAsistencias.getSelectedRow();

						if (tableAsistencias.getValueAt(row, 3) == "") { //Si registro vacio
							btnEntrada.setEnabled(true);
							btnModificar.setEnabled(false);
							btnEliminar.setEnabled(false);
						} else { //Si registro cargado
							btnEntrada.setEnabled(false);
							btnModificar.setEnabled(true);
							btnEliminar.setEnabled(true);
						}

						if (tableAsistencias.getValueAt(row, 5) != null) { //Si no tiene registro de salida
							btnSalida.setEnabled(false);
							btnModificar.setEnabled(true);
						} else { //Si ya tiene registro de salida
							btnSalida.setEnabled(true);
							btnModificar.setEnabled(false);
						}
					}

				}
			});

			// Creamos las columnas.
			ModeloAsistencia.addColumn("Dia");
			ModeloAsistencia.addColumn("Dia Semana");
			ModeloAsistencia.addColumn("Fecha");
			ModeloAsistencia.addColumn("Nombre y Apellido");
			ModeloAsistencia.addColumn("Hora de Entrada");
			ModeloAsistencia.addColumn("Hora de Salida");
			ModeloAsistencia.addColumn("Horas Extras");
			ModeloAsistencia.addColumn("Horas Desc.");
			ModeloAsistencia.addColumn("Usuario");
			ModeloAsistencia.addColumn("Observacion");
			ModeloAsistencia.addColumn("Total de Horas");
			ModeloAsistencia.addColumn("Codigo");

			tableAsistencias.getTableHeader().setReorderingAllowed(false); // Para que no se pueda mover columnas
			tableAsistencias.getTableHeader().setResizingAllowed(false); // Para que no se puede cambiar tamaño de columnas
			tableAsistencias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Para que solo se pueda seleccionar un registro a la vez

			// TAMANO DE CADA COLUMNA
			tableAsistencias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tableAsistencias.getColumnModel().getColumn(0).setPreferredWidth(50);
			tableAsistencias.getColumnModel().getColumn(1).setPreferredWidth(80);
			tableAsistencias.getColumnModel().getColumn(2).setPreferredWidth(100);
			//Ocultar columna 3
			tableAsistencias.getColumnModel().getColumn(3).setMaxWidth(0);
			tableAsistencias.getColumnModel().getColumn(3).setMinWidth(0);
			tableAsistencias.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
			tableAsistencias.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);

			tableAsistencias.getColumnModel().getColumn(4).setPreferredWidth(100);
			tableAsistencias.getColumnModel().getColumn(5).setPreferredWidth(100);
			tableAsistencias.getColumnModel().getColumn(6).setPreferredWidth(100);
			tableAsistencias.getColumnModel().getColumn(7).setPreferredWidth(100);
			//Ocultar columna 8
			tableAsistencias.getColumnModel().getColumn(8).setMaxWidth(0);
			tableAsistencias.getColumnModel().getColumn(8).setMinWidth(0);
			tableAsistencias.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
			tableAsistencias.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);

			tableAsistencias.getColumnModel().getColumn(9).setPreferredWidth(300);
			tableAsistencias.getColumnModel().getColumn(10).setPreferredWidth(100);
			tableAsistencias.getColumnModel().getColumn(11).setPreferredWidth(60);

			OrdenarTabla(0);

			// Formatear fecha
			tableAsistencias.getColumnModel().getColumn(2).setCellRenderer(FormatearJtable.getDateRenderer());

			ResultSetMetaData metaDatos = rs.getMetaData(); // Se obtiene el número de columnas.
			int numeroColumnas = metaDatos.getColumnCount(); // Bucle para cada resultado en la consulta
			while (rs.next()) { // Se crea un array que será una de las filas de la tabla.
				Object[] fila = new Object[numeroColumnas]; // se pone el numero de columnas en la tabla
				for (int i = 0; i < numeroColumnas; i++) // Se rellena cada posición del array con una de las columnas de la tabla en base de datos.
					fila[i] = rs.getObject(i + 1); // El primer indice en rs es el 1, no el cero, por eso se suma 1.

				ModeloAsistencia.addRow(fila); // Se añade al modelo la fila completa.
			}
			rs.close();

			Calendar cal = new GregorianCalendar((int) spAño.getValue(), cbMes.getSelectedIndex(), 1);
			int CantDiasEnUnMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			int Dia = 0;
			boolean DiaYaExiste = false;
			int BusquedaDiaEnColumna = 0;
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			String FechaFormateado = "";
			Date FechaDate = null;
			while (Dia < CantDiasEnUnMes) {
				Dia++;
				while (BusquedaDiaEnColumna < tableAsistencias.getRowCount()) {
					if ((Integer) tableAsistencias.getValueAt(BusquedaDiaEnColumna, 0) == Dia) {
						DiaYaExiste = true;
					}
					BusquedaDiaEnColumna++;
				}

				if (DiaYaExiste == false) {
					cal = new GregorianCalendar((int) spAño.getValue(), cbMes.getSelectedIndex(), Dia);
					FechaDate = cal.getTime();
					String cero = "";
					String cero2 = "";
					if (Dia <= 9) {
						cero = "0";
					}
					if ((cbMes.getSelectedIndex() + 1) <= 9) {
						cero2 = "0";
					}
					ModeloAsistencia.addRow(new Object[] { Dia, DiaDeLaSemana(FechaDate), (cero + Dia + "/" + cero2 + (cbMes.getSelectedIndex() + 1)) + "/" + spAño.getValue(), "", "", "", "00:00:00", "00:00:00", "", "", "00:00:00" });
				}

				DiaYaExiste = false;
				BusquedaDiaEnColumna = 0;
			}
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Cargar combo con consulta
	private void ConsultaTablaObservaciones() {
		Connection conexion = null;
		java.sql.Statement s = null;
		ResultSet rs = null;

		//limpio el combobox
		cbObservaciones.removeAllItems();
		try {
			Class.forName("org.postgresql.Driver");
			String BaseDeDatos = "skarlata";
			String Login = "postgres";
			String Seña = "admin";
			conexion = DriverManager.getConnection("jdbc:postgresql://localhost/" + BaseDeDatos, Login, Seña);
			s = conexion.createStatement();
			rs = s.executeQuery("SELECT obs_descri FROM observaciones ORDER BY obs_codigo");

			// Se recorre el ResultSet.
			while (rs.next()) {
				cbObservaciones.addItem(rs.getObject("obs_descri"));

			}
			rs.close();
			conexion.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error sql no se pueden leer datos, quizas no se puso el nombre de la base de datos" + e);

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al leer la base de datos" + e);

		}
	}

	private void LlenarTablaAsistenciaConFiltro() {
		int row = tableFuncionarios.getSelectedRow();
		int CodigoSeleccionado = (int) tableFuncionarios.getValueAt(row, 0);
		MostrarResultSetEnJtableAsistencia(ControlarAsistenciaSession.obtenerasistencias(CodigoSeleccionado, cbMes.getSelectedIndex() + 1, (Integer) spAño.getValue()));
		tableAsistencias.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tableAsistencias.setRowHeight(30);
		scrollPaneAsistencias.setViewportView(tableAsistencias);
	}

	private void MostrarFuncionario() {
		try {
			int row = tableFuncionarios.getSelectedRow();
			int CodigoSeleccionado = (int) tableFuncionarios.getValueAt(row, 0);
			ResultSet FuncionarioSeleccionado = ControlarAsistenciaSession.ObtenerFuncionarioPorCodigo(CodigoSeleccionado);
			FuncionarioSeleccionado.next();
			tCodigoFuncionario.setText(FuncionarioSeleccionado.getString("fun_codigo"));
			tNombreFuncionario.setText(FuncionarioSeleccionado.getString("fun_nombre"));
			tApellidoFuncionario.setText(FuncionarioSeleccionado.getString("fun_apellido"));
			tCargoFuncionario.setText(FuncionarioSeleccionado.getString("car_descri"));
			tHoraEntradaFuncionario.setText(FuncionarioSeleccionado.getString("frec_entrada"));
			tHoraSalidaFuncionario.setText(FuncionarioSeleccionado.getString("frec_salida"));
			FuncionarioSeleccionado.close();

		} catch (SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	private void OrdenarTabla(int NroColumna) {
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableAsistencias.getModel());
		tableAsistencias.setRowSorter(sorter);
		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

		int columnIndexToSort = NroColumna; //Que columna quieres
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING)); //En forma ascendente o descendente

		sorter.setSortKeys(sortKeys);
		sorter.sort();
	}

	private void CargarAsistenciaATextField() {
		try {
			int row = tableAsistencias.getSelectedRow();
			ResultSet AsistenciaSeleccionada = ControlarAsistenciaSession.ObtenerAsistenciaPorCodigo((Integer) tableAsistencias.getValueAt(row, 11));
			AsistenciaSeleccionada.next();
			tCodigo.setText(AsistenciaSeleccionada.getString(1));
			spDia.setValue(AsistenciaSeleccionada.getInt(2));
			dcFecha.setDate(AsistenciaSeleccionada.getDate(4));
			spHoraEntrada.setValue(AsistenciaSeleccionada.getTime(6));
			try {
				spHoraSalida.setValue(AsistenciaSeleccionada.getTime(7));
			} catch (Exception e) {
				// TODO: handle exception
			}
			ResultSet UsuarioSeleccionado = ControlarAsistenciaSession.ObtenerUsuarioPorCodigo(AsistenciaSeleccionada.getInt(10));
			UsuarioSeleccionado.next();
			tCodigoUsuario.setText(UsuarioSeleccionado.getString(1));
			tNombreUsuario.setText(UsuarioSeleccionado.getString(2));
			spHorasExtras.setValue(AsistenciaSeleccionada.getTime(8));
			SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
			spHorasDesc.setValue(Time.valueOf(AsistenciaSeleccionada.getString(9)));
			ConsultaTablaObservaciones();
			cbObservaciones.setSelectedIndex(AsistenciaSeleccionada.getInt(11) - 1);
			AsistenciaSeleccionada.close();
			UsuarioSeleccionado.close();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public static String DiaDeLaSemana(Date fecha) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(fecha);
		int NumeroDiaDeLaSemana = cal.get(Calendar.DAY_OF_WEEK);
		String NombreSemana[] = { "SAB", "DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB" };
		return NombreSemana[NumeroDiaDeLaSemana];
	}

	private static Time MiliAhhmmss(long Milisegundos) {
		//RestarHoras
		String TimeHora = "";
		try {
			SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
			long hora = Milisegundos / 3600000;
			long restohora = Milisegundos % 3600000;
			long minuto = restohora / 60000;
			long restominuto = restohora % 60000;
			long segundo = restominuto / 1000;
			Date DateHora = formatohora.parse(hora + ":" + minuto + ":" + segundo);
			TimeHora = formatohora.format(DateHora);

		} catch (ParseException e) {
			System.out.println(e);
			TimeHora = "00:00:00";
		}
		return Time.valueOf(TimeHora);

	}

	private static Object GenerarReporteAsistencia() {
		try {
			//Se carga la tabla al reporte

			JRTableModelDataSource tablemodel = new JRTableModelDataSource(ModeloAsistencia);
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = ControlarAsistencia.class.getResourceAsStream("/reportes/reportAsistencia.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			int ultimoregistro = tableAsistencias.getRowCount();
			parametros.put("FECHA_INICIAL", tableAsistencias.getValueAt(0, 2));
			parametros.put("FECHA_FINAL", tableAsistencias.getValueAt(ultimoregistro - 1, 2));
			parametros.put("NOMBRE_EMPRESA", "KARANDAY");
			parametros.put("MES", cbMes.getSelectedItem());

			ResultSet Funcionario = ControlarAsistenciaSession.ObtenerFuncionarioPorCodigo(Integer.parseInt(tCodigoFuncionario.getText()));
			Funcionario.next();
			parametros.put("CI_FUNCIONARIO", Funcionario.getString("fun_cedula"));
			parametros.put("CARGO_FUNCIONARIO", Funcionario.getString("car_descri"));
			parametros.put("NOMAPE_FUNCIONARIO", Funcionario.getString("fun_nombre") + " " + Funcionario.getString("fun_apellido"));
			Funcionario.close();

			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, tablemodel);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setTitle("Informe de Asistencia");
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

	private static Object GenerarLiquidacionSalarial() {
		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(ModeloAsistencia);
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = ControlarAsistencia.class.getResourceAsStream("/reportes/reportLiquidacionSalario.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			DecimalFormat decimalformato = new DecimalFormat("###,###"); //la , se imprime como .
			parametros.put("NOMBRE_EMPRESA", "KARANDAY");

			ResultSet Funcionario = ControlarAsistenciaSession.ObtenerFuncionarioPorCodigo(Integer.parseInt(tCodigoFuncionario.getText()));
			Funcionario.next();
			parametros.put("NOMAPE_FUNCIONARIO", Funcionario.getString("fun_nombre") + " " + Funcionario.getString("fun_apellido"));
			parametros.put("CI_FUNCIONARIO", decimalformato.format(Double.parseDouble(Funcionario.getString("fun_cedula").replace(".", ""))));
			parametros.put("CARGO_FUNCIONARIO", Funcionario.getString("car_descri"));
			String SalarioBase = decimalformato.format(Double.parseDouble(Funcionario.getString("car_salario").replace(".", "")));
			parametros.put("SALARIO_BASE", SalarioBase + " Gs");
			Funcionario.close();
			parametros.put("MES", cbMes.getSelectedItem());
			DecimalFormat decimalformato2 = new DecimalFormat("###"); //la , se imprime como .
			parametros.put("TOTAL_HORAS_TRABAJADAS", decimalformato2.format(SumatoriaColumnaTimeEnHoras(10)));
			parametros.put("TOTAL_HORAS_EXTRAS", decimalformato2.format(SumatoriaColumnaTimeEnHoras(6)));
			parametros.put("TOTAL_HORAS_DESCONTADAS", decimalformato2.format(SumatoriaColumnaTimeEnHoras(7)));

			double SalarioPorHora = (Double.parseDouble(SalarioBase.replace(".", "")) / 26) / 8; //SALARIOEJEMPLO/30= 55.274 (salario diario) /8 horas = 6.909 (por hora)
			System.out.println("Salario por hora: " + SalarioPorHora);
			double TotalHorasTrabajadas = SumatoriaColumnaTimeEnHoras(10);
			System.out.println("Total horas trabajadas: " + SumatoriaColumnaTime(10) + " Se pasa a horas--> " + TotalHorasTrabajadas);
			DecimalFormat decimalformato3 = new DecimalFormat("###,###,###"); //la , se imprime como .
			double Calculo = SalarioPorHora * TotalHorasTrabajadas;
			parametros.put("SALARIO", decimalformato3.format(Calculo) + " Gs");
			double TotalHorasExtras = SumatoriaColumnaTimeEnHoras(6);
			double CalculoHorasExtras = (SalarioPorHora + (SalarioPorHora / 2)) * TotalHorasExtras;
			System.out.println("Total horas extras: " + SumatoriaColumnaTime(6) + " Se pasa a horas--> " + TotalHorasExtras);
			parametros.put("HORAS_EXTRAS", decimalformato3.format(CalculoHorasExtras) + " Gs");
			parametros.put("HORA_EXTRA", decimalformato3.format((SalarioPorHora + (SalarioPorHora / 2)) * 7) + " Gs");
			double IPS = (Integer.parseInt(SalarioBase.replace(".", "")) * 9) / 100;
			parametros.put("IPS", decimalformato3.format(IPS) + " Gs");

			double TotalHorasDesc = SumatoriaColumnaTimeEnHoras(7);
			double CalculoHorasDesc = SalarioPorHora * TotalHorasDesc;
			System.out.println("Total horas descontadas: " + SumatoriaColumnaTime(7) + " Se pasa a horas--> " + TotalHorasDesc);
			parametros.put("DESCUENTOS", decimalformato3.format(CalculoHorasDesc) + " Gs");
			parametros.put("SUM_SALARIO_TOTAL", decimalformato3.format((Calculo + CalculoHorasExtras)) + " Gs");
			parametros.put("SUM_DESCUENTO_TOTAL", decimalformato3.format((CalculoHorasDesc + IPS)) + " Gs");
			parametros.put("TOTAL_NETO", decimalformato3.format(Calculo + CalculoHorasExtras - CalculoHorasDesc - IPS) + " Gs");
			System.out.println(MiliAhhmmss(7200000));
			Funcionario.close();
			JasperPrint jasperPrint = JasperFillManager.fillReport(rutajasper, parametros, tablemodel);
			JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
			jasperViewer.setTitle("Informe de Asistencia");
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

	private static String SumatoriaColumnaTime(int NroDeColumnaASumar) {
		SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
		Calendar HoraTrabajada = Calendar.getInstance();
		int totalRow = tableAsistencias.getRowCount();
		long SumatoriaHorasTrabajadasMilisegundos = 0;
		for (int i = 0; i < totalRow; i++) {
			try {
				HoraTrabajada.setTime(formatohora.parse(tableAsistencias.getValueAt(i, NroDeColumnaASumar).toString()));
				SumatoriaHorasTrabajadasMilisegundos = SumatoriaHorasTrabajadasMilisegundos + (HoraTrabajada.getTimeInMillis() - 14400000);

			} catch (ParseException e) {
				System.out.println("Error al sumar time " + e);
			}
		}
		long hora = SumatoriaHorasTrabajadasMilisegundos / 3600000;
		long restohora = SumatoriaHorasTrabajadasMilisegundos % 3600000;
		long minuto = restohora / 60000;
		long restominuto = restohora % 60000;
		long segundo = restominuto / 1000;
		DecimalFormat decimalformato = new DecimalFormat("00"); //la , se imprime como .
		System.out.println();
		String SumatoriaHorasTrabajadas = decimalformato.format(hora) + ":" + decimalformato.format(minuto) + ":" + decimalformato.format(segundo);
		return SumatoriaHorasTrabajadas;
	}

	private static double SumatoriaColumnaTimeEnHoras(int NroDeColumnaASumar) {
		SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
		Calendar HoraTrabajada = Calendar.getInstance();
		int totalRow = tableAsistencias.getRowCount();
		long SumatoriaHorasTrabajadasMilisegundos = 0;
		for (int i = 0; i < totalRow; i++) {
			try {
				HoraTrabajada.setTime(formatohora.parse(tableAsistencias.getValueAt(i, NroDeColumnaASumar).toString()));
				SumatoriaHorasTrabajadasMilisegundos = SumatoriaHorasTrabajadasMilisegundos + (HoraTrabajada.getTimeInMillis() - 14400000);

			} catch (ParseException e) {
				System.out.println("Error al sumar time " + e);
			}
		}
		long hora = SumatoriaHorasTrabajadasMilisegundos / 3600000;
		return hora;
	}
}
