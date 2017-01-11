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

		Joueur j = this.jP;
		this.fenetreGuidee = new FenetreGuidee(j);
		this.fenetreGuidee.setControler(this.ctrl);
		this.jP.addObserver(this.fenetreGuidee);

		this.setLayout(null);
		this.setSize(1390, 247);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		this.setBackground(Color.GREEN);

		this.dessinerNom();
		this.dessinerPtAction();
		// this.dessinerCarteDivinite();
		// this.dessinerCarteAction();
	}

	public void updateFenetreGuidee() {

	}

	public void dessinerNom() {
		JLabel lblNom = new JLabel("Nom: " + this.jP.getNom());
		lblNom.setBounds(310, 7, 257, 16);
		add(lblNom);

		JButton btnAfficherLesCartes = new JButton("Espace Guidée");
		btnAfficherLesCartes.setBounds(1172, 3, 193, 25);
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

		JLabel lblPtprire = new JLabel("PtPri\u1EBBre: " + this.jP.getPtPriere());
		lblPtprire.setBounds(744, 7, 78, 16);
		add(lblPtprire);

		JButton btnFinirJouer = new JButton("Finir Jouer");
		btnFinirJouer.setBounds(1015, 3, 97, 25);
		btnFinirJouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (jP.getActionEnTrain().equals("jouer") || jP.getActionEnTrain().equals("sacrifier")) {
					jP.setActionEnTrain("");
					ctrl.finir();
				}
			}
		});
		add(btnFinirJouer);

		JButton btnFinirDfausserCartes = new JButton("Finir mes choices");
		btnFinirDfausserCartes.setBounds(834, 3, 169, 25);
		btnFinirDfausserCartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA  " + jP.getActionEnTrain());
				if (jP.getActionEnTrain().equals("defausser")) {
					jP.setActionEnTrain("");
					ctrl.finir();
				} else if (jP.getActionEnTrain().equals("guiderCroyant")) {
					ctrl.ajouterGuidee();
				}
			}
		});
		add(btnFinirDfausserCartes);
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
			carteDivin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jP.getActionEnTrain().equals("sacrifier")) {
						jP.getLaMain().getCarteDivinite().effectuerCapaciteSpecial(ctrl.getPartie());
					}
				}
			});
			add(carteDivin);

		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}

		JLabel lblCarteDivinit = new JLabel("Carte divinit\u00E9");
		lblCarteDivinit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCarteDivinit.setBounds(87, 7, 150, 16);
		add(lblCarteDivinit);

		this.dessinerCarteAction();
	}

	public void dessinerCarteAction() {
		// LinkedList<JButton> boutonsCarte = new LinkedList<JButton>();
		int indice = 0;
		for (CarteAction carte : this.jP.getLaMain().getListeCarteA()) {
			indice++;
			JButton button = new JButton();
			button.setBounds(158 + (152 * 7 / this.jP.getLaMain().getListeCarteA().size()) * indice, 32, 150, 210);

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
		if (jP.getElimine()) {
			this.setVisible(false);
		}
		this.removeAll();
		this.dessinerNom();
		this.dessinerPtAction();
		this.dessinerCarteDivinite();
		this.dessinerCarteAction();
		this.repaint();
		this.validate();
	}
}
