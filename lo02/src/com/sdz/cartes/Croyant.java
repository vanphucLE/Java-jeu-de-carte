package com.sdz.cartes;

import java.util.ArrayList;

public class Croyant extends CarteAction {
	private int value;
	private Boolean estGuidee;
	private int id;
	private String type = "Croyant";

	private static final String[] nomCroyants = { "Moines", "Moines", "Moines", "Moines", "Moines", "Travailleurs",
			"Travailleurs", "Travailleurs", "Ermite", "Ermite", "Integristes", "Guerriers Saints", "Diplomates",
			"Démons", "Démons", "Démons", "Démons", "Démons", "Alchimistes", "Alchimistes", "Alchimistes", "Vampire",
			"Vampire", "Lycanthropes", "Pillards", "Illusionnistes", "Esprits", "Esprits", "Esprits", "Esprits",
			"Esprits", "Aliénés", "Aliénés", "Aliénés", "Revenant", "Révolutionnaires", "Nibillistes" };

	private static final String[] origineCroyants = { "Jour", "Jour", "Jour", "Jour", "Jour", "Jour", "Jour", "Jour",
			"Jour", "Jour", "Jour", "Jour", "Jour", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Nuit",
			"Nuit", "Nuit", "Nuit", "Nuit", "Nuit", "Néant", "Néant" };

	private static final String[][] dogmeCroyants = { { "Humain", "Nature", "Mystique" },
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

	public Croyant(int id) {
		this.id = id;
		this.nom = nomCroyants[id-1];
		this.origine = origineCroyants[id-1];
		this.dogme = dogmeCroyants[id-1];
	}

	public void setEstJouee(Boolean estGuidee) {
		this.estGuidee = estGuidee;
	}

	public Boolean getEstJouee() {
		return this.estJouee;
	}
	public void effectuerCapaciteSpecial(){
		
	}

}
