package com.sdz.cartes;

import java.util.Arrays;

import com.sdz.modele.Partie;

public class Croyant extends CarteAction {
	private int nbCroyant;
	private Boolean estGuidee;

	public static final String[] nomCroyants = { "Moines", "Moines", "Moines", "Moines", "Moines", "Travailleurs",
			"Travailleurs", "Travailleurs", "Ermite", "Ermite", "Integristes", "Guerriers Saints", "Diplomates",
			"D�mons", "D�mons", "D�mons", "D�mons", "D�mons", "Alchimistes", "Alchimistes", "Alchimistes", "Vampire",
			"Vampire", "Lycanthropes", "Pillards", "Illusionnistes", "Esprits", "Esprits", "Esprits", "Esprits",
			"Esprits", "Ali�n�s", "Ali�n�s", "Ali�n�s", "Revenant", "R�volutionnaires", "Nibillistes" };

	public static final String[] origineCroyants = { "Jour", "Jour", "Jour", "Jour", "Jour", "Jour", "Jour", "Jour",
			"Jour", "Jour", "Jour", "Jour", "Jour", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit",
			"Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "N�ant", "N�ant", "N�ant", "N�ant", "N�ant", "N�ant", "N�ant",
			"N�ant", "N�ant", "N�ant", "N�ant" };

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
			"Donne un point d'Action d'Origine Jour.","Donne un point d'Action d'Origine Jour.","Emp�che une Divinit� poss�dant le Dogme Nature ou Mystique de sacrifier une de ses cartes de Croyants durant ce tour.",
			"Emp�che une Divinit� poss�dant le Dogme Chaos ou Mystique de sacrifier un de ses Guides Spirituels durant ce tour.","Vous piochez deux cartes au hasard dans la main d'une autre Divinit�","Impose le sacrifice d'un Croyant d'un autre joueur, qui choisit la carte. La capacit� sp�ciale du sacrifice est jou�e.",
			"Impose le sacrifice d'un Croyant d'un autre joueur, qui choisit la carte. La capacit� sp�ciale du sacrifice est jou�e.", "Impose le sacrifice d'un Guide Spirituel d'un autre joueur, qui choisit la carte. La capacit� sp�ciale du sacrifice est jou�e.","Un Guide Spirituel revient dans la main de sa Divinit�. Ses Croyants reviennent au centre de la table.",
			"Relancez le d� de Cosmogonie. Le tour se finit normalement sous la nouvelle influence.","Donne un point d'Action d'Origine Nuit.", "Donne un point d'Action d'Origine Nuit.",
			"Donne un point d'Action d'Origine Nuit.","Donne un point d'Action d'Origine Nuit.","Donne un point d'Action d'Origine Nuit.",
			"Emp�che une Divinit� poss�dant le Dogme Humain ou Mystique de sacrifier une de ses cartes de Croyants durant ce tour de jeu.","Emp�che une Divinit� poss�dant le Dogme Humain ou Symboles de sacrifier un de ses Guides Spirituels durant ce tour de jeu.","Vous piochez deux cartes au hasard dans la main d'une autre Divinit�. ",
			"Impose le sacrifice d'un Croyant d'un autre joueur. Celui-ci choisit le sacrifi�. La capacit� sp�ciale du sacrifice est jou�e.","Impose le sacrifice d'un Croyant d'un autre joueur. Celui-ci choisit le sacrifi�. La capacit� sp�ciale du sacrifice est jou�e.", "Retirez tous les Croyants attach�s � l'un des Guides Spirituels d'une autre Divinit�. Les Croyants reviennent au milieu de la table, le Guide Spirituel est d�fauss�.",
			"R�cup�rez les points d'Action d'une Divinit� n'ayant pas encore jou� durant ce tour. Les points d'Action gardent leur Origine. La Divinit� perd ses points.","Illusionnistes Vous b�n�ficiez de la capacit� sp�ciale de sacrifice d'une carte de Croyants appartenant � une autre Divinit�. La carte en question reste en jeu.","Donne un point d'Action d'Origine N�ant ",
			"Donne un point d'Action d'Origine N�ant.","Donne un point d'Action d'Origine N�ant.","Donne un point d'Action d'Origine N�ant.",
			"Donne un point d'Action d'Origine N�ant.","Emp�che une Divinit� poss�dant le Dogme Nature ou Mystique de sacrifier une	de ses cartes de Croyants durant ce tour de jeu.","Emp�che une Divinit� poss�dant le Dogme Mystique ou Chaos de sacrifier un de ses Guides Spirituels durant ce tour de jeu.",
			"Vous piochez deux cartes au hasard dans la main d'une autre Divinit�.","Lancez le d� de Cosmogonie. Le tour se fini normalement, mais sous cette nouvelle influence.", "Imposez le sacrifice d'une carte de Croyants � autant de Divinit�s que vous le voulez. Chaque Divinit� choisit la carte � sacrifier. Les capacit�s sp�ciales sont jou�es.",
			"Jusqu'� la fin du tour, plus aucune Divinit� ne re�oit de points d'Action."};

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
		sb.append("      +[Capacit� speciale: " + this.capaciteSpecial+"] \n");
		return sb.toString();
	}
}
