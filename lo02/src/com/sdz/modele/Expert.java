package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
/*
 * Mettre en place strategie Debutant
 * @author LE Van
 */
public class Expert implements Stategie {
	/*
	 * choisir une carte pour joueur
	 * @param partie une Variable de Partie
	 * @param jv une variable de JoueurVirtuel
	 * @return une carte Action
	 */
	public CarteAction choisirCarteJouer(JoueurVirtuel jV, Partie partie) {
		CarteAction carteChoisi = new CarteAction();
		for (CarteAction carteA : jV.laMain.getListeCarteA()) {
			carteChoisi = this.carteEtPoint(carteA, jV, partie);
			if (carteChoisi.equals(carteA)) {
				break;
			}
		}
		return carteChoisi;
	}
	/*
	 * choisir la carte pour jouer et mettre a jour les point action
	 * @param partie une Variable de Partie
	 * @param jv une variable de JoueurVirtuel
	 * @param carteA une variable CarteAction
	 * @return une carte action pour jouer
	 */
	private CarteAction carteEtPoint(CarteAction carteA, JoueurVirtuel jV, Partie partie) {
		CarteAction carteChoisi = new CarteAction();
		if (carteA.getOrigine().equals("Jour") && this.testEntree(carteA, partie)) {
			if (jV.getPtAction_Jour() > 0) {
				carteChoisi = carteA;
				jV.setPtAction_Jour(jV.getPtAction_Jour() - 1);
			}
		} else if (carteA.getOrigine().equals("Nuit") && this.testEntree(carteA, partie)) {
			if (jV.getPtAction_Nuit() > 0) {
				carteChoisi = carteA;
				jV.setPtAction_Nuit(jV.getPtAction_Nuit() - 1);
			}
		} else if (carteA.getOrigine().equals("Néant") && this.testEntree(carteA, partie)) {
			if (jV.getPtAction_Neant() > 0) {
				carteChoisi = carteA;
				jV.setPtAction_Neant(jV.getPtAction_Neant() - 1);
			} else if (jV.getPtAction_Nuit() >= 2) {
				carteChoisi = carteA;
				jV.setPtAction_Nuit(jV.getPtAction_Nuit() - 2);
			} else if (jV.getPtAction_Jour() >= 2) {
				carteChoisi = carteA;
				jV.setPtAction_Jour(jV.getPtAction_Jour() - 2);
			}
		}
		return carteChoisi;
	}

	private Boolean testEntree(CarteAction carte, Partie partie) {
		Boolean test = true;
		if (carte.getType().equals("GuideSpirituel")) {
			test = false;
			if (carte.getType().equals("GuideSpitituel")) {
				for (CarteAction carteA : partie.getEspaceCommun().getListeCartesPret()) {
					for (String dogmeA : carteA.getDogme()) {
						for (String dogmeD : carte.getDogme()) {
							if (dogmeD.equals(dogmeA)) {
								test = true;
								break;
							}
						}
					}
				}
			}
		}
		if (carte.getType().equals("Apocalypse")) {
			if (partie.getEstApocalypseAvant() == 0 || partie.getEstApocalypseAvant() == -1) {
				test = false;
			}
		}
		return test;
	}

	public void jouerCapaciteSpecial() {

	};

	@Override
	/*
	 * strategie pour choisir les carte pour defausser
	 * @param partie une variable de Partie
	 * @return reprendre une Liste des Carte Action
	 */
	public LinkedList<CarteAction> choisirCartesDefausser(Partie partie) {
		Joueur joueurEnCours = partie.getJoueurEncours();
		LinkedList<CarteAction> cartesRecupere = new LinkedList<CarteAction>();
		int nbDe = (int) Math.ceil(Math.random() * 6);
		for (int i = nbDe; i >= 1; i--) {
			// Choisir au hasard les carte défaussée.
			int nbCa = (int) Math.ceil(Math.random() * (joueurEnCours.getLaMain().getListeCarteA().size() - 1));
			CarteAction carte = joueurEnCours.getLaMain().getListeCarteA().get(nbCa);
			joueurEnCours.getLaMain().seDeffausserCarte(carte);
			cartesRecupere.add(carte);
		}
		return cartesRecupere;
	}

	public int choisirIdDivinite(Partie partie) {
		int idChoisi = 0;
		do {
			idChoisi = (int) Math.ceil(Math.random() * partie.getListeJoueurs().size());
		} while (idChoisi == partie.getJoueurEncours().getId() || idChoisi == 0);
		return idChoisi;
	}
}
