package com.sdz.modele;

import java.util.Scanner;

import com.sdz.cartes.CarteAction;

public class JoueurPhysique extends Joueur {
	
	// Id du joueur phisique est 1 par défault;
	private static final int id=1;
	
	public JoueurPhysique(int id, String nom, int age){
		super(id,nom,age);
	}
	public void jouer(JeuDeCartes jeuDeCartes){
		this.seDefausserCartes(jeuDeCartes);
		this.Compeleter7Carte(jeuDeCartes);
		
	}
	//Un joueur veut défausser un plusieur Cartes
	private void seDefausserCartes(JeuDeCartes jeuDeCartes){
		System.out.println("Votre point Action : "+this.ptAction+" ---- Origine : "+this.ptActionOrigine);
		Scanner sc= new Scanner(System.in);
		String commande="";
		System.out.print("Voulez-vous défausser les cartes ? (Y/N):");
		commande = sc.nextLine();
		if(commande == "Y"){
			String commande2="";
			System.out.println("Les cartes actions tenu dans vôtre main:");
			System.out.println(this.laMain);
			System.out.print("Choisir les Id dont les cartes actions déffausées (Ex: 1 3 5) : ");
			commande2=sc.nextLine();
			commande2=commande2.trim();
			String[] cartesDef={};
			cartesDef=commande2.split(" ");
			for (String str : cartesDef ){
				int num=Integer.parseInt(str);
				CarteAction carteA = this.laMain.getListeCarteA().get(num-1);
				//la carte défaussées est recupéré dans le jeu de carte. 
				this.laMain.seDeffausserCarte(carteA);
				jeuDeCartes.recupererCarteAction(carteA);
			}
		}
	}
	
	//Le joueur compléte 7 cartes dans la main
	private void Compeleter7Carte(JeuDeCartes jeuDeCartes){
		int compte = this.laMain.getListeCarteA().size();
		while (compte < 7) {
			compte++;
			CarteAction carte = jeuDeCartes.distribuerCarteAction();
			this.getLaMain().completerCarteAction(carte);
		}
		System.out.println("Vous avez complété 7 Cartes. Maintenant, les cartes sont: ");
		System.out.println(this.laMain);
	}
	public void choisirCarteReel() {
		System.out.println("(Rappeler) Votre point Action : "+this.ptAction+" ---- Origine : "+this.ptActionOrigine);
		System.out.print("Choissiez Id dont la carte pour jouer(Ex: 5): ");
		
	}

	public void JoueurCapaSpeReel() {

	}
}
