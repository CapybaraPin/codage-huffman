/**
 * Compression.java          30/05/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;
import iut.info1.huffman.fichier.Fichier;
import iut.info1.huffman.fichier.FichierBinaire;

import static java.lang.System.err;
import static java.lang.System.out;
import static iut.info1.huffman.ApplicationHuffman.*;

public class Compression {

    /** chemin du fichier à compresser */
    private String cheminFichier;
    
    /** chemin du dossier où stocker le fichier .bin et le fichier clé */
    private String cheminRetour;

    /** Constructeur de la compression */
    public Compression(String cheminFichier, String cheminRetour) {
	this.cheminFichier = cheminFichier;
	this.cheminRetour = cheminRetour;
    }

    /**
     * Execute l'algorithme de compression des données en fonction
     * des différents chemins donnés en paramètre dans le constructeur
     * @return un tableau contenant les occurrences par caractères
     *         du fichier compressé ainsi que l'arbre binaire généré
     *         par la compression du fichier (donc sa racine)
     */
    public Object execute(){
	out.println("------------------------------------------");
	out.println("Compression de : " + cheminFichier);

	String chaineHuffmanValide;
	String nomFichier;
	
	String[] contenuFichier;
	String[] contenuTableauCodage;
	String[][] tableauCodageValide;
	StringBuilder chaineHuffman;
	
	Fichier fichier;
	Fichier fichierTableauCodage;
	FichierBinaire fichierBinaire;
	
	GestionFrequence frequences;
	ArbreBinaireHuffman arbre;

	/* Appel d'intéraction avec le fichier */
	fichier = new Fichier(cheminFichier);
	contenuFichier = fichier.contenuFichier();
	nomFichier = fichier.nomFichier();

	out.println("Taille d'origine : " + fichier.tailleFichier() + " octets");
	out.println("Nom du fichier : " + fichier.getFichierExploite().getName());

	/* Appel du calcul de fréquence */
	frequences = new GestionFrequence(contenuFichier);
	frequences.compterOccurrence();
	frequences.calculFrequences(); // TODO : Ne pas re-calculer les occurrences

	/* Appel de l'arbre huffman */
	Object[] tblCaracteres = new Object[frequences.getFrequences().length];
	double[] tblFrequences = new double[frequences.getOccurrences().length];

	/* Insertion des caractères et fréquences dans les tableaux */
	for (int i = 0; i < frequences.getOccurrences().length; i++) {
	    tblCaracteres[i] = frequences.getOccurrences()[i][0];
	    tblFrequences[i] = frequences.getFrequences()[i];
	}

	arbre = ArbreBinaireHuffman.insertionHuffman(tblCaracteres, tblFrequences);

	out.println("Arbre Huffman créé avec succès.");

	/* Affichage de l'arbre en console */
	arbre.afficherArbre();

	tableauCodageValide = arbre.parcoursProfondeur();

	/* Convertir le tableau de codage en une seule chaine */
	for (String[] mark : tableauCodageValide) {
	    mark[1] = mark[1].replaceAll(", ", "");
	}

	GestionTableauCodage tabCodage = new GestionTableauCodage(
		                                       tableauCodageValide);
	
	fichierTableauCodage = new Fichier(cheminRetour
		                           + nomFichier 
		                           + "_EncodeH.txt");
	contenuTableauCodage = tabCodage.formaterABHuffman();
	fichierTableauCodage.ecritureFichier(contenuTableauCodage);

	/* Chaine à envoyer dans le fichier .bin */
	chaineHuffman = new StringBuilder();

	for (String str : contenuFichier) {
	    chaineHuffman.append(str).append("\r\n");
	}

	chaineHuffmanValide = conversionBinaire(tableauCodageValide, 
		                                chaineHuffman.toString());

	fichierBinaire = new FichierBinaire(cheminRetour + nomFichier + ".bin");
	fichierBinaire.enregistrementFichierBinaire(chaineHuffmanValide);

	out.println("Fichier compréssé : " + cheminRetour + nomFichier + ".bin");
	out.println("Taille du fichier compréssé : " 
	            + fichierBinaire.tailleFichier() + " octets");
	out.println("Taux de compression : "
	            + fichier.rapportEntreDeuxFichiers(fichierBinaire.tailleFichier())
	            );

	return new Object[] {frequences.getOccurrences(), arbre};
    }

    /**
     * Convertit une chaîne de caractères en binaire à
     * l'aide d'un tableau de codage.
     * Ne vérifie pas le tableau de codages.
     * @param tabCodages un tableau de codage généré par la compression
     * @param chaine une chaîne de caractères à compresser
     * @return chaineBinaire la chaîne compressé
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
	    for (int indexCodage = 0;
		     indexCodage < tabCodages.length && !caractereTrouve;
		     indexCodage++) {
		int hashCodeCaractere = Character.hashCode(caractereChaine);
		if (hashCodeCaractere == hashCodesEncodes[indexCodage]) {
		    chaineBinaire += tabCodages[indexCodage][1];
		    caractereTrouve = true;
		}
	    }
	}
	return chaineBinaire;
    }
}