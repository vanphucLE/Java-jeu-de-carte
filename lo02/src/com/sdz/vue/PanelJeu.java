package com.sdz.vue;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class PanelJeu extends JPanel {
	private Fenetre fenetrePc;

	public PanelJeu(Fenetre fenetrePc) {
		this.fenetrePc = fenetrePc;
		this.setLayout(null);
		JLabel label = new JLabel();
		add(label);
		label.setForeground(Color.BLUE);
		label.setSize(1400,700);
		label.setIcon(new ImageIcon("images/table.jpg"));
	}

	public static void main(String[] args) {
		Fenetre b = new Fenetre();
		b.setVisible(true);
		JFrame a = new JFrame();
		System.out.println(JFrame.MAXIMIZED_BOTH);
		a.setSize(a.getMaximumSize());

		a.getContentPane().add(new PanelJeu(new Fenetre()));
		a.setVisible(true);
	}

}
