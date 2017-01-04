package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.Apocalypse;
import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.DeusEx;
import com.sdz.cartes.GuideSpirituel;

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
			GuideSpirituel carteGuideSpirituel = new GuideSpirituel(i);
			this.listeCartesAction.add(carteGuideSpirituel);
		}

		// Cr�e les Cartes DeusEx
		for (int i = 58; i <= 75; i++) {
			DeusEx carteDeusEx = new DeusEx(i);
			this.listeCartesAction.add(carteDeusEx);
		}

		// Cr�e les Cartes Apocalypses
		for (int i = 76; i <= 80; i++) {
			Apocalypse carteApocalypse = new Apocalypse(i);
			this.listeCartesAction.add(carteApocalypse);
		}

		// Cr�e les Cartes Divinit�
		for (int i = 81; i <= 90; i++) {
			CarteDivinite carteDivinite = new CarteDivinite(i);
			this.listeCartesDivinite.add(carteDivinite);
		}
		this.melangerCartesAction();
		this.melangerCartesDivinite();
	}

	// M�langer les Cartes Action
	public void melangerCartesAction() {
		int tail=this.listeCartesAction.size();
		for (int i = 0; i < tail; i++) {
			int position = (int) Math.ceil((tail - 1) * Math.random());
			CarteAction carteAction = this.listeCartesAction.pop();
			this.listeCartesAction.add(position, carteAction);
		}
	}

	// M�langer les Cartes Divinit�s
	public void melangerCartesDivinite() {
		for (int i = 81; i < 90; i++) {
			int position = (int) Math.ceil((90 - 81) * Math.random()) ;
			CarteDivinite carteDivinite = this.listeCartesDivinite.pop();
			this.listeCartesDivinite.add(position, carteDivinite);
		}
	}

	// Distribuer Carte Action
	public CarteAction distribuerCarteAction() {
		return this.listeCartesAction.removeFirst();
	}

	// Distribuer Carte Divinit� au joueur
	public CarteDivinite piocherCarteDivinite() {
		return this.listeCartesDivinite.removeFirst();
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

	public void setNbCarteAction() {
		this.nbCarteAction = this.listeCartesAction.size();
	}
}
