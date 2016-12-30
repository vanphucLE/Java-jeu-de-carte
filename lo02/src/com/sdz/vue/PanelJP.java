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
import com.sdz.controler.Controler;
import com.sdz.modele.JoueurPhysique;
import javax.swing.SwingConstants;

public class PanelJP extends JPanel implements Observer {

	private JoueurPhysique jP;
	private Controler ctr;
	private JFrame fenetreGuidee;

	/**
	 * Create the panel.
	 */
	public PanelJP() {
		super();
		// this.jP = jP;

		this.fenetreGuidee = new JFrame();
		this.fenetreGuidee.setTitle("Les cartes Guidée");
		this.fenetreGuidee.setSize(1580, 700);
		this.fenetreGuidee.setResizable(false);
		this.fenetreGuidee.setLocation(200, 50);

		this.setLayout(null);
		this.setSize(1248, 247);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		JLabel lblNom = new JLabel("Nom: ");
		lblNom.setBounds(243, 7, 56, 16);
		add(lblNom);

		JLabel lblPointDaction = new JLabel("Point d'Action [Jour:    |Nuit:    |Néant:    ]");
		lblPointDaction.setBounds(520, 7, 265, 16);
		add(lblPointDaction);

		JButton btnAfficherLesCartes = new JButton("Cartes Guid\u00E9es");
		btnAfficherLesCartes.setBounds(856, 3, 193, 25);
		btnAfficherLesCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fenetreGuidee.setVisible(true);
			}
		});
		add(btnAfficherLesCartes);

		JButton button = new JButton("New button");
		button.setBounds(6, 32, 150, 210);
		try {
			BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
			button.setIcon(icon);
			button.setMargin(new Insets(0, 10, 0, 0));
			add(button);
			
			JLabel lblCarteDivinit = new JLabel("Carte divinit\u00E9");
			lblCarteDivinit.setHorizontalAlignment(SwingConstants.CENTER);
			lblCarteDivinit.setBounds(6, 7, 150, 16);
			add(lblCarteDivinit);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}

		for (int i = 0; i < 7; i++) {
			button = new JButton("New button");
			button.setBounds(180 + 152 * i, 32, 150, 210);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/1.PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 10, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		// this.creerVu();
	}

	public void updateFenetreGuidee() {

	}

	public void creerVu() {
		for (CarteAction carte : this.jP.getLaMain().getListeCarteA()) {

		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.getContentPane().add(new PanelJP());
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
