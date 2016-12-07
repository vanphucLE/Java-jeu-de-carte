package com.sdz.modele;

import java.util.ArrayList;
import java.util.Scanner;

import com.sdz.cartes.CarteAction;

public class Main {

	public static void main(String[] args) {
		ArrayList<Joueur> listeJoueursVirtuel = new ArrayList<Joueur>();
		listeJoueursVirtuel.add(new JoueurVirtuel(2, "ALBERT", 21));
		listeJoueursVirtuel.add(new JoueurVirtuel(3, "ANZAR", 22));
		listeJoueursVirtuel.add(new JoueurVirtuel(4, "DIOUF", 21));
		listeJoueursVirtuel.add(new JoueurVirtuel(5, "GALANTE", 23));
		listeJoueursVirtuel.add(new JoueurVirtuel(6, "GENIN", 21));
		listeJoueursVirtuel.add(new JoueurVirtuel(7, "GUILLOUX", 25));
		listeJoueursVirtuel.add(new JoueurVirtuel(8, "LINARD", 20));
		listeJoueursVirtuel.add(new JoueurVirtuel(9, "NEULAT", 21));

		ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
		Scanner sc = new Scanner(System.in);
		String nb = "";
		do {
			System.out.print("Entrez le nombre de joueur (le nombre compris entre 2 et 9): ");
			nb = sc.nextLine();
			nb = nb.trim();
		} while (!nb.matches("\\d"));
		int nombreJoueur = Integer.parseInt(nb);

		listeJoueurs.add(new JoueurPhysique( "Phuc", 21));

		for (int i = 1; i < nombreJoueur; i++) {
			listeJoueurs.add(listeJoueursVirtuel.get(i - 1));
		}
		
		Partie partie= new Partie(listeJoueurs);
		for (Joueur j:partie.getListeJoueurs()){
			System.out.println(j);
		}
		for (CarteAction carte: partie.getJeuDeCartes().getListeCartesAction()){
			System.out.println(carte);
		};
	}

}
