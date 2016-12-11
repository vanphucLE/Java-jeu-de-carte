package com.sdz.cartes;

import java.util.Arrays;

import com.sdz.modele.Partie;

public class Croyant extends CarteAction {
	private int nbCroyant;
	private Boolean estGuidee;

	public static final String[] nomCroyants = { "Moines", "Moines", "Moines", "Moines", "Moines", "Travailleurs",
			"Travailleurs", "Travailleurs", "Ermite", "Ermite", "Integristes", "Guerriers Saints", "Diplomates",
			"Démons", "Démons", "Démons", "Démons", "Démons", "Alchimistes", "Alchimistes", "Alchimistes", "Vampire",
			"Vampire", "Lycanthropes", "Pillards", "Illusionnistes", "Esprits", "Esprits", "Esprits", "Esprits",
			"Esprits", "Aliénés", "Aliénés", "Aliénés", "Revenant", "Révolutionnaires", "Nibillistes" };

	public static final String[] origineCroyants = { "Jour", "Jour", "Jour", "Jour", "Jour", "Jour", "Jour", "Jour",
			"Jour", "Jour", "Jour", "Jour", "Jour", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit",
			"Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Néant", "Néant", "Néant", "Néant", "Néant", "Néant", "Néant",
			"Néant", "Néant", "Néant", "Néant" };

	public static final String[][] dogmeCroyants = { { "Humain", "Nature", "Mystique" },
			{ "Humain", "Mystique", "Chaos" }, { "Symboles", "Mystique", "Chaos" },
			{ "Nature", "Symboles", "Mystique" }, { "Nature", "Mystique", "Chaos" }, { "Humain", "Symboles", "Chaos" },
			{ "Nature", "Humain", "Symboles" }, { "Humain", "Mystique", "Chaos" }, { "Nature", "Mystique", "Chaos" },
			{ "Nature", "Symboles", "Mystique" }, { "Nature", "Humain", "Chaos" }, { "Nature", "Symboles", "Mystique" },
			{ "Humain", "Symboles", "Chaos" }, { "Nature", "Humain", "Mystique" }, { "Humain", "Mystique", "Chaos" },
			{ "Symboles", "Mystique", "Chaos" }, { "Nature", "Symboles", "Mystique" },
			{ "Nature", "Mystique", "Chaos" }, { "Nature", "Symboles", "Chaos" }, { "Nature", "Mystique", "Chaos" },
			{ "Nature", "Symboles", "Chaos" }, { "Nature", "Humain", "Symboles" }, { "Humain", "Mystique", "Chaos" },
			{ "Nature", "Humain", "Chaos" }, { "Nature", "Symboles", "Mystique" }, { "Humain", "Symboles", "Chaos" },
			{ "Nature", "Humain", "Mystique" }, { "Humain", "Mystique", "Chaos" }, { "Symboles", "Mystique", "Chaos" },
			{ "Nature", "Symboles", "Mystique" }, { "Nature", "Mystique", "Chaos" }, { "Humain", "Symboles", "Chaos" },
			{ "Nature", "Humain", "Symboles" }, { "Humain", "Mystique", "Chaos" }, { "Nature", "Humain", "Mystique" },
			{ "Humain", "Symboles", "Chaos" }, { "Symboles", "Mystique", "Chaos" } };
	public static final int[] nbCroyants = { 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 4,
										4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 4 };
	
	public static final String[] CapaCroyant={"Donne un point d'Action d'Origine Jour.", "Donne un point d'Action d'Origine Jour.","Donne un point d'Action d'Origine Jour.",
			"Donne un point d'Action d'Origine Jour.","Donne un point d'Action d'Origine Jour.","Empêche une Divinité possédant le Dogme Nature ou Mystique de sacrifier une de ses cartes de Croyants durant ce tour.",
			"Empêche une Divinité possédant le Dogme Chaos ou Mystique de sacrifier un de ses Guides Spirituels durant ce tour.","Vous piochez deux cartes au hasard dans la main d'une autre Divinité","Impose le sacrifice d'un Croyant d'un autre joueur, qui choisit la carte. La capacité spéciale du sacrifice est jouée.",
			"Impose le sacrifice d'un Croyant d'un autre joueur, qui choisit la carte. La capacité spéciale du sacrifice est jouée.", "Impose le sacrifice d'un Guide Spirituel d'un autre joueur, qui choisit la carte. La capacité spéciale du sacrifice est jouée.","Un Guide Spirituel revient dans la main de sa Divinité. Ses Croyants reviennent au centre de la table.",
			"Relancez le dé de Cosmogonie. Le tour se finit normalement sous la nouvelle influence.","Donne un point d'Action d'Origine Nuit.", "Donne un point d'Action d'Origine Nuit.",
			"Donne un point d'Action d'Origine Nuit.","Donne un point d'Action d'Origine Nuit.","Donne un point d'Action d'Origine Nuit.",
			"Empêche une Divinité possédant le Dogme Humain ou Mystique de sacrifier une de ses cartes de Croyants durant ce tour de jeu.","Empêche une Divinité possédant le Dogme Humain ou Symboles de sacrifier un de ses Guides Spirituels durant ce tour de jeu.","Vous piochez deux cartes au hasard dans la main d'une autre Divinité. ",
			"Impose le sacrifice d'un Croyant d'un autre joueur. Celui-ci choisit le sacrifié. La capacité spéciale du sacrifice est jouée.","Impose le sacrifice d'un Croyant d'un autre joueur. Celui-ci choisit le sacrifié. La capacité spéciale du sacrifice est jouée.", "Retirez tous les Croyants attachés à l'un des Guides Spirituels d'une autre Divinité. Les Croyants reviennent au milieu de la table, le Guide Spirituel est défaussé.",
			"Récupérez les points d'Action d'une Divinité n'ayant pas encore joué durant ce tour. Les points d'Action gardent leur Origine. La Divinité perd ses points.","Illusionnistes Vous bénéficiez de la capacité spéciale de sacrifice d'une carte de Croyants appartenant à une autre Divinité. La carte en question reste en jeu.","Donne un point d'Action d'Origine Néant ",
			"Donne un point d'Action d'Origine Néant.","Donne un point d'Action d'Origine Néant.","Donne un point d'Action d'Origine Néant.",
			"Donne un point d'Action d'Origine Néant.","Empêche une Divinité possédant le Dogme Nature ou Mystique de sacrifier une	de ses cartes de Croyants durant ce tour de jeu.","Empêche une Divinité possédant le Dogme Mystique ou Chaos de sacrifier un de ses Guides Spirituels durant ce tour de jeu.",
			"Vous piochez deux cartes au hasard dans la main d'une autre Divinité.","Lancez le dé de Cosmogonie. Le tour se fini normalement, mais sous cette nouvelle influence.", "Imposez le sacrifice d'une carte de Croyants à autant de Divinités que vous le voulez. Chaque Divinité choisit la carte à sacrifier. Les capacités spéciales sont jouées.",
			"Jusqu'à la fin du tour, plus aucune Divinité ne reçoit de points d'Action."};

	public Croyant(int id) {
		this.id = id;
		this.nom = nomCroyants[id - 1];
		this.origine = origineCroyants[id - 1];
		this.dogme = dogmeCroyants[id - 1];
		this.type = "Croyant";
		this.nbCroyant=nbCroyants[id-1];
		this.capaciteSpecial=CapaCroyant[id-1];
		this.capacite= new CapaciteSpeciale(this.id);
	}
	public Croyant(){
		
	}

	public void setEstJouee(Boolean estGuidee) {
		this.estGuidee = estGuidee;
	}

	public Boolean getEstJouee() {
		return this.estJouee;
	}
	
	@Override
	public void effectuerCapaciteSpecial(Partie partie) {
		this.capacite.effectuerCapacite(partie);
	}

	public int getNbCroyant() {
		return nbCroyant;
	}
	
	@Override
	public String toString() {
		//on conserve les dogme dans un chain
		StringBuffer sb = new StringBuffer();
		sb.append("Carte " + this.type + ": "+this.nom + " \t ");
		sb.append("[Id: " + this.id+ "] ");
		sb.append("[Dogme: " + Arrays.toString(dogme) + "] ");
		sb.append("[Origine: " + this.origine + "] ");
		sb.append("[Nombre de Croyant: " + this.nbCroyant + "] \n ");
		sb.append("      +[Capacité speciale: " + this.capaciteSpecial+"] \n");
		return sb.toString();
	}
}
