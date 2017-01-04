package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.sdz.cartes.CarteAction;
import com.sdz.controler.Controler;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurPhysique;

public class PanelJP extends JPanel implements Observer {

	private JoueurPhysique jP;
	private Controler ctrl;
	private FenetreGuidee fenetreGuidee;
	private LinkedList<CarteAction> listeCarteChoisi;

	/**
	 * Create the panel.
	 */
	public PanelJP(JoueurPhysique jP, Controler ctrl) {
		super();
		this.jP = jP;
		this.ctrl = ctrl;

		Joueur j=this.jP;
		this.fenetreGuidee = new FenetreGuidee(j);
		this.jP.addObserver(this.fenetreGuidee);
		
		this.setLayout(null);
		this.setSize(1390, 247);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		this.dessinerNom();
		this.dessinerPtAction();
		this.dessinerCarteDivinite();
		this.dessinerCarteAction();
	}

	public void updateFenetreGuidee() {

	}

	public void dessinerNom() {
		JLabel lblNom = new JLabel("Nom: " + this.jP.getNom());
		lblNom.setBounds(310, 7, 257, 16);
		add(lblNom);

		JButton btnAfficherLesCartes = new JButton("Cartes Guid\u00E9es");
		btnAfficherLesCartes.setBounds(1157, 3, 193, 25);
		btnAfficherLesCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fenetreGuidee.setVisible(true);
			}
		});
		add(btnAfficherLesCartes);
	}

	public void dessinerPtAction() {
		JLabel lblPointDaction = new JLabel("PtAction[Jour:" + this.jP.getPtAction_Jour() + " |Nuit:"
				+ this.jP.getPtAction_Nuit() + " |Néant:" + this.jP.getPtAction_Neant() + " ]");
		lblPointDaction.setBounds(520, 7, 265, 16);
		add(lblPointDaction);
	}

	public void dessinerCarteDivinite() {

		JButton carteDivin = new JButton("Carte Divinité");
		carteDivin.setBounds(6, 32, 292, 210);
		try {
			BufferedImage image = ImageIO
					.read(new File("cartes/" + this.jP.getLaMain().getCarteDivinite().getId() + ".PNG"));//
			ImageIcon icon = new ImageIcon(image.getScaledInstance(292, 210, image.SCALE_SMOOTH));
			carteDivin.setIcon(icon);
			carteDivin.setMargin(new Insets(0, 10, 0, 0));
			add(carteDivin);

			JLabel lblCarteDivinit = new JLabel("Carte divinit\u00E9");
			lblCarteDivinit.setHorizontalAlignment(SwingConstants.CENTER);
			lblCarteDivinit.setBounds(87, 7, 150, 16);
			add(lblCarteDivinit);

			JButton btnFinirDfausserCartes = new JButton("Finir mes choices");
			btnFinirDfausserCartes.setBounds(909, 3, 169, 25);
			btnFinirDfausserCartes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (jP.getActionEnTrain().equals("defausser") || jP.getActionEnTrain().equals("guider")) {
						jP.setActionEnTrain("");
						ctrl.finir();
					}
				}
			});
			add(btnFinirDfausserCartes);

		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.dessinerCarteAction();
	}

	public void dessinerCarteAction() {
		// LinkedList<JButton> boutonsCarte = new LinkedList<JButton>();
		int indice = 0;
		for (CarteAction carte : this.jP.getLaMain().getListeCarteA()) {
			indice++;
			JButton button = new JButton();
			button.setBounds(158 + 152 * indice, 32, 150, 210);

			try {
				BufferedImage image = ImageIO.read(new File("cartes/" + carte.getId() + ".PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 0, 0, 0));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jP.getActionEnTrain().equals("defausser")) {
							ctrl.defausserCarte(carte);
						} else if (jP.getActionEnTrain().equals("jouer")) {
							ctrl.jouerCarte(carte);
						} else if (jP.getActionEnTrain().equals("guider")) {
							ctrl.guiderCroyant(carte);
						}
					}
				});
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.dessinerNom();
		this.dessinerPtAction();
		this.dessinerCarteDivinite();
		this.dessinerCarteAction();
		this.repaint();
		this.validate();
	}
}
