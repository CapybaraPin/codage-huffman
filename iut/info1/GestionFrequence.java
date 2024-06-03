/**
 * ApplicationHuffman.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */

package iut.info1.huffman;

/**
 * Gère les méthodes associées aux fréquences
 */
public class GestionFrequence {

    /** le numerateur indiqué ne respecte pas les critères */
    private static final String ERREUR_NUMERATEUR_INVALIDE =
	    "erreur : Impossible d'effectuer la division, numérateur invalide.";

    private String[] contenuFichier;
    private String[][] occurrences;
    private double[] frequences;

    /**
     * Constructeur de la classe GestionFrequence
     * @param contenuFichier 
     */
    public GestionFrequence(String[] contenuFichier){
	this.contenuFichier = contenuFichier;
    }

    /**
     * rechercher tous les caractères stockés dans
     * un tableau de chaîne de caractères, et d'en extraire
     * le nombre de fois qu'ils apparaissent dans le tableau
     * de chaîne entier.
     */
    public void compterOccurrence() {

	if (contenuFichier.length == 0) {
	    occurrences = new String[][]{{"", "0"}};
	    return;
	}

	String contenuFichier;

	contenuFichier = String.join("\n",  this.contenuFichier);
	boolean estDejaApparue;

	occurrences = new String[][]{
	    {String.valueOf(contenuFichier.charAt(0)), "0"}
	};

	for (int indexRecherche = 0;
		indexRecherche < contenuFichier.length();
		indexRecherche++) {

	    estDejaApparue = false;
	    for (String[] caractereCompte : occurrences) {

		if (caractereCompte[0].toCharArray()[0]
			== (contenuFichier.charAt(indexRecherche))) {
		    estDejaApparue = true;

		    caractereCompte[1] =  String.valueOf(
			    Integer.parseInt(caractereCompte[1]) + 1);
		}
	    }

	    if (!estDejaApparue) {

		String[][] nouvelleTableDesOccurences
		= new String[occurrences.length+1][2];

		for (int indexActuel = 0;
			 indexActuel < occurrences.length;
			 indexActuel++) {

		    nouvelleTableDesOccurences[indexActuel]
			         = occurrences[indexActuel];
		}

		nouvelleTableDesOccurences[occurrences.length][0]
			  = String.valueOf(contenuFichier.charAt(indexRecherche));

		nouvelleTableDesOccurences[occurrences.length][1]
			  = "1";

		occurrences = nouvelleTableDesOccurences;
	    }
	}
    }

    /**
     * Calcul la fréquence d'apparition de chaque caractères.
     */
    public void calculFrequences(){

	compterOccurrence();

	int nbOccurrences;
	int nbLignes;

	nbOccurrences = 0;
	for (String[] occurrence : occurrences) {
	    nbOccurrences += Integer.valueOf(occurrence[1]);
	}

	if (nbOccurrences <= 0 || nbOccurrences >= Integer.MAX_VALUE) {
	    frequences = null;
	    throw new IllegalArgumentException(ERREUR_NUMERATEUR_INVALIDE);
	}

	nbLignes = occurrences.length;
	frequences = new double[nbLignes];

	for(int indiceFreq = 0; indiceFreq < nbLignes; indiceFreq++) {
	    frequences[indiceFreq] = Double.valueOf(occurrences[indiceFreq][1])
		                     / nbOccurrences;
	}
    }

    /**
     * Getter de contenuFichier
     * @return contenuFichier
     */
    public String[] getContenuFichier() {
	return contenuFichier;
    }

    /**
     * Getter de occurrences
     * @return occurrences
     */
    public String[][] getOccurrences() {
	return occurrences;
    }

    /**
     * Getter de frequences
     * @return frequences
     */
    public double[] getFrequences() {
	return frequences;
    }
}