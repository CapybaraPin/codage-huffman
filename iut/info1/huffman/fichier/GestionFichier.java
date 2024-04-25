/**
 * GestionFichier.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman.fichier;

import static java.lang.System.err;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * TODO commenter cette classe
 */
public class GestionFichier {


	/** impossible d'ouvrir le fichier renseigné */
	private static final String ERREUR_OUVERTURE_FICHIER = 
			"erreur : Impossible d'ouvrir le fichier au lien : ";

	/** impossible de fermer le fichier renseigné */
	private static final String ERREUR_FERMETURE_FICHIER = 
			"erreur : Impossible de fermer le fichier.";

	/** impossible d'acceder au contenu du fichier **/
	private static final String ERREUR_CONTENU_FICHIER =
			"erreur : Impossible d'accéder au contenu du fichier.";

	/** le format du paramètre est invalide **/
	private static final String ERREUR_FORMAT_PARAMETRE =
			"erreur : Le format du paramètre renseigné invalide.";

	/** le format du fichier n'est pas .txt ou .TXT **/
	private static final String ERREUR_EXTENSION_FICHIER =
			"erreur : Le format du fichier doit être .txt ou .TXT";

	/** indication du paramètre à renseigner pour l'utilisateur **/
	private static final String AIDE_USAGE = 
			"usage : data:\\\\MonSource.txt";
	
    /** code d'erreur lors d'un soucis de lecture fichier */
    public static final int CODE_ERREUR_FICHIER_LECTURE = 10;

    /** problème de lecture du fichier **/
    private static final String ERREUR_FICHIER_LECTURE
    = "Il y a eu un problème lors de l'ovuerture du fichier : ";
	
    /** suffixe des fichiers pris en charge **/
	private static final String SUFFIXE_FICHIER = ".txt";
	
	/** le numerateur indiqué ne respecte pas les critères **/
	private static final String ERREUR_NUMERATEUR_INVALIDE
	= "erreur : Impossible d'effectuer la division, numérateur invalide.";
	
	/**
	 * Lit le contenu d'un fichier texte ligne par ligne 
	 * et retourne un tableau de chaînes de caractères
	 * contenant chaque ligne du fichier.
	 *
	 * @param cheminFichier Le chemin du fichier à lire.
	 * @return contenu Un tableau de chaînes de caractères 
	 * contenant les lignes du fichier, null si une erreur survient.
	 */
	public static String[] lectureFichier(String cheminFichier) {
		BufferedReader lecteur;
		File fichier;
		String[] contenu;

		if (!cheminFichier.isEmpty()) {
			try {
				fichier = new File(cheminFichier);

				if (!fichier.getName().toLowerCase().endsWith(".txt")){
					err.println(ERREUR_EXTENSION_FICHIER); 
				}
			} catch (NullPointerException pbOuverture) {
				err.println(ERREUR_OUVERTURE_FICHIER + cheminFichier);
				err.println(AIDE_USAGE);
			}
		} else {
			err.println(ERREUR_FORMAT_PARAMETRE);
			err.println(AIDE_USAGE);
		}

		try {
			lecteur = new BufferedReader(new FileReader(cheminFichier));
		} catch (FileNotFoundException pbOuverture) {
			lecteur = null;
			err.println(ERREUR_OUVERTURE_FICHIER + cheminFichier);
			err.println(AIDE_USAGE);
		}

		int nbLignes = 0;

		try {
			while (lecteur.readLine() != null) {
				nbLignes++;
			}

			lecteur.close(); 
			lecteur = new BufferedReader(new FileReader(cheminFichier));

			contenu = new String[nbLignes]; 

			String ligneAct;
			int index = 0;

			while ((ligneAct = lecteur.readLine()) != null) {
				contenu[index] = ligneAct;
				index++;
			}

		} catch (IOException pbContenu) {
			err.println(ERREUR_CONTENU_FICHIER);
			lecteur = null;
			contenu = null;

		} 

		try {
			if (lecteur != null)
				lecteur.close();
		} catch (IOException pbFermeture) {
			err.println(ERREUR_FERMETURE_FICHIER);
		}


		return contenu;
	}

	/**
	 * Permet d'obtenir la taille d'un fichier
	 * @param cheminFichier le chemin du fichier analysé
	 * @return la taille du fichier
	 */
	public static double tailleDuFichier(String cheminFichier) {

		File fichierAnalyse;


		if (!cheminFichier.isEmpty()) {
			try {
				fichierAnalyse = new File(cheminFichier);


				if (!fichierAnalyse.getName().toLowerCase().endsWith(".txt")){
					err.println(ERREUR_EXTENSION_FICHIER); 
				} else {
					return fichierAnalyse.length();
				}
			} catch (NullPointerException pbOuverture) {
				err.println(ERREUR_OUVERTURE_FICHIER + cheminFichier);
				err.println(AIDE_USAGE);
			}


		} else {
			err.println(ERREUR_FORMAT_PARAMETRE);
			err.println(AIDE_USAGE);
		}

		return -1.0;

	}

	/**
	 * Permet de comparer la taille de deux fichiers
	 * pour en extraire un taux. Le but est de comparer
	 * le taux de compression d'un fichier
	 * Si le taux est supérieur à 1, alors la compression est efficace
	 * Si il est de 1, la compression est nulle
	 * Sinon, la compression est inefficace 
	 * @param tailleFichierInitiale la taille du fichier à compresser
	 * @param tailleFichierCompresse la taille du fichier compressé
	 * @return le rapport entre le fichier initial et le fichier compressé
	 */
	public static double rapportEntreDeuxFichiers(double tailleFichierInitiale
						      , double tailleFichierCompresse) {

		return tailleFichierInitiale/tailleFichierCompresse;
	}
	
	/**
	 * rechercher tous les caractères stockés dans 
	 * un tableau de chaîne de caractères, et d'en extraire 
	 * le nombre de fois qu'ils apparaissent dans le tableau 
	 * de chaîne entier.
	 * @param ligneFichier, un tableau pour qui chacune des
	 * chaînes de caractères est la ligne d'un fichier
	 * 
	 * @return un tableau de chaîne de caractères, avec l'occurence
	 * de chaque caractère
	 * 
	 */
	public static String[][] compterOccurrence(String[] ligneFichier) {
		
		if (ligneFichier.length == 0) {
			return  new String[][]{{"", "0"}};
		}
		
		String[][] occurrencesDesCaracteres;
		String contenuFichier = String.join("\n", ligneFichier);
		boolean estDejaApparue;
		

		
		occurrencesDesCaracteres = new String[][]{
			{String.valueOf(contenuFichier.charAt(0)), "0"}		
		};
		
		
		for (int indexDeRecherche = 0;
				indexDeRecherche < contenuFichier.length();
				indexDeRecherche++) {
		
			estDejaApparue = false;
			for (String[] caractereCompte : occurrencesDesCaracteres) {
							
				// Si le caractère actuel est dans les caractères compté
				if (caractereCompte[0].toCharArray()[0] == (contenuFichier.charAt(indexDeRecherche))) {
					estDejaApparue = true;
					
					// On ajoute 1
					caractereCompte[1] =  String.valueOf(Integer.parseInt(caractereCompte[1]) + 1);
				}
			}
			
			if (!estDejaApparue) {
				
				// On crée un tableau qui permet d'ajouter un caractère
				// aux occurrences
				String[][] nouvelleTableDesOccurences 
							= new String[occurrencesDesCaracteres.length + 1][2];
				
				// On transfert les éléments de notre table
				for (int indexActuel = 0;
						indexActuel < occurrencesDesCaracteres.length;
						indexActuel++) {
					
					nouvelleTableDesOccurences[indexActuel] 
							= occurrencesDesCaracteres[indexActuel];
				}
				
				// On ajoute le caractère trouvé
				nouvelleTableDesOccurences[occurrencesDesCaracteres.length][0]
						= String.valueOf(contenuFichier.charAt(indexDeRecherche));
				
				nouvelleTableDesOccurences[occurrencesDesCaracteres.length][1]
						= "1";
				
				// On remplace l'ancien tableau
				
				occurrencesDesCaracteres = nouvelleTableDesOccurences;
	
			}
		
		}
				
		return occurrencesDesCaracteres;
	}


	/**
	 * Enregistre une arborescence de Huffman
	 * générée par la classe GestionFichier. 
	 * Il mettra dans un fichier 3 éléments en évidence. 
	 * Le code huffman associé à un encodage Unicode UTF-8, 
	 * et le caractère associé à son encodage. 
	 * 
	 * @param tblArbreHuffman, nomDuFichier un tableau de tableau de chaînes
	 *  de caractères, ayant pour chaque sous-tableau un caractère
	 *   puis la position de ce dernier dans l'arborescence Huffman. Il est
  	 *   également demandé le nom du fichier compressé.
	 */
	public static void stockageABHuffman(String[][] tblArbreHuffman, String nomDuFichier) {

		String encodageDuCaractere;


		// System.out.printf("codeHuffman = %s ; encode = %s ; symbole = %s\n",
		// 				   feuilleABHuffman[1], encodageDuCaractere
		// 				   , feuilleABHuffman[0]);

		try {
			File fichierEnregistrement = new File(
											 	 nomDuFichier
											 	 +"_EncodeH"
											 	 +SUFFIXE_FICHIER);

			if (fichierEnregistrement.createNewFile()) {
				System.out.println("Fichier créé " 
						+ fichierEnregistrement.getName());
			} else {
				System.out.println("Ce fichier existe déjà");
			}

		} catch (IOException pbLecture) {
			System.err.println(ERREUR_FICHIER_LECTURE + nomDuFichier);
			System.exit(CODE_ERREUR_FICHIER_LECTURE);
		}

		try {
			FileWriter ecritureFichier = new FileWriter(
													   nomDuFichier
													   +"_EncodeH"
													   +SUFFIXE_FICHIER);

			// Ecriture de chaque données

			for (String[] feuilleABHuffman : tblArbreHuffman) {
				
				encodageDuCaractere = Integer.toBinaryString(Character.hashCode(
						feuilleABHuffman[0].charAt(0) // Converti le string en char
						));
				
				if (encodageDuCaractere.length() != 8) {
					encodageDuCaractere = "0".repeat(8 - encodageDuCaractere.length()) 
							+ encodageDuCaractere;
				}


				ecritureFichier.write(
						String.format("codeHuffman = %16s ; encode = %8s ; "
								+ "symbole = %1s\n",
								feuilleABHuffman[1], encodageDuCaractere,
								feuilleABHuffman[0]));
			}

			ecritureFichier.close();
		} catch (IOException pbEcriture) {
			// Too late !!
		}

	}
	
	/**
	 * L'objectif est d'afficher le contenu d'un tableau de
	 * chaîne de caractères, pour qui chaque éléments de
	 * cette chaîne est la ligne d'un fichier.
	 * \n
	 * Dans le cas où il existe plusieurs saut à la ligne vide, ces
	 * derniers seront conservés.
	 * 
	 * @param contenuFichier Tableau de chaîne de caractère
	 */
	public static void affichageFichier(String[] contenuFichier){
		for (String ligne : contenuFichier) {
			System.out.println(ligne);
		}
	}
	
	/**
	 * Calcul la fréquence d'apparition de chaque caractère. 
	 * 
	 * @param occurrences Tableau contenu le caractère et sont
	 * nombre d'occurences. 
	 * @return frequences d'apparition de chaque caractères. 
	 */
	public static float[] calculFrequences(String[][] occurrences){
		int nbOccurrences;
		int nbLignes;
		float[] frequences;
		
		nbOccurrences = 0;
		for (String[] occurrence : occurrences) {
			nbOccurrences += Integer.valueOf(occurrence[1]);
		}
		
		if (nbOccurrences <= 0 || nbOccurrences >= Integer.MAX_VALUE) {
			err.println(ERREUR_NUMERATEUR_INVALIDE);
			return null;
		}
		
		nbLignes = occurrences.length;
		frequences = new float[nbLignes];
		
		for(int indiceFreq = 0; indiceFreq < nbLignes; indiceFreq++) {
			frequences[indiceFreq] = Float.valueOf(occurrences[indiceFreq][1]) 
												/ nbOccurrences;
		}
		
		return frequences;		
	}

}
