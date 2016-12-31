package com.sdz.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;

public class Partie extends Observable implements Runnable {
	private Thread thread;
	private int nbJoueur;
	private Joueur Joueurgagnant;
	private Joueur joueurEncours;
	private ArrayList<Joueur> listeJoueurs;
	private JeuDeCartes jeuDeCartes;
	private Boolean estFini;
	private EspaceCommun espaceCommun;
	private String niveau;
	private Boolean finiTour;
	private int estApocalypseAvant;// cette attribute pour valider si le
									// carteApocalypse peut-�tre jou�
	private String faceDe;

	public Partie(ArrayList<Joueur> listeJoueurs, String niveau) {
		this.listeJoueurs = listeJoueurs;
		this.niveau = niveau;
		this.espaceCommun = new EspaceCommun();
		this.jeuDeCartes = new JeuDeCartes();
		this.estFini = false;
		this.finiTour = false;
		this.estApocalypseAvant = -1;
		this.faceDe = "";
		this.nbJoueur = this.listeJoueurs.size();
		this.commencerPartie();
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
				joueur.completerCarteAction(carte);
			}
		}
	}

	@Override
	public void run() {
		this.jouer();
	}

	public void commencer(){
		this.thread=new Thread(this);
		thread.start();
	}
	
	public void lancerDe() {
		String[] de = { "", "Jour", "Nuit", "N�ant" };
		int num = (int) Math.ceil(3 * Math.random());
		faceDe = de[num];
		System.out.println("******Face du d�: " + faceDe);
		if (faceDe.equals("Jour")) {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Jour")) {
					joueur.setPtAction_Jour(joueur.getPtAction_Jour() + 2);
				} else if (joueur.laMain.getCarteDivinite().getOrigine().equals("Aube")) {
					joueur.setPtAction_Jour(joueur.getPtAction_Jour() + 1);

				}
			}
		} else if (faceDe.equals("Nuit")) {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Nuit")) {
					joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 2);
				} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Cr�puscule")) {
					joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 1);
				}
			}
		} else if (faceDe.equals("N�ant")) {
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

	// D�scrire les actions des joueurs dans chaque tour
	// numCom: num�ro du joueur dans listJoueurs qui commence ce tour
	private void tourDeJeu(int numCom) {
		System.out.println(
				"\t----------------------------------------------NOUVELLE TOUR----------------------------------------------");
		this.finiTour = false;
		this.estApocalypseAvant++;
		// set les carte coyants guid�es peuvent �tre sacrifi�
		// les joueurs peuvent gargner le point d'Action
		for (Joueur j : this.listeJoueurs) {
			j.getLaMain().setTrueSacifice();
			j.setEstSetPointAction(true);
			j.setSacrifice(true);
		}
		// les cartes croyants pos�es dans le dernier tour va �tre pr�t � �tre
		// guid�e
		this.espaceCommun.preterCartes();
		Iterator<Joueur> it = this.listeJoueurs.iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			j.setEstSetPointAction(true);
			j.setSacrifice(true);
		}
		this.joueurEncours = this.listeJoueurs.get(numCom);
		if (!joueurEncours.bot) {
			System.out.println("\nLe tour de :" + joueurEncours);
			JOptionPane.showMessageDialog(null, "A vous de jouer !\nAu d�but, lancez le d� pour commencer!");
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
			if (!this.finiTour) {
				this.joueurEncours = this.listeJoueurs.get(i);
				System.out.println("\nLe tour de :" + joueurEncours);
				this.listeJoueurs.get(i).jouer(this);
			}
		}
		for (int i = 0; i < numCom; i++) {
			if (!this.finiTour) {
				this.joueurEncours = this.listeJoueurs.get(i);
				System.out.println("\nLe tour de :" + joueurEncours);
				this.listeJoueurs.get(i).jouer(this);
			}
		}
	}

	public void eliminerJoueur(Joueur joueur) {
		this.listeJoueurs.remove(joueur);
	}

	public void annoncerGagnant() {

	}

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

	public String getNiveau() {
		return this.niveau;
	}

	public void setJoueurEncours(Joueur joueurEncours) {
		this.joueurEncours = joueurEncours;
	}

	public Boolean getFiniTour() {
		return finiTour;
	}

	public void setFiniTour(Boolean finiTour) {
		this.finiTour = finiTour;
	}

	public String getFaceDe() {
		return faceDe;
	}

}
