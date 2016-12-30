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
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sdz.modele.JoueurVirtuel;

public class PanelJVCote extends JPanel {
	private JoueurVirtuel jVT;
	private JFrame fenetreGuidee;

	public PanelJVCote() {
		super();
		// this.jP = jP;

		this.fenetreGuidee = new JFrame();
		this.fenetreGuidee.setTitle("Les cartes Guidée");
		this.fenetreGuidee.setSize(1400, 700);
		this.fenetreGuidee.setResizable(false);
		this.fenetreGuidee.setLocation(200, 50);

		this.setLayout(null);
		this.setSize(208, 585);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		JLabel lblNom = new JLabel("Nom: ");
		lblNom.setBounds(12, 489, 56, 16);
		add(lblNom);

		JLabel lblPointDaction = new JLabel("Point d'Action: ");
		lblPointDaction.setBounds(12, 518, 98, 16);
		add(lblPointDaction);

		JButton btnAfficherLesCartes = new JButton("Afficher les Cartes Guid\u00E9es");
		btnAfficherLesCartes.setBounds(12, 547, 190, 25);
		btnAfficherLesCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fenetreGuidee.setVisible(true);
			}
		});
		add(btnAfficherLesCartes);

		for (int i = 0; i < 7; i++) {
			JButton button = new JButton("New button");
			button.setBounds(26, 6 + 60 * i, 160, 114);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/derriereHorizontale.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(160, 114, image.SCALE_SMOOTH));
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
		j.setResizable(false);
		j.getContentPane().add(new PanelJVCote());
		j.setVisible(true);

	}

}
