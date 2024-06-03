/* 
 * ArbreBinaireHuffman.java        30/05/2024  
 * IUT De Rodez             Pas de copyrights
 */

package iut.info1.huffman.arbre;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Gère la création de l'arbre binaire
 */
public class ArbreBinaireHuffman {

    private String valeurNoeud;
    
    private ArbreBinaireHuffman noeudDroit;
    
    private ArbreBinaireHuffman noeudGauche;

    /**
     * Constructeur
     * @param valeurNoeud le caractère représenté par le noeud
     * @param noeudDroit le noeud fils droite du noeud actuel
     * @param noeudGauche le noeud fils gauche du noeud actuel
     */
    public ArbreBinaireHuffman(String valeurNoeud
	    , ArbreBinaireHuffman noeudGauche
	    , ArbreBinaireHuffman noeudDroit) {

	this.valeurNoeud = valeurNoeud;
	this.noeudGauche = noeudGauche;
	this.noeudDroit = noeudDroit;
    }

    /**
     * Permet de lier 2 éléments en fonction de leur
     * fréquence dans un arbre binaire lié à l'architecture
     * huffman
     * @param premierObject le premier élément que l'on veut lié
     * @param secondObject le second élément que l'on veut lié
     * @param frequencePremierObjet la fréquence du premier élément
     * @param frequenceSecondObjet la fréquence du second élément
     * @return l'arbre binaire ayant pour fils les 2 éléments 
     *         (dans l'ordre des fréquences)
     */
    public static ArbreBinaireHuffman lienEntreDeuxObjets(Object premierObject
	    , Object secondObject
	    , double frequencePremierObjet
	    , double frequenceSecondObjet) {

	ArbreBinaireHuffman premierArbre;
	ArbreBinaireHuffman secondArbre;

	if (premierObject instanceof ArbreBinaireHuffman) {
	    premierArbre = (ArbreBinaireHuffman)premierObject;
	} else {
	    premierArbre = new ArbreBinaireHuffman(premierObject.toString(), null, null);
	}

	if (secondObject instanceof ArbreBinaireHuffman) {
	    secondArbre = (ArbreBinaireHuffman)secondObject;
	} else {
	    secondArbre = new ArbreBinaireHuffman(secondObject.toString(), null, null);
	}

	return new ArbreBinaireHuffman("lien",
		                  (frequencePremierObjet > frequenceSecondObjet
		                   ? premierArbre : secondArbre), 
		                  (frequencePremierObjet > frequenceSecondObjet
			           ? secondArbre  : premierArbre)); 
    }

    /**
     * Permet de récupérer l'arbre binaire huffman
     * provenant d'une compression
     * @param tableauCaractereCompresse un tableau contenant un 
     * 		  caractère puis son code huffman associé
     * @return l'arbre binaire regénéré
     * @throws IllegalArgumentException si le tableau n'est pas valide
     */
    public static ArbreBinaireHuffman recuperationArbreHuffman(
	                                  String[][] tableauCaractereCompresse
	                              ) throws IllegalArgumentException {
	int niveauProfondeur;
	int positionChemin;

	boolean presenceChemins;
	boolean estValide;
	
	ArbreBinaireHuffman nouvelArbre;
	String[] tableauCodesHuffman = new String[tableauCaractereCompresse.length];
	Object[] tableauLiensHuffman = new Object[tableauCaractereCompresse.length];

	/* Tableau contenant les chemins possibles. La taille est le carré de la taille du tableau de caractères compressés */
	Object[] tableauCheminsSousJacentHuffman =
		new String[ tableauCaractereCompresse.length
		          * tableauCaractereCompresse.length];

	Object[] tableauCheminsHuffman =
		new Object[ tableauCheminsSousJacentHuffman.length 
	                  + tableauLiensHuffman.length];

	for (int indiceParcours = 0;
		 indiceParcours < tableauCodesHuffman.length;
		 indiceParcours++) {

	    tableauCodesHuffman[indiceParcours] 
		    = tableauCaractereCompresse[indiceParcours][1];
	}

	Arrays.sort(tableauCodesHuffman);

	estValide = true;
	for (int indiceParcours = 0;
		 indiceParcours < tableauCodesHuffman.length - 1;
		 indiceParcours++) {

	    if (tableauCodesHuffman[indiceParcours]
		       .equals(tableauCodesHuffman[indiceParcours + 1])) {

		estValide = false;
	    }
	}

	for (int indiceParcours = 0;
		 indiceParcours < tableauLiensHuffman.length;
		 indiceParcours++) {

	    if (tableauCodesHuffman[indiceParcours].length() > 1) {
		System.out.println("tableau liens huffman" 
	                          + tableauLiensHuffman[indiceParcours]);
		System.out.println("tableau codes huffman"
	                          + tableauCodesHuffman[indiceParcours]);
		tableauLiensHuffman[indiceParcours] =
			tableauCodesHuffman[indiceParcours]
			.substring(0, tableauCodesHuffman[indiceParcours].length() - 1);
	    }
	}

	tableauLiensHuffman = Arrays.stream(tableauLiensHuffman).distinct().toArray();

	positionChemin = 0;
	niveauProfondeur = 1;
	presenceChemins= true;
	while (presenceChemins) {

	    presenceChemins = false;
	    for (int indiceParcours = 0;
		     indiceParcours < tableauLiensHuffman.length;
		     indiceParcours++) {


		if (   tableauLiensHuffman[indiceParcours] instanceof String 
		    && tableauLiensHuffman[indiceParcours].toString().length()
		       > niveauProfondeur) {

		    tableauCheminsSousJacentHuffman[positionChemin] =
			    tableauLiensHuffman[indiceParcours].toString()
			    .substring(0, niveauProfondeur + 1);
		    positionChemin += 1;
		    presenceChemins = true;
		}
	    }
	    niveauProfondeur += 1;
	}

	tableauCheminsSousJacentHuffman = Arrays.stream(tableauCheminsSousJacentHuffman)
		                                .distinct()
		                                .toArray();

	System.arraycopy(tableauCheminsSousJacentHuffman, 0, tableauCheminsHuffman
		       , 0, tableauCheminsSousJacentHuffman.length);

	System.arraycopy(tableauLiensHuffman, 0, tableauCheminsHuffman
		       , tableauCheminsSousJacentHuffman.length
		       , tableauLiensHuffman.length);

	tableauCheminsHuffman = Arrays.stream(tableauCheminsHuffman)
		                      .distinct()
		                      .toArray();

	for (Object lienHuffman : tableauCheminsHuffman) {

	    for (Object codeHuffman : tableauCodesHuffman) {

		if (   lienHuffman != null 
		    && lienHuffman.toString().equals(codeHuffman.toString())) {
		       estValide = false;
		}
	    }
	}

	if (!estValide) {
	    throw new IllegalArgumentException("Le tableau d'encodage est invalide !");
	}

	nouvelArbre = new ArbreBinaireHuffman("lien", null, null);
	for (String[] caractereCompresse : tableauCaractereCompresse) {

	    nouvelArbre.insertionCaractere(caractereCompresse[0], caractereCompresse[1]);
	}
	return nouvelArbre;
    }

    /**
     * Permet de creer un arbre binaire correspondant à
     * une structure huffmanienne.
     * 
     * @param caracteres les caractères à insérer dans l'arbre
     * @param frequences la fréquence des caractères précédent dans
     * 		  un document .txt
     */
    public static ArbreBinaireHuffman insertionHuffman(
	                                  Object[] caracteres,
	                                  double[] frequences
	                              ) {

	double plusPetiteFrequence;
	double secondePlusPetiteFrequence;
	double sommeFrequences;
	
	int indexPlusPetiteFrequence;
	int indexSecondePlusPetiteFrequence;
	int indexReequilibrage;

	double[] frequenceCaracteres = Arrays.copyOf(frequences, frequences.length);
	Object[] tblCaracteres = Arrays.copyOf(caracteres, caracteres.length);

	indexReequilibrage = 0;
	
	/* Initialisation avec des valeures minimales absurdes */
	plusPetiteFrequence = secondePlusPetiteFrequence = 2.0f;
	indexPlusPetiteFrequence = indexSecondePlusPetiteFrequence = -1;
	sommeFrequences = 0;
	for (double frequence : frequenceCaracteres) {
	    sommeFrequences += frequence;
	}

	if (   sommeFrequences < 0.99
	    || sommeFrequences > 1.01) {

	    throw new IllegalArgumentException("Erreur de frequencage");
	}

	while (frequenceCaracteres.length > 1) {

	    for (int indexParcours = 0;
		     indexParcours < frequenceCaracteres.length;
		     indexParcours++) {

		if (frequenceCaracteres[indexParcours] < plusPetiteFrequence) {
		    plusPetiteFrequence = frequenceCaracteres[indexParcours];

		    indexPlusPetiteFrequence = indexParcours;
		}
	    }

	    for (int indexParcours = 0;
		     indexParcours < frequenceCaracteres.length;
		     indexParcours++) {

		if (frequenceCaracteres[indexParcours] <= secondePlusPetiteFrequence 
			&& frequenceCaracteres[indexParcours] >= plusPetiteFrequence
			&& indexParcours != indexPlusPetiteFrequence) {

		    secondePlusPetiteFrequence = frequenceCaracteres[indexParcours];
		    indexSecondePlusPetiteFrequence = indexParcours;
		}
	    }

	    Object[] nouvelleTableCaracteres =
		    new Object[tblCaracteres.length - 1];
	    double[] nouvellesFrequencesDesCaracteres =
		    new double[frequenceCaracteres.length - 1];

	    for (int indexParcours = 0; 
		     indexParcours < frequenceCaracteres.length;
		     indexParcours++) {

		if (indexParcours ==
			Math.min(indexPlusPetiteFrequence,
				 indexSecondePlusPetiteFrequence)) {

		    nouvelleTableCaracteres[indexParcours] =
			    lienEntreDeuxObjets(
				    tblCaracteres[indexPlusPetiteFrequence],
				    tblCaracteres[indexSecondePlusPetiteFrequence],
				    frequenceCaracteres[indexPlusPetiteFrequence],
				    frequenceCaracteres[indexSecondePlusPetiteFrequence]
			    );

		    nouvellesFrequencesDesCaracteres[indexParcours] =
			    plusPetiteFrequence + secondePlusPetiteFrequence;

		} else if (indexParcours ==
			Math.max(indexPlusPetiteFrequence,
				 indexSecondePlusPetiteFrequence)) {
		    indexReequilibrage = 1;
		}else {
		    nouvelleTableCaracteres[indexParcours - indexReequilibrage]
			    = tblCaracteres[indexParcours];
		    nouvellesFrequencesDesCaracteres[indexParcours - indexReequilibrage]
			    = frequenceCaracteres[indexParcours];
		}
	    }
	    tblCaracteres       = nouvelleTableCaracteres;
	    frequenceCaracteres = nouvellesFrequencesDesCaracteres;
	    indexPlusPetiteFrequence        = -1;
	    indexSecondePlusPetiteFrequence = -1;
	    indexReequilibrage = 0;
	    plusPetiteFrequence = 1;
	    secondePlusPetiteFrequence = 1;
	}        
	return (ArbreBinaireHuffman)(tblCaracteres[0]);
    }

    /**
     * Permet le parcours préfixe d'un Arbre binaire huffman
     * @return tableau ayant pour chaque sous-tableau le caractère 
     *         et son code huffman
     */
    public String[][] parcoursProfondeur() {

	boolean profondeurEstValide;

	int indiceDonneesArbre;
	int nombreLiens;

	int[] cheminPossible;
	
	String valeurChemin;

	String[] liensExistants;
	String[] cheminsPossibles;
	String[] nouveauxCheminsPossibles;
	String[] cheminPartitionne;

	String[][] donneesArbre;
	String[][] nouvellesDonneesArbres;

	profondeurEstValide = true;
	indiceDonneesArbre = 0;
	nombreLiens = 0;
	donneesArbre = new String[0][2];
	nouvellesDonneesArbres = new String[1][2];

	cheminsPossibles = new String[]{"0", "1"};
	liensExistants   = new String[4];
	nouveauxCheminsPossibles = new String[4];
	valeurChemin = "";    
	while (profondeurEstValide) {

	    for (int indiceParcours = 0;
		    indiceParcours < cheminsPossibles.length;
		    indiceParcours++) {

		if (cheminsPossibles[indiceParcours] != null 
			&& !cheminsPossibles[indiceParcours].contains("null")){

		    cheminPartitionne = cheminsPossibles[indiceParcours]
			    .split(", ");

		    cheminPossible = new int[cheminPartitionne.length];

		    for (int indiceRemplacement = 0; 
			     indiceRemplacement < cheminPartitionne.length;
			     indiceRemplacement++) {

			cheminPossible[indiceRemplacement] = 
				Integer.parseInt(
					    cheminPartitionne[indiceRemplacement]
					);
		    }  

		    valeurChemin = this.valeurNoeudCherche(cheminPossible);

		    if (valeurChemin != null && valeurChemin.equals("lien")) {

			liensExistants[nombreLiens] = 
				cheminsPossibles[indiceParcours];
			nombreLiens += 1;

		    } else if (valeurChemin != null) {

			nouvellesDonneesArbres =
				new String[donneesArbre.length + 1][2];

			for (int indiceRemplacement = 0;
				 indiceRemplacement < donneesArbre.length;
				 indiceRemplacement++) {

			    nouvellesDonneesArbres[indiceRemplacement] 
				    = donneesArbre[indiceRemplacement];
			}

			nouvellesDonneesArbres[indiceDonneesArbre][0] =
				Integer.toBinaryString(
					    Character.hashCode(valeurChemin.charAt(0))
					);

			nouvellesDonneesArbres[indiceDonneesArbre][1] = 
				cheminsPossibles[indiceParcours];

			donneesArbre = nouvellesDonneesArbres;

			indiceDonneesArbre++;
		    }
		}
	    }

	    if (liensExistants.length == 0) {
		profondeurEstValide = false;
	    }

	    nouveauxCheminsPossibles = new String[nombreLiens * 2];

	    if (profondeurEstValide) {

		for (int indiceParcours = 0;
			indiceParcours < nouveauxCheminsPossibles.length;
			indiceParcours+=2) {

		    nouveauxCheminsPossibles[indiceParcours] =
			    liensExistants[indiceParcours / 2] + ", 0";

		    nouveauxCheminsPossibles[indiceParcours + 1] =
			    liensExistants[indiceParcours / 2] + ", 1";
		}
	    }
	    liensExistants = new String[nombreLiens * 2];
	    cheminsPossibles = nouveauxCheminsPossibles;
	    nombreLiens = 0;
	}         
	return donneesArbre; 
    }

    /**
     * Insere dans un arbre binaire une valeur donnée grace
     * au chemin qui lui est propre
     * @param caractereInsere Caractère à insérer dans l'arboresence
     * @param cheminInsertion Chemin du caractère
     */

    public void insertionCaractere(String caractereInsere, String cheminInsertion) {

	char cheminActuel;

	cheminActuel = cheminInsertion.charAt(0);

	if (cheminActuel == 'f') {
	    System.out.println("OUI");
	}

	if (cheminInsertion.length() == 1) {

	    if (cheminActuel == '0') {
		this.noeudGauche = new ArbreBinaireHuffman(caractereInsere, null, null);
	    } else {
		this.noeudDroit =  new ArbreBinaireHuffman(caractereInsere, null, null);
	    }
	} else {

	    if (cheminActuel == '0') {

		if (this.noeudGauche == null) {
		    this.noeudGauche = new ArbreBinaireHuffman("lien", null, null);
		}

		this.noeudGauche.insertionCaractere(caractereInsere, cheminInsertion.substring(1, cheminInsertion.length()));

	    } else {

		if (this.noeudDroit == null) {
		    this.noeudDroit = new ArbreBinaireHuffman("lien", null, null);
		}

		this.noeudDroit.insertionCaractere(caractereInsere, cheminInsertion.substring(1, cheminInsertion.length()));
	    }
	}
    }

    /**
     * Renvoi le noeud droit de l'instance de l'arbre
     * @return noeudDroit
     */
    public ArbreBinaireHuffman getNoeudDroit() {
	if (noeudDroit != null) {
	    return noeudDroit;
	} 
	return null;
    }

    /**
     * Renvoi le noeud gauche de l'instance de l'arbre
     * @return noeudGauche
     */
    public ArbreBinaireHuffman getNoeudGauche() {
	if (noeudGauche != null) {
	    return noeudGauche;
	} 
	return null;
    }

    /**
     * Permet d'accéder à la feuille correspondante à la séquence
     * de 1 et de 0 donnés. Respectivement le noeud droit et le
     * noeud gauche d'une séquence donnée
     * @param tblDeRecherche un tableau de 0 et de 1 pour retrouver
     * un caractère
     * @return le caractère cherché stockés dans l'arborescence
     */
    public String valeurNoeudCherche(int[] tblDeRecherche) {

	int[] nouveauTblDeRecherche;

	if (tblDeRecherche.length == 0) {
	    return valeurNoeud;
	}

	nouveauTblDeRecherche = new int[tblDeRecherche.length - 1];

	for (int indexDeRecherche = 0;
		indexDeRecherche < nouveauTblDeRecherche.length;
		indexDeRecherche++) {

	    nouveauTblDeRecherche[indexDeRecherche] 
		    = tblDeRecherche[indexDeRecherche + 1];
	}

	/* Si le chemin suivant est 0, on va dans le noeud gauche */
	if (tblDeRecherche[0] == 1) {

	    if (getNoeudDroit() == null) {
		return null;
	    }

	    return noeudDroit.valeurNoeudCherche(nouveauTblDeRecherche);
	}

	if (getNoeudGauche() == null) {
	    return null;
	}
	/* Sinon (le chemin suivant est 1), on va dans le noeud droit */
	return noeudGauche.valeurNoeudCherche(nouveauTblDeRecherche);
    }

    /**
     * Transforme une chaine de 0 et de 1 en une chaine de caractères
     * @param donneesFichierBinaire la chaine de caractères en binaire
     * composant le fichier.bin visé pour la décompression
     * @return le texte décompressé
     */
    public String[] restitutionTexteOriginal(String donneesFichierBinaire) {

	int nbZerosEnlevable;
	int ligne;

	int[] cheminSuivi;

	String donneesAnalyse;

	char symboleEncode;

	String[] tabContenuDecompresse;

	ArrayList<String> chaineDecompresse = new ArrayList<String>();
	ArrayList<Integer> cheminGenere = new ArrayList<Integer>();

	nbZerosEnlevable 
	= Integer.parseInt(donneesFichierBinaire
		 .substring(donneesFichierBinaire.length() - 8,
			donneesFichierBinaire.length()), 2);

	donneesAnalyse = donneesFichierBinaire
		.substring(0, donneesFichierBinaire.length() - 8);

	donneesAnalyse = donneesAnalyse.substring(0, donneesAnalyse.length() - 8)
		       + donneesAnalyse.substring(donneesAnalyse.length() - 8
		       + nbZerosEnlevable,
			 donneesAnalyse.length());

	ligne = 0;
	cheminSuivi = new int[1];
	chaineDecompresse.add("");
	for (int indiceParcours = 0;
		 indiceParcours < donneesAnalyse.length();
		 indiceParcours++) {

	    cheminGenere.add(Integer.valueOf(donneesAnalyse.charAt(indiceParcours)) - 48);

	    cheminSuivi = cheminGenere.stream().mapToInt(i -> i).toArray();

	    if (!this.valeurNoeudCherche(cheminSuivi).equals("lien")) {
		if (this.valeurNoeudCherche(cheminSuivi).equals("\n")) {
		    ligne++;
		    chaineDecompresse.add("");
		} else {
		    symboleEncode = (char) Integer.parseInt(this.valeurNoeudCherche(cheminSuivi), 2);
		    chaineDecompresse.set(ligne, chaineDecompresse.get(ligne) + symboleEncode);

		    cheminGenere.clear();
		    cheminSuivi = new int[1];
		}
	    }
	}
	tabContenuDecompresse = new String[chaineDecompresse.size()];
	chaineDecompresse.toArray(tabContenuDecompresse);
	return tabContenuDecompresse;
    }

    /**
     * Calcul la taille de l'arbre
     * @return la taille de l'arbre en entier
     */
    public int tailleMaxArbre() {
	return 1 + Math.max(
		(this.getNoeudGauche() != null
	       ? this.getNoeudGauche().tailleMaxArbre() : 0),
		(this.getNoeudDroit()  != null
	       ? this.getNoeudDroit() .tailleMaxArbre() : 0));
    }

    /**
     * renvoi la valeur de l'arbre 
     * @return valeurNoeud ou null si le noeud 
     */
    public String getValeurNoeud() {
	if (valeurNoeud != "lien" && valeurNoeud != null) {
	    return valeurNoeud;
	} 
	return null;
    }

    /**
     * Affiche l'arbre dans la console
     */
    public void afficherArbre() {
	afficherArbre("", true);
    }

    /**
     * Affiche sur le terminal l'arbre binaire généré
     * @param prefix Le chemin déjà généré de l'arbre
     * @param isTail Permet de savoir si l'élément affiché est une feuille
     */
    private void afficherArbre(String prefix, boolean isTail) {
	System.out.println(prefix + (isTail ? "└── " : "├── ") + valeurNoeud);
	if (noeudGauche != null) {
	    noeudGauche.afficherArbre(prefix + (isTail ? "    " : "│   "), noeudDroit == null);
	}
	if (noeudDroit != null) {
	    noeudDroit.afficherArbre(prefix + (isTail ? "    " : "│   "), true);
	}
    }

    @Override
    public String toString() {
	return this.valeurNoeud;
    }
}