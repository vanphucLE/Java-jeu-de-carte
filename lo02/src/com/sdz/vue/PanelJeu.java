package com.sdz.vue;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sdz.controler.Controler;

public class PanelJeu extends JPanel {
	private Fenetre fenetrePc;
	private PanelLancerDe panelLancerDe;
	private Controler ctr;
	private PanelJP panelJP;
	private PanelJVCote panelJVCoteG;
	private PanelJVCote panelJVCoteD;
	private PanelJVFace panelJVFaceG;
	private PanelJVFace panelJVFaceC;
	private PanelJVFace panelJVFaceD;
	private PanelEspaceCommun panelEC;
	private PanelCarteJouee panelC;
	
	public PanelJeu(){
		this.dessinerJoueurFC();
		this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVFaceC);
		this.dessinerJoueursCote();
		this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
		this.ctr.getPartie().getListeJoueurs().get(5).addObserver(this.panelJVCoteD);
		this.dessinerJoueursFGD();
		this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceG);
		this.ctr.getPartie().getListeJoueurs().get(4).addObserver(this.panelJVFaceD);
	}

	public PanelJeu(Fenetre fenetrePc, Controler ctr) {
		this.ctr = ctr;
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);

		this.setBackground(Color.GREEN);
		this.setBorder(lineBorder);
		this.fenetrePc = fenetrePc;
		this.setLayout(null);

		// JButton btnAfficherEspaceComun = new JButton("Afficher Espace
		// Comun");
		// btnAfficherEspaceComun.setBounds(this.fenetrePc.getWidth()/2-82,
		// this.fenetrePc.getHeight()/2-25, 165, 25);
		// add(btnAfficherEspaceComun);

		// joueur physique
		this.panelJP = new PanelJP();
		panelJP.setLocation(338, this.fenetrePc.getHeight() - 310);
		add(panelJP);

		int nbJ = this.ctr.getPartie().getNbJoueur();

		switch (nbJ) {
		case 2:
			this.dessinerJoueurFC();
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVFaceC);
			break;
		case 3:
			this.dessinerJoueursFGD();
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVFaceG);
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceD);
			break;
		case 4:
			this.dessinerJoueurFC();
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceC);
			this.dessinerJoueursCote();
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
			this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVCoteD);
			break;
		case 5:
			this.dessinerJoueursCote();
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
			this.ctr.getPartie().getListeJoueurs().get(4).addObserver(this.panelJVCoteD);
			this.dessinerJoueursFGD();
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceG);
			this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVFaceD);
			break;
		case 6:
			this.dessinerJoueurFC();
			this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVFaceC);
			this.dessinerJoueursCote();
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
			this.ctr.getPartie().getListeJoueurs().get(5).addObserver(this.panelJVCoteD);
			this.dessinerJoueursFGD();
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceG);
			this.ctr.getPartie().getListeJoueurs().get(4).addObserver(this.panelJVFaceD);
			break;
		}

		this.panelEC = new PanelEspaceCommun();
		this.ctr.getPartie().getEspaceCommun().addObserver(this.panelEC);
		panelEC.setLocation(220, 222);
		add(panelEC);

		this.panelLancerDe = new PanelLancerDe(this.ctr);
		this.panelLancerDe.setLocation(1500, 222);
		add(this.panelLancerDe);
		this.ctr.getPartie().addObserver(this.panelLancerDe);

		this.panelC = new PanelCarteJouee();
		this.panelC.setLocation(1500, 359);
		add(panelC);

	}

	public void dessinerJoueurFC() {
		this.panelJVFaceC = new PanelJVFace();
		panelJVFaceC.setLocation(this.fenetrePc.getWidth() / 2 - 296, 6);
		add(panelJVFaceC);
	}

	public void dessinerJoueursFGD() {
		this.panelJVFaceG = new PanelJVFace();
		panelJVFaceG.setLocation(6, 6);
		add(panelJVFaceG);

		this.panelJVFaceD = new PanelJVFace();
		panelJVFaceD.setLocation(this.fenetrePc.getWidth() - 608, 6);
		add(panelJVFaceD);
	}

	public void dessinerJoueursCote() {
		this.panelJVCoteG = new PanelJVCote();
		panelJVCoteG.setLocation(6, 218);
		add(panelJVCoteG);
		

		this.panelJVCoteD = new PanelJVCote();
		panelJVCoteD.setLocation(this.fenetrePc.getWidth() - 220, 218);
		add(panelJVCoteD);
		
	}

	public void ajouterVueJVSommet() {

	}

	public static void main(String[] args) {
		Fenetre b = new Fenetre();
		b.setVisible(true);
	}
}
