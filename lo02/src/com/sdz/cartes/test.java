package com.sdz.cartes;
import java.util.Scanner;

import com.sdz.modele.*;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Croyant carte=new Croyant(1);
		System.out.println(carte.toString());
		
		System.out.println(carte.dogmeCroyants.length);
		/*
		for (String a : carte.nomCroyants ){
			System.out.println(a+" ");
		}
		
		*/
		Joueur joueur1 =new Joueur(1,"LE",21);
		System.out.println(joueur1.toString());
		
		Scanner sc = new Scanner(System.in);
		String str = "";
		do {
			System.out.print("Entrez 'Lancer' pour lancer le dé! ");
			str = sc.nextLine();
		} while (!str.equals("Lancer"));
	int a=Integer.parseInt("a");
	}
	
	//,
	//Croyant
}
