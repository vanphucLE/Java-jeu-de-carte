package com.sdz.cartes;

public class Apocalypse extends CarteAction {
	
	private String type="Apocalypse";
	private String nom="Apocalypse";
	private int id;
	private static final String[] origineApocalypse={"Jour","Nuit","Néant","","",""};
	public Apocalypse(int id){
		this.id=id;
		this.origine=origineApocalypse[id-76];
	}
	
	public void effectuerCapaciteSpecial(){
		
	}
	
	

}
