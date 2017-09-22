import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JPanel;


/*
 * Classe permettant de contenir la représantation graphique du plateau.
 */
class PlateauPanel extends JPanel {
	Plateau plateau; // Le plateau contenant la matrice de jeu
	int largeur = 550;	// largeur en pixels du plateau graphique
	int hauteur = 550; // hauteur en pixels du plateau graphique
	
	/* 
	 * Constructeur.
	 */
	public PlateauPanel(Plateau plat) {
		plateau = plat;
		setPreferredSize(new Dimension(largeur, hauteur));
	}
	
	/*
	 * Dessine le plateau graphique.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			plateau.dessine(g, largeur, hauteur);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}