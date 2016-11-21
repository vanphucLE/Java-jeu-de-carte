package com.sdz.cartes;

public class CarteAction extends Carte {
	protected Boolean estJouee;

	public CarteAction() {
	
	}

	public Boolean estEgal(CarteAction CarteCompa) {
		if (this.id == CarteCompa.id) {
			return true;
		} else {
			return false;
		}
	}

	public void effectuerCapaciteSpecial(){
		
	};
	public void setEstJouee(Boolean estJouee) {
		this.estJouee = estJouee;
	}

	public Boolean getEstJouee() {
		return this.estJouee;
	}

}
