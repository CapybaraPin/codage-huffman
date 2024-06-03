/**
 * GestionFichier.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman.fichier;

import static java.lang.System.err;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;


/**
 * Gère la création, la lecture et tout autres outils ayant pour
 * but de gérer les fichiers
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

    /** problème de lecture du fichier **/
    private static final String ERREUR_FICHIER_LECTURE =
	    "Il y a eu un problème lors de l'ovuerture du fichier : ";

    /** le numerateur indiqué ne respecte pas les critères **/
    private static final String ERREUR_NUMERATEUR_INVALIDE =
	    "erreur : Impossible d'effectuer la division, numérateur invalide.";
    
    /** indication du paramètre à renseigner pour l'utilisateur **/
    private static final String AIDE_USAGE = 
	    "usage : data:\\\\MonSource.txt";
    
    /** code d'erreur lors d'un soucis de lecture fichier */
    public static final int CODE_ERREUR_FICHIER_LECTURE = 10;
    
    /** suffixe des fichiers pris en charge **/
    private static final String SUFFIXE_FICHIER = ".txt";

    /** suffixe des fichiers compréssés pris en charge **/
    private static final String SUFFIXE_FICHIER_DECOMPRESSE = ".bin";

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
	
	int nbLignes;
	
	BufferedReader lecteur;
	
	File fichier;
	
	String[] contenu;

	if (!cheminFichier.isEmpty()) {
	    try {
		fichier = new File(cheminFichier);

		if (!fichier.getName().toLowerCase().endsWith(SUFFIXE_FICHIER)){
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

	nbLignes = 0;

	try {
	    while (lecteur.readLine() != null) {
		nbLignes++;
	    }

	    String ligneAct;
	    int index;
	    
	    index = 0;
	    lecteur.close();
	    lecteur = new BufferedReader(new FileReader(cheminFichier));
	    contenu = new String[nbLignes]; 

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
	
	double tailleFichier;
	
	File fichierAnalyse;

	tailleFichier = -1.0;
	if (!cheminFichier.isEmpty()) {
	    try {
		fichierAnalyse = new File(cheminFichier);

		if (!fichierAnalyse.getName().toLowerCase().endsWith(SUFFIXE_FICHIER)){
		    err.println(ERREUR_EXTENSION_FICHIER); 
		} else {
		    tailleFichier = fichierAnalyse.length(); 
		}
	    } catch (NullPointerException pbOuverture) {
		err.println(ERREUR_OUVERTURE_FICHIER + cheminFichier);
		err.println(AIDE_USAGE);
	    }
	} else {
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    err.println(AIDE_USAGE);
	}
	return tailleFichier;
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
    public static double rapportEntreDeuxFichiers(double tailleFichierInitiale,
	                                          double tailleFichierCompresse) {
	return tailleFichierInitiale/tailleFichierCompresse;
    }

    /**
     * Rechercher tous les caractères stockés dans 
     * un tableau de chaîne de caractères, et d'en extraire 
     * le nombre de fois qu'ils apparaissent dans le tableau 
     * de chaîne entier.
     * @param lignesFichier, un tableau pour qui chacune des
     * chaînes de caractères est la ligne d'un fichier
     * 
     * @return un tableau de chaîne de caractères, avec l'occurence
     * de chaque caractère
     * 
     */
    public static String[][] compterOccurrence(String[] lignesFichier) {

	if (lignesFichier.length == 0) {
	    return  new String[][]{{"", "0"}};
	}

	String contenuFichier = String.join("\n", lignesFichier);
	
	boolean estDejaApparue;
	
	String[][] occurrencesCaracteres;

	occurrencesCaracteres = new String[][]{
	    {String.valueOf(contenuFichier.charAt(0)), "0"}		
	};

	for (int indexRecherche = 0;
		 indexRecherche < contenuFichier.length();
		 indexRecherche++) {

	    estDejaApparue = false;
	    for (String[] caractereCompte : occurrencesCaracteres) {

		if (     caractereCompte[0].toCharArray()[0] 
	             == (contenuFichier.charAt(indexRecherche))) {
		    estDejaApparue = true;

		    caractereCompte[1] =  String.valueOf(
			    Integer.parseInt(caractereCompte[1]) + 1);
		}
	    }

	    if (!estDejaApparue) {

		String[][] nouvelleTableDesOccurences =
			new String[occurrencesCaracteres.length+1][2];

		for (int indexActuel = 0;
			 indexActuel < occurrencesCaracteres.length;
			 indexActuel++) {

		    nouvelleTableDesOccurences[indexActuel] =
			    occurrencesCaracteres[indexActuel];
		}

		nouvelleTableDesOccurences[occurrencesCaracteres.length][0] =
			String.valueOf(contenuFichier.charAt(indexRecherche));

		nouvelleTableDesOccurences[occurrencesCaracteres.length][1] = "1";
		occurrencesCaracteres = nouvelleTableDesOccurences;
	    }
	}
	return occurrencesCaracteres;
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
     *  puis la position de ce dernier dans l'arborescence Huffman
     *  
     * @param nomDuFichier le nom du fichier à enregistrer
     */
    public static void stockageABHuffman(String[][] tblArbreHuffman, String nomDuFichier) {
	
	String encodageDuCaractere;

	try {
	    File fichierEnregistrement = new File(
		                                 nomDuFichier
		                               + "_EncodeH"
		                               + SUFFIXE_FICHIER);

	    if (fichierEnregistrement.createNewFile()) {
		System.out.println("Fichier créé " 
			+ fichierEnregistrement.getName());
	    } else {
		System.out.println("Ce fichier existe déjà"); //TODO constante
	    }

	} catch (IOException pbLecture) {
	    System.err.println(ERREUR_FICHIER_LECTURE + nomDuFichier);//TODO ERREUR_FICHIER_CREATION
	    System.exit(CODE_ERREUR_FICHIER_LECTURE);
	}

	try {
	    FileWriter ecritureFichier = new FileWriter(
		                                 nomDuFichier
		                                 + "_EncodeH"
		                                 + SUFFIXE_FICHIER);

	    for (String[] feuilleABHuffman : tblArbreHuffman) {

		encodageDuCaractere = Integer.toBinaryString(Character.hashCode(
		                          feuilleABHuffman[0].charAt(0)
		                      ));

		ecritureFichier.write(
			String.format("codeHuffman = %s ; encode = %s ; symbole = %1s\n",
			String.join("", feuilleABHuffman[1].split(", ")), encodageDuCaractere,
				   (feuilleABHuffman[0].equals("\n") ? "LF" : feuilleABHuffman[0])));
	    }
	    ecritureFichier.close(); // TODO ERREUR FERMETURE
	} catch (IOException pbEcriture) {
	    // TODO ERREUR ECRITURE
	}
    }

    /**
     * L'objectif est d'afficher le contenu d'un tableau de
     * chaîne de caractères, pour qui chaque éléments de
     * cette chaîne est la ligne d'un fichier.
     * <br>
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
     * @param occurrences Tableau contenant le caractère et sont
     * nombre d'occurences. 
     * @return frequences d'apparition de chaque caractères. 
     */
    public static double[] calculFrequences(String[][] occurrences){
	
	int nbOccurrences;
	int nbLignes;
	
	double[] frequences;

	nbOccurrences = 0;
	for (String[] occurrence : occurrences) {
	    nbOccurrences += Integer.valueOf(occurrence[1]);
	}

	if (nbOccurrences <= 0 || nbOccurrences >= Integer.MAX_VALUE) {
	    err.println(ERREUR_NUMERATEUR_INVALIDE);
	    return null;
	}

	nbLignes = occurrences.length;
	frequences = new double[nbLignes];

	for(int indiceFreq = 0; indiceFreq < nbLignes; indiceFreq++) {
	    frequences[indiceFreq] = Double.valueOf(occurrences[indiceFreq][1]) 
		    / nbOccurrences;
	}
	return frequences;		
    }

    /**
     * Convertit un tableau de codage type String[] issue
     * d'un fichier.<br>
     * Exemple de tableau de codage en paramètre :
     * <ul>
     * 	<li>
     *  {"codeHuffman =             0010 ; encode = 0000000001100010 ; symbole = b",<br>
     *   "codeHuffman =                0 ; encode = 0000000001100101 ; symbole = e",<br>
     *   "codeHuffman =              101 ; encode = 0000000001100001 ; symbole = a"} <br>
     * 	</li>
     * return :
     * 	<li>
     * 	{{"0000000001100010", "0010"}, {"0000000001100101", "0"}, {"0000000001100001", "101"}}
     *  </li>
     * </ul>
     * 
     * TODO : Vérifier le format du tableau de codage
     * 
     * @param tabFichierCodages 
     * @return tabCodages
     */
    public static String[][] conversionTableauCodage(String[] tabFichierCodages) {
	
	String codeHuffman;
	String encode;
	String ligneActuelle;
	
	String[] parties;
	
	String[][] tabCodages;

	if(tabFichierCodages.length == 0) {
	    return new String[][]{{}};
	}

	tabCodages = new String[tabFichierCodages.length][2];
	for (int index = 0; index < tabFichierCodages.length; index++) {
	    ligneActuelle = tabFichierCodages[index];
	    if(!verifierFormatTableauCodage(ligneActuelle)) {
		err.println(ERREUR_FORMAT_PARAMETRE);
		// TODO : Throw ?
		return new String[][]{{}};
	    }

	    parties     = ligneActuelle.split(";");
	    codeHuffman = parties[0]   .split("=")[1].trim();
	    encode      = parties[1]   .split("=")[1].trim();

	    tabCodages[index][0] = encode;
	    tabCodages[index][1] = codeHuffman;
	}
	return tabCodages;
    }

    /**
     * Convertit une chaîne de caractères en binaire à
     * l'aide d'un tableau de codage.
     * Ne vérifie pas le tableau de codages.
     * @param tabCodages un tableau de codage généré par la compression
     * @param chaine une chaîne de caractères à compresser
     * @return chaineBinaire
     */
    public static String conversionBinaire(String[][] tabCodages, String chaine) {
	
	String chaineBinaire;
	
	boolean caractereTrouve;
	
	int[] hashCodesEncodes;

	if (chaine.isEmpty()) {
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    return "";
	}
	
	hashCodesEncodes = new int[chaine.length()];
	for (int i = 0; i < tabCodages.length; i++) {
	    hashCodesEncodes[i] = Integer.parseInt(tabCodages[i][0], 2);
	}
	chaineBinaire = "";
	for (char caractereChaine : chaine.toCharArray()) {
	    caractereTrouve = false;
	    for (int indexCodage = 0; indexCodage < tabCodages.length && !caractereTrouve; indexCodage++) {
		
		int hashCodeCaractere;
		hashCodeCaractere = Character.hashCode(caractereChaine);
		
		if (hashCodeCaractere == hashCodesEncodes[indexCodage]) {
		    chaineBinaire += tabCodages[indexCodage][1];
		    caractereTrouve = true;
		}
	    }
	}
	return chaineBinaire;
    }

    /**
     * Enregistre un fichier binaire à partir d'une String
     * 
     * Exemple de chaineBinaire : "011000100"
     * 
     * Est stocké sous forme d'octet dans le fichier.
     * Si le dernier octet de la chaîne est incomplet,
     * on ajoute des 0 pour le compléter.
     * Le nombre de zero ajouté est stocké dans le dernier
     * octet du fichier.
     * 
     * Exemple de fichier binaire obtenu : 
     * 011000100000000000000111
     * @param chaineBinaire la chaine binaire à enregistrer
     * @param nomFichier le nom du fichier sur lequel enregistrer
     * 
     */
    public static void enregistrementFichierBinaire(String chaineBinaire, String nomFichier) {
	
	int longueur = chaineBinaire.length();
	int longueurDonnees = (int) Math.ceil((double) longueur / 8);
	int indexDonnees;
	int nbZeroComplementaire;
	
	byte[] donnees = new byte[longueurDonnees+1];

	if (chaineBinaire.isEmpty() || nomFichier.isEmpty()) {
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    return;
	}

	try {
	    File fichierEnregistrement = new File(
		                              nomFichier
		                            + SUFFIXE_FICHIER_DECOMPRESSE);

	    if (fichierEnregistrement.createNewFile()) {
		System.out.println("Fichier créé " 
			+ fichierEnregistrement.getName());
	    } else {
		System.out.println("Ce fichier existe déjà"); //TODO constante
		return; // TODO proposer une solution, écraser le fichier ?, renommer le fichier ?
	    }

	} catch (IOException pbLecture) {
	    System.err.println(ERREUR_FICHIER_LECTURE + nomFichier);//TODO ERREUR_FICHIER_CREATION
	    System.exit(CODE_ERREUR_FICHIER_LECTURE);
	}

	indexDonnees = 0;
	nbZeroComplementaire = 0;
	for (int i = 0; i < longueur; i += 8) {
	    String octet = chaineBinaire.substring(i, Math.min(i + 8, longueur));

	    while (octet.length() < 8) {
		octet = "0" + octet;
		nbZeroComplementaire++;
	    }
	    donnees[indexDonnees++] = (byte) Integer.parseInt(octet, 2);
	}
	donnees[longueurDonnees] = (byte) nbZeroComplementaire;

	try {
	    FileOutputStream fluxEcriture = new FileOutputStream(
		    nomFichier
		    +SUFFIXE_FICHIER_DECOMPRESSE );

	    fluxEcriture.write(donnees);
	    fluxEcriture.close(); // TODO ERREUR FERMETURE
	} catch (IOException pbEcriture) {
	    // TODO ERREUR ECRITURE
	}
    }

    /**
     * Vérifie si le format d'une ligne de codage est correct.
     * <br>
     * Tiens en compte de si :
     * <ul>
     *  <li>La ligne contient bien un code huffman</li>
     *  <li>La ligne contient bien un encodage</li>
     * 	<li>La ligne contient bien un symbole</li>
     *  <li>Les ";" sont bien présents</li>
     *  <li>La ligne est vide</li>
     *  <li>Le symbole correspond bien au caractère encodé</li>
     * </ul>
     * @param ligne
     * @return boolean
     */
    public static boolean verifierFormatTableauCodage(String ligne) {
	
	String codeHuffman;
	String patternCodeHuffman;
	String encode;
	String patternEncode;
	String patternSymbole;
	
	String[] parties;

	char symbole;
	char symboleEncode;
	char avantDernierCaractere;
	
	boolean estLF;

	if (ligne.isEmpty()){
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    return false;
	}

	parties = ligne.split(";");
	if(parties.length != 3) {
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    return false;
	}

	codeHuffman = parties[0].split("=")[1].trim();
	encode      = parties[1].split("=")[1].trim();
	symbole     = ligne.charAt(ligne.length()-1);

	patternCodeHuffman = "^[01\\s]{"+ codeHuffman.length() +"}$";
	patternEncode      = "^[01]{"+ encode.length() +"}$";
	patternSymbole     = "^.$";
	if (   !Pattern.matches(patternCodeHuffman, codeHuffman)
	    || !Pattern.matches(patternEncode, encode)
	    || !Pattern.matches(patternSymbole, String.valueOf(symbole))){
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    return false;
	}

	avantDernierCaractere = ligne.charAt(ligne.length()-2);
	symboleEncode = (char) Integer.parseInt(encode, 2);
	estLF = avantDernierCaractere == 'L' && symbole == 'F';
	if(symbole != symboleEncode && !estLF ) {
	    err.println(ERREUR_FORMAT_PARAMETRE);
	    return false;
	}
	return true;
    }

    /**
     * Permet la récupération des données dans un fichier binaire
     * @param cheminFichier le chemin du fichier binaire
     * @return le contenu du fichier
     *
     */
    public static String recupererDonneesFichierCompresse(String cheminFichier) {

	String contenuBinaireFichier;

	FileInputStream inputStream = null;
	
	try {
	    inputStream = new FileInputStream(cheminFichier);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}

	int data;
	String donneesData;

	contenuBinaireFichier = "";
	try {
	    while ((data = inputStream.read()) != -1) {

		donneesData = String.valueOf(Integer.toBinaryString(data));

		if (donneesData.length() != 8) {
		    donneesData = "0".repeat(8 - donneesData.length())
			    + donneesData;
		}
		contenuBinaireFichier += donneesData;
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}		
	return contenuBinaireFichier;
    }
}