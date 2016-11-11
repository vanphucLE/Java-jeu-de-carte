package com.sdz.cartes;

public class DeusEx extends CarteAction {

	private String type="DeusEx";
	
	private static final String[] nomDeusEx = { "Colère Divinie", "Colère Divinie", "Stase", "Ordre Céleste",
			"Fourberie", "Diversion", "Concentration", "Trou Noir", "Phoenix", "Influence Jour", "Influence Nuit",
			"Inffluence Néant", "Inffluence Nulle", "Inffluence Nulle", "Transe", "Miroir", "Bouleversement",
			"Inquisition" };

	private static final String[] origineDeusEx = { "Jour","Nuit","Jour","Jour","Nuit","Nuit",
													"Néant","Néant","Néant","","","","","","","","",""};
	public DeusEx(int id){
		this.id=id;
		this.nom=nomDeusEx[id-58];
		this.origine=origineDeusEx[id-58];
	}
	public void effectuerCapaciteSpecial() {

	}
}
