package fr.enib.game.editor.graphe.analysis;

/**
 *
 */
public class StructuralException extends Exception {
    /**
	 * A custom exception for irregular graph structure for certain algorithms
	 */
	private static final long serialVersionUID = -468633497832330356L;

	/**
	 * @param message
	 */
	public StructuralException(String message) {
        super(message);
    }
};
