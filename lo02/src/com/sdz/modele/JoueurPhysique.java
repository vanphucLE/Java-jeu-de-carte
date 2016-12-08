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

	public JoueurPhysique(String nom, int age) {
		super(id, nom, age);
		this.laMain = new LaMain();
		this.bot = false;
	}

	@Override
	public void jouer(Partie partie) {
		this.seDefausserCartes(partie.getJeuDeCartes());
		this.Compeleter7Carte(partie.getJeuDeCartes());
		this.choisirCarte(partie);
		String commande = "";
		if (this.laMain.getListeGuideSpirituelGuider().size() > 0) {
			Scanner sc =new Scanner(System.in);
			do {
				System.out.print("Voulez-vous sacrifier la cartes ? (Y/N):");
				commande = sc.nextLine();
			} while (!commande.equals("Y") && !commande.equals("N"));
			if(commande.equals("Y")){
				this.sacrifierCarte(partie);
			}
		}
	}

	@Override
	public void seDefausserCartes(JeuDeCartes jeuDeCartes) {
		System.out.println("Votre Point Action: (Jour: " + this.ptAction_Jour + ") " + "(Nuit: " + this.ptAction_Nuit
				+ ") " + "(Néant: " + this.ptAction_Neant + ")");
		System.out.println("Les cartes actions tenu dans vôtre main:");
		System.out.println(this.laMain);
		Scanner sc = new Scanner(System.in);
		String commande = "";
		do {
			System.out.print("Voulez-vous défausser les cartes ? (Y/N):");
			commande = sc.nextLine();
		} while (!commande.equals("Y") && !commande.equals("N"));
		if (commande.equals("Y")) {
			String commande2 = "";
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

	@Override
	public void choisirCarte(Partie partie) {
		partie.getEspaceCommun();
		System.out.println(partie.getEspaceCommun());
		System.out.println("(Rappeler) Votre Point Action  (Jour: " + this.ptAction_Jour + ") | " + "(Nuit: "
				+ this.ptAction_Nuit + ") | " + "(Néant: " + this.ptAction_Neant + ")");
		Boolean continu = true;
		while ((this.ptAction_Jour + this.ptAction_Nuit + this.ptAction_Neant) > 0 && continu) {
			String idChoisi = "";
			Scanner sc = new Scanner(System.in);
			int idChoisirInt = 0;
			CarteAction carteChoisi;
			do {
				do {
					System.out.print("Choissiez Id dont la carte action pour jouer(Ex: 1): ");
					idChoisi = sc.nextLine();
				} while (!this.testEntrer(idChoisi, this.laMain.getListeCarteA()));
				idChoisirInt = Integer.parseInt(idChoisi);
				carteChoisi = this.laMain.seDeffausserCarte(idChoisirInt);
			} while (!testGuideSpirituelEntree(carteChoisi, partie.getEspaceCommun()));

			System.out.println("Vous avez choisi la carte: " + carteChoisi);

			Boolean test = this.setPtAction(carteChoisi);
			if (!test) {
				// Si la choice de joueur n'est pas valide, la main va récupérer
				// la carte qui se sont défaussé.
				this.laMain.completerCarteAction(carteChoisi);
			} else {
				switch (carteChoisi.getType()) {
				case "Croyant":
					this.jouerCroyant(carteChoisi, partie.getEspaceCommun());
					break;
				case "GuideSpirituel":
					this.jouerGuideSpirituel(carteChoisi, partie.getEspaceCommun());
					break;
				case "DeusEx":
					this.jouerDeusEx(partie);
					break;
				case "Apocalypse":
					this.jouerApocalypse(carteChoisi, partie);
					break;
				}
			}
			if ((this.ptAction_Jour + this.ptAction_Nuit + this.ptAction_Neant) > 0) {
				System.out.println("(Rappeler) Votre Point Action  (Jour: " + this.ptAction_Jour + ") | " + "(Nuit: "
						+ this.ptAction_Nuit + ") | " + "(Néant: " + this.ptAction_Neant + ")\n");
				System.out.print("Vous voulez continuer à jouer l'autre cartes (Y/N) ?    ");
				String commande = sc.nextLine();
				continu = (commande.equals("Y")) ? true : false;
			}
		}

	}

	//Jouer Carte GuideSpirituel
	private void jouerGuideSpirituel(CarteAction carte, EspaceCommun espaceCommun) {
		GuideSpirituel carteG = (GuideSpirituel) carte;
		LinkedList<Croyant> listeCroyantsGuidee = new LinkedList<Croyant>();
		LinkedList<CarteAction> listeCroyants = new LinkedList<CarteAction>();

		for (CarteAction carteA : espaceCommun.getListeCartesPret()) {
			Boolean test = false;
			for (String dogmeA : carteA.getDogme()) {
				for (String dogmeD : carteG.getDogme()) {
					if (dogmeD.equals(dogmeA)) {
						test = true;
						break;
					}
				}
			}
			if (test == true) {
				listeCroyants.add(carteA);
			}
		}

		Scanner sc = new Scanner(System.in);
		String commande = "";
		LinkedList<Integer> idCartesGuidee;
		do {
			do {
				System.out.print("Vous pouvez guider " + carteG.getNbGuider()
						+ " carte(s) croyant(s). Choisir les Id dont la carte croyants dans l'espace commun pour guider suivant(Ex: 1 2): ");
				for (CarteAction carteA : listeCroyants) {
					System.out.println("  +" + carteA);
				}
				commande = sc.nextLine();
			} while (!this.testEntrer(commande, listeCroyants));
			idCartesGuidee = this.convertIdsEntree(commande);
			if (idCartesGuidee.size() <= carteG.getNbGuider()) {
				System.out.print("Vous ne pouvez guider que " + carteG.getNbGuider() + " carte(s) croyant(s)");
			}
		} while (idCartesGuidee.size() > carteG.getNbGuider());
		for (int elem : idCartesGuidee) {
			listeCroyantsGuidee.add((Croyant) espaceCommun.supprimerCarte(elem));
		}
		this.laMain.ajouterGuidee(listeCroyantsGuidee, carteG);
	}

	private void jouerDeusEx(Partie partie) {

	}

	// Jouer carteApocalypse
	private void jouerApocalypse(CarteAction carte, Partie partie) {
		//
		if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
			System.out.println("Vous ne pouvez pas jouer la carte Apocalypse en ce tour");
			this.laMain.completerCarteAction(carte);
		} else {
			partie.setEstApocalypseAvant(-1);
			int[] arPriere = {};
			int indice = -1;
			for (Joueur j : partie.getListeJoueurs()) {
				j.setPtPriere();
				arPriere[indice++] = j.getPtPriere();
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
				} else {
					for (Joueur j : partie.getListeJoueurs()) {
						if (j.getPtPriere() == arPriere[indice]) {
							partie.eliminerJoueur(j);
							break;
						}
					}
				}
			} else {
				if (arPriere[0] == arPriere[1]) {
					System.out.println(
							"Il y a 2 joueur ayant le même point prière premier. Cette carte Apocalypse est défaussé.");
				} else {
					for (Joueur j : partie.getListeJoueurs()) {
						if (j.getPtPriere() == arPriere[0]) {
							partie.setJoueurgagnant(j);
							partie.setEstFini(true);
							break;
						}
					}
				}
			}
		}
	}
	
	@Override 
	public void sacrifierCarte(Partie partie){
		System.out.println("Vous pouvez sacrifier les cartes suivants: ");
		for()
	}

	// on utilise cette méthode pour mettre à jour le point d'action de joueur
	// après qu'il a choisi une carte pour jouer.
	private Boolean setPtAction(CarteAction carte) {
		if (carte.getOrigine() != "") {
			if (carte.getOrigine() == "Jour") {
				if (this.ptAction_Jour == 0) {
					System.out.println("Eurreur en choissant!!");
					return false;
				} else {
					this.ptAction_Jour--;
				}
			} else if (carte.getOrigine() == "Nuit") {
				if (this.ptAction_Nuit == 0) {
					System.out.println("Eurreur en choissant!!");
					return false;
				} else {
					this.ptAction_Nuit--;
				}
			} else if (carte.getOrigine() == "Néant") {
				if (this.ptAction_Neant > 0) {
					this.ptAction_Neant--;
				} else if (this.ptAction_Jour >= 2) {
					this.ptAction_Jour -= 2;
				} else if (this.ptAction_Nuit >= 2) {
					this.ptAction_Nuit -= 2;
				} else {
					System.out.println("Eurreur en choissant!!");
					return false;
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
				System.out.println("Eurreur entrée!!! ");
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

	// tester la valeur entrée
	private Boolean testEntrer(String stringEntree, LinkedList<CarteAction> listeContenantId) {
		Boolean test = true;
		LinkedList<Integer> arrayIdEntree = this.convertIdsEntree(stringEntree);
		LinkedList<Integer> arrayId = new LinkedList<Integer>();
		if (arrayIdEntree.size() != 0) {
			Iterator<CarteAction> it = listeContenantId.iterator();
			while (it.hasNext()) {
				CarteAction carte = it.next();
				arrayId.add(carte.getId());
			}
			for (int elem : arrayIdEntree) {
				if (arrayId.indexOf(elem) == -1) {
					test = false;
					System.out.println("Eurreur entree!!! ");
					break;
				}
			}
		} else {
			test = false;
		}
		return test;
	}

	private Boolean testGuideSpirituelEntree(CarteAction carteD, EspaceCommun espaceCommun) {
		Boolean test = false;
		if (carteD.getType().equals("GuideSpitituel")) {
			for (CarteAction carteA : espaceCommun.getListeCartesPret()) {
				for (String dogmeA : carteA.getDogme()) {
					for (String dogmeD : carteD.getDogme()) {
						if (dogmeD.equals(dogmeA)) {
							test = true;
							break;
						}
					}
				}
			}
		}
		if (test == false) {
			System.out.println(
					"Le carte Guide Spirituel que vous avez choisi ne peut guider aucune carte Croyant dans l'espace Commun");
		} else {
			this.laMain.completerCarteAction(carteD);
		}
		return test;
	}


}
