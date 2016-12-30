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

import com.sdz.modele.JoueurVirtuel;

public class PanelJVCote extends JPanel implements Observer {
	private JoueurVirtuel jVT;
	private JFrame fenetreGuidee;

	public PanelJVCote() {
		super();
		// this.jP = jP;

		this.fenetreGuidee = new JFrame();
		this.fenetreGuidee.setTitle("Les cartes Guidée");
		this.fenetreGuidee.setSize(1, 700);
		this.fenetreGuidee.setResizable(false);
		this.fenetreGuidee.setLocation(200, 50);

		this.setLayout(null);
		this.setSize(208, 804);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		JLabel lblNom = new JLabel("Nom: ");
		lblNom.setBounds(12, 719, 56, 16);
		add(lblNom);

		JLabel lblPointDaction = new JLabel("PtAction[Jour:    |Nuit:    |Néant:    ]");
		lblPointDaction.setBounds(12, 743, 190, 16);
		add(lblPointDaction);

		JButton btnAfficherLesCartes = new JButton("Cartes Guid\u00E9es");
		btnAfficherLesCartes.setBounds(12, 772, 190, 25);
		btnAfficherLesCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fenetreGuidee.setVisible(true);
			}
		});
		add(btnAfficherLesCartes);

		JButton button = new JButton("New button");
		button.setBounds(32, 496, 150, 210);
		try {
			BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
			button.setIcon(icon);
			button.setMargin(new Insets(0, 10, 0, 0));
			add(button);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		for (int i = 0; i < 7; i++) {
			button = new JButton("New button");
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.setResizable(false);
		j.getContentPane().add(new PanelJVCote());
		j.setVisible(true);

	}

}
