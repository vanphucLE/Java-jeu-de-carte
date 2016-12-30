package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

import com.sdz.cartes.CarteAction;

public class EspaceCommun extends Observable{
	private LinkedList<CarteAction> listeCartesPret=new LinkedList<CarteAction>();
	private LinkedList<CarteAction> listeCartesPoseRecent =new LinkedList<CarteAction>();
	
	public EspaceCommun(){
		
	}
	// Un fois que la carte croyant est posé, elle ne peut pas être guidée
	public void ajouterCarte(CarteAction carte) {
		this.listeCartesPoseRecent.add(carte);
	}
	public void preterCartes(){
		for (CarteAction carte: this.listeCartesPoseRecent){
			this.listeCartesPret.add(carte);
			this.listeCartesPoseRecent.remove(carte);
		}
	}
	public CarteAction supprimerCarte(int id) {
		CarteAction carteReturn=new CarteAction();
		Iterator<CarteAction> it = this.listeCartesPret.iterator();
		int position = 0;
		while (it.hasNext()) {
			CarteAction carteA = (CarteAction) it.next();
			if (carteA.getId()==id) {
				carteReturn=this.listeCartesPret.remove(position);
				break;
			}
			position++;
		}
		return carteReturn;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer("Les cartes dans l'espace commun: \n");
		sb.append("Les cartes Croyants est posé récemment à guidé: \n");
		Iterator<CarteAction> it = this.listeCartesPoseRecent.iterator();
		while (it.hasNext()) {
			CarteAction carte = (CarteAction) it.next();
			sb.append(" ."+carte );
		}
		sb.append("Les cartes Croyants est prêt à guidé: \n");
		Iterator<CarteAction> it2 = this.listeCartesPret.iterator();
		while (it2.hasNext()) {
			CarteAction carte = (CarteAction) it2.next();
			sb.append(" ."+carte );
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
