import java.awt.*;

import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class Case {
	private boolean herbe;// présence ou non d'un brin d'herbe sur la case
	double valeur,valeur2; //servent � d�finir les probabilit�s d'apparition des �l�ments � l'initialisation
	String etat = new String(); // sert � donner l'etat de la case dans le programme textuel
	private Animal animal; // animal � cr�er sur la case
	private int nbCases; //sert a avoir la taille du plateau

	

	/*
	 * Constructeur.
	 * Initialise aléatoirement la case avec la présence ou non d'un brin d'herbe
	 * et la présence ou non d'un animal.
	 */
	
	public Case() { 
		
		valeur = Math.random();  
		valeur2 = Math.random();
		if (valeur < 0.70)  //donne la probabilit� de 70% pour que l'herbe pousse
			herbe = true; //si la probabilit� est respect�e, l'herbe pousse sur la case
		else
			herbe = false;
		if (valeur2 < 0.10) // probabilit� de 10% pour les moutons
			animal = new Mouton(100);
		else if (valeur2>=0.10 && valeur2<0.11) // probabilit� de 1% pour les loups
			animal = new Loup(200);
		else 
			animal = null;
	}
	
	public void setNbCases(int nbCases) { //permet de r�cuperer le nombre de case du plateau
		this.nbCases = nbCases; //cela servira pour la detection de la taille du plateau
	}
	
	
	public Animal getAnimal() { //retourne l'animal qui est pr�sent sur la case
		return animal;
	}
	
	public void setAnimal(Animal animal) { //permet de mettre ou de modifier un animal sur la case 
		this.animal = animal;
	}
	
	
	
	
	public boolean getHerbe() { //indique si la case a de l'herbe ou non. 
		return herbe;
	}
	
	
	public boolean setHerbe(boolean herbe) { //permet de mettre ou de retirer l'herbe de la case
		return this.herbe = herbe;
	}
	
	/*
	 * Retourne une chaîne de caractères représentant l'état de la case,
	 * pour affichage.
	 */
	public String afficheTxt() { //permet de representer textuellement la case
		if (herbe == true)
			etat = "H";
		else 
			etat = "O";
		// implémentation bouchon :
		return etat;
	}
	
	/*
	 * Dessine la case, selon l'état actuel de la matrice.
	 */
	public void dessine(Graphics g, int x, int y, int dx, int dy) throws IOException{
		if (herbe == true)  {
			g.setColor(new Color(0,128,0)); // s'il ya de l'herbe on definit la couleur de la case en vert
		}
		else {
			g.setColor(new Color(156, 93, 82)); //en marron s'il ya pas d'herbe
		}
		g.fillRect(x, y, dx, dy); //on dessine la case aux coordonn�es x,y et de dimensions dx,dy
		
		if (animal != null) {
			if (nbCases <= 17) // on regarde la taille du plateau 
				animal.dessineGrand(g,x+(dx/3),y+(dy/3),dx/3,dy/3); //s'il est grand on dessine les animaux en grand
			else 
				animal.dessinePetit(g,x+(dx/3),y+(dy/3),dx/3,dy/3); //sinon on les dessine en petit
				
		}
	}
	
	/*
	 * Modifie l'état de la case courante en fonction de son état actuel.
	 * S'il n'y a pas d'herbe, un brin d'herbe peut pousser avec une probabilité donnée.
	 * Si la case possède un animal, celui-ci peut bouger.
	 */
	public void uneEtape(Plateau plateau, int i, int j) { //execute une �tape du programme au niveau case
		valeur = Math.random(); //permet de g�rer la pousse de l'herbe
		if ( plateau.getCase(i,j).getHerbe() == false) { 
			if (valeur < 0.005)  // si la case n'a pas d'herbe, elle a une probabilit� de 0.5% d'en faire pousser
				plateau.getCase(i,j).setHerbe(true); 
		}
		if (animal != null)
			animal.uneEtape(plateau, this, i, j); // s'il y a un animal sur la case on execute une etape au niveau animal
		}

}
