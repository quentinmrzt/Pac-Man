package viewGraphique;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class InformationGraphique extends Observable {
	private int nombre, min, max;
	private List<Integer> valeurs;

	public InformationGraphique(int nombre, int min, int max) {
		this.valeurs = new ArrayList<Integer>();
		this.nombre = nombre;

		for (int i=0 ; i<nombre ; i++) {
			int random = (int) (min + (Math.random() * (max-min)+1));

			valeurs.add(random);
		}
		
		this.setBornes();
	}

	public InformationGraphique(List<Integer> valeurs) {
		this.valeurs = valeurs;
		this.nombre = valeurs.size();
		
		this.setBornes();
	}
	
	public void aleatoire() {
		this.valeurs = new ArrayList<Integer>();
		
		for (int i=0 ; i<nombre ; i++) {
			int random = (int) (min + (Math.random() * (max-min)+1));

			valeurs.add(random);
		}
		
		this.setBornes();
		
		this.setChanged();
		this.notifyObservers();
	}

	private void setBorne(int valeur) {
		if(valeur>this.max) {
			this.max = valeur;
		}

		if(valeur<this.min) {
			this.min = valeur;
		}
	}
	private void setBornes() {
		this.min = Integer.MAX_VALUE;
		this.max = Integer.MIN_VALUE;
		
		for (int valeur: this.valeurs) {
			this.setBorne(valeur);
		}
	}

	public int getNombre() { return nombre; }
	public int getMin() { return min; }
	public int getMax() { return max; }

	public int getValeur(int index) { return valeurs.get(index); }
	public void addValeur(int nombre) { 
		this.setBorne(nombre);
		this.valeurs.add(nombre); 
	}

	public String toString() {
		return "Nombre: "+this.nombre+" / Min: "+this.min+" / Max: "+this.max;
	}
}
