package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

public class Debutant implements Stategie {
	public LinkedList<CarteAction> choisirCartesDefausser(Partie partie){
		Joueur joueurEnCours=partie.getJoueurEncours();
		LinkedList<CarteAction> cartesRecupere=new LinkedList<CarteAction>();
		int nbDe = (int) Math.ceil(Math.random() * 7);
		for (int i = nbDe; i >= 1; i--) {
			// Choisir au hasard les carte défaussée.
			int nbCa = (int) Math.ceil(Math.random() * joueurEnCours.getLaMain().getListeCarteA().size());
			CarteAction carteA = joueurEnCours.laMain.getListeCarteA().remove(nbCa - 1);
			cartesRecupere.add(carteA);
		}
		return cartesRecupere;
	}
	public void choisirCartes(){
	}
	public void jouerCapaciteSpecial(){
		
	};
}
