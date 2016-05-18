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
public class VistableObjectComparator implements Comparator<IVisitableObject> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IVisitableObject o1, IVisitableObject o2) {
		// TODO Auto-generated method stub
		return Double.compare(o1.getValeurDeParcours(), o2.getValeurDeParcours());
	}

}