package com.sdz.vue;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sdz.controler.Controler;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.JoueurVirtuel;
import com.sdz.modele.Partie;

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
	private Partie partie;

	public PanelJeu(Fenetre fenetrePc, Controler ctr) {
		this.partie=ctr.getPartie();
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
		System.out.println(this.ctr.getPartie().getListeJoueurs());
		this.panelJP = new PanelJP((JoueurPhysique) this.ctr.getPartie().getListeJoueurs().get(0));
		panelJP.setLocation(268, this.fenetrePc.getHeight() - 310);
		add(panelJP);
		this.ctr.getPartie().getListeJoueurs().get(0).addObserver(this.panelJP);

		int nbJ = this.ctr.getPartie().getNbJoueur();

		switch (nbJ) {
		case 2:
			this.dessinerJoueurFC((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(1));
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVFaceC);
			break;
		case 3:
			this.dessinerJoueursFGD((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(1),
					(JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(2));
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVFaceG);
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceD);
			break;
		case 4:
			this.dessinerJoueurFC((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(2));
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceC);
			this.dessinerJoueursCote((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(1),
					(JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(3));
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
			this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVCoteD);
			break;
		case 5:
			this.dessinerJoueursCote((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(1),
					(JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(4));
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
			this.ctr.getPartie().getListeJoueurs().get(4).addObserver(this.panelJVCoteD);
			this.dessinerJoueursFGD((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(2),
					(JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(3));
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceG);
			this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVFaceD);
			break;
		case 6:
			this.dessinerJoueurFC((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(3));
			this.ctr.getPartie().getListeJoueurs().get(3).addObserver(this.panelJVFaceC);
			this.dessinerJoueursCote((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(1),
					(JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(5));
			this.ctr.getPartie().getListeJoueurs().get(1).addObserver(this.panelJVCoteG);
			this.ctr.getPartie().getListeJoueurs().get(5).addObserver(this.panelJVCoteD);
			this.dessinerJoueursFGD((JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(2),
					(JoueurVirtuel) this.ctr.getPartie().getListeJoueurs().get(4));
			this.ctr.getPartie().getListeJoueurs().get(2).addObserver(this.panelJVFaceG);
			this.ctr.getPartie().getListeJoueurs().get(4).addObserver(this.panelJVFaceD);
			break;
		}

		this.panelEC = new PanelEspaceCommun(this.partie.getEspaceCommun());
		this.ctr.getPartie().getEspaceCommun().addObserver(this.panelEC);
		panelEC.setLocation(220, 222);
		add(panelEC);
		this.partie.getEspaceCommun().addObserver(this.panelEC);

		this.panelLancerDe = new PanelLancerDe(this.ctr);
		this.panelLancerDe.setLocation(1500, 222);
		add(this.panelLancerDe);
		this.ctr.getPartie().addObserver(this.panelLancerDe);

		this.panelC = new PanelCarteJouee();
		this.panelC.setLocation(1500, 359);
		add(panelC);

	}

	public void dessinerJoueurFC(JoueurVirtuel jV) {
		this.panelJVFaceC = new PanelJVFace(jV);
		panelJVFaceC.setLocation(this.fenetrePc.getWidth() / 2 - 316, 6);
		add(panelJVFaceC);
	}

	public void dessinerJoueursFGD(JoueurVirtuel jV1, JoueurVirtuel jV2) {
		this.panelJVFaceG = new PanelJVFace(jV1);
		panelJVFaceG.setLocation(6, 6);
		add(panelJVFaceG);

		this.panelJVFaceD = new PanelJVFace(jV2);
		panelJVFaceD.setLocation(this.fenetrePc.getWidth() - 638, 6);
		add(panelJVFaceD);
	}

	public void dessinerJoueursCote(JoueurVirtuel jV1, JoueurVirtuel jV2) {
		this.panelJVCoteG = new PanelJVCote(jV1);
		panelJVCoteG.setLocation(6, 226);
		add(panelJVCoteG);

		this.panelJVCoteD = new PanelJVCote(jV2);
		panelJVCoteD.setLocation(this.fenetrePc.getWidth() - 220, 226);
		add(panelJVCoteD);

	}

	public void ajouterVueJVSommet() {

	}

	public static void main(String[] args) {
		Fenetre b = new Fenetre();
		b.setVisible(true);
	}
}
