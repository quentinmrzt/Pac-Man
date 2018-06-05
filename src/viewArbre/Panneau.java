package viewArbre;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;

import arbre.Arbre;
import arbre.Noeud;

public class Panneau extends JPanel  {
	int tailleX, tailleY, tailleCercle, tailleSocle;
	private Arbre arbre;
	List<Cercle> listeCercle;

	public Panneau(Arbre arbre) {
		super();

		this.tailleCercle = 40;
		listeCercle = new ArrayList<Cercle>();

		int hauteur = arbre.hauteur();

		this.tailleX = ((int) Math.pow(2, hauteur))*tailleCercle;
		this.tailleY = (hauteur*2)*tailleCercle;

		this.arbre = arbre;

		int nbNoeud = (int) Math.pow(2, hauteur-1);
		this.tailleSocle = nbNoeud*tailleCercle;
		
		this.setPreferredSize(new Dimension(tailleX,tailleY));
	}

	public void dessinerArbre(Graphics g) {
		this.listeCercle = new ArrayList<Cercle>();

		dessin(g, arbre.getNoeud(), "0", 0);

	}

	private int dessin(Graphics g, Noeud noeud, String binaire, int profondeur) {
		int positionX, positionY;
		g.setColor(Color.BLACK);

		double tailleDivisionSocle = this.tailleSocle / (Math.pow(2, profondeur)+1);
		double numeroNoeud = conversionBinaire(binaire);

		positionX = (int) (tailleDivisionSocle * (numeroNoeud+1));
		positionY = (profondeur*2)*this.tailleCercle;

		g.drawOval(positionX-(tailleCercle/2), positionY, this.tailleCercle, this.tailleCercle);


		if(noeud.aGauche()) {
			int positionGaucheX = dessin(g, noeud.getGauche(), binaire+"0", profondeur+1);
			int positionGaucheY = ((profondeur+1)*2)*tailleCercle;

			g.drawLine(positionX, positionY+tailleCercle, positionGaucheX, positionGaucheY);
		}

		if(noeud.aDroite()) {
			int positionDroiteX = dessin(g, noeud.getDroite(), binaire+"1", profondeur+1);
			int positionDroiteY = ((profondeur+1)*2)*tailleCercle;

			g.drawLine(positionX, positionY+tailleCercle, positionDroiteX, positionDroiteY);
		}


		FontMetrics metrics = g.getFontMetrics(new Font(" TimesRoman ",Font.BOLD , 10));
		String text = noeud.toString();
		int x =  metrics.stringWidth(text) / 2;
		int y =  metrics.getHeight() / 2;
		g.setFont(new Font("TimesRoman ",Font.BOLD , 10));
		g.drawString(text, positionX-x, positionY+(tailleCercle/2)+y);

		g.setColor(Color.BLACK);

		return positionX;
	}

	private int conversionBinaire(String binaire) {
		double resultat = 0;
		int taille = binaire.length()-1;

		for (int index=0 ; index<binaire.length() ; index++) {
			if(binaire.charAt(index)=='1') {
				resultat += Math.pow(2, taille);
			}
			taille--;
		}
		return (int)resultat;
	}

	public void paint(Graphics g) {
		super.paint(g);

		this.dessinerArbre(g);
	}
}