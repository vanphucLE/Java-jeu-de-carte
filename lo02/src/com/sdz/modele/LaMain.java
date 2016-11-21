package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

public class LaMain {

	private LinkedList<CarteAction> listeCarteA = new LinkedList<CarteAction>();

	// Un fois que le joueur se déffauser un carte, cette carte va être rétiré
	// de la main. On se déffauser cette carte par son id
	public CarteAction seDeffausserCarte(CarteAction carte) {
		CarteAction carteReturn = new CarteAction();
		Iterator<CarteAction> it = listeCarteA.iterator();
		while (it.hasNext()) {
			CarteAction carteA = (CarteAction) it.next();
			if (carteA.estEgal(carte)) {
				carteReturn = listeCarteA.remove();
				break;
			}
		}
		return carteReturn;
	}

	public void completerCartes() {

	}

	public void prendreCarteAction( ) {

	}
}
