/**
 * ApplicationHuffman.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman;

import iut.info1.huffman.gui.ControleurPrincipal;
import javafx.application.Application;

/**
 * Permet d'effectuer des operations de compression et
 * de decompression de fichiers
 */
public class ApplicationHuffman {

    /** le format du paramètre est invalide **/
    public static final String ERREUR_FORMAT_PARAMETRE =
	"erreur : Le format du paramètre renseigné invalide.";
    
    /** Aide à l'utilisateur pour lui permettre de comprendre ce 
     *  qui lui est permis de faire si il utilise l'application
     *  sur ligne de commande
     */
    private static final String AIDE =
    	"""
    	Usage: programme.jar [options] [arguments]

    	Options:
    	   -c               Compression
    	   -d               Décompression
    	   -h               Affiche l'aide

    	Exemples:
    	   programme.java -c [Chemin_du_fichier] [Répertoire_de_sortie]
    	   programme.java -d [Chemin_du_fichier_binaire] [Chemin_du_fichier_clé] [Répertoire_de_sortie]
    	   programme.java -h

    	Si aucun argument n'est fourni, la méthode IHM est appelée.
    	""";
    
    /**
     * Lance l'application en mode terminal
     * Donne la possibilité de compresser / décompresser 
     * des fichiers .txt
     * @param args non utilisés
     */
    public static void main(String[] args) {
		Compression compression;
		Decompression decompression;
		String action;
		String cheminFichier;
		String cheminFichierBin;
		String cheminFichierCle;
		String dossierRetour;

		if (args.length == 0) {
	        Application.launch(ControleurPrincipal.class, args);

			return;
		}

		action = args[0];

		switch (action){
		case "-h":
			System.out.println(AIDE);
			break;
			
		case "-c":
			cheminFichier = args[1];
			dossierRetour = args[2];
			compression = new Compression(cheminFichier, dossierRetour);
			compression.execute();
			break;
			
		case "-d":
			cheminFichierBin = args[1];
			cheminFichierCle = args[2];
			dossierRetour = args[3];
			decompression = new Decompression(cheminFichierBin, 
										cheminFichierCle, 
										dossierRetour   );
			decompression.execute();
			break;
			
		default:
			System.out.println(AIDE);
			break;
		}
    }
}