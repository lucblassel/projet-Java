import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Loup extends Animal {
	
	private boolean mouvement = false;
	
	public Loup (int vInit) {
		super(vInit); // constructeur à partir de la classe animal
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean peutBouger(Case c2) {
		if (c2.getAnimal() != null) {  	// si la case voisine contient un animal 
			if ( c2.getAnimal().getClass().getName() != "Loup") { // si cet animal n'est pas un loup (donc un mouton)
				return true; // le loup peut bouger (car il mange le mouton)
			}
			else 
				return false; // si c'est un loup, il ne peut pas bouger 
		}
		else 
			return true; // le loup peut bouger (il n'y a pas d'animal) 
	}

	@Override
	public boolean peutManger(Case c2) {
		if ((Mouton)c2.getAnimal() != null) // si la case voisine contient un mouton 
			return true; // le loup peut manger le mouton
		else
			return false; // sinon il ne peut pas le manger 
	}

	@Override
	public int manger(Case c2) { // méthode qui permet de manger le mouton sur la case c2 
		int vieMouton = ((Mouton)c2.getAnimal()).getVie(); // on récupère la vie du mouton 
		c2.setAnimal(null); // on le tue 
		return vie + vieMouton; // on récupère la vie du loup + celle du mouton pour l'attribuer au loup 
	}

	@Override
	public void reproduire (Case c1,Case c2) { // méthode qui permet au loup de se reproduire
		vieInit = 200; // vie initiale d'un loup 
		c2.getAnimal().setVie(vie - vieInit); // le loup sur la case c2 perd la vie qu'il donne au nouveau né
		c1.setAnimal(new Loup(vieInit)); // le loup naît avec vieInit points de vie
	}

	@Override
	public void dessinePetit(Graphics g, int x, int y, int dx, int dy) throws IOException { // même principe que pour le loup 
		// g.setColor(Color.white); // le mouton est dessiné en cercles blancs. 
		
		final BufferedImage image = ImageIO.read(new File("louppetit.png"));
		g.drawImage(image, x, y, null);
		//g.fillOval(x,y,dx,dy);
	}
	
	public void dessineGrand(Graphics g, int x, int y, int dx, int dy) throws IOException { // même principe que pour le loup 
		// g.setColor(Color.white); // le mouton est dessiné en cercles blancs. 
		
		final BufferedImage image = ImageIO.read(new File("loup.png"));
		g.drawImage(image, x, y, null);
		//g.fillOval(x,y,dx,dy);
	}
	
	
	
}
	