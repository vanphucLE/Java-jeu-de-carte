package com.sdz.cartes;

import java.util.Arrays;

import com.sdz.modele.Partie;
/*
 * Ce classe est pour decrire les attributs, les types, les methode d'une carte generale
 * @author TRAN Hoang
 * 
 */
public abstract class Carte {
	protected String nom;
	protected String[] dogme = {};
	protected String origine = "";
	protected String capaciteSpecial = "";
	protected String type = "";
	protected Boolean estSacrifie = false;
	protected CapaciteSpeciale capacite;
	
	public String getType() {
		return type;
	}

	protected int id;
	/*
	 * Carte Croyant: id :1 -->37 Carte Guide Spirituel: 38-->57 Carte Deus Ex :
	 * 58 --> 75 Carte Apocalypse: 76 --> 80 Carte Divinite: 81 -->90
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public String getOrigine() {
		return this.origine;
	}

	public String[] getDogme() {
		return this.dogme;
	}

	public Boolean getEstSacrifie() {
		return estSacrifie;
	}

	public void setEstSacrifie(Boolean estSacrifie) {
		this.estSacrifie = estSacrifie;
	}

	public String toString() {
		// on conserve les dogme dans un chain
		StringBuffer sb = new StringBuffer();
		sb.append("Carte " + this.type + ": " + this.nom + " \t ");
		sb.append("[Id: " + this.id + "] ");
		sb.append("[Dogme: " + Arrays.toString(dogme) + "] ");
		sb.append("[Origine: " + this.origine + "] \n ");
		sb.append("     +[Capacité speciale: " + this.capaciteSpecial + "] \n");
		return sb.toString();
	}
}
