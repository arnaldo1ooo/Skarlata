package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import utilidades.Utilidades;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class InicializarSistema extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Utilidades utilidades = new Utilidades();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InicializarSistema dialog = new InicializarSistema();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InicializarSistema() {
		setModal(true);
		setTitle("Inicializar sistema");

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
		setBounds(100, 100, 625, 409);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.activeCaption);
		contentPanel.setBorder(new TitledBorder(null, "Inicializar sistema", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JButton btnInicializarAsistencia = new JButton("Inicializar Asistencia");
		btnInicializarAsistencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea inicializar la tabla asistencia?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						utilidades.ejecutarSQL("TRUNCATE TABLE asistencia");
						utilidades.ejecutarSQL("ALTER SEQUENCE asistencia_id_asistencia_seq RESTART WITH 1");
						JOptionPane.showMessageDialog(null, "Tabla Asistencia inicializado con exito");
					} catch (Exception e2) {
						System.out.println("Error al inicializar " + e2);
					}
				}
			}
		});
		btnInicializarAsistencia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInicializarAsistencia.setBounds(192, 100, 223, 45);
		contentPanel.add(btnInicializarAsistencia);

		JButton btnInicializarProyecto = new JButton("Inicializar Proyecto");
		btnInicializarProyecto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea inicializar la tabla proyecto?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						utilidades.ejecutarSQL("TRUNCATE TABLE proyecto");
						utilidades.ejecutarSQL("ALTER SEQUENCE proyecto_id_proyecto_seq RESTART WITH 1");
						String sDirectorio = "src/imagencroquis/";
						File f = new File(sDirectorio);
						borrarDirectorio(f);
						JOptionPane.showMessageDialog(null, "Tabla Proyecto inicializado con exito");
					} catch (Exception e2) {
						System.out.println("Error al inicializar " + e2);
						
					}
				}

			}
		});
		btnInicializarProyecto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInicializarProyecto.setBounds(192, 42, 223, 45);
		contentPanel.add(btnInicializarProyecto);

		JButton btnInicializarTodo = new JButton("Inicializar Todo");
		btnInicializarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea inicializar todas las tablas?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						utilidades.ejecutarSQL("TRUNCATE TABLE proyecto, asistencia, funcionario, cliente, usuario");
						utilidades.ejecutarSQL("ALTER SEQUENCE proyecto_id_proyecto_seq RESTART WITH 1");
						utilidades.ejecutarSQL("ALTER SEQUENCE asistencia_id_asistencia_seq RESTART WITH 1");
						utilidades.ejecutarSQL("ALTER SEQUENCE funcionario_id_funcionario_seq RESTART WITH 1");
						utilidades.ejecutarSQL("ALTER SEQUENCE cliente_id_cliente_seq RESTART WITH 1");
						utilidades.ejecutarSQL("ALTER SEQUENCE usuario_id_usuario_seq RESTART WITH 1");
						JOptionPane.showMessageDialog(null, "Todas las tablas inicializadas con exito");
					} catch (Exception e2) {
						System.out.println("Error al inicializar " + e2);
					}
				}
			}
		});
		btnInicializarTodo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInicializarTodo.setBounds(192, 183, 223, 45);
		contentPanel.add(btnInicializarTodo);
		{
			JButton cancelButton = new JButton("Cancelar");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setBounds(457, 282, 132, 62);
			contentPanel.add(cancelButton);
			cancelButton.setIcon(new ImageIcon(InicializarSistema.class.getResource("/icono/Cancelar.png")));
			cancelButton.setActionCommand("Cancelar");
		}
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
}
