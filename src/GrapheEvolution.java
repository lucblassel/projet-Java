import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


/*
 * Classe permettant de contenir la représantation graphique du plateau.
 */
class GrapheEvolution extends JPanel {
	Plateau plateau; // Le plateau contenant la matrice de jeu
	int largeur = 600;	// largeur en pixels du plateau graphique
	int hauteur = 100; // hauteur en pixels du plateau graphique
	int x=0;
	
	
	/* 
	 * Constructeur.
	 */
	public GrapheEvolution (Plateau plat) {
		plateau = plat;
		setPreferredSize(new Dimension(largeur, hauteur));
	}
	
	
	/*
	 * Dessine le plateau graphique.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		plateau.dessineGraphe(g,x);
		x++;
	}
}