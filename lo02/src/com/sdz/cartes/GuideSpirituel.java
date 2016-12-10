package com.sdz.cartes;

import java.util.Arrays;

public class GuideSpirituel extends CarteAction {
	private int nbGuider;

	private static final String[] nomGuideSpirituels = { "Martyr", "Martyr", "Martyr", "Clerc", "Clerc", "Clerc",
			"Clerc", "Clerc", "Clerc", "Clerc", "Clerc", "Shaman", "Anarchiste", "Paladin", "Ascète", "Devin",
			"Exorciste", "Sorcier", "Tyran", "Messie" };

	private static final String[] origineGuideSpirituels = { "Jour", "Nuit", "Néant", "Jour", "Nuit", "Néant", "Jour",
			"Nuit", "Néant", "Jour", "Nuit", "Nuit", "Néant", "Jour", "Nuit", "Néant", "Jour", "Nuit", "Néant",
			"Jour" };

	private static final String[][] dogmeGuideSpirituels = { { "Humain", "Nature" }, { "Sysmboles", "Humain" },
			{ "Nature", "Chaos" }, { "Chaos", "Humain" }, { "Nature", "Symboles" }, { "Mystique", "Chaos" },
			{ "Nature", "Chaos" }, { "Symboles", "Mystique" }, { "Chaos", "Symboles" }, { "Mystique", "Chaos" },
			{ "Nature", "Chaos" }, { "Nature", "Symboles" }, { "Chaos", "Humain" }, { "Humain", "Mystique" },
			{ "Humain", "Symboles" }, { "Nature", "Mystique" }, { "Mystique", "Chaos" }, { "Symboles", "Mystique" },
			{ "Symboles", "Chaos" }, { "Mystique", "Humain" } };

	private static final int[] nbGuiders={2,2,2,2,2,2,2,2,2,2,2,3,3,3,1,1,1,3,3,3};
	
	public static final String [] CapaGuideSpirituel={"Equivalent à la pose d'une carte Apocalyps.","Equivalent à la pose d'une carte Apocalypse.",
			"Equivalent à la pose d'une carte Apocalypse.","Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.",
			"Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.",
			"Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.","Fait gagner un nombre de points d'Action égal au nombre de cartes de Croyants rattachées. L'Origine des points d'Action est au choix du joueur.",
			"Sacrifie tous les Croyants d'Origine Néant d'une Divinité ayant le Dogme Humain. Les capacités spécials sont jouées normalement.","Sacrifie un Guide Spirituel, si lui ou sa Divinité ne croit pas au Dogme Chaos. Les capacités spéciales sont jouées normalement. ","Tous les Croyants, d'Origine Nuit ou Néant et ayant le Dogme Nature, actuellement sur la table sont défaussés. Les capacités spéciales ne sont pas jouées.",
			"Sacrifie 2 cartes Croyants d'une Divinité ayant le Dogme Humain ou Symboles. Les capacités spéciales sont jouées normalement","Oblige une Divinité ayant le Dogme Nature ou Mystique à sacrifier l'un de ses Guides Spirituels.","Une Divinité d'Origine Nuit ou ayant les Dogmes Mystique et Chaos reprend dans sa main l'un de ses Guides Spirituels. Les Croyants qui y étaient attachés sont défaussés.",
			"Echangez l'un de vos Guides Spirituels avec un d'une autre Divinité. Vous choisissez les deux guides Spirituels en question. Les Croyants restent attachés aux mêmes cartes.","Défausse tous les Croyants ayant le Dogme Mystique actuellement au centre de la table.","Le joueur pose le dé de Cosmogonie sur la face qu'il désire et commence un nouveau tour de jeu."};
	
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
		sb.append("      +[Capacité speciale: " + this.capaciteSpecial+"] \n");
		return sb.toString();
	}

}
