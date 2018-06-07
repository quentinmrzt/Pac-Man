package viewGraphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class VueNuageDePoint extends JPanel implements Observer {
	private InformationGraphique information;
	private int largeur, hauteur, espace;
	private List<Point> listePoints;
	private Zone zoneDessin;

	public VueNuageDePoint(InformationGraphique graphique) {
		this.largeur = 900;
		this.hauteur = 600;
		this.setPreferredSize(new Dimension(largeur, hauteur));

		this.setBackground(Color.WHITE);

		this.information = graphique;
		this.zoneDessin = new Zone(50, 10, 500, 800);
		this.espace = this.zoneDessin.getLargeur()/information.getNombre();
		this.addMouseMotionListener(new Souris(this));

		// Création de la liste des points avec leurs coordonnées
		this.creationListePoints();

		this.information.addObserver(this);
	}

	public boolean surPoint(int x, int y) {		
		for (Point point: listePoints) {
			if(point.surPoint(x, y)) {
				return true;
			}
		}

		return false;
	}

	private int normalisation(int valeur) {
		// normalisée = (originale - MIN) * (max - min) / (MAX - MIN) + min 
		// [MIN,MAX] : interval d'origine 
		// [min,max] : interval cible 
		return (valeur - information.getMin()) * this.zoneDessin.getHauteur() / (this.information.getMax() - this.information.getMin()); 
	}

	public int getHauteur() { return this.hauteur; }
	public int getLargeur() { return this.largeur; }

	public void creationListePoints() {
		this.listePoints = new ArrayList<Point>();

		// Création de la liste des points avec leurs coordonnées
		for(int i=0 ; i<this.information.getNombre() ; i++) {
			int x = this.zoneDessin.getOrigineX() + (i*espace);
			int y = this.zoneDessin.getExtremiteY() - this.normalisation(this.information.getValeur(i));


			this.listePoints.add(new Point(x, y));
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		this.dessinerAxes(g);
		this.dessinerPoints(g);
		this.dessinerCourbe(g);
		this.dessinerValeurs(g);
	}

	private void dessinerAxes(Graphics g) {
		// Axe vertical
		g.drawLine(this.zoneDessin.getOrigineX(), this.zoneDessin.getExtremiteY(), this.zoneDessin.getOrigineX(), this.zoneDessin.getOrigineY());

		// Axe horizontal 
		if(this.information.getMin()>=0) {
			g.drawLine(this.zoneDessin.getOrigineX(), this.zoneDessin.getExtremiteY(), this.zoneDessin.getExtremiteX(), this.zoneDessin.getExtremiteY());
		} else {
			int y = this.zoneDessin.getExtremiteY() - this.normalisation(0);
			
			g.drawLine(this.zoneDessin.getOrigineX(), y, this.zoneDessin.getExtremiteX(), y);
		}
	}

	private void dessinerPoints(Graphics g) {
		for (Point point: listePoints) {
			g.fillOval(point.getCentreX(), point.getCentreY(), point.getTaille(), point.getTaille());
		}
	}

	private void dessinerCourbe(Graphics g) {
		for (int i=0 ; i<this.information.getNombre()-1 ; i++) {
			int x1 = listePoints.get(i).getX();
			int y1 = listePoints.get(i).getY();

			int x2 = listePoints.get(i+1).getX();
			int y2 = listePoints.get(i+1).getY();

			g.drawLine(x1, y1, x2, y2);	
		}
	}

	private void dessinerValeurs(Graphics g) {
		int distance = 13;		

		for (int i=0 ; i<this.information.getNombre() ; i++) {
			g.drawString(this.information.getValeur(i)+"", this.listePoints.get(i).getX(), this.listePoints.get(i).getY()+distance);
		}
	}

	public void update(Observable arg0, Object arg1) {
		System.out.println("REPAINT = VueNuageDePoint");

		this.creationListePoints();

		this.repaint();
	}
}
