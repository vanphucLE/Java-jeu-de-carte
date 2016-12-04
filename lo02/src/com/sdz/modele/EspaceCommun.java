package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;

public class EspaceCommun {
	private LinkedList<Croyant> listeCartes=new LinkedList<Croyant>();

	public void ajouterCarte(Croyant carte) {
		this.listeCartes.add(carte);
	}

	public void supprimerCarte(int id) {
		Iterator<Croyant> it = this.listeCartes.iterator();
		int position = 0;
		while (it.hasNext()) {
			position++;
			Croyant carteA = (Croyant) it.next();
			if (carteA.getId()==id) {
				this.listeCartes.remove(position);
				break;
			}
		}
	}
	
}
