package com.sdz.modele;

import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.GuideSpirituel;
import com.sdz.vue.PanelJeu;

public class JoueurVirtuel extends Joueur {
	private Stategie stategie;
	private EffectuerCapacite effectuerCapacite;

	// private Stategie stagie;
	public JoueurVirtuel(int id, String nom, int age, Stategie stategie) {
		super(id, nom, age);
		this.bot = true;
		this.stategie = stategie;
		effectuerCapacite = new EffectuerCapacite(this.partie);
	}

	public void setPanelJeu(PanelJeu panelJeu) {
		this.panelJeu = panelJeu;
	}

	@Override
	public void jouer(Partie partie) {
		System.out.println("Point Action: (Jour: " + this.ptAction_Jour + ") " + "(Nuit: " + this.ptAction_Nuit + ") "
				+ "(Néant: " + this.ptAction_Neant + ")");
		this.seDefausserCartesEtCompleter(partie);
		this.choisirCarte(partie);
		int rd;
		do {
			rd = (int) Math.ceil(Math.random() * 2);
		} while (rd == 0);
		if (rd == 2 && this.sacrifice && this.laMain.getListeCroyantGuidee().size() != 0) {
			this.sacrifierCroyant(this.laMain.getListeCroyantGuidee().get(0).get(0).getId(), partie);
		}
	}

	@Override
	public void seDefausserCartesEtCompleter(Partie partie) {
		JeuDeCartes jeuDeCartes = partie.getJeuDeCartes();
		// Choisir au hasard le nombre de carte défaussée.
		System.out.println("Les cartes actions tenu dans sa main:");
		System.out.println(this.laMain);

		LinkedList<Integer> ids = new LinkedList<Integer>();
		LinkedList<CarteAction> cartesRecupere = stategie.choisirCartesDefausser(partie);
		for (CarteAction carteA : cartesRecupere) {
			ids.add(carteA.getId());
		}
		System.out.println("Il a défaussé les cartes qui ont les Id en : " + ids);
		// jeuDeCartes recupére les cartes action après le joueur compléte 7
		// cartes.
		this.compeleter7Carte(jeuDeCartes);
		for (CarteAction carte : cartesRecupere) {
			jeuDeCartes.recupererCarteAction(carte);
		}
	}

	@Override
	public void choisirCarte(Partie partie) {
		CarteAction carteChoisi = this.stategie.choisirCarteJouer(this, partie);
		if (carteChoisi.getId() != 0) {
			System.out.println(this.nom + " a joué la carte: " + carteChoisi);
			this.panelJeu.dessinerPanelCarteJouee(carteChoisi);
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
				this.jouerApocalypse(carteChoisi);
				break;
			}
		} else {
			this.panelJeu.supprimmerCarteJouee();
		}
	}

	private void jouerGuideSpirituel(CarteAction carte, EspaceCommun espaceCommun) {
		GuideSpirituel carteG = (GuideSpirituel) carte;
		carteG.setEstSacrifie(true);
		LinkedList<CarteAction> listeCroyantsGuidee = new LinkedList<CarteAction>();
		int indice = 0;
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
			if (test == true && indice < carteG.getNbGuider()) {
				indice++;
				carteA.setEstSacrifie(true);
				listeCroyantsGuidee.add(carteA);
				espaceCommun.supprimerCarte(carteA);
			}
		}
		this.laMain.ajouterGuidee(listeCroyantsGuidee, carte);
	}

	private void jouerDeusEx(Partie partie) {

	}

	@Override
	public void jouerApocalypse(CarteAction carte) {
		this.jouerApocalypse();
	}

	public void jouerApocalypse() {
		JOptionPane.showMessageDialog(null, this.nom + "a joué la carte Apocalypse!");
		partie.setEstApocalypseAvant(-1);
		int[] arPriere = new int[partie.getListeJoueurs().size() + 1];
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
						JOptionPane.showMessageDialog(null, j.getNom() + " a gagné!");
						break;
					}
				}
			}
		}
	}

	@Override
	public void sacrifierCarte(CarteAction carte) {

		// changer choisir carte
		if (this.laMain.getListeCroyantGuidee().size() > 0) {
			this.sacrifierCroyant(this.laMain.getListeCroyantGuidee().get(0).get(0).getId(), this.partie);
		}
	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}

	public Stategie getStategie() {
		return stategie;
	}

	public EffectuerCapacite getEffectuerCapacite() {
		return effectuerCapacite;
	}

}
