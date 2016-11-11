package com.sdz.cartes;

public class GuideSpirituels extends CarteAction {
	private String type = "GuideSpirituel";

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

	public GuideSpirituels(int id){
		this.id=id;
		this.nom=nomGuideSpirituels[id-38];
		this.origine=origineGuideSpirituels[id-38];
		this.dogme=dogmeGuideSpirituels[id-38];
	}
	public void effectuerCapaciteSpecial() {

	}

}
