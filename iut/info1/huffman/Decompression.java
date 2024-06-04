/**
 * Decompression.java          30/05/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;
import iut.info1.huffman.fichier.Fichier;
import iut.info1.huffman.fichier.FichierBinaire;

import static java.lang.System.out;

public class Decompression {

    /** chemin du fichier .bin à décompresser */
    private String cheminFichierCompresse;
    
    /** chemin du fichier clé associé au fichier .bin */
    private String cheminFichierCle;
    
    /** chemin du dossier dans lequel le texte décompressé va se situer */
    private String cheminRetour;

    /** Constructeur de la classe Decompression */
    public Decompression(String cheminFichierCompresse,
                         String cheminFichierCle,
                         String cheminRetour) {

        this.cheminFichierCompresse = cheminFichierCompresse;
        this.cheminFichierCle = cheminFichierCle;
        this.cheminRetour = cheminRetour;
    }

    /**
     * Execute l'algorithme de décompression des données en fonction
     * des différents chemins donnés en paramètre dans le constructeur
     */
    public void execute(){
        out.println("------------------------------------------");
        out.println("Décompression de : " + cheminFichierCompresse);

        String contenuFichierCompresse;
        
        String[] contenuFichierCle;
        String[] contenuFichierRetour;
        
        Fichier fichierCle;
        Fichier fichierRetour;
        FichierBinaire fichierCompresse;
        
        GestionTableauCodage tableauCodageValide;
        ArbreBinaireHuffman arbreBinaireHuffman;

        fichierCle = new Fichier(cheminFichierCle);
        fichierCompresse = new FichierBinaire(cheminFichierCompresse);
        
        fichierRetour = new Fichier(cheminRetour 
                                  + fichierCompresse.nomFichier()
                                  + ".txt");

        contenuFichierCle = fichierCle.contenuFichier();
        contenuFichierCompresse = fichierCompresse.contenuFichierBinaire();

        tableauCodageValide = new GestionTableauCodage(null);
        tableauCodageValide.conversionTableauCodage(contenuFichierCle);
        
        arbreBinaireHuffman = 
        	ArbreBinaireHuffman.recuperationArbreHuffman(
        		                tableauCodageValide.getTabCodages()
        		            );

        contenuFichierRetour = 
        	arbreBinaireHuffman.restitutionTexteOriginal(contenuFichierCompresse);

        fichierRetour.ecritureFichier(contenuFichierRetour);
        
        out.println("Fichier décompressé : "
                   + cheminRetour
                   + fichierCompresse.nomFichier() + ".txt");
    }
}