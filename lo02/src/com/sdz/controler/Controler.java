package com.sdz.controler;

import com.sdz.modele.Partie;

public class Controler {
	private Partie partie;
	
	public Controler(Partie partie) {
		this.partie = partie;
	}
	
	public void lancerDe(){
		this.partie.lancerDe();
		
	}
	

	public Partie getPartie() {
		return partie;
	}


}
