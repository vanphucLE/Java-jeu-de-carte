package com.sdz.cartes;

public class CarteDivinite extends Carte {

	
	public static final String[] nomDivinite = { "Brewalen", "Drinded", "Yarstur", "Kilinstred", "Llewalla",
			"Pui-Tara", "Gwenghelen", "Shingva", "Gorpa", "Romtec" };
	public static final String[] origineDivinite = { "Jour", "Jour", "Jour", "Nuit", "Nuit", "Nuit", "Aube", "Aube",
			"Crepuscule", "Crepuscule" };
	public static final String[][] dogmeDivinite = { { "Nature", "Humain", "Mystique" },
			{ "Nature", "Humain", "Symboles" }, { "Chaos", "Symboles", "Mystique" }, { "Nature", "Mystique", "Chaos" },
			{ "Nature", "Mystique", "Chaos" }, { "Nature", "Mystique", "Symboles" },
			{ "Humain", "Mystique", "Symboles" }, { "Humain", "Mystique", "Chaos" }, { "Humain", "Symboles", "Chaos" },
			{ "Nature", "Humain", "Chaos" } };
	
	public CarteDivinite(int id){
		this.id=id;
		this.nom=nomDivinite[id-81];
		this.origine=origineDivinite[id-81];
		this.dogme=dogmeDivinite[id-81];
		this.type = "Divnité";
	}
	public void effectuerCapaciteSpecial() {

	}
}
