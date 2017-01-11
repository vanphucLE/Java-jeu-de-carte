package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.Border;

import com.sdz.cartes.CarteAction;
import com.sdz.modele.JoueurVirtuel;

public class PanelJVCote extends PanelJV {
	private JoueurVirtuel jVT;

	public PanelJVCote(JoueurVirtuel jV) {
		super(jV);
		// this.jP = jP;

		this.setLayout(null);
		this.setSize(208, 777);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		this.dessinerPtAction();
		this.dessinerNom();
		// this.dessinerCarteDivinite();
	}

	@Override
	public void dessinerCarteDivinite() {
		JButton button = new JButton("New button");
		button.setBounds(0, 495, 210, 150);
		try {
			BufferedImage image = ImageIO
					.read(new File("cartes/" + this.jV.getLaMain().getCarteDivinite().getId() + ".PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(210, 150, image.SCALE_SMOOTH));
			button.setIcon(icon);
			button.setMargin(new Insets(0, 10, 0, 0));
			add(button);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.dessinerCarteAction();
	}

	@Override
	public void dessinerPtAction() {
		JLabel lblPointDaction = new JLabel("PtAction[Jour:" + this.jV.getPtAction_Jour() + " |Nuit:"
				+ this.jV.getPtAction_Nuit() + " |Néant:" + this.jV.getPtAction_Neant() + " ]");
		lblPointDaction.setBounds(10, 687, 190, 16);
		add(lblPointDaction);

		JLabel lblPointPriere = new JLabel("PtPrière: " + this.jV.getPtPriere());
		lblPointPriere.setBounds(10, 705, 190, 16);
		add(lblPointPriere);

	}

	@Override
	public void dessinerNom() {
		JLabel lblNom = new JLabel("Nom: " + this.jV.getNom());
		lblNom.setBounds(10, 658, 190, 16);
		add(lblNom);

		JButton btnAfficherLesCartes = new JButton("Espace Guidée");
		btnAfficherLesCartes.setBounds(10, 735, 190, 25);
		btnAfficherLesCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fenetreGuidee.setVisible(true);
			}
		});
		add(btnAfficherLesCartes);

	}

	@Override
	public void dessinerCarteAction() {
		int indice = -1;
		for (CarteAction carte : this.jV.getLaMain().getListeCarteA()) {
			indice++;
			JButton button = new JButton("");
			button.setBounds(26, 6 + (60 * indice * 7 / this.jV.getLaMain().getListeCarteA().size()), 160, 114);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/derriereHorizontale.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(160, 114, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 0, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
