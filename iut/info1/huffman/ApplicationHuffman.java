/**
 * ApplicationHuffman.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman;

/**
 * Permet d'effectuer des operations de compression et
 * de decompression de fichiers
 */
public class ApplicationHuffman {

	/** le format du paramètre est invalide **/
	public static final String ERREUR_FORMAT_PARAMETRE =
			"erreur : Le format du paramètre renseigné invalide.";

	public static final String FOLDER_URL = "/Users/hugorobles/Desktop/codage-huffman/tests/";

	/** suffixe des fichiers compréssés pris en charge **/
	private static final String SUFFIXE_FICHIER_DECOMPRESSE = ".bin";

	public static void main(String[] args) {

			Compression.main("fichier2.txt", "resultats/");

			//Decompression.main("resultats/fichier2.bin", "resultats/fichier2_EncodeH.txt", "compt/");
	}

	


}