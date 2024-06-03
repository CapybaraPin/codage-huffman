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
			"C:\\fichier1.txt",
    		"C:\\fichier2.txt",
    		"C:\\fichier3.txt",
    		"C:\\fichier4.txt",
    		"C:\\fichier5.txt",
    		"C:\\fichier6.txt"
	};

	/** Nom des fichiers sans leurs extentions */
	public static final String[] NOM_FICHIER = {
			"fichier1",
			"fichier2",
			"fichier3",
			"fichier4",
			"fichier5",
			"fichier6"
	};

	/** Objets fichiers */
	public static final Fichier[] OBJET_FICHIER = new Fichier[] {
			new Fichier("C:\\fichier1.txt"),
			new Fichier("C:\\fichier2.txt"),
			new Fichier("C:\\fichier3.txt"),
			new Fichier("C:\\fichier4.txt"),
			new Fichier("C:\\fichier5.txt"),
			new Fichier("C:\\fichier6.txt")
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

			"ABCD\r\n",
            "Lorem\r\n\r\nIpsum\r\n\r\n",
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

	/** Validité du statut des fichiers */
	public static final boolean[] EXTENSIONS_VALIDES = {
			true,
			true,
			true,
			true,
			true,
			true
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
	void testEcritureFichier() {
		Fichier fichier;

		for (int indiceTest = 0;
			 indiceTest < CONTENU_DE_FICHIERS.length;
			 indiceTest++) {

			fichier = OBJET_FICHIER[indiceTest];

			fichier.ecritureFichier(CONTENU_DE_FICHIERS[indiceTest]);

			assertArrayEquals(fichier.contenuFichier(), CONTENU_DE_FICHIERS[indiceTest]);
		}
	}

	@Test
	void testExtensionValide(){
		Fichier fichier;

		for (int indiceTest = 0;
			 indiceTest < EXTENSIONS_VALIDES.length; indiceTest++) {

			fichier = OBJET_FICHIER[indiceTest];

			assertEquals(fichier.extensionValide(), EXTENSIONS_VALIDES[indiceTest]);
		}
	}

	@Test
	void TestNomFichier() {
		Fichier fichier;

		for (int indiceTest = 0;
			 indiceTest < NOM_FICHIER.length; indiceTest++) {

			fichier = new Fichier(LIEN_FICHIERS[indiceTest]);

			assertEquals(fichier.nomFichier(), NOM_FICHIER[indiceTest]);
		}
	}

	@Test
	void testContenuFichier() {
		Fichier fichier;
		
		for (int indiceTest = 0; 
    			indiceTest < OBJET_FICHIER.length; indiceTest++) {
			
			fichier = OBJET_FICHIER[indiceTest];
    		
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
	                indiceTest < OBJET_FICHIER.length;
	                indiceTest++) {
			 
			 	fichier = OBJET_FICHIER[indiceTest];

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
