package com.sdz.cartes;

import java.util.Arrays;

import com.sdz.modele.Partie;

public class CarteDivinite extends Carte {
	//Carte Divinite: 81 -->90
	private Boolean estCapaciteSpecialUtilise = false;
	
	public static final String[] nomDivinite = { "Brewalen", "Drinded", "Yarstur", "Kilinstred", "Llewalla",
			"Pui-Tara", "Gwenghelen", "Shingva", "Gorpa", "Romtec" };
	public static final String[] origineDivinite = { "Jour", "Jour", "Jour", "Nuit", "Nuit", "Nuit", "Aube", "Aube",
			"Crépuscule", "Crépuscule" };
	public static final String[][] dogmeDivinite = { { "Nature", "Humain", "Mystique" },
			{ "Nature", "Humain", "Symboles" }, { "Chaos", "Symboles", "Mystique" }, { "Nature", "Mystique", "Chaos" },
			{ "Nature", "Mystique", "Chaos" }, { "Nature", "Mystique", "Symboles" },
			{ "Humain", "Mystique", "Symboles" }, { "Humain", "Mystique", "Chaos" }, { "Humain", "Symboles", "Chaos" },
			{ "Nature", "Humain", "Chaos" } };
	
	public static final String[] CapaDivinite={"Peut empêcher l'utilisation d'une carte Apocalypse. La carte est défaussée.",
			"Peut empêcher le sacrifice d'un des Guides Spirituels de n'importe quel joueur.",
			"Peut détruire toutes les cartes de Croyants au centre de la table d'Origine Néant.",
			"Peut obliger un joueur à poser une carte Apocalypse s'il en possède une.",
			"Peut obliger un joueur à poser une carte Apocalypse s'il en possède une.",
			"Peut détruire toutes les cartes de Croyants au centre de la table d'Origine Jour.",
			"Récupère autant de points d'Action supplémentaires d'Origine Néant que le nombre de Guides Spirituels que la Divinité possède.",
			"Peut imposer le sacrifice d'un Guide Spirituel ayant le Dogme Symboles ou Nature.",
			"Peut récupérer les points d'Action d'une autre Divinité en plus des siens. L'autre Divinité ne reçoit aucun point d'Action ce tour-ci.",
			"Peut empêcher un jour de créer un Guide Spirituel. La carte est défaussée.",
			}; 

	public CarteDivinite(int id){
		this.id=id;
		this.nom=nomDivinite[id-81];
		this.origine=origineDivinite[id-81];
		this.dogme=dogmeDivinite[id-81];
		this.type = "Divnité";
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
		sb.append("      +[Capacité speciale: " + this.capaciteSpecial+"] \n");
		sb.append("      est utilisée: " + this.estCapaciteSpecialUtilise);
		return sb.toString();
	}
}
