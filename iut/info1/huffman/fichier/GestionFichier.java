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
			"usage : data\\\\MonSource.txt";
	
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
	 * @param tblArbreHuffman un tableau de tableau de chaînes
	 *  de caractères, ayant pour chaque sous-tableau un caractère
	 *   puis la position de ce dernier dans l'arborescence Huffman
	 */
	public static void stockageABHuffman(String[][] tblArbreHuffman) {
		//TODO Faire le corps de la fonction
		
		/*TODO Faire un tri dans l'ordre croissant en fct de l'encodage
		 * 	   Restituer l'encode de chaque caractères
		 * 	   Enregistrer les données dans un fichier
		 */
		
		for (String[] feuilleABHuffman : tblArbreHuffman) {
			System.out.printf("codeHuffman = %s ; encode = TODO ; symbole = %s\n",
							   feuilleABHuffman[1], feuilleABHuffman[0]);
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

}
