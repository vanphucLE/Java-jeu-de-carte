package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.EmptyBorder;

import com.sdz.cartes.CarteAction;
import com.sdz.cartes.GuideSpirituel;
import com.sdz.controler.Controler;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurPhysique;

public class FenetreGuidee extends JFrame implements Observer {

	private JPanel contentPane;
	private Joueur joueur;
	private Controler ctrl;

	public FenetreGuidee(Joueur joueur) {
		this.joueur = joueur;
		this.setTitle("Les cartes Guidée");
		this.setResizable(false);
		this.setLocation(200, 50);
		this.setBounds(50, 50, 1839, 916);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
//		contentPane.setBackground(Color.GREEN);
		setContentPane(contentPane);

	}

	public void setControler(Controler ctrl) {
		this.ctrl = ctrl;
	}

	private int indice;

	public void dessinerCarteGuidee() {
		this.indice=-1;
		for (int k = 0; k <= 3; k++) {
			for (int m = 0; m <= 3; m++) {
				if (this.joueur.getLaMain().getListeGuideSpirituelGuider().size() - 1 == indice) {
					break;
				}
				indice++;
				JButton button = new JButton();
				button.setBounds(460 * m + 10, 215 * k + 10, 150, 210);
				try {
					BufferedImage image = ImageIO.read(new File("cartes/"
							+ this.joueur.getLaMain().getListeGuideSpirituelGuider().get(indice).getId() + ".PNG"));
					ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 0, 0, 0));
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (!joueur.estBot()) {
								JoueurPhysique jP = (JoueurPhysique) joueur;
								if (jP.getActionEnTrain().equals("sacrifier")) {
									ctrl.sacrifier(joueur.getLaMain().getListeGuideSpirituelGuider().get(indice));
								}
							}
						}
					});
					this.contentPane.add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
				int i = -1;
				for (CarteAction carte : this.joueur.getLaMain().getListeCroyantGuidee().get(indice)) {
					i++;
					button = new JButton();
					button.setBounds(460 * m + 162 + 60 * i, 215 * k + 10, 150, 210);
					try {
						BufferedImage image = ImageIO.read(new File("cartes/" + carte.getId() + ".PNG"));
						ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
						button.setIcon(icon);
						button.setMargin(new Insets(0, 0, 0, 0));
						this.contentPane.add(button);
					} catch (IOException ex) {
						Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
			if (this.joueur.getLaMain().getListeGuideSpirituelGuider().size() - 1 == indice) {
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.contentPane.removeAll();
		this.dessinerCarteGuidee();
		this.repaint();
		this.validate();
	}

}
