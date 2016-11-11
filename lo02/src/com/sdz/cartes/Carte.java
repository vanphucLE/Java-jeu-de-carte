package com.sdz.cartes;

public abstract class Carte {
	protected String nom;
	protected String[] dogme={};
	protected String origine="";
	protected String capaciteSpecial="";
	protected Boolean estCapaciteSpecialUtilise = false;
	protected String type="";
	
	protected int id;
	/*
	 * Carte Croyant: id :1 -->37
	 * Carte Guide Spirituel: 38-->57
	 * Carte Deus Ex : 58 --> 75
	 * Carte Apocalypse: 76 --> 80
	 * Carte Divinite: 81 -->90
	 */

	
	public abstract void effectuerCapaciteSpecial() ;

	public String getNom() {
		return this.nom;
	}

	public String getOrigine() {
		return this.origine;
	}

	public String[] getDogme() {
		return this.dogme;
	}

}
