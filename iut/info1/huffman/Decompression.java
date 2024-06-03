package iut.info1.huffman;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;
import iut.info1.huffman.fichier.Fichier;
import iut.info1.huffman.fichier.FichierBinaire;

import static java.lang.System.out;

public class Decompression {

    private String cheminFichierCompresse;
    private String cheminFichierCle;
    private String cheminRetour;

    public Decompression(String cheminFichierCompresse,
                         String cheminFichierCle,
                         String cheminRetour) {

        this.cheminFichierCompresse = cheminFichierCompresse;
        this.cheminFichierCle = cheminFichierCle;
        this.cheminRetour = cheminRetour;
    }

    public void execute(){
        out.println("------------------------------------------");
        out.println("Décompression de : " + cheminFichierCompresse);

        Fichier fichierCle;
        Fichier fichierRetour;
        FichierBinaire fichierCompresse;
        GestionTableauCodage tableauCodageValide;
        ArbreBinaireHuffman arbreBinaireHuffman;

        String[] contenuFichierCle;
        String contenuFichierCompresse;
        String[] contenuFichierRetour;

        fichierCle = new Fichier(cheminFichierCle);
        fichierCompresse = new FichierBinaire(cheminFichierCompresse);
        fichierRetour = new Fichier(cheminRetour + fichierCompresse.nomFichier() + ".txt");

        contenuFichierCle = fichierCle.contenuFichier();
        contenuFichierCompresse = fichierCompresse.contenuFichierBinaire();

        tableauCodageValide = new GestionTableauCodage(null);
        tableauCodageValide.conversionTableauCodage(contenuFichierCle);
        arbreBinaireHuffman = ArbreBinaireHuffman.recuperationArbreHuffman(tableauCodageValide.getTabCodages());

        contenuFichierRetour = arbreBinaireHuffman.restitutionTexteOriginal(contenuFichierCompresse);

        fichierRetour.ecritureFichier(contenuFichierRetour);
        
        out.println("Fichier décompressé : " + cheminRetour + fichierCompresse.nomFichier() + ".txt");

    }
}
