package viewGraphe;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import graphe.Branche;
import graphe.Noeud;
import model.Modelisation;

public class VueGraphe extends JPanel  {
	private Modelisation modelisation;
	private int decalageBranche;
	private int decalageNoeud;


	public VueGraphe(Modelisation modelisation) {
		this.modelisation = modelisation;
		decalageBranche = 0;
		decalageNoeud = 0;
	}

	public void paint(Graphics g) {
		super.paint(g);
		
		
		
		Color c = g.getColor();

		for(int y=0; y<modelisation.getMap().getTailleY() ; y++) {
			for(int x=0 ; x<modelisation.getMap().getTailleX() ; x++) {
				// On dessine le graphe
				if(modelisation.getGraphe().getNoeud(x, y)!=null) {
					g.setColor(Color.BLACK);
					g.fillOval(x*16,y*16,16,16);
				} else if (modelisation.getGraphe().getBranche(x, y)!=null) {
					if(modelisation.getGraphe().getBranche(x, y).estHorizontal()) {
						g.setColor(Color.BLACK);
						g.fillRect(x*16,y*16+7,16,2);
					} else {
						g.setColor(Color.BLACK);
						g.fillRect(x*16+7,y*16,2,16);
					}
				}
				

				// Colorer les noeud
				decalageNoeud = 0;

				paintNoeud(g,modelisation.getPM().getNoeud(),Color.YELLOW,x,y);
				paintNoeud(g,modelisation.getFantome(Modelisation.BLINKY).getNoeud(),Color.RED,x,y);
				paintNoeud(g,modelisation.getFantome(Modelisation.PINKY).getNoeud(),Color.PINK,x,y);
				paintNoeud(g,modelisation.getFantome(Modelisation.INKY).getNoeud(),Color.BLUE,x,y);
				paintNoeud(g,modelisation.getFantome(Modelisation.CLYDE).getNoeud(),Color.ORANGE,x,y);
				
				// Colorer les branches
				decalageBranche = 0;

				paintBranche(g,modelisation.getPM().getBranche(),Color.YELLOW,x,y);
				paintBranche(g,modelisation.getFantome(Modelisation.BLINKY).getBranche(),Color.RED,x,y);
				paintBranche(g,modelisation.getFantome(Modelisation.PINKY).getBranche(),Color.PINK,x,y);
				paintBranche(g,modelisation.getFantome(Modelisation.INKY).getBranche(),Color.BLUE,x,y);
				paintBranche(g,modelisation.getFantome(Modelisation.CLYDE).getBranche(),Color.ORANGE,x,y);
			}

			g.setColor(c);
		}
	}
	
	public void paintNoeud(Graphics g, Noeud noeud, Color c, int x, int y) {
		if(noeud!=null) {
			if (noeud.getX()==x && noeud.getY()==y) {
				g.setColor(c);
				g.fillOval(x*16+decalageNoeud,y*16+decalageNoeud,16,16);
				decalageNoeud++;
			}
		}
	}
	
	public void paintBranche(Graphics g, Branche branche, Color c, int x, int y) {
		if (branche!=null) {
			if(branche.estHorizontal()) {
				if (x>branche.getN1().getX() && x<branche.getN2().getX() && y==branche.getN1().getY()) {
					g.setColor(c);
					g.fillRect(x*16,y*16+7+decalageBranche,16,2);
					decalageBranche++;
				}
			} else {
				if (y>branche.getN1().getY() && y<branche.getN2().getY() && x==branche.getN1().getX()) {
					g.setColor(c);
					g.fillRect(x*16+7+decalageBranche,y*16,2,16);
					decalageBranche++;
				}
			}
		}
	}
}
