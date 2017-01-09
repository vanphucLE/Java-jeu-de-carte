package com.sdz.modele;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.GuideSpirituel;

public class EffectuerCapacite {
	private Partie partie;

	public EffectuerCapacite(Partie partie) {
		this.partie = partie;
	}

	public void empecherCroyant(Joueur joueur, String dogme1, String dogme2, CarteAction carte) {
		Boolean test = false;
		for (String str : joueur.getLaMain().getCarteDivinite().getDogme()) {
			if (str.equals(dogme1) || str.equals(dogme2)) {
				test = true;
				break;
			}
		}
		if (test) {
			carte.setEstSacrifie(false);
		} else {
			if (!this.partie.getJoueurEncours().estBot()) {
				JOptionPane.showMessageDialog(null, "Joueur " + joueur.getNom() + " n'a pas un des dogmes [" + dogme1
						+ ", " + dogme2 + "]!\nCette Capacité n'est donc pas effectué, elle est annulée!");
			}
		}
	}

	public void recupererGuideSpirituel(Joueur joueur, CarteAction carte) {
		int index = joueur.getLaMain().getListeGuideSpirituelGuider().indexOf(carte);
		joueur.getLaMain().completerCarteAction(carte);
		joueur.getLaMain().getListeGuideSpirituelGuider().remove(carte);
		if (index != -1) {
			partie.getEspaceCommun().getListeCartesPret()
					.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(index));
		}
		joueur.notifyLaMain();
	}

	public void recupererGuideSpirituel2(Joueur joueur, CarteAction carte) {
		int index = joueur.getLaMain().getListeGuideSpirituelGuider().indexOf(carte);
		joueur.getLaMain().completerCarteAction(carte);
		joueur.getLaMain().getListeGuideSpirituelGuider().remove(carte);
		if (index != -1) {
			partie.getJeuDeCartes().getListeCartesAction()
					.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(index));
		}
		joueur.notifyLaMain();
	}

	public void deffauserGuideSpirituel(Joueur joueur, CarteAction carte) {
		int index = joueur.getLaMain().getListeGuideSpirituelGuider().indexOf(carte);
		this.partie.getJeuDeCartes().recupererCarteAction(carte);
		joueur.getLaMain().getListeGuideSpirituelGuider().remove(carte);
		if (index != -1) {
			partie.getEspaceCommun().getListeCartesPret()
					.addAll(joueur.getLaMain().getListeCroyantGuidee().remove(index));
		}
		joueur.notifyLaMain();
	}

	public void sacrifierGuideSpirituelCHAOS(Joueur joueur, CarteAction carte) {
		this.partie.setJoueurDernier(this.partie.getJoueurEncours());
		Boolean test = false;
		for (String dogme : joueur.getLaMain().getCarteDivinite().getDogme()) {
			if (dogme.equals("Chaos")) {
				test = true;
			}
		}
		if (!test) {
			for (String dogme : carte.getDogme()) {
				if (dogme.equals("Chaos")) {
					test = true;
				}
			}
		}
		if (test) {
			if (!this.partie.getJoueurEncours().estBot()) {
				JOptionPane.showMessageDialog(null,
						"Vous avez choisi un carte Guide Spirituel mais sa Divinité ou lui, ils contien dogme Chaos!\nLa capacité speciale est alors annulé!");
			}
		} else {
			if (!this.partie.getJoueurEncours().estBot()) {
				JOptionPane.showMessageDialog(null, "Joueur " + this.partie.getJoueurEncours().getNom()
						+ " vous a choisi pour sacrifier un Guide Spirituel (CHAOS)!");
			}
			this.partie.setJoueurEncours(joueur);
			joueur.sacrifierGuideSpirit(carte.getId(), this.partie);
		}
		this.partie.setJoueurEncours(this.partie.getJoueurDernier());
	}
	
	public void echangerGuideSpirituel(Joueur joueur1, CarteAction carte1,Joueur joueur2, CarteAction carte2){
		int index=joueur1.getLaMain().getListeGuideSpirituelGuider().indexOf(carte1);
		joueur1.getLaMain().getListeGuideSpirituelGuider().remove(index);
		LinkedList<CarteAction> cartesCroyants1=joueur1.getLaMain().getListeCroyantGuidee().remove(index);
		
		index=joueur2.getLaMain().getListeGuideSpirituelGuider().indexOf(carte2);
		joueur2.getLaMain().getListeGuideSpirituelGuider().remove(index);
		LinkedList<CarteAction> cartesCroyants2=joueur2.getLaMain().getListeCroyantGuidee().remove(index);
		
		joueur1.getLaMain().ajouterGuidee(cartesCroyants2, carte2);
		joueur2.getLaMain().ajouterGuidee(cartesCroyants1, carte1);
	}

}
