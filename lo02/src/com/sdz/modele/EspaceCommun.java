package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.Croyant;

public class EspaceCommun {
	private LinkedList<Croyant> listeCartes=new LinkedList<Croyant>();

	public void ajouterCarte(Croyant carte) {
		this.listeCartes.add(carte);
	}
	
	public Croyant supprimerCarte(int id) {
		Croyant carteReturn=new Croyant();
		Iterator<Croyant> it = this.listeCartes.iterator();
		int position = 0;
		while (it.hasNext()) {
			Croyant carteA = (Croyant) it.next();
			if (carteA.getId()==id) {
				carteReturn=this.listeCartes.remove(position);
				break;
			}
			position++;
		}
		return carteReturn;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer("");
		
		return sb.toString();
	}
	
}
