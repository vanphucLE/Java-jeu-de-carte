package com.sdz.vue;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sdz.cartes.CarteAction;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurVirtuel;

public abstract class PanelJV extends JPanel implements Observer {

	protected JoueurVirtuel jV;
	protected FenetreGuidee fenetreGuidee;

	public PanelJV(JoueurVirtuel jV) {
		super();
		this.jV = jV;
		Joueur j = this.jV;
		this.fenetreGuidee = new FenetreGuidee(j);
		this.jV.addObserver(this.fenetreGuidee);
	}

	public abstract void dessinerCarteAction();

	public abstract void dessinerCarteDivinite();

	public abstract void dessinerPtAction();

	public abstract void dessinerNom();

	@Override
	public void update(Observable o, Object arg) {
		if(jV.getElimine()){
			this.setVisible(false);
		}
		this.removeAll();
		this.dessinerNom();
		this.dessinerPtAction();
		this.dessinerCarteDivinite();
		this.dessinerCarteAction();
		this.repaint();
		this.validate();
	}

}
