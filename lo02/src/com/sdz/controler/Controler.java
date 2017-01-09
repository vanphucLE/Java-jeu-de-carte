package com.sdz.controler;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.modele.EffectuerCapacite;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.Partie;
import com.sdz.vue.PanelJeu;

public class Controler {
	private Partie partie;
	private JoueurPhysique jP;
	private PanelJeu panelJeu;
	private EffectuerCapacite effectuerCapacite;

	public Controler(Partie partie) {
		this.partie = partie;
		this.effectuerCapacite = new EffectuerCapacite(partie);
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
				this.jP.jouerApocalypse(carte);
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
			System.out.println(carte);
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
		this.panelJeu.dessinerPanelCarteJouee(carte);
		this.jP.sacrifierCarte(carte);
	}

	public void empecherCroyant(Joueur joueur, String dogme1, String dogme2, CarteAction carte) {
		this.effectuerCapacite.empecherCroyant(joueur, dogme1, dogme2, carte);
		this.jP.setActionEnTrain("sacrifier");
	}

	// capa carte id 12
	public void recupererGuideSpirituel(Joueur joueur, CarteAction carte) {
		this.effectuerCapacite.recupererGuideSpirituel(joueur, carte);
		this.jP.setActionEnTrain("sacrifier");
	}

	public void deffauserGuideSpirituel(Joueur joueur, CarteAction carte) {
		this.effectuerCapacite.deffauserGuideSpirituel(joueur, carte);
		this.jP.setActionEnTrain("sacrifier");
	}

	public void beneficierCapacite(Joueur joueur, CarteAction carte) {
		joueur.setSacrifice(true);
		this.panelJeu.dessinerPanelCarteJouee(carte);
		joueur.sacrifierCroyant(carte.getId(), this.partie);
		this.jP.setActionEnTrain("sacrifier");
	}

	// capa id=50
	public void sacrifierGuideSpirituelCHAOS(Joueur joueur, CarteAction carte) {
		joueur.setSacrifice(true);
		this.panelJeu.dessinerPanelCarteJouee(carte);
		this.effectuerCapacite.sacrifierGuideSpirituelCHAOS(joueur, carte);
		this.jP.setActionEnTrain("sacrifier");
	};

	// capa carte id 54
	public void recupererGuideSpirituel2(Joueur joueur, CarteAction carte) {
		this.effectuerCapacite.recupererGuideSpirituel2(joueur, carte);
	}

	// Capa carte id 55
	private CarteAction carteG_1;

	public void choisirGuideSpirituelEchanger_1(CarteAction carte) {
		this.carteG_1 = carte;
		JOptionPane.showMessageDialog(null, "Choissiez une carte Guide Spirituel de l'autre joueur pour échanger!");
		this.jP.setActionEnTrain("choisirGuideSpirituelEchanger_2");
	}

	public void choisirGuideSpirituelEchanger_2(CarteAction carte, Joueur joueur) {
		this.effectuerCapacite.echangerGuideSpirituel((Joueur) this.jP, this.carteG_1, joueur, carte);
		this.jP.setActionEnTrain("sacrifier");
	}

	// Capa id 66
	public void beneficierSansSacrifier(CarteAction carte) {
		carte.effectuerCapaciteSpecial(this.partie);
		this.jP.setActionEnTrain("jouer");
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
