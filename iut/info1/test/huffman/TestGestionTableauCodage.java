package iut.info1.test.huffman;

import iut.info1.huffman.GestionTableauCodage;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * Classe de test de gGestionTableauCodage
 */
class TestGestionTableauCodage {

    /**
     * TODO Faire la javadoc de FICHIER_ARBRE_HUFFMAN
     */
    public static final String[][] FICHIER_ARBRE_HUFFMAN_CONTENU_VALIDE = {

            {"codeHuffman = 00 ; encode = 1000001 ; symbole = A",
                    "codeHuffman = 01 ; encode = 1000010 ; symbole = B",
                    "codeHuffman = 10 ; encode = 1000011 ; symbole = C",
                    "codeHuffman = 1100 ; encode = 1000100 ; symbole = D"},

            {"codeHuffman = 00 ; encode = 1001100 ; symbole = L",
                    "codeHuffman = 01 ; encode = 1101111 ; symbole = o",
                    "codeHuffman = 10 ; encode = 1110010 ; symbole = r",
                    "codeHuffman = 110 ; encode = 1100101 ; symbole = e",
                    "codeHuffman = 111 ; encode = 1101101 ; symbole = m",
                    "codeHuffman = 1101 ; encode = 1010 ; symbole = LF",
                    "codeHuffman = 11100 ; encode = 1001001 ; symbole = I",
                    "codeHuffman = 11101 ; encode = 1110000 ; symbole = p",
                    "codeHuffman = 11110 ; encode = 1110011 ; symbole = s",
                    "codeHuffman = 111100 ; encode = 1110101 ; symbole = u"},

            {"codeHuffman = 00 ; encode = 1100001 ; symbole = a",
                    "codeHuffman = 10 ; encode = 1100010 ; symbole = b",
                    "codeHuffman = 01 ; encode = 1010 ; symbole = LF"},

            {"codeHuffman = 0 ; encode = 1010 ; symbole = LF"}
    };

    public static final String[][][] TABLEAU_CODAGE_VALIDE =
            {
                    {{"1000001", "00"}, {"1000010", "01"}, {"1000011", "10"}, {"1000100", "1100"}},
                    {{"1001100", "00"}, {"1101111", "01"}, {"1110010", "10"}, {"1100101", "110"}, {"1101101", "111"}, {"1010", "1101"},
                            {"1001001", "11100"}, {"1110000", "11101"}, {"1110011", "11110"}, {"1110101", "111100"}},
                    {{"1100001", "00"}, {"1100010", "10"}, {"1010", "01"}},
                    {{"1010", "0"}}
            };


    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("------TEST DE LA CLASSE GestionTableauCodage------");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("---FIN DE TEST DE LA CLASSE GestionTableauCodage---");

    }

    @Test
    void testConversionTableauCodageValide() {
        String[][] tableauCodage;
        GestionTableauCodage tabCodage;
        for (int indiceTest = 0; indiceTest < FICHIER_ARBRE_HUFFMAN_CONTENU_VALIDE.length; indiceTest++) {
            tabCodage = new GestionTableauCodage(null);
            try {
                tabCodage.conversionTableauCodage(FICHIER_ARBRE_HUFFMAN_CONTENU_VALIDE[indiceTest]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            tableauCodage = tabCodage.getTabCodages();
            assertArrayEquals(TABLEAU_CODAGE_VALIDE[indiceTest], tableauCodage);
        }
    }

    @Test
    void testFormaterABHuffman() {
        GestionTableauCodage tableauCodage;
        String[] formatTableauCodage;
        for (int indiceTest = 0; indiceTest < TABLEAU_CODAGE_VALIDE.length; indiceTest++) {
            tableauCodage = new GestionTableauCodage(TABLEAU_CODAGE_VALIDE[indiceTest]);
            formatTableauCodage = tableauCodage.formaterABHuffman();

            assertArrayEquals(FICHIER_ARBRE_HUFFMAN_CONTENU_VALIDE[indiceTest], formatTableauCodage);
        }
    }

    @Test
    void testVerifierFormatTableauCodage(){
        for (String[] tabCodages : FICHIER_ARBRE_HUFFMAN_CONTENU_VALIDE) {
            for (String ligne : tabCodages) {
                assertTrue(GestionTableauCodage.verifierFormatTableauCodage(ligne));
            }
        }
    }

    @Test
    void testGetTabCodages(){
        GestionTableauCodage tabCodage;
        for (int indiceTest = 0; indiceTest < TABLEAU_CODAGE_VALIDE.length; indiceTest++) {
            tabCodage = new GestionTableauCodage(TABLEAU_CODAGE_VALIDE[indiceTest]);
            assertArrayEquals(TABLEAU_CODAGE_VALIDE[indiceTest], tabCodage.getTabCodages());
        }
    }
}
