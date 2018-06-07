package lancer;

import evolution.Evolution;
import viewEvolution.FenetreEvolution;

public class Lancer {
	public static void main(String[] args) {
		Evolution evolution = new Evolution();
		new FenetreEvolution(evolution);
	}
}
