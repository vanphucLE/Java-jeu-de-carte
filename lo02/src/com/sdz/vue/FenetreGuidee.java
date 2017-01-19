package com.sdz.vue;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sdz.cartes.CarteAction;
import com.sdz.controler.Controler;
import com.sdz.modele.Joueur;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.JoueurVirtuel;

/*
 * l'objet de cette classe va contenir tous les cartes guides spirituels et cartes Croyants lors d'un la guidee d'un carte Guide Spirituel
 * @author LE Van Phuc
 */
public class FenetreGuidee extends JFrame implements Observer {

	private JPanel contentPane;
	private Joueur joueur;
	private Controler ctrl;
	private JoueurPhysique jP;

	public FenetreGuidee(Joueur joueur) {
		this.joueur = joueur;
		this.setTitle("Les cartes Guidée");
		this.setResizable(false);
		this.setLocation(200, 50);
		this.setBounds(50, 50, 1839, 916);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		// contentPane.setBackground(Color.GREEN);
		setContentPane(contentPane);

	}

	public void setControler(Controler ctrl) {
		this.ctrl = ctrl;
		this.jP = (JoueurPhysique) this.ctrl.getPartie().getListeJoueurs().get(0);
	}

	public void dessinerCarteGuidee() {
		int indice = -1;
		for (int k = 0; k <= 3; k++) {
			for (int m = 0; m <= 3; m++) {
				if (this.joueur.getLaMain().getListeGuideSpirituelGuider().size() - 1 == indice) {
					break;
				}
				indice++;
				// dessinier carte GuideSpirituel
				JButton button = new JButton();
				button.setBounds(460 * m + 10, 215 * k + 10, 150, 210);
				try {
					CarteAction carte = joueur.getLaMain().getListeGuideSpirituelGuider().get(indice);
					BufferedImage image = ImageIO.read(new File("cartes/"
							+ this.joueur.getLaMain().getListeGuideSpirituelGuider().get(indice).getId() + ".PNG"));
					ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 0, 0, 0));
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String action = jP.getActionEnTrain();
							if (!joueur.estBot()) {
								if (action.equals("sacrifier")) {
									ctrl.sacrifier(carte);
								} else if (action.equals("sacrifierGuideSpirituel")) {
									if (carte.getEstSacrifie()) {
										ctrl.sacrifierCarte_special(joueur, carte);
									} else {
										JOptionPane.showMessageDialog(null,
												"Vous ne pouvez pas sacirifier cette Carte!");
									}
								} else if (action.equals("recupererGuideSpirituel")) {
									ctrl.recupererGuideSpirituel(joueur, carte);
								} else if (action.equals("sacrifierGuideSpirituel")) {
									jP.sacrifierGuideSpirit(carte.getId(), ctrl.getPartie());
								} else if (action.equals("recupererGuideSpirituel2")) {
									ctrl.recupererGuideSpirituel2(joueur, carte);
								} else if (action.equals("choisirGuideSpirituelEchanger_1")) {
									ctrl.choisirGuideSpirituelEchanger_1(carte);
								} else if (action.equals("beneficierSansSacrifier")) {
									ctrl.beneficierSansSacrifier(carte);
								}

							} else {
								if (jP.getActionEnTrain().equals("choisirGuideSpirituelEchanger_1")) {
									ctrl.choisirGuideSpirituelEchanger_2(carte, joueur);
								} else if (action.equals("empercherGuideSpirituelChaosMystique")) {
									ctrl.empecherSacrifier(carte);
								} else if (action.equals("empercherGuideSpirituelHumainSymboles")) {
									ctrl.empecherSacrifier(carte);
								} else if (action.equals("defausserGuideSpirituel")) {
									ctrl.deffauserGuideSpirituel(joueur, carte);
								} else if (action.equals("sacrifierGuideSpirituelCHAOS")) {
									ctrl.sacrifierGuideSpirituelCHAOS(joueur, carte);
								}

							}
						}
					});
					this.contentPane.add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
				int i = -1;
				// dessiner les cartes Croyants
				for (CarteAction carte : this.joueur.getLaMain().getListeCroyantGuidee().get(indice)) {
					i++;
					button = new JButton();
					button.setBounds(460 * m + 162 + 60 * i, 215 * k + 10, 150, 210);
					try {
						BufferedImage image = ImageIO.read(new File("cartes/" + carte.getId() + ".PNG"));
						ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
						button.setIcon(icon);
						button.setMargin(new Insets(0, 0, 0, 0));
						button.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								String action = jP.getActionEnTrain();
								if (!joueur.estBot()) {
									// Panel du joueur physique
									if (action.equals("sacrifier")) {
										if (carte.getEstSacrifie()) {
											ctrl.sacrifier(carte);
										} else {
											JOptionPane.showMessageDialog(null,
													"Vous ne pouvez pas sacirifier cette Carte!");
										}
									} else if (action.equals("sacrifierCroyant")) {
										if (carte.getEstSacrifie()) {
											ctrl.sacrifierCarte_special(joueur, carte);
										} else {
											JOptionPane.showMessageDialog(null,
													"Vous ne pouvez pas sacirifier cette Carte!");
										}
									}
								} else {
									// Panel du joueur virtuel
									if (action.equals("empercherCroyantNatureMystique")) {
										ctrl.empecherSacrifier(carte);
									} else if (action.equals("empercherCroyantHumainMystique")) {
										ctrl.empecherSacrifier(carte);
									} else if (action.equals("beneficierCapacite")) {
										ctrl.beneficierCapacite(joueur, carte);
									}
								}
							}
						});
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
