package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.GuideSpirituel;

public class JoueurPhysique extends Joueur {

	public JoueurPhysique(String nom, int age) {
		super(1, nom, age);
		this.bot = false;
	}

	@Override
	public void jouer(Partie partie) {
		this.seDefausserCartesEtCompleter(partie);
		this.choisirCarte(partie);

		Boolean continu = false;
		int commande = JOptionPane.showConfirmDialog(null, "Voulez-vous sacrifier la cartes ?");

		if (commande == 0) {
			JOptionPane.showMessageDialog(null, "Choissiez la carte dans l'espace Guidee pour la sacifier! ");
			this.actionEnTrain = "sacrifier";

			try {
				this.partie.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void sacrifierCarte(CarteAction carte) {
		/*
		 * Carte Croyant: id :1 -->37 Carte Guide Spirituel: 38-->57 Carte Deus
		 * Ex : 58 --> 75 Carte Apocalypse: 76 --> 80 Carte Divinite: 81 -->90
		 */
		if (1 <= carte.getId() && carte.getId() <= 37) {
			this.sacrifierCroyant(carte.getId(), partie);
		}
		if (38 <= carte.getId() && carte.getId() <= 57) {
			this.sacrifierCroyant(carte.getId(), partie);
		}
	}

	@Override
	public void seDefausserCartesEtCompleter(Partie partie) {
		JeuDeCartes jeuDeCartes = partie.getJeuDeCartes();
		System.out.println("Votre Point Action: (Jour: " + this.ptAction_Jour + ") " + "(Nuit: " + this.ptAction_Nuit
				+ ") " + "(Néant: " + this.ptAction_Neant + ")");
		System.out.println("Les cartes actions tenu dans vôtre main:");
		System.out.println(this.laMain);

		int commande = JOptionPane.showConfirmDialog(null,
				"À vous de jouer!\nVoulez-vous défausser les cartes?\nSi oui,Choissiez les cartes pour les défausser en les cliquant!");
		if (commande == 0) {
			this.actionEnTrain = "defausser";
			try {
				this.partie.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		JOptionPane.showMessageDialog(null, "Complétez sa main à 7 cartes! ");
		this.compeleter7Carte(jeuDeCartes);
	}

	@Override
	public void choisirCarte(Partie partie) {
		System.out.println(partie.getEspaceCommun());
		System.out.println("(Rappeler) Votre Point Action  (Jour: " + this.ptAction_Jour + ") | " + "(Nuit: "
				+ this.ptAction_Nuit + ") | " + "(Néant: " + this.ptAction_Neant + ")");
		Boolean continu = false;

		int commande = JOptionPane.showConfirmDialog(null,
				"Voulez-vous choisir la carte pour jouer?\nSi oui, choissiez la carte pour jouer en la cliquant! ");
		if (commande == 0) {
			this.actionEnTrain = "jouer";

			// On suspend la partie pour attendre le commande du joueur
			// physique
			try {
				this.partie.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public LinkedList<CarteAction> croyantsPeutEtreGuidee() {
		LinkedList<CarteAction> listeCroyants = new LinkedList<CarteAction>();
		EspaceCommun espaceCommun = this.partie.getEspaceCommun();
		for (CarteAction carteA : espaceCommun.getListeCartesPret()) {
			Boolean test = false;
			for (String dogmeA : carteA.getDogme()) {
				for (String dogmeD : this.carteG.getDogme()) {
					if (dogmeD.equals(dogmeA)) {
						test = true;
						break;
					}
				}
				if (test)
					break;
			}
			if (test) {
				listeCroyants.add(carteA);
			}
		}
		return listeCroyants;
	}

	private CarteAction carteG;
	private int nbGuider;
	private LinkedList<CarteAction> listeCroyantsGuidee;

	// Jouer Carte GuideSpirituel
	public void jouerGuideSpirituel(CarteAction carte) {
		EspaceCommun espaceCommun = this.partie.getEspaceCommun();
		this.laMain.seDeffausserCarte(carte);

		this.carteG = carte;
		GuideSpirituel carteG=(GuideSpirituel) this.carteG;
		this.nbGuider = carteG.getNbGuider();
		this.listeCroyantsGuidee = new LinkedList<CarteAction>();

		// On trouve la liste des cartes Croyants qui peuvent être guidées
		LinkedList<CarteAction> listeCroyants = this.croyantsPeutEtreGuidee();
		System.out.println("Les cartes croyants que vous pouvez guider: ");
		System.out.println(listeCroyants);

		if (listeCroyants.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Il n'y a aucun carte croyant que cette carte peut guider!\n .Cette carte est alors défaussée! ");
			this.actionEnTrain = "jouer";
			// Cette carte va être récupérée par le jeu de carte
			this.partie.getJeuDeCartes().recupererCarteAction(carte);
		} else {
			this.carteG.setEstSacrifie(true);
			JOptionPane.showMessageDialog(null, "Vous pouvez faire guider " + carteG.getNbGuider()
					+ " carte(s) Croyant(s).\nChoissiez la carte croyant dans l'espace commun pour la guider en la cliquant! ");
			this.actionEnTrain = "guiderCroyant";
		}
	}

	public void ajouterGuidee() {
		if (this.listeCroyantsGuidee.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Vous ne choissiez aucun carte Croyant guidée. La carte GuideSpirituel est alors défaussée!");
		} else {
			this.laMain.ajouterGuidee(listeCroyantsGuidee, carteG);
		}
	}

	public int getNbGuider() {
		return nbGuider;
	}

	public void ajouterCroyantGuidee(CarteAction carte) {
		carte.setEstSacrifie(true);
		this.partie.getEspaceCommun().supprimerCarte(carte);
		this.listeCroyantsGuidee.add(carte);
		this.nbGuider--;
	}

	public void jouerDeusEx(Partie partie) {

	}

	@Override
	public void jouerApocalypse(CarteAction carte) {
		//
		if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas jouer la carte Apocalypse en ce tour! ");
			System.out.println("Vous ne pouvez pas jouer la carte Apocalypse en ce tour");
		} else {
			this.laMain.seDeffausserCarte(carte);
			this.partie.getJeuDeCartes().recupererCarteAction(carte);
			this.jouerApocalypse_2();
		}
	}

	public void jouerApocalypse_2() {
		this.partie.setEstApocalypseAvant(-1);
		int[] arPriere = new int[partie.getListeJoueurs().size() + 1];
		int indice = -1;
		for (Joueur j : this.partie.getListeJoueurs()) {
			j.setPtPriere();
			indice++;
			arPriere[indice] = j.getPtPriere();
		}
		for (int i = 0; i <= indice - 1; i++) {
			for (int j = i + 1; j <= indice; j++) {
				if (arPriere[i] < arPriere[j]) {
					int tg = arPriere[i];
					arPriere[i] = arPriere[j];
					arPriere[j] = tg;
				}
			}
		}
		if (indice + 1 >= 4) {
			if (arPriere[indice] == arPriere[indice - 1]) {
				System.out.println(
						"Il y a 2 joueur ayant le même point prière dernier. Cette carte Apocalypse est défaussé.");
				JOptionPane.showMessageDialog(null,
						"Il y a 2 joueur ayant le même point prière dernier. Cette carte Apocalypse est défaussé!");
			} else {
				for (Joueur j : partie.getListeJoueurs()) {
					if (j.getPtPriere() == arPriere[indice]) {
						this.partie.eliminerJoueur(j);
						break;
					}
				}
			}
		} else {
			if (arPriere[0] == arPriere[1]) {
				System.out.println(
						"Il y a 2 joueur ayant le même point prière premier. Cette carte Apocalypse est défaussé.");
				JOptionPane.showMessageDialog(null,
						"Il y a 2 joueur ayant le même point prière premier. Cette carte Apocalypse est défaussé!");
			} else {
				for (Joueur j : partie.getListeJoueurs()) {
					if (j.getPtPriere() == arPriere[0]) {
						JOptionPane.showMessageDialog(null, j.getNom() + " a gagné!");
						this.partie.setJoueurgagnant(j);
						this.partie.setEstFini(true);
						break;
					}
				}
			}
		}
	}

	public String getActionEnTrain() {
		return actionEnTrain;
	}

	public void setActionEnTrain(String actionEnTrain) {
		this.actionEnTrain = actionEnTrain;
	}

}