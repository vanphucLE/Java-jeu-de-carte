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
	}

	@Override
	public void seDefausserCartes(JeuDeCartes jeuDeCartes) {
		// Choisir au hasard le nombre de carte défaussée.
		System.out.println("Les cartes actions tenu dans sa main:");
		System.out.println(this.laMain);
		int nbDe = (int) Math.ceil(Math.random() * 7);
		LinkedList<Integer> ids = new LinkedList<Integer>();
		for (int i = nbDe; i >= 1; i--) {
			// Choisir au hasard les carte défaussée.
			int nbCa = (int) Math.ceil(Math.random() * this.getLaMain().getListeCarteA().size());
			CarteAction carteA = this.laMain.getListeCarteA().remove(nbCa - 1);
			jeuDeCartes.recupererCarteAction(carteA);
			ids.add(carteA.getId());
		}
		System.out.println("Il a défaussé les cartes qui ont les Id en : " + ids);
	}

	@Override
	public void choisirCarte(Partie partie) {
		CarteAction carteChoisi;
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
			} else if (carteA.getOrigine().equals("Néant") && this.testEntree(carteA, partie)) {
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
			if (carteChoisi.equals(carteA)) {
				break;
			}
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

	private void jouerGuideSpirituel(CarteAction carte, EspaceCommun espaceCommun){
		GuideSpirituel carteG= (GuideSpirituel)carte;
		LinkedList<Croyant> listeCroyantsGuidee = new LinkedList<Croyant>();
		for(CarteAction )
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
		return test;
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
