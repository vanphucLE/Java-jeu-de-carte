package com.sdz.cartes;

import com.sdz.modele.*;
import java.util.*;

import com.sdz.modele.Joueur;
import com.sdz.modele.Partie;

public class CapaciteSpeciale {
	//id de la carte
	private int id;
	private Partie partie;
	private Joueur joueur;
	Stategie stategie;

	public CapaciteSpeciale(int id) {
		this.id = id;
	}

	public int choisirlaDivinite() {
		int divine;
		if (!this.partie.getJoueurEncours().estBot()) {
			System.out.println("Choisir une Divinité pour effectuer la capacite parmi les divinités suivants: ");
			int i = 1;
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				System.out.println("   -" + it.next());
				i++;
			}
			Scanner sc = new Scanner(System.in);
			String commande = "";
			do {
				System.out.print("Choisir id de la Divinité: ");
				commande = sc.nextLine();
				divine = Integer.parseInt(commande);
			} while (!this.testIdDivinite(commande));
		} else {
			JoueurVirtuel j = (JoueurVirtuel) this.joueur;
			divine = j.getStategie().choisirIdDivinite(partie);
		}
		return divine;
	};

	// tester valeur entrée - id de divinité
	private Boolean testIdDivinite(String str) {
		Boolean test = false;
		str = str.trim();
		int idConv = 0;
		if (str.matches("\\d+")) {
			idConv = Integer.parseInt(str);
			test = true;
		}
		if ((idConv > (partie.getListeJoueurs().size() + 1) || idConv < 0) && idConv != this.joueur.getId()) {
			test = false;
		}
		if (test == false) {
			System.out.println("Eurreur quand vous saisiez la valueur Id du joueur!");
		}
		return test;
	}

	// convertir et tester valeur entrée
	private int convertIdsEntree(String str) {
		Boolean test = true;
		str = str.trim();
		int idReturn = 0;
		if (str.matches("\\d+")) {
			idReturn = Integer.parseInt(str);
		}
		return idReturn;
	}

	// empecher un joueur Sacrifier la carde
	public Boolean empecherSacrifice(String dogme1, String dogme2) {
		LinkedList<Integer> listeIdJoueurs = new LinkedList<Integer>();
		System.out.println("Les divinité possédant le dogme " + dogme1 + " ou " + dogme2 + ": ");
		for (Joueur j : partie.getListeJoueurs()) {
			if (!this.joueur.estEqual(j)) {
				Boolean test = false;
				for (String str : j.getLaMain().getCarteDivinite().getDogme()) {
					if (str.equals(dogme1) || str.equals(dogme2)) {
						test = true;
						break;
					}
				}
				if (test == true) {
					System.out.println("  +" + j + "\n   ." + j.getLaMain().getCarteDivinite());
					listeIdJoueurs.add(j.getId());
				}
			}
		}
		if (listeIdJoueurs.size() == 0) {
			System.out.println("Il n'y a aucun divinité possédant le dogme " + dogme1 + " ou " + dogme2 + "! ");
			return false;
		} else {
			// on distingu joueur physique et joueur virtuel
			if (!joueur.estBot()) {
				Scanner sc = new Scanner(System.in);
				String Divine = "";
				boolean choix = false;
				int idChoisi = 0;
				do {
					System.out.print("Choisir id Divinité possédant le dogme " + dogme1 + " ou " + dogme2
							+ " pour empêcher sa sacrifice à la carte croyant: ");
					Divine = sc.nextLine();
					idChoisi = Integer.parseInt(Divine);
				} while (!this.testIdDivinite(Divine));
				if (this.partie.getListeJoueurs().get(idChoisi - 1).getLaMain().getListeCroyantGuidee().size() > 0) {
					System.out.println("Ses cartes Croyants peuvent être sacrifiée:  ");
					LinkedList<CarteAction> listeCroyantSacrifice = new LinkedList<CarteAction>();
					for (LinkedList<CarteAction> cartes : partie.getListeJoueurs().get(idChoisi - 1).getLaMain()
							.getListeCroyantGuidee()) {
						for (CarteAction carte : cartes) {
							System.out.println("    + " + carte);
							listeCroyantSacrifice.add(carte);
						}
					}
					boolean testC = false;
					// tester valeur entrée si Id est vraie
					do {
						boolean choix2 = false;
						int idChoisi2 = 0;
						String commande = "";
						// tester valeur entrée si elle est le numéro ou
						// caractère
						do {
							System.out.print("Choisir id de la carte Croyant pour empêcher sa sacrifice : ");
							commande = sc.nextLine();
							idChoisi2 = this.convertIdsEntree(commande);
							if (idChoisi2 != 0) {
								choix2 = true;
							}
						} while (!choix2);
						for (LinkedList<CarteAction> cartes : partie.getListeJoueurs().get(idChoisi - 1).getLaMain()
								.getListeCroyantGuidee()) {
							for (CarteAction carte : cartes) {
								if (carte.getId() == idChoisi2) {
									carte.setEstSacrifie(false);
									testC = true;
									break;

								}
							}
						}
						if (!testC)
							System.out.println("Eurreur Entrée!");
					} while (!testC);
				} else {
					System.out.println("Il n'y a aucun carte croyant à sacrifier");
				}
			} else {
				if (this.partie.getListeJoueurs().get(listeIdJoueurs.get(0) - 1).getLaMain().getListeCroyantGuidee()
						.size() > 0) {
					partie.getListeJoueurs().get(listeIdJoueurs.get(0) - 1).getLaMain().getListeCroyantGuidee().get(0)
							.get(0).setEstSacrifie(false);
				}
			}
			return true;
		}

	}

	public Boolean effectuerCapacite(Partie partie) {
		this.partie = partie;
		this.joueur = partie.getJoueurEncours();
		Boolean testGlobal = false;
		if (this.id > 1 && this.id < 6) {
			partie.getJoueurEncours().setPtAction_Jour(partie.getJoueurEncours().getPtAction_Jour() + 1);
			testGlobal = true;
		} else if (this.id == 6) {
			testGlobal = this.empecherSacrifice("Nature", "Mystique");
		} else if (this.id == 7) {
			testGlobal = this.empecherSacrifice("Chaos", "Mystique");
		} else if (this.id == 8 || this.id == 21 || this.id == 34) {
			int idChoisi = 0;
			if (!joueur.estBot()) {
				System.out.println("Choisir Divinité pour prendre ses 2 cartes");
				Iterator<Joueur> it = this.partie.getListeJoueurs().iterator();
				while (it.hasNext()) {
					Joueur j = it.next();
					System.out.println("[id : " + j.getId() + " " + j.getNom() + "] ");
				}
				Scanner sc = new Scanner(System.in);
				String Divine = "";
				do {
					System.out.print("Choisir id de la Divinité: ");
					Divine = sc.nextLine();
					idChoisi = Integer.parseInt(Divine);
				} while (!this.testIdDivinite(Divine));
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
					partie.getJoueurEncours().getLaMain().completerCarteAction(j.getLaMain().getListeCarteA().pop());
					partie.getJoueurEncours().getLaMain().completerCarteAction(j.getLaMain().getListeCarteA().pop());
					choix = false;
				}
			}
			testGlobal = true;
		} else if (this.id >= 9 && this.id <= 11 || this.id == 23 || this.id == 22 || this.id == 9) {
			// imposer la sacrifice un croyant d'un joueur
			this.joueur = partie.getListeJoueurs().get(this.choisirlaDivinite() - 1);
			partie.setJoueurEncours(this.joueur);
			LinkedList<CarteAction> cartes = new LinkedList();
			System.out.println("Choisir id dont les cartes croyants suivant: ");
			for (LinkedList<CarteAction> carteAs : this.joueur.getLaMain().getListeCroyantGuidee()) {
				for (CarteAction carteA : carteAs) {
					cartes.add(carteA);
					System.out.println(carteA);
				}
			}
			if (this.joueur.estBot()) {
				Collections.shuffle(cartes);
				this.joueur.sacrifierCroyant(cartes.pop().getId(), partie);
			} else {
				System.out.println("Choisir id carte croyant à sacrifier: ");
				Scanner sc = new Scanner(System.in);
				int croyant = sc.nextInt();
				this.joueur.sacrifierCroyant(croyant, partie);
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
							for (int k = 1; k <= joueur.getLaMain().getListeGuideSpirituelGuider().size(); k++) {
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
			testGlobal = this.empecherSacrifice("Humain", "Mystique");
		} else if (this.id == 20) {
			testGlobal = this.empecherSacrifice("Humain", "Symboles");
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
				System.out.println("Cette divinité n'a aucun carte guide spirituel guidant les croyants!");
			}
			testGlobal = true;
		} else if (this.id == 25) {
			// prender pt Action
			System.out.println("Choisir Divinité pour prendre ses points d'action");
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
			testGlobal = this.empecherSacrifice("Nature", "Mystique");
		} else if (this.id == 33) {
			testGlobal = this.empecherSacrifice("Chaos", "Mystique");
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
		} else if (this.id >= 41 && this.id <= 48) {
			// gagner point action en fonction de carte croyant attache
			if (this.joueur.getEstSetPointAction()) {
				int ptAjoute = 0;
				for (LinkedList<CarteAction> cartes : this.joueur.getLaMain().getListeCroyantGuidee()) {
					ptAjoute += cartes.size();
				}
				if (!joueur.estBot()) {
					System.out.println("Choisir pt Action ajouter, 1 Jour, 2 Nuit, 3 Neant");
					Scanner sc = new Scanner(System.in);
					int originePtAction = sc.nextInt();
					switch (originePtAction) {
					case 1:
						joueur.setPtAction_Jour(joueur.getPtAction_Jour() + ptAjoute);
						break;
					case 2:
						joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + ptAjoute);
						break;
					case 3:
						joueur.setPtAction_Neant(joueur.getPtAction_Neant() + ptAjoute);
						break;
					default:
						System.out.println("Error de choisi!");
						break;
					}
				} else {
					joueur.setPtAction_Jour(joueur.getPtAction_Jour() + ptAjoute);
				}
				System.out.print("Nouvaux points d'Action: ");
				System.out.print("[Jour " + this.joueur.getPtAction_Jour() + "] ");
				System.out.print("[Nuit " + this.joueur.getPtAction_Nuit() + "] ");
				System.out.println("[Néant " + this.joueur.getPtAction_Neant() + "] ");
			}
		} else if (this.id == 49) {
			// sacrifie tous les cartes croyant Neant d'une Divinité Humain

			do {
				this.joueur = partie.getListeJoueurs().get(this.choisirlaDivinite() - 1);
				if (!this.joueur.getLaMain().getCarteDivinite().getOrigine().equals("Humain")) {
					System.out.println("Choisir la Divinité Humain ");
				}
			} while (joueur.getLaMain().getCarteDivinite().getId() > 82
					&& joueur.getLaMain().getCarteDivinite().getId() < 87);
			for (int i = 0; i < joueur.getLaMain().getListeCroyantGuidee().size(); i++) {
				for (int j = 0; i < joueur.getLaMain().getListeCroyantGuidee().get(i).size(); j++) {
					CarteAction carte = joueur.getLaMain().getListeCroyantGuidee().get(i).get(j);
					if (carte.getOrigine().equals("Neant")) {
						this.joueur.sacrifierCroyant(carte.getId(), partie);
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
			if (!this.joueur.estBot()) {
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
					if (this.joueur.getLaMain().getListeGuideSpirituelGuider().get(0).getId() == idCarteChoisi) {
						dogmeCarte = carte.getDogme();
						break;
					}
				}
			} else {
				JoueurVirtuel j = (JoueurVirtuel) this.joueur;
				idDivineChoisi = j.getStategie().choisirIdDivinite(partie);
				idCarteChoisi = this.joueur.getLaMain().getListeGuideSpirituelGuider().get(0).getId();
				dogmeCarte = this.joueur.getLaMain().getListeGuideSpirituelGuider().get(0).getDogme();
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
				this.joueur = this.partie.getListeJoueurs().get(idDivineChoisi - 1);
				joueur.sacrifierGuideSpirit(idCarteChoisi, this.partie);
			} else {
				contientChaos = false;
				for (String dogme : dogmeCarte) {
					if (dogme.equals("Chaos")) {
						contientChaos = true;
					}
				}
				if (!contientChaos) {
					this.joueur = this.partie.getListeJoueurs().get(idDivineChoisi - 1);
					joueur.sacrifierGuideSpirit(idCarteChoisi, this.partie);
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
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (joueur.getLaMain().getCarteDivinite().getId() == 84
					|| joueur.getLaMain().getCarteDivinite().getId() == 85) {
				System.out.println("Choisir la divinite d'origine Humain ou Symboles");
				joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			System.out.println("Voici la liste des guides spirituel, choisir 2 carte croyant a sacrifier");
			LinkedList<CarteAction> carte = null;
			Iterator<LinkedList<CarteAction>> it = joueur.getLaMain().getListeCroyantGuidee().iterator();
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
			joueur.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
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
			joueur.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
			carte.remove(croyant - 1);
		} else if (id == 53) {
			// oblige une Divinite Nature ou Mystique sacrifier une de ses Guide
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (joueur.getLaMain().getCarteDivinite().getId() == 89
					|| joueur.getLaMain().getCarteDivinite().getId() == 90) {
				System.out.println("Choisir la divinite d'origine Nature ou Mystique");
				joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			System.out.println("Voici la liste des guides spirituels");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
			joueur.sacrifierGuideSpirit(carte.get(Guide - 1).getId(), partie);
		} else if (id == 54) {
			// reprende dans sa main un de ses Guides
			// Divinite origine nuit ou dogme Mystique ou Chaos
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (!((joueur.getLaMain().getCarteDivinite().getId() > 82
					&& joueur.getLaMain().getCarteDivinite().getId() < 87)
					|| joueur.getLaMain().getCarteDivinite().getId() == 88)) {
				joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			System.out.println("Voici la liste des guides spirituels");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
			joueur.getLaMain().getListeCarteA()
					.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getJeuDeCartes().getListeCartesAction()
					.addAll(joueur.getLaMain().getListeCroyantGuidee().get(Guide - 1));
		} else if (id == 55) {
			// echanger 2 guide
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
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
			Iterator<CarteAction> it2 = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
					.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(Guide2 - 1));
			partie.getJoueurEncours().getLaMain().getListeCroyantGuidee()
					.add(joueur.getLaMain().getListeCroyantGuidee().remove(Guide2 - 1));
			joueur.getLaMain().getListeGuideSpirituelGuider()
					.add(partie.getJoueurEncours().getLaMain().getListeGuideSpirituelGuider().remove(Guide1 - 1));
			joueur.getLaMain().getListeCroyantGuidee()
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
						} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Crépuscule")) {
							joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 1);
						}
					}
				} else if (FaceDe == 3) {
					for (Joueur joueur : partie.getListeJoueurs()) {
						if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Aube")) {
							joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
						} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Crépuscule")) {
							joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
						}
					}
				}
			}
		} else if (id == 58) {
			// detruire un guide spirituel Nuit ou Neant
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des guides spirituel");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
					.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getEspaceCommun().getListeCartesPret()
					.addAll(joueur.getLaMain().getListeCroyantGuidee().get(Guide - 1));
		} else if (id == 59) {
			// detruire un guide sprituel Jour ou Neant
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des guides spirituel");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
					.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getEspaceCommun().getListeCartesPret()
					.addAll(joueur.getLaMain().getListeCroyantGuidee().get(Guide - 1));
		} else if (id == 60) {
			// proteger un Guide Spirituel
			
		} else if (id == 61 || id == 64) {
			// recuperez un guide d'un autre divinite et aussi les croyants
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des guides spirituel");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
					.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(Guide - 1));
			partie.getJoueurEncours().getLaMain().getListeCroyantGuidee()
					.add(joueur.getLaMain().getListeCroyantGuidee().remove(Guide - 1));
		} else if (id == 62) {
			// sacrifier 2 carte croyant
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			System.out.println("Voici la liste des croyant");
			LinkedList<CarteAction> carte = null;
			Iterator<LinkedList<CarteAction>> it = joueur.getLaMain().getListeCroyantGuidee().iterator();
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
			joueur.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
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
			joueur.sacrifierCroyant(carte.get(croyant - 1).getId(), partie);
			carte.remove(croyant - 1);

		} else if (id == 63) {
			// prendre 3 carte en main d'un autre Divine
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			partie.getJoueurEncours().getLaMain().getListeCarteA().add(joueur.getLaMain().getListeCarteA().pop());
			partie.getJoueurEncours().getLaMain().getListeCarteA().add(joueur.getLaMain().getListeCarteA().pop());
			partie.getJoueurEncours().getLaMain().getListeCarteA().add(joueur.getLaMain().getListeCarteA().pop());
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
			Iterator<LinkedList<CarteAction>> it = joueur.getLaMain().getListeCroyantGuidee().iterator();
			int i = 1;
			while (it.hasNext()) {
				for (int k = 0; k < it.next().size(); k++) {
					System.out.println(i + ":" + it.next());
					i++;
					carte.add(it.next().get(k));
				}
			}
			Iterator<CarteAction> it1 = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
			int idDivinite=this.choisirlaDivinite();
			
			
		} else if (id == 74) {
			// lancer le de
			partie.lancerDe();
		} else if (id == 75) {
			// lancer le de

		} else if (id == 82) {
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			joueur.setSacrifice(false);
		} else if (id == 83) {
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				if (carte.getOrigine() == "Neant") {
					partie.getJeuDeCartes().getListeCartesAction()
							.add(partie.getEspaceCommun().getListeCartesPret().remove(i));
				}
			}

		} else if (id == 84 || id == 85) { // thêm effectuer vô
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			Apocalypse carte;
			Iterator<CarteAction> it = joueur.getLaMain().getListeCarteA().iterator();
			while (it.hasNext()) {
				if (it.next().getId() < 81 && it.next().getId() > 75) {
					carte = (Apocalypse) it.next();
					carte.effectuerCapaciteSpecial();
					joueur.getLaMain().getListeCarteA().remove(carte);
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
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			int Guide;
			System.out.println("Voici la liste des guides ayant le dogme Symboles ou Nature");
			LinkedList<CarteAction> carte = null;
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
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
			joueur.sacrifierGuideSpirit(carte.get(Guide - 1).getId(), partie);
		} else if (id == 89) {
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			partie.getJoueurEncours()
					.setPtAction_Jour(partie.getJoueurEncours().getPtAction_Jour() + joueur.getPtAction_Jour());
			partie.getJoueurEncours()
					.setPtAction_Nuit(partie.getJoueurEncours().getPtAction_Nuit() + joueur.getPtAction_Nuit());
			partie.getJoueurEncours()
					.setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant() + joueur.getPtAction_Neant());
			joueur.setPtAction_Jour(0);
			joueur.setPtAction_Nuit(0);
			joueur.setPtAction_Neant(0);
			joueur.setEstSetPointAction(false);
		}

		return testGlobal;
	}
}
