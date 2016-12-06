package com.sdz.cartes;

import com.sdz.modele.*;
import java.util.*;

import com.sdz.modele.Joueur;
import com.sdz.modele.Partie;

public class CapaciteSpeciale {
	public CapaciteSpeciale(){
		
	}
	public void capacite(int id) {
		if (id < 6) {
			Partie.joueurEncours.ptAction_Jour++;
		}
		if (id == 6) {
		}
		if (id == 7) {
		}
		if (id == 8) {
			System.out.println("Choisir Divinité pour prendre ses 2 cartes");
			boolean choix = true;
			while (choix) {
				System.out.print("Choisir id de la Divinité");
				Iterator<Joueur> it = Partie.listeJoueurs.iterator();
				while (it.hasNext()) {
					System.out.println("id" + it.next().getId() + it.next().getNom());
				}
				Scanner sc = new Scanner(System.in);
				int Divine;
				Divine = sc.nextInt();
				while (it.hasNext()) {
					if (it.next().getId() == Divine && !it.next().equals(Partie.joueurEncours)
							&& it.next().laMain.listeCarteA.size() > 2) {
						Collections.shuffle(it.next().laMain.listeCarteA);
						Partie.joueurEncours.laMain.listeCarteA.add(it.next().laMain.listeCarteA.pop());
						Partie.joueurEncours.laMain.listeCarteA.add(it.next().laMain.listeCarteA.pop());
						choix = false;
					}
					;
				}

			}
		}
		if (id == 9) {
		}
		if (id == 10) {
		}
		if (id == 11) {
		}
		if (id == 12) {
			// guide revient dans sa main et croyant lie revient au centre de la
			// table
		}
		if (id == 13) {
			// relancer de de cosmonogie
			Partie.lancerDe();
		}
		if (id > 13 && id < 19) {
			// donner une point d'action de nuit
			Partie.joueurEncours.ptAction_Nuit++;
		}
		if (id == 19) {
		}
		if (id == 20) {
		}
		if (id == 21) {
			// pioche 2 carte au harsards dans la main une autre Divinité
		}
		if (id == 22) {
		}
		if (id == 23) {
		}
		if (id == 24) {
			// retirer les croyants rattacher une autre divinite
		}
		if (id == 25) {
			// prender pt Action
			System.out.println("Choisir Divinité pour prendre ses points d'action");
			boolean choix = true;
			while (choix) {
				System.out.print("Choisir id de la Divinité");
				Iterator<Joueur> it = Partie.listeJoueurs.iterator();
				while (it.hasNext()) {
					if (it.next().getId() > Partie.joueurEncours.getId()) {
						System.out.println("id" + it.next().getId() + it.next().getNom());
					}
				}
				Scanner sc = new Scanner(System.in);
				int Divine;
				Divine = sc.nextInt();
				if (Divine > Partie.joueurEncours.getId() && Divine < Partie.listeJoueurs.size()) {
					Partie.joueurEncours.ptAction_Jour = Partie.joueurEncours.ptAction_Jour
							+ Partie.listeJoueurs.get(Divine - 1).ptAction_Jour;
					Partie.listeJoueurs.get(Divine - 1).ptAction_Jour = 0;
					Partie.joueurEncours.ptAction_Nuit = Partie.joueurEncours.ptAction_Nuit
							+ Partie.listeJoueurs.get(Divine - 1).ptAction_Nuit;
					Partie.listeJoueurs.get(Divine - 1).ptAction_Nuit = 0;
					Partie.joueurEncours.ptAction_Neant = Partie.joueurEncours.ptAction_Neant
							+ Partie.listeJoueurs.get(Divine - 1).ptAction_Neant;
					Partie.listeJoueurs.get(Divine - 1).ptAction_Neant = 0;
					choix = false;
				}

			}
		}
		if (id == 26) {
			// benificier une capacite speciale dune carte croyant appartenant a
			// un autre joueur
		}
		if (id > 26 && id < 32) {
			Partie.joueurEncours.ptAction_Neant++;
		}
		if (id == 32) {

		}
	}
}
