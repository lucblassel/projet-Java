import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Plateau {
	
	private Case[][] grille; // la matrice de Cases
	int imax; // nombre de lignes
	int jmax; // nombre de colonnes
	int i; //sert � ipmpl�menter des boucles
	int j; //idem
	int k; //idem
	int dx,dy,x,y; //dimensions et coordonn�es de la case � dessiner
	int n,n2,n3; // permet de grapher le nombre de moutons, de loups et de cases herb�es
	int nbLoup,nbMouton,nbHerbe; //permet de stocker pour une it�ration ces nombres
	Case element = new Case(); // 
	private ArrayList listeMouton,listeLoup,listeHerbe; // garder en m�moire ces nombres pour chaque it�ration du programme
	private int hauteur=0; //hauteur du panneau sur lequel on mettra le graphe d'�volution
	
	/*
	 * Constructeur.
	 * imax: nombre de lignes
	 * jmax: nombre de colonnes
	 * Doit créer la matrice et l'initialiser.
	 */
	public Plateau(int imax, int jmax){ 
		this.imax=imax; //on definit le nopbre de cases et de colonnes
		this.jmax=jmax;
			grille = new Case[imax][jmax]; //on cr�e un nouvelle matrice de cases => le plateau
			for (i=0;i<imax;i++) { //on parcourt lignes et colonnes
				for (j=0;j<jmax;j++) {
					element = new Case(); //on initialise la case concern�e
					grille[i][j]=element; //on la place dans la matrice aux coordon�es (i,j)
					getCase(i,j).setNbCases(this.imax); // on donne la taille du plateau pour la fonction dessine
				}
			}
			listeMouton = new ArrayList(); // on cr�� des Arraylist, elle premettent de stocker le nombre de moutons
			listeLoup = new ArrayList(); //de loups et de cases herb�es pour une it�ration donn�es. 
			listeHerbe = new ArrayList();
			
	}
	
	public int compteAnimal(String animal){ // permet de compter le nombre d'un certain type d'animal (donn� en entr�e) pour une it�ration
		int compteur=0;
		for(i=0;i<imax;i++){ //on parcourt les cases
			for(j=0;j<jmax;j++){
				if(getCase(i,j).getAnimal()!=null && getCase(i,j).getAnimal().getClass().getName().equals(animal))
					compteur++; // si la case contient l'animal d�fini on incr�mente le compteur
			}
		}
		return compteur;
	}
	
	public int compteHerbe() { //pareil mais pour le nombre de cases herb�es
		int compteur = 0;
		for(i=0;i<imax;i++){
			for(j=0;j<jmax;j++){
				if (getCase(i,j).getHerbe() == true) 
					compteur++;
			}
		}
		return compteur;
	}

	
	/*
	 * Initialiser la matrice du plateau, avec des nouvelles Cases.
	 */
	public void init() {
		Case element = new Case();
	}
	
	/*
	 * Retourne la case à l'emplacement (i, j) de la matrice,
	 * si cette case existe, ou retourne null sinon.
	 */
	public Case getCase(int i, int j) { //retourne la case de coordonn�es(i,j)
		if (i<imax && i>=0 && j>=0 && j<jmax)
			return grille[i][j];
		else
			return null;
	}
	
	
	/*
	 * Affiche une représentation textuelle de la matrice.
	 */
	public void afficheTxt() {
		for (i=0;i<imax;i++) {
			for (j=0;j<jmax;j++) {
				System.out.print(getCase(i,j).afficheTxt()+ " "); //affiche les cases sur un quadrillage
			}														// selon leurs coordonn�es. 
			System.out.println(); //permet de passer � la ligne suivante du plateau
		}
	}
	
	/*
	 * Effectue une itération de la dynamique de l'écosystème.
	 */
	public void uneEtape(){ 
		for (i=0;i<imax;i++) { //on parcourt les cases
			for (j=0;j<jmax;j++) {
				if (getCase(i,j).getAnimal() != null) //s'il y a un animal dans la case
					getCase(i,j).getAnimal().setMouvement(false);//on r�initialise le boul�en mouvement au d�but de l'it�ration � false
			}													 // pour indiquer que l'animal n'a pas encore boug� � cette it�ration
		}
		
		
		for (i=0;i<imax;i++) { //on parcourt les cases
			for (j=0;j<jmax;j++) {
				getCase(i, j).uneEtape(this, i, j);// on execute une it�ration du programme au niveau de la case
			}
		}
		nbMouton=compteAnimal("Mouton"); //on compte le nombre de moutons � cett it�ration
		nbLoup=compteAnimal("Loup"); //idem
		nbHerbe = compteHerbe(); //idem
		listeMouton.add(nbMouton); //on ajoute le nombre de moutons dans la liste pour suivre l'�volution de la population
		listeLoup.add(nbLoup); // idem
		listeHerbe.add(nbHerbe); //idem
	}
	
	/*
	 * Boucle principale de jeu en mode texte.
	 * 
	 */
	public void playText(int nIter) { //programme textuel
		for (k =0 ;k<nIter;k++) {
			uneEtape();
			System.out.println();
			afficheTxt();
		}
	}
	
	/*
	 * Dessine la matrice, et donc chaque case.
	 * hauteur: hauteur de la zone d'affichage du plateau
	 * largeur: largeur de la zone d'affichage du plateau
	 * Il faut adapter la largeur des cases à la zone d'affichage.
	 * 
	 */
	public void dessineGraphe(Graphics g,int x){ //dessine les graphes d'�volution des populations
		 
		if (listeMouton.size() != 0) { //on v�rifie qu'il y a des �l�ments dans la liste
			Iterator iterM = listeMouton.iterator(); //on cr��e un it�rateur pour la liste
			x=0; // compteur d'it�rations du programme
			while (iterM.hasNext()) { // tant qu'il y a des elements dans la liste
				g.setColor(Color.gray); //couleur de la courbe des moutons
				n = Integer.parseInt(iterM.next().toString()); // on met dans l'int n le nombre de moutons de plateau pour l'it�ration x
				g.drawLine(x,hauteur-n,x,hauteur-n); //on dessine le pixel de coordonn�es(x,n)
				x++; 								 //on incr�mente le compteur d'it�rations
			}										 //on doit mettre hauteur-n car l'origine du panneau est en haut � gauche et pas en bas
		}
		if (listeLoup.size() != 0) { //idem
			Iterator iterL = listeLoup.iterator();
			x=0;
			while (iterL.hasNext()) {
				g.setColor(Color.red);
				n2 = Integer.parseInt(iterL.next().toString());
				g.drawLine(x,hauteur-n2,x,hauteur-n2);
				x++;
			}
		}
		
		if (listeHerbe.size() != 0) { //idem
			Iterator iterH = listeHerbe.iterator();
			x=0;
			while (iterH.hasNext()) {
				g.setColor(Color.green);
				n3 = Integer.parseInt(iterH.next().toString());
				n3=n3/5;
				System.out.println(n3);
				g.drawLine(x,hauteur-n3,x,hauteur-n3);
				x++;
			}
		}
	}
	
	public void dessine(Graphics g, int hauteur, int largeur) throws IOException{ //dessine les cases
		dx = largeur/imax; //definit la largeur de la case en fonction du nombre de cases totales
		dy = hauteur/jmax;
		
		for (i=0 ; i<imax ; i++) { // on parcourt les cases
			for (j=0;j<jmax;j++) {
				getCase(i,j).dessine(g,i*dy,j*dx,dx,dy); //on execute la m�thode dessine de case pour chaque case
			}
		}
	}
	public void setHauteur(int i){ // permet de donner la hauteur du plateau du graphe d'�volution
		hauteur = i;				// on se sert de cette hauteur dans la m�thode dessineGraphe
	}
	
	
}
