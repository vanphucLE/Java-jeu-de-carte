package com.sdz.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.Apocalypse;
import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituels;

public class JeuDeCartes {
	// On cr�e 2 attributes pour contenir Carte Action et Carte Divinit�
	private LinkedList<CarteAction> listeCartesAction;
	private LinkedList<CarteDivinite> listeCartesDivinite;
	private int nbCarteAction;

	public JeuDeCartes() {
		this.listeCartesAction = new LinkedList<CarteAction>();
		this.listeCartesDivinite = new LinkedList<CarteDivinite>();

		// Cr�e les Cartes Croyants
		for (int i = 1; i <= 37; i++) {
			Croyant carteCroyant = new Croyant(i);
			this.listeCartesAction.add(carteCroyant);
		}

		// Cr�e les Cartes Guide Spirituels
		for (int i = 38; i <= 57; i++) {
			GuideSpirituels carteGuideSpirituel = new GuideSpirituels(i);
			this.listeCartesAction.add(carteGuideSpirituel);
		}

		// Cr�e les Cartes Apocalypses
		for (int i = 58; i <= 75; i++) {
			Apocalypse carteApocalypse = new Apocalypse(i);
			this.listeCartesAction.add(carteApocalypse);
		}

		// Cr�e les Cartes DeusEx
		for (int i = 76; i <= 80; i++) {
			Croyant carteDeusEx = new Croyant(i);
			this.listeCartesAction.add(carteDeusEx);
		}

		// Cr�e les Cartes Divinit�
		for (int i = 81; i <= 90; i++) {
			CarteDivinite carteDivinite = new CarteDivinite(i);
			this.listeCartesDivinite.add(carteDivinite);
		}
	}

	// M�langer les Cartes Action
	public void melangerCartesAction() {

		for (int i = 0; i < 80; i++) {
			int position = (int) Math.round((80 - 1) * Math.random());
			CarteAction carteAction = this.listeCartesAction.pop();
			this.listeCartesAction.add(position, carteAction);
		}
	}

	// M�langer les Cartes Divinit�s
	public void melangerCartesDivinite() {
		for (int i = 81; i < 90; i++) {
			int position = (int) Math.round((90 - 81) * Math.random()) + 80;
			CarteDivinite carteDivinite = this.listeCartesDivinite.pop();
			this.listeCartesDivinite.add(position, carteDivinite);
		}
	}

	// Distribuer 7 Cartes Actions au d�but de la partie
	public LinkedList<CarteAction> distribuerCartesAction() {
		LinkedList<CarteAction> cartesA = new LinkedList<CarteAction>();
		while ((this.listeCartesAction.size() > 0) && (cartesA.size() <= 7)) {
			CarteAction ca = this.listeCartesAction.removeLast();
			cartesA.add(ca);
		}
		return cartesA;
	}

	// La carte action va �tre r�cup�r� au jeu de carte, lors que elle est
	// d�fauss�e ou sacrifi�e.
	// Ensuite, le jeu de carte va �tre m�lang� avant de commencer le nouveau
	// tour.
	public void recupererCarteAction(CarteAction carteAction) {
		this.listeCartesAction.add(carteAction);
		this.melangerCartesAction();
	}

	public LinkedList<CarteAction> getListeCartesAction() {
		return this.listeCartesAction;
	}

	public LinkedList<CarteDivinite> getListeCartesDivinite() {
		return this.listeCartesDivinite;
	}

	public int getNbCarteAction() {
		return nbCarteAction;
	}

	public void setNbCarteAction(int nbCarteAction) {
		this.nbCarteAction = nbCarteAction;
	}

}
