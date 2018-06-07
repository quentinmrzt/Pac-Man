package viewGraphique;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Souris implements MouseMotionListener {
	private VueNuageDePoint nuage;

	public Souris(VueNuageDePoint nuage) {
		this.nuage = nuage;
	}

	public void mouseDragged(MouseEvent e) { }

	public void mouseMoved(MouseEvent e) {
		if(this.surPoint(e.getX(),e.getY())) {
			//System.out.println("X: "+e.getX()+" / Y: "+e.getY());
		}
	}

	public boolean surPoint(int x, int y) {
		return this.nuage.surPoint(x,y);
	}

}
