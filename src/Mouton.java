import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Mouton extends Animal {

	private boolean mouvement = false;

	public Mouton(int vInit) {
		super(vInit); // appel du constructeur de la classe Animal 
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public boolean peutBouger(Case c2) { // vérifie si le mouton peut bouger sur la case voisine c2 
		if (c2.getAnimal() == null)  // s'il n'y a pas d'animal sur cette case c2 
			return true; // le mouton peut se déplacer sur cette case 
		else			// s'il y a un animal 
			return false; // il ne peut pas se déplacer 
	}

	@Override
	public boolean peutManger(Case c2) { // vérifie si le mouton peut manger l'herbe sur la case c2 qu'il vise
		if(c2.getHerbe()==true) // s'il y a de l'herbe 
			return true; // le mouton peut manger 
		else 			// sinon 
			return false; // il ne peut pas manger
	}

	@Override
	public int manger(Case c2) {
		c2.setHerbe(false); // on rend false l'herbe sur la case c2 
		return vie + 20;  // le mouton gagne 20 points de vie
	}

	@Override
	public void reproduire (Case c1,Case c2) { // même principe que pour le loup (cf classe Loup)
		vieInit = 100; // seule la vie initiale diffère 
		c2.getAnimal().setVie(vie - vieInit);
		c1.setAnimal(new Mouton(vieInit));
	}

	@Override
	public void dessinePetit(Graphics g, int x, int y, int dx, int dy) throws IOException { // même principe que pour le loup 
		// g.setColor(Color.white); // le mouton est dessiné en cercles blancs. 
		
		final BufferedImage image = ImageIO.read(new File("moutonpetit.png"));
		g.drawImage(image, x, y, null);
		//g.fillOval(x,y,dx,dy);
	}
	
	public void dessineGrand(Graphics g, int x, int y, int dx, int dy) throws IOException { // même principe que pour le loup 
		// g.setColor(Color.white); // le mouton est dessiné en cercles blancs. 
		
		final BufferedImage image = ImageIO.read(new File("mouton.png"));
		g.drawImage(image, x, y, null);
		//g.fillOval(x,y,dx,dy);
	}
	
	
	

}
