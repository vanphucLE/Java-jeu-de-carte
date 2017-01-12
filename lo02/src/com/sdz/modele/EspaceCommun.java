package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

import com.sdz.cartes.CarteAction;
/*
 * ce class creer une espace pour poser des cartes
 * @author LE Van Phuc
 */
public class EspaceCommun extends Observable {
	private LinkedList<CarteAction> listeCartesPret;
	private LinkedList<CarteAction> listeCartesPoseRecent;
	
	public EspaceCommun() {
		super();
		listeCartesPret = new LinkedList<CarteAction>();
		listeCartesPoseRecent = new LinkedList<CarteAction>();
	}
	/*
	 * ajouter carte croyant 
	 * @param carte une Variable de CarteAction
	 */
	// Un fois que la carte croyant est posé, elle ne peut pas être guidée
	public void ajouterCarte(CarteAction carte) {
		this.listeCartesPoseRecent.add(carte);
	}
	/*
	 * ajouter carte croyant dans liste prêt a etre guidee 
	 */
	public void preterCartes() {
		if (this.listeCartesPoseRecent.size() > 0) {
			Iterator<CarteAction> it = this.listeCartesPoseRecent.iterator();
			CarteAction carte;
			while (it.hasNext()) {
				carte = it.next();
				this.listeCartesPret.add(carte);
			}
			this.listeCartesPoseRecent = new LinkedList<CarteAction>();
			this.setChanged();
			this.notifyObservers();
		}
	}
	/*
	 * notifier les observers
	 */
	public void notifyEspaceCommun() {
		this.setChanged();
		this.notifyObservers();
	}
	/*
	 * supprimer une carte croyant depuis listeCartesPret 
	 * @param carte une Variable de CarteAction
	 */
	public void supprimerCarte(CarteAction carte) {
		this.listeCartesPret.remove(carte);
		this.setChanged();
		this.notifyObservers();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Les cartes dans l'espace commun: \n");
		sb.append("Les cartes Croyants est posé récemment à guidé: \n");
		Iterator<CarteAction> it = this.listeCartesPoseRecent.iterator();
		while (it.hasNext()) {
			CarteAction carte = (CarteAction) it.next();
			sb.append(" ." + carte);
		}
		sb.append("Les cartes Croyants est prêt à guidé: \n");
		Iterator<CarteAction> it2 = this.listeCartesPret.iterator();
		while (it2.hasNext()) {
			CarteAction carte = (CarteAction) it2.next();
			sb.append(" ." + carte);
		}
		return sb.toString();
	}
	/*
	 * prendre la variable listeCartesPret
	 * @return une valeur de LinkedList<CarteAction>
	 */
	public LinkedList<CarteAction> getListeCartesPret() {
		return this.listeCartesPret;
	}
	/*
	 * prendre la variable listeCartesPoseRecent
	 * @return une valeur de LinkedList<CarteAction>
	 */
	public LinkedList<CarteAction> getlisteCartesPoseRecent() {
		return this.listeCartesPoseRecent;
	}

}
