package iut.info1.huffman;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;
import iut.info1.huffman.fichier.Fichier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.err;
import static java.lang.System.out;
import static iut.info1.huffman.ApplicationHuffman.*;

public class Compression {

    /** suffixe des fichiers compréssés pris en charge **/
    private static final String SUFFIXE_FICHIER_DECOMPRESSE = ".bin";

    /** code d'erreur lors d'un soucis de lecture fichier */
    public static final int CODE_ERREUR_FICHIER_LECTURE = 10;

    /** problème de lecture du fichier **/
    private static final String ERREUR_FICHIER_LECTURE
            = "Il y a eu un problème lors de l'ovuerture du fichier : ";

    public static void main(String cheminFichier, String cheminRetour) {
        cheminFichier = FOLDER_URL + cheminFichier;
        out.println("------------------------------------------");
        out.println("Compression de : " + cheminFichier);

        Fichier fichier;
        Fichier fichierTableauCodage;
        Fichier fichierBinaire;
        GestionFrequence frequences;
        ArbreBinaireHuffman arbre;
        String chaineHuffmanValide;
        String nomFichier;
        String[] contenuFichier;
        String[] contenuTableauCodage;
        String[][] tableauCodageValide;
        StringBuilder chaineHuffman;


        // Appel d'intéraction avec le fichier
        fichier = new Fichier(cheminFichier);
        contenuFichier = fichier.contenuFichier();
        nomFichier = fichier.nomFichier();

        out.println("Taille d'origine : " + fichier.tailleFichier() + " octets");
        out.println("Nom du fichier : " + nomFichier);

        // Appel du calcul de fréquence
        frequences = new GestionFrequence(contenuFichier);
        frequences.compterOccurrence();
        frequences.calculFrequences(); // TODO : Ne pas re-calculer les occurrences

        // Appel de l'arbre huffman

        Object[] tblCaracteres = new Object[frequences.getFrequences().length];
        double[] tblFrequences = new double[frequences.getOccurrences().length];

        // Insertion des caractères et fréquences dans les tableaux
        for (int i = 0; i < frequences.getOccurrences().length; i++) {
            tblCaracteres[i] = frequences.getOccurrences()[i][0];
            tblFrequences[i] = frequences.getFrequences()[i];
        }

        arbre = ArbreBinaireHuffman.insertionHuffman(tblCaracteres, tblFrequences);

        out.println("Arbre Huffman créé avec succès.");

        // Affichage de l'arbre en console
        arbre.afficherArbre();

        // Affichage des référenres huffman a partie de l'arbre
        out.println(Arrays.deepToString(arbre.parcoursProfondeur()));

        tableauCodageValide = arbre.parcoursProfondeur();

        GestionTableauCodage tabCodage = new GestionTableauCodage(tableauCodageValide);

        fichierTableauCodage = new Fichier(FOLDER_URL + cheminRetour + nomFichier + "_EncodeH.txt");
        contenuTableauCodage = tabCodage.formaterABHuffman();
        fichierTableauCodage.ecritureFichier(contenuTableauCodage);

        // Chaine à envoyer dans le fichier .bin
        chaineHuffman = new StringBuilder();

        for (String str : contenuFichier) {
            chaineHuffman.append(str).append("\r\n");
        }

        chaineHuffmanValide = conversionBinaire(tableauCodageValide, chaineHuffman.toString());

        enregistrementFichierBinaire(chaineHuffmanValide, FOLDER_URL + cheminRetour + nomFichier);

        fichierBinaire = new Fichier(FOLDER_URL + cheminRetour + nomFichier + ".bin");
        out.println("Taille du fichier compréssé : " + fichierBinaire.tailleFichier() + " octets");
        out.println("Taux de compression : " + fichier.rapportEntreDeuxFichiers(fichierBinaire.tailleFichier()));
    }

    /**
     * Convertit une chaîne de caractères en binaire à
     * l'aide d'un tableau de codage.
     * Ne vérifie pas le tableau de codages.
     * @param tabCodages
     * @param chaine
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
                int hashCodeCaractere = Character.hashCode(caractereChaine); // <-- Ce qui prend du temps...
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
     * @param contenuFichier
     */
    public static void enregistrementFichierBinaire(String chaineBinaire, String nomFichier) {
        int longueur = chaineBinaire.length();
        int longueurDonnees = (int) Math.ceil((double) longueur / 8);
        byte[] donnees = new byte[longueurDonnees+1];
        int indexDonnees;
        int nbZeroComplementaire;

        if (chaineBinaire.isEmpty() || nomFichier.isEmpty()) {
            err.println(ERREUR_FORMAT_PARAMETRE);
            return;
        }

        try {
            File fichierEnregistrement = new File(
                    nomFichier
                            +SUFFIXE_FICHIER_DECOMPRESSE);

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
}
