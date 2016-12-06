package com.sdz.modele;

import java.util.LinkedList;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.Croyant;

public class Joueur {
	protected int id;
	protected String nom;
	protected int age;
	protected int ptPriere;
	protected int ptAction;
	public int ptAction_Jour;
	public int ptAction_Nuit;
	public int ptAction_Neant;
	protected String ptActionOrigine;
	protected Boolean estElimine;
	protected int idCarteDivinite;
	public LaMain laMain = new LaMain();
	public boolean sacrifice=true;

	public Joueur(int id, String nom, int age) {
		this.id = id;
		this.nom = nom;
		this.age = age;
	}

	public String getNom() {
		return this.nom;
	}

	public int getId() {
		return this.id;
	}

	public void setPtAction_Jour(int ptAction) {
		this.ptAction_Jour =this.ptAction_Jour + ptAction;
	}

	public int getPtAction_Jour() {
		return this.ptAction_Jour;
	}
	public void setPtAction_Nuit(int ptAction) {
		this.ptAction_Nuit =this.ptAction_Nuit+ ptAction;
	}

	public int getPtAction_Nuit() {
		return this.ptAction_Nuit;
	}
	public void setPtAction_Neant(int ptAction) {
		this.ptAction_Neant =this.ptAction_Neant+ ptAction;
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

	public String getPtActionOrigine() {
		return ptActionOrigine;
	}

	public void setPtActionOrigine(String ptActionOrigine) {
		this.ptActionOrigine = ptActionOrigine;
	}

	public void jouer(Partie partie) {

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Joueur n." + this.id + " : ");
		sb.append(this.nom + " ");
		sb.append("(" + this.age + " ans) | ");
		sb.append("Point Prière: " + this.ptPriere + " | ");
		sb.append("Point Action: " + this.ptAction + " (origine: " + this.ptActionOrigine + ")\n");
		return sb.toString();
	}
	public void sacrifierCroyant(CarteAction a){
		//rut me cai carte di
		//
		if(this.sacrifice )
	}
}
