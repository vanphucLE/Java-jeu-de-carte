package com.sdz.modele;

import java.util.LinkedList;
import java.util.Observable;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;
import com.sdz.vue.PanelJeu;
/*
 * definir un Joueur general
 * @author LE Van Phuc
 */
public abstract class Joueur extends Observable {
	protected PanelJeu panelJeu;
	protected Partie partie;
	protected int id;
	protected String nom;
	protected int age;
	protected int ptPriere;
	protected int ptAction_Jour;
	protected int ptAction_Nuit;
	protected int ptAction_Neant;
	protected Boolean elimine;
	protected int idCarteDivinite;
	protected LaMain laMain;
	protected boolean sacrifice = true;
	protected boolean bot;
	// chua xu li bien setpointAction
	protected boolean estSetPointAction = true;
	protected String actionEnTrain;

	public Joueur(int id, String nom, int age) {
		this.id = id;
		this.nom = nom;
		this.age = age;
		this.actionEnTrain = "";
		this.laMain = new LaMain(this);
		this.ptPriere = 0;
		this.elimine=false;
	}
	/*
	 * completer les carte en main
	 * @param carte une variable de CarteAction
	 */
	public void completerCarteAction(CarteAction carte) {
		this.laMain.completerCarteAction(carte);
		if (!this.estBot()) {
			this.setChanged();
			this.notifyObservers();
		}
	}
	/*
	 * verifier si le jouerr est bot
	 * @return une variable boolean
	 */
	public boolean estBot() {
		return this.bot;
	}
	/*
	 * verifier si le joueur est capable recevoir la point d action
	 * @return une variable boolean
	 */
	public boolean getEstSetPointAction() {
		return estSetPointAction;
	}
	
	public void setEstSetPointAction(boolean estSetPointAction) {
		this.estSetPointAction = estSetPointAction;
	}
	/*
	 * prendre le id de la carte divinite 
	 * @return une variable int
	 */
	public int getidCarteDivinite() {
		return this.idCarteDivinite;
	}
	/*
	 * verifier si le jouer peut sacrifier la carte
	 */
	public void setSacrifice(boolean sacrifice) {
		this.sacrifice = sacrifice;
	}
	/*
	 * prendre le nom de ce joueur
	 * @return une variable string
	 */
	public String getNom() {
		return this.nom;
	}
	/*
	 * prendre le ID de ce joueur
	 * @return une variable int
	 */
	public int getId() {
		return this.id;
	}
	/*
	 * mettre a jour point action Jour
	 * @param ptAction une variable int
	 */
	public void setPtAction_Jour(int ptAction) {
		if (this.estSetPointAction) {
			this.ptAction_Jour = ptAction;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getPtAction_Jour() {
		return this.ptAction_Jour;
	}
	/*
	 * mettre a jour point action Nuit
	 * @param ptAction une variable int
	 */
	public void setPtAction_Nuit(int ptAction) {
		if (this.estSetPointAction) {
			this.ptAction_Nuit = ptAction;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getPtAction_Nuit() {
		return this.ptAction_Nuit;
	}
	/*
	 * mettre a jour point action Neant
	 * @param ptAction une variable int
	 */
	public void setPtAction_Neant(int ptAction) {
		if (this.estSetPointAction) {
			this.ptAction_Neant = ptAction;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public int getPtAction_Neant() {
		return this.ptAction_Neant;
	}
	/*
	 * set point Prieres
	 */
	public void setPtPriere() {
		int sumPtPriere = 0;
		for (LinkedList<CarteAction> listeCarte : laMain.getListeCroyantGuidee()) {
			for (CarteAction carte : listeCarte) {
				Croyant c = (Croyant) carte;
				sumPtPriere += c.getNbCroyant();
			}
		}
		this.ptPriere = sumPtPriere;
		this.setChanged();
		this.notifyObservers();
	}

	public int getPtPriere() {
		return this.ptPriere;
	}

	public Boolean getElimine() {
		return elimine;
	}

	public void setElimine(Boolean elimine) {
		this.elimine = elimine;
	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}

	public void jouer(Partie partie) {
	}

	// Un joueur veut défausser un plusieur Cartes
	public abstract void seDefausserCartesEtCompleter(Partie partie);

	// un fois que les cartes dans la main sont changées, la main va appeler
	// cette méthode pour notify observateur
	public void notifyLaMain() {
		this.setChanged();
		this.notifyObservers();
	}

	// Le joueur compléte 7 cartes dans la main
	public void compeleter7Carte(JeuDeCartes jeuDeCartes) {
		int compte = this.laMain.getListeCarteA().size();
		while (compte < 7) {
			compte++;
			CarteAction carte = jeuDeCartes.distribuerCarteAction();
			this.getLaMain().completerCarteAction(carte);
		}
		System.out.println("Vous avez complété 7 Cartes. Maintenant, les cartes sont: ");
		System.out.println(this.laMain);
		this.setChanged();
		this.notifyObservers();
	}

	// Choisir carte pour jouer
	public abstract void choisirCarte(Partie partie);

	public abstract void sacrifierCarte(CarteAction carte);

	public Boolean estEqual(Joueur j) {
		if (this.id == j.id) {
			return true;
		} else {
			return false;
		}
	}

	public void jouerCroyant(CarteAction carte, EspaceCommun espaceCommun) {
		System.out.println("Cette carte est transmis à l'espace commun!");
		this.laMain.seDeffausserCarte(carte);
		espaceCommun.ajouterCarte(carte);
	}

	public abstract void jouerApocalypse(CarteAction carte);
	public void joueurDeusEx(CarteAction carte){
		carte.effectuerCapaciteSpecial(partie);
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Joueur n." + this.id + " : ");
		sb.append(this.nom + "\t ");
		sb.append("(" + this.age + " ans) | ");
		sb.append("Point Prière: " + this.ptPriere + " | ");
		sb.append("Point Action : (Jour: " + this.ptAction_Jour + ") ");
		sb.append("(Nuit: " + this.ptAction_Nuit + ") ");
		sb.append("(Néant: " + this.ptAction_Neant + ")");
		return sb.toString();
	}

	public void sacrifierCroyant(int croyant, Partie partie) {
		CarteAction carteCroyant;
		if (this.sacrifice) {
			for (int i = 0; i < this.getLaMain().getListeCroyantGuidee().size(); i++) {
				Boolean test = false;
				for (int j = 0; j < this.getLaMain().getListeCroyantGuidee().get(i).size(); j++) {
					if (croyant == this.getLaMain().getListeCroyantGuidee().get(i).get(j).getId()) {
						carteCroyant = this.getLaMain().getListeCroyantGuidee().get(i).remove(j);
						partie.getJeuDeCartes().getListeCartesAction().add(carteCroyant);
						if (this.getLaMain().getListeCroyantGuidee().get(i).size() == 0) {
							partie.getJeuDeCartes().getListeCartesAction()
									.add(this.getLaMain().getListeGuideSpirituelGuider().remove(i));
						}
						this.setChanged();
						this.notifyObservers();
						carteCroyant.effectuerCapaciteSpecial(partie);
						test = true;
						break;
					}
				}
				if (test)
					break;
			}
		} else
			System.out.println("Vous ne pouvez pas sacrifier la carte");
	}

	public void sacrifierGuideSpirit(int guide, Partie partie) {
		CarteAction carteGuide;
		if (this.sacrifice) {
			for (int i = 0; i < this.getLaMain().getListeGuideSpirituelGuider().size(); i++) {
				if (guide == (this.getLaMain().getListeGuideSpirituelGuider().get(i).getId())) {
					carteGuide = this.getLaMain().getListeGuideSpirituelGuider().remove(i);
					partie.getJeuDeCartes().getListeCartesAction().add(carteGuide);
					partie.getEspaceCommun().getListeCartesPret()
							.addAll(this.getLaMain().getListeCroyantGuidee().remove(i));
					carteGuide.effectuerCapaciteSpecial(partie);
					this.setChanged();
					this.notifyObservers();
					break;
				}
			}
		}
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

}
