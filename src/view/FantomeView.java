package view;

import java.util.Observable;
import java.util.Observer;

import model.Blinky;
import model.Clyde;
import model.Inky;
import model.Pinky;

public class FantomeView extends ElementDuJeu implements Observer{

	public FantomeView(int x, int y) {
		super(x, y);

	}

	public void update(Observable obs, Object arg) {
		if(obs instanceof Blinky) {
			System.out.println("Blinky a �t� modifi�.");
		}

		if(obs instanceof Pinky) {
			System.out.println("Pinky a �t� modifi�.");
		}

		if(obs instanceof Inky) {
			System.out.println("Inky a �t� modifi�.");
		}

		if(obs instanceof Clyde) {
			System.out.println("Clyde a �t� modifi�.");
		}
	}
}
