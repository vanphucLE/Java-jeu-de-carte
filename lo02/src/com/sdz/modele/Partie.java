package com.sdz.modele;

import com.sdz.cartes.CarteAction;

public class Partie {
	
	private int nbJoueur;
	private Joueur gagnant;
	private Joueur joueurEncours;
	
	public void setNbJoueur(int nbJoueur){
		this.nbJoueur=nbJoueur;
	}
	
	public int getNbJoueur(){
		return this.nbJoueur;
	}
	public String lancerDe(){
		String[] de={"","Jour","Nuit","Néant"};
		int num = (int) Math.ceil(3 * Math.random());
		return de[num];
	}
	
	public void annoncerGagnant(){
		
	}
	/*
	public void jouerCarte(CarteAction carte) {
		switch (carte.getType()) {
		case "Croyant":
			jouerCroyant();
			break;
		case "DeusEx":
			jouerCroyant();
			break;
		case "Apocalypse":
			jouerCroyant();
			break;
		case "GuideSpirituel":
			jouerCroyant();
			break;
		}
	}
	*/
	
	
	
	
}
