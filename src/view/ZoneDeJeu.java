package view;


import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import controller.Controller;
import model.Map;

public class ZoneDeJeu extends JPanel {
	
	Case tabCases[][];
	
	public ZoneDeJeu(Controller c) {
		super();
		this.setPreferredSize(new Dimension(800, 800)) ;
		
		Map map = c.getMap();
		
		tabCases = new Case[map.getTailleX()][map.getTailleY()];
		System.out.println("X:"+map.getTailleX()+" Y:"+map.getTailleY());
		
		int positionX = 0;
		int positionY = 0;
		int taille = 16;
		
		for (int y=0 ; y<map.getTailleY() ; y++) {
			positionX = 0;
			for (int x=0 ; x<map.getTailleX() ; x++) {
				System.out.print(map.getCase(x, y)+" ");
				tabCases[x][y] = new Case(positionX,positionY,taille,map.getCase(x, y));
				tabCases[x][y].positionX = positionX;
				tabCases[x][y].positionY = positionY;
				
				positionX = positionX + taille;
			}
			System.out.println("");
			positionY = positionY + taille;
		}

	}
	
	public int getTailleY() {
		return tabCases[0].length;
	}
	
	public int getTailleX() {
		return tabCases.length;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int y=0 ; y<getTailleY() ; y++) {
			for (int x=0 ; x<getTailleX() ; x++) {
				g.drawImage(tabCases[x][y].image, tabCases[x][y].positionX, tabCases[x][y].positionY, null);
			}
		}
		
		
	}
}
