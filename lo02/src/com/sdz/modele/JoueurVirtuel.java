package com.sdz.modele;

public class JoueurVirtuel extends Joueur {
	// private Stategie stagie;
	public JoueurVirtuel(int id, String nom, int age) {
		super(id,nom,age);
		this.laMain=new LaMain();
	}
	
	@Override
	public void jouer(Partie partie){
		
	}
	public void choisirCarte(){
		
	}
	public void JoueurCapaSpe(){
		
	}
	

	public LaMain getLaMain() {
		return laMain;
	}

	public void setLaMain(LaMain laMain) {
		this.laMain = laMain;
	}
}
