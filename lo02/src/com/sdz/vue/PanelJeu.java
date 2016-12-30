package com.sdz.vue;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.JButton;

public class PanelJeu extends JPanel {
	private Fenetre fenetrePc;

	public PanelJeu(Fenetre fenetrePc) {
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);

		this.setBackground(Color.GREEN);
		this.setBorder(lineBorder);
		this.fenetrePc = fenetrePc;
		this.setLayout(null);

		// JButton btnAfficherEspaceComun = new JButton("Afficher Espace
		// Comun");
		// btnAfficherEspaceComun.setBounds(this.fenetrePc.getWidth()/2-82,
		// this.fenetrePc.getHeight()/2-25, 165, 25);
		// add(btnAfficherEspaceComun);

		// joueur physique
		JPanel panel = new PanelJP();
		panel.setLocation(420, this.fenetrePc.getHeight() - 310);
		add(panel);

		// joueur virtuel 1
		JPanel panelcote1 = new PanelJVCote();
		panelcote1.setLocation(6, 260);
		add(panelcote1);

		// joueur virtuel 2
		JPanel panelcote2 = new PanelJVCote();
		panelcote2.setLocation(this.fenetrePc.getWidth() - 220, 260);
		add(panelcote2);

		// joueur virtuel face 1
		JPanel panelcote3 = new PanelJVFace();
		panelcote3.setLocation(6, 6);
		add(panelcote3);

		// joueur virtuel face 1
		JPanel panelcote4 = new PanelJVFace();
		panelcote4.setLocation(this.fenetrePc.getWidth() / 2 - 230, 6);
		add(panelcote4);

		// joueur virtuel face 1
		JPanel panelcote5 = new PanelJVFace();
		panelcote5.setLocation(this.fenetrePc.getWidth() - 510, 6);
		add(panelcote5);
		
		JPanel panel_1 = new PanelEspaceCommun();
		panel_1.setLocation(220, 222);
		add(panel_1);
		
		JPanel panel_3 = new PanelLancerDe();
		panel_3.setLocation(1500, 222);
		add(panel_3);
		
		JPanel panel_2 = new PanelCarteJouee();
		panel_2.setLocation(1500, 359);
		add(panel_2);
		
		
	}

	public void ajouterVueJP() {

	}

	public void ajouterVueJVCote() {

	}

	public void ajouterVueJVFace() {

	}

	public void ajouterVueJVSommet() {

	}

	public static void main(String[] args) {
		Fenetre b = new Fenetre();
		b.setVisible(true);
	}
}
