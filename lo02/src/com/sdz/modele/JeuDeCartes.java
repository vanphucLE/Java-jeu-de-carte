package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.Apocalypse;
import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.DeusEx;
import com.sdz.cartes.GuideSpirituel;
/*
 * Ce classe cree un Jeu de Carte qui contient tous les cartes
 * @author TRAN Hoang
 */
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

		// Crée les Cartes DeusEx
		for (int i = 58; i <= 75; i++) {
			DeusEx carteDeusEx = new DeusEx(i);
			this.listeCartesAction.add(carteDeusEx);
		}

		// Crée les Cartes Apocalypses
		for (int i = 76; i <= 80; i++) {
			Apocalypse carteApocalypse = new Apocalypse(i);
			this.listeCartesAction.add(carteApocalypse);
		}

		// Crée les Cartes Divinité
		for (int i = 81; i <= 90; i++) {
			CarteDivinite carteDivinite = new CarteDivinite(i);
			this.listeCartesDivinite.add(carteDivinite);
		}
		this.melangerCartesAction();
		this.melangerCartesDivinite();
	}

	// Mélanger les Cartes Action
	/*
	 * Melanger les cartes Action
	 */
	public void melangerCartesAction() {
		int tail=this.listeCartesAction.size();
		for (int i = 0; i < tail; i++) {
			int position = (int) Math.ceil((tail - 1) * Math.random());
			CarteAction carteAction = this.listeCartesAction.pop();
			this.listeCartesAction.add(position, carteAction);
		}
	}
	/*
	 * Melanger les cartes Divinite
	 */
	// Mélanger les Cartes Divinités
	public void melangerCartesDivinite() {
		for (int i = 81; i < 90; i++) {
			int position = (int) Math.ceil((90 - 81) * Math.random()) ;
			CarteDivinite carteDivinite = this.listeCartesDivinite.pop();
			this.listeCartesDivinite.add(position, carteDivinite);
		}
	}
	/*
	 * Distribuer Carte Actione
	 * @return une CarteAction
	 */
	// Distribuer Carte Action
	public CarteAction distribuerCarteAction() {
		return this.listeCartesAction.removeFirst();
	}
	/*
	 * Distribuer Carte Divinité
	 * @return une carte divinite
	 */
	// Distribuer Carte Divinité au joueur
	public CarteDivinite piocherCarteDivinite() {
		return this.listeCartesDivinite.removeFirst();
	}

	// La carte action va être récupéré au jeu de carte, lors que elle est
	// défaussée ou sacrifiée.
	// Ensuite, le jeu de carte va être mélangé avant de commencer le nouveau
	// tour.
	/*
	 * recuperer une carteAction
	 */
	public void recupererCarteAction(CarteAction carteAction) {
		this.listeCartesAction.add(carteAction);
		this.melangerCartesAction();
	}
	/*
	 * prendre liscartesAction
	 * @return liste Carte Action
	 */
	public LinkedList<CarteAction> getListeCartesAction() {
		return this.listeCartesAction;
	}
	/*
	 * prendre liscartesdivinite
	 * @return liste Carte divinite
	 */
	public LinkedList<CarteDivinite> getListeCartesDivinite() {
		return this.listeCartesDivinite;
	}
	/*
	 * prendre nombre de carte action
	 * @return une valeur int de nombre carte Action
	 */
	public int getNbCarteAction() {
		return nbCarteAction;
	}
	/*
	 * set nombre de carte action
	 */
	public void setNbCarteAction() {
		this.nbCarteAction = this.listeCartesAction.size();
	}
}
