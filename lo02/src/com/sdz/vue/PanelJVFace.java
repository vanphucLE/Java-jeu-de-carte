package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
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

import com.sdz.cartes.CarteAction;
import com.sdz.modele.JoueurVirtuel;
import javax.swing.SwingConstants;

public class PanelJVFace extends PanelJV {
	private JoueurVirtuel jVT;
	private JFrame fenetreGuidee;

	public PanelJVFace(JoueurVirtuel jV) {
		super(jV);
		// this.jP = jP;

		this.fenetreGuidee = new JFrame();
		this.fenetreGuidee.setTitle("Les cartes Guidée");
		this.fenetreGuidee.setSize(1400, 700);
		this.fenetreGuidee.setResizable(false);
		this.fenetreGuidee.setLocation(200, 50);

		this.setLayout(null);
		this.setSize(626, 208);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		JLabel lblNom = new JLabel("Nom: ");
		lblNom.setBounds(16, 181, 56, 16);
		add(lblNom);

		JLabel lblPointDaction = new JLabel("Point d'Action [Jour:    |Nuit:    |Néant:    ]");
		lblPointDaction.setBounds(202, 181, 250, 16);
		add(lblPointDaction);

		JButton btnAfficherLesCartes = new JButton("Cartes Guid\u00E9es");
		btnAfficherLesCartes.setBounds(487, 177, 127, 25);
		btnAfficherLesCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fenetreGuidee.setVisible(true);
			}
		});
		add(btnAfficherLesCartes);

		JButton button = new JButton("Carte Divinité");
		button.setBounds(6, 6, 222, 160);
		try {
			BufferedImage image = ImageIO
					.read(new File("cartes/" + this.jV.getLaMain().getCarteDivinite().getId() + ".PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(222,160, image.SCALE_SMOOTH));
			button.setIcon(icon);
			button.setMargin(new Insets(0, 10, 0, 0));
			add(button);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.dessinerCarteAction();

	}

	@Override
	public void dessinerCarteAction() {
		int indice = -1;
		for (CarteAction carte:this.jV.getLaMain().getListeCarteA()) {
			indice++;
			JButton button = new JButton("");
			button.setBounds(228 + 46 * indice, 6, 114, 160);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/derriereVerticale.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(114, 160, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 0, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	// public static void main(String[] args) {
	// JFrame j = new JFrame();
	// j.setSize(j.getMaximumSize());
	// j.setResizable(false);
	// j.getContentPane().add(new PanelJVFace());
	// j.setVisible(true);
	//
	// }

}
