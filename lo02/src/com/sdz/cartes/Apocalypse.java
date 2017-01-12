package com.sdz.cartes;

import javax.swing.JOptionPane;

import com.sdz.modele.Joueur;
import com.sdz.modele.Partie;

public class Apocalypse extends CarteAction {
	/* 
	 *Décrire une carte Apocalypse 
	 */
	
	private static final String[] origineApocalypse={"Jour","Nuit","Néant","","",""};
	
	public Apocalypse(int id){
		this.id=id;
		this.origine=origineApocalypse[id-76];
		this.type="Apocalypse";
		this.nom="Apocalypse";
	}
	/*
	 * effectuer une capacite d'une carte sur la partie mentione dans la parametre de ce methode
	 * 
	 */
	public void effectuerCapaciteSpecial(Partie partie){
		partie.setEstApocalypseAvant(-1);
		int[] arPriere = new int[partie.getListeJoueurs().size() + 1];
		int indice = -1;
		for (Joueur j : partie.getListeJoueurs()) {
			j.setPtPriere();
			indice++;
			arPriere[indice] = j.getPtPriere();
		}

		for (int i = 0; i <= indice - 1; i++) {
			for (int j = i + 1; j <= indice; j++) {
				if (arPriere[i] < arPriere[j]) {
					int tg = arPriere[i];
					arPriere[i] = arPriere[j];
					arPriere[j] = tg;
				}
			}
		}

		if (indice + 1 >= 4) {
			if (arPriere[indice] == arPriere[indice - 1]) {
				System.out.println(
						"Il y a 2 joueur ayant le même point prière dernier. Cette carte Apocalypse est défaussé.");
				JOptionPane.showMessageDialog(null,
						"Il y a 2 joueur ayant le même point prière dernier. Cette carte Apocalypse est défaussé!");
			} else {
				for (Joueur j : partie.getListeJoueurs()) {
					if (j.getPtPriere() == arPriere[indice]) {
						j.setElimine(true);
						partie.setFiniTour(true);
						break;
					}
				}
			}
		} else {
			if (arPriere[0] == arPriere[1]) {
				System.out.println(
						"Il y a 2 joueur ayant le même point prière premier. Cette carte Apocalypse est défaussé.");
				JOptionPane.showMessageDialog(null,
						"Il y a 2 joueur ayant le même point prière premier. Cette carte Apocalypse est défaussé!");
			} else {
				for (Joueur j : partie.getListeJoueurs()) {
					if (j.getPtPriere() == arPriere[0]) {
						partie.setJoueurgagnant(j);
						partie.setEstFini(true);
						JOptionPane.showMessageDialog(null, j.getNom() + " a gagné!");
						break;
					}
				}
			}
		}
	}
	
	

}
