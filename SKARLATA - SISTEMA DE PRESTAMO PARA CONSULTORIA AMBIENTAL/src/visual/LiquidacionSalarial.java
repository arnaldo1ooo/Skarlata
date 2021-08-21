package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import groovyjarjarcommonscli.ParseException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import session.ControlarAsistenciaSession;
import utilidades.Utilidades;

public class LiquidacionSalarial extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Utilidades utilidades = new Utilidades();
	private JTextField textField;
	private static JTable tableFuncionarios;
	private static JComboBox cbMes;
	private static JSpinner spAño;
	private JButton btnProcesar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LiquidacionSalarial dialog = new LiquidacionSalarial();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LiquidacionSalarial() {
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
		setBounds(100, 100, 853, 344);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.activeCaption);
		contentPanel.setBorder(new TitledBorder(null, "Liquidacion Salarial", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton cancelButton = new JButton("Salir");
			cancelButton.setFont(new Font("SansSerif", Font.BOLD, 12));
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setBounds(672, 215, 145, 62);
			contentPanel.add(cancelButton);
			cancelButton.setIcon(new ImageIcon(LiquidacionSalarial.class.getResource("/icono/Salir copia.png")));
			cancelButton.setActionCommand("Cancelar");
		}

		btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerarLiquidacionSalarial();
			}
		});
		btnProcesar.setEnabled(false);
		btnProcesar.setIcon(new ImageIcon(LiquidacionSalarial.class.getResource("/icono/icono.reporte.rt_-200x200.png")));
		btnProcesar.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnProcesar.setActionCommand("Cancelar");
		btnProcesar.setBounds(672, 141, 145, 62);
		contentPanel.add(btnProcesar);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Funcionarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(28, 24, 591, 253);
		contentPanel.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 23, 475, 197);
		panel.add(scrollPane);

		tableFuncionarios = new JTable();
		MostrarResultSetEnJtableFuncionarios(ControlarAsistenciaSession.ObtenerFuncionarios());
		scrollPane.setViewportView(tableFuncionarios);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(311, 216, 218, 33);
		panel.add(textField);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre", "Apellido", "Cargo", "Todos"}));
		comboBox.setSelectedIndex(4);
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 15));
		comboBox.setBounds(157, 216, 156, 33);
		panel.add(comboBox);

		JLabel label = new JLabel("Buscar por");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(66, 216, 92, 33);
		panel.add(label);

		cbMes = new JComboBox();
		java.util.Date FechaActual = new java.util.Date();
		cbMes.setModel(new DefaultComboBoxModel(new String[] { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE" }));
		cbMes.setSelectedIndex(FechaActual.getMonth());
		cbMes.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbMes.setBounds(683, 39, 134, 30);
		contentPanel.add(cbMes);

		JLabel label_1 = new JLabel("Mes");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		label_1.setBounds(626, 39, 56, 30);
		contentPanel.add(label_1);

		JLabel label_2 = new JLabel("A\u00F1o");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
		label_2.setBounds(634, 81, 47, 30);
		contentPanel.add(label_2);

		spAño = new JSpinner();
		spAño.setFont(new Font("SansSerif", Font.PLAIN, 15));
		spAño.setBounds(685, 81, 131, 30);
		spAño.setModel(new SpinnerNumberModel(2016, 1950, 2100, 1));
		contentPanel.add(spAño);
	}

	public static void borrarDirectorio(File directorio) {
		File[] ficheros = directorio.listFiles();

		for (int x = 0; x < ficheros.length; x++) {
			if (ficheros[x].isDirectory()) {
				borrarDirectorio(ficheros[x]);
			}
			ficheros[x].delete();
		}

		if (directorio.delete())
			System.out.println("El directorio " + directorio + " ha sido borrado correctamente");
		else
			System.out.println("El directorio " + directorio + " no se ha podido borrar");
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
					if (tableFuncionarios.getSelectedRow() != -1) {
						btnProcesar.setEnabled(true);
					} else {
						btnProcesar.setEnabled(false);
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

	private static double SumatoriaColumnaTimeEnHoras(String NombreColumna) {
		int row = tableFuncionarios.getSelectedRow();
		ResultSet Asistencias = ControlarAsistenciaSession.obtenerasistencias(Integer.parseInt(tableFuncionarios.getValueAt(row, 0).toString()), cbMes.getSelectedIndex() + 1, (Integer) spAño.getValue());
		SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
		Calendar HoraTrabajada = Calendar.getInstance();
		long SumatoriaHorasTrabajadasMilisegundos = 0;
		try {
			for (int i = 0; (Asistencias.next() == true); i++) {
				HoraTrabajada.setTime(formatohora.parse(Asistencias.getString(NombreColumna)));
				SumatoriaHorasTrabajadasMilisegundos = SumatoriaHorasTrabajadasMilisegundos + (HoraTrabajada.getTimeInMillis() - 14400000);
			}
			Asistencias.close();

		} catch (java.text.ParseException | SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		long hora = SumatoriaHorasTrabajadasMilisegundos / 3600000;
		return hora;
	}

	private static String SumarColumnaTime(String NombreColumna) {
		int row = tableFuncionarios.getSelectedRow();
		ResultSet Asistencias = ControlarAsistenciaSession.obtenerasistencias(Integer.parseInt(tableFuncionarios.getValueAt(row, 0).toString()), cbMes.getSelectedIndex() + 1, (Integer) spAño.getValue());
		SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
		Calendar HoraTrabajada = Calendar.getInstance();
		long SumatoriaHorasTrabajadasMilisegundos = 0;

		try {
			for (int i = 0; (Asistencias.next() == true); i++) {
				HoraTrabajada.setTime(formatohora.parse(Asistencias.getString(NombreColumna)));
				SumatoriaHorasTrabajadasMilisegundos = SumatoriaHorasTrabajadasMilisegundos + (HoraTrabajada.getTimeInMillis() - 14400000);
			}
			Asistencias.close();
		} catch (java.text.ParseException | SQLException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
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

	private static Time MiliAhhmmss(long Milisegundos) {
		//RestarHoras
		String TimeHora = "";
		SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
		long hora = Milisegundos / 3600000;
		long restohora = Milisegundos % 3600000;
		long minuto = restohora / 60000;
		long restominuto = restohora % 60000;
		long segundo = restominuto / 1000;
		Date DateHora;
		try {
			DateHora = formatohora.parse(hora + ":" + minuto + ":" + segundo);
			TimeHora = formatohora.format(DateHora);
		} catch (java.text.ParseException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}

		return Time.valueOf(TimeHora);

	}

	private static Object GenerarLiquidacionSalarial() {
		try {
			//Se carga la tabla al reporte
			JRTableModelDataSource tablemodel = new JRTableModelDataSource(tableFuncionarios.getModel());
			//Para que funcione InputStream la libreria jasperreports-fonts-.5.6.0.jar no debe tener via de construccion, o sea debe quedarse en la carpeta lib
			java.io.InputStream rutajasper = ControlarAsistencia.class.getResourceAsStream("/reportes/reportLiquidacionSalario.jasper");
			if (rutajasper == null) {
				JOptionPane.showMessageDialog(null, "Archivo jasper no encontrado");
			}
			Map parametros = new HashMap();
			DecimalFormat decimalformato = new DecimalFormat("###,###"); //la , se imprime como .
			parametros.put("NOMBRE_EMPRESA", "KARANDAY");
			int row = tableFuncionarios.getSelectedRow();
			ResultSet Funcionario = ControlarAsistenciaSession.ObtenerFuncionarioPorCodigo(Integer.parseInt(tableFuncionarios.getValueAt(row, 0).toString()));
			Funcionario.next();
			parametros.put("NOMAPE_FUNCIONARIO", Funcionario.getString("fun_nombre") + " " + Funcionario.getString("fun_apellido"));
			parametros.put("CI_FUNCIONARIO", decimalformato.format(Double.parseDouble(Funcionario.getString("fun_cedula").replace(".", ""))));
			parametros.put("CARGO_FUNCIONARIO", Funcionario.getString("car_descri"));
			String SalarioBase = decimalformato.format(Double.parseDouble(Funcionario.getString("car_salario").replace(".", "")));
			parametros.put("SALARIO_BASE", SalarioBase + " Gs");
			Funcionario.close();
			parametros.put("MES", cbMes.getSelectedItem());
			DecimalFormat decimalformato2 = new DecimalFormat("###"); //la , se imprime como .
			parametros.put("TOTAL_HORAS_TRABAJADAS", decimalformato2.format(SumatoriaColumnaTimeEnHoras("asi_totalhoras")));
			parametros.put("TOTAL_HORAS_EXTRAS", decimalformato2.format(SumatoriaColumnaTimeEnHoras("asi_horaextra")));
			parametros.put("TOTAL_HORAS_DESCONTADAS", decimalformato2.format(SumatoriaColumnaTimeEnHoras("asi_horadescontada")));

			double SalarioPorHora = (Double.parseDouble(SalarioBase.replace(".", "")) / 26) / 8; //SALARIOEJEMPLO/30= 55.274 (salario diario) /8 horas = 6.909 (por hora)
			System.out.println("Salario por hora: " + SalarioPorHora);
			double TotalHorasTrabajadas = SumatoriaColumnaTimeEnHoras("asi_totalhoras");
			System.out.println("Total horas trabajadas: " + SumatoriaColumnaTimeEnHoras("asi_totalhoras") + " Se pasa a horas--> " + TotalHorasTrabajadas);
			DecimalFormat decimalformato3 = new DecimalFormat("###,###,###"); //la , se imprime como .
			double Calculo = SalarioPorHora * TotalHorasTrabajadas;
			parametros.put("SALARIO", decimalformato3.format(Calculo) + " Gs");
			double TotalHorasExtras = SumatoriaColumnaTimeEnHoras("asi_horaextra");
			double CalculoHorasExtras = (SalarioPorHora + (SalarioPorHora / 2)) * TotalHorasExtras;
			System.out.println("Total horas extras: " + SumatoriaColumnaTimeEnHoras("asi_horaextra") + " Se pasa a horas--> " + TotalHorasExtras);
			parametros.put("HORAS_EXTRAS", decimalformato3.format(CalculoHorasExtras) + " Gs");
			double IPS = (Integer.parseInt(SalarioBase.replace(".", "")) * 9) / 100;
			parametros.put("IPS", decimalformato3.format(IPS) + " Gs");

			double TotalHorasDesc = SumatoriaColumnaTimeEnHoras("asi_horadescontada");
			double CalculoHorasDesc = SalarioPorHora * TotalHorasDesc;
			System.out.println("Horas Descontadas: " + SumatoriaColumnaTimeEnHoras("asi_horadescontada") + " Se pasa a horas--> " + TotalHorasDesc);
			parametros.put("DESCUENTOS", decimalformato3.format(CalculoHorasDesc) + " Gs");
			parametros.put("SUM_SALARIO_TOTAL",  decimalformato3.format((Calculo + CalculoHorasExtras)) + " Gs");
			parametros.put("SUM_DESCUENTO_TOTAL",  decimalformato3.format((CalculoHorasDesc + IPS)) + " Gs");
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

}
