package jeu;

/**
 * Impl�mentation simple d'un singleton.
 * L'instance est cr��e � l'initialisation. 
 */
public class Horloge implements Runnable {
	private Thread horloge;
	private static int temps;
	/** Instance unique pr�-initialis�e */
	private static Horloge INSTANCE = new Horloge();

	/** Constructeur priv� */
	private Horloge() {
		// Gestion de l'horloge
		horloge = new Thread(this);
		horloge.start();
		temps = 0;
	}

	/** Point d'acc�s pour l'instance unique du singleton */
	public static Horloge getInstance(){   
		return INSTANCE;
	}
	
	// Implementation de Runnable
	public void run() {
		// Notre horloge 
		while(true) {		
			try {
				temps += 1;
				Thread.sleep(1);
			} catch(InterruptedException e) { 
				System.err.println("ERREUR: Probl�me sur l'horloge.");
			}
		} 
	}
	
	public static int getTemps() {
		return temps;
	}
}