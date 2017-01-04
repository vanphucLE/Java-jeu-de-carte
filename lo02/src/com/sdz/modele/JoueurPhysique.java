package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.GuideSpirituel;

public class JoueurPhysique extends Joueur {
	private Partie partie;

	public JoueurPhysique(String nom, int age) {
		super(1, nom, age);
		this.bot = false;
	}

	@Override
	public void jouer(Partie partie) {
		this.seDefausserCartesEtCompleter(partie);
		this.choisirCarte(partie);

		Boolean continu = false;
		int commande = JOptionPane.showConfirmDialog(null, "Voulez-vous sacrifier la cartes ?");
		if (commande == 0) {
			continu = true;
		}
		while (continu) {
			JOptionPane.showMessageDialog(null, "Choissiez la carte pour la sacifier! ");
			this.actionEnTrain = "sacrifier";
			try {
				this.partie.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// this.sacrifierCarte(partie);
			continu = false;
			int commande2 = JOptionPane.showConfirmDialog(null, "Voulez-vous continuer à sacrifier la cartes ?");
			if (commande2 == 0) {
				continu = true;
			}
		}

	}

	@Override
	public void sacrifierCarte(Partie partie) {
		/*
		 * Carte Croyant: id :1 -->37 Carte Guide Spirituel: 38-->57 Carte Deus
		 * Ex : 58 --> 75 Carte Apocalypse: 76 --> 80 Carte Divinite: 81 -->90
		 */
		// if (1 <= idSacrifice && idSacrifice <= 37) {
		// this.sacrifierCroyant(idSacrifice, partie);
		// }
		// if (38 <= idSacrifice && idSacrifice <= 57) {
		// this.sacrifierCroyant(idSacrifice, partie);
		// }
	}

	@Override
	public void seDefausserCartesEtCompleter(Partie partie) {
		JeuDeCartes jeuDeCartes = partie.getJeuDeCartes();
		System.out.println("Votre Point Action: (Jour: " + this.ptAction_Jour + ") " + "(Nuit: " + this.ptAction_Nuit
				+ ") " + "(Néant: " + this.ptAction_Neant + ")");
		System.out.println("Les cartes actions tenu dans vôtre main:");
		System.out.println(this.laMain);

		int commande = JOptionPane.showConfirmDialog(null, "À vous de jouer!\nVoulez-vous défausser les cartes?");
		if (commande == 0) {
			this.actionEnTrain = "defausser";

			JOptionPane.showMessageDialog(null, "Choissiez les cartes pour les défausser en les cliquant! ");
			try {
				this.partie.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		JOptionPane.showMessageDialog(null, "Complétez sa main à 7 cartes! ");
		this.compeleter7Carte(jeuDeCartes);
	}

	@Override
	public void choisirCarte(Partie partie) {
		System.out.println(partie.getEspaceCommun());
		System.out.println("(Rappeler) Votre Point Action  (Jour: " + this.ptAction_Jour + ") | " + "(Nuit: "
				+ this.ptAction_Nuit + ") | " + "(Néant: " + this.ptAction_Neant + ")");
		Boolean continu = false;
		if ((this.ptAction_Jour + this.ptAction_Nuit + this.ptAction_Neant) <= 0) {
			JOptionPane.showMessageDialog(null, "Vous n'avez pas point d'Action pour jouer! ");
		} else {
			int commande = JOptionPane.showConfirmDialog(null, "Voulez-vous choisir la carte pour jouer?");
			if (commande == 0) {
				continu = true;
			}
			while (continu) {
				JOptionPane.showMessageDialog(null, "Choissiez la carte pour jouer en la cliquant! ");
				this.actionEnTrain = "jouer";

				// On suspend la partie pour attendre le commande du joueur
				// physique
				try {
					this.partie.suspend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				continu = false;
				if ((this.ptAction_Jour + this.ptAction_Nuit + this.ptAction_Neant) > 0) {
					System.out.println("(Rappeler) Votre Point Action  (Jour: " + this.ptAction_Jour + ") | "
							+ "(Nuit: " + this.ptAction_Nuit + ") | " + "(Néant: " + this.ptAction_Neant + ")\n");
					int commande2 = JOptionPane.showConfirmDialog(null,
							"Voulez-vous continuer à jouer la carte pour jouer?");
					if (commande2 == 0) {
						continu = true;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vous n'avez pas encore point d'action pour jouer! ");
				}
			}
		}
	}

	public LinkedList<CarteAction> croyantsPeutEtreGuidee() {
		LinkedList<CarteAction> listeCroyants = new LinkedList<CarteAction>();
		EspaceCommun espaceCommun = this.partie.getEspaceCommun();
		for (CarteAction carteA : espaceCommun.getListeCartesPret()) {
			Boolean test = false;
			for (String dogmeA : carteA.getDogme()) {
				for (String dogmeD : this.carteG.getDogme()) {
					if (dogmeD.equals(dogmeA)) {
						test = true;
						break;
					}
				}
				if (test)
					break;
			}
			if (test) {
				listeCroyants.add(carteA);
			}
		}
		return listeCroyants;
	}

	private GuideSpirituel carteG;
	private int nbGuider;
	private LinkedList<CarteAction> listeCroyantsGuidee;

	// Jouer Carte GuideSpirituel
	public void jouerGuideSpirituel(CarteAction carte) {
		EspaceCommun espaceCommun = this.partie.getEspaceCommun();
		this.laMain.seDeffausserCarte(carte);

		this.carteG = (GuideSpirituel) carte;
		this.nbGuider = this.carteG.getNbGuider();
		this.listeCroyantsGuidee = new LinkedList<CarteAction>();

		// On trouve la liste des cartes Croyants qui peuvent être guidées
		LinkedList<CarteAction> listeCroyants = this.croyantsPeutEtreGuidee();

		if (listeCroyants.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Il n'y a aucun carte croyant que cette carte peut guider!\n .Cette carte est alors défaussée! ");
			// Cette carte va être récupérée par le jeu de carte
			this.partie.getJeuDeCartes().recupererCarteAction(carte);
		} else {
			this.carteG.setEstSacrifie(true);
			JOptionPane.showMessageDialog(null, "Vous pouvez faire guider " + carteG.getNbGuider()
					+ " carte(s) Croyant(s).\nChoissiez la carte croyant dans l'espace commun pour la guider en la cliquant! ");
			this.actionEnTrain = "guider";
		}
	}

	public void ajouterGuidee() {
		this.laMain.ajouterGuidee(listeCroyantsGuidee, carteG);
	}

	public int getNbGuider() {
		return nbGuider;
	}

	public void ajouterCroyantGuidee(CarteAction carte) {
		this.partie.getEspaceCommun().supprimerCarte(carte.getId());
		this.listeCroyantsGuidee.add(carte);
		this.nbGuider--;
	}

	public void jouerDeusEx(Partie partie) {

	}

	@Override
	public void jouerApocalypse(CarteAction carte, Partie partie) {
		//
		if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas jouer la carte Apocalypse en ce tour! ");
			System.out.println("Vous ne pouvez pas jouer la carte Apocalypse en ce tour");
		} else {
			this.laMain.seDeffausserCarte(carte);
			this.partie.getJeuDeCartes().recupererCarteAction(carte);
			partie.setEstApocalypseAvant(-1);
			int[] arPriere = new int[partie.getListeJoueurs().size()];
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
							partie.eliminerJoueur(j);
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
							break;
						}
					}
				}
			}
		}
	}

	// convertir et tester valeur entrée
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

	// tester valeur entree si la carte est guide spirituel
	private Boolean testGuideSpirituelEntree(CarteAction carteD, EspaceCommun espaceCommun) {
		if (carteD.getType().equals("GuideSpirituel")) {
			Boolean test = false;
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
			if (test == false) {
				System.out.println(
						"Le carte Guide Spirituel que vous avez choisi ne peut guider aucune carte Croyant dans l'espace Commun");
			} else {
				this.laMain.completerCarteAction(carteD);
			}
			return test;
		} else {
			return true;
		}
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

}
