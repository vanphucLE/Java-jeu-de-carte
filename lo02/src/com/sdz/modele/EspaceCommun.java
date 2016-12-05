package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;

public class EspaceCommun {
	private LinkedList<Croyant> listeCartes=new LinkedList<Croyant>();
	private LinkedList<Boolean> pretGuidee = new LinkedList<Boolean>(); 
	
	public EspaceCommun(){
		
	}
	public void ajouterCarte(Croyant carte) {
		this.listeCartes.add(carte);
		this.pretGuidee.add(false);
	}
	
	public Croyant supprimerCarte(int id) {
		Croyant carteReturn=new Croyant();
		Iterator<Croyant> it = this.listeCartes.iterator();
		int position = 0;
		while (it.hasNext()) {
			Croyant carteA = (Croyant) it.next();
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
		Iterator<Croyant> it = this.listeCartes.iterator();
		int indice=0;
		while (it.hasNext()) {
			Croyant carte = (Croyant) it.next();
			sb.append(" ."+carte );
			sb.append(" ----- est prêt à être guidé: "+ this.pretGuidee.get(indice));
			indice++;
		}
		return sb.toString();
	}
	public LinkedList<Croyant> getListeCartes() {
		return listeCartes;
	}
	
	
}
