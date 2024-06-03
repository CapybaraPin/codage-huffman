package iut.info1.huffman.fichier;

import java.io.*;

import static java.lang.System.err;
import static java.lang.System.out;

public class FichierBinaire extends Fichier {

    /** suffixe des fichiers compréssés pris en charge **/
    private static final String SUFFIXE_FICHIER_DECOMPRESSE = ".bin";

    /** le format du paramètre est invalide **/
    private static final String ERREUR_FORMAT_PARAMETRE =
            "erreur : Le format du paramètre renseigné invalide.";

    /** impossible d'acceder au contenu du fichier **/
    private static final String ERREUR_CONTENU_FICHIER =
            "erreur : Impossible d'accéder au contenu du fichier.";

    private static String cheminFichier;

    public FichierBinaire(String cheminFichier) {
        super(cheminFichier);

        FichierBinaire.cheminFichier = cheminFichier;
    }

    /**
     * Permet la récupération des données dans un fichier binaire
     * @return le contenu du fichier
     *
     */
    public String contenuFichierBinaire() {
        String contenuBinaireFichier;

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(getFichierExploite().getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int data;
        String donneesData;

        contenuBinaireFichier = "";
        try {
            while ((data = inputStream.read()) != -1) {

                donneesData = String.valueOf(Integer.toBinaryString(data));

                if (donneesData.length() != 8) { // TODO Faire une méthode pour ce 'if'
                    donneesData = "0".repeat(8 - donneesData.length())
                            + donneesData;
                }
                contenuBinaireFichier += donneesData;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return contenuBinaireFichier; //STUB
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
     * @param chaineBinaire
     */
    public void enregistrementFichierBinaire(String chaineBinaire) {
        int longueur = chaineBinaire.length();
        int longueurDonnees = (int) Math.ceil((double) longueur / 8);
        byte[] donnees = new byte[longueurDonnees+1];
        int indexDonnees;
        int nbZeroComplementaire;

        if (chaineBinaire.isEmpty() || cheminFichier.isEmpty()) {
            err.println(ERREUR_FORMAT_PARAMETRE);
            return;
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
            FileOutputStream fluxEcriture = new FileOutputStream(cheminFichier);
            fluxEcriture.write(donnees);
            fluxEcriture.close(); // TODO ERREUR FERMETURE

        } catch (IOException pbEcriture) {
            // TODO ERREUR ECRITURE
        }
    }
}
