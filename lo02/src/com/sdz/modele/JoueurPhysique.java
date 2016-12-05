package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;

public class JoueurPhysique extends Joueur {

	// Id du joueur phisique est 1 par défault;
	private static final int id = 1;

	public JoueurPhysique(int id, String nom, int age) {
		super(id, nom, age);
	}

	public void jouer(JeuDeCartes jeuDeCartes, EspaceCommun espaceCommun) {
		this.seDefausserCartes(jeuDeCartes);
		this.Compeleter7Carte(jeuDeCartes);
		this.choisirCarteReel(espaceCommun);

	}

	// Un joueur veut défausser un plusieur Cartes
	private void seDefausserCartes(JeuDeCartes jeuDeCartes) {
		System.out.println("Votre point Action : " + this.ptAction + " ---- Origine : " + this.ptActionOrigine);
		Scanner sc = new Scanner(System.in);
		String commande = "";
		System.out.print("Voulez-vous défausser les cartes ? (Y/N):");
		commande = sc.nextLine();
		if (commande == "Y") {
			String commande2 = "";
			System.out.println("Les cartes actions tenu dans vôtre main:");
			System.out.println(this.laMain);
			do {
				System.out.print("Choisir les Id dont les cartes actions déffausées (Ex: 1 3 5) : ");
				commande2 = sc.nextLine();
			} while (!this.testEntrer(commande2, this.laMain.getListeCarteA()));
			String[] cartesDef = {};
			cartesDef = commande2.split(" ");
			for (String str : cartesDef) {
				int num = Integer.parseInt(str);
				// la carte défaussées est recupéré dans le jeu de carte.
				CarteAction carteA = this.laMain.seDeffausserCarte(num);
				jeuDeCartes.recupererCarteAction(carteA);
			}
		}
	}

	// Le joueur compléte 7 cartes dans la main
	private void Compeleter7Carte(JeuDeCartes jeuDeCartes) {
		int compte = this.laMain.getListeCarteA().size();
		while (compte < 7) {
			compte++;
			CarteAction carte = jeuDeCartes.distribuerCarteAction();
			this.getLaMain().completerCarteAction(carte);
		}
		System.out.println("Vous avez complété 7 Cartes. Maintenant, les cartes sont: ");
		System.out.println(this.laMain);
	}

	private void choisirCarteReel(EspaceCommun espaceCommun) {
		System.out.println("Les cartes en l'espace commun: ");
		System.out.println(espaceCommun);
		System.out.println(
				"(Rappeler) Votre point Action : " + this.ptAction + " ---- Origine : " + this.ptActionOrigine);
		Boolean continu = true;
		while (this.ptAction > 0 && continu) {
			System.out.print("Choissiez Id dont la carte action pour jouer(Ex: 1): ");
			Scanner sc = new Scanner(System.in);
			String idChoisir = sc.nextLine();
			int idChoisirInt = Integer.parseInt(idChoisir);
			CarteAction carteChoisi = this.laMain.seDeffausserCarte(idChoisirInt);
			System.out.print("Vous avez choisi la carte: " + carteChoisi);
			Boolean test = this.setPtAction(carteChoisi);
			if (!test) {
				// Si la choice de joueur n'est pas valide, la main va récupérer
				// la carte qui se sont défaussé.
				this.laMain.completerCarteAction(carteChoisi);
			}
			switch (carteChoisi.getType()) {
			case "Croyant":
				this.jouerCroyant(carteChoisi, espaceCommun);
				break;
			case "GuideSpirituel":
				this.jouerGuideSpirituel(carteChoisi, espaceCommun);
				break;
			case "DeusEx":
				this.jouerDeusEx();
				break;
			case "Apocalypse":
				this.jouerApocalypse();
				break;
			}
			if (this.ptAction > 0) {
				System.out.print("Vous avez encore " + this.ptAction + " point d'action " + " ---- Origine : "
						+ this.ptActionOrigine);
				System.out.print("Vous voulez continuer à jouer l'autre cartes (Y/N) ?    ");
				String commande = sc.nextLine();
				continu = (commande.equals("Y")) ? true : false;
			}
		}

	}

	private void jouerCroyant(CarteAction carte, EspaceCommun espaceCommun) {
		espaceCommun.ajouterCarte((Croyant) carte);
	}

	private void jouerGuideSpirituel(CarteAction carte, EspaceCommun espaceCommun) {
		GuideSpirituel carteG = (GuideSpirituel) carte;
		LinkedList<Croyant> listeCroyants = new LinkedList<Croyant>();

		Scanner sc = new Scanner(System.in);
		String commande = "";
		do {
			System.out.print("Vous pouvez guider " + carteG
					+ " carte(s) croyant(s). Choisir les Id dont la carte croyants dans l'espace commun pour guider (Ex: 1 2): ");
			commande = sc.nextLine();
		} while (!this.testEntrer(commande, espaceCommun.getListeCartes()));

		LinkedList<Integer> idCartesGuidee = this.convertIdsEntree(commande);
		for (int elem : idCartesGuidee) {
			listeCroyants.add(espaceCommun.supprimerCarte(elem));
		}
		this.laMain.ajouterGuidee(listeCroyants, carteG);
	}

	private void jouerDeusEx() {

	}

	private void jouerApocalypse() {

	}

	// on utilise cette méthode pour mettre à jour le point d'action de joueur
	// après qu'il a choisi une carte pour jouer.
	private Boolean setPtAction(CarteAction carte) {
		if (carte.getOrigine() != "") {
			if (carte.getOrigine() == this.ptActionOrigine) {
				this.ptAction--;
			} else if (carte.getOrigine() == "Néant") {
				if (this.ptAction < 2) {
					System.out.println("Eurreur en choissant!!");
					return false;
				} else {
					this.ptAction -= 2;
				}
			}
		}
		return true;
	}

	private LinkedList<Integer> convertIdsEntree(String str) {
		Boolean test = true;
		str = str.trim();
		String[] arrayIdStr = str.split(" ");
		LinkedList<Integer> arrayId = new LinkedList<Integer>();
		for (String elem : arrayIdStr) {
			if (elem.matches("\\d+")) {
				arrayId.add(Integer.parseInt(elem));
			} else if (elem != "") {
				System.out.println("Eurreur entree!!! ");
				test = false;
				break;
			}
		}
		if (test == true) {
			return arrayId;
		} else {
			return new LinkedList<Integer>();
		}
	}

	private Boolean testEntrer(String stringEntree, LinkedList<CarteAction> listeContenantId) {
		Boolean test = true;
		LinkedList<Integer> arrayIdEntree = this.convertIdsEntree(stringEntree);
		LinkedList<Integer> arrayId = new LinkedList<Integer>();
		if (arrayId.size() != 0) {
			Iterator<CarteAction> it = listeContenantId.iterator();
			while (it.hasNext()) {
				CarteAction carte = it.next();
				arrayId.add(carte.getId());
			}
			for (int elem : arrayIdEntree) {
				if (arrayId.indexOf(elem) == -1) {
					test = false;
				}
			}
		} else {
			test = false;
		}
		return test;
	}

	public void JoueurCapaSpeReel() {

	}
}
