package iut.info1.test.huffman;

import iut.info1.huffman.GestionFrequence;
import iut.info1.huffman.fichier.GestionFichier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TestGestionFrequence {

    /** TODO Faire la javadoc de CONTENU_DE_FICHIERS*/
    public static final String[][] CONTENU_DE_FICHIERS = {
        {"ABCD"},
        {"Lorem", "", "Ipsum"},
        {"a", "", "", "",  "b"},
        {"", "", "",  ""},
		{"Lorem", "DELETE FROM mysql.user", "Ipsum",  "Dolor sit amet"}
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
    		{
    			{"L", "2"},  {"o", "3"}, {"r", "3"}, {"e", "3"}, {"m", "4"}, 
    			{"\n", "3"}, {"D", "2"}, {"E", "3"}, {"T", "1"}, {" ", "4"},
    			{"F", "1"},  {"R", "1"},
    			{"O", "1"},  {"M", "1"}, {"y", "1"}, {"s", "4"}, {"q", "1"},
    			{"l", "2"},  {".", "1"}, {"u", "2"}, {"I", "1"}, {"p", "1"},
    			{"i", "1"},  {"t", "2"}, {"a", "1"}
    		}
    };

    /** TODO Faire la javadoc de FREQUENCE_DES_OCCURENCES*/
    public static final double[][] FREQUENCE_DES_OCCURENCES = {
    	    {1.0/4.0, 1.0/4.0, 1.0/4.0, 1.0/4.0},
    	    {1.0/12.0, 1.0/12.0, 1.0/12.0, 1.0/12.0, 2.0/12.0, 2.0/12.0, 1.0/12.0, 1.0/12.0, 1.0/12.0, 1.0/12.0},
    	    {1.0/6.0, 4.0/6.0, 1.0/6.0},
    	    {3.0/3.0},
    	    {2.0/49.0, 3.0/49.0, 3.0/49.0, 3.0/49.0, 4.0/49.0, 3.0/49.0, 2.0/49.0, 3.0/49.0, 1.0/49.0, 4.0/49.0, 1.0/49.0, 1.0/49.0,
    	     1.0/49.0, 1.0/49.0, 1.0/49.0, 4.0/49.0, 1.0/49.0, 2.0/49.0, 1.0/49.0, 2.0/49.0, 1.0/49.0, 1.0/49.0, 1.0/49.0, 2.0/49.0,
    	     1.0/49.0}
    	};

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("------TEST DE LA CLASSE GestionFrequence------");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("---FIN DE TEST DE LA CLASSE GestionFrequence---");

    }

	@Test
	void testCompterOccurrence() {
		String[][] occurrencesAttendu;
		String[][] occurrencesObtenue;

		GestionFrequence occurrences;

		for (int indiceTest = 0; indiceTest < CONTENU_DE_FICHIERS.length; indiceTest++) {

			occurrencesAttendu = OCCURRENCE_DES_CARACTERES[indiceTest];

			occurrences = new GestionFrequence(CONTENU_DE_FICHIERS[indiceTest]);
			occurrences.compterOccurrence();

			occurrencesObtenue = occurrences.getOccurrences();

			for (int indiceAct = 0; indiceAct < OCCURRENCE_DES_CARACTERES[indiceTest].length; indiceAct++) {

				assertArrayEquals(occurrencesAttendu[indiceAct], occurrencesObtenue[indiceAct]);
			}
		}
	}

	@Test
	void testCalculFrequence() {

		GestionFrequence frequences;

		for (int indiceTest = 0; indiceTest < CONTENU_DE_FICHIERS.length; indiceTest++) {
			frequences = new GestionFrequence(CONTENU_DE_FICHIERS[indiceTest]);
			frequences.calculFrequences();

			assertArrayEquals(FREQUENCE_DES_OCCURENCES[indiceTest],
					frequences.getFrequences());
		}
	}

	@Test
	void testGetContenuFichier() {
		GestionFrequence occurrences;
		String[] contenuFichierAttendu;

        for (String[] contenuFichier : CONTENU_DE_FICHIERS) {
            occurrences = new GestionFrequence(contenuFichier);
            contenuFichierAttendu = contenuFichier;

            assertArrayEquals(contenuFichierAttendu, occurrences.getContenuFichier());
        }
	}

	@Test
	void testGetOccurrences() {
		GestionFrequence occurrences;
		String[][] occurrencesAttendu;

		for (int indiceTest = 0; indiceTest < CONTENU_DE_FICHIERS.length; indiceTest++) {
			occurrences = new GestionFrequence(CONTENU_DE_FICHIERS[indiceTest]);
			occurrences.compterOccurrence();

			occurrencesAttendu = OCCURRENCE_DES_CARACTERES[indiceTest];

			assertArrayEquals(occurrencesAttendu, occurrences.getOccurrences());
		}
	}

	@Test
	void testGetFrequences() {
		GestionFrequence occurrences;
		double[] frequencesAttendu;

		for (int indiceTest = 0; indiceTest < CONTENU_DE_FICHIERS.length; indiceTest++) {
			occurrences = new GestionFrequence(CONTENU_DE_FICHIERS[indiceTest]);
			occurrences.calculFrequences();

			frequencesAttendu = FREQUENCE_DES_OCCURENCES[indiceTest];

			assertArrayEquals(frequencesAttendu, occurrences.getFrequences());
		}
	}
}
