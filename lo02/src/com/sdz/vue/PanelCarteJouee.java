package com.sdz.vue;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.sdz.cartes.CarteAction;

public class PanelCarteJouee extends JPanel {

	/**
	 * Create the panel.
	 */
	private JLabel lblCarte;

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

	public void supprimer() {
		this.removeAll();
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		JLabel lblCarteJouee = new JLabel("Carte Jou\u00E9e");
		lblCarteJouee.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarteJouee.setBounds(0, 0, 200, 29);
		lblCarteJouee.setBorder(lineBorder);
		add(lblCarteJouee);
	}

	public void dessinerCarteJouee(CarteAction carte, String nomJ) {
		this.supprimer();
		JLabel lblJoueur = new JLabel("Joueur: " + nomJ);
		lblJoueur.setBounds(10, 42, 178, 16);
		add(lblJoueur);

		this.lblCarte = new JLabel("");
		lblCarte.setBounds(5, 72, 190, 272);
		try {
			System.out.println(carte);
			BufferedImage image = ImageIO.read(new File("cartes/" + carte.getId() + ".PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(190, 272, image.SCALE_SMOOTH));
			lblCarte.setIcon(icon);
			add(lblCarte);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.repaint();
		this.validate();
	}
}
