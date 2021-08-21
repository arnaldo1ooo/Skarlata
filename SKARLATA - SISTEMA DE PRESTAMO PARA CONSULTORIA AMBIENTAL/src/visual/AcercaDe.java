package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AcercaDe extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AcercaDe dialog = new AcercaDe();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AcercaDe() {
		setTitle("Acerca de Skarlata");
		setBounds(100, 100, 640, 351);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
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
		
		setLocationRelativeTo(null);
		JLabel label = new JLabel("");
		label.setBounds(6, 6, 262, 253);
		contentPanel.add(label);

		ImageIcon imagen = new ImageIcon(Principal.class.getResource("/icono/Logo Skarlata.jpg"));
		label.setIcon(new ImageIcon(Principal.class.getResource("/icono/Logo Skarlata.jpg")));

		Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(icono);
		
		JLabel lblNewLabel = new JLabel("Sistema de Gestion para Consultoria Ambiental SKARLATA");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNewLabel.setBounds(280, 25, 332, 16);
		contentPanel.add(lblNewLabel);
		
		JLabel lblVersionMarsRelease = new JLabel("Version: 1.20");
		lblVersionMarsRelease.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblVersionMarsRelease.setBounds(280, 53, 332, 16);
		contentPanel.add(lblVersionMarsRelease);
		
		JLabel lblcCopyrightEclipse = new JLabel("(c) Copyright Skarlata, 2016.  ");
		lblcCopyrightEclipse.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblcCopyrightEclipse.setBounds(280, 81, 332, 16);
		contentPanel.add(lblcCopyrightEclipse);
		
		JLabel lblTodosLosDerechos = new JLabel("Todos los derechos reservados. Skarlata y el logo Skarlata ");
		lblTodosLosDerechos.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblTodosLosDerechos.setBounds(280, 109, 332, 16);
		contentPanel.add(lblTodosLosDerechos);
		
		JLabel lblSonTrademarksOf = new JLabel("son marcas comerciales de Skarlata, Inc.");
		lblSonTrademarksOf.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblSonTrademarksOf.setBounds(280, 126, 332, 16);
		contentPanel.add(lblSonTrademarksOf);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.WHITE);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 21));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
