package com.sdz.cartes;

import java.util.Arrays;

public abstract class Carte {
	protected String nom;
	protected String[] dogme = {};
	protected String origine = "";
	protected String capaciteSpecial = "";
	protected Boolean estCapaciteSpecialUtilise = false;
	protected String type = "";

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

	public abstract void effectuerCapaciteSpecial();

	public String getNom() {
		return this.nom;
	}

	public String getOrigine() {
		return this.origine;
	}

	public String[] getDogme() {
		return this.dogme;
	}

	@Override
	
	public String toString() {
		//on conserve les dogme dans un chain
		StringBuffer sb = new StringBuffer();
		sb.append("Carte " + this.type + ": "+this.nom + " \t| ");
		sb.append("Id: " + this.id+ " | ");
		sb.append("Dogme: " + Arrays.toString(dogme) + " | ");
		sb.append("Origine: " + this.origine + " | ");
		sb.append("Capacité speciale (" + this.capaciteSpecial);
		sb.append(") est utilisée: " + this.capaciteSpecial + " | ");
		return sb.toString();
	}
}
