/**
 * 
 */
package fr.enib.game.parcours.graphe;

import java.util.Comparator;

import fr.enib.game.model.interfaces.IVisitableObject;

/**
 * @author Corentin Boleis
 *
 */
public class VisitableObjectComparator implements Comparator<IVisitableObject> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IVisitableObject o1, IVisitableObject o2) {
		return Double.compare(o2.getValeurDeParcours(), o1.getValeurDeParcours());
	}

}
