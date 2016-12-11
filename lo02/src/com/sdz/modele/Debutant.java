package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

public class Debutant implements Stategie {

	public LinkedList<CarteAction> choisirCartesDefausser(Partie partie) {
		Joueur joueurEnCours = partie.getJoueurEncours();
		LinkedList<CarteAction> cartesRecupere = new LinkedList<CarteAction>();
		int nbDe = (int) Math.ceil(Math.random() * 7);
		for (int i = nbDe; i >= 1; i--) {
			// Choisir au hasard les carte d�fauss�e.
			int nbCa = (int) Math.ceil(Math.random() * joueurEnCours.getLaMain().getListeCarteA().size());
			CarteAction carteA = joueurEnCours.laMain.getListeCarteA().remove(nbCa - 1);
			cartesRecupere.add(carteA);
		}
		return cartesRecupere;
	}

	public int choisirIdDivinite(Partie partie) {
		int idChoisi = 0;
		do {
			idChoisi = (int) Math.ceil(Math.random() * (partie.getListeJoueurs().size() + 1));
		} while (idChoisi == partie.getJoueurEncours().getId());
		return idChoisi;
	}

	public void choisirCartes() {
	}

	public void jouerCapaciteSpecial() {

	};

}
