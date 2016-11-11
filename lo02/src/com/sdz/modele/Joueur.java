package com.sdz.modele;

public class Joueur {
	protected int id;
	protected String nom;
	protected int age;
	protected int ptPriere;
	protected int ptAction;
	protected Boolean estElimine;
	protected int idCarteDivinite;
	
	public void setNom(String nom){
		this.nom=nom;
	}
	public String getNom(){
		return this.nom;
	}
	public int getId(){
		return this.id;
	}
	public void piocherDivinite(int id){
		this.id=id;
	}
	public void setPtAction(int ptAction){
		this.ptAction=ptAction;
	}
	public int getPtAction(){
		return this.ptAction;
	}
	public void setPtPriere(int ptPriere){
		this.ptPriere=ptPriere;
	}
	public int getPtPriere(){
		return this.ptPriere;
	}
	public void setElimine(Boolean estElimie){
		this.estElimine=estElimine;
	}
	public Boolean getElimine(){
		return this.estElimine;
	}
	
}
