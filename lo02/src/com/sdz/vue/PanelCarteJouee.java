package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PanelCarteJouee extends JPanel {

	/**
	 * Create the panel.
	 */
	private JButton button; 
	public PanelCarteJouee() {
		this.setLayout(null);
		this.setSize(200, 409);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);

		JLabel lblCarteJouee = new JLabel("Carte Jou\u00E9e");
		lblCarteJouee.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarteJouee.setBounds(0, 0, 200, 29);
		lblCarteJouee.setBorder(lineBorder);
		add(lblCarteJouee);
	}

	public void dessinerCarteJouee(int idCarte) {
		this.button = new JButton();
		button.setBounds(5, 56, 190, 272);
		try {
			BufferedImage image = ImageIO.read(new File("cartes/" + idCarte + ".PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(190, 272, image.SCALE_SMOOTH));
			button.setIcon(icon);
			button.setMargin(new Insets(0, 0, 0, 0));
			add(button);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.repaint();
		this.validate();
	}

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.getContentPane().add(new PanelCarteJouee());
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
