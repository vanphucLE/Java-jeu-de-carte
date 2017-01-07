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

	private int convertIdsEntree(String str) {
		Boolean test = true;
		str = str.trim();
		int idReturn = 0;
		if (str.matches("\\d+")) {
			idReturn = Integer.parseInt(str);
		}
		return idReturn;
	}

	// get liste Joueur ayant le dogme1 ou le dogme2 peut être empêché de
	// sacrifier un croyant
	public LinkedList<Joueur> listeJoueurPeutEtreEmpecher(String dogme1, String dogme2) {

		LinkedList<Joueur> listeJoueurs = new LinkedList<Joueur>();
		System.out.println("Les divinité possédant le dogme " + dogme1 + " ou " + dogme2 + ": ");
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

	private int boxChoisiJoueur(String str) {
		int idChoisi = 0;
		if (!this.joueurEnCours.estBot()) {
			String[] chaine = new String[this.partie.getListeJoueurs().size() - 1];
			int i = -1;
			for (Joueur j : this.partie.getListeJoueurs()) {
				if (!j.equals(this.joueurEnCours)) {
					i++;
					chaine[i] = j.getNom();
				}
			}
			String nomJ = (String) JOptionPane.showInputDialog(null, str, "Acte", JOptionPane.QUESTION_MESSAGE, null,
					chaine, chaine[0]);
			for (Joueur j : this.partie.getListeJoueurs()) {
				if (j.getNom().equals(nomJ)) {
					idChoisi = j.getId();
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
		return idChoisi;
	}

	public Boolean effectuerCapacite(Partie partie) {
		this.partie = partie;
		this.joueurEnCours = partie.getJoueurEncours();
		Boolean testGlobal = false;
		if (this.id >= 1 && this.id <= 5) {
			this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + 1);
			JOptionPane.showMessageDialog(null, "Vous avez gagner un point d'action Jour!");
		} else if (this.id == 6) {
			// empêcher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Nature ou Mystique pour empêcher sa sacrifice!");
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
			// empêcher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Chaos ou Mystique pour empêcher sa sacrifice!");
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
			int idChoisi = this.boxChoisiJoueur("Choississez le joueur à prendre 2 carte au hasard: ");

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
					choix = false;
				}
			}
		} else if (this.id >= 9 && this.id <= 11 || this.id == 23 || this.id == 22 || this.id == 52) {
			// imposer la sacrifice un croyant d'un autre joueur
			int idChoisi = this.boxChoisiJoueur("Choississez le joueur à lui obliger de sacrifier une carte croyant: ");
			this.partie.setJoueurDernier(this.joueurEnCours);
			// joueur qui est choisi va sacifier un carteCroyant
			Joueur joueur = partie.getListeJoueurs().get(idChoisi - 1);
			if (!joueur.estBot()) {
				JOptionPane.showMessageDialog(null, "Joueur " + this.joueurEnCours.getNom()
						+ " vous a obligé de sacrifier 1 carte Croyant!\nChoissiez une  carte Croyant dans l'espace Guidee pour sacrifier!");

				this.joueurEnCours = joueur;
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("sacrifierCroyant");
				try {
					this.partie.suspend();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				this.joueurEnCours = joueur;
				if (this.joueurEnCours.getLaMain().getListeCroyantGuidee().size() > 0
						&& this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0).get(0).getEstSacrifie()) {
					this.joueurEnCours.sacrifierCroyant(
							this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0).get(0).getId(), this.partie);
				}
			}
		} else if (this.id == 12) {
			// guideSpirituel revient dans sa main et croyant lie revient au
			// centre de la
			// table
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez la carte Guide Spirituel dans votre espace guidee pour lui revient à vôtre main!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("recupererGuideSpirituel");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					jV.getEffectuerCapacite().recupererGuideSpirituel(this.joueurEnCours,
							this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0));
				}
			}

		} else if (this.id == 13 || this.id == 35) {
			// relancer de de cosmonogie
			partie.lancerDe();
			partie.setFiniTour(true);
		} else if (this.id >= 14 && this.id <= 18) {
			// donner une point d'action de nuit
			this.joueurEnCours.setPtAction_Nuit(partie.getJoueurEncours().getPtAction_Nuit() + 1);
		} else if (this.id == 19) {
			// Empêcher sacifier croyant
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Humain ou Mystique pour empêcher sa sacrifice!");
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
			// Empêcher sacifier croyant
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Humain ou Symboles pour empêcher sa sacrifice!");
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
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Guide Spirituel dans l'espace Guidée d'un autre joueur pour la défausser!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("defausserGuideSpirituel");
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					jV.getEffectuerCapacite().deffauserGuideSpirituel(this.joueurEnCours,
							this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0));
				}
			}
		} else if (this.id == 25) {
			// prender pt Action
			System.out.println("Choisir Divinité pour prendre ses points d'action");
			int Divine = this.boxChoisiJoueur("Choissiez un joueur pour récupérer ses points!");
			this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour()
					+ this.partie.getListeJoueurs().get(Divine - 1).getPtAction_Jour());
			this.partie.getListeJoueurs().get(Divine - 1).setPtAction_Jour(0);
			this.joueurEnCours.setPtAction_Nuit(this.joueurEnCours.getPtAction_Nuit()
					+ this.partie.getListeJoueurs().get(Divine - 1).getPtAction_Nuit());
			this.partie.getListeJoueurs().get(Divine - 1).setPtAction_Nuit(0);
			this.joueurEnCours.setPtAction_Neant(this.joueurEnCours.getPtAction_Neant()
					+ this.partie.getListeJoueurs().get(Divine - 1).getPtAction_Neant());
			this.partie.getListeJoueurs().get(Divine - 1).setPtAction_Neant(0);

		} else if (this.id == 26) {
			// benificier une capacite speciale dune carte croyant appartenant a
			// un autre joueur
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un autre joueur pour bénéficier sa capacité speciale!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("beneficierCapacite");
			} else {

			}
		}
		if (this.id >= 27 && this.id <= 31) {
			this.joueurEnCours.setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant() + 1);
		}
		if (this.id == 32) {
			// empêcher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Nature ou Mystique pour empêcher sa sacrifice!");
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
			// empêcher Croyant sacrifier
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Chaos ou Mystique pour empêcher sa sacrifice!");
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
			if (!this.joueurEnCours.estBot()) {
				if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
					JOptionPane.showMessageDialog(null,
							"Vous ne pouvez pas jouer cette carte Apocalypse en ce tour!/nCette carte est alors défaussé! ");
					System.out.println("Vous ne pouvez pas jouer la carte Apocalypse en ce tour");
				} else {
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.jouerApocalypse_2();
				}
			} else {
				if (partie.getEstApocalypseAvant() != 0 && partie.getEstApocalypseAvant() != -1) {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					jV.jouerApocalypse();
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
					String[] chaine = { "Jour", "Nuit", "Néant" };
					String originePtAction = (String) JOptionPane.showInputDialog(null,
							"Choissiez l'origine du point d'Action: ", "Acte", JOptionPane.QUESTION_MESSAGE, null,
							chaine, chaine[0]);
					JOptionPane.showMessageDialog(null,
							"Vous avez gagné " + ptAjoute + " point d'Action " + originePtAction + " !");
					switch (originePtAction) {
					case "Jour":
						this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + ptAjoute);
						break;
					case "Nuit":
						this.joueurEnCours.setPtAction_Nuit(this.joueurEnCours.getPtAction_Nuit() + ptAjoute);
						break;
					case "Néant":
						this.joueurEnCours.setPtAction_Neant(this.joueurEnCours.getPtAction_Neant() + ptAjoute);
						break;
					}
				} else {
					this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + ptAjoute);
				}
				System.out.print("Nouvaux points d'Action: ");
				System.out.print("[Jour " + this.joueurEnCours.getPtAction_Jour() + "] ");
				System.out.print("[Nuit " + this.joueurEnCours.getPtAction_Nuit() + "] ");
				System.out.println("[Néant " + this.joueurEnCours.getPtAction_Neant() + "] ");
			}
		} else if (this.id == 49) {
			// sacrifie tous les cartes croyant Neant d'une Divinité Humain
			int idDivineChoisi = this.boxChoisiJoueur("Choisi un Divinité ayant le Dogme Humain: ");
			this.partie.setJoueurDernier(this.joueurEnCours);
			Joueur joueur = partie.getListeJoueurs().get(idDivineChoisi - 1);
			if (joueur.getLaMain().getCarteDivinite().getId() > 82
					&& joueur.getLaMain().getCarteDivinite().getId() < 87) {
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null, "Jouer " + this.joueurEnCours.getNom()
							+ " vous a choisi pour sacrifier tous cartes croyant!");
				}
				this.joueurEnCours = joueur;
				for (int i = 0; i < this.joueurEnCours.getLaMain().getListeCroyantGuidee().size(); i++) {
					for (int j = 0; i < this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(i).size(); j++) {
						CarteAction carte = this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(i).get(j);
						if (carte.getOrigine().equals("Neant")) {
							this.joueurEnCours.sacrifierCroyant(carte.getId(), partie);
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Vous avez choisi un Divinité qui n'a pas le Dogmae Huamain!\nCette carte est alors défaussé!");
				}
			}
			this.joueurEnCours = this.partie.getJoueurDernier();
		} else if (this.id == 50) {
			// sacrifie Guide Spirituel si lui ou sa Divinite ne croit pas au
			// dogme Chaos
			this.partie.setJoueurDernier(this.joueurEnCours);
			if (!this.joueurEnCours.estBot()) {
				JOptionPane.showMessageDialog(null,
						"Choissiez un carte Guide Spirituel si lui ou sa Divinite ne croit pas au dogme Chaos!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("sacrifierGuideSpirituelCHAOS");
			} else {
				int idDivin = this.boxChoisiJoueur("");
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
				if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					jV.getEffectuerCapacite().sacrifierGuideSpirituelCHAOS(joueur,
							joueur.getLaMain().getListeGuideSpirituelGuider().get(0));
				}
			}
		} else if (this.id == 51) {
			// defausser tous les cartes croyant Origine Nuit ou Neant; et Dogme
			// Nature
			for (int i = 0; i < partie.getEspaceCommun().getListeCartesPret().size(); i++) {
				CarteAction carte = partie.getEspaceCommun().getListeCartesPret().get(i);
				if (carte.getOrigine().equals("Nuit") || carte.getOrigine().equals("Neant")) {
					for (String dogme : carte.getDogme()) {
						if (dogme.equals("Nature")) {
							this.partie.getJeuDeCartes().recupererCarteAction(carte);
							this.partie.getEspaceCommun().supprimerCarte(carte);
							break;
						}
					}
				}
			}
			// id 52 ~~ id 9
		} else if (id == 53) {
			// oblige une Divinite Nature ou Mystique sacrifier une de ses Guide
			int idDivin = this.boxChoisiJoueur(
					" Choissiez une Divinité ayant le Dogme Nature ou Mystique à sacrifier l'un de ses Guides Spirituels");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			Boolean test = false;
			for (String dogme : joueur.getLaMain().getCarteDivinite().getDogme()) {
				if (dogme.equals("Nature") || dogme.equals("Mystique")) {
					test = true;
					break;
				}
			}
			if (test) {
				this.partie.setJoueurDernier(this.joueurEnCours);
				this.joueurEnCours = joueur;
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null, "Choissiez carte Guide Spirituel pour sacrifier!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("sacrifierGuideSpirituel");
				} else {
					if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
						this.joueurEnCours.sacrifierGuideSpirit(
								this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0).getId(),
								this.partie);
						this.partie.setJoueurEncours(this.partie.getJoueurDernier());
					}
				}
			} else {
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Vous avez choisi une Divinité qui n'a pas le Dogme Nature et Mystique.\nCette Capacité speciale est alors annulée! ");
				}
			}
		} else if (id == 54) {
			// reprende dans sa main un de ses Guides
			// Divinite origine nuit ou dogme Mystique ou Chaos
			int idDivin = this.boxChoisiJoueur("Choissiez un Divinite origine Nuit ou dogme Mystique ou Chaos");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			Boolean test = false;
			if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Nuit")) {
				test = true;
			} else {
				for (String dogme : joueur.getLaMain().getCarteDivinite().getDogme()) {
					if (dogme.equals("Mystique") || dogme.equals("Chaos")) {
						test = true;
						break;
					}
				}
			}
			if (test) {
				if (joueur.estBot()) {
					if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
						joueur.getLaMain().getListeCarteA()
								.add(this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().remove(0));
						this.partie.getJeuDeCartes().getListeCartesAction()
								.addAll(this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0));
					}
				} else {
					JOptionPane.showMessageDialog(null,"Choissiez un carte Guide Spirituel!");
					JoueurPhysique jP=(JoueurPhysique)joueur;
					jP.setActionEnTrain("recupererGuideSpirituel2");
				}
			}
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

		} else if (id == 84 || id == 85) { // thêm effectuer vô
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
