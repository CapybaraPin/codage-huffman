package iut.info1.test.huffman;

import iut.info1.huffman.GestionTableauCodage;
import static org.junit.jupiter.api.Assertions.*;

import iut.info1.huffman.fichier.Fichier;
import iut.info1.huffman.fichier.FichierBinaire;
import iut.info1.huffman.fichier.GestionFichier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * Classe de test de gGestionTableauCodage
 */
class TestFichierBinaire {

    public static final String[] CHEMIN_FICHIR_BINAIRE = {
            "C:\\Users\\Travail\\Documents\\huffman\\result\\fichier1.bin",
            "C:\\Users\\Travail\\Documents\\huffman\\result\\fichier2.bin",
            "C:\\Users\\Travail\\Documents\\huffman\\result\\fichier3.bin",
            "C:\\Users\\Travail\\Documents\\huffman\\result\\fichier4.bin",
            "C:\\Users\\Travail\\Documents\\huffman\\result\\fichier5.bin"
    };

    public static final String[] CONTENU_FICHIER_BIN = {
            "1101001000000000",
            "01110011111000101010100000110001001100100000101000000100",
            "110000100000000000000111",
            "0000000000000100",
            "0011101111001100011101100001110100011110100101111010010000"
           +"0011110111011111001010011100000100100000000101011000000100"
           +"0101000100101101011011010001000010111011000011011101010011"
           +"1100101000010000010001100100010110111100000110011000000000"
    };

    public static final String[] CONTENU_BIN_A_ENREGISTRER = {
            "11010010",
            "01110011111000101010100000110001001100101010",
            "110000100",
            "0000",
             "0011101111001100011101100001110100011110100101111010010000"
            +"0011110111011111001010011100000100100000000101011000000100"
            +"0101000100101101011011010001000010111011000011011101010011"
            +"11001010000100000100011001000101101111000001100110"
    };

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("------TEST DE LA CLASSE FichierBinaire------");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("---FIN DE TEST DE LA CLASSE FichierBinaire---");
    }
    @Test
    void testEnregistrementFichierBinaire() {

        for (int i = 0; i < CHEMIN_FICHIR_BINAIRE.length; i++) {
            FichierBinaire fichierBinaire = new FichierBinaire(CHEMIN_FICHIR_BINAIRE[i]);
            fichierBinaire.enregistrementFichierBinaire(CONTENU_BIN_A_ENREGISTRER[i]);
            assertEquals(CONTENU_FICHIER_BIN[i], fichierBinaire.contenuFichierBinaire());
        }
    }

    @Test
    void testContenuFichierBinaire() {
        for (int i = 0; i < CHEMIN_FICHIR_BINAIRE.length; i++) {
            FichierBinaire fichierBinaire = new FichierBinaire(CHEMIN_FICHIR_BINAIRE[i]);
            assertEquals(CONTENU_FICHIER_BIN[i], fichierBinaire.contenuFichierBinaire());
        }
    }



}
