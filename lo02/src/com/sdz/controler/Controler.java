package com.sdz.controler;

import java.util.LinkedList;

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

	public void finir() {
		this.partie.resume();
	}

	public void jouerCarte(CarteAction carte) {
		Boolean valid = this.setPtAction(carte);
		if (valid) {
			if ((partie.getEstApocalypseAvant() != 0 && partie.getEstApocalypseAvant() != -1)
					|| !carte.getType().equals("Apocalypse")) {
				this.panelJeu.dessinerPanelCarteJouee(carte);
			}
			switch (carte.getType()) {
			case "Croyant":
				this.jP.setActionEnTrain("jouerCroyant");
				this.jP.jouerCroyant(carte, partie.getEspaceCommun());
				this.jP.setActionEnTrain("jouer");
				break;
			case "GuideSpirituel":
				this.jP.setActionEnTrain("jouerGuideSpirituel");
				this.jP.jouerGuideSpirituel(carte);
				break;
			case "DeusEx":
				this.jP.setActionEnTrain("jouerDeusEx");
				this.jP.jouerDeusEx(partie);
				this.jP.setActionEnTrain("jouer");
				break;
			case "Apocalypse":
				this.jP.setActionEnTrain("jouerApocalypse");
				this.jP.jouerApocalypse(carte, partie);
				this.jP.setActionEnTrain("jouer");
				break;
			}
		}
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

	public void guiderCroyant(CarteAction carte) {
		if (this.jP.getNbGuider() > 0) {
			LinkedList<CarteAction> listeCroyants = this.jP.croyantsPeutEtreGuidee();
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			System.out.println(listeCroyants);
			if (listeCroyants.indexOf(carte) == -1) {
				JOptionPane.showMessageDialog(null,
						"Vous ne pouvez pas faire guider cette Carte Croyant!\nChoissiez l'autre carte!");
			} else {
				this.jP.ajouterCroyantGuidee(carte);
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Vous ne pouvez faire guider la carte Croyant plus!\nCliquez button - Finir mes choices!");
		}
	}

	// quand on fini de choisir carte croyant guidées, on va ajouter les cartes
	// à la main
	public void ajouterGuidee() {
		this.jP.ajouterGuidee();
		this.jP.setActionEnTrain("jouer");
		JOptionPane.showMessageDialog(null,
				"Vous avez fait guider les cartes. Ces Cartes est transmis à l'Espace Guidée!");
	}

	public void sacrifier(CarteAction carte) {
		// Do something

		this.partie.resume();
	}

	public void lancerDe() {
		this.jP.setActionEnTrain("");
		this.partie.resume();
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPanelJeu(PanelJeu panelJeu) {
		this.panelJeu = panelJeu;
	}

}
