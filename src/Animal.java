import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public abstract class Animal {
	
	int vie;			// points de vie actuels de l'animal
	int vieInit;		// vie initiale de l'animal
	int[][] dirs = {{1,0},{0,1},{-1,0},{0,-1}}; // tableau utile pour gérer les 4 points cardinaux
	int x,y;
	private boolean mouvement = false;
	/*
	 * Constructeur.
	 * Doit initialiser la vie de l'animal à vInit, et mémoriser la valeur de la vie initiale.
	 */
	public Animal(int vInit) {
		vie = vInit; // initialise la vie à vieInit
		vieInit = vInit;
	}
	
	
	
	public boolean getMouvement() {
		return mouvement; // retourne le mouvement de l'animal 
	}
	
	public void setMouvement(boolean mouvement) {
		this.mouvement = mouvement; //méthode de modification de la valeur de mouvement 
	}
	
	
	/*
	 * Action de l'animal.
	 * Sa vie est diminuée d'une unité, 
	 * et peut mourir (disparaitre de la grille) si sa vie arrive à 0.
	 * Il essaye de se déplacer dans une cellule voisine, si cela est possible.
	 * Il essaye de manger si cela est possible, et augmente sa vie le cas échéant.
	 * Il essaye de se reproduire si cela est possible, et diminue sa vie de vieInit unités 
	 * (utilisées pour créer le nouvel individu) le cas échéant. Le nouvel individu est placé 
	 * dans la case actuelle de l'animal ; aussi l'animal ne peut-il se reproduire que s'il
	 * se déplace (et libère une case).
	 *  
	 * plateau: le plateau de jeu
	 * c: la case où se trouve actuellement l'animal
	 * i: ligne de la case dans la matrice
	 * j: colonne de la case dans la matrice 
	 */
	public void uneEtape(Plateau plateau, Case c, int i, int j) {
		if (estVivant() == true && mouvement == false) { // si l'animal est vivant et que l'animal n'a pas encore bougé
			vie--; //il perd un point de vie à chaque itération 
			Case memoire; // on appelle la case mémoire qui va retenir la case où l'animal est actuellement avant de se déplacer
			int di,dj; // les coordonnées de déplacement 
			int nombre; 
			nombre = (int) Math.floor(Math.random()*4); // on choisit un nombre aléatoire entre 0 et 3 
			di = dirs[nombre][0]; // on choisit donc di et dj aléatoirement parmis la matrice dirs
			dj = dirs[nombre][1];
			memoire = plateau.getCase(i, j); // on mémorise la case sur laquelle l'animal est 
			i = i+di; // on actualise la position de l'animal 
			j = j+dj;
			if (plateau.getCase(i,j) != null) { // s'il y a un animal sur la nouvelle case où l'animal compte se déplacer 
				c = plateau.getCase(i,j); // on update la case c sur les nouvelles coordonnées 
				if (peutBouger(c)) {     // si l'animal peut bouger 
					if (memoire.getAnimal().peutManger(c) == true) { // et si l'animal peut manger 
						memoire.getAnimal().setVie(memoire.getAnimal().manger(c)); // l'animal mange ce qu'il y a sur la case c et récupère la vie de l'animal mangé
					}
					c.setAnimal(memoire.getAnimal()); // quoi qu'il en soit on déplace l'animal sur la case c 
					memoire.setAnimal(null);         // et on l'efface de son ancienne case 
					mouvement = true;				// mouvement prend la valeur true donc cet animal ne va pas se redéplacer avant la prochaine itération
					if (c.getAnimal() != null && c.getAnimal().peutReproduire() == true) // si l'animal est sur la case c et qu'il peut se reproduire
						c.getAnimal().reproduire(memoire, c);  // il fait naître un animal sur l'ancienne case 
				}
				
			}

		}
		if (estVivant() == false) {			// si l'animal n'est pas vivant 
			c.setAnimal(null);				// on le tue 
		}
		
			
	}

	/*
	 * Méthodes abstraites à implémenter dans les classe dérivées.
	 */
	
	// Renvoie vrai si l'animal peut se déplacer sur la case cible c2:
	public abstract boolean peutBouger(Case c2);
	// Renvoie vrai si l'animal peut manger ce qui se trouve sur la case cible c2:
	public abstract boolean peutManger(Case c2);
	// Mange effectivement ce qui se trouve sur la case cible c2, 
	// et retourne le nombre de points de vie ainsi gagnés:
	public abstract int manger(Case c2);
	// Retourne un nouvel animal de la même classe que l'animal courant:
	
	public abstract void reproduire(Case c1,Case c2);
	
	/*
	 * Retourne vrai si l'animal possède suffisamment de points de vie pour se reproduire.
	 * Ses points de vie doivent être supérieurs à 2 fois sa vieInit. 
	 */
	public boolean peutReproduire() {
		if (vie>=vieInit*2)  // si l'animal a une vie au moins 2 fois supérieure à sa vie initiale 
			return true;	// il peut se reproduire 
		else 				// sinon il trace sa life sans avoir besoin de fonder une famille 
			return false;
	}
	
	
	public void setVie (int vie) {
		this.vie = vie; // méthode d'altération de la vie d'un animal 
	}
	
	public int getVie () {
		return vie;	//récupère la vie d'un animal 
	}
	
	
	/*
	 * Dessine l'animal dans le rectangle (x,y, x+dx,y+dy). 
	 */
	public abstract void dessinePetit(Graphics g, int x, int y, int dx, int dy) throws IOException;
	
	public abstract void dessineGrand(Graphics g, int x, int y, int dx, int dy) throws IOException;
	
	/*
	 * Retourne vrai si l'animal est vivant (sa vie est positive).
	 */
	public boolean estVivant(){
		if (vie != 0) // si sa vie est non nulle
			return true; // l'animal est vivant 
		else 
			return false;
	}

}
