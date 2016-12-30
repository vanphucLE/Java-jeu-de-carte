package com.sdz.vue;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PanelLancerDe extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelLancerDe() {
		this.setLayout(null);
		this.setSize(200, 132);
		Border lineBorder = BorderFactory.createLineBorder(Color.blue);
		this.setBorder(lineBorder);
		
		JButton btnLancer = new JButton("Lancer le dé!");
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLancer.setBounds(0, 0, 200, 25);
		add(btnLancer);
		
		JLabel lblDe = new JLabel("");
		lblDe.setBounds(39, 30, 123, 95);
		try {
			BufferedImage image = ImageIO.read(new File("images/de.PNG"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(123, 95, image.SCALE_SMOOTH));
			lblDe.setIcon(icon);
			add(lblDe);
		} catch (IOException ex) {
			Logger.getLogger(PanelJP.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(j.getMaximumSize());
		j.getContentPane().add(new PanelLancerDe());
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
