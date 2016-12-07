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
		sb.append("Carte " + this.type + ": "+this.nom + " \t| ");
		sb.append("Id: " + this.id+ " | ");
		sb.append("Dogme: " + Arrays.toString(dogme) + " | ");
		sb.append("Origine: " + this.origine + " | ");
		sb.append("Nombre de carte Croyant guidee: " + this.nbGuider + " | ");
		sb.append("Capacité speciale (" + this.capaciteSpecial);
		sb.append(") est utilisée: " + this.capaciteSpecial + " | ");
		return sb.toString();
	}

}
