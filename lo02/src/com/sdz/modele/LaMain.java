package com.sdz.modele;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.CarteDivinite;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;
/*
 * contient tous les cartes en main et les utiliser
 * @author TRAN Hoang
 */
public class LaMain {

	private CarteDivinite carteDivinite;
	private Joueur j;

	private LinkedList<CarteAction> listeCarteA;

	// listeCroyantGuidee est un collection continant les
	// cartes croyants qui a été guidées par la carte
	// guide spirituel qui sont enregistrées dans listGuideSpirituelGuider
	private LinkedList<LinkedList<CarteAction>> listeCroyantGuidee;
	private LinkedList<CarteAction> listeGuideSpirituelGuider;

	public LaMain(Joueur j) {
		this.j = j;
		this.listeCarteA = new LinkedList<CarteAction>();
		this.listeCroyantGuidee = new LinkedList();
		this.listeGuideSpirituelGuider = new LinkedList();
	}
	/*
	 * prendre la liste de Carte Action dans la main
	 * @return liste carte action
	 */
	public LinkedList<CarteAction> getListeCarteA() {
		return listeCarteA;
	}

	public void setListeCarteA(LinkedList<CarteAction> listeCarteA) {
		this.listeCarteA = listeCarteA;
	}

	public void ajouterGuidee(LinkedList<CarteAction> listeCroyant, CarteAction guideSpirituel) {
		this.listeCroyantGuidee.add(listeCroyant);
		this.listeGuideSpirituelGuider.add(guideSpirituel);
		this.j.notifyLaMain();
	}
	/*
	 * prendre la liste de Carte Divinite dans la main
	 * @return carte divinite
	 */
	public CarteDivinite getCarteDivinite() {
		return carteDivinite;
	}
	/*
	 * reprendre la carte divinite
	 * @param carteDivinite
	 */
	public void piocherDivinite(CarteDivinite carteDivinite) {
		this.carteDivinite = carteDivinite;
	}

	// cette méthode est utilisé pour set l'attribute True à sacrifier
	public void setTrueSacifice() {
		for (LinkedList<CarteAction> cartes : this.listeCroyantGuidee) {
			for (CarteAction carte : cartes) {
				carte.setEstSacrifie(true);
			}
		}
		for (CarteAction carte : this.listeGuideSpirituelGuider) {
			carte.setEstSacrifie(true);
		}
	}
	/*
	 * prendre la liste de Carte croyant guidee dans la main
	 * @return liste carte action
	 */
	public LinkedList<LinkedList<CarteAction>> getListeCroyantGuidee() {
		return listeCroyantGuidee;
	}
	/*
	 * prendre la liste de Carte guide sprirtuel dans la main
	 * @return liste carte action
	 */
	public LinkedList<CarteAction> getListeGuideSpirituelGuider() {
		return listeGuideSpirituelGuider;
	}

	// Un fois que le joueur se déffauser un carte, cette carte va être rétiré
	// de la main
	/*
	 * defausser la carte
	 * @param carte une variable carte action
	 */
	public void seDeffausserCarte(CarteAction carte) {
		this.listeCarteA.remove(carte);
		this.j.notifyLaMain();
	}

	// un fois que la carte est jouée, il est supprimée de la main
	public CarteAction seDeffausserCarte(int id) {
		CarteAction carteReturn = new CarteAction();
		Iterator<CarteAction> it = listeCarteA.iterator();
		int position = 0;
		while (it.hasNext()) {
			CarteAction carteA = (CarteAction) it.next();
			if (carteA.getId() == id) {
				carteReturn = listeCarteA.remove(position);
				break;
			}
			position++;
		}
		this.j.notifyLaMain();
		return carteReturn;
	}

	public void completerCarteAction(CarteAction carte) {
		this.listeCarteA.add(carte);
		this.j.notifyLaMain();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("Carte Divinite");
		sb.append(this.carteDivinite + "\n");
		sb.append("Les cartes Actions: \n");
		Iterator<CarteAction> it = this.listeCarteA.iterator();
		while (it.hasNext()) {
			CarteAction carte = (CarteAction) it.next();
			sb.append(" ." + carte + "\n");
		}
		sb.append("Liste cartes guidées: ");
		Iterator<CarteAction> it2 = this.listeGuideSpirituelGuider.iterator();
		int compte = -1;
		while (it2.hasNext()) {
			compte++;
			GuideSpirituel carteG = (GuideSpirituel) it2.next();
			sb.append(" .Carte guide spirituel: " + carteG);
			Iterator<CarteAction> it3 = this.listeCroyantGuidee.get(compte).iterator();
			while (it3.hasNext()) {
				Croyant carteA = (Croyant) it3.next();
				sb.append("\n   - Guider: " + carteA);
			}
		}
		return sb.toString();
	}

}
