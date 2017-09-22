
public class IterationThread extends Thread {
	Fenetre fen;
	
	public IterationThread (Fenetre f) {
		fen = f;
	}
	public void run() {
		fen.play(50);
	}
}