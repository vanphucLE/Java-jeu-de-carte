package com.sdz.cartes;

import java.util.Arrays;

import com.sdz.modele.Partie;

public class CarteDivinite extends Carte {
	//Carte Divinite: 81 -->90
	private Boolean estCapaciteSpecialUtilise = false;
	
	public static final String[] nomDivinite = { "Brewalen", "Drinded", "Yarstur", "Kilinstred", "Llewalla",
			"Pui-Tara", "Gwenghelen", "Shingva", "Gorpa", "Romtec" };
	public static final String[] origineDivinite = { "Jour", "Jour", "Jour", "Nuit", "Nuit", "Nuit", "Aube", "Aube",
			"Cr�puscule", "Cr�puscule" };
	public static final String[][] dogmeDivinite = { { "Nature", "Humain", "Mystique" },
			{ "Nature", "Humain", "Symboles" }, { "Chaos", "Symboles", "Mystique" }, { "Nature", "Mystique", "Chaos" },
			{ "Nature", "Mystique", "Chaos" }, { "Nature", "Mystique", "Symboles" },
			{ "Humain", "Mystique", "Symboles" }, { "Humain", "Mystique", "Chaos" }, { "Humain", "Symboles", "Chaos" },
			{ "Nature", "Humain", "Chaos" } };
	
	public static final String[] CapaDivinite={"Peut emp�cher l'utilisation d'une carte Apocalypse. La carte est d�fauss�e.",
			"Peut emp�cher le sacrifice d'un des Guides Spirituels de n'importe quel joueur.",
			"Peut d�truire toutes les cartes de Croyants au centre de la table d'Origine N�ant.",
			"Peut obliger un joueur � poser une carte Apocalypse s'il en poss�de une.",
			"Peut obliger un joueur � poser une carte Apocalypse s'il en poss�de une.",
			"Peut d�truire toutes les cartes de Croyants au centre de la table d'Origine Jour.",
			"R�cup�re autant de points d'Action suppl�mentaires d'Origine N�ant que le nombre de Guides Spirituels que la Divinit� poss�de.",
			"Peut imposer le sacrifice d'un Guide Spirituel ayant le Dogme Symboles ou Nature.",
			"Peut r�cup�rer les points d'Action d'une autre Divinit� en plus des siens. L'autre Divinit� ne re�oit aucun point d'Action ce tour-ci.",
			"Peut emp�cher un jour de cr�er un Guide Spirituel. La carte est d�fauss�e.",
			}; 

	public CarteDivinite(int id){
		this.id=id;
		this.nom=nomDivinite[id-81];
		this.origine=origineDivinite[id-81];
		this.dogme=dogmeDivinite[id-81];
		this.type = "Divnit�";
		this.capaciteSpecial=CapaDivinite[id-81];
	}
	public void effectuerCapaciteSpecial(Partie partie) {

	}
	
	public Boolean getEstCapaciteSpecialUtilise() {
		return estCapaciteSpecialUtilise;
	}
	public void setEstCapaciteSpecialUtilise(Boolean estCapaciteSpecialUtilise) {
		this.estCapaciteSpecialUtilise = estCapaciteSpecialUtilise;
	}
	@Override
	public String toString() {
		//on conserve les dogme dans un chain
		StringBuffer sb = new StringBuffer();
		sb.append("Carte " + this.type + ": "+this.nom + " \t ");
		sb.append("[Id: " + this.id+ "] ");
		sb.append("[Dogme: " + Arrays.toString(dogme) + "] ");
		sb.append("[Origine: " + this.origine + "] \n ");
		sb.append("      +[Capacit� speciale: " + this.capaciteSpecial+"] \n");
		sb.append("      est utilis�e: " + this.estCapaciteSpecialUtilise);
		return sb.toString();
	}
}
