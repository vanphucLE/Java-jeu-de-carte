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
		String commande = "";
		if (this.laMain.getListeGuideSpirituelGuider().size() > 0) {
			Scanner sc = new Scanner(System.in);
			do {
				System.out.print("Voulez-vous sacrifier la cartes ? (Y/N):");
				commande = sc.nextLine();
			} while (!commande.equals("Y") && !commande.equals("N"));
			if (commande.equals("Y")) {
				this.sacrifierCarte(partie);
			}
		}
	}

	@Override
	public void seDefausserCartesEtCompleter(Partie partie) {
		JeuDeCartes jeuDeCartes = partie.getJeuDeCartes();
		System.out.println("Votre Point Action: (Jour: " + this.ptAction_Jour + ") " + "(Nuit: " + this.ptAction_Nuit
				+ ") " + "(Néant: " + this.ptAction_Neant + ")");
		System.out.println("Les cartes actions tenu dans vôtre main:");
		System.out.println(this.laMain);

		int commande = JOptionPane.showConfirmDialog(null, "Voulez-vous défausser les cartes?");
		if (commande == 0) {
			this.actionEnTrain = "defausser";

			JOptionPane.showMessageDialog(null, "Choissiez les cartes pour les défausser en les cliquant! ");
			try {
				this.partie.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(null, "Complétez sa main à 7 cartes! ");
			this.Compeleter7Carte(jeuDeCartes);
		}
	}

	public void notifyLaMain() {
		this.setChanged();
		this.notifyObservers();
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

				// On suspend la partie pour attendre le commande du joueur physique
				try {
					this.partie.suspend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				continu=false;
				if ((this.ptAction_Jour + this.ptAction_Nuit + this.ptAction_Neant) > 0) {
					System.out.println("(Rappeler) Votre Point Action  (Jour: " + this.ptAction_Jour + ") | "
							+ "(Nuit: " + this.ptAction_Nuit + ") | " + "(Néant: " + this.ptAction_Neant + ")\n");
					int commande2 = JOptionPane.showConfirmDialog(null, "Voulez-vous continuer à jouer la carte pour jouer?");
					if(commande2==0){
						continu=true;
					}
				}
			}
		}
	}

	// Jouer Carte GuideSpirituel
	public void jouerGuideSpirituel(CarteAction carte, EspaceCommun espaceCommun) {
		GuideSpirituel carteG = (GuideSpirituel) carte;
		LinkedList<CarteAction> listeCroyantsGuidee = new LinkedList<CarteAction>();
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
			CarteAction carteA = espaceCommun.supprimerCarte(elem);
			carteA.setEstSacrifie(true);
			listeCroyantsGuidee.add(carteA);
		}
		carteG.setEstSacrifie(true);
		this.laMain.ajouterGuidee(listeCroyantsGuidee, carteG);
	}

	public void jouerDeusEx(Partie partie) {

	}

	@Override
	public void jouerApocalypse(CarteAction carte, Partie partie) {
		//
		if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
			System.out.println("Vous ne pouvez pas jouer la carte Apocalypse en ce tour");
			this.laMain.completerCarteAction(carte);
		} else {
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
	public void sacrifierCarte(Partie partie) {
		LinkedList<CarteAction> liste = new LinkedList<CarteAction>();
		for (CarteAction carte : this.laMain.getListeGuideSpirituelGuider()) {
			liste.add(carte);
		}
		for (LinkedList<CarteAction> listeCarte : this.laMain.getListeCroyantGuidee()) {
			for (CarteAction carte : listeCarte) {
				liste.add(carte);
			}
		}
		Scanner sc = new Scanner(System.in);
		String idChoisi = "";
		Boolean choice = false;
		int idSacrifice = 0;
		Joueur joueurEnCours = partie.getJoueurEncours();
		Boolean continu;
		do {
			partie.setJoueurEncours(joueurEnCours);
			do {
				do {
					System.out.print("Choissiez la carte pour sacrifier (Entrez l'id, comme: 1): ");
					idChoisi = sc.nextLine();
				} while (!this.testEntrer(idChoisi, liste));
				idSacrifice = Integer.parseInt(idChoisi);
				for (CarteAction carte : liste) {
					if (idSacrifice == carte.getId() && carte.getEstSacrifie()) {
						choice = true;
						System.out.println("Cette carte ne peut pas être sacifiée!");
					}
				}
			} while (!choice);
			String commande = "";
			continu = false;
			if (joueurEnCours.getLaMain().getListeCroyantGuidee().size() > 0 && !partie.getFiniTour()) {
				do {
					System.out.print("Vous voulez continuer à sacrifier l'autre cartes (Y/N) ?    ");
					commande = sc.nextLine();
				} while (!commande.equals("Y") && !commande.equals("N"));
				continu = commande.equals("Y");
			}
		} while (joueurEnCours.getLaMain().getListeCroyantGuidee().size() > 0 && continu);
		/*
		 * Carte Croyant: id :1 -->37 Carte Guide Spirituel: 38-->57 Carte Deus
		 * Ex : 58 --> 75 Carte Apocalypse: 76 --> 80 Carte Divinite: 81 -->90
		 */
		if (1 <= idSacrifice && idSacrifice <= 37) {
			this.sacrifierCroyant(idSacrifice, partie);
		}
		if (38 <= idSacrifice && idSacrifice <= 57) {
			this.sacrifierCroyant(idSacrifice, partie);
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
