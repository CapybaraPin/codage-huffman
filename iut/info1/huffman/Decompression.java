package iut.info1.huffman;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;
import iut.info1.huffman.fichier.Fichier;
import iut.info1.huffman.fichier.FichierBinaire;

import static iut.info1.huffman.ApplicationHuffman.FOLDER_URL;
import static java.lang.System.out;

public class Decompression {

    public static void main(String cheminFichierCompresse, String cheminFichierCle, String cheminRetour) {
        Fichier fichierCle;
        Fichier fichierRetour;
        FichierBinaire fichierCompresse;
        GestionTableauCodage tableauCodageValide;
        ArbreBinaireHuffman arbreBinaireHuffman;

        String[] contenuFichierCle;
        String contenuFichierCompresse;
        String[] contenuFichierRetour;

        fichierCle = new Fichier(FOLDER_URL + cheminFichierCle); // TODO TRY
        fichierCompresse = new FichierBinaire(FOLDER_URL + cheminFichierCompresse);
        fichierRetour = new Fichier(FOLDER_URL + cheminRetour + fichierCompresse.nomFichier() + ".txt");
        out.println(FOLDER_URL + cheminRetour + fichierCompresse.nomFichier() + ".txt");


        contenuFichierCle = fichierCle.contenuFichier();
        contenuFichierCompresse = fichierCompresse.contenuFichierBinaire();

        tableauCodageValide = new GestionTableauCodage(null);
        tableauCodageValide.conversionTableauCodage(contenuFichierCle);
        arbreBinaireHuffman = ArbreBinaireHuffman.recuperationArbreHuffman(tableauCodageValide.getTabCodages());

        contenuFichierRetour = arbreBinaireHuffman.restitutionTexteOriginal(contenuFichierCompresse);

        fichierRetour.ecritureFichier(contenuFichierRetour);
    }
}
