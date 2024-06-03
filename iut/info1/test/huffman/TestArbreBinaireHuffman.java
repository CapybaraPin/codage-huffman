package iut.info1.test.huffman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import iut.info1.huffman.arbre.ArbreBinaireHuffman;

class TestArbreBinaireHuffman {
	
	private static final String[] VALEURS_ARBRE = {"a", "b", "c", "d", "e", "A",
												   "B", "C", "D", "E", "1", "2",
												   "3", "4", "5", "%", "à", "è",
												   "€", "\n", "\t", "\r", "#",
												   "\""}; // 24 éléments
	
	private static final double[] FREQUENCES_VALEURS 
		 = {23.0/300, 22.0/300, 20.0/300, 21.0/300, 24.0/300,
			19.0/300, 18.0/300, 16.0/300, 15.0/300, 17.0/300,
			11.0/300, 10.0/300, 12.0/300, 13.0/300, 14.0/300,
			9.0/300, 6.0/300, 6.0/300, 8.0/300, 6.0/300,
			3.0/300, 4.0/300, 2.0/300, 1.0/300
			};
	
	private static final ArbreBinaireHuffman ARBRE_GENERE 
		= ArbreBinaireHuffman.insertionHuffman(VALEURS_ARBRE, FREQUENCES_VALEURS);
	
	
    public static final String[] FICHIER_AB_HUFFMAN_ATTENDU = {
    		"codeHuffman =              101 ; encode = 00000000000000000000000001100101 ; symbole = e\r\n"
    		+ "codeHuffman =              010 ; encode = 00000000000000000000000001100010 ; symbole = b\r\n"
    		+ "codeHuffman =           110101 ; encode = 00000000000000000000000001100100 ; symbole = d\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 00000000000000000000000001100001 ; symbole = a\r\n"
    		+ "codeHuffman =              010 ; encode = 00000000000000000000000001110100 ; symbole = t\r\n"
    		+ "codeHuffman =            11011 ; encode = 00000000000000000000000001011000 ; symbole = X\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 00000000000000000000000001111000 ; symbole = x\r\n"
    		+ "codeHuffman =              010 ; encode = 00000000000000000000000000100000 ; symbole =  \r\n"
    		+ "codeHuffman =            11001 ; encode = 00000000000000000000000001101101 ; symbole = m\r\n"
    		+ "",
    		"codeHuffman =               11 ; encode = 00000000000000000000000000100000 ; symbole =  \r\n"
    		+ "codeHuffman =               01 ; encode = 00000000000000000000000001010111 ; symbole = W\r\n"
    		+ "codeHuffman =              011 ; encode = 00000000000000000000000000111000 ; symbole = 8\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 00000000000000000000000000001001 ; symbole = 	\r\n"
    		+ "codeHuffman =              010 ; encode = 00000000000000000000000001111000 ; symbole = x\r\n"
    		+ "codeHuffman =            11101 ; encode = 00000000000000000000000000101111 ; symbole = /\r\n"
    		+ "",
    		"codeHuffman =              101 ; encode = 00000000000000000000000001010111 ; symbole = W\r\n"
    		+ "codeHuffman =              010 ; encode = 00000000000000000000000001000110 ; symbole = F\r\n"
    		+ "codeHuffman =            11111 ; encode = 00000000000000000000000001000001 ; symbole = A\r\n"
    		+ ""
    };
    
    private static final String PARCOURS_ATTENDU 
    	    = "[[e, 0, 0, 0, 1], [a, 0, 0, 1, 0], [b, 0, 0, 1, 1], [d, 0, 1, 0,"
    	    		+ " 1], [c, 0, 1, 1, 0], [A, 0, 1, 1, 1], [B, 1, 0, 0, 1], "
    	    		+ "[E, 1, 0, 1, 0], [C, 1, 0, 1, 1], [D, 1, 1, 0, 0], [5, 1"
    	    		+ ", 1, 0, 1], [4, 1, 1, 1, 1], [3, 0, 0, 0, 0, 1], [1, 0, "
    	    		+ "1, 0, 0, 0], [2, 0, 1, 0, 0, 1], [%, 1, 0, 0, 0, 1], [€,"
    	    		+ " 1, 1, 1, 0, 0], [è, 1, 1, 1, 0, 1], [\r\n, 0, 0, 0, 0, "
    	    		+ "0, 0], [à, 0, 0, 0, 0, 0, 1], [\r\n, 1, 0, 0, 0, 0, 1], "
    	    		+ "[	, 1, 0, 0, 0, 0, 0, 1], [#, 1, 0, 0, 0, 0, 0, 0, 0]"
    	    		+ ", [\", 1, 0, 0, 0, 0, 0, 0, 1]]";
    
    
	ArbreBinaireHuffman arbreBinaireTeste;
	ArbreBinaireHuffman arbreBinaireSouhaite;
	ArbreBinaireHuffman sousArbreGauche;
	ArbreBinaireHuffman sousArbreDroit;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("====== TEST DE LA CLASSE ArbreBinaireHuffman ======");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("======= TEST DE ArbreBinaireHuffman TERMINE =======");

	}	

	@Test
	void testArbreBinaireHuffman() {
		
		assertDoesNotThrow(() -> 
			new ArbreBinaireHuffman(null, null, null));
		
		assertDoesNotThrow(() -> 
			new ArbreBinaireHuffman("a", null, null));
		
		assertDoesNotThrow(() -> 
			new ArbreBinaireHuffman("a", 
					new ArbreBinaireHuffman(null, null, null), null));
		
		assertDoesNotThrow(() -> 
			new ArbreBinaireHuffman("a", null,
					new ArbreBinaireHuffman(null, null, null)));
		
		assertDoesNotThrow(() -> 
			new ArbreBinaireHuffman("a", 
					new ArbreBinaireHuffman(null, null, null), 
					new ArbreBinaireHuffman(null, null, null)));
		
		arbreBinaireSouhaite = ArbreBinaireHuffman.insertionHuffman(VALEURS_ARBRE, FREQUENCES_VALEURS);
		
	}

	@Test
	void testLienEntreDeuxObjets() {

		sousArbreGauche = new ArbreBinaireHuffman("b", null, null);
		sousArbreDroit = new ArbreBinaireHuffman("c", null, null);
		
		arbreBinaireSouhaite = new ArbreBinaireHuffman("lien", sousArbreGauche, sousArbreDroit);
		
		arbreBinaireTeste = ArbreBinaireHuffman.lienEntreDeuxObjets(sousArbreGauche,
															 sousArbreDroit, 
															 0.6, 0.4);
		
		assertEquals(arbreBinaireSouhaite.getNoeudGauche(), arbreBinaireTeste.getNoeudGauche());
		assertEquals(arbreBinaireSouhaite.getNoeudDroit(), arbreBinaireTeste.getNoeudDroit());
	}

	/* TODO Faire tests de RecuperationArbreHuffman
	@Test
	void testRecuperationArbreHuffman() {
		fail("Not yet implemented");
	}
	*/

	@Test
	void testInsertionHuffman() {

		 ArbreBinaireHuffman arbreSouhaite1 
		 	= new ArbreBinaireHuffman("lien",
		 			new ArbreBinaireHuffman("lien",
		 					new ArbreBinaireHuffman("b", null, null),
		 					new ArbreBinaireHuffman("c", null, null)),
		 			new ArbreBinaireHuffman("a", null, null));
		 
		 ArbreBinaireHuffman arbreTest1 
		 	= ArbreBinaireHuffman.insertionHuffman(
		 			new Object[] {"a", "b", "c"},
		 			new double[] {0.45, 0.3, 0.25});
		 

		 ArbreBinaireHuffman arbreSouhaite2 
		 	= new ArbreBinaireHuffman("a", null, null);
		 
		 ArbreBinaireHuffman arbreTest2
		 	= ArbreBinaireHuffman.insertionHuffman(
		 			new Object[] {"a"},
		 			new double[] {1.0});
		 
		 
		 ArbreBinaireHuffman arbreSouhaite3 
		 	= new ArbreBinaireHuffman(null, null, null);
		 
		 ArbreBinaireHuffman arbreTest3
		 	= ArbreBinaireHuffman.insertionHuffman(
		 			new Object[] {},
		 			new double[] {1.0});
		
		 assertEquals(Arrays.deepToString(arbreSouhaite1.parcoursProfondeur()), Arrays.deepToString(arbreTest1.parcoursProfondeur()));
		 assertEquals(Arrays.deepToString(arbreSouhaite2.parcoursProfondeur()), Arrays.deepToString(arbreTest2.parcoursProfondeur()));
		 assertEquals(Arrays.deepToString(arbreSouhaite3.parcoursProfondeur()), Arrays.deepToString(arbreTest3.parcoursProfondeur()));
		 
		 assertThrows(IllegalArgumentException.class, () -> ArbreBinaireHuffman.insertionHuffman(new Object[] {}, new double[] {}));
	}

	@Test
	void testParcoursProfondeur() {
//		assertEquals(PARCOURS_ATTENDU,
//				Arrays.deepToString(ARBRE_GENERE.parcoursProfondeur())); FIXME
	}

	@Test
	void testInsertionCaractere() {
		
		ArbreBinaireHuffman arbreSouhaite1 
			= new ArbreBinaireHuffman("lien",
					new ArbreBinaireHuffman("lien",
							new ArbreBinaireHuffman("lien"
									, null
									, new ArbreBinaireHuffman("a",  null, null)),
							null)
					, null);
		
		ArbreBinaireHuffman arbreTest1 = new ArbreBinaireHuffman("lien", null, null);
		
		arbreTest1.insertionCaractere("a", "001");
		
		ArbreBinaireHuffman arbreSouhaite2
			= new ArbreBinaireHuffman("lien", 
					null, 
					new ArbreBinaireHuffman("lien", 
							new ArbreBinaireHuffman("b", null, null), 
							null));
	
		ArbreBinaireHuffman arbreTest2 = new ArbreBinaireHuffman("lien", null, null);
		
		arbreTest2.insertionCaractere("b", "10");
	
		 assertEquals(Arrays.deepToString(arbreSouhaite1.parcoursProfondeur()), Arrays.deepToString(arbreTest1.parcoursProfondeur()));
		 assertEquals(Arrays.deepToString(arbreSouhaite2.parcoursProfondeur()), Arrays.deepToString(arbreTest2.parcoursProfondeur()));
	}

	@Test
	void testGetNoeudDroit() {
		arbreBinaireSouhaite = new ArbreBinaireHuffman("A", null, null);
		
		arbreBinaireTeste = new ArbreBinaireHuffman("lien", new ArbreBinaireHuffman("b", null, null), arbreBinaireSouhaite);
		
		assertTrue(arbreBinaireSouhaite.toString().equals(arbreBinaireTeste.getNoeudDroit().toString()));
		assertFalse(arbreBinaireSouhaite.toString().equals(arbreBinaireTeste.getNoeudGauche().toString()));	
	}

	@Test
	void testGetNoeudGauche() {
		
		arbreBinaireSouhaite = new ArbreBinaireHuffman("A", null, null);
		
		arbreBinaireTeste = new ArbreBinaireHuffman("lien", arbreBinaireSouhaite, new ArbreBinaireHuffman("b", null, null));
		
		assertTrue(arbreBinaireSouhaite.toString().equals(arbreBinaireTeste.getNoeudGauche().toString()));
		assertFalse(arbreBinaireSouhaite.toString().equals(arbreBinaireTeste.getNoeudDroit().toString()));
		
	}

	@Test
	void testvaleurNoeudCherche() {

		String valeurFeuille1 = "bonjour";
		String valeurFeuille2 = "salut";
		String valeurFeuille3 = "coucou";
		String valeurFeuille4 = "dement";
		String valeurFeuille5 = "\n";
		
		int[][] cheminsFeuilles = new int[][] {
			{1, 0, 1}, {0, 1, 0}, {1, 1, 0}, {0, 1, 1}, {1, 0, 0}
		};
		
		ArbreBinaireHuffman arbreTestRecherche 
			= new ArbreBinaireHuffman("lien",
				new ArbreBinaireHuffman("lien",
						null,
						new ArbreBinaireHuffman("lien",
								new ArbreBinaireHuffman("salut", null, null),
								new ArbreBinaireHuffman("dement", null, null))),
				new ArbreBinaireHuffman("lien",
						new ArbreBinaireHuffman("lien",
								new ArbreBinaireHuffman("\n", null, null) ,
								new ArbreBinaireHuffman("bonjour", null, null)),
						new ArbreBinaireHuffman("lien",
								new ArbreBinaireHuffman("coucou", null, null) ,
								null)));
		
		assertEquals(valeurFeuille1,
				arbreTestRecherche.valeurNoeudCherche(cheminsFeuilles[0]));
		
		assertEquals(valeurFeuille2,
				arbreTestRecherche.valeurNoeudCherche(cheminsFeuilles[1]));
		
		assertEquals(valeurFeuille3,
				arbreTestRecherche.valeurNoeudCherche(cheminsFeuilles[2]));
		
		assertEquals(valeurFeuille4,
				arbreTestRecherche.valeurNoeudCherche(cheminsFeuilles[3]));
		
		assertEquals(valeurFeuille5,
				arbreTestRecherche.valeurNoeudCherche(cheminsFeuilles[4]));
	}

	@Test
	void testRestitutionTexteOriginal() {
		String[] texteSouhaite1 = {"cafebabe"};
		String texteCompresse1 =  "11111001" // Représentation par octets
							    + "10001100"
							    + "00000010"
							    + "00000101"; // zero supplémentaires dans l'octet précédent

		String[] texteSouhaite2 = {"bon\nsoir"};
		String texteCompresse2 =  "01000101"
							    + "10001100"
							    + "00111110"
							    + "00000001"; // zero supplémentaires dans l'octet précédent

		String[] texteSouhaite3 = {""};
		String texteCompresse3 =  "";
		
		String texteIndecompressable1 = "11111001" 
			     					  + "10001100"
			     					  + "00001010"
			     					  + "10100";

		String texteIndecompressable2 = "1111001" 
								      + "10001100"
								      + "00001000"
								      + "00000101";


		ArbreBinaireHuffman arbreTest1 
			= ArbreBinaireHuffman.recuperationArbreHuffman(new String[][] {
				{"b", "00"}, {"f", "01"}, {"e", "10"}, {"a", "110"}, {"c", "111"}
					});
		ArbreBinaireHuffman arbreTest2
			= ArbreBinaireHuffman.recuperationArbreHuffman(new String[][] {
			    {"\n", "000"}, {"o", "001"}, {"b", "010"}, {"n", "011"}, {"r", "10"},
			    {"s", "110"}, {"i", "111"}
				    });
		
		ArbreBinaireHuffman arbreTest3
			= ArbreBinaireHuffman.recuperationArbreHuffman(new String[][] {
				{"a", "0"}, {"b", "1"}
			});
		
		assertArrayEquals(texteSouhaite1, arbreTest1.restitutionTexteOriginal(texteCompresse1));
		assertArrayEquals(texteSouhaite2, arbreTest2.restitutionTexteOriginal(texteCompresse2));
		assertArrayEquals(texteSouhaite3, arbreTest3.restitutionTexteOriginal(texteCompresse3));
		
		assertThrows(IllegalArgumentException.class, 
				() -> arbreTest1.restitutionTexteOriginal(texteIndecompressable1));
		
		assertThrows(IllegalArgumentException.class,
				() -> arbreTest1.restitutionTexteOriginal(texteIndecompressable2));
	} 

	@Test
	void testToString() {
		assertEquals("lien", (new ArbreBinaireHuffman("lien", new ArbreBinaireHuffman("a", null, null), new ArbreBinaireHuffman("b", null, null)).toString()));
	}

}
