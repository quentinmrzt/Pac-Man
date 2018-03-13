package jeu;

/**
 * Implémentation simple d'un singleton.
 * L'instance est créée à l'initialisation. 
 */
public class Horloge implements Runnable {
	private Thread horloge;
	private static int temps;
	/** Instance unique pré-initialisée */
	private static Horloge INSTANCE = new Horloge();

	/** Constructeur privé */
	private Horloge() {
		// Gestion de l'horloge
		horloge = new Thread(this);
		horloge.start();
		temps = 0;
	}

	/** Point d'accès pour l'instance unique du singleton */
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
				System.err.println("ERREUR: Problème sur l'horloge.");
			}
		} 
	}
	
	public static int getTemps() {
		return temps;
	}
}