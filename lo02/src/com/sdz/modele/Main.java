package com.sdz.modele;

import java.util.ArrayList;
import java.util.Scanner;

import com.sdz.cartes.CarteAction;

public class Main {

	public static void main(String[] args) {

		ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
		Scanner sc = new Scanner(System.in);

		String commande = "";
		do {
			System.out.print("Quel niveau voulez jouer (Débutant/Expert) (D/E): ");
			commande=sc.nextLine();
		} while (!commande.equals("D") && !commande.equals("E"));

		String nb = "";
		do {
			System.out.print("Entrez le nombre de joueur (le nombre compris entre 2 et 9): ");
			nb = sc.nextLine();
			nb = nb.trim();
		} while (!nb.matches("\\d"));
		int nombreJoueur = Integer.parseInt(nb);

		listeJoueurs.add(new JoueurPhysique("Phuc", 21));

		if (commande.equals("D")) {
			ArrayList<Joueur> listeJoueursVirtuel = new ArrayList<Joueur>();
			listeJoueursVirtuel.add(new JoueurVirtuel(2, "ALBERT", 21,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(3, "ANZAR", 22,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(4, "DIOUF", 21,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(5, "GALANTE", 23,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(6, "GENIN", 21,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(7, "GUILLOUX", 25,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(8, "LINARD", 20,new Debutant()));
			listeJoueursVirtuel.add(new JoueurVirtuel(9, "NEULAT", 21,new Debutant()));
			for (int i = 1; i < nombreJoueur; i++) {
				listeJoueurs.add(listeJoueursVirtuel.get(i - 1));
			}
		}else{
			ArrayList<Joueur> listeJoueursVirtuel = new ArrayList<Joueur>();
			listeJoueursVirtuel.add(new JoueurVirtuel(2, "ALBERT", 21,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(3, "ANZAR", 22,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(4, "DIOUF", 21,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(5, "GALANTE", 23,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(6, "GENIN", 21,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(7, "GUILLOUX", 25,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(8, "LINARD", 20,new Expert()));
			listeJoueursVirtuel.add(new JoueurVirtuel(9, "NEULAT", 21,new Expert()));
		}

		Partie partie = new Partie(listeJoueurs);

		// for (Joueur j:partie.getListeJoueurs()){
		// System.out.println(j);
		// }
		// for (CarteAction carte:
		// partie.getJeuDeCartes().getListeCartesAction()){
		// System.out.println(carte);
		// };
		partie.jouer();
	}

}
