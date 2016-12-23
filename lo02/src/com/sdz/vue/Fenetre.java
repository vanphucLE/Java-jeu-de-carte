package com.sdz.vue;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Fenetre extends JFrame implements ActionListener {

	public static final String strImagePathEntree = "images/fenetreEntree.PNG";

	private FenetreConfig fenetreConfig;
	private JMenuItem mntmNouveau;
	private JMenuItem mntmFermer;
	private JMenuItem mntmPropos;
	private ImageIcon bg;
	private JPanel jpanel;

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
		setBounds(100, 100, 753, 540);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFiche = new JMenu("Fiche");
		mnFiche.setMnemonic(KeyEvent.VK_F);
		menuBar.add(mnFiche);

		this.mntmNouveau = new JMenuItem("Nouveau");
		mntmNouveau.addActionListener(this);
		mnFiche.add(mntmNouveau);

		JSeparator separator = new JSeparator();
		mnFiche.add(separator);

		this.mntmFermer = new JMenuItem("Fermer");
		mntmFermer.addActionListener(this);
		mnFiche.add(mntmFermer);

		JMenu mnAide = new JMenu("Aide");
		mnAide.setMnemonic(KeyEvent.VK_A);
		menuBar.add(mnAide);

		this.mntmPropos = new JMenuItem("À propos");
		mntmPropos.addActionListener(this);
		mnAide.add(mntmPropos);

		fenetreConfig = new FenetreConfig();
		this.setPanelEntree();
	}

	public void setPanelEntree() {

		this.setBgEntree(new ImageIcon(strImagePathEntree));
		ImageIcon bPlay = new ImageIcon("images/bplay.gif");
		JButton button = new JButton(bPlay);
		button.setBounds(748, 837, 418, 80);
		getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenetreConfig.setVisible(true);
			}
		});
	}

	public void setBgEntree(ImageIcon bg) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem) e.getSource();
		if (item == mntmNouveau) {
			JOptionPane.showMessageDialog(null, "Vous voulez ouvrir un nouveau?");
		} else if (item == mntmFermer) {
			System.exit(0);
		} else if (item == mntmPropos) {
			JOptionPane.showMessageDialog(null, "Vous voulez ouvrir un nouveau?");
		}
	}

}
