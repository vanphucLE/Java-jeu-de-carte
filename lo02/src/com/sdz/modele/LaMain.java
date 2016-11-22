package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;

public class LaMain {

	private CarteDivinite carteDivinite;
	
	private LinkedList<CarteAction> listeCarteA = new LinkedList<CarteAction>();

	// listeCroyantGuidee est un collection continant les
	// cartes croyants qui a été guidées par la carte
	// guide spirituel qui sont enregistrées dans listGuideSpirituelGuider 
	private LinkedList<LinkedList<Croyant>> listeCroyantGuidee=new LinkedList();
	private LinkedList<GuideSpirituel> listeGuideSpirituelGuider=new LinkedList();

	public LinkedList<CarteAction> getListeCarteA() {
		return listeCarteA;
	}

	public void setListeCarteA(LinkedList<CarteAction> listeCarteA) {
		this.listeCarteA = listeCarteA;
	}

	public LinkedList<LinkedList<Croyant>> getlisteCroyantGuidee() {
		return listeCroyantGuidee;
	}

	public void setCardeGuidee(LinkedList<Croyant> listeCroyant, GuideSpirituel guideSpirituel) {
		this.listeCroyantGuidee.add(listeCroyant);
		this.listeGuideSpirituelGuider.add(guideSpirituel);
	}

	public CarteDivinite getCarteDivinite() {
		return carteDivinite;
	}

	public void piocherDivinite(CarteDivinite carteDivinite) {
		this.carteDivinite = carteDivinite;
	}

	public LinkedList<LinkedList<Croyant>> getListeCroyantGuidee() {
		return listeCroyantGuidee;
	}

	public LinkedList<GuideSpirituel> getListeGuideSpirituelGuider() {
		return listeGuideSpirituelGuider;
	}

	// Un fois que le joueur se déffauser un carte, cette carte va être rétiré
	// de la main
	public void seDeffausserCarte(CarteAction carte) {
		
		Iterator<CarteAction> it = listeCarteA.iterator();
		int position=0; 
		while (it.hasNext()) {
			CarteAction carteA = (CarteAction) it.next();
			if (carteA.estEgal(carte)) {
				position++;
				listeCarteA.remove(position);
				break;
			}
		}
	}
	

	public void completerCarteAction(CarteAction carte) {
		this.listeCarteA.add(carte);
	}

}
