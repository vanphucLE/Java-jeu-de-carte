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
		if (id == 8||id==21||id == 34) {
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
							&& it.next().getLaMain().getListeCarteA().size() > 2) {
						Collections.shuffle(it.next().getLaMain().getListeCarteA());
						partie.getJoueurEncours().getLaMain().getListeCarteA().add(it.next().getLaMain().getListeCarteA().pop());
						partie.getJoueurEncours().getLaMain().getListeCarteA().add(it.next().getLaMain().getListeCarteA().pop());
						choix = false;
					}
					;
				}

			}
		}
		if (id >= 9 && id <=11 || id==23||id==22||id==9) {
			//imposer la sacrifice un croyant d'un joueur
			System.out.println("Choisir Divinité pour lui imposer sa sacrifice");
			for (int i=1;i<=partie.getListeJoueurs().size();i++){
				System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i-1).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
			}
			Scanner sc=new Scanner(System.in);
			int Divine= sc.nextInt();
			Joueur joueur=partie.getListeJoueurs().get(Divine-1);
			LinkedList<Croyant> carte= new LinkedList();
			System.out.println("Choisir id carte croyant a sacrifier");
			for (int i=1;i<=joueur.getLaMain().getListeCroyantGuidee().size();i++){
				for (int j=1;j<=joueur.getLaMain().getListeCroyantGuidee().get(i-1).size();j++){
					carte.add(joueur.getLaMain().getListeCroyantGuidee().get(i-1).get(j-1));
					System.out.println(carte.toString());
				}
			}
			if(joueur.estBot()){
				Collections.shuffle(carte);
				joueur.setSacrifice(true);
				joueur.sacrifierCroyant(carte.pop().getId(), partie);
			}
			else {
				int croyant=sc.nextInt();
				joueur.setSacrifice(true);
				joueur.sacrifierCroyant(croyant, partie);
			}
		}
		if (id == 12) {
			// guide revient dans sa main et croyant lie revient au centre de la
			// table
			for (int i=1;i<=partie.getListeJoueurs().size();i++){
				for (int j=1;j<=partie.getListeJoueurs().get(i-1).getLaMain().getListeCroyantGuidee().get(i-1).size();j++){
					if (partie.getListeJoueurs().get(i-1).getLaMain().getListeCroyantGuidee().get(i-1).get(j-1).getId()==id){
						Joueur joueur=partie.getListeJoueurs().get(i-1);
						if(joueur.estBot()){
							int guide= (int) Math.ceil((joueur.getLaMain().getListeGuideSpirituelGuider().size()) * Math.random());
							partie.getJeuDeCartes().getListeCartesAction().add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(guide));
							partie.getEspaceCommun().getListeCartesPret().addAll(joueur.getLaMain().getListeCroyantGuidee().remove(guide));
						}
						else {
							System.out.println("choisir la carte guide spirituel a revenir");
							for(int k=1; k<=joueur.getLaMain().getListeGuideSpirituelGuider().size();k++){
								System.out.println(k+":"+joueur.getLaMain().getListeGuideSpirituelGuider().get(k-1).getNom());
							}
							Scanner sc=new Scanner(System.in);
							int guide=sc.nextInt();
							partie.getJeuDeCartes().getListeCartesAction().add(joueur.getLaMain().getListeGuideSpirituelGuider().remove(guide));
							partie.getEspaceCommun().getListeCartesPret().addAll(joueur.getLaMain().getListeCroyantGuidee().remove(guide));
						}
						break;
					}
				}
			}
		}
		if (id == 13||id == 35) {
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
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i-1).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
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
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i-1).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
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
		//id 21=8
		//id 22, 23=9
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
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i-1).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
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
					System.out.print("id "+i+" nom "+partie.getListeJoueurs().get(i-1).getNom()+" Dogme "+partie.getListeJoueurs().get(i).getLaMain().getCarteDivinite().getDogme());
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
		//id 34 piocher 2 cartes id=8
		//id =35 lancer le de id=13
		//id== 36 imposer la sacrifice =9
		
		if (id ==37){
			//ne recevoir pas pt actions
			Iterator<Joueur> it= partie.getListeJoueurs().iterator();
			while(it.hasNext()){
				it.next().setpointAction(false);
			}
		}
		if(id>37 && id< 41){
			//apocalyse
		}
		if(id>40 && id<49){
			//gagner point action en fonction de carte croyant attache
			for (int i=1; i<= partie.getListeJoueurs().size();i++){
				Joueur joueur=partie.getListeJoueurs().get(i);
				for(int j=1;j<=joueur.getLaMain().getListeGuideSpirituelGuider().size();j++){
					if(joueur.getLaMain().getListeGuideSpirituelGuider().get(j).id==id){
						int ptActionajouter=joueur.getLaMain().getlisteCroyantGuidee().get(j).size();
						System.out.println("Choisir pt Action ajouter, 1 Jour, 2 Nuit, 3 Neant");
						Scanner sc= new Scanner(System.in);
						int originePtAction=sc.nextInt();
						switch(originePtAction) {
							case 1: joueur.setPtAction_Jour(joueur.getPtAction_Jour()+ptActionajouter);break;
							case 2: joueur.setPtAction_Nuit(joueur.getPtAction_Nuit()+ptActionajouter);break;
							case 3: joueur.setPtAction_Neant(joueur.getPtAction_Neant()+ptActionajouter);break;
							default: System.out.println("Non pt Action a ajouter "); break;
						}
					}
				}
			}
			
		}
		
	}
}
