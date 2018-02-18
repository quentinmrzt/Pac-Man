package view;

//import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;
import model.Modelisation;


public class Panneau extends JPanel implements Observer {
	protected ZoneDeJeu zdj;
	protected JButton demarrer, stop;

	public Panneau(Controller c) {
		super();
		
		// En haut le jeu et en bas les boutons de controle
		//this.setLayout(new GridLayout(2,1));
		
		demarrer = new JButton("Demarrer/Pause");
		stop = new JButton("Stop");
		
		JPanel controle = new JPanel();
		controle.add(demarrer);
		controle.add(stop);
		
		zdj = new ZoneDeJeu(c);
		
		this.add(zdj/*, BorderLayout.CENTER*/);
		//this.add(controle);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof Modelisation){
			System.out.println("Je suis dans le panneau et la valeur a été modifié !");
		}
	}
}
