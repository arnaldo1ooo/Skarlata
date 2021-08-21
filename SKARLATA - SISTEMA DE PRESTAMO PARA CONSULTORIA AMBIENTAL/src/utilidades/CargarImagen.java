package utilidades;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FileChooserUI;
import javax.swing.JFileChooser;

public class CargarImagen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static JFileChooser fcCargarImagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CargarImagen dialog = new CargarImagen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CargarImagen() {
		setTitle("Busqueda Croquis");
		setBounds(100, 100, 819, 465);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			fcCargarImagen = new JFileChooser();
			
			String Usuario = System.getProperty("user.name"); 
			fcCargarImagen.setCurrentDirectory(new File("C:/Users/"+ Usuario +"/Desktop"));

			
			
			fcCargarImagen.setDialogType(1);
			fcCargarImagen.setDialogTitle("");
			fcCargarImagen.setBounds(0, 0, 801, 418);
			contentPanel.add(fcCargarImagen);
		}
	}

}
