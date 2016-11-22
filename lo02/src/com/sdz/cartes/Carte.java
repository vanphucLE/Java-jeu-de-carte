package com.sdz.cartes;

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

	public String toString() {
		//on conserve les dogme dans un chain
		String dogmeString="";
		for(String dogmeElem : this.dogme ){
			dogmeString +=dogmeElem+", ";
		}
		dogmeString=dogmeString.substring(0, dogmeString.length()-2);
		
		StringBuffer sb = new StringBuffer();
		sb.append("Carte " + this.type + " : "+this.nom + " | ");
		sb.append("Dogme: " + dogmeString + " | ");
		sb.append("Origine: " + this.origine + " \n");
		return sb.toString();
	}

}
