package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

public interface Stategie {
	public CarteAction choisirCarteJouer(JoueurVirtuel jV, Partie partie);
	public void jouerCapaciteSpecial();
	public LinkedList<CarteAction> choisirCartesDefausser(Partie partie);
	//Choisir idDivinite pour attaquer
	public int choisirIdDivinite(Partie partie);
	
}
