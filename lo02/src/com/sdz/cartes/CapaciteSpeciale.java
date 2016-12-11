package com.sdz.cartes;

import com.sdz.modele.*;
import java.util.*;

import com.sdz.modele.Joueur;
import com.sdz.modele.Partie;

public class CapaciteSpeciale {
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
			System.out.print("Choisir une Divinité pour effectuer la capacite");
			int i = 1;
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				System.out.println(i + ":" + it.next());
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
			if(partie.getDifficulte().equals("Debutant")){
				this.stategie=new Debutant();
			}else{
				this.stategie=new Expert();
			}
			divine=this.stategie.choisirIdDivinite(partie);
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
		System.out.println("Les divinité possédant le dogme Nature ou Mystique: ");
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
			System.out.println("Il n'y a aucun divinité possédant le dogme Nature ou Mystique!");
			return false;
		} else {
			// on distingu joueur physique et joueur virtuel
			if (!joueur.estBot()) {
				Scanner sc = new Scanner(System.in);
				String Divine = "";
				boolean choix = false;
				int idChoisi = 0;
				do {
					System.out.print(
							"Choisir id Divinité possédant le dogme Nature ou Mystique pour empêcher sa sacrifice à la carte croyant: ");
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
				if(partie.getDifficulte().equals("Debutant")){
					this.stategie=new Debutant();
				}else{
					this.stategie=new Expert();
				}
				idChoisi=this.stategie.choisirIdDivinite(partie);
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
		} else if (this.id >= 9 && this.id <= 11 || this.id == 23 || this.id == 22 || this.id == 9) {
			// imposer la sacrifice un croyant d'un joueur
			this.joueur = partie.getListeJoueurs().get(this.choisirlaDivinite()-1);
			partie.setJoueurEncours(this.joueur);
			LinkedList<Croyant> carte = new LinkedList();
			System.out.println("Choisir id carte croyant a sacrifier");
			for (int i = 1; i <= joueur.getLaMain().getListeCroyantGuidee().size(); i++) {
				for (int j = 1; j <= joueur.getLaMain().getListeCroyantGuidee().get(i - 1).size(); j++) {
					carte.add((Croyant) joueur.getLaMain().getListeCroyantGuidee().get(i - 1).get(j - 1));
					System.out.println(carte.toString());
				}
			}
			if (this.joueur.estBot()) {
				Collections.shuffle(carte);
				this.joueur.setSacrifice(true);
				this.joueur.sacrifierCroyant(carte.pop().getId(), partie);
			} else {
				Scanner sc = new Scanner(System.in);
				int croyant = sc.nextInt();
				this.joueur.setSacrifice(true);
				this.joueur.sacrifierCroyant(croyant, partie);
			}
		} else if (this.id == 12) {
			// guide revient dans sa main et croyant lie revient au centre de la
			// table
			for (int i = 1; i <= partie.getListeJoueurs().size(); i++) {
				for (int j = 1; j <= partie.getListeJoueurs().get(i - 1).getLaMain().getListeCroyantGuidee().get(i - 1)
						.size(); j++) {
					if (partie.getListeJoueurs().get(i - 1).getLaMain().getListeCroyantGuidee().get(i - 1).get(j - 1)
							.getId() == id) {
						Joueur joueur = partie.getListeJoueurs().get(i - 1);
						if (joueur.estBot()) {
							int guide = (int) Math
									.ceil((joueur.getLaMain().getListeGuideSpirituelGuider().size()) * Math.random());
							partie.getJeuDeCartes().getListeCartesAction()
									.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(guide));
							partie.getEspaceCommun().getListeCartesPret()
									.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(guide));
						} else {
							System.out.println("choisir la carte guide spirituel a revenir");
							for (int k = 1; k <= joueur.getLaMain().getListeGuideSpirituelGuider().size(); k++) {
								System.out.println(k + ":"
										+ joueur.getLaMain().getListeGuideSpirituelGuider().get(k - 1).getNom());
							}
							Scanner sc = new Scanner(System.in);
							int guide = sc.nextInt();
							partie.getJeuDeCartes().getListeCartesAction()
									.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(guide));
							partie.getEspaceCommun().getListeCartesPret()
									.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(guide));
						}
						break;
					}
				}
			}
		} else if (this.id == 13 || this.id == 35) {
			// relancer de de cosmonogie
			partie.lancerDe();
		} else if (this.id > 13 && this.id < 19) {
			// donner une point d'action de nuit
			partie.getJoueurEncours().setPtAction_Jour(partie.getJoueurEncours().getPtAction_Nuit() + 1);
		} else if (this.id == 19) {
			boolean choix = true;
			while (choix) {
				System.out.println(
						"Choisir id Divinité origine Humaine ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i = 1; i <= partie.getListeJoueurs().size(); i++) {
					System.out.print("id " + i + " nom " + partie.getListeJoueurs().get(i - 1).getNom() + " Dogme "
							+ partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc = new Scanner(System.in);
				int Divine = sc.nextInt();
				Joueur joueur = partie.getListeJoueurs().get(Divine - 1);
				if (!joueur.equals(partie.getJoueurEncours())) {
					partie.getListeJoueurs().get(Divine - 1).setSacrifice(false);
					choix = false;
				}
			}
		} else if (this.id == 20) {
			boolean choix = true;
			while (choix) {
				System.out.println(
						"Choisir id Divinité origine Humain ou Symbole pour empecher sa sacrifice la carte croyant");
				for (int i = 1; i <= partie.getListeJoueurs().size(); i++) {
					System.out.print("id " + i + " nom " + partie.getListeJoueurs().get(i - 1).getNom() + " Dogme "
							+ partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc = new Scanner(System.in);
				int Divine = sc.nextInt();
				Joueur joueur = partie.getListeJoueurs().get(Divine - 1);
				if (!joueur.equals(partie.getJoueurEncours()) && joueur.getLaMain().getCarteDivinite().id != 84
						&& joueur.getLaMain().getCarteDivinite().id != 85) {
					partie.getListeJoueurs().get(Divine - 1).setSacrifice(false);
					choix = false;
				}
			}
		} else
		// id 21=8
		// id 22, 23=9
		if (this.id == 24) {
			// retirer les croyants rattacher une autre divinite
			System.out.println("Choisir id de la Divinité pour effectuer la capacite");
			boolean choix = true;
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				if (it.next().getId() > partie.getJoueurEncours().getId()) {
					System.out.println("id" + it.next().getId() + it.next().getNom());
				}
			}
			while (choix) {
				Scanner sc = new Scanner(System.in);
				int Divine;
				Divine = sc.nextInt();
				if (Divine != partie.getJoueurEncours().getId() && Divine < partie.getListeJoueurs().size()
						&& Divine < 0) {
					System.out.println("Choisir la carte guider a retirer"
							+ partie.getListeJoueurs().get(Divine).getLaMain().getListeGuideSpirituelGuider());
				}

			}

		} else if (this.id == 25) {
			// prender pt Action
			System.out.println("Choisir Divinité pour prendre ses points d'action");
			boolean choix = true;
			int Divine;
			while (choix) {
				if (!partie.getJoueurEncours().estBot()) {
					System.out.print("Choisir la Divinité");
					Iterator<Joueur> it = partie.getListeJoueurs().iterator();
					int i = 1;
					while (it.hasNext()) {
						if (it.next().getId() != partie.getJoueurEncours().getId()) {
							System.out.println(i + ":" + it.next());
							i++;
						}
					}
					Scanner sc = new Scanner(System.in);

					Divine = sc.nextInt();
				} else {

					do {
						Divine = (int) Math.ceil((partie.getListeJoueurs().size() - 1) * Math.random());
					} while (partie.getListeJoueurs().get(Divine - 1).getId() == partie.getJoueurEncours().getId());
				}
				if (partie.getListeJoueurs().get(Divine - 1).getId() != partie.getJoueurEncours().getId()
						&& Divine < partie.getListeJoueurs().size()) {
					partie.getJoueurEncours().setPtAction_Jour(partie.getJoueurEncours().getPtAction_Jour()
							+ partie.getListeJoueurs().get(Divine - 1).getPtAction_Jour());
					partie.getListeJoueurs().get(Divine - 1).setPtAction_Jour(0);
					partie.getJoueurEncours().setPtAction_Nuit(partie.getJoueurEncours().getPtAction_Nuit()
							+ partie.getListeJoueurs().get(Divine - 1).getPtAction_Nuit());
					partie.getListeJoueurs().get(Divine - 1).setPtAction_Nuit(0);
					partie.getJoueurEncours().setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant()
							+ partie.getListeJoueurs().get(Divine - 1).getPtAction_Neant());
					partie.getListeJoueurs().get(Divine - 1).setPtAction_Neant(0);
					choix = false;
				}

			}
		} else if (this.id == 26) {
			// benificier une capacite speciale dune carte croyant appartenant a
			// un autre joueur
		}
		if (this.id > 26 && this.id < 32) {
			partie.getJoueurEncours().setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant() + 1);
		}
		if (this.id == 32) {
			boolean choix = true;
			while (choix) {
				System.out.println(
						"Choisir id Divinité origine Nature ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i = 1; i <= partie.getListeJoueurs().size(); i++) {
					System.out.print("id " + i + " nom " + partie.getListeJoueurs().get(i - 1).getNom() + " Dogme "
							+ partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc = new Scanner(System.in);
				int Divine = sc.nextInt();
				Joueur joueur = partie.getListeJoueurs().get(Divine - 1);
				if (!joueur.equals(partie.getJoueurEncours()) && joueur.getLaMain().getCarteDivinite().id != 89) {
					joueur.setSacrifice(false);
					choix = false;
				}
			}
		} else if (this.id == 33) {
			boolean choix = true;
			while (choix) {
				System.out.println(
						"Choisir id Divinité origine Chaos ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i = 1; i <= partie.getListeJoueurs().size(); i++) {
					System.out.print("id " + i + " nom " + partie.getListeJoueurs().get(i - 1).getNom() + " Dogme "
							+ partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc = new Scanner(System.in);
				int Divine = sc.nextInt();
				Joueur joueur = partie.getListeJoueurs().get(Divine - 1);
				if (!joueur.equals(partie.getJoueurEncours()) && joueur.getLaMain().getCarteDivinite().id != 82) {
					joueur.setSacrifice(false);
					choix = false;
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
		} else if (this.id > 37 && this.id < 41) {
			// apocalyse
		} else if (this.id > 40 && this.id < 49) {
			// gagner point action en fonction de carte croyant attache
			for (int i = 1; i <= partie.getListeJoueurs().size(); i++) {
				Joueur joueur = partie.getListeJoueurs().get(i);
				for (int j = 1; j <= joueur.getLaMain().getListeGuideSpirituelGuider().size(); j++) {
					if (joueur.getLaMain().getListeGuideSpirituelGuider().get(j).id == id) {
						int ptActionajouter = joueur.getLaMain().getListeCroyantGuidee().get(j).size();
						System.out.println("Choisir pt Action ajouter, 1 Jour, 2 Nuit, 3 Neant");
						Scanner sc = new Scanner(System.in);
						int originePtAction = sc.nextInt();
						switch (originePtAction) {
						case 1:
							joueur.setPtAction_Jour(joueur.getPtAction_Jour() + ptActionajouter);
							break;
						case 2:
							joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + ptActionajouter);
							break;
						case 3:
							joueur.setPtAction_Neant(joueur.getPtAction_Neant() + ptActionajouter);
							break;
						default:
							System.out.println("Non pt Action a ajouter ");
							break;
						}
					}
				}
			}

		} else if (this.id == 49) {
			// sacrifie tous les cartes croyant Neant d'une Divinité Humain
			joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			while (joueur.getLaMain().getCarteDivinite().getId() > 82
					&& joueur.getLaMain().getCarteDivinite().getId() < 87) {
				System.out.println("Choisir la Divinité Humain");
				joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
			}
			for (int i = 0; i < joueur.getLaMain().getListeCroyantGuidee().size(); i++) {
				for (int j = 0; i < joueur.getLaMain().getListeCroyantGuidee().get(i).size(); j++) {
					CarteAction carte = joueur.getLaMain().getListeCroyantGuidee().get(i).get(j);
					if (carte.getOrigine() == "Neant") {
						joueur.sacrifierCroyant(carte.getId(), partie);
					}
				}
			}
		} else if (this.id == 50) {
			// sacrifie Guide Spirituel si lui ou sa Divinite ne croit pas au
			// dogme Chaos
			boolean choix = false;
			int Guide;
			while (!choix) {
				joueur = partie.getListeJoueurs().get(this.choisirlaDivinite());
				for (int i = 0; i < 3; i++) {
					if (joueur.getLaMain().getCarteDivinite().getDogme()[i] == "Chaos") {
						choix = true;
					}
				}
			}
			System.out.println("Choisir la guide Spirituel");
			Iterator<CarteAction> it = joueur.getLaMain().getListeGuideSpirituelGuider().iterator();
			while (it.hasNext()) {
				int i = 1;
				System.out.println(i + ":" + it.next());
			}
			if (partie.getJoueurEncours().estBot()) {
				Guide = (int) Math.ceil((joueur.getLaMain().getListeGuideSpirituelGuider().size()) * Math.random());
			} else {
				Scanner sc = new Scanner(System.in);
				Guide = sc.nextInt();
				while (Guide < 1 || Guide > joueur.getLaMain().getListeGuideSpirituelGuider().size()) {
					System.out.println("Rechoisir la Guide Spirituel");
					Guide = sc.nextInt();
				}
			}
			joueur.sacrifierGuideSpirit(joueur.getLaMain().getListeGuideSpirituelGuider().get(Guide - 1).getId(),
					partie);

		} else if (this.id == 51) {
			// defausser tous les cartes croyant Origine Nuit ou Neant; et Dogme
			// Nature
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				if (carte.getOrigine() == "Nuit" || carte.getOrigine() == "Neant") {
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
				for (int k = 0; k < it.next().size(); i++) {
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
					System.out.println("Rechoisir la Guide Spirituel");
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
					System.out.println("Rechoisir la Guide Spirituel");
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
