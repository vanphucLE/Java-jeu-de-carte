package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;

public class JoueurVirtuel extends Joueur {
	// private Stategie stagie;
	public JoueurVirtuel(int id, String nom, int age) {
		super(id, nom, age);
		this.laMain = new LaMain();
		this.bot = true;
	}

	@Override
	public void jouer(Partie partie) {
		System.out.println("Point Action: (Jour: " + this.ptAction_Jour + ") " + "(Nuit: " + this.ptAction_Nuit + ") "
				+ "(N�ant: " + this.ptAction_Neant + ")");
		this.seDefausserCartesEtCompleter(partie.getJeuDeCartes());
		this.choisirCarte(partie);
		if (this.sacrifice && this.laMain.getListeCroyantGuidee().size() != 0) {
			this.sacrifierCroyant(this.laMain.getListeCroyantGuidee().get(0).get(0).getId(), partie);
		}
	}

	@Override
	public void seDefausserCartesEtCompleter(JeuDeCartes jeuDeCartes) {
		// Choisir au hasard le nombre de carte d�fauss�e.
		System.out.println("Les cartes actions tenu dans sa main:");
		System.out.println(this.laMain);
		int nbDe = (int) Math.ceil(Math.random() * 7);
		LinkedList<Integer> ids = new LinkedList<Integer>();
		LinkedList<CarteAction> cartesRecupere = new LinkedList<CarteAction>();
		for (int i = nbDe; i >= 1; i--) {
			// Choisir au hasard les carte d�fauss�e.
			int nbCa = (int) Math.ceil(Math.random() * this.getLaMain().getListeCarteA().size());
			CarteAction carteA = this.laMain.getListeCarteA().remove(nbCa - 1);
			cartesRecupere.add(carteA);
			ids.add(carteA.getId());
		}
		System.out.println("Il a d�fauss� les cartes qui ont les Id en : " + ids);
		// jeuDeCartes recup�re les cartes action apr�s le joueur compl�te 7
		// cartes.
		this.Compeleter7Carte(jeuDeCartes);
		for (CarteAction carte : cartesRecupere) {
			jeuDeCartes.recupererCarteAction(carte);
		}
	}

	@Override
	public void choisirCarte(Partie partie) {
		CarteAction carteChoisi = new CarteAction();
		for (CarteAction carteA : this.laMain.getListeCarteA()) {
			if (carteA.getOrigine().equals("Jour") && this.testEntree(carteA, partie)) {
				if (this.ptAction_Jour > 0) {
					carteChoisi = carteA;
					this.ptAction_Jour--;
				}
			} else if (carteA.getOrigine().equals("Nuit") && this.testEntree(carteA, partie)) {
				if (this.ptAction_Nuit > 0) {
					carteChoisi = carteA;
					this.ptAction_Nuit--;
				}
			} else if (carteA.getOrigine().equals("N�ant") && this.testEntree(carteA, partie)) {
				if (this.ptAction_Neant > 0) {
					carteChoisi = carteA;
					this.ptAction_Neant--;
				} else if (this.ptAction_Nuit >= 2) {
					carteChoisi = carteA;
					this.ptAction_Nuit -= 2;
				} else if (this.ptAction_Jour >= 2) {
					carteChoisi = carteA;
					this.ptAction_Jour -= 2;
				}
			}
			if (carteChoisi.estEgal(carteA)) {
				break;
			}
		}
		if (carteChoisi.getId() != 0) {
			System.out.println(this.nom + " a jou� la carte: " + carteChoisi);
		}
		switch (carteChoisi.getType()) {
		case "Croyant":
			this.jouerCroyant(carteChoisi, partie.getEspaceCommun());
			break;
		case "GuideSpirituel":
			this.jouerGuideSpirituel(carteChoisi, partie.getEspaceCommun());
			break;
		case "DeusEx":
			this.jouerDeusEx(partie);
			break;
		case "Apocalypse":
			this.jouerApocalypse(carteChoisi, partie);
			break;
		}

	}

	private void jouerGuideSpirituel(CarteAction carte, EspaceCommun espaceCommun) {
		GuideSpirituel carteG = (GuideSpirituel) carte;
		LinkedList<CarteAction> listeCroyantsGuidee = new LinkedList<CarteAction>();
		int indice = 0;
		for (CarteAction carteA : espaceCommun.getListeCartesPret()) {
			Boolean test = false;
			for (String dogmeA : carteA.getDogme()) {
				for (String dogmeD : carteG.getDogme()) {
					if (dogmeD.equals(dogmeA)) {
						test = true;
						break;
					}
				}
			}
			if (test == true && indice < carteG.getNbGuider()) {
				indice++;
				listeCroyantsGuidee.add(carteA);
			}
		}
		this.laMain.ajouterGuidee(listeCroyantsGuidee, carteG);
	}

	private Boolean testEntree(CarteAction carte, Partie partie) {
		Boolean test = true;
		if (carte.getType().equals("GuideSpirituel")) {
			test = false;
			if (carte.getType().equals("GuideSpitituel")) {
				for (CarteAction carteA : partie.getEspaceCommun().getListeCartesPret()) {
					for (String dogmeA : carteA.getDogme()) {
						for (String dogmeD : carte.getDogme()) {
							if (dogmeD.equals(dogmeA)) {
								test = true;
								break;
							}
						}
					}
				}
			}
		}
		if (carte.getType().equals("Apocalypse")) {
			if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
				test = false;
			}
		}
		return test;
	}

	private void jouerDeusEx(Partie partie) {

	}

	private void jouerApocalypse(CarteAction carte, Partie partie) {
		partie.setEstApocalypseAvant(-1);
		int[] arPriere = {};
		int indice = -1;
		for (Joueur j : partie.getListeJoueurs()) {
			j.setPtPriere();
			arPriere[indice++] = j.getPtPriere();
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
						"Il y a 2 joueur ayant le m�me point pri�re dernier. Cette carte Apocalypse est d�fauss�.");
			} else {
				for (Joueur j : partie.getListeJoueurs()) {
					if (j.getPtPriere() == arPriere[indice]) {
						partie.eliminerJoueur(j);
						break;
					}
				}
			}
		} else {
			if (arPriere[0] == arPriere[1]) {
				System.out.println(
						"Il y a 2 joueur ayant le m�me point pri�re premier. Cette carte Apocalypse est d�fauss�.");
			} else {
				for (Joueur j : partie.getListeJoueurs()) {
					if (j.getPtPriere() == arPriere[0]) {
						partie.setJoueurgagnant(j);
						partie.setEstFini(true);
						break;
					}
				}
			}
		}
	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}
}
