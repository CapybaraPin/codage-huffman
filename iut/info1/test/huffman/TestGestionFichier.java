package iut.info1.test.huffman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

    /** TODO Faire la javadoc de ARBRES_BINAIRES_VALIDES*/
    public static final String[][][] ARBRES_BINAIRES_VALIDES = 
    	{
    			{{"e", "101"}, {"b", "010"}, {"d", "110101"}},
    			{{"a", "101"}, {"t", "010"}, {"X", "11011"}},
    			{{"x", "101"}, {" ", "010"}, {"m", "11001"}},
    			{{" ", "11"}, {"W", "01"}, {"8", "011"}},
    			{{"\t", "101"}, {"x", "010"}, {"/", "11101"}},
    			{{"W", "101"}, {"F", "010"}, {"A", "11111"}}
    	};
    
    /** TODO Faire la javadoc de NOMS_DES_FICHIERS_A_COMPRESSE*/
    public static final String[] NOMS_DES_FICHIERS_A_COMPRESSE = {
    		"C:\\huffmantest\\fichier1",
    		"C:\\huffmantest\\fichier2",
    		"C:\\huffmantest\\fichier3",
    		"C:\\huffmantest\\fichier4",
    		"C:\\huffmantest\\fichier5",
    		"C:\\huffmantest\\fichier6",

    };
    
    /** TODO Faire la javadoc de FICHIER_AB_HUFFMAN_ATTENDU*/
    public static final String[] FICHIER_AB_HUFFMAN_ATTENDU = {
    		"codeHuffman =              101 ; encode = 01100101 ; symbole = e\r\n"
    		+ "codeHuffman =              010 ; encode = 01100010 ; symbole = b\r\n"
    		+ "codeHuffman =           110101 ; encode = 01100100 ; symbole = d\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 01100001 ; symbole = a\r\n"
    		+ "codeHuffman =              010 ; encode = 01110100 ; symbole = t\r\n"
    		+ "codeHuffman =            11011 ; encode = 01011000 ; symbole = X\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 01111000 ; symbole = x\r\n"
    		+ "codeHuffman =              010 ; encode = 00100000 ; symbole =  \r\n"
    		+ "codeHuffman =            11001 ; encode = 01101101 ; symbole = m\r\n"
    		+ "",
    		"codeHuffman =               11 ; encode = 00100000 ; symbole =  \r\n"
    		+ "codeHuffman =               01 ; encode = 01010111 ; symbole = W\r\n"
    		+ "codeHuffman =              011 ; encode = 00111000 ; symbole = 8\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 00001001 ; symbole = 	\r\n"
    		+ "codeHuffman =              010 ; encode = 01111000 ; symbole = x\r\n"
    		+ "codeHuffman =            11101 ; encode = 00101111 ; symbole = /\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 01010111 ; symbole = W\r\n"
    		+ "codeHuffman =              010 ; encode = 01000110 ; symbole = F\r\n"
    		+ "codeHuffman =            11111 ; encode = 01000001 ; symbole = A\r\n"
    		+ ""
    };
	
    /** TODO Faire la javadoc de OCCURRENCE_DES_CARACTERES*/
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
    
    /** TODO Faire la javadoc de FREQUENCE_DES_OCCURENCES*/
    public static final float[][] FREQUENCE_DES_OCCURENCES = {
    	    {1.0f/4.0f, 1.0f/4.0f, 1.0f/4.0f, 1.0f/4.0f},
    	    {1.0f/12.0f, 1.0f/12.0f, 1.0f/12.0f, 1.0f/12.0f, 2.0f/12.0f, 2.0f/12.0f, 1.0f/12.0f, 1.0f/12.0f, 1.0f/12.0f, 1.0f/12.0f},
    	    {1.0f/6.0f, 4.0f/6.0f, 1.0f/6.0f},
    	    {3.0f/3.0f},
    	    {2.0f/49.0f, 3.0f/49.0f, 3.0f/49.0f, 3.0f/49.0f, 4.0f/49.0f, 3.0f/49.0f, 2.0f/49.0f, 3.0f/49.0f, 1.0f/49.0f, 4.0f/49.0f, 1.0f/49.0f, 1.0f/49.0f,
    	     1.0f/49.0f, 1.0f/49.0f, 1.0f/49.0f, 4.0f/49.0f, 1.0f/49.0f, 2.0f/49.0f, 1.0f/49.0f, 2.0f/49.0f, 1.0f/49.0f, 1.0f/49.0f, 1.0f/49.0f, 2.0f/49.0f,
    	     1.0f/49.0f},
    	    null
    	};

    /** TODO Faire la javadoc de TAILLE_FICHIERS*/
    public static final int[] TAILLE_FICHIERS = {
            4, 
            14,
            10,
            8,
            52,
            0
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
    void testStockageABHuffman() {
    	
    	FileReader fichierVerifie;
    	BufferedReader liseurDeLignes;
    	String contenuDuFichierVerifie;
    	
    	contenuDuFichierVerifie = "";
    	for (int indexDeTest = 0;
    			indexDeTest < NOMS_DES_FICHIERS_A_COMPRESSE.length;
    			indexDeTest++) {

    		GestionFichier.stockageABHuffman(ARBRES_BINAIRES_VALIDES[indexDeTest]
    				, NOMS_DES_FICHIERS_A_COMPRESSE[indexDeTest]);
    		
    		try {
    			fichierVerifie = new FileReader(
    					NOMS_DES_FICHIERS_A_COMPRESSE[indexDeTest] 
    							+ "_EncodeH.txt");

    			liseurDeLignes = new BufferedReader(fichierVerifie);
    			
    			String ligne = liseurDeLignes.readLine();
    			
    			while (ligne != null) {
    				contenuDuFichierVerifie += ligne +  "\r\n";
    				
    				// lecture de la prochaine ligne
    				ligne = liseurDeLignes.readLine();
    			}
    			fichierVerifie.close();
    		} catch (IOException pbOuverture) {
    			pbOuverture.printStackTrace();
    		}    	
    		
    		assertEquals(FICHIER_AB_HUFFMAN_ATTENDU[indexDeTest]
    					, contenuDuFichierVerifie);
    		
    		contenuDuFichierVerifie = "";
    	
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

    @Test
    void testCalculFrequence() {
    	
        for (int indiceTest = 0; indiceTest < CONTENU_DE_FICHIERS.length; indiceTest++) {
            assertArrayEquals(FREQUENCE_DES_OCCURENCES[indiceTest], 
            		GestionFichier.calculFrequences(GestionFichier.compterOccurrence(CONTENU_DE_FICHIERS[indiceTest])));
        }
    }
    
    @Test
    void testTailleDuFichiers() {

    	for (int indexDeParcours = 0;
    			indexDeParcours < ARBRES_BINAIRES_VALIDES.length;
    			indexDeParcours++) {

    		assertEquals(TAILLE_FICHIERS[indexDeParcours],
    					GestionFichier.tailleDuFichier(LIEN_FICHIERS[indexDeParcours]));
		}

    }
}
