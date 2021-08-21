package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import session.LoginSession;

public class Login extends JDialog {

	private JPanel contentPane;
	private JButton btnIngresar;
	public static JTextField tUsuario;
	private JPasswordField pContraseña;
	public static int PrivilegioUsuario = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					Login frame = new Login();
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
	public Login() {
		setUndecorated(true);
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
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 652, 452);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JPanel pnCentral = new JPanel();
		pnCentral.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnCentral.setBounds(48, 42, 555, 367);
		panel.add(pnCentral);
		pnCentral.setLayout(null);

		pContraseña = new JPasswordField();
		pContraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char car = (char) e.getKeyCode();
				if (car == e.VK_ENTER) {//Al apretar ENTER QUE HAGA ALGO
					btnIngresar.requestFocus();
				}

			}
		});

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(93, 100, 76, 35);
		pnCentral.add(lblUsuario);
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setFont(new Font("Segoe UI Emoji", Font.BOLD, 17));

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(72, 163, 97, 35);
		pnCentral.add(lblContrasea);
		lblContrasea.setForeground(Color.WHITE);
		lblContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasea.setFont(new Font("Segoe UI Emoji", Font.BOLD, 17));

		tUsuario = new JTextField();
		tUsuario.setBounds(182, 100, 302, 35);
		pnCentral.add(tUsuario);
		tUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char car = (char) e.getKeyCode();
				if (car == e.VK_ENTER) {//Al apretar ENTER QUE HAGA ALGO
					pContraseña.requestFocus();
				}

			}
		});
		tUsuario.setFont(new Font("SansSerif", Font.PLAIN, 17));
		tUsuario.setColumns(10);
		pContraseña.setFont(new Font("SansSerif", Font.PLAIN, 17));
		pContraseña.setBounds(182, 163, 302, 35);
		pnCentral.add(pContraseña);

		btnIngresar = new JButton("Ingresar");
		btnIngresar.setBounds(72, 279, 169, 61);
		pnCentral.add(btnIngresar);
		btnIngresar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char car = (char) e.getKeyCode();
				if (car == e.VK_ENTER) {//Al apretar ENTER QUE HAGA ALGO
					btnIngresar.doClick();
				}
			}
		});
		btnIngresar.setIcon(new ImageIcon(Login.class.getResource("/icono/Desbloquear.png")));
		btnIngresar.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 18));
		btnIngresar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				// Obtener el password
				char passArray[] = getpContraseña().getPassword();
				// Revisar que sean letras y numeros
				for (int i = 0; i < passArray.length; i++) {
					char c = passArray[i];
					// Si no es letra o numero entonces no es valido
					if (!Character.isLetterOrDigit(c))
						JOptionPane.showMessageDialog(null, "La contraseña deben ser numeros y/o letras");
				}
				// Convertir el password a String
				String pass = new String(passArray);

				ResultSet BuscarUsuario = LoginSession.BuscarUsuario(gettUsuario().getText(), pass);
				try {
					if (BuscarUsuario.next() == true) {
						PrivilegioUsuario = BuscarUsuario.getInt("usu_privilegio");
					}
				} catch (SQLException e1) {
					// TODO Bloque catch generado automáticamente

					e1.printStackTrace();

				}
				try {
					if (BuscarUsuario.absolute(1)) {
						dispose();
						Principal principal = new Principal();
						principal.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null, "Nombre de usuario o Contraseña incorrecta");
						tUsuario.requestFocus();
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
		});
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { tUsuario, pContraseña, btnIngresar, contentPane, panel, pnCentral }));

		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(313, 279, 169, 61);
		pnCentral.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opt = JOptionPane.showConfirmDialog(null, "Desea salir del sistema?", "Confirmacion", JOptionPane.YES_NO_OPTION);
				if (opt == 0) {
					try {
						System.exit(0);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnSalir.setIcon(new ImageIcon(Login.class.getResource("/icono/Salir copia.png")));
		btnSalir.setFont(new Font("Perpetua Titling MT", Font.PLAIN, 18));

		JLabel lblFondo2 = new JLabel("");
		lblFondo2.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		lblFondo2.setIcon(new ImageIcon(Login.class.getResource("/icono/Fondo.jpg")));
		lblFondo2.setBounds(0, 0, 555, 367);
		pnCentral.add(lblFondo2);

		JLabel lblFondo = new JLabel("");
		lblFondo.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFondo.setIcon(new ImageIcon(Login.class.getResource("/icono/Fondo.jpg")));
		lblFondo.setBounds(0, 0, 650, 450);
		panel.add(lblFondo);
		tUsuario.requestFocus();
	}

	public JTextField gettUsuario() {
		return tUsuario;
	}

	public void settUsuario(JTextField tUsuario) {
		this.tUsuario = tUsuario;
	}

	public JPasswordField getpContraseña() {
		return pContraseña;
	}

	public void setpContraseña(JPasswordField pContraseña) {
		this.pContraseña = pContraseña;
	}
}
