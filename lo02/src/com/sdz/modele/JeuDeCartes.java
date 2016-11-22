package com.sdz.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.Apocalypse;
import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;

public class JeuDeCartes {
	// On crée 2 attributes pour contenir Carte Action et Carte Divinité
	private LinkedList<CarteAction> listeCartesAction;
	private LinkedList<CarteDivinite> listeCartesDivinite;
	private int nbCarteAction;

	public JeuDeCartes() {
		this.listeCartesAction = new LinkedList<CarteAction>();
		this.listeCartesDivinite = new LinkedList<CarteDivinite>();

		// Crée les Cartes Croyants
		for (int i = 1; i <= 37; i++) {
			Croyant carteCroyant = new Croyant(i);
			this.listeCartesAction.add(carteCroyant);
		}

		// Crée les Cartes Guide Spirituels
		for (int i = 38; i <= 57; i++) {
			GuideSpirituel carteGuideSpirituel = new GuideSpirituel(i);
			this.listeCartesAction.add(carteGuideSpirituel);
		}

		// Crée les Cartes Apocalypses
		for (int i = 58; i <= 75; i++) {
			Apocalypse carteApocalypse = new Apocalypse(i);
			this.listeCartesAction.add(carteApocalypse);
		}

		// Crée les Cartes DeusEx
		for (int i = 76; i <= 80; i++) {
			Croyant carteDeusEx = new Croyant(i);
			this.listeCartesAction.add(carteDeusEx);
		}

		// Crée les Cartes Divinité
		for (int i = 81; i <= 90; i++) {
			CarteDivinite carteDivinite = new CarteDivinite(i);
			this.listeCartesDivinite.add(carteDivinite);
		}
	}

	// Mélanger les Cartes Action
	public void melangerCartesAction() {

		for (int i = 0; i < 80; i++) {
			int position = (int) Math.ceil((80 - 1) * Math.random());
			CarteAction carteAction = this.listeCartesAction.pop();
			this.listeCartesAction.add(position, carteAction);
		}
	}

	// Mélanger les Cartes Divinités
	public void melangerCartesDivinite() {
		for (int i = 81; i < 90; i++) {
			int position = (int) Math.ceil((90 - 81) * Math.random()) + 80;
			CarteDivinite carteDivinite = this.listeCartesDivinite.pop();
			this.listeCartesDivinite.add(position, carteDivinite);
		}
	}

	// Distribuer Carte Action 
	public CarteAction distribuerCarteAction() {
		return this.listeCartesAction.removeLast();
	}

	//Distribuer Carte Divinité au joueur
	public CarteDivinite piocherCarteDivinite(){
		  return this.listeCartesDivinite.removeLast();
	}
	
	// La carte action va être récupéré au jeu de carte, lors que elle est
	// défaussée ou sacrifiée.
	// Ensuite, le jeu de carte va être mélangé avant de commencer le nouveau
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

	public void setNbCarteAction(){
		this.nbCarteAction = this.listeCartesAction.size();
	}

}
