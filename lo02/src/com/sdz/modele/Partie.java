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

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public int getNbJoueur() {
		return this.nbJoueur;
	}

	public String lancerDe() {
		String[] de = { "", "Jour", "Nuit", "N�ant" };
		int num = (int) Math.ceil(3 * Math.random());
		return de[num];
	}

	// un partie va commencer par appeller cette m�thode
	public void jouer() {
		this.commencerPartie();

	}

	// Cette m�thode est cr�e pour distribuer les cartes au d�but de la partie
	// de jeu de carte
	private void commencerPartie() {
		for (Joueur joueur : this.listeJoueurs) {

			// Prendre une carte Divinit�
			CarteDivinite carteDivinite = jeuDeCartes.piocherCarteDivinite();
			joueur.getLaMain().piocherDivinite(carteDivinite);

			// 7 cartes est distribu�es au joueur
			int compte = 0;
			while (compte < 7) {
				compte++;
				CarteAction carte = jeuDeCartes.distribuerCarteAction();
				joueur.getLaMain().completerCarteAction(carte);
			}
		}
	}

	// D�scrire les action du joueur dans chaque tour
	private void TourDeJeu() {
		Scanner sc = new Scanner(System.in);
		String str = "";
		do {
			System.out.print("Entrez 'Lancer' pour lancer le d�! ");
			str = sc.nextLine();
		} while (str != "Lancer");
		String FaceDe = lancerDe();
		if (FaceDe=="Jour"){
			
		}

	}

	public void annoncerGagnant() {

	}
	/*
	 * public void jouerCarte(CarteAction carte) { switch (carte.getType()) {
	 * case "Croyant": jouerCroyant(); break; case "DeusEx": jouerCroyant();
	 * break; case "Apocalypse": jouerCroyant(); break; case "GuideSpirituel":
	 * jouerCroyant(); break; } }
	 */

}
