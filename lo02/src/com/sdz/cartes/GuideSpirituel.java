package com.sdz.cartes;

import java.util.Arrays;

public class GuideSpirituel extends CarteAction {
	private int nbGuider;

	private static final String[] nomGuideSpirituels = { "Martyr", "Martyr", "Martyr", "Clerc", "Clerc", "Clerc",
			"Clerc", "Clerc", "Clerc", "Clerc", "Clerc", "Shaman", "Anarchiste", "Paladin", "Asc�te", "Devin",
			"Exorciste", "Sorcier", "Tyran", "Messie" };

	private static final String[] origineGuideSpirituels = { "Jour", "Nuit", "N�ant", "Jour", "Nuit", "N�ant", "Jour",
			"Nuit", "N�ant", "Jour", "Nuit", "Nuit", "N�ant", "Jour", "Nuit", "N�ant", "Jour", "Nuit", "N�ant",
			"Jour" };

	private static final String[][] dogmeGuideSpirituels = { { "Humain", "Nature" }, { "Sysmboles", "Humain" },
			{ "Nature", "Chaos" }, { "Chaos", "Humain" }, { "Nature", "Symboles" }, { "Mystique", "Chaos" },
			{ "Nature", "Chaos" }, { "Symboles", "Mystique" }, { "Chaos", "Symboles" }, { "Mystique", "Chaos" },
			{ "Nature", "Chaos" }, { "Nature", "Symboles" }, { "Chaos", "Humain" }, { "Humain", "Mystique" },
			{ "Humain", "Symboles" }, { "Nature", "Mystique" }, { "Mystique", "Chaos" }, { "Symboles", "Mystique" },
			{ "Symboles", "Chaos" }, { "Mystique", "Humain" } };

	private static final int[] nbGuiders={2,2,2,2,2,2,2,2,2,2,2,3,3,3,1,1,1,3,3,3};
	
	public static final String [] CapaGuideSpirituel={"Equivalent � la pose d'une carte Apocalyps.","Equivalent � la pose d'une carte Apocalypse.",
			"Equivalent � la pose d'une carte Apocalypse.","Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.",
			"Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.",
			"Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action �gal au nombre de cartes de Croyants rattach�es. L'Origine des points d'Action est au choix du joueur.",
			"Sacrifie tous les Croyants d'Origine N�ant d'une Divinit� ayant le Dogme Humain. Les capacit�s sp�cials sont jou�es normalement.","Sacrifie un Guide Spirituel, si lui ou sa Divinit� ne croit pas au Dogme Chaos. Les capacit�s sp�ciales sont jou�es normalement. ","Tous les Croyants, d'Origine Nuit ou N�ant et ayant le Dogme Nature, actuellement sur la table sont d�fauss�s. Les capacit�s sp�ciales ne sont pas jou�es.",
			"Sacrifie 2 cartes Croyants d'une Divinit� ayant le Dogme Humain ou Symboles. Les capacit�s sp�ciales sont jou�es normalement","Oblige une Divinit� ayant le Dogme Nature ou Mystique � sacrifier l'un de ses Guides Spirituels.","Une Divinit� d'Origine Nuit ou ayant les Dogmes Mystique et Chaos reprend dans sa main l'un de ses Guides Spirituels. Les Croyants qui y �taient attach�s sont d�fauss�s.",
			"Echangez l'un de vos Guides Spirituels avec un d'une autre Divinit�. Vous choisissez les deux guides Spirituels en question. Les Croyants restent attach�s aux m�mes cartes.","D�fausse tous les Croyants ayant le Dogme Mystique actuellement au centre de la table.","Le joueur pose le d� de Cosmogonie sur la face qu'il d�sire et commence un nouveau tour de jeu."};
	
	public GuideSpirituel(int id){
		this.id=id;
		this.nom=nomGuideSpirituels[id-38];
		this.origine=origineGuideSpirituels[id-38];
		this.dogme=dogmeGuideSpirituels[id-38];
		this.type = "GuideSpirituel";
		this.nbGuider=nbGuiders[id-38];
	}
	public void effectuerCapaciteSpecial() {
		
	}
	
	public int getNbGuider() {
		return nbGuider;
	}
	
	@Override
	
	public String toString() {
		//on conserve les dogme dans un chain
		StringBuffer sb = new StringBuffer();
		sb.append("Carte " + this.type + ": "+this.nom + " \t ");
		sb.append("[Id: " + this.id+ "] ");
		sb.append("[Dogme: " + Arrays.toString(dogme) + "] ");
		sb.append("[Origine: " + this.origine + "] ");
		sb.append("[Nombre de carte Croyant guidee: " + this.nbGuider + "] \n");
		sb.append("      +[Capacit� speciale: " + this.capaciteSpecial+"] \n");
		return sb.toString();
	}

}
