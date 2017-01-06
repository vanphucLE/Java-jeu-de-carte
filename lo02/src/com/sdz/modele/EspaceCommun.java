package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

import com.sdz.cartes.CarteAction;

public class EspaceCommun extends Observable {
	private LinkedList<CarteAction> listeCartesPret;
	private LinkedList<CarteAction> listeCartesPoseRecent;

	public EspaceCommun() {
		super();
		listeCartesPret = new LinkedList<CarteAction>();
		listeCartesPoseRecent = new LinkedList<CarteAction>();
	}

	// Un fois que la carte croyant est posé, elle ne peut pas être guidée
	public void ajouterCarte(CarteAction carte) {
		this.listeCartesPoseRecent.add(carte);
	}

	public void preterCartes() {
		if (this.listeCartesPoseRecent.size() > 0) {
			Iterator<CarteAction> it = this.listeCartesPoseRecent.iterator();
			CarteAction carte;
			while (it.hasNext()) {
				carte =  it.next();
				this.listeCartesPret.add(carte);
			}
			this.listeCartesPoseRecent=new LinkedList<CarteAction>();
			this.setChanged();
			this.notifyObservers();
		}
	}

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

	public LinkedList<CarteAction> getListeCartesPret() {
		return this.listeCartesPret;
	}

	public LinkedList<CarteAction> getlisteCartesPoseRecent() {
		return this.listeCartesPoseRecent;
	}

}
