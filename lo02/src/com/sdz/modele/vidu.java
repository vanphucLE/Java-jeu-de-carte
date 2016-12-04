package com.sdz.modele;

import java.util.ArrayList;
import java.util.Scanner;

public class vidu {
	public void cong(ArrayList<Integer> abc) {
		abc.remove(2);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * System.out.println("ashfbalskfb \n alksjbfalkjsfb");
		 * 
		 * LinkedList<Integer> arr =new LinkedList<Integer>(); arr.add(11);
		 * arr.add(22); arr.add(33); System.out.println(arr.size());
		 * System.out.println(arr.get(2)); int compte=0; Iterator<Integer>
		 * it=arr.iterator(); while( it.hasNext()){ compte++; Integer
		 * a=(Integer)it.next(); if (a==22){ arr.remove(compte);
		 * System.out.println(compte); break; } }
		 * 
		 * int num = (int) Math.ceil(3 * Math.random());
		 * System.out.println(num);
		 * 
		 * /* Scanner sc =new Scanner(System.in); System.out.print("choisir : "
		 * ); String str=sc.nextLine(); System.out.println(str);
		 * 
		 * 
		 * Croyant a =new Croyant(1); System.out.print(a.toString());
		 * System.out.println("a"+(1+2)+"a");
		 */
		// CarteAction carte=new Croyant(1);
		// LaMain laMain=new LaMain();
		// CarteDivinite carteDI=new CarteDivinite(81);
		// laMain.piocherDivinite(carteDI);
		// laMain.completerCarteAction(carte);
		// laMain.completerCarteAction(carte);
		// LinkedList<Croyant> listeCroyant=new LinkedList<Croyant>();
		// Croyant cr=new Croyant(5);
		// listeCroyant.add(cr);
		// GuideSpirituel guideSpirituel=new GuideSpirituel(38);
		// laMain.setCardeGuidee(listeCroyant, guideSpirituel);
		// System.out.println(laMain);
		//
		ArrayList<Integer> abc = new ArrayList();
		abc.add(1);
		abc.add(2);
		abc.add(3);
		abc.add(4);
		abc.add(5);

		vidu ab = new vidu();
		ab.cong(abc);
		System.out.println(abc.toString());

		Scanner sc = new Scanner(System.in);

		System.out.print("Vous voulez continuer à jouer l'autre cartes (Y/N) ?    ");
		String commande = sc.nextLine();
		Boolean continu;
		continu= (commande.equals("Y")) ? true : false;
		System.out.println(continu);
		System.out.println("("+commande+")");
		if(commande=="Y"){
			System.out.println("!");
		}
		if(commande.equals("Y")){
			System.out.println("a");
		}
		
	}
}
