package com.sdz.cartes;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.sdz.modele.Debutant;
import com.sdz.modele.Expert;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.JoueurVirtuel;
import com.sdz.modele.Partie;
import com.sdz.modele.Stategie;

public class CapaciteSpeciale {
	// id de la carte
	private int id;
	private Partie partie;
	private Joueur joueurEnCours;
	Stategie stategie;

	public CapaciteSpeciale(int id) {
		this.id = id;
	}

	public int choisirlaDivinite() {
		int divine;
		if (!this.partie.getJoueurEncours().estBot()) {
			System.out.println("Choisir une Divinit� pour effectuer la capacite parmi les divinit�s suivants: ");
			int i = 1;
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				System.out.println("   -" + it.next());
				i++;
			}
			Scanner sc = new Scanner(System.in);
			String commande = "";
			do {
				System.out.print("Choisir id de la Divinit�: ");
				commande = sc.nextLine();
				divine = Integer.parseInt(commande);
			} while (!this.testIdDivinite(commande));
		} else {
			JoueurVirtuel j = (JoueurVirtuel) this.joueurEnCours;
			divine = j.getStategie().choisirIdDivinite(partie);
		}
		return divine;
	};

	// // tester valeur entr�e - id de divinit�
	// private Boolean testIdDivinite(String str) {
	// Boolean test = false;
	// str = str.trim();
	// int idConv = 0;
	// if (str.matches("\\d+")) {
	// idConv = Integer.parseInt(str);
	// test = true;
	// }
	// if ((idConv > (partie.getListeJoueurs().size() + 1) || idConv < 0) &&
	// idConv != this.joueurEnCours.getId()) {
	// test = false;
	// }
	// if (test == false) {
	// System.out.println("Eurreur quand vous saisiez la valueur Id du
	// joueur!");
	// }
	// return test;
	// }

	// convertir et tester valeur entr�e
	private int convertIdsEntree(String str) {
		Boolean test = true;
		str = str.trim();
		int idReturn = 0;
		if (str.matches("\\d+")) {
			idReturn = Integer.parseInt(str);
		}
		return idReturn;
	}

	// get liste Joueur ayant le dogme1 ou le dogme2 peut �tre emp�ch� de
	// sacrifier un croyant
	public LinkedList<Joueur> listeJoueurPeutEtreEmpecher(String dogme1, String dogme2) {

		LinkedList<Joueur> listeJoueurs = new LinkedList<Joueur>();
		System.out.println("Les divinit� poss�dant le dogme " + dogme1 + " ou " + dogme2 + ": ");
		for (Joueur j : partie.getListeJoueurs()) {
			if (!this.joueurEnCours.estEqual(j)) {
				Boolean test = false;
				for (String str : j.getLaMain().getCarteDivinite().getDogme()) {
					if (str.equals(dogme1) || str.equals(dogme2)) {
						test = true;
						break;
					}
				}
				if (test == true) {
					System.out.println("  +" + j + "\n   ." + j.getLaMain().getCarteDivinite());
					listeJoueurs.add(j);
				}
			}
		}
		return listeJoueurs;
	}

	public Boolean effectuerCapacite(Partie partie) {
		this.partie = partie;
		this.joueurEnCours = partie.getJoueurEncours();
		Boolean testGlobal = false;
		if (this.id >= 1 && this.id <= 5) {
			this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + 1);
			JOptionPane.showMessageDialog(null, "Vous avez gagner un point d'action Jour!");
		} else if (this.id == 6) {
			// emp�cher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guid�e d'un joueur ayant dogme Nature ou Mystique pour emp�cher sa sacrifice!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("empercherCroyantNatureMystique");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Nature", "Mystique");
				if (listJoueurs.size() > 0) {
					int i = (int) Math.ceil(Math.random() * (listJoueurs.size() - 1));
					Joueur joueur = listJoueurs.get(i);
					if (joueur.getLaMain().getListeCroyantGuidee().size() > 0) {
						jV.getEffectuerCapacite().empecherCroyant(joueur, "Nature", "Mystique",
								joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
					}
				}
			}
		} else if (this.id == 7) {
			// emp�cher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guid�e d'un joueur ayant dogme Chaos ou Mystique pour emp�cher sa sacrifice!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("empercherCroyantChaosMystique");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Chaos", "Mystique");
				if (listJoueurs.size() > 0) {
					int i = (int) Math.ceil(Math.random() * (listJoueurs.size() - 1));
					Joueur joueur = listJoueurs.get(i);
					if (joueur.getLaMain().getListeCroyantGuidee().size() > 0) {
						jV.getEffectuerCapacite().empecherCroyant(joueur, "Chaos", "Mystique",
								joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
					}
				}
			}
		} else if (this.id == 8 || this.id == 21 || this.id == 34) {
			// Prendre 2 carte de l'autre joueur
			int idChoisi=0;
			if (!this.joueurEnCours.estBot()) {
				String[] chaine = new String[this.partie.getListeJoueurs().size() - 1];
				int i = -1;
				for (Joueur j : this.partie.getListeJoueurs()) {
					if (!j.equals(this.joueurEnCours)) {
						i++;
						chaine[i] = j.getNom();
					}
				}
				String nomJ = (String) JOptionPane.showInputDialog(null,
						"Choississez le joueur � prendre 2 carte au hasard", "Acte", JOptionPane.QUESTION_MESSAGE, null,
						chaine, chaine[0]);
				for (Joueur j : this.partie.getListeJoueurs()) {
					if (j.getNom().equals(nomJ)) {
						idChoisi=j.getId();
						break;
					}
				}
			} else {
				if (partie.getNiveau().equals("Debutant")) {
					this.stategie = new Debutant();
				} else {
					this.stategie = new Expert();
				}
				idChoisi = this.stategie.choisirIdDivinite(partie);
			}
			Iterator<Joueur> it = this.partie.getListeJoueurs().iterator();
			boolean choix = true;
			while (it.hasNext() && choix) {
				Joueur j = it.next();
				if (j.getId() == idChoisi && j.getLaMain().getListeCarteA().size() > 2) {
					Collections.shuffle(j.getLaMain().getListeCarteA());
					for (int k = 0; k < 2; k++) {
						CarteAction carte = j.getLaMain().getListeCarteA().get(0);
						j.getLaMain().seDeffausserCarte(carte);
						this.joueurEnCours.getLaMain().completerCarteAction(carte);
					}
					choix=false;
				}
			}
		} else if (this.id >= 9 && this.id <= 11 || this.id == 23 || this.id == 22 ) {
			// imposer la sacrifice un croyant d'un autre joueur
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite() - 1);
			partie.setJoueurEncours(this.joueurEnCours);
			LinkedList<CarteAction> cartes = new LinkedList();
			System.out.println("Choisir id dont les cartes croyants suivant: ");
			for (LinkedList<CarteAction> carteAs : this.joueurEnCours.getLaMain().getListeCroyantGuidee()) {
				for (CarteAction carteA : carteAs) {
					cartes.add(carteA);
					System.out.println(carteA);
				}
			}
			if (this.joueurEnCours.estBot()) {
				Collections.shuffle(cartes);
				this.joueurEnCours.sacrifierCroyant(cartes.pop().getId(), partie);
			} else {
				System.out.println("Choisir id carte croyant � sacrifier: ");
				Scanner sc = new Scanner(System.in);
				int croyant = sc.nextInt();
				this.joueurEnCours.sacrifierCroyant(croyant, partie);
			}
			testGlobal = true;
		} else if (this.id == 12) {
			// guide revient dans sa main et croyant lie revient au centre de la
			// table
			for (int i = 0; i < partie.getListeJoueurs().size(); i++) {
				for (int j = 0; j < partie.getListeJoueurs().get(i).getLaMain().getListeCroyantGuidee().get(i)
						.size(); j++) {
					if (partie.getListeJoueurs().get(i).getLaMain().getListeCroyantGuidee().get(i).get(j)
							.getId() == this.id) {
						Joueur joueurD = partie.getListeJoueurs().get(i);
						if (joueurD.estBot()) {
							int guide = (int) Math
									.ceil((joueurD.getLaMain().getListeGuideSpirituelGuider().size()) * Math.random());
							partie.getJeuDeCartes().getListeCartesAction()
									.add(joueurD.getLaMain().getListeGuideSpirituelGuider().remove(guide));
							partie.getEspaceCommun().getListeCartesPret()
									.addAll(joueurD.getLaMain().getListeCroyantGuidee().remove(guide));
						} else {
							System.out.println("choisir la carte guide spirituel a revenir");
							for (int k = 1; k <= this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider()
									.size(); k++) {
								System.out.println(k + ":"
										+ joueurD.getLaMain().getListeGuideSpirituelGuider().get(k - 1).getNom());
							}
							Scanner sc = new Scanner(System.in);
							int guide = sc.nextInt();
							partie.getJeuDeCartes().getListeCartesAction()
									.add(joueurD.getLaMain().getListeGuideSpirituelGuider().remove(guide));
							partie.getEspaceCommun().getListeCartesPret()
									.addAll(joueurD.getLaMain().getListeCroyantGuidee().remove(guide));
						}
						break;
					}
				}
			}
		} else if (this.id == 13 || this.id == 35) {
			// relancer de de cosmonogie
			partie.lancerDe();
			partie.setFiniTour(true);
		} else if (this.id > 13 && this.id < 19) {
			// donner une point d'action de nuit
			partie.getJoueurEncours().setPtAction_Nuit(partie.getJoueurEncours().getPtAction_Nuit() + 1);
		} else if (this.id == 19) {
			// Emp�cher sacifier croyant
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guid�e d'un joueur ayant dogme Humain ou Mystique pour emp�cher sa sacrifice!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("empercherCroyantHumainMystique");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Humain", "Mystique");
				if (listJoueurs.size() > 0) {
					int i = (int) Math.ceil(Math.random() * (listJoueurs.size() - 1));
					Joueur joueur = listJoueurs.get(i);
					if (joueur.getLaMain().getListeCroyantGuidee().size() > 0) {
						jV.getEffectuerCapacite().empecherCroyant(joueur, "Humain", "Mystique",
								joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
					}
				}
			}
		} else if (this.id == 20) {
			// Emp�cher sacifier croyant
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guid�e d'un joueur ayant dogme Humain ou Symboles pour emp�cher sa sacrifice!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("empercherCroyantHumainSymboles");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Humain", "Symboles");
				if (listJoueurs.size() > 0) {
					int i = (int) Math.ceil(Math.random() * (listJoueurs.size() - 1));
					Joueur joueur = listJoueurs.get(i);
					if (joueur.getLaMain().getListeCroyantGuidee().size() > 0) {
						jV.getEffectuerCapacite().empecherCroyant(joueur, "Humain", "Symboles",
								joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
					}
				}
			}
		} else
		// id 21=8
		// id 22, 23=9
		if (this.id == 24) {
			// retirer les croyants rattacher une autre divinite
			int idDivinite = this.choisirlaDivinite();
			Joueur j = this.partie.getListeJoueurs().get(idDivinite - 1);
			if (j.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
				int idD;
				if (!j.estBot()) {
					Boolean choix = true;
					LinkedList<CarteAction> cartesD = new LinkedList<CarteAction>();
					System.out.println("Choisir id d'un des carte Guide Spirituel suivant: ");
					for (CarteAction carteD : j.getLaMain().getListeGuideSpirituelGuider()) {
						System.out.println("    -" + carteD);
						cartesD.add(carteD);
					}
					Scanner sc = new Scanner(System.in);
					idD = sc.nextInt();
				} else {
					idD = j.getLaMain().getListeGuideSpirituelGuider().get(0).getId();
				}
				for (int i = 0; i <= j.getLaMain().getListeGuideSpirituelGuider().size(); i++) {
					if (j.getLaMain().getListeGuideSpirituelGuider().get(i).getId() == idD) {
						CarteAction carte = j.getLaMain().getListeGuideSpirituelGuider().remove(i);
						this.partie.getJeuDeCartes().recupererCarteAction(carte);
						this.partie.getEspaceCommun().getListeCartesPret()
								.addAll(j.getLaMain().getListeCroyantGuidee().remove(i));
						break;
					}
				}
			} else {
				System.out.println("Cette divinit� n'a aucun carte guide spirituel guidant les croyants!");
			}
			testGlobal = true;
		} else if (this.id == 25) {
			// prender pt Action
			System.out.println("Choisir Divinit� pour prendre ses points d'action");
			int Divine = this.choisirlaDivinite();
			this.partie.getJoueurEncours().setPtAction_Jour(this.partie.getJoueurEncours().getPtAction_Jour()
					+ this.partie.getListeJoueurs().get(Divine - 1).getPtAction_Jour());
			this.partie.getListeJoueurs().get(Divine - 1).setPtAction_Jour(0);
			this.partie.getJoueurEncours().setPtAction_Nuit(this.partie.getJoueurEncours().getPtAction_Nuit()
					+ this.partie.getListeJoueurs().get(Divine - 1).getPtAction_Nuit());
			this.partie.getListeJoueurs().get(Divine - 1).setPtAction_Nuit(0);
			this.partie.getJoueurEncours().setPtAction_Neant(this.partie.getJoueurEncours().getPtAction_Neant()
					+ this.partie.getListeJoueurs().get(Divine - 1).getPtAction_Neant());
			this.partie.getListeJoueurs().get(Divine - 1).setPtAction_Neant(0);

		} else if (this.id == 26) {
			// pas fini
			// benificier une capacite speciale dune carte croyant appartenant a
			// un autre joueur
			int idDivine = this.choisirlaDivinite();
			System.out.println("Choisiez id d'un des cartes Croyants suivants: ");
			LinkedList<CarteAction> cartes = new LinkedList<CarteAction>();
			for (LinkedList<CarteAction> cartesA : this.partie.getListeJoueurs().get(idDivine - 1).getLaMain()
					.getListeCroyantGuidee())
				for (CarteAction carte : cartesA) {
					cartes.add(carte);
					System.out.println("    -" + carte);
				}
			Scanner sc = new Scanner(System.in);
			int idCarte = sc.nextInt();
			for (int i = 0; i < this.partie.getListeJoueurs().get(idDivine - 1).getLaMain().getListeCroyantGuidee()
					.size(); i++)
				for (int j = 0; j < this.partie.getListeJoueurs().get(idDivine - 1).getLaMain().getListeCroyantGuidee()
						.get(i).size(); j++) {
					CarteAction carte = this.partie.getListeJoueurs().get(idDivine - 1).getLaMain()
							.getListeCroyantGuidee().get(i).get(j);
					if (carte.getId() == idCarte) {
						carte.effectuerCapaciteSpecial(partie);
						CarteAction croyant = this.partie.getListeJoueurs().get(idDivine - 1).getLaMain()
								.getListeCroyantGuidee().get(i).remove(j);
						this.partie.getJeuDeCartes().recupererCarteAction(croyant);
						if (this.partie.getListeJoueurs().get(idDivine - 1).getLaMain().getListeCroyantGuidee().get(i)
								.size() == 0) {
							this.partie.getListeJoueurs().get(idDivine - 1).getLaMain().getListeCroyantGuidee()
									.remove(i);
							CarteAction carteG = this.partie.getListeJoueurs().get(idDivine - 1).getLaMain()
									.getListeGuideSpirituelGuider().remove(i);
							this.partie.getJeuDeCartes().recupererCarteAction(carteG);
						}
						break;
					}
				}
		}
		if (this.id > 26 && this.id < 32) {
			partie.getJoueurEncours().setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant() + 1);
		}
		if (this.id == 32) {
			// emp�cher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guid�e d'un joueur ayant dogme Nature ou Mystique pour emp�cher sa sacrifice!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("empercherCroyantNatureMystique");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Nature", "Mystique");
				if (listJoueurs.size() > 0) {
					int i = (int) Math.ceil(Math.random() * (listJoueurs.size() - 1));
					Joueur joueur = listJoueurs.get(i);
					if (joueur.getLaMain().getListeCroyantGuidee().size() > 0) {
						jV.getEffectuerCapacite().empecherCroyant(joueur, "Nature", "Mystique",
								joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
					}
				}
			}
		} else if (this.id == 33) {
			// emp�cher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guid�e d'un joueur ayant dogme Chaos ou Mystique pour emp�cher sa sacrifice!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("empercherCroyantChaosMystique");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Chaos", "Mystique");
				if (listJoueurs.size() > 0) {
					int i = (int) Math.ceil(Math.random() * (listJoueurs.size() - 1));
					Joueur joueur = listJoueurs.get(i);
					if (joueur.getLaMain().getListeCroyantGuidee().size() > 0) {
						jV.getEffectuerCapacite().empecherCroyant(joueur, "Chaos", "Mystique",
								joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
					}
				}
			}
		} else
		// id 34 piocher 2 cartes id=8
		// id =35 lancer le de id=13
		// id== 36 imposer la sacrifice =9

		if (this.id == 37) {
			// ne recevoir pas pt actions
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				it.next().setEstSetPointAction(false);
			}
		} else if (this.id >= 38 && this.id <= 40) {
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
							"Il y a 2 joueur ayant le m�me point pri�re dernier. Cette carte Apocalypse est d�fauss�.");
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
							"Il y a 2 joueur ayant le m�me point pri�re premier. Cette carte Apocalypse est d�fauss�.");
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
		} else if (this.id >= 41 && this.id <= 48) {
			// gagner point action en fonction de carte croyant attache
			if (this.joueurEnCours.getEstSetPointAction()) {
				int ptAjoute = 0;
				for (LinkedList<CarteAction> cartes : this.joueurEnCours.getLaMain().getListeCroyantGuidee()) {
					ptAjoute += cartes.size();
				}
				if (!this.joueurEnCours.estBot()) {
					System.out.println("Choisir pt Action ajouter, 1 Jour, 2 Nuit, 3 Neant");
					Scanner sc = new Scanner(System.in);
					int originePtAction = sc.nextInt();
					switch (originePtAction) {
					case 1:
						this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + ptAjoute);
						break;
					case 2:
						this.joueurEnCours.setPtAction_Nuit(this.joueurEnCours.getPtAction_Nuit() + ptAjoute);
						break;
					case 3:
						this.joueurEnCours.setPtAction_Neant(this.joueurEnCours.getPtAction_Neant() + ptAjoute);
						break;
					default:
						System.out.println("Error de choisi!");
						break;
					}
				} else {
					this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + ptAjoute);
				}
				System.out.print("Nouvaux points d'Action: ");
				System.out.print("[Jour " + this.joueurEnCours.getPtAction_Jour() + "] ");
				System.out.print("[Nuit " + this.joueurEnCours.getPtAction_Nuit() + "] ");
				System.out.println("[N�ant " + this.joueurEnCours.getPtAction_Neant() + "] ");
			}
		} else if (this.id == 49) {
			// sacrifie tous les cartes croyant Neant d'une Divinit� Humain

			do {
				this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite() - 1);
				if (!this.joueurEnCours.getLaMain().getCarteDivinite().getOrigine().equals("Humain")) {
					System.out.println("Choisir la Divinit� Humain ");
				}
			} while (this.joueurEnCours.getLaMain().getCarteDivinite().getId() > 82
					&& this.joueurEnCours.getLaMain().getCarteDivinite().getId() < 87);
			for (int i = 0; i < this.joueurEnCours.getLaMain().getListeCroyantGuidee().size(); i++) {
				for (int j = 0; i < this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(i).size(); j++) {
					CarteAction carte = this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(i).get(j);
					if (carte.getOrigine().equals("Neant")) {
						this.joueurEnCours.sacrifierCroyant(carte.getId(), partie);
					}
				}
			}
		} else if (this.id == 50) {
			// sacrifie Guide Spirituel si lui ou sa Divinite ne croit pas au
			// dogme Chaos
			int idDivineChoisi;
			String[] dogmeDivinite;
			int idCarteChoisi;
			String[] dogmeCarte = {};
			if (!this.joueurEnCours.estBot()) {
				idDivineChoisi = this.choisirlaDivinite();
				System.out.println("Vous pouvez choisir un des carte Guide Spirituel suivant: ");
				for (CarteAction carte : this.partie.getListeJoueurs().get(idDivineChoisi - 1).getLaMain()
						.getListeGuideSpirituelGuider()) {
					System.out.println("   -" + carte);
				}
				System.out.print("Choisir id: ");
				Scanner sc = new Scanner(System.in);
				idCarteChoisi = sc.nextInt();
				for (CarteAction carte : this.partie.getListeJoueurs().get(idDivineChoisi - 1).getLaMain()
						.getListeGuideSpirituelGuider()) {
					if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0).getId() == idCarteChoisi) {
						dogmeCarte = carte.getDogme();
						break;
					}
				}
			} else {
				JoueurVirtuel j = (JoueurVirtuel) this.joueurEnCours;
				idDivineChoisi = j.getStategie().choisirIdDivinite(partie);
				idCarteChoisi = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0).getId();
				dogmeCarte = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0).getDogme();
			}
			dogmeDivinite = this.partie.getListeJoueurs().get(idDivineChoisi - 1).getLaMain().getCarteDivinite()
					.getDogme();
			Boolean contientChaos = false;
			for (String dogme : dogmeDivinite) {
				if (dogme.equals("Chaos")) {
					contientChaos = true;
				}
			}
			if (!contientChaos) {
				this.joueurEnCours = this.partie.getListeJoueurs().get(idDivineChoisi - 1);
				this.joueurEnCours.sacrifierGuideSpirit(idCarteChoisi, this.partie);
			} else {
				contientChaos = false;
				for (String dogme : dogmeCarte) {
					if (dogme.equals("Chaos")) {
						contientChaos = true;
					}
				}
				if (!contientChaos) {
					this.joueurEnCours = this.partie.getListeJoueurs().get(idDivineChoisi - 1);
					this.joueurEnCours.sacrifierGuideSpirit(idCarteChoisi, this.partie);
				}
			}
		} else if (this.id == 51) {
			// defausser tous les cartes croyant Origine Nuit ou Neant; et Dogme
			// Nature
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				if (carte.getOrigine().equals("Nuit") || carte.getOrigine().equals("Neant")) {
					for (int j = 0; j < 3; j++) {
						if (carte.getDogme()[j] == "Nature") {
							partie.getJeuDeCartes().getListeCartesAction()
									.add(partie.getEspaceCommun().getListeCartesPret().remove(i));
						}
					}
				}
			}
		} else if (this.id == 52) {
			// sacrifier 2 croyant de la Divinite Humain ou Sympboles
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (this.joueurEnCours.getLaMain().getCarteDivinite().getId() == 84
					|| this.joueurEnCours.getLaMain().getCarteDivinite().getId() == 85) {
				System.out.println("Choisir la divinite d'origine Humain ou Symboles");
				this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			System.out.println("Voici la liste des guides spirituel, choisir 2 carte croyant a sacrifier");
			LinkedList<CarteAction> carte = null;
			Iterator<LinkedList<CarteAction>> it = this.joueurEnCours.getLaMain().getListeCroyantGuidee().iterator();
			int i = 1;
			while (it.hasNext()) {
				for (int k = 0; k < it.next().size(); k++) {
					System.out.println(i + ":" + it.next());
					i++;
					carte.add(it.next().get(k));
				}
			}
			int croyant;
			if (partie.getJoueurEncours().estBot()) {
				croyant = (int) Math.ceil(carte.size() * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				croyant = sc.nextInt();
				while (croyant < 1 || croyant > carte.size()) {
					System.out.println("Rechoisir la Croyant");
					croyant = sc.nextInt();
				}
			}
			this.joueurEnCours.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
			carte.remove(croyant - 1);
			if (partie.getJoueurEncours().estBot()) {
				croyant = (int) Math.ceil(carte.size() * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				croyant = sc.nextInt();
				while (croyant < 1 || croyant > carte.size()) {
					System.out.println("Rechoisir la Croyant");
					croyant = sc.nextInt();
				}
			}
			this.joueurEnCours.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
			carte.remove(croyant - 1);
		} else if (id == 53) {
			// oblige une Divinite Nature ou Mystique sacrifier une de ses Guide
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (this.joueurEnCours.getLaMain().getCarteDivinite().getId() == 89
					|| this.joueurEnCours.getLaMain().getCarteDivinite().getId() == 90) {
				System.out.println("Choisir la divinite d'origine Nature ou Mystique");
				this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			System.out.println("Voici la liste des guides spirituels");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			int Guide;
			while (it.hasNext()) {
				System.out.println(i + ":" + it.next());
				i++;
				carte.add(it.next());
			}
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			this.joueurEnCours.sacrifierGuideSpirit(carte.get(Guide - 1).getId(), partie);
		} else if (id == 54) {
			// reprende dans sa main un de ses Guides
			// Divinite origine nuit ou dogme Mystique ou Chaos
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (!((this.joueurEnCours.getLaMain().getCarteDivinite().getId() > 82
					&& this.joueurEnCours.getLaMain().getCarteDivinite().getId() < 87)
					|| this.joueurEnCours.getLaMain().getCarteDivinite().getId() == 88)) {
				this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			System.out.println("Voici la liste des guides spirituels");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			int Guide;
			while (it.hasNext()) {
				System.out.println(i + ":" + it.next());
				i++;
				carte.add(it.next());
			}
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			this.joueurEnCours.getLaMain().getListeCarteA()
					.add(this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getJeuDeCartes().getListeCartesAction()
					.addAll(this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(Guide - 1));
		} else if (id == 55) {
			// echanger 2 guide
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici votre liste des guides spirituels");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = partie.getJoueurEncours().getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			int Guide1;
			while (it.hasNext()) {
				System.out.println(i + ":" + it.next());
				i++;
				carte.add(it.next());
			}
			if (partie.getJoueurEncours().estBot()) {
				Guide1 = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide1 = sc.nextInt();
				while (Guide1 < 1 || Guide1 > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide1 = sc.nextInt();
				}
			}
			System.out.println("Voici la liste des guides spirituels");
			LinkedList<CarteAction> carte2 = null;
			Iterator<CarteAction> it2 = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			i = 1;
			int Guide2;
			while (it2.hasNext()) {
				System.out.println(i + ":" + it2.next());
				i++;
				carte2.add(it2.next());
			}
			if (partie.getJoueurEncours().estBot()) {
				Guide2 = (int) Math.ceil((carte2.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide2 = sc.nextInt();
				while (Guide2 < 1 || Guide2 > carte2.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide2 = sc.nextInt();
				}
			}
			partie.getJoueurEncours().getLaMain().getListeGuideSpirituelGuider()
					.add(this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().remove(Guide2 - 1));
			partie.getJoueurEncours().getLaMain().getListeCroyantGuidee()
					.add(this.joueurEnCours.getLaMain().getListeCroyantGuidee().remove(Guide2 - 1));
			this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider()
					.add(partie.getJoueurEncours().getLaMain().getListeGuideSpirituelGuider().remove(Guide1 - 1));
			this.joueurEnCours.getLaMain().getListeCroyantGuidee()
					.add(partie.getJoueurEncours().getLaMain().getListeCroyantGuidee().remove(Guide1 - 1));
		} else if (id == 56) {
			// defausser tous les cartes croyants Mystique au centre la table
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				for (int j = 0; j < 3; j++) {
					if (carte.getDogme()[j] == "Mystique") {
						partie.getJeuDeCartes().getListeCartesAction()
								.add(partie.getEspaceCommun().getListeCartesPret().remove(i));
					}

				}
			}
		} else if (id == 57) {
			// poser le de en face qu'il veut
			if (partie.getJoueurEncours().estBot()) {
				partie.lancerDe();
			} else {
				System.out.println("Choisir le face de De:");
				System.out.println("1:Nuit");
				System.out.println("2:Nuit");
				System.out.println("3:Neant");
				Scanner sc = new Scanner(System.in);
				int FaceDe = sc.nextInt();
				while (FaceDe > 3 || FaceDe < 1) {
					System.out.println("Choisir le face de De, par exemple 1");
					FaceDe = sc.nextInt();
				}
				if (FaceDe == 1) {
					for (Joueur joueur : partie.getListeJoueurs()) {
						if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Jour")) {
							joueur.setPtAction_Jour(joueur.getPtAction_Jour() + 2);
						} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Aube")) {
							joueur.setPtAction_Jour(joueur.getPtAction_Jour() + 1);

						}
					}
				} else if (FaceDe == 2) {
					for (Joueur joueur : partie.getListeJoueurs()) {
						if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Nuit")) {
							joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 2);
						} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Cr�puscule")) {
							joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 1);
						}
					}
				} else if (FaceDe == 3) {
					for (Joueur joueur : partie.getListeJoueurs()) {
						if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Aube")) {
							joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
						} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Cr�puscule")) {
							joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
						}
					}
				}
			}
		} else if (id == 58) {
			// detruire un guide spirituel Nuit ou Neant
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des guides spirituel");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			while (it.hasNext()) {
				if (it.next().getOrigine() == "Nuit" || it.next().getOrigine() == "Neant") {
					System.out.println(i + ":" + it.next());
					i++;
					carte.add(it.next());
				}
			}
			int Guide;
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			partie.getJeuDeCartes().getListeCartesAction()
					.add(this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getEspaceCommun().getListeCartesPret()
					.addAll(this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(Guide - 1));
		} else if (id == 59) {
			// detruire un guide sprituel Jour ou Neant
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des guides spirituel");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			while (it.hasNext()) {
				if (it.next().getOrigine() == "Jour" || it.next().getOrigine() == "Neant") {
					System.out.println(i + ":" + it.next());
					i++;
					carte.add(it.next());
				}
			}
			int Guide;
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			partie.getJeuDeCartes().getListeCartesAction()
					.add(this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getEspaceCommun().getListeCartesPret()
					.addAll(this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(Guide - 1));
		} else if (id == 60) {
			// proteger un Guide Spirituel

		} else if (id == 61 || id == 64) {
			// recuperez un guide d'un autre divinite et aussi les croyants
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des guides spirituel");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			while (it.hasNext()) {
				System.out.println(i + ":" + it.next());
				i++;
				carte.add(it.next());
			}
			int Guide;
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			partie.getJoueurEncours().getLaMain().getListeGuideSpirituelGuider()
					.add(this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getJoueurEncours().getLaMain().getListeCroyantGuidee()
					.add(this.joueurEnCours.getLaMain().getListeCroyantGuidee().remove(Guide - 1));
		} else if (id == 62) {
			// sacrifier 2 carte croyant
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des croyant");
			LinkedList<CarteAction> carte = null;
			Iterator<LinkedList<CarteAction>> it = this.joueurEnCours.getLaMain().getListeCroyantGuidee().iterator();
			int i = 1;
			while (it.hasNext()) {
				for (int k = 0; k < it.next().size(); k++) {
					System.out.println(i + ":" + it.next());
					i++;
					carte.add(it.next().get(k));
				}
			}
			int croyant;
			if (partie.getJoueurEncours().estBot()) {
				croyant = (int) Math.ceil(carte.size() * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				croyant = sc.nextInt();
				while (croyant < 1 || croyant > carte.size()) {
					System.out.println("Rechoisir la Croyant");
					croyant = sc.nextInt();
				}
			}
			this.joueurEnCours.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
			carte.remove(croyant - 1);
			if (partie.getJoueurEncours().estBot()) {
				croyant = (int) Math.ceil(carte.size() * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				croyant = sc.nextInt();
				while (croyant < 1 || croyant > carte.size()) {
					System.out.println("Rechoisir la Croyant");
					croyant = sc.nextInt();
				}
			}
			this.joueurEnCours.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
			carte.remove(croyant - 1);

		} else if (id == 63) {
			// prendre 3 carte en main d'un autre Divine
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			partie.getJoueurEncours().getLaMain().getListeCarteA()
					.add(this.joueurEnCours.getLaMain().getListeCarteA().pop());
			partie.getJoueurEncours().getLaMain().getListeCarteA()
					.add(this.joueurEnCours.getLaMain().getListeCarteA().pop());
			partie.getJoueurEncours().getLaMain().getListeCarteA()
					.add(this.joueurEnCours.getLaMain().getListeCarteA().pop());
		}
		// id64 =61
		else if (id == 65) {
			// aucun joueur recoit pt Action ce tour
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				it.next().setEstSetPointAction(false);
			}
		} else if (id == 66) {
			// beneficier une capactier d'une guide ou croyant sans sacrifier
			System.out.println("Choisir carte a beneficier");
			LinkedList<CarteAction> carte = null;
			Iterator<LinkedList<CarteAction>> it = this.joueurEnCours.getLaMain().getListeCroyantGuidee().iterator();
			int i = 1;
			while (it.hasNext()) {
				for (int k = 0; k < it.next().size(); k++) {
					System.out.println(i + ":" + it.next());
					i++;
					carte.add(it.next().get(k));
				}
			}
			Iterator<CarteAction> it1 = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			while (it1.hasNext()) {
				System.out.println(i + ":" + it.next());
				i++;
				carte.add(it1.next());
			}
			CarteAction cartebeneficie;
			if (partie.getJoueurEncours().estBot()) {
				Collections.shuffle(carte);
				cartebeneficie = carte.pop();
			} else {
				Scanner sc = new Scanner(System.in);
				cartebeneficie = carte.get(sc.nextInt() - 1);
			}
			cartebeneficie.effectuerCapaciteSpecial(partie);
		} else if (id == 67) {
			// annule capacite une carte action nuit ou neant
			int idDivinite = this.choisirlaDivinite();

		} else if (id == 74) {
			// lancer le de
			partie.lancerDe();
		} else if (id == 75) {
			// lancer le de

		} else if (id == 82) {
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			this.joueurEnCours.setSacrifice(false);
		} else if (id == 83) {
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				if (carte.getOrigine() == "Neant") {
					partie.getJeuDeCartes().getListeCartesAction()
							.add(partie.getEspaceCommun().getListeCartesPret().remove(i));
				}
			}

		} else if (id == 84 || id == 85) { // th�m effectuer v�
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			Apocalypse carte;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeCarteA().iterator();
			while (it.hasNext()) {
				if (it.next().getId() < 81 && it.next().getId() > 75) {
					carte = (Apocalypse) it.next();
					carte.effectuerCapaciteSpecial();
					this.joueurEnCours.getLaMain().getListeCarteA().remove(carte);
					break;
				}
			}
		} else if (id == 86) {
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				if (carte.getOrigine() == "Jour") {
					partie.getJeuDeCartes().getListeCartesAction()
							.add(partie.getEspaceCommun().getListeCartesPret().remove(i));
				}
			}

		} else if (id == 87) {
			int ptActionNeantajouer = partie.getJoueurEncours().getLaMain().getListeGuideSpirituelGuider().size();
			partie.getJoueurEncours()
					.setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant() + ptActionNeantajouer);

		} else if (id == 88) {
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			int Guide;
			System.out.println("Voici la liste des guides ayant le dogme Symboles ou Nature");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().iterator();
			int i = 1;
			while (it.hasNext()) {
				for (int j = 0; j < 2; j++) {
					if (it.next().getDogme()[j] == "Nature" || it.next().getDogme()[j] == "Nature") {
						System.out.println(i + ":" + it.next());
						i++;
						carte.add(it.next());
					}
				}
			}
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((carte.size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > carte.size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			this.joueurEnCours.sacrifierGuideSpirit(carte.get(Guide - 1).getId(), partie);
		} else if (id == 89) {
			this.joueurEnCours = partie.getListeJoueurs().get(this.choisirlaDivinite());
			partie.getJoueurEncours().setPtAction_Jour(
					partie.getJoueurEncours().getPtAction_Jour() + this.joueurEnCours.getPtAction_Jour());
			partie.getJoueurEncours().setPtAction_Nuit(
					partie.getJoueurEncours().getPtAction_Nuit() + this.joueurEnCours.getPtAction_Nuit());
			partie.getJoueurEncours().setPtAction_Neant(
					partie.getJoueurEncours().getPtAction_Neant() + this.joueurEnCours.getPtAction_Neant());
			this.joueurEnCours.setPtAction_Jour(0);
			this.joueurEnCours.setPtAction_Nuit(0);
			this.joueurEnCours.setPtAction_Neant(0);
			this.joueurEnCours.setEstSetPointAction(false);
		}

		return testGlobal;
	}
}
