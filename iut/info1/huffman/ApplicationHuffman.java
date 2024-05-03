/**
 * ApplicationHuffman			01/05/2024
 * IUT De RODEZ					Pas de copyrights
 */

package iut.info1.huffman;

import iut.info1.huffman.fichier.GestionFichier;

import java.util.Arrays;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;

/**
 * Permet d'effectuer des operations de compression et
 * de decompression de fichiers
 * @author Romain Porcer, Hugo Robles, Tom Gutierrez, Erwan Thierry
 */
public class ApplicationHuffman {

	/**
	 * Crée une compression des données passés en paramètres
	 * @param contenuFichier, le contenu à compresser
	 * @return une chaîne de caractères composé de binaire. 
	 */
	public static String compressionDonnees(String[] contenuFichier) {

		Object[][] occurencesContenu;

		Object[] caracteresContenu;
		float[] frequenceCaracteres;
		ArbreBinaireHuffman ArbreHuffmanAssocie;
		String[][] donneesArbreBinaire;
		boolean estTrouve;
		
		String contenuCompresse;
		String contenuFichierExploitable;
		String cheminCaractere;

		occurencesContenu = GestionFichier.compterOccurrence(contenuFichier);

		caracteresContenu = new Object[occurencesContenu.length];


		for (int indiceParcours = 0;
				indiceParcours < occurencesContenu.length;
				indiceParcours++) {

			caracteresContenu[indiceParcours] = occurencesContenu[indiceParcours][0];

		}


		frequenceCaracteres = GestionFichier.calculFrequences(GestionFichier.compterOccurrence(contenuFichier));

		ArbreHuffmanAssocie = ArbreBinaireHuffman.insertionHuffman(caracteresContenu, frequenceCaracteres);

		donneesArbreBinaire = ArbreHuffmanAssocie.parcoursProfondeur();	

		System.out.println(Arrays.toString(frequenceCaracteres));
		System.out.println(Arrays.deepToString(donneesArbreBinaire));

		cheminCaractere = "";
		contenuCompresse = "";
		contenuFichierExploitable = "";

		for (int indiceParcours = 0;
				indiceParcours < contenuFichier.length;
				indiceParcours++) {

			contenuFichierExploitable += contenuFichier[indiceParcours];
		}
		

		for (int indiceParcours = 0;
				indiceParcours < donneesArbreBinaire.length;
				indiceParcours++) {
			
			cheminCaractere = "";

			for (String binaire : donneesArbreBinaire[indiceParcours][1].split(", ")) {
				cheminCaractere += binaire;
			}
			
			donneesArbreBinaire[indiceParcours][1] = cheminCaractere;
		}

		for (char caractere : contenuFichierExploitable.toCharArray()) {

			estTrouve = false;
			
			for (int indiceParcours = 0;
					indiceParcours < donneesArbreBinaire.length && !estTrouve;
					indiceParcours++) {

				if (caractere == donneesArbreBinaire[indiceParcours][0].charAt(0)) {
					contenuCompresse += donneesArbreBinaire[indiceParcours][1];
					estTrouve = true;
				}
				

			}
			
		}

		return contenuCompresse;

	}

}
