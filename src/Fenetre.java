import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * La fenetre graphique
 */
public class Fenetre extends JFrame implements ActionListener, ChangeListener{
	
	Plateau plateau; // on appelle l'objet plateau 
	static boolean testThread = false; // teste si on a effectué l'opération stop ou start 
	int nCasesX = 10; 				// correspond à imax et jmax de la matrice plateau 
	int nCasesY = 10;
	private IterationThread t=null;// sert pour les boutons stop et start 
	private JPanel panneau, panneauGraphe; // on appelle l'objet panneau où se situe le jeu et panneauGraphe où on va tracer les évolutions de population
	private Plateau plat = new Plateau(nCasesX,nCasesY); // on inialise un objet plat avec imax et jmax lignes et colonnes 
	private JButton boutonStart,boutonStop,boutonReset; // on appelle les 3 boutons qui permettent d'intéragir avec le jeu 
	private JToolBar barreOutils; // appel de la barre d'outils où se situeront les boutons 
	private JLabel sliderLabel, dimensionLabel; //etiquette du slider de vitesse
	private JTextField dimension;
	private int x=0; // variable d'itération 
	private JSlider sliderSpeed; //sert a regler la vitesse des it�rations
	private int speed; //vitesse des it�rations
	
	
	
	public Plateau getPlateau() {
		return plat; // récupère le plateau d'un objet fenêtre 
	}
	
	
	/* 
	 * Constructeur.
	 */
	
	public Fenetre(){
		super("Ecosysteme"); // titre Ecosystème 
		
		panneau = new PlateauPanel(plat); // on initialise les objets panneau et panneauGraphe en appelant les classes créées à cet effet 
		panneauGraphe = new GrapheEvolution(plat);
		panneauGraphe.setBackground(Color.white); // on met un fond blanc pour le graphe 
		Container contenu = getContentPane();  // on récupère le contenu du panneau 
		contenu.add(panneau,BorderLayout.WEST); // on dispose les deux panneau sur la fenêtre 
		contenu.add(panneauGraphe,BorderLayout.SOUTH);
		
		barreOutils = new JToolBar(JToolBar.VERTICAL); // on crée un barre d'outils verticale à droite 
		boutonStart = new JButton("Start");
		boutonStop = new JButton("Stop");
		boutonReset = new JButton("Reset");
		sliderSpeed = new JSlider(10,1000);
		sliderLabel = new JLabel("Vitesse");
		dimension = new JTextField();
		dimensionLabel = new JLabel("dimension du plateau:");
		
		
		Hashtable label = new Hashtable();
		label.put(new Integer(10),new JLabel("Lent"));
		label.put(new Integer(1000), new JLabel("rapide"));
		sliderSpeed.setLabelTable(label);
		sliderSpeed.setPaintLabels(true);
		
		barreOutils.add(boutonStart); // on ajoute le bouton start 
		boutonStart.addActionListener(this); // on écoute le bouton 
		
		
		barreOutils.add(boutonStop);
		boutonStop.addActionListener(this);
		
		barreOutils.add(boutonReset);
		boutonReset.addActionListener(this); // idem 
		
		barreOutils.add(sliderLabel);
		barreOutils.add(sliderSpeed);
		sliderSpeed.addChangeListener(this);
		
		barreOutils.add(dimensionLabel);
		barreOutils.add(dimension);
		dimension.addActionListener(this);
		
	
		
		contenu.add(barreOutils,"East");
		setHauteur();
		
		

		// À faire à la fin :
		this.pack();
		setSize(800,800); // on initialise la taille de la fenêtre 
		setLocationRelativeTo(null); // on la centre 
		setVisible(true); // et on la rend visible 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ferme la fenêtre dès qu'on clique sur fermer 
// recalculer et afficher tous les composants à la bonne taille.
	}
	
	public int getHauteur(){
		return panneauGraphe.getHeight(); //récupère la hauteur du panneau graphe  
	}
	public void setHauteur(){
		plat.setHauteur(getHauteur()); //méthode d'altération de la hauteur du panneau  
	}
	/* 
	 * Boucle principale de jeu.
	 */
	public void play(int iterations) {
		while (testThread == true){ // tant qu'on n'a pas fait le Thread 
			plat.uneEtape(); // on appelle uneEtape de plateau (toutes les cases bougent) 
			panneau.repaint();// on repeint le panneau à chaque fois 
			plat.setHauteur(getHauteur()); 
			//panneauGraphe.revalidate();
			panneauGraphe.repaint();
			try {
				Thread.sleep(1010-speed); // pause de 100 ms entre chaque itération 
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}
	
	public int getX(){
		return x;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		t = new IterationThread(this);
		if (e.getSource() == boutonStart && testThread == false) { // si on a appuyé sur le boutonStart et qu'il n'a pas été appuyé 
			t.start(); // on démarre le jeu 
			testThread = true;
		}
		if (e.getSource() == boutonStop && t!=null) {  // idem pour les 2 autres boutons 
			Thread.currentThread().interrupt();
			testThread = false;
		}
		if (e.getSource() == boutonReset && t!=null) {
			this.dispose(); // on ferme la fenêtre 
			new Fenetre(); // on la recrée avec un nouveau jeu 
		}
		 if(e.getSource()==dimension){
			 nCasesX=Integer.parseInt(dimension.getText());
			 nCasesY=nCasesX;
			 plat = new Plateau(nCasesX,nCasesY);
			 panneau=new PlateauPanel(plat);
			 panneau.repaint();
			 
		 }
	}
	 public void stateChanged(ChangeEvent e){
		 JSlider source = (JSlider)e.getSource();
		 if(e.getSource()== sliderSpeed){
			 speed = (int)source.getValue();
			 System.out.println(1010-speed);
		 }
		
			 
	 }

}


