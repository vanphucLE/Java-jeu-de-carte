package com.sdz.modele;

import java.util.LinkedList;

import javax.print.attribute.standard.RequestingUserName;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;
import com.sdz.cartes.GuideSpirituel;

public class Joueur {
	protected int id;
	protected String nom;
	protected int age;
	protected int ptPriere;
	protected int ptAction_Jour;
	protected int ptAction_Nuit;
	protected int ptAction_Neant;
	protected Boolean estElimine;
	protected int idCarteDivinite;
	protected LaMain laMain;
	protected boolean sacrifice = true;
	protected boolean bot;
	//chua xu li bien setpointAction
	protected boolean setpointAction;

	public Joueur(int id, String nom, int age) {
		this.id = id;
		this.nom = nom;
		this.age = age;
	}
	public boolean estBot(){
			return this.bot;
	}
	public void setpointAction(boolean setpointAction){
	this.setpointAction=setpointAction;
	}
	public int getidCarteDivinite() {
		return this.idCarteDivinite;
	}

	public void setSacrifice(boolean sacrifice) {
		this.sacrifice = sacrifice;
	}

	public String getNom() {
		return this.nom;
	}

	public int getId() {
		return this.id;
	}

	public void setPtAction_Jour(int ptAction) {
		if(this.setpointAction){
		this.ptAction_Jour = ptAction;}
	}

	public int getPtAction_Jour() {
		return this.ptAction_Jour;
	}

	public void setPtAction_Nuit(int ptAction) {
		if(this.setpointAction){
		this.ptAction_Nuit = ptAction;}
	}

	public int getPtAction_Nuit() {
		return this.ptAction_Nuit;
	}

	public void setPtAction_Neant(int ptAction) {
		if(this.setpointAction){
		this.ptAction_Neant = ptAction;}
	}

	public int getPtAction_Neant() {
		return this.ptAction_Neant;
	}

	public void setPtPriere() {
		int sumPtPriere = 0;
		for (LinkedList<Croyant> listeCarte : laMain.getListeCroyantGuidee()) {
			for (Croyant carte : listeCarte) {
				sumPtPriere += carte.getNbCroyant();
			}
		}
		this.ptPriere = sumPtPriere;
	}

	public int getPtPriere() {
		return this.ptPriere;
	}

	public void setElimine(Boolean estElimie) {
		this.estElimine = estElimine;
	}

	public Boolean getElimine() {
		return this.estElimine;
	}

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}

	public void jouer(Partie partie) {
		this.seDefausserCartes(partie.getJeuDeCartes());
		this.Compeleter7Carte(partie.getJeuDeCartes());
		this.choisirCarte(partie);
	}

	// Un joueur veut défausser un plusieur Cartes
	public void seDefausserCartes(JeuDeCartes jeuDeCartes) {

	}

	// Le joueur compléte 7 cartes dans la main
	public void Compeleter7Carte(JeuDeCartes jeuDeCartes) {
		int compte = this.laMain.getListeCarteA().size();
		while (compte < 7) {
			compte++;
			CarteAction carte = jeuDeCartes.distribuerCarteAction();
			this.getLaMain().completerCarteAction(carte);
		}
		System.out.println("Vous avez complété 7 Cartes. Maintenant, les cartes sont: ");
		System.out.println(this.laMain);
	}

	// Choisir carte pour jouer
	public void choisirCarte(Partie partie) {
	}
	public void jouerCroyant(CarteAction carte, EspaceCommun espaceCommun) {
		espaceCommun.ajouterCarte((Croyant) carte);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Joueur n." + this.id + " : ");
		sb.append(this.nom + "\t ");
		sb.append("(" + this.age + " ans) | ");
		sb.append("Point Prière: " + this.ptPriere + " | ");
		sb.append("Point Action : (Jour: " + this.ptAction_Jour + ") ");
		sb.append("(Nuit: " + this.ptAction_Nuit + ") ");
		sb.append("(Néant: " + this.ptAction_Neant + ")");
		return sb.toString();
	}

	public void sacrifierCroyant(int croyant,Partie partie) {
		// rut me cai carte di
		if (this.sacrifice) {
			
			for(int i=0;i<this.getLaMain().getlisteCroyantGuidee().size();i++){
				for(int j=0;j<this.getLaMain().getlisteCroyantGuidee().get(i).size();j++){
					if(id==(this.getLaMain().getlisteCroyantGuidee().get(i).get(j)).getId()){
						this.sacrifice=false;
						this.getLaMain().getlisteCroyantGuidee().get(i).get(j).effectuerCapaciteSpecial();
						partie.getJeuDeCartes().getListeCartesAction().add(this.getLaMain().getlisteCroyantGuidee().get(i).remove(j));
						if(this.getLaMain().getlisteCroyantGuidee().get(i).size()==0){
							partie.getJeuDeCartes().getListeCartesAction().add(this.getLaMain().getListeGuideSpirituelGuider().remove(i));
						}
					break;	
					}
				}
			}
			
			
		} else System.out.println("Vous ne pouvez pas sacrifier la carte");
	}
	public void sacrifierGuideSpirit(int guide,Partie partie){
		if (this.sacrifice){
			
			for(int i=0;i<this.getLaMain().getListeGuideSpirituelGuider().size();i++){
				if(guide==(this.getLaMain().getListeGuideSpirituelGuider().get(i).getId())){
					this.sacrifice=false;
					this.getLaMain().getListeGuideSpirituelGuider().get(i).effectuerCapaciteSpecial();
					partie.getJeuDeCartes().getListeCartesAction().add(this.getLaMain().getListeGuideSpirituelGuider().remove(i));
					partie.getEspaceCommun().getListeCartesPret().addAll(this.getLaMain().getListeCroyantGuidee().get(i));
					break;
				}
			}
			
		}
		
	}
}
