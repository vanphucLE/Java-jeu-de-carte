package com.sdz.cartes;

public class DeusEx extends CarteAction {

	
	
	private static final String[] nomDeusEx = { "Col�re Divinie", "Col�re Divinie", "Stase", "Ordre C�leste",
			"Fourberie", "Diversion", "Concentration", "Trou Noir", "Phoenix", "Influence Jour", "Influence Nuit",
			"Inffluence N�ant", "Inffluence Nulle", "Inffluence Nulle", "Transe", "Miroir", "Bouleversement",
			"Inquisition" };

	private static final String[] origineDeusEx = { "Jour","Nuit","Jour","Jour","Nuit","Nuit",
													"N�ant","N�ant","N�ant","","","","","","","","",""};
	
	public static final String [] CapaDeusEx={"D�truit une carte Guide Spirituel d'Origine Nuit ou N�ant, dont la capacit� sp�ciale n'a pas effet. Les Croyants attach�s reviennent au centre de la table.","D�truit une carte Guide Spirituel d'Origine Jour ou N�ant, dont la capacit� sp�ciale n'a pas effet. Les Croyants attach�s reviennent au centre de la table.","Prot�ge un Guide Spirituel et ses Croyants jusqu'� ce que cette carte soit annul�e ou jusqu'� la prochaine tentative d'Apocalypse.",
			"Vous r�cup�rez un des Guides Spirituels pos�s devant une autre Divinit� et le placez devant vous avec les Croyants qui y sont attach�s.","Sacrifiez 2 cartes Croyants d'une autre Divinit�. Les capacit�s sp�ciales ne sont pas jou�es ","Prenez 3 cartes dans la main d'un autre joueur et incluez-les � votre main.",
			"Vous r�cup�rez un des Guides Spirituels pos�s devant une autre Divinit� et le placez devant vous avec les Croyants qui y sont attach�s.","Aucun autre joueur ne gagne de points d'Action durant ce tour.","Permet de b�n�ficier de la capacit� sp�ciale de l'un de vos Croyants ou Guides Spirituels sans sacrifier la carte.",
			"Annule la capacit� sp�ciale d'une carte d'Action d'Origine Nuit ou N�ant.","Annule la capacit� sp�ciale d'une carte d'Action d'Origine Jour ou N�ant.","Annule la capacit� sp�ciale d'une carte d'Action d'Origine Jour ou Nuit.",
			"Annule la capacit� sp�ciale d'une autre carte d'Action.","Annule la capacit� sp�ciale d'une autre carte d'Action.","Permet de r�cup�rer les effets b�n�fiques d'une carte d'Action pos�e par une autre Divinit�. S'il s'agit d'une carte Croyants ou Guide Spirituel, vous posez la carte devant vous.",
			"Retourne les effets d'une carte d'Action sur la Divinit� qui l'a pos�e.","Relancez le d� de Cosmogonie. Le tour de jeu se terminera normalement, mais sous la nouvelle influence.","Choisissez un des Guides Spirituels d'un autre joueur, et l'un des votres. Lancez le d� de Cosmogonie. Sur Jour, le Guide adverse est sacrifi�, sur Nuit le votre est sacrifi�, sur N�ant rien ne se passe. Les"};
	
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
