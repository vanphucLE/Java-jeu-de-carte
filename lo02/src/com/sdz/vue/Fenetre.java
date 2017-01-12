package com.sdz.vue;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.sdz.controler.Controler;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurVirtuel;
import com.sdz.modele.Partie;

public class Fenetre extends JFrame implements  Runnable {

	public static final String strImagePathEntree = "images/fenetreEntree.PNG";
	public static final String strImagePathTable = "images/table.jpg";

	private Thread thread;
	private FenetreConfig fenetreConfig;
	private JMenuItem mntmNouveau;
	private JMenuItem mntmFermer;
	private JMenuItem mntmPropos;
	private ImageIcon bg;
	private JPanel jpanel;
	private Controler ctrl;
	private Partie partie;
	private PanelJeu panelJeu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fenetre frame = new Fenetre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */

	public Fenetre() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 753, 540);
		this.setSize(this.getMaximumSize());
		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFiche = new JMenu("Fiche");
		mnFiche.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFiche);

		this.mntmNouveau = new JMenuItem("Nouveau");
		mntmNouveau.addActionListener(new NouveauListener());
		mnFiche.add(mntmNouveau);

		JSeparator separator = new JSeparator();
		mnFiche.add(separator);

		this.mntmFermer = new JMenuItem("Fermer");
		mntmFermer.addActionListener(new FermerListener());
		mnFiche.add(mntmFermer);

		JMenu mnAide = new JMenu("Aide");
		mnAide.setMnemonic(KeyEvent.VK_A);
		menuBar.add(mnAide);

		this.mntmPropos = new JMenuItem("� propos");
		mntmPropos.addActionListener(new ProposListener());
		mnAide.add(mntmPropos);

		fenetreConfig = new FenetreConfig(this);
		this.setPanelEntree();
	}

	public void commencerPartie() {
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		this.partie.commencer();
	}

	
	public Controler getCtrl() {
		return ctrl;
	}

	public void setCtrl(Partie partie) {
		this.ctrl = new Controler(partie);
		this.partie=partie;
	}

	public void setPanelEntree() {
		this.setBg(new ImageIcon(strImagePathEntree));
		ImageIcon bPlay = new ImageIcon("images/bplay.png");
		JButton button = new JButton(bPlay);
		button.setBounds(748, 837, 418, 80);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetreConfig.setVisible(true);
			}
		});
	}

	public void setPanelJeu() {
		this.getContentPane().removeAll();
		// this.setBg(new ImageIcon(this.strImagePathTable));
		panelJeu = new PanelJeu(this, this.ctrl);
		this.setContentPane(panelJeu);
		this.ctrl.setPanelJeu(this.panelJeu);
		this.repaint();
		this.validate();
		for(Joueur j:this.partie.getListeJoueurs()){
			if(j.estBot()){
				JoueurVirtuel jV=(JoueurVirtuel)j;
				jV.setPanelJeu(panelJeu);
			}
		}
	}

	public void setBg(ImageIcon bg) {
		this.bg = bg;
		jpanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (bg != null) {
					g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			}
		};
		jpanel.setLayout(null);
		this.setContentPane(jpanel);
	}

	class NouveauListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "Vous voulez ouvrir un nouveau?");
		}
	}

	class FermerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class ProposListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String mess = "<html><h2>Pr�sentation du jeu</h2>"
					+ "Vous incarnez des Divinit�s, qui sontcaract�ris�es parleurOrigine (Jour, Nuit,\n"
					+ "Aube ou Cr�puscule) qui exprime leur filiation, et leurs Dogmes (3 parmi :\n"
					+ "Nature, Humain, Symboles, Mystique, Chaos) qui d�finissent leurs croyances.\n"
					+ "Chaque Divinit� poss�dent une capacit� sp�ciale, un pouvoir utilisable une\n"
					+ "unique fois pendantlapartie.\n"
					+ "Le but du jeu est d��liminer les autres Divinit�s et de prendre la place du Haut\n"
					+ "Dieuenr�cup�rantles pri�res d�unmaximumde Croyants.\n"
					+ "Pour cela, les joueurs vont cr�er des Croyants, qui seront mis en commun au\n"
					+ "milieu de la table. Par la suite, les joueurs pourront cr�er des Guides Spirituels,\n"
					+ "dont le r�le est d�amener � une Divinit� un nombre variable de cartes de\n"
					+ "Croyants. A cela s�ajoute des cartes Deus Ex qui modifient les rapports de force\n"
					+ "encours de partie.\n"
					+ "Lorsqu�une carte Apocalypse est pos�e, un joueur est �limin� (4 joueurs ou\n"
					+ "plus) ouun joueurgagne lapartie (2 ou3 joueurs) en fonction des points de Pri�re\n"
					+ "apport�s parles Croyants de chaque Divinit�.\n\n"
					+ "<html><p><b>Auteurs: </b><i>LE Van Phuc - TRAN Hoang Doan Hung</i></p>"
					+ "<p>Version 1.0 - Projet LO02 A16 - Universit� de Technologie de Troyes";

			JOptionPane.showMessageDialog(null, mess, "� propos", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
