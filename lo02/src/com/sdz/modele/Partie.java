package com.sdz.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;

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
	private Boolean finiTour;
	private EspaceCommun espaceCommun;
	private String niveau;
	private int estApocalypseAvant;// cette attribute pour valider si le
									// carteApocalypse peut-être joué
	private boolean waitEntree;
	private String faceDe;
	private Joueur joueurDernier;

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
		this.waitEntree = true;
	}

	public void commencer() {
		for (Joueur j : this.listeJoueurs) {
			j.setPartie(this);
		}
		this.thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		this.commencerPartie();
		int numCom = -1;
		while (!this.estFini) {
			if (numCom < this.getListeJoueurs().size() - 1) {
				numCom++;
			} else {
				numCom = 0;
			}
			this.tourDeJeu(numCom);
		}
	}

	// Cette méthode est crée pour distribuer les cartes au début de la partie
	// de jeu de carte
	private void commencerPartie() {
		for (Joueur joueur : this.listeJoueurs) {
			// Prendre une carte Divinité
			CarteDivinite carteDivinite = jeuDeCartes.piocherCarteDivinite();
			joueur.getLaMain().piocherDivinite(carteDivinite);
			joueur.compeleter7Carte(jeuDeCartes);
		}
	}

	// Déscrire les actions des joueurs dans chaque tour
	// numCom: numéro du joueur dans listJoueurs qui commence ce tour
	private void tourDeJeu(int numCom) {
		System.out.println(
				"\t----------------------------------------------NOUVELLE TOUR----------------------------------------------");
		this.finiTour = false;
		this.estApocalypseAvant++;
		// set les carte coyants guidées peuvent être sacrifié
		// les joueurs peuvent gargner le point d'Action
		Iterator<Joueur> it = this.listeJoueurs.iterator();
		while (it.hasNext()) {
			Joueur j = it.next();
			j.getLaMain().setTrueSacifice();
			j.setEstSetPointAction(true);
			j.setSacrifice(true);
		}

		System.out.println(this.espaceCommun.getlisteCartesPoseRecent());
		this.faceDe = "3face";
		this.setChanged();
		this.notifyObservers();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.joueurEncours = this.listeJoueurs.get(numCom);
		if (!joueurEncours.bot) {
			System.out.println("\nLe tour de :" + joueurEncours);
			// Annoncer le joueur Physique
			JOptionPane.showMessageDialog(null, "A vous de jouer !\nAu début, lancez le dé pour commencer!");
			JoueurPhysique jP = (JoueurPhysique) this.joueurEncours;
			jP.setActionEnTrain("lancerDe");

			// les cartes croyants posées dans le dernier tour va être prêt à
			// être guidée
			this.espaceCommun.preterCartes();

			try {
				this.suspend();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.lancerDe("");
			this.joueurEncours.jouer(this);
		} else {
			JOptionPane.showMessageDialog(null, "Nouvelle tour!\n" + this.joueurEncours.getNom() + " a lancé le dé! ");
			System.out.print(this.joueurEncours.getNom() + " a lancé le dé! ");
			// les cartes croyants posées dans le dernier tour va être prêt à
			// être guidée
			this.espaceCommun.preterCartes();
			this.lancerDe("");
			this.joueurEncours.jouer(this);
		}

		for (int i = numCom + 1; i < this.listeJoueurs.size(); i++) {
			if (this.finiTour || this.estFini) {
				break;
			}

			this.joueurEncours = this.listeJoueurs.get(i);
			System.out.println("\nLe tour de :" + joueurEncours);
			this.listeJoueurs.get(i).jouer(this);

		}
		for (int i = 0; i < numCom; i++) {
			if (this.finiTour || this.estFini) {
				break;
			}
			this.joueurEncours = this.listeJoueurs.get(i);
			System.out.println("\nLe tour de :" + joueurEncours);
			this.listeJoueurs.get(i).jouer(this);
		}
	}

	/*
	 * Cette clase lancerDe a objctif pour ...
	 * 
	 * @param face
	 * 
	 * @author
	 */
	public void lancerDe(String face) {
		if (face.equals("Jour") || face.equals("Nuit") || face.equals("Néant")) {
			faceDe = face;
		} else {
			String[] de = { "", "Jour", "Nuit", "Néant", "", "", "", "" , "" };
			String origine = this.listeJoueurs.get(0).getLaMain().getCarteDivinite().getOrigine();
			if (origine.equals("Aube")) {
				origine = "Jour";
			} else if (origine.equals("Crépuscule")) {
				origine = "Nuit";
			}
			for (int i = 4; i <= 8; i++) {
				de[i] = origine;
			}
			int num = (int) Math.ceil(8 * Math.random());
			faceDe = de[num];
		}
		System.out.println("******Face du dé: " + faceDe);
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
				} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Crépuscule")) {
					joueur.setPtAction_Nuit(joueur.getPtAction_Nuit() + 1);
				}
			}
		} else if (faceDe.equals("Néant")) {
			for (Joueur joueur : this.listeJoueurs) {
				if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Aube")) {
					joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
				} else if (joueur.getLaMain().getCarteDivinite().getOrigine().equals("Crépuscule")) {
					joueur.setPtAction_Neant(joueur.getPtAction_Neant() + 1);
				}
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	public synchronized void suspend() throws InterruptedException {
		this.waitEntree = true;
		while (this.waitEntree) {
			this.wait();
		}
	}

	public synchronized void resume() {
		this.waitEntree = false;
		this.notifyAll();
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

	public Joueur getJoueurDernier() {
		return joueurDernier;
	}

	public void setJoueurDernier(Joueur joueurDernier) {
		this.joueurDernier = joueurDernier;
	}

}
