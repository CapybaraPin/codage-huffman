package iut.info1.test.huffman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import iut.info1.huffman.fichier.Fichier;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Classe de test de gestion des fichiers
 */
class TestFichier {
	
	/** sortieStandard du terminal */
	private final PrintStream sortieStandard = System.out;
	private final ByteArrayOutputStream capteurDeSortie = new ByteArrayOutputStream();

	/** Lien vers les fichiers */
	public static final String[] LIEN_FICHIERS = {
			/** VERSION MAC
			"/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier1.txt",
			"/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier2.txt",
			"/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier3.txt",
			"/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier4.txt",
			"/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier5.txt",
			"/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier6.txt"*/
			
			"C:\\fichier1.txt",
    		"C:\\fichier2.txt",
    		"C:\\fichier3.txt",
    		"C:\\fichier4.txt",
    		"C:\\fichier5.txt",
    		"C:\\fichier6.txt"
	};

	/** Objets fichiers */
	public static final Fichier[] OBJET_FICHIER = new Fichier[] { 
			/** VERSION MAC
			new Fichier("/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier1.txt"),
			new Fichier("/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier2.txt"),
			new Fichier("/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier3.txt"),
			new Fichier("/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier4.txt"),
			new Fichier("/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier5.txt"),
			new Fichier("/Users/hugorobles/Desktop/SAEBarrios/codage-huffman/tests/fichier6.txt") */
			
			new Fichier("C:\\fichier1.txt"),
			new Fichier("C:\\fichier1.txt"),
			new Fichier("C:\\fichier1.txt"),
			new Fichier("C:\\fichier1.txt"),
			new Fichier("C:\\fichier1.txt"),
			new Fichier("C:\\fichier1.txt")
	};

	/** Contenu des fichiers */
	public static final String[][] CONTENU_DE_FICHIERS = {
			{"ABCD"},
			{"Lorem", "", "Ipsum"},
			{"a", "", "", "",  "b"},
			{"", "", "",  ""},
			{"Lorem", "DELETE FROM mysql.user", "Ipsum",  "Dolor sit amet"},
			{}
	};

	/** Résultats des contenus des fichiers */
	public static final String[] RESULTAT_POUR_CONTENU = {
			/** VERSION MAC
			
			"ABCD\n",
			"Lorem\n\nIpsum\n",
			"a\n\n\n\nb\n",
			"\n\n\n\n",
			"Lorem\nDELETE FROM mysql.user\nIpsum\nDolor sit amet\n",
			""*/
			
			"ABCD\r\n",
            "Lorem\r\n\r\nIpsum\r\n",
            "a\r\n\r\n\r\n\r\nb\r\n",
            "\r\n\r\n\r\n\r\n",
            "Lorem\r\nDELETE FROM mysql.user\r\nIpsum\r\nDolor sit amet\r\n",
            ""
	};

	/** Taille des fichiers */
	public static final int[] TAILLE_FICHIERS = {
			4, 
			14,
			10,
			8,
			52,
			-1
	}; 

	/** Taille factisse d'un fichier comparé et le rapport entre les deux fichiers */
	public static final double[][] TAILLE_FICHIER_RAPPORT = {
			{4, 4.0/4.0}, 
			{10, 14.0/10.0},
			{5, 10.0/5.0},
			{21, 8.0/21.0},
			{25, 52.0/25.0},
			{44, -1.0/44.0}
	}; 
    

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("------TEST DE LA CLASSE Fichier------");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("---FIN DE TEST DE LA CLASSE Fichier---");

    }

	@Test
	void testContenuFichier() {
		Fichier fichier;
		
		for (int indiceTest = 0; 
    			indiceTest < LIEN_FICHIERS.length; indiceTest++) {
			
			fichier = new Fichier(LIEN_FICHIERS[indiceTest]);
    		
    		assertArrayEquals(CONTENU_DE_FICHIERS[indiceTest], fichier.contenuFichier());
    		
    	}
	}

	@Test
	void testTailleFichier() {
		Fichier fichier;
		
		for (int indiceTest = 0; 
    			indiceTest < LIEN_FICHIERS.length; indiceTest++) {

			fichier = new Fichier(LIEN_FICHIERS[indiceTest]);
    		
    		assertEquals(TAILLE_FICHIERS[indiceTest], fichier.tailleFichier());
    		
    	}
		
	}

	@Test
	void testAffichageFichier() {
		Fichier fichier;
		
		 for (int indiceTest = 0;
	                indiceTest < LIEN_FICHIERS.length;
	                indiceTest++) {
			 
			 	fichier = new Fichier(LIEN_FICHIERS[indiceTest]);

	            System.setOut(new PrintStream(capteurDeSortie));
	            
	            fichier.affichageFichier();    

	            assertEquals(RESULTAT_POUR_CONTENU[indiceTest],
	                        capteurDeSortie.toString());

	            System.setOut(sortieStandard);
	            
	            capteurDeSortie.reset();
		 }
	}

	@Test
	void testRapportEntreDeuxFichiers() {
		Fichier fichier;
		
		for (int indiceTest = 0; 
    			indiceTest < LIEN_FICHIERS.length; indiceTest++) {

			fichier = new Fichier(LIEN_FICHIERS[indiceTest]);
			
    		assertEquals(TAILLE_FICHIER_RAPPORT[indiceTest][1], 
    					fichier.rapportEntreDeuxFichiers(TAILLE_FICHIER_RAPPORT[indiceTest][0]));
    		
    	}
	}

	@Test
	void testGetFichierExploite() {
		Fichier fichier;
		
		for (int indiceTest = 0; 
    			indiceTest < LIEN_FICHIERS.length; indiceTest++) {

			fichier = new Fichier(LIEN_FICHIERS[indiceTest]);
    		
    		assertEquals(fichier.getFichierExploite(), OBJET_FICHIER[indiceTest].getFichierExploite());
    		
    	}
	}

}
