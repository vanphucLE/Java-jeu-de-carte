package com.sdz.modele;

import java.util.ArrayList;
import java.util.Iterator;
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
	private String difficulte;
	// cette attribute pour valider si le carteApocalypse peut-�tre jou�
	private int estApocalypseAvant = -1;

	public Partie(ArrayList<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	public void lancerDe() {
		String[] de = { "", "Jour", "Nuit", "N�ant" };
		int num = (int) Math.ceil(3 * Math.random());
		String FaceDe = de[num];
		System.out.println("******Face du d�: " + FaceDe);
		if (FaceDe.equals("Jour")) {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Jour")) {
					joueur.setPtAction_Jour(joueur.getPtAction_Jour() + 2);
				} else if (joueur.laMain.getCarteDivinite().getOrigine().equals("Aube")) {
					joueur.setPtAction_Jour(joueur.getPtAction_Jour() + 1);

				}
			}
		} else if (FaceDe.equals("Nuit")) {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Nuit")) {
					joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 2);
				} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Cr�puscule")) {
					joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 1);
				}
			}
		} else if (FaceDe.equals("N�ant")) {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Aube")) {
					joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
				} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Cr�puscule")) {
					joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
				}
			}
		}
	}

	// un partie va commencer par appeller cette m�thode
	public void jouer() {
		int numCom = -1;
		this.commencerPartie();
		while (!this.estFini) {
			if (numCom < this.getListeJoueurs().size() - 1) {
				numCom++;
			} else {
				numCom = 0;
			}
			this.tourDeJeu(numCom);
		}
		this.tourDeJeu(0);

	}

	// Cette m�thode est cr�e pour distribuer les cartes au d�but de la partie
	// de jeu de carte
	private void commencerPartie() {
		for (Joueur joueur : this.listeJoueurs) {
			// Prendre une carte Divinit�
			CarteDivinite carteDivinite = jeuDeCartes.piocherCarteDivinite();
			joueur.getLaMain().piocherDivinite(carteDivinite);

			// 7 cartes est distribu�es au joueur
			int compte = 0;
			while (compte < 7) {
				compte++;
				CarteAction carte = jeuDeCartes.distribuerCarteAction();
				joueur.getLaMain().completerCarteAction(carte);
			}
		}
	}

	// D�scrire les actions des joueurs dans chaque tour
	// numCom: num�ro du joueur dans listJoueurs qui commence ce tour
	private void tourDeJeu(int numCom) {
		System.out.println(
				"\t----------------------------------------------NOUVELLE TOUR----------------------------------------------");
		this.estApocalypseAvant++;
		// set les carte coyants guid�es peuvent �tre sacrif�e
		for (Joueur j : this.listeJoueurs) {
			j.getLaMain().setTrueSacifice();
		}
		// les cartes croyants pos�es dans le dernier tour va �tre pr�t � �tre
		// guid�e
		this.espaceCommun.preterCartes();
		Iterator<Joueur> it = this.listeJoueurs.iterator();
		while (it.hasNext()) {
			it.next().setEstSetPointAction(true);
			it.next().setSacrifice(true);
		}
		this.joueurEncours = this.listeJoueurs.get(numCom);
		if (!joueurEncours.bot) {
			System.out.println("\nLe tour de :" + joueurEncours);
			Scanner sc = new Scanner(System.in);
			String str = "";
			do {
				System.out.print("Entrez 'L' pour lancer le d�! ");
				str = sc.nextLine();
			} while (!str.equals("L"));
			// lancer le d�
			this.lancerDe();
			this.joueurEncours.jouer(this);
		} else {
			System.out.print(this.joueurEncours.getNom() + " a lanc� le d�! ");
			this.lancerDe();
			this.joueurEncours.jouer(this);
		}

		for (int i = numCom + 1; i < this.listeJoueurs.size(); i++) {
			this.joueurEncours = this.listeJoueurs.get(i);
			System.out.println("\nLe tour de :" + joueurEncours);
			this.listeJoueurs.get(i).jouer(this);
		}
		for (int i = 0; i < numCom; i++) {
			this.joueurEncours = this.listeJoueurs.get(i);
			System.out.println("\nLe tour de :" + joueurEncours);
			this.listeJoueurs.get(i).jouer(this);
		}
	}

	public void eliminerJoueur(Joueur joueur) {
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

	public void setNbJoueur(int nbJoueur) {
		this.nbJoueur = nbJoueur;
	}

	public int getNbJoueur() {
		return this.nbJoueur;
	}

	public Joueur getJoueurEncours() {
		return joueurEncours;
	}

}
