package com.sdz.cartes;

public class DeusEx extends CarteAction {

	
	
	private static final String[] nomDeusEx = { "Colère Divinie", "Colère Divinie", "Stase", "Ordre Céleste",
			"Fourberie", "Diversion", "Concentration", "Trou Noir", "Phoenix", "Influence Jour", "Influence Nuit",
			"Inffluence Néant", "Inffluence Nulle", "Inffluence Nulle", "Transe", "Miroir", "Bouleversement",
			"Inquisition" };

	private static final String[] origineDeusEx = { "Jour","Nuit","Jour","Jour","Nuit","Nuit",
													"Néant","Néant","Néant","","","","","","","","",""};
	
	public static final String [] CapaDeusEx={"Détruit une carte Guide Spirituel d'Origine Nuit ou Néant, dont la capacité spéciale n'a pas effet. Les Croyants attachés reviennent au centre de la table.","Détruit une carte Guide Spirituel d'Origine Jour ou Néant, dont la capacité spéciale n'a pas effet. Les Croyants attachés reviennent au centre de la table.","Protège un Guide Spirituel et ses Croyants jusqu'à ce que cette carte soit annulée ou jusqu'à la prochaine tentative d'Apocalypse.",
			"Vous récupérez un des Guides Spirituels posés devant une autre Divinité et le placez devant vous avec les Croyants qui y sont attachés.","Sacrifiez 2 cartes Croyants d'une autre Divinité. Les capacités spéciales ne sont pas jouées ","Prenez 3 cartes dans la main d'un autre joueur et incluez-les à votre main.",
			"Vous récupérez un des Guides Spirituels posés devant une autre Divinité et le placez devant vous avec les Croyants qui y sont attachés.","Aucun autre joueur ne gagne de points d'Action durant ce tour.","Permet de bénéficier de la capacité spéciale de l'un de vos Croyants ou Guides Spirituels sans sacrifier la carte.",
			"Annule la capacité spéciale d'une carte d'Action d'Origine Nuit ou Néant.","Annule la capacité spéciale d'une carte d'Action d'Origine Jour ou Néant.","Annule la capacité spéciale d'une carte d'Action d'Origine Jour ou Nuit.",
			"Annule la capacité spéciale d'une autre carte d'Action.","Annule la capacité spéciale d'une autre carte d'Action.","Permet de récupérer les effets bénéfiques d'une carte d'Action posée par une autre Divinité. S'il s'agit d'une carte Croyants ou Guide Spirituel, vous posez la carte devant vous.",
			"Retourne les effets d'une carte d'Action sur la Divinité qui l'a posée.","Relancez le dé de Cosmogonie. Le tour de jeu se terminera normalement, mais sous la nouvelle influence.","Choisissez un des Guides Spirituels d'un autre joueur, et l'un des votres. Lancez le dé de Cosmogonie. Sur Jour, le Guide adverse est sacrifié, sur Nuit le votre est sacrifié, sur Néant rien ne se passe. Les"};
	
	public DeusEx(int id){
		this.id=id;
		this.nom=nomDeusEx[id-58];
		this.origine=origineDeusEx[id-58];
		this.type="DeusEx";
		this.capaciteSpecial=CapaDeusEx[id-58];
		this.estSacrifie=true;
	}
	public void effectuerCapaciteSpecial() {

	}
}
