package com.sdz.cartes;

public abstract class CarteAction extends Carte {
	protected Boolean estJouee;
	
	public void setEstJouee(Boolean estJouee){
		this.estJouee=estJouee;
	}
	public Boolean getEstJouee(){
		return this.estJouee;
	}

}
