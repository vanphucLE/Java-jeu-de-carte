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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sdz.cartes.CarteAction;
import com.sdz.controler.Controler;
import com.sdz.modele.EspaceCommun;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.Partie;

public class PanelEspaceCommun extends JPanel implements Observer {

	private EspaceCommun espaceCM;
	private Partie partie;
	private Controler ctrl;

	public PanelEspaceCommun(Partie partie, Controler ctrl) {
		this.ctrl = ctrl;
		this.partie = partie;
		this.espaceCM = this.partie.getEspaceCommun();
		this.setLayout(null);
		this.setSize(1270, 546);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);

		// this.setBackground(Color.GRAY);
		this.peindreEspace();
	}

	public void peindreEspace() {
		this.removeAll();

		JLabel lblEspaceCommun = new JLabel("ESPACE COMMUN");
		lblEspaceCommun.setBounds(572, 241, 145, 68);
		add(lblEspaceCommun);
		int indice = -1;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.espaceCM.getListeCartesPret().size() - 1 == indice) {
					break;
				}
				indice++;
				JButton button = new JButton("");
				button.setBounds(10 + 140 * i, 182 * j, 130, 182);
				try {
					BufferedImage image = ImageIO.read(
							new File("cartes/" + this.espaceCM.getListeCartesPret().get(indice).getId() + ".PNG"));
					CarteAction carte = espaceCM.getListeCartesPret().get(indice);
					ImageIcon icon = new ImageIcon(image.getScaledInstance(130, 182, image.SCALE_SMOOTH));
					button.setIcon(icon);
					button.setMargin(new Insets(0, 0, 0, 0));
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (!partie.getJoueurEncours().estBot()) {
								JoueurPhysique jP = (JoueurPhysique) partie.getJoueurEncours();
								if (jP.getActionEnTrain().equals("guiderCroyant")) {
									ctrl.guiderCroyant(carte);
								}
							}
						}
					});
					add(button);
				} catch (IOException ex) {
					Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			if (this.espaceCM.getListeCartesPret().size() - 1 == indice) {
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.peindreEspace();
		this.repaint();
		this.validate();
	}
}
