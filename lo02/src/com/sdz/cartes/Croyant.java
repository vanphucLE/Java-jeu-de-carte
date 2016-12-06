package com.sdz.cartes;

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

	public Croyant(int id) {
		this.id = id;
		this.nom = nomCroyants[id - 1];
		this.origine = origineCroyants[id - 1];
		this.dogme = dogmeCroyants[id - 1];
		this.type = "Croyant";
		this.nbCroyant=nbCroyants[id];
	}
	public Croyant(){
		
	}

	public void setEstJouee(Boolean estGuidee) {
		this.estGuidee = estGuidee;
	}

	public Boolean getEstJouee() {
		return this.estJouee;
	}

	public void effectuerCapaciteSpecial() {
		CapaciteSpeciale a=new CapaciteSpeciale();
		a.capacite(this.id);
	}

	public int getNbCroyant() {
		return nbCroyant;
	}

}
