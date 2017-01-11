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
				if (j.getLaMain().getListeGuideSpirituelGuider().size() == 0) {
					test = false;
				}
				if (test) {
					System.out.println("  +" + j + "\n   ." + j.getLaMain().getCarteDivinite());
					listeJoueurs.add(j);
				}
			}
		}
		return listeJoueurs;
	}

	// test
	public LinkedList<Joueur> listeJoueurACarteGuidee() {
		LinkedList<Joueur> listeJoueurs = new LinkedList<Joueur>();
		for (Joueur j : partie.getListeJoueurs()) {
			if (!this.joueurEnCours.estEqual(j)) {
				Boolean test = true;
				if (j.getLaMain().getListeGuideSpirituelGuider().size() == 0) {
					test = false;
				}
				if (test) {
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

	public void effectuerCapacite(Partie partie) {
		this.partie = partie;
		this.joueurEnCours = partie.getJoueurEncours();
		if (this.id >= 1 && this.id <= 5) {
			this.joueurEnCours.setPtAction_Jour(this.joueurEnCours.getPtAction_Jour() + 1);
			JOptionPane.showMessageDialog(null, "Vous avez gagner un point d'action Jour!");
		} else if (this.id == 6) {
			// empêcher Croyant sacrifier
			System.out.println("empêcher Croyant sacrifier une carte Guide Spirituel");
			LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Nature", "Mystique");
			if (listJoueurs.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Nature ou Mystique pour empêcher sa sacrifice!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("empercherCroyantNatureMystique");
				} else {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					if (listJoueurs.size() > 0) {
						int i = (int) Math.ceil(Math.random() * listJoueurs.size());
						if (i != 0) {
							i--;
						}
						Joueur joueur = listJoueurs.get(i);
						joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null,
									"Joueur " + this.joueurEnCours.getNom()
											+ " a empêcher la sacrifice sa carte Coyant "
											+ joueur.getLaMain().getListeCroyantGuidee().get(0).get(0) + " !");
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun Divinité ayant le requirement de la carte!");
				}
			}
		} else if (this.id == 7) {
			// empêcher Guide Spirituel sacrifier
			System.out.println("empêcher Guide Spirituel sacrifier une carte Guide Spirituel");
			LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Chaos", "Mystique");
			if (listJoueurs.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Guide Spirituel dans l'espace Guidée d'un joueur ayant dogme Chaos ou Mystique pour empêcher sa sacrifice!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("empercherGuideSpirituelChaosMystique");
				} else {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					if (listJoueurs.size() > 0) {
						int i = (int) Math.ceil(Math.random() * listJoueurs.size());
						if (i != 0) {
							i--;
						}
						Joueur joueur = listJoueurs.get(i);
						joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null,
									"Joueur " + this.joueurEnCours.getNom()
											+ " a empêcher la sacrifice sa carte Coyant "
											+ joueur.getLaMain().getListeGuideSpirituelGuider().get(0) + " !");
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun Divinité ayant le requirement de la carte!");
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
		} else if (this.id == 9 && this.id == 10 || this.id == 23 || this.id == 22 || this.id == 52) {
			// imposer la sacrifice un croyant d'un autre joueur
			int idChoisi = this
					.boxChoisiJoueur("Choississez le joueur pour lui obliger de sacrifier une carte croyant: ");
			System.out.println("imposer la sacrifice un croyant d'un autre joueur");

			// joueur qui est choisi va sacifier un carte Croyant
			Joueur joueur = partie.getListeJoueurs().get(idChoisi - 1);
			if (!joueur.estBot()) {
				JOptionPane.showMessageDialog(null, "Joueur " + this.joueurEnCours.getNom()
						+ " vous a obligé de sacrifier 1 carte Croyant!\nChoissiez une  carte Croyant dans l'espace Guidee pour sacrifier!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("sacrifierCroyant");
			} else {
				Joueur joueurPrin = this.joueurEnCours;
				this.joueurEnCours = joueur;
				if (this.joueurEnCours.getLaMain().getListeCroyantGuidee().size() > 0
						&& this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0).get(0).getEstSacrifie()) {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					jV.getPanelJeu().dessinerPanelCarteJouee(
							this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0).get(0));
					this.joueurEnCours.sacrifierCroyant(
							this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0).get(0).getId(), this.partie);
				}
				this.joueurEnCours = joueurPrin;
			}
		} else if (this.id == 11) {
			// imposer la sacrifice un guide Spirituel d'un autre joueur
			int idChoisi = this
					.boxChoisiJoueur("Choississez le joueur pour lui obliger de sacrifier une carte guideSpirituel: ");
			System.out.println("imposer la sacrifice un croyant d'un autre joueur");

			// joueur qui est choisi va sacifier un carte Croyant
			Joueur joueur = partie.getListeJoueurs().get(idChoisi - 1);
			if (!joueur.estBot()) {
				JOptionPane.showMessageDialog(null, "Joueur " + this.joueurEnCours.getNom()
						+ " vous a obligé de sacrifier 1 carte Croyant!\nChoissiez une  carte Croyant dans l'espace Guidee pour sacrifier!");
				JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				jP.setActionEnTrain("sacrifierGuideSpirituel");
			} else {
				Joueur joueurPrin = this.joueurEnCours;
				this.joueurEnCours = joueur;
				if (this.joueurEnCours.getLaMain().getListeCroyantGuidee().size() > 0
						&& this.joueurEnCours.getLaMain().getListeCroyantGuidee().get(0).get(0).getEstSacrifie()) {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					jV.getPanelJeu().dessinerPanelCarteJouee(
							this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0));
					this.joueurEnCours.sacrifierCroyant(
							this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0).getId(), this.partie);
				}
				this.joueurEnCours = joueurPrin;
			}
		} else if (this.id == 12) {
			// guideSpirituel revient dans sa main et croyant lie revient au
			// centre de la
			// table
			if (!this.joueurEnCours.estBot()) {
				if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					JOptionPane.showMessageDialog(null,
							"Choissiez la carte Guide Spirituel dans votre espace guidee pour lui revient à vôtre main!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("recupererGuideSpirituel");
				} else {
					JOptionPane.showMessageDialog(null,
							"Vous n'avez aucun carte Guide Spirituel dans votre espace guidee pour lui revient à vôtre main!");
				}
			} else {
				JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
				if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					jV.getEffectuerCapacite().recupererGuideSpirituel(this.joueurEnCours,
							this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0));
				}
			}
		} else if (this.id == 13 || this.id == 35) {
			// relancer de de cosmonogie
			partie.lancerDe("");
			partie.setFiniTour(true);
		} else if (this.id >= 14 && this.id <= 18) {
			// donner une point d'action de nuit
			this.joueurEnCours.setPtAction_Nuit(partie.getJoueurEncours().getPtAction_Nuit() + 1);
		} else if (this.id == 19) {
			// Empêcher sacifier croyant
			System.out.println("Empêcher une Carte Croyant!");
			LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Humain", "Mystique");
			if (listJoueurs.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Humain ou Mystique pour empêcher sa sacrifice!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("empercherCroyantHumainMystique");
				} else {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					if (listJoueurs.size() > 0) {
						int i = (int) Math.ceil(Math.random() * listJoueurs.size());
						if (i != 0) {
							i--;
						}
						Joueur joueur = listJoueurs.get(i);
						joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null,
									"Joueur " + this.joueurEnCours.getNom()
											+ " a empêcher la sacrifice sa carte Coyant "
											+ joueur.getLaMain().getListeCroyantGuidee().get(0).get(0) + " !");
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun Divinité ayant le requirement de la carte!");
				}
			}
		} else if (this.id == 20) {
			// Empêcher sacifier guide Spirituel
			System.out.println("Empêcher une Carte Guide Spirituel!");
			LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Humain", "Mystique");
			if (listJoueurs.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Guide Spirituel dans l'espace Guidée d'un joueur ayant dogme Humain ou Symboles pour empêcher sa sacrifice!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("empercherGuideSpirituelHumainSymboles");
				} else {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					if (listJoueurs.size() > 0) {
						int i = (int) Math.ceil(Math.random() * listJoueurs.size());
						if (i != 0) {
							i--;
						}
						Joueur joueur = listJoueurs.get(i);
						joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null,
									"Joueur " + this.joueurEnCours.getNom()
											+ " a empêcher la sacrifice sa carte Coyant "
											+ joueur.getLaMain().getListeGuideSpirituelGuider().get(0) + " !");
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun Divinité ayant le requirement de la carte!");
				}
			}
		} else
		// id 21=8
		// id 22, 23=9
		if (this.id == 24) {
			// retirer les croyants rattacher une autre divinite
			LinkedList<Joueur> listJoueur = this.listeJoueurACarteGuidee();
			if (listJoueur.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Guide Spirituel dans l'espace Guidée d'un autre joueur pour la défausser!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("defausserGuideSpirituel");
				} else {
					int i = (int) Math.ceil(Math.random() * listJoueur.size());
					if (i != 0) {
						i--;
					}
					Joueur joueur = listJoueur.get(i);
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					jV.getEffectuerCapacite().deffauserGuideSpirituel(joueur,
							joueur.getLaMain().getListeGuideSpirituelGuider().get(0));
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun joueur ayant carte dans espace guidée!");
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
			System.out.println("Empêcher la sacrifice d'une carte Croyant");
			LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Nature", "Mystique");
			if (listJoueurs.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Croyant dans l'espace Guidée d'un joueur ayant dogme Nature ou Mystique pour empêcher sa sacrifice!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("empercherCroyantNatureMystique");
				} else {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					if (listJoueurs.size() > 0) {
						int i = (int) Math.ceil(Math.random() * listJoueurs.size());
						if (i != 0) {
							i--;
						}
						Joueur joueur = listJoueurs.get(i);
						joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null,
									"Joueur " + this.joueurEnCours.getNom()
											+ " a empêcher la sacrifice sa carte Coyant "
											+ joueur.getLaMain().getListeGuideSpirituelGuider().get(0) + " !");
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun Divinité ayant le requirement de la carte!");
				}
			}
		} else if (this.id == 33) {
			// empêcher Guide Spirituel sacrifier
			System.out.println("empêcher Guide Spirituel sacrifier une carte Guide Spirituel");
			LinkedList<Joueur> listJoueurs = this.listeJoueurPeutEtreEmpecher("Chaos", "Mystique");
			if (listJoueurs.size() > 0) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Choissiez une carte Guide Spirituel dans l'espace Guidée d'un joueur ayant dogme Chaos ou Mystique pour empêcher sa sacrifice!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("empercherGuideSpirituelChaosMystique");
				} else {
					JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;
					if (listJoueurs.size() > 0) {
						int i = (int) Math.ceil(Math.random() * listJoueurs.size());
						if (i != 0) {
							i--;
						}
						Joueur joueur = listJoueurs.get(i);
						joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null,
									"Joueur " + this.joueurEnCours.getNom()
											+ " a empêcher la sacrifice sa carte Coyant "
											+ joueur.getLaMain().getListeGuideSpirituelGuider().get(0) + " !");
						}
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun Divinité ayant le requirement de la carte!");
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
			Joueur joueurPrin = this.joueurEnCours;
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
			this.joueurEnCours = joueurPrin;
		} else if (this.id == 50) {
			// sacrifie Guide Spirituel si lui ou sa Divinite ne croit pas au
			// dogme Chaos
			LinkedList<Joueur> listJoueur = this.listeJoueurACarteGuidee();
			if (listJoueur.size() > 0) {
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
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Il n'y a aucun joueur ayant carte dans son espace guidée!");
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
				// this.partie.setJoueurDernier(this.joueurEnCours);
				// this.joueurEnCours = joueur;
				// if (!joueur.estBot()) {
				// JOptionPane.showMessageDialog(null, "Choissiez carte Guide
				// Spirituel pour sacrifier!");
				// JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
				// jP.setActionEnTrain("sacrifierGuideSpirituel");
				// } else {
				// if
				// (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size()
				// > 0) {
				// this.joueurEnCours.sacrifierGuideSpirit(
				// this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0).getId(),
				// this.partie);
				// this.partie.setJoueurEncours(this.partie.getJoueurDernier());
				// }
				// }
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
						joueur.notifyLaMain();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Choissiez un carte Guide Spirituel!");
					JoueurPhysique jP = (JoueurPhysique) joueur;
					jP.setActionEnTrain("recupererGuideSpirituel2");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Vous avez choisi une Divinité qui n'a pas le Dogme Chaos et Mystique et origine Nuit! La capacité spéciale est alors défaussé!");
			}
		} else if (id == 55) {
			// echanger 2 guide
			Boolean test = false;
			for (Joueur j : this.partie.getListeJoueurs()) {
				if (j.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					test = true;
					break;
				}
			}
			if (test) {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null, "Choissiez un des ses carte Guide Spirituel pour échanger! ");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("choisirGuideSpirituelEchanger_1");
				} else {
					int idDivin = this.boxChoisiJoueur("");
					Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
					if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0
							&& this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
						JoueurVirtuel jV = (JoueurVirtuel) this.joueurEnCours;

						jV.getEffectuerCapacite().echangerGuideSpirituel(this.joueurEnCours,
								this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0), joueur,
								joueur.getLaMain().getListeGuideSpirituelGuider().get(0));
					}
				}
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Il n'y a aucun joueur ayant carte Guide Spirituel dans espace Guidee!\nCette Capacite est défaussé! ");
				}
			}
		} else if (id == 56) {
			// defausser tous les cartes croyants Mystique au centre la table
			LinkedList<Integer> indexs = new LinkedList<Integer>();
			for (CarteAction carte : this.partie.getEspaceCommun().getListeCartesPret()) {
				for (String dogme : carte.getDogme()) {
					if (dogme.equals("Mystique")) {
						this.partie.getJeuDeCartes().recupererCarteAction(carte);
						indexs.add(this.partie.getEspaceCommun().getListeCartesPret().indexOf(carte));
						break;
					}
				}
			}
			int indice = 0;
			for (int index : indexs) {
				this.partie.getEspaceCommun()
						.supprimerCarte(this.partie.getEspaceCommun().getListeCartesPret().get(index - indice));
				indice++;
			}
		} else if (id == 57) {
			// poser le de en face qu'il veut
			if (this.joueurEnCours.estBot()) {
				this.partie.lancerDe(this.joueurEnCours.getLaMain().getCarteDivinite().getOrigine());
				this.partie.setFiniTour(true);
			} else {
				String[] chaine = { "Jour", "Nuit", "Néant" };
				String faceDe = (String) JOptionPane.showInputDialog(null, "Choissiez l'origine que vous voulez: ",
						"Acte", JOptionPane.QUESTION_MESSAGE, null, chaine, chaine[0]);
				this.partie.lancerDe(faceDe);
			}
		} else if (id == 58) {
			// detruire un guide spirituel Nuit ou Neant
			int idDivin = this.boxChoisiJoueur("Choissiez un Divinité pour détruire une carte Guide Spirituel");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			int index = -1;
			for (int i = 0; i < joueur.getLaMain().getListeGuideSpirituelGuider().size(); i++) {
				CarteAction carte = joueur.getLaMain().getListeGuideSpirituelGuider().get(i);
				if (carte.getOrigine().equals("Nuit") || carte.getOrigine().equals("Néant")) {
					index = i;
					break;
				}
			}
			if (index != -1) {
				System.out
						.println("Il a choisi carte: " + joueur.getLaMain().getListeGuideSpirituelGuider().get(index));
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Joueur " + this.joueurEnCours + " a détruit votre carte Guide Spirituel!");
				}
				this.partie.getJeuDeCartes()
						.recupererCarteAction(joueur.getLaMain().getListeGuideSpirituelGuider().remove(index));
				this.partie.getEspaceCommun().getListeCartesPret()
						.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(index));
				this.partie.getEspaceCommun().notifyEspaceCommun();
				joueur.notifyLaMain();
			}
		} else if (id == 59) {
			// detruire un guide sprituel Jour ou Neant
			int idDivin = this.boxChoisiJoueur("Choissiez un Divinité pour détruire une carte Guide Spirituel");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			int index = -1;
			for (int i = 0; i < joueur.getLaMain().getListeGuideSpirituelGuider().size(); i++) {
				CarteAction carte = joueur.getLaMain().getListeGuideSpirituelGuider().get(i);
				if (carte.getOrigine().equals("Jour") || carte.getOrigine().equals("Néant")) {
					index = i;
					break;
				}
			}
			if (index != -1) {
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Joueur " + this.joueurEnCours + " a détruit votre carte Guide Spirituel!");
				}
				System.out
						.println("Il a choisi carte: " + joueur.getLaMain().getListeGuideSpirituelGuider().get(index));
				this.partie.getJeuDeCartes()
						.recupererCarteAction(joueur.getLaMain().getListeGuideSpirituelGuider().remove(index));
				this.partie.getEspaceCommun().getListeCartesPret()
						.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(index));
				this.partie.getEspaceCommun().notifyEspaceCommun();
				joueur.notifyLaMain();
			}
		} else if (id == 60) {
			// proteger un Guide Spirituel

		} else if (id == 61 || id == 64) {
			// recuperez un guide d'un autre divinite et aussi les croyants
			int idDivin = this.boxChoisiJoueur("Choissiez un Divinité pour récupérer une carte Guide Spirituel");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Joueur " + this.joueurEnCours + " a récupéré votre carte Guide Spirituel!");
				}
				System.out.println("Il a choisi carte: " + joueur.getLaMain().getListeGuideSpirituelGuider().get(0));
				this.joueurEnCours.getLaMain().getListeCroyantGuidee()
						.add(joueur.getLaMain().getListeCroyantGuidee().remove(0));
				this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider()
						.add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(0));
				joueur.notifyLaMain();
				this.joueurEnCours.notifyLaMain();
			} else {
				if (!this.joueurEnCours.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Le joueur que vous avez choisi n'a aucun carte dans l'espace guidée!\n Cette Capacité spécial est alors défaussé!");
				}
			}
		} else if (id == 62) {
			// sacrifier 2 carte croyant
			int idDivin = this.boxChoisiJoueur("Choissiez un Divinité pour lui attaquer: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			for (int i = 1; i <= 2; i++) {
				if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					this.partie.getJeuDeCartes()
							.recupererCarteAction(joueur.getLaMain().getListeCroyantGuidee().get(0).remove(0));
					if (joueur.getLaMain().getListeCroyantGuidee().get(0).size() == 0) {
						joueur.getLaMain().getListeCroyantGuidee().remove(0);
						this.partie.getJeuDeCartes()
								.recupererCarteAction(joueur.getLaMain().getListeGuideSpirituelGuider().remove(0));
					}
				}
			}
			joueur.notifyLaMain();
		} else if (id == 63) {
			// prendre 3 carte en main d'un autre Divine
			int idDivin = this.boxChoisiJoueur("Choissiez un joueur pour récupérer 3 carte: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			for (int i = 1; i <= 3; i++) {
				if (joueur.getLaMain().getListeCarteA().size() > 0) {
					this.joueurEnCours.getLaMain().completerCarteAction(joueur.getLaMain().getListeCarteA().pop());
				}
			}
			joueur.notifyLaMain();
		}
		// id64 =61
		else if (id == 65) {
			// aucun joueur recoit pt Action ce tour
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				it.next().setEstSetPointAction(false);
			}
		} else if (id == 66 || id == 72) {
			// beneficier une capactier d'une guide ou croyant sans sacrifier
			if (!this.joueurEnCours.estBot()) {
				if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					JOptionPane.showMessageDialog(null,
							"Choissiez carte dans l'espace Guidee pour beneficier une capactier d'une guide ou croyant sans sacrifier!");
					JoueurPhysique jP = (JoueurPhysique) this.joueurEnCours;
					jP.setActionEnTrain("beneficierSansSacrifier");
				} else {
					JOptionPane.showMessageDialog(null,
							"Vous n'avez aucun carte dans l'espace Guidee!\nCette capacité speciale est alors annulé!");
				}
			} else {
				if (this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
					this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().get(0)
							.effectuerCapaciteSpecial(this.partie);
				}
			}
			System.out.println("Choisir carte a beneficier");

		} else if (id == 67) {
			// annule capacite une carte action nuit ou neant
			int idDivin = this.boxChoisiJoueur(
					"Choissiez un joueur pour annuler la capacité speciale d'un des Carte ayant origine Nuit ou Néant: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
				Iterator it = joueur.getLaMain().getListeCroyantGuidee().get(0).iterator();
				while (it.hasNext()) {
					CarteAction carte = (CarteAction) it.next();
					if (carte.getOrigine().equals("Nuit") || carte.getOrigine().equals("Néant")) {
						carte.setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null, "Joueur " + this.joueurEnCours.getNom()
									+ " vous a choisi pour annuler la capacité speciale de votre carte!\n" + carte);
						}
						break;
					}
				}
			}
		} else if (id == 68) {
			// annule capacite une carte action nuit ou neant
			int idDivin = this.boxChoisiJoueur(
					"Choissiez un joueur pour annuler la capacité speciale d'un des Carte ayant origine Jour ou Néant: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
				Iterator it = joueur.getLaMain().getListeCroyantGuidee().get(0).iterator();
				while (it.hasNext()) {
					CarteAction carte = (CarteAction) it.next();
					if (carte.getOrigine().equals("Jour") || carte.getOrigine().equals("Néant")) {
						carte.setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null, "Joueur " + this.joueurEnCours.getNom()
									+ " vous a choisi pour annuler la capacité speciale de votre carte!\n" + carte);
						}
						break;
					}
				}
			}
		} else if (id == 69) {
			// annule capacite une carte action nuit ou neant
			int idDivin = this.boxChoisiJoueur(
					"Choissiez un joueur pour annuler la capacité speciale d'un des Carte ayant origine Nuit ou Jour: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
				Iterator it = joueur.getLaMain().getListeCroyantGuidee().get(0).iterator();
				while (it.hasNext()) {
					CarteAction carte = (CarteAction) it.next();
					if (carte.getOrigine().equals("Nuit") || carte.getOrigine().equals("Jour")) {
						carte.setEstSacrifie(false);
						if (!joueur.estBot()) {
							JOptionPane.showMessageDialog(null, "Joueur " + this.joueurEnCours.getNom()
									+ " vous a choisi pour annuler la capacité speciale de votre carte!\n" + carte);
						}
						break;
					}
				}
			}
		} else if (id == 70 || id == 71) {
			// annule capacite une carte action nuit ou neant
			int idDivin = this.boxChoisiJoueur(
					"Choissiez un joueur pour annuler la capacité speciale d'un des Carte ayant origine Nuit ou Néant: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			if (joueur.getLaMain().getListeGuideSpirituelGuider().size() > 0) {
				joueur.getLaMain().getListeCroyantGuidee().get(0).get(0).setEstSacrifie(false);
				if (!joueur.estBot()) {
					JOptionPane.showMessageDialog(null,
							"Joueur " + this.joueurEnCours.getNom()
									+ " vous a choisi pour annuler la capacité speciale de votre carte!\n"
									+ joueur.getLaMain().getListeCroyantGuidee().get(0).get(0));
				}
			}
		}
		// id 72 ~ 66
		else if (id == 73) {

		} else if (id == 74) {
			// lancer le de
			this.partie.lancerDe("");
			this.partie.setFiniTour(true);
		} else if (id == 75) {
			this.partie.lancerDe("");
		} // 76->80 Apocalypse
		else if (id == 81) {
			this.partie.setEstApocalypseAvant(0);
		} else if (id == 82) {
			int idDivin = this.boxChoisiJoueur("Choissiez un joueur pour lui empecher de sacrifier: ");
			Joueur joueur = partie.getListeJoueurs().get(idDivin - 1);
			joueur.setSacrifice(false);
		} else if (id == 83) {
			// détruire Croyant Néant
			Iterator it = partie.getEspaceCommun().getListeCartesPret().iterator();
			while (it.hasNext()) {
				CarteAction carte = (CarteAction) it.next();
				if (carte.getOrigine() == "Néant") {
					this.partie.getJeuDeCartes().recupererCarteAction(carte);
					this.partie.getEspaceCommun().supprimerCarte(carte);
					;
				}
			}
		} else if (id == 84 || id == 85) {
			int idDivin = this.boxChoisiJoueur(
					"Choissiez un joueur pour lui obliger à poser une carte Apocalypse s'il en possède une: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);

			Iterator<CarteAction> it = this.joueurEnCours.getLaMain().getListeCarteA().iterator();
			while (it.hasNext()) {
				if (it.next().getId() <= 80 && it.next().getId() >= 76) {
					Apocalypse carte = (Apocalypse) it.next();
					carte.effectuerCapaciteSpecial(this.partie);
					joueur.getLaMain().seDeffausserCarte(carte);
					break;
				}
			}
		} else if (id == 86) {
			// détruire Croyant Jour
			Iterator it = partie.getEspaceCommun().getListeCartesPret().iterator();
			while (it.hasNext()) {
				CarteAction carte = (CarteAction) it.next();
				if (carte.getOrigine() == "Jour") {
					this.partie.getJeuDeCartes().recupererCarteAction(carte);
					this.partie.getEspaceCommun().supprimerCarte(carte);
					;
				}
			}

		} else if (id == 87) {
			int ptActionNeantajouer = this.joueurEnCours.getLaMain().getListeGuideSpirituelGuider().size();
			this.joueurEnCours.setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant() + ptActionNeantajouer);

		} else if (id == 88) {
			JOptionPane.showMessageDialog(null,
					"Choissiez un Guide Spirituel ayant le Dogme Symboles ou Nature pour sacrifier!");
		} else if (id == 89) {
			int idDivin = this.boxChoisiJoueur("Choissiez un joueur pour récupérer ses point d'action: ");
			Joueur joueur = this.partie.getListeJoueurs().get(idDivin - 1);
			this.joueurEnCours.setPtAction_Jour(joueur.getPtAction_Jour() + this.joueurEnCours.getPtAction_Jour());
			this.joueurEnCours.setPtAction_Nuit(joueur.getPtAction_Nuit() + this.joueurEnCours.getPtAction_Nuit());
			this.joueurEnCours.setPtAction_Neant(joueur.getPtAction_Neant() + this.joueurEnCours.getPtAction_Neant());
			joueur.setPtAction_Jour(0);
			joueur.setPtAction_Nuit(0);
			joueur.setPtAction_Neant(0);
			joueur.setEstSetPointAction(false);
		} else if (id == 90) {

		}

	}
}
