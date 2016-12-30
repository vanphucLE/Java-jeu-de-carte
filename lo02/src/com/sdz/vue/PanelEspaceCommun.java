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
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;

public class PanelEspaceCommun extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelEspaceCommun() {

		this.setLayout(null);
		this.setSize(1270, 546);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);

		JLabel lblEspaceCommun = new JLabel("ESPACE COMMUN");
		lblEspaceCommun.setBounds(572, 241, 145, 68);
		add(lblEspaceCommun);
		// this.setBackground(Color.GRAY);

		this.peindreEspace(21);

	}

	public void peindreEspace(int nbCartes) {
		int val = 9;
		if (val >= nbCartes) {
			val = nbCartes;
			nbCartes = 0;
		} else {
			nbCartes = nbCartes - 9;
		}
		for (int i = 0; i < val; i++) {
			JButton button = new JButton("New button");
			button.setBounds(10 + 140 * i, 0, 130, 182);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(130, 182, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 10, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		val = 9;
		if (val >= nbCartes) {
			val = nbCartes;
			nbCartes = 0;
		} else {
			nbCartes = nbCartes - 9;
		}
		for (int i = 0; i < val; i++) {
			JButton button = new JButton("New button");
			button.setBounds(10 + 140 * i, 182, 130, 182);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(130, 182, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 10, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		val = 9;
		if (val >= nbCartes) {
			val = nbCartes;
			nbCartes = 0;
		} else {
			nbCartes = nbCartes - 9;
		}
		for (int i = 0; i < val; i++) {
			JButton button = new JButton("New button");
			button.setBounds(10 + 140 * i, 364, 130, 182);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(130, 182, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 10, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.getContentPane().add(new PanelEspaceCommun());
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
