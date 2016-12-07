package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;

public class Joueur {
	protected int id;
	protected String nom;
	protected int age;
	protected int ptPriere;
	protected int ptAction_Jour;
	protected int ptAction_Nuit;
	protected int ptAction_Neant;
	protected Boolean estElimine;
	protected int idCarteDivinite;
	protected LaMain laMain;
	protected boolean sacrifice = true;

	public Joueur(int id, String nom, int age) {
		this.id = id;
		this.nom = nom;
		this.age = age;
	}

	public int getidCarteDivinite() {
		return this.idCarteDivinite;
	}

	public void setSacrifice(boolean sacrifice) {
		this.sacrifice = sacrifice;
	}

	public String getNom() {
		return this.nom;
	}

	public int getId() {
		return this.id;
	}

	public void setPtAction_Jour(int ptAction) {
		this.ptAction_Jour = ptAction;
	}

	public int getPtAction_Jour() {
		return this.ptAction_Jour;
	}

	public void setPtAction_Nuit(int ptAction) {
		this.ptAction_Nuit = ptAction;
	}

	public int getPtAction_Nuit() {
		return this.ptAction_Nuit;
	}

	public void setPtAction_Neant(int ptAction) {
		this.ptAction_Neant = ptAction;
	}

	public int getPtAction_Neant() {
		return this.ptAction_Neant;
	}

	public void setPtPriere() {
		int sumPtPriere = 0;
		for (LinkedList<Croyant> listeCarte : laMain.getListeCroyantGuidee()) {
			for (Croyant carte : listeCarte) {
				sumPtPriere += carte.getNbCroyant();
			}
		}
		this.ptPriere = sumPtPriere;
	}

	public int getPtPriere() {
		return this.ptPriere;
	}

	public void setElimine(Boolean estElimie) {
		this.estElimine = estElimine;
	}

	public Boolean getElimine() {
		return this.estElimine;
	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}

	public void jouer(Partie partie) {
		this.seDefausserCartes(partie.getJeuDeCartes());
		this.Compeleter7Carte(partie.getJeuDeCartes());
		this.choisirCarteReel(partie);

	}

	// Choisir carte pour jouer
	public void choisirCarteReel(Partie partie) {
	}

	// Le joueur compléte 7 cartes dans la main
	public void Compeleter7Carte(JeuDeCartes jeuDeCartes) {
	}

	// Un joueur veut défausser un plusieur Cartes
	public void seDefausserCartes(JeuDeCartes jeuDeCartes) {

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Joueur n." + this.id + " : ");
		sb.append(this.nom + "\t ");
		sb.append("(" + this.age + " ans) | ");
		sb.append("Point Prière: " + this.ptPriere + " | ");
		sb.append("Point Action : (Jour: " + this.ptAction_Jour + ") ");
		sb.append("(Nuit: " + this.ptAction_Nuit + ") ");
		sb.append("(Néant: " + this.ptAction_Neant + ")");
		return sb.toString();
	}

	public void sacrifierCroyant(CarteAction a) {
		// rut me cai carte di
		//
		if (this.sacrifice) {
		}
	}
}
