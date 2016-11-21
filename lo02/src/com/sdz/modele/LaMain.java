package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.CarteAction;

public class LaMain {

	private LinkedList<CarteAction> listeCarteA = new LinkedList<CarteAction>();

	// Un fois que le joueur se d�ffauser un carte, cette carte va �tre r�tir�
	// de la main. On se d�ffauser cette carte par son id
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
