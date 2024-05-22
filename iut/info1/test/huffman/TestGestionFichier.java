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
    		// "C:\\fichier5.txt",
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
        // {"Lorem", "DELETE FROM mysql.user", "Ipsum",  "Dolor sit amet"},
        {}
    };
    
    /** TODO Faire la javadoc de TABLEAU_CODAGE*/
    public static final String[][][] TABLEAU_CODAGE = 
        {
            {{"0000000001000001", "00"}, {"0000000001000010", "01"}, {"0000000001000011", "10"}, {"0000000001000100", "1100"}},
            {{"0000000001001100", "00"}, {"0000000001101111", "01"}, {"0000000001110010", "10"}, {"0000000001100101", "110"}, {"0000000001101101", "111"}, {"0000000000001010", "1101"},
             {"0000000001001001", "11100"}, {"0000000001110000", "11101"}, {"0000000001110011", "11110"}, {"0000000001110101", "111100"}},
            {{"0000000001100001", "00"}, {"0000000001100010", "10"}, {"0000000000001010", "01"}},
            {{"0000000000001010", "0"}},
            //{{"L", "00"}, {"o", "01"}, {"r", "10"}, {"e", "110"}, {"m", "111"}, {"\r", "1100"}, {"\n", "1101"}, {"D", "11100"}, {"E", "11101"}, {"T", "11110"}, {" ", "11111"}, {"F", "110000"}, {"R", "110001"}, {"m", "110010"}, {"s", "110011"}, {"i", "110100"}, {"t", "110101"}, {"a", "110110"}, {"e", "110111"}, {"\r", "111000"}, {"\n", "111001"}},
            {{}}
        }; 

	/** TODO Faire la javadoc de FICHIER_ARBRE_HUFFMAN*/
	public static final String[][] FICHIER_ARBRE_HUFFMAN_CONTENU = {

			{"codeHuffman =                         00 ; encode = 0000000001000001 ; symbole = A",
			 "codeHuffman =                         01 ; encode = 0000000001000010 ; symbole = B",
			 "codeHuffman =                         10 ; encode = 0000000001000011 ; symbole = C",
			 "codeHuffman =                       1100 ; encode = 0000000001000100 ; symbole = D"},

		    {"codeHuffman =                         00 ; encode = 0000000001001100 ; symbole = L",
			 "codeHuffman =                         01 ; encode = 0000000001101111 ; symbole = o",
			 "codeHuffman =                         10 ; encode = 0000000001110010 ; symbole = r",
			 "codeHuffman =                        110 ; encode = 0000000001100101 ; symbole = e",
			 "codeHuffman =                        111 ; encode = 0000000001101101 ; symbole = m",
			 "codeHuffman =                       1101 ; encode = 0000000000001010 ; symbole = LF",
			 "codeHuffman =                      11100 ; encode = 0000000001001001 ; symbole = I",
			 "codeHuffman =                      11101 ; encode = 0000000001110000 ; symbole = p",
			 "codeHuffman =                      11110 ; encode = 0000000001110011 ; symbole = s",
			 "codeHuffman =                     111100 ; encode = 0000000001110101 ; symbole = u"},

			{"codeHuffman =                         00 ; encode = 0000000001100001 ; symbole = a",
			 "codeHuffman =                         10 ; encode = 0000000001100010 ; symbole = b",
			 "codeHuffman =                         01 ; encode = 0000000000001010 ; symbole = LF"},

			{"codeHuffman =                          0 ; encode = 0000000000001010 ; symbole = LF"},
			//{}, TODO
			{}
	};

    /** TODO Faire la javadoc de RESULTAT_POUR_CONTENU*/
    public static final String[] RESULTAT_POUR_CONTENU = {
            
            "ABCD\r\n",
            "Lorem\r\n\r\nIpsum\r\n",
            "a\r\n\r\n\r\n\r\nb\r\n",
            "\r\n\r\n\r\n\r\n",
            //"Lorem\r\nDELETE FROM mysql.user\r\nIpsum\r\nDolor sit amet\r\n",
            ""
    };
    
    /** TODO Faire la javadoc de CHAINES_BINAIRES_VALIDES*/
    public static final String[] CHAINES_BINAIRES_VALIDES = {
    		 "0001101100",
    		 "000110110111110111011110011101111101111001111101",
    		 "00010101011001",
    		 "0000",
    		// TODO : Lorem\r\nDELETE FROM mysql.user\r\nIpsum\r\nDolor sit amet\r\n
    		 ""
    };

	public static final String[] FICHIER_COMPRESSE = {
		"00011011001101111000000000000110",
		"000110110111110011011100110111100111011111011110011111001101000000000100",
		"00011101110111011110011100000000",
		"0101010100000000",
		// TODO : Lorem\r\nDELETE FROM mysql.user\r\nIpsum\r\nDolor sit amet\r\n,
		""
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
    		// "C:\\huffmantest\\fichier5",
    		"C:\\huffmantest\\fichier6",

    };
    
    /** TODO Faire la javadoc de FICHIER_AB_HUFFMAN_ATTENDU*/
    public static final String[] FICHIER_AB_HUFFMAN_ATTENDU = {
    		  "codeHuffman =                        101 ; encode = 0000000001100101 ; symbole = e\r\n"
    		+ "codeHuffman =                        010 ; encode = 0000000001100010 ; symbole = b\r\n"
    		+ "codeHuffman =                     110101 ; encode = 0000000001100100 ; symbole = d\r\n"
    		+ "",
    		  "codeHuffman =                        101 ; encode = 0000000001100001 ; symbole = a\r\n"
    		+ "codeHuffman =                        010 ; encode = 0000000001110100 ; symbole = t\r\n"
    		+ "codeHuffman =                      11011 ; encode = 0000000001011000 ; symbole = X\r\n"
    		+ "",
    		  "codeHuffman =                        101 ; encode = 0000000001111000 ; symbole = x\r\n"
    		+ "codeHuffman =                        010 ; encode = 0000000000100000 ; symbole =  \r\n"
    		+ "codeHuffman =                      11001 ; encode = 0000000001101101 ; symbole = m\r\n"
    		+ "",
    		  "codeHuffman =                         11 ; encode = 0000000000100000 ; symbole =  \r\n"
    		+ "codeHuffman =                         01 ; encode = 0000000001010111 ; symbole = W\r\n"
    		+ "codeHuffman =                        011 ; encode = 0000000000111000 ; symbole = 8\r\n"
    		+ "",
    		  "codeHuffman =                        101 ; encode = 0000000000001001 ; symbole = 	\r\n"
    		+ "codeHuffman =                        010 ; encode = 0000000001111000 ; symbole = x\r\n"
    		+ "codeHuffman =                      11101 ; encode = 0000000000101111 ; symbole = /\r\n"
    		+ "",
    		  "codeHuffman =                        101 ; encode = 0000000001010111 ; symbole = W\r\n"
    		+ "codeHuffman =                        010 ; encode = 0000000001000110 ; symbole = F\r\n"
    		+ "codeHuffman =                      11111 ; encode = 0000000001000001 ; symbole = A\r\n"
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
    		},    		
    		/*{
    			{"L", "2"},  {"o", "3"}, {"r", "3"}, {"e", "3"}, {"m", "4"}, 
    			{"\n", "3"}, {"D", "2"}, {"E", "3"}, {"T", "1"}, {" ", "4"},
    			{"F", "1"},  {"R", "1"},
    			{"O", "1"},  {"M", "1"}, {"y", "1"}, {"s", "4"}, {"q", "1"},
    			{"l", "2"},  {".", "1"}, {"u", "2"}, {"I", "1"}, {"p", "1"},
    			{"i", "1"},  {"t", "2"}, {"a", "1"}
    		},*/
    		{
    			{"", "0"}
    		},
    };
    

    
    /** TODO Faire la javadoc de FREQUENCE_DES_OCCURENCES*/
    public static final double[][] FREQUENCE_DES_OCCURENCES = {
    	    {1.0/4.0, 1.0/4.0, 1.0/4.0, 1.0/4.0},
    	    {1.0/12.0, 1.0/12.0, 1.0/12.0, 1.0/12.0, 2.0/12.0, 2.0/12.0, 1.0/12.0, 1.0/12.0, 1.0/12.0, 1.0/12.0},
    	    {1.0/6.0, 4.0/6.0, 1.0/6.0},
    	    {3.0/3.0},
    	    /*{2.0/49.0, 3.0/49.0, 3.0/49.0, 3.0/49.0, 4.0/49.0, 3.0/49.0, 2.0/49.0, 3.0/49.0, 1.0/49.0, 4.0/49.0, 1.0/49.0, 1.0/49.0,
    	     1.0/49.0, 1.0/49.0, 1.0/49.0, 4.0/49.0, 1.0/49.0, 2.0/49.0, 1.0/49.0, 2.0/49.0, 1.0/49.0, 1.0/49.0, 1.0/49.0, 2.0/49.0,
    	     1.0/49.0},*/
    	    null
    	};

    /** TODO Faire la javadoc de TAILLE_FICHIERS*/
    public static final int[] TAILLE_FICHIERS = {
            4, 
            14,
            10,
            8,
            // 52,
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
    	for (int indiceTest = 0;
    			indiceTest < NOMS_DES_FICHIERS_A_COMPRESSE.length;
    			indiceTest++) {

    		GestionFichier.stockageABHuffman(ARBRES_BINAIRES_VALIDES[indiceTest]
    				, NOMS_DES_FICHIERS_A_COMPRESSE[indiceTest]);
    		
    		try {
    			fichierVerifie = new FileReader(
    					NOMS_DES_FICHIERS_A_COMPRESSE[indiceTest] 
    							+ "_EncodeH.txt");

    			liseurDeLignes = new BufferedReader(fichierVerifie);
    			
    			String ligne = liseurDeLignes.readLine();
    			
    			while (ligne != null) {
    				contenuDuFichierVerifie += ligne +  "\r\n";
    				
    				ligne = liseurDeLignes.readLine();
    			}
    			fichierVerifie.close();
    		} catch (IOException pbOuverture) {
    			pbOuverture.printStackTrace();
    		}    	
    		
    		assertEquals(FICHIER_AB_HUFFMAN_ATTENDU[indiceTest]
    					, contenuDuFichierVerifie);
    		
    		contenuDuFichierVerifie = "";
    	
    	}
    }
   
    @Test
    void testCompterOccurrence() {

    	String[][] occurrenceActuelleAttendu;
    	String[][] occurrenceActuelleObtenue;
    	
       	occurrenceActuelleAttendu = occurrenceActuelleObtenue = null;
    	
    	for (int indiceTest = 0; 
    			indiceTest < CONTENU_DE_FICHIERS.length; 
    			indiceTest++) {
    		
    		occurrenceActuelleAttendu = OCCURRENCE_DES_CARACTERES[indiceTest];
    		occurrenceActuelleObtenue = GestionFichier
    								   .compterOccurrence(CONTENU_DE_FICHIERS
    										   			 [indiceTest]);	
		
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
    			indexDeParcours < TAILLE_FICHIERS.length;
    			indexDeParcours++) {
    		
    		assertEquals(TAILLE_FICHIERS[indexDeParcours],
    					GestionFichier.tailleDuFichier(LIEN_FICHIERS[indexDeParcours]));
		}

    }
    
    @Test
    void testConversionBinaire() {
    	for (int indexParcours = 0;
    			indexParcours < TABLEAU_CODAGE.length;
    			indexParcours++) {
    		assertEquals(CHAINES_BINAIRES_VALIDES[indexParcours],
    				GestionFichier.conversionBinaire(TABLEAU_CODAGE[indexParcours], RESULTAT_POUR_CONTENU[indexParcours]));
    		
    	}
    }
    
	@Test
	void testConversionTableauCodage(){
		for (int indexParcours = 0;
				 indexParcours < TABLEAU_CODAGE.length;
				 indexParcours++) {
			System.out.println(indexParcours);
			assertArrayEquals(TABLEAU_CODAGE[indexParcours],
					
					GestionFichier.conversionTableauCodage(FICHIER_ARBRE_HUFFMAN_CONTENU[indexParcours]));
		}
	}

	@Test
	void testEcritureFichierBinaire() {
		for (int indexParcours = 0;
				indexParcours < NOMS_DES_FICHIERS_A_COMPRESSE.length;
				indexParcours++) {
			GestionFichier.enregistrementFichierBinaire(CHAINES_BINAIRES_VALIDES[indexParcours], NOMS_DES_FICHIERS_A_COMPRESSE[indexParcours]);
			//TODO lorsque lectureFichierBinaire sera fonctionnel assertArrayEquals
		}
	}

	@Test
	void testVerifierFormatTableauCodage(){
		for (String[] tabCodages : FICHIER_ARBRE_HUFFMAN_CONTENU) {
			for (String ligne : tabCodages) {
				assertTrue(GestionFichier.verifierFormatTableauCodage(ligne));
			}
		}
	}
}
