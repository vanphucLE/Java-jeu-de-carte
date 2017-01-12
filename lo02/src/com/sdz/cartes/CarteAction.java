package com.sdz.cartes;

import com.sdz.modele.Partie;
/*
 * Ce classe est pour creer les cartes actions
 * @author TRAN Hoang
 */
public class CarteAction extends Carte {
	protected Boolean estJouee;

	public CarteAction() {

	}

	public void effectuerCapaciteSpecial(Partie partie) {
		this.capacite.effectuerCapacite(partie);
	}

	public void setPartieCarte(Partie partie){
		
	}
	public void setEstJouee(Boolean estJouee) {
		this.estJouee = estJouee;
	}

	public Boolean getEstJouee() {
		return this.estJouee;
	}

}
