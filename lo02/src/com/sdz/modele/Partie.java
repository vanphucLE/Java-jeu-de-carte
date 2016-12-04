package com.sdz.modele;

import java.util.ArrayList;
import java.util.Scanner;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;

public class Partie {
	private int nbJoueur;
	private Joueur gagnant;
	private Joueur joueurEncours;
	private ArrayList<Joueur> listeJoueurs = new ArrayList();
	private JeuDeCartes jeuDeCartes = new JeuDeCartes();
	private Boolean estFini = false;
	private EspaceCommun espaceCommun = new EspaceCommun();

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public int getNbJoueur() {
		return this.nbJoueur;
	}

	public void lancerDe() {
		String[] de = { "", "Jour", "Nuit", "Néant" };
		int num = (int) Math.ceil(3 * Math.random());
		String FaceDe = de[num];
		if (FaceDe == "Jour") {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Jour") {
					joueur.setPtAction(2);
					joueur.setPtActionOrigine("Jour");
				}
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Aube") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Jour");
				}
			}
		} else if (FaceDe == "Nuit") {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Nuit") {
					joueur.setPtAction(2);
					joueur.setPtActionOrigine("Nuit");
				}
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Crépuscule") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Nuit");
				}
			}
		} else if (FaceDe == "Néant") {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Aube") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Néant");
				}
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Crépuscule") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Néant");
				}
			}
		}
	}

	// un partie va commencer par appeller cette méthode
	public void jouer() {
		this.commencerPartie();
		while (!this.estFini) {

		}

	}

	// Cette méthode est crée pour distribuer les cartes au début de la partie
	// de jeu de carte
	private void commencerPartie() {
		for (Joueur joueur : this.listeJoueurs) {

			// Prendre une carte Divinité
			CarteDivinite carteDivinite = jeuDeCartes.piocherCarteDivinite();
			joueur.getLaMain().piocherDivinite(carteDivinite);

			// 7 cartes est distribuées au joueur
			int compte = 0;
			while (compte < 7) {
				compte++;
				CarteAction carte = jeuDeCartes.distribuerCarteAction();
				joueur.getLaMain().completerCarteAction(carte);
			}
		}
	}

	// Déscrire les actions des joueurs dans chaque tour
	// numCom: numéro du joueur dans listJoueurs qui commence ce tour
	private void TourDeJeu(int numCom) {
		Scanner sc = new Scanner(System.in);
		String str = "";
		do {
			System.out.print("Entrez 'Lancer' pour lancer le dé! ");
			str = sc.nextLine();
		} while (str.equals("Lancer"));
		// lancer dé
		this.lancerDe();
	}

	public void annoncerGagnant() {

	}
	/*
	 * public void jouerCarte(CarteAction carte) { switch (carte.getType()) {
	 * case "Croyant": jouerCroyant(); break; case "DeusEx": jouerCroyant();
	 * break; case "Apocalypse": jouerCroyant(); break; case "GuideSpirituel":
	 * jouerCroyant(); break; } }
	 */
	// s

}
