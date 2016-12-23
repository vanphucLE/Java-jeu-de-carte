package com.sdz.vue;

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/


import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author tauit_dnmd
 */
public class testFrame extends JFrame {
	public static final String strImagePath = "BoundBall.png";
	ImageIcon background;
	JPanel jpanel;

	public testFrame() {

		background = null;
		this.setSize(300, 200);
		jpanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (background != null) {
					g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
				}
			}
		};
		setContentPane(jpanel);

	}

	public void setBackground(ImageIcon img) {
		this.background = img;
	}

	public static void main(String[] str) {

		testFrame frame = new testFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBackground(new ImageIcon("images/fenetreEntree.PNG"));
		frame.setVisible(true);
		for (int i = 0; i < 10; i++) {
			JButton but = new JButton("A" + i);
			but.setBounds(60 * i + 2, 10, 60, 30);
			but.setVisible(true);
			frame.add(but);
		}

	}

}
