/**
 * GestionFichier.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman.fichier;

import static java.lang.System.err;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;  

/**
 * La classe Fichier gère les opérations de lecture 
 * sur des fichiers texte. Elle permet de lire le contenu 
 * d'un fichier ligne par ligne, de calculer la taille d'un fichier, 
 * d'afficher le contenu du fichier et de comparer la taille de deux
 * fichiers pour déterminer l'efficacité de la compression.
 * <br>
 * Les fichiers pris en charge doivent avoir une extension .txt ou .TXT.
 * La classe gère également les erreurs courantes liées à l'ouverture,
 * la lecture et la fermeture des fichiers.
 */
public class Fichier {

	
	private File fichierExploite;
	private FileReader lecteurFichier;
	private BufferedReader tamponFichier;
	

	/** impossible d'ouvrir le fichier renseigné */
	private static final String ERREUR_OUVERTURE_FICHIER = 
			"erreur : Impossible d'ouvrir le fichier au lien : ";

	/** impossible de fermer le fichier renseigné */
	private static final String ERREUR_FERMETURE_FICHIER = 
			"erreur : Impossible de fermer le fichier.";

	/** impossible d'acceder au contenu du fichier **/
	private static final String ERREUR_CONTENU_FICHIER =
			"erreur : Impossible d'accéder au contenu du fichier.";

	/** le format du fichier n'est pas .txt ou .TXT **/
	private static final String ERREUR_EXTENSION_FICHIER =
			"erreur : Le format du fichier doit être .txt ou .TXT";
	
	/** le format du paramètre est invalide **/
	private static final String ERREUR_FORMAT_PARAMETRE =
			"erreur : Le format du paramètre renseigné invalide.";
	
	/** problème de lecture du fichier **/
    private static final String ERREUR_FICHIER_LECTURE = 
    		"erreur : Il y a eu un problème lors de l'ovuerture du fichier : ";
    
	/** le numerateur indiqué ne respecte pas les critères **/
	private static final String ERREUR_NUMERATEUR_INVALIDE = 
			"erreur : Impossible d'effectuer la division, numérateur invalide.";
	
    /** code d'erreur lors d'un soucis de lecture fichier */
    public static final int CODE_ERREUR_FICHIER_LECTURE = 10;

    /** indication du paramètre à renseigner pour l'utilisateur **/
	private static final String AIDE_USAGE = 
			"usage : data:\\\\MonSource.txt";
	
    /** suffixe des fichiers pris en charge **/
	private static final String SUFFIXE_FICHIER = ".txt";
	
	/**
	 * Constructeur de la classe Fichier.
	 * <br>
	 * Initialise les objets nécessaires pour lire le fichier 
	 * spécifié par le chemin fourni.
	 * <p>
	 * Vérifie si le chemin du fichier est valide et si le 
	 * fichier a une extension correcte (.txt ou .TXT). 
	 * 
	 * @param cheminFichier : Le chemin du fichier à lire.
	 */
	public Fichier(String cheminFichier) {
		if (!cheminFichier.isEmpty()) {
			try {
				fichierExploite = new File(cheminFichier);

				if (!fichierExploite.getName()
									.toLowerCase()
									.endsWith(SUFFIXE_FICHIER)){
					
					err.println(ERREUR_EXTENSION_FICHIER); 
				}
				
				lecteurFichier = new FileReader(cheminFichier);
				tamponFichier = new BufferedReader(lecteurFichier); 
				
			} catch (IOException pbOuverture) {
				err.println(ERREUR_OUVERTURE_FICHIER + cheminFichier);
				err.println(AIDE_USAGE);
			}
		} else {
			err.println(ERREUR_FORMAT_PARAMETRE);
			err.println(AIDE_USAGE);
		}
	} 

	/**
	 * Lit le contenu d'un fichier texte ligne par ligne et retourne un tableau 
	 * de chaînes de caractères contenant chaque ligne du fichier.
	 * <br>
	 * La méthode commence par compter le nombre de lignes dans le fichier 
	 * afin de créer un tableau de la taille appropriée. 
	 * Ensuite, elle relit le fichier pour remplir ce tableau avec les lignes lues.
	 * <br>
	 * Si une erreur survient pendant la lecture du fichier, 
	 * un message d'erreur est affiché et la méthode retourne null.
	 * 
	 * @return Un tableau de chaînes de caractères contenant les lignes du fichier, 
	 *         ou null si une erreur survient.
	 */
	public String[] contenuFichier() {
		String[] contenu = null;
		int nbLignes = 0;
		String ligneAct;

		try {
			while ((ligneAct = tamponFichier.readLine()) != null) {
				nbLignes++;
			}

			try {
				tamponFichier.close();
			} catch (IOException pbFermeture) {
				err.println(ERREUR_FERMETURE_FICHIER);
			}

			// Réinitialiser le BufferedReader pour relire le fichier
			lecteurFichier = new FileReader(fichierExploite);
			tamponFichier = new BufferedReader(lecteurFichier);

			contenu = new String[nbLignes]; 
			int index = 0;

			while ((ligneAct = tamponFichier.readLine()) != null) {
				contenu[index] = ligneAct;
				index++;
			}


		} catch (IOException pbContenu) {
			err.println(ERREUR_CONTENU_FICHIER);
			contenu = null;
		}

		return contenu;
	}
	
	/**
	 * Retourne la taille du fichier en octets.
	 * 
	 * @return La taille du fichier en octets. 
	 * Si le fichier n'existe pas ou ne peut pas être lu, retourne -1.
	 */
	public double tailleFichier() {
		double tailleFichier;
		
		tailleFichier = -1.0;

		if (fichierExploite.length() > 1) {
			return fichierExploite.length();
		}
		
		return tailleFichier; //STUB
		
	}
	
	/**
	 * Affiche le contenu d'un tableau de chaînes de caractères, 
	 * où chaque élément du tableau représente une ligne d'un fichier.
	 * <br>
	 * Les sauts de ligne vides sont conservés dans l'affichage.
	 */
	public void affichageFichier() {
	    String[] contenu = contenuFichier();

        for (String ligne : contenu) {
            System.out.println(ligne);
        }
	    
	}
	
	/**
	 * Compare la taille du fichier actuel avec la taille d'un autre fichier 
	 * pour déterminer le taux de compression. 
	 * <br>
	 * Le taux de compression est calculé comme le rapport de la taille 
	 * du fichier actuel sur la taille du fichier à comparer.
	 * <ul>
	 *   <li>Si le taux est supérieur à 1, alors la compression est efficace.</li>
	 *   <li>Si le taux est égal à 1, alors la compression est nulle.</li>
	 *   <li>Si le taux est inférieur à 1, alors la compression est inefficace.</li>
	 * </ul>
	 * 
	 * @param tailleFichierCompare La taille du fichier à comparer. 
	 * Doit être supérieure à 0.
	 * @return Le rapport entre la taille du fichier actuel et celle du fichier à comparer.
	 * @throws IllegalArgumentException Si la taille du fichier à comparer est inférieure ou égale à 0.
	 */
	public double rapportEntreDeuxFichiers(double tailleFichierCompare) {
	    double tailleFichierActuel = tailleFichier();

	    if (tailleFichierCompare <= 0) {
	        throw new IllegalArgumentException(ERREUR_NUMERATEUR_INVALIDE);
	    }

	    return tailleFichierActuel / tailleFichierCompare;
	}
	
	/**
	 * Retourne l'objet File représentant le fichier exploité.
	 * 
	 * @return fichier exploité.
	 */
	public File getFichierExploite() {
		return fichierExploite;
	}
	
}
