package com.sdz.cartes;

public class DeusEx extends CarteAction {

	private String type="DeusEx";
	
	private static final String[] nomDeusEx = { "Col�re Divinie", "Col�re Divinie", "Stase", "Ordre C�leste",
			"Fourberie", "Diversion", "Concentration", "Trou Noir", "Phoenix", "Influence Jour", "Influence Nuit",
			"Inffluence N�ant", "Inffluence Nulle", "Inffluence Nulle", "Transe", "Miroir", "Bouleversement",
			"Inquisition" };

	private static final String[] origineDeusEx = { "Jour","Nuit","Jour","Jour","Nuit","Nuit",
													"N�ant","N�ant","N�ant","","","","","","","","",""};
	public DeusEx(int id){
		this.id=id;
		this.nom=nomDeusEx[id-58];
		this.origine=origineDeusEx[id-58];
	}
	public void effectuerCapaciteSpecial() {

	}
}
