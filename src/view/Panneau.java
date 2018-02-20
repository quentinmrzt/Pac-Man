package view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;


public class Panneau extends JPanel implements Observer {
	protected ZoneDeJeu zdj;
	protected JButton demarrer, stop;

	public Panneau(Controller control) {
		super();

		// installer le gestionnaire
		// http://b.kostrzewa.free.fr/java/td-composit/layouts.html
		 GridBagLayout g = new GridBagLayout();
		 setLayout(g);
		 
		 // créer un objet de type GridBagConstraints
		 GridBagConstraints constraints = new GridBagConstraints();
		 
		 // on utilise tout l'espace d'une cellule
		 constraints.fill = GridBagConstraints.BOTH; // BOTH: remplir tout l'espace offert.
		 constraints.weightx = 1.0; 
		 // 1 - Element à gauche
		 Button peche = new Button("Pêche");
		 add(peche); 
		 g.setConstraints(peche,constraints);
		 // 2 - Element au centre
		 Button poire = new Button("Poire");
		 add(poire); 
		 g.setConstraints(poire, constraints);
		 
		 // 3 - on va terminer la ligne avec ce composant
		 constraints.gridwidth = GridBagConstraints.REMAINDER; // "remplit le reste de la ligne et saute" 
		 Button pomme = new Button("Pomme");
		 add(pomme); 
		 g.setConstraints(pomme,constraints);
		 
		 // réinitialisation
		 constraints.weightx=0.0; 
		 constraints.weighty=1.0;
		 constraints.gridwidth=1; // sur 1 colonne
		 constraints.gridheight=2; //sur 2 lignes
		 // 1 -  
		 Button prune=new Button("Prune");
		 add(prune); 
		 g.setConstraints(prune,constraints);
		 
		 // nouvelle réinitialisation
		 constraints.weighty=0.0; 
		 constraints.gridwidth=GridBagConstraints.RELATIVE;
		 constraints.gridheight=1; // sur 1 ligne
		 Button fraise=new Button("Fraise");
		 add(fraise); 
		 g.setConstraints(fraise,constraints);
		 
		 // on termine la ligne
		 constraints.gridwidth=GridBagConstraints.REMAINDER;
		 Button cerise=new Button("Cerise");
		 add(cerise); 
		 g.setConstraints(cerise,constraints);
		 Button ananas=new Button("Ananas");
		 add(ananas);
		 g.setConstraints(ananas,constraints);
		
		
		
		
		// Ajout d'un écouteur sur le clavier
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new ControleClavier(control));


		// En haut le jeu et en bas les boutons de controle
		//this.setLayout(new BorderLayout());

		demarrer = new JButton("Demarrer/Pause");
		demarrer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				control.boutonDemarrer();
			}
		});

		stop = new JButton("Stop");
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				control.boutonStop();
			}
		});


		JPanel controle = new JPanel();
		controle.add(demarrer);
		controle.add(stop);

		zdj = new ZoneDeJeu(control);


		//this.add("West", zdj);
		//this.add("East", controle);

	}

	public void update(Observable o, Object arg) {
		zdj.update(o, arg);
	}
}