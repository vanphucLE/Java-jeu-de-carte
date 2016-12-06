package com.sdz.modele;

import java.util.ArrayList;
import java.util.Scanner;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;

public class Partie {
	private int nbJoueur;
	private Joueur Joueurgagnant;
	private Joueur joueurEncours;
	private ArrayList<Joueur> listeJoueurs = new ArrayList();
	private JeuDeCartes jeuDeCartes = new JeuDeCartes();
	private Boolean estFini = false;
	private EspaceCommun espaceCommun = new EspaceCommun();
	// cette attribute pour valider si le carteApocalypse peut-être joué
	private int estApocalypseAvant=0;

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public int getNbJoueur() {
		return this.nbJoueur;
	}

	public void lancerDe() {
		String[] de = { "", "Jour", "Nuit", "Néant" };
		int num = (int) Math.ceil(3 * Math.random());
		String FaceDe = de[num];
		if (FaceDe == "Jour") {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Jour") {
					joueur.setPtAction(2);
					joueur.setPtActionOrigine("Jour");
				}
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Aube") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Jour");
				}
			}
		} else if (FaceDe == "Nuit") {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Nuit") {
					joueur.setPtAction(2);
					joueur.setPtActionOrigine("Nuit");
				}
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Crépuscule") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Nuit");
				}
			}
		} else if (FaceDe == "Néant") {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Aube") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Néant");
				}
				if (joueur.laMain.getCarteDivinite().getOrigine() == "Crépuscule") {
					joueur.setPtAction(1);
					joueur.setPtActionOrigine("Néant");
				}
			}
		}
	}

	// un partie va commencer par appeller cette méthode
	public void jouer() {
		this.commencerPartie();
		while (!this.estFini) {

		}

	}

	// Cette méthode est crée pour distribuer les cartes au début de la partie
	// de jeu de carte
	private void commencerPartie() {
		for (Joueur joueur : this.listeJoueurs) {

			// Prendre une carte Divinité
			CarteDivinite carteDivinite = jeuDeCartes.piocherCarteDivinite();
			joueur.getLaMain().piocherDivinite(carteDivinite);

			// 7 cartes est distribuées au joueur
			int compte = 0;
			while (compte < 7) {
				compte++;
				CarteAction carte = jeuDeCartes.distribuerCarteAction();
				joueur.getLaMain().completerCarteAction(carte);
			}
		}
	}

	// Déscrire les actions des joueurs dans chaque tour
	// numCom: numéro du joueur dans listJoueurs qui commence ce tour
	private void TourDeJeu(int numCom) {
		this.joueurEncours = this.listeJoueurs.get(numCom);
		System.out.println("Le tour de :" + joueurEncours);
		Scanner sc = new Scanner(System.in);
		String str = "";
		do {
			System.out.print("Entrez 'Lancer' pour lancer le dé! ");
			str = sc.nextLine();
		} while (str.equals("Lancer"));
		// lancer le dé
		this.lancerDe();
		this.listeJoueurs.get(numCom).jouer(this);
		for (int i = numCom + 1; i < this.listeJoueurs.size(); i++) {
			this.listeJoueurs.get(i).jouer(this);
		}
		for (int i = 0; i < numCom; i++) {
			this.listeJoueurs.get(i).jouer(this);
		}
	}

	public void eliminerJoueur(Joueur joueur){
		this.listeJoueurs.remove(joueur);
	}
	public void annoncerGagnant() {

	}
	/*
	 * public void jouerCarte(CarteAction carte) { switch (carte.getType()) {
	 * case "Croyant": jouerCroyant(); break; case "DeusEx": jouerCroyant();
	 * break; case "Apocalypse": jouerCroyant(); break; case "GuideSpirituel":
	 * jouerCroyant(); break; } }
	 */
	// s

	public ArrayList<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public JeuDeCartes getJeuDeCartes() {
		return jeuDeCartes;
	}

	public EspaceCommun getEspaceCommun() {
		return espaceCommun;
	}

	public int getEstApocalypseAvant() {
		return estApocalypseAvant;
	}

	public void setEstApocalypseAvant(int estApocalypseAvant) {
		this.estApocalypseAvant = estApocalypseAvant;
	}

	public Joueur getJoueurgagnant() {
		return Joueurgagnant;
	}

	public void setJoueurgagnant(Joueur joueurgagnant) {
		Joueurgagnant = joueurgagnant;
	}

	public Boolean getEstFini() {
		return estFini;
	}

	public void setEstFini(Boolean estFini) {
		this.estFini = estFini;
	}
	

}
