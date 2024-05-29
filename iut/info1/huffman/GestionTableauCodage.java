/*
 * GestionTableauCodage.java          28/05/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman;

import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 * Classe de gestion des tableau de codage
 * 
 * // TODO : JAVADOC
 */
public class GestionTableauCodage {

    /** Tableau de codage */
    private String[][] tabCodages;

    /** Le format du paramètre est invalide **/
    private static final String ERREUR_FORMAT_PARAMETRE =
            "erreur : Le format du paramètre renseigné invalide.";

    /**
     * Gestion de tableau de codage
     * <p>
     * Permettant la lecture, manipulation et vérification de celui-ci.
     * </p>
     * @param tableauCodage
     */
    public GestionTableauCodage(String[][] tableauCodage) {
        this.tabCodages = tableauCodage;
    }

    /**
     * Convertit un tableau de codage type String[] issue
     * d'un fichier.<br>
     * Exemple de tableau de codage en paramètre :
     * <ul>
     * 	<li>
     *  {"codeHuffman = 0010 ; encode = 1100010 ; symbole = b",<br>
     *   "codeHuffman = 0 ; encode = 1100101 ; symbole = e",<br>
     *   "codeHuffman = 101 ; encode = 1100001 ; symbole = a"} <br>
     * 	</li>
     *  Définit tabCodages en :
     * 	<li>
     * 	{{"0000000001100010", "0010"}, {"0000000001100101", "0"}, {"0000000001100001", "101"}}
     *  </li>
     * </ul>
     *
     * @param tabFichierCodages
     * @return tabCodages
     */
    public void conversionTableauCodage(String[] tabFichierCodages) {
        String ligneActuelle;
        String[] parties;
        String codeHuffman;
        String encode;

        if(tabFichierCodages.length == 0) {
            throw new IllegalArgumentException(ERREUR_FORMAT_PARAMETRE);
        }

        this.tabCodages = new String[tabFichierCodages.length][2];
        for (int index = 0; index < tabFichierCodages.length; index++) {
            ligneActuelle = tabFichierCodages[index];
            if(!verifierFormatTableauCodage(ligneActuelle)) {
                throw new IllegalArgumentException(ERREUR_FORMAT_PARAMETRE);
            }

            parties = ligneActuelle.split(";");
            codeHuffman = parties[0].split("=")[1].trim();
            encode = parties[1].split("=")[1].trim();
            this.tabCodages[index][0] = encode;
            this.tabCodages[index][1] = codeHuffman;
        }
    }

    /**
     * Formate le tableau de codage de cette manière :
     *  <li>
     *      {{"1100010", "0010"}, {"1100101", "0"}, {"1100001", "101"}}
     * 	</li>
     * 	retourne :
     * 	<li>
     *  {"codeHuffman = 0010 ; encode = 1100010 ; symbole = b",<br>
     *   "codeHuffman = 0 ; encode = 1100101 ; symbole = e",<br>
     *   "codeHuffman = 101 ; encode = 1100001 ; symbole = a"} <br>
     * 	</li>
     *
     */
    public String[] formaterABHuffman() {
        String[] contenuFichier;
        String encodageCaractere;
        String codeHuffman;
        String symbole;

        contenuFichier = new String[tabCodages.length];
        for (int indiceLigne = 0; indiceLigne < this.tabCodages.length; indiceLigne++) {
            encodageCaractere = this.tabCodages[indiceLigne][0];

            codeHuffman = String.join("", this.tabCodages[indiceLigne][1].split(", "));

            symbole = String.valueOf((char) Integer.parseInt(encodageCaractere,2));

            if (symbole.equals("\n")){
                symbole = "LF";
            }
            System.out.println(symbole);
            contenuFichier[indiceLigne] =  String.format(
                    "codeHuffman = %s ; encode = %s ; symbole = %s",
                    codeHuffman, encodageCaractere, symbole
            );
        }
        return contenuFichier;
    }

    /**
     * @return tabCodages
     */
    public String[][] getTabCodages() {
        return this.tabCodages;
    }


    /**
     * Vérifie si le format d'une ligne de codage est correct.
     * <br>
     * Tiens en compte de si :
     * <ul>
     *  <li>La ligne contient bien un code huffman</li>
     *  <li>La ligne contient bien un encodage</li>
     * 	<li>La ligne contient bien un symbole</li>
     *  <li>Les ";" sont bien présents</li>
     *  <li>La ligne est vide</li>
     *  <li>Le symbole correspond bien au caractère encodé</li>
     * </ul>
     * @param ligne d'un tableau de codage
     * @return boolean
     */
    public static boolean verifierFormatTableauCodage(String ligne) { // TODO : à tester avec des Regex
        String[] parties;
        String codeHuffman;
        String patternCodeHuffman;
        String encode;
        String patternEncode;
        char symbole;
        char symboleEncode;
        String patternSymbole;
        boolean estLF;
        char avantDernierCaractere;


        if (ligne.isEmpty()){
            err.println(ERREUR_FORMAT_PARAMETRE);
            return false;
        }

        parties = ligne.split(";");
        if(parties.length != 3) {
            err.println(ERREUR_FORMAT_PARAMETRE);
            return false;
        }

        codeHuffman = parties[0].split("=")[1].trim();
        encode = parties[1].split("=")[1].trim();
        symbole = ligne.charAt(ligne.length()-1);

        patternCodeHuffman = "^[01\\s]{"+ codeHuffman.length() +"}$";
        patternEncode = "^[01]{"+ encode.length() +"}$";
        patternSymbole = "^.$";
        if (!Pattern.matches(patternCodeHuffman, codeHuffman)
                || !Pattern.matches(patternEncode, encode)
                || !Pattern.matches(patternSymbole, String.valueOf(symbole))){
            err.println(ERREUR_FORMAT_PARAMETRE);
            return false;
        }

        avantDernierCaractere = ligne.charAt(ligne.length()-2);
        symboleEncode = (char) Integer.parseInt(encode, 2);
        estLF = avantDernierCaractere == 'L' && symbole == 'F';
        if(symbole != symboleEncode && !estLF ) {
            err.println(ERREUR_FORMAT_PARAMETRE);
            return false;
        }

        return true;
    }
}