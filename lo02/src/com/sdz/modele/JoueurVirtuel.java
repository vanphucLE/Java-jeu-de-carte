package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

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
		if (this.ptAction_Neant > 0) {
			for (CarteAction carte : this.getLaMain().getListeCarteA()) {
				if (carte.getOrigine().equals("Néant")) {
					this.ptAction_Neant--;
					this.getLaMain().seDeffausserCarte(carte);
					carteChoisi = carte;
					break;
				}
				;
			}
		} else if (this.ptAction_Jour >= 2) {
			for (CarteAction carte : this.getLaMain().getListeCarteA()) {
				if (carte.getOrigine().equals("Néant")) {
					this.ptAction_Jour -= 2;
					this.getLaMain().seDeffausserCarte(carte);
					carteChoisi = carte;
					break;
				}
				;
			}
		} else if (this.ptAction_Nuit >= 2) {
			for (CarteAction carte : this.getLaMain().getListeCarteA()) {
				if (carte.getOrigine().equals("Néant")) {
					this.ptAction_Nuit -= 2;
					this.getLaMain().seDeffausserCarte(carte);
					carteChoisi = carte;
					break;
				}
				;
			}
		} else if (this.ptAction_Jour >= 1) {
			for (CarteAction carte : this.getLaMain().getListeCarteA()) {
				if (carte.getOrigine().equals("Jour")) {
					this.ptAction_Jour--;
					this.getLaMain().seDeffausserCarte(carte);
					carteChoisi = carte;
					break;
				}
				;
			}
		} else if (this.ptAction_Nuit >= 1) {
			for (CarteAction carte : this.getLaMain().getListeCarteA()) {
				if (carte.getOrigine().equals("Nuit")) {
					this.ptAction_Nuit--;
					this.getLaMain().seDeffausserCarte(carte);
					carteChoisi = carte;
					break;
				}
				;
			}
		}else{
			for (CarteAction carte : this.getLaMain().getListeCarteA()) {
				if (carte.getOrigine().equals("")) {
					this.getLaMain().seDeffausserCarte(carte);
					carteChoisi = carte;
					break;
				}
				;
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

	public void JoueurCapaSpe() {

	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}
}
