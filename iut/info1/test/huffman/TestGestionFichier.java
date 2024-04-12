package iut.info1.test.huffman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import iut.info1.huffman.fichier.GestionFichier;

class TestGestionFichier {
    
    private final PrintStream sortieStandard = System.out;
    private final ByteArrayOutputStream capteurDeSortie = new ByteArrayOutputStream();
    
    /** TODO : faire la javadoc de LIEN_FICHIER **/
    public static final String[] LIEN_FICHIERS = {
    		"C:\\fichier1.txt",
    		"C:\\fichier2.txt",
    		"C:\\fichier3.txt",
    		"C:\\fichier4.txt",
    		"C:\\fichier5.txt",
    		"C:\\fichier6.txt"
    		// "C:\\fichier6.TXT"
    		// "C:\\fichier6.java"
    };
    
    /** TODO Faire la javadoc de CONTENU_DE_FICHIERS*/
    public static final String[][] CONTENU_DE_FICHIERS = {
        {"ABCD"},
        {"Lorem", "", "Ipsum"},
        {"a", "", "", "",  "b"},
        {"", "", "",  ""},
        {"Lorem", "DELETE FROM mysql.user", "Ipsum",  "Dolor sit amet"},
        {}
    };

    /** TODO Faire la javadoc de CONTENU_DE_FICHIERS*/
    public static final String[][][] OCCURRENCE_DES_CARACTERES = {
    		{
    			{"A", "1"}, {"B", "1"}, {"C", "1"}, {"D", "1"}
    		},    		{
    			{"L", "1"}, {"o", "1"}, {"r", "1"}, {"e", "1"}, {"m", "2"},
    			{"\n", "2"}, {"I", "1"}, {"p", "1"}, {"s", "1"}, {"u", "1"}
    		},    		{
    			{"a", "1"}, {"\n", "4"}, {"b", "1"}
    		},    		{
    			{"\n", "3"}
    		},    		{
    			{"L", "2"},  {"o", "3"}, {"r", "3"}, {"e", "3"}, {"m", "4"}, 
    			{"\n", "3"}, {"D", "2"}, {"E", "3"}, {"T", "1"}, {" ", "4"},
    			{"F", "1"},  {"R", "1"},
    			{"O", "1"},  {"M", "1"}, {"y", "1"}, {"s", "4"}, {"q", "1"},
    			{"l", "2"},  {".", "1"}, {"u", "2"}, {"I", "1"}, {"p", "1"},
    			{"i", "1"},  {"t", "2"}, {"a", "1"}
    		},    		{
    			{"", "0"}
    		},
    };
    
    /** TODO Faire la javadoc de RESULTAT_POUR_CONTENU*/
    public static final String[] RESULTAT_POUR_CONTENU = {
            
            "ABCD\r\n",
            "Lorem\r\n\r\nIpsum\r\n",
            "a\r\n\r\n\r\n\r\nb\r\n",
            "\r\n\r\n\r\n\r\n",
            "Lorem\r\nDELETE FROM mysql.user\r\nIpsum\r\nDolor sit amet\r\n",
            ""
        };
    
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("------TEST DE LA CLASSE GestionFichier------");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("---FIN DE TEST DE LA CLASSE GestionFichier---");

    }
    
    @Test
    void testLectureFichier() {
    	for (int indiceTest = 0; 
    			indiceTest < LIEN_FICHIERS.length; indiceTest++) {
    		
    		assertArrayEquals(CONTENU_DE_FICHIERS[indiceTest], GestionFichier.lectureFichier(LIEN_FICHIERS[indiceTest]));
    	}
    }

   
    @Test
    void testCompterOccurrence() {

    	String[][] occurrenceActuelleAttendu;
    	String[][] occurrenceActuelleObtenue;
    	
    	
    	// Initialise avec un mauvais élément
    	occurrenceActuelleAttendu = occurrenceActuelleObtenue = null;
    	
    	for (int indexDeTest = 0; 
    			indexDeTest < CONTENU_DE_FICHIERS.length; 
    			indexDeTest++) {
    		
    		occurrenceActuelleAttendu = OCCURRENCE_DES_CARACTERES[indexDeTest];
    		occurrenceActuelleObtenue = GestionFichier
    								   .compterOccurrence(CONTENU_DE_FICHIERS
    										   			 [indexDeTest]);	
		
    		for (int indexActuel = 0;
    				indexActuel < occurrenceActuelleObtenue.length;
    				indexActuel++) {

        		assertArrayEquals(occurrenceActuelleAttendu[indexActuel],
        				occurrenceActuelleObtenue[indexActuel]);

			}
		}
    	
    }
    
    @Test
    void testAffichageFichier() {

        for (int indiceTest = 0;
                indiceTest < CONTENU_DE_FICHIERS.length;
                indiceTest++) {

            System.setOut(new PrintStream(capteurDeSortie));
            
            GestionFichier.affichageFichier(CONTENU_DE_FICHIERS[indiceTest]);                

            assertEquals(RESULTAT_POUR_CONTENU[indiceTest],
                        capteurDeSortie.toString());

            System.setOut(sortieStandard);
            
            capteurDeSortie.reset();
        }
        
    }

}
