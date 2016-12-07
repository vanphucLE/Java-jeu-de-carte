package com.sdz.cartes;

import com.sdz.modele.*;
import java.util.*;

import com.sdz.modele.Joueur;
import com.sdz.modele.Partie;

public class CapaciteSpeciale {
	public CapaciteSpeciale(){
		
	}
	public void capacite(int id,Partie partie) {
		if (id < 6) {
			partie.getJoueurEncours().setPtAction_Jour(partie.getJoueurEncours().getPtAction_Jour()+1);
		}
		if (id == 6) {
			boolean choix=true;
			while(choix){
				System.out.println("Choisir id Divinité origine Nature ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i=1;i<=partie.getListeJoueurs().size();i++){
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc=new Scanner(System.in);
				int Divine= sc.nextInt();
				Joueur joueur=partie.getListeJoueurs().get(Divine-1);
				if(!joueur.equals(partie.getJoueurEncours())&&joueur.getLaMain().getCarteDivinite().id!=89){
					joueur.setSacrifice(false);
					choix=false;
				}
			}
		}
		if (id == 7) {
			boolean choix=true;
			while(choix){
				System.out.println("Choisir id Divinité origine Chaos ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i=1;i<=partie.getListeJoueurs().size();i++){
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc=new Scanner(System.in);
				int Divine= sc.nextInt();
				Joueur joueur=partie.getListeJoueurs().get(Divine-1);
				if(!joueur.equals(partie.getJoueurEncours()) &&joueur.getLaMain().getCarteDivinite().id!=82 ){
					joueur.setSacrifice(false);
					choix=false;
				}
			}
		}
		if (id == 8) {
			System.out.println("Choisir Divinité pour prendre ses 2 cartes");
			boolean choix = true;
			while (choix) {
				System.out.print("Choisir id de la Divinité");
				Iterator<Joueur> it = partie.getListeJoueurs().iterator(); 
				while (it.hasNext()) {
					System.out.println("id" + it.next().getId() + it.next().getNom());
				}
				Scanner sc = new Scanner(System.in);
				int Divine;
				Divine = sc.nextInt();
				while (it.hasNext()) {
					if (it.next().getId() == Divine && !it.next().equals(partie.getJoueurEncours())
							&& it.next().getLaMain().listeCarteA.size() > 2) {
						Collections.shuffle(it.next().getLaMain().listeCarteA);
						partie.getJoueurEncours().getLaMain().listeCarteA.add(it.next().getLaMain().listeCarteA.pop());
						partie.getJoueurEncours().getLaMain().listeCarteA.add(it.next().getLaMain().listeCarteA.pop());
						choix = false;
					}
					;
				}

			}
		}
		if (id == 9) {
		}
		if (id == 10) {
		}
		if (id == 11) {
		}
		if (id == 12) {
			// guide revient dans sa main et croyant lie revient au centre de la
			// table
		}
		if (id == 13) {
			// relancer de de cosmonogie
			partie.lancerDe();
		}
		if (id > 13 && id < 19) {
			// donner une point d'action de nuit
			partie.getJoueurEncours().setPtAction_Jour(partie.getJoueurEncours().getPtAction_Nuit()+1);
		}
		if (id == 19) {
			boolean choix=true;
			while(choix){
				System.out.println("Choisir id Divinité origine Humaine ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i=1;i<=partie.getListeJoueurs().size();i++){
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc=new Scanner(System.in);
				int Divine= sc.nextInt();
				Joueur joueur=partie.getListeJoueurs().get(Divine-1);
				if(!joueur.equals(partie.getJoueurEncours()) ){
					partie.getListeJoueurs().get(Divine-1).setSacrifice(false);
					choix=false;
				}
			}
		}
		if (id == 20) {
			boolean choix=true;
			while(choix){
				System.out.println("Choisir id Divinité origine Humain ou Symbole pour empecher sa sacrifice la carte croyant");
				for (int i=1;i<=partie.getListeJoueurs().size();i++){
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc=new Scanner(System.in);
				int Divine= sc.nextInt();
				Joueur joueur=partie.getListeJoueurs().get(Divine-1);
				if(!joueur.equals(partie.getJoueurEncours()) &&joueur.getLaMain().getCarteDivinite().id!=84 &&joueur.getLaMain().getCarteDivinite().id!=85){
					partie.getListeJoueurs().get(Divine-1).setSacrifice(false);
					choix=false;
				}
			}
		}
		if (id == 21) {
			// pioche 2 carte au harsards dans la main une autre Divinité id=8
		}
		if (id == 22) {
		}
		if (id == 23) {
		}
		if (id == 24) {
			// retirer les croyants rattacher une autre divinite
			System.out.println("Choisir id de la Divinité pour effectuer la capacite");
			boolean choix=true;
			Iterator<Joueur> it = partie.getListeJoueurs().iterator();
			while (it.hasNext()) {
				if (it.next().getId() > partie.getJoueurEncours().getId()) {
					System.out.println("id" + it.next().getId() + it.next().getNom());
				}
			}
			while (choix) {
				Scanner sc = new Scanner(System.in);
				int Divine;
				Divine = sc.nextInt();
				if(Divine != partie.getJoueurEncours().getId() && Divine< partie.getListeJoueurs().size() && Divine<0){
					System.out.println("Choisir la carte guider a retirer"+partie.getListeJoueurs().get(Divine).getLaMain().getListeGuideSpirituelGuider());
				}
				

			}
			
		}
		if (id == 25) {
			// prender pt Action
			System.out.println("Choisir Divinité pour prendre ses points d'action");
			boolean choix = true;
			while (choix) {
				System.out.print("Choisir id de la Divinité");
				Iterator<Joueur> it = partie.getListeJoueurs().iterator();
				while (it.hasNext()) {
					if (it.next().getId() > partie.getJoueurEncours().getId()) {
						System.out.println("id" + it.next().getId() + it.next().getNom());
					}
				}
				Scanner sc = new Scanner(System.in);
				int Divine;
				Divine = sc.nextInt();
				if (Divine > partie.getJoueurEncours().getId() && Divine < partie.getListeJoueurs().size()) {
					partie.getJoueurEncours().setPtAction_Jour(partie.getJoueurEncours().getPtAction_Jour()+partie.getListeJoueurs().get(Divine-1).getPtAction_Jour());
					partie.getListeJoueurs().get(Divine-1).setPtAction_Jour(0);
					partie.getJoueurEncours().setPtAction_Nuit(partie.getJoueurEncours().getPtAction_Nuit()+partie.getListeJoueurs().get(Divine-1).getPtAction_Nuit());
					partie.getListeJoueurs().get(Divine-1).setPtAction_Nuit(0);
					partie.getJoueurEncours().setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant()+partie.getListeJoueurs().get(Divine-1).getPtAction_Neant());
					partie.getListeJoueurs().get(Divine-1).setPtAction_Neant(0);
					choix = false;
				}

			}
		}
		if (id == 26) {
			// benificier une capacite speciale dune carte croyant appartenant a
			// un autre joueur
		}
		if (id > 26 && id < 32) {
			partie.getJoueurEncours().setPtAction_Neant(partie.getJoueurEncours().getPtAction_Neant()+1);
		}
		if (id == 32) {
			boolean choix=true;
			while(choix){
				System.out.println("Choisir id Divinité origine Nature ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i=1;i<=partie.getListeJoueurs().size();i++){
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc=new Scanner(System.in);
				int Divine= sc.nextInt();
				Joueur joueur=partie.getListeJoueurs().get(Divine-1);
				if(!joueur.equals(partie.getJoueurEncours()) &&joueur.getLaMain().getCarteDivinite().id!=89){
					joueur.setSacrifice(false);
					choix=false;
				}
			}
		}
		if (id == 33) {
			boolean choix=true;
			while(choix){
				System.out.println("Choisir id Divinité origine Chaos ou Mystique pour empecher sa sacrifice la carte croyant");
				for (int i=1;i<=partie.getListeJoueurs().size();i++){
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
				}
				Scanner sc=new Scanner(System.in);
				int Divine= sc.nextInt();
				Joueur joueur=partie.getListeJoueurs().get(Divine-1);
				if(!joueur.equals(partie.getJoueurEncours()) &&joueur.getLaMain().getCarteDivinite().id!=82){
					joueur.setSacrifice(false);
					choix=false;
				}
			}
		}
		if (id == 34) {
			//piocher 2 cartes id=8
		}
		if (id == 35) {
			//lancer le de id=13
		}
	}
}
