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
import javax.swing.JPanel;

import com.sdz.cartes.CarteAction;
import com.sdz.modele.JoueurVirtuel;

public class PanelJV extends JPanel implements Observer {

	private JoueurVirtuel jV;

	public PanelJV(JoueurVirtuel jV) {
		super();
		this.jV = jV;
	}

	public void dessinerCarteAction() {
		// LinkedList<JButton> boutonsCarte = new LinkedList<JButton>();
		int indice = 0;
		for (CarteAction carte : this.jV.getLaMain().getListeCarteA()) {
			indice++;
			JButton button = new JButton("New button");
			button.setBounds(28 + 152 * indice, 32, 150, 210);
			try {
				BufferedImage image = ImageIO.read(new File("cartes/" + carte.getId() + ".PNG"));
				ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 210, image.SCALE_SMOOTH));
				button.setIcon(icon);
				button.setMargin(new Insets(0, 10, 0, 0));
				add(button);
			} catch (IOException ex) {
				Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		this.removeAll();
		this.dessinerCarteAction();
		this.repaint();
		// this.validate();
	}

}
