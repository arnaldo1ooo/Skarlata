package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import session.AdministrarClientesSession;
import session.AdministrarFuncionariosSession;
import session.TableConsultoresSession;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;
import javax.swing.ImageIcon;

public class TableConsultores extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField tBusqueda;
	private JTable table;
	private TableRowSorter trsfiltro;
	private JComboBox cbFiltro;
	public static int codigoseleccionado;
	public static String consultorseleccionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TableConsultores dialog = new TableConsultores();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TableConsultores() {

		setResizable(false);
		setModal(true);
		setTitle("Funcionarios");
		setBounds(100, 100, 1095, 546);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.activeCaption);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
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

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(16, 0, 1055, 390);
		contentPanel.add(scrollPane);

		table = new JTable();
		MostrarResultSetEnJtable(TableConsultoresSession.obtenertabla());
		scrollPane.setViewportView(table);

		JLabel label = new JLabel("Buscar por");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("SansSerif", Font.BOLD, 14));
		label.setBounds(259, 386, 83, 33);
		contentPanel.add(label);

		cbFiltro = new JComboBox();
		cbFiltro.setModel(new DefaultComboBoxModel(new String[] {"Codigo", "Nombre", "Apellido", "Direccion", "Cargo", "Todos"}));
		cbFiltro.setSelectedIndex(5);
		cbFiltro.setFont(new Font("SansSerif", Font.PLAIN, 15));
		cbFiltro.setBounds(354, 386, 173, 33);
		contentPanel.add(cbFiltro);

		tBusqueda = new JTextField();
		tBusqueda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				Character s = e.getKeyChar();
				if (Character.isLetter(s)) {
					tBusqueda.setText(tBusqueda.getText().toUpperCase());
				}

				String cadena = (tBusqueda.getText().toUpperCase());
				tBusqueda.setText(cadena);
				repaint();

				trsfiltro = new TableRowSorter(table.getModel());
				filtro();
				table.setRowSorter(trsfiltro);
			}
		});
		tBusqueda.setFont(new Font("SansSerif", Font.PLAIN, 15));
		tBusqueda.setColumns(10);
		tBusqueda.setBounds(539, 386, 532, 33);
		contentPanel.add(tBusqueda);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.activeCaption);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int row = table.getSelectedRow();
						codigoseleccionado = (int) table.getValueAt(row, 0);
						consultorseleccionado = (String) table.getValueAt(row, 1) + " " + table.getValueAt(row, 2);
						AdministrarProyectos.tCodigoConsultor.setText(codigoseleccionado + "");
						AdministrarProyectos.tNomApeConsultor.setText(consultorseleccionado);
						dispose();
					}
				});
				okButton.setIcon(new ImageIcon(TableConsultores.class.getResource("/icono/Ok.png")));
				okButton.setFont(new Font("Tahoma", Font.BOLD, 18));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.setIcon(new ImageIcon(TableConsultores.class.getResource("/icono/Cancelar.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.BOLD, 18));
				cancelButton.setActionCommand("Cancelar");
				buttonPane.add(cancelButton);
			}
		}
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
			table.getColumnModel().getColumn(3).setPreferredWidth(225);
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

	public void filtro() {
		int columnaABuscar = 0;
		if (cbFiltro.getSelectedItem() == "Codigo") {
			columnaABuscar = 0;
		}
		if (cbFiltro.getSelectedItem().toString() == "Nombre") {
			columnaABuscar = 1;
		}
		if (cbFiltro.getSelectedItem() == "Apellido") {
			columnaABuscar = 2;
		}
		if (cbFiltro.getSelectedItem() == "Direccion") {
			columnaABuscar = 3;
		}
		if (cbFiltro.getSelectedItem() == "Cargo") {
			columnaABuscar = 4;
		}

		if (cbFiltro.getSelectedItem() == "Todos") {
			trsfiltro.setRowFilter(RowFilter.regexFilter(tBusqueda.getText(), 0, 1, 2, 3, 4));
			return;
		}
		trsfiltro.setRowFilter(RowFilter.regexFilter(tBusqueda.getText(), columnaABuscar));
	}
}
