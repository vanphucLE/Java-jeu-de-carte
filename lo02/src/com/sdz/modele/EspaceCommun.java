package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

public class EspaceCommun {
	private LinkedList<CarteAction> listeCartes=new LinkedList<CarteAction>();
	private LinkedList<Boolean> pretGuidee = new LinkedList<Boolean>(); 
	
	public EspaceCommun(){
		
	}
	public void ajouterCarte(CarteAction carte) {
		this.listeCartes.add(carte);
		this.pretGuidee.add(false);
	}
	
	public CarteAction supprimerCarte(int id) {
		CarteAction carteReturn=new CarteAction();
		Iterator<CarteAction> it = this.listeCartes.iterator();
		int position = 0;
		while (it.hasNext()) {
			CarteAction carteA = (CarteAction) it.next();
			if (carteA.getId()==id) {
				if (this.pretGuidee.get(position)==true){
					carteReturn=this.listeCartes.remove(position);
				}else{
					System.out.println("Cette carte n'est pas prêt à être guidée");
				}
				break;
			}
			position++;
		}
		return carteReturn;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer("Les cartes dans l'espace commun: ");
		Iterator<CarteAction> it = this.listeCartes.iterator();
		int indice=0;
		while (it.hasNext()) {
			CarteAction carte = (CarteAction) it.next();
			sb.append(" ."+carte );
			sb.append(" ----- est prêt à être guidé: "+ this.pretGuidee.get(indice));
			indice++;
		}
		return sb.toString();
	}
	public LinkedList<CarteAction> getListeCartes() {
		return listeCartes;
	}
	
	
}
