package com.sdz.cartes;

import com.sdz.modele.Partie;

public class CarteAction extends Carte {
	protected Boolean estJouee;
	public CarteAction() {
	
	}

	public void effectuerCapaciteSpecial(Partie partie){
		
	};
	public void setPartieCarte(Partie partie){
		
	}
	public void setEstJouee(Boolean estJouee) {
		this.estJouee = estJouee;
	}

	public Boolean getEstJouee() {
		return this.estJouee;
	}

}
