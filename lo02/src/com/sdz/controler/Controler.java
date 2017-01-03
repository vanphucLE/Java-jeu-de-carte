package com.sdz.controler;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.Partie;
import com.sdz.vue.PanelJeu;

public class Controler {
	private Partie partie;
	private JoueurPhysique jP;
	private PanelJeu panelJeu;

	public Controler(Partie partie) {
		this.partie = partie;
		this.jP = (JoueurPhysique) this.partie.getListeJoueurs().get(0);
	}

	public void defausserCarte(CarteAction carte) {
		CarteAction carteA = jP.getLaMain().seDeffausserCarte(carte.getId());
		this.partie.getJeuDeCartes().recupererCarteAction(carte);
	}

	public void finirDefausser() {
		this.partie.resume();
	}

	public void jouerCarte(CarteAction carte) {
		Boolean valid = this.setPtAction(carte);
		if (valid) {
			this.panelJeu.dessinerPanelCarteJouee(carte);
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			switch (carte.getType()) {
			case "Croyant":
				this.jP.jouerCroyant(carte, partie.getEspaceCommun());
				break;
			case "GuideSpirituel":
				this.jP.jouerGuideSpirituel(carte, partie.getEspaceCommun());
				break;
			case "DeusEx":
				this.jP.jouerDeusEx(partie);
				break;
			case "Apocalypse":
				this.jP.jouerApocalypse(carte, partie);
				break;
			}
		}
		this.partie.resume();
	}

	// on utilise cette méthode pour mettre à jour le point d'action de joueur
	// après qu'il a choisi une carte pour jouer.
	private Boolean setPtAction(CarteAction carte) {
		if (carte.getOrigine() != "") {
			if (carte.getOrigine().equals("Jour")) {
				if (this.jP.getPtAction_Jour() == 0) {
					System.out.println("Eurreur en choissant!!");
					JOptionPane.showMessageDialog(null,
							"Vous n'avez pas assez point d'action Jour pour jouer cette carte!");
					return false;
				} else {
					this.jP.setPtAction_Jour(this.jP.getPtAction_Jour() - 1);
				}
			} else if (carte.getOrigine().equals("Nuit")) {
				if (this.jP.getPtAction_Nuit() == 0) {
					System.out.println("Eurreur en choissant!!");
					JOptionPane.showMessageDialog(null,
							"Vous n'avez pas assez point d'action Nuit pour jouer cette carte!");
					return false;
				} else {
					this.jP.setPtAction_Nuit(this.jP.getPtAction_Nuit() - 1);
				}
			} else if (carte.getOrigine().equals("Néant")) {
				if (this.jP.getPtAction_Neant() > 0) {
					this.jP.setPtAction_Neant(this.jP.getPtAction_Neant() - 1);
				} else if (this.jP.getPtAction_Jour() >= 2) {
					this.jP.setPtAction_Jour(this.jP.getPtAction_Jour() - 2);
				} else if (this.jP.getPtAction_Nuit() >= 2) {
					this.jP.setPtAction_Nuit(this.jP.getPtAction_Nuit() - 2);
				} else {
					System.out.println("Eurreur en choissant!!");
					JOptionPane.showMessageDialog(null, "Vous n'avez pas assez point d'action pour jouer cette carte!");
					return false;
				}
			}
		}
		return true;
	}

	public void lancerDe() {
		this.partie.resume();
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPanelJeu(PanelJeu panelJeu) {
		this.panelJeu = panelJeu;
	}

}
