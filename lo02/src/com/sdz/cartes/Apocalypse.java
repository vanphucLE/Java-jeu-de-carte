package com.sdz.cartes;

public class Apocalypse extends CarteAction {
	
	private static final String[] origineApocalypse={"Jour","Nuit","Néant","","",""};
	
	public Apocalypse(int id){
		this.id=id;
		this.origine=origineApocalypse[id-76];
		this.type="Apocalypse";
		this.nom="Apocalypse";
	}
	
	public void effectuerCapaciteSpecial(){
		
	}
	
	

}
