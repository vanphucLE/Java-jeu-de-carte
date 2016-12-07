package com.sdz.modele;

import java.util.Scanner;

import com.sdz.cartes.CarteAction;

public class JoueurVirtuel extends Joueur {
	// private Stategie stagie;
	public JoueurVirtuel(int id, String nom, int age) {
		super(id, nom, age);
		this.laMain = new LaMain();
	}

	@Override
	public void seDefausserCartes(JeuDeCartes jeuDeCartes) {
		//Choisir au hasard le nombre de carte défaussée.
		int nbDe = (int) Math.ceil(Math.random() * 7);
		for (int i = 1; i <= 7; i++) {
			//Choisir au hasard les carte défaussée.
			int nbCa = (int) Math.ceil(Math.random() * i);
			CarteAction carteA=this.laMain.getListeCarteA().remove(nbCa-1);
		}
		

		for (String str : cartesDef) {
			int num = Integer.parseInt(str);
			// la carte défaussées est recupéré dans le jeu de carte.
			CarteAction carteA = this.laMain.seDeffausserCarte(num);
			jeuDeCartes.recupererCarteAction(carteA);
		}
	}

	@Override
	public void Compeleter7Carte(JeuDeCartes jeuDeCartes) {
	}

	@Override
	public void choisirCarteReel(Partie partie) {

	}

	public void choisirCarte() {

	}

	public void JoueurCapaSpe() {

	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}
}
