package com.sdz.vue;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PanelCarteJouee extends JPanel {

	/**
	 * Create the panel.
	 */
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

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.getContentPane().add(new PanelCarteJouee());
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
