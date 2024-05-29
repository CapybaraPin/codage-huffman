package iut.info1.huffman.fichier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FichierBinaire extends Fichier {
    public FichierBinaire(String cheminFichier) {
        super(cheminFichier);
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
            // TODO Auto-generated catch block
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
}
