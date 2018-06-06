package viewEvolution;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import evolution.Evolution;

public class VueGeneration extends JPanel {
	
	public VueGeneration(Evolution evolution) {
		super();
		
		this.setBorder(BorderFactory.createTitledBorder("Generations"));
		this.setBackground(Color.WHITE);
		
		VueScroll vueScroll = new VueScroll(evolution);

		this.add(vueScroll);
	}
}
