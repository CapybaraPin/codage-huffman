/**
 * ApplicationHuffman.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import iut.info1.huffman.fichier.Fichier;
import iut.info1.huffman.fichier.GestionFichier;
import iut.info1.huffman.arbre.ArbreBinaireHuffman;

/**
 * Permet d'effectuer des operations de compression et
 * de decompression de fichiers
 * @author Romain Porcer, Hugo Robles, Tom Gutierrez, Erwan Thierry
 */
public class ApplicationHuffman {
	
    private static final String FOLDER_URL = "C:/huffmantest/";
	
	/**
	 * Permet de compresser un fichier
	 * Demande le nom du fichier a compresser
	 * et effectue la compression
	 * @param args
	 */
	public static void main(String[] args) {
		
		String nomFichier;
		Scanner scanner = new Scanner(System.in);
		
		out.print("Entrez le nom de votre fichier : ");
		
		if (scanner.hasNext()) {
			
			nomFichier = scanner.next();
			compression(FOLDER_URL + nomFichier);		
		
		} else {
			out.print("USAGE : fichier.txt");
		}
		
	
	}
	
	/**
	 * Permet de compresser un fichier
	 * @param cheminFichier
	 */
	public static void compression(String cheminFichier) {
		out.println("------------------------------------------");
		out.println("Compression de : " + cheminFichier);
		
		Fichier fichier;
		Fichier fichierTableauCodage;
		Fichier fichierBinaire;
		ArbreBinaireHuffman arbre;
		String[] contenuFichier;
		String[][] occurrencesFichier;
		double[] frequencesFichier; 
		String[] contenuTableauCodage;
		String chaineBinaire;
		String[][] tableauCodageValide;
		
		fichier = new Fichier(cheminFichier);
		contenuFichier = fichier.contenuFichier();
		occurrencesFichier = GestionFichier.compterOccurrence(contenuFichier);
		frequencesFichier = GestionFichier.calculFrequences(occurrencesFichier);
		
		out.println("Taille : " + fichier.tailleFichier() + " octets");
		
		// Création des tableaux pour les caractères et leurs fréquences
		Object[] tblCaracteres = new Object[occurrencesFichier.length];
		double[] tblFrequences = new double[occurrencesFichier.length];
		
		// Insertion des caractères et fréquences dans les tableaux
		for (int i = 0; i < occurrencesFichier.length; i++) {
			tblCaracteres[i] = occurrencesFichier[i][0];
			tblFrequences[i] = frequencesFichier[i];
		}
		
		// Création de l'arbre binaire Huffman avec les caractères et leurs fréquences
		arbre = ArbreBinaireHuffman.insertionHuffman(tblCaracteres, tblFrequences);
		
		// Affichage ou utilisation de l'arbre créé
		out.println("Arbre Huffman créé avec succès.");
		
		// Affichage de l'arbre en console
		arbre.afficherArbre();
		
		System.out.println(Arrays.deepToString(arbre.parcoursProfondeur()));
		
		GestionFichier.stockageABHuffman(transformerListe(arbre.parcoursProfondeur()), cheminFichier);
		
		// Ouverture du fichier tableaud de codage
		fichierTableauCodage = new Fichier(cheminFichier + "_EncodeH.txt");
		contenuTableauCodage = fichierTableauCodage.contenuFichier();
		
		tableauCodageValide = GestionFichier.conversionTableauCodage(contenuTableauCodage);
		
		// TODO ; changer conversion binaire et supprimer joinStrings
		chaineBinaire = GestionFichier.conversionBinaire(tableauCodageValide, joinStrings(contenuFichier));
		// TODO : Erreur de compression
		// Création du fichier binaire
		GestionFichier.enregistrementFichierBinaire(chaineBinaire, cheminFichier);
		
		fichierBinaire = new Fichier(cheminFichier + "_Encode.bin");
		out.println("Taille du fichier compréssé : " + fichierBinaire.tailleFichier() + " octets");
		out.println("Taux de compression : " + fichier.rapportEntreDeuxFichiers(fichierBinaire.tailleFichier()));

	}
	

	/** TODO COMMENTER CETTE METHODE
	 * 
	 * @param liste
	 * @return resultat
	 */
	public static String[][] transformerListe(String[][] liste) { // changer le nom de la méthode pour qu'elle soit plus explicite
        List<String[]> resultat = new ArrayList<>();

        for (String[] entree : liste) {
            if (entree.length < 2) continue;
            String caractere = entree[0];
            StringBuilder code = new StringBuilder();

            for (int i = 1; i < entree.length; i++) {
                code.append(entree[i]);
            }

            resultat.add(new String[]{caractere, code.toString()});
        }

        return resultat.toArray(new String[0][]);
    }
	
	/**
	 * Convertit un tableau de chaînes en une seule chaîne
	 * @param strings
	 */
	public static String joinStrings(String[] strings) {
        StringBuilder resultat = new StringBuilder();
        
        for (String str : strings) {
            resultat.append(str).append("\r\n");
        }
        
        return resultat.toString();
    }
}