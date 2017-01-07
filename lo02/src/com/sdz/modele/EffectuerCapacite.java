package com.sdz.modele;

import javax.swing.JOptionPane;

import com.sdz.cartes.CarteAction;

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
}
