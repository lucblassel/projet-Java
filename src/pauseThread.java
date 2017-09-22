public class pauseThread extends Thread {
	Fenetre fen;
	
	public pauseThread (Fenetre f) {
		fen = f;
	}
	
	
	public void run() {
		fen = new Fenetre();
	}
}
