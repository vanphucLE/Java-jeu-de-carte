package com.sdz.vue;

import java.awt.Color;
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

import com.sdz.controler.Controler;
import com.sdz.modele.JoueurPhysique;
import com.sdz.modele.Partie;

public class PanelLancerDe extends JPanel implements Observer {

	private JLabel lblDe;
	private Partie partie;
	private Controler ctr;
	private String faceDe;

	public PanelLancerDe(Controler ctr) {
		this.ctr = ctr;
		this.partie = this.ctr.getPartie();
		this.setLayout(null);
		this.setSize(200, 132);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);

		JButton btnLancer = new JButton("Lancer le dé!");
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!partie.getJoueurEncours().estBot()) {
					JoueurPhysique jP = (JoueurPhysique) partie.getJoueurEncours();
					if (jP.getActionEnTrain().equals("lancerDe")) {
						ctr.lancerDe();
					}
				}
			}
		});
		btnLancer.setBounds(0, 0, 200, 25);
		add(btnLancer);

		lblDe = new JLabel("");
		lblDe.setBounds(39, 30, 123, 95);
		this.setBgLabel("images/de.PNG");
	}

	public void setBgLabel(String link) {
		try {
			BufferedImage image = ImageIO.read(new File(link));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(123, 95, image.SCALE_SMOOTH));
			lblDe.setIcon(icon);
			add(lblDe);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
		this.repaint();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.faceDe = this.partie.getFaceDe();
		if (this.faceDe.equals("Jour")) {
			this.setBgLabel("images/Jour.png");
		} else if (this.faceDe.equals("Nuit")) {
			this.setBgLabel("images/Nuit.png");
		} else if (this.faceDe.equals("Néant")) {
			this.setBgLabel("images/Neant.png");
		} else if (this.faceDe.equals("3face")) {
			this.setBgLabel("images/de.png");
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.repaint();
		this.validate();
	}

}
