package viewGraphe;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import model.Modelisation;

public class VueGraphe extends JPanel  {
	private Modelisation modelisation;

	public VueGraphe(Modelisation modelisation) {
		this.modelisation = modelisation;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();

		for(int y=0; y<modelisation.getMap().getTailleY() ; y++) {
			for(int x=0 ; x<modelisation.getMap().getTailleX() ; x++) {
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
			}
			
			g.setColor(c);
		}
	}
}
